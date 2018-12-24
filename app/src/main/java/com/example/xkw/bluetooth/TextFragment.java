package com.example.xkw.bluetooth;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author xkw
 */
public class TextFragment extends Fragment {

    /**
     * UI
     */
    private MainActivity mContext;
    private TextView tvDataRec, tvDataSend;
    private EditText editSend;

    /**
     * variable
     */
    private String dataRec = "  接收区域:\n";
    private String dataSend = "  发送区域:\n";
    private boolean mReceive = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_text, container, false);
        mContext = (MainActivity)getActivity();

        editSend = (EditText)view.findViewById(R.id.et_send_text);

        tvDataRec = (TextView)view.findViewById(R.id.tv_data_rec_text);
        tvDataRec.setMovementMethod(ScrollingMovementMethod.getInstance());
        tvDataRec.setText(dataRec);

        tvDataSend = (TextView)view.findViewById(R.id.tv_data_send_text);
        tvDataSend.setMovementMethod(ScrollingMovementMethod.getInstance());
        tvDataSend.setText(dataSend);

        Button btSend = (Button)view.findViewById(R.id.btn_send_text);
        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mContext.getConnectionState()){
                    String str = editSend.getText().toString();
                    dataSend += "  " + str + '\n';
                    tvDataSend.setText(dataSend);
                    mContext.sendData(str);
                }else {
                    Toast.makeText(mContext,"蓝牙未连接...",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MainActivity.REC_DATA);
        mContext.registerReceiver(mRecData,intentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        mContext.unregisterReceiver(mRecData);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private final BroadcastReceiver mRecData = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (MainActivity.REC_DATA.equals(action)){
                if (mReceive){
                    dataRec += "  " + mContext.getData() + '\n';
                    tvDataRec.setText(dataRec);
                }
            }
        }
    };

    public void clearRecField(){
        dataRec = "  接收区域:\n";
        tvDataRec.setText(dataRec);
    }

    public void clearSendField(){
        dataSend = "  发送区域:\n";
        tvDataSend.setText(dataSend);
    }

    public void setRecState(boolean state){
        mReceive = state;
    }
}
