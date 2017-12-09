package m.tool.item;

import m.tool.MTOOL;
import m.tool.R;
import m.tool.stuff.Item;
import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.os.Build;
import android.view.View;

public class Bluetooth implements ItemClass{

	public final int CONNECTION_STATE = -10;
	public final int CONNECTION_CONNECTED = 10;
	public final int CONNECTION_DISCONNECTED = 11;
	
	public void layout(int feld_nummer, int breite, int hoehe, int seite, String option) {
		for(int i = 0; i < ids.size(); i++)
			if(ids.get(i)[0].equals(MTOOL.seiten+1))
				ids.clear();
		MTOOL.page[seite-1].bluetooth[feld_nummer].build(R.drawable.bluetooth, R.drawable.bluetooth_off, R.drawable.bluetooth_light, R.drawable.bluetooth_off_light,
				R.string.bluetooth, false, true, false, null, null, null, null, "", 0, null);
		if(!ids.contains(new Integer[]{seite, feld_nummer})) {
			ids.add(new Integer[]{seite, feld_nummer});
			ids_option.add(option);
		}
		MTOOL.page[seite-1].bluetooth[feld_nummer].setVisibility(View.VISIBLE);
		update(seite, feld_nummer, option);
	}

	public Item getLayout(Context context, String option) {
		Item item = new Item(context);
		item.build(R.drawable.bluetooth, R.drawable.bluetooth_off, R.drawable.bluetooth_light, R.drawable.bluetooth_off_light,
				R.string.bluetooth, false, true, false, null, null, null, null, "", 0, null);
		return item;
	}
	
	public void update() {
		for(int i = 0; i < ids.size(); i++)
			update(ids.get(i)[0], ids.get(i)[1], ids_option.get(i));
	}
	
	public void update(int seite, int feld_nummer, String option) {
		if(get(STATUS) == STATUS_ENABLED)
			MTOOL.page[seite-1].bluetooth[feld_nummer].activate();
    	else if(get(STATUS) == STATUS_DISABLED)
    		MTOOL.page[seite-1].bluetooth[feld_nummer].deactivate();
    	else if(get(STATUS) == STATUS_LOADING)
    		MTOOL.page[seite-1].bluetooth[feld_nummer].loading();
		if(isConnected())
			MTOOL.page[seite-1].bluetooth[feld_nummer].setThings("green", R.drawable.bluetooth_connected, R.drawable.bluetooth_connected_light, null);
	}
	
	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	public boolean isConnected() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			BluetoothAdapter ba = BluetoothAdapter.getDefaultAdapter();
			if(ba != null)
				return(ba.getProfileConnectionState(BluetoothProfile.HEADSET) == BluetoothAdapter.STATE_CONNECTED);
			return false;
		} return false;
	}

	public void click(String option) {
		toggle();
	}
	
	public void toggle() {
		set(STATUS, (!is(STATUS)) ? STATUS_ENABLED : STATUS_DISABLED);
	}
	public void activate() {
		set(STATUS, STATUS_ENABLED);
	}
	public void deactivate() {
		set(STATUS, STATUS_DISABLED);
	}
	
	public int get(int what) {
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
	
	public boolean is(int what) {
		if(what == STATUS)
			return (get(STATUS) == STATUS_ENABLED);
		if(what == CONNECTION_STATE)
			return (get(CONNECTION_STATE) == CONNECTION_CONNECTED);
		return false;
	}
	
	public void set(int what, int value) {
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