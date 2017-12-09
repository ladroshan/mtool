package m.tool.item;

import java.util.ArrayList;

import m.tool.MTOOL;
import m.tool.R;
import m.tool.stuff.Item;
import android.content.Context;
import android.view.View;

public class Info implements ItemClass{

	public static ArrayList<Integer[]> ids = new ArrayList<Integer[]>();
	
	public void layout(int feld_nummer, int breite, int hoehe, int seite, String option) {
		for(int i = 0; i < ids.size(); i++)
			if(ids.get(i)[0].equals(MTOOL.seiten+1))
				ids.clear();
		MTOOL.page[seite-1].wifi[feld_nummer].build(R.drawable.icon, 0, R.drawable.icon, 0, R.string.app_name,
				true, false, false, null, null, null, null, "", 0, null);
		if(!ids.contains(new Integer[]{seite, feld_nummer}))
				ids.add(new Integer[]{seite, feld_nummer});
		MTOOL.page[seite-1].wifi[feld_nummer].setVisibility(View.VISIBLE);
		update(seite, feld_nummer, "");
	}
	
	public Item getLayout(Context context, String option) {
		Item item = new Item(context);
		item.build(R.drawable.icon, 0, R.drawable.icon, 0, R.string.app_name,
				true, false, false, null, null, null, null, "", 0, null);
		return item;
	}

	public void update() {}
	public void update(int seite, int feld_nummer, String option) {}
	public void click(String option) {}
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