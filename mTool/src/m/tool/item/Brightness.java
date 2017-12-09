package m.tool.item;

import m.tool.MTOOL;
import m.tool.R;
import m.tool.stuff.Item;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.provider.Settings;
import android.view.View;

public class Brightness implements ItemClass{

	public static final int AUTOMATIC = 10;
	public static final int LOW = 11;
	public static final int MEDIUM = 12;
	public static final int HIGH = 13;
	
	public static final int BRIGHTNESS = -10;
	public static final int TOGGLE_AUTOMATIC = -11;
	
	public void layout(int feld_nummer, int breite, int hoehe, int seite, String option) {
		for(int i = 0; i < ids.size(); i++)
			if(ids.get(i)[0].equals(MTOOL.seiten+1))
				ids.clear();
		MTOOL.page[seite-1].brightness[feld_nummer].build(R.drawable.brightness, 0, R.drawable.brightness_light, 0, R.string.brightness,
				false, true, false, null, null, null, null, "", 0, null);
		if(!ids.contains(new Integer[]{seite, feld_nummer})) {
			ids.add(new Integer[]{seite, feld_nummer});
			ids_option.add(option);
		}
		MTOOL.page[seite-1].brightness[feld_nummer].setVisibility(View.VISIBLE);
		update(seite, feld_nummer, option);
	}
	
	public Item getLayout(Context context, String option) {
		Item item = new Item(context);
		item.build(R.drawable.brightness, 0, R.drawable.brightness_light, 0, R.string.brightness,
				false, true, false, null, null, null, null, "", 0, null);
		return item;
	}

	public void update() {
		for(int i = 0; i < ids.size(); i++)
			if(MTOOL.page != null && MTOOL.page[ids.get(i)[0]-1] != null && MTOOL.page[ids.get(i)[0]-1].created && 
			MTOOL.page[ids.get(i)[0]-1].brightness[ids.get(i)[1]] != null)
				update(ids.get(i)[0], ids.get(i)[1], ids_option.get(i));
	}
	
	public void update(int seite, int feld_nummer, String option) {
		if(get(BRIGHTNESS) == AUTOMATIC)
			MTOOL.page[seite-1].brightness[feld_nummer].setThings(R.drawable.brightness_auto, R.drawable.brightness_auto_light, null, "");
		else if(get(BRIGHTNESS) == LOW)
			MTOOL.page[seite-1].brightness[feld_nummer].setThings(R.drawable.brightness_low, R.drawable.brightness_low_light, null, "");
		else if(get(BRIGHTNESS) == HIGH)
			MTOOL.page[seite-1].brightness[feld_nummer].setThings(R.drawable.brightness_high, R.drawable.brightness_high_light, null, "");
		else
			MTOOL.page[seite-1].brightness[feld_nummer].setThings(R.drawable.brightness, R.drawable.brightness_light, null, "");
	}

	public void click(String option) {
		int selected_item = get(BRIGHTNESS)-10;
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MTOOL.mtool);
		alertDialogBuilder
			.setSingleChoiceItems(R.array.brightness_array, selected_item, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					set(BRIGHTNESS, which+10);
					dialog.cancel();
				}
			})
			.setOnCancelListener(new DialogInterface.OnCancelListener() {
				public void onCancel(DialogInterface dialog) {
					if(MTOOL.finish_later) {
						MTOOL.finish_later = false;
		    			MTOOL.close();
					}
				}
			})
			.show();
	}
	
	public void toggle() {}
	public void activate() {}
	public void deactivate() {}
	
	public int get(int what) {
		if(what == BRIGHTNESS) {
			int b = Settings.System.getInt(MTOOL.mtool.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 128);
			int m = Settings.System.getInt(MTOOL.mtool.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
			if(m == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC)
				return AUTOMATIC;
			else if(b <= 50)
				return LOW;
			else if(b > 50 && b < 205)
				return MEDIUM;
			else return HIGH;
		} return NOTHING;
	}
	
	public boolean is(int what) {
		if(what == AUTOMATIC || what == TOGGLE_AUTOMATIC)
			return (get(BRIGHTNESS) == AUTOMATIC);
		return false;
	}
	
	public void set(int what, int value) {
		if(what == BRIGHTNESS) {
			if(value == AUTOMATIC)
				Settings.System.putInt(MTOOL.mtool.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
			else {
				Settings.System.putInt(MTOOL.mtool.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
				if(value == LOW)
					Settings.System.putInt(MTOOL.mtool.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 10);
				else if(value == MEDIUM)
					Settings.System.putInt(MTOOL.mtool.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 128);
				else if(value == HIGH)
					Settings.System.putInt(MTOOL.mtool.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 245);
				else if(value >= 0 && value <= 255)
					Settings.System.putInt(MTOOL.mtool.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, value);
			}
		} if(what == TOGGLE_AUTOMATIC) {
			if(is(AUTOMATIC))
				set(BRIGHTNESS, MEDIUM);
			else
				set(BRIGHTNESS, AUTOMATIC);
		}
	}
	
	public boolean gotStatus() {
		return false;
	}
	public boolean opensDialog(String option) {
		return true;
	}
}