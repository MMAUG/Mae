package org.mmaug.mae.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import java.util.ArrayList;
import java.util.List;
import org.mmaug.mae.R;
import org.mmaug.mae.models.Party;
import org.mmaug.mae.utils.FontCache;
import org.mmaug.mae.view.AspectRatioImageView;

/**
 * Created by Ye Lin Aung on 15/08/04.
 */
public class PartyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
  private Context mContext;
  private List<Party> mParties;
  private ClickInterface mClickInterface;

  public PartyAdapter() {
    mParties = new ArrayList<>();
  }

  public void setParties(List<Party> parties) {
    mParties = parties;
    notifyDataSetChanged();
  }

  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    mContext = parent.getContext();
    View view = LayoutInflater.from(mContext).inflate(R.layout.item_party, parent, false);
    return new PartyViewHolder(view);
  }

  @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    if (holder instanceof PartyViewHolder) {
      Party party = mParties.get(position);
      ((PartyViewHolder) holder).mPartyNameMyanmar.setText(party.getPartyName());
      List<String> leaders = party.getLeadership();
      Typeface typefaceTitle = FontCache.get("MyanmarAngoun.ttf",mContext);
      Typeface typefacelight = FontCache.get("pyidaungsu.ttf", mContext);
      ((PartyViewHolder) holder).mPartyNameMyanmar.setTypeface(typefaceTitle);
   /* holder.mPartyLeader.setText(""); //Reset the textview unless you want some weird shit to happen
    for (String leader : leaders) {
      if (leaders.indexOf(leader) == leaders.size() - 1) {
        holder.mPartyLeader.append(leader);
      } else {
        holder.mPartyLeader.append(leader + "၊ ");
      }
    }*/
      Glide.with(mContext)
          .load(party.getPartyFlag())
          .diskCacheStrategy(DiskCacheStrategy.ALL)
          .centerCrop()
          .into(((PartyViewHolder) holder).mPartyFlag);
      ((PartyViewHolder) holder).mPartyFlag.setAdjustViewBounds(true);
    }
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

  class PartyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView mPartyNameEnglish;
    private TextView mPartyNameMyanmar;
    private AspectRatioImageView mPartyFlag;

    public PartyViewHolder(View itemView) {
      super(itemView);
      itemView.setOnClickListener(this);
      mPartyNameMyanmar = (TextView) itemView.findViewById(R.id.mPartyNameMyanmar);
      mPartyFlag = (AspectRatioImageView) itemView.findViewById(R.id.party_image);
    }

    @Override public void onClick(View view) {
      if (mClickInterface != null) {
        mClickInterface.onItemClick(view, getAdapterPosition());
      }
    }
  }
}
