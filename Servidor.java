import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Servidor {

    public static void main(String[] args) {

        final int Puerto = 4020;
        byte[] buffer = new byte[1024];

        try {
            DatagramSocket socketUDP = new DatagramSocket(Puerto);
            System.out.println("Servidor iniciado en el puerto " + Puerto);

            while(true){
            DatagramPacket peticion = new DatagramPacket(buffer, buffer.length); //& Esta diciendo que el buffer tiene un size de 1024 bytes
            socketUDP.receive(peticion);
            System.out.println("Peticion recibida");
            String mensaje = new String(peticion.getData());
            System.out.println(mensaje);

            int puertoCliente = peticion.getPort();

            InetAddress direccionCliente = peticion.getAddress();

            mensaje = "Hola";
            buffer = mensaje.getBytes();

            DatagramPacket respuesta = new DatagramPacket(buffer, buffer.length, direccionCliente, puertoCliente);
            System.out.println("Respuesta enviada");

            socketUDP.send(respuesta);

            }     
            
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}