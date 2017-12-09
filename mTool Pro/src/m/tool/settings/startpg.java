package m.tool.settings;

import m.tool.pro.MTOOL;
import m.tool.pro.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class startpg extends Activity{
	
	int pg = 0;
	boolean customspg = false;
	Button plus;
	Button minus;
	TextView tv;
	CheckBox last_page;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startpg);

        plus = (Button)findViewById(R.id.spp);
        minus = (Button)findViewById(R.id.spm);
        tv = (TextView)findViewById(R.id.sp);
        last_page = (CheckBox)findViewById(R.id.last_page);
        
        findViewById(R.id.csp).setVisibility(View.GONE);
    	findViewById(R.id.tvsp).setVisibility(View.GONE);
        
        if(MTOOL.startpg != 0) {
        	findViewById(R.id.csp).setVisibility(View.VISIBLE);
        	findViewById(R.id.tvsp).setVisibility(View.VISIBLE);
        	customspg = true;
        }
        
        if(MTOOL.startpg == 0)
        	tv.setText(Integer.toString(MTOOL.seite));
        else
        	tv.setText(Integer.toString(MTOOL.startpg));
        
        pg = Integer.parseInt((String) tv.getText());
        
        last_page.setChecked(!customspg);
        last_page.setOnCheckedChangeListener(new OnCheckedChangeListener()
        {
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if(!isChecked) {
			        findViewById(R.id.csp).setVisibility(View.VISIBLE);
			        findViewById(R.id.tvsp).setVisibility(View.VISIBLE);
				}
				if(isChecked) {
			        findViewById(R.id.csp).setVisibility(View.GONE);
			        findViewById(R.id.tvsp).setVisibility(View.GONE);
				}
			}
        });
        
		plus.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v) {
				if(pg < MTOOL.seiten)
					pg++;
				tv.setText(Integer.toString(pg));
			}
		});
		
		minus.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v) {
				if(pg > 1)
					pg--;
				tv.setText(Integer.toString(pg));
			}
		});
	}

	public void doit(View v)
	{
		customspg = !last_page.isChecked();
		if(customspg)
			MTOOL.startpg = pg;
		else
			MTOOL.startpg = 0;
		MTOOL.stuffdb.set_startpg(MTOOL.startpg);
		MTOOL.db.updateEntry(MTOOL.stuffdb);
		MTOOL.db.close();
		finish();
	}
	
	public void close(View v)
	{
		finish();
	}
}
