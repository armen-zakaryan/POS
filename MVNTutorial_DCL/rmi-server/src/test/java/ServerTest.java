import junit.framework.Assert;
import org.aua.echo.common.Pos;
import org.aua.echo.common.Product;
import org.aua.sample.server.Server;
import org.junit.*;

import javax.naming.InitialContext;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
public class ServerTest {

    private static final String RMI_SERVER_NAME = "rmi://localhost/pos";
    private static InitialContext registry;
    private static Pos store;
    private String username = "u.user";

    /**
     *
     * @throws Exception
     */
    @BeforeClass
    public static void start() throws Exception {
        try {
            new Server();
            registry = new InitialContext(new Properties());
            System.err.println("Server ready");
            store = (Pos)registry.lookup(RMI_SERVER_NAME);
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }

    /**
     *
     * @throws Exception
     */
    @Test
    public void TestAuthenticate() throws Exception {
        System.out.println("Authentication ...");
        String notHappyResponse = store.authenticate(username,"wrongPass");
        Assert.assertNull(notHappyResponse);
        System.out.println("Please Try Again " + notHappyResponse);

        System.out.println("Authentication ...");
        String happyResponse = store.authenticate(username,"pass");
        Assert.assertNotNull(happyResponse);
        Assert.assertEquals("Message and response must be equal", username, happyResponse);
        System.out.println("Welcome!!! " + happyResponse);
    }

    /**
     *
     * @throws Exception
     */
    @Test
    public void getProductList() throws Exception {
        Map<Long,Product> response = store.getProductList();
        Assert.assertNotNull(response);
        System.out.println("*** Product List ***");
        for (Long key: response.keySet()) {
            System.out.println(response.get(key).toString());
        }
    }

    @Before
    public void addItemInit() throws Exception {
        store.authenticate(username,"pass");
    }
    @Test
    public void addItem() throws Exception {
        boolean res1 = store.addItem(username, Long.parseLong("025444665189"), 5);
        double total1 = store.getTotalCost(username);
        boolean res2 = store.addItem(username,Long.parseLong("0068700305245"),5);
        double total2 = store.getTotalCost(username);
        Assert.assertTrue(res1);
        Assert.assertTrue(res2);
        Assert.assertTrue(total2>total1);
    }

    @Before
    public void removeItemInit() throws Exception {
        store.authenticate(username,"pass");
        store.addItem(username, Long.parseLong("025444665189"), 5);
        store.addItem(username,Long.parseLong("0068700305245"),4);
    }
    @Test
    public void removeItem() throws Exception {
        double total1 = store.getTotalCost(username);
        boolean delResp = store.removeItem(username,Long.parseLong("0068700305245"),1);
        Assert.assertTrue(delResp);
        double total2 = store.getTotalCost(username);
        Assert.assertTrue(total2<total1);
    }

    @Before
    public void makeSaleInit() throws Exception {
        store.authenticate(username,"pass");
        store.addItem(username, Long.parseLong("025444665189"), 5);
        store.addItem(username,Long.parseLong("0068700305245"),5);
    }
    @Test
    public void makeAndEndSale() throws Exception {
        ArrayList<Product> notAddedCurtItemsList = (ArrayList<Product>)store.makeSale(username);
        Assert.assertTrue(notAddedCurtItemsList.size() == 0);
        double total = store.getSaleTotal(username);
        System.out.println("Sale total is "+total);
        boolean archiveCreation = store.endSale(username);
        Assert.assertTrue(archiveCreation);
    }


    @AfterClass
    public static void end() throws Exception {
        registry.unbind(RMI_SERVER_NAME);
        System.err.println("Server stopped");
    }

}