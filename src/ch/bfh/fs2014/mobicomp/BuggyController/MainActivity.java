package ch.bfh.fs2014.mobicomp.BuggyController;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
    /**
     * Called when the activity is first created.
     */

    Boolean abort = false;

    TinkerforgeThread tinkerforgeThread = null;

    public TinkerforgeThread getNewTinkerforgeThread() {
        return new TinkerforgeThread(this) {
            @Override
            public void onPostExecute(Boolean result) {
                final Button buttonConnect = (Button) findViewById(R.id.buttonConnect);
                buttonConnect.setText("Connect");
                shouldAbort(true, false);
            }

            @Override
            public void onPreExecute() {

            }

            @Override
            public void onProgressUpdate(Void... values) {

            }
        };
    }

    public synchronized boolean shouldAbort(Boolean isSet, Boolean newValue) {
        if(isSet) {
            abort = newValue;
        }
        return abort;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final Button buttonConnect = (Button) findViewById(R.id.buttonConnect);
        buttonConnect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(buttonConnect.getText().equals("Disconnect")) {
                    shouldAbort(true, true);

                }
                else {
                    buttonConnect.setText("Disconnect");

                    tinkerforgeThread = getNewTinkerforgeThread();
                    tinkerforgeThread.execute();
                }
            }
        });

        final Button buttonUp = (Button) findViewById(R.id.buttonUp);
        buttonUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        shouldAbort(true, true);
    }
}
