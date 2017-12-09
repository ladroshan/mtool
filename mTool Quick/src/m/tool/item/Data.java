package m.tool.item;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.content.Context;
import android.net.ConnectivityManager;

public class Data implements ItemClass{

	public void activate(Context context) {
		set(STATUS, STATUS_ENABLED, context);
	}
	public void deactivate(Context context) {
		set(STATUS, STATUS_DISABLED, context);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int get(int what, Context context) {
		if(what == STATUS) {
			boolean mobileDataEnabled = false;
		    ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
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
	
	public boolean is(int what, Context context) {
		if(what == STATUS)
			return (get(STATUS, context) == STATUS_ENABLED);
		return false;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void set(int what, int value, Context context) {
		if(value != NOTHING)
			if(what == STATUS) {
				final ConnectivityManager conman = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
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