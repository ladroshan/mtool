package m.tool.stuff;

import m.tool.MTOOL;
import m.tool.R;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class TorchActivity extends Activity {

	boolean isLighOn = false;
	Camera camera;
	LinearLayout lightScreen;
	Button buttonFlash;

	@Override
	protected void onStop() {
		super.onStop();
		if (camera != null) {
			camera.release();
		}
		if(MTOOL.finish_later) {
			MTOOL.close();
			MTOOL.mtool.finish();
		}
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		if (camera != null) {
			camera.release();
		}
		if(MTOOL.finish_later) {
			MTOOL.close();
			MTOOL.mtool.finish();
		} finish();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.torch_activity);
		lightScreen = (LinearLayout)findViewById(R.id.lightScreen);
		if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
			Toast.makeText(getApplicationContext(), R.string.no_torch, Toast.LENGTH_SHORT).show();
		} else {
			camera = Camera.open();
			final Parameters p = camera.getParameters();
			p.setFlashMode(Parameters.FLASH_MODE_TORCH);
			camera.setParameters(p);
			camera.startPreview();
			lightScreen.setBackgroundResource(R.drawable.lightbulb);
			isLighOn = true;
			lightScreen.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					if (isLighOn) {
						p.setFlashMode(Parameters.FLASH_MODE_OFF);
						camera.setParameters(p);
						camera.stopPreview();
						lightScreen.setBackgroundResource(R.drawable.lightbulb_off);
						isLighOn = false;
					} else {
						p.setFlashMode(Parameters.FLASH_MODE_TORCH);
						camera.setParameters(p);
						camera.startPreview();
						lightScreen.setBackgroundResource(R.drawable.lightbulb);
						isLighOn = true;
					}
				}
			});
		}
	}
	public void close(View v) {
		finish();
	}
}