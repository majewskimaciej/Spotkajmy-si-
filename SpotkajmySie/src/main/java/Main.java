import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {
        Reader reader = new Reader();
        Meeting meeting = new Meeting();

        meeting.addCalendars(reader.reader("calendar1"));
        meeting.addCalendars(reader.reader("calendar2"));
        meeting.setMeetingDuration(30);

        ArrayList<Hours> result = meeting.newMeetings();


        for (Hours hours : result) {
            System.out.print(hours.getStart() + " do ");
            System.out.print(hours.getEnd() + "\n");
        }
    }
}
