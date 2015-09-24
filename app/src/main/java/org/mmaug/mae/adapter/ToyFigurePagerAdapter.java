package org.mmaug.mae.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import java.util.HashMap;
import java.util.Map;
import org.mmaug.mae.Config;
import org.mmaug.mae.R;

/**
 * Created by yemyatthu on 9/24/15.
 */
public class ToyFigurePagerAdapter extends PagerAdapter {
  private LinearLayout mCurrentItem;
  private Map<String,Integer> candidateCount = new HashMap<>();
  private Context mContext;
  public ToyFigurePagerAdapter(){

  }

  public void setItems(Map<String,Integer> candidateCount){
    this.candidateCount = candidateCount;
    notifyDataSetChanged();
  }
  @Override public int getCount() {
    return 3;
  }

  @Override public CharSequence getPageTitle(int position) {
    switch (position){
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
    LinearLayout linearLayout =
        (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.toy_compare_layout, container,
            false);
    LinearLayout toyImageGroup = (LinearLayout) linearLayout.findViewById(R.id.this_year_toys);
    int countToFilter;
    switch (position){
      case 0:
        countToFilter =  candidateCount.get(Config.AMYOTHAE_HLUTTAW);
        break;
      case 1:
        countToFilter = candidateCount.get(Config.PYITHU_HLUTTAW);
        break;
      case 2:
        countToFilter = candidateCount.get(Config.TINEDAYTHA_HLUTTAW);
        break;
      default:
        countToFilter = candidateCount.get(Config.AMYOTHAE_HLUTTAW);
        break;
    }
    colorFilterImageViews(toyImageGroup,countToFilter);
    container.addView(linearLayout);
    return linearLayout;
  }

  @Override public void destroyItem(ViewGroup container, int position, Object object) {
    container.removeView((LinearLayout) object);
  }

  @Override public void setPrimaryItem(ViewGroup container, int position, Object object) {
    super.setPrimaryItem(container, position, object);
    mCurrentItem = (LinearLayout)object;
  }

  public LinearLayout getCurrentItem(){
    return mCurrentItem;
  }

  private void colorFilterImageViews(ViewGroup parent,int countToColor){
    for(int i=0;i<parent.getChildCount();i++){
      if(i<countToColor-1){
        ((ImageView)parent.getChildAt(i)).setColorFilter(mContext.getResources().getColor(R.color.red));
      }else{
        ((ImageView)parent.getChildAt(i)).setColorFilter(mContext.getResources().getColor(R.color.mdtp_light_gray));
      }
    }
  }
}
