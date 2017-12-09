package m.tool.item;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import m.tool.pro.MTOOL;
import m.tool.pro.R;
import m.tool.stuff.Item;
import android.content.Context;
import android.net.ConnectivityManager;
import android.view.View;

public class Data implements ItemClass{

	public void layout(int feld_nummer, int breite, int hoehe, int seite, String option) {
		for(int i = 0; i < ids.size(); i++)
			if(ids.get(i)[0].equals(MTOOL.seiten+1))
				ids.clear();
		MTOOL.page[seite-1].data[feld_nummer].build(R.drawable.data, R.drawable.data_off, R.drawable.data_light, R.drawable.data_off_light,
				R.string.data, false, true, false, null, null, null, null, "", 0, null);
		if(!ids.contains(new Integer[]{seite, feld_nummer})) {
			ids.add(new Integer[]{seite, feld_nummer});
			ids_option.add(option);
		}
		MTOOL.page[seite-1].data[feld_nummer].setVisibility(View.VISIBLE);
		update(seite, feld_nummer, option);
	}

	public Item getLayout(Context context, String option) {
		Item item = new Item(context);
		item.build(R.drawable.data, R.drawable.data_off, R.drawable.data_light, R.drawable.data_off_light,
				R.string.data, false, true, false, null, null, null, null, "", 0, null);
		return item;
	}
	
	public void update() {
		for(int i = 0; i < ids.size(); i++)
			update(ids.get(i)[0], ids.get(i)[1], ids_option.get(i));
	}
	
	public void update(int seite, int feld_nummer, String option) {
	    if(is(STATUS))
	    	MTOOL.page[seite-1].data[feld_nummer].activate();
	    else
	    	MTOOL.page[seite-1].data[feld_nummer].deactivate();
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
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int get(int what) {
		if(what == STATUS) {
			boolean mobileDataEnabled = false;
		    ConnectivityManager cm = (ConnectivityManager) MTOOL.context.getSystemService(Context.CONNECTIVITY_SERVICE);
		    try {
		        Class cmClass = Class.forName(cm.getClass().getName());
		        Method method = cmClass.getDeclaredMethod("getMobileDataEnabled");
		        method.setAccessible(true);
		        mobileDataEnabled = (Boolean)method.invoke(cm);
		    } catch (Exception e) {
		    	e.printStackTrace();
		    }
		    return mobileDataEnabled ? STATUS_ENABLED : STATUS_DISABLED;
		} return NOTHING;
	}
	
	public boolean is(int what) {
		if(what == STATUS)
			return (get(STATUS) == STATUS_ENABLED);
		return false;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void set(int what, int value) {
		if(what == STATUS) {
			final ConnectivityManager conman = (ConnectivityManager) MTOOL.context.getSystemService(Context.CONNECTIVITY_SERVICE);
		    Class conmanClass = null;
			try {
				conmanClass = Class.forName(conman.getClass().getName());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		    Field iConnectivityManagerField = null;
			try {
				iConnectivityManagerField = conmanClass.getDeclaredField("mService");
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			}
		    iConnectivityManagerField.setAccessible(true);
		    Object iConnectivityManager = null;
			try {
				iConnectivityManager = iConnectivityManagerField.get(conman);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		    Class iConnectivityManagerClass = null;
			try {
				iConnectivityManagerClass = Class.forName(iConnectivityManager.getClass().getName());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		    Method setMobileDataEnabledMethod = null;
			try {
				setMobileDataEnabledMethod = iConnectivityManagerClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		    setMobileDataEnabledMethod.setAccessible(true);
	
		    try {
				setMobileDataEnabledMethod.invoke(iConnectivityManager, (value == STATUS_ENABLED));
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean gotStatus() {
		return true;
	}
	public boolean opensDialog(String option) {
		return false;
	}
}