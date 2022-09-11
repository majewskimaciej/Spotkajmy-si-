import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Meeting {
    int meetingDuration;
    ArrayList<Calendar> calendars;

    public void setMeetingDuration (int meetingDuration) {
        this.meetingDuration = meetingDuration;
    }

    public Meeting () {
        calendars = new ArrayList<>();
    }

    public void addCalendars (Calendar calendar) {
        calendars.add(calendar);
    }

    public boolean checkIfCollides(ArrayList<Hours> allPlannedMeetings, LocalTime newStart) {
        for (Hours hour : allPlannedMeetings) {
            if (newStart.isBefore(hour.getEnd()) && newStart.isAfter(hour.getStart())) {
                return true;
            }
        }

        return false;
    }

    public Hours getNewStartAndEnd () {
        Hours tempHours;
        LocalTime newStart, newEnd;

        ArrayList<Calendar> temp = calendars;

        Collections.sort(temp, Comparator.comparing(Calendar::getWorkingHoursStart));
        Collections.reverse(temp);
        newStart = temp.get(0).getWorkingHoursStart();

        Collections.sort(temp, Comparator.comparing(Calendar::getWorkingHoursEnd));
        newEnd = temp.get(0).getWorkingHoursEnd();

        tempHours = new Hours(newStart, newEnd);

        return tempHours;
    }

    public ArrayList<Hours> getAllPlannedMeetings (LocalTime newStart) {
        ArrayList<Hours> allPlannedMeetings = new ArrayList<>();

        for (Calendar calendar : calendars) {
            for (int j = 0; j < calendar.getPlannedMeetings().size(); j++) {
                Hours hour = calendar.getPlannedMeeting(j);
                if (!hour.getStart().isBefore(newStart)) {
                    allPlannedMeetings.add(hour);
                } else {
                    newStart = hour.getEnd();
                }
            }
        }

        allPlannedMeetings.sort(Comparator.comparing(Hours::getStart));


        return allPlannedMeetings;
    }

    public ArrayList<Hours> newMeetings () {
        LocalTime newStart = getNewStartAndEnd().getStart();
        LocalTime newEnd = getNewStartAndEnd().getEnd();

        ArrayList<Hours> allPlannedMeetings = getAllPlannedMeetings(newStart);

        ArrayList<Hours> result = new ArrayList<>();

        int diff = (int) newStart.until(newEnd, ChronoUnit.MINUTES);

        while (diff >= 0) {
            if (!(checkIfCollides(allPlannedMeetings, newStart))) {
                int count = 0;

                while (!(checkIfCollides(allPlannedMeetings, newStart)) && diff >= 0) {
                    if (count == meetingDuration) {
                        result.add(new Hours(newStart.minusMinutes(meetingDuration), newStart));
                        count = 0;
                    }

                    newStart = newStart.plusMinutes(1);
                    count++;
                    diff--;
                }
            }
            newStart = newStart.plusMinutes(1);
            diff--;
        }

        return result;
    }
}
