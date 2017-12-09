package m.tool;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

import m.tool.Settings.reset;
import m.tool.item.App;
import m.tool.item.Battery;
import m.tool.item.Bluetooth;
import m.tool.item.Brightness;
import m.tool.item.Clock;
import m.tool.item.Data;
import m.tool.item.GPS;
import m.tool.item.Hotspot;
import m.tool.item.Info;
import m.tool.item.ItemClass;
import m.tool.item.Keyguard;
import m.tool.item.NFC;
import m.tool.item.Profile;
import m.tool.item.QuickItem;
import m.tool.item.Rotation;
import m.tool.item.Sync;
import m.tool.item.Timeout;
import m.tool.item.Torch;
import m.tool.item.Volumes;
import m.tool.item.Wifi;
import m.tool.stuff.AppwidgetEntry;
import m.tool.stuff.DatabaseHandler;
import m.tool.stuff.Feld;
import m.tool.stuff.Item;
import m.tool.stuff.PagerAdapter;
import m.tool.stuff.ProfileEntry;
import m.tool.stuff.ScrollViewPager;
import m.tool.stuff.StuffEntry;
import m.tool.stuff.SwipeInterface;
import m.tool.stuff.ViewPager3D;
import m.tool.stuff.YouEntry;
import m.tool.stuff.touchController;
import m.tool.stuff.drag_and_drop.DeleteZone;
import m.tool.stuff.drag_and_drop.DragController;
import m.tool.stuff.drag_and_drop.DragLayer;
import m.tool.stuff.drag_and_drop.DragSource;
import m.tool.stuff.drag_and_drop.Feld_Target;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;
import android.widget.TextView;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class MTOOL extends FragmentActivity implements SwipeInterface, TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener{
	
	RelativeLayout rlall;
	ImageView ivcustombg;
	Button bsettings;
	Button bclose;
	public static Button badd;
	TextView tvseite;
	ImageView ivseitenl1, ivseitenl2, ivseitenl3, ivseitenl4, ivseitenl5, ivseitenl6, ivseitenl7, ivseitenl8, ivseitenl9, ivseitenl10;
	ImageView ivseitenr1, ivseitenr2, ivseitenr3, ivseitenr4, ivseitenr5, ivseitenr6, ivseitenr7, ivseitenr8, ivseitenr9, ivseitenr10;
	RelativeLayout rlmain;
	static TabHost tabHost;
	static Page page_view;
	static ViewPager3D viewPager;
	static ScrollViewPager scrollViewPager;
	List<Fragment> fragments;
	RelativeLayout rlbottom;
	RelativeLayout rltop;
	
	public static boolean seitenTabs = false;
	
	public static Context context;
	public static Activity mtool;
	
	public static Page[] page;
//	public static PageView[] page;
	
	static ArrayList<Integer> cur_items = new ArrayList<Integer>();
	
	public static Info info;
	public static App app;
	public static Clock clock;
	public static Wifi wifi;
	public static Bluetooth bluetooth;
	public static Profile profiles;
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
	public static Battery battery;
	public static Torch torch;
	public static QuickItem quick;
	public static ItemClass[] itemClass = new ItemClass[20];
	
	static DragController mDragController;
	DragLayer mDragLayer;
	public static DeleteZone mDeleteZone;
	
	public static SharedPreferences prefs;
	touchController touchables;
	
	public static boolean keyguardOff = false;

	public static boolean new_data = false;
	public static boolean you_changed = false;
	public static boolean finish_now = false;
	public static boolean finish_soon = false;
	public static boolean finish_later = false;
	
	public static String gridsize = "";
	public static int felder = 0;
	public static int w = 0;
	public static int h = 0;
	
	public static Timer t;
	public static TimerTask tt;
	
	public static int farbe = 0x00000000;
	public static ProfileEntry profile;
	
	public static StuffEntry stuffdb;
	public static YouEntry[] youdb;
	
	public static DatabaseHandler db;
	
	public static boolean created = false;
	public static boolean layouted = false;
	public static boolean real_app = true;
	
	public static int seite = 1;
	public static int seiten = 1;
	public static int startpg = 0;
	public static String wallpaper;
	public static int bgalpha;
	public static int italpha;
	public static int icsize;
	
	class TabFactory implements TabContentFactory {
		private final Context mContext;
	    public TabFactory(Context context) {
	        mContext = context;
	    }
	    public View createTabContent(String tag) {
	        View v = new View(mContext);
	        v.setMinimumWidth(0);
	        v.setMinimumHeight(0);
	        return v;
	    }
	}
	
	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH){
	    	if(ViewConfiguration.get(getApplicationContext()).hasPermanentMenuKey())
	    		inflater.inflate(R.menu.menu, menu);
	    }
	    else inflater.inflate(R.menu.menu, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
		    case R.id.menu_help:
	            help();
	            return true;
	        case R.id.menu_settings:
	            settings();
	            return true;
	        case R.id.menu_add:
	        	return add(this);
	        case R.id.menu_close:
	            close();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(null);
		Bundle extras = getIntent().getExtras();
		context = getApplicationContext();
        mtool = this;
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        db = new DatabaseHandler(getApplicationContext());
	    if(extras != null && extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID) != 0) {
	    	this.setVisible(false);
	    	int widgetID = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
			AppwidgetEntry ae = new AppwidgetEntry(0, 0, "");
			for(int i = 0; i < db.getAllAppwidgetEntrys().size(); i++)
				if(db.getAllAppwidgetEntrys().get(i).get_awid() == widgetID)
					ae = db.getAllAppwidgetEntrys().get(i);
			db.close();
			int item = ae.get_item();
			String option = ae.get_option();
			click(item, option);
			real_app = false;
			created = true;
			layouted = true;
			if(opensDialog(item, option))
				finish_later = true;
			else
				close();
	    } else/* if(extras.getBoolean("BALBLABLATHISISVERILICENSEDUNDSO133465556542", false))*/{
	    	setContentView(R.layout.main);
		    layouted = false;
		    finish_later = false;
		    if(db.getAllStuffEntrys().isEmpty())
	        	db.addEntry(new StuffEntry(1, 1, "", 255, 150, 10, 0, "NEW"));
	        if(db.getAllStuffEntrys().size() > 1)
	        	for(int i = 0; i < db.getAllStuffEntrys().size()-1; i++)
	        		db.deleteEntry(db.getStuffEntry(i));
	        if(db.getAllStuffEntrys().isEmpty())
	        	db.addEntry(new StuffEntry(1, 1, "", 255, 150, 10, 0, ""));
	        stuffdb = db.getAllStuffEntrys().get(0);
	        if(!stuffdb.get_do().equals("no_animations"))
	        	overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	        else
	        	stuffdb.set_do("");
	        db.updateEntry(stuffdb);
	        db.close();
	        createComponents_I();
	    }/* else 
	    	finish();*/
	}
	
	public void createComponents_I() {
    	touchables = new touchController(this);
    	rlall = (RelativeLayout)findViewById(R.id.rlall);
    	ivseitenl1 = (ImageView)findViewById(R.id.ivseitenl1);
    	ivseitenl2 = (ImageView)findViewById(R.id.ivseitenl2);
    	ivseitenl3 = (ImageView)findViewById(R.id.ivseitenl3);
    	ivseitenl4 = (ImageView)findViewById(R.id.ivseitenl4);
    	ivseitenl5 = (ImageView)findViewById(R.id.ivseitenl5);
    	ivseitenl6 = (ImageView)findViewById(R.id.ivseitenl6);
    	ivseitenl7 = (ImageView)findViewById(R.id.ivseitenl7);
    	ivseitenl8 = (ImageView)findViewById(R.id.ivseitenl8);
    	ivseitenl9 = (ImageView)findViewById(R.id.ivseitenl9);
    	ivseitenl10 = (ImageView)findViewById(R.id.ivseitenl10);
    	ivseitenr1 = (ImageView)findViewById(R.id.ivseitenr1);
    	ivseitenr2 = (ImageView)findViewById(R.id.ivseitenr2);
    	ivseitenr3 = (ImageView)findViewById(R.id.ivseitenr3);
    	ivseitenr4 = (ImageView)findViewById(R.id.ivseitenr4);
    	ivseitenr5 = (ImageView)findViewById(R.id.ivseitenr5);
    	ivseitenr6 = (ImageView)findViewById(R.id.ivseitenr6);
    	ivseitenr7 = (ImageView)findViewById(R.id.ivseitenr7);
    	ivseitenr8 = (ImageView)findViewById(R.id.ivseitenr8);
    	ivseitenr9 = (ImageView)findViewById(R.id.ivseitenr9);
    	ivseitenr10 = (ImageView)findViewById(R.id.ivseitenr10);
    	tvseite = (TextView)findViewById(R.id.tvseite);
    	rlmain = (RelativeLayout)findViewById(R.id.rlmain);
    	ivcustombg = (ImageView)findViewById(R.id.ivcustombg);
    	bclose = (Button)findViewById(R.id.bclose);
    	bclose.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				close();
			}
    	});
    	bclose.setBackgroundResource(R.drawable.bclosebg);
    	bsettings = (Button)findViewById(R.id.bsettings);
    	bsettings.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				settings();
			}
    	});
    	bsettings.setBackgroundResource(R.drawable.bsettingsbg);
    	badd = (Button)findViewById(R.id.badditem);
    	badd.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				add(mtool);
			}
    	});
    	badd.setBackgroundResource(R.drawable.badditembg);
    	rlbottom = (RelativeLayout)findViewById(R.id.rlbottom);
    	rlbottom.setOnTouchListener(touchables);
    	rltop = (RelativeLayout)findViewById(R.id.rltop);
    	rltop.setOnTouchListener(touchables);
    	AdView adv_main_banner_1 = new AdView(this, AdSize.SMART_BANNER, "ca-app-pub-3475959862490054/5768292123");
    	RelativeLayout main_banner_1 = (RelativeLayout)findViewById(R.id.main_banner_1);
    	main_banner_1.addView(adv_main_banner_1);
    	adv_main_banner_1.loadAd(new AdRequest());
		createComponents_II(true);
    }
	
	public static void createComponents_II(boolean goOn) {
		info = new Info();					itemClass[0] = info;
    	app = new App(); 					itemClass[1] = app;
    	clock = new Clock();				itemClass[2] = clock;
    	wifi = new Wifi();					itemClass[3] = wifi;
    	bluetooth = new Bluetooth();		itemClass[4] = bluetooth;
    	profiles = new Profile();			itemClass[5] = profiles;
    	data = new Data();         			itemClass[6] = data;      
    	timeout = new Timeout();       		itemClass[7] = timeout;   
    	brightness = new Brightness();    	itemClass[8] = brightness;
    	rotation = new Rotation();      	itemClass[9] = rotation;  
    	volumes = new Volumes();       		itemClass[10] = volumes;  
    	gps = new GPS();            		itemClass[11] = gps;      
    	nfc = new NFC();            		itemClass[12] = nfc;      
    	keyguard = new Keyguard();      	itemClass[13] = keyguard; 
    	sync = new Sync();          		itemClass[14] = sync;     
    	hotspot = new Hotspot();       		itemClass[15] = hotspot;
    	battery = new Battery();       		itemClass[17] = battery;
    	torch = new Torch();       			itemClass[18] = torch;
    	quick = new QuickItem();       		itemClass[19] = quick;
    	if(goOn)
    		((MTOOL) mtool).createComponents_III();
	}
	
	public void createComponents_III() {
        if(db.getAllEntrys().isEmpty() && stuffdb.get_do().equals("NEW")) {
        	db.addEntry(new YouEntry(1, 0, 1, 1, 4, "", ""));
        	db.addEntry(new YouEntry(1, 1, 1, 1, 5, "", ""));
        	db.addEntry(new YouEntry(1, 2, 1, 1, 7, "", ""));
        	db.addEntry(new YouEntry(1, 3, 1, 1, 6, "", ""));
        	db.addEntry(new YouEntry(1, 4, 1, 1, 14, "", ""));
        	db.addEntry(new YouEntry(1, 5, 2, 1, 3, "", ""));
        	db.addEntry(new YouEntry(1, 7, 1, 1, 10, "", ""));
        	db.addEntry(new YouEntry(1, 8, 1, 1, 8, "", ""));
        	db.addEntry(new YouEntry(1, 9, 1, 1, 9, "", ""));
        	db.addEntry(new YouEntry(1, 10, 1, 1, 11, "", ""));
        	db.addEntry(new YouEntry(1, 11, 1, 1, 15, "", ""));
        	db.addEntry(new YouEntry(1, 12, 1, 1, 18, "", ""));
        	db.addEntry(new YouEntry(1, 13, 1, 1, 12, "", ""));
        	db.addEntry(new YouEntry(1, 14, 1, 1, 16, "", ""));
        	db.addEntry(new YouEntry(1, 15, 1, 1, 19, "", ""));
        	stuffdb.set_do("");
        	db.updateEntry(stuffdb);
        }
        if(db.getAllProfileEntrys().isEmpty())
	        db.addEntry(new ProfileEntry(getResources().getString(R.string.plane), "0;25",
	        		  Integer.toString(R.string.wifi) + ":1;" + Integer.toString(R.string.bluetooth) + ":1;" + Integer.toString(R.string.data) + ":1;" + 
	        		  Integer.toString(R.string.timeout) + ":0;" + Integer.toString(R.string.brightness) + ":0;" + Integer.toString(R.string.rotation) + ":0;" +
	        		  Integer.toString(R.string.volume) + ":0:0:0:0:0;" +Integer.toString(R.string.gps) + ":1;" + Integer.toString(R.string.nfc) + ":1;" + 
	        		  Integer.toString(R.string.keyguard) + ":0;" + Integer.toString(R.string.sync) + ":1;" + Integer.toString(R.string.hotspot) + ":1;" + "0;"));
        stuffdb = db.getAllStuffEntrys().get(0);
        seiten = stuffdb.get_pages();
        seite = stuffdb.get_current_page();
        wallpaper = stuffdb.get_wallpaper();
        bgalpha = stuffdb.get_bgalpha();
        italpha = stuffdb.get_italpha();
        icsize = stuffdb.get_icsize();
        startpg = stuffdb.get_startpg();
        youdb = new YouEntry[db.getAllEntrys().size()];
        for(int i = 0; i < db.getAllEntrys().size(); i++) {
        	youdb[i] = db.getAllEntrys().get(i);
        	db.command("UPDATE YOU SET id = " + Integer.toString(i+1) + " WHERE id = " + Integer.toString(youdb[i].getID()));
        	youdb[i].setID(i+1);
        }
        db.close();
        if(!gridsize.equals("") && !gridsize.equals(prefs.getString("gridsize_pref", "4x4"))) {
        	reset.you();
        	restart();
        }
        gridsize = prefs.getString("gridsize_pref", "4x4");
        if(gridsize.equals("3x3")) {w = 3; h = 3;}
        if(gridsize.equals("3x4")) {w = 3; h = 4;}
        if(gridsize.equals("4x4")) {w = 4; h = 4;}
        if(gridsize.equals("4x5")) {w = 4; h = 5;}
        if(gridsize.equals("5x5")) {w = 5; h = 5;}
        felder = w*h;
        if(layouted) {
        	w = rlmain.getWidth()/w;
	    	h = rlmain.getHeight()/h;
        }
		if(prefs.getBoolean("defaultbg_pref", true)) {
	    	ivcustombg.setVisibility(View.VISIBLE);
	    	ivcustombg.setImageDrawable(getWallpaper());
	    }
	    if(prefs.getBoolean("custombg_pref", false) && !wallpaper.equals("null") && !wallpaper.equals("")){
	        ivcustombg.setVisibility(View.VISIBLE);
	        BitmapFactory.Options bmo = new BitmapFactory.Options();
	        bmo.inJustDecodeBounds = true;
	        BitmapFactory.decodeFile(wallpaper, bmo);
	        int iss = 1;
	        if(bmo.outHeight > 1000 || bmo.outWidth > 1000)
	        {
	        	if(bmo.outHeight >= bmo.outWidth)
	        		iss = bmo.outHeight/1000;
	        	if(bmo.outHeight < bmo.outWidth)
	        		iss = bmo.outWidth/1000;
	        }
	        BitmapFactory.Options bo = new BitmapFactory.Options();
	        bo.inSampleSize = iss;
	        Bitmap b = BitmapFactory.decodeFile(wallpaper, bo);
	        Display display = getWindowManager().getDefaultDisplay(); 
	        int height = display.getHeight();
	        if(b != null) {
	        	Drawable d = new BitmapDrawable(Bitmap.createScaledBitmap(b, (b.getWidth())*((height*100)/(b.getHeight()))/100, height, true));
	        	ivcustombg.setImageDrawable(d);
	        }
	        else {
	        	ivcustombg.setImageDrawable(getWallpaper());
	        	Toast.makeText(getApplicationContext(), R.string.wallpaper_404, Toast.LENGTH_SHORT).show();
	        }
		}
		ivcustombg.setAlpha(bgalpha);
		if(startpg != 0 && startpg <= seiten && !created){
			seite = startpg;
			tabHost.setCurrentTab(seite);
			viewPager.setCurrentItem(seite);
		}
		if(layouted)
			for(int i = 0; i < page.length; i++)
				if(page[i].created)
					page[i].style_update();
		created = true;
		timer();
		update_page_view();
    }
    
    @Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if((new_data || you_changed) && !layouted) {
			new_data = false;
			you_changed = false;
		}
		else if(!layouted && created) {
			rlmain.getLayoutParams().width = rlmain.getWidth()/w*w;
			rlmain.getLayoutParams().height = rlmain.getHeight()/h*h;
			w = rlmain.getWidth()/w;
	    	h = rlmain.getHeight()/h;
	    	layouted = true;
	    	createComponents_IV();
		}
	}

    public void createComponents_IV() {
        mDragController = new DragController(this);
	    mDragLayer = (DragLayer)findViewById(R.id.drag_layer);
	    mDragLayer.setDragController(mDragController);
	    mDragController.setDragListener(mDragLayer);
	    mDeleteZone = (DeleteZone)findViewById(R.id.delete_zone);
	    if (mDeleteZone != null) mDeleteZone.setVisibility(View.INVISIBLE);
	    tabHost = (TabHost)findViewById(android.R.id.tabhost);
	    page = new Page[seiten];
//	    page = new PageView[seiten];
//	    for(int i = 0; i < seiten; i++)
//    		page[i] = new PageView(context, i+1);
	    if(seiten > 1) {
	    	tabHost.setup();
	    	for(int i = 0; i < seiten; i++) {
	    		TabHost.TabSpec tabSpec =  tabHost.newTabSpec("Tab" + Integer.toString(i)).setIndicator("Tab " + Integer.toString(i));
	    		tabSpec.setContent(new TabFactory(this));
	            tabHost.addTab(tabSpec);
	    	}
	    	for(int i = 0; i < seiten; i++)
	    		page[i] = new Page(i+1);
	    	fragments = new Vector<Fragment>();
	    	for(int i = 0; i < seiten; i++)
	       		fragments.add(i, page[i]);
	    	tabHost.setCurrentTab(seite-1);
	    	tabHost.setOnTabChangedListener(this);
			viewPager = (ViewPager3D)findViewById(R.id.viewpager);
			viewPager.setOffscreenPageLimit(seiten-1);
			viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager(), fragments));
			viewPager.setCurrentItem(seite-1);
			viewPager.setOnPageChangeListener(this);
			seitenTabs = true;
	    } else {
	    	page_view = new Page(seite);
	    	page[0] = page_view;
	    	FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(rlmain.getId(), page_view).commit();
            tabHost.setVisibility(View.GONE);
	    	seitenTabs = false;
	    }
//	    ArrayList<PageView> itemsfsvp = new ArrayList<PageView>();
//	    for(int i = 0; i < seiten; i++)
//	    	itemsfsvp.add(i, page[i]);
//	    scrollViewPager = (ScrollViewPager)findViewById(R.id.scrollViewPager);
//	    scrollViewPager.setFeatureItems(itemsfsvp);
    	if(prefs.getBoolean("autobr_pref", false)) {
    		android.provider.Settings.System.putInt(getContentResolver(),
    				android.provider.Settings.System.SCREEN_BRIGHTNESS_MODE,
    				android.provider.Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
    		WindowManager.LayoutParams lp = getWindow().getAttributes();
    		getWindow().setAttributes(lp);
    	}
    	if(seitenTabs)
    		createCompontents_V();
    }
    
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
	public void createCompontents_V() {
    	if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD)
    		viewPager.setOverScrollMode(ViewPager.OVER_SCROLL_NEVER);
    }

	public void update_page_view()
    {
		ivseitenl1.setVisibility(View.INVISIBLE);
		ivseitenl2.setVisibility(View.INVISIBLE);
		ivseitenl3.setVisibility(View.INVISIBLE);
		ivseitenl4.setVisibility(View.INVISIBLE);
		ivseitenl5.setVisibility(View.INVISIBLE);
		ivseitenl6.setVisibility(View.INVISIBLE);
		ivseitenl7.setVisibility(View.INVISIBLE);
		ivseitenl8.setVisibility(View.INVISIBLE);
		ivseitenl9.setVisibility(View.INVISIBLE);
		ivseitenl10.setVisibility(View.INVISIBLE);
		ivseitenr1.setVisibility(View.INVISIBLE);
		ivseitenr2.setVisibility(View.INVISIBLE);
		ivseitenr3.setVisibility(View.INVISIBLE);
		ivseitenr4.setVisibility(View.INVISIBLE);
		ivseitenr5.setVisibility(View.INVISIBLE);
		ivseitenr6.setVisibility(View.INVISIBLE);
		ivseitenr7.setVisibility(View.INVISIBLE);
		ivseitenr8.setVisibility(View.INVISIBLE);
		ivseitenr9.setVisibility(View.INVISIBLE);
		ivseitenr10.setVisibility(View.INVISIBLE);
		if(seite >= 2)
			ivseitenl1.setVisibility(View.VISIBLE);
		if(seite >= 3)
			ivseitenl2.setVisibility(View.VISIBLE);
		if(seite >= 4)
			ivseitenl3.setVisibility(View.VISIBLE);
		if(seite >= 5)
			ivseitenl4.setVisibility(View.VISIBLE);
		if(seite >= 6)
			ivseitenl5.setVisibility(View.VISIBLE);
		if(seite >= 7)
			ivseitenl6.setVisibility(View.VISIBLE);
		if(seite >= 8)
			ivseitenl7.setVisibility(View.VISIBLE);
		if(seite >= 9)
			ivseitenl8.setVisibility(View.VISIBLE);
		if(seite >= 10)
			ivseitenl9.setVisibility(View.VISIBLE);
		if(seite == 11) {
			ivseitenl10.setImageResource(R.drawable.i2);
			ivseitenl10.setVisibility(View.VISIBLE);
		}
		if(seite > 11) {
			ivseitenl10.setImageResource(R.drawable.pl);
			ivseitenl10.setVisibility(View.VISIBLE);
		}
		if(seiten - seite >= 1)
			ivseitenr1.setVisibility(View.VISIBLE);
		if(seiten - seite >= 2)
			ivseitenr2.setVisibility(View.VISIBLE);
		if(seiten - seite >= 3)
			ivseitenr3.setVisibility(View.VISIBLE);
		if(seiten - seite >= 4)
			ivseitenr4.setVisibility(View.VISIBLE);
		if(seiten - seite >= 5)
			ivseitenr5.setVisibility(View.VISIBLE);
		if(seiten - seite >= 6)
			ivseitenr6.setVisibility(View.VISIBLE);
		if(seiten - seite >= 7)
			ivseitenr7.setVisibility(View.VISIBLE);
		if(seiten - seite >= 8)
			ivseitenr8.setVisibility(View.VISIBLE);
		if(seiten - seite >= 9)
			ivseitenr9.setVisibility(View.VISIBLE);
		if(seiten - seite == 10) {
			ivseitenr10.setImageResource(R.drawable.i2);
			ivseitenr10.setVisibility(View.VISIBLE);
		}
		if(seiten - seite > 10) {
			ivseitenr10.setImageResource(R.drawable.pr);
			ivseitenr10.setVisibility(View.VISIBLE);
		}
		if(seiten < 100)
			tvseite.setText(Integer.toString(seite));
		else
			tvseite.setText("..");
    }
	
    static View.OnLongClickListener long_click_add = new View.OnLongClickListener() {
		public boolean onLongClick(View v) {
			return add(v.getContext());
		}
	};
	static View.OnLongClickListener long_click_drag = new View.OnLongClickListener() {
		public boolean onLongClick(View v) {
			return startDrag((Feld)v);
		}
	};
	static View.OnClickListener click = new View.OnClickListener() {
		public void onClick(View v) {
			click(((Feld)v).item, ((Feld)v).option);
		}
	};

	public void bottom2top(View v) {
		settings();
	}
	public void left2right(View v) {
		if(seitenTabs) {
			if(seite > 1)
				seite--;
			else if(seite == 1)
				seite = seiten;
			viewPager.setCurrentItem(seite-1);
			update_page_view();
		}
	}
	public void right2left(View v) {
		if(seitenTabs) {
			if(seite < seiten)
				seite++;
			else if(seite == seiten)
				seite = 1;
			viewPager.setCurrentItem(seite-1);
			update_page_view();
		}
	}
	public void top2bottom(View v) {
		close();
	}

	public void onTabChanged(String arg0) {
		int pos = tabHost.getCurrentTab();
		if(pos < seite-1)
				seite--;
		else if(pos > seite-1)
				seite++;
		update_page_view();
	}
	
	public void onPageScrolled(int position, float arg1, int arg2) {
		tabHost.setCurrentTab(position);
	}
	public void onPageScrollStateChanged(int state) {}
	public void onPageSelected(int position) {}
	
	public static boolean startDrag (View v) {
		if (mDeleteZone != null) mDeleteZone.setVisibility (View.VISIBLE);
		badd.setVisibility(View.INVISIBLE);
	    DragSource dragSource = (DragSource) v;
	    mDragController.startDrag(v, dragSource, dragSource, DragController.DRAG_ACTION_MOVE);
	    return true;
	}
	
	public static void scrollRight() {
		if(seitenTabs) {
			if(viewPager.getCurrentItem()+1 < seiten)
				viewPager.setCurrentItem(seite, true);
			seite = viewPager.getCurrentItem()+1;
		}
	}
	public static void scrollLeft() {
		if(seitenTabs) {
			if(viewPager.getCurrentItem()+1 > 1)
				viewPager.setCurrentItem(seite-2, true);
			seite = viewPager.getCurrentItem()+1;
		}
	}
	
	public void settings() {
		Intent i = new Intent(getApplicationContext(), Settings.class);
		startActivity(i);
    }
	
	public static boolean add(final Context c) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(c);
		alertDialogBuilder
			.setNegativeButton(android.R.string.cancel,	new DialogInterface.OnClickListener() {
		    	public void onClick(DialogInterface dialog, int id) {
		    		dialog.cancel();
		    	}
			})
			.setItems(R.array.add_array, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					if(which == 0) {
						Intent i = new Intent(c, Add.class);
						c.startActivity(i);
					}
					else if(which == 1) {
						if(seiten < 2) {
							seiten++;
							seite = seiten;
							stuffdb.set_pages(seiten);
							stuffdb.set_current_page(seite);
							db.updateEntry(stuffdb);
							db.close();
							restart();
						} else {
							Toast.makeText(context, R.string.pages_notpro, Toast.LENGTH_LONG).show();
						}
					}
					else if(which == 2) {
						if(seiten > 1) {
							for(int i = 0; i < youdb.length; i++)
								if(youdb[i].get_page() == seite)
									db.deleteEntry(youdb[i]);
							for(int i = 0; i < youdb.length; i++)
								if(youdb[i].get_page() > seite) {
									youdb[i].set_page(youdb[i].get_page()-1);
									db.updateEntry(youdb[i]);
								}
							if(seite != 1)
								seite--;
							seiten--;
							stuffdb.set_pages(seiten);
							stuffdb.set_current_page(seite);
							db.updateEntry(stuffdb);
							db.close();
							restart();
						} else {
							for(int i = 0; i < youdb.length; i++)
								db.deleteEntry(youdb[i]);
							db.close();
							restart();
						}
					}
					else if(which == 3) {
						Intent i = new Intent(c, Profiles.class);
						c.startActivity(i);
					}
					else if(which == 4) {
						Intent i = new Intent(c, Quick.class);
						c.startActivity(i);
					}
				}
			}).show();
		return true;
	}
	
    public static void close() {
		mtool.finish();
    	mtool.overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
    }
    public static void restart() {
    	stuffdb.set_do("no_animations");
    	db.updateEntry(stuffdb);
    	db.close();
    	mtool.finish();
    	mtool.overridePendingTransition(0, 0);
    	mtool.startActivity(mtool.getIntent());
    	mtool.overridePendingTransition(0, 0);
    }
    @Override
	public void onBackPressed() {
		super.onBackPressed();
		close();
	}
    
    public void help() {
    	Intent appIntent = new Intent(Intent.ACTION_VIEW);
        appIntent.setData(Uri.parse("market://details?id=" + getPackageName()));
		startActivity(appIntent);
    }

    @Override
	protected void onResume() {
		super.onResume();
		if(finish_soon){
			finish_soon = false;
			close();
		}
		if(new_data || you_changed)
			restart();
		else if(!created) {
			createComponents_III();
			for(int i = 1; i < seiten+1; i++)
				update(i);
		}
	}
    
	@Override
	protected void onPause() {
		super.onPause();
		if(real_app) {
			created = false;
			if(t != null)
				t.cancel();
			if(tt != null)
				tt.cancel();
			t = null;
			tt = null;
			if(finish_now) {
				finish_now = false;
				close();
			}
			if(!new_data && !you_changed) {
				stuffdb.set_things(seite, seiten, wallpaper, bgalpha, italpha, icsize, startpg, stuffdb.get_do());
				db.updateEntry(stuffdb);
				db.close();
			}
			new_data = false;
		}
	}
	
	public static void timer() {
	    final Handler handler = new Handler();
	    t = null;
	    t = new Timer();
	    tt = null;
	    tt = new TimerTask() {
	    	public void run() {
	    		handler.post(new Runnable() {
	    			public void run() {
	    				update();
	    			}
	    		});
	    	}
	    };
	    t.schedule(tt, 0, 1500);
    }
	
	public static void gridlayout(RelativeLayout[] rlfeld, Feld[] feld, Feld_Target[] feld_target) {
		RelativeLayout.LayoutParams[] lp = new RelativeLayout.LayoutParams[felder*2];
		for(int i = 0, o = 0; i < felder*2; i += 2, o++) {
			lp[i] = new RelativeLayout.LayoutParams(w, h);
			rlfeld[o].setLayoutParams(lp[i]);
			lp[i+1] = new RelativeLayout.LayoutParams(w, h);
			feld_target[o].setLayoutParams(lp[i+1]);
		}
		for(int i = 0; i < 6; i++)
			lp[i].addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
		for(int i = 0; i < 2; i++)
			lp[i].addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
		for(int i = 2; i < 4; i++)
			lp[i].leftMargin = w;
		for(int i = 4; i < 6; i++)
			lp[i].leftMargin = w*2;
		if(felder == 9 || felder == 12) {
			for(int i = 6; i < 12; i++)
				lp[i].topMargin = h;
			for(int i = 6; i < 8; i++)
	    		lp[i].addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
			for(int i = 8; i < 10; i++)
				lp[i].leftMargin = w;
			for(int i = 10; i < 12; i++)
				lp[i].leftMargin = w*2;
			for(int i = 12; i < 18; i++)
				lp[i].topMargin = h*2;
			for(int i = 12; i < 14; i++)
	    		lp[i].addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
			for(int i = 14; i < 16; i++)
				lp[i].leftMargin = w;
			for(int i = 16; i < 18; i++)
				lp[i].leftMargin = w*2;
		}
		if(felder == 12) {
			for(int i = 18; i < 24; i++)
				lp[i].topMargin = h*3;
			for(int i = 18; i < 20; i++)
	    		lp[i].addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
			for(int i = 20; i < 22; i++)
				lp[i].leftMargin = w;
			for(int i = 22; i < 24; i++)
				lp[i].leftMargin = w*2;
		}
		if(felder == 16 || felder == 20) {
			for(int i = 6; i < 8; i++)
				lp[i].leftMargin = w*3;
			for(int i = 8; i < 16; i++)
				lp[i].topMargin = h;
			for(int i = 8; i < 10; i++)
	    		lp[i].addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
			for(int i = 10; i < 12; i++)
				lp[i].leftMargin = w;
			for(int i = 12; i < 14; i++)
				lp[i].leftMargin = w*2;
			for(int i = 14; i < 16; i++)
				lp[i].leftMargin = w*3;
			for(int i = 16; i < 24; i++)
				lp[i].topMargin = h*2;
			for(int i = 16; i < 18; i++)
	    		lp[i].addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
			for(int i = 18; i < 20; i++)
				lp[i].leftMargin = w;
			for(int i = 20; i < 22; i++)
				lp[i].leftMargin = w*2;
			for(int i = 22; i < 24; i++)
				lp[i].leftMargin = w*3;
			for(int i = 24; i < 32; i++)
				lp[i].topMargin = h*3;
			for(int i = 24; i < 26; i++)
	    		lp[i].addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
			for(int i = 26; i < 28; i++)
				lp[i].leftMargin = w;
			for(int i = 28; i < 30; i++)
				lp[i].leftMargin = w*2;
			for(int i = 30; i < 32; i++)
				lp[i].leftMargin = w*3;
		}
		if(felder == 20) {
			for(int i = 32; i < 40; i++)
				lp[i].topMargin = h*4;
			for(int i = 32; i < 34; i++)
	    		lp[i].addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
			for(int i = 34; i < 36; i++)
				lp[i].leftMargin = w;
			for(int i = 36; i < 38; i++)
				lp[i].leftMargin = w*2;
			for(int i = 38; i < 40; i++)
				lp[i].leftMargin = w*3;
		}
		if(felder == 25) {
			for(int i = 6; i < 8; i++)
				lp[i].leftMargin = w*3;
			for(int i = 8; i < 10; i++)
				lp[i].leftMargin = w*4;
			for(int i = 10; i < 20; i++)
				lp[i].topMargin = h;
			for(int i = 10; i < 12; i++)
	    		lp[i].addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
			for(int i = 12; i < 14; i++)
				lp[i].leftMargin = w;
			for(int i = 14; i < 16; i++)
				lp[i].leftMargin = w*2;
			for(int i = 16; i < 18; i++)
				lp[i].leftMargin = w*3;
			for(int i = 18; i < 20; i++)
				lp[i].leftMargin = w*4;
			for(int i = 20; i < 30; i++)
				lp[i].topMargin = h*2;
			for(int i = 20; i < 22; i++)
	    		lp[i].addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
			for(int i = 22; i < 24; i++)
				lp[i].leftMargin = w;
			for(int i = 24; i < 26; i++)
				lp[i].leftMargin = w*2;
			for(int i = 26; i < 28; i++)
				lp[i].leftMargin = w*3;
			for(int i = 28; i < 30; i++)
				lp[i].leftMargin = w*4;
			for(int i = 30; i < 40; i++)
				lp[i].topMargin = h*3;
			for(int i = 30; i < 32; i++)
	    		lp[i].addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
			for(int i = 32; i < 34; i++)
				lp[i].leftMargin = w;
			for(int i = 34; i < 36; i++)
				lp[i].leftMargin = w*2;
			for(int i = 36; i < 38; i++)
				lp[i].leftMargin = w*3;
			for(int i = 38; i < 40; i++)
				lp[i].leftMargin = w*4;
			for(int i = 40; i < 50; i++)
				lp[i].topMargin = h*4;
			for(int i = 40; i < 42; i++)
	    		lp[i].addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
			for(int i = 42; i < 44; i++)
				lp[i].leftMargin = w;
			for(int i = 44; i < 46; i++)
				lp[i].leftMargin = w*2;
			for(int i = 46; i < 48; i++)
				lp[i].leftMargin = w*3;
			for(int i = 48; i < 50; i++)
				lp[i].leftMargin = w*4;
		}
	}
	
	public static void update(int seite) {
		if(page != null && page[seite-1] != null && page[seite-1].created) {
	    	for(int i = 0; i < felder; i++) {
	    		page[seite-1].rlfeld[i].setVisibility(View.GONE);
	    		for(int o = 0; o < page[seite-1].allItems.length; o++)
	    			page[seite-1].allItems[o][i].setVisibility(View.GONE);
	    		page[seite-1].feld[i].mEmpty = true;
	    	}
	    	ArrayList<Integer> skipThis = new ArrayList<Integer>();
			for(int i = 0; i < youdb.length; i++) {
	 			if(youdb[i] != null && youdb[i].getID() != 0 && youdb[i].get_page() == seite) {
					int f = youdb[i].get_feld();
					if(!skipThis.contains(f)) {
						page[seite-1].feld[f].mEmpty = false;
			    		int fb = youdb[i].get_width();
			    		int fh = youdb[i].get_height();
			    		page[seite-1].rlfeld[f].setVisibility(View.VISIBLE);
			    		page[seite-1].rlfeld[f].getLayoutParams().width = w*fb;
			    		page[seite-1].rlfeld[f].getLayoutParams().height = h*fh;
			    		felder(f, youdb[i].get_item(), youdb[i].get_option(), fb, fh, youdb[i].get_page());
			    		if(felder == 9 || felder == 12) {
				    		if(fb >= 2)	skipThis.add(f+1);
				    		if(fb >= 3)	skipThis.add(f+2);
				    		if(fh >= 2)	skipThis.add(f+3);
				    		if(fb >= 2 && fh >= 2) skipThis.add(f+4);
				    		if(fb >= 3 && fh >= 2) skipThis.add(f+5);
				    		if(fh >= 3)	skipThis.add(f+6);
				    		if(fb >= 2 && fh >= 3) skipThis.add(f+7);
				    		if(fb >= 3 && fh >= 3) skipThis.add(f+8);
				    		if(fh == 4)	skipThis.add(f+9);
				    		if(fb >= 2 && fh == 4) skipThis.add(f+10);
				    		if(fb >= 3 && fh == 4) skipThis.add(f+11);
			    		} else if(felder == 16 || felder == 20) {
				    		if(fb >= 2)	skipThis.add(f+1);
				    		if(fb >= 3)	skipThis.add(f+2);
				    		if(fb == 4)	skipThis.add(f+3);
				    		if(fh >= 2)	skipThis.add(f+4);
				    		if(fb >= 2 && fh >= 2) skipThis.add(f+5);
				    		if(fb >= 3 && fh >= 2) skipThis.add(f+6);
				    		if(fb == 4 && fh >= 2) skipThis.add(f+7);
				    		if(fh >= 3)	skipThis.add(f+8);
				    		if(fb >= 2 && fh >= 3) skipThis.add(f+9);
				    		if(fb >= 3 && fh >= 3) skipThis.add(f+10);
				    		if(fb == 4 && fh >= 3) skipThis.add(f+11);
				    		if(fh == 4)	skipThis.add(f+12);
				    		if(fb >= 2 && fh == 4) skipThis.add(f+13);
				    		if(fb >= 3 && fh == 4) skipThis.add(f+14);
				    		if(fb == 4 && fh == 4) skipThis.add(f+15);
			    		} else if(felder == 25) {
				    		if(fb >= 2)	skipThis.add(f+1);
				    		if(fb >= 3)	skipThis.add(f+2);
				    		if(fb == 4)	skipThis.add(f+3);
				    		if(fh >= 2)	skipThis.add(f+5);
				    		if(fb >= 2 && fh >= 2) skipThis.add(f+6);
				    		if(fb >= 3 && fh >= 2) skipThis.add(f+7);
				    		if(fb == 4 && fh >= 2) skipThis.add(f+8);
				    		if(fh >= 3)	skipThis.add(f+10);
				    		if(fb >= 2 && fh >= 3) skipThis.add(f+11);
				    		if(fb >= 3 && fh >= 3) skipThis.add(f+12);
				    		if(fb == 4 && fh >= 3) skipThis.add(f+13);
				    		if(fh == 4)	skipThis.add(f+15);
				    		if(fb >= 2 && fh == 4) skipThis.add(f+16);
				    		if(fb >= 3 && fh == 4) skipThis.add(f+17);
				    		if(fb == 4 && fh == 4) skipThis.add(f+18);
			    		}
					}
				}
			}
			for(int i = 0; i < skipThis.size(); i++)
				page[seite-1].feld[skipThis.get(i)].mEmpty = false;
			skipThis.clear();
//			ArrayList<PageView> itemsfsvp = new ArrayList<PageView>();
//		    for(int i = 0; i < seiten; i++) {
//		    	itemsfsvp.add(i, page[i]);
//		    	if(itemsfsvp.get(i).getParent() != null)
//		    		((ViewGroup)itemsfsvp.get(i).getParent()).removeView(itemsfsvp.get(i));
//		    }
//		    scrollViewPager.removeAllViews();
//		    scrollViewPager.setFeatureItems(itemsfsvp);
		}
	}
	    
	public static void felder(int feld_nummer, int item, String option, int breite, int hoehe, int seite) {
    	if(!cur_items.contains(item))
    		cur_items.add(item);
		itemClass[item-1].layout(feld_nummer, breite, hoehe, seite, option);
	}
	
	public static void update() {
		for(int i = 0; i < itemClass.length; i++)
			if(cur_items.contains(i+1))
				itemClass[i].update();
    }
    
	public static int get(int item, int what) {
		createComponents_II(false);
		return itemClass[item-1].get(what);
	}
	
	public static boolean is(int item, int what) {
		createComponents_II(false);
		return itemClass[item-1].is(what);
	}
	
	public static void set(int item, int what, int value) {
		createComponents_II(false);
		itemClass[item-1].set(what, value);
	}
	
    public static void click(int item, String option) {
    	createComponents_II(false);
    	itemClass[item-1].click(option);
    }
    
    public static boolean opensDialog(int item, String option) {
    	createComponents_II(false);
   		return itemClass[item-1].opensDialog(option);
    }
    
    public static Item getItem(Context context, int item, String option) {
    	createComponents_II(false);
   		return itemClass[item-1].getLayout(context, option);
    }
    
    public static ItemClass getItemClass(int item) {
    	createComponents_II(false);
   		return itemClass[item-1];
    }
    
    public static void activate_profile(ProfileEntry pe) {
		profile = pe;
		if(profile.get_name().equals(context.getResources().getString(R.string.plane)) && 
				profile.get_inhalt().split(";")[profile.get_inhalt().split(";").length-1].equals("1")) {
			Intent i = new Intent();
			i.setAction(android.provider.Settings.ACTION_AIRPLANE_MODE_SETTINGS);
			mtool.startActivity(i);
		} else {
			bluetooth.set(ItemClass.STATUS, Integer.parseInt(pe.get_inhalt().split(";")[1].split(":")[1]));
			data.set(ItemClass.STATUS, Integer.parseInt(pe.get_inhalt().split(";")[2].split(":")[1]));
			timeout.set(Timeout.TIMEOUT, Integer.parseInt(pe.get_inhalt().split(";")[3].split(":")[1])+9);
			brightness.set(Brightness.BRIGHTNESS, Integer.parseInt(pe.get_inhalt().split(";")[4].split(":")[1])+9);
			rotation.set(ItemClass.STATUS, Integer.parseInt(pe.get_inhalt().split(";")[5].split(":")[1]));
			volumes.set(Volumes.MODE, Integer.parseInt(pe.get_inhalt().split(";")[6].split(":")[1])+9);
			gps.set(ItemClass.STATUS, Integer.parseInt(pe.get_inhalt().split(";")[7].split(":")[1]));
			nfc.set(ItemClass.STATUS, Integer.parseInt(pe.get_inhalt().split(";")[8].split(":")[1]));
			keyguard.set(ItemClass.STATUS, Integer.parseInt(pe.get_inhalt().split(";")[9].split(":")[1]));
			sync.set(ItemClass.STATUS, Integer.parseInt(pe.get_inhalt().split(";")[10].split(":")[1]));
			hotspot.set(ItemClass.STATUS, Integer.parseInt(pe.get_inhalt().split(";")[11].split(":")[1]));
			wifi.set(ItemClass.STATUS, Integer.parseInt(pe.get_inhalt().split(";")[0].split(":")[1]));
		}
	}
}