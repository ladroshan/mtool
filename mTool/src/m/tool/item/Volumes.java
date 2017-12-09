package m.tool.item;

import m.tool.MTOOL;
import m.tool.R;
import m.tool.stuff.Item;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.view.View;

public class Volumes implements ItemClass{

	public static final int MODE_NORMAL = 10;
	public static final int MODE_VIBRATE = 11;
	public static final int MODE_SILENT = 12;
	public static final int TONE = 13;
	public static final int SILENT = 13;
	public static final int VIBRATION = 14;
	
	public static final int MODE = -10;
	public static final int VOLUME_RINGER = -11;
	public static final int VOLUME_MEDIA = -12;
	public static final int VOLUME_ALARM = -13;
	public static final int VOLUME_NOTIFICATION = -14;
	public static final int VOLUME_RINGER_MAX = -15;
	public static final int VOLUME_MEDIA_MAX = -16;
	public static final int VOLUME_ALARM_MAX = -17;
	public static final int VOLUME_NOTIFICATION_MAX = -18;
	public static final int TOGGLE_SILENT = -19;
	public static final int TOGGLE_VIBRATION = -20;
	
	public void layout(int feld_nummer, int breite, int hoehe, int seite, String option) {
		for(int i = 0; i < ids.size(); i++)
			if(ids.get(i)[0].equals(MTOOL.seiten+1))
				ids.clear();
		MTOOL.page[seite-1].volumes[feld_nummer].build(R.drawable.volume, 0, R.drawable.volume_light, 0,
				R.string.volume, false, true, false, null, null, null, null, "", 0, null);
		if(!ids.contains(new Integer[]{seite, feld_nummer})) {
			ids.add(new Integer[]{seite, feld_nummer});
			ids_option.add(option);
		}
		MTOOL.page[seite-1].volumes[feld_nummer].setVisibility(View.VISIBLE);
		update(seite, feld_nummer, option);
	}

	public Item getLayout(Context context, String option) {
		Item item = new Item(context);
		item.build(R.drawable.volume, 0, R.drawable.volume_light, 0,
				R.string.volume, false, true, false, null, null, null, null, "", 0, null);
		return item;
	}
	
	public void update() {
		for(int i = 0; i < ids.size(); i++)
			update(ids.get(i)[0], ids.get(i)[1], ids_option.get(i));
	}
	
	public void update(int seite, int feld_nummer, String option) {
		if(get(MODE) == MODE_NORMAL)
			MTOOL.page[seite-1].volumes[feld_nummer].setThings(R.drawable.volume, R.drawable.volume_light, null, "");
		else if(get(MODE) == MODE_VIBRATE)
			MTOOL.page[seite-1].volumes[feld_nummer].setThings(R.drawable.volume_v, R.drawable.volume_v_light, null, "");
		else if(get(MODE) == MODE_SILENT)
			MTOOL.page[seite-1].volumes[feld_nummer].setThings(R.drawable.volume_s, R.drawable.volume_s_light, null, "");
	}
	
	public void click(String option) {
		int selected_item = get(MODE)-10;
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MTOOL.mtool);
		alertDialogBuilder
			.setSingleChoiceItems(R.array.volumes_array, selected_item, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					set(MODE, which+10);
					dialog.cancel();
				}
			})
			.setOnCancelListener(new DialogInterface.OnCancelListener() {
				public void onCancel(DialogInterface dialog) {
					if(MTOOL.finish_later) {
						MTOOL.finish_later = false;
		    			MTOOL.close();
					}
				}
			})
			.show();
	}
	
	public void toggle() {}
	public void activate() {}
	public void deactivate() {}
	
	public int get(int what) {
		AudioManager am = (AudioManager) MTOOL.context.getSystemService(Context.AUDIO_SERVICE);
		if(what == MODE) {
			if(am.getRingerMode() == AudioManager.RINGER_MODE_NORMAL)
				return MODE_NORMAL;
			if(am.getRingerMode() == AudioManager.RINGER_MODE_VIBRATE)
				return MODE_VIBRATE;
			if(am.getRingerMode() == AudioManager.RINGER_MODE_SILENT)
				return MODE_SILENT;
		} if(what == VOLUME_RINGER)
			return am.getStreamVolume(AudioManager.STREAM_RING);
		if(what == VOLUME_MEDIA)
			return am.getStreamVolume(AudioManager.STREAM_MUSIC);
		if(what == VOLUME_ALARM)
			return am.getStreamVolume(AudioManager.STREAM_ALARM);
		if(what == VOLUME_NOTIFICATION)
			return am.getStreamVolume(AudioManager.STREAM_NOTIFICATION);
		if(what == VOLUME_RINGER_MAX)
			return am.getStreamMaxVolume(AudioManager.STREAM_RING);
		if(what == VOLUME_MEDIA_MAX)
			return am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		if(what == VOLUME_ALARM_MAX)
			return am.getStreamMaxVolume(AudioManager.STREAM_ALARM);
		if(what == VOLUME_NOTIFICATION_MAX)
			return am.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION);
		return NOTHING;
	}
	
	public boolean is(int what) {
		if(what == TONE)
			if(get(MODE) == MODE_NORMAL)
				return true;
		if(what == SILENT || what == TOGGLE_SILENT)
			if(get(MODE) == MODE_SILENT)
				return true;
		if(what == VIBRATION || what == TOGGLE_VIBRATION)
			if(get(MODE) == MODE_VIBRATE)
				return true;
		if(what == MODE_NORMAL || what == MODE_VIBRATE || what == MODE_SILENT)
			if(get(MODE) == what)
				return true;
		return false;
	}
	
	public void set(int what, int value) {
		AudioManager am = (AudioManager) MTOOL.context.getSystemService(Context.AUDIO_SERVICE);
		if(what == MODE) {
			if(value == MODE_NORMAL)
				am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
			if(value == MODE_VIBRATE)
				am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
			if(value == MODE_SILENT)
				am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
		} if(what == VOLUME_RINGER)
			am.setStreamVolume(AudioManager.STREAM_RING, value, 0);
		if(what == VOLUME_MEDIA)
			am.setStreamVolume(AudioManager.STREAM_MUSIC, value, 0);
		if(what == VOLUME_ALARM)
			am.setStreamVolume(AudioManager.STREAM_ALARM, value, 0);
		if(what == VOLUME_NOTIFICATION)
			am.setStreamVolume(AudioManager.STREAM_NOTIFICATION, value, 0);
		if(what == TOGGLE_SILENT) {
			if(get(MODE) == MODE_SILENT)
				set(MODE, MODE_NORMAL);
			else set(MODE, MODE_SILENT);
		} if(what == TOGGLE_VIBRATION) {
			if(get(MODE) == MODE_VIBRATE)
				set(MODE, MODE_NORMAL);
			else set(MODE, MODE_VIBRATE);
		}
	}
	
	public boolean gotStatus() {
		return false;
	}

	public boolean opensDialog(String option) {
		return true;
	}
}