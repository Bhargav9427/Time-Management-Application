package com.toptal.timemgmt.controller;

import com.toptal.timemgmt.exception.ResourceNotFoundException;
import com.toptal.timemgmt.model.RoleName;
import com.toptal.timemgmt.model.User;
import com.toptal.timemgmt.model.Work;
import com.toptal.timemgmt.payloads.ApiResponse;
import com.toptal.timemgmt.payloads.WorkLogRequest;
import com.toptal.timemgmt.payloads.WorkLogResponse;
import com.toptal.timemgmt.repository.UserRepository;
import com.toptal.timemgmt.repository.WorkRepository;
import com.toptal.timemgmt.security.CurrentUser;
import com.toptal.timemgmt.security.UserPrincipal;
import com.toptal.timemgmt.service.ExcelService;
import com.toptal.timemgmt.service.WorkService;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/work/")
public class WorkLogController {

  @Autowired
  private WorkRepository workRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private WorkService workService;

  @Autowired
  private ExcelService excelService;

  //@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
  @GetMapping("/getWorkLog/{id}")
  public ResponseEntity<List<WorkLogResponse>> getAllWorkLogForUser(@PathVariable Long id,
      @CurrentUser
          UserPrincipal currentUser) {

    List<WorkLogResponse> data = new ArrayList<>();
    //If the logged in user is a admin then fetch all the work log data irrespective of any user
    if (currentUser.getAuthorities()
        .contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.name()))) {
      for (Work log : workRepository.findAll()) {
        WorkLogResponse response = new WorkLogResponse(log.getWorkId(), log.getNotes(),
            log.getWorkingDate(),
            log.getWorkingHours(), log.getUser().getId());
        data.add(response);
      }
    } else {
      data = workRepository.findByUserId(id);
    }

    Map<Date, Integer> dateHourMap = new HashMap<>();
    List<User> users = userRepository.findAll();
    for (WorkLogResponse log : data) {
      dateHourMap.put(log.getWorkingDate(),
          dateHourMap.getOrDefault(log.getWorkingDate(), 0) + log.getWorkingTime());
    }
    //for each user get his preferred working hours.
    List<WorkLogResponse> finalData = data;
    users.forEach(user -> {
      //iterate over worklist and update the color flag.
      int preferredWorkingHours = user
          .getPreferredWorkingHourPerDay() == null ? 0 : user.getPreferredWorkingHourPerDay();
      for (WorkLogResponse workItem : finalData) {
        if (workItem.getUserId().equals(user.getId())
            && dateHourMap.getOrDefault(workItem.getWorkingDate(), 0) < preferredWorkingHours) {
          workItem.setRed(true);
        }
      }
    });

    return ResponseEntity.ok(data);
  }


  @PostMapping("/addWorkLog")
  public ResponseEntity<ApiResponse> addWorkLog(@RequestBody WorkLogRequest workLogRequest) {

    workRepository.save(workService.getWorkObject(workLogRequest));
    return ResponseEntity.ok(new ApiResponse(Boolean.TRUE, "Work log has been added successfully"));
  }

  @GetMapping("/getspecificWorklog/{workId}")
  public ResponseEntity<WorkLogResponse> getSpecificWorklog(@PathVariable Long workId) {
    Work singleWork = workRepository.findById(workId)
        .orElseThrow(() -> new ResourceNotFoundException("", "", ""));
    WorkLogResponse response = new WorkLogResponse(singleWork.getWorkId(), singleWork.getNotes(),
        singleWork.getWorkingDate(), singleWork.getWorkingHours(), singleWork.getUser().getId());
    return ResponseEntity.ok(response);
  }

  @PutMapping("/updateWorkLog")
  public ResponseEntity<ApiResponse> updateWorkLog(@RequestBody WorkLogRequest workLogRequest) {
    workRepository.save(workService.getWorkObject(workLogRequest));
    return ResponseEntity
        .ok(new ApiResponse(Boolean.TRUE, "Work log has been updated successfully"));
  }

  @DeleteMapping("/deleteWorklog/{id}")
  public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id) {
    Work workLogToBeDeleted = workRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Work Log does not exists", "", ""));
    workRepository.delete(workLogToBeDeleted);
    Map<String, Boolean> response = new HashMap<>();
    response.put("deleted", Boolean.TRUE);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/export")
  public ResponseEntity<Resource> getFile(@RequestBody List<WorkLogResponse> workLogData) {
    String filename = "filteredData.xlsx";


    InputStreamResource file = new InputStreamResource(excelService.formData(workLogData));
    HttpHeaders header = new HttpHeaders();
    header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename);
    header.add("Cache-Control", "no-cache, no-store, must-revalidate");
    header.add("Pragma", "no-cache");
    header.add("Expires", "0");
    return ResponseEntity.ok()
        .headers(header)
        .contentType(MediaType.APPLICATION_OCTET_STREAM)
        .body(file);
  }

}
