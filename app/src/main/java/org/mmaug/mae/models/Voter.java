package org.mmaug.mae.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by poepoe on 12/9/15.
 */
public class Voter {
  @SerializedName("dateofbirth") private String dateOfBirth;
  private String village;
  @SerializedName("father_name") private String fatherName;
  private String nrcno;
  private String state;
  @SerializedName("voter_name") private String voterName;
  @SerializedName("dateofbirth_num") private String dateofbirthNum;
  @SerializedName("mother_name") private String motherName;
  private String township;
  private String district;

  public String getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(String dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public String getVillage() {
    return village;
  }

  public void setVillage(String village) {
    this.village = village;
  }

  public String getFatherName() {
    return fatherName;
  }

  public void setFatherName(String fatherName) {
    this.fatherName = fatherName;
  }

  public String getNrcno() {
    return nrcno;
  }

  public void setNrcno(String nrcno) {
    this.nrcno = nrcno;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getVoterName() {
    return voterName;
  }

  public void setVoterName(String voterName) {
    this.voterName = voterName;
  }

  public String getDateofbirthNum() {
    return dateofbirthNum;
  }

  public void setDateofbirthNum(String dateofbirthNum) {
    this.dateofbirthNum = dateofbirthNum;
  }

  public String getMotherName() {
    return motherName;
  }

  public void setMotherName(String motherName) {
    this.motherName = motherName;
  }

  public String getTownship() {
    return township;
  }

  public void setTownship(String township) {
    this.township = township;
  }

  public String getDistrict() {
    return district;
  }

  public void setDistrict(String district) {
    this.district = district;
  }
}
