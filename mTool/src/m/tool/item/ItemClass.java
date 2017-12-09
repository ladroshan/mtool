package m.tool.item;

import java.util.ArrayList;

import m.tool.stuff.Item;
import android.content.Context;

public interface ItemClass {
	
	public final int STATUS = -1;
	public final int NOTHING = 0;
	public final int STATUS_DISABLED = 1;
	public final int STATUS_ENABLED  = 2;
	public final int STATUS_LOADING  = 3;
	//other numbers from -9 to 9 are reserved for future usage
	
	public ArrayList<Integer[]> ids 	= new ArrayList<Integer[]>();
	public ArrayList<String> ids_option = new ArrayList<String>();
	
	public void layout(int feld_nummer, int breite, int hoehe, int seite, String option);
	
	public Item getLayout(Context context, String option);

	public void update();
	public void update(int seite, int feld_nummer, String option);

	public void click(String option);
	
	public void toggle();
	public void activate();
	public void deactivate();
	
	public int get(int what);
	public boolean is(int what);
	public void set(int what, int value);
	
	public boolean gotStatus();
	public boolean opensDialog(String option);
}
