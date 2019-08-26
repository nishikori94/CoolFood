package com.example.coolfood;

import android.content.Context;
import android.os.Bundle;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.innovattic.rangeseekbar.RangeSeekBar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class FilterBottomSheetDialog extends BottomSheetDialogFragment {

    private RangeSeekBar timeRSB;
    private RangeSeekBar quantityRSB;
    private TextView timeTV;
    private TextView quantityTV;
    private Button filterBtn;
    private String minTime;
    private String maxTime;

    private BottomSheetListener bottomSheetListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.filter_bottom_sheet_layout, container, false);

        timeTV = view.findViewById(R.id.timeTV);
        timeRSB = view.findViewById(R.id.timeSeekBar);
        quantityRSB = view.findViewById(R.id.quantitySeekBar);
        quantityTV = view.findViewById(R.id.quantityTV);
        filterBtn = view.findViewById(R.id.filterBtn);

        timeTV.setText("00:00 - 24:00");

        timeRSB.setSeekBarChangeListener(new RangeSeekBar.SeekBarChangeListener() {
            @Override
            public void onStartedSeeking() {

            }

            @Override
            public void onStoppedSeeking() {

            }

            @Override
            public void onValueChanged(int i, int i1) {
                int minHour = i / 4;
                int minMin = i % 4 * 15;
                int maxHour = i1 / 4;
                int maxMin = i1 % 4 * 15;
                minTime = Integer.toString(minHour) + Integer.toString(minMin);
                maxTime = Integer.toString(maxHour) + Integer.toString(maxMin);
                String minHourS = Integer.toString(minHour);
                String maxHourS = Integer.toString(maxHour);
                String minMinS = Integer.toString(minMin);
                String maxMinS = Integer.toString(maxMin);
                if (minHour >= 0 && minHour < 10)
                    minHourS = "0" + Integer.toString(minHour);
                if (maxHour >= 0 && maxHour < 10)
                    maxHourS = "0" + Integer.toString(maxHour);
                if (maxMin == 0)
                    maxMinS = "0" + Integer.toString(maxMin);
                if (minMin == 0)
                    minMinS = "0" + Integer.toString(minMin);

                timeTV.setText(minHourS + ":" + minMinS + " - " + maxHourS + ":" + maxMinS);
            }
        });

        quantityRSB.setSeekBarChangeListener(new RangeSeekBar.SeekBarChangeListener() {
            @Override
            public void onStartedSeeking() {

            }

            @Override
            public void onStoppedSeeking() {

            }

            @Override
            public void onValueChanged(int i, int i1) {
                quantityTV.setText(i + " - " + i1);
            }
        });

        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetListener.onButtonClicked(minTime,maxTime, Integer.toString(quantityRSB.getMinThumbValue()), Integer.toString(quantityRSB.getMaxThumbValue()));
                dismiss();
            }
        });

        return view;
    }

    public interface BottomSheetListener {
        void onButtonClicked(String minTime, String maxTime, String minQ, String maxQ);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            bottomSheetListener = (BottomSheetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement BottomSheetListener");
        }
    }
}
