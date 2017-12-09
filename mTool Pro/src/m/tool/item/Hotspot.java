package m.tool.item;

import java.lang.reflect.Method;

import m.tool.pro.MTOOL;
import m.tool.pro.R;
import m.tool.stuff.Item;
import android.annotation.TargetApi;
import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.view.View;

public class Hotspot implements ItemClass{

    private static int WIFI_AP_STATE_DISABLING = 0;
    private static int WIFI_AP_STATE_DISABLED = 1;
    private static int WIFI_AP_STATE_ENABLING = 2;
    private static int WIFI_AP_STATE_ENABLED = 3;
	
	public void layout(int feld_nummer, int breite, int hoehe, int seite, String option) {
		for(int i = 0; i < ids.size(); i++)
			if(ids.get(i)[0].equals(MTOOL.seiten+1))
				ids.clear();
		MTOOL.page[seite-1].hotspot[feld_nummer].build(R.drawable.hotspot, R.drawable.hotspot_off, R.drawable.hotspot_light, R.drawable.hotspot_off_light, R.string.hotspot,
				false, true, false, null, null, null, null, "", 0, null);
		if(!ids.contains(new Integer[]{seite, feld_nummer})) {
			ids.add(new Integer[]{seite, feld_nummer});
			ids_option.add(option);
		}
		MTOOL.page[seite-1].hotspot[feld_nummer].setVisibility(View.VISIBLE);
		update(seite, feld_nummer, option);
	}
	
	public Item getLayout(Context context, String option) {
		Item item = new Item(context);
		item.build(R.drawable.hotspot, R.drawable.hotspot_off, R.drawable.hotspot_light, R.drawable.hotspot_off_light, R.string.hotspot,
				false, true, false, null, null, null, null, "", 0, null);
		return item;
	}

	public void update() {
		for(int i = 0; i < ids.size(); i++)
			if(MTOOL.page != null && MTOOL.page[ids.get(i)[0]-1] != null && MTOOL.page[ids.get(i)[0]-1].created && MTOOL.page[ids.get(i)[0]-1].hotspot[ids.get(i)[1]] != null)
				update(ids.get(i)[0], ids.get(i)[1], ids_option.get(i));
	}
	
	public void update(int seite, int feld_nummer, String option) {
		if(get(STATUS) == STATUS_ENABLED)
			MTOOL.page[seite-1].hotspot[feld_nummer].activate();
		else if(get(STATUS) == STATUS_DISABLED)
			MTOOL.page[seite-1].hotspot[feld_nummer].deactivate();
		else if(get(STATUS) == STATUS_LOADING)
			MTOOL.page[seite-1].hotspot[feld_nummer].loading();
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
		setNumbers();
		if(what == STATUS) {
	        try {
	        	WifiManager wm = (WifiManager) MTOOL.context.getSystemService(Context.WIFI_SERVICE);
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

	public boolean is(int what) {
		if(what == STATUS)
			return (get(what) == STATUS_ENABLED);
		return false;
	}
	
	public void set(int what, int value) {
		if(what == STATUS) {
			boolean enabled = (value == STATUS_ENABLED);
			WifiManager wm = (WifiManager) MTOOL.context.getSystemService(Context.WIFI_SERVICE);
			if (enabled && wm.getConnectionInfo() != null) {
				MTOOL.wifi.deactivate();
	        }
			try {
				MTOOL.wifi.deactivate();
	            Method method1 = wm.getClass().getMethod("setWifiApEnabled",
	                WifiConfiguration.class, boolean.class);
	            method1.invoke(wm, null, enabled);
	        } catch (Exception e) {
	        	e.printStackTrace();
	        }
	        if (!enabled)
	            MTOOL.wifi.activate();
		}
	}
	
	public boolean gotStatus() {
		return true;
	}
	public boolean opensDialog(String option) {
		return false;
	}
}