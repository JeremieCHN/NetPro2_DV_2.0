import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        if (Router.init()) {
            try {
                // Listening DV is the first.
                Router.BeginListenDV();

                // Add the Neighbors. Need input from console
                MyConsole.RunForAddNeighbor();
                Router.getTable().show();

                // Send DV route table to neighbors
                Router.BeginSendDV();

                // Begin to listen Message
                Router.BeginListenMessage();

                // run as cmd, accept input and do something
                MyConsole.RunAsCMD();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Initialization Failed. Router shutdown.");
        }
    }
}
