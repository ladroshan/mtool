package m.tool.stuff.drag_and_drop;

import m.tool.MTOOL;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

public class DeleteZone	extends ImageView	implements DropTarget
{
	public DeleteZone (Context context) {
	   super  (context);
	}
	public DeleteZone (Context context, AttributeSet attrs) {
		super (context, attrs);
	}
	public DeleteZone (Context context, AttributeSet attrs, int style) 
	{
		super (context, attrs, style);
	}
	
	private DragController mDragController;
	private boolean mEnabled = true;
	
	public DragController getDragController ()
	{
	   return mDragController;
	}
	
	public void setDragController (DragController newValue)
	{
	   mDragController = newValue;
	}

	public void onDrop(DragSource source, int x, int y, int xOffset, int yOffset,
	        DragView dragView, Object dragInfo, boolean success)
	{
	}

	public void onDragEnter(DragSource source, int x, int y, int xOffset, int yOffset,
	        DragView dragView, Object dragInfo)
	{
		MTOOL.farbe = 0x88ff0000;
	    if (isEnabled ()) setImageLevel (2);
	}
	
	public void onDragOver(DragSource source, int x, int y, int xOffset, int yOffset,
	        DragView dragView, Object dragInfo)
	{
	}
	
	public void onDragExit(DragSource source, int x, int y, int xOffset, int yOffset,
	        DragView dragView, Object dragInfo)
	{
		MTOOL.farbe = 0x00000000;
	    if (isEnabled ()) setImageLevel (1);
	}
	
	public boolean acceptDrop(DragSource source, int x, int y, int xOffset, int yOffset,
	        DragView dragView, Object dragInfo)
	{
	    return isEnabled ();
	}
	
	public Rect estimateDropLocation(DragSource source, int x, int y, int xOffset, int yOffset,
	            DragView dragView, Object dragInfo, Rect recycle)
	{
	    return null;
	}
	
	public boolean isEnabled ()
	{
	   return mEnabled && (getVisibility () == View.VISIBLE);
	}
	
	public void setup (DragController controller)
	{
	    mDragController = controller;
	
	    if (controller != null) {
	       controller.addDropTarget (this);
	    }
	}
}