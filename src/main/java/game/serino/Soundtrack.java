package game.serino;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;

/**
 * La classe Soundtrack gestisce la riproduzione di un file audio e implementa l'interfaccia {@code Runnable}
 * per eseguire il processo di riproduzione su un thread separato.
 * La classe consente di regolare il volume e attivare/disattivare l'audio (mute).
 */
public class Soundtrack implements Runnable {

    /** Indica se l'audio è in modalità mute. */
    private boolean mute = false;

    /**
     * Metodo eseguito quando il thread viene avviato. Carica e riproduce un file audio 
     * e permette di gestire il volume e lo stato mute durante l'esecuzione.
     */
    @Override
    public final void run() {
        Clip audioClip = null;
        AudioInputStream audioStream = null;
        FloatControl volumeControl;

        try {
            File audioFile = new File("src/main/resources/Sound/Theme.wav");
            
            if (!audioFile.exists()) {
                System.out.println("Il file audio non esiste: " + audioFile.getAbsolutePath());
            }

            audioStream = AudioSystem.getAudioInputStream(audioFile);

            AudioFormat format = audioStream.getFormat();

            DataLine.Info info = new DataLine.Info(Clip.class, format);

            audioClip = (Clip) AudioSystem.getLine(info);
            
            audioClip.open(audioStream);
        } catch (Exception e) {
            Logger.getLogger(Soundtrack.class.getName()).log(Level.SEVERE, null, e);
        }

        volumeControl = (FloatControl) audioClip.getControl(FloatControl.Type.MASTER_GAIN);
        volumeControl.setValue(-8); 

        while (true) {
            try {
                audioClip.start();  
                Thread.sleep(100);  

                while (audioClip.isRunning()) {
                    Thread.sleep(5); 

                    
                    if (mute) {
                        volumeControl.setValue(-50); 
                    } else {
                        volumeControl.setValue(-8);  
                    }
                }

                if (!audioClip.isRunning()) {
                    audioClip.setMicrosecondPosition(0); 
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage()); 
            }
        }
    }

    /**
     * Alterna lo stato mute dell'audio. Se l'audio è attivo, lo disattiva e viceversa.
     */
    public final void setMute() {
        this.mute = !mute; 
        System.out.println("MUTO " + mute); 
    }
}
