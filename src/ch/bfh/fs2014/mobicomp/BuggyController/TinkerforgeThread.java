package ch.bfh.fs2014.mobicomp.BuggyController;

import android.os.AsyncTask;
import com.tinkerforge.*;

import java.io.IOException;

public abstract class TinkerforgeThread extends AsyncTask<String, Void, Boolean> {

    private static final String TAG = "UserInfoDownloader"; // for LogCat

    private static final String HOST = "192.168.43.224";
    private static final int PORT = 4223;

    private static IPConnection ipcon = new IPConnection(); // Create IP connection
    private static BrickDC dcLeft = new BrickDC("6wW1Yj", ipcon); // Create device object
    private static BrickDC dcRight = new BrickDC("6jEWMX", ipcon); // Create device object

    private static MainActivity mainActivity;


    /*
     * Constructor
     */
    public TinkerforgeThread(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    protected Boolean doInBackground(String... Params) { // this method runs in dedicated non-UI thread

        try {
            ipcon.connect(HOST, PORT); // Connect to brickd

            // Don't use device before ipcon is connected

            dcLeft.setPWMFrequency(10000); // Use PWM frequency of 10kHz

            dcLeft.setDriveMode((short) 1); // Use 1 = Drive/Coast instead of 0 = Drive/Brake

            dcLeft.enable();
            dcLeft.setAcceleration(5000); // Slow acceleration
            dcLeft.setVelocity((short) 32767); // Full speed forward




        } catch (IOException e) {
            e.printStackTrace();
        } catch (AlreadyConnectedException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (NotConnectedException e) {
            e.printStackTrace();
        }

        while (!mainActivity.shouldAbort(false, false)) {

            try {



                Thread.sleep(10);
                publishProgress();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        try {

            dcLeft.disable();

            ipcon.disconnect();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (NotConnectedException e) {
            e.printStackTrace();
        }

        return true;
    }




    @Override
    public abstract void onPostExecute(Boolean result);

    @Override
    public abstract void onPreExecute();

    @Override
    public abstract void onProgressUpdate(Void... values);
  } 

