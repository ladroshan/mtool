//package m.tool.item;
//
//import java.util.ArrayList;
//
//import m.tool.profiles.MTOOL;
//import m.tool.stuff.ProfileEntry;
//import android.app.AlertDialog;
//import android.content.Context;
//import android.content.DialogInterface;
//
//public class Profile implements ItemClass{
//
////	public Item getLayout(Context context, String option) {
////		Item item = new Item(context);
////		MTOOL.db = new DatabaseHandler(context);
////		if(option != null && !option.equals("") && MTOOL.db.getProfileEntry(Integer.parseInt(option)) != null)
////			item.build(
////					MTOOL.images[Integer.parseInt(MTOOL.db.getAllProfileEntrys().get(Integer.parseInt(option)-1).get_style().split(";")[1])], 0,
////					MTOOL.images_light[Integer.parseInt(MTOOL.db.getAllProfileEntrys().get(Integer.parseInt(option)-1).get_style().split(";")[1])], 0,
////					0, false, true, false, null, null, null, null, MTOOL.db.getAllProfileEntrys().get(Integer.parseInt(option)-1).get_name(),
////					context.getResources().getIntArray(R.array.colors_int)[Integer.parseInt(MTOOL.db.getAllProfileEntrys().get(
////							Integer.parseInt(option)-1).get_style().split(";")[0])], null);
////		else
////			item.build(R.drawable.profiles, 0, R.drawable.profiles_light, 0,
////					R.string.profiles, false, true, false, null, null, null, null, "", 0, null);
////		return item;
////	}
//
//
//	public ArrayList<ProfileEntry> profiles;
//	public void click(String option, final Context context) {
//		if(option == null || option.equals("")) {
//			profiles = new ArrayList<ProfileEntry>();
//			String[] names = new String[MTOOL.db.getAllProfileEntrys().size()];
//			for(int i = 0; i < MTOOL.db.getAllProfileEntrys().size(); i++)
//				profiles.add(MTOOL.db.getAllProfileEntrys().get(i));
//			for(int i = 0; i < profiles.size(); i++)
//				names[i] = profiles.get(i).get_name();
//			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
//			alertDialogBuilder
//				.setNegativeButton(android.R.string.cancel,	new DialogInterface.OnClickListener() {
//			    	public void onClick(DialogInterface dialog, int id) {
//			    		dialog.cancel();
//		    			System.exit(0);
//			    	}
//				})
//				.setItems(names, new DialogInterface.OnClickListener() {
//					public void onClick(DialogInterface dialog, int which) {
//						MTOOL.activate_profile(profiles.get(which), context);
//						dialog.cancel();
//					}
//				})
//				.setOnCancelListener(new DialogInterface.OnCancelListener() {
//					public void onCancel(DialogInterface dialog) {
//						System.exit(0);
//						MTOOL.mtool.finish();
//					}
//				})
//				.show();
//		}
//		else
//			MTOOL.activate_profile(MTOOL.db.getProfileEntry(Integer.parseInt(option)), context);
//		MTOOL.mtool.finish();
//	}
//
//	public void activate(Context context) {}
//	public void deactivate(Context context) {}
//	public int get(int what, Context context) {return 0;}
//	public boolean is(int what, Context context) {return false;}
//	public void set(int what, int value, Context context) {
//		MTOOL.activate_profile(MTOOL.db.getProfileEntry(what), context);
//		MTOOL.db.close();
//	}
//
//	public boolean gotStatus() {
//		return false;
//	}
//	public boolean opensDialog(String option) {
//		return true;
//	}
//}