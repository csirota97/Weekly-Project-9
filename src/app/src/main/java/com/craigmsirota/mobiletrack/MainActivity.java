package com.craigmsirota.mobiletrack;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static String IP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IP = getIPAddress(true);

        IP = "0.0.0.0";
        TextView IPAddr = (TextView) findViewById(R.id.IPaddr);
        IPAddr.setText("IP: " + IP);
    }

    public static String getIPAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress();
                        //boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        boolean isIPv4 = sAddr.indexOf(':')<0;

                        if (useIPv4) {
                            if (isIPv4)
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%'); // drop ip6 zone suffix
                                return delim<0 ? sAddr.toUpperCase() : sAddr.substring(0, delim).toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (Exception ignored) { } // for now eat exceptions
        return "";
    }


    public void send(View v) {
        Log.d("MSG","SENDING");

        Runnable myRunnable =
                new Runnable(){
                    public void run(){
                        System.out.println("Runnable running");
                        DatagramSocket datagramSocket = null;

                        try {
                            datagramSocket = new DatagramSocket();

                            String data = collectPos(); //data to send
                            byte[] b = (data).getBytes();
                            String macIP = "10.0.1.3";
                            String otherIP = "98.221.217.22";
                            String pythoncsrutgerseduIP = "128.6.13.233";
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

        return '(' + xpos.getText().toString() + ',' + ypos.getText().toString() + ')';
    }

    private String collectIP() {
        return ((EditText) findViewById(R.id.ipInput)).getText().toString();
    }



}
