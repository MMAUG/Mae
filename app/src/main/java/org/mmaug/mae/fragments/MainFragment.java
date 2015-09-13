package org.mmaug.mae.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import butterknife.Bind;
import butterknife.ButterKnife;
import org.mmaug.mae.R;
import org.mmaug.mae.adapter.AvatarAdapter;
import org.mmaug.mae.models.Avatar;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainFragment extends Fragment {

  private Avatar mSelectedAvatar = Avatar.ONE;
  private View mSelectedAvatarView;
  @Bind(R.id.avatars) GridView mAvatarGrid;

  public MainFragment() {
  }

  private void setUpGridView(View container) {
    mAvatarGrid.setAdapter(new AvatarAdapter(getActivity()));
    mAvatarGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mSelectedAvatarView = view;
        mSelectedAvatar = Avatar.values()[position];
      }
    });
    mAvatarGrid.setNumColumns(4);
    mAvatarGrid.setItemChecked(mSelectedAvatar.ordinal(), true);
  }

  //TODO reenable
  private int calculateSpanCount() {
    int avatarSize = getResources().getDimensionPixelSize(R.dimen.size_fab);
    int avatarPadding = getResources().getDimensionPixelSize(R.dimen.spacing_double);
    return mAvatarGrid.getWidth() / (avatarSize + avatarPadding);
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_main, container, false);
    ButterKnife.bind(this,rootView);
    setUpGridView(rootView);
    return rootView;
  }
}
