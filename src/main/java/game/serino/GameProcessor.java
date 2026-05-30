package game.serino;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 * La classe GameProcessor gestisce lo stato del gioco, inclusi gli oggetti, l'inventario,
 * i bottoni di stato e la gestione delle stanze. 
 * Implementa l'interfaccia {@code Serializable} per consentire il salvataggio e il caricamento dello stato di gioco.
 * 
 * @author lucas
 */
public class GameProcessor implements Serializable {
    
    /** L'istanza corrente della partita, marcata come transient per evitare la serializzazione */
    private transient Partita p;
    
    /** Indice corrente per scorrere l'inventario */
    private int index = 0;
    
    /** Lista di tutti gli oggetti del gioco */
    private List<AdvObject> allobject = new ArrayList<>();
    
    /** Lista degli oggetti nell'inventario del giocatore */
    private List<AdvObject> inventory = new ArrayList<>();
    
    /** Lista dei bottoni di stato nel gioco */
    private List<JButton> statusButton = new ArrayList<>();
    
    /** Indica la stanza corrente in cui si trova il giocatore */
    private int currentRoom;
    
    /** Istanza di salvataggio, marcata come transient per evitare la serializzazione */
    private transient Save s = new Save();

    /**
     * Costruttore della classe GameProcessor. 
     * Inizializza una nuova partita o carica uno stato di gioco esistente in base al valore del parametro {@code b}.
     * 
     * @param p l'istanza di {@code Partita}.
     * @param b se true, inizializza una nuova partita; se false, carica una partita salvata.
     */
    GameProcessor(Partita p, boolean b) {
        this.p = p;
        if (b) {
            currentRoom = 1;
            DataBaseInit db = new DataBaseInit();
            db.initGame(this);
        } else {
            s.load(this);
            refreshInv();
            refreshStatus();
        }
    }

    /**
     * Costruttore senza parametri che inizializza una nuova partita.
     */
    GameProcessor() {
        DataBaseInit db = new DataBaseInit();
        db.initGame(this);
    }

    /**
     * Restituisce la stanza corrente in cui si trova il giocatore.
     * 
     * @return l'indice della stanza corrente.
     */
    public int getCurrentRoom() {
        return currentRoom;
    }

    /**
     * Imposta la stanza corrente.
     * 
     * @param currentRoom l'indice della stanza da impostare come corrente.
     */
    public void setCurrentRoom(int currentRoom) {
        this.currentRoom = currentRoom;
    }

    /**
     * Restituisce la lista di tutti gli oggetti presenti nel gioco.
     * 
     * @return la lista di oggetti.
     */
    public List<AdvObject> getAllobject() {
        return allobject;
    }

    /**
     * Restituisce la lista degli oggetti nell'inventario del giocatore.
     * 
     * @return la lista degli oggetti nell'inventario.
     */
    public List<AdvObject> getInventory() {
        return inventory;
    }

    /**
     * Restituisce la lista dei bottoni di stato del gioco.
     * 
     * @return la lista di bottoni di stato.
     */
    public List<JButton> getStatusButton() {
        return statusButton;
    }

    /**
     * Permette di selezionare un oggetto nell'inventario e aggiornare l'icona del bottone passato come parametro.
     * Scorre attraverso l'inventario in avanti o indietro in base al valore di {@code scroll}.
     * 
     * @param scroll se true, scorre avanti nell'inventario; se false, scorre indietro.
     * @param button il bottone da aggiornare con l'icona dell'oggetto selezionato.
     */
    public void choseObject(Boolean scroll, JButton button) {
        String image;
        String name;
        if (scroll) {
            index = index + 1;
            if (inventory.size() < index + 1)
                index = 0;
            image = inventory.get(index).getPath();
            name = inventory.get(index).getName();
        } else {
            index = index - 1;
            if (index == -1)
                index = inventory.size() - 1;
            image = inventory.get(index).getPath();
            name = inventory.get(index).getName();
        }
        changeIcon(image, button, name);
    }

    /**
     * Cambia l'icona di un bottone specificato con l'immagine e il nome dell'oggetto.
     * 
     * @param image il percorso dell'immagine da impostare come icona.
     * @param button il bottone da aggiornare.
     * @param name il nome dell'oggetto per il suggerimento del tool.
     */
    public void changeIcon(String image, JButton button, String name) {
        button.setIcon(new ImageIcon("src/main/resources/" + image + ".png"));
        button.setToolTipText(name);
    }

    /**
     * Stampa nella console tutte le informazioni sugli oggetti presenti nel gioco.
     */
    public void stampa() {
        for (AdvObject o : allobject) {
            System.out.println("id " + o.getId() + " nome " + o.getName() + " path " + o.getPath());
        }
    }

    /**
     * Salva lo stato attuale del gioco, inclusi i bottoni di stato, utilizzando la classe {@code Save}.
     */
    public void salva() {
        setStatusButton();
        s.saveGame(this);
        System.err.println("Salvataggio eseguito!");
    }

    /**
     * Aggiorna l'inventario del giocatore, impostando le icone degli oggetti corrispondenti nei bottoni dello zaino.
     */
    public void refreshInv() {
        String path;
        JLabel b;
        for (int i = 0; i < p.getListZaino().size(); i++) {
            b = p.getListZaino().get(i);
            if (inventory.size() > i) {
                path = inventory.get(i).getPath();
                b.setIcon(new ImageIcon("src/main/resources/" + path + ".png"));
            } else {
                b.setIcon(null);
            }
        }
    }

    /**
     * Imposta i bottoni di stato attuali nel gioco, copiando quelli presenti nell'oggetto {@code Partita}.
     */
    public void setStatusButton() {
        statusButton.removeAll(statusButton);
        statusButton.addAll(p.getListButton());
    }

    /**
     * Aggiorna i bottoni di stato in base alla visibilità corrente dei bottoni di stato salvati.
     */
    public void refreshStatus() {
        for (int i = 0; i < p.getListButton().size(); i++) {
            if (!statusButton.get(i).isVisible()) {
                p.getListButton().get(i).setVisible(false);
            }
        }
    }

    /**
     * Verifica se esiste un file di salvataggio per il gioco.
     * 
     * @return true se il file di salvataggio esiste, false altrimenti.
     */
    public final boolean exist() {
        File f = new File("src/main/salvataggio.dat");
        return f.exists();
    }
}
