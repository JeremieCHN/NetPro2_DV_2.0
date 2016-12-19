import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

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

    RouteEntry(JSONObject obj) throws JSONException, IOException {
        DestinationIP_ = new IP(obj.getString("Destination"), '.');
        NextHopIP_ = new IP(obj.getString("NextHop"), '.');
        Cost_ = obj.getInt("Cost");
    }

    // getters
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

    // compare two entry for sorting
    static int compare(RouteEntry entry1, RouteEntry entry2) {
        if (entry1.getDestinationIP().equals(entry2.getDestinationIP()))
            return entry1.getCost() - entry2.getCost();
        else
            return entry1.getDestinationIP().compare(entry2.getDestinationIP());
    }

    // to string as the format of json
    @Override
    public String toString() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("Destination", DestinationIP_.toString('.'));
            obj.put("NextHop", DestinationIP_.toString('.'));
            obj.put("Cost", Cost_);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj.toString();
    }
}
