/*
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Message {
    private IP Source;
    private IP Destination;
    private List<IP> PointList;
    private String ExtraStr;

    // constructors

    public Message(String ExtraStr_) {
        PointList = new LinkedList<>();
        ExtraStr = ExtraStr_;
    }

    public Message(JSONObject jsonObject) throws JSONException, IOException {
        Source = new IP(jsonObject.getString("Source"), '.');
        Destination = new IP(jsonObject.getString("Destination"), '.');
        ExtraStr = jsonObject.getString("ExtraStr");

        PointList = new LinkedList<>();
        JSONArray array = jsonObject.getJSONArray("PointList");
        for (int i = 0; i < array.length(); i++) {
            PointList.add(new IP(array.getString(i), '.'));
        }
    }

    // gets and sets

    public void setSource(IP ip) {
        Source = ip;
    }

    public IP getSource() {
        return Source;
    }

    public void setDestination(IP destination) {
        Destination = destination;
    }

    public IP getDestination() {
        return Destination;
    }

    public List<IP> getPointList() {
        return PointList;
    }

    public String getExtraStr() {
        return ExtraStr;
    }

    public void addPoint(IP ip) {
        PointList.add(ip);
    }

    // format as json

    @Override
    public String toString() {
        return toJSONObject().toString();
    }

    public JSONObject toJSONObject() {
        try {
            JSONObject obj = new JSONObject();
            obj.put("Source", Source.toString());
            obj.put("Destination", Destination.toString());
            obj.put("PointList", PointList);
            obj.put("ExtraStr", ExtraStr);
            return obj;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    // print in the window

    public void show() {
        System.out.println("Source:" + Source.toString());
        System.out.println("Destination:" + Destination.toString());
        System.out.println("PointList:");
        for (int i = 0; i < PointList.size(); i++) {
            System.out.println("          " + PointList.get(i).toString());
        }
    }
}
*/
