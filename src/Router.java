import java.io.IOException;
import java.net.Inet4Address;

/**
 * Router class, the operation and message of the local router
 * Created by xu国宝 on 2016/12/19.
 */
public class Router {
    private Router() {}

    static private RouteTable Neighbors_;
    static private IP LocalIP_;
    static private RouteTable LocalTable_;
    static boolean isTableRefresh = false;
    static int Port_ListenDV = 8000;

    // run the router
    static boolean init() {
        try {
            // get the local IPv4 address
            Inet4Address address = (Inet4Address) Inet4Address.getLocalHost();
            LocalIP_ = new IP(address.getHostAddress(), "\\.");

            Neighbors_ = new RouteTable();
            LocalTable_ = new RouteTable();

            System.out.println("Local IP is " + LocalIP_.toString('.'));

            // start listen thread
            new ListenDVThread().start();
        } catch (Exception e) {
            e.printStackTrace();
            MyConsole.log("Router Initialization failed");
            return false;
        }
        return true;
    }

    // add neighbor
    static void addNeighbor(IP neighborIP, int cost) throws IOException {
        Neighbors_.add(new RouteEntry(neighborIP, neighborIP, cost));
        SendDVToNeighbor thread = new SendDVToNeighbor(neighborIP);
        thread.start();
    }

    // remove neighbor
    static void RemoveNeighbor(IP neighborIP) {
        for (RouteEntry entry : Neighbors_) {
            IP i = entry.getDestinationIP();
            if (i.equals(neighborIP)) {
                Neighbors_.remove(entry);
                break;
            }
        }
    }

    // check whether the neighbor alive
    static boolean isNeighborConnected(IP neighborIP) {
        for (RouteEntry entry : Neighbors_) {
            if (entry.getDestinationIP().equals(neighborIP))
                return true;
        }
        return false;
    }

    // combine the table from neighbor
    static void addRoutesFromNeighbor(RouteTable table) {
        isTableRefresh = true;
        LocalTable_.combine(table);
        LocalTable_.combine(Neighbors_);
        isTableRefresh = false;
    }

    // getters
    static RouteTable getNeighbors() {
        return Neighbors_;
    }

    static RouteTable getRouteTable() {
        return LocalTable_;
    }

    static IP getLocalIP() {
        return LocalIP_;
    }
}
