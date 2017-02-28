import java.io.IOException;

public class Test {
    static public void main(String[] args) {
        try {
            RouteTable neighbors1 = new RouteTable();
            neighbors1.add(new RouteEntry(new IP("1.1.1.2", "\\."), new IP("1.1.1.2", "\\."), 1));
            neighbors1.add(new RouteEntry(new IP("1.1.1.3", "\\."), new IP("1.1.1.3", "\\."), 3));


            RouteTable table1 = new RouteTable();
            table1.combine(neighbors1, neighbors1);


            RouteTable table2 = new RouteTable();
            table2.add(new RouteEntry(new IP("1.1.1.3", "\\."), new IP("1.1.1.2", "\\."), 2));

            table1.combine(table2, neighbors1);

            table1.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
