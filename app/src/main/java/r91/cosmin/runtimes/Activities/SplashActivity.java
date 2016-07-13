package r91.cosmin.runtimes.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import r91.cosmin.runtimes.R;

public class SplashActivity extends BaseActivity {

    private SharedPreferences sharedPreferences;
    private String init = "INIT";
    private boolean isStarted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if(isInit()&&!isStarted)
            startActivity(new Intent(this, MainActivity.class));
    }

    private boolean isInit() {
        return(sharedPreferences.getBoolean(init, true));
    }

    private void markInit() {
        sharedPreferences.edit().putBoolean(init, true).apply();
    }

    public void StartMain()
    {
        startActivity(new Intent(this, MainActivity.class));
        markInit();
        isStarted = true;
    }
}
