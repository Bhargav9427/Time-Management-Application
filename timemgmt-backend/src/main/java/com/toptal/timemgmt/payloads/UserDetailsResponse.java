package com.toptal.timemgmt.payloads;

public class UserDetailsResponse {

  private Long id;
  private String name;
  private String username;
  private String email;
  private String role;
  private Integer preferredWorkingHoursPerDay;
  public UserDetailsResponse() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public Integer getPreferredWorkingHoursPerDay() {
    return preferredWorkingHoursPerDay;
  }

  public void setPreferredWorkingHoursPerDay(Integer preferredWorkingHoursPerDay) {
    this.preferredWorkingHoursPerDay = preferredWorkingHoursPerDay;
  }
}
