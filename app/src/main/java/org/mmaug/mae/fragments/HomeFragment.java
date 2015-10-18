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
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hu.aut.utillib.circular.animation.CircularAnimationUtils;
import java.text.ParseException;
import java.util.HashMap;
import org.mmaug.mae.R;
import org.mmaug.mae.activities.AboutActivity;
import org.mmaug.mae.activities.CandidateListActivity;
import org.mmaug.mae.activities.FaqListActivity;
import org.mmaug.mae.activities.HowToVoteActivity;
import org.mmaug.mae.activities.LocationActivity;
import org.mmaug.mae.activities.MainActivity;
import org.mmaug.mae.activities.PartyListActivity;
import org.mmaug.mae.activities.VoteGameActivity;
import org.mmaug.mae.activities.VotedActivity;
import org.mmaug.mae.base.BaseActivity;
import org.mmaug.mae.services.AlarmManagerBroadcastReceiver;
import org.mmaug.mae.utils.MMTextUtils;
import org.mmaug.mae.utils.MixUtils;
import org.mmaug.mae.utils.UserPrefUtils;

public class HomeFragment extends android.support.v4.app.Fragment {

  @Bind(R.id.month_day_left) TextView monthDayLeft;
  @Bind(R.id.hour_minute_left) TextView hourMinuteLeft;
  @Bind(R.id.cardview_vote_check) CardView backdrop;
  @Bind(R.id.txt_cardview_vote_check) TextView txt_cardview_vote_check;
  @Bind(R.id.txt_where_can_i_vote) TextView txt_where_can_i_vote;
  @Bind(R.id.txt_howtovote) TextView txt_howto_vote;
  @Bind(R.id.txt_game) TextView txtVoteGame;
  @Bind(R.id.card_candidate_list) TextView card_candidate_list;
  @Bind(R.id.cardview_party_condtion_list) TextView cardview_party_condtion_list;
  @Bind(R.id.valid_sign) ImageView valid_sign;
  @Bind(R.id.to_vote) TextView toVote;
  @Bind(R.id.faq) ImageView faqImg;
  @Bind(R.id.cardview_voted) CardView cardViewVoted;
  @Bind(R.id.txt_voted) TextView mVoted;

  private AlarmManagerBroadcastReceiver alarm;
  private Dialog voteResultDialog;
  Typeface typefaceTitle;
  Typeface typefacelight;
  boolean isUnicode;

  public HomeFragment() {
    // Required empty public constructor
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    BaseActivity baseActivity = (BaseActivity) getActivity();
    typefaceTitle = baseActivity.getTypefaceTitle();
    typefacelight = baseActivity.getTypefaceLight();
    isUnicode = baseActivity.isUnicode();
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
      backdrop.setCardBackgroundColor(getResources().getColor(R.color.accent_color));
      txt_cardview_vote_check.setTextColor(Color.WHITE);
      valid_sign.setImageDrawable(getResources().getDrawable(R.drawable.ic_mark));
    } else {
      backdrop.setCardBackgroundColor(Color.parseColor("#FFC107"));
      valid_sign.setImageDrawable(getResources().getDrawable(R.drawable.ic_exclamation_mark));
      txt_cardview_vote_check.setTextColor(Color.WHITE);
    }
    return view;
  }

  void setTypeFace() {
    if (isUnicode) {
      //user can see unicode
      monthDayLeft.setTypeface(typefacelight);
      hourMinuteLeft.setTypeface(typefacelight);
      toVote.setTypeface(typefaceTitle);
      txt_cardview_vote_check.setTypeface(typefaceTitle);
      txt_where_can_i_vote.setTypeface(typefaceTitle);
      txt_howto_vote.setTypeface(typefaceTitle);
      txtVoteGame.setTypeface(typefaceTitle);
      card_candidate_list.setTypeface(typefaceTitle);
      cardview_party_condtion_list.setTypeface(typefaceTitle);
      mVoted.setTypeface(typefaceTitle);
    } else {//user can't see unicode so force
      MMTextUtils.getInstance(getContext())
          .prepareMultipleViews(monthDayLeft, hourMinuteLeft, toVote, txt_cardview_vote_check,
              txt_where_can_i_vote, txt_howto_vote, txtVoteGame, card_candidate_list,
              cardview_party_condtion_list, mVoted);
    }
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

  @OnClick(R.id.cardview_voted) void Voted() {
    Intent intent = new Intent(getActivity(), VotedActivity.class);
    startActivity(intent);
  }

  @OnClick(R.id.card_vote_game) void voteGame() {
    Intent intent = new Intent(getActivity(), VoteGameActivity.class);
    startActivity(intent);
  }

  @OnClick(R.id.cardview_vote_check) public void showVoteResult(CardView textView) {
    final UserPrefUtils userPrefUtils = new UserPrefUtils(getActivity());
    boolean ok = userPrefUtils.isValid();
    View view;
    voteResultDialog =
        new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar);
    if (ok) {
      view = getActivity().getLayoutInflater().inflate(R.layout.dialog_voter_check_ok, null);
      View myTargetView = view.findViewById(R.id.circle_full);
      View mySourceView = view.findViewById(R.id.circle_empty);
      TextView okBtn = (TextView) view.findViewById(R.id.voter_check_ok_btn);
      TextView txt_recheck = (TextView) view.findViewById(R.id.txt_recheck);
      TextView recheck = (TextView) view.findViewById(R.id.recheck);
      TextView voter_check_not_ok = (TextView) view.findViewById(R.id.voter_check_not_ok);

      if (isUnicode) {
        voter_check_not_ok.setTypeface(typefaceTitle);
        txt_recheck.setTypeface(typefacelight);
        okBtn.setTypeface(typefaceTitle);
        recheck.setTypeface(typefaceTitle);
      } else {
        MMTextUtils.getInstance(getContext())
            .prepareMultipleViews(voter_check_not_ok, txt_recheck, okBtn, recheck);
      }

      recheck.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
          userPrefUtils.setSKIP(false);
          Intent i = new Intent(getActivity(), MainActivity.class);
          startActivity(i);
        }
      });

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
            backdrop.setCardBackgroundColor(getResources().getColor(R.color.accent_color));
            txt_cardview_vote_check.setTextColor(Color.WHITE);
            valid_sign.setImageDrawable(getResources().getDrawable(R.drawable.ic_mark));
          }
        }
      });
    } else {

      view = getActivity().getLayoutInflater().inflate(R.layout.dialog_voter_check_not_ok, null);
      TextView okBtn = (TextView) view.findViewById(R.id.voter_check_ok_btn);
      TextView txt_recheck = (TextView) view.findViewById(R.id.txt_recheck);
      TextView voter_check_not_ok = (TextView) view.findViewById(R.id.voter_check_not_ok);
      TextView recheck = (TextView) view.findViewById(R.id.recheck);
      if (isUnicode) {
        voter_check_not_ok.setTypeface(typefaceTitle);
        txt_recheck.setTypeface(typefacelight);
        okBtn.setTypeface(typefaceTitle);
        recheck.setTypeface(typefaceTitle);
      } else {
        MMTextUtils.getInstance(getContext())
            .prepareMultipleViews(voter_check_not_ok, txt_recheck, okBtn, recheck);
      }
      voteResultDialog.setContentView(view);
      voteResultDialog.show();
      okBtn.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
          if (voteResultDialog.isShowing()) {
            voteResultDialog.dismiss();
            backdrop.setCardBackgroundColor(getResources().getColor(R.color.orange));
            valid_sign.setImageDrawable(getResources().getDrawable(R.drawable.ic_exclamation_mark));
            txt_cardview_vote_check.setTextColor(Color.WHITE);
          }
        }
      });
      recheck.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
          userPrefUtils.setSKIP(false);
          Intent i = new Intent(getActivity(), MainActivity.class);
          startActivity(i);
        }
      });
    }
  }

  @OnClick(R.id.about) void about() {
    startActivity(new Intent(getActivity(), AboutActivity.class));
  }
}
