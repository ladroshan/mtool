package m.tool.item;

import android.content.Context;
import android.provider.Settings;

public class Brightness implements ItemClass{

	public static final int NOTHING = 9;
	public static final int AUTOMATIC = 10;
	public static final int LOW = 11;
	public static final int MEDIUM = 12;
	public static final int HIGH = 13;
	
	public static final int BRIGHTNESS = -10;
	public static final int TOGGLE_AUTOMATIC = -11;
	
	public void activate(Context context) {}
	public void deactivate(Context context) {}
	
	public int get(int what, Context context) {
		if(what == BRIGHTNESS) {
			int b = Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 128);
			int m = Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
			if(m == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC)
				return AUTOMATIC;
			else if(b <= 50)
				return LOW;
			else if(b > 50 && b < 205)
				return MEDIUM;
			else return HIGH;
		} return NOTHING;
	}
	
	public boolean is(int what, Context context) {
		if(what == AUTOMATIC || what == TOGGLE_AUTOMATIC)
			return (get(BRIGHTNESS, context) == AUTOMATIC);
		return false;
	}
	
	public void set(int what, int value, Context context) {
		if(value != NOTHING) {
			if(what == BRIGHTNESS) {
				if(value == AUTOMATIC)
					Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
				else {
					Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
					if(value == LOW)
						Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 10);
					else if(value == MEDIUM)
						Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 128);
					else if(value == HIGH)
						Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 245);
					else if(value >= 1 && value <= 255)
						Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, value);
				}
			} if(what == TOGGLE_AUTOMATIC) {
				if(is(AUTOMATIC, context))
					set(BRIGHTNESS, MEDIUM, context);
				else
					set(BRIGHTNESS, AUTOMATIC, context);
			}
		}
	}
	
	public boolean gotStatus() {
		return false;
	}
	public boolean opensDialog(String option) {
		return true;
	}
}