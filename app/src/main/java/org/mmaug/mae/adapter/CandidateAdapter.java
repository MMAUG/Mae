package org.mmaug.mae.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import java.util.ArrayList;
import org.mmaug.mae.R;
import org.mmaug.mae.base.BaseAdapter;
import org.mmaug.mae.models.Candidate;
import org.mmaug.mae.view.AspectRatioImageView;

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
    viewHolder.bindCandidate(candidates.get(position));
  }

  @Override public int getItemCount() {
    return candidates.size();
  }

  class CandidateViewHolder extends BaseViewHolder {

    @Bind(R.id.tv_candidate_name) TextView tvCandidateName;
    @Bind(R.id.tv_candidate_degree) TextView tvCandidateDegree;
    @Bind(R.id.tv_candidate_job) TextView tvCandidateJob;
    @Bind(R.id.iv_candidate) AspectRatioImageView ivCandidateProfile;

    public CandidateViewHolder(View itemView, CandidateAdapter adapter) {
      super(itemView);
      ButterKnife.bind(this, itemView);
      itemView.setOnClickListener(this);
      mAdapter = adapter;
    }

    private void bindCandidate(Candidate candidate) {
      tvCandidateName.setText(candidate.getName());
      tvCandidateDegree.setText(candidate.getEducation());
      tvCandidateJob.setText(candidate.getOccupation());
      Glide.with(itemView.getContext())
          .load(candidate.getPhotoUrl())
          .diskCacheStrategy(DiskCacheStrategy.ALL)
          .into(ivCandidateProfile);
      ivCandidateProfile.setAdjustViewBounds(true);
    }
  }
}
