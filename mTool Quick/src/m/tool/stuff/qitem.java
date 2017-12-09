package m.tool.stuff;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;

@SuppressLint("ViewConstructor")
public class qitem extends RelativeLayout{

	public Switch sw;
	public CheckBox cb;
	public ImageView iv;
	public AutoResizeTextView tv;
	public View seperator;
	public int item = 1;
	float logicalDensity;
	public String option = "";
	
	public qitem(Context context, int item, String option, String name, Drawable d, DisplayMetrics metrics, boolean last_one) {
		super(context);
		
		this.item = item;
		this.option = option;
	    logicalDensity = metrics.density;
	    this.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) (70 * logicalDensity + 0.5)));

		iv = new ImageView(getContext());
		iv.setImageDrawable(d);
		RelativeLayout.LayoutParams ivlp = new RelativeLayout.LayoutParams((int) (50 * logicalDensity + 0.5), (int) (50 * logicalDensity + 0.5));
		ivlp.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
		ivlp.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		ivlp.leftMargin = (int) (10 * logicalDensity + 0.5);
		iv.setLayoutParams(ivlp);
		this.addView(iv);
		
		tv = new AutoResizeTextView(getContext());
		tv.setText(name);
		tv.setTextSize(26);
		tv.setTextColor(Color.parseColor("#ffffff"));
		tv.setShadowLayer(5, 0, 0, Color.parseColor("#ffffff"));
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
	
	@SuppressLint("DrawAllocation")
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		RelativeLayout.LayoutParams tvlp = new RelativeLayout.LayoutParams(getWidth()-(int) (120 * logicalDensity + 0.5), RelativeLayout.LayoutParams.WRAP_CONTENT);
		tvlp.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		tvlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
		tv.setPadding(0, 5, 0, 5);
		tv.setSingleLine(true);
		tvlp.rightMargin = (int) (55 * logicalDensity + 0.5);
		tv.setGravity(Gravity.RIGHT);
		tv.setLayoutParams(tvlp);
	}
	
	@Override
	protected void onVisibilityChanged(View changedView, int visibility) {
		super.onVisibilityChanged(changedView, visibility);
		RelativeLayout.LayoutParams tvlp = new RelativeLayout.LayoutParams(getWidth()-(int) (120 * logicalDensity + 0.5), RelativeLayout.LayoutParams.WRAP_CONTENT);
		tvlp.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		tvlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
		tv.setPadding(0, 5, 0, 5);
		tv.setSingleLine(true);
		tvlp.rightMargin = (int) (55 * logicalDensity + 0.5);
		tv.setGravity(Gravity.RIGHT);
		tv.setLayoutParams(tvlp);
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		RelativeLayout.LayoutParams tvlp = new RelativeLayout.LayoutParams(getWidth()-(int) (120 * logicalDensity + 0.5), RelativeLayout.LayoutParams.WRAP_CONTENT);
		tvlp.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		tvlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
		tv.setPadding(0, 5, 0, 5);
		tv.setSingleLine(true);
		tvlp.rightMargin = (int) (55 * logicalDensity + 0.5);
		tv.setGravity(Gravity.RIGHT);
		tv.setLayoutParams(tvlp);
	}
	
	public boolean isChecked(){
		return cb.isChecked();
	}
}