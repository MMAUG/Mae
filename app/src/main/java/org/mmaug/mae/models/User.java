package org.mmaug.mae.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by poepoe on 14/9/15.
 */
public class User {

  @SerializedName("name") private String name;
  private String nrc;
  @SerializedName("dob") private String dateOfBirth;
  @SerializedName("father_name") private String fatherName;
  private String township;
  @SerializedName("updated_at") private long updatedAt;
  @SerializedName("created_at") private long createdAt;
  @SerializedName("_id") private String id;
  @SerializedName("mps_token") private String mpsToken;
  @SerializedName("access_token") private String accessToken;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(String dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public String getFatherName() {
    return fatherName;
  }

  public void setFatherName(String fatherName) {
    this.fatherName = fatherName;
  }

  public String getTownship() {
    return township;
  }

  public void setTownship(String township) {
    this.township = township;
  }

  public long getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(long updatedAt) {
    this.updatedAt = updatedAt;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getMpsToken() {
    return mpsToken;
  }

  public void setMpsToken(String mpsToken) {
    this.mpsToken = mpsToken;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }
}
