package game.serino;

import java.io.Serializable;

/**
 * La classe {@code AdvObject} rappresenta un oggetto di avventura nel gioco.
 * Ogni oggetto ha un identificativo univoco, un nome e un percorso associato a un'icona
 * o una rappresentazione visiva.
 * 
 * Implementa l'interfaccia {@code Serializable} per permettere la serializzazione
 * e il salvataggio dello stato dell'oggetto.
 * 
 * @author lucas
 */
public class AdvObject implements Serializable {

    private int id;
    private String name;
    private String path;

    /**
     * Costruttore della classe {@code AdvObject}.
     * 
     * @param id   l'identificativo univoco dell'oggetto
     * @param name il nome dell'oggetto
     * @param path il percorso dell'icona o della risorsa associata all'oggetto
     */
    public AdvObject(int id, String name, String path) {
        this.id = id;
        this.name = name;
        this.path = path;
    }

    /**
     * Restituisce l'identificativo univoco dell'oggetto.
     * 
     * @return l'id dell'oggetto
     */
    public int getId() {
        return id;
    }

    /**
     * Imposta l'identificativo univoco dell'oggetto.
     * 
     * @param id l'id da assegnare all'oggetto
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Restituisce il nome dell'oggetto.
     * 
     * @return il nome dell'oggetto
     */
    public String getName() {
        return name;
    }

    /**
     * Imposta il nome dell'oggetto.
     * 
     * @param name il nome da assegnare all'oggetto
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Restituisce il percorso della risorsa associata all'oggetto (ad esempio, l'icona).
     * 
     * @return il percorso della risorsa
     */
    public String getPath() {
        return path;
    }

    /**
     * Imposta il percorso della risorsa associata all'oggetto.
     * 
     * @param path il percorso da assegnare all'oggetto
     */
    public void setPath(String path) {
        this.path = path;
    }
}
