package m.tool.settings;

import m.tool.pro.MTOOL;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

public class pickbg extends Activity{
	
    private final int PICK_IMAGE = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
	        case PICK_IMAGE:
	            if (resultCode == RESULT_OK) {
	            	Uri selectedImage = data.getData();
	                String[] filePathColumn = {MediaStore.Images.Media.DATA};
	
	                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
	                cursor.moveToFirst();
	
	                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
	                String filePath = cursor.getString(columnIndex);
	                cursor.close();
	
	                MTOOL.stuffdb.set_wallpaper(filePath);
					MTOOL.db.updateEntry(MTOOL.stuffdb);
					MTOOL.db.close();
					
	                finish();
	            }
	            else if(resultCode == RESULT_CANCELED)
	            	finish();
        }
    }
}