public interface Comu {

	// COLOR LETRAS
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	// DATOS PARA LA CONEXIÓN
	static final String HOST = "localhost";
	static final int PORT = 5000;

	// LISTA DE HACKERS (HE AGRAGADO EL DAÑO DE ATAQUE, EL DAÑO, EL TIEMPO DE
	// ATAQUE, EL LADO (bueno: false, malo:true) Y UN BOOLEANO DE ATAQUE)
	static final Object HACKERS[][] = { { "Neo", 20, 2, true, false }, { "P4q1T0", 10, 1, true, false },
			{ "PaU3T", 10, 1, true, false }, { "Ab4$t0$", 10, 1, false, false } };

}
