package seng302.util.runnable;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.Synthesizer;

/**
 * Runnable play object. These get stacked into the executor for execution.
 *
 * @author adg62
 */
public class PlayMidiRunnable implements Runnable {
    private Synthesizer synthesizer;
    private Integer midi;
    private Integer velocity;
    private Integer duration;

    /**
     * Constructor.
     *
     * @param synthesizer The synthesizer we're playing on.
     * @param midi The midi note we're playing.
     * @param velocity The speed we're playing at.
     * @param duration The duration we're playing for.
     */
    public PlayMidiRunnable(Synthesizer synthesizer, Integer midi, Integer velocity,
                            Integer duration) {
        this.synthesizer = synthesizer;
        this.midi = midi;
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

            midiChannels[0].noteOn(this.midi, this.velocity);

            try {
                Thread.sleep(this.duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            midiChannels[0].noteOff(this.midi);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
