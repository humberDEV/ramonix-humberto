import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Ataque implements Comu {

    private Socket socket;

    public void abrirServidor(ArrayList<Thread> listaHackers) throws IOException {

        // CREAMOS UN NUEVO SOCKET CON EL HOST Y EL PUERTO PREDEFINIDO EN LA INTERFAZ
        // "Comu.java"
        socket = new Socket(HOST, PORT);

        // IMPRIMIMOS QUE HEMOS ABIERTO EL SERVIDOR Y SU DIRECCIÓN
        System.out.println("\nCLIENTE ACTIVO (Principal) / " + socket.getLocalSocketAddress());

        // Escribimos el número de elementos en la lista pasado como parámetro (hackerList)
        // como un entero en un flujo de salida (flujoSalida) asociado con el socket
        // (socket). Primero se obtiene el flujo de salida del socket
        // (flujoEntrada) y luego se crea un flujo de salida de datos (DataOutputStream) a
        // partir del flujo de salida. Finalmente, se escribe el tamaño de la lista en
        // el flujo de salida.
        OutputStream flujoEntrada = socket.getOutputStream();
        DataOutputStream flujoSalida = new DataOutputStream(flujoEntrada);
        flujoSalida.writeInt(listaHackers.size());

        //COMPROBAREMOS QUE EL SERVER BLOQUEE EL FLUJO DE DATOS
        socket.close();
    }

}
