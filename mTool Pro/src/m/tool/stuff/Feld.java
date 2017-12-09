package m.tool.stuff;

import m.tool.pro.MTOOL;
import m.tool.stuff.drag_and_drop.DragController;
import m.tool.stuff.drag_and_drop.DragSource;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

public class Feld	extends RelativeLayout	implements DragSource
{
    public boolean mEmpty = true;
    public int mCellNumber = 0;
    public int mPage = 0;
    public YouEntry inhalt;
    public int item = 0;
    public int h = 0;
    public int b = 0;
    public String option = "";
    public String note = "";

	public Feld (Context context) {
		super (context);
	}
	public Feld (Context context, AttributeSet attrs) {
		super (context, attrs);
	}
	public Feld (Context context, AttributeSet attrs, int style) {
		super (context, attrs, style);
	}
	
	public boolean allowDrag () {
	    return true;
	}
	
	public void setDragController (DragController dragger) {
	}
	
	public void onDropCompleted (View target, boolean success) {
	    if (success) {
	    	delete();
	    }
	    for(int i = 0; i < MTOOL.seiten; i++) {
    		MTOOL.page[i].update();
   		}
	}
	
	public boolean isEmpty() {
	    return mEmpty;
	}
	
	public void tellInhalt(YouEntry inhalt) {
		b = inhalt.get_width();
		h = inhalt.get_height();
		item = inhalt.get_item();
		option = inhalt.get_option();
		note = "";
		this.inhalt = new YouEntry(mPage, mCellNumber, b, h, item, option, note);
	}
	
	public void setInhalt(YouEntry inhalt) {
		if(inhalt == null)
			delete();
		else {
			tellInhalt(inhalt);
			if(this.inhalt != inhalt) {
				MTOOL.db.addEntry(new YouEntry(mPage, mCellNumber, inhalt._width, inhalt._height, inhalt._item, inhalt._option, ""));
				MTOOL.youdb = new YouEntry[MTOOL.db.getAllEntrys().size()];
				for(int i = 0; i < MTOOL.db.getAllEntrys().size(); i++)
					MTOOL.youdb[i] = MTOOL.db.getAllEntrys().get(i);
				MTOOL.db.close();
			}
		}
	}
	
	public void empty() {
		mEmpty = true;
		if(MTOOL.felder == 9 || MTOOL.felder == 12) {
	    	if(b >= 2) MTOOL.page[mPage-1].feld[mCellNumber+1].mEmpty = true;
			if(b >= 3) MTOOL.page[mPage-1].feld[mCellNumber+2].mEmpty = true;
			if(h >= 2) MTOOL.page[mPage-1].feld[mCellNumber+3].mEmpty = true;
			if(b >= 2 && h >= 2) MTOOL.page[mPage-1].feld[mCellNumber+4].mEmpty = true;
			if(b >= 3 && h >= 2) MTOOL.page[mPage-1].feld[mCellNumber+5].mEmpty = true;
			if(h >= 3) MTOOL.page[mPage-1].feld[mCellNumber+6].mEmpty = true;
			if(b >= 2 && h >= 3) MTOOL.page[mPage-1].feld[mCellNumber+7].mEmpty = true;
			if(b >= 3 && h >= 3) MTOOL.page[mPage-1].feld[mCellNumber+8].mEmpty = true;
			if(h == 4) MTOOL.page[mPage-1].feld[mCellNumber+9].mEmpty = true;
			if(b >= 2 && h == 4) MTOOL.page[mPage-1].feld[mCellNumber+10].mEmpty = true;
			if(b >= 3 && h == 4) MTOOL.page[mPage-1].feld[mCellNumber+11].mEmpty = true;
		} else if(MTOOL.felder == 16 || MTOOL.felder == 20) {
	    	if(b >= 2) MTOOL.page[mPage-1].feld[mCellNumber+1].mEmpty = true;
			if(b >= 3) MTOOL.page[mPage-1].feld[mCellNumber+2].mEmpty = true;
			if(b == 4) MTOOL.page[mPage-1].feld[mCellNumber+3].mEmpty = true;
			if(h >= 2) MTOOL.page[mPage-1].feld[mCellNumber+4].mEmpty = true;
			if(b >= 2 && h >= 2) MTOOL.page[mPage-1].feld[mCellNumber+5].mEmpty = true;
			if(b >= 3 && h >= 2) MTOOL.page[mPage-1].feld[mCellNumber+6].mEmpty = true;
			if(b == 4 && h >= 2) MTOOL.page[mPage-1].feld[mCellNumber+7].mEmpty = true;
			if(h >= 3) MTOOL.page[mPage-1].feld[mCellNumber+8].mEmpty = true;
			if(b >= 2 && h >= 3) MTOOL.page[mPage-1].feld[mCellNumber+9].mEmpty = true;
			if(b >= 3 && h >= 3) MTOOL.page[mPage-1].feld[mCellNumber+10].mEmpty = true;
			if(b == 4 && h >= 3) MTOOL.page[mPage-1].feld[mCellNumber+11].mEmpty = true;
			if(h == 4) MTOOL.page[mPage-1].feld[mCellNumber+12].mEmpty = true;
			if(b >= 2 && h == 4) MTOOL.page[mPage-1].feld[mCellNumber+13].mEmpty = true;
			if(b >= 3 && h == 4) MTOOL.page[mPage-1].feld[mCellNumber+14].mEmpty = true;
			if(b == 4 && h == 4) MTOOL.page[mPage-1].feld[mCellNumber+15].mEmpty = true;
		} else if(MTOOL.felder == 25) {
	    	if(b >= 2) MTOOL.page[mPage-1].feld[mCellNumber+1].mEmpty = true;
			if(b >= 3) MTOOL.page[mPage-1].feld[mCellNumber+2].mEmpty = true;
			if(b == 4) MTOOL.page[mPage-1].feld[mCellNumber+3].mEmpty = true;
			if(h >= 2) MTOOL.page[mPage-1].feld[mCellNumber+5].mEmpty = true;
			if(b >= 2 && h >= 2) MTOOL.page[mPage-1].feld[mCellNumber+6].mEmpty = true;
			if(b >= 3 && h >= 2) MTOOL.page[mPage-1].feld[mCellNumber+7].mEmpty = true;
			if(b == 4 && h >= 2) MTOOL.page[mPage-1].feld[mCellNumber+8].mEmpty = true;
			if(h >= 3) MTOOL.page[mPage-1].feld[mCellNumber+10].mEmpty = true;
			if(b >= 2 && h >= 3) MTOOL.page[mPage-1].feld[mCellNumber+11].mEmpty = true;
			if(b >= 3 && h >= 3) MTOOL.page[mPage-1].feld[mCellNumber+12].mEmpty = true;
			if(b == 4 && h >= 3) MTOOL.page[mPage-1].feld[mCellNumber+13].mEmpty = true;
			if(h == 4) MTOOL.page[mPage-1].feld[mCellNumber+15].mEmpty = true;
			if(b >= 2 && h == 4) MTOOL.page[mPage-1].feld[mCellNumber+16].mEmpty = true;
			if(b >= 3 && h == 4) MTOOL.page[mPage-1].feld[mCellNumber+17].mEmpty = true;
			if(b == 4 && h == 4) MTOOL.page[mPage-1].feld[mCellNumber+18].mEmpty = true;
		}
	}
	
	public void delete() {
		empty();
		for(int i = 0; i < MTOOL.youdb.length; i++) {
			if(MTOOL.youdb[i].get_feld() == mCellNumber && MTOOL.youdb[i].get_page() == mPage)
				MTOOL.youdb[i].setID(0);
		}
		MTOOL.db.command("DELETE FROM YOU WHERE page = " + Integer.toString(mPage) + " AND feld = " + Integer.toString(mCellNumber));
		MTOOL.youdb = new YouEntry[MTOOL.db.getAllEntrys().size()];
		for(int i = 0; i < MTOOL.db.getAllEntrys().size(); i++)
			MTOOL.youdb[i] = MTOOL.db.getAllEntrys().get(i);
		MTOOL.db.close();
		this.inhalt = null;
	}

	public boolean performLongClick () {
    	empty();
    	return super.performLongClick();
	}
}