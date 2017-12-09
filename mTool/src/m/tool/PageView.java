package m.tool;

import m.tool.stuff.Feld;
import m.tool.stuff.Item;
import m.tool.stuff.drag_and_drop.Feld_Target;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;

@SuppressLint("ViewConstructor" )
public class PageView extends RelativeLayout {
	
	int seite = 0;
	View v;
	
	public boolean created = false;
	
	RelativeLayout[] rlfeld = new RelativeLayout[MTOOL.felder];
	public Feld[] feld = new Feld[MTOOL.felder];
	public Feld_Target[] feld_target = new Feld_Target[MTOOL.felder];
	
	public Item[] wifi = new Item[MTOOL.felder];
	public Item[] app = new Item[MTOOL.felder];
	public Item[] clock = new Item[MTOOL.felder];
	public Item[] bluetooth = new Item[MTOOL.felder];
	public Item[] profile = new Item[MTOOL.felder];
	public Item[] data = new Item[MTOOL.felder];
	public Item[] gps = new Item[MTOOL.felder];
	public Item[] nfc = new Item[MTOOL.felder];
	public Item[] volumes = new Item[MTOOL.felder];
	public Item[] timeout = new Item[MTOOL.felder];
	public Item[] brightness = new Item[MTOOL.felder];
	public Item[] rotation = new Item[MTOOL.felder];
	public Item[] keyguard = new Item[MTOOL.felder];
	public Item[] sync = new Item[MTOOL.felder];
	public Item[] hotspot = new Item[MTOOL.felder];
	public Item[] torch = new Item[MTOOL.felder];
	public Item[] battery = new Item[MTOOL.felder];
	
	public Item[][] allItems = {wifi, app, clock, bluetooth, profile, data, gps, nfc, volumes, timeout, brightness, rotation, keyguard, sync, hotspot, torch, battery};
	
	public PageView(Context c, int p) {
		super(c);
		seite = p;
		if(MTOOL.gridsize.equals("3x3"))
			v = View.inflate(c, R.layout.x33, null);
		if(MTOOL.gridsize.equals("3x4"))
			v = View.inflate(c, R.layout.x34, null);
		if(MTOOL.gridsize.equals("4x4"))
			v = View.inflate(c, R.layout.x44, null);
		if(MTOOL.gridsize.equals("4x5"))
			v = View.inflate(c, R.layout.x45, null);
		if(MTOOL.gridsize.equals("5x5"))
			v = View.inflate(c, R.layout.x55, null);
		v.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		this.addView(v);
		createComponents();
		update();
	}
	
	public void createComponents() {
		v.setBackgroundResource((MTOOL.prefs.getBoolean("mainbg_pref", true)) ? R.drawable.mainbg : android.R.color.transparent);
		v.setOnLongClickListener(MTOOL.long_click_add);
		rlfeld[0] = (RelativeLayout)v.findViewById(R.id.rlfelda);
    	rlfeld[1] = (RelativeLayout)v.findViewById(R.id.rlfeldb);
    	rlfeld[2] = (RelativeLayout)v.findViewById(R.id.rlfeldc);
    	rlfeld[3] = (RelativeLayout)v.findViewById(R.id.rlfeldd);
    	rlfeld[4] = (RelativeLayout)v.findViewById(R.id.rlfelde);
    	rlfeld[5] = (RelativeLayout)v.findViewById(R.id.rlfeldf);
    	rlfeld[6] = (RelativeLayout)v.findViewById(R.id.rlfeldg);
    	rlfeld[7] = (RelativeLayout)v.findViewById(R.id.rlfeldh);
    	rlfeld[8] = (RelativeLayout)v.findViewById(R.id.rlfeldi);
    	if(MTOOL.felder > 9) rlfeld[9] = (RelativeLayout)v.findViewById(R.id.rlfeldj);
    	if(MTOOL.felder > 10) rlfeld[10] = (RelativeLayout)v.findViewById(R.id.rlfeldk);
    	if(MTOOL.felder > 11) rlfeld[11] = (RelativeLayout)v.findViewById(R.id.rlfeldl);
    	if(MTOOL.felder > 12) rlfeld[12] = (RelativeLayout)v.findViewById(R.id.rlfeldm);
    	if(MTOOL.felder > 13) rlfeld[13] = (RelativeLayout)v.findViewById(R.id.rlfeldn);
    	if(MTOOL.felder > 14) rlfeld[14] = (RelativeLayout)v.findViewById(R.id.rlfeldo);
    	if(MTOOL.felder > 15) rlfeld[15] = (RelativeLayout)v.findViewById(R.id.rlfeldp);
    	if(MTOOL.felder > 16) rlfeld[16] = (RelativeLayout)v.findViewById(R.id.rlfeldq);
    	if(MTOOL.felder > 17) rlfeld[17] = (RelativeLayout)v.findViewById(R.id.rlfeldr);
    	if(MTOOL.felder > 18) rlfeld[18] = (RelativeLayout)v.findViewById(R.id.rlfelds);
    	if(MTOOL.felder > 19) rlfeld[19] = (RelativeLayout)v.findViewById(R.id.rlfeldt);
    	if(MTOOL.felder > 20) rlfeld[20] = (RelativeLayout)v.findViewById(R.id.rlfeldu);
    	if(MTOOL.felder > 21) rlfeld[21] = (RelativeLayout)v.findViewById(R.id.rlfeldv);
    	if(MTOOL.felder > 22) rlfeld[22] = (RelativeLayout)v.findViewById(R.id.rlfeldw);
    	if(MTOOL.felder > 23) rlfeld[23] = (RelativeLayout)v.findViewById(R.id.rlfeldx);
    	if(MTOOL.felder > 24) rlfeld[24] = (RelativeLayout)v.findViewById(R.id.rlfeldy);
    	feld[0] = (Feld)v.findViewById(R.id.felda);
    	feld[1] = (Feld)v.findViewById(R.id.feldb);
    	feld[2] = (Feld)v.findViewById(R.id.feldc);
    	feld[3] = (Feld)v.findViewById(R.id.feldd);
    	feld[4] = (Feld)v.findViewById(R.id.felde);
    	feld[5] = (Feld)v.findViewById(R.id.feldf);
    	feld[6] = (Feld)v.findViewById(R.id.feldg);
    	feld[7] = (Feld)v.findViewById(R.id.feldh);
    	feld[8] = (Feld)v.findViewById(R.id.feldi);
    	if(MTOOL.felder > 9) feld[9] = (Feld)v.findViewById(R.id.feldj);
    	if(MTOOL.felder > 10) feld[10] = (Feld)v.findViewById(R.id.feldk);
    	if(MTOOL.felder > 11) feld[11] = (Feld)v.findViewById(R.id.feldl);
    	if(MTOOL.felder > 12) feld[12] = (Feld)v.findViewById(R.id.feldm);
    	if(MTOOL.felder > 13) feld[13] = (Feld)v.findViewById(R.id.feldn);
    	if(MTOOL.felder > 14) feld[14] = (Feld)v.findViewById(R.id.feldo);
    	if(MTOOL.felder > 15) feld[15] = (Feld)v.findViewById(R.id.feldp);
    	if(MTOOL.felder > 16) feld[16] = (Feld)v.findViewById(R.id.feldq);
    	if(MTOOL.felder > 17) feld[17] = (Feld)v.findViewById(R.id.feldr);
    	if(MTOOL.felder > 18) feld[18] = (Feld)v.findViewById(R.id.felds);
    	if(MTOOL.felder > 19) feld[19] = (Feld)v.findViewById(R.id.feldt);
    	if(MTOOL.felder > 20) feld[20] = (Feld)v.findViewById(R.id.feldu);
    	if(MTOOL.felder > 21) feld[21] = (Feld)v.findViewById(R.id.feldv);
    	if(MTOOL.felder > 22) feld[22] = (Feld)v.findViewById(R.id.feldw);
    	if(MTOOL.felder > 23) feld[23] = (Feld)v.findViewById(R.id.feldx);
    	if(MTOOL.felder > 24) feld[24] = (Feld)v.findViewById(R.id.feldy);
    	feld_target[0] = (Feld_Target)v.findViewById(R.id.targeta);
    	feld_target[1] = (Feld_Target)v.findViewById(R.id.targetb);
    	feld_target[2] = (Feld_Target)v.findViewById(R.id.targetc);
    	feld_target[3] = (Feld_Target)v.findViewById(R.id.targetd);
    	feld_target[4] = (Feld_Target)v.findViewById(R.id.targete);
    	feld_target[5] = (Feld_Target)v.findViewById(R.id.targetf);
    	feld_target[6] = (Feld_Target)v.findViewById(R.id.targetg);
    	feld_target[7] = (Feld_Target)v.findViewById(R.id.targeth);
    	feld_target[8] = (Feld_Target)v.findViewById(R.id.targeti);
    	if(MTOOL.felder > 9) feld_target[9] = (Feld_Target)v.findViewById(R.id.targetj);
    	if(MTOOL.felder > 10) feld_target[10] = (Feld_Target)v.findViewById(R.id.targetk);
    	if(MTOOL.felder > 11) feld_target[11] = (Feld_Target)v.findViewById(R.id.targetl);
    	if(MTOOL.felder > 12) feld_target[12] = (Feld_Target)v.findViewById(R.id.targetm);
    	if(MTOOL.felder > 13) feld_target[13] = (Feld_Target)v.findViewById(R.id.targetn);
    	if(MTOOL.felder > 14) feld_target[14] = (Feld_Target)v.findViewById(R.id.targeto);
    	if(MTOOL.felder > 15) feld_target[15] = (Feld_Target)v.findViewById(R.id.targetp);
    	if(MTOOL.felder > 16) feld_target[16] = (Feld_Target)v.findViewById(R.id.targetq);
    	if(MTOOL.felder > 17) feld_target[17] = (Feld_Target)v.findViewById(R.id.targetr);
    	if(MTOOL.felder > 18) feld_target[18] = (Feld_Target)v.findViewById(R.id.targets);
    	if(MTOOL.felder > 19) feld_target[19] = (Feld_Target)v.findViewById(R.id.targett);
    	if(MTOOL.felder > 20) feld_target[20] = (Feld_Target)v.findViewById(R.id.targetu);
    	if(MTOOL.felder > 21) feld_target[21] = (Feld_Target)v.findViewById(R.id.targetv);
    	if(MTOOL.felder > 22) feld_target[22] = (Feld_Target)v.findViewById(R.id.targetw);
    	if(MTOOL.felder > 23) feld_target[23] = (Feld_Target)v.findViewById(R.id.targetx);
    	if(MTOOL.felder > 24) feld_target[24] = (Feld_Target)v.findViewById(R.id.targety);
    	for(int i = 0; i < MTOOL.felder; i++) {
    		feld[i].mPage = seite;
    		feld[i].mCellNumber = i;
    		feld[i].setOnClickListener(MTOOL.click);
    		feld[i].setOnLongClickListener(MTOOL.long_click_drag);
    		feld_target[i].mPage = seite;
    		feld_target[i].mCellNumber = i;
    		feld_target[i].setVisibility(View.GONE);
    		for(int o = 0; o < allItems.length; o++) {
    			allItems[o][i] = new Item(getContext());
				feld[i].addView(allItems[o][i]);
    		}
    	}
    	for(int i = 0; i < MTOOL.youdb.length; i++)
    		if(MTOOL.youdb[i].get_page() == seite)
	    		feld[MTOOL.youdb[i].get_feld()].tellInhalt(MTOOL.youdb[i]);
    	created = true;
    }
	
	public void style_update() {
		v.setBackgroundResource((MTOOL.prefs.getBoolean("mainbg_pref", true)) ? R.drawable.mainbg : android.R.color.transparent);
		for(int i = 0; i < MTOOL.felder; i++)
			for(int o = 0; o < allItems.length; o++)
				if(allItems[o][i].built)
					allItems[o][i].style_update();
	}
	
	public void update() {
		MTOOL.gridlayout(rlfeld, feld, feld_target);
		MTOOL.update(seite);
	}
}