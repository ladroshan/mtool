package m.tool.stuff;

public class StuffEntry {
 
    int _id;
    String _do;
 
    public StuffEntry(int id, String do_){
        _id = id;
        _do = do_;
    }
 
    public StuffEntry(String do_){
        _do = do_;
    }
    
    public StuffEntry() {}

	public void set_things(String do_){
        _do = do_;
    }
    
    public int getID(){
        return _id;
    }
 
    public void setID(int id){
        this._id = id;
    }
 
	public String get_do() {
		return _do;
	}

	public void set_do(String _do) {
		this._do = _do;
	}
}