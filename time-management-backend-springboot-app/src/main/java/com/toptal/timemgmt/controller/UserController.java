package com.toptal.timemgmt.controller;


import com.toptal.timemgmt.exception.ResourceNotFoundException;
import com.toptal.timemgmt.model.User;
import com.toptal.timemgmt.payloads.ApiResponse;
import com.toptal.timemgmt.payloads.UserDetailsResponse;
import com.toptal.timemgmt.payloads.UserIdentityAvailability;
import com.toptal.timemgmt.payloads.UserSummary;
import com.toptal.timemgmt.repository.UserRepository;
import com.toptal.timemgmt.security.CurrentUser;
import com.toptal.timemgmt.security.UserPrincipal;
import com.toptal.timemgmt.service.ExcelService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/")
public class UserController {

  @Autowired
  private UserRepository userRepository;

  @PreAuthorize("hasRole('ROLE_ADMIN') or #user.id == authentication.principal.id")
  @PutMapping("/updateUserSetting")
  public ResponseEntity<ApiResponse> updateUserSetting(@RequestBody User user) {
    User existingUser = userRepository.findById(user.getId())
        .orElseThrow(() -> new ResourceNotFoundException("", "", ""));
    existingUser.setPreferredWorkingHourPerDay(user.getPreferredWorkingHourPerDay());
    existingUser.setEmail(user.getEmail());
    existingUser.setName(user.getName());
    existingUser.setUsername(user.getUsername());
    userRepository.save(existingUser);
    return ResponseEntity.ok(new ApiResponse(Boolean.TRUE, "user updated successfully"));
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
  @GetMapping("/getUsers")
  public ResponseEntity<List<UserDetailsResponse>> getAllUsers() {
    List<User> result = userRepository.findAll();
    List<UserDetailsResponse> response = new ArrayList<>();
    result.forEach(user -> {
      UserDetailsResponse userDetailsResponse = new UserDetailsResponse();
      userDetailsResponse.setId(user.getId());
      userDetailsResponse.setEmail(user.getEmail());
      userDetailsResponse.setName(user.getName());
      userDetailsResponse.setUsername(user.getUsername());
      userDetailsResponse.setPreferredWorkingHoursPerDay(user.getPreferredWorkingHourPerDay());

      assignRole(userDetailsResponse, user);

      response.add(userDetailsResponse);
    });
    return ResponseEntity.ok(response);
  }

  //TODO : Change the design to have a single role.
  private void assignRole(UserDetailsResponse userDetailsResponse, User user) {
    user.getRoles().forEach(role -> userDetailsResponse.setRole(role.getName().name()));
  }

  private void assignRole(UserSummary userSummary, User user) {
    user.getRoles().forEach(role -> userSummary.setRole(role.getName().name()));
  }

  @GetMapping("/me")
  //@PreAuthorize("hasRole('USER')")
  public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
    User user = userRepository.findById(currentUser.getId())
        .orElseThrow(() -> new ResourceNotFoundException("", "", ""));

    UserSummary userSummary = new UserSummary(user.getId(), user.getUsername(), user.getName(),
        user.getEmail(), user.getPreferredWorkingHourPerDay());

    assignRole(userSummary, user);

    return userSummary;
  }

  @GetMapping("/checkUsernameAvailability")
  public UserIdentityAvailability checkUsernameAvailability(@RequestParam(value = "username") String username) {
    Boolean isAvailable = !userRepository.existsByUsername(username);
    return new UserIdentityAvailability(isAvailable);
  }

  @GetMapping("/checkEmailAvailability")
  public UserIdentityAvailability checkEmailAvailability(@RequestParam(value = "email") String email) {
    Boolean isAvailable = !userRepository.existsByEmail(email);
    return new UserIdentityAvailability(isAvailable);
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Map<String, Boolean>> deleteUser(@PathVariable Long id) {
    User userToBeDeleted = userRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("User does not exists", "", ""));
    userRepository.delete(userToBeDeleted);
    Map<String, Boolean> response = new HashMap<>();
    response.put("deleted", Boolean.TRUE);
    return ResponseEntity.ok(response);
  }

}
