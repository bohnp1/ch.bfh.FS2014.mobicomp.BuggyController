package ch.bfh.fs2014.mobicomp.BuggyController;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.tinkerforge.*;

import java.io.IOException;

public class MainActivity extends Activity {
    /**
     * Called when the activity is first created.
     */

    private static final String HOST = "192.168.43.224";
    private static final int PORT = 4223;

    private static IPConnection ipcon = new IPConnection(); // Create IP connection
    private static BrickDC dcLeft = new BrickDC("6wW1Yj", ipcon); // Create device object

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final Button buttonConnect = (Button) findViewById(R.id.buttonConnect);
        buttonConnect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    ipcon.connect(HOST, PORT); // Connect to brickd

                    // Don't use device before ipcon is connected

                    dcLeft.setPWMFrequency(10000); // Use PWM frequency of 10kHz

                    dcLeft.setDriveMode((short) 1); // Use 1 = Drive/Coast instead of 0 = Drive/Brake

                    dcLeft.enable();
                    dcLeft.setAcceleration(5000); // Slow acceleration
                    dcLeft.setVelocity((short)32767); // Full speed forward


                    final TextView textViewLog = (TextView) findViewById(R.id.textViewLog);
                    textViewLog.setText("connected =)");

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (AlreadyConnectedException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                } catch (NotConnectedException e) {
                    e.printStackTrace();
                }
            }
        });

        final Button buttonUp = (Button) findViewById(R.id.buttonUp);
        buttonUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });


        //final TinkerforgeApplication the17HerzApplication = TinkerforgeApplication.getInstance(); // new The17HerzApplication();


        //TinkerforgeStackAgency.getInstance().getStackAgent(TinkerforgeApplication.BARO_SENSOR).addApplication(the17HerzApplication);




        //System.console().readLine("Press key to exit\n");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //final TinkerforgeApplication the17HerzApplication = TinkerforgeApplication.getInstance(); // new The17HerzApplication();

        //TinkerforgeStackAgency.getInstance().getStackAgent(TinkerforgeApplication.BARO_SENSOR).removeApplication(the17HerzApplication);

        try {
            ipcon.disconnect();
        } catch (NotConnectedException e) {
            e.printStackTrace();
        }
    }
}
