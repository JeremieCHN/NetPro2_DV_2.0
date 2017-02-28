import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.LinkedList;

/**
 * Route table only use to:
 * 1.save entry
 * 2.combine two table
 * 3.search next hop
 * <p>
 * Created by xu国宝 on 2016/12/19.
 */
public class RouteTable extends LinkedList<RouteEntry> {

    RouteTable() {
        super();
    }

    public RouteTable(JSONArray array) throws JSONException, IOException {
        super();
        for (int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);
            add(new RouteEntry(obj));
        }
    }

    // search for the next hop of a destination
    public IP getNextHop(IP destination) {
        for (RouteEntry e : this) {
            if (e.getDestinationIP().equals(destination))
                return e.getNextHopIP();
        }
        return null;
    }

    // combine two table
    // @param table, is a RouteTable, the table which would be combine with this.
    // @param Neighbors, is a RouteTable, if a entry whose next hop in table can reach,
    // the entry's next hop must be a destination of someone in Neighbors.
    // That means the entry whose next hop cannot reach will be cleared.
    public void combine(RouteTable table, RouteTable Neighbors) {
        for (RouteEntry e : table)
            this.add(e);

        // TODO clear the entry whose next hop cannot reach
        removeIf(entry -> (Neighbors.getNextHop(entry.getNextHopIP()) == null));

        // clear the entry whose cost is more
        for (int i = 1; i < size(); i++) {
            if (this.get(i).getDestinationIP().equals(this.get(i - 1).getDestinationIP()))
                this.get(i).setCost(-1);
        }

        removeIf(entry -> entry.getCost() == -1);
    }

    // after add a new entry, it must be sorted
    @Override
    public boolean add(RouteEntry entry) {
        boolean b = super.add(entry);
        sort(RouteEntry::compare);
        return b;
    }

    // print in the window
    void show() {
        MyConsole.log("Destination    |Next Hop       |Cost");
        MyConsole.log("---------------|---------------|----");
        forEach(entry -> {
            String str = "";

            IP destination = entry.getDestinationIP();
            str += destination.toString('.');
            for (int i = 0; i < 15 - destination.toString('.').length(); i++)
                str += " ";

            str += "|";
            IP nextHop = entry.getNextHopIP();
            str += nextHop.toString('.');
            for (int i = 0; i < 15 - nextHop.toString('.').length(); i++)
                str += " ";

            str += "|";
            str += Integer.toString(entry.getCost());

            MyConsole.log(str);
        });
    }

    // to string as format of a JSONArray
    @Override
    public String toString() {
        JSONArray array = new JSONArray();
        for (RouteEntry e : this) {
            array.put(e.toString());
        }
        return array.toString();
    }
}
