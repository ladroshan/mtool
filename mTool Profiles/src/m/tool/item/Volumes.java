package m.tool.item;

import android.content.Context;
import android.media.AudioManager;

public class Volumes implements ItemClass{

	public static final int NOTHING = 9;
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
	
	public void activate(Context context) {}
	public void deactivate(Context context) {}
	
	public int get(int what, Context context) {
		AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
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
	
	public boolean is(int what, Context context) {
		if(what == TONE)
			if(get(MODE, context) == MODE_NORMAL)
				return true;
		if(what == SILENT || what == TOGGLE_SILENT)
			if(get(MODE, context) == MODE_SILENT)
				return true;
		if(what == VIBRATION || what == TOGGLE_VIBRATION)
			if(get(MODE, context) == MODE_VIBRATE)
				return true;
		if(what == MODE_NORMAL || what == MODE_VIBRATE || what == MODE_SILENT)
			if(get(MODE, context) == what)
				return true;
		return false;
	}
	
	public void set(int what, int value, Context context) {
		if(value != NOTHING) {
			AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
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
				if(get(MODE, context) == MODE_SILENT)
					set(MODE, MODE_NORMAL, context);
				else set(MODE, MODE_SILENT, context);
			} if(what == TOGGLE_VIBRATION) {
				if(get(MODE, context) == MODE_VIBRATE)
					set(MODE, MODE_NORMAL, context);
				else set(MODE, MODE_VIBRATE, context);
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