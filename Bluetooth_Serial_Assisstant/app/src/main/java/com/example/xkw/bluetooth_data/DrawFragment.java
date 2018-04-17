package com.example.xkw.bluetooth_data;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class DrawFragment extends Fragment
    implements OnChartValueSelectedListener,OnChartGestureListener{

    //UI
    private LineChart mChart;
    private MainActivity mContext;
    private TextView tv_data_send;
    private EditText edit_send;
    private Button btn_send;

    //variable
    private String data_send = "  发送区域:\n";
    private boolean mReceive = true;

    //constant
    private static final String TAG = "DrawFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_draw, container, false);

        UI_Init(view);

        Draw_Init(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MainActivity.Rec_Data);
        mContext.registerReceiver(mRecData,intentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        mContext.unregisterReceiver(mRecData);
    }

    private final BroadcastReceiver mRecData = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (MainActivity.Rec_Data.equals(action)){
                if (mReceive){
                    try {
                        float data_new = Float.parseFloat(mContext.GetData());
                        addEntry(data_new);
                    }catch (Exception e){
                        //接收到的不是数字
                        Log.d(TAG, "onReceive: not a number");
                        Toast.makeText(mContext,"不是数字类型",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    };

    private void UI_Init(View view){
        mContext = (MainActivity)getActivity();

        edit_send = (EditText)view.findViewById(R.id.et_send_draw);

        tv_data_send = (TextView)view.findViewById(R.id.tv_data_send_draw);
        tv_data_send.setMovementMethod(ScrollingMovementMethod.getInstance());
        tv_data_send.setText(data_send);

        btn_send = (Button)view.findViewById(R.id.btn_send_draw);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mContext.GetConnectionState()){
                    String str = edit_send.getText().toString();
                    data_send += "  " + str + '\n';
                    tv_data_send.setText(data_send);
                    mContext.SendData(str);
                }else {
                    Toast.makeText(mContext,"蓝牙未连接...",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void Draw_Init(View view){
        mChart = (LineChart)view.findViewById(R.id.chart1_draw);
        mChart.setOnChartGestureListener(this);
        mChart.setOnChartValueSelectedListener(this);
        mChart.setDrawGridBackground(false);
        // no description text
        mChart.getDescription().setEnabled(false);

        // enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(true);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        //去除右边的y轴
        YAxis yAxisRight = mChart.getAxisRight();
        yAxisRight.setEnabled(false);

        // add an empty data object
        mChart.setData(new LineData());

        mChart.invalidate();
    }

    public void addEntry(float yValue){
        LineData line_data = mChart.getData();

        ILineDataSet set = line_data.getDataSetByIndex(0);

        if (set == null) {
            set = createSet();
            line_data.addDataSet(set);
        }

        line_data.addEntry(new Entry(set.getEntryCount(),yValue), 0);
        line_data.notifyDataChanged();

        // let the chart know it's data has changed
        mChart.notifyDataSetChanged();
        mChart.invalidate();//重绘
    }

    private LineDataSet createSet() {

        LineDataSet set = new LineDataSet(null, "DataSet 1");
        set.setLineWidth(2.5f);
        set.setCircleRadius(4.5f);
        set.setColor(Color.rgb(240, 99, 99));
        set.setCircleColor(Color.rgb(240, 99, 99));
        set.setHighLightColor(Color.rgb(190, 190, 190));
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setValueTextSize(10f);

        return set;
    }

    public void ClearRecField(){
        mChart.clearValues();
    }

    public void ClearSendField(){
        data_send = "  发送区域:\n";
        tv_data_send.setText(data_send);
    }

    public void SetRecState(boolean state){
        mReceive = state;
    }

    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartLongPressed(MotionEvent me) {

    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {

    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {

    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {

    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Toast.makeText(mContext, e.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected() {

    }
}
