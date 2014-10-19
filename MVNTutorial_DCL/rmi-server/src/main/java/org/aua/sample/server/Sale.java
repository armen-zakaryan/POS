package org.aua.sample.server;
import org.aua.echo.common.CartItem;

import java.util.ArrayList;

/**
 * Created by Armen on 12-Oct-14.
 */
public class Sale {
    private ShoppingCart shopingCart;
    private double saleTotal;

    /**
     *
     * @param sh
     */
    Sale(ShoppingCart sh){
        this.shopingCart = sh;
    }

    /**
     *
     * @return
     */
    public ArrayList<CartItem> verify(){
        ArrayList<CartItem> notAddedItems = new ArrayList<>();

        for(Long key : shopingCart.getCartItems().keySet()){
            CartItem ci = shopingCart.getCartItems().get(key);
            if(ci.getAmount() > ci.getAvailableNumber()){
                shopingCart.getCartItems().remove(ci.getUPS());
                notAddedItems.add(ci);
            } else {
                saleTotal += ci.getSum();
            }
        }
        return notAddedItems;
    }

    /**
     *
     * @return
     */
    public double getSaleTotal(){ return this.saleTotal; }

    /**
     *
     * @return
     */
    public ShoppingCart getShopingCart(){ return this.shopingCart; }

}
