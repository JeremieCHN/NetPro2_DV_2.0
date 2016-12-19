import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
/*

public class ListenForMessage extends Thread {

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(Router.Port_listenMessage);
            while (Router.isRunning) {
                Socket socket = serverSocket.accept();

                InputStream is = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String line = reader.readLine();

                try {
                    if (line == null) {
                        MyConsole.log("Receive a null message from " + socket.getRemoteSocketAddress().toString());
                        return;
                    }

                    MyConsole.log("Receive a message from " + socket.getRemoteSocketAddress().toString());

                    // response the last point
                    OutputStream os = socket.getOutputStream();
                    PrintWriter writer = new PrintWriter(os);
                    writer.write("Receive your message!");
                    writer.flush();
                    writer.close();

                    // deal with the message
                    Message message = new Message(new JSONObject(line));

                    // This point is the destination
                    if (message.getDestination().equals(Router.getLocalIP())) {
                        MyConsole.log("The message's destination is me!");
                        MyConsole.log("The message's source is " + message.getSource());
                        MyConsole.log("The message has forwarded by these points:");
                        for (IP i : message.getPointList()) {
                            MyConsole.log(i.show());
                        }
                        MyConsole.log("The message is: " + message.getExtraStr());
                    }

                    // this point is not the destination, forwarding the message
                    else {
                        MyConsole.log("Forwarding the message.");
                        new ForwardMessage(message).start();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
*/
