package br.com.ciscience.scienceci.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import br.com.ciscience.scienceci.R;
import br.com.ciscience.scienceci.model.entity.impl.Student;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pedrodimoura on 28/07/16.
 */
public class RankingRecyclerViewAdapter extends RecyclerView.Adapter<RankingRecyclerViewAdapter.RankingViewHolder> {

    private List<Student> mStudents;

    private View mView;
    private Context mContext;

    public RankingRecyclerViewAdapter(Context context) {
        this.mContext = context;
        if (this.mStudents == null) this.mStudents = new ArrayList<>();
    }

    public void addRanking(Student student) {
        this.mStudents.add(student);
        notifyDataSetChanged();
    }

    public void clear() {
        this.mStudents.clear();
        notifyDataSetChanged();
    }

    @Override
    public RankingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_ranking_item, parent, false);
        return new RankingViewHolder(this.mView);
    }

    @Override
    public void onBindViewHolder(RankingViewHolder holder, int position) {
        holder.setIsRecyclable(false);

        if (position == 0) {
            Picasso
                    .with(this.mContext)
                    .load(R.drawable.lead)
                    .into(holder.circleImageViewBadge);
        } else {
            Picasso.with(this.mContext)
                    .load(getDrawable(this.mStudents.get(position).getScore().intValue()))
                    .into(holder.circleImageViewBadge);
        }

        holder.textViewRanking.setText(this.mStudents.get(position).getName());

    }

    public int getDrawable(int score) {
        switch (score) {
            case 25:
                return R.drawable.b1;
            case 35:
                return R.drawable.b2;
            case 45:
                return R.drawable.b3;
            case 50:
                return R.drawable.b4;
            case 100:
                return R.drawable.p1;
            case 140:
                return R.drawable.p2;
            case 180:
                return R.drawable.p3;
            case 220:
                return R.drawable.p4;
            case 250:
                return R.drawable.o1;
            case 300:
                return R.drawable.o2;
            case 400:
                return R.drawable.o3;
            case 500:
                return R.drawable.o4;
            default:
                return R.drawable.b1;
        }
    }

    @Override
    public int getItemCount() {
        return (null != this.mStudents && this.mStudents.size() > 0 ? this.mStudents.size() : 0);
    }

    public class RankingViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageViewBadge) ImageView circleImageViewBadge;
        @BindView(R.id.textViewRanking) TextView textViewRanking;

        public RankingViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(RankingViewHolder.this, itemView);
        }
    }

}
