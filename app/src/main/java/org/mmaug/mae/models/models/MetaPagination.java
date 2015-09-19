package org.mmaug.mae.models.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ye Lin Aung on 15/08/03.
 */
public class MetaPagination {

  private int total;
  private int count;
  @SerializedName("per_page") private int perPage;
  @SerializedName("current_page") private int currentPage;
  @SerializedName("total_pages") private int totalPages;
  //private MetaLinks links;

  public MetaPagination() {
  }

  /**
   * @return The total
   */
  public int getTotal() {
    return total;
  }

  /**
   * @param total The total
   */
  public void setTotal(int total) {
    this.total = total;
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

  /**
   * @return The perPage
   */
  public int getPerPage() {
    return perPage;
  }

  /**
   * @param perPage The per_page
   */
  public void setPerPage(int perPage) {
    this.perPage = perPage;
  }

  /**
   * @return The currentPage
   */
  public int getCurrentPage() {
    return currentPage;
  }

  /**
   * @param currentPage The current_page
   */
  public void setCurrentPage(int currentPage) {
    this.currentPage = currentPage;
  }

  /**
   * @return The totalPages
   */
  public int getTotalPages() {
    return totalPages;
  }

  /**
   * @param totalPages The total_pages
   */
  public void setTotalPages(int totalPages) {
    this.totalPages = totalPages;
  }
  //
  ///**
  // * @return The links
  // */
  //public MetaLinks getLinks() {
  //  return links;
  //}
  //
  ///**
  // * @param links The links
  // */
  //public void setLinks(MetaLinks links) {
  //  this.links = links;
  //}
}
