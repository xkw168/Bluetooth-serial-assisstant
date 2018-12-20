package com.example.xkw.bluetooth;

/**
 * Created by xkw on 2018/1/13.
 */

public class BluetoothData {
    /**
     * 字符串常量，Intent中的数据
     */
    public final static String DATA = "DATA";
    /**
     * 字符串常量，存放在Intent中的设备对象
     */
    public static final String DEVICE = "DEVICE";
    /**
     * 此程序使用的UUID
     */
    public static String DEVICE_SERVICE = "0000ffe0-0000-1000-8000-00805f9b34fb";
    public static String DEVICE_CHARACTERISTIC_SERVICE = "0000ffe1-0000-1000-8000-00805f9b34fb";

    /**
     * 发送的数据
     */
    public static String Send_Data = "send";
    public static String Connect_suc = "star";

    /**
     * 读写文件的文件名
     */
    public final static String File_name = "file.txt";
}