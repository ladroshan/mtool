package m.tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

import m.tool.settings.pickbg;
import m.tool.settings.startpg;
import m.tool.settings.statussc;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;

public class Settings extends PreferenceActivity{

	PreferenceCategory generalPrefCat;
		PreferenceScreen appearance_pref;
			PreferenceCategory backgroundPrefCat;
				CheckBoxPreference defaultbg_pref;
				CheckBoxPreference custombg_pref;
				PreferenceScreen bgalpha_pref;
			PreferenceCategory itemstylePrefCat;
				ListPreference theme_pref;
				PreferenceScreen icsize_pref;
				CheckBoxPreference itemtext_pref;
				PreferenceScreen italpha_pref;
			PreferenceCategory otherapPrefCat;
//				ListPreference gridsize_pref;
				CheckBoxPreference mainbg_pref;
				CheckBoxPreference dad_hint_pref;
		PreferenceScreen startpg_pref;
		CheckBoxPreference quickstart_pref;
		CheckBoxPreference autobr_pref;
		CheckBoxPreference statussc_pref;
//		CheckBoxPreference switchnclose_pref;
//	PreferenceCategory backupPrefCat;
		/*PreferenceScreen backup_pref;
		PreferenceScreen restore_pref;*/
		PreferenceScreen reset_pref;
	PreferenceCategory aboutPrefCat;
		PreferenceScreen info_pref;
		PreferenceScreen tutorial_pref;
		
	CheckBoxPreference noAnimations;
	
    @SuppressWarnings("deprecation")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPreferenceScreen(createPreferenceHierarchy());
    }

    @SuppressWarnings({ "deprecation"})
	public PreferenceScreen createPreferenceHierarchy(){

    	PreferenceScreen root = getPreferenceManager().createPreferenceScreen(this);

        generalPrefCat = new PreferenceCategory(this);
        generalPrefCat.setTitle(R.string.general_prefcat);
        root.addPreference(generalPrefCat);

        appearance_pref = getPreferenceManager().createPreferenceScreen(this);
        appearance_pref.setKey("appearance_pref");
        appearance_pref.setTitle(R.string.appearance_pref_title);
        appearance_pref.setSummary(R.string.appearance_pref_summary);
        generalPrefCat.addPreference(appearance_pref);
        
        backgroundPrefCat = new PreferenceCategory(this);
        backgroundPrefCat.setTitle(R.string.background_prefcat);
        appearance_pref.addPreference(backgroundPrefCat);
        
        defaultbg_pref = new CheckBoxPreference(this);
        defaultbg_pref.setKey("defaultbg_pref");
        defaultbg_pref.setTitle(R.string.defaultbg_pref_title);
        defaultbg_pref.setSummary(R.string.defaultbg_pref_summary);
        defaultbg_pref.setDefaultValue(true);
        defaultbg_pref.setOnPreferenceChangeListener(new OnPreferenceChangeListener(){
			public boolean onPreferenceChange(Preference arg0, Object arg1) {
				defaultbg_pref.setChecked(true);
				custombg_pref.setChecked(false);
				return false;
			}
        });
        backgroundPrefCat.addPreference(defaultbg_pref);

        custombg_pref = new CheckBoxPreference(this);
        custombg_pref.setKey("custombg_pref");
        custombg_pref.setTitle(R.string.custombg_pref_title);
        custombg_pref.setSummary(R.string.custombg_pref_summary);
        custombg_pref.setDefaultValue(false);
        custombg_pref.setOnPreferenceChangeListener(new OnPreferenceChangeListener(){
			public boolean onPreferenceChange(Preference arg0, Object arg1) {
				defaultbg_pref.setChecked(false);
				custombg_pref.setChecked(true);
				Intent i = new Intent(getApplicationContext(), pickbg.class);
				startActivity(i);
				return false;
			}
        });
        backgroundPrefCat.addPreference(custombg_pref);
        
        bgalpha_pref = getPreferenceManager().createPreferenceScreen(this);
        bgalpha_pref.setKey("bgalpha_pref");
        bgalpha_pref.setOnPreferenceClickListener(new OnPreferenceClickListener(){
			public boolean onPreferenceClick(Preference preference) {
				final sbView tv = new sbView(Settings.this, MTOOL.bgalpha, 255);
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Settings.this);
				alertDialogBuilder
					.setPositiveButton(android.R.string.ok,	new DialogInterface.OnClickListener() {
				    	public void onClick(DialogInterface dialog, int id) {
				    		MTOOL.stuffdb.set_bgalpha(tv.sb.getProgress());
							MTOOL.db.updateEntry(MTOOL.stuffdb);
							MTOOL.db.close();
				    	}
					})	
					.setNegativeButton(android.R.string.cancel,	new DialogInterface.OnClickListener() {
				    	public void onClick(DialogInterface dialog, int id) {
				    		dialog.cancel();
				    	}
					})
					.setTitle(R.string.alpha_pref_title)
					.setView(tv)
					.show();
				return false;
			}
        });
        bgalpha_pref.setTitle(R.string.alpha_pref_title);
        bgalpha_pref.setSummary(R.string.bgalpha_pref_summary);
        backgroundPrefCat.addPreference(bgalpha_pref);
        
        itemstylePrefCat = new PreferenceCategory(this);
        itemstylePrefCat.setTitle(R.string.itemstyle_prefcat);
        appearance_pref.addPreference(itemstylePrefCat);
        
        theme_pref = new ListPreference(this);
        theme_pref.setKey("theme_pref");
        theme_pref.setEntries(R.array.theme);
        theme_pref.setEntryValues(R.array.theme_values);
        theme_pref.setTitle(R.string.theme_pref_title);
        theme_pref.setSummary("%s");
        theme_pref.setDialogTitle(R.string.theme_pref_title);
        theme_pref.setDefaultValue("dark");
        itemstylePrefCat.addPreference(theme_pref);
        
        icsize_pref = getPreferenceManager().createPreferenceScreen(this);
        icsize_pref.setKey("icsize_pref");
        icsize_pref.setOnPreferenceClickListener(new OnPreferenceClickListener(){
			public boolean onPreferenceClick(Preference preference) {
				final sbView tv = new sbView(Settings.this, MTOOL.icsize-5, 15);
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Settings.this);
				alertDialogBuilder
					.setPositiveButton(android.R.string.ok,	new DialogInterface.OnClickListener() {
				    	public void onClick(DialogInterface dialog, int id) {
				    		MTOOL.stuffdb.set_icsize(tv.sb.getProgress()+5);
							MTOOL.db.updateEntry(MTOOL.stuffdb);
							MTOOL.db.close();
				    	}
					})	
					.setNegativeButton(android.R.string.cancel,	new DialogInterface.OnClickListener() {
				    	public void onClick(DialogInterface dialog, int id) {
				    		dialog.cancel();
				    	}
					})
					.setTitle(R.string.icsize_pref_title)
					.setView(tv)
					.show();
				return false;
			}
        });
        icsize_pref.setTitle(R.string.icsize_pref_title);
        icsize_pref.setSummary(R.string.icsize_pref_summary);
        itemstylePrefCat.addPreference(icsize_pref);
        
        itemtext_pref = new CheckBoxPreference(this);
        itemtext_pref.setKey("itemtext_pref");
        itemtext_pref.setTitle(R.string.itemtext_pref_title);
        itemtext_pref.setSummary(R.string.itemtext_pref_summary);
        itemtext_pref.setDefaultValue(false);
        itemstylePrefCat.addPreference(itemtext_pref);
        
        italpha_pref = getPreferenceManager().createPreferenceScreen(this);
        italpha_pref.setKey("italpha_pref");
        italpha_pref.setOnPreferenceClickListener(new OnPreferenceClickListener(){
			public boolean onPreferenceClick(Preference preference) {
				final sbView tv = new sbView(Settings.this, MTOOL.italpha, 255);
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Settings.this);
				alertDialogBuilder
					.setPositiveButton(android.R.string.ok,	new DialogInterface.OnClickListener() {
				    	public void onClick(DialogInterface dialog, int id) {
				    		MTOOL.stuffdb.set_italpha(tv.sb.getProgress());
							MTOOL.db.updateEntry(MTOOL.stuffdb);
							MTOOL.db.close();
				    	}
					})	
					.setNegativeButton(android.R.string.cancel,	new DialogInterface.OnClickListener() {
				    	public void onClick(DialogInterface dialog, int id) {
				    		dialog.cancel();
				    	}
					})
					.setTitle(R.string.alpha_pref_title)
					.setView(tv)
					.show();
				return false;
			}
        });
        italpha_pref.setTitle(R.string.alpha_pref_title);
        italpha_pref.setSummary(R.string.italpha_pref_summary);
        itemstylePrefCat.addPreference(italpha_pref);
        
        otherapPrefCat = new PreferenceCategory(this);
        otherapPrefCat.setTitle(R.string.other_prefcat);
        appearance_pref.addPreference(otherapPrefCat);
        
//        gridsize_pref = new ListPreference(this);
//        gridsize_pref.setKey("gridsize_pref");
//        gridsize_pref.setEntries(R.array.gridsize);
//        gridsize_pref.setEntryValues(R.array.gridsize_values);
//        gridsize_pref.setTitle(R.string.gridsize_pref_title);
//        gridsize_pref.setSummary("%s");
//        gridsize_pref.setDialogTitle(R.string.gridsize_pref_title);
//        gridsize_pref.setDefaultValue("4x4");
//        gridsize_pref.setOnPreferenceClickListener(new OnPreferenceClickListener(){
//			public boolean onPreferenceClick(Preference preference) {
//				Toast.makeText(getApplicationContext(), R.string.gridsize_note, Toast.LENGTH_LONG).show();
//				return false;
//			}
//        });
//        otherapPrefCat.addPreference(gridsize_pref);
        
        mainbg_pref = new CheckBoxPreference(this);
        mainbg_pref.setKey("mainbg_pref");
        mainbg_pref.setTitle(R.string.mainbg_pref_title);
        mainbg_pref.setSummary(R.string.mainbg_pref_summary);
        mainbg_pref.setDefaultValue(false);
        otherapPrefCat.addPreference(mainbg_pref);
        
        dad_hint_pref = new CheckBoxPreference(this);
        dad_hint_pref.setKey("dad_hint_pref");
        dad_hint_pref.setTitle(R.string.dad_hint_pref_title);
        dad_hint_pref.setSummary(R.string.dad_hint_pref_summary);
        dad_hint_pref.setDefaultValue(false);
        otherapPrefCat.addPreference(dad_hint_pref);
        
        quickstart_pref = new CheckBoxPreference(this);
        quickstart_pref.setKey("quickstart_pref");
        quickstart_pref.setTitle(R.string.quickstart_pref_title);
        quickstart_pref.setSummary(R.string.quickstart_pref_summary);
        quickstart_pref.setDefaultValue(false);
//        generalPrefCat.addPreference(quickstart_pref);
        
        startpg_pref = getPreferenceManager().createPreferenceScreen(this);
        startpg_pref.setKey("startpg_pref");
        startpg_pref.setIntent(new Intent(getApplicationContext(), startpg.class));
        startpg_pref.setTitle(R.string.startpg_pref_title);
        startpg_pref.setSummary(R.string.startpg_pref_summary);
        generalPrefCat.addPreference(startpg_pref);
        
        autobr_pref = new CheckBoxPreference(this);
        autobr_pref.setKey("autobr_pref");
        autobr_pref.setTitle(R.string.autobr_pref_title);
        autobr_pref.setSummary(R.string.autobr_pref_summary);
        autobr_pref.setDefaultValue(false);
        generalPrefCat.addPreference(autobr_pref);
        
        statussc_pref = new CheckBoxPreference(this);
        statussc_pref.setKey("statussc_pref");
        statussc_pref.setTitle(R.string.statussc_pref_title);
        statussc_pref.setSummary(R.string.statussc_pref_summary);
        statussc_pref.setDefaultValue(false);
        statussc_pref.setIntent(new Intent(getApplicationContext(), statussc.class));
        generalPrefCat.addPreference(statussc_pref);
        
//        switchnclose_pref = new CheckBoxPreference(this);
//        switchnclose_pref.setKey("switchnclose_pref");
//        switchnclose_pref.setTitle(R.string.switchnclose_pref_title);
//        switchnclose_pref.setSummary(R.string.switchnclose_pref_summary);
//        switchnclose_pref.setDefaultValue(false);
//        generalPrefCat.addPreference(switchnclose_pref);
        
//        backupPrefCat = new PreferenceCategory(this);
//        backupPrefCat.setTitle(R.string.backup_prefcat);
//        root.addPreference(backupPrefCat);
//        
//        backup_pref = getPreferenceManager().createPreferenceScreen(this);
//        backup_pref.setKey("backup_pref");
//        backup_pref.setOnPreferenceClickListener(new OnPreferenceClickListener(){
//			public boolean onPreferenceClick(Preference preference) {
//				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Settings.this);
//				alertDialogBuilder
//					.setPositiveButton(android.R.string.ok,	new DialogInterface.OnClickListener() {
//				    	public void onClick(DialogInterface dialog, int id) {
//				    		backup.doit();
//				    	}
//					})	
//					.setNegativeButton(android.R.string.cancel,	new DialogInterface.OnClickListener() {
//				    	public void onClick(DialogInterface dialog, int id) {
//				    		dialog.cancel();
//				    	}
//					})
//					.setTitle(R.string.backup_pref_title)
//					.setMessage(R.string.backup_note)
//					.show();
//				return false;
//			}
//        });
//        backup_pref.setTitle(R.string.backup_pref_title);
//        backup_pref.setSummary(R.string.backup_pref_summary);
//        backupPrefCat.addPreference(backup_pref);
//        
//        restore_pref = getPreferenceManager().createPreferenceScreen(this);
//        restore_pref.setKey("restore_pref");
//        restore_pref.setOnPreferenceClickListener(new OnPreferenceClickListener(){
//			public boolean onPreferenceClick(Preference preference) {
//				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Settings.this);
//				alertDialogBuilder
//					.setPositiveButton(android.R.string.ok,	new DialogInterface.OnClickListener() {
//				    	public void onClick(DialogInterface dialog, int id) {
//				    		restore.doit();
//				    	}
//					})	
//					.setNegativeButton(android.R.string.cancel,	new DialogInterface.OnClickListener() {
//				    	public void onClick(DialogInterface dialog, int id) {
//				    		dialog.cancel();
//				    	}
//					})
//					.setTitle(R.string.restore_pref_title);
//		        File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/mTool/backup/");
//		    	if(!dir.exists())
//		    		alertDialogBuilder.setMessage(R.string.no_backup);
//		    	else
//		    		alertDialogBuilder.setMessage(R.string.restore_note);
//		    	alertDialogBuilder.show();
//				return false;
//			}
//        });
//        restore_pref.setTitle(R.string.restore_pref_title);
//        restore_pref.setSummary(R.string.restore_pref_summary);
//        backupPrefCat.addPreference(restore_pref);
        
        reset_pref = getPreferenceManager().createPreferenceScreen(this);
        reset_pref.setKey("reset_pref");
        reset_pref.setOnPreferenceClickListener(new OnPreferenceClickListener(){
			public boolean onPreferenceClick(Preference preference) {
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Settings.this);
				alertDialogBuilder
					.setPositiveButton(android.R.string.ok,	new DialogInterface.OnClickListener() {
				    	public void onClick(DialogInterface dialog, int id) {
				    		reset.doit(Settings.this);
				    	}
					})	
					.setNegativeButton(android.R.string.cancel,	new DialogInterface.OnClickListener() {
				    	public void onClick(DialogInterface dialog, int id) {
				    		dialog.cancel();
				    	}
					})
					.setTitle(R.string.reset_pref_title)
					.setMessage(R.string.reset_note)
					.show();
				return false;
			}
        });
        reset_pref.setTitle(R.string.reset_pref_title);
        reset_pref.setSummary(R.string.reset_pref_summary);
        generalPrefCat.addPreference(reset_pref);
        
        aboutPrefCat = new PreferenceCategory(this);
        aboutPrefCat.setTitle(R.string.about_prefcat);
        root.addPreference(aboutPrefCat);
        
        info_pref = getPreferenceManager().createPreferenceScreen(this);
        info_pref.setKey("info_pref");
        Intent appIntent = new Intent(Intent.ACTION_VIEW);
        appIntent.setData(Uri.parse("market://details?id=" + getPackageName()));
        info_pref.setIntent(appIntent);
        info_pref.setTitle(R.string.info_pref_title);
        try {
        	info_pref.setSummary(getString(R.string.info_pref_summary) + MTOOL.context.getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
        aboutPrefCat.addPreference(info_pref);
        
        return root;
    }
    
    public static class sbView extends RelativeLayout {
    	SeekBar sb;
    	public sbView(Context c, int progress, int max){
    		super(c);
    		this.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
    		sb = new SeekBar(c);
    		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
    		lp.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
    		lp.topMargin = 50;
    		sb.setMax(max);
    		sb.setProgress(progress);
    		sb.setLayoutParams(lp);
    		this.addView(sb);
		}
    }
    
    public static class reset {
    	public static void doit(Settings settings) {
    		you();
    		stuff();
    		profiles();
    		appwidget();
    		settings();
    		settings.startActivity(new Intent(settings.getApplicationContext(), statussc.class));
    		settings.finish();
    	}
    	
    	public static void you() {
    		while(MTOOL.db.getAllEntrys().size() > 0)
	    		for(int i = 0; i < MTOOL.db.getAllEntrys().size(); i++)
	    			MTOOL.db.deleteEntry(MTOOL.db.getAllEntrys().get(i));
    		MTOOL.db.close();
    		MTOOL.new_data = true;
    	}
    	
    	public static void stuff() {
    		while(MTOOL.db.getAllStuffEntrys().size() > 0)
	    		for(int i = 0; i < MTOOL.db.getAllStuffEntrys().size(); i++)
	    			MTOOL.db.deleteEntry(MTOOL.db.getAllStuffEntrys().get(i));
    		MTOOL.db.close();
    		MTOOL.new_data = true;
    	}
    	
    	public static void profiles() {
    		while(MTOOL.db.getAllProfileEntrys().size() > 0)
	    		for(int i = 0; i < MTOOL.db.getAllProfileEntrys().size(); i++)
	    			MTOOL.db.deleteEntry(MTOOL.db.getAllProfileEntrys().get(i));
    		MTOOL.db.close();
    		MTOOL.new_data = true;
    	}
    	
    	public static void appwidget() {
    		while(MTOOL.db.getAllAppwidgetEntrys().size() > 0)
	    		for(int i = 0; i < MTOOL.db.getAllAppwidgetEntrys().size(); i++)
	    			MTOOL.db.deleteEntry(MTOOL.db.getAllAppwidgetEntrys().get(i));
    		MTOOL.db.close();
    		MTOOL.new_data = true;
    	}
    	
    	public static void settings() {
        	SharedPreferences.Editor editor = MTOOL.prefs.edit();
        	editor.clear();
    		editor.commit();
    	}
    }
    public static class restore {
    	@SuppressWarnings("resource")
		public static void doit()
    	{
    		File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/mTool/backup/");
        	if(dir.exists()) {
	        	try {
	    	        File sd = Environment.getExternalStorageDirectory();
	    	        File data = Environment.getDataDirectory();
	
	    	        if (sd.canWrite()) {
	
	    	        	String backupDBPath = "/data/onno.app.mtool/databases/MTOOL.db";
	    	            String currentDBPath = "/mTool/backup/db.mtool";
	    	            File currentDB = new File(data, currentDBPath);
	    	            File backupDB = new File(sd, backupDBPath);
	    	            
	
	    	            if (currentDB.exists()) {
	    					FileChannel src = new FileInputStream(currentDB).getChannel();
	    	                FileChannel dst = new FileOutputStream(backupDB).getChannel();
	    	                dst.transferFrom(src, 0, src.size());
	    	                src.close();
	    	                dst.close();
	    	            }
	    	            String backupsePath = "/data/onno.app.mtool/shared_prefs/onno.app.mtool_preferences.xml";
	    	            String currentsePath = "/mTool/backup/set.mtool";
	    	            File currentse = new File(data, currentsePath);
	    	            File backupse = new File(sd, backupsePath);
	
	    	            if (currentse.exists()) {
	    					FileChannel src = new FileInputStream(currentse).getChannel();
	    	                FileChannel dst = new FileOutputStream(backupse).getChannel();
	    	                dst.transferFrom(src, 0, src.size());
	    	                src.close();
	    	                dst.close();
	    	            }
	    	        }
	    	    } catch (Exception e) {
	    	    	e.printStackTrace();
	    	    }
	        	MTOOL.new_data = true;
	    		Intent i = new Intent(MTOOL.context, statussc.class);
	    		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    		MTOOL.context.startActivity(i);
        	}
    	}
    }
    public static class backup {
    	@SuppressWarnings("resource")
		public static void doit()
    	{
    		boolean ok = true;
    		try {
    	        File sd = Environment.getExternalStorageDirectory();
    	        File data = Environment.getDataDirectory();

    	        if (sd.canWrite()) {
    	        	File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/mTool/backup/");
    	        	if(!dir.exists()) {
    	        		dir.mkdirs();
    	        	}
    	        	
    	            String currentDBPath = "/data/onno.app.mtool/databases/MTOOL.db";
    	            String backupDBPath = "/mTool/backup/db.mtool";
    	            File currentDB = new File(data, currentDBPath);
    	            File backupDB = new File(sd, backupDBPath);
    	            

    	            if (currentDB.exists()) {
    					FileChannel src = new FileInputStream(currentDB).getChannel();
    	                FileChannel dst = new FileOutputStream(backupDB).getChannel();
    	                dst.transferFrom(src, 0, src.size());
    	                src.close();
    	                dst.close();
    	            }
    	            String currentsePath = "/data/onno.app.mtool/shared_prefs/onno.app.mtool_preferences.xml";
    	            String backupsePath = "/mTool/backup/set.mtool";
    	            File currentse = new File(data, currentsePath);
    	            File backupse = new File(sd, backupsePath);

    	            if (currentse.exists()) {
    					FileChannel src = new FileInputStream(currentse).getChannel();
    	                FileChannel dst = new FileOutputStream(backupse).getChannel();
    	                dst.transferFrom(src, 0, src.size());
    	                src.close();
    	                dst.close();
    	            }
    	        }
    	    } catch (Exception e) {
    	    	e.printStackTrace();
    	    	ok = false;
    	    }
        
    		if(ok)
        		Toast.makeText(MTOOL.context, R.string.backup_ok, Toast.LENGTH_SHORT).show();
    		else
        		Toast.makeText(MTOOL.context, R.string.backup_not_ok, Toast.LENGTH_SHORT).show();
    	}
    }
}