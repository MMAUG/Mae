package org.mmaug.mae.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by yemyatthu on 8/12/15.
 */
public class FAQDetailReturnObject {
  @SerializedName("data") private FAQ mFAQ;

  public FAQDetailReturnObject() {

  }

  /**
   * @return FAQ
   */
  public FAQ getFAQ() {
    return mFAQ;
  }

  /**
   *
   * @param FAQ
   */
  public void setFAQ(FAQ FAQ) {
    mFAQ = FAQ;
  }
}
