package m.tool.item;

import java.lang.reflect.Method;

import m.tool.pro.MTOOL;
import m.tool.pro.R;
import m.tool.stuff.Item;
import android.annotation.TargetApi;
import android.content.Context;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.os.Build;
import android.view.View;

public class NFC implements ItemClass{

	public void layout(int feld_nummer, int breite, int hoehe, int seite, String option) {
		for(int i = 0; i < ids.size(); i++)
			if(ids.get(i)[0].equals(MTOOL.seiten+1))
				ids.clear();
		MTOOL.page[seite-1].nfc[feld_nummer].build(R.drawable.nfc, R.drawable.nfc_off, R.drawable.nfc_light, R.drawable.nfc_off_light, R.string.nfc,
				false, true, false, null, null, null, null, "", 0, null);
		if(!ids.contains(new Integer[]{seite, feld_nummer})) {
			ids.add(new Integer[]{seite, feld_nummer});
			ids_option.add(option);
		}
		MTOOL.page[seite-1].nfc[feld_nummer].setVisibility(View.VISIBLE);
		update(seite, feld_nummer, option);
	}

	public Item getLayout(Context context, String option) {
		Item item = new Item(context);
		item.build(R.drawable.nfc, R.drawable.nfc_off, R.drawable.nfc_light, R.drawable.nfc_off_light, R.string.nfc,
				false, true, false, null, null, null, null, "", 0, null);
		return item;
	}
	
	public void update() {
		for(int i = 0; i < ids.size(); i++)
			update(ids.get(i)[0], ids.get(i)[1], ids_option.get(i));
	}
	
	public void update(int seite, int feld_nummer, String option) {
		if(is(STATUS))
			MTOOL.page[seite-1].nfc[feld_nummer].activate();
		else
			MTOOL.page[seite-1].nfc[feld_nummer].deactivate();
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
	
	@TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1)
	public int get(int what) {
		if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			if(what == STATUS) {
				NfcAdapter na = NfcAdapter.getDefaultAdapter(MTOOL.context);
				if(na != null)
					return na.isEnabled() ? STATUS_ENABLED : STATUS_DISABLED;
			} 
		} return NOTHING;
	}
	public boolean is(int what) {
		if(what == STATUS)
			return (get(what) == STATUS_ENABLED);
		return false;
	}
	@TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1)
	public void set(int what, int value) {
		if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			if(what == STATUS) {
				final NfcAdapter na = NfcAdapter.getDefaultAdapter(MTOOL.context);
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
			NfcManager manager = (NfcManager)context.getSystemService(Context.NFC_SERVICE);
			NfcAdapter adapter = manager.getDefaultAdapter();
			if(adapter != null)
				return true;
		} return false;
	}
}