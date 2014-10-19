package org.aua.echo.client;

import org.aua.echo.common.Pos;
import org.aua.echo.common.Product;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Armen on 12-Oct-14.
 */
public class Test {
    private Pos remoteObject = null;
    private Map<Long,Product> productList;

    Test(Pos ro){
        this.remoteObject = ro;
    }

    public void happyCaseAuthenticate()throws RemoteException {
        System.out.println("Authentication ...");
        System.out.println("WELCOME "+remoteObject.authenticate("z.armen", "pass"));
    }
    public void failCaseAuthenticate()throws RemoteException {
        System.out.println("Authentication ...");
        System.out.println("No Such User "+ remoteObject.authenticate("unknown", "pass") );
    }

    public void happyMakeSail()throws RemoteException {
        System.out.println("Loading List of Items ...");
        productList = remoteObject.getProductList();
        for (Long key: productList.keySet()) {
            System.out.println(productList.get(key).toString());
        }
        System.out.println("Please choose the Items ");
        chooseItem("z.armen",Long.parseLong("025444665189"),5);
        chooseItem("z.armen",Long.parseLong("0068700305245"),50);

        System.out.println("Total cost of the shopping cart is "+remoteObject.getTotalCost("z.armen"));

        removeItem("z.armen",Long.parseLong("0068700305245"),4);
        System.out.println("Total cost of the shopping cart is "+remoteObject.getTotalCost("z.armen"));

        System.out.println("Make Sale");
        ArrayList<Product> p = (ArrayList<Product>)remoteObject.makeSale("z.armen");
        System.out.println("p is "+ p.size());
    }

    public void failCaseMakeSail()throws RemoteException {
        //System.out.println("Make Sale ...");

    }






    private void chooseItem(String userName, long ups, int amount)throws RemoteException {
        System.out.println("Selected "+amount+" "+productList.get(ups).getName());
        remoteObject.addItem(userName,ups,amount);
    }
    private void removeItem(String userName, long ups, int amount)throws RemoteException {
        if(remoteObject.removeItem(userName,ups,amount)){
            System.out.println("Removed "+amount+" "+productList.get(ups).getName());
        } else {
            System.out.println("Wrong UPS please make sure you have type it correctly ");
        }
    }

}
