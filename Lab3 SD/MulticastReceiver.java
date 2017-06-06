import java.io.*;
import java.net.*;
import java.util.Scanner;
/**
 *
 * @author lycog
 */
public class MulticastReceiver {
    public static void main(String[] args) {

        MulticastSocket socket = null;
        DatagramPacket inPacket = null;
        byte[] inBuf = new byte[256];

        DatagramSocket socket_send = null;
        DatagramPacket outPacket = null;

        byte[] outBuf;
        final int PORT_SEND = 9000;
        Scanner s = new Scanner(System.in);
        try {
            //Prepare to join multicast group
            socket = new MulticastSocket(8888);
            InetAddress address = InetAddress.getByName("224.2.2.3");
            socket.joinGroup(address);

            while (true) {
                inPacket = new DatagramPacket(inBuf, inBuf.length);
                socket.receive(inPacket);
                String msg = new String(inBuf, 0, inPacket.getLength());
                System.out.println("From " + inPacket.getAddress() + " Msg : " + msg);
                try {
                    socket_send = new DatagramSocket();
                    long counter = 0;
                    msg = "Guilherme: " + s.nextLine();
                    outBuf = msg.getBytes();
                    //Send to multicast IP address and port
                    outPacket = new DatagramPacket(outBuf, outBuf.length, address, PORT_SEND);
                    socket_send.send(outPacket);
                    System.out.println("Server sends : " + msg);
                    try {
                        Thread.sleep(500);
                    }
                    catch (InterruptedException ie) {
                    }
                }
                catch (IOException ioe) {
                    System.out.println(ioe);
                }
            }
        }
        catch (IOException ioe) {
            System.out.println(ioe);
        }
    }
}
