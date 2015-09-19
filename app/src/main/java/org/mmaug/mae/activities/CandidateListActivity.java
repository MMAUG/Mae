package org.mmaug.mae.activities;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.mmaug.mae.Config;
import org.mmaug.mae.R;
import org.mmaug.mae.base.BaseActivity;
import org.mmaug.mae.models.Candidate;
import org.mmaug.mae.models.CandidateListReturnObject;
import org.mmaug.mae.rest.RESTClient;
import org.mmaug.mae.rest.RESTService;
import org.mmaug.mae.utils.PotratitImageView;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;

public class CandidateListActivity extends BaseActivity {


  @Bind(R.id.pyithu_hluttaw_grid) GridLayout mPyithuHlutawGrid;
  @Bind(R.id.section_text_pyithu) TextView mPyithuHeader;
  @Bind(R.id.pyithu_hluttaw_progesss) ProgressBar mPyithuProgress;

  @Bind(R.id.pyithu_tinedaytha_grid) GridLayout mTineDathaGrid;
  @Bind(R.id.section_text_tinedaytha) TextView mTineDaythaHeader;
  @Bind(R.id.pyithu_tinedaytha_progesss) ProgressBar mTineDaythaProgress;

  @Bind(R.id.pyithu_amyothar_grid) GridLayout mAmyoTharGrid;
  @Bind(R.id.section_text_amyothar) TextView mAmyoTharHeader;
  @Bind(R.id.pyithu_amyothar_progesss) ProgressBar mAmyoTharProgress;


  private RESTService mMpsService;
  private View candidateView;
  @Override protected int getLayoutResource() {
    return R.layout.activity_candidate;
  }

  @Override protected boolean getHomeUpEnabled() {
    return true;
  }

  @Override protected boolean needToolbar() {
    return true;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ButterKnife.bind(this);
    mMpsService = RESTClient.getMPSService();
    mPyithuHeader.setText(Config.PYITHU_HLUTTAW);
    mAmyoTharHeader.setText(Config.AMYOTHAE_HLUTTAW);
    mTineDaythaHeader.setText(Config.TINEDAYTHA_HLUTTAW);
    fetchCandidate();

  }

  private void fetchCandidate() {
    Map<String, String> pyithuParams = new HashMap<>();
    //Probably, there won't be much more than 25 candidates for a township for the same legislature
    pyithuParams.put(Config.PER_PAGE, "25");
    pyithuParams.put(Config.LEGISLATURE, Config.PYITHU_HLUTTAW);
    // TODO: 9/20/15 SO MUCH HARD_CODED , FILL THIS WITH CHOOSEN TS DATA<YE MYAT THU>
    pyithuParams.put(Config.CONSTITUENCY_ST_PCODE, "MMR013");
    pyithuParams.put(Config.CONSTITUENCY_DT_PCODE, "MMR013D001");
    pyithuParams.put(Config.CONSTITUENCY_TS_PCODE, "MMR013001");
    Call<CandidateListReturnObject> pyithuCall = mMpsService.getCandidateList(pyithuParams);
    pyithuCall.enqueue(new Callback<CandidateListReturnObject>() {
      @Override public void onResponse(Response<CandidateListReturnObject> response) {
        List<Candidate> pyithuCal = response.body().getData();
        inflateGrid(pyithuCal, mPyithuHlutawGrid, mPyithuProgress, mPyithuHeader,
            Config.PYITHU_HLUTTAW);
      }

      @Override public void onFailure(Throwable t) {

      }
    });

    Map<String, String> tinedaythaParams = new HashMap<>();
    //Probably, there won't be much more than 25 candidates for a township for the same legislature
    tinedaythaParams.put(Config.PER_PAGE, "25");
    tinedaythaParams.put(Config.LEGISLATURE, Config.TINEDAYTHA_HLUTTAW);
    // TODO: 9/20/15 SO MUCH HARD_CODED , FILL THIS WITH CHOOSEN TS DATA<YE MYAT THU>
    tinedaythaParams.put(Config.CONSTITUENCY_ST_PCODE, "MMR013");
    tinedaythaParams.put(Config.CONSTITUENCY_DT_PCODE, "MMR013D001");
    tinedaythaParams.put(Config.CONSTITUENCY_TS_PCODE, "MMR013001");

    Call<CandidateListReturnObject> tinedaythaCall = mMpsService.getCandidateList(tinedaythaParams);
    tinedaythaCall.enqueue(new Callback<CandidateListReturnObject>() {
      @Override public void onResponse(Response<CandidateListReturnObject> response) {
        List<Candidate> tineList = response.body().getData();
        inflateGrid(tineList, mTineDathaGrid, mTineDaythaProgress, mTineDaythaHeader,
            Config.TINEDAYTHA_HLUTTAW);
      }

      @Override public void onFailure(Throwable t) {

      }
    });

    Map<String, String> amyotharParams = new HashMap<>();
    //Probably, there won't be much more than 25 candidates for a township for the same legislature
    amyotharParams.put(Config.PER_PAGE, "25");
    amyotharParams.put(Config.LEGISLATURE, Config.AMYOTHAE_HLUTTAW);
    // TODO: 9/20/15 SO MUCH HARD_CODED , FILL THIS WITH CHOOSEN TS DATA<YE MYAT THU>
    amyotharParams.put(Config.CONSTITUENCY_ST_PCODE, "MMR013");
    amyotharParams.put(Config.CONSTITUENCY_DT_PCODE, "MMR013D001");
    amyotharParams.put(Config.CONSTITUENCY_TS_PCODE, "MMR013001");

    Call<CandidateListReturnObject> amyotharCall = mMpsService.getCandidateList(amyotharParams);
    amyotharCall.enqueue(new Callback<CandidateListReturnObject>() {
      @Override public void onResponse(Response<CandidateListReturnObject> response) {
        List<Candidate> amyoList = response.body().getData();
        inflateGrid(amyoList,mAmyoTharGrid,mAmyoTharProgress,mAmyoTharHeader,Config.AMYOTHAE_HLUTTAW);
      }

      @Override public void onFailure(Throwable t) {

      }
    });

  }

  //@Override public void onItemClick(View view, int position) {
  //  Intent intent = new Intent();
  //  intent.setClass(this, PartyDetailActivity.class);
  //  Bundle bundle = new Bundle();
  //  bundle.putSerializable("party", mParties.get(position));
  //  intent.putExtras(bundle);
  //  startActivity(intent);
  //}
  @Override protected String getToolbarText() {
    return "အမတ်လောင်းများ";
  }

  //class CandidateViewHolder{
  //  @Bind(R.id.iv_candidate) AspectRatioImageView candidateIamge;
  //  @Bind(R.id.tv_candidate_degree) TextView candidateDegree;
  //  @Bind(R.id.tv_candidate_job) TextView candidateJob;
  //  @Bind(R.id.tv_candidate_name) TextView candidateName;
  //  public CandidateViewHolder(View view){
  //    ButterKnife.bind(this,view);
  //  }
  //}

  private void inflateView(View view,Candidate candidate){
    PotratitImageView imageView = (PotratitImageView) view.findViewById(R.id.iv_candidate);
    TextView candidateName = (TextView) view.findViewById(R.id.tv_candidate_name);
    TextView candidateJob = (TextView) view.findViewById(R.id.tv_candidate_job);
    TextView candidateEducation = (TextView) view.findViewById(R.id.tv_candidate_degree);
    Glide.with(CandidateListActivity.this)
        .load(candidate.getPhotoUrl())
        .diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
    candidateName.setText(candidate.getName());
    candidateJob.setText(candidate.getOccupation());
    candidateEducation.setText(candidate.getEducation());
  }

  private void inflateGrid(List<Candidate> candList,GridLayout gridLayout,ProgressBar progressBar,TextView header,String headerName){
    int totalCandidateSize = candList.size();
    if (totalCandidateSize == 0){
      header.setVisibility(View.GONE);
      progressBar.setVisibility(View.GONE);
      return;
    }
    gridLayout.setColumnCount(2);
    gridLayout.setColumnOrderPreserved(true);
    gridLayout.setRowOrderPreserved(true);
    gridLayout.setAlignmentMode(GridLayout.ALIGN_BOUNDS);
    int rowCount;
    if(totalCandidateSize%2==0){
      rowCount = totalCandidateSize/2;
    }else{
      rowCount = (totalCandidateSize/2)+1;
    }
    gridLayout.setRowCount(rowCount);
    Display display = getWindowManager().getDefaultDisplay();
    Point size = new Point();
    display.getSize(size);
    int width = size.x/2-getResources().getDimensionPixelSize(R.dimen.spacing_minor)*2;
    for(int i =0, c = 0, r = 0; i < totalCandidateSize; i++, c++)
    {
      if(c == 2)
      {
        c = 0;
        r++;
      }
      View view = getLayoutInflater().inflate(R.layout.item_candidate,gridLayout,false);
      GridLayout.LayoutParams param = (GridLayout.LayoutParams) view.getLayoutParams();
      param.height = GridLayout.LayoutParams.WRAP_CONTENT;
      param.width = width;
      param.rightMargin = getResources().getDimensionPixelSize(R.dimen.spacing_minor);
      param.leftMargin = getResources().getDimensionPixelSize(R.dimen.spacing_minor);
      param.topMargin = getResources().getDimensionPixelSize(R.dimen.spacing_minor);
      param.setGravity(Gravity.FILL);
      param.columnSpec = GridLayout.spec(c);
      param.rowSpec = GridLayout.spec(r);
      view.setLayoutParams(param);
      inflateView(view,candList.get(i));
      gridLayout.addView(view);
    }
    progressBar.setVisibility(View.GONE);
  }
}
