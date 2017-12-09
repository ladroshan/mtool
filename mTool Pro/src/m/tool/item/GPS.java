package m.tool.item;

import m.tool.pro.MTOOL;
import m.tool.pro.R;
import m.tool.stuff.Item;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.view.View;

public class GPS implements ItemClass{

	public void layout(int feld_nummer, int breite, int hoehe, int seite, String option) {
		for(int i = 0; i < ids.size(); i++)
			if(ids.get(i)[0].equals(MTOOL.seiten+1))
				ids.clear();
		MTOOL.page[seite-1].gps[feld_nummer].build(R.drawable.gps, R.drawable.gps_off, R.drawable.gps_light, R.drawable.gps_off_light,
				R.string.gps, false, true, false, null, null, null, null, "", 0, null);
		if(!ids.contains(new Integer[]{seite, feld_nummer})) {
			ids.add(new Integer[]{seite, feld_nummer});
			ids_option.add(option);
		}
		MTOOL.page[seite-1].gps[feld_nummer].setVisibility(View.VISIBLE);
		update(seite, feld_nummer, option);
	}

	public Item getLayout(Context context, String option) {
		Item item = new Item(context);
		item.build(R.drawable.gps, R.drawable.gps_off, R.drawable.gps_light, R.drawable.gps_off_light,
				R.string.gps, false, true, false, null, null, null, null, "", 0, null);
		return item;
	}
	
	public void update() {
		for(int i = 0; i < ids.size(); i++)
			update(ids.get(i)[0], ids.get(i)[1], ids_option.get(i));
	}
	
	public void update(int seite, int feld_nummer, String option) {
		if(is(STATUS))
			MTOOL.page[seite-1].gps[feld_nummer].activate();
    	else
    		MTOOL.page[seite-1].gps[feld_nummer].deactivate();
	}
	
	public void click(String option) {
		toggle();
	}
	
	public void toggle() {
		final Intent poke = new Intent();
		poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
		poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
		poke.setData(Uri.parse("3"));
		MTOOL.mtool.sendBroadcast(poke);
	}
	public void activate() {
		set(STATUS, STATUS_ENABLED);
	}
	public void deactivate() {
		set(STATUS, STATUS_DISABLED);
	}
	
	public int get(int what) {
		if(what == STATUS) {
			String provider = Settings.Secure.getString(MTOOL.context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
			if(provider.contains("gps"))
				return STATUS_ENABLED;
			return STATUS_DISABLED;
		} return NOTHING;
	}
	public boolean is(int what) {
		if(what == STATUS)
			return (get(STATUS) == STATUS_ENABLED);
		return false;
	}
	public void set(int what, int value) {
		if(what == STATUS) {
			String provider = Settings.Secure.getString(MTOOL.mtool.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
			if ((!provider.contains("gps") && value == STATUS_ENABLED) || (provider.contains("gps") && value != STATUS_ENABLED))
				toggle();
		}
	}

	public boolean gotStatus() {
		return true;
	}
	public boolean opensDialog(String option) {
		return false;
	}
}