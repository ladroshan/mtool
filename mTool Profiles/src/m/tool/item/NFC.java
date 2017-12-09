package m.tool.item;

import java.lang.reflect.Method;

import android.annotation.TargetApi;
import android.content.Context;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.os.Build;

public class NFC implements ItemClass{

	public void activate(Context context) {
		set(STATUS, STATUS_ENABLED, context);
	}
	public void deactivate(Context context) {
		set(STATUS, STATUS_DISABLED, context);
	}
	
	@TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1)
	public int get(int what, Context context) {
		if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			if(what == STATUS) {
				NfcAdapter na = NfcAdapter.getDefaultAdapter(context);
				if(na != null)
					return na.isEnabled() ? STATUS_ENABLED : STATUS_DISABLED;
			} 
		} return NOTHING;
	}
	public boolean is(int what, Context context) {
		if(what == STATUS)
			return (get(what, context) == STATUS_ENABLED);
		return false;
	}
	@TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1)
	public void set(int what, int value, Context context) {
		if(value != NOTHING)
			if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
				if(what == STATUS) {
					final NfcAdapter na = NfcAdapter.getDefaultAdapter(context);
					if(na != null) {
			            Class<?> NfcManagerClass;
			            Method setNfcEnabled, setNfcDisabled;
			            if (value == STATUS_ENABLED) {
			                try {
			                    NfcManagerClass = Class.forName(na.getClass().getName());
			                    setNfcEnabled   = NfcManagerClass.getDeclaredMethod("enable");
			                    setNfcEnabled.setAccessible(true);
			                } catch (ClassNotFoundException e) {
			                } catch (NoSuchMethodException e) {
			                } catch (IllegalArgumentException e) {
			                }
			            } else {
			                try {
			                    NfcManagerClass = Class.forName(na.getClass().getName());
			                    setNfcDisabled  = NfcManagerClass.getDeclaredMethod("disable");
			                    setNfcDisabled.setAccessible(true);
			                } catch (ClassNotFoundException e) {
			                } catch (NoSuchMethodException e) {
			                } catch (IllegalArgumentException e) {
			                }
			            }
					}
				}
			}
	}

	public boolean gotStatus() {
		return true;
	}
	public boolean opensDialog(String option) {
		return false;
	}
	
	@TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1)
	public boolean is_nfc(Context context) {
		if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD_MR1) {
			NfcManager manager = (NfcManager) context.getSystemService(Context.NFC_SERVICE);
			NfcAdapter adapter = manager.getDefaultAdapter();
			if(adapter != null)
				return true;
		} return false;
	}
}