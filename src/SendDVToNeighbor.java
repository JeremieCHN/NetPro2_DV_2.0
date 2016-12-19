import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

public class SendDVToNeighbor extends Thread {
    private IP neighborIP_;
    private Socket socket_;

    public SendDVToNeighbor(IP neighborIP) throws IOException {
        neighborIP_ = neighborIP;
        socket_ = new Socket();
    }

    @Override
    public void run() {
        try {
            socket_.connect(new InetSocketAddress(Inet4Address.getByAddress(neighborIP_.getBytes()), Router.Port_ListenDV));
            socket_.setKeepAlive(true);

            long time = 5000;
            while (Router.isNeighborConnected(neighborIP_)) {
                Thread.sleep(time);

                if (!socket_.isConnected()) {
                    MyConsole.log("The connection with " + neighborIP_.toString() + " for send DV is closed, code:0.");
                    break;
                }

                if (!Router.isTableRefresh) {
                    time = 30000;

                    PrintWriter writer = new PrintWriter(socket_.getOutputStream());
                    writer.write(Router.getRouteTable().toString() + "\r\n");
                    writer.flush();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket_.getInputStream()));
                    String line = reader.readLine();

                    if (line == null) {
                        break;
                    }
                } else {
                    time = 1000;
                }
            }

            MyConsole.log("Cannot send DV to Neighbor(" + neighborIP_.toString() + "), code:0.");
            Router.RemoveNeighbor(neighborIP_);
        } catch (SocketException e) {
            e.printStackTrace();
            MyConsole.log("Cannot send DV to Neighbor(" + neighborIP_.toString() + "), code:1.");
            Router.RemoveNeighbor(neighborIP_);
        } catch (IOException e) {
            MyConsole.log("Cannot send DV to Neighbor(" + neighborIP_.toString() + "), code:2.");
            Router.RemoveNeighbor(neighborIP_);
        } catch (InterruptedException e) {
            MyConsole.log("Cannot send DV to Neighbor(" + neighborIP_.toString() + "), code:3.");
            Router.RemoveNeighbor(neighborIP_);
        }
    }
}
