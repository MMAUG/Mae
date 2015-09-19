package org.mmaug.mae.models.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TokenReturnObject {

  @SerializedName("_meta") @Expose private TokenMeta Meta;
  @Expose private TokenData data;

  /**
   * @return The Meta
   */
  public TokenMeta getMeta() {
    return Meta;
  }

  /**
   * @param Meta The _meta
   */
  public void setMeta(TokenMeta Meta) {
    this.Meta = Meta;
  }

  /**
   * @return The data
   */
  public TokenData getTokenData() {
    return data;
  }

  /**
   * @param data The data
   */
  public void setTokenData(TokenData data) {
    this.data = data;
  }
}