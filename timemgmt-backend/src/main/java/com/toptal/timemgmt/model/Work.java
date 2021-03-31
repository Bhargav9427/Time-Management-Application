package com.toptal.timemgmt.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.toptal.timemgmt.model.audit.DateAudit;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;


@Entity
@Table(name = "work")
public class Work extends DateAudit {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long workId;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  private String notes;

  @Temporal(TemporalType.DATE)
  private Date workingDate;

  private Integer workingHours;

  @JsonInclude()
  @Transient
  private Long userId;

  public Work() {
  }

  public Long getWorkId() {
    return workId;
  }

  public void setWorkId(Long workId) {
    this.workId = workId;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
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

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  @Override
  public String toString() {
    return "Work{" +
        "workId=" + workId +
        ", user=" + user +
        ", notes='" + notes + '\'' +
        ", workingDate=" + workingDate +
        ", workingHours=" + workingHours +
        '}';
  }
}
