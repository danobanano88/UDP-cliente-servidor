package ChatMulticast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Scanner;

class Chat {

    public static final int BUFSIZ = 1024;

    static void envia_mensaje_multicast(byte[] buffer, String ip, int puerto) throws IOException {
        DatagramSocket socket = new DatagramSocket();
        socket.send(new DatagramPacket(buffer, buffer.length, InetAddress.getByName(ip), puerto));
        socket.close();
    }

    static byte[] recibe_mensaje_multicast(MulticastSocket socket, int longitud_mensaje) throws IOException {
        byte[] buffer = new byte[longitud_mensaje];
        DatagramPacket paquete = new DatagramPacket(buffer, buffer.length);
        socket.receive(paquete);
        return paquete.getData();
    }

    static class Worker extends Thread {
        public void run() {
            while(true) {
                try{
                    InetAddress grupo = InetAddress.getByName("230.0.0.0"); //*multicast Address
                    MulticastSocket socket = new MulticastSocket(50000);
                    socket.joinGroup(grupo);

                    byte[] buffer = recibe_mensaje_multicast(socket, BUFSIZ);
                    System.out.println(new String(buffer, "UTF-8"));
                    socket.leaveGroup(grupo);
                    socket.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Worker w = new Worker();
        w.start();
        String nombre = args[0];
        Scanner scanner = new Scanner(System. in);
    
        while(true) {
            System.out.println("Ingrese el mensaje a enviar:");
            String mensaje = scanner. nextLine();
            byte buffer[] = String.format("%s:%s", nombre, mensaje).getBytes();
            envia_mensaje_multicast(buffer, "230.0.0.0",50000);
        }
    }

}