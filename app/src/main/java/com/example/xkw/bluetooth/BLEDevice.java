package com.example.xkw.bluetooth;

import java.io.Serializable;

/**
 *
 * @author xkw
 * @date 2017/12/27
 */

public class BLEDevice implements Serializable{
    private String deviceName;
    private String deviceAddress;

    public BLEDevice(){

    }

    BLEDevice(String deviceName, String deviceAddress){
        this.deviceName = deviceName;
        this.deviceAddress = deviceAddress;
    }

    String getDeviceName(){
        return deviceName;
    }

    String getDeviceAddress(){
        return deviceAddress;
    }

    public void setDeviceName(String name){
        deviceName = name;
    }

    public void setDeviceAddress(String address){
        deviceAddress = address;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BLEDevice){
            BLEDevice temp = (BLEDevice)obj;
            return this.deviceName.equals(temp.deviceName) && this.deviceAddress.equals(temp.deviceAddress);
        }
        return super.equals(obj);
    }
}
