package br.com.ciscience.scienceci.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.ciscience.scienceci.R;
import br.com.ciscience.scienceci.model.entity.impl.Quiz;
import br.com.ciscience.scienceci.util.Constants;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by pedrodimoura on 15/06/16.
 */
public class QuizRecyclerViewAdapter extends RecyclerView.Adapter<QuizRecyclerViewAdapter.QuizViewHolder> {

    private List<Quiz> mQuiz;

    public QuizRecyclerViewAdapter() {
        if (this.mQuiz == null ) this.mQuiz = new ArrayList<>();
    }

    public void addQuiz(Quiz quiz) {
        this.mQuiz.add(quiz);
        this.notifyDataSetChanged();
    }

    @Override
    public QuizViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new QuizViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_quiz_item, parent, false));
    }

    @Override
    public void onBindViewHolder(QuizViewHolder holder, int position) {
        holder.setIsRecyclable(false);

        holder.textViewQuizName.setText(this.mQuiz.get(position).getName());
        holder.textViewQuizContest.setText(this.mQuiz.get(position).getContest().getName());
    }

    @Override
    public int getItemCount() {
        return (null != this.mQuiz && this.mQuiz.size() > 0 ? this.mQuiz.size() : 0);
    }

    public class QuizViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.quizName)
        TextView textViewQuizName;

        @BindView(R.id.quizContest)
        TextView textViewQuizContest;

        public QuizViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(QuizViewHolder.this, itemView);
        }

        @OnClick({R.id.buttonAnswer})
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.buttonAnswer:
                    Log.d(Constants.DEBUG_KEY, "onClick -> " + view);
                    break;
            }
        }
    }

}