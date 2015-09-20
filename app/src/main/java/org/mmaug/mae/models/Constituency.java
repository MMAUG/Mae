package org.mmaug.mae.models;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/**
 * Created by Ye Lin Aung on 15/08/03.
 */
public class Constituency implements Serializable {
  private String name;
  private String number;
  @SerializedName("ST_PCODE") private String STPCODE;
  @SerializedName("DT_PCODE") private Object DTPCODE;
  @SerializedName("TS_PCODE") private Object TSPCODE;
  @SerializedName("AM_PCODE") private String AMPCODE;
  @SerializedName("parent") private String parent;

  public Constituency() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public String getSTPCODE() {
    return STPCODE;
  }

  public void setSTPCODE(String STPCODE) {
    this.STPCODE = STPCODE;
  }

  public Object getDTPCODE() {
    return DTPCODE;
  }

  public void setDTPCODE(Object DTPCODE) {
    this.DTPCODE = DTPCODE;
  }

  public Object getTSPCODE() {
    return TSPCODE;
  }

  public void setTSPCODE(Object TSPCODE) {
    this.TSPCODE = TSPCODE;
  }

  public String getAMPCODE() {
    return AMPCODE;
  }

  public void setAMPCODE(String AMPCODE) {
    this.AMPCODE = AMPCODE;
  }

  public String getParent() {
    return parent;
  }

  public void setParent(String parent) {
    this.parent = parent;
  }
}
