package m.tool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import m.tool.stuff.YouEntry;
import m.tool.stuff.aiitem;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Add extends Activity{
	
	TextView aitop;
	TextView tvb;
	TextView tvh;
	Spinner aiwh;
	Button bnext;
	Button bback;
	int seite;

	String source;
	
	String[] values;
	
	String[] values_1 = {"1 x 1", "2 x 1", "3 x 1", "4 x 1", "1 x 2", "2 x 2", "3 x 2", "4 x 2", "1 x 3", "2 x 3", "3 x 3", "4 x 3", "1 x 4", "2 x 4",
			"3 x 4", "4 x 4"};
	
	boolean ok = true;
	
	int it = 0;
	int h = 1;
	int b = 1;
	String op = "";
	
	RelativeLayout aisize;
	LinearLayout aiitem;
	RelativeLayout aioption;
	
	List<ResolveInfo> packs;
	ListView lv;
	
	String options = "";
	
	int itemcount = 17;
	
	aiitem[] aii = new aiitem[itemcount];
	
	List<RadioButton> radioButtons = new ArrayList<RadioButton>();
	
	int schritt = 0;
	
	ArrayList<String> DATA = new ArrayList<String>();
	ArrayList<Drawable> IMAGE_DATA = new ArrayList<Drawable>();
	
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
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);
        createComponents();
        main();
	}
	
	public void createComponents() {
		aitop = (TextView)findViewById(R.id.aitop);
		aisize = (RelativeLayout)findViewById(R.id.aisize);
		aiitem = (LinearLayout)findViewById(R.id.aiitem);
		aioption = (RelativeLayout)findViewById(R.id.aioption);
		lv = (ListView)findViewById(R.id.ailv);
		bnext = (Button)findViewById(R.id.ainext);
		bnext.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				next();
			}
		});
		bnext.setEnabled(false);
		bback = (Button)findViewById(R.id.aiback);
		bback.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				back();
			}
		});
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		boolean nfc = false;
		nfc = MTOOL.nfc.is_nfc();
//		aii[0] = new aiitem(this, 1, getResources().getString(R.string.info), getResources().getDrawable(R.drawable.icon), metrics, false);
		aii[0] = new aiitem(this, 2, getResources().getString(R.string.app), getResources().getDrawable(R.drawable.app), metrics, false);
		aii[1] = new aiitem(this, 3, getResources().getString(R.string.uhr), getResources().getDrawable(R.drawable.clock), metrics, false);
		aii[2] = new aiitem(this, 4, getResources().getString(R.string.wifi), getResources().getDrawable(R.drawable.wifi), metrics, false);
		aii[3] = new aiitem(this, 5, getResources().getString(R.string.bluetooth), getResources().getDrawable(R.drawable.bluetooth), metrics, false);
		aii[4] = new aiitem(this, 6, getResources().getString(R.string.profiles), getResources().getDrawable(R.drawable.profiles), metrics, false);
		aii[5] = new aiitem(this, 7, getResources().getString(R.string.data), getResources().getDrawable(R.drawable.data), metrics, false);
		aii[6] = new aiitem(this, 8, getResources().getString(R.string.timeout), getResources().getDrawable(R.drawable.timeout), metrics, false);
		aii[7] = new aiitem(this, 9, getResources().getString(R.string.brightness), getResources().getDrawable(R.drawable.brightness), metrics, false);
		aii[8] = new aiitem(this, 10, getResources().getString(R.string.rotation), getResources().getDrawable(R.drawable.rotation), metrics, false);
		aii[9] = new aiitem(this, 11, getResources().getString(R.string.volume), getResources().getDrawable(R.drawable.volume), metrics, false);
		aii[10] = new aiitem(this, 12, getResources().getString(R.string.gps), getResources().getDrawable(R.drawable.gps), metrics, false);
		aii[11] = new aiitem(this, 13, getResources().getString(R.string.nfc), getResources().getDrawable(R.drawable.nfc), metrics, false);
		if(!nfc)
			aii[11].setVisibility(View.GONE);
		aii[12] = new aiitem(this, 14, getResources().getString(R.string.keyguard), getResources().getDrawable(R.drawable.keyguard), metrics, false);
		aii[13] = new aiitem(this, 15, getResources().getString(R.string.sync), getResources().getDrawable(R.drawable.sync), metrics, false);
		aii[14] = new aiitem(this, 16, getResources().getString(R.string.hotspot), getResources().getDrawable(R.drawable.hotspot), metrics, false);
		aii[15] = new aiitem(this, 18, getResources().getString(R.string.battery), getResources().getDrawable(R.drawable.battery), metrics, false);
		aii[16] = new aiitem(this, 19, getResources().getString(R.string.torch), getResources().getDrawable(R.drawable.torch), metrics, true);
		for(int i = 0; i < aii.length; i++)
			aiitem.addView(aii[i]);
		for(int i = 0; i < itemcount; i++) {
			aii[i].setOnClickListener(rbclick);
			aii[i].rb.setOnClickListener(rbclickrb);
			radioButtons.add(aii[i].rb);
		}
		for (RadioButton button : radioButtons){
		    button.setOnCheckedChangeListener(new OnCheckedChangeListener(){
		        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		            if (isChecked) processRadioButtonClick(buttonView);
		        }   
		    });
		}
		tvb = (TextView)findViewById(R.id.aib);
		tvb.setText(Integer.toString(b));
		tvh = (TextView)findViewById(R.id.aih);
		tvh.setText(Integer.toString(h));
	}
	
	public void main() {
		if(schritt == 0) {
			aitop.setText(R.string.item);
			findViewById(R.id.aisv).setVisibility(View.VISIBLE);
			aioption.setVisibility(View.INVISIBLE);
			aiitem.setVisibility(View.VISIBLE);
			aisize.setVisibility(View.INVISIBLE);
			bnext.setText(R.string.next);
			bback.setText(android.R.string.cancel);
		}
		if(schritt == 1) {
			aitop.setText(R.string.choose);
			options = "";
			DATA.clear();
			IMAGE_DATA.clear();
			findViewById(R.id.aisv).setVisibility(View.INVISIBLE);
			aioption.setVisibility(View.VISIBLE);
			aiitem.setVisibility(View.INVISIBLE);
			aisize.setVisibility(View.INVISIBLE);
			bnext.setText(R.string.next);
			bback.setText(R.string.back);
			values = values_1;
			if(it == 2)
				options = "app";
			else if(it == 6)
				options = "profile";
			else
				go();
			if(options.equals("app")) {
				bnext.setEnabled(false);
				lv.setAdapter(new adapter(this));
				Intent mainIntent = new Intent(Intent.ACTION_MAIN);
		        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		        packs = getPackageManager().queryIntentActivities(mainIntent, 0);
		        Collections.sort(packs, new ResolveInfo.DisplayNameComparator(getPackageManager()));
		        for(int i = 0; i < packs.size(); i++) {
		            ResolveInfo p = packs.get(i);
		            String appname = p.activityInfo.loadLabel(getPackageManager()).toString();
		            Drawable icon = p.activityInfo.loadIcon(getPackageManager());
		            DATA.add(appname);
		            IMAGE_DATA.add(icon);
		            if(i == packs.size()-1)
		            	break;
		        }
		        lv.setOnItemClickListener(new OnItemClickListener() {
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						ComponentName cn = new ComponentName(packs.get(arg2).activityInfo.packageName, packs.get(arg2).activityInfo.name);
						op = cn.flattenToString();
						bnext.setEnabled(true);
						next();
					}
				});
			}
			if(options.equals("profile")) {
				bnext.setEnabled(false);
				lv.setAdapter(new adapter(this));
				DATA.add(getResources().getString(R.string.all));
				IMAGE_DATA.add(null);
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
		if(schritt == 2) {
			findViewById(R.id.aisv).setVisibility(View.INVISIBLE);
			aitop.setText(R.string.size);
			aioption.setVisibility(View.INVISIBLE);
			aiitem.setVisibility(View.INVISIBLE);
			aisize.setVisibility(View.VISIBLE);
			bnext.setText(android.R.string.ok);
			bback.setText(R.string.back);
			aiwh = (Spinner)findViewById(R.id.aiwh);
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, values);
			aiwh.setAdapter(adapter);
			aiwh.setOnItemSelectedListener(new OnItemSelectedListener() {
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					if(schritt == 3) {
						schritt = 2;
						bnext.setText(android.R.string.ok);
					}
					b = Integer.parseInt(aiwh.getSelectedItem().toString().substring(0, 1));
					h = Integer.parseInt(aiwh.getSelectedItem().toString().substring(4, 5));
					tvb.setText(Integer.toString(b));
					tvh.setText(Integer.toString(h));
				}
				public void onNothingSelected(AdapterView<?> arg0) {}
			});
		}
	}
	
	View.OnClickListener rbclick = new View.OnClickListener() {
		public void onClick(View v) {
			if(!bnext.isEnabled())
				bnext.setEnabled(true);
			it = ((aiitem)v).item;
			((aiitem)v).rb.setChecked(true);
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
	
	public void next()
	{
		if(schritt < 2)	{
			schritt++;
			source = "next";
			main();
		}
		else if(schritt == 2)
		{ 
			b = Integer.parseInt(aiwh.getSelectedItem().toString().substring(0, 1));
			h = Integer.parseInt(aiwh.getSelectedItem().toString().substring(4, 5));
			ok = true;
			seite = MTOOL.seite;
			if(MTOOL.felder == 9) {
				boolean f0 = true, f1 = true, f2 = true, f3 = true, f4 = true, f5 = true, f6 = true, f7 = true, f8 = true;
				for(int i = 0; i < MTOOL.youdb.length; i++) {
					if(MTOOL.youdb[i].get_feld() == 0 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f0 = false;
						if(MTOOL.youdb[i].get_width() >= 2)	f1 = false;
						if(MTOOL.youdb[i].get_width() >= 3)	f2 = false;
						if(MTOOL.youdb[i].get_height() >= 2) f3 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 2)	f4 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 3)	f5 = false;
						if(MTOOL.youdb[i].get_height() >= 3) f6 = false;
						if(MTOOL.youdb[i].get_height() >= 3 && MTOOL.youdb[i].get_width() >= 2)	f7 = false;
						if(MTOOL.youdb[i].get_height() >= 3 && MTOOL.youdb[i].get_width() >= 3)	f8 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 1 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f1 = false;
						if(MTOOL.youdb[i].get_width() >= 2)	f2 = false;
						if(MTOOL.youdb[i].get_height() >= 2) f4 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 2)	f5 = false;
						if(MTOOL.youdb[i].get_height() >= 3) f7 = false;
						if(MTOOL.youdb[i].get_height() >= 3 && MTOOL.youdb[i].get_width() >= 2)	f8 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 2 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f2 = false;
						if(MTOOL.youdb[i].get_height() >= 2) f5 = false;
						if(MTOOL.youdb[i].get_height() >= 3) f8 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 3 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f3 = false;
						if(MTOOL.youdb[i].get_width() >= 2)	f4 = false;
						if(MTOOL.youdb[i].get_width() >= 3)	f5 = false;
						if(MTOOL.youdb[i].get_height() >= 2) f6 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 2)	f7 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 3)	f8 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 4 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f4 = false;
						if(MTOOL.youdb[i].get_width() >= 2)	f5 = false;
						if(MTOOL.youdb[i].get_height() >= 2) f7 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 2)	f8 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 5 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f5 = false;
						if(MTOOL.youdb[i].get_height() >= 2) f8 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 6 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f6 = false;
						if(MTOOL.youdb[i].get_width() >= 2)	f7 = false;
						if(MTOOL.youdb[i].get_width() >= 3)	f8 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 7 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f7 = false;
						if(MTOOL.youdb[i].get_width() >= 2)	f8 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 8 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f8 = false;
					}
				}
				if(b == 1 && h == 1) {
					if(f0) MTOOL.db.addEntry(new YouEntry(seite, 0, b, h, it, op, ""));
					else if(f1) MTOOL.db.addEntry(new YouEntry(seite, 1, b, h, it, op, ""));
					else if(f2) MTOOL.db.addEntry(new YouEntry(seite, 2, b, h, it, op, ""));
					else if(f3) MTOOL.db.addEntry(new YouEntry(seite, 3, b, h, it, op, ""));
					else if(f4) MTOOL.db.addEntry(new YouEntry(seite, 4, b, h, it, op, ""));
					else if(f5) MTOOL.db.addEntry(new YouEntry(seite, 5, b, h, it, op, ""));
					else if(f6) MTOOL.db.addEntry(new YouEntry(seite, 6, b, h, it, op, ""));
					else if(f7) MTOOL.db.addEntry(new YouEntry(seite, 7, b, h, it, op, ""));
					else if(f8) MTOOL.db.addEntry(new YouEntry(seite, 8, b, h, it, op, ""));
					else ok = false;
				}
				if(b == 2 && h == 1) {
					if(f0 && f1) MTOOL.db.addEntry(new YouEntry(seite, 0, b, h, it, op, ""));
					else if(f1 && f2) MTOOL.db.addEntry(new YouEntry(seite, 1, b, h, it, op, ""));
					else if(f3 && f4) MTOOL.db.addEntry(new YouEntry(seite, 3, b, h, it, op, ""));
					else if(f4 && f5) MTOOL.db.addEntry(new YouEntry(seite, 4, b, h, it, op, ""));
					else if(f6 && f7) MTOOL.db.addEntry(new YouEntry(seite, 6, b, h, it, op, ""));
					else if(f7 && f8) MTOOL.db.addEntry(new YouEntry(seite, 7, b, h, it, op, ""));
					else ok = false;
				}
				if(b == 3 && h == 1) {
					if(f0 && f1 && f2) MTOOL.db.addEntry(new YouEntry(seite, 0, b, h, it, op, ""));
					else if(f3 && f4 && f5) MTOOL.db.addEntry(new YouEntry(seite, 3, b, h, it, op, ""));
					else if(f6 && f7 && f8) MTOOL.db.addEntry(new YouEntry(seite, 6, b, h, it, op, ""));
					else ok = false;
				}
				if(b == 4 && h == 1) {
					ok = false;
				}
				if(b == 1 && h == 2) {
					if(f0 && f3) MTOOL.db.addEntry(new YouEntry(seite, 0, b, h, it, op, ""));
					else if(f1 && f4) MTOOL.db.addEntry(new YouEntry(seite, 1, b, h, it, op, ""));
					else if(f2 && f5) MTOOL.db.addEntry(new YouEntry(seite, 2, b, h, it, op, ""));
					else if(f3 && f6) MTOOL.db.addEntry(new YouEntry(seite, 3, b, h, it, op, ""));
					else if(f4 && f7) MTOOL.db.addEntry(new YouEntry(seite, 4, b, h, it, op, ""));
					else if(f5 && f8) MTOOL.db.addEntry(new YouEntry(seite, 5, b, h, it, op, ""));
					else ok = false;
				}
				if(b == 1 && h == 3) {
					if(f0 && f3 && f6) MTOOL.db.addEntry(new YouEntry(seite, 0, b, h, it, op, ""));
					else if(f1 && f4 && f7) MTOOL.db.addEntry(new YouEntry(seite, 1, b, h, it, op, ""));
					else if(f2 && f5 && f8) MTOOL.db.addEntry(new YouEntry(seite, 2, b, h, it, op, ""));
					else ok = false;
				}
				if(b == 1 && h == 4) {
					ok = false;
				}
				if(b == 2 && h == 2) {
					if(f0 && f1 && f3 && f4) MTOOL.db.addEntry(new YouEntry(seite, 0, b, h, it, op, ""));
					else if(f1 && f2 && f4 && f5) MTOOL.db.addEntry(new YouEntry(seite, 1, b, h, it, op, ""));
					else if(f3 && f4 && f6 && f7) MTOOL.db.addEntry(new YouEntry(seite, 3, b, h, it, op, ""));
					else if(f4 && f5 && f7 && f8) MTOOL.db.addEntry(new YouEntry(seite, 4, b, h, it, op, ""));
					else ok = false;
				}
				if(b == 2 && h == 3) {
					if(f0 && f1 && f3 && f4 && f6 && f7) MTOOL.db.addEntry(new YouEntry(seite, 0, b, h, it, op, ""));
					else if(f1 && f2 && f4 && f5 && f7 && f8) MTOOL.db.addEntry(new YouEntry(seite, 1, b, h, it, op, ""));
					else ok = false;
				}
				if(b == 2 && h == 4) {
					ok = false;
				}
				if(b == 3 && h == 2) {
					if(f0 && f1 && f2 && f3 && f4 && f5) MTOOL.db.addEntry(new YouEntry(seite, 0, b, h, it, op, ""));
					else if(f3 && f4 && f5 && f6 && f7 && f8) MTOOL.db.addEntry(new YouEntry(seite, 3, b, h, it, op, ""));
					else ok = false;
				}
				if(b == 4 && h == 2) {
					ok = false;
				}
				if(b == 3 && h == 3) {
					if(f0 && f1 && f2 && f3 && f4 && f5 && f6 && f7 && f8) MTOOL.db.addEntry(new YouEntry(seite, 0, b, h, it, op, ""));
					else ok = false;
				}
				if(b == 3 && h == 4) {
					ok = false;
				}
				if(b == 4 && h == 3) {
					ok = false;
				}
				if(b == 4 && h == 4) {
					ok = false;
				}
			} else if(MTOOL.felder == 12) {
				boolean f0 = true, f1 = true, f2 = true, f3 = true, f4 = true, f5 = true, f6 = true, f7 = true,
						f8 = true, f9 = true, f10 = true, f11 = true;
				for(int i = 0; i < MTOOL.youdb.length; i++) {
					if(MTOOL.youdb[i].get_feld() == 0 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f0 = false;
						if(MTOOL.youdb[i].get_width() >= 2)	f1 = false;
						if(MTOOL.youdb[i].get_width() >= 3)	f2 = false;
						if(MTOOL.youdb[i].get_height() >= 2) f3 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 2)	f4 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 3)	f5 = false;
						if(MTOOL.youdb[i].get_height() >= 3) f6 = false;
						if(MTOOL.youdb[i].get_height() >= 3 && MTOOL.youdb[i].get_width() >= 2)	f7 = false;
						if(MTOOL.youdb[i].get_height() >= 3 && MTOOL.youdb[i].get_width() >= 3)	f8 = false;
						if(MTOOL.youdb[i].get_height() == 4) f9 = false;
						if(MTOOL.youdb[i].get_height() == 4 && MTOOL.youdb[i].get_width() >= 2)	f10 = false;
						if(MTOOL.youdb[i].get_height() == 4 && MTOOL.youdb[i].get_width() >= 3)	f11 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 1 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f1 = false;
						if(MTOOL.youdb[i].get_width() >= 2)	f2 = false;
						if(MTOOL.youdb[i].get_height() >= 2) f4 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 2)	f5 = false;
						if(MTOOL.youdb[i].get_height() >= 3) f7 = false;
						if(MTOOL.youdb[i].get_height() >= 3 && MTOOL.youdb[i].get_width() >= 2)	f8 = false;
						if(MTOOL.youdb[i].get_height() == 4) f10 = false;
						if(MTOOL.youdb[i].get_height() == 4 && MTOOL.youdb[i].get_width() >= 2)	f11 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 2 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f2 = false;
						if(MTOOL.youdb[i].get_height() >= 2) f5 = false;
						if(MTOOL.youdb[i].get_height() >= 3) f8 = false;
						if(MTOOL.youdb[i].get_height() == 4) f11 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 3 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f3 = false;
						if(MTOOL.youdb[i].get_width() >= 2)	f4 = false;
						if(MTOOL.youdb[i].get_width() >= 3)	f5 = false;
						if(MTOOL.youdb[i].get_height() >= 2) f6 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 2)	f7 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 3)	f8 = false;
						if(MTOOL.youdb[i].get_height() >= 3) f9 = false;
						if(MTOOL.youdb[i].get_height() >= 3 && MTOOL.youdb[i].get_width() >= 2)	f10 = false;
						if(MTOOL.youdb[i].get_height() >= 3 && MTOOL.youdb[i].get_width() >= 3)	f11 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 4 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f4 = false;
						if(MTOOL.youdb[i].get_width() >= 2)	f5 = false;
						if(MTOOL.youdb[i].get_height() >= 2) f7 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 2)	f8 = false;
						if(MTOOL.youdb[i].get_height() >= 3) f10 = false;
						if(MTOOL.youdb[i].get_height() >= 3 && MTOOL.youdb[i].get_width() >= 2)	f11 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 5 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f5 = false;
						if(MTOOL.youdb[i].get_height() >= 2) f8 = false;
						if(MTOOL.youdb[i].get_height() >= 3) f11 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 6 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f6 = false;
						if(MTOOL.youdb[i].get_width() >= 2)	f7 = false;
						if(MTOOL.youdb[i].get_width() >= 3)	f8 = false;
						if(MTOOL.youdb[i].get_height() >= 2) f9 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 2)	f10 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 3)	f11 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 7 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f7 = false;
						if(MTOOL.youdb[i].get_width() >= 2)	f8 = false;
						if(MTOOL.youdb[i].get_height() >= 2) f10 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 2)	f11 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 8 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f8 = false;
						if(MTOOL.youdb[i].get_height() >= 2) f11 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 9 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f9 = false;
						if(MTOOL.youdb[i].get_width() >= 2)	f10 = false;
						if(MTOOL.youdb[i].get_width() >= 3)	f11 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 10 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f10 = false;
						if(MTOOL.youdb[i].get_width() >= 2)	f11 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 11 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f11 = false;
					}
				}
				if(b == 1 && h == 1) {
					if(f0) MTOOL.db.addEntry(new YouEntry(seite, 0, b, h, it, op, ""));
					else if(f1) MTOOL.db.addEntry(new YouEntry(seite, 1, b, h, it, op, ""));
					else if(f2) MTOOL.db.addEntry(new YouEntry(seite, 2, b, h, it, op, ""));
					else if(f3) MTOOL.db.addEntry(new YouEntry(seite, 3, b, h, it, op, ""));
					else if(f4) MTOOL.db.addEntry(new YouEntry(seite, 4, b, h, it, op, ""));
					else if(f5) MTOOL.db.addEntry(new YouEntry(seite, 5, b, h, it, op, ""));
					else if(f6) MTOOL.db.addEntry(new YouEntry(seite, 6, b, h, it, op, ""));
					else if(f7) MTOOL.db.addEntry(new YouEntry(seite, 7, b, h, it, op, ""));
					else if(f8) MTOOL.db.addEntry(new YouEntry(seite, 8, b, h, it, op, ""));
					else if(f9) MTOOL.db.addEntry(new YouEntry(seite, 9, b, h, it, op, ""));
					else if(f10) MTOOL.db.addEntry(new YouEntry(seite, 10, b, h, it, op, ""));
					else if(f11) MTOOL.db.addEntry(new YouEntry(seite, 11, b, h, it, op, ""));
					else ok = false;
				}
				if(b == 2 && h == 1) {
					if(f0 && f1) MTOOL.db.addEntry(new YouEntry(seite, 0, b, h, it, op, ""));
					else if(f1 && f2) MTOOL.db.addEntry(new YouEntry(seite, 1, b, h, it, op, ""));
					else if(f3 && f4) MTOOL.db.addEntry(new YouEntry(seite, 3, b, h, it, op, ""));
					else if(f4 && f5) MTOOL.db.addEntry(new YouEntry(seite, 4, b, h, it, op, ""));
					else if(f6 && f7) MTOOL.db.addEntry(new YouEntry(seite, 6, b, h, it, op, ""));
					else if(f7 && f8) MTOOL.db.addEntry(new YouEntry(seite, 7, b, h, it, op, ""));
					else if(f9 && f10) MTOOL.db.addEntry(new YouEntry(seite, 9, b, h, it, op, ""));
					else if(f10 && f11) MTOOL.db.addEntry(new YouEntry(seite, 10, b, h, it, op, ""));
					else ok = false;
				}
				if(b == 3 && h == 1) {
					if(f0 && f1 && f2) MTOOL.db.addEntry(new YouEntry(seite, 0, b, h, it, op, ""));
					else if(f3 && f4 && f5) MTOOL.db.addEntry(new YouEntry(seite, 3, b, h, it, op, ""));
					else if(f6 && f7 && f8) MTOOL.db.addEntry(new YouEntry(seite, 6, b, h, it, op, ""));
					else if(f9 && f10 && f11) MTOOL.db.addEntry(new YouEntry(seite, 9, b, h, it, op, ""));
					else ok = false;
				}
				if(b == 4 && h == 1) {
					ok = false;
				}
				if(b == 1 && h == 2) {
					if(f0 && f3) MTOOL.db.addEntry(new YouEntry(seite, 0, b, h, it, op, ""));
					else if(f1 && f4) MTOOL.db.addEntry(new YouEntry(seite, 1, b, h, it, op, ""));
					else if(f2 && f5) MTOOL.db.addEntry(new YouEntry(seite, 2, b, h, it, op, ""));
					else if(f3 && f6) MTOOL.db.addEntry(new YouEntry(seite, 3, b, h, it, op, ""));
					else if(f4 && f7) MTOOL.db.addEntry(new YouEntry(seite, 4, b, h, it, op, ""));
					else if(f5 && f8) MTOOL.db.addEntry(new YouEntry(seite, 5, b, h, it, op, ""));
					else if(f6 && f9) MTOOL.db.addEntry(new YouEntry(seite, 6, b, h, it, op, ""));
					else if(f7 && f10) MTOOL.db.addEntry(new YouEntry(seite, 7, b, h, it, op, ""));
					else if(f8 && f11) MTOOL.db.addEntry(new YouEntry(seite, 8, b, h, it, op, ""));
					else ok = false;
				}
				if(b == 1 && h == 3) {
					if(f0 && f3 && f6) MTOOL.db.addEntry(new YouEntry(seite, 0, b, h, it, op, ""));
					else if(f1 && f4 && f7) MTOOL.db.addEntry(new YouEntry(seite, 1, b, h, it, op, ""));
					else if(f2 && f5 && f8) MTOOL.db.addEntry(new YouEntry(seite, 2, b, h, it, op, ""));
					else if(f3 && f6 && f9) MTOOL.db.addEntry(new YouEntry(seite, 3, b, h, it, op, ""));
					else if(f4 && f7 && f10) MTOOL.db.addEntry(new YouEntry(seite, 4, b, h, it, op, ""));
					else if(f5 && f8 && f11) MTOOL.db.addEntry(new YouEntry(seite, 5, b, h, it, op, ""));
					else ok = false;
				}
				if(b == 1 && h == 4) {
					if(f0 && f3 && f6 && f9) MTOOL.db.addEntry(new YouEntry(seite, 0, b, h, it, op, ""));
					else if(f1 && f4 && f7 && f10) MTOOL.db.addEntry(new YouEntry(seite, 1, b, h, it, op, ""));
					else if(f2 && f5 && f8 && f11) MTOOL.db.addEntry(new YouEntry(seite, 2, b, h, it, op, ""));
					else ok = false;
				}
				if(b == 2 && h == 2) {
					if(f0 && f1 && f3 && f4) MTOOL.db.addEntry(new YouEntry(seite, 0, b, h, it, op, ""));
					else if(f1 && f2 && f4 && f5) MTOOL.db.addEntry(new YouEntry(seite, 1, b, h, it, op, ""));
					else if(f3 && f4 && f6 && f7) MTOOL.db.addEntry(new YouEntry(seite, 3, b, h, it, op, ""));
					else if(f4 && f5 && f7 && f8) MTOOL.db.addEntry(new YouEntry(seite, 4, b, h, it, op, ""));
					else if(f6 && f7 && f9 && f10) MTOOL.db.addEntry(new YouEntry(seite, 6, b, h, it, op, ""));
					else if(f7 && f8 && f10 && f11) MTOOL.db.addEntry(new YouEntry(seite, 7, b, h, it, op, ""));
					else ok = false;
				}
				if(b == 2 && h == 3) {
					if(f0 && f1 && f3 && f4 && f6 && f7) MTOOL.db.addEntry(new YouEntry(seite, 0, b, h, it, op, ""));
					else if(f1 && f2 && f4 && f5 && f7 && f8) MTOOL.db.addEntry(new YouEntry(seite, 1, b, h, it, op, ""));
					else if(f3 && f4 && f6 && f7 && f9 && f10) MTOOL.db.addEntry(new YouEntry(seite, 3, b, h, it, op, ""));
					else if(f4 && f5 && f7 && f8 && f10 && f11) MTOOL.db.addEntry(new YouEntry(seite, 4, b, h, it, op, ""));
					else ok = false;
				}
				if(b == 2 && h == 4) {
					if(f0 && f1 && f3 && f4 && f6 && f7 && f9 && f10) MTOOL.db.addEntry(new YouEntry(seite, 0, b, h, it, op, ""));
					else if(f1 && f2 && f4 && f5 && f7 && f8 && f10 && f11) MTOOL.db.addEntry(new YouEntry(seite, 1, b, h, it, op, ""));
					else ok = false;
				}
				if(b == 3 && h == 2) {
					if(f0 && f1 && f2 && f3 && f4 && f5) MTOOL.db.addEntry(new YouEntry(seite, 0, b, h, it, op, ""));
					else if(f3 && f4 && f5 && f6 && f7 && f8) MTOOL.db.addEntry(new YouEntry(seite, 3, b, h, it, op, ""));
					else if(f6 && f7 && f8 && f9 && f10 && f11) MTOOL.db.addEntry(new YouEntry(seite, 4, b, h, it, op, ""));
					else ok = false;
				}
				if(b == 4 && h == 2) {
					ok = false;
				}
				if(b == 3 && h == 3) {
					if(f0 && f1 && f2 && f3 && f4 && f5 && f6 && f7 && f8) MTOOL.db.addEntry(new YouEntry(seite, 0, b, h, it, op, ""));
					else if(f3 && f4 && f5 && f6 && f7 && f8 && f9 && f10 && f11) MTOOL.db.addEntry(new YouEntry(seite, 3, b, h, it, op, ""));
					else ok = false;
				}
				if(b == 3 && h == 4) {
					if(f0 && f1 && f2 && f3 && f4 && f5 && f6 && f7 && f8 && f9 && f10 && f11) MTOOL.db.addEntry(new YouEntry(seite, 0, b, h, it, op, ""));
					else ok = false;
				}
				if(b == 4 && h == 3) {
					ok = false;
				}
				if(b == 4 && h == 4) {
					ok = false;
				}
			} else if(MTOOL.felder == 16) {
				boolean f0 = true, f1 = true, f2 = true, f3 = true, f4 = true, f5 = true, f6 = true, f7 = true,
						f8 = true, f9 = true, f10 = true, f11 = true, f12 = true, f13 = true, f14 = true, f15 = true;
				for(int i = 0; i < MTOOL.youdb.length; i++) {
					if(MTOOL.youdb[i].get_feld() == 0 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f0 = false;
						if(MTOOL.youdb[i].get_width() >= 2)	f1 = false;
						if(MTOOL.youdb[i].get_width() >= 3)	f2 = false;
						if(MTOOL.youdb[i].get_width() == 4) f3 = false;
						if(MTOOL.youdb[i].get_height() >= 2) f4 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 2)	f5 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 3)	f6 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() == 4)	f7 = false;
						if(MTOOL.youdb[i].get_height() >= 3) f8 = false;
						if(MTOOL.youdb[i].get_height() >= 3 && MTOOL.youdb[i].get_width() >= 2)	f9 = false;
						if(MTOOL.youdb[i].get_height() >= 3 && MTOOL.youdb[i].get_width() >= 3)	f10 = false;
						if(MTOOL.youdb[i].get_height() >= 3 && MTOOL.youdb[i].get_width() == 4)	f11 = false;
						if(MTOOL.youdb[i].get_height() == 4) f12 = false;
						if(MTOOL.youdb[i].get_height() == 4 && MTOOL.youdb[i].get_width() >= 2)	f13 = false;
						if(MTOOL.youdb[i].get_height() == 4 && MTOOL.youdb[i].get_width() >= 3)	f14 = false;
						if(MTOOL.youdb[i].get_height() == 4 && MTOOL.youdb[i].get_width() == 4)	f15 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 1 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f1 = false;
						if(MTOOL.youdb[i].get_width() >= 2)	f2 = false;
						if(MTOOL.youdb[i].get_width() >= 3) f3 = false;
						if(MTOOL.youdb[i].get_height() >= 2) f5 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 2)	f6 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 3)	f7 = false;
						if(MTOOL.youdb[i].get_height() >= 3) f9 = false;
						if(MTOOL.youdb[i].get_height() >= 3 && MTOOL.youdb[i].get_width() >= 2)	f10 = false;
						if(MTOOL.youdb[i].get_height() >= 3 && MTOOL.youdb[i].get_width() >= 3)	f11 = false;
						if(MTOOL.youdb[i].get_height() == 4) f13 = false;
						if(MTOOL.youdb[i].get_height() == 4 && MTOOL.youdb[i].get_width() >= 2)	f14 = false;
						if(MTOOL.youdb[i].get_height() == 4 && MTOOL.youdb[i].get_width() >= 3)	f15 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 2 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f2 = false;
						if(MTOOL.youdb[i].get_width() >= 2) f3 = false;
						if(MTOOL.youdb[i].get_height() >= 2) f6 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 2)	f7 = false;
						if(MTOOL.youdb[i].get_height() >= 3) f10 = false;
						if(MTOOL.youdb[i].get_height() >= 3 && MTOOL.youdb[i].get_width() >= 2)	f11 = false;
						if(MTOOL.youdb[i].get_height() == 4) f14= false;
						if(MTOOL.youdb[i].get_height() == 4 && MTOOL.youdb[i].get_width() >= 2)	f15 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 3 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f3 = false;
						if(MTOOL.youdb[i].get_height() >= 2) f7 = false;
						if(MTOOL.youdb[i].get_height() >= 3) f11 = false;
						if(MTOOL.youdb[i].get_height() == 4) f15 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 4 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f4 = false;
						if(MTOOL.youdb[i].get_width() >= 2)	f5 = false;
						if(MTOOL.youdb[i].get_width() >= 3)	f6 = false;
						if(MTOOL.youdb[i].get_width() == 4)	f7 = false;
						if(MTOOL.youdb[i].get_height() >= 2) f8 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 2)	f9 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 3)	f10 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() == 4)	f11 = false;
						if(MTOOL.youdb[i].get_height() >= 3) f12 = false;
						if(MTOOL.youdb[i].get_height() >= 3 && MTOOL.youdb[i].get_width() >= 2)	f13 = false;
						if(MTOOL.youdb[i].get_height() >= 3 && MTOOL.youdb[i].get_width() >= 3)	f14 = false;
						if(MTOOL.youdb[i].get_height() >= 3 && MTOOL.youdb[i].get_width() == 4)	f15 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 5 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f5 = false;
						if(MTOOL.youdb[i].get_width() >= 2)	f6 = false;
						if(MTOOL.youdb[i].get_width() >= 3)	f7 = false;
						if(MTOOL.youdb[i].get_height() >= 2) f9 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 2)	f10 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 3)	f11 = false;
						if(MTOOL.youdb[i].get_height() >= 3) f13 = false;
						if(MTOOL.youdb[i].get_height() >= 3 && MTOOL.youdb[i].get_width() >= 2)	f14 = false;
						if(MTOOL.youdb[i].get_height() >= 3 && MTOOL.youdb[i].get_width() >= 3)	f15 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 6 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f6 = false;
						if(MTOOL.youdb[i].get_width() >= 2)	f7 = false;
						if(MTOOL.youdb[i].get_height() >= 2) f10 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 2)	f11 = false;
						if(MTOOL.youdb[i].get_height() >= 3) f14 = false;
						if(MTOOL.youdb[i].get_height() >= 3 && MTOOL.youdb[i].get_width() >= 2)	f15 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 7 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f7 = false;
						if(MTOOL.youdb[i].get_height() >= 2) f11 = false;
						if(MTOOL.youdb[i].get_height() >= 3) f15 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 8 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f8 = false;
						if(MTOOL.youdb[i].get_width() >= 2)	f9 = false;
						if(MTOOL.youdb[i].get_width() >= 3)	f10 = false;
						if(MTOOL.youdb[i].get_width() == 4)	f11 = false;
						if(MTOOL.youdb[i].get_height() >= 2) f12 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 2)	f13 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 3)	f14 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() == 4)	f15 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 9 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f9 = false;
						if(MTOOL.youdb[i].get_width() >= 2)	f10 = false;
						if(MTOOL.youdb[i].get_width() >= 3)	f11 = false;
						if(MTOOL.youdb[i].get_height() >= 2) f13 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 2)	f14 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 3)	f15 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 10 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f10 = false;
						if(MTOOL.youdb[i].get_width() >= 2)	f11 = false;
						if(MTOOL.youdb[i].get_height() >= 2) f14 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 2)	f15 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 11 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f11 = false;
						if(MTOOL.youdb[i].get_height() >= 2) f15 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 12 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f12 = false;
						if(MTOOL.youdb[i].get_width() >= 2)	f13 = false;
						if(MTOOL.youdb[i].get_width() >= 3)	f14 = false;
						if(MTOOL.youdb[i].get_width() == 4)	f15 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 13 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f13 = false;
						if(MTOOL.youdb[i].get_width() >= 2)	f14 = false;
						if(MTOOL.youdb[i].get_width() >= 3)	f15 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 14 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f14 = false;
						if(MTOOL.youdb[i].get_width() >= 2)	f15 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 15 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f15 = false;
					}
				}
				if(b == 1 && h == 1) {
					if(f0) MTOOL.db.addEntry(new YouEntry(seite, 0, b, h, it, op, ""));
					else if(f1) MTOOL.db.addEntry(new YouEntry(seite, 1, b, h, it, op, ""));
					else if(f2) MTOOL.db.addEntry(new YouEntry(seite, 2, b, h, it, op, ""));
					else if(f3) MTOOL.db.addEntry(new YouEntry(seite, 3, b, h, it, op, ""));
					else if(f4) MTOOL.db.addEntry(new YouEntry(seite, 4, b, h, it, op, ""));
					else if(f5) MTOOL.db.addEntry(new YouEntry(seite, 5, b, h, it, op, ""));
					else if(f6) MTOOL.db.addEntry(new YouEntry(seite, 6, b, h, it, op, ""));
					else if(f7) MTOOL.db.addEntry(new YouEntry(seite, 7, b, h, it, op, ""));
					else if(f8) MTOOL.db.addEntry(new YouEntry(seite, 8, b, h, it, op, ""));
					else if(f9) MTOOL.db.addEntry(new YouEntry(seite, 9, b, h, it, op, ""));
					else if(f10) MTOOL.db.addEntry(new YouEntry(seite, 10, b, h, it, op, ""));
					else if(f11) MTOOL.db.addEntry(new YouEntry(seite, 11, b, h, it, op, ""));
					else if(f12) MTOOL.db.addEntry(new YouEntry(seite, 12, b, h, it, op, ""));
					else if(f13) MTOOL.db.addEntry(new YouEntry(seite, 13, b, h, it, op, ""));
					else if(f14) MTOOL.db.addEntry(new YouEntry(seite, 14, b, h, it, op, ""));
					else if(f15) MTOOL.db.addEntry(new YouEntry(seite, 15, b, h, it, op, ""));
					else ok = false;
				}
				if(b == 2 && h == 1) {
					if(f0 && f1) MTOOL.db.addEntry(new YouEntry(seite, 0, b, h, it, op, ""));
					else if(f1 && f2) MTOOL.db.addEntry(new YouEntry(seite, 1, b, h, it, op, ""));
					else if(f2 && f3) MTOOL.db.addEntry(new YouEntry(seite, 2, b, h, it, op, ""));
					else if(f4 && f5) MTOOL.db.addEntry(new YouEntry(seite, 4, b, h, it, op, ""));
					else if(f5 && f6) MTOOL.db.addEntry(new YouEntry(seite, 5, b, h, it, op, ""));
					else if(f6 && f7) MTOOL.db.addEntry(new YouEntry(seite, 6, b, h, it, op, ""));
					else if(f8 && f9) MTOOL.db.addEntry(new YouEntry(seite, 8, b, h, it, op, ""));
					else if(f9 && f10) MTOOL.db.addEntry(new YouEntry(seite, 9, b, h, it, op, ""));
					else if(f10 && f11) MTOOL.db.addEntry(new YouEntry(seite, 10, b, h, it, op, ""));
					else if(f12 && f13) MTOOL.db.addEntry(new YouEntry(seite, 12, b, h, it, op, ""));
					else if(f13 && f14) MTOOL.db.addEntry(new YouEntry(seite, 13, b, h, it, op, ""));
					else if(f14 && f15) MTOOL.db.addEntry(new YouEntry(seite, 14, b, h, it, op, ""));
					else ok = false;
				}
				if(b == 3 && h == 1) {
					if(f0 && f1 && f2) MTOOL.db.addEntry(new YouEntry(seite, 0, b, h, it, op, ""));
					else if(f1 && f2 && f3) MTOOL.db.addEntry(new YouEntry(seite, 1, b, h, it, op, ""));
					else if(f4 && f5 && f6) MTOOL.db.addEntry(new YouEntry(seite, 4, b, h, it, op, ""));
					else if(f5 && f6 && f7) MTOOL.db.addEntry(new YouEntry(seite, 5, b, h, it, op, ""));
					else if(f8 && f9 && f10) MTOOL.db.addEntry(new YouEntry(seite, 8, b, h, it, op, ""));
					else if(f9 && f10 && f11) MTOOL.db.addEntry(new YouEntry(seite, 9, b, h, it, op, ""));
					else if(f12 && f13 && f14) MTOOL.db.addEntry(new YouEntry(seite, 12, b, h, it, op, ""));
					else if(f13 && f14 && f15) MTOOL.db.addEntry(new YouEntry(seite, 13, b, h, it, op, ""));
					else ok = false;
				}
				if(b == 4 && h == 1) {
					if(f0 && f1 && f2 && f3) MTOOL.db.addEntry(new YouEntry(seite, 0, b, h, it, op, ""));
					else if(f4 && f5 && f6 && f7) MTOOL.db.addEntry(new YouEntry(seite, 4, b, h, it, op, ""));
					else if(f8 && f9 && f10 && f11) MTOOL.db.addEntry(new YouEntry(seite, 8, b, h, it, op, ""));
					else if(f12 && f13 && f14 && f15) MTOOL.db.addEntry(new YouEntry(seite, 12, b, h, it, op, ""));
					else ok = false;
				}
				if(b == 1 && h == 2) {
					if(f0 && f4) MTOOL.db.addEntry(new YouEntry(seite, 0, b, h, it, op, ""));
					else if(f1 && f5) MTOOL.db.addEntry(new YouEntry(seite, 1, b, h, it, op, ""));
					else if(f2 && f6) MTOOL.db.addEntry(new YouEntry(seite, 2, b, h, it, op, ""));
					else if(f3 && f7) MTOOL.db.addEntry(new YouEntry(seite, 3, b, h, it, op, ""));
					else if(f4 && f8) MTOOL.db.addEntry(new YouEntry(seite, 4, b, h, it, op, ""));
					else if(f5 && f9) MTOOL.db.addEntry(new YouEntry(seite, 5, b, h, it, op, ""));
					else if(f6 && f10) MTOOL.db.addEntry(new YouEntry(seite, 6, b, h, it, op, ""));
					else if(f7 && f11) MTOOL.db.addEntry(new YouEntry(seite, 7, b, h, it, op, ""));
					else if(f8 && f12) MTOOL.db.addEntry(new YouEntry(seite, 8, b, h, it, op, ""));
					else if(f9 && f13) MTOOL.db.addEntry(new YouEntry(seite, 9, b, h, it, op, ""));
					else if(f10 && f14) MTOOL.db.addEntry(new YouEntry(seite, 10, b, h, it, op, ""));
					else if(f11 && f15) MTOOL.db.addEntry(new YouEntry(seite, 11, b, h, it, op, ""));
					else ok = false;
				}
				if(b == 1 && h == 3) {
					if(f0 && f4 && f8) MTOOL.db.addEntry(new YouEntry(seite, 0, b, h, it, op, ""));
					else if(f1 && f5 && f9) MTOOL.db.addEntry(new YouEntry(seite, 1, b, h, it, op, ""));
					else if(f2 && f6 && f10) MTOOL.db.addEntry(new YouEntry(seite, 2, b, h, it, op, ""));
					else if(f3 && f7 && f11) MTOOL.db.addEntry(new YouEntry(seite, 3, b, h, it, op, ""));
					else if(f4 && f8 && f12) MTOOL.db.addEntry(new YouEntry(seite, 4, b, h, it, op, ""));
					else if(f5 && f9 && f13) MTOOL.db.addEntry(new YouEntry(seite, 5, b, h, it, op, ""));
					else if(f6 && f10 && f14) MTOOL.db.addEntry(new YouEntry(seite, 6, b, h, it, op, ""));
					else if(f7 && f11 && f15) MTOOL.db.addEntry(new YouEntry(seite, 7, b, h, it, op, ""));
					else ok = false;
				}
				if(b == 1 && h == 4) {
					if(f0 && f4 && f8 && f12) MTOOL.db.addEntry(new YouEntry(seite, 0, b, h, it, op, ""));
					else if(f1 && f5 && f9 && f13) MTOOL.db.addEntry(new YouEntry(seite, 1, b, h, it, op, ""));
					else if(f2 && f6 && f10 && f14) MTOOL.db.addEntry(new YouEntry(seite, 2, b, h, it, op, ""));
					else if(f3 && f7 && f11 && f15) MTOOL.db.addEntry(new YouEntry(seite, 3, b, h, it, op, ""));
					else ok = false;
				}
				if(b == 2 && h == 2) {
					if(f0 && f1 && f4 && f5) MTOOL.db.addEntry(new YouEntry(seite, 0, b, h, it, op, ""));
					else if(f1 && f2 && f5 && f6) MTOOL.db.addEntry(new YouEntry(seite, 1, b, h, it, op, ""));
					else if(f2 && f3 && f6 && f7) MTOOL.db.addEntry(new YouEntry(seite, 2, b, h, it, op, ""));
					else if(f4 && f5 && f8 && f9) MTOOL.db.addEntry(new YouEntry(seite, 4, b, h, it, op, ""));
					else if(f5 && f6 && f9 && f10) MTOOL.db.addEntry(new YouEntry(seite, 5, b, h, it, op, ""));
					else if(f6 && f7 && f10 && f11) MTOOL.db.addEntry(new YouEntry(seite, 6, b, h, it, op, ""));
					else if(f8 && f9 && f12 && f13) MTOOL.db.addEntry(new YouEntry(seite, 8, b, h, it, op, ""));
					else if(f9 && f10 && f13 && f14) MTOOL.db.addEntry(new YouEntry(seite, 9, b, h, it, op, ""));
					else if(f10 && f11 && f14 && f15) MTOOL.db.addEntry(new YouEntry(seite, 10, b, h, it, op, ""));
					else ok = false;
				}
				if(b == 2 && h == 3) {
					if(f0 && f1 && f4 && f5 && f8 && f9) MTOOL.db.addEntry(new YouEntry(seite, 0, b, h, it, op, ""));
					else if(f1 && f2 && f5 && f6 && f9 && f10) MTOOL.db.addEntry(new YouEntry(seite, 1, b, h, it, op, ""));
					else if(f2 && f3 && f6 && f7 && f10 && f11) MTOOL.db.addEntry(new YouEntry(seite, 2, b, h, it, op, ""));
					else if(f4 && f5 && f8 && f9 && f12 && f13) MTOOL.db.addEntry(new YouEntry(seite, 4, b, h, it, op, ""));
					else if(f5 && f6 && f9 && f10 && f13 && f14) MTOOL.db.addEntry(new YouEntry(seite, 5, b, h, it, op, ""));
					else if(f6 && f7 && f10 && f11 && f14 && f15) MTOOL.db.addEntry(new YouEntry(seite, 6, b, h, it, op, ""));
					else ok = false;
				}
				if(b == 2 && h == 4) {
					if(f0 && f1 && f4 && f5 && f8 && f9 && f12 && f13) MTOOL.db.addEntry(new YouEntry(seite, 0, b, h, it, op, ""));
					else if(f1 && f2 && f5 && f6 && f9 && f10 && f13 && f14) MTOOL.db.addEntry(new YouEntry(seite, 1, b, h, it, op, ""));
					else if(f2 && f3 && f6 && f7 && f10 && f11 && f14 && f15) MTOOL.db.addEntry(new YouEntry(seite, 2, b, h, it, op, ""));
					else ok = false;
				}
				if(b == 3 && h == 2) {
					if(f0 && f1 && f2 && f4 && f5 && f6) MTOOL.db.addEntry(new YouEntry(seite, 0, b, h, it, op, ""));
					else if(f1 && f2 && f3 && f5 && f6 && f7) MTOOL.db.addEntry(new YouEntry(seite, 1, b, h, it, op, ""));
					else if(f4 && f5 && f6 && f8 && f9 && f10) MTOOL.db.addEntry(new YouEntry(seite, 4, b, h, it, op, ""));
					else if(f5 && f6 && f7 && f9 && f10 && f11) MTOOL.db.addEntry(new YouEntry(seite, 5, b, h, it, op, ""));
					else if(f8 && f9 && f10 && f12 && f13 && f14) MTOOL.db.addEntry(new YouEntry(seite, 8, b, h, it, op, ""));
					else if(f9 && f10 && f11 && f13 && f14 && f15) MTOOL.db.addEntry(new YouEntry(seite, 9, b, h, it, op, ""));
					else ok = false;
				}
				if(b == 4 && h == 2) {
					if(f0 && f1 && f2 && f3 && f4 && f5 && f6 && f7) MTOOL.db.addEntry(new YouEntry(seite, 0, b, h, it, op, ""));
					else if(f4 && f5 && f6 && f7 && f8 && f9 && f10 && f11) MTOOL.db.addEntry(new YouEntry(seite, 4, b, h, it, op, ""));
					else if(f8 && f9 && f10 && f11 && f12 && f13 && f14 && f15) MTOOL.db.addEntry(new YouEntry(seite, 8, b, h, it, op, ""));
					else ok = false;
				}
				if(b == 3 && h == 3) {
					if(f0 && f1 && f2 && f4 && f5 && f6 && f8 && f9 && f10) MTOOL.db.addEntry(new YouEntry(seite, 0, b, h, it, op, ""));
					else if(f1 && f2 && f3 && f5 && f6 && f7 && f9 && f10 && f11) MTOOL.db.addEntry(new YouEntry(seite, 1, b, h, it, op, ""));
					else if(f4 && f5 && f6 && f8 && f9 && f10 && f12 && f13 && f14) MTOOL.db.addEntry(new YouEntry(seite, 4, b, h, it, op, ""));
					else if(f5 && f6 && f7 && f9 && f10 && f11 && f13 && f14 && f15) MTOOL.db.addEntry(new YouEntry(seite, 5, b, h, it, op, ""));
					else ok = false;
				}
				if(b == 3 && h == 4) {
					if(f0 && f1 && f2 && f4 && f5 && f6 && f8 && f9 && f10 && f12 && f13 && f14) MTOOL.db.addEntry(new YouEntry(seite, 0, b, h, it, op, ""));
					else if(f1 && f2 && f3 && f5 && f6 && f7 && f9 && f10 && f11 && f13 && f14 && f15) MTOOL.db.addEntry(new YouEntry(seite, 1, b, h, it, op, ""));
					else ok = false;
				}
				if(b == 4 && h == 3) {
					if(f0 && f1 && f2 && f3 && f4 && f5 && f6 && f7 && f8 && f9 && f10 && f11) MTOOL.db.addEntry(new YouEntry(seite, 0, b, h, it, op, ""));
					else if(f4 && f5 && f6 && f7 && f8 && f9 && f10 && f11 && f12 && f13 && f14 && f15) MTOOL.db.addEntry(new YouEntry(seite, 4, b, h, it, op, ""));
					else ok = false;
				}
				if(b == 4 && h == 4) {
					if(f0 && f1 && f2 && f3 && f4 && f5 && f6 && f7 && f8 && f9 && f10 && f11 && f12 && f13 && f14 && f15) 
						MTOOL.db.addEntry(new YouEntry(seite, 0, b, h, it, op, ""));
					else ok = false;
				}
			} else if(MTOOL.felder == 20) {
				boolean f0 = true, f1 = true, f2 = true, f3 = true, f4 = true, f5 = true, f6 = true, f7 = true,
						f8 = true, f9 = true, f10 = true, f11 = true, f12 = true, f13 = true, f14 = true, f15 = true,
						f16 = true, f17 = true, f18 = true, f19 = true;
				for(int i = 0; i < MTOOL.youdb.length; i++) {
					if(MTOOL.youdb[i].get_feld() == 0 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f0 = false;
						if(MTOOL.youdb[i].get_width() >= 2)	f1 = false;
						if(MTOOL.youdb[i].get_width() >= 3)	f2 = false;
						if(MTOOL.youdb[i].get_width() == 4) f3 = false;
						if(MTOOL.youdb[i].get_height() >= 2) f4 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 2)	f5 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 3)	f6 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() == 4)	f7 = false;
						if(MTOOL.youdb[i].get_height() >= 3) f8 = false;
						if(MTOOL.youdb[i].get_height() >= 3 && MTOOL.youdb[i].get_width() >= 2)	f9 = false;
						if(MTOOL.youdb[i].get_height() >= 3 && MTOOL.youdb[i].get_width() >= 3)	f10 = false;
						if(MTOOL.youdb[i].get_height() >= 3 && MTOOL.youdb[i].get_width() == 4)	f11 = false;
						if(MTOOL.youdb[i].get_height() == 4) f12 = false;
						if(MTOOL.youdb[i].get_height() == 4 && MTOOL.youdb[i].get_width() >= 2)	f13 = false;
						if(MTOOL.youdb[i].get_height() == 4 && MTOOL.youdb[i].get_width() >= 3)	f14 = false;
						if(MTOOL.youdb[i].get_height() == 4 && MTOOL.youdb[i].get_width() == 4)	f15 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 1 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f1 = false;
						if(MTOOL.youdb[i].get_width() >= 2)	f2 = false;
						if(MTOOL.youdb[i].get_width() >= 3) f3 = false;
						if(MTOOL.youdb[i].get_height() >= 2) f5 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 2)	f6 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 3)	f7 = false;
						if(MTOOL.youdb[i].get_height() >= 3) f9 = false;
						if(MTOOL.youdb[i].get_height() >= 3 && MTOOL.youdb[i].get_width() >= 2)	f10 = false;
						if(MTOOL.youdb[i].get_height() >= 3 && MTOOL.youdb[i].get_width() >= 3)	f11 = false;
						if(MTOOL.youdb[i].get_height() == 4) f13 = false;
						if(MTOOL.youdb[i].get_height() == 4 && MTOOL.youdb[i].get_width() >= 2)	f14 = false;
						if(MTOOL.youdb[i].get_height() == 4 && MTOOL.youdb[i].get_width() >= 3)	f15 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 2 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f2 = false;
						if(MTOOL.youdb[i].get_width() >= 2) f3 = false;
						if(MTOOL.youdb[i].get_height() >= 2) f6 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 2)	f7 = false;
						if(MTOOL.youdb[i].get_height() >= 3) f10 = false;
						if(MTOOL.youdb[i].get_height() >= 3 && MTOOL.youdb[i].get_width() >= 2)	f11 = false;
						if(MTOOL.youdb[i].get_height() == 4) f14= false;
						if(MTOOL.youdb[i].get_height() == 4 && MTOOL.youdb[i].get_width() >= 2)	f15 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 3 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f3 = false;
						if(MTOOL.youdb[i].get_height() >= 2) f7 = false;
						if(MTOOL.youdb[i].get_height() >= 3) f11 = false;
						if(MTOOL.youdb[i].get_height() == 4) f15 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 4 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f4 = false;
						if(MTOOL.youdb[i].get_width() >= 2)	f5 = false;
						if(MTOOL.youdb[i].get_width() >= 3)	f6 = false;
						if(MTOOL.youdb[i].get_width() == 4)	f7 = false;
						if(MTOOL.youdb[i].get_height() >= 2) f8 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 2)	f9 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 3)	f10 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() == 4)	f11 = false;
						if(MTOOL.youdb[i].get_height() >= 3) f12 = false;
						if(MTOOL.youdb[i].get_height() >= 3 && MTOOL.youdb[i].get_width() >= 2)	f13 = false;
						if(MTOOL.youdb[i].get_height() >= 3 && MTOOL.youdb[i].get_width() >= 3)	f14 = false;
						if(MTOOL.youdb[i].get_height() >= 3 && MTOOL.youdb[i].get_width() == 4)	f15 = false;
						if(MTOOL.youdb[i].get_height() == 4) f16 = false;
						if(MTOOL.youdb[i].get_height() == 4 && MTOOL.youdb[i].get_width() >= 2)	f17 = false;
						if(MTOOL.youdb[i].get_height() == 4 && MTOOL.youdb[i].get_width() >= 3)	f18 = false;
						if(MTOOL.youdb[i].get_height() == 4 && MTOOL.youdb[i].get_width() == 4)	f19 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 5 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f5 = false;
						if(MTOOL.youdb[i].get_width() >= 2)	f6 = false;
						if(MTOOL.youdb[i].get_width() >= 3)	f7 = false;
						if(MTOOL.youdb[i].get_height() >= 2) f9 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 2)	f10 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 3)	f11 = false;
						if(MTOOL.youdb[i].get_height() >= 3) f13 = false;
						if(MTOOL.youdb[i].get_height() >= 3 && MTOOL.youdb[i].get_width() >= 2)	f14 = false;
						if(MTOOL.youdb[i].get_height() >= 3 && MTOOL.youdb[i].get_width() >= 3)	f15 = false;
						if(MTOOL.youdb[i].get_height() == 4) f17 = false;
						if(MTOOL.youdb[i].get_height() == 4 && MTOOL.youdb[i].get_width() >= 2)	f18 = false;
						if(MTOOL.youdb[i].get_height() == 4 && MTOOL.youdb[i].get_width() >= 3)	f19 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 6 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f6 = false;
						if(MTOOL.youdb[i].get_width() >= 2)	f7 = false;
						if(MTOOL.youdb[i].get_height() >= 2) f10 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 2)	f11 = false;
						if(MTOOL.youdb[i].get_height() >= 3) f14 = false;
						if(MTOOL.youdb[i].get_height() >= 3 && MTOOL.youdb[i].get_width() >= 2)	f15 = false;
						if(MTOOL.youdb[i].get_height() == 4) f18 = false;
						if(MTOOL.youdb[i].get_height() == 4 && MTOOL.youdb[i].get_width() >= 2)	f19 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 7 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f7 = false;
						if(MTOOL.youdb[i].get_height() >= 2) f11 = false;
						if(MTOOL.youdb[i].get_height() >= 3) f15 = false;
						if(MTOOL.youdb[i].get_height() == 4) f19 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 8 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f8 = false;
						if(MTOOL.youdb[i].get_width() >= 2)	f9 = false;
						if(MTOOL.youdb[i].get_width() >= 3)	f10 = false;
						if(MTOOL.youdb[i].get_width() == 4)	f11 = false;
						if(MTOOL.youdb[i].get_height() >= 2) f12 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 2)	f13 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 3)	f14 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() == 4)	f15 = false;
						if(MTOOL.youdb[i].get_height() >= 3) f16 = false;
						if(MTOOL.youdb[i].get_height() >= 3 && MTOOL.youdb[i].get_width() >= 2)	f17 = false;
						if(MTOOL.youdb[i].get_height() >= 3 && MTOOL.youdb[i].get_width() >= 3)	f18 = false;
						if(MTOOL.youdb[i].get_height() >= 3 && MTOOL.youdb[i].get_width() == 4)	f19 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 9 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f9 = false;
						if(MTOOL.youdb[i].get_width() >= 2)	f10 = false;
						if(MTOOL.youdb[i].get_width() >= 3)	f11 = false;
						if(MTOOL.youdb[i].get_height() >= 2) f13 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 2)	f14 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 3)	f15 = false;
						if(MTOOL.youdb[i].get_height() >= 3) f17 = false;
						if(MTOOL.youdb[i].get_height() >= 3 && MTOOL.youdb[i].get_width() >= 2)	f18 = false;
						if(MTOOL.youdb[i].get_height() >= 3 && MTOOL.youdb[i].get_width() >= 3)	f19 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 10 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f10 = false;
						if(MTOOL.youdb[i].get_width() >= 2)	f11 = false;
						if(MTOOL.youdb[i].get_height() >= 2) f14 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 2)	f15 = false;
						if(MTOOL.youdb[i].get_height() >= 3) f18 = false;
						if(MTOOL.youdb[i].get_height() >= 3 && MTOOL.youdb[i].get_width() >= 2)	f19 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 11 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f11 = false;
						if(MTOOL.youdb[i].get_height() >= 2) f15 = false;
						if(MTOOL.youdb[i].get_height() >= 2) f19 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 12 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f12 = false;
						if(MTOOL.youdb[i].get_width() >= 2)	f13 = false;
						if(MTOOL.youdb[i].get_width() >= 3)	f14 = false;
						if(MTOOL.youdb[i].get_width() == 4)	f15 = false;
						if(MTOOL.youdb[i].get_height() >= 2) f16 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 2)	f17 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 3)	f18 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() == 4)	f19 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 13 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f13 = false;
						if(MTOOL.youdb[i].get_width() >= 2)	f14 = false;
						if(MTOOL.youdb[i].get_width() >= 3)	f15 = false;
						if(MTOOL.youdb[i].get_height() >= 2) f17 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 2)	f18 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 3)	f19 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 14 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f14 = false;
						if(MTOOL.youdb[i].get_width() >= 2)	f15 = false;
						if(MTOOL.youdb[i].get_height() >= 2) f18 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 2)	f19 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 15 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f15 = false;
						if(MTOOL.youdb[i].get_height() >= 2) f19 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 16 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f16 = false;
						if(MTOOL.youdb[i].get_width() >= 2)	f17 = false;
						if(MTOOL.youdb[i].get_width() >= 3)	f18 = false;
						if(MTOOL.youdb[i].get_width() == 4)	f19 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 17 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f17 = false;
						if(MTOOL.youdb[i].get_width() >= 2)	f18 = false;
						if(MTOOL.youdb[i].get_width() >= 3)	f19 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 18 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f18 = false;
						if(MTOOL.youdb[i].get_width() >= 2)	f19 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 19 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f19 = false;
					}
				}
				if(b == 1 && h == 1) {
					if(f0) MTOOL.db.addEntry(new YouEntry(seite, 0, b, h, it, op, ""));
					else if(f1) MTOOL.db.addEntry(new YouEntry(seite, 1, b, h, it, op, ""));
					else if(f2) MTOOL.db.addEntry(new YouEntry(seite, 2, b, h, it, op, ""));
					else if(f3) MTOOL.db.addEntry(new YouEntry(seite, 3, b, h, it, op, ""));
					else if(f4) MTOOL.db.addEntry(new YouEntry(seite, 4, b, h, it, op, ""));
					else if(f5) MTOOL.db.addEntry(new YouEntry(seite, 5, b, h, it, op, ""));
					else if(f6) MTOOL.db.addEntry(new YouEntry(seite, 6, b, h, it, op, ""));
					else if(f7) MTOOL.db.addEntry(new YouEntry(seite, 7, b, h, it, op, ""));
					else if(f8) MTOOL.db.addEntry(new YouEntry(seite, 8, b, h, it, op, ""));
					else if(f9) MTOOL.db.addEntry(new YouEntry(seite, 9, b, h, it, op, ""));
					else if(f10) MTOOL.db.addEntry(new YouEntry(seite, 10, b, h, it, op, ""));
					else if(f11) MTOOL.db.addEntry(new YouEntry(seite, 11, b, h, it, op, ""));
					else if(f12) MTOOL.db.addEntry(new YouEntry(seite, 12, b, h, it, op, ""));
					else if(f13) MTOOL.db.addEntry(new YouEntry(seite, 13, b, h, it, op, ""));
					else if(f14) MTOOL.db.addEntry(new YouEntry(seite, 14, b, h, it, op, ""));
					else if(f15) MTOOL.db.addEntry(new YouEntry(seite, 15, b, h, it, op, ""));
					else if(f16) MTOOL.db.addEntry(new YouEntry(seite, 16, b, h, it, op, ""));
					else if(f17) MTOOL.db.addEntry(new YouEntry(seite, 17, b, h, it, op, ""));
					else if(f18) MTOOL.db.addEntry(new YouEntry(seite, 18, b, h, it, op, ""));
					else if(f19) MTOOL.db.addEntry(new YouEntry(seite, 19, b, h, it, op, ""));
					else ok = false;
				}
				if(b == 2 && h == 1) {
					if(f0 && f1) MTOOL.db.addEntry(new YouEntry(seite, 0, b, h, it, op, ""));
					else if(f1 && f2) MTOOL.db.addEntry(new YouEntry(seite, 1, b, h, it, op, ""));
					else if(f2 && f3) MTOOL.db.addEntry(new YouEntry(seite, 2, b, h, it, op, ""));
					else if(f4 && f5) MTOOL.db.addEntry(new YouEntry(seite, 4, b, h, it, op, ""));
					else if(f5 && f6) MTOOL.db.addEntry(new YouEntry(seite, 5, b, h, it, op, ""));
					else if(f6 && f7) MTOOL.db.addEntry(new YouEntry(seite, 6, b, h, it, op, ""));
					else if(f8 && f9) MTOOL.db.addEntry(new YouEntry(seite, 8, b, h, it, op, ""));
					else if(f9 && f10) MTOOL.db.addEntry(new YouEntry(seite, 9, b, h, it, op, ""));
					else if(f10 && f11) MTOOL.db.addEntry(new YouEntry(seite, 10, b, h, it, op, ""));
					else if(f12 && f13) MTOOL.db.addEntry(new YouEntry(seite, 12, b, h, it, op, ""));
					else if(f13 && f14) MTOOL.db.addEntry(new YouEntry(seite, 13, b, h, it, op, ""));
					else if(f14 && f15) MTOOL.db.addEntry(new YouEntry(seite, 14, b, h, it, op, ""));
					else if(f16 && f17) MTOOL.db.addEntry(new YouEntry(seite, 16, b, h, it, op, ""));
					else if(f17 && f18) MTOOL.db.addEntry(new YouEntry(seite, 17, b, h, it, op, ""));
					else if(f18 && f19) MTOOL.db.addEntry(new YouEntry(seite, 18, b, h, it, op, ""));
					else ok = false;
				}
				if(b == 3 && h == 1) {
					if(f0 && f1 && f2) MTOOL.db.addEntry(new YouEntry(seite, 0, b, h, it, op, ""));
					else if(f1 && f2 && f3) MTOOL.db.addEntry(new YouEntry(seite, 1, b, h, it, op, ""));
					else if(f4 && f5 && f6) MTOOL.db.addEntry(new YouEntry(seite, 4, b, h, it, op, ""));
					else if(f5 && f6 && f7) MTOOL.db.addEntry(new YouEntry(seite, 5, b, h, it, op, ""));
					else if(f8 && f9 && f10) MTOOL.db.addEntry(new YouEntry(seite, 8, b, h, it, op, ""));
					else if(f9 && f10 && f11) MTOOL.db.addEntry(new YouEntry(seite, 9, b, h, it, op, ""));
					else if(f12 && f13 && f14) MTOOL.db.addEntry(new YouEntry(seite, 12, b, h, it, op, ""));
					else if(f13 && f14 && f15) MTOOL.db.addEntry(new YouEntry(seite, 13, b, h, it, op, ""));
					else if(f16 && f17 && f18) MTOOL.db.addEntry(new YouEntry(seite, 16, b, h, it, op, ""));
					else if(f17 && f18 && f19) MTOOL.db.addEntry(new YouEntry(seite, 17, b, h, it, op, ""));
					else ok = false;
				}
				if(b == 4 && h == 1) {
					if(f0 && f1 && f2 && f3) MTOOL.db.addEntry(new YouEntry(seite, 0, b, h, it, op, ""));
					else if(f4 && f5 && f6 && f7) MTOOL.db.addEntry(new YouEntry(seite, 4, b, h, it, op, ""));
					else if(f8 && f9 && f10 && f11) MTOOL.db.addEntry(new YouEntry(seite, 8, b, h, it, op, ""));
					else if(f12 && f13 && f14 && f15) MTOOL.db.addEntry(new YouEntry(seite, 12, b, h, it, op, ""));
					else if(f16 && f17 && f18 && f19) MTOOL.db.addEntry(new YouEntry(seite, 16, b, h, it, op, ""));
					else ok = false;
				}
				if(b == 1 && h == 2) {
					if(f0 && f4) MTOOL.db.addEntry(new YouEntry(seite, 0, b, h, it, op, ""));
					else if(f1 && f5) MTOOL.db.addEntry(new YouEntry(seite, 1, b, h, it, op, ""));
					else if(f2 && f6) MTOOL.db.addEntry(new YouEntry(seite, 2, b, h, it, op, ""));
					else if(f3 && f7) MTOOL.db.addEntry(new YouEntry(seite, 3, b, h, it, op, ""));
					else if(f4 && f8) MTOOL.db.addEntry(new YouEntry(seite, 4, b, h, it, op, ""));
					else if(f5 && f9) MTOOL.db.addEntry(new YouEntry(seite, 5, b, h, it, op, ""));
					else if(f6 && f10) MTOOL.db.addEntry(new YouEntry(seite, 6, b, h, it, op, ""));
					else if(f7 && f11) MTOOL.db.addEntry(new YouEntry(seite, 7, b, h, it, op, ""));
					else if(f8 && f12) MTOOL.db.addEntry(new YouEntry(seite, 8, b, h, it, op, ""));
					else if(f9 && f13) MTOOL.db.addEntry(new YouEntry(seite, 9, b, h, it, op, ""));
					else if(f10 && f14) MTOOL.db.addEntry(new YouEntry(seite, 10, b, h, it, op, ""));
					else if(f11 && f15) MTOOL.db.addEntry(new YouEntry(seite, 11, b, h, it, op, ""));
					else if(f12 && f16) MTOOL.db.addEntry(new YouEntry(seite, 12, b, h, it, op, ""));
					else if(f13 && f17) MTOOL.db.addEntry(new YouEntry(seite, 13, b, h, it, op, ""));
					else if(f14 && f18) MTOOL.db.addEntry(new YouEntry(seite, 14, b, h, it, op, ""));
					else if(f15 && f19) MTOOL.db.addEntry(new YouEntry(seite, 15, b, h, it, op, ""));
					else ok = false;
				}
				if(b == 1 && h == 3) {
					if(f0 && f4 && f8) MTOOL.db.addEntry(new YouEntry(seite, 0, b, h, it, op, ""));
					else if(f1 && f5 && f9) MTOOL.db.addEntry(new YouEntry(seite, 1, b, h, it, op, ""));
					else if(f2 && f6 && f10) MTOOL.db.addEntry(new YouEntry(seite, 2, b, h, it, op, ""));
					else if(f3 && f7 && f11) MTOOL.db.addEntry(new YouEntry(seite, 3, b, h, it, op, ""));
					else if(f4 && f8 && f12) MTOOL.db.addEntry(new YouEntry(seite, 4, b, h, it, op, ""));
					else if(f5 && f9 && f13) MTOOL.db.addEntry(new YouEntry(seite, 5, b, h, it, op, ""));
					else if(f6 && f10 && f14) MTOOL.db.addEntry(new YouEntry(seite, 6, b, h, it, op, ""));
					else if(f7 && f11 && f15) MTOOL.db.addEntry(new YouEntry(seite, 7, b, h, it, op, ""));
					else if(f8 && f12 && f16) MTOOL.db.addEntry(new YouEntry(seite, 8, b, h, it, op, ""));
					else if(f9 && f13 && f17) MTOOL.db.addEntry(new YouEntry(seite, 9, b, h, it, op, ""));
					else if(f10 && f14 && f18) MTOOL.db.addEntry(new YouEntry(seite, 10, b, h, it, op, ""));
					else if(f11 && f15 && f19) MTOOL.db.addEntry(new YouEntry(seite, 11, b, h, it, op, ""));
					else ok = false;
				}
				if(b == 1 && h == 4) {
					if(f0 && f4 && f8 && f12) MTOOL.db.addEntry(new YouEntry(seite, 0, b, h, it, op, ""));
					else if(f1 && f5 && f9 && f13) MTOOL.db.addEntry(new YouEntry(seite, 1, b, h, it, op, ""));
					else if(f2 && f6 && f10 && f14) MTOOL.db.addEntry(new YouEntry(seite, 2, b, h, it, op, ""));
					else if(f3 && f7 && f11 && f15) MTOOL.db.addEntry(new YouEntry(seite, 3, b, h, it, op, ""));
					else if(f4 && f8 && f12 && f16) MTOOL.db.addEntry(new YouEntry(seite, 4, b, h, it, op, ""));
					else if(f5 && f9 && f13 && f17) MTOOL.db.addEntry(new YouEntry(seite, 5, b, h, it, op, ""));
					else if(f6 && f10 && f14 && f18) MTOOL.db.addEntry(new YouEntry(seite, 6, b, h, it, op, ""));
					else if(f7 && f11 && f15 && f19) MTOOL.db.addEntry(new YouEntry(seite, 7, b, h, it, op, ""));
					else ok = false;
				}
				if(b == 2 && h == 2) {
					if(f0 && f1 && f4 && f5) MTOOL.db.addEntry(new YouEntry(seite, 0, b, h, it, op, ""));
					else if(f1 && f2 && f5 && f6) MTOOL.db.addEntry(new YouEntry(seite, 1, b, h, it, op, ""));
					else if(f2 && f3 && f6 && f7) MTOOL.db.addEntry(new YouEntry(seite, 2, b, h, it, op, ""));
					else if(f4 && f5 && f8 && f9) MTOOL.db.addEntry(new YouEntry(seite, 4, b, h, it, op, ""));
					else if(f5 && f6 && f9 && f10) MTOOL.db.addEntry(new YouEntry(seite, 5, b, h, it, op, ""));
					else if(f6 && f7 && f10 && f11) MTOOL.db.addEntry(new YouEntry(seite, 6, b, h, it, op, ""));
					else if(f8 && f9 && f12 && f13) MTOOL.db.addEntry(new YouEntry(seite, 8, b, h, it, op, ""));
					else if(f9 && f10 && f13 && f14) MTOOL.db.addEntry(new YouEntry(seite, 9, b, h, it, op, ""));
					else if(f10 && f11 && f14 && f15) MTOOL.db.addEntry(new YouEntry(seite, 10, b, h, it, op, ""));
					else if(f12 && f13 && f16 && f17) MTOOL.db.addEntry(new YouEntry(seite, 12, b, h, it, op, ""));
					else if(f13 && f14 && f17 && f18) MTOOL.db.addEntry(new YouEntry(seite, 13, b, h, it, op, ""));
					else if(f14 && f15 && f18 && f19) MTOOL.db.addEntry(new YouEntry(seite, 14, b, h, it, op, ""));
					else ok = false;
				}
				if(b == 2 && h == 3) {
					if(f0 && f1 && f4 && f5 && f8 && f9) MTOOL.db.addEntry(new YouEntry(seite, 0, b, h, it, op, ""));
					else if(f1 && f2 && f5 && f6 && f9 && f10) MTOOL.db.addEntry(new YouEntry(seite, 1, b, h, it, op, ""));
					else if(f2 && f3 && f6 && f7 && f10 && f11) MTOOL.db.addEntry(new YouEntry(seite, 2, b, h, it, op, ""));
					else if(f4 && f5 && f8 && f9 && f12 && f13) MTOOL.db.addEntry(new YouEntry(seite, 4, b, h, it, op, ""));
					else if(f5 && f6 && f9 && f10 && f13 && f14) MTOOL.db.addEntry(new YouEntry(seite, 5, b, h, it, op, ""));
					else if(f6 && f7 && f10 && f11 && f14 && f15) MTOOL.db.addEntry(new YouEntry(seite, 6, b, h, it, op, ""));
					else if(f8 && f9 && f12 && f13 && f16 && f17) MTOOL.db.addEntry(new YouEntry(seite, 8, b, h, it, op, ""));
					else if(f9 && f10 && f13 && f14 && f17 && f18) MTOOL.db.addEntry(new YouEntry(seite, 9, b, h, it, op, ""));
					else if(f10 && f11 && f14 && f15 && f18 && f19) MTOOL.db.addEntry(new YouEntry(seite, 10, b, h, it, op, ""));
					else ok = false;
				}
				if(b == 2 && h == 4) {
					if(f0 && f1 && f4 && f5 && f8 && f9 && f12 && f13) MTOOL.db.addEntry(new YouEntry(seite, 0, b, h, it, op, ""));
					else if(f1 && f2 && f5 && f6 && f9 && f10 && f13 && f14) MTOOL.db.addEntry(new YouEntry(seite, 1, b, h, it, op, ""));
					else if(f2 && f3 && f6 && f7 && f10 && f11 && f14 && f15) MTOOL.db.addEntry(new YouEntry(seite, 2, b, h, it, op, ""));
					else if(f4 && f5 && f8 && f9 && f12 && f13 && f16 && f17) MTOOL.db.addEntry(new YouEntry(seite, 4, b, h, it, op, ""));
					else if(f5 && f6 && f9 && f10 && f13 && f14 && f17 && f18) MTOOL.db.addEntry(new YouEntry(seite, 5, b, h, it, op, ""));
					else if(f6 && f7 && f10 && f11 && f14 && f15 && f18 && f19) MTOOL.db.addEntry(new YouEntry(seite, 6, b, h, it, op, ""));
					else ok = false;
				}
				if(b == 3 && h == 2) {
					if(f0 && f1 && f2 && f4 && f5 && f6) MTOOL.db.addEntry(new YouEntry(seite, 0, b, h, it, op, ""));
					else if(f1 && f2 && f3 && f5 && f6 && f7) MTOOL.db.addEntry(new YouEntry(seite, 1, b, h, it, op, ""));
					else if(f4 && f5 && f6 && f8 && f9 && f10) MTOOL.db.addEntry(new YouEntry(seite, 4, b, h, it, op, ""));
					else if(f5 && f6 && f7 && f9 && f10 && f11) MTOOL.db.addEntry(new YouEntry(seite, 5, b, h, it, op, ""));
					else if(f8 && f9 && f10 && f12 && f13 && f14) MTOOL.db.addEntry(new YouEntry(seite, 8, b, h, it, op, ""));
					else if(f9 && f10 && f11 && f13 && f14 && f15) MTOOL.db.addEntry(new YouEntry(seite, 9, b, h, it, op, ""));
					else if(f12 && f13 && f14 && f16 && f17 && f18) MTOOL.db.addEntry(new YouEntry(seite, 12, b, h, it, op, ""));
					else if(f13 && f14 && f15 && f17 && f18 && f19) MTOOL.db.addEntry(new YouEntry(seite, 13, b, h, it, op, ""));
					else ok = false;
				}
				if(b == 4 && h == 2) {
					if(f0 && f1 && f2 && f3 && f4 && f5 && f6 && f7) MTOOL.db.addEntry(new YouEntry(seite, 0, b, h, it, op, ""));
					else if(f4 && f5 && f6 && f7 && f8 && f9 && f10 && f11) MTOOL.db.addEntry(new YouEntry(seite, 4, b, h, it, op, ""));
					else if(f8 && f9 && f10 && f11 && f12 && f13 && f14 && f15) MTOOL.db.addEntry(new YouEntry(seite, 8, b, h, it, op, ""));
					else if(f12 && f13 && f14 && f15 && f16 && f17 && f18 && f19) MTOOL.db.addEntry(new YouEntry(seite, 12, b, h, it, op, ""));
					else ok = false;
				}
				if(b == 3 && h == 3) {
					if(f0 && f1 && f2 && f4 && f5 && f6 && f8 && f9 && f10) MTOOL.db.addEntry(new YouEntry(seite, 0, b, h, it, op, ""));
					else if(f1 && f2 && f3 && f5 && f6 && f7 && f9 && f10 && f11) MTOOL.db.addEntry(new YouEntry(seite, 1, b, h, it, op, ""));
					else if(f4 && f5 && f6 && f8 && f9 && f10 && f12 && f13 && f14) MTOOL.db.addEntry(new YouEntry(seite, 4, b, h, it, op, ""));
					else if(f5 && f6 && f7 && f9 && f10 && f11 && f13 && f14 && f15) MTOOL.db.addEntry(new YouEntry(seite, 5, b, h, it, op, ""));
					else if(f8 && f9 && f10 && f12 && f13 && f14 && f16 && f17 && f18) MTOOL.db.addEntry(new YouEntry(seite, 8, b, h, it, op, ""));
					else if(f9 && f10 && f11 && f13 && f14 && f15 && f17 && f18 && f19) MTOOL.db.addEntry(new YouEntry(seite, 9, b, h, it, op, ""));
					else ok = false;
				}
				if(b == 3 && h == 4) {
					if(f0 && f1 && f2 && f4 && f5 && f6 && f8 && f9 && f10 && f12 && f13 && f14) MTOOL.db.addEntry(new YouEntry(seite, 0, b, h, it, op, ""));
					else if(f1 && f2 && f3 && f5 && f6 && f7 && f9 && f10 && f11 && f13 && f14 && f15) MTOOL.db.addEntry(new YouEntry(seite, 1, b, h, it, op, ""));
					else if(f4 && f5 && f6 && f8 && f9 && f10 && f12 && f13 && f14 && f16 && f17 && f18) MTOOL.db.addEntry(new YouEntry(seite, 4, b, h, it, op, ""));
					else if(f5 && f6 && f7 && f9 && f10 && f11 && f13 && f14 && f15 && f17 && f18 && f19) MTOOL.db.addEntry(new YouEntry(seite, 5, b, h, it, op, ""));
					else ok = false;
				}
				if(b == 4 && h == 3) {
					if(f0 && f1 && f2 && f3 && f4 && f5 && f6 && f7 && f8 && f9 && f10 && f11) MTOOL.db.addEntry(new YouEntry(seite, 0, b, h, it, op, ""));
					else if(f4 && f5 && f6 && f7 && f8 && f9 && f10 && f11 && f12 && f13 && f14 && f15) MTOOL.db.addEntry(new YouEntry(seite, 4, b, h, it, op, ""));
					else if(f8 && f9 && f10 && f11 && f12 && f13 && f14 && f15 && f16 && f17 && f18 && f19)
						MTOOL.db.addEntry(new YouEntry(seite, 8, b, h, it, op, ""));
					else ok = false;
				}
				if(b == 4 && h == 4) {
					if(f0 && f1 && f2 && f3 && f4 && f5 && f6 && f7 && f8 && f9 && f10 && f11 && f12 && f13 && f14 && f15) 
						MTOOL.db.addEntry(new YouEntry(seite, 0, b, h, it, op, ""));
					else if(f4 && f5 && f6 && f7 && f8 && f9 && f10 && f11 && f12 && f13 && f14 && f15 && f16 && f17 && f18 && f19) 
						MTOOL.db.addEntry(new YouEntry(seite, 4, b, h, it, op, ""));
					else ok = false;
				}
			} else if(MTOOL.felder == 25) {
				boolean f0 = true, f1 = true, f2 = true, f3 = true, f4 = true, f5 = true, f6 = true, f7 = true,
						f8 = true, f9 = true, f10 = true, f11 = true, f12 = true, f13 = true, f14 = true, f15 = true,
						f16 = true, f17 = true, f18 = true, f19 = true, f20 = true, f21 = true, f22 = true, f23 = true,
						f24 = true;
				for(int i = 0; i < MTOOL.youdb.length; i++) {
					if(MTOOL.youdb[i].get_feld() == 0 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f0 = false;
						if(MTOOL.youdb[i].get_width() >= 2)	f1 = false;
						if(MTOOL.youdb[i].get_width() >= 3)	f2 = false;
						if(MTOOL.youdb[i].get_width() == 4) f3 = false;
						if(MTOOL.youdb[i].get_height() >= 2) f5 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 2)	f6 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 3)	f7 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() == 4)	f8 = false;
						if(MTOOL.youdb[i].get_height() >= 3) f10 = false;
						if(MTOOL.youdb[i].get_height() >= 3 && MTOOL.youdb[i].get_width() >= 2)	f11 = false;
						if(MTOOL.youdb[i].get_height() >= 3 && MTOOL.youdb[i].get_width() >= 3)	f12 = false;
						if(MTOOL.youdb[i].get_height() >= 3 && MTOOL.youdb[i].get_width() == 4)	f13 = false;
						if(MTOOL.youdb[i].get_height() == 4) f15 = false;
						if(MTOOL.youdb[i].get_height() == 4 && MTOOL.youdb[i].get_width() >= 2)	f16 = false;
						if(MTOOL.youdb[i].get_height() == 4 && MTOOL.youdb[i].get_width() >= 3)	f17 = false;
						if(MTOOL.youdb[i].get_height() == 4 && MTOOL.youdb[i].get_width() == 4)	f18 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 1 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f1 = false;
						if(MTOOL.youdb[i].get_width() >= 2)	f2 = false;
						if(MTOOL.youdb[i].get_width() >= 3) f3 = false;
						if(MTOOL.youdb[i].get_width() == 4) f4 = false;
						if(MTOOL.youdb[i].get_height() >= 2) f6 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 2)	f7 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 3)	f8 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() == 4)	f9 = false;
						if(MTOOL.youdb[i].get_height() >= 3) f11 = false;
						if(MTOOL.youdb[i].get_height() >= 3 && MTOOL.youdb[i].get_width() >= 2)	f12 = false;
						if(MTOOL.youdb[i].get_height() >= 3 && MTOOL.youdb[i].get_width() >= 3)	f13 = false;
						if(MTOOL.youdb[i].get_height() >= 3 && MTOOL.youdb[i].get_width() == 4)	f14 = false;
						if(MTOOL.youdb[i].get_height() == 4) f16 = false;
						if(MTOOL.youdb[i].get_height() == 4 && MTOOL.youdb[i].get_width() >= 2)	f17 = false;
						if(MTOOL.youdb[i].get_height() == 4 && MTOOL.youdb[i].get_width() >= 3)	f18 = false;
						if(MTOOL.youdb[i].get_height() == 4 && MTOOL.youdb[i].get_width() == 4)	f19 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 2 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f2 = false;
						if(MTOOL.youdb[i].get_width() >= 2) f3 = false;
						if(MTOOL.youdb[i].get_width() >= 3) f4 = false;
						if(MTOOL.youdb[i].get_height() >= 2) f7 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 2)	f8 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 3)	f9 = false;
						if(MTOOL.youdb[i].get_height() >= 3) f12 = false;
						if(MTOOL.youdb[i].get_height() >= 3 && MTOOL.youdb[i].get_width() >= 2)	f13 = false;
						if(MTOOL.youdb[i].get_height() >= 3 && MTOOL.youdb[i].get_width() >= 3)	f14 = false;
						if(MTOOL.youdb[i].get_height() == 4) f17= false;
						if(MTOOL.youdb[i].get_height() == 4 && MTOOL.youdb[i].get_width() >= 2)	f18 = false;
						if(MTOOL.youdb[i].get_height() == 4 && MTOOL.youdb[i].get_width() >= 3)	f19 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 3 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f3 = false;
						if(MTOOL.youdb[i].get_width() >= 2) f4 = false;
						if(MTOOL.youdb[i].get_height() >= 2) f8 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 2)	f9 = false;
						if(MTOOL.youdb[i].get_height() >= 3) f13 = false;
						if(MTOOL.youdb[i].get_height() >= 3 && MTOOL.youdb[i].get_width() >= 2)	f14 = false;
						if(MTOOL.youdb[i].get_height() == 4) f18 = false;
						if(MTOOL.youdb[i].get_height() == 4 && MTOOL.youdb[i].get_width() >= 2)	f19 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 4 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f4 = false;
						if(MTOOL.youdb[i].get_height() >= 2) f9 = false;
						if(MTOOL.youdb[i].get_height() >= 3) f14 = false;
						if(MTOOL.youdb[i].get_height() == 4) f19 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 5 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f5 = false;
						if(MTOOL.youdb[i].get_width() >= 2)	f6 = false;
						if(MTOOL.youdb[i].get_width() >= 3)	f7 = false;
						if(MTOOL.youdb[i].get_width() == 4)	f8 = false;
						if(MTOOL.youdb[i].get_height() >= 2) f10 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 2)	f11 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 3)	f12 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() == 4)	f13 = false;
						if(MTOOL.youdb[i].get_height() >= 3) f15 = false;
						if(MTOOL.youdb[i].get_height() >= 3 && MTOOL.youdb[i].get_width() >= 2)	f16 = false;
						if(MTOOL.youdb[i].get_height() >= 3 && MTOOL.youdb[i].get_width() >= 3)	f17 = false;
						if(MTOOL.youdb[i].get_height() >= 3 && MTOOL.youdb[i].get_width() == 4)	f18 = false;
						if(MTOOL.youdb[i].get_height() == 4) f20 = false;
						if(MTOOL.youdb[i].get_height() == 4 && MTOOL.youdb[i].get_width() >= 2)	f21 = false;
						if(MTOOL.youdb[i].get_height() == 4 && MTOOL.youdb[i].get_width() >= 3)	f22 = false;
						if(MTOOL.youdb[i].get_height() == 4 && MTOOL.youdb[i].get_width() == 4)	f23 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 6 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f6 = false;
						if(MTOOL.youdb[i].get_width() >= 2)	f7 = false;
						if(MTOOL.youdb[i].get_width() >= 3)	f8 = false;
						if(MTOOL.youdb[i].get_width() == 4)	f9 = false;
						if(MTOOL.youdb[i].get_height() >= 2) f11 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 2)	f12 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 3)	f13 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() == 4)	f14 = false;
						if(MTOOL.youdb[i].get_height() >= 3) f16 = false;
						if(MTOOL.youdb[i].get_height() >= 3 && MTOOL.youdb[i].get_width() >= 2)	f17 = false;
						if(MTOOL.youdb[i].get_height() >= 3 && MTOOL.youdb[i].get_width() >= 3)	f18 = false;
						if(MTOOL.youdb[i].get_height() >= 3 && MTOOL.youdb[i].get_width() == 4)	f19 = false;
						if(MTOOL.youdb[i].get_height() == 4) f21 = false;
						if(MTOOL.youdb[i].get_height() == 4 && MTOOL.youdb[i].get_width() >= 2)	f22 = false;
						if(MTOOL.youdb[i].get_height() == 4 && MTOOL.youdb[i].get_width() >= 3)	f23 = false;
						if(MTOOL.youdb[i].get_height() == 4 && MTOOL.youdb[i].get_width() == 4)	f24 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 7 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f7 = false;
						if(MTOOL.youdb[i].get_width() >= 2)	f8 = false;
						if(MTOOL.youdb[i].get_width() >= 3)	f9 = false;
						if(MTOOL.youdb[i].get_height() >= 2) f12 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 2)	f13 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 3)	f14 = false;
						if(MTOOL.youdb[i].get_height() >= 3) f17 = false;
						if(MTOOL.youdb[i].get_height() >= 3 && MTOOL.youdb[i].get_width() >= 2)	f18 = false;
						if(MTOOL.youdb[i].get_height() >= 3 && MTOOL.youdb[i].get_width() >= 3)	f19 = false;
						if(MTOOL.youdb[i].get_height() == 4) f22 = false;
						if(MTOOL.youdb[i].get_height() == 4 && MTOOL.youdb[i].get_width() >= 2)	f23 = false;
						if(MTOOL.youdb[i].get_height() == 4 && MTOOL.youdb[i].get_width() >= 3)	f24 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 8 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f8 = false;
						if(MTOOL.youdb[i].get_width() >= 2)	f9 = false;
						if(MTOOL.youdb[i].get_height() >= 2) f13 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 2)	f14 = false;
						if(MTOOL.youdb[i].get_height() >= 3) f18 = false;
						if(MTOOL.youdb[i].get_height() >= 3 && MTOOL.youdb[i].get_width() >= 2)	f19 = false;
						if(MTOOL.youdb[i].get_height() == 4) f23 = false;
						if(MTOOL.youdb[i].get_height() == 4 && MTOOL.youdb[i].get_width() >= 2)	f24 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 9 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f9 = false;
						if(MTOOL.youdb[i].get_height() >= 2) f14 = false;
						if(MTOOL.youdb[i].get_height() >= 3) f19 = false;
						if(MTOOL.youdb[i].get_height() == 4) f24 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 10 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f10 = false;
						if(MTOOL.youdb[i].get_width() >= 2)	f11 = false;
						if(MTOOL.youdb[i].get_width() >= 3)	f12 = false;
						if(MTOOL.youdb[i].get_width() == 4) f13 = false;
						if(MTOOL.youdb[i].get_height() >= 2) f15 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 2)	f16 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 3)	f17 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() == 4)	f18 = false;
						if(MTOOL.youdb[i].get_height() >= 3) f20 = false;
						if(MTOOL.youdb[i].get_height() >= 3 && MTOOL.youdb[i].get_width() >= 2)	f21 = false;
						if(MTOOL.youdb[i].get_height() >= 3 && MTOOL.youdb[i].get_width() >= 3)	f22 = false;
						if(MTOOL.youdb[i].get_height() >= 3 && MTOOL.youdb[i].get_width() == 4)	f23 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 11 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f11 = false;
						if(MTOOL.youdb[i].get_width() >= 2)	f12 = false;
						if(MTOOL.youdb[i].get_width() >= 3) f13 = false;
						if(MTOOL.youdb[i].get_width() == 4) f14 = false;
						if(MTOOL.youdb[i].get_height() >= 2) f16 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 2)	f17 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 3)	f18 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() == 4)	f19 = false;
						if(MTOOL.youdb[i].get_height() >= 3) f21 = false;
						if(MTOOL.youdb[i].get_height() >= 3 && MTOOL.youdb[i].get_width() >= 2)	f22 = false;
						if(MTOOL.youdb[i].get_height() >= 3 && MTOOL.youdb[i].get_width() >= 3)	f23 = false;
						if(MTOOL.youdb[i].get_height() >= 3 && MTOOL.youdb[i].get_width() == 4)	f24 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 12 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f12 = false;
						if(MTOOL.youdb[i].get_width() >= 2) f13 = false;
						if(MTOOL.youdb[i].get_width() >= 3) f14 = false;
						if(MTOOL.youdb[i].get_height() >= 2) f17 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 2)	f18 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 3)	f19 = false;
						if(MTOOL.youdb[i].get_height() >= 3) f22 = false;
						if(MTOOL.youdb[i].get_height() >= 3 && MTOOL.youdb[i].get_width() >= 2)	f23 = false;
						if(MTOOL.youdb[i].get_height() >= 3 && MTOOL.youdb[i].get_width() >= 3)	f24 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 13 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f13 = false;
						if(MTOOL.youdb[i].get_width() >= 2) f14 = false;
						if(MTOOL.youdb[i].get_height() >= 2) f18 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 2)	f19 = false;
						if(MTOOL.youdb[i].get_height() >= 3) f23 = false;
						if(MTOOL.youdb[i].get_height() >= 3 && MTOOL.youdb[i].get_width() >= 2)	f24 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 14 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f14 = false;
						if(MTOOL.youdb[i].get_height() >= 2) f19 = false;
						if(MTOOL.youdb[i].get_height() >= 3) f24 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 15 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f15 = false;
						if(MTOOL.youdb[i].get_width() >= 2)	f16 = false;
						if(MTOOL.youdb[i].get_width() >= 3)	f17 = false;
						if(MTOOL.youdb[i].get_width() == 4)	f18 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 2)	f21 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 3)	f22 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() == 4)	f23 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 16 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f16 = false;
						if(MTOOL.youdb[i].get_width() >= 2)	f17 = false;
						if(MTOOL.youdb[i].get_width() >= 3)	f18 = false;
						if(MTOOL.youdb[i].get_width() == 4)	f19 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 2)	f22 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 3)	f23 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() == 4)	f24 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 17 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f17 = false;
						if(MTOOL.youdb[i].get_width() >= 2)	f18 = false;
						if(MTOOL.youdb[i].get_width() >= 3)	f19 = false;
						if(MTOOL.youdb[i].get_height() >= 2) f22 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 2)	f23 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 3)	f24 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 18 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f18 = false;
						if(MTOOL.youdb[i].get_width() >= 2)	f19 = false;
						if(MTOOL.youdb[i].get_height() >= 2) f23 = false;
						if(MTOOL.youdb[i].get_height() >= 2 && MTOOL.youdb[i].get_width() >= 2)	f24 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 19 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f19 = false;
						if(MTOOL.youdb[i].get_height() >= 2) f24 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 20 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f20 = false;
						if(MTOOL.youdb[i].get_width() >= 2)	f21 = false;
						if(MTOOL.youdb[i].get_width() >= 3)	f22 = false;
						if(MTOOL.youdb[i].get_width() == 4)	f23 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 21 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f21 = false;
						if(MTOOL.youdb[i].get_width() >= 2)	f22 = false;
						if(MTOOL.youdb[i].get_width() >= 3)	f23 = false;
						if(MTOOL.youdb[i].get_width() == 4)	f24 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 22 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f22 = false;
						if(MTOOL.youdb[i].get_width() >= 2)	f23 = false;
						if(MTOOL.youdb[i].get_width() >= 3)	f24 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 23 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f23 = false;
						if(MTOOL.youdb[i].get_width() >= 2)	f24 = false;
					}
					if(MTOOL.youdb[i].get_feld() == 24 && MTOOL.youdb[i].get_page() == seite && MTOOL.youdb[i].getID() != 0){
						f24 = false;
					}
				}
				if(b == 1 && h == 1) {
					if(f0) MTOOL.db.addEntry(new YouEntry(seite, 0, b, h, it, op, ""));
					else if(f1) MTOOL.db.addEntry(new YouEntry(seite, 1, b, h, it, op, ""));
					else if(f2) MTOOL.db.addEntry(new YouEntry(seite, 2, b, h, it, op, ""));
					else if(f3) MTOOL.db.addEntry(new YouEntry(seite, 3, b, h, it, op, ""));
					else if(f4) MTOOL.db.addEntry(new YouEntry(seite, 4, b, h, it, op, ""));
					else if(f5) MTOOL.db.addEntry(new YouEntry(seite, 5, b, h, it, op, ""));
					else if(f6) MTOOL.db.addEntry(new YouEntry(seite, 6, b, h, it, op, ""));
					else if(f7) MTOOL.db.addEntry(new YouEntry(seite, 7, b, h, it, op, ""));
					else if(f8) MTOOL.db.addEntry(new YouEntry(seite, 8, b, h, it, op, ""));
					else if(f9) MTOOL.db.addEntry(new YouEntry(seite, 9, b, h, it, op, ""));
					else if(f10) MTOOL.db.addEntry(new YouEntry(seite, 10, b, h, it, op, ""));
					else if(f11) MTOOL.db.addEntry(new YouEntry(seite, 11, b, h, it, op, ""));
					else if(f12) MTOOL.db.addEntry(new YouEntry(seite, 12, b, h, it, op, ""));
					else if(f13) MTOOL.db.addEntry(new YouEntry(seite, 13, b, h, it, op, ""));
					else if(f14) MTOOL.db.addEntry(new YouEntry(seite, 14, b, h, it, op, ""));
					else if(f15) MTOOL.db.addEntry(new YouEntry(seite, 15, b, h, it, op, ""));
					else if(f16) MTOOL.db.addEntry(new YouEntry(seite, 16, b, h, it, op, ""));
					else if(f17) MTOOL.db.addEntry(new YouEntry(seite, 17, b, h, it, op, ""));
					else if(f18) MTOOL.db.addEntry(new YouEntry(seite, 18, b, h, it, op, ""));
					else if(f19) MTOOL.db.addEntry(new YouEntry(seite, 19, b, h, it, op, ""));
					else if(f20) MTOOL.db.addEntry(new YouEntry(seite, 20, b, h, it, op, ""));
					else if(f21) MTOOL.db.addEntry(new YouEntry(seite, 21, b, h, it, op, ""));
					else if(f22) MTOOL.db.addEntry(new YouEntry(seite, 22, b, h, it, op, ""));
					else if(f23) MTOOL.db.addEntry(new YouEntry(seite, 23, b, h, it, op, ""));
					else if(f24) MTOOL.db.addEntry(new YouEntry(seite, 24, b, h, it, op, ""));
					else ok = false;
				}
				if(b == 2 && h == 1) {
					if(f0 && f1) MTOOL.db.addEntry(new YouEntry(seite, 0, b, h, it, op, ""));
					else if(f1 && f2) MTOOL.db.addEntry(new YouEntry(seite, 1, b, h, it, op, ""));
					else if(f2 && f3) MTOOL.db.addEntry(new YouEntry(seite, 2, b, h, it, op, ""));
					else if(f3 && f4) MTOOL.db.addEntry(new YouEntry(seite, 3, b, h, it, op, ""));
					else if(f5 && f6) MTOOL.db.addEntry(new YouEntry(seite, 5, b, h, it, op, ""));
					else if(f6 && f7) MTOOL.db.addEntry(new YouEntry(seite, 6, b, h, it, op, ""));
					else if(f7 && f8) MTOOL.db.addEntry(new YouEntry(seite, 7, b, h, it, op, ""));
					else if(f8 && f9) MTOOL.db.addEntry(new YouEntry(seite, 8, b, h, it, op, ""));
					else if(f10 && f11) MTOOL.db.addEntry(new YouEntry(seite, 10, b, h, it, op, ""));
					else if(f11 && f12) MTOOL.db.addEntry(new YouEntry(seite, 11, b, h, it, op, ""));
					else if(f12 && f13) MTOOL.db.addEntry(new YouEntry(seite, 12, b, h, it, op, ""));
					else if(f13 && f14) MTOOL.db.addEntry(new YouEntry(seite, 13, b, h, it, op, ""));
					else if(f15 && f16) MTOOL.db.addEntry(new YouEntry(seite, 15, b, h, it, op, ""));
					else if(f16 && f17) MTOOL.db.addEntry(new YouEntry(seite, 16, b, h, it, op, ""));
					else if(f17 && f18) MTOOL.db.addEntry(new YouEntry(seite, 17, b, h, it, op, ""));
					else if(f18 && f19) MTOOL.db.addEntry(new YouEntry(seite, 18, b, h, it, op, ""));
					else if(f20 && f21) MTOOL.db.addEntry(new YouEntry(seite, 20, b, h, it, op, ""));
					else if(f21 && f22) MTOOL.db.addEntry(new YouEntry(seite, 21, b, h, it, op, ""));
					else if(f22 && f23) MTOOL.db.addEntry(new YouEntry(seite, 22, b, h, it, op, ""));
					else if(f23 && f24) MTOOL.db.addEntry(new YouEntry(seite, 23, b, h, it, op, ""));
					else ok = false;
				}
				if(b == 3 && h == 1) {
					if(f0 && f1 && f2) MTOOL.db.addEntry(new YouEntry(seite, 0, b, h, it, op, ""));
					else if(f1 && f2 && f3) MTOOL.db.addEntry(new YouEntry(seite, 1, b, h, it, op, ""));
					else if(f2 && f3 && f4) MTOOL.db.addEntry(new YouEntry(seite, 2, b, h, it, op, ""));
					else if(f5 && f6 && f7) MTOOL.db.addEntry(new YouEntry(seite, 5, b, h, it, op, ""));
					else if(f6 && f7 && f8) MTOOL.db.addEntry(new YouEntry(seite, 6, b, h, it, op, ""));
					else if(f7 && f8 && f9) MTOOL.db.addEntry(new YouEntry(seite, 7, b, h, it, op, ""));
					else if(f10 && f11 && f12) MTOOL.db.addEntry(new YouEntry(seite, 10, b, h, it, op, ""));
					else if(f11 && f12 && f13) MTOOL.db.addEntry(new YouEntry(seite, 11, b, h, it, op, ""));
					else if(f12 && f13 && f14) MTOOL.db.addEntry(new YouEntry(seite, 12, b, h, it, op, ""));
					else if(f15 && f16 && f17) MTOOL.db.addEntry(new YouEntry(seite, 15, b, h, it, op, ""));
					else if(f16 && f17 && f18) MTOOL.db.addEntry(new YouEntry(seite, 16, b, h, it, op, ""));
					else if(f17 && f18 && f19) MTOOL.db.addEntry(new YouEntry(seite, 17, b, h, it, op, ""));
					else if(f20 && f21 && f22) MTOOL.db.addEntry(new YouEntry(seite, 20, b, h, it, op, ""));
					else if(f21 && f22 && f23) MTOOL.db.addEntry(new YouEntry(seite, 21, b, h, it, op, ""));
					else if(f22 && f23 && f24) MTOOL.db.addEntry(new YouEntry(seite, 22, b, h, it, op, ""));
					else ok = false;
				}
				if(b == 4 && h == 1) {
					if(f0 && f1 && f2 && f3) MTOOL.db.addEntry(new YouEntry(seite, 0, b, h, it, op, ""));
					else if(f1 && f2 && f3 && f4) MTOOL.db.addEntry(new YouEntry(seite, 1, b, h, it, op, ""));
					else if(f5 && f6 && f7 && f8) MTOOL.db.addEntry(new YouEntry(seite, 5, b, h, it, op, ""));
					else if(f6 && f7 && f8 && f9) MTOOL.db.addEntry(new YouEntry(seite, 6, b, h, it, op, ""));
					else if(f10 && f11 && f12 && f13) MTOOL.db.addEntry(new YouEntry(seite, 10, b, h, it, op, ""));
					else if(f11 && f12 && f13 && f14) MTOOL.db.addEntry(new YouEntry(seite, 11, b, h, it, op, ""));
					else if(f15 && f16 && f17 && f18) MTOOL.db.addEntry(new YouEntry(seite, 15, b, h, it, op, ""));
					else if(f16 && f17 && f18 && f19) MTOOL.db.addEntry(new YouEntry(seite, 16, b, h, it, op, ""));
					else if(f20 && f21 && f22 && f23) MTOOL.db.addEntry(new YouEntry(seite, 20, b, h, it, op, ""));
					else if(f21 && f22 && f23 && f24) MTOOL.db.addEntry(new YouEntry(seite, 21, b, h, it, op, ""));
					else ok = false;
				}
				if(b == 1 && h == 2) {
					if(f0 && f5) MTOOL.db.addEntry(new YouEntry(seite, 0, b, h, it, op, ""));
					else if(f1 && f6) MTOOL.db.addEntry(new YouEntry(seite, 1, b, h, it, op, ""));
					else if(f2 && f7) MTOOL.db.addEntry(new YouEntry(seite, 2, b, h, it, op, ""));
					else if(f3 && f8) MTOOL.db.addEntry(new YouEntry(seite, 3, b, h, it, op, ""));
					else if(f4 && f9) MTOOL.db.addEntry(new YouEntry(seite, 4, b, h, it, op, ""));
					else if(f5 && f10) MTOOL.db.addEntry(new YouEntry(seite, 5, b, h, it, op, ""));
					else if(f6 && f11) MTOOL.db.addEntry(new YouEntry(seite, 6, b, h, it, op, ""));
					else if(f7 && f12) MTOOL.db.addEntry(new YouEntry(seite, 7, b, h, it, op, ""));
					else if(f8 && f13) MTOOL.db.addEntry(new YouEntry(seite, 8, b, h, it, op, ""));
					else if(f9 && f14) MTOOL.db.addEntry(new YouEntry(seite, 9, b, h, it, op, ""));
					else if(f10 && f15) MTOOL.db.addEntry(new YouEntry(seite, 10, b, h, it, op, ""));
					else if(f11 && f16) MTOOL.db.addEntry(new YouEntry(seite, 11, b, h, it, op, ""));
					else if(f12 && f17) MTOOL.db.addEntry(new YouEntry(seite, 12, b, h, it, op, ""));
					else if(f13 && f18) MTOOL.db.addEntry(new YouEntry(seite, 13, b, h, it, op, ""));
					else if(f14 && f19) MTOOL.db.addEntry(new YouEntry(seite, 14, b, h, it, op, ""));
					else if(f15 && f20) MTOOL.db.addEntry(new YouEntry(seite, 15, b, h, it, op, ""));
					else if(f16 && f21) MTOOL.db.addEntry(new YouEntry(seite, 16, b, h, it, op, ""));
					else if(f17 && f22) MTOOL.db.addEntry(new YouEntry(seite, 17, b, h, it, op, ""));
					else if(f18 && f23) MTOOL.db.addEntry(new YouEntry(seite, 18, b, h, it, op, ""));
					else if(f19 && f24) MTOOL.db.addEntry(new YouEntry(seite, 19, b, h, it, op, ""));
					else ok = false;
				}
				if(b == 1 && h == 3) {
					if(f0 && f5 && f10) MTOOL.db.addEntry(new YouEntry(seite, 0, b, h, it, op, ""));
					else if(f1 && f6 && f11) MTOOL.db.addEntry(new YouEntry(seite, 1, b, h, it, op, ""));
					else if(f2 && f7 && f12) MTOOL.db.addEntry(new YouEntry(seite, 2, b, h, it, op, ""));
					else if(f3 && f8 && f13) MTOOL.db.addEntry(new YouEntry(seite, 3, b, h, it, op, ""));
					else if(f4 && f9 && f14) MTOOL.db.addEntry(new YouEntry(seite, 4, b, h, it, op, ""));
					else if(f5 && f10 && f15) MTOOL.db.addEntry(new YouEntry(seite, 5, b, h, it, op, ""));
					else if(f6 && f11 && f16) MTOOL.db.addEntry(new YouEntry(seite, 6, b, h, it, op, ""));
					else if(f7 && f12 && f17) MTOOL.db.addEntry(new YouEntry(seite, 7, b, h, it, op, ""));
					else if(f8 && f13 && f18) MTOOL.db.addEntry(new YouEntry(seite, 8, b, h, it, op, ""));
					else if(f9 && f14 && f19) MTOOL.db.addEntry(new YouEntry(seite, 9, b, h, it, op, ""));
					else if(f10 && f15 && f20) MTOOL.db.addEntry(new YouEntry(seite, 10, b, h, it, op, ""));
					else if(f11 && f16 && f21) MTOOL.db.addEntry(new YouEntry(seite, 11, b, h, it, op, ""));
					else if(f12 && f17 && f22) MTOOL.db.addEntry(new YouEntry(seite, 12, b, h, it, op, ""));
					else if(f13 && f18 && f23) MTOOL.db.addEntry(new YouEntry(seite, 13, b, h, it, op, ""));
					else if(f14 && f19 && f24) MTOOL.db.addEntry(new YouEntry(seite, 14, b, h, it, op, ""));
					else ok = false;
				}
				if(b == 1 && h == 4) {
					if(f0 && f5 && f10 && f15) MTOOL.db.addEntry(new YouEntry(seite, 0, b, h, it, op, ""));
					else if(f1 && f6 && f11 && f16) MTOOL.db.addEntry(new YouEntry(seite, 1, b, h, it, op, ""));
					else if(f2 && f7 && f12 && f17) MTOOL.db.addEntry(new YouEntry(seite, 2, b, h, it, op, ""));
					else if(f3 && f8 && f13 && f18) MTOOL.db.addEntry(new YouEntry(seite, 3, b, h, it, op, ""));
					else if(f4 && f9 && f14 && f19) MTOOL.db.addEntry(new YouEntry(seite, 4, b, h, it, op, ""));
					else if(f5 && f10 && f15 && f20) MTOOL.db.addEntry(new YouEntry(seite, 5, b, h, it, op, ""));
					else if(f6 && f11 && f16 && f21) MTOOL.db.addEntry(new YouEntry(seite, 6, b, h, it, op, ""));
					else if(f7 && f12 && f17 && f22) MTOOL.db.addEntry(new YouEntry(seite, 7, b, h, it, op, ""));
					else if(f8 && f13 && f18 && f23) MTOOL.db.addEntry(new YouEntry(seite, 8, b, h, it, op, ""));
					else if(f9 && f14 && f19 && f24) MTOOL.db.addEntry(new YouEntry(seite, 9, b, h, it, op, ""));
					else ok = false;
				}
				if(b == 2 && h == 2) {
					if(f0 && f1 && f5 && f6) MTOOL.db.addEntry(new YouEntry(seite, 0, b, h, it, op, ""));
					else if(f1 && f2 && f6 && f7) MTOOL.db.addEntry(new YouEntry(seite, 1, b, h, it, op, ""));
					else if(f2 && f3 && f7 && f8) MTOOL.db.addEntry(new YouEntry(seite, 2, b, h, it, op, ""));
					else if(f3 && f4 && f8 && f9) MTOOL.db.addEntry(new YouEntry(seite, 3, b, h, it, op, ""));
					else if(f5 && f6 && f10 && f11) MTOOL.db.addEntry(new YouEntry(seite, 5, b, h, it, op, ""));
					else if(f6 && f7 && f11 && f12) MTOOL.db.addEntry(new YouEntry(seite, 6, b, h, it, op, ""));
					else if(f7 && f8 && f12 && f13) MTOOL.db.addEntry(new YouEntry(seite, 7, b, h, it, op, ""));
					else if(f8 && f9 && f13 && f14) MTOOL.db.addEntry(new YouEntry(seite, 8, b, h, it, op, ""));
					else if(f10 && f11 && f14 && f15) MTOOL.db.addEntry(new YouEntry(seite, 10, b, h, it, op, ""));
					else if(f11 && f12 && f16 && f17) MTOOL.db.addEntry(new YouEntry(seite, 11, b, h, it, op, ""));
					else if(f12 && f13 && f17 && f18) MTOOL.db.addEntry(new YouEntry(seite, 12, b, h, it, op, ""));
					else if(f13 && f14 && f18 && f19) MTOOL.db.addEntry(new YouEntry(seite, 13, b, h, it, op, ""));
					else if(f15 && f16 && f20 && f21) MTOOL.db.addEntry(new YouEntry(seite, 15, b, h, it, op, ""));
					else if(f16 && f17 && f21 && f22) MTOOL.db.addEntry(new YouEntry(seite, 16, b, h, it, op, ""));
					else if(f17 && f18 && f22 && f23) MTOOL.db.addEntry(new YouEntry(seite, 17, b, h, it, op, ""));
					else if(f18 && f19 && f23 && f24) MTOOL.db.addEntry(new YouEntry(seite, 18, b, h, it, op, ""));
					else ok = false;
				}
				if(b == 2 && h == 3) {
					if(f0 && f1 && f5 && f6 && f10 && f11) MTOOL.db.addEntry(new YouEntry(seite, 0, b, h, it, op, ""));
					else if(f1 && f2 && f6 && f7 && f11 && f12) MTOOL.db.addEntry(new YouEntry(seite, 1, b, h, it, op, ""));
					else if(f2 && f3 && f7 && f8 && f12 && f13) MTOOL.db.addEntry(new YouEntry(seite, 2, b, h, it, op, ""));
					else if(f3 && f4 && f8 && f9 && f13 && f14) MTOOL.db.addEntry(new YouEntry(seite, 3, b, h, it, op, ""));
					else if(f5 && f6 && f10 && f11 && f15 && f16) MTOOL.db.addEntry(new YouEntry(seite, 5, b, h, it, op, ""));
					else if(f6 && f7 && f11 && f12 && f16 && f17) MTOOL.db.addEntry(new YouEntry(seite, 6, b, h, it, op, ""));
					else if(f7 && f8 && f12 && f13 && f17 && f18) MTOOL.db.addEntry(new YouEntry(seite, 7, b, h, it, op, ""));
					else if(f8 && f9 && f13 && f14 && f18 && f19) MTOOL.db.addEntry(new YouEntry(seite, 8, b, h, it, op, ""));
					else if(f10 && f11 && f15 && f16 && f20 && f21) MTOOL.db.addEntry(new YouEntry(seite, 10, b, h, it, op, ""));
					else if(f11 && f12 && f16 && f17 && f21 && f22) MTOOL.db.addEntry(new YouEntry(seite, 11, b, h, it, op, ""));
					else if(f12 && f13 && f17 && f18 && f22 && f23) MTOOL.db.addEntry(new YouEntry(seite, 12, b, h, it, op, ""));
					else if(f13 && f14 && f18 && f19 && f23 && f24) MTOOL.db.addEntry(new YouEntry(seite, 13, b, h, it, op, ""));
					else ok = false;
				}
				if(b == 2 && h == 4) {
					if(f0 && f1 && f5 && f6 && f10 && f11 && f15 && f16) MTOOL.db.addEntry(new YouEntry(seite, 0, b, h, it, op, ""));
					else if(f1 && f2 && f6 && f7 && f11 && f12 && f16 && f17) MTOOL.db.addEntry(new YouEntry(seite, 1, b, h, it, op, ""));
					else if(f2 && f3 && f7 && f8 && f12 && f13 && f17 && f18) MTOOL.db.addEntry(new YouEntry(seite, 2, b, h, it, op, ""));
					else if(f3 && f4 && f8 && f9 && f13 && f14 && f18 && f19) MTOOL.db.addEntry(new YouEntry(seite, 3, b, h, it, op, ""));
					else if(f5 && f6 && f10 && f11 && f15 && f16 && f20 && f21) MTOOL.db.addEntry(new YouEntry(seite, 5, b, h, it, op, ""));
					else if(f6 && f7 && f11 && f12 && f16 && f17 && f21 && f22) MTOOL.db.addEntry(new YouEntry(seite, 6, b, h, it, op, ""));
					else if(f7 && f8 && f12 && f13 && f17 && f18 && f22 && f23) MTOOL.db.addEntry(new YouEntry(seite, 7, b, h, it, op, ""));
					else if(f8 && f9 && f13 && f14 && f18 && f19 && f23 && f24) MTOOL.db.addEntry(new YouEntry(seite, 8, b, h, it, op, ""));
					else ok = false;
				}
				if(b == 3 && h == 2) {
					if(f0 && f1 && f2 && f5 && f6 && f7) MTOOL.db.addEntry(new YouEntry(seite, 0, b, h, it, op, ""));
					else if(f1 && f2 && f3 && f6 && f7 && f8) MTOOL.db.addEntry(new YouEntry(seite, 1, b, h, it, op, ""));
					else if(f2 && f3 && f4 && f7 && f8 && f9) MTOOL.db.addEntry(new YouEntry(seite, 2, b, h, it, op, ""));
					else if(f5 && f6 && f7 && f10 && f11 && f12) MTOOL.db.addEntry(new YouEntry(seite, 5, b, h, it, op, ""));
					else if(f6 && f7 && f8 && f11 && f12 && f13) MTOOL.db.addEntry(new YouEntry(seite, 6, b, h, it, op, ""));
					else if(f7 && f8 && f9 && f12 && f13 && f14) MTOOL.db.addEntry(new YouEntry(seite, 7, b, h, it, op, ""));
					else if(f10 && f11 && f12 && f15 && f16 && f17) MTOOL.db.addEntry(new YouEntry(seite, 10, b, h, it, op, ""));
					else if(f11 && f12 && f13 && f16 && f17 && f18) MTOOL.db.addEntry(new YouEntry(seite, 11, b, h, it, op, ""));
					else if(f12 && f13 && f14 && f17 && f18 && f19) MTOOL.db.addEntry(new YouEntry(seite, 12, b, h, it, op, ""));
					else if(f15 && f16 && f17 && f20 && f21 && f22) MTOOL.db.addEntry(new YouEntry(seite, 15, b, h, it, op, ""));
					else if(f16 && f17 && f18 && f21 && f22 && f23) MTOOL.db.addEntry(new YouEntry(seite, 16, b, h, it, op, ""));
					else if(f17 && f18 && f19 && f22 && f23 && f24) MTOOL.db.addEntry(new YouEntry(seite, 17, b, h, it, op, ""));
					else ok = false;
				}
				if(b == 4 && h == 2) {
					if(f0 && f1 && f2 && f3 && f5 && f6 && f7 && f8) MTOOL.db.addEntry(new YouEntry(seite, 0, b, h, it, op, ""));
					else if(f1 && f2 && f3 && f4 && f6 && f7 && f8 && f9) MTOOL.db.addEntry(new YouEntry(seite, 1, b, h, it, op, ""));
					else if(f5 && f6 && f7 && f8 && f10 && f11 && f12 && f13) MTOOL.db.addEntry(new YouEntry(seite, 5, b, h, it, op, ""));
					else if(f6 && f7 && f8 && f9 && f11 && f12 && f13 && f14) MTOOL.db.addEntry(new YouEntry(seite, 6, b, h, it, op, ""));
					else if(f10 && f11 && f12 && f13 && f15 && f16 && f17 && f18) MTOOL.db.addEntry(new YouEntry(seite, 10, b, h, it, op, ""));
					else if(f11 && f12 && f13 && f14 && f16 && f17 && f18 && f19) MTOOL.db.addEntry(new YouEntry(seite, 11, b, h, it, op, ""));
					else if(f15 && f16 && f17 && f18 && f20 && f21 && f22 && f23) MTOOL.db.addEntry(new YouEntry(seite, 15, b, h, it, op, ""));
					else if(f16 && f17 && f18 && f19 && f21 && f22 && f23 && f24) MTOOL.db.addEntry(new YouEntry(seite, 16, b, h, it, op, ""));
					else ok = false;
				}
				if(b == 3 && h == 3) {
					if(f0 && f1 && f2 && f5 && f6 && f7 && f10 && f11 && f12) MTOOL.db.addEntry(new YouEntry(seite, 0, b, h, it, op, ""));
					else if(f1 && f2 && f3 && f6 && f7 && f8 && f11 && f12 && f13) MTOOL.db.addEntry(new YouEntry(seite, 1, b, h, it, op, ""));
					else if(f2 && f3 && f4 && f7 && f8 && f9 && f12 && f13 && f14) MTOOL.db.addEntry(new YouEntry(seite, 2, b, h, it, op, ""));
					else if(f5 && f6 && f7 && f10 && f11 && f12 && f15 && f16 && f17) MTOOL.db.addEntry(new YouEntry(seite, 5, b, h, it, op, ""));
					else if(f6 && f7 && f8 && f11 && f12 && f13 && f16 && f17 && f18) MTOOL.db.addEntry(new YouEntry(seite, 6, b, h, it, op, ""));
					else if(f7 && f8 && f9 && f12 && f13 && f14 && f17 && f18 && f19) MTOOL.db.addEntry(new YouEntry(seite, 7, b, h, it, op, ""));
					else if(f10 && f11 && f12 && f15 && f16 && f17 && f20 && f21 && f22) MTOOL.db.addEntry(new YouEntry(seite, 10, b, h, it, op, ""));
					else if(f11 && f12 && f13 && f16 && f17 && f18 && f21 && f22 && f23) MTOOL.db.addEntry(new YouEntry(seite, 11, b, h, it, op, ""));
					else if(f12 && f13 && f14 && f17 && f18 && f19 && f22 && f23 && f24) MTOOL.db.addEntry(new YouEntry(seite, 12, b, h, it, op, ""));
					else ok = false;
				}
				if(b == 3 && h == 4) {
					if(f0 && f1 && f2 && f5 && f6 && f7 && f10 && f11 && f12 && f15 && f16 && f17) MTOOL.db.addEntry(new YouEntry(seite, 0, b, h, it, op, ""));
					else if(f1 && f2 && f3 && f6 && f7 && f8 && f11 && f12 && f13 && f16 && f17 && f18) MTOOL.db.addEntry(new YouEntry(seite, 1, b, h, it, op, ""));
					else if(f2 && f3 && f4 && f7 && f8 && f9 && f12 && f13 && f14 && f17 && f18 && f19) MTOOL.db.addEntry(new YouEntry(seite, 2, b, h, it, op, ""));
					else if(f5 && f6 && f7 && f10 && f11 && f12 && f15 && f16 && f17 && f20 && f21 && f22)
						MTOOL.db.addEntry(new YouEntry(seite, 5, b, h, it, op, ""));
					else if(f6 && f7 && f8 && f11 && f12 && f13 && f16 && f17 && f18 && f21 && f22 && f23)
						MTOOL.db.addEntry(new YouEntry(seite, 6, b, h, it, op, ""));
					else if(f7 && f8 && f9 && f12 && f13 && f14 && f17 && f18 && f19 && f22 && f23 && f24)
						MTOOL.db.addEntry(new YouEntry(seite, 7, b, h, it, op, ""));
					else ok = false;
				}
				if(b == 4 && h == 3) {
					if(f0 && f1 && f2 && f3 && f5 && f6 && f7 && f8 && f10 && f11 && f12 && f13) MTOOL.db.addEntry(new YouEntry(seite, 0, b, h, it, op, ""));
					else if(f1 && f2 && f3 && f4 && f6 && f7 && f8 && f9 && f11 && f12 && f13 && f14) MTOOL.db.addEntry(new YouEntry(seite, 1, b, h, it, op, ""));
					else if(f5 && f6 && f7 && f8 && f10 && f11 && f12 && f13 && f15 && f16 && f17 && f18) MTOOL.db.addEntry(new YouEntry(seite, 5, b, h, it, op, ""));
					else if(f6 && f7 && f8 && f9 && f11 && f12 && f13 && f14 && f16 && f17 && f18 && f19) MTOOL.db.addEntry(new YouEntry(seite, 6, b, h, it, op, ""));
					else if(f10 && f11 && f12 && f13 && f15 && f16 && f17 && f18 && f20 && f21 && f22 && f23)
						MTOOL.db.addEntry(new YouEntry(seite, 10, b, h, it, op, ""));
					else if(f11 && f12 && f13 && f14 && f16 && f17 && f18 && f19 && f21 && f22 && f23 && f24)
						MTOOL.db.addEntry(new YouEntry(seite, 11, b, h, it, op, ""));
					else ok = false;
				}
				if(b == 4 && h == 4) {
					if(f0 && f1 && f2 && f3 && f5 && f6 && f7 && f8 && f10 && f11 && f12 && f13 && f15 && f16 && f17 && f18) 
						MTOOL.db.addEntry(new YouEntry(seite, 0, b, h, it, op, ""));
					else if(f1 && f2 && f3 && f4 && f6 && f7 && f8 && f9 && f11 && f12 && f13 && f14 && f16 && f17 && f18 && f19) 
						MTOOL.db.addEntry(new YouEntry(seite, 1, b, h, it, op, ""));
					else if(f5 && f6 && f7 && f8 && f10 && f11 && f12 && f13 && f15 && f16 && f17 && f18 && f20 && f21 && f22 && f23) 
						MTOOL.db.addEntry(new YouEntry(seite, 5, b, h, it, op, ""));
					else if(f6 && f7 && f8 && f9 && f11 && f12 && f13 && f14 && f16 && f17 && f18 && f19 && f21 && f22 && f23 && f24) 
						MTOOL.db.addEntry(new YouEntry(seite, 6, b, h, it, op, ""));
					else ok = false;
				}
			}
			if(ok) {
				MTOOL.db.close();
		        MTOOL.you_changed = true;
				finish();
			} else {
				schritt = 3;
				Toast.makeText(getApplicationContext(), R.string.no_place, Toast.LENGTH_LONG).show();
				bnext.setText(android.R.string.cancel);
			}
		}
		else if(schritt == 3)
			finish();
	}
	
	public void back() {
		if(schritt > 0) {
			schritt--;
			source = "back";
			main();
			bnext.setEnabled(true);
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
}