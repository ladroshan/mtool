package m.tool.settings;

import m.tool.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class info extends Activity{
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);
	}
	public void close(View v)
	{
		finish();
	}
}
