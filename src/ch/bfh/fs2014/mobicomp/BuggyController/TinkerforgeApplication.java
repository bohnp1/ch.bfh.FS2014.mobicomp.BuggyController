package ch.bfh.fs2014.mobicomp.BuggyController;

import ch.quantasy.tinkerforge.tinker.agency.implementation.TinkerforgeStackAgency;
import ch.quantasy.tinkerforge.tinker.agent.implementation.TinkerforgeStackAgent;
import ch.quantasy.tinkerforge.tinker.agent.implementation.TinkerforgeStackAgentIdentifier;
import ch.quantasy.tinkerforge.tinker.application.implementation.AbstractTinkerforgeApplication;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeDevice;
import com.tinkerforge.Device;
import com.tinkerforge.TinkerforgeException;

import java.text.SimpleDateFormat;
import java.util.*;


public class TinkerforgeApplication extends AbstractTinkerforgeApplication {

    private static TinkerforgeApplication instance = null;

    public static TinkerforgeApplication getInstance() {
        if(instance == null) {
            instance = new TinkerforgeApplication();
        }
        return instance;
    }

    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
    // Assumes to be connected via USB
    public static final TinkerforgeStackAgentIdentifier BARO_SENSOR = new TinkerforgeStackAgentIdentifier("localhost");
    public static HashMap<Device, AbstractTinkerforgeApplication> connectedApps = new HashMap<Device, AbstractTinkerforgeApplication>();


    @Override
    public void deviceDisconnected(final TinkerforgeStackAgent tinkerforgeStackAgent, final Device device) {

        if (connectedApps.containsKey(device)) {

            super.removeTinkerforgeApplication(connectedApps.get(device));

            connectedApps.remove(device);

            //System.out.println("Device " + device + " disconnected and connected application removed!");
        } else {
            //System.out.println("Device " + device + " disconnected without connected application!");
        }
    }

    @Override
    public void deviceConnected(final TinkerforgeStackAgent tinkerforgeStackAgent, final Device device) {
        try {
            if (TinkerforgeDevice.getDevice(device) == TinkerforgeDevice.Barometer) {
                //BarometerApplication app = new BarometerApplication(device.getIdentity().uid);
                //app.addDoorEventListener(this);
            //    addApplication(device, app);
            } else {
                System.out.println("INFO: Device " + device + " with ID " + device.getIdentity().uid + " has no connectable Application!");
            }
        } catch (TinkerforgeException ex) {
            System.out.println("ERROR: Failed to connect Device " + device + "!");
        }
    }

    public void addApplication(final Device device, final AbstractTinkerforgeApplication newApplication) {
        connectedApps.put(device, newApplication);

        super.addTinkerforgeApplication(newApplication);

        System.out.println("Application  " + newApplication + " connected with Device " + device + " !");
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public boolean equals(final Object obj) {
        return this == obj;
    }
}
