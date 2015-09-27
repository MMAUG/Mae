package org.mmaug.mae.fragments;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hu.aut.utillib.circular.animation.CircularAnimationUtils;
import java.text.ParseException;
import java.util.HashMap;
import org.mmaug.mae.R;
import org.mmaug.mae.activities.CandidateListActivity;
import org.mmaug.mae.activities.FaqListActivity;
import org.mmaug.mae.activities.HowToVoteActivity;
import org.mmaug.mae.activities.LocationActivity;
import org.mmaug.mae.activities.PartyListActivity;
import org.mmaug.mae.services.AlarmManagerBroadcastReceiver;
import org.mmaug.mae.utils.FontCache;
import org.mmaug.mae.utils.MixUtils;
import org.mmaug.mae.utils.UserPrefUtils;

public class HomeFragment extends android.support.v4.app.Fragment {
  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";

  @Bind(R.id.month_day_left) TextView monthDayLeft;
  @Bind(R.id.hour_minute_left) TextView hourMinuteLeft;
  @Bind(R.id.backdrop) LinearLayout backdrop;
  @Bind(R.id.txt_cardview_vote_check) TextView txt_cardview_vote_check;
  @Bind(R.id.txt_where_can_i_vote) TextView txt_where_can_i_vote;
  @Bind(R.id.txt_howtovote) TextView txt_howto_vote;
  @Bind(R.id.card_candidate_list) TextView card_candidate_list;
  @Bind(R.id.cardview_party_condtion_list) TextView cardview_party_condtion_list;
  @Bind(R.id.valid_sign) ImageView valid_sign;
  @Bind(R.id.to_vote) TextView toVote;
  @Bind(R.id.faq) ImageView faqImg;

  private AlarmManagerBroadcastReceiver alarm;
  private Dialog voteResultDialog;

  public HomeFragment() {
    // Required empty public constructor
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_home, container, false);
    ButterKnife.bind(this, view);
    setTypeFace();
    alarm = new AlarmManagerBroadcastReceiver();
    alarm.SetAlarm(getActivity());
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
          //toVote.setText("ပြောင်းလဲရန်");
          monthDayLeft.setText("အခ\u103Bိန\u103Aရောက\u103Aပ\u103Cီ");
          monthDayLeft.setVisibility(View.VISIBLE);
          hourMinuteLeft.setVisibility(View.GONE);
        }
      }.start();
    } catch (ParseException e) {
      e.printStackTrace();
    }
    faqImg.setColorFilter(getResources().getColor(R.color.white));
    faqImg.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        Intent faqIntent = new Intent(getActivity(), FaqListActivity.class);
        startActivity(faqIntent);
      }
    });

    UserPrefUtils userPrefUtils = new UserPrefUtils(getActivity());
    if (userPrefUtils.isValid()) {
      backdrop.setBackgroundColor(getResources().getColor(R.color.accent_color));
      txt_cardview_vote_check.setTextColor(Color.WHITE);
      valid_sign.setImageDrawable(getResources().getDrawable(R.drawable.ic_mark));
    } else {
      backdrop.setBackgroundColor(Color.parseColor("#FFC107"));
      valid_sign.setImageDrawable(getResources().getDrawable(R.drawable.ic_exclamation_mark));
      txt_cardview_vote_check.setTextColor(Color.WHITE);
    }
    return view;
  }

  void setTypeFace() {
    Typeface typefaceTitle = FontCache.get("MyanmarAngoun.ttf", getActivity());
    Typeface typefacelight = FontCache.get("pyidaungsu.ttf", getActivity());
    monthDayLeft.setTypeface(typefacelight);
    hourMinuteLeft.setTypeface(typefacelight);
    toVote.setTypeface(typefaceTitle);
    txt_cardview_vote_check.setTypeface(typefacelight);
    txt_where_can_i_vote.setTypeface(typefacelight);
    txt_howto_vote.setTypeface(typefacelight);
    card_candidate_list.setTypeface(typefacelight);
    cardview_party_condtion_list.setTypeface(typefacelight);
  }

  @Override public void onAttach(Activity activity) {
    super.onAttach(activity);
  }

  @Override public void onDetach() {
    super.onDetach();
  }

  @OnClick(R.id.cardview_where_can_i_vote) public void whereToVote(CardView cardView) {
    Intent mapIntent = new Intent(getActivity(), LocationActivity.class);
    startActivity(mapIntent);
  }

  @OnClick(R.id.cardview_candidate_condtion) public void candidateList(CardView cardView) {
    Intent mapIntent = new Intent(getActivity(), CandidateListActivity.class);
    startActivity(mapIntent);
  }

  @OnClick(R.id.cardview_party_condtion) public void partyList(CardView cardView) {
    Intent intent = new Intent(getActivity(), PartyListActivity.class);
    startActivity(intent);
  }

  @OnClick(R.id.card_how_to_vote) public void howToVote() {
    Intent intent = new Intent(getActivity(), HowToVoteActivity.class);
    startActivity(intent);
  }

  @OnClick(R.id.cardview_vote_check) public void showVoteResult(CardView textView) {
    //// TODO: 9/15/15 Handle the NOT OK result <Ye Myat Thu>
    UserPrefUtils userPrefUtils = new UserPrefUtils(getActivity());
    boolean ok = userPrefUtils.isValid();
    View view;
    voteResultDialog =
        new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar);
    if (ok) {
      Typeface typefaceTitle = FontCache.get("MyanmarAngoun.ttf", getActivity());
      Typeface typefacelight = FontCache.get("pyidaungsu.ttf", getActivity());
      view = getActivity().getLayoutInflater().inflate(R.layout.dialog_voter_check_ok, null);
      View myTargetView = view.findViewById(R.id.circle_full);
      View mySourceView = view.findViewById(R.id.circle_empty);
      TextView okBtn = (TextView) view.findViewById(R.id.voter_check_ok_btn);
      TextView txt_recheck = (TextView) view.findViewById(R.id.txt_recheck);
      TextView voter_check_not_ok = (TextView) view.findViewById(R.id.voter_check_not_ok);
      voter_check_not_ok.setTypeface(typefaceTitle);
      txt_recheck.setTypeface(typefaceTitle);
      okBtn.setTypeface(typefaceTitle);
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
            backdrop.setBackgroundColor(getResources().getColor(R.color.accent_color));
            txt_cardview_vote_check.setTextColor(Color.WHITE);
            valid_sign.setImageDrawable(getResources().getDrawable(R.drawable.ic_mark));
          }
        }
      });
    } else {
      Typeface typefaceTitle = FontCache.get("MyanmarAngoun.ttf", getActivity());
      Typeface typefacelight = FontCache.get("pyidaungsu.ttf", getActivity());
      view = getActivity().getLayoutInflater().inflate(R.layout.dialog_voter_check_not_ok, null);
      TextView okBtn = (TextView) view.findViewById(R.id.voter_check_ok_btn);
      TextView txt_recheck = (TextView) view.findViewById(R.id.txt_recheck);
      TextView voter_check_not_ok = (TextView) view.findViewById(R.id.voter_check_not_ok);
      voter_check_not_ok.setTypeface(typefaceTitle);
      txt_recheck.setTypeface(typefaceTitle);
      okBtn.setTypeface(typefaceTitle);
      voteResultDialog.setContentView(view);
      voteResultDialog.show();
      okBtn.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
          if (voteResultDialog.isShowing()) {
            voteResultDialog.dismiss();
            backdrop.setBackgroundColor(Color.parseColor("#FFC107"));
            valid_sign.setImageDrawable(getResources().getDrawable(R.drawable.ic_exclamation_mark));
            txt_cardview_vote_check.setTextColor(Color.WHITE);
          }
        }
      });
    }
  }
}
