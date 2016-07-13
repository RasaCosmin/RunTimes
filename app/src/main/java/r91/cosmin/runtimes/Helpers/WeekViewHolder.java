package r91.cosmin.runtimes.Helpers;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import r91.cosmin.runtimes.R;

/**
 * Created by Cosmin on 12.07.2016.
 */
public class WeekViewHolder extends RecyclerView.ViewHolder{
    public CardView card;
    public TextView week;
    public TextView day;

    public WeekViewHolder(View itemView) {
        super(itemView);
        card = (CardView) itemView.findViewById(R.id.card);
        week = (TextView) itemView.findViewById(R.id.week_text);
        day = (TextView) itemView.findViewById(R.id.day_text);
    }
}
