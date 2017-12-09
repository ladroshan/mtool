package m.tool.stuff;

public class YouEntry {
 
    int _id;
    int _page;
    int _feld;
    int _width;
    int _height;
    int _item;
    String _option;
    String _note;
 
    public YouEntry(){
    }

    public YouEntry(int id, int page, int feld, int width, int height, int item, String option, String note){
        _id = id;
        _page = page;
        _feld = feld;
        _width = width;
        _height = height;
        _item = item;
        _option = option;
        _note = note;
    }
 
    public YouEntry(int page, int feld, int width, int height, int item, String option, String note){
        _page = page;
        _feld = feld;
        _width = width;
        _height = height;
        _item = item;
        _option = option;
        _note = note;
    }
    
    public void set_things(int page, int feld, int width, int height, int item, String option, String note){
    	_page = page;
        _feld = feld;
        _width = width;
        _height = height;
        _item = item;
        _option = option;
        _note = note;
    }
    
    public void set_things(int item, int width, int height, String option, String note){
        _item = item;
        _width = width;
        _height = height;
        _option = option;
        _note = note;
    }
    
    public int getID(){
        return _id;
    }
 
    public void setID(int id){
        this._id = id;
    }
 
    public int get_page() {
		return _page;
	}

	public void set_page(int _page) {
		this._page = _page;
	}

	public int get_feld() {
		return _feld;
	}

	public void set_feld(int _feld) {
		this._feld = _feld;
	}

	public int get_width() {
		return _width;
	}

	public void set_width(int _width) {
		this._width = _width;
	}

	public int get_height() {
		return _height;
	}

	public void set_height(int _height) {
		this._height = _height;
	}

	public int get_item() {
		return _item;
	}

	public void set_item(int _item) {
		this._item = _item;
	}

	public String get_option() {
		return _option;
	}

	public void set_option(String _option) {
		this._option = _option;
	}

	public String get_note() {
		return _note;
	}

	public void set_note(String _note) {
		this._note = _note;
	}
}