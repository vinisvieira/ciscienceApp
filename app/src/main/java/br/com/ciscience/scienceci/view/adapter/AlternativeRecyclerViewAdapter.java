package br.com.ciscience.scienceci.view.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import java.util.ArrayList;
import java.util.List;

import br.com.ciscience.scienceci.R;
import br.com.ciscience.scienceci.model.entity.impl.Alternative;
import br.com.ciscience.scienceci.view.activity.IQuestionView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pedrodimoura on 25/07/16.
 */
public class AlternativeRecyclerViewAdapter extends RecyclerView.Adapter<AlternativeRecyclerViewAdapter.AlternativeViewHolder> {

    private List<Alternative> mAlternatives;
    private View mView;
    private Context mContext;
    private IQuestionView mIQuestionView;

    private RadioButton lastChecked;
    private RadioButton correctAlternative;
    private int lastCheckedPos = 0;
    private int mCumulativeScore = 0;

    public AlternativeRecyclerViewAdapter(Context context, IQuestionView iQuestionView) {
        this.mContext = context;
        this.mIQuestionView = iQuestionView;
    }

    public void setAlternatives(List<Alternative> alternatives) {
        this.lastChecked = null;
        if (this.mAlternatives == null) this.mAlternatives = new ArrayList<>();
        this.mAlternatives.clear();
        this.mAlternatives = alternatives;
        notifyDataSetChanged();
    }

    public void answerQuestion() {
        if (this.mAlternatives.get((Integer) lastChecked.getTag()).getAnswer())
            this.mCumulativeScore += this.mIQuestionView.getCurrentQuestion().getScore();
    }

    public boolean isAlternativeChecked() {
        return (lastChecked != null);
    }

    public int getCumulativePoints() {
        return this.mCumulativeScore;
    }

    public void showCorrectAlternative() {
        correctAlternative.setText(correctAlternative.getText().toString().toUpperCase());
        correctAlternative.setTextColor(ContextCompat.getColor(correctAlternative.getContext(), R.color.colorAnswer));
        correctAlternative.setTypeface(Typeface.DEFAULT_BOLD);
    }

    @Override
    public AlternativeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_alternative_item, parent, false);
        return new AlternativeViewHolder(this.mView);
    }

    @Override
    public void onBindViewHolder(AlternativeViewHolder holder, int position) {
        holder.setIsRecyclable(false);

        holder.radioButton.setText(this.mAlternatives.get(position).getText());
        holder.radioButton.setTextColor(ContextCompat.getColor(holder.radioButton.getContext(), R.color.colorText));
        holder.radioButton.setTag(position);

        if (this.mAlternatives.get(position).getAnswer()) correctAlternative = holder.radioButton;

        holder.radioButton.setOnClickListener(view -> {
            RadioButton rb = (RadioButton) view;
            int clickedPos = ((Integer) rb.getTag());

            if (rb.isChecked()) {
                if (lastChecked != null && lastChecked != rb) {
                    lastChecked.setChecked(false);
                }
                lastChecked = rb;
                lastCheckedPos = clickedPos;
            } else {
                lastChecked = null;
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != this.mAlternatives && this.mAlternatives.size() > 0 ? this.mAlternatives.size() : 0);
    }

    public class AlternativeViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.radioButtonAlternative)
        RadioButton radioButton;

        public AlternativeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(AlternativeViewHolder.this, itemView);
        }
    }

}
