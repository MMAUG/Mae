package org.mmaug.mae.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import java.util.ArrayList;
import java.util.List;
import org.mmaug.mae.R;
import org.mmaug.mae.models.Party;

/**
 * Created by Ye Lin Aung on 15/08/04.
 */
public class PartyAdapter extends RecyclerView.Adapter<PartyAdapter.PartyViewHolder> {
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

  @Override public PartyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    mContext = parent.getContext();
    View view = LayoutInflater.from(mContext).inflate(R.layout.item_party, parent, false);
    return new PartyViewHolder(view);
  }

  @Override public void onBindViewHolder(PartyViewHolder holder, int position) {
    Party party = mParties.get(position);
    holder.mPartyNameMyanmar.setText(party.getPartyName());
    holder.mPartyNameEnglish.setText(party.getPartyNameEnglish());
    List<String> leaders = party.getLeadership();
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
        .centerCrop()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(holder.mPartyFlag);
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
    private ImageView mPartyFlag;

    public PartyViewHolder(View itemView) {
      super(itemView);
      itemView.setOnClickListener(this);
      mPartyNameEnglish = (TextView) itemView.findViewById(R.id.mPartyNameEnglish);
      mPartyNameMyanmar = (TextView) itemView.findViewById(R.id.mPartyNameMyanmar);
      mPartyFlag = (ImageView) itemView.findViewById(R.id.party_image);
    }

    @Override public void onClick(View view) {
      if (mClickInterface != null) {
        mClickInterface.onItemClick(view, getAdapterPosition());
      }
    }
  }
}
