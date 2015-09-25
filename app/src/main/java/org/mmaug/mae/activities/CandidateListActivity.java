package org.mmaug.mae.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.mmaug.mae.Config;
import org.mmaug.mae.R;
import org.mmaug.mae.adapter.CandidateAdapter;
import org.mmaug.mae.adapter.SectionHeaderAdapter;
import org.mmaug.mae.base.BaseActivity;
import org.mmaug.mae.models.Candidate;
import org.mmaug.mae.models.CandidateListReturnObject;
import org.mmaug.mae.rest.RESTClient;
import org.mmaug.mae.view.SpacesItemDecoration;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;

public class CandidateListActivity extends BaseActivity
    implements CandidateAdapter.OnItemClickListener {

  @Bind(R.id.rv_candidate_list) RecyclerView mRecyclerView;
  @Bind(R.id.pb_candidate_list) ProgressBar mProgressBar;
  Candidate candidateFromDetail;
  private CandidateAdapter candidateAdapter;
  private SectionHeaderAdapter sectionAdapter;
  private Dialog candidateResultDialog;

  @Override protected int getLayoutResource() {
    return R.layout.activity_candidate;
  }

  @Override protected boolean getHomeUpEnabled() {
    return true;
  }

  @Override protected boolean needToolbar() {
    return true;
  }

  @Override protected String getToolbarText() {
    return "အမတ်လောင်းများ";
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ButterKnife.bind(this);
    initRecyclerView();
    fetchCandidate();
    candidateFromDetail = (Candidate) getIntent().getSerializableExtra(Config.CANDIDATE);
    if (candidateFromDetail != null) {
      tvToolbarTitle.setText(getResources().getString(R.string.compare_title));
    }
  }

  private void initRecyclerView() {
    mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    candidateAdapter = new CandidateAdapter();
    candidateAdapter.setOnItemClickListener(this);
    sectionAdapter =
        new SectionHeaderAdapter(this, R.layout.item_candidate_header, R.id.tv_candidate_header,
            mRecyclerView, candidateAdapter);
    int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing_micro);
    mRecyclerView.setHasFixedSize(true);
    mRecyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
    mRecyclerView.setAdapter(sectionAdapter);
  }

  private void fetchCandidate() {
    showHideProgressBar(true);
    Map<String, String> pyithuParams = new HashMap<>();
    //Probably, there won't be much more than 200 candidates for a township for the same legislature
    pyithuParams.put(Config.PER_PAGE, "200");
    //TODO remove hardcoded PCODE
    pyithuParams.put(Config.CONSTITUENCY_ST_PCODE, "MMR013");
    pyithuParams.put(Config.CONSTITUENCY_DT_PCODE, "MMR013D001");
    pyithuParams.put(Config.CONSTITUENCY_TS_PCODE, "MMR013001");
    pyithuParams.put(Config.WITH, Config.PARTY);
    Call<CandidateListReturnObject> pyithuCall =
        RESTClient.getMPSService().getCandidateList(pyithuParams);
    pyithuCall.enqueue(new Callback<CandidateListReturnObject>() {
      @Override public void onResponse(Response<CandidateListReturnObject> response) {
        List<Candidate> candidates = response.body().getData();

        //sort
        Collections.sort(candidates, new Comparator<Candidate>() {
          @Override public int compare(Candidate lhs, Candidate rhs) {
            return rhs.getLegislature().compareTo(lhs.getLegislature());
          }
        });

        //header section
        List<SectionHeaderAdapter.Section> sections = new ArrayList<>();

        for (int i = 0; i < candidates.size(); i++) {
          Candidate location = candidates.get(i);
          //get type from the array
          if (sections.size() > 0) {
            if (!checkSection(sections, location.getLegislature())) {
              sections.add(new SectionHeaderAdapter.Section(i, location.getLegislature()));
            }
          } else {
            //add first type
            sections.add(new SectionHeaderAdapter.Section(0, location.getLegislature()));
          }
        }

        SectionHeaderAdapter.Section[] dummy = new SectionHeaderAdapter.Section[sections.size()];
        sectionAdapter.setSections(sections.toArray(dummy));
        candidateAdapter.setCandidates((ArrayList<Candidate>) candidates);
        showHideProgressBar(false);
      }

      @Override public void onFailure(Throwable t) {
        t.printStackTrace();
        showHideProgressBar(false);
      }
    });
  }

  private void showHideProgressBar(boolean visibility) {
    mProgressBar.setVisibility(visibility ? View.VISIBLE : View.GONE);
  }

  private boolean checkSection(List<SectionHeaderAdapter.Section> sections, String s) {
    for (int i = 0; i < sections.size(); i++) {
      if (s.equalsIgnoreCase(sections.get(i).getTitle())) return true;
    }
    return false;
  }

  @Override public void onItemClick(Candidate candidate) {
    //list item click
    if (candidateFromDetail != null) {

      if (candidate.getId().equals(candidateFromDetail.getId())) {
        candidateResultDialog =
            new Dialog(this, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar);
        View view = this.getLayoutInflater().inflate(R.layout.dialog_candidate_check, null);
        View okBtn = view.findViewById(R.id.voter_check_ok_btn);
        TextView textView = (TextView) view.findViewById(R.id.tv_vote_message);
        TextView canCompare = (TextView) view.findViewById(R.id.incorrect_vote);
        canCompare.setText("ကျေးဇူးပြု၍ အခြားအမတ်ကို ရွေးချယ်ပါ");
        textView.setText(getResources().getString(R.string.duplicate_candidate_choose));
        candidateResultDialog.setContentView(view);
        candidateResultDialog.show();
        okBtn.setOnClickListener(new View.OnClickListener() {
          @Override public void onClick(View view) {
            if (candidateResultDialog.isShowing()) {
              candidateResultDialog.dismiss();
            }
          }
        });
      } else if (candidate.getMpid() == null) {
        candidateResultDialog =
            new Dialog(this, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar);
        View view = this.getLayoutInflater().inflate(R.layout.dialog_candidate_check, null);
        View okBtn = view.findViewById(R.id.voter_check_ok_btn);
        TextView textView = (TextView) view.findViewById(R.id.tv_vote_message);
        TextView canCompare = (TextView) view.findViewById(R.id.incorrect_vote);
        canCompare.setText(candidate.getName() + " နှင့် " + candidateFromDetail.getName()
            + getResources().getString(R.string.cannot_compare_candidate));
        textView.setText(
            candidate.getName() + getResources().getString(R.string.incrroect_candidate_compare));
        candidateResultDialog.setContentView(view);
        candidateResultDialog.show();
        okBtn.setOnClickListener(new View.OnClickListener() {
          @Override public void onClick(View view) {
            if (candidateResultDialog.isShowing()) {
              candidateResultDialog.dismiss();
            }
          }
        });
      } else {
        Intent intent = new Intent(this, CandidateCompareActivity.class);
        intent.putExtra(Config.CANDIDATE, candidate);
        intent.putExtra(Config.CANDIDATE_COMPARE, candidateFromDetail);
        startActivity(intent);
      }
    } else {
      Intent intent = new Intent(this, CandidateDetailActivity.class);
      intent.putExtra(Config.CANDIDATE, candidate);
      startActivity(intent);
    }
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      super.onBackPressed();
      return true;
    }
    return false;
  }
}