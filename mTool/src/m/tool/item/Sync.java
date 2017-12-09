package m.tool.item;

import m.tool.MTOOL;
import m.tool.R;
import m.tool.stuff.Item;
import android.content.ContentResolver;
import android.content.Context;
import android.view.View;

public class Sync implements ItemClass{

	public void layout(int feld_nummer, int breite, int hoehe, int seite, String option) {
		for(int i = 0; i < ids.size(); i++)
			if(ids.get(i)[0].equals(MTOOL.seiten+1))
				ids.clear();
		MTOOL.page[seite-1].sync[feld_nummer].build(R.drawable.sync, R.drawable.sync_off, R.drawable.sync_light, R.drawable.sync_off_light, R.string.sync,
				false, true, false, null, null, null, null, "", 0, null);
		if(!ids.contains(new Integer[]{seite, feld_nummer})) {
			ids.add(new Integer[]{seite, feld_nummer});
			ids_option.add(option);
		}
		MTOOL.page[seite-1].sync[feld_nummer].setVisibility(View.VISIBLE);
		update(seite, feld_nummer, option);
	}
	
	public Item getLayout(Context context, String option) {
		Item item = new Item(context);
		item.build(R.drawable.sync, R.drawable.sync_off, R.drawable.sync_light, R.drawable.sync_off_light, R.string.sync,
				false, true, false, null, null, null, null, "", 0, null);
		return item;
	}

	public void update() {
		for(int i = 0; i < ids.size(); i++)
			if(MTOOL.page != null && MTOOL.page[ids.get(i)[0]-1] != null && MTOOL.page[ids.get(i)[0]-1].created && MTOOL.page[ids.get(i)[0]-1].sync[ids.get(i)[1]] != null)
				update(ids.get(i)[0], ids.get(i)[1], ids_option.get(i));
	}
	
	public void update(int seite, int feld_nummer, String option) {
		if(get(STATUS) == STATUS_ENABLED)
			MTOOL.page[seite-1].sync[feld_nummer].activate();
		else if(get(STATUS) == STATUS_DISABLED)
			MTOOL.page[seite-1].sync[feld_nummer].deactivate();
		else if(get(STATUS) == STATUS_LOADING)
			MTOOL.page[seite-1].sync[feld_nummer].loading();
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
			if(ContentResolver.getMasterSyncAutomatically())
				return STATUS_ENABLED;
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
			ContentResolver.setMasterSyncAutomatically(value == STATUS_ENABLED);
		}
	}
	
	public boolean gotStatus() {
		return true;
	}
	public boolean opensDialog(String option) {
		return false;
	}
}