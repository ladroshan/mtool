//package m.tool.pro;
//
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.app.Dialog;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.provider.Settings.Secure;
//import android.widget.Toast;
//
//import com.google.android.vending.licensing.AESObfuscator;
//import com.google.android.vending.licensing.LicenseChecker;
//import com.google.android.vending.licensing.LicenseCheckerCallback;
//import com.google.android.vending.licensing.ServerManagedPolicy;
//
//public class LicenseCheck extends Activity {
//	private class MyLicenseCheckerCallback implements LicenseCheckerCallback {
//		public void allow(int reason) {
//			if (isFinishing())
//				return;
//			startMainActivity();
//		}
//
//		@SuppressWarnings("deprecation")
//		public void dontAllow(int reason) {
//			if (isFinishing())
//				return;
//			showDialog(0);
//		}
//
//		public void applicationError(int errorCode) {
//			if (isFinishing())
//				return;
//		    String errorName = "";
//		    if(errorCode == 1)
//		    	errorName = "ERROR_INVALID_PACKAGE_NAME";
//		    if(errorCode == 2)
//		    	errorName = "ERROR_NON_MATCHING_UID";
//		    if(errorCode == 3)
//		    	errorName = "ERROR_NOT_MARKET_MANAGED";
//		    if(errorCode == 4)
//		    	errorName = "ERROR_CHECK_IN_PROGRESS";
//		    if(errorCode == 5)
//		    	errorName = "ERROR_INVALID_PUBLIC_KEY";
//		    if(errorCode == 6)
//		    	errorName = "ERROR_MISSING_PERMISSION";
//			toast("Error: " + errorName);
//			startMainActivity();
//		}
//	}
//	private static final String BASE64_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhbqibzeX/1HmkBHkwfYB8Hl/6qCSMUxy0dH8AwZiRS7wcr9C7+shkEmgUf1aRn7Vz3JqJErc4Ux0Fsl1HvGLJorC6Z7NQUgl4lRCYVwkMSHk3Fz5wPxl2RO4VjbxWcPBdbJOtuQ7D1kfujUSzV/Zb9HVIQ7Pj2krZj22DCX/YULkieLD4PGKvPhzp57T9Bh2QJ5CDWbgFUTYlybGIimDH3lee2AmMZEj5RQjZZqWeyHJFQ8eZVLG/nnKoFnxehJr8JbnjoaHWJMVl6/Zf3VvcLy/KphnyKnzXjBvbO+VWT/Ju1kca78GGff+JAt2upto04jdGtl+Si4uKwWC5X2IdQIDAQAB";
//
//	private static final byte[] SALT = new byte[] {-74, -79, 106, -99, -13, -46, 89, 86, 78, 34, 12, 1, 104, -123, -81, 16, 73, -121, 101, 64};
//	private LicenseChecker mChecker;
//
//	private LicenseCheckerCallback mLicenseCheckerCallback;
//
//	private void doCheck() {
//		mChecker.checkAccess(mLicenseCheckerCallback);
//	}
//
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		String deviceId = Secure.getString(getContentResolver(), Secure.ANDROID_ID);
//		mLicenseCheckerCallback = new MyLicenseCheckerCallback();
//		mChecker = new LicenseChecker(this, new ServerManagedPolicy(this, new AESObfuscator(SALT, getPackageName(), deviceId)), BASE64_PUBLIC_KEY);
//		doCheck();
//	}
//
//	@Override
//	protected Dialog onCreateDialog(int id) {
//		return new AlertDialog.Builder(this)
//				.setTitle("Application Not Licensed")
//				.setCancelable(false)
//				.setMessage(
//						"This application is not licensed. Please purchase it from Android Market")
//				.setPositiveButton("Buy App",
//						new DialogInterface.OnClickListener() {
//							public void onClick(DialogInterface dialog,
//									int which) {
//								Intent marketIntent = new Intent(
//										Intent.ACTION_VIEW,
//										Uri.parse("http://market.android.com/details?id=m.tool.pro"
//												+ getPackageName()));
//								startActivity(marketIntent);
//								finish();
//							}
//						})
//				.setNegativeButton("Exit",
//						new DialogInterface.OnClickListener() {
//							public void onClick(DialogInterface dialog,
//									int which) {
//								finish();
//							}
//						}).create();
//	}
//
//	@Override
//	protected void onDestroy() {
//		super.onDestroy();
//		mChecker.onDestroy();
//	}
//
//	private void startMainActivity() {
//		Intent i = new Intent(this, MTOOL.class);
//		i.putExtra("BALBLABLATHISISVERILICENSEDUNDSO133465556542", true);
//		startActivity(i);
//		finish();
//	}
//
//	public void toast(String string) {
//		Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
//	}
//}