package org.mmaug.mae.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ye Lin Aung on 15/08/03.
 */
public class CandidateListReturnObject {
  private List<Candidate> data = new ArrayList<Candidate>();
  private CandidateMeta meta;

  public CandidateListReturnObject() {
  }

  /**
   * @return The data
   */
  public List<Candidate> getData() {
    return data;
  }

  /**
   * @param data The data
   */
  public void setData(List<Candidate> data) {
    this.data = data;
  }

  /**
   * @return The meta
   */
  public CandidateMeta getMeta() {
    return meta;
  }

  /**
   * @param meta The meta
   */
  public void setMeta(CandidateMeta meta) {
    this.meta = meta;
  }
}
