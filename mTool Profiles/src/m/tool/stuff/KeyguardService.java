package m.tool.stuff;

import m.tool.profiles.MTOOL;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

@SuppressWarnings("deprecation")
public class KeyguardService extends Service{

	KeyguardManager km;
	KeyguardLock lock;
	
	@Override
	public void onCreate() {
		super.onCreate();
		km = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
    	lock = km.newKeyguardLock(getClass().getSimpleName());
    	lock.disableKeyguard();
    	MTOOL.keyguardOff = true;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		lock.reenableKeyguard();
		MTOOL.keyguardOff = false;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}