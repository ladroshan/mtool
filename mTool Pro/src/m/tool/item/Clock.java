package m.tool.item;

import java.util.TimeZone;

import m.tool.pro.MTOOL;
import m.tool.pro.R;
import m.tool.stuff.Item;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.format.DateUtils;
import android.text.format.Time;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class Clock implements ItemClass{

	public void layout(int feld_nummer, int breite, int hoehe, int seite, String option) {
		for(int i = 0; i < ids.size(); i++)
			if(ids.get(i)[0].equals(MTOOL.seiten+1))
				ids.clear();
		Clock c = new Clock();
		object o = c.new object(MTOOL.context);
		MTOOL.page[seite-1].clock[feld_nummer].build(0, 0, 0, 0, 0, false, false, true, null, null, null, null, "", 0, o);
		if(!ids.contains(new Integer[]{seite, feld_nummer})) {
			ids.add(new Integer[]{seite, feld_nummer});
			ids_option.add(option);
		}
		MTOOL.page[seite-1].clock[feld_nummer].setVisibility(View.VISIBLE);
	}
	
	public Item getLayout(Context context, String option) {
		Item item = new Item(context);
		Clock c = new Clock();
		object o = c.new object(context);
		item.build(0, 0, 0, 0, 0, false, false, true, null, null, null, null, "", 0, o);
		return item;
	}
	
	public class object extends RelativeLayout {
		v ac;
		public object(Context context) {
			super(context);
			this.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
			ac = new v(context);
			RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
			lp.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
			lp.setMargins(2, 2, 2, 2);
			ac.setLayoutParams(lp);
			this.addView(ac);
		}
		public class v extends View {
			private Time mCalendar;

		    private Drawable mHourHand;
		    private Drawable mMinuteHand;
		    private Drawable mDial;

		    private boolean mAttached;

		    private final Handler mHandler = new Handler();
		    private float mMinutes;
		    private float mHour;
		    private boolean mChanged;

		    public v(Context context) {
		    	super(context);
		        mDial = context.getResources().getDrawable(R.drawable.appwidget_clock_dial);
		        mHourHand = context.getResources().getDrawable(R.drawable.appwidget_clock_hour);
		        mMinuteHand = context.getResources().getDrawable(R.drawable.appwidget_clock_minute);
		        mCalendar = new Time();
		    }

		    @Override
		    protected void onAttachedToWindow() {
		        super.onAttachedToWindow();
		        if (!mAttached) {
		            mAttached = true;
		            IntentFilter filter = new IntentFilter();
		            filter.addAction(Intent.ACTION_TIME_TICK);
		            filter.addAction(Intent.ACTION_TIME_CHANGED);
		            filter.addAction(Intent.ACTION_TIMEZONE_CHANGED);
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
		        mCalendar = new Time();
		        onTimeChanged();
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
		        int availableWidth = getWidth();
		        int availableHeight = getHeight();
		        int x = availableWidth / 2;
		        int y = availableHeight / 2;
		        final Drawable dial = mDial;
		        int w = dial.getIntrinsicWidth();
		        int h = dial.getIntrinsicHeight();
		        boolean scaled = false;
		        if (availableWidth < w || availableHeight < h) {
		            scaled = true;
		            float scale = Math.min((float) availableWidth / (float) w,
		                                   (float) availableHeight / (float) h);
		            canvas.save();
		            canvas.scale(scale, scale, x, y);
		        }
		        if (changed)
		            dial.setBounds(x - (w / 2), y - (h / 2), x + (w / 2), y + (h / 2));
		        dial.draw(canvas);
		        canvas.save();
		        canvas.rotate(mHour / 12.0f * 360.0f, x, y);
		        final Drawable hourHand = mHourHand;
		        if (changed) {
		            w = hourHand.getIntrinsicWidth();
		            h = hourHand.getIntrinsicHeight();
		            hourHand.setBounds(x - (w / 2), y - (h / 2), x + (w / 2), y + (h / 2));
		        }
		        hourHand.draw(canvas);
		        canvas.restore();
		        canvas.save();
		        canvas.rotate(mMinutes / 60.0f * 360.0f, x, y);
		        final Drawable minuteHand = mMinuteHand;
		        if (changed) {
		            w = minuteHand.getIntrinsicWidth();
		            h = minuteHand.getIntrinsicHeight();
		            minuteHand.setBounds(x - (w / 2), y - (h / 2), x + (w / 2), y + (h / 2));
		        }
		        minuteHand.draw(canvas);
		        canvas.restore();
		        if (scaled)
		            canvas.restore();
		    }

		    private void onTimeChanged() {
		        mCalendar.setToNow();
		        int hour = mCalendar.hour;
		        int minute = mCalendar.minute;
		        int second = mCalendar.second;
		        mMinutes = minute + second / 60.0f;
		        mHour = hour + mMinutes / 60.0f;
		        mChanged = true;
		        updateContentDescription(mCalendar);
		    }

		    private final BroadcastReceiver mIntentReceiver = new BroadcastReceiver() {
		        @Override
		        public void onReceive(Context context, Intent intent) {
		            if (intent.getAction().equals(Intent.ACTION_TIMEZONE_CHANGED)) {
		                String tz = intent.getStringExtra("time-zone");
		                mCalendar = new Time(TimeZone.getTimeZone(tz).getID());
		            }
		            onTimeChanged();
		            invalidate();
		        }
		    };

		    private void updateContentDescription(Time time) {
		        @SuppressWarnings("deprecation")
				final int flags = DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_24HOUR;
		        String contentDescription = DateUtils.formatDateTime(getContext(),
		                time.toMillis(false), flags);
		        setContentDescription(contentDescription);
		    }
		}
	}

	public void update() {}
	public void update(int seite, int feld_nummer, String option) {}
	public void click(String option) {
		PackageManager packageManager = MTOOL.context.getPackageManager();
		Intent alarmClockIntent = new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LAUNCHER);
		String clockImpls[][] = {
		        {"com.htc.android.worldclock", "com.htc.android.worldclock.WorldClockTabControl"},
		        {"com.android.deskclock", "com.android.deskclock.DeskClock"},
		        {"com.google.android.deskclock", "com.android.deskclock.DeskClock"},
		        {"com.motorola.blur.alarmclock",  "com.motorola.blur.alarmclock.AlarmClock"},
		        {"com.sec.android.app.clockpackage","com.sec.android.app.clockpackage.ClockPackage"}
		};
		boolean foundClockImpl = false;
		for(int i=0; i<clockImpls.length; i++) {
		    String packageName = clockImpls[i][0];
		    String className = clockImpls[i][1];
		    try {
		        ComponentName cn = new ComponentName(packageName, className);
		        @SuppressWarnings("unused")
				ActivityInfo aInfo = packageManager.getActivityInfo(cn, PackageManager.GET_META_DATA);
		        alarmClockIntent.setClassName(packageName, className);
		        foundClockImpl = true;
		    } catch (NameNotFoundException e) {}
		}
		if (foundClockImpl)
			MTOOL.mtool.startActivity(alarmClockIntent);
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