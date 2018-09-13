
package com.sun.demo;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Custom implementation of the MarkerView.
 * 
 * @author Philipp Jahoda
 */
public class MyMarkerView extends MarkerView {

    private TextView tvContent;
    private Date mDate;

    public MyMarkerView(Context context, int layoutResource,Date date) {
        super(context, layoutResource);

        tvContent = findViewById(R.id.tvContent);
        mDate =date;
    }

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {

//        if (e instanceof CandleEntry) {
//
//            CandleEntry ce = (CandleEntry) e;
//
//            tvContent.setText("" + Utils.formatNumber(ce.getHigh(), 1, true));
//        } else {
        Log.e("cheng","ex:"+e.getX());

            tvContent.setText(new SimpleDateFormat("HH:mm:ss").format(new Date((long)e.getX()*1000+mDate.getTime()))  +" "+ e.getY());
//        }

        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
}
