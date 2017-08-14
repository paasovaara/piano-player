package pianoplayer;

import org.jfugue.devtools.DiagnosticParserListener;
import org.jfugue.midi.MidiParser;
import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;
import org.jfugue.temporal.TemporalPLP;
import org.staccato.StaccatoParser;
import org.staccato.StaccatoParserListener;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import java.io.File;
import java.io.IOException;

public class MyTemporalExample {
    //http://www.jfugue.org/examples.html

    private static final String MUSIC = "C D E F G A B";
    private static final long TEMPORAL_DELAY = 0;

    public static File selectFile() {
        return new File("data/GNR_November_Rain.mid");
    }

    public static Sequence selectSequence() throws InvalidMidiDataException, IOException {
        return MidiSystem.getSequence(selectFile());
    }

    public Pattern getStaccatoPattern() throws Exception {
        MidiParser parser = new MidiParser();

        StaccatoParserListener listener = new StaccatoParserListener();
        parser.addParserListener(listener);
        parser.parse(selectSequence()); // Change to the name of a MIDI file that you own the rights to
        Pattern staccatoPattern = listener.getPattern();
        System.out.println(staccatoPattern);

        return staccatoPattern;
    }

    public static void main(String[] args) {
        try {
            // Part 1. Parse the original music
            //StaccatoParser parser = new StaccatoParser();
            MidiParser parser = new MidiParser();

            TemporalPLP plp = new TemporalPLP();
            parser.addParserListener(plp);
            parser.parse(selectSequence());

            // Part 2. Send the events from Part 1, and play the original music with a delay
            DiagnosticParserListener dpl = new DiagnosticParserListener(); // Or your AnimationParserListener!
            plp.addParserListener(dpl);
            new Player().delayPlay(TEMPORAL_DELAY, selectSequence());
            plp.parse();

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}