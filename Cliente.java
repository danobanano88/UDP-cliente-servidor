import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Cliente {
    public static void main(String[] args) {

        final int PuertoServidor = 4020;
        byte[] buffer = new byte[1024];     


        try {
            InetAddress direccionServidor;
            direccionServidor = InetAddress.getByName("localhost");
            
            DatagramSocket socketUDP = new DatagramSocket();
            String mensaje = "Hola";
            buffer = mensaje.getBytes();

            DatagramPacket pregunta = new DatagramPacket(buffer, buffer.length, direccionServidor, PuertoServidor);

            System.out.println("Peticion enviada");
            socketUDP.send(pregunta);
          

            DatagramPacket peticion = new DatagramPacket(buffer, buffer.length);

            socketUDP.receive(peticion);
            System.out.println("Peticion recibida");
            mensaje = new String(peticion.getData());
            System.out.println(mensaje);

            socketUDP.close();


        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }  catch (IOException e) {
            e.printStackTrace();
        }
        
    }
}
