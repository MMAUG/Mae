package org.mmaug.mae.utils;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;
import hu.aut.utillib.circular.animation.CircularAnimationUtils;
import org.mmaug.mae.R;
import org.mmaug.mae.activities.MainActivity;

/**
 * Created by lynn on 10/20/15.
 */
public class VoterCheckDialog {

    private MainActivity mActivity;

    private OkButtonClickListener mListener;

    private Dialog mDialog;

    private boolean isUnicode = false;

    public VoterCheckDialog(MainActivity activity, OkButtonClickListener listener) {
        mActivity = activity;
        mListener = listener;
        mDialog = new Dialog(activity, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar);
    }

    public void unicode(boolean isUni) {
        isUnicode = isUni;
    }

    public void showValid(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.dialog_voter_check_ok, null);

        View myTargetView = view.findViewById(R.id.circle_full);
        View mySourceView = view.findViewById(R.id.circle_empty);
        TextView okBtn = (TextView) view.findViewById(R.id.voter_check_ok_btn);
        TextView txt_recheck = (TextView) view.findViewById(R.id.txt_recheck);
        TextView recheck = (TextView) view.findViewById(R.id.recheck);
        TextView voter_check_ok = (TextView) view.findViewById(R.id.tv_voter_check_ok);

        if (isUnicode) {
            Typeface typefaceTitle = mActivity.getTypefaceTitle();

            voter_check_ok.setTypeface(typefaceTitle);
            okBtn.setTypeface(typefaceTitle);
            recheck.setTypeface(typefaceTitle);
            txt_recheck.setTypeface( mActivity.getTypefaceLight());
        } else {
            MMTextUtils.getInstance(mActivity.getApplicationContext())
                    .prepareMultipleViews(voter_check_ok, txt_recheck, okBtn, recheck);
        }

        //myTargetView & mySourceView are children in the CircularFrameLayout
        float finalRadius = CircularAnimationUtils.hypo(200, 200);
        ObjectAnimator animator =
                CircularAnimationUtils.createCircularTransform(myTargetView, mySourceView, 1, 2, 0F,
                        finalRadius);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(1500);
        mDialog.setContentView(view);
        mDialog.show();
        animator.start();

        recheck.setOnClickListener(new RecheckClickListener());
        okBtn.setOnClickListener(new OkClickListener(true));
    }

    public void showInvalid(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.dialog_voter_check_not_ok, null);

        TextView okBtn = (TextView) view.findViewById(R.id.voter_check_ok_btn);
        TextView txt_recheck = (TextView) view.findViewById(R.id.txt_recheck);
        TextView voter_check_not_ok = (TextView) view.findViewById(R.id.voter_check_not_ok);
        TextView recheck = (TextView) view.findViewById(R.id.recheck);

        if (isUnicode) {
            Typeface typefaceTitle = mActivity.getTypefaceTitle();

            voter_check_not_ok.setTypeface(typefaceTitle);
            okBtn.setTypeface(typefaceTitle);
            recheck.setTypeface(typefaceTitle);
            txt_recheck.setTypeface(mActivity.getTypefaceLight());
        } else {
            MMTextUtils.getInstance(mActivity.getApplicationContext())
                    .prepareMultipleViews(voter_check_not_ok, txt_recheck, okBtn, recheck);
        }

        mDialog.setContentView(view);
        mDialog.show();

        recheck.setOnClickListener(new RecheckClickListener());
        okBtn.setOnClickListener(new OkClickListener(false));
    }

    /*
     * OnClick Listener for ReCheck Button Click Event.
     */
    private class RecheckClickListener implements View.OnClickListener
    {
        @Override
        public void onClick(View view) {
            final UserPrefUtils userPrefUtils = new UserPrefUtils(mActivity);
            userPrefUtils.setSKIP(false);

            Intent i = new Intent(mActivity, MainActivity.class);
            mActivity.startActivity(i);
        }
    }

    /*
    * OnClick Listener for Ok Button Click Event.
    */
    private class OkClickListener implements View.OnClickListener
    {
        private boolean isValid =false;

        public OkClickListener(boolean validBoolean) {
            isValid = validBoolean;
        }

        @Override
        public void onClick(View view) {
            if (mDialog.isShowing()) {
                mDialog.dismiss();
                mListener.onClickOkFromDialog(view, isValid);
            }
        }
    }

    /**
     * Interface for OK Button Click Event.
     */
    public interface OkButtonClickListener {
        void onClickOkFromDialog(View view, boolean valid);
    }
}
