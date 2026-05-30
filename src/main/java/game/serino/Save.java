package game.serino;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * La classe Save gestisce il salvataggio e il caricamento dello stato del gioco.
 * Implementa l'interfaccia {@code Serializable} per consentire la serializzazione degli oggetti del gioco.
 */
public class Save implements Serializable {

    /**
     * Salva lo stato corrente del gioco su un file. La serializzazione dello stato del gioco
     * viene effettuata utilizzando {@code ObjectOutputStream}.
     *
     * @param gp il {@code GameProcessor} che rappresenta lo stato attuale del gioco da salvare.
     */
    public final void saveGame(GameProcessor gp) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(
                    new FileOutputStream("src/main/salvataggio.dat"));
            out.writeObject(gp); 
            out.close(); 
            System.err.println("Salvataggio effettuato..");
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage()); 
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Carica lo stato del gioco da un file. Viene deserializzato l'oggetto salvato e
     * ripristinato lo stato del gioco nell'istanza {@code GameProcessor} passata come parametro.
     *
     * @param gp il {@code GameProcessor} in cui caricare lo stato deserializzato.
     */
    public final void load(GameProcessor gp) {
        try {
            
            FileInputStream inFile = new FileInputStream("src/main/salvataggio.dat");
            ObjectInputStream inStream = new ObjectInputStream(inFile);
            
            GameProcessor loaded = (GameProcessor) inStream.readObject();

            gp.getAllobject().addAll(loaded.getAllobject());
            gp.getInventory().addAll(loaded.getInventory());
            loadPaths(gp, loaded); 
            gp.setCurrentRoom(loaded.getCurrentRoom());
            gp.getStatusButton().addAll(loaded.getStatusButton());

            inStream.close();
        } catch (FileNotFoundException e) {
            System.out.println("no file");
        } catch (ClassNotFoundException e) {
            System.out.println("no class"); 
        } catch (IOException e) {
            System.out.println(e.getMessage()); 
        }
    }

    /**
     * Carica i percorsi di gioco specifici dall'istanza deserializzata del {@code GameProcessor}.
     * Questo metodo può essere personalizzato per includere il ripristino di dati complessi come percorsi o stati specifici.
     *
     * @param gp l'istanza corrente del {@code GameProcessor}.
     * @param loaded l'istanza caricata del {@code GameProcessor} da cui vengono caricati i percorsi.
     */
    public final void loadPaths(GameProcessor gp, GameProcessor loaded) {
    }
}