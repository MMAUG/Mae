package org.mmaug.mae.models.models;

import com.google.gson.annotations.Expose;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yemyatthu on 8/18/15.
 */
public class GeoReturnObject {

  @Expose private List<Geo> data = new ArrayList<Geo>();

  /**
   * @return The data
   */
  public List<Geo> getData() {
    return data;
  }

  /**
   * @param data The data
   */
  public void setData(List<Geo> data) {
    this.data = data;
  }
}