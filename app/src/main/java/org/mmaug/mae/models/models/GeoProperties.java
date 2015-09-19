package org.mmaug.mae.models.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GeoProperties {

  @Expose private Integer OBJECTID;
  @Expose private String ST;
  @SerializedName("ST_PCODE") @Expose private String STPCODE;
  @Expose private String DT;
  @SerializedName("DT_PCODE") @Expose private String DTPCODE;
  @SerializedName("Shape_Leng") @Expose private Double ShapeLeng;
  @SerializedName("Shape_Area") @Expose private Double ShapeArea;
  @SerializedName("DT_Mya") @Expose private String DTMya;

  /**
   * @return The OBJECTID
   */
  public Integer getOBJECTID() {
    return OBJECTID;
  }

  /**
   * @param OBJECTID The OBJECTID
   */
  public void setOBJECTID(Integer OBJECTID) {
    this.OBJECTID = OBJECTID;
  }

  /**
   * @return The ST
   */
  public String getST() {
    return ST;
  }

  /**
   * @param ST The ST
   */
  public void setST(String ST) {
    this.ST = ST;
  }

  /**
   * @return The STPCODE
   */
  public String getSTPCODE() {
    return STPCODE;
  }

  /**
   * @param STPCODE The ST_PCODE
   */
  public void setSTPCODE(String STPCODE) {
    this.STPCODE = STPCODE;
  }

  /**
   * @return The DT
   */
  public String getDT() {
    return DT;
  }

  /**
   * @param DT The DT
   */
  public void setDT(String DT) {
    this.DT = DT;
  }

  /**
   * @return The DTPCODE
   */
  public String getDTPCODE() {
    return DTPCODE;
  }

  /**
   * @param DTPCODE The DT_PCODE
   */
  public void setDTPCODE(String DTPCODE) {
    this.DTPCODE = DTPCODE;
  }

  /**
   * @return The ShapeLeng
   */
  public Double getShapeLeng() {
    return ShapeLeng;
  }

  /**
   * @param ShapeLeng The Shape_Leng
   */
  public void setShapeLeng(Double ShapeLeng) {
    this.ShapeLeng = ShapeLeng;
  }

  /**
   * @return The ShapeArea
   */
  public Double getShapeArea() {
    return ShapeArea;
  }

  /**
   * @param ShapeArea The Shape_Area
   */
  public void setShapeArea(Double ShapeArea) {
    this.ShapeArea = ShapeArea;
  }

  /**
   * @return The DTMya
   */
  public String getDTMya() {
    return DTMya;
  }

  /**
   * @param DTMya The DT_Mya
   */
  public void setDTMya(String DTMya) {
    this.DTMya = DTMya;
  }
}