package org.aua.sample.server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Armen on 11-Oct-14.
 */
public class UserList {
    private Map<String, User> userList = new HashMap<String, User>();

    public UserList(){
        StringTokenizer token = new StringTokenizer(readFile(),",");
        while (token.hasMoreTokens()) {
            String username = token.nextToken();
            userList.put(username, new User(username, token.nextToken(), token.nextToken(), token.nextToken(), new Address(token.nextToken()), token.nextToken()) );

        }
    }

    public String authenticate(String username, String password){
        User user = userList.get(username);
        if(user != null){
            return user.isValidPassword(password);
        }
        else return null;
    }

    private String readFile() {
        String result = null;
        try (BufferedReader file = new BufferedReader(new FileReader("rmi-server/src/main/resources/userlist")) ) {
            String currentLine;
            while ((currentLine = file.readLine()) != null) {
                result += currentLine;
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Map<String,User> getUserList(){
        return this.userList;
    }
}
