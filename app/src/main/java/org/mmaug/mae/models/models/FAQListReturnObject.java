package org.mmaug.mae.models.models;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ye Lin Aung on 15/08/06.
 */
public class FAQListReturnObject {
  private List<FAQ> data = new ArrayList<>();
  @SerializedName("_meta") private FaqMeta meta;

  /**
   * @return The data
   */
  public List<FAQ> getData() {
    return data;
  }

  /**
   * @param data The data
   */
  public void setData(List<FAQ> data) {
    this.data = data;
  }

  /**
   * @return The meta
   */
  public FaqMeta getMeta() {
    return meta;
  }

  /**
   * @param meta The meta
   */
  public void setMeta(FaqMeta meta) {
    this.meta = meta;
  }
}
