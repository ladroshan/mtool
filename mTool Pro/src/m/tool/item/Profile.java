package m.tool.item;

import java.util.ArrayList;

import m.tool.pro.MTOOL;
import m.tool.pro.Profiles;
import m.tool.pro.R;
import m.tool.stuff.DatabaseHandler;
import m.tool.stuff.Item;
import m.tool.stuff.ProfileEntry;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

public class Profile implements ItemClass{

	public void layout(int feld_nummer, int breite, int hoehe, int seite, String option) {
		for(int i = 0; i < ids.size(); i++)
			if(ids.get(i)[0].equals(MTOOL.seiten+1))
				ids.clear();
		if(option != null && !option.equals("") && MTOOL.db.getProfileEntry(Integer.parseInt(option)) != null)
			MTOOL.page[seite-1].profile[feld_nummer].build(
					Profiles.images[Integer.parseInt(MTOOL.db.getAllProfileEntrys().get(Integer.parseInt(option)-1).get_style().split(";")[1])], 0,
					Profiles.images_light[Integer.parseInt(MTOOL.db.getAllProfileEntrys().get(Integer.parseInt(option)-1).get_style().split(";")[1])], 0,
					0, false, true, false, null, null, null, null, MTOOL.db.getAllProfileEntrys().get(Integer.parseInt(option)-1).get_name(),
					MTOOL.mtool.getResources().getIntArray(R.array.colors_int)[Integer.parseInt(MTOOL.db.getAllProfileEntrys().get(
							Integer.parseInt(option)-1).get_style().split(";")[0])], null);
		else
			MTOOL.page[seite-1].profile[feld_nummer].build(R.drawable.profiles, 0, R.drawable.profiles_light, 0,
					R.string.profiles, false, true, false, null, null, null, null, "", 0, null);
		if(!ids.contains(new Integer[]{seite, feld_nummer})) {
				ids.add(new Integer[]{seite, feld_nummer});
				ids_option.add(option);
		}
		MTOOL.page[seite-1].profile[feld_nummer].setVisibility(View.VISIBLE);
		update(seite, feld_nummer, option);
	}
	
	public Item getLayout(Context context, String option) {
		Item item = new Item(context);
		MTOOL.db = new DatabaseHandler(context);
		if(option != null && !option.equals("") && MTOOL.db.getProfileEntry(Integer.parseInt(option)) != null)
			item.build(
					Profiles.images[Integer.parseInt(MTOOL.db.getAllProfileEntrys().get(Integer.parseInt(option)-1).get_style().split(";")[1])], 0,
					Profiles.images_light[Integer.parseInt(MTOOL.db.getAllProfileEntrys().get(Integer.parseInt(option)-1).get_style().split(";")[1])], 0,
					0, false, true, false, null, null, null, null, MTOOL.db.getAllProfileEntrys().get(Integer.parseInt(option)-1).get_name(),
					MTOOL.mtool.getResources().getIntArray(R.array.colors_int)[Integer.parseInt(MTOOL.db.getAllProfileEntrys().get(
							Integer.parseInt(option)-1).get_style().split(";")[0])], null);
		else
			item.build(R.drawable.profiles, 0, R.drawable.profiles_light, 0,
					R.string.profiles, false, true, false, null, null, null, null, "", 0, null);
		return item;
	}

	public void update() {
		for(int i = 0; i < ids.size(); i++)
			update(ids.get(i)[0], ids.get(i)[1], ids_option.get(i));
	}
	
	public void update(int seite, int feld_nummer, String option) {
		if(option.equals("")) {
			String[] colors = {"cyan", "green", "blue", "yellow", "orange", "pink", "red", "blackwhite"};
			String color = ((MTOOL.profile != null) ? colors[Integer.parseInt(MTOOL.profile.get_style().split(";")[0])] : "off");
			if(MTOOL.profile != null)
				MTOOL.page[seite-1].profile[feld_nummer].setThings(color, Profiles.images[Integer.parseInt(MTOOL.profile.get_style().split(";")[1])],
					Profiles.images_light[Integer.parseInt(MTOOL.profile.get_style().split(";")[1])], null);
			if(color.equals("off"))
				MTOOL.page[seite-1].profile[feld_nummer].status.setVisibility(View.GONE);
			else
				MTOOL.page[seite-1].profile[feld_nummer].status.setVisibility(View.VISIBLE);
		}
	}

	public ArrayList<ProfileEntry> profiles;
	public void click(String option) {
		if(option == null || option.equals("")) {
			profiles = new ArrayList<ProfileEntry>();
			String[] names = new String[MTOOL.db.getAllProfileEntrys().size()];
			for(int i = 0; i < MTOOL.db.getAllProfileEntrys().size(); i++)
				profiles.add(MTOOL.db.getAllProfileEntrys().get(i));
			for(int i = 0; i < profiles.size(); i++)
				names[i] = profiles.get(i).get_name();
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MTOOL.mtool);
			alertDialogBuilder
				.setNegativeButton(android.R.string.cancel,	new DialogInterface.OnClickListener() {
			    	public void onClick(DialogInterface dialog, int id) {
			    		dialog.cancel();
			    		if(MTOOL.finish_later) {
							MTOOL.finish_later = false;
			    			MTOOL.close();
						}
			    	}
				})
				.setItems(names, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						MTOOL.activate_profile(profiles.get(which));
						dialog.cancel();
					}
				})
				.setOnCancelListener(new DialogInterface.OnCancelListener() {
					public void onCancel(DialogInterface dialog) {
						if(MTOOL.finish_later) {
							MTOOL.finish_later = false;
			    			MTOOL.close();
						}
					}
				})
				.show();
		}
		else
			MTOOL.activate_profile(MTOOL.db.getProfileEntry(Integer.parseInt(option)));
	}

	public void toggle() {}
	public void activate() {}
	public void deactivate() {}
	public int get(int what) {return 0;}
	public boolean is(int what) {return false;}
	public void set(int what, int value) {
		MTOOL.activate_profile(MTOOL.db.getProfileEntry(what));
		MTOOL.db.close();
	}

	public boolean gotStatus() {
		return false;
	}
	public boolean opensDialog(String option) {
		return true;
	}
}