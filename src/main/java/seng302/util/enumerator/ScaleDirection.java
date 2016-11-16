package seng302.util.enumerator;

/**
 * Enum for direction the scale is going
 * UP if the scale is ascending i.e. C4 to D4
 * DOWN if the scale is descending i.e. D4 to C4
 * BOTH if the scale is ascending then descending i.e. C4 to D4 to C4
 */
public enum ScaleDirection {
    UP("up", "Ascending"),
    DOWN("down", "Descending"),
    BOTH("both", "Ascending then Descending");

    private String text;
    private String properName;

    ScaleDirection(String text, String properName) {
        this.text = text;
        this.properName = properName;
    }

    public String toString() {
        return this.text;
    }

    public String getProperName() {
        return properName;
    }

    /**
     * Gets a valid scale direction.
     * @param direction The given scale direction.
     * @return The valid scale direction or null.
     */
    public static ScaleDirection fromString(String direction) {
        if (direction != null) {
            for (ScaleDirection scaleDirection : ScaleDirection.values()) {
                if (direction.equalsIgnoreCase(scaleDirection.text)) {
                    return scaleDirection;
                }
            }
        }
        return null;
    }
}