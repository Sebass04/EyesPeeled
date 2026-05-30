package game.serino;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * La classe {@code DataBaseInit} gestisce l'inizializzazione e la configurazione 
 * del database di gioco. Crea le tabelle necessarie e inserisce dati di esempio
 * se il database è vuoto. Inoltre, inizializza il gioco caricando gli oggetti dal database.
 * 
 * @author lucas
 */
public class DataBaseInit {

    /**
     * Crea il database e la tabella degli oggetti, se non esistono già.
     * Inserisce gli oggetti predefiniti nel database solo se la tabella è vuota.
     */
    public final void createDb() {
        try {
            // Connessione al database H2 con credenziali di default
            Connection c = DriverManager.getConnection("jdbc:h2:~/db_gioco", "init", "init");

            // Creazione della tabella OGGETTI se non esiste
            Statement stm = c.createStatement();
            stm.execute("CREATE TABLE IF NOT EXISTS OGGETTI("
                    + "ID INT PRIMARY KEY,"
                    + "NOME VARCHAR(255),"
                    + "PATH VARCHAR(255));");
            stm.close();

            // Verifica se ci sono dati nella tabella OGGETTI
            String checkDataSql = "SELECT COUNT(*) FROM OGGETTI";
            stm = c.createStatement();
            ResultSet result = stm.executeQuery(checkDataSql);
            result.next();
            int number = result.getInt(1); // Numero di record presenti
            result.close();

            // Se la tabella è vuota, inserisci dati predefiniti
            if (number == 0) {
                stm = c.createStatement();
                stm.execute("INSERT INTO OGGETTI VALUES" +
                        "(1, 'Biglietto', 'IconPaper')," +
                        "(2, 'Post-it', 'IconPostIt')," +
                        "(3, 'Primo Enigma', 'IconPaper')," +
                        "(4, 'Secondo Enigma', 'IconPaper')," +
                        "(5, 'Bigliettino', 'IconPaper')," +
                        "(6, 'Pomello', 'IconPomello')," +
                        "(7, 'Chiave Bianca', 'IconWhiteKey')," +
                        "(8, 'Chiave Verde', 'IconGreenKey')," +
                        "(9, 'Chiave Rossa', 'IconRedKey')," +
                        "(10, 'Chiave Blu', 'IconBlueKey')," +
                        "(11, 'Chiave Gialla', 'IconYellowKey')," +
                        "(12, 'Pezzo Puzzle 1', 'PezzoPuzzle1')," +
                        "(13, 'Pezzo Puzzle 2', 'PezzoPuzzle2')," +
                        "(14, 'Pezzo Puzzle 3', 'PezzoPuzzle3')," +
                        "(15, 'Pezzo Puzzle 4', 'PezzoPuzzle4')," +
                        "(16, 'Pezzo Puzzle 5', 'PezzoPuzzle5')," +
                        "(17, 'Pezzo Puzzle 6', 'PezzoPuzzle6')," +
                        "(18, 'Puzzle Completo', 'PuzzleCompleto')," +
                        "(19, 'Chiave Marrone', 'IconBrownKey');");
                stm.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseInit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Inizializza il gioco caricando tutti gli oggetti dal database e popolando
     * la lista degli oggetti nel {@code GameProcessor}.
     * 
     * @param gp l'istanza di {@code GameProcessor} in cui caricare gli oggetti.
     */
    public final void initGame(GameProcessor gp) {
        try {
            // Connessione al database H2
            Connection conn = DriverManager.getConnection("jdbc:h2:~/db_gioco", "init", "init");
            Statement stm = conn.createStatement();

            // Esecuzione della query per recuperare tutti gli oggetti dal database
            ResultSet result = stm.executeQuery("SELECT * FROM OGGETTI");

            // Aggiunta degli oggetti al GameProcessor
            while (result.next()) {
                gp.getAllobject().add(new AdvObject(result.getInt(1), result.getString(2), result.getString(3)));
            }

            result.close();
            stm.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
