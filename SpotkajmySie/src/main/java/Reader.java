import org.json.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalTime;

public class Reader {

    public Calendar reader (String name) throws IOException {
        Path f
                = Path.of("resources/" + name + ".json");

        String str = Files.readString(f);

        JSONObject obj = new JSONObject(str);

        Calendar calendar = new Calendar();

        Hours workingHours = new Hours(LocalTime.parse(obj.getJSONObject("working_hours").getString("start")), LocalTime.parse(obj.getJSONObject("working_hours").getString("end")));
        calendar.setWorkingHours(workingHours);

        JSONArray jsonArray = obj.getJSONArray("planned_meeting");

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject plan = jsonArray.getJSONObject(i);
            calendar.addPlannedMeetings(new Hours(LocalTime.parse(plan.getString("start")), LocalTime.parse(plan.getString("end"))));
        }

        return calendar;
    }
}
