package r91.cosmin.runtimes.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import r91.cosmin.runtimes.Activities.MainActivity;
import r91.cosmin.runtimes.Activities.SplashActivity;
import r91.cosmin.runtimes.Models.ActivitiesModel;
import r91.cosmin.runtimes.Models.WeekModel;

/**
 * Created by Cosmin on 12.07.2016.
 */
public class DBHelper extends SQLiteOpenHelper{
    private static final int Db_Version = 1;
    private static final String DB_name = "RunTimes";

    private static final String CreateWeeksTable =
            "CREATE TABLE [weeks] (" +
                    "[id] INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    "[week] INTEGER NOT NULL," +
                    "[day] INTEGER NOT NULL," +
                    "[activity] INTEGER NOT NULL);";
    private static final String DropWeeksTable = "DROP TABLE IF EXISTS weeks";

    private static final String CreateActivitiesTable =
            "CREATE TABLE [activities] (" +
                    "[id] INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    "[idActivity] INTEGER NOT NULL," +
                    "[type] TEXT NOT NULL," +
                    "[time] INTEGER NOT NULL);";
    private static final String DropActivitiesTable = "DROP TABLE IF EXISTS activities";

    private Context context;

    public DBHelper(Context context) {
        super(context, DB_name, null, Db_Version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CreateWeeksTable);
        db.execSQL(CreateActivitiesTable);
        LoadDataFromCSV(db);
        ((SplashActivity)context).StartMain();
    }

    public boolean ExistTable(String name) {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT name FROM sqlite_master WHERE name=?";
        Cursor cursor = db.rawQuery(sql, new String[]{name});
        int result = cursor.getCount();
        cursor.close();
        db.close();

        return result > 0;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DropWeeksTable);
        db.execSQL(DropActivitiesTable);

        onCreate(db);
    }

    public void LoadDataFromCSV(SQLiteDatabase db) {
        ReadFile(1, db);
        ReadFile(2, db);
    }

    private void ReadFile(int i, SQLiteDatabase db){
        String filename;
        if(i==1){
            filename = "runing_times_activities.csv";
        }else{
            filename = "runing_times_weeks.csv";
        }
        AssetManager assetManager = this.context.getAssets();
        try{
            InputStream inputStream = assetManager.open(filename);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while((line = bufferedReader.readLine())!=null){
                ContentValues cvalues = new ContentValues();
                String values[] = line.split(";");
                switch(i){
                    case 1:
                        ActivitiesModel activity = new ActivitiesModel();
                        activity.setActivityId(Integer.parseInt(values[0]));
                        activity.setType(values[1]);
                        activity.setTime(Integer.parseInt(values[2]));
                        cvalues.put("idActivity", activity.getActivityId());
                        cvalues.put("type", activity.getType());
                        cvalues.put("time", activity.getTime());
                        db.insert("activities",null, cvalues);
                        break;
                    case 2:
                        WeekModel week = new WeekModel();
                        week.setWeek(Integer.parseInt(values[0]));
                        week.setDay(Integer.parseInt(values[1]));
                        week.setActivityId(Integer.parseInt(values[2]));
                        cvalues.put("week", week.getWeek());
                        cvalues.put("day", week.getDay());
                        cvalues.put("activity", week.getActivityId());
                        db.insert("weeks",null, cvalues);
                        break;
                }
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void InsertWeek(WeekModel week){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("week", week.getWeek());
        values.put("day", week.getDay());
        values.put("activity", week.getActivityId());
        db.insert("weeks",null, values);
        db.close();
    }

    public void InsertActivity(ActivitiesModel activitiesModel){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("idActivity", activitiesModel.getActivityId());
        values.put("type", activitiesModel.getType());
        values.put("time", activitiesModel.getTime());
        db.insert("activities",null, values);
        db.close();
    }

    public int GetActivityIdByWeekAndDay(int week, int day){
        SQLiteDatabase db = getReadableDatabase();
        String sqlSelect = "Select * from weeks where week="+week+" AND day="+day+";";
        Cursor cursor = db.rawQuery(sqlSelect, null);
        int activityId =-1;
        if(cursor!=null){
            if(cursor.moveToFirst())
            {
                activityId = cursor.getInt(3);
            }
        }
        cursor.close();
        db.close();

        return activityId;
    }

    public ArrayList<WeekModel> GetAllWeeks(){
        SQLiteDatabase db = getReadableDatabase();
        String sqlSelect = "Select * from weeks;";
        Cursor cursor = db.rawQuery(sqlSelect, null);
        ArrayList<WeekModel> weeks = new ArrayList<>();

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    WeekModel week = new WeekModel();
                    week.setId(cursor.getInt(0));
                    week.setWeek(cursor.getInt(1));
                    week.setDay(cursor.getInt(2));
                    week.setActivityId(cursor.getInt(3));

                    weeks.add(week);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();

        return weeks;
    }

    public ArrayList<ActivitiesModel> GetActivityById(int activityId){
        SQLiteDatabase db = getReadableDatabase();
        String sqlSelect = "Select * from activities where idActivity="+activityId+";";
        Cursor cursor = db.rawQuery(sqlSelect, null);
        ArrayList<ActivitiesModel> activities = new ArrayList<>();

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    ActivitiesModel activity = new ActivitiesModel();
                    activity.setId(cursor.getInt(0));
                    activity.setActivityId(cursor.getInt(1));
                    activity.setType(cursor.getString(2));
                    activity.setTime(cursor.getInt(3));

                    activities.add(activity);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();

        return activities;
    }

}
