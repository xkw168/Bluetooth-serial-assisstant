package com.example.xkw.bluetooth;

import java.io.Serializable;

/**
 * Created by xkw on 2017/12/27.
 */

public class BLEDevice implements Serializable{
    private String Device_name;
    private String Device_address;

    public BLEDevice(){

    }

    public BLEDevice(String Device_name, String Device_address){
        this.Device_name = Device_name;
        this.Device_address = Device_address;
    }

    public String GetDeviceName(){
        return Device_name;
    }

    public String GetDeviceAddress(){
        return Device_address;
    }

    public void SetDeviceName(String name){
        Device_name = name;
    }

    public void SetDeviceAddress(String address){
        Device_address = address;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BLEDevice){
            BLEDevice temp = (BLEDevice)obj;
            return this.Device_name.equals(temp.Device_name) && this.Device_address.equals(temp.Device_address);
        }
        return super.equals(obj);
    }
}
