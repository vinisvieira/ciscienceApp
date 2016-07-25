package br.com.ciscience.scienceci.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import java.util.List;

import br.com.ciscience.scienceci.R;
import br.com.ciscience.scienceci.model.entity.impl.Alternative;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pedrodimoura on 25/07/16.
 */
public class AlternativeRecyclerViewAdapter extends RecyclerView.Adapter<AlternativeRecyclerViewAdapter.AlternativeViewHolder> {

    private List<Alternative> mAlternatives;
    private View mView;

    @Override
    public AlternativeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_alternative_item, parent, false);
        return new AlternativeViewHolder(this.mView);
    }

    @Override
    public void onBindViewHolder(AlternativeViewHolder holder, int position) {

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
