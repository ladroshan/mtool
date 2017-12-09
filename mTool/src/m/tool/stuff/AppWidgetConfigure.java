package m.tool.stuff;

import java.util.ArrayList;
import java.util.List;

import m.tool.AppWidget;
import m.tool.MTOOL;
import m.tool.Profiles;
import m.tool.R;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AppWidgetConfigure extends Activity{
	private int widgetID;

	TextView awctop;
	Button bnext;
	Button bback;
	int seite;

	String source;
	
	boolean ok = true;
	
	int[] opitems = {6};
	
	int it = 0;
	String op = "";
	
	LinearLayout aiitem;
	RelativeLayout awcoption;
	
	List<ResolveInfo> packs;
	ListView lv;
	
	String options = "";
	
	int itemcount = 15;
	
	aiitem[] awci = new aiitem[itemcount];
	
	List<RadioButton> radioButtons = new ArrayList<RadioButton>();
	
	int schritt = 0;
	
	ArrayList<String> DATA = new ArrayList<String>();
	ArrayList<Drawable> IMAGE_DATA = new ArrayList<Drawable>();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		widgetID = AppWidgetManager.INVALID_APPWIDGET_ID;
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		if (extras != null) {
			widgetID = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
					AppWidgetManager.INVALID_APPWIDGET_ID);
		}
		if (widgetID == AppWidgetManager.INVALID_APPWIDGET_ID)
			finish();
		setResult(RESULT_CANCELED);
		DatabaseHandler db = new DatabaseHandler(getApplicationContext());
		if(db.getAllAppwidgetEntrys().size() >= 4) {
			db.close();
			Toast.makeText(getApplicationContext(), R.string.aw_notpro, Toast.LENGTH_LONG).show();
			finish();
		} db.close();
		setContentView(R.layout.widget_config);
		createComponents();
		main();
	}
	
	private class adapter extends BaseAdapter implements ListAdapter{
        private LayoutInflater mInflater;

        public adapter(Context context) {
            mInflater = LayoutInflater.from(context);
        }

        public int getCount() {
            return DATA.size();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.list_item, null);
                holder = new ViewHolder();
                holder.text = (TextView) convertView.findViewById(R.id.text);
                holder.icon = (ImageView) convertView.findViewById(R.id.icon);
                convertView.setTag(holder);
            } else
                holder = (ViewHolder) convertView.getTag();
            holder.text.setText(DATA.get(position));
            holder.icon.setImageDrawable(IMAGE_DATA.get(position));
            return convertView;
        }

        class ViewHolder {
            TextView text;
            ImageView icon;
        }
    }
	
	public void createComponents() {
		awctop = (TextView)findViewById(R.id.awctop);
		aiitem = (LinearLayout)findViewById(R.id.aiitem);
		awcoption = (RelativeLayout)findViewById(R.id.awcoption);
		lv = (ListView)findViewById(R.id.awclv);
		bnext = (Button)findViewById(R.id.awcnext);
		bnext.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				next();
			}
		});
		bnext.setEnabled(false);
		bback = (Button)findViewById(R.id.awcback);
		bback.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				back();
			}
		});
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		boolean nfc = false;
		nfc = MTOOL.nfc.is_nfc();
		awci[0] = new aiitem(this, 20, getResources().getString(R.string.quick), getResources().getDrawable(R.drawable.quick), metrics, false);
		awci[1] = new aiitem(this, 6, getResources().getString(R.string.profiles), getResources().getDrawable(R.drawable.profiles), metrics, false);
		awci[2] = new aiitem(this, 4, getResources().getString(R.string.wifi), getResources().getDrawable(R.drawable.wifi), metrics, false);
		awci[3] = new aiitem(this, 5, getResources().getString(R.string.bluetooth), getResources().getDrawable(R.drawable.bluetooth), metrics, false);
		awci[4] = new aiitem(this, 7, getResources().getString(R.string.data), getResources().getDrawable(R.drawable.data), metrics, false);
		awci[5] = new aiitem(this, 8, getResources().getString(R.string.timeout), getResources().getDrawable(R.drawable.timeout), metrics, false);
		awci[6] = new aiitem(this, 9, getResources().getString(R.string.brightness), getResources().getDrawable(R.drawable.brightness), metrics, false);
		awci[7] = new aiitem(this, 10, getResources().getString(R.string.rotation), getResources().getDrawable(R.drawable.rotation), metrics, false);
		awci[8] = new aiitem(this, 11, getResources().getString(R.string.volume), getResources().getDrawable(R.drawable.volume), metrics, false);
		awci[9] = new aiitem(this, 12, getResources().getString(R.string.gps), getResources().getDrawable(R.drawable.gps), metrics, false);
		awci[10] = new aiitem(this, 13, getResources().getString(R.string.nfc), getResources().getDrawable(R.drawable.nfc), metrics, false);
		if(!nfc)
			awci[10].setVisibility(View.GONE);
		awci[11] = new aiitem(this, 14, getResources().getString(R.string.keyguard), getResources().getDrawable(R.drawable.keyguard), metrics, false);
		awci[12] = new aiitem(this, 15, getResources().getString(R.string.sync), getResources().getDrawable(R.drawable.sync), metrics, false);
		awci[13] = new aiitem(this, 16, getResources().getString(R.string.hotspot), getResources().getDrawable(R.drawable.hotspot), metrics, false);
		awci[14] = new aiitem(this, 19, getResources().getString(R.string.torch), getResources().getDrawable(R.drawable.torch), metrics, true);
		for(int i = 0; i < awci.length; i++)
			aiitem.addView(awci[i]);
		for(int i = 0; i < itemcount; i++) {
			awci[i].setOnClickListener(rbclick);
			awci[i].rb.setOnClickListener(rbclickrb);
			radioButtons.add(awci[i].rb);
		}
		for (RadioButton button : radioButtons){
		    button.setOnCheckedChangeListener(new OnCheckedChangeListener(){
		        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		            if (isChecked) processRadioButtonClick(buttonView);
		        }   
		    });
		}
	}
	
	public void main() {
		if(schritt == 0) {
			awctop.setText(R.string.item);
			findViewById(R.id.awcsv).setVisibility(View.VISIBLE);
			awcoption.setVisibility(View.INVISIBLE);
			aiitem.setVisibility(View.VISIBLE);
			bnext.setText(R.string.next);
			bback.setText(android.R.string.cancel);
		}
		if(schritt == 1) {
			awctop.setText(R.string.choose);
			options = "";
			DATA.clear();
			IMAGE_DATA.clear();
			findViewById(R.id.awcsv).setVisibility(View.INVISIBLE);
			awcoption.setVisibility(View.VISIBLE);
			aiitem.setVisibility(View.INVISIBLE);
			bnext.setText(android.R.string.ok);
			bback.setText(R.string.back);
			if(it == 6)
				options = "profile";
			else
				okay();
			if(options.equals("profile")) {
				bnext.setEnabled(false);
				lv.setAdapter(new adapter(this));
				DATA.add(getResources().getString(R.string.all));
				IMAGE_DATA.add(null);
				MTOOL.db = new DatabaseHandler(this);
		        for(int i = 0; i < MTOOL.db.getAllProfileEntrys().size(); i++) {
		            DATA.add(MTOOL.db.getAllProfileEntrys().get(i).get_name());
		            IMAGE_DATA.add(getResources().getDrawable(Profiles.images[Integer.parseInt(MTOOL.db.getAllProfileEntrys().get(i).get_style().split(";")[1])]));
		        }
		        lv.setOnItemClickListener(new OnItemClickListener() {
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						if(arg2 > 0)
							op = Integer.toString(MTOOL.db.getAllProfileEntrys().get(arg2-1).getID());
						else
							op = "";
						bnext.setEnabled(true);
						next();
					}
				});
			}
		}
	}
	
	View.OnClickListener rbclick = new View.OnClickListener() {
		public void onClick(View v) {
			if(!bnext.isEnabled())
				bnext.setEnabled(true);
			it = ((aiitem)v).item;
			((aiitem)v).rb.setChecked(true);
			bnext.setText(android.R.string.ok);
			for(int i = 0; i < opitems.length; i++)
				if(opitems[i] == it)
					bnext.setText(R.string.next);
		}
	};
	
	View.OnClickListener rbclickrb = new View.OnClickListener() {
		public void onClick(View v) {
			if(!bnext.isEnabled())
				bnext.setEnabled(true);
			it = ((aiitem)((RadioButton)v).getParent()).item;
			((RadioButton)v).setChecked(true);
		}
	};
	
	private void processRadioButtonClick(CompoundButton buttonView) {
	    for (RadioButton button : radioButtons){
	        if (button != buttonView) button.setChecked(false);
	    }
	}
	
	public void next() {
		if(schritt < 1)	{
			schritt++;
			source = "next";
			main();
		} else {
			okay();
		}
	}
	
	public void back() {
		if(schritt > 0) {
			schritt--;
			source = "back";
			main();
		}
		else
			finish();
	}
	
	public void go() {
		if(source.equals("next"))
			next();
		else if(source.equals("back"))
			back();
	}
	
	public void okay() {
		DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        db.addEntry(new AppwidgetEntry(widgetID, it, op));
        db.close();
		final Context context = AppWidgetConfigure.this;
		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
		AppWidget.updateWidgetView(widgetID, context, appWidgetManager);
		Uri.Builder build = new Uri.Builder();
		build.appendPath("" + widgetID);
		Uri uri = build.build();
		Intent intentUpdate = new Intent(context, AppWidget.class);
		intentUpdate.setAction(AppWidget.JUST_UPDATE_THEAPPWIDGET);
		intentUpdate.setData(uri);
		AppWidget.addUri(widgetID, uri);
		intentUpdate.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetID);
		PendingIntent pendingIntentAlarm = PendingIntent.getBroadcast(AppWidgetConfigure.this, 0, intentUpdate, PendingIntent.FLAG_UPDATE_CURRENT);
		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (2000), (2000), pendingIntentAlarm);
		Intent resultValue = new Intent();
		resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetID);
		setResult(RESULT_OK, resultValue);
		finish();
	}
}