package org.mmaug.mae.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import java.util.ArrayList;
import org.mmaug.mae.R;
import org.mmaug.mae.utils.DataUtils;

/**
 * Created by poepoe on 16/9/15.
 */
public class TownshipAdapter extends BaseAdapter {
  private Context mContext;
  private ArrayList<DataUtils.Township> townships;

  public TownshipAdapter(Context context, ArrayList<DataUtils.Township> townships) {
    mContext = context;
    this.townships = townships;
  }

  @Override public int getCount() {
    return townships.size();
  }

  @Override public DataUtils.Township getItem(int i) {
    return townships.get(i);
  }

  @Override public long getItemId(int i) {
    return i;
  }

  @Override public View getView(int i, View view, ViewGroup viewGroup) {

    ViewHolder holder;
    if (view != null) {
      holder = (ViewHolder) view.getTag();
    } else {
      view = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
          R.layout.row_township, viewGroup, false);
      holder = new ViewHolder(view);
      view.setTag(holder);
    }
    holder.mText.setText(getItem(i).getTowhshipNameBurmese());
    return view;
  }

  static class ViewHolder {
    @Bind(R.id.tv_township) TextView mText;

    ViewHolder(View view) {
      ButterKnife.bind(this, view);
    }
  }
}