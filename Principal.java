import java.io.IOException;
import java.util.ArrayList;

public class Principal implements Comu {

    private static ArrayList<Thread> listaHackers = new ArrayList<Thread>();
    private static SalaReuniones sala = new SalaReuniones();

    public static void main(String[] args) {

        try {
            // CREAMOS CADA OBJETO "HACKER" UNO POR UNO A TRAVÉS DE ESTE BUCLE FOR
            // ADEMÁS, LOS AÑADIMOS AL ARRAYLIST "listaHackers"
            for (int i = 0; i < HACKERS.length; i++) {
                String nombre = (String) HACKERS[i][0];
                int fuerza = (int) HACKERS[i][1];
                int tiempoAtaque = (int) HACKERS[i][2];
                boolean lado = (boolean) HACKERS[i][3];
                boolean estaAtacando = (boolean) HACKERS[i][4];

                Hacker hacker = new Hacker(nombre, fuerza, tiempoAtaque, lado, estaAtacando, sala);
                listaHackers.add(new Thread(hacker));
            }

            // INCIAMOS EL SERVIDOR DESDE LA CLASE ATAQUE. TODA LA INFORMACIÓN SOBRE ESTA
            // CLASE SE PUEDE ENCONTRAR EN EL ARCHIVO "Ataque.java"
            // ENVIAMOS EL ARRAYLIST CON TODOS LOS HACKERS (objetos) A ESTA CLASE, QUE SE
            // ENCARGARÁ DE EMPEZAR LOS ATAQUES.
            Ataque Inicial = new Ataque();
            Inicial.abrirServidor(listaHackers);

            // FINALMENTE EMPEZAMOS TODOS LOS HILOS PERTENECIENTES A LA LISTA DE HACKERS
            for (int i = 0; i < HACKERS.length; i++) {
                listaHackers.get(i).start();
            }
        } catch (IOException e) {
        }

        // SI RAMONIX ACABA IMPRIMIMOS POR PANTALLA QUE HA LLEGADO A SU FIN
        for (int i = 0; i < 4; i++) {
            try {
                listaHackers.get(i).join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(ANSI_BLUE + "RAMONIX TANGO DOWN!!");
    }
}
