package org.aua.sample.server;

/**
 * Created by Armen on 12-Oct-14.
 */
public class User {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private Address address;
    private String phoneNumber;

    public User(String username, String password, String firstName, String lastName, Address address, String phoneNumber){
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    //Checking is done inside User Class for security reasons.
    public String isValidPassword(String password){
        if(this.password.equals(password)){
            return this.username;
        } else return null;
    }

    //Getters
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public Address getAddress() {
        return address;
    }

}
