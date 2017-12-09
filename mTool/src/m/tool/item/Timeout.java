package m.tool.item;

import m.tool.MTOOL;
import m.tool.R;
import m.tool.stuff.Item;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.provider.Settings;
import android.view.View;

public class Timeout implements ItemClass{

	public static final int ALWAYS_ON = 10;
	public static final int SECOND_15 = 11;
	public static final int SECOND_30 = 12;
	public static final int MINUTE_1 = 13;
	public static final int MINUTE_2 = 14;
	public static final int MINUTE_5 = 15;
	public static final int MINUTE_10 = 16;
	public static final int MINUTE_30 = 17;
	
	public static final int TIMEOUT = -10;
	public static final int TOGGLE_ALWAYS_ON = -11;
	
	public void layout(int feld_nummer, int breite, int hoehe, int seite, String option) {
		for(int i = 0; i < ids.size(); i++)
			if(ids.get(i)[0].equals(MTOOL.seiten+1))
				ids.clear();
		MTOOL.page[seite-1].timeout[feld_nummer].build(R.drawable.timeout, R.drawable.timeout_off, R.drawable.timeout_light, R.drawable.timeout_off_light, 
				R.string.timeout, false, true, false, null, null, null, null, "", 0, null);
		if(!ids.contains(new Integer[]{seite, feld_nummer})) {
			ids.add(new Integer[]{seite, feld_nummer});
			ids_option.add(option);
		}
		MTOOL.page[seite-1].timeout[feld_nummer].setVisibility(View.VISIBLE);
		update(seite, feld_nummer, option);
	}
	
	public Item getLayout(Context context, String option) {
		Item item = new Item(context);
		item.build(R.drawable.timeout, R.drawable.timeout_off, R.drawable.timeout_light, R.drawable.timeout_off_light, 
				R.string.timeout, false, true, false, null, null, null, null, "", 0, null);
		return item;
	}

	public void update() {
		for(int i = 0; i < ids.size(); i++)
			if(MTOOL.page != null && MTOOL.page[ids.get(i)[0]-1] != null && MTOOL.page[ids.get(i)[0]-1].created && 
			MTOOL.page[ids.get(i)[0]-1].timeout[ids.get(i)[1]] != null)
				update(ids.get(i)[0], ids.get(i)[1], ids_option.get(i));
	}
	
	public void update(int seite, int feld_nummer, String option) {
		if(get(TIMEOUT) == ALWAYS_ON)
			MTOOL.page[seite-1].timeout[feld_nummer].deactivate();
		else
			MTOOL.page[seite-1].timeout[feld_nummer].activate();
	}

	public void click(String option) {
		int selected_item = get(TIMEOUT)-10;
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MTOOL.mtool);
		alertDialogBuilder
			.setSingleChoiceItems(R.array.timeout_array, selected_item, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					set(TIMEOUT, which+10);
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
		if(what == TIMEOUT) {
			int i = Settings.System.getInt(MTOOL.mtool.getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, 30000);
			if(i == -1 || i == 86400000)
				return ALWAYS_ON;
			if(i == 15000)
				return SECOND_15;
			if(i == 30000)
				return SECOND_30;
			if(i == 60000)
				return MINUTE_1;
			if(i == 120000)
				return MINUTE_2;
			if(i == 300000)
				return MINUTE_5;
			if(i == 600000)
				return MINUTE_10;
			if(i == 1800000)
				return MINUTE_30;
			return i;
		} else if(what == STATUS) {
			int i = Settings.System.getInt(MTOOL.mtool.getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, 30000);
			if(i == -1 || i == 86400000)
				return STATUS_DISABLED;
			return STATUS_ENABLED;
		} return NOTHING;
	}
	
	public boolean is(int what) {
		if(what == ALWAYS_ON || what == TOGGLE_ALWAYS_ON)
			return (get(TIMEOUT) == ALWAYS_ON);
		if(what == STATUS)
			return (get(STATUS) == STATUS_ENABLED);
		return false;
	}
	
	public void set(int what, int value) {
		if(what == TIMEOUT) {
			int i = 0;
			if(value == ALWAYS_ON)
				i = 86400000;
			else if(value == SECOND_15)
				i = 15000;
			else if(value == SECOND_30)
				i = 30000;
			else if(value == MINUTE_1)
				i = 60000;
			else if(value == MINUTE_2)
				i = 120000;
			else if(value == MINUTE_5)
				i = 300000;
			else if(value == MINUTE_10)
				i = 600000;
			else if(value == MINUTE_30)
				i = 1800000;
			else if(value > 5000)
				i = value;
			if(i == -1 || i > 5000)
				Settings.System.putInt(MTOOL.mtool.getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, i);
		} if(what == TOGGLE_ALWAYS_ON) {
			if(is(ALWAYS_ON))
				set(TIMEOUT, MINUTE_1);
			else
				set(TIMEOUT, ALWAYS_ON);
		}
	}
	
	public boolean gotStatus() {
		return true;
	}
	public boolean opensDialog(String option) {
		return true;
	}
}