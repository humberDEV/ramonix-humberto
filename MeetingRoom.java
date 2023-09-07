import java.io.Serializable;
import java.util.ArrayList;

class SalaReuniones implements Serializable {

    // CREAMOS LAS VARIABLES
    private boolean neo;
    private ArrayList<String> participants;

    // SI LLAMAMOS AL CONSTRUCTOR SIN PARÁMETROS LE DAMOS VALOR A neo = false E
    // INICIALIZAMOS LA LISTA DE PARTICIPANTES
    public SalaReuniones() {
        this.neo = false;
        this.participants = new ArrayList<String>();
    }

    // SI LLAMAMOS AL CONSTRUCTOR CON UN PARÁMETRO BOOLEANO RECUPERAMOS
    // EL VALOR DE neo CON SU RESPECTIVO GETTER
    public SalaReuniones(boolean isNeo) {
        this.neo = isNeo;
        this.participants = new ArrayList<String>();
    }

    // MÉTODOS GETTERS Y SETTERS (Además de los metodos para añadir y obtener los
    // particcipantes de la lista)

    public void addParticipant(String name) {
        this.participants.add(name);
    }

    public ArrayList<String> getParticipants() {
        return this.participants;
    }

    public boolean isNeo() {
        return neo;
    }

    public void setNeo() {
        this.neo = true;
    }

}