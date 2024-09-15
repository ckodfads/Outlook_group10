package vn.edu.usth.weather;

import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ForecastFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = new View(getContext());
        v.setBackgroundColor(0x200000FF);

        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setBackgroundColor(Color.BLUE);

        TextView dayTextView = new TextView(getActivity());
        dayTextView.setText("Thursday");
        dayTextView.setTextSize(20);
        dayTextView.setTextColor(Color.BLACK);


        ImageView weatherIcon = new ImageView(getActivity());
        weatherIcon.setImageResource(R.drawable.cloud);

        linearLayout.addView(dayTextView);
        linearLayout.addView(weatherIcon);

        return inflater.inflate(R.layout.fragment_forecast, container, false);
    }
}