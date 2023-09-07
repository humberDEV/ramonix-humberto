import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;

public class Ramonix implements Serializable {

    // INICIALIZAMOS LAS VARIABLES NECESARIAS PARA INICIAR EL SERVIDOR
    private static int energia;
    private static final int PORT = 5000;
    private static Socket socket;
    private static ServerSocket server;

    // CUANDO RAMONIX SE CREA TENDRIAMOS EL SIGUIENTE CONSTRUCTOR
    public Ramonix(int energia) {
        System.out.println("Bienvenidos a RAMONIX!");
        this.energia = energia;
    }

    // EN ESTE CASO EL Main() ÚNICAMENTE LLAMARÁ AL CONSTRUCTOR DANDOLE LA ENERGÍA,
    // EN ESTE CASO SERÁ DE 500
    public static void main(String[] args) throws IOException, InterruptedException {
        Ramonix ramonix = new Ramonix(500);
        // INICIAMOS EL SERVER
        ramonix.iniciarServer();
    }

    // GETTERS Y SETTERS DE @energia

    public int getenergia() {
        return energia;
    }

    public void setenergia(int energia) {
        this.energia = energia;
    }

    // SE INICIA EL SERVIDOR, RECIBE EL NÚMERO DE HACKERS Y LOS ACEPTA (Socket)
    // DESPUÉS
    // EMPIEZA A RECIBIR ATAQUES DESDE LA CLASE "Hacker.java" CUANDO LA ENERGÍA SE
    // ACABA, RAMONIX CIERRA EL SERVER
    public static void iniciarServer() throws IOException, InterruptedException {
        server = new ServerSocket(PORT);
        System.out.println("SERVIDOR ACTIVO (" + server.getLocalSocketAddress() + ")\n");
        int nHackers = recibirHackers();
        aceptarHackers(nHackers);
        while (energia > 0) {
            enviarSenyalParar();
            recibirAtaque();
        }
        System.out.println("--- COMUNICO AL CLIENTE QUE CIERRE");
        server.close();
    }

    // SI LA ENERGÍA SE ACABA (energia = 0) ENVÍA UNA SEÑAL PARA PARAR EL HILO
    public static void enviarSenyalParar() throws IOException {
        OutputStream auxOut = socket.getOutputStream();
        DataOutputStream fluxOut = new DataOutputStream(auxOut);
        if (energia > 0) {
            fluxOut.writeBoolean(false);
        } else {
            fluxOut.writeBoolean(true);
        }
    }


    // RECIBE DESDE LA CLASE "Hacker.java" UN ATAQUE CON UNA CANTIDAD DETERMINADA DE
    // FUERZA E IMPRIME EL ATAQUE CAUSADO AL SERVIDOR, REDUCIENDO O AUMENTANDO LA
    // ENERGIA SEGÚN EL LADO DEL HACKER
    public static void recibirAtaque() throws IOException {
        InputStream datoEntrada = socket.getInputStream();
        DataInputStream flujoEntrada = new DataInputStream(datoEntrada);
        int ataque = Integer.parseInt(flujoEntrada.readUTF());
        energia += ataque;
        System.out.println("ATAQUE DESDE: "+recibirNombre()+" (" + ataque + ")");
        System.out.println("**** Energia: " + energia);
    }

    public static String recibirNombre() throws IOException {
        InputStream datoEntrada = socket.getInputStream();
        DataInputStream flujoEntrada = new DataInputStream(datoEntrada);
        String nombre = flujoEntrada.readUTF();
        return nombre;
    }

    // ACEPTAR A LOS HACKERS QUE SE CONECTEN AL SERVIDOR. ESTO SE HACE
    // LEYENDO UN ENTERO DEL FLUJO DE ENTRADA DEL SOCKET, Y DEVOLVIÉNDOLO COMO
    // RESULTADO
    public static int recibirHackers() throws IOException {
        socket = server.accept();
        System.out.println("Aceptando  :");
        InputStream datoEntrada = socket.getInputStream();
        DataInputStream flujoEntrada = new DataInputStream(datoEntrada);
        return flujoEntrada.readInt();
    }

    // ACEPTAN LA CONEXIÓN DE CADA HACKER SECUENCIALMENTE Y MOSTRANDO UN MENSAJE DE
    // ÉXITO PARA CADA UNO.
    public static void aceptarHackers(int nHackers) throws IOException {
        for (int i = 0; i < nHackers; i++) {
            socket = server.accept();
            System.out.println("CLIENTE " + (i + 1) + "... CONECTADO!\n");
        }
    }

}