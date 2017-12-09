package m.tool.quick;

import java.util.TimerTask;

import m.tool.item.Bluetooth;
import m.tool.item.Brightness;
import m.tool.item.Data;
import m.tool.item.GPS;
import m.tool.item.Hotspot;
import m.tool.item.ItemClass;
import m.tool.item.Keyguard;
import m.tool.item.NFC;
import m.tool.item.Rotation;
import m.tool.item.Sync;
import m.tool.item.Timeout;
import m.tool.item.Volumes;
import m.tool.item.Wifi;
import m.tool.stuff.qitem;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

public class MTOOL extends Activity{
	
	LinearLayout qitems;
	qitem[] qi = new qitem[13];
	int before = 0;
	public TimerTask tt;
	boolean dont_react = false;
	
	AdView adv_main_banner_1;
	AdView adv_main_banner_2;
	AlertDialog mainDlg;
	AlertDialog.Builder mainDlgBuilder;
	
	public static boolean keyguardOff = false;
	
	public static Wifi wifi;
	public static Bluetooth bluetooth;
	public static Data data;
	public static Timeout timeout;
	public static Brightness brightness;
	public static Rotation rotation;
	public static Volumes volumes;
	public static GPS gps;
	public static NFC nfc;
	public static Keyguard keyguard;
	public static Sync sync;
	public static Hotspot hotspot;
	public static ItemClass[] itemClass = new ItemClass[13];
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		adv_main_banner_1 = new AdView(this, AdSize.SMART_BANNER, "ca-app-pub-3475959862490054/1500726128");
    	RelativeLayout main_banner_1 = (RelativeLayout)findViewById(R.id.admb1rl);
    	main_banner_1.addView(adv_main_banner_1);
    	adv_main_banner_1.loadAd(new AdRequest());
    	adv_main_banner_2 = new AdView(this, AdSize.SMART_BANNER, "ca-app-pub-3475959862490054/2977459328");
    	RelativeLayout main_banner_2 = (RelativeLayout)findViewById(R.id.admb2rl);
    	main_banner_2.addView(adv_main_banner_2);
    	adv_main_banner_2.loadAd(new AdRequest());
//        View child = getLayoutInflater().inflate(R.layout.profiles, main, false);
//        main.addView(child);
        ScrollView scrollView = new ScrollView(getApplicationContext());
        scrollView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        qitems = new LinearLayout(getApplicationContext());
        qitems.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        qitems.setOrientation(LinearLayout.VERTICAL);
        scrollView.addView(qitems);
		mainDlgBuilder = new AlertDialog.Builder(this);
		mainDlg =
		mainDlgBuilder
			.setView(scrollView)
			.setNegativeButton(android.R.string.cancel,	new DialogInterface.OnClickListener() {
			    	public void onClick(DialogInterface dialog, int id) {
			    		dialog.cancel();
			    	}
				})
			.setOnCancelListener(new DialogInterface.OnCancelListener() {
				public void onCancel(DialogInterface dialog) {
					Toast.makeText(getApplicationContext(), R.string.exit_click, Toast.LENGTH_SHORT).show();
				}
			})
			.create();
		mainDlg.show();
    	wifi = new Wifi();					itemClass[0] = wifi;
    	bluetooth = new Bluetooth();		itemClass[1] = bluetooth;
    	data = new Data();         			itemClass[2] = data;      
    	timeout = new Timeout();       		itemClass[3] = timeout;   
    	brightness = new Brightness();    	itemClass[4] = brightness;
    	rotation = new Rotation();      	itemClass[5] = rotation;  
    	volumes = new Volumes();       		itemClass[6] = volumes;  
    	gps = new GPS();            		itemClass[7] = gps;      
    	nfc = new NFC();            		itemClass[8] = nfc;      
    	keyguard = new Keyguard();      	itemClass[9] = keyguard; 
    	sync = new Sync();          		itemClass[10] = sync;     
    	hotspot = new Hotspot();       		itemClass[11] = hotspot;
//		((Button)findViewById(R.id.qback)).setOnClickListener(new OnClickListener() {
//			public void onClick(View v) {
//				finish();
//			}
//		});
//		qitems = (LinearLayout)findViewById(R.id.qitems);
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		boolean nfc = false;
		nfc = (new NFC()).is_nfc(getApplicationContext());
		qi[0] = new qitem(this, 0, "-1", getResources().getString(R.string.wifi), getResources().getDrawable(R.drawable.wifi), metrics, false);
		qi[1] = new qitem(this, 1, "-1", getResources().getString(R.string.bluetooth), getResources().getDrawable(R.drawable.bluetooth), metrics, false);
		qi[2] = new qitem(this, 2, "-1", getResources().getString(R.string.data), getResources().getDrawable(R.drawable.data), metrics, false);
		qi[3] = new qitem(this, 3, Integer.toString(Timeout.TOGGLE_ALWAYS_ON)
				, getResources().getString(R.string.timeout_alwayson), getResources().getDrawable(R.drawable.timeout), metrics, false);
		qi[4] = new qitem(this, 4, Integer.toString(Brightness.TOGGLE_AUTOMATIC)
				, getResources().getString(R.string.brightness_auto), getResources().getDrawable(R.drawable.brightness_auto), metrics, false);
		qi[5] = new qitem(this, 5, "-1", getResources().getString(R.string.rotation), getResources().getDrawable(R.drawable.rotation), metrics, false);
		qi[6] = new qitem(this, 6, Integer.toString(Volumes.TOGGLE_SILENT)
				, getResources().getString(R.string.volume_silent), getResources().getDrawable(R.drawable.volume_s), metrics, false);
		qi[7] = new qitem(this, 6, Integer.toString(Volumes.TOGGLE_VIBRATION)
				, getResources().getString(R.string.volume_vibration), getResources().getDrawable(R.drawable.volume_v), metrics, false);
		qi[8] = new qitem(this, 7, "-1", getResources().getString(R.string.gps), getResources().getDrawable(R.drawable.gps), metrics, false);
		qi[9] = new qitem(this, 8, "-1", getResources().getString(R.string.nfc), getResources().getDrawable(R.drawable.nfc), metrics, false);
		if(!nfc)
			qi[9].setVisibility(View.GONE);
		qi[10] = new qitem(this, 9, "-1", getResources().getString(R.string.keyguard), getResources().getDrawable(R.drawable.keyguard), metrics, false);
		qi[11] = new qitem(this, 10, "-1", getResources().getString(R.string.sync), getResources().getDrawable(R.drawable.sync), metrics, false);
		qi[12] = new qitem(this, 11, "-1", getResources().getString(R.string.hotspot), getResources().getDrawable(R.drawable.hotspot), metrics, true);
		for(int i = 0; i < qi.length; i++) {
			qitems.addView(qi[i]);
			qi[i].cb.setChecked(is(qi[i].item, Integer.parseInt(qi[i].option)));
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
				if(item == 6 && option == Volumes.TOGGLE_SILENT) {
					dont_react = true;
					qi[7].cb.setChecked(false);
				}
				if(item == 6 && option == Volumes.TOGGLE_VIBRATION) {
					dont_react = true;
					qi[6].cb.setChecked(false);
				}
				set(item, option, isChecked ? ItemClass.STATUS_ENABLED : ItemClass.STATUS_DISABLED);
			} dont_react = false;
		}
	};

	public boolean is(int item, int what) {
		return itemClass[item].is(what, getApplicationContext());
	}
	
	public void set(int item, int what, int value) {
		itemClass[item].set(what, value, getApplicationContext());
	}
	
	public void close(View v) {
		finish();
	}
}