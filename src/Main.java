public class Main {
    public static void main(String[] args) {

        // initialize the router
        if (Router.init()) {
            // start CMD
            MyConsole.RunAsCMD();
        } else {
            System.out.println("Initialization Failed. Router shutdown.");
        }
    }
}
