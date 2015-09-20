package org.mmaug.mae.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.mmaug.mae.R;
import org.mmaug.mae.models.Party;

public class PartyDetailFragment extends Fragment {

  public PartyDetailFragment() {
    // Required empty public constructor
  }

  public static PartyDetailFragment createInstance(Party party) {
    PartyDetailFragment detailFragment = new PartyDetailFragment();
    Bundle bundle = new Bundle();
    detailFragment.setArguments(bundle);
    return detailFragment;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_party_detail, container, false);
  }
}
