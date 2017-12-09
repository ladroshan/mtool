package m.tool.item;

import m.tool.MTOOL;
import m.tool.R;
import m.tool.stuff.Item;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.view.Surface;
import android.view.View;

public class Rotation implements ItemClass{

	public static final int ROTATION = -10;
	
	public void layout(int feld_nummer, int breite, int hoehe, int seite, String option) {
		for(int i = 0; i < ids.size(); i++)
			if(ids.get(i)[0].equals(MTOOL.seiten+1))
				ids.clear();
		MTOOL.page[seite-1].rotation[feld_nummer].build(R.drawable.rotation, R.drawable.rotation_port, R.drawable.rotation_light, R.drawable.rotation_port_light,
				R.string.rotation, false, true, false, null, null, null, null, "", 0, null);
		if(!ids.contains(new Integer[]{seite, feld_nummer})) {
			ids.add(new Integer[]{seite, feld_nummer});
			ids_option.add(option);
		}
		MTOOL.page[seite-1].rotation[feld_nummer].setVisibility(View.VISIBLE);
		update(seite, feld_nummer, option);
	}
	
	public Item getLayout(Context context, String option) {
		Item item = new Item(context);
		item.build(R.drawable.rotation, R.drawable.rotation_port, R.drawable.rotation_light, R.drawable.rotation_port_light, R.string.rotation,
				false, true, false, null, null, null, null, "", 0, null);
		return item;
	}

	public void update() {
		for(int i = 0; i < ids.size(); i++)
			if(MTOOL.page != null && MTOOL.page[ids.get(i)[0]-1] != null && MTOOL.page[ids.get(i)[0]-1].created && 
			MTOOL.page[ids.get(i)[0]-1].rotation[ids.get(i)[1]] != null)
				update(ids.get(i)[0], ids.get(i)[1], ids_option.get(i));
	}
	
	public void update(int seite, int feld_nummer, String option) {
		if(get(STATUS) == STATUS_ENABLED)
			MTOOL.page[seite-1].rotation[feld_nummer].activate();
		else if(get(STATUS) == STATUS_DISABLED) {
			MTOOL.page[seite-1].rotation[feld_nummer].deactivate();
			MTOOL.page[seite-1].rotation[feld_nummer].setThings("off", R.drawable.rotation_port, R.drawable.rotation_port_light, null);
			if(get(ROTATION) == Surface.ROTATION_180 || get(ROTATION) == Surface.ROTATION_270)
				MTOOL.page[seite-1].rotation[feld_nummer].setThings("off", R.drawable.rotation_land, R.drawable.rotation_land_light, null);
		}
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
			if(Settings.System.getInt(MTOOL.mtool.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 1) == 1)
				return STATUS_ENABLED;
			return STATUS_DISABLED;
		} if(what == ROTATION) {
			return getRotation(0);
		} return NOTHING;
	}
	
	public boolean is(int what) {
		if(what == STATUS)
			return (get(STATUS) == STATUS_ENABLED);
		return false;
	}
	
	public void set(int what, int value) {
		if(what == ROTATION) {
			set(STATUS, STATUS_DISABLED);
			setRotation(value);
		} if(what == STATUS) {
			int i = (value == STATUS_DISABLED) ? 0 : 1;
			Settings.System.putInt(MTOOL.mtool.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, i);
		}
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public int getRotation(int value) {
		return Settings.System.getInt(MTOOL.mtool.getContentResolver(), Settings.System.USER_ROTATION, value);
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void setRotation(int value) {
		Settings.System.putInt(MTOOL.mtool.getContentResolver(), Settings.System.USER_ROTATION, value);
	}
	
	public boolean gotStatus() {
		return true;
	}
	public boolean opensDialog(String option) {
		return false;
	}
}