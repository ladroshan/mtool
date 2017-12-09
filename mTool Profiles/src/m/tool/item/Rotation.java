package m.tool.item;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;

public class Rotation implements ItemClass{

	public static final int ROTATION = -10;
	
	public void activate(Context context) {
		set(STATUS, STATUS_ENABLED, context);
	}
	public void deactivate(Context context) {
		set(STATUS, STATUS_DISABLED, context);
	}
	
	public int get(int what, Context context) {
		if(what == STATUS) {
			if(Settings.System.getInt(context.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 1) == 1)
				return STATUS_ENABLED;
			return STATUS_DISABLED;
		} if(what == ROTATION) {
			return getRotation(0, context);
		} return NOTHING;
	}
	
	public boolean is(int what, Context context) {
		if(what == STATUS)
			return (get(STATUS, context) == STATUS_ENABLED);
		return false;
	}
	
	public void set(int what, int value, Context context) {
		if(value != NOTHING) {
			if(what == ROTATION) {
				set(STATUS, STATUS_DISABLED, context);
				setRotation(value, context);
			} if(what == STATUS) {
				int i = (value == STATUS_DISABLED) ? 0 : 1;
				Settings.System.putInt(context.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, i);
			}
		}
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public int getRotation(int value, Context context) {
		return Settings.System.getInt(context.getContentResolver(), Settings.System.USER_ROTATION, value);
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void setRotation(int value, Context context) {
		Settings.System.putInt(context.getContentResolver(), Settings.System.USER_ROTATION, value);
	}
	
	public boolean gotStatus() {
		return true;
	}
	public boolean opensDialog(String option) {
		return false;
	}
}