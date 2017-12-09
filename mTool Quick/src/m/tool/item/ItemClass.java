package m.tool.item;

import java.util.ArrayList;

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
	
	public void activate(Context context);
	public void deactivate(Context context);
	
	public int get(int what, Context context);
	public boolean is(int what, Context context);
	public void set(int what, int value, Context context);
	
	public boolean gotStatus();
	public boolean opensDialog(String option);
}
