package com.centrahub.focus.sourav.swipeselectionview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.centrahub.focus.sourav.swipeselectionview.swipeSelection.SwipeSelectionView;

public class MainActivity extends AppCompatActivity {

    public String[] listOfFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initArrayList();
        setContentView(getSwipeScrollView());
    }

    private void initArrayList() {
        listOfFood = new String[15];
        for (int i = 0; i < 15; i++) {
            listOfFood[i ] = "Food Item - " + "<"+i+">";
            if (i==4)
                listOfFood[i ] = "GG UyyypppItem - " + "<"+i+">";

        }
    }


    public View getSwipeScrollView() {
        RelativeLayout linearLayout = new RelativeLayout(this);
        linearLayout.setGravity(Gravity.CENTER);
//        linearLayout.setOrientation(LinearLayout.VERTICAL);
//        linearLayout.setBackgroundColor(Color.BLACK);
        linearLayout.setBackground(getResources().getDrawable(R.drawable.ic_launcher_background));
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        ImageView img = new ImageView(this);
        img.setScaleType(ImageView.ScaleType.CENTER);
        img.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        img.setImageDrawable(getResources().getDrawable(R.drawable.bg_image));


        SwipeSelectionView clCalendarView = new SwipeSelectionView(this, listOfFood);
        clCalendarView.setSelectionList(listOfFood);
//        clCalendarView.setCurrentValue(17);
        RelativeLayout.LayoutParams simpleDetailsViewParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        simpleDetailsViewParam.addRule(RelativeLayout.CENTER_IN_PARENT);
        clCalendarView.setLayoutParams(simpleDetailsViewParam);
        clCalendarView.setOnCalenderPageChangeListener(new SwipeSelectionView.OnCalenderPageChangeListener() {
            @Override
            public void onChange(int itemPosition, String itemLabel) {
                Toast.makeText(MainActivity.this,itemPosition+" -"+itemLabel,Toast.LENGTH_SHORT).show();
            }
        });
        linearLayout.addView(clCalendarView);
        return linearLayout;
    }
}
