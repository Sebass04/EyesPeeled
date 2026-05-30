package game.serino;

/**
 * La classe Serino è la classe principale del gioco. 
 * Essa avvia l'inizializzazione del database e crea una nuova partita rendendola visibile.
 * 
 * @author lucas
 */
public class Serino {

    /**
     * Metodo principale del programma, che funge da punto di ingresso per l'applicazione.
     * Inizializza il database e avvia una nuova partita.
     *
     * @param args Argomenti della linea di comando (non utilizzati in questo contesto).
     */
    public static void main(String[] args) {
        DataBaseInit db = new DataBaseInit();
        db.createDb(); 

        Partita p = new Partita();
        p.setVisible(true); 
    }
}

