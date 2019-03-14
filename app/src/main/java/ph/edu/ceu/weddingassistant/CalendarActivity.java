package ph.edu.ceu.weddingassistant;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.Calendar;

public class CalendarActivity extends AppCompatActivity{

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        MaterialCalendarView calendar = (MaterialCalendarView) findViewById(R.id.calendarView);

        calendar.setCurrentDate(CalendarDay.today());
        Calendar currentCal = Calendar.getInstance();
        currentCal.clear(Calendar.HOUR_OF_DAY);
        currentCal.clear(Calendar.MINUTE);
        currentCal.clear(Calendar.SECOND);
        currentCal.clear(Calendar.MILLISECOND);
        calendar.setSelectedDate(CalendarDay.today());

        Calendar startCal = (Calendar) currentCal.clone();
        startCal.set(Calendar.DAY_OF_WEEK, startCal.getFirstDayOfWeek());

        Calendar endCal = (Calendar) startCal.clone();
        endCal.add(Calendar.WEEK_OF_YEAR, 1);
        calendar.state().edit().setMinimumDate(
                CalendarDay.from(
                        startCal.get(Calendar.YEAR),
                        startCal.get(Calendar.MONTH),
                        startCal.get(Calendar.DAY_OF_MONTH)
                )
        ).commit();

        String val =calendar.getCurrentDate().toString();
        TextView text = (TextView) findViewById(R.id.currentDate);
        text.setText(val);
    }
}
