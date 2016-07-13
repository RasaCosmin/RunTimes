package r91.cosmin.runtimes.Activities;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import r91.cosmin.runtimes.Models.ActivitiesModel;
import r91.cosmin.runtimes.R;

public class RunActivity extends BaseActivity {

    private ArrayList<ActivitiesModel> activities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run);
        int week = getIntent().getIntExtra("week", 0);
        int day = getIntent().getIntExtra("day", 0);
        int activityId = dbHelper.GetActivityIdByWeekAndDay(week, day);
        activities = dbHelper.GetActivityById(activityId);
        findViewById(R.id.start_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartActivity();
            }
        });
    }

    private void StartActivity(){
        findViewById(R.id.start_activity).setVisibility(View.GONE);
        findViewById(R.id.activity_type).setVisibility(View.VISIBLE);
        Calendar calendar = Calendar.getInstance();
        Timer timer = new Timer();
        PlaySound(activities.get(0).getType());

        for(int i=0; i<activities.size(); i++){
            ActivitiesModel a = activities.get(i);
            String nA;
            if(i==activities.size()-1){
                nA = "Finish";
            }else{
                nA = activities.get(i+1).getType()+" "+(i+1);
            }
            final String nextA = nA;

            calendar.add(Calendar.SECOND, a.getTime());
            Date alarmTime = calendar.getTime();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    PlaySound(nextA);
                }
            }, alarmTime);
        }

    }

    private void PlaySound(final String type)
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((TextView)findViewById(R.id.activity_type)).setText(type);
            }
        });

        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
