import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ListenDVThread extends Thread {
    @Override
    public void run() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(Router.Port_ListenDV);
            while (Router.isRunning) {
                Socket socket = serverSocket.accept();
                new DVServerThread(socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (serverSocket != null) serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class DVServerThread extends Thread {
        private Socket socket;

        DVServerThread(Socket socket_) {
            super();
            socket = socket_;
        }

        @Override
        public void run() {
            try {
                while (Router.isRunning) {
                    String strIP = socket.getRemoteSocketAddress().toString().substring(1);
                    IP neighborIP_ = new IP(strIP.substring(0, strIP.indexOf(':')));

                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String line = reader.readLine();

                    if (line != null) {

                        Router.addRoutesFromNeighbor(new RouteTable(line), neighborIP_);

                        PrintWriter writer = new PrintWriter(socket.getOutputStream());
                        writer.write(Router.getLocalIP().toString() + " Receive!\r\n");
                        writer.flush();
                    } else {
                        break;
                    }
                }

                String strIP = socket.getRemoteSocketAddress().toString().substring(1);
                IP ip = new IP(strIP.substring(0, strIP.indexOf(':')));
                Router.RemoveNeighbor(ip);
                MyConsole.log("Cannot receive DV from Neighbor(" + ip.toString() + "), code:0.");
            } catch (IOException e) {
                String strIP = socket.getRemoteSocketAddress().toString().substring(1);
                IP ip = new IP(strIP.substring(0, strIP.indexOf(':')));
                Router.RemoveNeighbor(ip);
                MyConsole.log("Cannot receive DV from Neighbor(" + ip.toString() + "), code:1.");
            } catch (JSONException e) {
                String strIP = socket.getRemoteSocketAddress().toString().substring(1);
                IP ip = new IP(strIP.substring(0, strIP.indexOf(':')));
                Router.RemoveNeighbor(ip);
                MyConsole.log("Cannot receive DV from Neighbor(" + ip.toString() + "), code:2.");
            }
        }
    }
}
