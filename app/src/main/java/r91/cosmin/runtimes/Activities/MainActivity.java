package r91.cosmin.runtimes.Activities;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import r91.cosmin.runtimes.Helpers.WeeksRecyclerAdapter;
import r91.cosmin.runtimes.Models.WeekModel;
import r91.cosmin.runtimes.R;

public class MainActivity extends BaseActivity {
    private ArrayList<WeekModel> weeks;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        GridLayoutManager glm = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(glm);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                PopulateData();
                FillLayout();
            }
        }, 2000);

    }

    private void PopulateData() {
        weeks = dbHelper.GetAllWeeks();
        FillLayout();
    }

    private void FillLayout() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                WeeksRecyclerAdapter adapter = new WeeksRecyclerAdapter(weeks, MainActivity.this);
                recyclerView.setAdapter(adapter);
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        });
    }
}
