package org.aua.sample.server;

import org.aua.echo.common.CartItem;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Armen on 11-Oct-14.
 */
public class ShoppingCart {
    private double totalCost;
    private String owner;
    private Map<Long,CartItem> cartItems;

    public ShoppingCart(String userName) {
        totalCost = 0;
        this.cartItems = new HashMap<>();
        this.owner = userName;
    }

    public void addItem(Long ups, CartItem ci){
        CartItem cartItem = cartItems.get(ups);
        if(cartItem == null){
            this.cartItems.put(ups, ci);
        }else {
            cartItem.changeAmount(ci.getAmount());
        }
        this.totalCost+=ci.getSum();
    }

    public boolean removeItem(Long ups, int amount){
        CartItem cartItem = cartItems.get(ups);
        if(cartItem == null){
            return false;
        }else {
            if(cartItem.getAmount() <= amount){
                amount = cartItem.getAmount();
                this.cartItems.remove(ups);
            }else {
                cartItem.changeAmount(-amount);
            }
            this.totalCost -= cartItem.getPrice()*amount;
            return true;
        }
    }

    public double getTotalCost(){ return this.totalCost; }
    public Map<Long,CartItem> getCartItems(){ return this.cartItems; }
    public String getOwner(){return this.owner;}
}
