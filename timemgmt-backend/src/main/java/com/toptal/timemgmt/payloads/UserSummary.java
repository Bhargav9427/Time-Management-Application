package com.toptal.timemgmt.payloads;

public class UserSummary {

  private Long id;
  private String username;
  private String name;
  private String email;
  private Integer preferredWorkingHoursPerDay;
  private String role;

  public UserSummary(Long id, String username, String name, String email, Integer preferredWorkingHoursPerDay) {
    this.id = id;
    this.username = username;
    this.name = name;
    this.email = email;
    this.preferredWorkingHoursPerDay = preferredWorkingHoursPerDay;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Integer getPreferredWorkingHoursPerDay() {
    return preferredWorkingHoursPerDay;
  }

  public void setPreferredWorkingHoursPerDay(Integer preferredWorkingHoursPerDay) {
    this.preferredWorkingHoursPerDay = preferredWorkingHoursPerDay;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }
}
