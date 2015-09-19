package org.mmaug.mae.models;

/**
 * Created by indexer on 9/19/15.
 */
public class HomeMenuItem {
  private int drawable_resouce;
  private String title;
  private boolean isValid;

  public int getDrawable_resouce() {
    return drawable_resouce;
  }

  public void setDrawable_resouce(int drawable_resouce) {
    this.drawable_resouce = drawable_resouce;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public boolean isValid() {
    return isValid;
  }

  public void setIsValid(boolean isValid) {
    this.isValid = isValid;
  }
}
