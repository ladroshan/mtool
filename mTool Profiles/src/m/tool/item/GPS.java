package m.tool.item;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

public class GPS implements ItemClass{

	public void toggle(Context context) {
		final Intent poke = new Intent();
		poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
		poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
		poke.setData(Uri.parse("3"));
		context.sendBroadcast(poke);
	}
	public void activate(Context context) {
		set(STATUS, STATUS_ENABLED, context);
	}
	public void deactivate(Context context) {
		set(STATUS, STATUS_DISABLED, context);
	}
	
	public int get(int what, Context context) {
		if(what == STATUS) {
			String provider = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
			if(provider.contains("gps"))
				return STATUS_ENABLED;
			return STATUS_DISABLED;
		} return NOTHING;
	}
	public boolean is(int what, Context context) {
		if(what == STATUS)
			return (get(STATUS, context) == STATUS_ENABLED);
		return false;
	}
	public void set(int what, int value, Context context) {
		if(value != NOTHING)
			if(what == STATUS) {
				String provider = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
				if ((!provider.contains("gps") && value == STATUS_ENABLED) || (provider.contains("gps") && value != STATUS_ENABLED))
					toggle(context);
			}
	}

	public boolean gotStatus() {
		return true;
	}
	public boolean opensDialog(String option) {
		return false;
	}
}