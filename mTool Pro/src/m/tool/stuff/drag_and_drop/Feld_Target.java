package m.tool.stuff.drag_and_drop;

import m.tool.pro.MTOOL;
import m.tool.stuff.Feld;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class Feld_Target	extends RelativeLayout	implements DropTarget
{
    public int mCellNumber = 0;
    public int mPage = 0;

	public Feld_Target (Context context) {
		super (context);
	}
	public Feld_Target (Context context, AttributeSet attrs) {
		super (context, attrs);
	}
	public Feld_Target (Context context, AttributeSet attrs, int style) {
		super (context, attrs, style);
	}
	
	public void setDragController (DragController dragger) {
	}
	
	public void onDrop(DragSource source, int x, int y, int xOffset, int yOffset, DragView dragView, Object dragInfo, boolean success)
	{
		if(success){
			MTOOL.page[mPage-1].feld[mCellNumber].setInhalt(((Feld)source).inhalt);
			for(int i = 0; i < MTOOL.seiten; i++) {
				MTOOL.page[i].update();
			}
		}
	}
	
	public void onDragEnter(DragSource source, int x, int y, int xOffset, int yOffset,
	        DragView dragView, Object dragInfo)
	{
		if(isEmpty(source) && MTOOL.prefs.getBoolean("dad_hint_pref", false))
			this.setBackgroundColor(0xaa045482);
	    MTOOL.farbe = (isEmpty(source) ? 0x00000000 : 0x88ffff00);
	}
	
	public void onDragOver(DragSource source, int x, int y, int xOffset, int yOffset,
	        DragView dragView, Object dragInfo)
	{
	}
	
	public void onDragExit(DragSource source, int x, int y, int xOffset, int yOffset,
	        DragView dragView, Object dragInfo)
	{
	    MTOOL.farbe = 0x00000000;
	    this.setBackgroundColor(0x00000000);
	}
	
	public boolean acceptDrop(DragSource source, int x, int y, int xOffset, int yOffset,
	        DragView dragView, Object dragInfo)
	{
		return isEmpty(source);
	}
	
	public Rect estimateDropLocation(DragSource source, int x, int y, int xOffset, int yOffset, DragView dragView, Object dragInfo, Rect recycle) {
	    return null;
	}
	
	public boolean isEmpty (DragSource source) {
		boolean f0 = true, f1 = true, f2 = true, f3 = true, f4 = true, f5 = true, f6 = true, f7 = true,
				f8 = true, f9 = true, f10 = true, f11 = true, f12 = true, f13 = true, f14 = true, f15 = true;
		f0 = MTOOL.page[mPage-1].feld[mCellNumber].mEmpty;
		if(MTOOL.felder == 9) {
			f3 = false;
			f7 = false;
			f11 = false;
			f12 = false;
			f13 = false;
			f14 = false;
			f15 = false;
			if(mCellNumber != 2 && mCellNumber != 5 && mCellNumber != 8) {
				f1 = MTOOL.page[mPage-1].feld[mCellNumber+1].mEmpty;
				if(mCellNumber != 1 && mCellNumber != 4 && mCellNumber != 7)
					f2 = MTOOL.page[mPage-1].feld[mCellNumber+2].mEmpty;
				else f2 = false;
			} else f1 = false;
			if(mCellNumber < 6) {
				f4 = MTOOL.page[mPage-1].feld[mCellNumber+3].mEmpty;
				if(mCellNumber != 2 && mCellNumber != 5) {
					f5 = MTOOL.page[mPage-1].feld[mCellNumber+4].mEmpty;
					if(mCellNumber != 1 && mCellNumber != 4)
						f6 = MTOOL.page[mPage-1].feld[mCellNumber+5].mEmpty;
					else f6 = false;
				} else f5 = false;
			} else f4 = false;
			if(mCellNumber < 3) { 
				f8 = MTOOL.page[mPage-1].feld[mCellNumber+6].mEmpty;
				if(mCellNumber != 2) { 
					f9 = MTOOL.page[mPage-1].feld[mCellNumber+7].mEmpty;
					if(mCellNumber != 1)
						f10 = MTOOL.page[mPage-1].feld[mCellNumber+8].mEmpty;
					else f10 = false;
				} else f9 = false;
			} else f8 = false;
		} else if(MTOOL.felder == 12) {
			f3 = false;
			f7 = false;
			f11 = false;
			f15 = false;
			if(mCellNumber != 2 && mCellNumber != 5 && mCellNumber != 8 && mCellNumber != 11) {
				f1 = MTOOL.page[mPage-1].feld[mCellNumber+1].mEmpty;
				if(mCellNumber != 1 && mCellNumber != 4 && mCellNumber != 7 && mCellNumber != 10)
					f2 = MTOOL.page[mPage-1].feld[mCellNumber+2].mEmpty;
				else f2 = false;
			} else f1 = false;
			if(mCellNumber < 9) {
				f4 = MTOOL.page[mPage-1].feld[mCellNumber+3].mEmpty;
				if(mCellNumber != 2 && mCellNumber != 5 && mCellNumber != 8) {
					f5 = MTOOL.page[mPage-1].feld[mCellNumber+4].mEmpty;
					if(mCellNumber != 1 && mCellNumber != 4 && mCellNumber != 7)
						f6 = MTOOL.page[mPage-1].feld[mCellNumber+5].mEmpty;
					else f6 = false;
				} else f5 = false;
			} else f4 = false;
			if(mCellNumber < 6) { 
				f8 = MTOOL.page[mPage-1].feld[mCellNumber+6].mEmpty;
				if(mCellNumber != 2 && mCellNumber != 5) { 
					f9 = MTOOL.page[mPage-1].feld[mCellNumber+7].mEmpty;
					if(mCellNumber != 1 && mCellNumber != 4)
						f10 = MTOOL.page[mPage-1].feld[mCellNumber+8].mEmpty;
					else f10 = false;
				} else f9 = false;
			} else f8 = false;
			if(mCellNumber < 3) {
				f12 = MTOOL.page[mPage-1].feld[mCellNumber+9].mEmpty;
				if(mCellNumber != 2) {
					f13 = MTOOL.page[mPage-1].feld[mCellNumber+10].mEmpty;
					if(mCellNumber != 1)
						f14 = MTOOL.page[mPage-1].feld[mCellNumber+11].mEmpty;
					else f14 = false;
				} else f13 = false;
			} else f12 = false;
		} else if(MTOOL.felder == 16) {
			if(mCellNumber != 3 && mCellNumber != 7 && mCellNumber != 11 && mCellNumber != 15) {
				f1 = MTOOL.page[mPage-1].feld[mCellNumber+1].mEmpty;
				if(mCellNumber != 2 && mCellNumber != 6 && mCellNumber != 10 && mCellNumber != 14) {
					f2 = MTOOL.page[mPage-1].feld[mCellNumber+2].mEmpty;
					if(mCellNumber != 1 && mCellNumber != 5 && mCellNumber != 9 && mCellNumber != 13)
						f3 = MTOOL.page[mPage-1].feld[mCellNumber+3].mEmpty;
					else f3 = false;
				} else f2 = false;
			} else f1 = false;
			if(mCellNumber < 12) {
				f4 = MTOOL.page[mPage-1].feld[mCellNumber+4].mEmpty;
				if(mCellNumber != 3 && mCellNumber != 7 && mCellNumber != 11) {
					f5 = MTOOL.page[mPage-1].feld[mCellNumber+5].mEmpty;
					if(mCellNumber != 2 && mCellNumber != 6 && mCellNumber != 10) {
						f6 = MTOOL.page[mPage-1].feld[mCellNumber+6].mEmpty;
						if(mCellNumber != 1 && mCellNumber != 5 && mCellNumber != 9)
							f7 = MTOOL.page[mPage-1].feld[mCellNumber+7].mEmpty;
						else f7 = false;
					} else f6 = false;
				} else f5 = false;
			} else f4 = false;
			if(mCellNumber < 8) { 
				f8 = MTOOL.page[mPage-1].feld[mCellNumber+8].mEmpty;
				if(mCellNumber != 3 && mCellNumber != 7) { 
					f9 = MTOOL.page[mPage-1].feld[mCellNumber+9].mEmpty;
					if(mCellNumber != 2 && mCellNumber != 6) {
						f10 = MTOOL.page[mPage-1].feld[mCellNumber+10].mEmpty;
						if(mCellNumber != 1 && mCellNumber != 5) 
							f11 = MTOOL.page[mPage-1].feld[mCellNumber+11].mEmpty;
						else f11 = false;
					} else f10 = false;
				} else f9 = false;
			} else f8 = false;
			if(mCellNumber < 4) {
				f12 = MTOOL.page[mPage-1].feld[mCellNumber+12].mEmpty;
				if(mCellNumber != 3) {
					f13 = MTOOL.page[mPage-1].feld[mCellNumber+13].mEmpty;
					if(mCellNumber != 2) { 
						f14 = MTOOL.page[mPage-1].feld[mCellNumber+14].mEmpty;
						if(mCellNumber != 1) 
							f15 = MTOOL.page[mPage-1].feld[mCellNumber+15].mEmpty;
						else f15 = false;
					} else f14 = false;
				} else f13 = false;
			} else f12 = false;
		} else if(MTOOL.felder == 20) {
			if(mCellNumber != 3 && mCellNumber != 7 && mCellNumber != 11 && mCellNumber != 15 && mCellNumber != 19) {
				f1 = MTOOL.page[mPage-1].feld[mCellNumber+1].mEmpty;
				if(mCellNumber != 2 && mCellNumber != 6 && mCellNumber != 10 && mCellNumber != 14 && mCellNumber != 18) {
					f2 = MTOOL.page[mPage-1].feld[mCellNumber+2].mEmpty;
					if(mCellNumber != 1 && mCellNumber != 5 && mCellNumber != 9 && mCellNumber != 13 && mCellNumber != 17)
						f3 = MTOOL.page[mPage-1].feld[mCellNumber+3].mEmpty;
					else f3 = false;
				} else f2 = false;
			} else f1 = false;
			if(mCellNumber < 16) {
				f4 = MTOOL.page[mPage-1].feld[mCellNumber+4].mEmpty;
				if(mCellNumber != 3 && mCellNumber != 7 && mCellNumber != 11 && mCellNumber != 15) {
					f5 = MTOOL.page[mPage-1].feld[mCellNumber+5].mEmpty;
					if(mCellNumber != 2 && mCellNumber != 6 && mCellNumber != 10 && mCellNumber != 14) {
						f6 = MTOOL.page[mPage-1].feld[mCellNumber+6].mEmpty;
						if(mCellNumber != 1 && mCellNumber != 5 && mCellNumber != 9 && mCellNumber != 13)
							f7 = MTOOL.page[mPage-1].feld[mCellNumber+7].mEmpty;
						else f7 = false;
					} else f6 = false;
				} else f5 = false;
			} else f4 = false;
			if(mCellNumber < 12) { 
				f8 = MTOOL.page[mPage-1].feld[mCellNumber+8].mEmpty;
				if(mCellNumber != 3 && mCellNumber != 7 && mCellNumber != 11) { 
					f9 = MTOOL.page[mPage-1].feld[mCellNumber+9].mEmpty;
					if(mCellNumber != 2 && mCellNumber != 6 && mCellNumber != 10) {
						f10 = MTOOL.page[mPage-1].feld[mCellNumber+10].mEmpty;
						if(mCellNumber != 1 && mCellNumber != 5 && mCellNumber != 19) 
							f11 = MTOOL.page[mPage-1].feld[mCellNumber+11].mEmpty;
						else f11 = false;
					} else f10 = false;
				} else f9 = false;
			} else f8 = false;
			if(mCellNumber < 8) {
				f12 = MTOOL.page[mPage-1].feld[mCellNumber+12].mEmpty;
				if(mCellNumber != 3 && mCellNumber != 7) {
					f13 = MTOOL.page[mPage-1].feld[mCellNumber+13].mEmpty;
					if(mCellNumber != 2 && mCellNumber != 6) { 
						f14 = MTOOL.page[mPage-1].feld[mCellNumber+14].mEmpty;
						if(mCellNumber != 1 && mCellNumber != 5) 
							f15 = MTOOL.page[mPage-1].feld[mCellNumber+15].mEmpty;
						else f15 = false;
					} else f14 = false;
				} else f13 = false;
			} else f12 = false;
		} else if(MTOOL.felder == 25) {
			if(mCellNumber != 4 && mCellNumber != 9 && mCellNumber != 14 && mCellNumber != 19 && mCellNumber != 24) {
				f1 = MTOOL.page[mPage-1].feld[mCellNumber+1].mEmpty;
				if(mCellNumber != 3 && mCellNumber != 8 && mCellNumber != 13 && mCellNumber != 18 && mCellNumber != 23) {
					f2 = MTOOL.page[mPage-1].feld[mCellNumber+2].mEmpty;
					if(mCellNumber != 2 && mCellNumber != 7 && mCellNumber != 12 && mCellNumber != 17 && mCellNumber != 22)
						f3 = MTOOL.page[mPage-1].feld[mCellNumber+3].mEmpty;
					else f3 = false;
				} else f2 = false;
			} else f1 = false;
			if(mCellNumber < 20) {
				f4 = MTOOL.page[mPage-1].feld[mCellNumber+5].mEmpty;
				if(mCellNumber != 4 && mCellNumber != 9 && mCellNumber != 14 && mCellNumber != 19) {
					f5 = MTOOL.page[mPage-1].feld[mCellNumber+6].mEmpty;
					if(mCellNumber != 3 && mCellNumber != 8 && mCellNumber != 13 && mCellNumber != 18) {
						f6 = MTOOL.page[mPage-1].feld[mCellNumber+7].mEmpty;
						if(mCellNumber != 2 && mCellNumber != 7 && mCellNumber != 12 && mCellNumber != 17)
							f7 = MTOOL.page[mPage-1].feld[mCellNumber+8].mEmpty;
						else f7 = false;
					} else f6 = false;
				} else f5 = false;
			} else f4 = false;
			if(mCellNumber < 15) {
				f8 = MTOOL.page[mPage-1].feld[mCellNumber+10].mEmpty;
				if(mCellNumber != 4 && mCellNumber != 9 && mCellNumber != 14) {
					f9 = MTOOL.page[mPage-1].feld[mCellNumber+11].mEmpty;
					if(mCellNumber != 3 && mCellNumber != 8 && mCellNumber != 13) {
						f10 = MTOOL.page[mPage-1].feld[mCellNumber+12].mEmpty;
						if(mCellNumber != 2 && mCellNumber != 7 && mCellNumber != 12) 
							f11 = MTOOL.page[mPage-1].feld[mCellNumber+13].mEmpty;
						else f11 = false;
					} else f10 = false;
				} else f9 = false;
			} else f8 = false;
			if(mCellNumber < 10) {
				f12 = MTOOL.page[mPage-1].feld[mCellNumber+15].mEmpty;
				if(mCellNumber != 4 && mCellNumber != 9) {
					f13 = MTOOL.page[mPage-1].feld[mCellNumber+16].mEmpty;
					if(mCellNumber != 3 && mCellNumber != 8) { 
						f14 = MTOOL.page[mPage-1].feld[mCellNumber+17].mEmpty;
						if(mCellNumber != 2 && mCellNumber != 7) 
							f15 = MTOOL.page[mPage-1].feld[mCellNumber+18].mEmpty;
						else f15 = false;
					} else f14 = false;
				} else f13 = false;
			} else f12 = false;
		}
		if(((Feld)source).b == 1 && ((Feld)source).h == 1)
			if(f0)
				return true;
		if(((Feld)source).b == 2 && ((Feld)source).h == 1)
			if(f0 && f1)
				return true;
		if(((Feld)source).b == 3 && ((Feld)source).h == 1)
			if(f0 && f1 && f2)
				return true;
		if(((Feld)source).b == 4 && ((Feld)source).h == 1)
			if(f0 && f1 && f2 && f3)
				return true;
		if(((Feld)source).b == 1 && ((Feld)source).h == 2)
			if(f0 && f4)
				return true;
		if(((Feld)source).b == 1 && ((Feld)source).h == 3)
			if(f0 && f4 && f8)
				return true;
		if(((Feld)source).b == 1 && ((Feld)source).h == 4)
			if(f0 && f4 && f8 && f12)
				return true;
		if(((Feld)source).b == 2 && ((Feld)source).h == 2)
			if(f0 && f1 && f4 && f5)
				return true;
		if(((Feld)source).b == 3 && ((Feld)source).h == 2)
			if(f0 && f1 && f2 && f4 && f5 && f6)
				return true;
		if(((Feld)source).b == 4 && ((Feld)source).h == 2)
			if(f0 && f1 && f2 && f3 && f4 && f5 && f6 && f7)
				return true;
		if(((Feld)source).b == 2 && ((Feld)source).h == 3)
			if(f0 && f1 && f4 && f5 && f8 && f9)
				return true;
		if(((Feld)source).b == 2 && ((Feld)source).h == 4)
			if(f0 && f1 && f4 && f5 && f8 && f9 && f12 && f13)
				return true;
		if(((Feld)source).b == 3 && ((Feld)source).h == 3)
			if(f0 && f1 && f2 && f4 && f5 && f6 && f8 && f9 && f10)
				return true;
		if(((Feld)source).b == 4 && ((Feld)source).h == 3)
			if(f0 && f1 && f2 && f3 && f4 && f5 && f6 && f7 && f8 && f9 && f10 && f11)
				return true;
		if(((Feld)source).b == 3 && ((Feld)source).h == 4)
			if(f0 && f1 && f2 && f4 && f5 && f6 && f8 && f9 && f10 && f12 && f13 && f14)
				return true;
		if(((Feld)source).b == 4 && ((Feld)source).h == 4)
			if(f0 && f1 && f2 && f3 && f4 && f5 && f6 && f7 && f8 && f9 && f10 && f11 && f12 && f13 && f14 && f15)
				return true;
//		} 
//		else if(MTOOL.felder == 25) {
//			boolean f0 = true, f1 = true, f2 = true, f3 = true, f4 = true, f5 = true, f6 = true, f7 = true,
//					f8 = true, f9 = true, f10 = true, f11 = true, f12 = true, f13 = true, f14 = true, f15 = true,
//					f16 = true, f17 = true, f18 = true, f19 = true, f20 = true, f21 = true, f22 = true, f23 = true,
//					f24 = true;
//			f0 = MTOOL.page[mPage-1].feld[mCellNumber].mEmpty;
//			if(mCellNumber != 4 && mCellNumber != 9 && mCellNumber != 14 && mCellNumber != 19 && mCellNumber != 24) {
//				f1 = MTOOL.page[mPage-1].feld[mCellNumber+1].mEmpty;
//				if(mCellNumber != 3 && mCellNumber != 8 && mCellNumber != 13 && mCellNumber != 18 && mCellNumber != 23) {
//					f2 = MTOOL.page[mPage-1].feld[mCellNumber+2].mEmpty;
//					if(mCellNumber != 2 && mCellNumber != 7 && mCellNumber != 12 && mCellNumber != 17 && mCellNumber != 22)
//						f3 = MTOOL.page[mPage-1].feld[mCellNumber+3].mEmpty;
//					else f3 = false;
//				} else f2 = false;
//			} else f1 = false;
////			if(mCellNumber != 12 && mCellNumber != 13 && mCellNumber != 14 && mCellNumber != 15) 
////				f4 = MTOOL.page[mPage-1].feld[mCellNumber+4].mEmpty;
////			else f4 = false;
//			if(mCellNumber < 20) {
//				f5 = MTOOL.page[mPage-1].feld[mCellNumber+5].mEmpty;
//				if(mCellNumber != 4 && mCellNumber != 9 && mCellNumber != 14 && mCellNumber != 19) { 
//					f6 = MTOOL.page[mPage-1].feld[mCellNumber+6].mEmpty;
//					if(mCellNumber != 3 && mCellNumber != 8 && mCellNumber != 13 && mCellNumber != 18) {
//						f7 = MTOOL.page[mPage-1].feld[mCellNumber+7].mEmpty;
//						if(mCellNumber != 2 && mCellNumber != 7 && mCellNumber != 12 && mCellNumber != 17) 
//							f8 = MTOOL.page[mPage-1].feld[mCellNumber+8].mEmpty;
//						else f8 = false;
//					} else f7 = false;
//				} else f6 = false;
//			} else f5 = false;
////			if(mCellNumber == 0 || mCellNumber == 1 || mCellNumber == 2 || mCellNumber == 4 ||
////					mCellNumber == 5 || mCellNumber == 6) 
////				f9 = MTOOL.page[mPage-1].feld[mCellNumber+9].mEmpty;
////			else f9 = false;
//			if(mCellNumber < 15)  {
//				f10 = MTOOL.page[mPage-1].feld[mCellNumber+10].mEmpty;
//				if(mCellNumber != 4 && mCellNumber != 9 && mCellNumber != 14) { 
//					f11 = MTOOL.page[mPage-1].feld[mCellNumber+11].mEmpty;
//					if(mCellNumber != 3 && mCellNumber != 8 && mCellNumber != 13) {
//						f12 = MTOOL.page[mPage-1].feld[mCellNumber+12].mEmpty;
//						if(mCellNumber != 2 && mCellNumber != 7 && mCellNumber != 12) 
//							f13 = MTOOL.page[mPage-1].feld[mCellNumber+13].mEmpty;
//						else f13 = false;
//					} else f12 = false;
//				} else f11 = false;
//			} else f10 = false;
//			if(mCellNumber == 0 || mCellNumber == 1) 
//				f14 = MTOOL.page[mPage-1].feld[mCellNumber+14].mEmpty;
//			else f14 = false;
//			if(mCellNumber == 0) 
//				f15 = MTOOL.page[mPage-1].feld[mCellNumber+15].mEmpty;
//			else f15 = false;
//			if(((Feld)source).b == 1 && ((Feld)source).h == 1)
//				if(f0)
//					return true;
//			if(((Feld)source).b == 2 && ((Feld)source).h == 1)
//				if(f0 && f1)
//					return true;
//			if(((Feld)source).b == 3 && ((Feld)source).h == 1)
//				if(f0 && f1 && f2)
//					return true;
//			if(((Feld)source).b == 4 && ((Feld)source).h == 1)
//				if(f0 && f1 && f2 && f3)
//					return true;
//			if(((Feld)source).b == 1 && ((Feld)source).h == 2)
//				if(f0 && f4)
//					return true;
//			if(((Feld)source).b == 1 && ((Feld)source).h == 3)
//				if(f0 && f4 && f8)
//					return true;
//			if(((Feld)source).b == 1 && ((Feld)source).h == 4)
//				if(f0 && f4 && f8 && f12)
//					return true;
//			if(((Feld)source).b == 2 && ((Feld)source).h == 2)
//				if(f0 && f1 && f4 && f5)
//					return true;
//			if(((Feld)source).b == 3 && ((Feld)source).h == 2)
//				if(f0 && f1 && f2 && f4 && f5 && f6)
//					return true;
//			if(((Feld)source).b == 4 && ((Feld)source).h == 2)
//				if(f0 && f1 && f2 && f3 && f4 && f5 && f6 && f7)
//					return true;
//			if(((Feld)source).b == 2 && ((Feld)source).h == 3)
//				if(f0 && f1 && f4 && f5 && f8 && f9)
//					return true;
//			if(((Feld)source).b == 2 && ((Feld)source).h == 4)
//				if(f0 && f1 && f4 && f5 && f8 && f9 && f12 && f13)
//					return true;
//			if(((Feld)source).b == 3 && ((Feld)source).h == 3)
//				if(f0 && f1 && f2 && f4 && f5 && f6 && f8 && f9 && f10)
//					return true;
//			if(((Feld)source).b == 4 && ((Feld)source).h == 3)
//				if(f0 && f1 && f2 && f3 && f4 && f5 && f6 && f7 && f8 && f9 && f10 && f11)
//					return true;
//			if(((Feld)source).b == 3 && ((Feld)source).h == 4)
//				if(f0 && f1 && f2 && f4 && f5 && f6 && f8 && f9 && f10 && f12 && f13 && f14)
//					return true;
//			if(((Feld)source).b == 4 && ((Feld)source).h == 4)
//				if(f0 && f1 && f2 && f3 && f4 && f5 && f6 && f7 && f8 && f9 && f10 && f11 && f12 && f13 && f14 && f15)
//					return true;
//		}
	    return false;
	}
}