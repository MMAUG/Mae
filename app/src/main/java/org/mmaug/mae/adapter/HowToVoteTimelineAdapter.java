package org.mmaug.mae.adapter;

import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import java.util.ArrayList;
import java.util.Random;
import org.mmaug.mae.R;
import org.mmaug.mae.base.BaseAdapter;
import org.mmaug.mae.view.CustomCircleDrawable;

/**
 * Created by poepoe on 23/9/15.
 */
public class HowToVoteTimelineAdapter extends BaseAdapter<BaseAdapter.BaseViewHolder> {

  ArrayList<HTVObject> htvObjectList;

  public HowToVoteTimelineAdapter(ArrayList<HTVObject> htvObjectList) {
    this.htvObjectList = htvObjectList;
  }

  @Override public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    View itemView = inflater.inflate(R.layout.item_how_to_vote, parent, false);
    return new ViewHolder(itemView, this);
  }

  @Override public void onBindViewHolder(BaseViewHolder holder, int position) {

    HTVObject object = htvObjectList.get(position);
    ViewHolder viewHolder = ((ViewHolder) holder);

    //step
    int step = position + 1;
    Random rnd = new Random();
    int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    viewHolder.mIvStep.setImageDrawable(new CustomCircleDrawable.Builder(step + "", color).build());

    //title text
    viewHolder.mTvTitle.setText(object.getTitle());

    //warning text with *
    String warning = viewHolder.itemView.getContext()
        .getString(R.string.how_to_vote_placeholder_text, object.getWarning());
    viewHolder.mTvWarning.setText(Html.fromHtml(warning));

    //show hide message view
    if (object.getMessage() != null) {
      viewHolder.mTvMessage.setText(object.getMessage());
    } else {
      viewHolder.mTvMessage.setVisibility(View.GONE);
    }

    if (object.getDrawable() != 0) {
      viewHolder.mIvMessage.setImageResource(object.getDrawable());
    } else {
      viewHolder.mIvMessage.setVisibility(View.GONE);
    }
  }

  @Override public int getItemCount() {
    return htvObjectList.size();
  }

  class ViewHolder extends BaseAdapter.BaseViewHolder {
    @Bind(R.id.tv_htv_title) TextView mTvTitle;
    @Bind(R.id.tv_htv_message) TextView mTvMessage;
    @Bind(R.id.tv_htv_warning) TextView mTvWarning;
    @Bind(R.id.iv_htv_message) ImageView mIvMessage;
    @Bind(R.id.iv_step) ImageView mIvStep;

    public ViewHolder(View itemView, HowToVoteTimelineAdapter adapter) {
      super(itemView);
      ButterKnife.bind(this, itemView);
      itemView.setOnClickListener(this);
      mAdapter = adapter;
    }
  }

  public static class HTVObject {
    private String title;
    private String message;
    private String warning;
    private int drawable;

    public void setTitle(String title) {
      this.title = title;
    }

    public void setDrawable(int drawable) {
      this.drawable = drawable;
    }

    public void setMessage(String message) {
      this.message = message;
    }

    public void setWarning(String warning) {
      this.warning = warning;
    }

    public int getDrawable() {
      return drawable;
    }

    public String getMessage() {
      return message;
    }

    public String getTitle() {
      return title;
    }

    public String getWarning() {
      return warning;
    }
  }
}
