package m.tool.settings;

import m.tool.MTOOL;
import m.tool.R;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;

public class statussc extends Activity {

	NotificationManager mNotificationManager;
	SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if(prefs.getBoolean("statussc_pref", false)) {
	        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
	                new Intent(this, MTOOL.class), PendingIntent.FLAG_UPDATE_CURRENT);
	        Notification notif = new Notification();
	        notif.contentIntent = contentIntent;
	        CharSequence text = getText(R.string.app_name);
	        notif.tickerText = text;
	        notif.icon = R.drawable.icon;
	        notif.flags = Notification.FLAG_NO_CLEAR;
	        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.statussc);
	        notif.contentView = contentView;
	        mNotificationManager.notify(R.layout.main, notif);
        }
        else
        	mNotificationManager.cancel(R.layout.main);
        finish();
    }
}