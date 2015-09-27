package org.mmaug.mae.models;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrentCollection {

  @SerializedName("Speaker") @Expose private String Speaker;
  @SerializedName("Deputy Speaker") @Expose private String DeputySpeaker;
  @SerializedName("Seats") @Expose private Integer Seats;
  @SerializedName("members") @Expose private JsonObject members;

  /**
   * @return The Speaker
   */
  public String getSpeaker() {
    return Speaker;
  }

  /**
   * @param Speaker The Speaker
   */
  public void setSpeaker(String Speaker) {
    this.Speaker = Speaker;
  }

  /**
   * @return The DeputySpeaker
   */
  public String getDeputySpeaker() {
    return DeputySpeaker;
  }

  /**
   * @param DeputySpeaker The Deputy Speaker
   */
  public void setDeputySpeaker(String DeputySpeaker) {
    this.DeputySpeaker = DeputySpeaker;
  }

  /**
   * @return The Seats
   */
  public Integer getSeats() {
    return Seats;
  }

  /**
   * @param Seats The Seats
   */
  public void setSeats(Integer Seats) {
    this.Seats = Seats;
  }

  /**
   * @return The members
   */
  public JsonObject getMembers() {
    return members;
  }

  /**
   * @param members The members
   */
  public void setMembers(JsonObject members) {
    this.members = members;
  }
}