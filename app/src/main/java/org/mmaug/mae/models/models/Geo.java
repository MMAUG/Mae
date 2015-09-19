package org.mmaug.mae.models.models;

import com.google.gson.annotations.Expose;
import java.io.Serializable;

public class Geo implements Serializable {

  @Expose private String id;
  @Expose private String type;
  @Expose private Properties properties;
  @Expose private Geometry geometry;

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
   * @return The properties
   */
  public Properties getProperties() {
    return properties;
  }

  /**
   * @param properties The properties
   */
  public void setProperties(Properties properties) {
    this.properties = properties;
  }

  /**
   * @return The geometry
   */
  public Geometry getGeometry() {
    return geometry;
  }

  /**
   * @param geometry The geometry
   */
  public void setGeometry(Geometry geometry) {
    this.geometry = geometry;
  }
}