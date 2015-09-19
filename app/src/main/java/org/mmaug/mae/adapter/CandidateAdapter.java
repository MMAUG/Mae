package org.mmaug.mae.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import java.util.ArrayList;
import org.mmaug.mae.R;
import org.mmaug.mae.base.BaseAdapter;
import org.mmaug.mae.models.Candidate;

/**
 * Created by poepoe on 19/9/15.
 */
public class CandidateAdapter extends BaseAdapter<BaseAdapter.BaseViewHolder> {

  ArrayList<Candidate> candidates;

  public CandidateAdapter() {
    this.candidates = new ArrayList<>();
  }

  public void setCandidates(ArrayList<Candidate> candidates) {
    this.candidates = candidates;
    notifyDataSetChanged();
  }

  public void addCandidates(ArrayList<Candidate> candidateArrayList) {
    int startIndex = this.candidates.size();
    this.candidates.addAll(startIndex, candidateArrayList);
    notifyItemRangeChanged(startIndex, candidateArrayList.size());
  }

  @Override public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    View itemView = inflater.inflate(R.layout.item_candidate, parent, false);
    return new CandidateViewHolder(itemView, this);
  }

  @Override public void onBindViewHolder(BaseViewHolder holder, int position) {
    CandidateViewHolder viewHolder = ((CandidateViewHolder) holder);
  }

  @Override public int getItemCount() {
    return candidates.size();
  }

  class CandidateViewHolder extends BaseViewHolder {

    public CandidateViewHolder(View itemView, CandidateAdapter adapter) {
      super(itemView);
      ButterKnife.bind(this, itemView);
      itemView.setOnClickListener(this);
      mAdapter = adapter;
    }
  }
}
