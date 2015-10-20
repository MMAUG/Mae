package org.mmaug.mae.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import java.text.ParseException;
import java.util.HashMap;
import org.mmaug.mae.R;
import org.mmaug.mae.activities.AboutActivity;
import org.mmaug.mae.activities.CandidateListActivity;
import org.mmaug.mae.activities.FaqListActivity;
import org.mmaug.mae.activities.FontCheckerActivity;
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
import org.mmaug.mae.utils.VoterCheckDialog;

public class HomeFragment extends android.support.v4.app.Fragment implements VoterCheckDialog.OkButtonClickListener {

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

    VoterCheckDialog dialog = new VoterCheckDialog((MainActivity) getActivity(), this);
    // Set uni or zg for dialog
    dialog.unicode(isUnicode);

    if (ok) {
      dialog.showValid(getActivity().getLayoutInflater());
    } else {
      dialog.showInvalid(getActivity().getLayoutInflater());
    }
  }

  /**
   * Override the Dialog Box's OK Button Click Event from VoterCheckDialog.
   * @param view Click View
   * @param isValid Voter Check Result
   */
  @Override
  public void onClickOkFromDialog(View view, boolean isValid) {
    if (isValid) {
      backdrop.setCardBackgroundColor(getResources().getColor(R.color.accent_color));
      valid_sign.setImageDrawable(getResources().getDrawable(R.drawable.ic_mark));
    } else {
      backdrop.setCardBackgroundColor(getResources().getColor(R.color.orange));
      valid_sign.setImageDrawable(getResources().getDrawable(R.drawable.ic_exclamation_mark));
    }
    txt_cardview_vote_check.setTextColor(Color.WHITE);
  }

  @OnClick(R.id.about) void about() {
    startActivity(new Intent(getActivity(), AboutActivity.class));
  }

  @OnClick(R.id.font_setting) void fontSetting() {
    startActivity(new Intent(getActivity(), FontCheckerActivity.class));
    getActivity().finish();
  }
}
