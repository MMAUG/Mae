package org.mmaug.mae.models;

import java.io.Serializable;

/**
 * Created by indexer on 10/9/15.
 */
public class CandidateSearchResult implements Serializable {
  private String id;
  private String name;
  private String party_name;
  private String photo_url;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getParty() {
    return party_name;
  }

  public void setParty(String party) {
    this.party_name = party;
  }

  public String toString() {
    return this.id + " - " + this.name + " - " + this.party_name;
  }

  public String getPhoto_url() {
    return photo_url;
  }

  public void setPhoto_url(String photo_url) {
    this.photo_url = photo_url;
  }
}
