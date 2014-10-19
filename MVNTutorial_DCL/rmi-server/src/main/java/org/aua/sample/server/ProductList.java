package org.aua.sample.server;

import org.aua.echo.common.Product;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Armen on 11-Oct-14.
 */
public class ProductList {
    private Map<Long, Product> productList = new HashMap<>();

    public ProductList(){
        StringTokenizer token = new StringTokenizer(readFile(),",");
        token.nextToken();
        while (token.hasMoreTokens()) {
            Long ups = Long.parseLong(token.nextToken());
            this.productList.put(ups, new Product(ups,token.nextToken(),Double.parseDouble(token.nextToken()),Integer.parseInt(token.nextToken()) ) );
        }
    }

    private String readFile(){
        String result = null;
        try (BufferedReader file = new BufferedReader(new FileReader("rmi-server/src/main/resources/productlist")) ) {
            String currentLine;
            //file.readLine();//Since the first Line does not contains any valuable information
            while ((currentLine = file.readLine()) != null) {
                result += currentLine;
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Map<Long,Product> getProductList(){
        return this.productList;
    }

}
