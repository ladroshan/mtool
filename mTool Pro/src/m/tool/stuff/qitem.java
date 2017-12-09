package m.tool.stuff;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

@SuppressLint("ViewConstructor")
public class qitem extends RelativeLayout{

	public Switch sw;
	public CheckBox cb;
	public ImageView iv;
	public TextView tv;
	public View seperator;
	public int item = 1;
	public String option = "";
	
	public qitem(Context context, int item, String option, String name, Drawable d, DisplayMetrics metrics, boolean last_one) {
		super(context);
		
		this.item = item;
		this.option = option;
	    float logicalDensity = metrics.density;
	    this.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) (70 * logicalDensity + 0.5)));

		iv = new ImageView(getContext());
		iv.setImageDrawable(d);
		RelativeLayout.LayoutParams ivlp = new RelativeLayout.LayoutParams((int) (50 * logicalDensity + 0.5), (int) (50 * logicalDensity + 0.5));
		ivlp.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
		ivlp.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		ivlp.leftMargin = (int) (10 * logicalDensity + 0.5);
		iv.setLayoutParams(ivlp);
		this.addView(iv);
		
		tv = new TextView(getContext());
		tv.setText(name);
		tv.setTextSize(24);
		tv.setTextColor(Color.parseColor("#ffffff"));
		tv.setShadowLayer(5, 0, 0, Color.parseColor("#ffffff"));
		RelativeLayout.LayoutParams tvlp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		tvlp.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		tvlp.leftMargin = (int) (70 * logicalDensity + 0.5);
		tv.setLayoutParams(tvlp);
		this.addView(tv);
		
		cb = new CheckBox(getContext());
		RelativeLayout.LayoutParams cblp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, (int) (50 * logicalDensity + 0.5));
		cblp.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		cblp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
		cblp.rightMargin = (int) (10 * logicalDensity + 0.5);
		cb.setLayoutParams(cblp);
		this.addView(cb);
		
		seperator = new View(getContext());
		RelativeLayout.LayoutParams slp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, (int) (logicalDensity + 0.5));
		slp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
		slp.rightMargin = (int) (4 * logicalDensity + 0.5);
		slp.leftMargin = (int) (4 * logicalDensity + 0.5);
		seperator.setBackgroundColor(0x60888888);
		seperator.setLayoutParams(slp);
		if(!last_one)
			this.addView(seperator);
	}
	
	public boolean isChecked(){
		return cb.isChecked();
	}
}