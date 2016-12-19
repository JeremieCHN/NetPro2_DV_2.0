import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.System.exit;

class MyConsole {
    static String getInputLine() throws IOException {
        System.out.print(Router.getLocalIP().toString('.') + "> ");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        return reader.readLine();
    }

    static void RunAsCMD() {
        String input;
        try {
            while ((input = getInputLine()) != null) {

                // add neighbor and the cost to it
                if (Pattern.matches("add [0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3} [0-9]* *$", input)) {
                    Pattern pattern = Pattern.compile("[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3} [0-9]*$");
                    Matcher matcher = pattern.matcher(input);

                    String str = null;
                    if (matcher.find())
                        str = matcher.group(0);

                    Pattern patternIP = Pattern.compile("[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}");
                    Matcher matcherIP = patternIP.matcher(input);

                    String strIP = null;
                    if (matcherIP.find())
                        strIP = matcherIP.group(0);

                    if (str != null && strIP != null) {
                        String strCost = str.substring(strIP.length()+1);
                        Router.addNeighbor(new IP(strIP, "\\."), Integer.valueOf(strCost));
                    }
                }

                // remove neighbor
                else if (Pattern.matches("remove [0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3} *$", input)) {
                    Pattern patternIP = Pattern.compile("[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}");
                    Matcher matcherIP = patternIP.matcher(input);

                    String strIP = null;
                    if (matcherIP.find())
                        strIP = matcherIP.group(0);

                    Router.RemoveNeighbor(new IP(strIP, "\\."));
                }

                // send message (not the DV route message) to other route
                /*else if (Pattern.matches("send to [0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3} *$", input)) {
                    Pattern IPPattern = Pattern.compile("[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}$");
                    Matcher IPMatcher = IPPattern.matcher(input);
                    if (IPMatcher.find()) {
                        IP destinationIP = new IP(IPMatcher.group(0), '.');

                        MyConsole.log("Please input the message to "+destinationIP.toString());
                        String extra = reader.readLine();

                        //Router.sendNewMessage(destinationIP, extra);
                    }
                }*/

                // show the route table
                else if (Pattern.matches("show route table *$", input)) {
                    Router.getRouteTable().show();
                }

                // show the neighbors had added
                else if (Pattern.matches("show neighbors *$", input)) {
                    System.out.println("Neighbors:");
                    for (RouteEntry entry : Router.getNeighbors()) {
                        System.out.println(entry.getDestinationIP().toString('.'));
                    }
                }

                // show the help
                else if (Pattern.matches("help *$", input)) {
                    System.out.println("send to A.B.C.D   ---- Send a message to the IP A.B.C.D");
                    System.out.println("show route table  ---- Show the route table now");
                    System.out.println("show neighbors    ---- Show neighbors of this router");
                    System.out.println("help              ---- Show the order can be used");
                    System.out.println("exit              ---- Exit the program");
                }

                // exit the route
                else if (Pattern.matches("exit *$", input))
                    exit(0);

                // other input
                else if (!Pattern.matches(" *$", input)) {
                    System.out.println("Your input is wrong, please check!");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            log("Something unexpected happened, exit the router.");
            exit(0);
        }
    }

    static void log(String str) {
        System.out.println(str);
    }
}
