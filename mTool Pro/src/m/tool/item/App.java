package m.tool.item;

import m.tool.pro.MTOOL;
import m.tool.pro.R;
import m.tool.stuff.Item;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.view.View;

public class App implements ItemClass{

	public void layout(int feld_nummer, int breite, int hoehe, int seite, String option) {
		for(int i = 0; i < ids.size(); i++)
			if(ids.get(i)[0].equals(MTOOL.seiten+1))
				ids.clear();
		Drawable icon = null;
		try {
			icon = MTOOL.context.getPackageManager().getActivityInfo(ComponentName.unflattenFromString(option), 0).loadIcon(MTOOL.context.getPackageManager());
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
    	String name = "";
		try {
			name = MTOOL.context.getPackageManager().getActivityInfo(ComponentName.unflattenFromString(option), 0).loadLabel(MTOOL.context.getPackageManager()).toString();
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		if(icon == null)
			MTOOL.page[seite-1].app[feld_nummer].build(R.drawable.app, 0, R.drawable.app, 0, 0, true, true, false, null, null, null, null, name, 0, null);
		else
			MTOOL.page[seite-1].app[feld_nummer].build(0, 0, 0, 0, 0, true, true, false, icon, null, null, null, name, 0, null);
		if(!ids.contains(new Integer[]{seite, feld_nummer})) {
			ids.add(new Integer[]{seite, feld_nummer});
			ids_option.add(option);
		}
		MTOOL.page[seite-1].app[feld_nummer].setVisibility(View.VISIBLE);
	}

	public Item getLayout(Context context, String option) {
		Item item = new Item(context);
		Drawable icon = null;
		try {
			icon = MTOOL.context.getPackageManager().getActivityInfo(ComponentName.unflattenFromString(option), 0).loadIcon(MTOOL.context.getPackageManager());
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
    	String name = "";
		try {
			name = MTOOL.context.getPackageManager().getActivityInfo(ComponentName.unflattenFromString(option), 0).loadLabel(MTOOL.context.getPackageManager()).toString();
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		if(icon == null)
			item.build(R.drawable.app, 0, R.drawable.app, 0, 0, true, true, false, null, null, null, null, name, 0, null);
		else
			item.build(0, 0, 0, 0, 0, true, true, false, icon, null, null, null, name, 0, null);
		return item;
	}
	
	public void click(String option) {
		Intent i = new Intent();
		i.setComponent(ComponentName.unflattenFromString(option));
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		MTOOL.finish_now = true;
		MTOOL.context.startActivity(i);
	}
	
	public void update() {}
	public void update(int seite, int feld_nummer, String option) {}
	public void toggle() {}
	public void activate() {}
	public void deactivate() {}
	public int get(int what) {return 0;}
	public boolean is(int what) {return false;}
	public void set(int what, int value) {}

	public boolean gotStatus() {
		return false;
	}
	public boolean opensDialog(String option) {
		return false;
	}
}