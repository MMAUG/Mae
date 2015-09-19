package org.mmaug.mae.models.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ye Lin Aung on 15/08/06.
 */
public class FAQ implements Serializable {
  private String id;
  private String question;
  private String answer;
  private String type;
  private String basis;
  private List<String> sections = new ArrayList<>();
  private String url;

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
   * @return The question
   */
  public String getQuestion() {
    return question;
  }

  /**
   * @param question The question
   */
  public void setQuestion(String question) {
    this.question = question;
  }

  /**
   * @return The answer
   */
  public String getAnswer() {
    return answer;
  }

  /**
   * @param answer The answer
   */
  public void setAnswer(String answer) {
    this.answer = answer;
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
   * @return The basis
   */
  public String getBasis() {
    return basis;
  }

  /**
   * @param basis The basis
   */
  public void setBasis(String basis) {
    this.basis = basis;
  }

  /**
   * @return The sections
   */
  public List<String> getSections() {
    return sections;
  }

  /**
   * @param sections The sections
   */
  public void setSections(List<String> sections) {
    this.sections = sections;
  }

  /**
   * @return The url
   */
  public String getUrl() {
    return url;
  }

  /**
   * @param url The url
   */
  public void setUrl(String url) {
    this.url = url;
  }
}
