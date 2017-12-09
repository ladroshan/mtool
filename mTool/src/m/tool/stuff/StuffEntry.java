package m.tool.stuff;

public class StuffEntry {
 
    int _id;
    int _current_page;
    int _pages;
    String _wallpaper;
    int _bgalpha;
    int _italpha;
    int _icsize;
	int _startpg;
    String _do;
 
    public StuffEntry(int id, int current_page, int pages, String wallpaper, int bgalpha, int italpha, int icsize, int startpg, String do_){
        _id = id;
        _current_page = current_page;
        _pages = pages;
        _bgalpha = bgalpha;
        _italpha = italpha;
        _icsize = icsize;
        _wallpaper = wallpaper;
        _startpg = startpg;
        _do = do_;
    }
 
    public StuffEntry(int current_page, int pages, String wallpaper, int bgalpha, int italpha, int icsize, int startpg, String do_){
        _current_page = current_page;
        _pages = pages;
        _bgalpha = bgalpha;
        _italpha = italpha;
        _icsize = icsize;
        _wallpaper = wallpaper;
        _startpg = startpg;
        _do = do_;
    }
    
    public StuffEntry() {}

	public void set_things(int current_page, int pages, String wallpaper, int bgalpha, int italpha, int icsize, int startpg, String do_){
    	_current_page = current_page;
        _pages = pages;
        _bgalpha = bgalpha;
        _italpha = italpha;
        _icsize = icsize;
        _wallpaper = wallpaper;
        _startpg = startpg;
        _do = do_;
    }
    
    public int getID(){
        return _id;
    }
 
    public void setID(int id){
        this._id = id;
    }
 
    public int get_current_page() {
		return _current_page;
	}

	public void set_current_page(int _current_page) {
		this._current_page = _current_page;
	}

	public int get_pages() {
		return _pages;
	}

	public void set_pages(int _pages) {
		this._pages = _pages;
	}
	
	public String get_wallpaper() {
		return _wallpaper;
	}

	public void set_wallpaper(String _wallpaper) {
		this._wallpaper = _wallpaper;
	}
	
	public int get_bgalpha() {
		return _bgalpha;
	}

	public void set_bgalpha(int _bgalpha) {
		this._bgalpha = _bgalpha;
	}
	
	public int get_italpha() {
		return _italpha;
	}

	public void set_italpha(int _italpha) {
		this._italpha = _italpha;
	}

	public int get_icsize() {
		return _icsize;
	}

	public void set_icsize(int _icsize) {
		this._icsize = _icsize;
	}
	
	public int get_startpg() {
		return _startpg;
	}

	public void set_startpg(int _startpg) {
		this._startpg = _startpg;
	}
	
	public String get_do() {
		return _do;
	}

	public void set_do(String _do) {
		this._do = _do;
	}
}