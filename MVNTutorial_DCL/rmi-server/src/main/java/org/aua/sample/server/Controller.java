package org.aua.sample.server;
import org.aua.echo.common.CartItem;
import org.aua.echo.common.Pos;
import org.aua.echo.common.Product;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Armen on 11-Oct-14.
 */
public class Controller extends UnicastRemoteObject implements Pos {
    private Server store = null;
    private UserList userList = null;
    private ProductList productList = null;
    private Archive archive = null;
    private Map<String, ShoppingCart> activeUsersShoppingCarts = new HashMap<>();
    private Map<String, Sale> curentSalse = new HashMap<>();

    public Controller(Server st, UserList ul, ProductList pl, Archive a) throws RemoteException {
        this.store = st;
        this.userList = ul;
        this.productList = pl;
        this.archive = a;
    }

    /**
     * authenticate returns
     * -1 if  such User does not exist or
     * an integer > 0 if such User exist.
     * @param username
     * @param password
     */
    @Override
    public String authenticate(String username, String password) throws RemoteException {
        String authUser = this.userList.authenticate(username, password);
        if(authUser != null){
            this.activeUsersShoppingCarts.put(username,new ShoppingCart(username));
        }
        return authUser;
    }

    @Override
    public Map<Long,Product> getProductList() throws RemoteException {
        return productList.getProductList();
    }

    /**
     * Add Item into shopping cart.
     * @param userName
     * @param ups
     * @param amount
     */
    @Override
    public boolean addItem(String userName, Long ups, int amount) throws RemoteException {
        ShoppingCart shopingCurt = this.activeUsersShoppingCarts.get(userName);
        if (shopingCurt != null){
            shopingCurt.addItem(ups,new CartItem(productList.getProductList().get(ups),amount));
            return true;
        } else { return false; }
    }
    @Override
    public double getTotalCost(String userName) throws RemoteException{
        ShoppingCart shoppingCart = this.activeUsersShoppingCarts.get(userName);
        if(shoppingCart != null){
            return shoppingCart.getTotalCost();
        } else { return 0; }
    }

    @Override
    public double getSaleTotal(String userName) throws RemoteException{
        Sale sale = this.curentSalse.get(userName);
        if(sale != null){
            return sale.getSaleTotal();
        }  else { return 0; }
    }

    /**
     * makeSale returns
     * null if all Products are added from shopping cart to sale or
     * ArrayList Products which where not added to the sale.
     * @param userName
     * @param ups
     * @param amount
     */
    @Override
    public boolean removeItem(String userName, Long ups, int amount) throws RemoteException {
        return this.activeUsersShoppingCarts.get(userName).removeItem(ups,amount);
    }

    /**
     * makeSale returns
     * null if all Products are added from shopping cart to sale or
     * ArrayList Products which where not added to the sale.
     *
     * @param userName
     */
    @Override
    public ArrayList makeSale(String userName) throws RemoteException {
        Sale sale = new Sale(this.activeUsersShoppingCarts.get(userName));
        this.curentSalse.put(userName,sale);
        this.activeUsersShoppingCarts.remove(userName);
        ArrayList<CartItem> notAddedItems = new ArrayList<>();
        return sale.verify();
    }

    /**
     * endSale returns
     * true if an Archive Record created false otherwise.
     */
    @Override
    public boolean endSale(String username) throws RemoteException {
        Sale sale = this.curentSalse.get(username);
        if(sale != null){
            archive.endSale(username, sale);
            return true;
        }
        return false;
    }
}
