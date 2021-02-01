package io.theflysong.github.example.example2;

import java.io.IOException;
import java.net.InetAddress;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            throw new RuntimeException("Args not enough");
        }
        else if (args[0].equals("-Server")){
            try {
                Server server = new Server(Integer.parseInt(args[1]));
                server.run();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (args[0].equals("-Client")) {
            if (args.length < 2) {
                throw new RuntimeException("Args not enough");
            }
            try {
                Client client = new Client(InetAddress.getByName(args[1]),Integer.parseInt(args[2]));
                client.run();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            throw new RuntimeException("Invalid Arg[1] " + args[1]);
        }
    }
}
