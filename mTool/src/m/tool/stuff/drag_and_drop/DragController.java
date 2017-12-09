package m.tool.stuff.drag_and_drop;

import java.util.ArrayList;

import m.tool.MTOOL;
import m.tool.R;
import m.tool.stuff.Feld;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

public class DragController {
    public static int DRAG_ACTION_MOVE = 0;
    public static int DRAG_ACTION_COPY = 1;

    private static final boolean PROFILE_DRAWING_DURING_DRAG = false;

//    private static final int SCROLL_DELAY = 600;

    private static final int SCROLL_OUTSIDE_ZONE = 0;
    private static final int SCROLL_WAITING_IN_ZONE = 1;

    private Context mContext;

    private Rect mRectTemp = new Rect();
    private final int[] mCoordinatesTemp = new int[2];

    private boolean mDragging;

    private float mMotionDownX;
    private float mMotionDownY;

    private DisplayMetrics mDisplayMetrics = new DisplayMetrics();
    
    private View mOriginator;
    
    private float mTouchOffsetX;
    private float mTouchOffsetY;
    
    private int mScrollZone;
    
    private int mScrollState = SCROLL_OUTSIDE_ZONE;

    private DragSource mDragSource;
    
    private Object mDragInfo;
    
    private DragView mDragView;
    
    private ArrayList<DropTarget> mDropTargets = new ArrayList<DropTarget>();
    
    private DragListener mListener;
    
    private IBinder mWindowToken;

    private View mMoveTarget;

    private DropTarget mLastDropTarget;

    private InputMethodManager mInputMethodManager;

    interface DragListener {
        
        public void onDragStart(DragSource source, Object info, int dragAction);
        
        public void onDragEnd();
    }
    
    public DragController(Context context) {
        mContext = context;
        mScrollZone = context.getResources().getDimensionPixelSize(R.dimen.scroll_zone);
    }

    public void startDrag(View v, DragSource source, Object dragInfo, int dragAction) {
        boolean doDrag = source.allowDrag ();
        if (!doDrag) return;

        mOriginator = v;

        Bitmap b = getViewBitmap(v);

        if (b == null) {
            return;
        }

        int[] loc = mCoordinatesTemp;
        v.getLocationOnScreen(loc);
        int screenX = loc[0];
        int screenY = loc[1];

        startDrag(b, screenX, screenY, 0, 0, b.getWidth(), b.getHeight(),
                source, dragInfo, dragAction);

        b.recycle();

        if (dragAction == DRAG_ACTION_MOVE) {
            v.setVisibility(View.GONE);
        }
    }

    public void startDrag(Bitmap b, int screenX, int screenY,
            int textureLeft, int textureTop, int textureWidth, int textureHeight,
            DragSource source, Object dragInfo, int dragAction) {
        if (PROFILE_DRAWING_DURING_DRAG) {
            android.os.Debug.startMethodTracing("Launcher");
        }

        if (mInputMethodManager == null) {
            mInputMethodManager = (InputMethodManager)
                    mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        }
        mInputMethodManager.hideSoftInputFromWindow(mWindowToken, 0);

        if (mListener != null) {
            mListener.onDragStart(source, dragInfo, dragAction);
        }

        int registrationX = ((int)mMotionDownX) - screenX;
        int registrationY = ((int)mMotionDownY) - screenY;

        mTouchOffsetX = mMotionDownX - screenX;
        mTouchOffsetY = mMotionDownY - screenY;

        mDragging = true;
        mDragSource = source;
        mDragInfo = dragInfo;

        DragView dragView = mDragView = new DragView(mContext, b, registrationX, registrationY,
                textureLeft, textureTop, textureWidth, textureHeight);
        dragView.show(mWindowToken, (int)mMotionDownX, (int)mMotionDownY);
    }

    private Bitmap getViewBitmap(View v) {
        v.clearFocus();
        v.setPressed(false);

        boolean willNotCache = v.willNotCacheDrawing();
        v.setWillNotCacheDrawing(false);

        int color = v.getDrawingCacheBackgroundColor();
        v.setDrawingCacheBackgroundColor(0);

        if (color != 0) {
            v.destroyDrawingCache();
        }
        v.buildDrawingCache();
        Bitmap cacheBitmap = v.getDrawingCache();
        if (cacheBitmap == null) {
            return null;
        }

        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);

        v.destroyDrawingCache();
        v.setWillNotCacheDrawing(willNotCache);
        v.setDrawingCacheBackgroundColor(color);

        return bitmap;
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        return mDragging;
    }

    public void cancelDrag() {
        endDrag();
    }

    private void endDrag() {
        if (mDragging) {
            mDragging = false;
            if (mOriginator != null) {
                mOriginator.setVisibility(View.VISIBLE);
            }
            if (mListener != null) {
                mListener.onDragEnd();
            }
            if (mDragView != null) {
                mDragView.remove();
                mDragView = null;
            }
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();

        if (action == MotionEvent.ACTION_DOWN) {
            recordScreenSize();
        }

        final int screenX = clamp((int)ev.getRawX(), 0, mDisplayMetrics.widthPixels);
        final int screenY = clamp((int)ev.getRawY(), 0, mDisplayMetrics.heightPixels);

        switch (action) {
            case MotionEvent.ACTION_MOVE:
                break;

            case MotionEvent.ACTION_DOWN:
                mMotionDownX = screenX;
                mMotionDownY = screenY;
                mLastDropTarget = null;
                break;

            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (mDragging) {
                    drop(screenX, screenY);
                }
                endDrag();
                break;
        }

        return mDragging;
    }

    void setMoveTarget(View view) {
        mMoveTarget = view;
    }    

    public boolean dispatchUnhandledMove(View focused, int direction) {
        return mMoveTarget != null && mMoveTarget.dispatchUnhandledMove(focused, direction);
    }

    public boolean onTouchEvent(MotionEvent ev) {
        if (!mDragging) {
            return false;
        }

        final int action = ev.getAction();
        final int screenX = clamp((int)ev.getRawX(), 0, mDisplayMetrics.widthPixels);
        final int screenY = clamp((int)ev.getRawY(), 0, mDisplayMetrics.heightPixels);

        switch (action) {
        case MotionEvent.ACTION_DOWN:
            mMotionDownX = screenX;
            mMotionDownY = screenY;
            break;
        case MotionEvent.ACTION_MOVE:
            mDragView.move((int)ev.getRawX(), (int)ev.getRawY());
            mDragView.refresh();
            final int[] coordinates = mCoordinatesTemp;
            DropTarget dropTarget = findDropTarget(screenX, screenY, coordinates);
            if (dropTarget != null) {
                if (mLastDropTarget == dropTarget) {
                    dropTarget.onDragOver(mDragSource, coordinates[0], coordinates[1],
                        (int) mTouchOffsetX, (int) mTouchOffsetY, mDragView, mDragInfo);
                } else {
                    if (mLastDropTarget != null) {
                        mLastDropTarget.onDragExit(mDragSource, coordinates[0], coordinates[1],
                            (int) mTouchOffsetX, (int) mTouchOffsetY, mDragView, mDragInfo);
                    }
                    dropTarget.onDragEnter(mDragSource, coordinates[0], coordinates[1],
                        (int) mTouchOffsetX, (int) mTouchOffsetY, mDragView, mDragInfo);
                }
            } else {
                if (mLastDropTarget != null) {
                    mLastDropTarget.onDragExit(mDragSource, coordinates[0], coordinates[1],
                        (int) mTouchOffsetX, (int) mTouchOffsetY, mDragView, mDragInfo);
                }
            }
            mLastDropTarget = dropTarget;

            @SuppressWarnings("deprecation")
			int w = ((WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
            
            if (screenX < mScrollZone) {
                if (mScrollState == SCROLL_OUTSIDE_ZONE) {
                    mScrollState = SCROLL_WAITING_IN_ZONE;
                    MTOOL.scrollLeft();
                    DragLayer.update_page();
                }
            } else if (screenX > w - mScrollZone) {
                if (mScrollState == SCROLL_OUTSIDE_ZONE) {
                    mScrollState = SCROLL_WAITING_IN_ZONE;
                    MTOOL.scrollRight();
                    DragLayer.update_page();
                }
            } else {
                if (mScrollState == SCROLL_WAITING_IN_ZONE) {
                    mScrollState = SCROLL_OUTSIDE_ZONE;
                }
            }
            
            break;
        case MotionEvent.ACTION_UP:
            if (mDragging) {
                drop(screenX, screenY);
            }
            endDrag();

            break;
        case MotionEvent.ACTION_CANCEL:
            cancelDrag();
        }
        return true;
    }

    private boolean drop(float x, float y) {

        final int[] coordinates = mCoordinatesTemp;
        DropTarget dropTarget = findDropTarget((int) x, (int) y, coordinates);

        if (dropTarget != null) {
            dropTarget.onDragExit(mDragSource, coordinates[0], coordinates[1],
                    (int) mTouchOffsetX, (int) mTouchOffsetY, mDragView, mDragInfo);
            if (dropTarget.acceptDrop(mDragSource, coordinates[0], coordinates[1],
                    (int) mTouchOffsetX, (int) mTouchOffsetY, mDragView, mDragInfo)) {
            	if(dropTarget.equals(MTOOL.mDeleteZone))
            		dropTarget.onDrop(mDragSource, coordinates[0], coordinates[1],
            				(int) mTouchOffsetX, (int) mTouchOffsetY, mDragView, mDragInfo, true);
            	else if(((Feld_Target)dropTarget).mCellNumber == ((Feld)mDragSource).mCellNumber && ((Feld_Target)dropTarget).mPage == ((Feld)mDragSource).mPage)
            		dropTarget.onDrop(mDragSource, coordinates[0], coordinates[1],
            				(int) mTouchOffsetX, (int) mTouchOffsetY, mDragView, mDragInfo, false);
            	else
            		dropTarget.onDrop(mDragSource, coordinates[0], coordinates[1],
            				(int) mTouchOffsetX, (int) mTouchOffsetY, mDragView, mDragInfo, true);
                if(dropTarget.equals(MTOOL.mDeleteZone))
                	mDragSource.onDropCompleted((View) dropTarget, true);
                else if(((Feld_Target)dropTarget).mCellNumber == ((Feld)mDragSource).mCellNumber && ((Feld_Target)dropTarget).mPage == ((Feld)mDragSource).mPage)
                	mDragSource.onDropCompleted((View) dropTarget, false);
                else
                	mDragSource.onDropCompleted((View) dropTarget, true);
                return true;
            } else {
                mDragSource.onDropCompleted((View) dropTarget, false);
                return true;
            }
        }
        return false;
    }

    private DropTarget findDropTarget(int x, int y, int[] dropCoordinates) {
        final Rect r = mRectTemp;

        final ArrayList<DropTarget> dropTargets = mDropTargets;
        final int count = dropTargets.size();
        for (int i=count-1; i>=0; i--) {
            final DropTarget target = dropTargets.get(i);
            target.getHitRect(r);
            target.getLocationOnScreen(dropCoordinates);
            r.offset(dropCoordinates[0] - target.getLeft(), dropCoordinates[1] - target.getTop());
            if (r.contains(x, y)) {
                dropCoordinates[0] = x - dropCoordinates[0];
                dropCoordinates[1] = y - dropCoordinates[1];
                return target;
            }
        }
        return null;
    }

    private void recordScreenSize() {
        ((WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getMetrics(mDisplayMetrics);
    }

    private static int clamp(int val, int min, int max) {
        if (val < min) {
            return min;
        } else if (val >= max) {
            return max - 1;
        } else {
            return val;
        }
    }

    public void setWindowToken(IBinder token) {
        mWindowToken = token;
    }

    public void setDragListener(DragListener l) {
        mListener = l;
    }

    public void removeDragListener(DragListener l) {
        mListener = null;
    }

    public void addDropTarget(DropTarget target) {
        mDropTargets.add(target);
    }

    public void removeDropTarget(DropTarget target) {
        mDropTargets.remove(target);
    }

    public void removeAllDropTargets () {
        mDropTargets = new ArrayList<DropTarget> ();
    }

}