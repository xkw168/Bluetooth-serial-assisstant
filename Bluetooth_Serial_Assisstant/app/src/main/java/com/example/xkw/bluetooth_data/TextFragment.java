package com.example.xkw.bluetooth_data;

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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class TextFragment extends Fragment {

    //UI
    private MainActivity mContext;
    private TextView tv_data_rec,tv_data_send;
    private EditText edit_send;
    private Button btn_send;

    //variable
    private String data_rec = "  接收区域:\n";
    private String data_send = "  发送区域:\n";
    private boolean mReceive = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_text, container, false);
        mContext = (MainActivity)getActivity();

        edit_send = (EditText)view.findViewById(R.id.et_send_text);

        tv_data_rec = (TextView)view.findViewById(R.id.tv_data_rec_text);
        tv_data_rec.setMovementMethod(ScrollingMovementMethod.getInstance());
        tv_data_rec.setText(data_rec);

        tv_data_send = (TextView)view.findViewById(R.id.tv_data_send_text);
        tv_data_send.setMovementMethod(ScrollingMovementMethod.getInstance());
        tv_data_send.setText(data_send);

        btn_send = (Button)view.findViewById(R.id.btn_send_text);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private final BroadcastReceiver mRecData = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (MainActivity.Rec_Data.equals(action)){
                if (mReceive){
                    data_rec += "  " + mContext.GetData() + '\n';
                    tv_data_rec.setText(data_rec);
                }
            }
        }
    };

    public void ClearRecField(){
        data_rec = "  接收区域:\n";
        tv_data_rec.setText(data_rec);
    }

    public void ClearSendField(){
        data_send = "  发送区域:\n";
        tv_data_send.setText(data_send);
    }

    public void SetRecState(boolean state){
        mReceive = state;
    }
}
