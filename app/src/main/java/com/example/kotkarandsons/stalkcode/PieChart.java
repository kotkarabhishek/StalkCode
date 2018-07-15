package com.example.kotkarandsons.stalkcode;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PieChart extends Fragment{

    public  PieChart(){}
    com.github.mikephil.charting.charts.PieChart pieChart;

    int codeCount=0;
    long dateCount=0;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference assistantDatabase;
    static float implementation=0f, strings=0f, sorting=0f, searching=0f, graphTheory=0f, greedy=0f,
            dynamic=0f, bitManip=0f, recursion=0f, gameTheory=0f, np=0f;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.piechart,null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        pieChart = (com.github.mikephil.charting.charts.PieChart)getView().findViewById(R.id.piechart);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(true);
        pieChart.setExtraOffsets(5, 10, 5, 5);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic);

        assistantDatabase = FirebaseDatabase.getInstance().getReference(user.getUid()+ "/Codes");

        assistantDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot walker : dataSnapshot.getChildren()) {
                        CodeInfo code = walker.getValue(CodeInfo.class);
                        if (code.getCodeType().equals("Implementation")) {
                            implementation++;
                        } else if (code.getCodeType().equals("String")) {
                            strings++;
                        } else if (code.getCodeType().equals("Sorting")) {
                            sorting++;
                        } else if (code.getCodeType().equals("Searching")) {
                            searching++;
                        } else if (code.getCodeType().equals("Graph Theory")) {
                            graphTheory++;
                        } else if (code.getCodeType().equals("Greedy")) {
                            greedy++;
                        } else if (code.getCodeType().equals("Dynamic Programming")) {
                            dynamic++;
                        } else if (code.getCodeType().equals("Bit Manipulation")) {
                            bitManip++;
                        } else if (code.getCodeType().equals("Recursion")) {
                            recursion++;
                        } else if (code.getCodeType().equals("Game Theory")) {
                            gameTheory++;
                        } else if (code.getCodeType().equals("NP Complete")) {
                            np++;
                        }


                }



                ArrayList<PieEntry> values = new ArrayList<>();

                values.add(new PieEntry(implementation, "Implementation"));
                values.add(new PieEntry(strings, "Strings"));
                values.add(new PieEntry(sorting, "Sorting"));
                values.add(new PieEntry(searching, "Searching"));
                values.add(new PieEntry(graphTheory, "Graph Theory"));
                values.add(new PieEntry(greedy, "Greedy"));
                values.add(new PieEntry(dynamic, "Dynamic Programming"));
                values.add(new PieEntry(bitManip, "Bit Manipulation"));
                values.add(new PieEntry(recursion, "Recursion"));
                values.add(new PieEntry(gameTheory, "Game Theory"));
                values.add(new PieEntry(np, "NP Complete"));

                PieDataSet dataSet = new PieDataSet(values, "Domains");
                dataSet.setSliceSpace(3f);
                dataSet.setSelectionShift(5f);
                dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

                PieData pieData = new PieData(dataSet);
                pieData.setValueTextSize(10f);
                pieData.setValueTextColor(Color.YELLOW);

                pieChart.getDescription().setEnabled(false);
                pieChart.setData(pieData);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }
}
