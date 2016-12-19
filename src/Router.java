import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Router class, the operation and message of the local router
 * Created by xu国宝 on 2016/12/19.
 */
public class Router {
    private Router() {}

    static private List<IP> Neighbors_;
    static private IP LocalIP_;
    static private RouteTable LocalTable_;

    // run the router
    static boolean init() {
        try {
            Inet4Address address = (Inet4Address) Inet4Address.getLocalHost();
            LocalIP_ = new IP(address.getHostAddress(), '.');

            Neighbors_ = new LinkedList<>();
            LocalTable_ = new RouteTable();

            System.out.println("Local IP is " + LocalIP_.toString('.'));

        } catch (Exception e) {
            e.printStackTrace();
            MyConsole.log("Router Initialization failed");
            return false;
        }
        return true;
    }

    // add neighbor
    void addNeighbor(IP neighborIP) throws IOException {
        Neighbors_.add(neighborIP);
        SendDVToNeighbor thread = new SendDVToNeighbor(neighborIP);
        thread.start();
    }

    // remove neighbor
    void RemoveNeighbor(IP neighborIP) {
        for (IP i : Neighbors_) {
            if (i.equals(neighborIP)) {
                Neighbors_.remove(i);
                break;
            }
        }
    }

    // getters
    static List<IP> getNeighbors() {
        return Neighbors_;
    }

    static RouteTable getRouteTable() {
        return LocalTable_;
    }

    static IP getLocalIP() {
        return LocalIP_;
    }
}
