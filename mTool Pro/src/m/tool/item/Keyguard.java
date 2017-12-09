package m.tool.item;

import m.tool.pro.MTOOL;
import m.tool.pro.R;
import m.tool.stuff.Item;
import m.tool.stuff.KeyguardService;
import android.content.Context;
import android.content.Intent;
import android.view.View;

public class Keyguard implements ItemClass{

	public void layout(int feld_nummer, int breite, int hoehe, int seite, String option) {
		for(int i = 0; i < ids.size(); i++)
			if(ids.get(i)[0].equals(MTOOL.seiten+1))
				ids.clear();
		MTOOL.page[seite-1].keyguard[feld_nummer].build(R.drawable.keyguard, R.drawable.keyguard_off, R.drawable.keyguard_light, R.drawable.keyguard_off_light
				, R.string.keyguard, false, true, false, null, null, null, null, "", 0, null);
		if(!ids.contains(new Integer[]{seite, feld_nummer})) {
			ids.add(new Integer[]{seite, feld_nummer});
			ids_option.add(option);
		}
		MTOOL.page[seite-1].keyguard[feld_nummer].setVisibility(View.VISIBLE);
		update(seite, feld_nummer, option);
	}
	
	public Item getLayout(Context context, String option) {
		Item item = new Item(context);
		item.build(R.drawable.keyguard, R.drawable.keyguard_off, R.drawable.keyguard_light, R.drawable.keyguard_off_light
				, R.string.keyguard, false, true, false, null, null, null, null, "", 0, null);
		return item;
	}

	public void update() {
		for(int i = 0; i < ids.size(); i++)
			if(MTOOL.page != null && MTOOL.page[ids.get(i)[0]-1] != null && MTOOL.page[ids.get(i)[0]-1].created && MTOOL.page[ids.get(i)[0]-1].keyguard[ids.get(i)[1]] != null)
				update(ids.get(i)[0], ids.get(i)[1], ids_option.get(i));
	}
	
	public void update(int seite, int feld_nummer, String option) {
		if(get(STATUS) == STATUS_ENABLED)
			MTOOL.page[seite-1].keyguard[feld_nummer].activate();
		else if(get(STATUS) == STATUS_DISABLED)
			MTOOL.page[seite-1].keyguard[feld_nummer].deactivate();
	}

	public void click(String option) {
		toggle();
//		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MTOOL.mtool);
//		alertDialogBuilder
//			.setItems(R.array.keyguard_array, new DialogInterface.OnClickListener() {
//				public void onClick(DialogInterface dialog, int which) {
//					int i = (which == 0) ? STATUS_ENABLED : STATUS_DISABLED;
//					set(STATUS, i);
//					dialog.cancel();
//				}
//			})
//			.setOnCancelListener(new DialogInterface.OnCancelListener() {
//				public void onCancel(DialogInterface dialog) {
//					if(MTOOL.finish_later && Quick.mtoolSave == null) {
//						MTOOL.finish_later = false;
//		    			MTOOL.close();
//					}
//				}
//			})
//			.show();
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
			return MTOOL.keyguardOff ? STATUS_DISABLED : STATUS_ENABLED;
		} return NOTHING;
	}
	
	public boolean is(int what) {
		if(what == STATUS)
			return (get(what) == STATUS_ENABLED);
		return false;
	}
	
	public void set(int what, int value) {
		if(what == STATUS) {
			if(value == STATUS_DISABLED)
				MTOOL.mtool.startService(new Intent(MTOOL.context, KeyguardService.class));
			if(value == STATUS_ENABLED)
				MTOOL.mtool.stopService(new Intent(MTOOL.context, KeyguardService.class));
		}
	}
	
	public boolean gotStatus() {
		return true;
	}
	public boolean opensDialog(String option) {
		return false;
	}
}