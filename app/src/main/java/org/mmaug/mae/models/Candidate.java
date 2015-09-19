package org.mmaug.mae.models;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ye Lin Aung on 15/08/03.
 */
public class Candidate implements Serializable {

  private String id;
  private String name;
  private String legislature;
  @SerializedName("national_id") private String nationalId;
  private int birthdate;
  private List<String> education = new ArrayList<String>();
  private List<String> occupation = new ArrayList<String>();
  @SerializedName("nationality_religion") private String nationalityReligion;
  private Residency residency;
  private Constituency constituency;
  @SerializedName("party_id") private String partyId;
  private Mother mother;
  private Father father;
  private String gender;
  @SerializedName("photo_url") private String photoUrl;

  public Candidate() {
  }

  /**
   * @return The id
   */
  public String getId() {
    return id;
  }

  /**
   * @param id The id
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * @return The name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name The name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return The legislature
   */
  public String getLegislature() {
    return legislature;
  }

  /**
   * @param legislature The legislature
   */
  public void setLegislature(String legislature) {
    this.legislature = legislature;
  }

  /**
   * @return The nationalId
   */
  public String getNationalId() {
    return nationalId;
  }

  /**
   * @param nationalId The national_id
   */
  public void setNationalId(String nationalId) {
    this.nationalId = nationalId;
  }

  /**
   * @return The birthdate
   */
  public int getBirthdate() {
    return birthdate;
  }

  /**
   * @param birthdate The birthdate
   */
  public void setBirthdate(int birthdate) {
    this.birthdate = birthdate;
  }

  /**
   * @return The education
   */
  public List<String> getEducation() {
    return education;
  }

  /**
   * @param education The education
   */
  public void setEducation(List<String> education) {
    this.education = education;
  }

  /**
   * @return The occupation
   */
  public List<String> getOccupation() {
    return occupation;
  }

  /**
   * @param occupation The occupation
   */
  public void setOccupation(List<String> occupation) {
    this.occupation = occupation;
  }

  /**
   * @return The nationalityReligion
   */
  public String getNationalityReligion() {
    return nationalityReligion;
  }

  /**
   * @param nationalityReligion The nationality_religion
   */
  public void setNationalityReligion(String nationalityReligion) {
    this.nationalityReligion = nationalityReligion;
  }

  /**
   * @return The residency
   */
  public Residency getResidency() {
    return residency;
  }

  /**
   * @param residency The residency
   */
  public void setResidency(Residency residency) {
    this.residency = residency;
  }

  /**
   * @return The constituency
   */
  public Constituency getConstituency() {
    return constituency;
  }

  /**
   * @param constituency The constituency
   */
  public void setConstituency(Constituency constituency) {
    this.constituency = constituency;
  }

  /**
   * @return The partyId
   */
  public String getPartyId() {
    return partyId;
  }

  /**
   * @param partyId The party_id
   */
  public void setPartyId(String partyId) {
    this.partyId = partyId;
  }

  /**
   * @return The mother
   */
  public Mother getMother() {
    return mother;
  }

  /**
   * @param mother The mother
   */
  public void setMother(Mother mother) {
    this.mother = mother;
  }

  /**
   * @return The father
   */
  public Father getFather() {
    return father;
  }

  /**
   * @param father The father
   */
  public void setFather(Father father) {
    this.father = father;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getPhotoUrl() {
    return photoUrl;
  }

  public void setPhotoUrl(String photoUrl) {
    this.photoUrl = photoUrl;
  }
}
