package org.mmaug.mae.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import java.util.ArrayList;
import java.util.List;
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
public class CandidateAdapter extends RecyclerView.Adapter<CandidateAdapter.CandidateViewHolder>
    implements Filterable {

  ArrayList<Candidate> candidates;
  Filter candidatesFilter;
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

  @Override public Filter getFilter() {
    if (candidatesFilter == null) candidatesFilter = new CandidateFilter();

    return candidatesFilter;
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

  private class CandidateFilter extends Filter {

    @Override protected FilterResults performFiltering(CharSequence constraint) {

      FilterResults results = new FilterResults();
      // We implement here the filter logic
      if (constraint == null || constraint.length() == 0) {
        // No filter implemented we return all the list
        results.values = candidates;
        results.count = candidates.size();
      } else {
        // We perform filtering operation
        ArrayList<Candidate> nCandidateList = new ArrayList<Candidate>();
        for (Candidate p : candidates) {
          if (p.getName().startsWith(constraint.toString())) {
            nCandidateList.add(p);
          }
        }
        results.values = nCandidateList;
        results.count = nCandidateList.size();
      }
      return results;
    }

    @Override protected void publishResults(CharSequence constraint, FilterResults results) {

      // Now we have to inform the adapter about the new list filtered
      if (results.count == 0) {
        
      } else {
        candidates.clear();
        candidates.addAll((ArrayList<Candidate>) results.values);
        notifyDataSetChanged();
      }
    }

    private boolean checkSection(List<SectionHeaderAdapter.Section> sections, String s) {
      for (int i = 0; i < sections.size(); i++) {
        if (s.equalsIgnoreCase(sections.get(i).getTitle())) return true;
      }
      return false;
    }
  }
}
