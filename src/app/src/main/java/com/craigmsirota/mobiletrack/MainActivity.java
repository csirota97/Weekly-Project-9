package com.craigmsirota.mobiletrack;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static String IP;
    public static int x, y, step;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IP = getIPAddress(true);

        IP = "0.0.0.0";
        TextView IPAddr = (TextView) findViewById(R.id.IPaddr);
        IPAddr.setText("IP: " + IP);
        x = 500;
        y = 500;
        step = 10;
        sendArrow(("(" + x + ',' + y + ')'));
    }

    public static String getIPAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress();
                        boolean isIPv4 = sAddr.indexOf(':')<0;

                        if (useIPv4) {
                            if (isIPv4)
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%');
                                return delim<0 ? sAddr.toUpperCase() : sAddr.substring(0, delim).toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    public void send(View v) {
        sendArrow(collectPos());

    }


    public void sendArrow(final String data) {
        Log.d("MSG","SENDING");

        Runnable myRunnable =
                new Runnable(){
                    public void run(){
                        System.out.println("Runnable running");
                        DatagramSocket datagramSocket = null;

                        try {
                            datagramSocket = new DatagramSocket();
                            byte[] b = (data).getBytes();
                            InetAddress inetAddress = InetAddress.getByName(collectIP());           //IP Address of python.cs.rutgers.edu  -- currently only works on pythoncsrutgerseduIP -- trying to figure out local IP
                            int inet = Log.d("InetAddr", inetAddress.toString());
                            DatagramPacket datagramPacket = new DatagramPacket(b,b.length,inetAddress,19876);

                            datagramSocket.send(datagramPacket);

                            Log.d("MSG","SENT");
                        } catch (IOException e) {

                            Log.d("ERR","BEEP");
                            e.printStackTrace();
                        }
                    }
                };

        Thread thread = new Thread(myRunnable);
        thread.start();

    }


    private String collectPos() {
        EditText xpos = (EditText) findViewById(R.id.XPos);
        EditText ypos = (EditText) findViewById(R.id.YPos);
        x = Integer.parseInt(xpos.getText().toString());
        y = Integer.parseInt(ypos.getText().toString());
        Log.d("XPos", x+"");
Log.d("YPos", y+"");

        return "(" + x + "," + y + ')';
    }

    public void sendArrowBase(View v) {
        EditText xpos = (EditText) findViewById(R.id.XPos);
        EditText ypos = (EditText) findViewById(R.id.YPos);
        x = Integer.parseInt(xpos.getText().toString());
        y = Integer.parseInt(ypos.getText().toString());

        switch (v.getId()) {
            case R.id.up:
                y -= step;
                if (y < 0) y = 0;
                ypos.setText(y+"");
                break;
            case R.id.leftButton:
                x -= step;
                if (x < 0) x = 0;
                xpos.setText(x+"");
                break;
            case R.id.down:
                y += step;
                ypos.setText(y+"");
                break;
            case R.id.rightButton:
                x += step;
                xpos.setText(x+"");
                break;

        }

        sendArrow("(" + x + "," + y + ')');
    }

    private String collectIP() {
        return ((EditText) findViewById(R.id.ipInput)).getText().toString();
    }



}
