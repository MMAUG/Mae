package org.mmaug.mae.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import java.util.ArrayList;
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

    View itemView = inflater.inflate(R.layout.item_township, parent, false);
    return new ViewHolder(itemView, this);
  }

  @Override public void onBindViewHolder(BaseViewHolder holder, int position) {
    ViewHolder myHolder = ((ViewHolder) holder);
    String candidateName = candidates.get(position).getName();
    myHolder.mText.setText(candidateName);

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
    @Bind(R.id.tv_township) TextView mText;

    public ViewHolder(View itemView, CandidateSearchAdapter adapter) {
      super(itemView);
      ButterKnife.bind(this, itemView);
      itemView.setOnClickListener(this);
      mAdapter = adapter;
    }
  }
}