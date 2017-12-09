package m.tool.item;

import m.tool.MTOOL;
import m.tool.R;
import m.tool.stuff.Item;
import m.tool.stuff.TorchActivity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

public class Torch implements ItemClass{

	public void layout(int feld_nummer, int breite, int hoehe, int seite, String option) {
		for(int i = 0; i < ids.size(); i++)
			if(ids.get(i)[0].equals(MTOOL.seiten+1))
				ids.clear();
		MTOOL.page[seite-1].torch[feld_nummer].build(R.drawable.torch, 0, R.drawable.torch_light, 0, 
				R.string.torch, false, true, false, null, null, null, null, "", 0, null);
		if(!ids.contains(new Integer[]{seite, feld_nummer})) {
			ids.add(new Integer[]{seite, feld_nummer});
			ids_option.add(option);
		}
		MTOOL.page[seite-1].torch[feld_nummer].setVisibility(View.VISIBLE);
		update(seite, feld_nummer, option);
	}
	
	public Item getLayout(Context context, String option) {
		Item item = new Item(context);
		item.build(R.drawable.torch, 0, R.drawable.torch_light, 0, R.string.torch,
				false, true, false, null, null, null, null, "", 0, null);
		return item;
	}

	public void update() {}
	public void update(int seite, int feld_nummer, String option) {}

	public void click(String option) {
		MTOOL.mtool.startActivity(new Intent(MTOOL.context, TorchActivity.class));
	}
	
	public void toggle() {}
	public void activate() {}
	public void deactivate() {}
	public int get(int what) {return NOTHING;}
	public boolean is(int what) {return false;}
	public void set(int what, int value) {}
	public boolean gotStatus() {
		return false;
	}
	public boolean opensDialog(String option) {
		return true;
	}
}