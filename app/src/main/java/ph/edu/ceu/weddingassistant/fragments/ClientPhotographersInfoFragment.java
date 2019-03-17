package ph.edu.ceu.weddingassistant.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.threeten.bp.format.DateTimeFormatter;

import ph.edu.ceu.weddingassistant.R;

public class ClientPhotographersInfoFragment extends Fragment
        implements OnDateSelectedListener {
    View mView;
    TextView text_service_name,
            text_service_email,
            text_service_cost,
            text_service_contact,
            text_service_category,
            text_service_permit;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("EEE, d MMM yyyy");

    MaterialCalendarView widget;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_client_photographers_info, container, false);

        Bundle bundle = this.getArguments();
        String service_name = bundle.getString("photographer_service_name");
        String service_email = bundle.getString("photographer_service_email");
        String service_cost = bundle.getString("photographer_service_cost");
        String service_contact = bundle.getString("photographer_service_contact");
        String service_category = bundle.getString("photographer_service_category");
        String service_permit = bundle.getString("photographer_service_permit");

        text_service_name = mView.findViewById(R.id.info_catering_name);
        text_service_email = mView.findViewById(R.id.info_catering_email);
        text_service_cost = mView.findViewById(R.id.info_catering_cost);
        text_service_contact = mView.findViewById(R.id.info_catering_contact);
        text_service_category = mView.findViewById(R.id.info_catering_category);
        text_service_permit = mView.findViewById(R.id.info_catering_permit);

        text_service_name.setText(service_name);
        text_service_email.setText(service_email);
        text_service_cost.setText(service_cost);
        text_service_contact.setText(service_contact);
        text_service_category.setText(service_category);
        text_service_permit.setText(service_permit);

        widget = (MaterialCalendarView) mView.findViewById(R.id.calendarViewPhotographer);

        widget.setOnDateChangedListener(this);

        return mView;
    }

    @Override
    public void onDateSelected(
            @NonNull MaterialCalendarView widget,
            @NonNull CalendarDay date,
            boolean selected) {
        final String text = String.format("%s is available", FORMATTER.format(date.getDate()));
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }
}
