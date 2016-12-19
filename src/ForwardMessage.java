/*
import java.io.*;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ForwardMessage extends Thread {
    private Message message_;

    ForwardMessage(Message message) {
        super();
        message_ = message;
    }


    @Override
    public void run() {
        IP nextHop = Router.getTable().getNextHop(message_.getDestination());
        if (nextHop != null) {
            message_.addPoint(Router.getLocalIP());
            try {
                Socket socket = new Socket();

                socket.connect(new InetSocketAddress(Inet4Address.getByAddress(nextHop.getBytes()), Router.Port_listenMessage));

                OutputStream os = socket.getOutputStream();
                PrintWriter writer = new PrintWriter(os);
                writer.write(message_.toString() + "\r\n");
                writer.flush();

                MyConsole.log("Send message to " + nextHop.toString());

                InputStream is = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                MyConsole.log(nextHop.toString() + " response: " + reader.readLine());

                if (!socket.isClosed())
                    socket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            MyConsole.log("This router cannot reach the destination:" + message_.getDestination());
        }
    }
}
*/
