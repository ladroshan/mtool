package m.tool.item;

import android.content.Context;
import android.provider.Settings;

public class Timeout implements ItemClass{

	public static final int NOTHING = 9;
	public static final int ALWAYS_ON = 10;
	public static final int SECOND_15 = 11;
	public static final int SECOND_30 = 12;
	public static final int MINUTE_1 = 13;
	public static final int MINUTE_2 = 14;
	public static final int MINUTE_5 = 15;
	public static final int MINUTE_10 = 16;
	public static final int MINUTE_30 = 17;
	
	public static final int TIMEOUT = -10;
	public static final int TOGGLE_ALWAYS_ON = -11;
	
	public void activate(Context context) {}
	public void deactivate(Context context) {}
	
	public int get(int what, Context context) {
		if(what == TIMEOUT) {
			int i = Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, 30000);
			if(i == -1 || i == 86400000)
				return ALWAYS_ON;
			if(i == 15000)
				return SECOND_15;
			if(i == 30000)
				return SECOND_30;
			if(i == 60000)
				return MINUTE_1;
			if(i == 120000)
				return MINUTE_2;
			if(i == 300000)
				return MINUTE_5;
			if(i == 600000)
				return MINUTE_10;
			if(i == 1800000)
				return MINUTE_30;
			return i;
		} else if(what == STATUS) {
			int i = Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, 30000);
			if(i == -1 || i == 86400000)
				return STATUS_DISABLED;
			return STATUS_ENABLED;
		} return NOTHING;
	}
	
	public boolean is(int what, Context context) {
		if(what == ALWAYS_ON || what == TOGGLE_ALWAYS_ON)
			return (get(TIMEOUT, context) == ALWAYS_ON);
		if(what == STATUS)
			return (get(STATUS, context) == STATUS_ENABLED);
		return false;
	}
	
	public void set(int what, int value, Context context) {
		if(value != NOTHING) {
			if(what == TIMEOUT) {
				int i = 0;
				if(value == ALWAYS_ON)
					i = 86400000;
				else if(value == SECOND_15)
					i = 15000;
				else if(value == SECOND_30)
					i = 30000;
				else if(value == MINUTE_1)
					i = 60000;
				else if(value == MINUTE_2)
					i = 120000;
				else if(value == MINUTE_5)
					i = 300000;
				else if(value == MINUTE_10)
					i = 600000;
				else if(value == MINUTE_30)
					i = 1800000;
				else if(value > 5000)
					i = value;
				if(i == -1 || i > 5000)
					Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, i);
			} if(what == TOGGLE_ALWAYS_ON) {
				if(is(ALWAYS_ON, context))
					set(TIMEOUT, MINUTE_1, context);
				else
					set(TIMEOUT, ALWAYS_ON, context);
			}
		}
	}
	
	public boolean gotStatus() {
		return true;
	}
	public boolean opensDialog(String option) {
		return true;
	}
}