package org.aua.echo.client;

import org.aua.echo.common.Pos;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Properties;

/**
 * Created by Armen on 12-Oct-14.
 */
public class Client {
    private Properties settings;
    public static void main(String[] args) throws Exception { new Client(); }

    public Client() throws RemoteException {
        System.out.println("Client is starting up");
        try {
            //Setting Security policy for dynamic class downloading
            System.setProperty("java.security.policy", "client.policy");
            System.setSecurityManager(new SecurityManager());

            //contact remote server
            String url = "rmi://localhost/pos";
            //JNDI VERSION
            //File propertiesDir = new File(System.getProperty("user.home"), ".jndi");
            //File propertiesFile = new File(propertiesDir, "system.properties");
            settings = new Properties(); //or default if no exist
            /*if (propertiesFile.exists()){
                try (FileInputStream in = new FileInputStream(propertiesFile)) {
                    settings.load(in);
                } catch (IOException e) {
                    System.out.println("Did not find jndi properties file");
                    e.printStackTrace();
                }
            }*/
            Pos store =  (Pos) new InitialContext(settings).lookup(url);
            //Tests
            Test testRemote = new Test(store);
            testRemote.happyCaseAuthenticate();
            testRemote.failCaseAuthenticate();
            testRemote.happyMakeSail();
            //testRemote.failCaseMakeSail();

        } catch (NamingException e) {
            System.out.println("The server could not be found");
            e.printStackTrace(System.err);
        } catch (RemoteException e) {
            System.out.println("The object could not be called");
            e.printStackTrace(System.err);
        } catch (Exception e) {
            System.out.println("The object could not be created");
            e.printStackTrace(System.err);
        }
    }

}

