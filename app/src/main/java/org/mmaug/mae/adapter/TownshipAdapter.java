package org.mmaug.mae.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import java.util.ArrayList;
import org.mmaug.mae.R;
import org.mmaug.mae.base.BaseAdapter;
import org.mmaug.mae.utils.DataUtils;

/**
 * Created by poepoe on 16/9/15.
 */
public class TownshipAdapter extends BaseAdapter<BaseAdapter.BaseViewHolder> {
  private ArrayList<DataUtils.Township> townships;

  public TownshipAdapter(ArrayList<DataUtils.Township> townships) {
    this.townships = townships;
  }

  public void setTownships(ArrayList<DataUtils.Township> townships) {
    this.townships = townships;
    notifyDataSetChanged();
  }

  @Override public BaseAdapter.BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    View itemView = inflater.inflate(R.layout.row_township, parent, false);
    return new ViewHolder(itemView);
  }

  @Override public void onBindViewHolder(BaseViewHolder holder, int position) {
    String townshipName = townships.get(position).getTowhshipNameBurmese();
    ((ViewHolder)holder).mText.setText(townshipName);
  }

  @Override public int getItemCount() {
    return townships.size();
  }

  class ViewHolder extends BaseAdapter.BaseViewHolder {
    @Bind(R.id.tv_township) TextView mText;

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
      itemView.setOnClickListener(this);
    }
  }
}