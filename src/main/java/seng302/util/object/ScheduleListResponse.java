package seng302.util.object;

import java.util.ArrayList;

/**
 * A schedule list response object.
 * Acts as a wrapper for a list of schedules that can be sent to the client.
 *
 * @author adg62
 */
public class ScheduleListResponse {

    private ArrayList<Object> schedules;

    public ScheduleListResponse(ArrayList schedules) {
        this.schedules = schedules;
    }

    public ArrayList<Object> getSchedules() {
        return schedules;
    }
}
