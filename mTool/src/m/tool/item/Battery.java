package m.tool.item;

import m.tool.MTOOL;
import m.tool.R;
import m.tool.stuff.Item;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.BatteryManager;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Battery implements ItemClass{

	public void layout(int feld_nummer, int breite, int hoehe, int seite, String option) {
		for(int i = 0; i < ids.size(); i++)
			if(ids.get(i)[0].equals(MTOOL.seiten+1))
				ids.clear();
		Battery b = new Battery();
		object o = b.new object(MTOOL.context);
		MTOOL.page[seite-1].battery[feld_nummer].build(0, 0, 0, 0, 0, false, false, true, null, null, null, null, "", 0, o);
		if(!ids.contains(new Integer[]{seite, feld_nummer})) {
			ids.add(new Integer[]{seite, feld_nummer});
			ids_option.add(option);
		}
		MTOOL.page[seite-1].battery[feld_nummer].setVisibility(View.VISIBLE);
	}
	
	public Item getLayout(Context context, String option) {
		Item item = new Item(context);
		Battery b = new Battery();
		object o = b.new object(context);
		item.build(0, 0, 0, 0, 0, false, false, true, null, null, null, null, "", 0, o);
		return item;
	}
	
	public class object extends RelativeLayout {
		v b;
		public TextView tvp;
		public TextView tvc;
		public object(Context context) {
			super(context);
			this.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
			RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
			lp.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
			lp.setMargins(2, 2, 2, 2);
			b = new v(context);
			b.setLayoutParams(lp);
			this.addView(b);
			tvp = new TextView(getContext());
			this.addView(tvp);
			tvc = new TextView(getContext());
			this.addView(tvc);
		}
		public void drawn() {
			if(b.scale == 0)
				b.scale = 1;
			RelativeLayout.LayoutParams tvplp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, (int) (b.blocks[3].getBounds().height()*b.scale));
			tvplp.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
			tvp.setTextColor(0xff000000);
			tvp.setTextSize((float) (b.blocks[3].getBounds().height()*b.scale/1.5));
			tvp.setPadding(-2, -10, 0, -10);
			tvplp.topMargin = (int) ((b.y/2 - b.h*b.scale/2) + (b.blocks[3].getBounds().height()*b.scale)*3 + (b.h*b.scale*10/150) + (b.h*b.scale*2/150)*3 + 1);
			tvp.setGravity(Gravity.CENTER);
			tvp.setLayoutParams(tvplp);
			RelativeLayout.LayoutParams tvclp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, (int) (b.blocks[2].getBounds().height()*b.scale));
			tvclp.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
			tvc.setTextColor(0xff000000);
			tvc.setTextSize((float) (b.blocks[2].getBounds().height()*b.scale/2));
			tvc.setPadding(-1, -10, 0, -10);
			tvclp.topMargin = (int) ((b.y/2 - b.h*b.scale/2) + (b.blocks[2].getBounds().height()*b.scale)*2 + (b.h*b.scale*10/150) + (b.h*b.scale*2/150)*2 + 1);
			tvc.setGravity(Gravity.CENTER);
			tvc.setLayoutParams(tvclp);
		}
		public class v extends View {
			private Drawable battery_obg, battery_obg_glass;
		    public ColorDrawable[] blocks = new ColorDrawable[4];
		    private int[] colors = {0x00000000, 0x00000000, 0x00000000, 0x00000000};
		    private boolean mAttached = false;
		    private final Handler mHandler = new Handler();
		    private boolean mChanged;

		    public int x, x2;
		    public int y, y2;
		    public int w;
		    public int h;
		    
		    public float scale;
		    
		    public int mPercent;
		    
		    boolean drawn = false;
		    
		    public v(Context context) {
		    	super(context);
		    	battery_obg = context.getResources().getDrawable(R.drawable.battery_obg);
		    	battery_obg_glass = context.getResources().getDrawable(R.drawable.battery_obg_glass);
		    	for(int i = 0; i < blocks.length; i++)
		    		blocks[i] = new ColorDrawable();
		    	mChanged = true;
		    }

		    @Override
		    protected void onAttachedToWindow() {
		        super.onAttachedToWindow();
		        if (!mAttached) {
		            mAttached = true;
		            IntentFilter filter = new IntentFilter();
		            filter.addAction(Intent.ACTION_BATTERY_CHANGED);
		            try {
		            	MTOOL.mtool.unregisterReceiver(mIntentReceiver);
					} catch (Exception e) {
						e.printStackTrace();
					}
		            try {
						MTOOL.mtool.registerReceiver(mIntentReceiver, filter, null, mHandler);
					} catch (Exception e) {
						e.printStackTrace();
					}
		        }
		        invalidate();
		    }

		    @Override
		    protected void onDetachedFromWindow() {
		        super.onDetachedFromWindow();
		        if (mAttached) {
		            try {
		            	MTOOL.mtool.unregisterReceiver(mIntentReceiver);
					} catch (Exception e) {
						e.printStackTrace();
					}
		            mAttached = false;
		        }
		    }

		    @Override
		    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		        super.onSizeChanged(w, h, oldw, oldh);
		        mChanged = true;
		    }
		    
		    @Override
		    protected void onDraw(Canvas canvas) {
		        super.onDraw(canvas);
		        boolean changed = mChanged;
		        if (changed)
		            mChanged = false;
		        x = getWidth();
		        y = getHeight();
		        final Drawable batterybg = battery_obg;
		        final Drawable batterybgg = battery_obg_glass;
		        w = batterybg.getIntrinsicWidth();
		        h = batterybg.getIntrinsicHeight();
		        if (x < w || y < h) {
		            scale = Math.min((float) x / (float) w,
		                                   (float) y / (float) h);
		            canvas.save();
		            canvas.scale(scale, scale, x / 2, y / 2);
		            x2 = canvas.getWidth();
		            y2 = canvas.getHeight();
		        }
		        if (changed) {
		        	batterybg.setBounds((x / 2) - (w / 2), (y / 2) - (h / 2), (x / 2) + (w / 2), (y / 2) + (h / 2));
		        	batterybgg.setBounds((x / 2) - (w / 2), (y / 2) - (h / 2) + h*10/150, (x / 2) + (w / 2), (y / 2) + (h / 2));
		        }
		        final Drawable b1 = blocks[0];
		        if (changed)
		            b1.setBounds((x / 2) - (w / 2) , (y / 2) - (h / 2)+(h*(13+34*0)/150)-1, (x / 2) + (w / 2), (y / 2) - (h / 2)+(h*(13+34*0)/150)+(h*32/150)+1);
		        b1.draw(canvas);
		        final Drawable b2 = blocks[1];
		        if (changed)
		            b2.setBounds((x / 2) - (w / 2), (y / 2) - (h / 2)+(h*(13+34*1)/150)-1, (x / 2) + (w / 2), (y / 2) - (h / 2)+(h*(13+34*1)/150)+(h*32/150)+1);
		        b2.draw(canvas);
		        final Drawable b3 = blocks[2];
		        if (changed)
		            b3.setBounds((x / 2) - (w / 2), (y / 2) - (h / 2)+(h*(13+34*2)/150)-1, (x / 2) + (w / 2), (y / 2) - (h / 2)+(h*(13+34*2)/150)+(h*32/150)+1);
		        b3.draw(canvas);
		        final Drawable b4 = blocks[3];
		        if (changed)
		            b4.setBounds((x / 2) - (w / 2), (y / 2) - (h / 2)+(h*(13+34*3)/150)-1, (x / 2) + (w / 2), (y / 2) - (h / 2)+(h*(13+34*3)/150)+(h*32/150)+1);
		        b4.draw(canvas);
		        batterybg.draw(canvas);
		        batterybgg.draw(canvas);
	        	if(!drawn)
		        	object.this.drawn();
	        	drawn = true;
		    }

		    private final BroadcastReceiver mIntentReceiver = new BroadcastReceiver() {
		        @Override
		        public void onReceive(Context context, Intent intent) {
		        	mChanged = true;
		        	mPercent = intent.getIntExtra("level", 0);
		        	String plug = "";
		        	if(intent.getIntExtra("plugged", 0) == BatteryManager.BATTERY_PLUGGED_AC)
		        		plug = "AC";
		        	if(intent.getIntExtra("plugged", 0) == BatteryManager.BATTERY_PLUGGED_USB)
		        		plug = "USB";
		        	if(intent.getIntExtra("plugged", 0) == BatteryManager.BATTERY_PLUGGED_WIRELESS)
		        		plug = "((¡))";
		        	for(int i = 0; i < colors.length-1; i++)
		        		colors[i] = 0x00000000;
	        		colors[3] = 0xccff0000;
	        		if(mPercent >= 15)
		        		colors[3] = 0xccffff00;
	        		if(mPercent >= 25)
	        			colors[2] = 0xccffff00;
	        		if(mPercent >= 35)
	        			colors[2] = 0xcc00ff00;
		        	if(mPercent >= 50) {
		        		colors[1] = 0xcc00ff00;
		        		colors[3] = 0xcc00ff00;
		        	}
		        	if(mPercent >= 75)
		        		colors[0] = 0xcc00ff00;
		        	for(int i = 0; i < blocks.length; i++)
		        		blocks[i] = new ColorDrawable(colors[i]);
	        		object.this.tvp.setText(Integer.toString(mPercent));
	        		object.this.tvc.setText(plug);
		            invalidate();
		        }
		    };

		}
	}

	public void update() {}
	public void update(int seite, int feld_nummer, String option) {}
	public void click(String option) {
		Intent i = new Intent();
		i.setAction(Intent.ACTION_POWER_USAGE_SUMMARY);
		MTOOL.mtool.startActivity(i);
	}
	public void toggle() {}
	public void activate() {}
	public void deactivate() {}
	public int get(int what) {return 0;}
	public boolean is(int what) {return false;}
	public void set(int what, int value) {}

	public boolean gotStatus() {
		return false;
	}
	public boolean opensDialog(String option) {
		return false;
	}
}