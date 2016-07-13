package r91.cosmin.runtimes.Helpers;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import r91.cosmin.runtimes.Activities.RunActivity;
import r91.cosmin.runtimes.Models.WeekModel;
import r91.cosmin.runtimes.R;

/**
 * Created by Cosmin on 12.07.2016.
 */
public class WeeksRecyclerAdapter extends RecyclerView.Adapter<WeekViewHolder>{
    private Context context;
    private List<WeekModel> weeks;

    public WeeksRecyclerAdapter(List<WeekModel> weeks, Context context) {
        this.weeks = weeks;
        this.context = context;
    }

    @Override
    public WeekViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.week_item, parent,false);
        WeekViewHolder holder = new WeekViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(WeekViewHolder holder, int position) {
        final WeekModel weekModel = weeks.get(position);
        holder.day.setText("week "+weekModel.getDay());
        holder.week.setText("day "+weekModel.getWeek()+"");
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, RunActivity.class);
                intent.putExtra("day", weekModel.getDay());
                intent.putExtra("week", weekModel.getWeek());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return weeks.size();
    }
}
