package m.tool;

import java.util.ArrayList;
import java.util.regex.Pattern;

import m.tool.stuff.ProfileEntry;
import m.tool.stuff.aiitem;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Profiles extends Activity{
	
	ListView listView;
	Button b, b2, b3, b4;
	ImageButton b5;
	ArrayList<ProfileEntry> profiles;
	ArrayList<String> names;
	EditText nameedit;
	aiitem[] pi = new aiitem[13];
	aiitem style;
	int[] arrays = {R.array.onoff_array, R.array.onoff_array, R.array.onoff_array, R.array.ptimeout_array, R.array.pbrightness_array, R.array.onoff_array,
			R.array.pvolumes_array, R.array.onoff_array, R.array.onoff_array, R.array.onoff_array, R.array.onoff_array, R.array.onoff_array};
	ArrayList<String> inhalt;
	ArrayList<Integer> style_str;
	ProfileEntry profile;
	public static int[] images = {R.drawable.settings, R.drawable.home, R.drawable.pen, R.drawable.computer, R.drawable.alarm, R.drawable.earth, R.drawable.calevent,
			R.drawable.laptop, R.drawable.map, R.drawable.heart, R.drawable.star, R.drawable.play, R.drawable.person, R.drawable.twoperson, R.drawable.threeperson,
			R.drawable.way, R.drawable.paperplane, R.drawable.headphone, R.drawable.headset, R.drawable.point, R.drawable.warn, R.drawable.realwarn,
			R.drawable.streetsign, R.drawable.game, R.drawable.cloud, R.drawable.plane};
	public static int[] images_light = {R.drawable.settings_light, R.drawable.home_light, R.drawable.pen_light, R.drawable.computer_light, R.drawable.alarm_light,
		R.drawable.earth_light, R.drawable.calevent_light, R.drawable.laptop_light, R.drawable.map_light, R.drawable.heart_light, R.drawable.star_light,
		R.drawable.play_light, R.drawable.person_light, R.drawable.twoperson_light, R.drawable.threeperson_light, R.drawable.way_light, R.drawable.paperplane_light,
		R.drawable.headphone_light, R.drawable.headset_light, R.drawable.point_light, R.drawable.warn_light, R.drawable.realwarn_light, R.drawable.streetsign_light,
		R.drawable.game_light, R.drawable.cloud_light, R.drawable.plane_light};
	int selected_item;
	int pre_selected_item;
	int io;
	int schritt = 0;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profiles);
        create();
        first();
	}
	
	public void create() {
		b = (Button)findViewById(R.id.pcancel);
    	b.setOnClickListener(new OnClickListener(){
			public void onClick(View arg0) {
				finish();
			}
    	});
    	b2 = (Button)findViewById(R.id.pback);
    	b2.setOnClickListener(new OnClickListener(){
			public void onClick(View arg0) {
				first();
			}
    	});
    	b3 = (Button)findViewById(R.id.pnext);
    	b3.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				if(profile.get_name().equals(getResources().getString(R.string.plane))) {
					inhalt.set(inhalt.size()-1, pi[0].rb.isChecked() ? "1" : "0");
					profile.set_inhalt(inhalt.toString().replace(", ",";").substring(1, inhalt.toString().replace(", ",";").length()-1));
				}
				if(profile.getID() != 0) {
					MTOOL.db.updateEntry(profile);
					first();
				}
				else if(nameedit.getText().length() != 0  && !(names.contains(profile.get_name()))) {
					MTOOL.db.addEntry(profile);
					first();
				}
				else
					Toast.makeText(getApplicationContext(), R.string.pnerror, Toast.LENGTH_SHORT).show();
				MTOOL.db.close();
			}
    	});
    	b4 = (Button)findViewById(R.id.pactivate);
    	b4.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				if(profile.getID() != 0)
					MTOOL.db.updateEntry(profile);
				else if(nameedit.getText().length() != 0  && !(names.contains(profile.get_name())))
					MTOOL.db.addEntry(profile);
				MTOOL.db.close();
				MTOOL.activate_profile(profile);
				finish();
			}
    	});
    	b5 = (ImageButton)findViewById(R.id.pdelete);
    	b5.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				if(profile.getID() != 0) {
					if(profile.get_name().equals(getResources().getString(R.string.plane))) {
						AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Profiles.this);
						alertDialogBuilder
							.setPositiveButton(android.R.string.ok,	new DialogInterface.OnClickListener() {
						    	public void onClick(DialogInterface dialog, int id) {
						    		MTOOL.db.deleteEntry(profile);
									MTOOL.db.close();
						    	}
							})	
							.setNegativeButton(android.R.string.cancel,	new DialogInterface.OnClickListener() {
						    	public void onClick(DialogInterface dialog, int id) {
						    		dialog.cancel();
						    	}
							})
							.setTitle(R.string.plane)
							.setMessage(R.string.delete_plane_note)
							.show();
					} else {
						MTOOL.db.deleteEntry(profile);
						MTOOL.db.close();
					}
				}
				first();
			}
    	});
    	DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		nameedit = (EditText)findViewById(R.id.ptitle);
		nameedit.addTextChangedListener(new TextWatcher(){
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}
			public void afterTextChanged(Editable s) {
				profile.set_name(s.toString());
			}
		});
		boolean nfc = false;
		nfc = MTOOL.nfc.is_nfc();
		pi[0] = new aiitem(this, -1, getResources().getString(R.string.real_plane), getResources().getDrawable(R.drawable.plane), metrics, false);
		pi[0].iv.setVisibility(View.GONE);
		pi[1] = new aiitem(this, 0, getResources().getString(R.string.wifi), getResources().getDrawable(R.drawable.wifi), metrics, false);
		pi[2] = new aiitem(this, 1, getResources().getString(R.string.bluetooth), getResources().getDrawable(R.drawable.bluetooth), metrics, false);
		pi[3] = new aiitem(this, 2, getResources().getString(R.string.data), getResources().getDrawable(R.drawable.data), metrics, false);
		pi[4] = new aiitem(this, 3, getResources().getString(R.string.timeout), getResources().getDrawable(R.drawable.timeout), metrics, false);
		pi[5] = new aiitem(this, 4, getResources().getString(R.string.brightness), getResources().getDrawable(R.drawable.brightness), metrics, false);
		pi[6] = new aiitem(this, 5, getResources().getString(R.string.rotation), getResources().getDrawable(R.drawable.rotation), metrics, false);
		pi[7] = new aiitem(this, 6, getResources().getString(R.string.volume), getResources().getDrawable(R.drawable.volume), metrics, false);
		pi[8] = new aiitem(this, 7, getResources().getString(R.string.gps), getResources().getDrawable(R.drawable.gps), metrics, false);
		pi[9] = new aiitem(this, 8, getResources().getString(R.string.nfc), getResources().getDrawable(R.drawable.nfc), metrics, false);
		if(!nfc)
			pi[9].setVisibility(View.GONE);
		pi[10] = new aiitem(this, 9, getResources().getString(R.string.keyguard), getResources().getDrawable(R.drawable.keyguard), metrics, false);
		pi[11] = new aiitem(this, 10, getResources().getString(R.string.sync), getResources().getDrawable(R.drawable.sync), metrics, false);
		pi[12] = new aiitem(this, 11, getResources().getString(R.string.hotspot), getResources().getDrawable(R.drawable.hotspot), metrics, true);
		style = new aiitem(this, 12, getResources().getString(R.string.pstyle), getResources().getDrawable(R.drawable.settings), metrics, false);
		((LinearLayout)findViewById(R.id.pitem)).addView(style);
		for(int i = 0; i < pi.length; i++)
			((LinearLayout)findViewById(R.id.pitem)).addView(pi[i]);
		float logicalDensity = metrics.density;
		for(int i = 1; i < pi.length; i++) {
			pi[i].rb.setVisibility(View.GONE);
			LinearLayout.LayoutParams ivlp = (LinearLayout.LayoutParams) pi[i].getLayoutParams();
			ivlp.leftMargin = (int) (-30 * logicalDensity + 0.5);
		}
		pi[0].tv.setTextSize(29);
		pi[0].setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				pi[0].rb.setChecked(!pi[0].isChecked());
			}
		});
		pi[0].rb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if(isChecked) {
					pi[1].setVisibility(View.GONE);
					pi[2].setVisibility(View.GONE);
					pi[3].setVisibility(View.GONE);
					pi[8].setVisibility(View.GONE);
					pi[9].setVisibility(View.GONE);
					pi[11].setVisibility(View.GONE);
					pi[12].setVisibility(View.GONE);
					pi[10].lastOne = true;
				} else {
					pi[1].setVisibility(View.VISIBLE);
					pi[2].setVisibility(View.VISIBLE);
					pi[3].setVisibility(View.VISIBLE);
					pi[8].setVisibility(View.VISIBLE);
					pi[9].setVisibility(View.VISIBLE);
					pi[11].setVisibility(View.VISIBLE);
					pi[12].setVisibility(View.VISIBLE);
					boolean nfc = false;
					nfc = MTOOL.nfc.is_nfc();
					if(!nfc) pi[9].setVisibility(View.GONE);
					pi[10].lastOne = false;
				}
			}
		});
		style.rb.setVisibility(View.GONE);
		LinearLayout.LayoutParams ivlp = (LinearLayout.LayoutParams) style.getLayoutParams();
		ivlp.leftMargin = (int) (-30 * logicalDensity + 0.5);
		listView = (ListView) findViewById(R.id.lvp);
		for(int i = 0; i < MTOOL.db.getAllProfileEntrys().size(); i++)
			MTOOL.db.getAllProfileEntrys().get(i).setID(i+1);
		profiles = new ArrayList<ProfileEntry>();
		names = new ArrayList<String>();
		for(int i = 0; i < MTOOL.db.getAllProfileEntrys().size(); i++)
			profiles.add(MTOOL.db.getAllProfileEntrys().get(i));
		for(int i = 0; i < profiles.size(); i++)
			names.add(profiles.get(i).get_name());
		names.add("New");
		MTOOL.db.close();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				this, android.R.layout.simple_list_item_1, android.R.id.text1, names);
		listView.setAdapter(adapter);
		listView.setOnItemLongClickListener(new OnItemLongClickListener(){
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(position < (names.size()-1))
					profile_dialog(position);
				return false;
			}
		});
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int item, long id) {
				if(item < (names.size()-1)) {
					edit(profiles.get(item));
					second();
				}
				else {
					if (profiles.size() < 3) {
						add();
						second();
					} else {
						Toast.makeText(getApplicationContext(), R.string.profiles_notpro, Toast.LENGTH_LONG).show();
					}
				}
			}
		});
		style.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				style_dialog();
			}
		});
		for(io = 1; io < pi.length; io++)
			pi[io].setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					dialog(inhalt.get(((aiitem)v).item), arrays[((aiitem)v).item], ((aiitem)v).item);
				}
			});
	}
	
	public void first()	{
		schritt = 0;
		((RelativeLayout)findViewById(R.id.pfirst_view)).setVisibility(View.VISIBLE);
		((RelativeLayout)findViewById(R.id.psecond_view)).setVisibility(View.GONE);
		profiles.clear();
		names.clear();
		for(int i = 0; i < MTOOL.db.getAllProfileEntrys().size(); i++)
			profiles.add(MTOOL.db.getAllProfileEntrys().get(i));
		MTOOL.db.close();
		for(int i = 0; i < profiles.size(); i++)
			names.add(profiles.get(i).get_name());
		names.add("New");
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				this, android.R.layout.simple_list_item_1, android.R.id.text1, names);
		listView.setAdapter(adapter);
	}
	
	public void second() {
		schritt = 1;
		((ScrollView)findViewById(R.id.psv)).scrollTo(0, 0);
		((RelativeLayout)findViewById(R.id.pfirst_view)).setVisibility(View.GONE);
		((RelativeLayout)findViewById(R.id.psecond_view)).setVisibility(View.VISIBLE);
	}
	
	public void add() {
		profile = new ProfileEntry("", "0;0", Integer.toString(R.string.wifi) + ":0;" + Integer.toString(R.string.bluetooth) + ":0;" + 
				  Integer.toString(R.string.data) + ":0;" + Integer.toString(R.string.timeout) + ":0;" + Integer.toString(R.string.brightness) + ":0;" +
				  Integer.toString(R.string.rotation) + ":0;" + Integer.toString(R.string.volume) + ":0:0:0:0:0;" +Integer.toString(R.string.gps) + ":0;" +
				  Integer.toString(R.string.nfc) + ":0;" + Integer.toString(R.string.keyguard) + ":0;" + Integer.toString(R.string.sync) + ":0;" +
				  Integer.toString(R.string.hotspot) + ":0;" + "0;");
		edit(profile);
	}
	
	public void edit(ProfileEntry p) {
		profile = p;
		inhalt = new ArrayList<String>();
		nameedit.setText(profile.get_name());
		nameedit.setFocusable(!profile.get_name().equals(getResources().getString(R.string.plane)));
		nameedit.setFocusableInTouchMode(!profile.get_name().equals(getResources().getString(R.string.plane)));
		style_str = new ArrayList<Integer>();
		for(int i = 0; i < profile.get_inhalt().split(Pattern.quote(";")).length; i++)
			inhalt.add(profile.get_inhalt().split(Pattern.quote(";"))[i]);
		for(int i = 0; i < profile.get_style().split(Pattern.quote(";")).length; i++)
			style_str.add(Integer.parseInt(profile.get_style().split(Pattern.quote(";"))[i]));
		style.tv.setTextColor(getResources().getIntArray(R.array.colors_int)[style_str.get(0)]);
		style.iv.setImageResource(images[style_str.get(1)]);
		pi[0].rb.setChecked(inhalt.get(inhalt.size()-1).equals("1"));
		pi[0].setVisibility(View.GONE);
		if(profile.get_name().equals(getResources().getString(R.string.plane)))
			pi[0].setVisibility(View.VISIBLE);
	}
	
	public void profile_dialog(int index) {
		final ProfileEntry pe = profiles.get(index);
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder
			.setNegativeButton(android.R.string.cancel,	new DialogInterface.OnClickListener() {
		    	public void onClick(DialogInterface dialog, int id) {
		    		dialog.cancel();
		    	}
			})
			.setItems(R.array.pitemlc, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					if(which == 0) {
						MTOOL.activate_profile(pe);
						finish();
					}
					else if(which == 1) {
						second();
						edit(pe);
					}
					else if(which == 2) {
						if(pe.get_name().equals(getResources().getString(R.string.plane))) {
							AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Profiles.this);
							alertDialogBuilder
								.setPositiveButton(android.R.string.ok,	new DialogInterface.OnClickListener() {
							    	public void onClick(DialogInterface dialog, int id) {
							    		MTOOL.db.deleteEntry(pe);
										MTOOL.db.close();
										first();
							    	}
								})	
								.setNegativeButton(android.R.string.cancel,	new DialogInterface.OnClickListener() {
							    	public void onClick(DialogInterface dialog, int id) {
							    		dialog.cancel();
							    	}
								})
								.setTitle(R.string.plane)
								.setMessage(R.string.delete_plane_note)
								.show();
						} else {
							MTOOL.db.deleteEntry(pe);
							MTOOL.db.close();
							first();
						}
					}
				}
			})
			.setTitle(pe.get_name())
			.show();
	}
	
	public void dialog(final String i, int res, final int index) {
		selected_item = Integer.parseInt(i.split(Pattern.quote(":"))[1]);
		pre_selected_item = selected_item;
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder
			.setPositiveButton(android.R.string.ok,	new DialogInterface.OnClickListener() {
		    	public void onClick(DialogInterface dialog, int id) {
		    		selected_item = pre_selected_item;
		    		inhalt.set(index, i.split(Pattern.quote(":"))[0] + ":" + Integer.toString(selected_item));
		    		profile.set_inhalt(inhalt.toString().replace(", ",";").substring(1, inhalt.toString().replace(", ",";").length()-1));
		    	}
			})
			.setNegativeButton(android.R.string.cancel,	new DialogInterface.OnClickListener() {
		    	public void onClick(DialogInterface dialog, int id) {
		    		dialog.cancel();
		    	}
			})
			.setSingleChoiceItems(res, selected_item, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					pre_selected_item = which;
				}
			})
			.setTitle(Integer.parseInt(i.split(Pattern.quote(":"))[0]))
			.show();
	}
	
	public void style_dialog() {
		final styleDlgView sdv = new styleDlgView(this);
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder
			.setPositiveButton(android.R.string.ok,	new DialogInterface.OnClickListener() {
		    	public void onClick(DialogInterface dialog, int id) {
		    		style_str.set(0, sdv.color);
		    		style_str.set(1, sdv.image);
		    		profile.set_style(style_str.toString().replace(", ",";").substring(1, style_str.toString().length()-2));
		    		style.tv.setTextColor(getResources().getIntArray(R.array.colors_int)[style_str.get(0)]);
		    		style.iv.setImageResource(images[style_str.get(1)]);
		    	}
			})	
			.setNegativeButton(android.R.string.cancel,	new DialogInterface.OnClickListener() {
		    	public void onClick(DialogInterface dialog, int id) {
		    		dialog.cancel();
		    	}
			})
			.setView(sdv)
			.setTitle(R.string.pstyle)
			.show();
	}
	
	public class ColorAdapter extends ArrayAdapter<String>{
		public ColorAdapter(Context context, int resource,
				int textViewResourceId, String[] objects) {
			super(context, resource, textViewResourceId, objects);
		}
		@Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);
            TextView text = (TextView)view.findViewById(android.R.id.text1);
            if(position == 0 || position == 1 || position == 3)
            	text.setTextColor(Color.BLACK);
           	view.setBackgroundColor(getResources().getIntArray(R.array.colors_int)[position]);
            return view;
        }   
	}
	
	public class styleDlgView extends RelativeLayout {
		Spinner sp;
		ImageView iv;
		Button b1, b2;
		int image;
		int color;
		public styleDlgView(Context context) {
			super(context);
			this.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
			color = style_str.get(0);
			image = style_str.get(1);
			sp = new Spinner(context);
			sp.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
			ColorAdapter adapter = new ColorAdapter(context, android.R.layout.simple_list_item_1, android.R.id.text1, getResources().getStringArray(R.array.colors));
			sp.setAdapter(adapter);
			sp.setSelection(color);
			sp.setOnItemSelectedListener(new OnItemSelectedListener(){
				public void onItemSelected(AdapterView<?> parent, View view,
						int position, long id) {
					color = position;
				}
				public void onNothingSelected(AdapterView<?> parent) {
				}
			});
			this.addView(sp);
			RelativeLayout.LayoutParams lpll = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
			lpll.addRule(RelativeLayout.ALIGN_BOTTOM, RelativeLayout.TRUE);
			lpll.topMargin = 100;
			lpll.bottomMargin = 10;
			LinearLayout ll = new LinearLayout(context);
			ll.setLayoutParams(lpll);
			ll.setGravity(Gravity.CENTER);
			b1 = new Button(context);
			b1 = styleButton(context);
			b1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
			b1.setText(R.string.lt);
			b1.setPadding(10, 0, 10, 20);
			b1.setTextSize(70);
			b1.setOnClickListener(new OnClickListener(){
				public void onClick(View v) {
					if(image > 0)
						image--;
					else
						image = images.length-1;
					iv.setImageResource(images[image]);
				}
			});
			ll.addView(b1);
			iv = new ImageView(context);
			iv.setImageResource(images[image]);
			iv.setLayoutParams(new LinearLayout.LayoutParams(100, 100));
			ll.addView(iv);
			b2 = new Button(context);
			b2 = styleButton(context);
			b2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
			b2.setText(R.string.gt);
			b2.setPadding(10, 0, 10, 20);
			b2.setTextSize(70);
			b2.setOnClickListener(new OnClickListener(){
				public void onClick(View v) {
					if(image < images.length-1)
						image++;
					else
						image = 0;
					iv.setImageResource(images[image]);
				}
			});
			ll.addView(b2);
			this.addView(ll);
		}
		
		@TargetApi(Build.VERSION_CODES.HONEYCOMB)
		public Button styleButton(Context context) {
			return new Button(context, null, android.R.attr.borderlessButtonStyle);
		}
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		if(schritt == 0)
			finish();
		else
			first();
	}
}