package org.aua.sample.server;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.ServerException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Properties;

/**
 * Created by Armen on 11-Oct-14.
 */
public class Server {
    Controller controller;
    private Properties settings;

    public static void main(String[] args) throws Exception {
        new Server();
        System.out.println("Start the server(Store)");
    }

    public Server() throws RemoteException {
        try {
            //Setting Security policy for dynamic class downloading
            System.setProperty("java.security.policy", "server.policy");
            System.setSecurityManager(new SecurityManager());

            LocateRegistry.createRegistry(Registry.REGISTRY_PORT); //with default Port
        } catch (java.rmi.server.ExportException ee) {
            System.out.println("Registry already exists!!");
        } catch (RemoteException e) {
            throw new ServerException("Could not create registry", e);
        }
        System.out.println("Created Registry");

        controller = new Controller(this, new UserList(),new ProductList(), new Archive());
        if (controller != null){ System.out.println("We have a remote object");}

        //get properties JNDI
        File propertiesDir = new File(System.getProperty("user.home"), ".jndi");
        File propertiesFile = new File(propertiesDir, "system.properties");
        settings = new Properties(); //or default if no exist
        if (propertiesFile.exists()) {
            try (FileInputStream in = new FileInputStream(propertiesFile)) {
                settings.load(in);
            } catch (IOException e) {
                System.out.println("Did not find jndi properties file");
                e.printStackTrace();
            }
        }

        //Rebind the pos to the registry
        try{
            InitialContext ctx = new InitialContext(settings);
            ctx.rebind("rmi://localhost/pos", controller);
        }
        catch(NamingException e) {
            System.out.println("Naming Exception");
            throw new ServerException("Server cannot bind to the naming service", e);
        }

    }
}
