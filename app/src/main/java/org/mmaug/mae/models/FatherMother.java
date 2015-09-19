package org.mmaug.mae.models;

import java.io.Serializable;

/**
 * Created by Ye Lin Aung on 15/08/03.
 */
public class FatherMother implements Serializable {
  private String name;
  private String religion;

  public FatherMother() {
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
   * @return The religion
   */
  public String getReligion() {
    return religion;
  }

  /**
   * @param religion The religion
   */
  public void setReligion(String religion) {
    this.religion = religion;
  }
}
