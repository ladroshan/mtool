package m.tool.stuff;

public class AppwidgetEntry {
 
    int _id;
    int _awid;
    String _option;
 
    public AppwidgetEntry(){
    }

    public AppwidgetEntry(int id, int awid, String option){
        _id = id;
        _awid = awid;
        _option = option;
    }
 
    public AppwidgetEntry(int awid, String option){
    	_awid = awid;
        _option = option;
    }
    
    public void set_things(int awid, String option){
    	_awid = awid;
        _option = option;
    }
    
    public int getID(){
        return _id;
    }
 
    public void setID(int id){
        this._id = id;
    }
 
	public int get_awid() {
		return _awid;
	}

	public void set_awid(int _awid) {
		this._awid = _awid;
	}

	public String get_option() {
		return _option;
	}

	public void set_option(String _option) {
		this._option = _option;
	}
}