package m.tool.item;

import m.tool.MTOOL;
import m.tool.R;
import m.tool.stuff.Item;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.view.View;

public class Wifi implements ItemClass{

	public void layout(int feld_nummer, int breite, int hoehe, int seite, String option) {
		for(int i = 0; i < ids.size(); i++)
			if(ids.get(i)[0].equals(MTOOL.seiten+1))
				ids.clear();
		MTOOL.page[seite-1].wifi[feld_nummer].build(R.drawable.wifi, R.drawable.wifi_off, R.drawable.wifi_light, R.drawable.wifi_off_light, R.string.wifi,
				false, true, false, null, null, null, null, "", 0, null);
		if(!ids.contains(new Integer[]{seite, feld_nummer})) {
			ids.add(new Integer[]{seite, feld_nummer});
			ids_option.add(option);
		}
		MTOOL.page[seite-1].wifi[feld_nummer].setVisibility(View.VISIBLE);
		update(seite, feld_nummer, option);
	}
	
	public Item getLayout(Context context, String option) {
		Item item = new Item(context);
		item.build(R.drawable.wifi, R.drawable.wifi_off, R.drawable.wifi_light, R.drawable.wifi_off_light, R.string.wifi,
				false, true, false, null, null, null, null, "", 0, null);
		return item;
	}

	public void update() {
		for(int i = 0; i < ids.size(); i++)
			if(MTOOL.page != null && MTOOL.page[ids.get(i)[0]-1] != null && MTOOL.page[ids.get(i)[0]-1].created && MTOOL.page[ids.get(i)[0]-1].wifi[ids.get(i)[1]] != null)
				update(ids.get(i)[0], ids.get(i)[1], ids_option.get(i));
	}
	
	public void update(int seite, int feld_nummer, String option) {
		if(get(STATUS) == STATUS_ENABLED)
			MTOOL.page[seite-1].wifi[feld_nummer].activate();
		else if(get(STATUS) == STATUS_DISABLED)
			MTOOL.page[seite-1].wifi[feld_nummer].deactivate();
		else if(get(STATUS) == STATUS_LOADING)
			MTOOL.page[seite-1].wifi[feld_nummer].loading();
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
			WifiManager wm = (WifiManager) MTOOL.context.getSystemService(Context.WIFI_SERVICE);
			if(wm.getWifiState() == WifiManager.WIFI_STATE_ENABLED)
				return STATUS_ENABLED;
			if(wm.getWifiState() == WifiManager.WIFI_STATE_ENABLING || wm.getWifiState() == WifiManager.WIFI_STATE_DISABLING)
				return STATUS_LOADING;
			return STATUS_DISABLED;
		} return NOTHING;
	}
	public boolean is(int what) {
		if(what == STATUS)
			return (get(what) == STATUS_ENABLED);
		return false;
	}
	
	public void set(int what, int value) {
		if(what == STATUS) {
			WifiManager wm = (WifiManager) MTOOL.context.getSystemService(Context.WIFI_SERVICE);
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