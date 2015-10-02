package org.mmaug.mae.adapter;

import android.content.Context;
import android.graphics.Typeface;
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
import org.mmaug.mae.Config;
import org.mmaug.mae.R;
import org.mmaug.mae.models.Candidate;
import org.mmaug.mae.utils.FontCache;
import org.mmaug.mae.utils.MMTextUtils;
import org.mmaug.mae.utils.UserPrefUtils;
import org.mmaug.mae.view.AutofitTextView;

/**
 * Created by poepoe on 19/9/15.
 */
public class CandidateAdapter extends RecyclerView.Adapter<CandidateAdapter.CandidateViewHolder> {

  ArrayList<Candidate> candidates;
  private OnItemClickListener onItemClickListener;
  private Context mContext;
  private Typeface typeface;
  private boolean isUni;
  private MMTextUtils mmTextUtils;

  public CandidateAdapter() {

    this.candidates = new ArrayList<>();
  }

  @Override
  public CandidateAdapter.CandidateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    mContext = parent.getContext();

    //get typeface
    typeface = FontCache.getTypefaceLight(mContext);
    //check user has choose unicode or not
    isUni = UserPrefUtils.getInstance(mContext).getTextPref().equals(Config.UNICODE);
    mmTextUtils = MMTextUtils.getInstance(mContext);

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

  public void removeCandidate(int position) {
    this.candidates.remove(position);
    notifyDataSetChanged();
  }

  @Override public void onBindViewHolder(CandidateViewHolder holder, final int position) {
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

  public interface OnItemClickListener {
    void onItemClick(Candidate candidate);
  }

  class CandidateViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.tv_candidate_name) AutofitTextView tvCandidateName;
    @Bind(R.id.tv_candidate_degree) TextView tvCandidateDegree;
    @Bind(R.id.tv_candidate_job) TextView tvCandidateJob;
    @Bind(R.id.iv_candidate_party_flag) ImageView ivPartyFlag;
    @Bind(R.id.iv_candidate) ImageView ivCandidateProfile;

    public CandidateViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    private void bindCandidate(Candidate candidate) {
      tvCandidateName.setText(candidate.getName());
      tvCandidateName.setSizeToFit(true);
      tvCandidateDegree.setText(candidate.getEducation());
      tvCandidateJob.setText(candidate.getOccupation());

      if (isUni) {
        tvCandidateName.setTypeface(typeface);
        tvCandidateDegree.setTypeface(typeface);
        tvCandidateJob.setTypeface(typeface);
      } else {
        mmTextUtils.prepareMultipleViews(tvCandidateName, tvCandidateJob, tvCandidateDegree);
      }

      Glide.with(itemView.getContext())
          .load(candidate.getPhotoUrl())
          .diskCacheStrategy(DiskCacheStrategy.ALL)
          .into(ivCandidateProfile);
      ivCandidateProfile.setAdjustViewBounds(true);
      Glide.with(itemView.getContext())
          .load(candidate.getParty().getPartyFlag())
          .diskCacheStrategy(DiskCacheStrategy.ALL)
          .into(ivPartyFlag);
    }
  }
}
