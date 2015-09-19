package org.mmaug.mae.models.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by yemyatthu on 8/4/15.
 */
public class PartyListReturnObject {
  @SerializedName("_meta") private PartyMetaData meta;

  private List<Party> data;

  public List<Party> getData() {
    return data;
  }

  public void setData(List<Party> data) {
    this.data = data;
  }

  public PartyMetaData getMeta() {
    return meta;
  }

  public void setMeta(PartyMetaData meta) {
    this.meta = meta;
  }
}
