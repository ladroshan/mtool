package m.tool.stuff;

public class AppwidgetEntry {
 
    int _id;
    int _awid;
    int _item;
    String _option;
 
    public AppwidgetEntry(){
    }

    public AppwidgetEntry(int id, int awid, int item, String option){
        _id = id;
        _awid = awid;
        _item = item;
        _option = option;
    }
 
    public AppwidgetEntry(int awid, int item, String option){
    	_awid = awid;
        _item = item;
        _option = option;
    }
    
    public void set_things(int awid, int item, String option){
    	_awid = awid;
        _item = item;
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
}