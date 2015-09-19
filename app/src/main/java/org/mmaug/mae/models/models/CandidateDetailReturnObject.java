package org.mmaug.mae.models.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by yemyatthu on 8/12/15.
 */
public class CandidateDetailReturnObject {

  @SerializedName("data") private Candidate mCandidate;

  public CandidateDetailReturnObject() {
  }

  /**
   * @return candidate
   */
  public Candidate getCandidate() {
    return mCandidate;
  }

  /**
   *
   * @param candidate
   */
  public void setCandidate(Candidate candidate) {
    mCandidate = candidate;
  }
}
