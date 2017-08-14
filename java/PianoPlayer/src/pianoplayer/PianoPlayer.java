package pianoplayer;

import org.jfugue.midi.MidiParser;
import org.jfugue.pattern.Pattern;
import org.jfugue.player.ManagedPlayer;
import org.jfugue.player.Player;
import org.jfugue.player.SequencerManager;
import org.staccato.StaccatoParserListener;

import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;

public class PianoPlayer {

    public File selectFile() {
        return new File("data/GNR_November_Rain.mid");
    }

    public Sequence selectSequence() throws InvalidMidiDataException, IOException {
        return MidiSystem.getSequence(selectFile());
    }

    public void play() throws Exception {
        MidiParser parser = new MidiParser();

        StaccatoParserListener listener = new StaccatoParserListener();
        parser.addParserListener(listener);
        parser.parse(selectSequence()); // Change to the name of a MIDI file that you own the rights to
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
            PianoPlayer player = new PianoPlayer();
            //player.play();


            MyPlayer player2 = new MyPlayer();
            //player2.getManagedPlayer();
            MyMidiParserListener listener = new MyMidiParserListener();
            Sequencer sequencer = player2.getManagedPlayer().getSequencer();//SequencerManager.getInstance().getDefaultSequencer();

            int[] codes = new int[] {ShortMessage.NOTE_ON, ShortMessage.NOTE_OFF};
            sequencer.addControllerEventListener(listener, codes);
            sequencer.addMetaEventListener(listener);

            Sequence seq = player.selectSequence();
            Thread t = new Thread() {
                public void run() {
                    player2.play(seq);
                    //player2.play("C D E F G A B");

                }
            };
            t.start();
            t.join(1000);
            System.out.println("Is running: " + sequencer.isRunning());
            System.out.println("seq: " + sequencer.getSequence());

        }
        catch (Exception e) {
            e.printStackTrace();
        }

	}

}
