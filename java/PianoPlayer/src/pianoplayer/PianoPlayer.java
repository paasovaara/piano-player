package pianoplayer;

import org.jfugue.midi.MidiParser;
import org.jfugue.pattern.Pattern;
import org.jfugue.player.ManagedPlayer;
import org.jfugue.player.Player;
import org.jfugue.player.SequencerManager;
import org.staccato.StaccatoParserListener;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
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

        try {
            //PianoPlayer player = new PianoPlayer();
            //player.play();


            Player player2 = new Player();
            //player2.getManagedPlayer();
            MyMidiParserListener listener = new MyMidiParserListener();
            Sequencer sequencer = SequencerManager.getInstance().getDefaultSequencer();

            int[] codes = new int[] {ShortMessage.NOTE_ON, ShortMessage.NOTE_OFF};
            sequencer.addControllerEventListener(listener, codes);
            sequencer.addMetaEventListener(listener);

            player2.play("C D E F G A B");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

	}

}
