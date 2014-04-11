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
    private static final String UID = "aetiNB3mX2u"; // Change to your UID

    private static IPConnection ipcon = new IPConnection(); // Create IP connection
    private static BrickDC dc = new BrickDC(UID, ipcon); // Create device object

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final Button button = (Button) findViewById(R.id.buttonUp);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });

        //final TinkerforgeApplication the17HerzApplication = TinkerforgeApplication.getInstance(); // new The17HerzApplication();


        //TinkerforgeStackAgency.getInstance().getStackAgent(TinkerforgeApplication.BARO_SENSOR).addApplication(the17HerzApplication);


        try {
            ipcon.connect(HOST, PORT); // Connect to brickd

        // Don't use device before ipcon is connected

            dc.setPWMFrequency(10000); // Use PWM frequency of 10kHz

            dc.setDriveMode((short) 1); // Use 1 = Drive/Coast instead of 0 = Drive/Brake

            dc.enable();
            dc.setAcceleration(5000); // Slow acceleration
            dc.setVelocity((short)32767); // Full speed forward


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
