package org.mmaug.mae.models;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Party implements Serializable {

  @SerializedName("_id") private String partyId;
  @SerializedName("party_name") private String PartyName;
  @SerializedName("party_name_english") private String PartyNameEnglish;
  @SerializedName("establishment_date") private String EstablishmentDate;
  @SerializedName("member_count") private String MemberCount;
  @SerializedName("leadership") private List<String> Leadership = new ArrayList<>();
  @SerializedName("establishment_approval_date") private String EstablishmentApprovalDate;
  @SerializedName("registration_application_date") private String RegistrationApplicationDate;
  @SerializedName("registration_approval_date") private String RegistrationApprovalDate;
  @SerializedName("approved_party_number") private String ApprovedPartyNumber;
  @SerializedName("party_flag") private String PartyFlag;
  @SerializedName("party_seal") private String PartySeal;
  @SerializedName("chairman") private List<String> Chairman = new ArrayList<>();
  @SerializedName("region") private String Region;
  @SerializedName("headquarters") private String Headquarters;
  @SerializedName("contact") private List<String> Contact = new ArrayList<>();
  @SerializedName("policy") private String Policy;
  private String establishmentDateString;

  public String getPartyId() {
    return partyId;
  }

  public void setPartyId(String partyId) {
    this.partyId = partyId;
  }

  /**
   * @return The PartyName
   */
  public String getPartyName() {
    return PartyName;
  }

  /**
   * @param PartyName The PartyName
   */
  public void setPartyName(String PartyName) {
    this.PartyName = PartyName;
  }

  /**
   * @return The PartyNameEnglish
   */
  public String getPartyNameEnglish() {
    return PartyNameEnglish;
  }

  /**
   * @param PartyNameEnglish The PartyNameEnglish
   */
  public void setPartyNameEnglish(String PartyNameEnglish) {
    this.PartyNameEnglish = PartyNameEnglish;
  }

  /**
   * @return The EstablishmentDate
   */
  public String getEstablishmentDate() {
    return EstablishmentDate;
  }

  /**
   * @param EstablishmentDate The EstablishmentDate
   */
  public void setEstablishmentDate(String EstablishmentDate) {
    this.EstablishmentDate = EstablishmentDate;
  }

  /**
   * @return The MemberCount
   */
  public String getMemberCount() {
    return MemberCount;
  }

  /**
   * @param MemberCount The MemberCount
   */
  public void setMemberCount(String MemberCount) {
    this.MemberCount = MemberCount;
  }

  /**
   * @return The Leadership
   */
  public List<String> getLeadership() {
    return Leadership;
  }

  /**
   * @param Leadership The Leadership
   */
  public void setLeadership(List<String> Leadership) {
    this.Leadership = Leadership;
  }

  /**
   * @return The EstablishmentApprovalDate
   */
  public String getEstablishmentApprovalDate() {
    return EstablishmentApprovalDate;
  }

  /**
   * @param EstablishmentApprovalDate The EstablishmentApprovalDate
   */
  public void setEstablishmentApprovalDate(String EstablishmentApprovalDate) {
    this.EstablishmentApprovalDate = EstablishmentApprovalDate;
  }

  /**
   * @return The RegistrationApplicationDate
   */
  public String getRegistrationApplicationDate() {
    return RegistrationApplicationDate;
  }

  /**
   * @param RegistrationApplicationDate The RegistrationApplicationDate
   */
  public void setRegistrationApplicationDate(String RegistrationApplicationDate) {
    this.RegistrationApplicationDate = RegistrationApplicationDate;
  }

  /**
   * @return The RegistrationApprovalDate
   */
  public String getRegistrationApprovalDate() {
    return RegistrationApprovalDate;
  }

  /**
   * @param RegistrationApprovalDate The RegistrationApprovalDate
   */
  public void setRegistrationApprovalDate(String RegistrationApprovalDate) {
    this.RegistrationApprovalDate = RegistrationApprovalDate;
  }

  /**
   * @return The ApprovedPartyNumber
   */
  public String getApprovedPartyNumber() {
    return ApprovedPartyNumber;
  }

  /**
   * @param ApprovedPartyNumber The ApprovedPartyNumber
   */
  public void setApprovedPartyNumber(String ApprovedPartyNumber) {
    this.ApprovedPartyNumber = ApprovedPartyNumber;
  }

  /**
   * @return The PartyFlag
   */
  public String getPartyFlag() {
    return PartyFlag;
  }

  /**
   * @param PartyFlag The PartyFlag
   */
  public void setPartyFlag(String PartyFlag) {
    this.PartyFlag = PartyFlag;
  }

  /**
   * @return The PartySeal
   */
  public String getPartySeal() {
    return PartySeal;
  }

  /**
   * @param PartySeal The PartySeal
   */
  public void setPartySeal(String PartySeal) {
    this.PartySeal = PartySeal;
  }

  /**
   * @return The Chairman
   */
  public List<String> getChairman() {
    return Chairman;
  }

  /**
   * @param Chairman The Chairman
   */
  public void setChairman(List<String> Chairman) {
    this.Chairman = Chairman;
  }

  /**
   * @return The Region
   */
  public String getRegion() {
    return Region;
  }

  /**
   * @param Region The Region
   */

  public void setRegion(String Region) {
    this.Region = Region;
  }

  /**
   * @return The Contact
   */
  public List<String> getContact() {
    return Contact;
  }

  /**
   * @param contact The Contact
   */
  public void setContact(List<String> contact) {
    Contact = contact;
  }

  /**
   * @return The Headquarters
   */
  public String getHeadquarters() {
    return Headquarters;
  }

  /**
   * @param headquarters The Headquarters
   */
  public void setHeadquarters(String headquarters) {
    Headquarters = headquarters;
  }

  /**
   * @return The Policy
   */
  public String getPolicy() {
    return Policy;
  }

  /**
   * @param policy The Policy
   */
  public void setPolicy(String policy) {
    Policy = policy;
  }

  public String getEstablishmentDateString() {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy", Locale.getDefault());
    if(getEstablishmentDate()!=null){
      Calendar calender = Calendar.getInstance();
      calender.setTimeInMillis(Long.parseLong(getEstablishmentDate()));
      Date date = calender.getTime();
      return dateFormat.format(date);
    }else{
      return "-";
    }
  }

  public void setEstablishmentDateString(String establishmentDateString) {
    this.establishmentDateString = establishmentDateString;
  }
}
