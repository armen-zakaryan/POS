package org.aua.sample.server;

/**
 * Created by Armen on 12-Oct-14.
 */
public class Address {
    private String country;
    private String state;
    private String city;

    Address(String addressStr) {
        String[] addrArr = addressStr.split(";");
        this.country = addrArr[0];
        this.state = addrArr[1];
        this.city = addrArr[2];
    }

    public String getCountry() {
        return country;
    }
    public String getState() {
        return state;
    }
    public String getCity() {
        return city;
    }
}
