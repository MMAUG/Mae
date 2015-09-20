package org.mmaug.mae.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import java.util.ArrayList;
import org.mmaug.mae.R;
import org.mmaug.mae.models.Candidate;

/**
 * Created by poepoe on 19/9/15.
 */
public class CandidateAdapter extends RecyclerView.Adapter<CandidateAdapter.CandidateViewHolder> {

  ArrayList<Candidate> candidates;
  private OnItemClickListener onItemClickListener;

  public CandidateAdapter() {
    this.candidates = new ArrayList<>();
  }

  @Override
  public CandidateAdapter.CandidateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    View itemView = inflater.inflate(R.layout.item_candidate, parent, false);
    return new CandidateViewHolder(itemView);
  }

  public void setCandidates(ArrayList<Candidate> candidates) {
    this.candidates = candidates;
    notifyDataSetChanged();
  }

  public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
    this.onItemClickListener = onItemClickListener;
  }

  public void addCandidates(ArrayList<Candidate> candidateArrayList) {
    int startIndex = this.candidates.size();
    this.candidates.addAll(startIndex, candidateArrayList);
    notifyItemRangeChanged(startIndex, candidateArrayList.size());
  }

  @Override public void onBindViewHolder(CandidateViewHolder holder, int position) {
    final Candidate candidate = candidates.get(position);
    holder.bindCandidate(candidate);
    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        onItemClickListener.onItemClick(candidate);
      }
    });
  }

  @Override public int getItemCount() {
    return candidates.size();
  }

  class CandidateViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.tv_candidate_name) public TextView tvCandidateName;
    @Bind(R.id.tv_candidate_degree) public TextView tvCandidateDegree;
    @Bind(R.id.tv_candidate_job) public TextView tvCandidateJob;
    @Bind(R.id.iv_candidate) public ImageView ivCandidateProfile;

    public CandidateViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
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

  public interface OnItemClickListener {
    void onItemClick(Candidate candidate);
  }
}
