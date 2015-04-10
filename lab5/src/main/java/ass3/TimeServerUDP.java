package ass3;

import java.net.*;
import java.util.*;
import java.text.*;

class TimeServerUDP {
    public static void main(String[] args) {
        int port = (args.length == 0 ? 30000 : Integer.parseInt(args[0]));
        System.out.println("Running server on port: " + port);
        try { 
            DatagramSocket ds = new DatagramSocket(port);
            while (true) {
                DatagramPacket in = new DatagramPacket(new byte[4], 4);
                ds.receive(in);
                System.out.println("Got packet from: " + in.getAddress() + " length: " + in.getLength() + 
                    " port: " + in.getPort());
                InetAddress inet = in.getAddress();
                int outPort = in.getPort();
                String cmd = new String(in.getData(), 0, 4);
                Date now = new Date();
                switch (cmd) {
                    case "both": 
                        sendDatagram(ds, DateFormat.
                        getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, new Locale("sv", "SE")).
                        format(now).toString(), inet, outPort);
                        break;
                    case "time": 
                        sendDatagram(ds, 
                            DateFormat.getTimeInstance(DateFormat.LONG, new Locale("sv", "SE")).format(now).toString(),
                            inet, outPort);
                        break;
                    case "date": 
                        sendDatagram(ds, 
                            DateFormat.getDateInstance(DateFormat.LONG, new Locale("sv", "SE")).format(now).toString()
                            ,inet, outPort);
                        break;
                    default:     
                        sendDatagram(ds, "Specify either time, date, or both as arguments", inet, outPort);
                }
            }
        } catch (SocketException e) {
            System.err.println("Exception caught: " + e.getMessage());
        } catch (java.io.IOException e) {
            System.err.println("Expeptlin: " + e.getMessage());
        }
    }
    public static void sendDatagram(DatagramSocket ds, String data, InetAddress inet, int port) throws java.io.IOException {
        byte[] bytes = data.getBytes();
        DatagramPacket out = new DatagramPacket(bytes, bytes.length, inet, port);
        ds.send(out);
    }
}
