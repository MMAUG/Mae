package org.mmaug.mae.models.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by maepaesoh on 8/4/15.
 */
public class Error {
  @SerializedName("errors") private Errors error;

  public Errors getError() {
    return error;
  }

  public void setError(Errors error) {
    this.error = error;
  }

  public class Errors {
    private String message;
    private String type;

    public String getMessage() {
      return message;
    }

    public void setMessage(String message) {
      this.message = message;
    }

    public String getType() {
      return type;
    }

    public void setType(String type) {
      this.type = type;
    }
  }
}
