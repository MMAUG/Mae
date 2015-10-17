package org.mmaug.mae.models;

import java.io.Serializable;

/**
 * Created by indexer on 10/9/15.
 */
public class CandidateSearchResult implements Serializable {
  private String id;
  private String name;
  private String party_name;

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
}
