package org.mmaug.mae.models.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TokenMeta {

  @Expose private String status;
  @Expose private Integer count;
  @SerializedName("api_version") @Expose private Integer apiVersion;

  /**
   * @return The status
   */
  public String getStatus() {
    return status;
  }

  /**
   * @param status The status
   */
  public void setStatus(String status) {
    this.status = status;
  }

  /**
   * @return The count
   */
  public Integer getCount() {
    return count;
  }

  /**
   * @param count The count
   */
  public void setCount(Integer count) {
    this.count = count;
  }

  /**
   * @return The apiVersion
   */
  public Integer getApiVersion() {
    return apiVersion;
  }

  /**
   * @param apiVersion The api_version
   */
  public void setApiVersion(Integer apiVersion) {
    this.apiVersion = apiVersion;
  }
}