package org.mmaug.mae.adapter;

import android.content.Context;
import android.graphics.Typeface;
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
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import org.mmaug.mae.Config;
import org.mmaug.mae.R;
import org.mmaug.mae.base.BaseAdapter;
import org.mmaug.mae.models.Candidate;
import org.mmaug.mae.models.CandidateSearchResult;
import org.mmaug.mae.utils.DataUtils;
import org.mmaug.mae.utils.FontCache;
import org.mmaug.mae.utils.MMTextUtils;
import org.mmaug.mae.utils.UserPrefUtils;

/**
 * Created by poepoe on 16/9/15.
 */
public class CandidateSearchAdapter extends BaseAdapter<BaseAdapter.BaseViewHolder> {
  Context mContext;
  ArrayList<CandidateSearchResult> candidates;
  private Typeface typeface;
  private boolean isUni;
  private MMTextUtils mmTextUtils;

  public CandidateSearchAdapter() {
  }

  public void setCandidates(ArrayList<CandidateSearchResult> candidates) {
    this.candidates = candidates;
    notifyDataSetChanged();
  }

  @Override public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    mContext = parent.getContext();

    //get typeface
    typeface = FontCache.getTypefaceLight(mContext);
    //check user has choose unicode or not
    isUni = UserPrefUtils.getInstance(mContext).getTextPref().equals(Config.UNICODE);
    mmTextUtils = MMTextUtils.getInstance(mContext);

    View itemView = inflater.inflate(R.layout.item_search_candidate, parent, false);
    return new ViewHolder(itemView, this);
  }

  @Override public void onBindViewHolder(BaseViewHolder holder, int position) {
    ViewHolder myHolder = ((ViewHolder) holder);
    String candidateName = candidates.get(position).getName();
    if (candidates.get(position).getParty() != null) {
      String candidateParty = candidates.get(position).getParty();
      myHolder.mTextParty.setText(candidateParty);
    }
    myHolder.mText.setText(candidateName);
    Glide.with(mContext)
        .load(candidates.get(position).getPhoto_url())
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .bitmapTransform(new CropCircleTransformation(mContext))
        .placeholder(R.drawable.profile_placeholder)
        .into(myHolder.mCandidateImage);

    if (isUni) {//if uni set typeface
      myHolder.mText.setTypeface(typeface);
    } else {//force to show uni
      mmTextUtils.prepareSingleView(myHolder.mText);
    }
  }

  @Override public int getItemCount() {
    return candidates.size();
  }

  class ViewHolder extends BaseViewHolder {
    @Bind(R.id.tv_candidate_name) TextView mText;
    @Bind(R.id.tv_candidate_party) TextView mTextParty;
    @Bind(R.id.candidateImage) ImageView mCandidateImage;

    public ViewHolder(View itemView, CandidateSearchAdapter adapter) {
      super(itemView);
      ButterKnife.bind(this, itemView);
      itemView.setOnClickListener(this);
      mAdapter = adapter;
    }
  }
}