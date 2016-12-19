import java.io.IOException;

/**
 * IPv4 address class
 * Created by xu国宝 on 2016/12/19.
 */
class IP {
    private String ip0;
    private String ip1;
    private String ip2;
    private String ip3;

    // constructor
    IP(String str, char division) throws IOException {
        String[] strings = str.split(Character.toString(division));
        if (strings.length != 4)
            throw new IOException("The String of IP is wrong");
        else {
            ip0 = strings[0];
            ip1 = strings[1];
            ip2 = strings[2];
            ip3 = strings[3];
        }
    }

    String toString(char division) {
        return ip0 + division + ip1 + division + ip2 + division + ip3;
    }

    byte[] getBytes() {
        byte[] bytes = new byte[4];
        for (int i = 0; i < 4; i++) {
            bytes[i] = Integer.to (toIntArray()[i]);
        }
    }

    // compare two ip (for sorting)
    int compare(IP ip) {
        int[] array1 = this.toIntArray();
        int[] array2 = ip.toIntArray();

        if (array1[0] != array2[0])
            return array1[0] - array2[0];

        else if (array1[1] != array2[1])
            return array1[1] - array2[1];

        else if (array1[2] != array2[2])
            return array1[2] - array2[2];

        else
            return array1[3] - array2[3];
    }

    boolean equals(IP ip) {
        return compare(ip) == 0;
    }

    private int[] toIntArray() {
        int[] array = new int[4];
        array[0] = Integer.valueOf(ip0);
        array[1] = Integer.valueOf(ip1);
        array[2] = Integer.valueOf(ip2);
        array[3] = Integer.valueOf(ip3);
        return array;
    }
}
