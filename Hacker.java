import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.Socket;

class Hacker implements Runnable, Serializable, Comu {

    // INICIALIZAMOS TODAS LAS VARIABLES QUE DEFINEN UN HACHER (atributos):
    private String nombre;
    private int fuerza;
    private int tiempoAtaque;
    private boolean lado;
    private boolean estaAtacando;
    private SalaReuniones sala;
    private static Socket socket;

    // CONSTRUCTOR DE LA CLASE HACKER, DESDE LA CLASE "Principal.java" SE
    // INICIALIZAN TODOS LOS HACKERS CON ESTOS ATRIBUTOS
    public Hacker(String nombre, int fuerza, int tiempoAtaque, boolean lado, boolean estaAtacando,
            SalaReuniones sala) throws IOException {
        this.nombre = nombre;
        this.fuerza = fuerza;
        this.tiempoAtaque = tiempoAtaque;
        this.lado = lado;
        this.estaAtacando = estaAtacando;
        this.sala = sala;
    }

    // INTENTA CONECTAR UN NUEVO SOCKET AL SERVIDOR, LUEGO COMPROBAR SI
    // NEO SE ENCUENTRA EN EL SERVIDOR, LUEGO ESTABLECER QUE ESTÁ
    // ATACANDO (VARIABLE), INICIAR EL ATAQUE Y FINALMENTE CERRAR EL SOCKET.
    public void conectarServer() throws IOException, InterruptedException {
        socket = new Socket(HOST, PORT);
        System.out.println("CLIENTE ACTIVO (" + nombre + ")" + socket.getLocalSocketAddress());
        combrobarNeoEnSala(socket);
        this.estaAtacando = true;
        iniciarAtaque(socket);
        socket.close();
        System.out.println("CLIENTE CERRADO (" + nombre + ")" + socket.getLocalSocketAddress());
    }

    // BÁSICAMENTE ESTE MÉTODO COMPRUEBA QUE EN LA SALA SE ENCUENTREN TODOS LOS
    // HACKERS, SIENDO ESTE, EN CONJUNTO CON LA SALA synchronized.
    // HACE LOS Wait() y NotifyAll() NECESARIOS PARA SINCRONIZARLO.
    public void combrobarNeoEnSala(Socket socket) throws IOException, InterruptedException {
        synchronized (sala) {
            sala.hackerLlega(this.nombre);
            switch (nombre) {

                // EN EL CASO DE QUE EL NOMBRE SEA "Neo", COMPRUEBA SI HAY 4 HACKERS O NO
                case "Neo":
                    sala.setNeo();
                    if (sala.obtenerHackers().size() == 4) {
                        System.out.println("Neo ha llegado. LOS 4 HACKERS ESTÁN  EN LA SALA.");
                        sala.notifyAll();
                    } else {
                        System.out.println(
                                "Neo ha llegado. FALTAN " + (4 - sala.obtenerHackers().size()) + " HACKERS");
                        sala.wait();
                    }
                    break;

                // EN EL CASO DE QUE EL NOMBRE SEA CUALQUIER OTRO, SIGNIFICARÁ QUE "Neo" (El
                // elegido) PUEDE ESTAR EN LA SALA O NO. SE COMPRUEBA:
                default:
                    if (sala.isNeo()) {
                        if (sala.obtenerHackers().size() == 4) {
                            System.out.println("Neo ha llegado. LOS 4 HACKERS ESTÁN  EN LA SALA.");
                            sala.notifyAll();
                        } else {
                            System.out.println(
                                    "Neo ha llegado. FALTAN " + (4 - sala.obtenerHackers().size()) + " HACKERS");
                            sala.wait();
                        }
                    } else {
                        System.out.println("Neo no ha llegado.");
                        sala.wait();
                    }
                    break;
            }

        }
    }

    // MÉTODO QUE REALIZA LOS ATAQUES DE CADA HACKER
    public void iniciarAtaque(Socket socket) throws IOException, InterruptedException {

        // TODOS EN UN PRINCIPIO ESTÁN ATACANDO, POR TANTO SIEMPRE ENTRARÁN A ESTE BUCLE
        while (estaAtacando) {

            // SI SE LE ENVÍA UNA SEÑAL DE PARADA DEL ATAQUE DESDE EL MÉTODO SENYAL PARAR,
            // SE TERMINARÁ EL ATAQUE
            if (senyalParar()) {
                estaAtacando = false;
                // EN CASO CONTRARIO SE REALIZARÁ EL ATAQUE EL ATAQUE SE REALIZA ENVIANDO UN
                // PAQUETE DE DATOS AL SOCKET CON LA FUERZA DETERMINADA
            } else {

                // DORMIMOS EL HILO DURANTE EL TIEMPO DE ATAQUE
                Thread.sleep(this.tiempoAtaque * 1000);

                // CREAMOS EL FLUJO DE SALIDA DE DATOS QUE ENVIAREMOS, EN ESTE CASO SERÁ UN int,
                // LA ENERGÍA QUE LE QUITAREMOS A RAMONIX
                OutputStream auxiliarSalida = socket.getOutputStream();
                DataOutputStream flujoSalida = new DataOutputStream(auxiliarSalida);
                int paqueteDato;

                // SI ES DEL LADO DE LOS "DESTRUCTORES" SU FUERZA SERÁ NEGATIVA
                if (lado) {
                    paqueteDato = this.fuerza * (-1);
                    // EN CASO CONTRARIO, LA FUERZA SERÁ POSITIVA
                } else {
                    paqueteDato = this.fuerza;
                }
                System.out.println(nombre + " atacando...");
                flujoSalida.writeUTF(String.valueOf(paqueteDato));
                enviarNombre(nombre);
            }
        }
    }

    // ESTE MÉTODO ENVIARÁ A "Ramonix.java" EL NOMBRE DEL HACKER QUE REALIZA EL
    // ATAQUE
    public void enviarNombre(String nombre) throws IOException {
        OutputStream auxiliarSalida = socket.getOutputStream();
        DataOutputStream flujoSalida = new DataOutputStream(auxiliarSalida);
        flujoSalida.writeUTF(nombre);
    }

    // LEE SI EL HILO DEBE PARAR O NO. ESTO ES ENVIADO DESDE "Ramonix.java"
    public boolean senyalParar() throws IOException {
        InputStream auxIn = socket.getInputStream();
        DataInputStream fluxIn = new DataInputStream(auxIn);
        boolean val = fluxIn.readBoolean();
        return val;
    }

    // CUANDO INICIA EL HILO CONECTAMOS CON EL SERVER
    @Override
    public void run() {
        try {
            conectarServer();
        } catch (IOException ex) {
        } catch (InterruptedException ex) {
        }
    }

}