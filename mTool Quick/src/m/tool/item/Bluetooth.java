package m.tool.item;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.os.Build;

public class Bluetooth implements ItemClass{

	public final int CONNECTION_STATE = -10;
	public final int CONNECTION_CONNECTED = 10;
	public final int CONNECTION_DISCONNECTED = 11;
	
	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	public boolean isConnected() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			BluetoothAdapter ba = BluetoothAdapter.getDefaultAdapter();
			if(ba != null)
				return(ba.getProfileConnectionState(BluetoothProfile.HEADSET) == BluetoothAdapter.STATE_CONNECTED);
			return false;
		} return false;
	}

	public void activate(Context context) {
		set(STATUS, STATUS_ENABLED, context);
	}
	public void deactivate(Context context) {
		set(STATUS, STATUS_DISABLED, context);
	}
	
	public int get(int what, Context context) {
		if(what == STATUS) {
			BluetoothAdapter ba = BluetoothAdapter.getDefaultAdapter();
			if(ba != null) {
				if(ba.getState() == BluetoothAdapter.STATE_ON)
					return STATUS_ENABLED;
				if(ba.getState() == BluetoothAdapter.STATE_TURNING_ON || ba.getState() == BluetoothAdapter.STATE_TURNING_OFF)
					return STATUS_LOADING;
				return STATUS_DISABLED;
			} return NOTHING;
		} if(what == CONNECTION_STATE) {
			if(isConnected())
				return CONNECTION_CONNECTED;
			return CONNECTION_DISCONNECTED;
		} return NOTHING;
	}
	
	public boolean is(int what, Context context) {
		if(what == STATUS)
			return (get(STATUS, context) == STATUS_ENABLED);
		if(what == CONNECTION_STATE)
			return (get(CONNECTION_STATE, context) == CONNECTION_CONNECTED);
		return false;
	}
	
	public void set(int what, int value, Context context) {
		if(value != NOTHING)
			if(what == STATUS) {
				BluetoothAdapter ba = BluetoothAdapter.getDefaultAdapter();
				if(ba != null) {
					if(value == STATUS_ENABLED)
						ba.enable();
					else
						ba.disable();
				}
			}
	}

	public boolean gotStatus() {
		return true;
	}
	public boolean opensDialog(String option) {
		return false;
	}
}