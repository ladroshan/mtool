package m.tool.item;

import java.lang.reflect.Method;

import m.tool.quick.MTOOL;
import android.annotation.TargetApi;
import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;

public class Hotspot implements ItemClass{

    private static int WIFI_AP_STATE_DISABLING = 0;
    private static int WIFI_AP_STATE_DISABLED = 1;
    private static int WIFI_AP_STATE_ENABLING = 2;
    private static int WIFI_AP_STATE_ENABLED = 3;
	
	public void activate(Context context) {
		set(STATUS, STATUS_ENABLED, context);
	}
	public void deactivate(Context context) {
		set(STATUS, STATUS_DISABLED, context);
	}
	
	public int get(int what, Context context) {
		setNumbers();
		if(what == STATUS) {
	        try {
	        	WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
	            Method method2 = wm.getClass().getMethod("getWifiApState");
	            int state = (Integer) method2.invoke(wm);
	            if(state == WIFI_AP_STATE_DISABLED) return STATUS_DISABLED;
	            if(state == WIFI_AP_STATE_ENABLED)	return STATUS_ENABLED;
	            if(state == WIFI_AP_STATE_DISABLING || state == WIFI_AP_STATE_ENABLING) return STATUS_LOADING;
	            return NOTHING;
	        } catch (Exception e) {
	        	return NOTHING;
	        }
		} return NOTHING;
	}
	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	private void setNumbers() {
		WIFI_AP_STATE_DISABLING = 10;
	    WIFI_AP_STATE_DISABLED = 11;
	    WIFI_AP_STATE_ENABLING = 12;
	    WIFI_AP_STATE_ENABLED = 13;
	}

	public boolean is(int what, Context context) {
		if(what == STATUS)
			return (get(what, context) == STATUS_ENABLED);
		return false;
	}
	
	public void set(int what, int value, Context context) {
		if(value != NOTHING)
			if(what == STATUS) {
				boolean enabled = (value == STATUS_ENABLED);
				WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
				if (enabled && wm.getConnectionInfo() != null) {
					MTOOL.wifi.deactivate(context);
		        }
				try {
					MTOOL.wifi.deactivate(context);
		            Method method1 = wm.getClass().getMethod("setWifiApEnabled",
		                WifiConfiguration.class, boolean.class);
		            method1.invoke(wm, null, enabled);
		        } catch (Exception e) {
		        	e.printStackTrace();
		        }
		        if (!enabled)
		            MTOOL.wifi.activate(context);
			}
	}
	
	public boolean gotStatus() {
		return true;
	}
	public boolean opensDialog(String option) {
		return false;
	}
}