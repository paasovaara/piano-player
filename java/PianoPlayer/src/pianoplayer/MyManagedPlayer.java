package pianoplayer;

import org.jfugue.player.EndOfTrackListener;
import org.jfugue.player.ManagedPlayerListener;
import org.jfugue.player.SequencerManager;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;

/**
 * Created by mpaa on 14.8.2017.
 */
public class MyManagedPlayer implements EndOfTrackListener
{
    protected SequencerManager common;
    private boolean started;
    private boolean finished;
    private boolean paused;

    private CopyOnWriteArrayList<ManagedPlayerListener> playerListeners;

    public MyManagedPlayer() {
        playerListeners = new CopyOnWriteArrayList<ManagedPlayerListener>();
        try {
            common = SequencerManager.getInstance();
        } catch (MidiUnavailableException e) {
            Logger.getLogger("org.jfugue").severe(e.getLocalizedMessage());
        }
    }

    public void addManagedPlayerListener(ManagedPlayerListener listener) {
        playerListeners.add(listener);
    }

    public void removeManagedPlayerListener(ManagedPlayerListener listener) {
        playerListeners.add(listener);
    }

    public List<ManagedPlayerListener> getManagedPlayerListeners() {
        return playerListeners;
    }

    /**
     * This method opens the sequencer (if it is not already open - @see PlayerCommon),
     * sets the sequence, tells listeners that play is starting, and starts the sequence.
     * @param sequence
     */
    public void start(Sequence sequence) throws InvalidMidiDataException, MidiUnavailableException {
        common.openSequencer();
//		common.connectSequencerToSynthesizer(); // TODO - TEST connectSequencerToSynthesizer in ManagedPlayer // 2016-03-07 THIS IS CAUSING A PROBLEM WITH DOUBLE-HIT NOTES!!!
        common.addEndOfTrackListener(this);
        common.getSequencer().setSequence(sequence);
        fireOnStarted(sequence);
        this.started = true;
        this.paused = false;
        this.finished = false;
        common.getSequencer().start();
    }

    public Sequencer getSequencer() {
        return common.getSequencer();
    }

    /**
     * To resume play, @see resume()
     */
    public void pause() {
        fireOnPaused();
        this.paused = true;
        common.getSequencer().stop();
    }

    /**
     * To pause play, @see pause()
     */
    public void resume() {
        fireOnResumed();
        this.paused = false;
        common.getSequencer().start();
    }

    public void seek(long tick) {
        fireOnSeek(tick);
        common.getSequencer().setTickPosition(tick);
    }

    public void finish() {
        common.close();
        this.finished = true;
        fireOnFinished();
    }

    public void reset() {
        common.close();
        this.started = false;
        this.paused = false;
        this.finished = false;
        fireOnReset();
    }

    public long getTickLength() {
        return common.getSequencer().getTickLength();
    }

    public long getTickPosition() {
        return common.getSequencer().getTickPosition();
    }

    public boolean isStarted() {
        return this.started;
    }

    public boolean isFinished() {
        return this.finished;
    }

    public boolean isPaused() {
        return this.paused;
    }

    public boolean isPlaying() {
        return common.getSequencer().isRunning();
    }

    @Override
    public void onEndOfTrack() {
        finish();
    }

    protected void fireOnStarted(Sequence sequence) {
        List<ManagedPlayerListener> listeners = getManagedPlayerListeners();
        for (ManagedPlayerListener listener : listeners) {
            listener.onStarted(sequence);
        }
    }

    protected void fireOnFinished() {
        List<ManagedPlayerListener> listeners = getManagedPlayerListeners();
        for (ManagedPlayerListener listener : listeners) {
            listener.onFinished();
        }
    }

    protected void fireOnPaused() {
        List<ManagedPlayerListener> listeners = getManagedPlayerListeners();
        for (ManagedPlayerListener listener : listeners) {
            listener.onPaused();
        }
    }

    protected void fireOnResumed() {
        List<ManagedPlayerListener> listeners = getManagedPlayerListeners();
        for (ManagedPlayerListener listener : listeners) {
            listener.onResumed();
        }
    }

    protected void fireOnSeek(long tick) {
        List<ManagedPlayerListener> listeners = getManagedPlayerListeners();
        for (ManagedPlayerListener listener : listeners) {
            listener.onSeek(tick);
        }
    }

    protected void fireOnReset() {
        List<ManagedPlayerListener> listeners = getManagedPlayerListeners();
        for (ManagedPlayerListener listener : listeners) {
            listener.onReset();
        }
    }

}
