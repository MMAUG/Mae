package org.mmaug.mae.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import java.util.ArrayList;
import org.mmaug.mae.R;
import org.mmaug.mae.utils.DataUtils;

/**
 * Created by poepoe on 16/9/15.
 */
public class TownshipAdapter extends RecyclerView.Adapter<TownshipAdapter.ViewHolder> {
  private ArrayList<DataUtils.Township> townships;
  private OnItemClick onItemClick;

  public TownshipAdapter(ArrayList<DataUtils.Township> townships) {
    this.townships = townships;
  }

  public void setTownships(ArrayList<DataUtils.Township> townships) {
    this.townships = townships;
    notifyDataSetChanged();
  }

  public void setOnItemClick(OnItemClick onItemClick) {
    this.onItemClick = onItemClick;
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    View itemView = inflater.inflate(R.layout.row_township, parent, false);
    return new ViewHolder(itemView);
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    String townshipName = townships.get(position).getTowhshipNameBurmese();
    holder.mText.setText(townshipName);
  }

  @Override public int getItemCount() {
    return townships.size();
  }

  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    @Bind(R.id.tv_township) TextView mText;

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
      itemView.setOnClickListener(this);
    }

    @Override public void onClick(View v) {
      onItemClick.onItemClick(townships.get(getAdapterPosition()));
    }
  }

  public interface OnItemClick {
    void onItemClick(DataUtils.Township township);
  }
}