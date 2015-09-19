package org.mmaug.mae.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by yemyatthu on 8/4/15.
 */
public class PartyMetaData {
  private String status;
  private int count;
  @SerializedName("api_version") private int apiVersion;
  private boolean unicode;
  private String format;

  public boolean isUnicode() {
    return unicode;
  }

  public void setUnicode(boolean unicode) {
    this.unicode = unicode;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getFormat() {
    return format;
  }

  public void setFormat(String format) {
    this.format = format;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public int getApiVersion() {
    return apiVersion;
  }

  public void setApiVersion(int apiVersion) {
    this.apiVersion = apiVersion;
  }
}

