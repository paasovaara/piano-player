package pianoplayer;

import org.jfugue.midi.MidiParserListener;

import javax.sound.midi.ControllerEventListener;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.ShortMessage;

public class MyMidiParserListener extends MidiParserListener implements MetaEventListener, ControllerEventListener {

    public void meta(MetaMessage meta) {
        System.out.println("MeTA: " + meta);
    }

    public void controlChange(ShortMessage event) {
        System.out.println("CONTROL: " + event);

    }
}
