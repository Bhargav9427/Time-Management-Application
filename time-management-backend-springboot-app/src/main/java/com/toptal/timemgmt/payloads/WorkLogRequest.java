package com.toptal.timemgmt.payloads;

import java.util.Date;

public class WorkLogRequest {

  private Long userId;

  private Long workId;

  private String notes;

  private Date workingDate;

  private Integer workingHours;

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Long getWorkId() {
    return workId;
  }

  public void setWorkId(Long workId) {
    this.workId = workId;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public Date getWorkingDate() {
    return workingDate;
  }

  public void setWorkingDate(Date workingDate) {
    this.workingDate = workingDate;
  }

  public Integer getWorkingHours() {
    return workingHours;
  }

  public void setWorkingHours(Integer workingHours) {
    this.workingHours = workingHours;
  }
}
