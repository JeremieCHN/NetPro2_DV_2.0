/**
 * route entry, save destination, next hop and cost
 * compare with another entry
 * Created by xu国宝 on 2016/12/19.
 */
public class RouteEntry {
    private IP DestinationIP_;
    private IP NextHopIP_;
    private int Cost_;

    RouteEntry(IP destination, IP nextHop, int cost) {
        DestinationIP_ = destination;
        NextHopIP_ = nextHop;
        Cost_ = cost;
    }


    public IP getDestinationIP() {
        return DestinationIP_;
    }

    public IP getNextHopIP() {
        return NextHopIP_;
    }

    public int getCost() {
        return Cost_;
    }

    public void setCost(int cost) {
        Cost_ = cost;
    }


    static int compare(RouteEntry entry1, RouteEntry entry2) {
        if (entry1.getDestinationIP().equals(entry2.getDestinationIP()))
            return entry1.getCost() - entry2.getCost();
        else
            return entry1.getDestinationIP().compare(entry2.getDestinationIP());
    }
}
