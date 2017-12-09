package m.tool.item;

import android.content.Context;
import android.net.wifi.WifiManager;

public class Wifi implements ItemClass{

	public void activate(Context context) {
		set(STATUS, STATUS_ENABLED, context);
	}
	public void deactivate(Context context) {
		set(STATUS, STATUS_DISABLED, context);
	}
	
	public int get(int what, Context context) {
		if(what == STATUS) {
			WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
			if(wm.getWifiState() == WifiManager.WIFI_STATE_ENABLED)
				return STATUS_ENABLED;
			if(wm.getWifiState() == WifiManager.WIFI_STATE_ENABLING || wm.getWifiState() == WifiManager.WIFI_STATE_DISABLING)
				return STATUS_LOADING;
			return STATUS_DISABLED;
		} return NOTHING;
	}
	public boolean is(int what, Context context) {
		if(what == STATUS)
			return (get(what, context) == STATUS_ENABLED);
		return false;
	}
	
	public void set(int what, int value, Context context) {
		if(value != NOTHING)
		if(what == STATUS) {
			WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
			wm.setWifiEnabled(value == STATUS_ENABLED);
		}
	}
	
	public boolean gotStatus() {
		return true;
	}
	public boolean opensDialog(String option) {
		return false;
	}
}