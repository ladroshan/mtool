package m.tool.stuff;

import m.tool.MTOOL;
import m.tool.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

@SuppressLint("ViewConstructor")
public class Item extends LinearLayout{

	RelativeLayout button;
	public ImageView icon;
	public TextView title;
	public RelativeLayout status;
	ImageView statusc, statusl, statusr;
	int color;
	public boolean active = false;
	boolean has_status;
	boolean light_theme;
	boolean hasIds;
	boolean title_yo;
	boolean transparent;
	public boolean clickable;
	View o;
	Drawable icon_on, icon_off;
	int textid;
	String text;
	int icon_on_id, icon_off_id;
	Drawable icon_dark_on, icon_dark_off, icon_light_on, icon_light_off;
	public int icon_dark_on_id, icon_dark_off_id, icon_light_on_id, icon_light_off_id;
	public boolean built = false;
	
	public Item(Context context) {
		super(context);
		button = new RelativeLayout(getContext());
		icon = new ImageView(getContext());
		title = new TextView(getContext());
		status = new RelativeLayout(getContext());
		statusc = new ImageView(getContext());
		statusl = new ImageView(getContext());
		statusr = new ImageView(getContext());
	}
	
	public Item(Context context, int icon_on_dark_id, int icon_off_dark_id, int icon_on_light_id, int icon_off_light_id, int text_id, boolean title_yes,
				boolean clickabl, boolean transculent, Drawable icon_on_dark, Drawable icon_off_dark, Drawable icon_on_light, Drawable icon_off_light,
				String texts, int col, View v){
		super(context);
		button = new RelativeLayout(getContext());
		icon = new ImageView(getContext());
		title = new TextView(getContext());
		status = new RelativeLayout(getContext());
		statusc = new ImageView(getContext());
		statusl = new ImageView(getContext());
		statusr = new ImageView(getContext());
		build(icon_on_dark_id, icon_off_dark_id, icon_on_light_id, icon_off_light_id, text_id, title_yes, clickabl,
				transculent, icon_on_dark, icon_off_dark, icon_on_light, icon_off_light, texts, col, v);
	}
	
	public void build(int icon_on_dark_id, int icon_off_dark_id, int icon_on_light_id, int icon_off_light_id, int text_id, boolean title_yes, boolean clickabl, 
			boolean transculent, Drawable icon_on_dark, Drawable icon_off_dark, Drawable icon_on_light, Drawable icon_off_light, String texts, int col, View v){
		this.removeAllViews();
		int icsize = MTOOL.icsize;
		o = v;
		color = col;
		clickable = clickabl;
		button.removeAllViews();
		status.removeAllViews();
		transparent = transculent;
		title_yo = title_yes;
		has_status = !(icon_off_dark == null && icon_off_dark_id == 0);
		hasIds = !(icon_on_dark_id == 0);
		textid = text_id;
		text = texts;
		if(icon_on_light == null && icon_on_dark != null) icon_on_light = icon_on_dark;
		if(icon_off_light == null && icon_off_dark != null) icon_off_light = icon_off_dark;
		if(icon_on_light_id == 0 && icon_on_dark_id != 0) icon_on_light_id = icon_on_dark_id;
		if(icon_off_light_id == 0 && icon_off_dark_id != 0) icon_off_light_id = icon_off_dark_id;
		if(icon_on_dark_id != 0 && icon_off_dark_id == 0){icon_off_dark_id = icon_on_dark_id; icon_off_light_id = icon_on_light_id;}
		icon_dark_on = icon_on_dark;
		icon_dark_off = icon_off_dark;
		icon_light_on = icon_on_light;
		icon_light_off = icon_off_light;
		icon_dark_on_id = icon_on_dark_id;
		icon_dark_off_id = icon_off_dark_id;
		icon_light_on_id = icon_on_light_id;
		icon_light_off_id = icon_off_light_id;
		this.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
		if(MTOOL.layouted)
			light_theme = (MTOOL.prefs.getString("theme_pref", "dark").equals("light") || MTOOL.prefs.getString("theme_pref", "dark").equals("light_yellow")
					|| MTOOL.prefs.getString("theme_pref", "dark").equals("light_green") || MTOOL.prefs.getString("theme_pref", "dark").equals("light_blue")
					|| MTOOL.prefs.getString("theme_pref", "dark").equals("light_orange") || MTOOL.prefs.getString("theme_pref", "dark").equals("light_red")
					|| MTOOL.prefs.getString("theme_pref", "dark").equals("light_pink") || MTOOL.prefs.getString("theme_pref", "dark").equals("light_black"));
		else
			light_theme = false;
		icon_on = light_theme ? icon_on_light : icon_on_dark;
		icon_on_id = light_theme ? icon_on_light_id : icon_on_dark_id;
		if(has_status) icon_off = light_theme ? icon_off_light : icon_off_dark;
		if(has_status) icon_off_id = light_theme ? icon_off_light_id : icon_off_dark_id;
		if(!transparent) this.setBackgroundResource(light_theme ? R.drawable.item_bg_light : R.drawable.item_bg_dark);
		if(MTOOL.layouted)
			if(this.getBackground() != null) this.getBackground().setAlpha(MTOOL.italpha);
		this.setGravity(Gravity.CENTER);
		button.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));
		button.setBackgroundResource(clickable ? R.drawable.item_button : android.R.color.transparent);
		if(MTOOL.layouted)
			if(color == 0) {
				if(MTOOL.prefs.getString("theme_pref", "dark").equals("dark_green") || MTOOL.prefs.getString("theme_pref", "dark").equals("light_green"))
					button.setBackgroundResource(clickable ? R.drawable.item_button_green : android.R.color.transparent);
				else if(MTOOL.prefs.getString("theme_pref", "dark").equals("dark_blue") || MTOOL.prefs.getString("theme_pref", "dark").equals("light_blue"))
					button.setBackgroundResource(clickable ? R.drawable.item_button_blue : android.R.color.transparent);
				else if(MTOOL.prefs.getString("theme_pref", "dark").equals("dark_red") || MTOOL.prefs.getString("theme_pref", "dark").equals("light_red"))
					button.setBackgroundResource(clickable ? R.drawable.item_button_red : android.R.color.transparent);
				else if(MTOOL.prefs.getString("theme_pref", "dark").equals("dark_yellow") || MTOOL.prefs.getString("theme_pref", "dark").equals("light_yellow"))
					button.setBackgroundResource(clickable ? R.drawable.item_button_yellow : android.R.color.transparent);
				else if(MTOOL.prefs.getString("theme_pref", "dark").equals("dark_orange") || MTOOL.prefs.getString("theme_pref", "dark").equals("light_orange"))
					button.setBackgroundResource(clickable ? R.drawable.item_button_orange : android.R.color.transparent);
				else if(MTOOL.prefs.getString("theme_pref", "dark").equals("dark_pink") || MTOOL.prefs.getString("theme_pref", "dark").equals("light_pink"))
					button.setBackgroundResource(clickable ? R.drawable.item_button_pink : android.R.color.transparent);
				else if(MTOOL.prefs.getString("theme_pref", "dark").equals("light_black"))
					button.setBackgroundResource(clickable ? R.drawable.item_button_black : android.R.color.transparent);
				else if(MTOOL.prefs.getString("theme_pref", "dark").equals("dark_white"))
					button.setBackgroundResource(clickable ? R.drawable.item_button_white : android.R.color.transparent);
				else
					button.setBackgroundResource(clickable ? R.drawable.item_button : android.R.color.transparent);
			} 
		if(color != 0) {
			if(color == 0xff00ff00)
				button.setBackgroundResource(clickable ? R.drawable.item_button_green : android.R.color.transparent);
			else if(color == 0xff0000ff)
				button.setBackgroundResource(clickable ? R.drawable.item_button_blue : android.R.color.transparent);
			else if(color == 0xffff0000)
				button.setBackgroundResource(clickable ? R.drawable.item_button_red : android.R.color.transparent);
			else if(color == 0xffffff00)
				button.setBackgroundResource(clickable ? R.drawable.item_button_yellow : android.R.color.transparent);
			else if(color == 0xffff8800)
				button.setBackgroundResource(clickable ? R.drawable.item_button_orange : android.R.color.transparent);
			else if(color == 0xffff00ff)
				button.setBackgroundResource(clickable ? R.drawable.item_button_pink : android.R.color.transparent);
			else if(color == 0xff888888 && light_theme)
				button.setBackgroundResource(clickable ? R.drawable.item_button_black : android.R.color.transparent);
			else if(color == 0xff888888 && !light_theme)
				button.setBackgroundResource(clickable ? R.drawable.item_button_white : android.R.color.transparent);
			else
				button.setBackgroundResource(clickable ? R.drawable.item_button : android.R.color.transparent);
		}
		if(MTOOL.layouted)
			button.getBackground().setAlpha(MTOOL.italpha);
		if(has_status) button.setPadding(0, (int)getContext().getResources().getDimension(R.dimen.item_padding), 0, 0);
		if(MTOOL.layouted)
			if(!has_status)	button.setPadding(0, 
					(o == null && (MTOOL.prefs.getBoolean("itemtext_pref", false) || title_yo) && !(textid == 0 && text.equals(""))) ? 10 : 0, 0, 0);
		this.addView(button);
		float icondim = getResources().getDimension(R.dimen.item_icon)*icsize/10+10;
		RelativeLayout.LayoutParams iconlp = new RelativeLayout.LayoutParams((int) (icondim), 
				(int) (icondim));
		if(!hasIds) {
			iconlp.width = (int) (iconlp.width*1.2);
			iconlp.height = (int) (iconlp.height*1.2);
		}
		if(MTOOL.layouted)
		iconlp.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
		icon.setLayoutParams(iconlp);
		if(has_status) {
			if(!hasIds)
				icon.setImageDrawable(icon_off);
			else
				icon.setImageResource(icon_off_id);
		}
		else {
			if(!hasIds)
				icon.setImageDrawable(icon_on);
			else
				icon.setImageResource(icon_on_id);
		}
		if(o == null)
			button.addView(icon);
		else
			button.addView(o);
		RelativeLayout.LayoutParams titlelp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		titlelp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
		title.setLayoutParams(titlelp);
		if(text_id == 0)
			title.setText(text);
		else
			title.setText(text_id);
		title.setGravity(Gravity.CENTER);
		title.setTextSize(11);
		if(!has_status) title.setPadding(0, 0, 0, 15);
		title.setTextColor(light_theme ? Color.BLACK : Color.WHITE);
		if(MTOOL.layouted)
			title.setVisibility((o == null && (MTOOL.prefs.getBoolean("itemtext_pref", false) || title_yo) && !(textid == 0 && text.equals("")))
					? View.VISIBLE : View.GONE);
		else
			title.setVisibility(View.GONE);
		button.addView(title);
		float statusdim = getResources().getDimension(R.dimen.item_status)*icsize/10;
		RelativeLayout.LayoutParams statuslp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 
				(int) (statusdim));
		statuslp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
		status.setLayoutParams(statuslp);
		button.addView(status);
		statusc.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
		statusc.setImageResource(R.drawable.status_off_c);
		statusc.setScaleType(ImageView.ScaleType.FIT_XY);
		statusc.setPadding(6, 0, 6, 0);
		status.addView(statusc);
		RelativeLayout.LayoutParams statusllp = new RelativeLayout.LayoutParams(6, RelativeLayout.LayoutParams.WRAP_CONTENT);
		statusllp.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
		statusl.setLayoutParams(statusllp);
		statusl.setImageResource(R.drawable.status_off_l);
		statusl.setScaleType(ImageView.ScaleType.FIT_XY);
		status.addView(statusl);
		RelativeLayout.LayoutParams statusrlp = new RelativeLayout.LayoutParams(6, RelativeLayout.LayoutParams.WRAP_CONTENT);
		statusrlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
		statusr.setLayoutParams(statusrlp);
		statusr.setImageResource(R.drawable.status_off_r);
		statusr.setScaleType(ImageView.ScaleType.FIT_XY);
		status.addView(statusr);
		if(!has_status)	status.setVisibility(View.GONE);
		this.setVisibility(View.GONE);
		built = true;
	}
	
	public void style_update() {
		if(MTOOL.layouted)
			light_theme = (MTOOL.prefs.getString("theme_pref", "dark").equals("light") || MTOOL.prefs.getString("theme_pref", "dark").equals("light_yellow")
					|| MTOOL.prefs.getString("theme_pref", "dark").equals("light_green") || MTOOL.prefs.getString("theme_pref", "dark").equals("light_blue")
					|| MTOOL.prefs.getString("theme_pref", "dark").equals("light_orange") || MTOOL.prefs.getString("theme_pref", "dark").equals("light_red")
					|| MTOOL.prefs.getString("theme_pref", "dark").equals("light_pink") || MTOOL.prefs.getString("theme_pref", "dark").equals("light_black"));
		else
			light_theme = false;
		icon_on = light_theme ? icon_light_on : icon_dark_on;
		icon_on_id = light_theme ? icon_light_on_id : icon_dark_on_id;
		if(has_status) icon_off = light_theme ? icon_light_off : icon_dark_off;
		if(has_status) icon_off_id = light_theme ? icon_light_off_id : icon_dark_off_id;
		title.setTextColor(light_theme ? Color.BLACK : Color.WHITE);
		if(MTOOL.layouted)
			title.setVisibility((o == null && (MTOOL.prefs.getBoolean("itemtext_pref", false) || title_yo) && !(textid == 0 && text.equals("")))
					? View.VISIBLE : View.GONE);
		else
			title.setVisibility(View.GONE);
		if(!transparent) this.setBackgroundResource(light_theme ? R.drawable.item_bg_light : R.drawable.item_bg_dark);
		if(MTOOL.layouted)
			if(this.getBackground() != null) this.getBackground().setAlpha(MTOOL.italpha);
		button.setBackgroundResource(clickable ? R.drawable.item_button : android.R.color.transparent);
		if(MTOOL.layouted)
			if(color == 0) {
				if(MTOOL.prefs.getString("theme_pref", "dark").equals("dark_green") || MTOOL.prefs.getString("theme_pref", "dark").equals("light_green"))
					button.setBackgroundResource(clickable ? R.drawable.item_button_green : android.R.color.transparent);
				else if(MTOOL.prefs.getString("theme_pref", "dark").equals("dark_blue") || MTOOL.prefs.getString("theme_pref", "dark").equals("light_blue"))
					button.setBackgroundResource(clickable ? R.drawable.item_button_blue : android.R.color.transparent);
				else if(MTOOL.prefs.getString("theme_pref", "dark").equals("dark_red") || MTOOL.prefs.getString("theme_pref", "dark").equals("light_red"))
					button.setBackgroundResource(clickable ? R.drawable.item_button_red : android.R.color.transparent);
				else if(MTOOL.prefs.getString("theme_pref", "dark").equals("dark_yellow") || MTOOL.prefs.getString("theme_pref", "dark").equals("light_yellow"))
					button.setBackgroundResource(clickable ? R.drawable.item_button_yellow : android.R.color.transparent);
				else if(MTOOL.prefs.getString("theme_pref", "dark").equals("dark_orange") || MTOOL.prefs.getString("theme_pref", "dark").equals("light_orange"))
					button.setBackgroundResource(clickable ? R.drawable.item_button_orange : android.R.color.transparent);
				else if(MTOOL.prefs.getString("theme_pref", "dark").equals("dark_pink") || MTOOL.prefs.getString("theme_pref", "dark").equals("light_pink"))
					button.setBackgroundResource(clickable ? R.drawable.item_button_pink : android.R.color.transparent);
				else if(MTOOL.prefs.getString("theme_pref", "dark").equals("light_black"))
					button.setBackgroundResource(clickable ? R.drawable.item_button_black : android.R.color.transparent);
				else if(MTOOL.prefs.getString("theme_pref", "dark").equals("dark_white"))
					button.setBackgroundResource(clickable ? R.drawable.item_button_white : android.R.color.transparent);
				else
					button.setBackgroundResource(clickable ? R.drawable.item_button : android.R.color.transparent);
			} 
		if(color != 0) {
			if(color == 0xff00ff00)
				button.setBackgroundResource(clickable ? R.drawable.item_button_green : android.R.color.transparent);
			else if(color == 0xff0000ff)
				button.setBackgroundResource(clickable ? R.drawable.item_button_blue : android.R.color.transparent);
			else if(color == 0xffff0000)
				button.setBackgroundResource(clickable ? R.drawable.item_button_red : android.R.color.transparent);
			else if(color == 0xffffff00)
				button.setBackgroundResource(clickable ? R.drawable.item_button_yellow : android.R.color.transparent);
			else if(color == 0xffff8800)
				button.setBackgroundResource(clickable ? R.drawable.item_button_orange : android.R.color.transparent);
			else if(color == 0xffff00ff)
				button.setBackgroundResource(clickable ? R.drawable.item_button_pink : android.R.color.transparent);
			else if(color == 0xff888888 && light_theme)
				button.setBackgroundResource(clickable ? R.drawable.item_button_black : android.R.color.transparent);
			else if(color == 0xff888888 && !light_theme)
				button.setBackgroundResource(clickable ? R.drawable.item_button_white : android.R.color.transparent);
			else
				button.setBackgroundResource(clickable ? R.drawable.item_button : android.R.color.transparent);
		}
		if(MTOOL.layouted)
			button.getBackground().setAlpha(MTOOL.italpha);
	}
	
	public void toggle() {
		if(active)
			deactivate();
		else
			activate();
	}
	
	public void setThings(String color, int icon_id_dark, int icon_id_light, Drawable icon_d)
	{
		if(icon_d == null && !(icon_id_dark == 0 || icon_id_light == 0))
			icon.setImageResource(light_theme ? icon_id_light : icon_id_dark);
		else if(icon_d != null)
			icon.setImageDrawable(icon_d);
		if(color.equals("cyan")) {
			statusc.setImageResource(R.drawable.status_c);
			statusl.setImageResource(R.drawable.status_l);
			statusr.setImageResource(R.drawable.status_r);
		}
		else if(color.equals("green")) {
			statusc.setImageResource(R.drawable.status_green_c);
			statusl.setImageResource(R.drawable.status_green_l);
			statusr.setImageResource(R.drawable.status_green_r);
		}
		else if(color.equals("blue")) {
			statusc.setImageResource(R.drawable.status_blue_c);
			statusl.setImageResource(R.drawable.status_blue_l);
			statusr.setImageResource(R.drawable.status_blue_r);
		}
		else if(color.equals("orange")) {
			statusc.setImageResource(R.drawable.status_orange_c);
			statusl.setImageResource(R.drawable.status_orange_l);
			statusr.setImageResource(R.drawable.status_orange_r);
		}
		else if(color.equals("yellow")) {
			statusc.setImageResource(R.drawable.status_yellow_c);
			statusl.setImageResource(R.drawable.status_yellow_l);
			statusr.setImageResource(R.drawable.status_yellow_r);
		}
		else if(color.equals("pink")) {
			statusc.setImageResource(R.drawable.status_pink_c);
			statusl.setImageResource(R.drawable.status_pink_l);
			statusr.setImageResource(R.drawable.status_pink_r);
		}
		else if(color.equals("red")) {
			statusc.setImageResource(R.drawable.status_red_c);
			statusl.setImageResource(R.drawable.status_red_l);
			statusr.setImageResource(R.drawable.status_red_r);
		}
		else if(color.equals("white")) {
			statusc.setImageResource(R.drawable.status_white_c);
			statusl.setImageResource(R.drawable.status_white_l);
			statusr.setImageResource(R.drawable.status_white_r);
		}
		else if(color.equals("black")) {
			statusc.setImageResource(R.drawable.status_black_c);
			statusl.setImageResource(R.drawable.status_black_l);
			statusr.setImageResource(R.drawable.status_black_r);
		}
		else if(color.equals("off")) {
			statusc.setImageResource(R.drawable.status_off_c);
			statusl.setImageResource(R.drawable.status_off_l);
			statusr.setImageResource(R.drawable.status_off_r);
		}
	}
	
	public void setThings(int icon_id, int icon_id_light, Drawable icon_d, String state) {
		if(state.equals("off"))
			deactivate();
		else if(state.equals("loading"))
			loading();
		else if(state.equals("on"))
			activate();
		if(icon_id != 0)
			icon.setImageResource(light_theme ? icon_id_light : icon_id);
		else if(icon_d != null)
			icon.setImageDrawable(icon_d);
	}

	public void activate() {
		active = true;
		if(MTOOL.prefs.getString("theme_pref", "dark").equals("light") || MTOOL.prefs.getString("theme_pref", "dark").equals("dark")) {
			statusc.setImageResource(R.drawable.status_c);
			statusl.setImageResource(R.drawable.status_l);
			statusr.setImageResource(R.drawable.status_r);
		}
		if(MTOOL.prefs.getString("theme_pref", "dark").equals("light_green") || MTOOL.prefs.getString("theme_pref", "dark").equals("dark_green")) {
			statusc.setImageResource(R.drawable.status_green_c);
			statusl.setImageResource(R.drawable.status_green_l);
			statusr.setImageResource(R.drawable.status_green_r);
		}
		if(MTOOL.prefs.getString("theme_pref", "dark").equals("light_blue") || MTOOL.prefs.getString("theme_pref", "dark").equals("dark_blue")) {
			statusc.setImageResource(R.drawable.status_blue_c);
			statusl.setImageResource(R.drawable.status_blue_l);
			statusr.setImageResource(R.drawable.status_blue_r);
		}
		if(MTOOL.prefs.getString("theme_pref", "dark").equals("light_orange") || MTOOL.prefs.getString("theme_pref", "dark").equals("dark_orange")) {
			statusc.setImageResource(R.drawable.status_orange_c);
			statusl.setImageResource(R.drawable.status_orange_l);
			statusr.setImageResource(R.drawable.status_orange_r);
		}
		if(MTOOL.prefs.getString("theme_pref", "dark").equals("light_yellow") || MTOOL.prefs.getString("theme_pref", "dark").equals("dark_yellow")) {
			statusc.setImageResource(R.drawable.status_yellow_c);
			statusl.setImageResource(R.drawable.status_yellow_l);
			statusr.setImageResource(R.drawable.status_yellow_r);
		}
		if(MTOOL.prefs.getString("theme_pref", "dark").equals("light_pink") || MTOOL.prefs.getString("theme_pref", "dark").equals("dark_pink")) {
			statusc.setImageResource(R.drawable.status_pink_c);
			statusl.setImageResource(R.drawable.status_pink_l);
			statusr.setImageResource(R.drawable.status_pink_r);
		}
		if(MTOOL.prefs.getString("theme_pref", "dark").equals("light_red") || MTOOL.prefs.getString("theme_pref", "dark").equals("dark_red")) {
			statusc.setImageResource(R.drawable.status_red_c);
			statusl.setImageResource(R.drawable.status_red_l);
			statusr.setImageResource(R.drawable.status_red_r);
		}
		if(MTOOL.prefs.getString("theme_pref", "dark").equals("dark_white")) {
			statusc.setImageResource(R.drawable.status_white_c);
			statusl.setImageResource(R.drawable.status_white_l);
			statusr.setImageResource(R.drawable.status_white_r);
		}
		if(MTOOL.prefs.getString("theme_pref", "dark").equals("light_black")) {
			statusc.setImageResource(R.drawable.status_black_c);
			statusl.setImageResource(R.drawable.status_black_l);
			statusr.setImageResource(R.drawable.status_black_r);
		}
		if(!hasIds)
			icon.setImageDrawable(icon_on);
		else
			icon.setImageResource(icon_on_id);
	}
	
	public void deactivate() {
		active = false;
		statusc.setImageResource(R.drawable.status_off_c);
		statusl.setImageResource(R.drawable.status_off_l);
		statusr.setImageResource(R.drawable.status_off_r);
		if(has_status)
			if(!hasIds)
				icon.setImageDrawable(icon_off);
			else
				icon.setImageResource(icon_off_id);
	}
	
	public void loading() {
		if(MTOOL.prefs.getString("theme_pref", "dark").equals("light") || MTOOL.prefs.getString("theme_pref", "dark").equals("dark")) {
			statusc.setImageResource(R.drawable.status_loading_c);
			statusl.setImageResource(R.drawable.status_loading_l);
			statusr.setImageResource(R.drawable.status_loading_r);
		}
		if(MTOOL.prefs.getString("theme_pref", "dark").equals("light_green") || MTOOL.prefs.getString("theme_pref", "dark").equals("dark_green")) {
			statusc.setImageResource(R.drawable.status_loading_green_c);
			statusl.setImageResource(R.drawable.status_loading_green_l);
			statusr.setImageResource(R.drawable.status_loading_green_r);
		}
		if(MTOOL.prefs.getString("theme_pref", "dark").equals("light_blue") || MTOOL.prefs.getString("theme_pref", "dark").equals("dark_blue")) {
			statusc.setImageResource(R.drawable.status_loading_blue_c);
			statusl.setImageResource(R.drawable.status_loading_blue_l);
			statusr.setImageResource(R.drawable.status_loading_blue_r);
		}
		if(MTOOL.prefs.getString("theme_pref", "dark").equals("light_orange") || MTOOL.prefs.getString("theme_pref", "dark").equals("dark_orange")) {
			statusc.setImageResource(R.drawable.status_loading_orange_c);
			statusl.setImageResource(R.drawable.status_loading_orange_l);
			statusr.setImageResource(R.drawable.status_loading_orange_r);
		}
		if(MTOOL.prefs.getString("theme_pref", "dark").equals("light_yellow") || MTOOL.prefs.getString("theme_pref", "dark").equals("dark_yellow")) {
			statusc.setImageResource(R.drawable.status_loading_yellow_c);
			statusl.setImageResource(R.drawable.status_loading_yellow_l);
			statusr.setImageResource(R.drawable.status_loading_yellow_r);
		}
		if(MTOOL.prefs.getString("theme_pref", "dark").equals("light_pink") || MTOOL.prefs.getString("theme_pref", "dark").equals("dark_pink")) {
			statusc.setImageResource(R.drawable.status_loading_pink_c);
			statusl.setImageResource(R.drawable.status_loading_pink_l);
			statusr.setImageResource(R.drawable.status_loading_pink_r);
		}
		if(MTOOL.prefs.getString("theme_pref", "dark").equals("light_red") || MTOOL.prefs.getString("theme_pref", "dark").equals("dark_red")) {
			statusc.setImageResource(R.drawable.status_loading_red_c);
			statusl.setImageResource(R.drawable.status_loading_red_l);
			statusr.setImageResource(R.drawable.status_loading_red_r);
		}
		if(MTOOL.prefs.getString("theme_pref", "dark").equals("dark_white")) {
			statusc.setImageResource(R.drawable.status_loading_white_c);
			statusl.setImageResource(R.drawable.status_loading_white_l);
			statusr.setImageResource(R.drawable.status_loading_white_r);
		}
		if(MTOOL.prefs.getString("theme_pref", "dark").equals("light_black")) {
			statusc.setImageResource(R.drawable.status_loading_black_c);
			statusl.setImageResource(R.drawable.status_loading_black_l);
			statusr.setImageResource(R.drawable.status_loading_black_r);
		}
		if(!hasIds)
			icon.setImageDrawable(icon_on);
		else
			icon.setImageResource(icon_on_id);
	}
}