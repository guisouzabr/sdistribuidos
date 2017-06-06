import java.io.*;
import java.net.*;
import java.util.Scanner;
/**
 * @author lycog
 */
public class MulticastSender {
    public static void main(String[] args) {
        //recebe
        MulticastSocket socket_receive = null;
        DatagramPacket inPacket = null;

        byte[] inBuf = new byte[256];
        DatagramSocket socket = null;
        DatagramPacket outPacket = null;

        byte[] outBuf;
        final int PORT_SEND = 8888;
        final int PORT_RECEIVE = 9000;
        Scanner s = new Scanner(System.in);

        try {
            socket = new DatagramSocket();
            long counter = 0;
            String msg;

            while (true) {
                msg = "Guilherme: " + s.nextLine();
                outBuf = msg.getBytes();

                //Send to multicast IP address and port
                InetAddress address = InetAddress.getByName("224.2.2.3");
                outPacket = new DatagramPacket(outBuf, outBuf.length, address, PORT_SEND);

                socket.send(outPacket);

                System.out.println("Server sends : " + msg);
                try {
                    //Prepare to join multicast group
                    socket_receive = new MulticastSocket(PORT_RECEIVE);
                    socket_receive.joinGroup(address);
                    inPacket = new DatagramPacket(inBuf, inBuf.length);
                    socket_receive.receive(inPacket);
                    msg = new String(inBuf, 0, inPacket.getLength());
                    System.out.println("From " + inPacket.getAddress() + " Msg : " + msg);
                }
                catch (IOException ioe) {
                    System.out.println(ioe);
                }
                try {
                    Thread.sleep(500);
                }
                catch (InterruptedException ie) {

                }
            }
        }
        catch (IOException ioe) {
            System.out.println(ioe);
        }
    }
}
