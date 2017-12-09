package m.tool.stuff.drag_and_drop;

import android.view.View;

public interface DragSource 
{
    boolean allowDrag ();
    
    void setDragController(DragController dragger);
    
    void onDropCompleted (View target, boolean success);
}
