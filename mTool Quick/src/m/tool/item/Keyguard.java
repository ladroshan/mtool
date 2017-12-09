package m.tool.item;

import m.tool.quick.MTOOL;
import m.tool.stuff.KeyguardService;
import android.content.Context;
import android.content.Intent;

public class Keyguard implements ItemClass{

	public void activate(Context context) {
		set(STATUS, STATUS_ENABLED, context);
	}
	public void deactivate(Context context) {
		set(STATUS, STATUS_DISABLED, context);
	}
	
	public int get(int what, Context context) {
		if(what == STATUS) {
			return MTOOL.keyguardOff ? STATUS_DISABLED : STATUS_ENABLED;
		} return NOTHING;
	}
	
	public boolean is(int what, Context context) {
		if(what == STATUS)
			return (get(what, context) == STATUS_ENABLED);
		return false;
	}
	
	public void set(int what, int value, Context context) {
		if(value != NOTHING)
			if(what == STATUS) {
				if(value == STATUS_DISABLED)
					context.startService(new Intent(context, KeyguardService.class));
				if(value == STATUS_ENABLED)
					context.stopService(new Intent(context, KeyguardService.class));
			}
	}
	
	public boolean gotStatus() {
		return true;
	}
	public boolean opensDialog(String option) {
		return false;
	}
}