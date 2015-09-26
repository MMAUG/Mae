package org.mmaug.mae.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
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
import org.mmaug.mae.utils.FontCache;
import org.mmaug.mae.utils.MixUtils;
import org.mmaug.mae.view.AutofitTextView;
import org.mmaug.mae.view.CustomCircleDrawable;

/**
 * Created by poepoe on 23/9/15.
 */
public class HowToVoteTimelineAdapter extends BaseAdapter<BaseAdapter.BaseViewHolder> {

  ArrayList<HTVObject> htvObjectList;
  int[] colors;
  Context mContext;
  Typeface typefaceTitle;
  Typeface typefacelight;
  public HowToVoteTimelineAdapter() {
    this.htvObjectList = new ArrayList<>();
  }

  public void setHtvObjectList(ArrayList<HTVObject> htvObjectList) {
    this.htvObjectList = htvObjectList;
    notifyDataSetChanged();
  }

  @Override public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    mContext =parent.getContext();
    View itemView = inflater.inflate(R.layout.item_how_to_vote, parent, false);
    return new ViewHolder(itemView, this);
  }

  @Override public void onBindViewHolder(BaseViewHolder holder, int position) {

    HTVObject object = htvObjectList.get(position);
    ViewHolder viewHolder = ((ViewHolder) holder);

    //step
    int step = position + 1;
    if (colors == null) {
      TypedArray ta =
          viewHolder.itemView.getContext().getResources().obtainTypedArray(R.array.color);
      colors = new int[ta.length()];
      for (int i = 0; i < ta.length(); i++) {
        colors[i] = ta.getColor(i, 0);
      }
      ta.recycle();
    }
    int color = colors[new Random().nextInt(colors.length)];
    viewHolder.mIvStep.setImageDrawable(
        new CustomCircleDrawable.Builder(MixUtils.convertToBurmese(step + ""), color).build());
    if (typefaceTitle == null) typefaceTitle = FontCache.get("MyanmarAngoun.ttf", mContext);
    if (typefacelight == null) typefacelight = FontCache.get("pyidaungsu.ttf", mContext);
    //title text
    viewHolder.mTvTitle.setText(object.getTitle());
    viewHolder.mTvTitle.setTypeface(typefaceTitle);


    //warning text with *
    String warning = viewHolder.itemView.getContext()
        .getString(R.string.how_to_vote_placeholder_text, object.getWarning());
    viewHolder.mTvWarning.setText(Html.fromHtml(warning));
    viewHolder.mTvWarning.setTypeface(typefacelight);

    //show hide message view
    if (object.getMessage() != null) {
      viewHolder.mTvMessage.setVisibility(View.VISIBLE);
      viewHolder.mTvMessage.setText(object.getMessage());
      viewHolder.mTvMessage.setSizeToFit(true);
      viewHolder.mTvMessage.setTypeface(typefaceTitle);
    } else {
      viewHolder.mTvMessage.setVisibility(View.GONE);
    }

    if (object.getDrawable() != 0) {
      viewHolder.mIvMessage.setVisibility(View.VISIBLE);
      viewHolder.mIvMessage.setImageResource(object.getDrawable());
    } else {
      viewHolder.mIvMessage.setVisibility(View.GONE);
    }
  }

  @Override public int getItemCount() {
    return htvObjectList.size();
  }

  public static class HTVObject {
    private String title;
    private String message;
    private String warning;
    private int drawable;

    public int getDrawable() {
      return drawable;
    }

    public void setDrawable(int drawable) {
      this.drawable = drawable;
    }

    public String getMessage() {
      return message;
    }

    public void setMessage(String message) {
      this.message = message;
    }

    public String getTitle() {
      return title;
    }

    public void setTitle(String title) {
      this.title = title;
    }

    public String getWarning() {
      return warning;
    }

    public void setWarning(String warning) {
      this.warning = warning;
    }
  }

  class ViewHolder extends BaseAdapter.BaseViewHolder {
    @Bind(R.id.tv_htv_title) TextView mTvTitle;
    @Bind(R.id.tv_htv_message) AutofitTextView mTvMessage;
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
}
