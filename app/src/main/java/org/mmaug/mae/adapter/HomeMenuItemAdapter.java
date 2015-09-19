package org.mmaug.mae.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import org.mmaug.mae.R;
import org.mmaug.mae.models.HomeMenuItem;

/**
 * Created by indexer on 9/19/15.
 */

/**
 * Created by Ye Lin Aung on 15/08/04.
 */
public class HomeMenuItemAdapter
    extends RecyclerView.Adapter<HomeMenuItemAdapter.HomeMenuItemViewHolder> {
  private Context mContext;
  private List<HomeMenuItem> mParties;
  private ClickInterface mClickInterface;

  public HomeMenuItemAdapter() {
    mParties = new ArrayList<>();
  }

  public void setItems(List<HomeMenuItem> parties) {
    mParties = parties;
    notifyDataSetChanged();
  }

  @Override public HomeMenuItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    mContext = parent.getContext();
    View view = LayoutInflater.from(mContext).inflate(R.layout.home_item, parent, false);
    return new HomeMenuItemViewHolder(view);
  }

  @Override public void onBindViewHolder(HomeMenuItemViewHolder holder, int position) {
    HomeMenuItem homeMenuItem = mParties.get(position);
    holder.mTitle.setText(homeMenuItem.getTitle());
  }

  public void setOnItemClickListener(ClickInterface clickInterface) {
    mClickInterface = clickInterface;
  }

  @Override public int getItemCount() {
    return mParties != null ? mParties.size() : 0;
  }

  public interface ClickInterface {
    void onItemClick(View view, int position);
  }

  class HomeMenuItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView mTitle;

    public HomeMenuItemViewHolder(View itemView) {
      super(itemView);
      itemView.setOnClickListener(this);
      mTitle = (TextView) itemView.findViewById(R.id.text_title);
    }

    @Override public void onClick(View view) {
      if (mClickInterface != null) {
        mClickInterface.onItemClick(view, getAdapterPosition());
      }
    }
  }
}
