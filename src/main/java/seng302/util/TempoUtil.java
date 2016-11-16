package seng302.util;

import seng302.workspace.Project;

/**
 * Tempo Utilities.
 *
 * @author adg62, jps100, wrs35, avh17
 */
public final class TempoUtil {

    /** Private Constructor. */
    private TempoUtil() {}

    /**
     * Returns the current tempo speed.
     *
     * @return Tempo speed.
     */
    public static Integer getTempo() {
        return Project.getInstance().getTempo();
    }

    /**
     * Sets the tempo speed and update the crotchet length.
     *
     * @param newTempo New tempo speed.
     */
    public static void setTempo(Integer newTempo) {
        Project.getInstance().setTempo(newTempo);
    }

    /**
     * Returns the current crotchet length.
     *
     * @return Crotchet length.
     */
    public static Integer getCrotchet() {
        return Project.getInstance().getCrotchet();
    }

    /**
     * Resets the tempo variables to their default values.
     */
    public static void resetTempo() {
        Project.getInstance().resetTempo();
    }

    /**
     * Used to get the first swing split value.
     *
     * @return The first swing split value.
     */
    public static Float getFirstSwingSplit() {
        return Project.getInstance().getFirstSwingSplit();
    }

    /**
     * Used to get the second swing split value.
     *
     * @return The second swing split value.
     */
    public static Float getSecondSwingSplit() {
        return Project.getInstance().getSecondSwingSplit();
    }
}
