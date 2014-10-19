package org.aua.echo.common;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Armen on 11-Oct-14.
 */
public interface Pos extends Remote {
    /**
     * authenticate returns
     *  null if  such User does not exist or
     *  username if such User exist.
     */
    public String authenticate(String username, String password) throws RemoteException;
    public Map<Long,Product> getProductList() throws RemoteException;
    public boolean addItem(String userName, Long ups, int amount) throws RemoteException;
    public boolean removeItem(String userName, Long ups, int amount) throws RemoteException;
    public double getTotalCost(String userName) throws RemoteException;

    /**
     * makeSale returns
     *  null if all Products are added from shopping cart to sale or
     *  ArrayList Products which where not added to the sale.
     */
    public ArrayList makeSale(String userName) throws RemoteException;
    public double getSaleTotal(String userName) throws RemoteException;
    /**
     * endSale returns
     * true if an Archive Record created false otherwise.
     */
    public boolean endSale(String username) throws RemoteException;
}
