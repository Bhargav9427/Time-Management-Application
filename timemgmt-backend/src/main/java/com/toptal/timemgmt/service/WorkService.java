package com.toptal.timemgmt.service;

import com.toptal.timemgmt.exception.ResourceNotFoundException;
import com.toptal.timemgmt.model.Work;
import com.toptal.timemgmt.payloads.WorkLogRequest;
import com.toptal.timemgmt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkService {

  @Autowired
  private UserRepository userRepository;

  public Work getWorkObject(WorkLogRequest workLogRequest) {
    Work work = new Work();
    work.setNotes(workLogRequest.getNotes());
    work.setWorkId(workLogRequest.getWorkId());
    work.setWorkingDate(workLogRequest.getWorkingDate());
    work.setWorkingHours(workLogRequest.getWorkingHours());
    work.setUser(userRepository.findById(workLogRequest.getUserId()).orElseThrow(
        () -> new ResourceNotFoundException(String.valueOf(workLogRequest.getUserId()), "", "")));

    return work;
  }

}
