package org.mmaug.mae.models;

import java.io.Serializable;

/**
 * Created by Ye Lin Aung on 15/08/03.
 */
public class Constituency implements Serializable {
  private String type;
  private String name;
  private int count;

  public Constituency() {
  }

  /**
   * @return The type
   */
  public String getType() {
    return type;
  }

  /**
   * @param type The type
   */
  public void setType(String type) {
    this.type = type;
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
   * @return The count
   */
  public int getCount() {
    return count;
  }

  /**
   * @param count The count
   */
  public void setCount(int count) {
    this.count = count;
  }
}
