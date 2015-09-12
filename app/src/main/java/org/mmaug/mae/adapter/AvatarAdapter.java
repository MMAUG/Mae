package org.mmaug.mae.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import org.mmaug.mae.R;
import org.mmaug.mae.models.Avatar;
import org.mmaug.mae.view.AvatarView;

/**
 * Created by indexer on 9/13/15.
 */
public class AvatarAdapter extends BaseAdapter {

  private static final Avatar[] mAvatars = Avatar.values();

  private final LayoutInflater mLayoutInflater;

  public AvatarAdapter(Context context) {
    mLayoutInflater = LayoutInflater.from(context);
  }

  @Override public View getView(int position, View convertView, ViewGroup parent) {
    if (null == convertView) {
      convertView = mLayoutInflater.inflate(R.layout.item_avatar, parent, false);
    }
    setAvatar((AvatarView) convertView, mAvatars[position]);
    return convertView;
  }

  private void setAvatar(AvatarView mIcon, Avatar avatar) {
    mIcon.setImageResource(avatar.getDrawableId());
    mIcon.setContentDescription(avatar.getNameForAccessibility());
  }

  @Override public int getCount() {
    return mAvatars.length;
  }

  @Override public Object getItem(int position) {
    return mAvatars[position];
  }

  @Override public long getItemId(int position) {
    return position;
  }
}

