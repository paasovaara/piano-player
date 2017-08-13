package pianoplayer;

import org.jfugue.midi.MidiParser;
import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;
import org.staccato.StaccatoParserListener;

import javax.sound.midi.MidiSystem;
import java.io.File;

public class PianoPlayer {

    public File selectFile() {
        return new File("data/GNR_November_Rain.mid");
    }

    public void play() throws Exception {
        MidiParser parser = new MidiParser();

        StaccatoParserListener listener = new StaccatoParserListener();
        parser.addParserListener(listener);
        parser.parse(MidiSystem.getSequence(selectFile())); // Change to the name of a MIDI file that you own the rights to
        Pattern staccatoPattern = listener.getPattern();
        System.out.println(staccatoPattern);
    }



	/**
	 * @param args
	 */
	public static void main(String[] args) {
        // TODO Auto-generated method stub
        System.out.println("Miko toimiiko tämä?");

        PianoPlayer player = new PianoPlayer();
        try {
            player.play();

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        Player player2 = new Player();
        player2.play("C D E F G A B");
	}

}
