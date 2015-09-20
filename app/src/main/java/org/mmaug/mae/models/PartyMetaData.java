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
  private int total_pages;

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

  public int getTotal_pages() {
    return total_pages;
  }

  public void setTotal_pages(int total_pages) {
    this.total_pages = total_pages;
  }
}

