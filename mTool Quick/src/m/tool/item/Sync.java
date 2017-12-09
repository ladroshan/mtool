package m.tool.item;

import android.content.ContentResolver;
import android.content.Context;

public class Sync implements ItemClass{

	public void activate(Context context) {
		set(STATUS, STATUS_ENABLED, context);
	}
	public void deactivate(Context context) {
		set(STATUS, STATUS_DISABLED,  context);
	}
	
	public int get(int what, Context context) {
		if(what == STATUS) {
			if(ContentResolver.getMasterSyncAutomatically())
				return STATUS_ENABLED;
			return STATUS_DISABLED;
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
				ContentResolver.setMasterSyncAutomatically(value == STATUS_ENABLED);
			}
	}
	
	public boolean gotStatus() {
		return true;
	}
	public boolean opensDialog(String option) {
		return false;
	}
}