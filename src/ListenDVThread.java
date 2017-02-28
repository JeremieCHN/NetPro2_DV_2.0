import org.json.JSONArray;
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
            while (true) {
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
        private IP neighborIP_;

        DVServerThread(Socket socket_) {
            super();
            socket = socket_;
            String strIP = socket.getRemoteSocketAddress().toString().substring(1);
            try {
                neighborIP_ = new IP(strIP.substring(0, strIP.indexOf(':')), "\\.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                while (true) {
                    MyConsole.log("receive a connection");

                    socket.setSoTimeout(60);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String line = reader.readLine();

                    if (line != null) {

                        Router.addRoutesFromNeighbor(new RouteTable(new JSONArray(line)));

                        PrintWriter writer = new PrintWriter(socket.getOutputStream());
                        writer.write(Router.getLocalIP().toString() + " Receive!\r\n");
                        writer.flush();
                    } else {
                        break;
                    }
                }

                Router.RemoveNeighbor(neighborIP_);
                MyConsole.log("Cannot receive DV from Neighbor(" + neighborIP_.toString('.') + "), code:0.");
            } catch (IOException e) {
                Router.RemoveNeighbor(neighborIP_);
                MyConsole.log("Cannot receive DV from Neighbor(" + neighborIP_.toString('.') + "), code:1.");
            } catch (JSONException e) {
                Router.RemoveNeighbor(neighborIP_);
                MyConsole.log("Cannot receive DV from Neighbor(" + neighborIP_.toString('.') + "), code:2.");
            }
        }
    }
}
