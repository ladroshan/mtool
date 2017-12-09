package m.tool.pro;

import java.util.HashMap;

import m.tool.item.ItemClass;
import m.tool.pro.R;
import m.tool.stuff.AppwidgetEntry;
import m.tool.stuff.DatabaseHandler;
import m.tool.stuff.Item;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;

@SuppressLint("UseSparseArrays")
public class AppWidget extends AppWidgetProvider {

	public static final String JUST_UPDATE_THEAPPWIDGET = "m.tool.stuff.JUST_UPDATE_THEAPPWIDGET";
	private static HashMap<Integer, Uri> uris = new HashMap<Integer, Uri>();
	
	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		if (action.equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE) || action.equals(JUST_UPDATE_THEAPPWIDGET)) {
			int widgetID = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
			if (widgetID != AppWidgetManager.INVALID_APPWIDGET_ID)
				this.onUpdate(context, AppWidgetManager.getInstance(context), new int[] {widgetID});
		} else {
			if (AppWidgetManager.ACTION_APPWIDGET_DELETED.equals(action)) {
				Bundle extras = intent.getExtras();
				if (extras != null && extras.containsKey(AppWidgetManager.EXTRA_APPWIDGET_ID)) {
					final int appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID);
					this.onDeleted(context, new int[] {appWidgetId});
				}
			} else if (AppWidgetManager.ACTION_APPWIDGET_ENABLED.equals(action))
				this.onEnabled(context);
			else if (AppWidgetManager.ACTION_APPWIDGET_DISABLED.equals(action))
				this.onDisabled(context);
		}
	}
	
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		for (int appWidgetId : appWidgetIds) {
			Intent intent = new Intent(context, MTOOL.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
			PendingIntent pi = PendingIntent.getActivity(context, appWidgetId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
			RemoteViews views = new RemoteViews(AppWidget.class.getPackage().getName(), R.layout.widget);
			views.setOnClickPendingIntent(R.id.awll0, pi);
			DatabaseHandler db = new DatabaseHandler(context);
			boolean ok = false;
			for (int o = 0; o < db.getAllAppwidgetEntrys().size(); o++)
				if (db.getAllAppwidgetEntrys().get(o).get_awid() == appWidgetId)
					ok = true;
			db.close();
			if (ok)
				updateWidgetView(appWidgetId, context, appWidgetManager);
			appWidgetManager.updateAppWidget(appWidgetId, views);
		}
	}
	
	public static void updateWidgetView(int currentWidgetId, Context context, AppWidgetManager appWidgetManager) {
		RemoteViews views = new RemoteViews(AppWidget.class.getPackage().getName(), R.layout.widget);
		DatabaseHandler db = new DatabaseHandler(context);
		AppwidgetEntry ae = new AppwidgetEntry(0, 0, "");
		for(int i = 0; i < db.getAllAppwidgetEntrys().size(); i++)
			if(db.getAllAppwidgetEntrys().get(i).get_awid() == currentWidgetId)
				ae = db.getAllAppwidgetEntrys().get(i);
		Item i = MTOOL.getItem(context, ae.get_item(), ae.get_option());
		int d = i.icon_dark_on_id;
		MTOOL.context = context;
		if(MTOOL.getItemClass(ae.get_item()).get(ItemClass.STATUS) == ItemClass.STATUS_DISABLED)
			d = i.icon_dark_off_id;
	    views.setImageViewResource(R.id.awicon, d);
	    if(MTOOL.getItemClass(ae.get_item()).get(ItemClass.STATUS) == ItemClass.STATUS_ENABLED) {
	    	views.setImageViewResource(R.id.awstatusc, R.drawable.status_c);
	    	views.setImageViewResource(R.id.awstatusl, R.drawable.status_l);
	    	views.setImageViewResource(R.id.awstatusr, R.drawable.status_r);
	    } else if(MTOOL.getItemClass(ae.get_item()).get(ItemClass.STATUS) == ItemClass.STATUS_LOADING) {
	    	views.setImageViewResource(R.id.awstatusc, R.drawable.status_loading_c);
	    	views.setImageViewResource(R.id.awstatusl, R.drawable.status_loading_l);
	    	views.setImageViewResource(R.id.awstatusr, R.drawable.status_loading_r);
	    } else {
	    	views.setImageViewResource(R.id.awstatusc, R.drawable.status_off_c);
	    	views.setImageViewResource(R.id.awstatusl, R.drawable.status_off_l);
	    	views.setImageViewResource(R.id.awstatusr, R.drawable.status_off_r);
	    }
	    if(!MTOOL.getItemClass(ae.get_item()).gotStatus())
	    	views.setViewVisibility(R.id.awstatus, View.GONE);
	    db.close();
	    appWidgetManager.updateAppWidget(currentWidgetId, views);
	}
	
	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		super.onDeleted(context, appWidgetIds);
		for (int appWidgetId : appWidgetIds) {
			cancelAlarmManager(context, appWidgetId);
			DatabaseHandler db = new DatabaseHandler(context);
			db.command("DELETE FROM APPWIDGET WHERE awid = " + Integer.toString(appWidgetId));
			db.close();
		}
	}
	
	protected void cancelAlarmManager(Context context, int widgetID) {
		AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		Intent intentUpdate = new Intent(context, AppWidget.class);
		intentUpdate.setAction(AppWidget.JUST_UPDATE_THEAPPWIDGET);
		intentUpdate.setData(uris.get(widgetID));
		intentUpdate.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetID);
		PendingIntent pendingIntentAlarm = PendingIntent.getBroadcast(context, 0, intentUpdate, PendingIntent.FLAG_UPDATE_CURRENT);
		alarm.cancel(pendingIntentAlarm);
		uris.remove(widgetID);
	}
	
	public static void addUri(int id, Uri uri) {
		uris.put(Integer.valueOf(id), uri);
	}
}