package com.toptal.timemgmt.payloads;

import java.util.Date;

public class WorkLogResponse {

  private Long id;
  private String notes;
  private Integer workingTime;
  private Date workingDate;
  private Long userId;
  private boolean isRed = false;

  public WorkLogResponse(Long id, String notes, Date workingDate, Integer workingTime, Long userId) {
    this.id = id;
    this.notes = notes;
    this.workingTime = workingTime;
    this.workingDate = workingDate;
    this.userId = userId;
  }

  public WorkLogResponse() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public Integer getWorkingTime() {
    return workingTime;
  }

  public void setWorkingTime(Integer workingTime) {
    this.workingTime = workingTime;
  }

  public Date getWorkingDate() {
    return workingDate;
  }

  public void setWorkingDate(Date workingDate) {
    this.workingDate = workingDate;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public boolean getRed() {
    return isRed;
  }

  public void setRed(boolean red) {
    isRed = red;
  }
}
