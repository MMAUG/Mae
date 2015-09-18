package org.mmaug.mae.models;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.util.List;

/**
 * Created by yemyatthu on 9/18/15.
 */
public class GeoReturnObject implements Serializable {
  @Expose private List<Geo> data;

  public List<Geo> getData() {
    return data;
  }

  public void setData(List<Geo> data) {
    this.data = data;
  }
}
