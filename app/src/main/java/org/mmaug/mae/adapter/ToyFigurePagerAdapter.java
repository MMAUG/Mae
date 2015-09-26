package org.mmaug.mae.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.HashMap;
import java.util.Map;
import org.mmaug.mae.Config;
import org.mmaug.mae.R;
import org.mmaug.mae.utils.FontCache;

/**
 * Created by yemyatthu on 9/24/15.
 */
public class ToyFigurePagerAdapter extends PagerAdapter {
  private LinearLayout mCurrentItem;
  private Map<String, Integer> candidateCount = new HashMap<>();
  private Context mContext;
  private int mColor;

  public ToyFigurePagerAdapter(Context context) {
    mContext = context;
    mColor = mContext.getResources().getColor(R.color.red);
  }

  public void setItems(Map<String, Integer> candidateCount) {
    this.candidateCount = candidateCount;
    notifyDataSetChanged();
  }

  @Override public int getCount() {
    return 3;
  }

  @Override public CharSequence getPageTitle(int position) {
    switch (position) {
      case 0:
        return Config.AMYOTHAE_HLUTTAW;
      case 1:
        return Config.PYITHU_HLUTTAW;
      case 2:
        return Config.TINEDAYTHA_HLUTTAW;
      default:
        return "";
    }
  }

  @Override public boolean isViewFromObject(View view, Object object) {
    return view == object;
  }

  @Override public Object instantiateItem(ViewGroup container, int position) {
    mContext = container.getContext();
    LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(mContext)
        .inflate(R.layout.toy_compare_layout, container, false);
    LinearLayout toyImageGroup = (LinearLayout) linearLayout.findViewById(R.id.this_year_toys);
    TextView compareHeader = (TextView) linearLayout.findViewById(R.id.compare_head_tv);
    TextView compareResult = (TextView) linearLayout.findViewById(R.id.compare_result_tv);
    Typeface typefaceTitle = FontCache.get("MyanmarAngoun.ttf", mContext);
    Typeface typefacelight = FontCache.get("pyidaungsu.ttf", mContext);
    compareHeader.setTypeface(typefaceTitle);
    compareResult.setTypeface(typefacelight);

    int realCandidateCount;
    int seatCandidateCount;
    int countToFilter;
    switch (position) {
      case 0:
        countToFilter = candidateCount.get(Config.AMYOTHAE_HLUTTAW);
        realCandidateCount = candidateCount.get(Config.AMYOTHAR_REAL_COUNT);
        seatCandidateCount = candidateCount.get(Config.AMYOTHAR_SEAT_COUNT);
        break;
      case 1:
        countToFilter = candidateCount.get(Config.PYITHU_HLUTTAW);
        realCandidateCount = candidateCount.get(Config.PYITHU_REAL_COUNT);
        seatCandidateCount = candidateCount.get(Config.PYITHU_SEAT_COUNT);
        break;
      case 2:
        countToFilter = candidateCount.get(Config.TINEDAYTHA_HLUTTAW);
        realCandidateCount = candidateCount.get(Config.TINE_DAYTHA_REAL_COUNT);
        seatCandidateCount = candidateCount.get(Config.TINE_DAYTHA_SEAT_COUNT);
        break;
      default:
        countToFilter = candidateCount.get(Config.AMYOTHAE_HLUTTAW);
        realCandidateCount = candidateCount.get(Config.AMYOTHAR_REAL_COUNT);
        seatCandidateCount = candidateCount.get(Config.AMYOTHAR_SEAT_COUNT);
        break;
    }
    Resources res = mContext.getResources();
    String realCountStr;
    if (realCandidateCount > 0) {
      realCountStr = String.format(res.getString(R.string.current_compare_result),
          String.valueOf(realCandidateCount));
    } else {
      realCountStr = res.getString(R.string.current_compare_result_zero);
    }
    compareResult.setText(realCountStr);

    String seatCountStr;
    seatCountStr = String.format(res.getString(R.string.current_compare_head),
        String.valueOf(seatCandidateCount));
    compareHeader.setText(seatCountStr);

    colorFilterImageViews(toyImageGroup, countToFilter,mColor);
    container.addView(linearLayout);
    return linearLayout;
  }

  @Override public void destroyItem(ViewGroup container, int position, Object object) {
    container.removeView((LinearLayout) object);
  }

  @Override public void setPrimaryItem(ViewGroup container, int position, Object object) {
    super.setPrimaryItem(container, position, object);
    mCurrentItem = (LinearLayout) object;
  }

  public LinearLayout getCurrentItem() {
    return mCurrentItem;
  }

  private void colorFilterImageViews(ViewGroup parent, int countToColor,int color) {
    for (int i = 0; i < parent.getChildCount(); i++) {
      if (i < countToColor - 1) {
        ((ImageView) parent.getChildAt(i)).setColorFilter(color);
      } else {
        ((ImageView) parent.getChildAt(i)).setColorFilter(
            mContext.getResources().getColor(R.color.mdtp_light_gray));
      }
    }
  }
  public void setFilterColor(int color){
    mColor = color;
    notifyDataSetChanged();
  }
}
