import java.time.LocalTime;
import java.util.ArrayList;

public class Calendar {
    private Hours workingHours;
    private final ArrayList<Hours> plannedMeetings;

    public Calendar () {
        this.plannedMeetings = new ArrayList<>();
    }

    public void addPlannedMeetings(Hours hours) {
        plannedMeetings.add(hours);
    }

    public LocalTime getWorkingHoursStart () {
        return workingHours.getStart();
    }

    public LocalTime getWorkingHoursEnd () {
        return workingHours.getEnd();
    }
    public void setWorkingHours (Hours workingHours) {
        this.workingHours = workingHours;
    }

    public ArrayList<Hours> getPlannedMeetings () {
        return plannedMeetings;
    }

    public Hours getPlannedMeeting (int i) {
        return plannedMeetings.get(i);
    }
}
