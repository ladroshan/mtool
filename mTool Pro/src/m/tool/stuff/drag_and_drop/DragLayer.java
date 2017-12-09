package m.tool.stuff.drag_and_drop;

import m.tool.pro.MTOOL;
import m.tool.stuff.Feld;
import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

public class DragLayer extends FrameLayout	implements DragController.DragListener
{
    static DragController mDragController;
    static DragSource source;
    static int[] variantenx9 = {
    	9, 6, 3, 0, 6, 4, 2, 0, 3, 2, 1, 0, 0, 0, 0, 0,
    	0, 1, 2, 3, 4, 5, 6, 7, 8,
    	0, 1, 3, 4, 6, 7, 0, 0, 0,
    	0, 3, 6, 0, 0, 0, 0, 0, 0,
    	0, 0, 0, 0, 0, 0, 0, 0, 0,
    	0, 1, 2, 3, 4, 5, 0, 0, 0,
    	0, 1, 3, 4, 0, 0, 0, 0, 0,
    	0, 3, 0, 0, 0, 0, 0, 0, 0,
    	0, 0, 0, 0, 0, 0, 0, 0, 0,
    	0, 1, 2, 0, 0, 0, 0, 0, 0,
    	0, 1, 0, 0, 0, 0, 0, 0, 0,
    	0, 0, 0, 0, 0, 0, 0, 0, 0,
    	0, 0, 0, 0, 0, 0, 0, 0, 0,
    	0, 0, 0, 0, 0, 0, 0, 0, 0,
    	0, 0, 0, 0, 0, 0, 0, 0, 0,
    	0, 0, 0, 0, 0, 0, 0, 0, 0,
    	0, 0, 0, 0, 0, 0, 0, 0, 0};
    static int[] variantenx12 = {
    	12, 8, 4, 0, 9, 6, 3, 0, 6, 4, 2, 0, 3, 2, 1, 0,
    	0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11,
    	0, 1, 3, 4, 6, 7, 9, 10, 0, 0, 0, 0,
    	0, 3, 6, 9, 0, 0, 0, 0, 0, 0, 0, 0,
    	0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    	0, 1, 2, 3, 4, 5, 6, 7, 8, 0, 0, 0,
    	0, 1, 3, 4, 6, 7, 0, 0, 0, 0, 0, 0,
    	0, 3, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    	0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    	0, 1, 2, 3, 4, 5, 0, 0, 0, 0, 0, 0,
    	0, 1, 3, 4, 0, 0, 0, 0, 0, 0, 0, 0,
    	0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    	0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    	0, 1, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    	0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    	0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    	0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    static int[] variantenx16 = {
    	16, 12, 8, 4, 12, 9, 6, 3, 8, 6, 4, 2, 4, 3, 2, 1,
    	0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,
    	0, 1, 2, 4, 5, 6, 8, 9, 10, 12, 13, 14, 0, 0, 0, 0,
    	0, 1, 4, 5, 8, 9, 12, 13, 0, 0, 0, 0, 0, 0, 0, 0,
    	0, 4, 8, 12, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    	0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 0, 0, 0, 0,
    	0, 1, 2, 4, 5, 6, 8, 9, 10, 0, 0, 0, 0, 0, 0, 0,
    	0, 1, 4, 5, 8, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    	0, 4, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    	0, 1, 2, 3, 4, 5, 6, 7, 0, 0, 0, 0, 0, 0, 0, 0,
    	0, 1, 2, 4, 5, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    	0, 1, 4, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    	0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    	0, 1, 2, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    	0, 1, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    	0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    	0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    static int[] variantenx20 = {
    	20, 15, 10, 5, 16, 12, 8, 4, 12, 9, 6, 3, 8, 6, 4, 2,
    	0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19,
    	0, 1, 2, 4, 5, 6, 8, 9, 10, 12, 13, 14, 16, 17, 18, 0, 0, 0, 0, 0,
    	0, 1, 4, 5, 8, 9, 12, 13, 16, 17, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    	0, 4, 8, 12, 16, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    	0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0, 0, 0, 0,
    	0, 1, 2, 4, 5, 6, 8, 9, 10, 12, 13, 14, 0, 0, 0, 0, 0, 0, 0, 0,
    	0, 1, 4, 5, 8, 9, 12, 13, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    	0, 4, 8, 12, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    	0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 0, 0, 0, 0, 0, 0, 0, 0,
    	0, 1, 2, 4, 5, 6, 8, 9, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    	0, 1, 4, 5, 8, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    	0, 4, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    	0, 1, 2, 3, 4, 5, 6, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    	0, 1, 2, 4, 5, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    	0, 1, 4, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    	0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    static int[] variantenx25 = {
    	25, 20, 15, 10, 20, 16, 12, 8, 15, 12, 9, 6, 10, 8, 6, 4,
    	0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24,
    	0, 1, 2, 3, 5, 6, 7, 8, 10, 11, 12, 13, 15, 16, 17, 18, 20, 21, 22, 23, 0, 0, 0, 0, 0,
    	0, 1, 2, 5, 6, 7, 10, 11, 12, 15, 16, 17, 20, 21, 22, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    	0, 1, 5, 6, 10, 11, 15, 16, 20, 21, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    	0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 0, 0, 0, 0, 0,
    	0, 1, 2, 3, 5, 6, 7, 8, 10, 11, 12, 13, 15, 16, 17, 18, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    	0, 1, 2, 5, 6, 7, 10, 11, 12, 15, 16, 17, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    	0, 1, 5, 6, 10, 11, 15, 16, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    	0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    	0, 1, 2, 3, 5, 6, 7, 8, 10, 11, 12, 13, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    	0, 1, 2, 5, 6, 7, 10, 11, 12, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    	0, 1, 5, 6, 10, 11, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    	0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    	0, 1, 2, 3, 5, 6, 7, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    	0, 1, 2, 5, 6, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    	0, 1, 5, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    public DragLayer (Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setDragController(DragController controller) {
        mDragController = controller;
    }
    
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
    	if(mDragController != null)
    		return mDragController.dispatchKeyEvent(event) || super.dispatchKeyEvent(event);
    	else
    		return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
    	if(mDragController != null)
    		return mDragController.onInterceptTouchEvent(ev);
    	else
    		return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
    	if(mDragController != null && ev != null)
    		return mDragController.onTouchEvent(ev);
    	return false;
    }

    @Override
    public boolean dispatchUnhandledMove(View focused, int direction) {
        return mDragController.dispatchUnhandledMove(focused, direction);
    }

	public void onDragStart(DragSource source, Object info, int dragAction) 
	{
		DragLayer.source = source;
		update_page();
	}
	
	public static void update_page()
	{
		mDragController.removeAllDropTargets();
		int[] varianten = {0};
		int variante = 0;
		if(((Feld)source).b == 1 && ((Feld)source).h == 1) variante = 0;
		if(((Feld)source).b == 2 && ((Feld)source).h == 1) variante = 1;
		if(((Feld)source).b == 3 && ((Feld)source).h == 1) variante = 2;
		if(((Feld)source).b == 4 && ((Feld)source).h == 1) variante = 3;
		if(((Feld)source).b == 1 && ((Feld)source).h == 2) variante = 4;
		if(((Feld)source).b == 2 && ((Feld)source).h == 2) variante = 5;
		if(((Feld)source).b == 3 && ((Feld)source).h == 2) variante = 6;
		if(((Feld)source).b == 4 && ((Feld)source).h == 2) variante = 7;
		if(((Feld)source).b == 1 && ((Feld)source).h == 3) variante = 8;
		if(((Feld)source).b == 2 && ((Feld)source).h == 3) variante = 9;
		if(((Feld)source).b == 3 && ((Feld)source).h == 3) variante = 10;
		if(((Feld)source).b == 4 && ((Feld)source).h == 3) variante = 11;
		if(((Feld)source).b == 1 && ((Feld)source).h == 4) variante = 12;
		if(((Feld)source).b == 2 && ((Feld)source).h == 4) variante = 13;
		if(((Feld)source).b == 3 && ((Feld)source).h == 4) variante = 14;
		if(((Feld)source).b == 4 && ((Feld)source).h == 4) variante = 15;
		if(MTOOL.felder == 9) varianten = variantenx9;
		if(MTOOL.felder == 12) varianten = variantenx12;
		if(MTOOL.felder == 16) varianten = variantenx16;
		if(MTOOL.felder == 20) varianten = variantenx20;
		if(MTOOL.felder == 25) varianten = variantenx25;
		for(int i = 0; i < varianten[variante]; i++) {
			int nummer = (varianten[variante*MTOOL.felder+16+i]);
	    	for(int o = 0; o < MTOOL.seiten; o++) {
	    		mDragController.addDropTarget(MTOOL.page[o].feld_target[nummer]);
	    		MTOOL.page[o].feld_target[nummer].setVisibility(View.VISIBLE);
	    		MTOOL.page[o].feld_target[nummer].getLayoutParams().width = (MTOOL.w*((Feld)source).b);
	    		MTOOL.page[o].feld_target[nummer].getLayoutParams().height = (MTOOL.h*((Feld)source).h);
	    	}
		}
		mDragController.addDropTarget(MTOOL.mDeleteZone);
	}
	
	public void onDragEnd() 
	{
	    mDragController.removeAllDropTargets();
	    for(int i = 0; i < MTOOL.seiten; i++)
	    	for(int o = 0; o < MTOOL.felder; o++)
	    		MTOOL.page[i].feld_target[o].setVisibility(View.GONE);
	    MTOOL.mDeleteZone.setVisibility(View.GONE);
	    MTOOL.badd.setVisibility(View.VISIBLE);
	}
}