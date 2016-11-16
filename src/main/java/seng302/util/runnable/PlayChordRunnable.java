package seng302.util.runnable;

import java.util.List;
import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.Synthesizer;


/**
 * Runnable play chord object. These get stacked into the executor for execution.
 *
 * @author jps100
 */
public class PlayChordRunnable implements Runnable {
    private Synthesizer synthesizer;
    private List<Integer> midis;
    private Integer velocity;
    private Integer duration;

    /**
     * Constructor.
     *
     * @param synthesizer The synthesizer we're playing on.
     * @param midis The midi notes in the chord we're playing.
     * @param velocity The speed we're playing at.
     * @param duration The duration we're playing for.
     */
    public PlayChordRunnable(Synthesizer synthesizer, List<Integer> midis, Integer velocity,
                             Integer duration) {
        this.synthesizer = synthesizer;
        this.midis = midis;
        this.velocity = velocity;
        this.duration = duration;
    }

    /**
     * The runnable code, called when the executor reaches this object.
     */
    @Override
    public void run() {
        try {
            Instrument[] instruments = synthesizer.getDefaultSoundbank().getInstruments();
            MidiChannel[] midiChannels = synthesizer.getChannels();

            synthesizer.loadInstrument(instruments[0]);

            for (Integer midi : this.midis) {
                midiChannels[this.midis.indexOf(midi)].noteOn(midi, this.velocity);
            }

            try {
                Thread.sleep(this.duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (Integer midi : this.midis) {
                midiChannels[this.midis.indexOf(midi)].noteOff(midi);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
