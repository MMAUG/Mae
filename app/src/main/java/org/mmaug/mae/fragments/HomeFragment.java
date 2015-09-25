package org.mmaug.mae.fragments;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hu.aut.utillib.circular.animation.CircularAnimationUtils;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Random;
import org.mmaug.mae.R;
import org.mmaug.mae.activities.CandidateListActivity;
import org.mmaug.mae.activities.HowToVoteActivity;
import org.mmaug.mae.activities.LocationActivity;
import org.mmaug.mae.activities.PartyListActivity;
import org.mmaug.mae.utils.MixUtils;

public class HomeFragment extends android.support.v4.app.Fragment {
  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";

  @Bind(R.id.month_day_left) TextView monthDayLeft;
  @Bind(R.id.hour_minute_left) TextView hourMinuteLeft;
  @Bind(R.id.to_vote) TextView toVote;
  @Bind(R.id.tvThumb) TextView tvThumb;
  @Bind(R.id.tv_candidate_list) TextView tvThumb_party_condition;

  // TODO: Rename and change types of parameters
  private String mParam1;
  private String mParam2;
  private Dialog voteResultDialog;

  private OnFragmentInteractionListener mListener;

  public HomeFragment() {
    // Required empty public constructor
  }

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @param param1 Parameter 1.
   * @param param2 Parameter 2.
   * @return A new instance of fragment HomeFragment.
   */
  // TODO: Rename and change types and number of parameters
  public static HomeFragment newInstance(String param1, String param2) {
    HomeFragment fragment = new HomeFragment();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    args.putString(ARG_PARAM2, param2);
    fragment.setArguments(args);
    return fragment;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mParam1 = getArguments().getString(ARG_PARAM1);
      mParam2 = getArguments().getString(ARG_PARAM2);
    }
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_home, container, false);
    ButterKnife.bind(this, view);
    try {

      new CountDownTimer(MixUtils.calculateTimeLeftToVote(), 1000) {
        @Override public void onTick(long millisUntilFinished) {

          HashMap<String, String> values = MixUtils.formatTime(millisUntilFinished);
          String monthDay = MixUtils.convertToBurmese(values.get("month_day"));
          String hourMinute = MixUtils.convertToBurmese(values.get("hour_minute"));
          monthDayLeft.setText(monthDay);
          hourMinuteLeft.setText(hourMinute);
          if (monthDay.isEmpty()) {
            monthDayLeft.setVisibility(View.GONE);
          }
        }

        @Override public void onFinish() {
          toVote.setText("ပြောင်းလဲရန်");
          monthDayLeft.setText("အခ\u103Bိန\u103Aရောက\u103Aပ\u103Cီ");
          monthDayLeft.setVisibility(View.VISIBLE);
          hourMinuteLeft.setVisibility(View.GONE);
        }
      }.start();
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return view;
  }

  // TODO: Rename method, update argument and hook method into UI event
  public void onButtonPressed(Uri uri) {
    if (mListener != null) {
      mListener.onFragmentInteraction(uri);
    }
  }

  @Override public void onAttach(Activity activity) {
    super.onAttach(activity);
  }

  @Override public void onDetach() {
    super.onDetach();
  }

  @OnClick(R.id.cardview_party) public void whereToVote(CardView cardView) {
    Intent mapIntent = new Intent(getActivity(), LocationActivity.class);
    startActivity(mapIntent);
  }

  @OnClick(R.id.tv_candidate_list) public void candidateList(TextView cardView) {
    Intent mapIntent = new Intent(getActivity(), CandidateListActivity.class);
    startActivity(mapIntent);
  }

  @OnClick(R.id.tv_party_list) public void partyList(TextView cardView) {
    Intent intent = new Intent(getActivity(), PartyListActivity.class);
    startActivity(intent);
  }

  @OnClick(R.id.cardview_how_to_vote) public void howToVote() {
    Intent intent = new Intent(getActivity(), HowToVoteActivity.class);
    startActivity(intent);
  }

  @OnClick(R.id.tvThumb) public void showVoteResult(TextView textView) {
    //// TODO: 9/15/15 Handle the NOT OK result <Ye Myat Thu>
    boolean ok = new Random().nextBoolean();
    View view;
    voteResultDialog =
        new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar);
    if (ok) {
      view = getActivity().getLayoutInflater().inflate(R.layout.dialog_voter_check_ok, null);
      View myTargetView = view.findViewById(R.id.circle_full);
      View mySourceView = view.findViewById(R.id.circle_empty);
      View okBtn = view.findViewById(R.id.voter_check_ok_btn);
      //myTargetView & mySourceView are children in the CircularFrameLayout
      float finalRadius = CircularAnimationUtils.hypo(200, 200);
      ////getCenter computes from 2 view: One is touched, and one will be animated, but you can use anything for center
      //int[] center = CircularAnimationUtils.getCenter(fab, myTargetView);
      ObjectAnimator animator =
          CircularAnimationUtils.createCircularTransform(myTargetView, mySourceView, 1, 2, 0F,
              finalRadius);
      animator.setInterpolator(new AccelerateDecelerateInterpolator());
      animator.setDuration(1500);
      voteResultDialog.setContentView(view);
      voteResultDialog.show();
      animator.start();
      okBtn.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
          if (voteResultDialog.isShowing()) {
            voteResultDialog.dismiss();
          }
        }
      });
    } else {
      view = getActivity().getLayoutInflater().inflate(R.layout.dialog_voter_check_not_ok, null);
      View okBtn = view.findViewById(R.id.voter_check_ok_btn);
      voteResultDialog.setContentView(view);
      voteResultDialog.show();
      okBtn.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
          if (voteResultDialog.isShowing()) {
            voteResultDialog.dismiss();
          }
        }
      });
    }
  }

  /**
   * This interface must be implemented by activities that contain this
   * fragment to allow an interaction in this fragment to be communicated
   * to the activity and potentially other fragments contained in that
   * activity.
   * <p>
   * See the Android Training lesson <a href=
   * "http://developer.android.com/training/basics/fragments/communicating.html"
   * >Communicating with Other Fragments</a> for more information.
   */
  public interface OnFragmentInteractionListener {
    // TODO: Update argument type and name
    public void onFragmentInteraction(Uri uri);
  }
}
