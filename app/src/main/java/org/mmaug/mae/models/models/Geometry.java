package org.mmaug.mae.models.models;

import com.google.gson.JsonArray;
import com.google.gson.annotations.Expose;
import java.io.Serializable;

public class Geometry implements Serializable {

  @Expose private String type;
  @Expose private JsonArray coordinates = new JsonArray();

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
   * @return The coordinates
   */
  public JsonArray getCoordinates() {
    return coordinates;
  }

  /**
   * @param coordinates The coordinates
   */
  public void setCoordinates(JsonArray coordinates) {
    this.coordinates = coordinates;
  }
}