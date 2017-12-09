package m.tool.pro;

import java.util.TimerTask;

import m.tool.item.Brightness;
import m.tool.item.ItemClass;
import m.tool.item.NFC;
import m.tool.item.Timeout;
import m.tool.item.Volumes;
import m.tool.stuff.qitem;
import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;

public class Quick extends Activity{
	
	LinearLayout qitems;
	int itemcount = 13;
	qitem[] qi = new qitem[(itemcount+MTOOL.db.getAllProfileEntrys().size())];
	int before = 0;
	public TimerTask tt;
	boolean dont_react = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quick);
		((Button)findViewById(R.id.qback)).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
		qitems = (LinearLayout)findViewById(R.id.qitems);
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		boolean nfc = false;
		nfc = (new NFC()).is_nfc(getApplicationContext());
		for(int i = 0; i < MTOOL.db.getAllProfileEntrys().size(); i++) {
			qi[i] = new qitem(this, 6, Integer.toString(MTOOL.db.getAllProfileEntrys().get(i).getID()), MTOOL.db.getAllProfileEntrys().get(i).get_name(),
					getResources().getDrawable(Profiles.images[Integer.parseInt(MTOOL.db.getAllProfileEntrys().get(i).get_style().split(";")[1])]), metrics, false);
			qi[i].cb.setVisibility(View.GONE);
			before++;
		}
		MTOOL.db.close();
		qi[before+0] = new qitem(this, 4, "-1", getResources().getString(R.string.wifi), getResources().getDrawable(R.drawable.wifi), metrics, false);
		qi[before+1] = new qitem(this, 5, "-1", getResources().getString(R.string.bluetooth), getResources().getDrawable(R.drawable.bluetooth), metrics, false);
		qi[before+2] = new qitem(this, 7, "-1", getResources().getString(R.string.data), getResources().getDrawable(R.drawable.data), metrics, false);
		qi[before+3] = new qitem(this, 8, Integer.toString(Timeout.TOGGLE_ALWAYS_ON)
				, getResources().getString(R.string.timeout_alwayson), getResources().getDrawable(R.drawable.timeout), metrics, false);
		qi[before+4] = new qitem(this, 9, Integer.toString(Brightness.TOGGLE_AUTOMATIC)
				, getResources().getString(R.string.brightness_auto), getResources().getDrawable(R.drawable.brightness_auto), metrics, false);
		qi[before+5] = new qitem(this, 10, "-1", getResources().getString(R.string.rotation), getResources().getDrawable(R.drawable.rotation), metrics, false);
		qi[before+6] = new qitem(this, 11, Integer.toString(Volumes.TOGGLE_SILENT)
				, getResources().getString(R.string.volume_silent), getResources().getDrawable(R.drawable.volume_s), metrics, false);
		qi[before+7] = new qitem(this, 11, Integer.toString(Volumes.TOGGLE_VIBRATION)
				, getResources().getString(R.string.volume_vibration), getResources().getDrawable(R.drawable.volume_v), metrics, false);
		qi[before+8] = new qitem(this, 12, "-1", getResources().getString(R.string.gps), getResources().getDrawable(R.drawable.gps), metrics, false);
		qi[before+9] = new qitem(this, 13, "-1", getResources().getString(R.string.nfc), getResources().getDrawable(R.drawable.nfc), metrics, false);
		if(!nfc)
			qi[before+9].setVisibility(View.GONE);
		qi[before+10] = new qitem(this, 14, "-1", getResources().getString(R.string.keyguard), getResources().getDrawable(R.drawable.keyguard), metrics, false);
		qi[before+11] = new qitem(this, 15, "-1", getResources().getString(R.string.sync), getResources().getDrawable(R.drawable.sync), metrics, false);
		qi[before+12] = new qitem(this, 16, "-1", getResources().getString(R.string.hotspot), getResources().getDrawable(R.drawable.hotspot), metrics, true);
		for(int i = 0; i < qi.length; i++) {
			qitems.addView(qi[i]);
			qi[i].cb.setChecked(MTOOL.is(qi[i].item, Integer.parseInt(qi[i].option)));
			qi[i].setOnClickListener(click);
			qi[i].cb.setOnCheckedChangeListener(clickcb);
		}
	}
	
	public OnClickListener click = new OnClickListener() {
		public void onClick(View v) {
			((qitem)v).cb.toggle();
		}
	};
	
	public OnCheckedChangeListener clickcb = new OnCheckedChangeListener() {
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			if(!dont_react) {
				int item = ((qitem)buttonView.getParent()).item;
				int option = Integer.parseInt(((qitem)buttonView.getParent()).option);
				if(item == 11 && option == Volumes.TOGGLE_SILENT) {
					dont_react = true;
					qi[before+7].cb.setChecked(false);
				}
				if(item == 11 && option == Volumes.TOGGLE_VIBRATION) {
					dont_react = true;
					qi[before+6].cb.setChecked(false);
				}
				MTOOL.set(item, option, isChecked ? ItemClass.STATUS_ENABLED : ItemClass.STATUS_DISABLED);
				if(item == 6)
					finish();
			} dont_react = false;
		}
	};

	@Override
	protected void onPause() {
		super.onPause();
		if(MTOOL.finish_later) {
			MTOOL.close();
			MTOOL.mtool.finish();
		}
	}
}