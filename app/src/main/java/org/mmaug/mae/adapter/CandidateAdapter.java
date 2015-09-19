package org.mmaug.mae.adapter;

import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import org.mmaug.mae.base.BaseAdapter;

/**
 * Created by poepoe on 19/9/15.
 */
public class CandidateAdapter extends BaseAdapter<BaseAdapter.BaseViewHolder> {

  @Override public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return null;
  }

  @Override public void onBindViewHolder(BaseViewHolder holder, int position) {

  }

  @Override public int getItemCount() {
    return 0;
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
