package m.tool.stuff;

import java.util.ArrayList;

import m.tool.pro.PageView;
import m.tool.pro.R;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class ScrollViewPager extends HorizontalScrollView {
    private static final int SWIPE_MIN_DISTANCE = 5;
    private static final int SWIPE_THRESHOLD_VELOCITY = 300;
 
    private ArrayList<PageView> mItems = null;
    private GestureDetector mGestureDetector;
    private int mActiveFeature = 0;
 
    public ScrollViewPager(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
 
    public ScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
 
    public ScrollViewPager(Context context) {
        super(context);
    }
 
    @SuppressWarnings("deprecation")
	public void setFeatureItems(ArrayList<PageView> items){
        LinearLayout internalWrapper = new LinearLayout(getContext());
        internalWrapper.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        internalWrapper.setOrientation(LinearLayout.HORIZONTAL);
        addView(internalWrapper);
        this.mItems = items;
        for(int i = 0; i< items.size();i++){
            RelativeLayout featureLayout = (RelativeLayout) View.inflate(this.getContext(),R.layout.x44,null);
            Button b = new Button(getContext());
            b.setLayoutParams(new RelativeLayout.LayoutParams(500, 100));
            b.setText("Hallo");
            featureLayout.addView(b);
            //...
          //Create the view for each screen in the scroll view
            //...
            internalWrapper.addView(items.get(i));
        }
        setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                //If the user swipes
                if (mGestureDetector.onTouchEvent(event)) {
                    return true;
                }
                else if(event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL ){
                    int scrollX = getScrollX();
                    int featureWidth = v.getMeasuredWidth();
                    mActiveFeature = ((scrollX + (featureWidth/2))/featureWidth);
                    int scrollTo = mActiveFeature*featureWidth;
                    smoothScrollTo(scrollTo, 0);
                    return true;
                }
                else{
                    return false;
                }
            }
        });
        mGestureDetector = new GestureDetector(new MyGestureDetector());
    }
        class MyGestureDetector extends SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                //right to left
                if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    int featureWidth = getMeasuredWidth();
                    mActiveFeature = (mActiveFeature < (mItems.size() - 1))? mActiveFeature + 1:mItems.size() -1;
                    smoothScrollTo(mActiveFeature*featureWidth, 0);
                    return true;
                }
                //left to right
                else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    int featureWidth = getMeasuredWidth();
                    mActiveFeature = (mActiveFeature > 0)? mActiveFeature - 1:0;
                    smoothScrollTo(mActiveFeature*featureWidth, 0);
                    return true;
                }
            } catch (Exception e) {
                    Log.e("Fling", "There was an error processing the Fling event:" + e.getMessage());
            }
            return false;
        }
    }
}