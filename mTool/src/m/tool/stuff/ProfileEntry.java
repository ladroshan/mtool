package m.tool.stuff;

public class ProfileEntry {
 
    int _id;
    String _name;
    String _style;
    String _inhalt;
 
    public ProfileEntry(){
    }

    public ProfileEntry(int id, String name, String style, String inhalt){
        _id = id;
        _name = name;
        _style = style;
        _inhalt = inhalt;
    }
 
    public ProfileEntry(String name, String style, String inhalt){
    	_name = name;
        _style = style;
        _inhalt = inhalt;
    }
    
    public void set_things(String name, String style, String inhalt){
    	_name = name;
        _style = style;
        _inhalt = inhalt;
    }
    
    public int getID(){
        return _id;
    }
 
    public void setID(int id){
        this._id = id;
    }

	public String get_name() {
		return _name;
	}

	public void set_name(String _name) {
		this._name = _name;
	}

	public String get_style() {
		return _style;
	}

	public void set_style(String _style) {
		this._style = _style;
	}

	public String get_inhalt() {
		return _inhalt;
	}

	public void set_inhalt(String _inhalt) {
		this._inhalt = _inhalt;
	}
}