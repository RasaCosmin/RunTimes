package r91.cosmin.runtimes.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import r91.cosmin.runtimes.Helpers.DBHelper;

public class BaseActivity extends AppCompatActivity {
    protected DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(dbHelper==null) {
            dbHelper = new DBHelper(this);
            dbHelper.getReadableDatabase();
        }
    }
}
