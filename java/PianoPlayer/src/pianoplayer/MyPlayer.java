package pianoplayer;

import org.jfugue.midi.MidiParserListener;
import org.jfugue.pattern.Pattern;
import org.jfugue.pattern.PatternProducer;
import org.jfugue.player.ManagedPlayer;
import org.jfugue.player.Player;
import org.staccato.StaccatoParser;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;

/**
 * Created by mpaa on 14.8.2017.
 */
public class MyPlayer {
    private StaccatoParser staccatoParser;
    private MidiParserListener midiParserListener;
    protected MyManagedPlayer managedPlayer;

    public MyPlayer() {
        managedPlayer = new MyManagedPlayer();
        staccatoParser = new StaccatoParser();
        midiParserListener = new MidiParserListener();
        staccatoParser.addParserListener(midiParserListener);
    }

    public Sequence getSequence(PatternProducer... patternProducers) {
        return getSequence(new Pattern(patternProducers));
    }

    public Sequence getSequence(PatternProducer patternProducer) {
        return getSequence(patternProducer.getPattern().toString());
    }

    public Sequence getSequence(String... strings) {
        return getSequence(new Pattern(strings));
    }

    public Sequence getSequence(String string) {
        staccatoParser.parse(string);
        return midiParserListener.getSequence();
    }

    public void play(PatternProducer... patternProducers) {
        play(new Pattern(patternProducers));
    }

    public void play(PatternProducer patternProducer) {
        play(patternProducer.getPattern().toString());
    }

    public void play(String... strings) {
        play(new Pattern(strings));
    }

    public void play(String string) {
        play(getSequence(string));
    }

    /**
     * This method plays a sequence by starting the sequence and waiting for the sequence
     * to finish before continuing. It also converts InvalidMidiDataException and
     * MidiUnavailableException to RuntimeExceptions for easier end-user programming.
     * If you want to create an application where you catch those exceptions, you
     * may want to use ManagedPlayer directly.
     *
     * @param sequence
     */
    public void play(Sequence sequence) {
        try {
            managedPlayer.start(sequence);
        } catch (InvalidMidiDataException e) {
            throw new RuntimeException(e);
        } catch (MidiUnavailableException e) {
            throw new RuntimeException(e);
        }

        // Wait for the sequence to finish playing
        while (!managedPlayer.isFinished()) {
            try {
                Thread.sleep(20); // don't hog all of the CPU
            } catch (InterruptedException e) {
                // Nothing to do here
            }
        }
    }

    public void delayPlay(final long millisToDelay, final PatternProducer... patternProducers) {
        delayPlay(millisToDelay, new Pattern(patternProducers));
    }

    public void delayPlay(final long millisToDelay, final PatternProducer patternProducer) {
        delayPlay(millisToDelay, patternProducer.getPattern().toString());
    }

    public void delayPlay(final long millisToDelay, final String... strings) {
        delayPlay(millisToDelay, new Pattern(strings));
    }

    public void delayPlay(final long millisToDelay, final String string) {
        delayPlay(millisToDelay, getSequence(string));
    }

    public void delayPlay(final long millisToDelay, final Sequence sequence) {
        Thread thread = new Thread() {
            public void run() {
                try {
                    Thread.sleep(millisToDelay);
                } catch (InterruptedException e) {
                    // Get yourself an egg and beat it!
                }
                MyPlayer.this.play(sequence);
            }
        };
        thread.start();
    }

    /**
     * Returns the ManagedPlayer behind this Player. You can start, pause, stop, resume, and seek a ManagedPlayer.
     * @see ManagedPlayer
     */
    public MyManagedPlayer getManagedPlayer() {
        return this.managedPlayer;
    }

    /**
     * Returns the StaccatoParser used by this Player. The only thing you might want to do with this is set whether the parser
     * throws an exception if an unknown token is found.
     * @see StaccatoParser
     */
    public StaccatoParser getStaccatoParser() {
        return this.staccatoParser;
    }

    /**
     * Returns the MidiParserListener used by this Player.
     * @see MidiParserListener
     */
    public MidiParserListener getMidiParserListener() {
        return this.midiParserListener;
    }
}
