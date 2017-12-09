package m.tool.item;

import m.tool.pro.MTOOL;
import m.tool.pro.Quick;
import m.tool.pro.R;
import m.tool.stuff.Item;
import android.content.Context;
import android.content.Intent;

public class QuickItem implements ItemClass{

	public void layout(int feld_nummer, int breite, int hoehe, int seite, String option) {}
	
	public Item getLayout(Context context, String option) {
		Item item = new Item(context);
		item.build(R.drawable.quick, R.drawable.quick_off, R.drawable.quick_light, R.drawable.quick_off_light, R.string.quick,
				false, true, false, null, null, null, null, "", 0, null);
		return item;
	}

	public void update() {}
	public void update(int seite, int feld_nummer, String option) {}

	public void click(String option) {
		MTOOL.mtool.startActivity(new Intent(MTOOL.mtool.getApplicationContext(), Quick.class));
	}
	
	public void toggle() {}
	public void activate() {}
	public void deactivate() {}
	public int get(int what) {
		return 0;
	}
	public boolean is(int what) {
		return false;
	}
	public void set(int what, int value) {}
	public boolean gotStatus() {
		return false;
	}
	public boolean opensDialog(String option) {
		return true;
	}
}