/*
package com.toptal.timemgmt.controller;

import com.toptal.timemgmt.exception.ResourceNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {

  @Autowired
  private EmployeeRepository employeeRepository;

  @GetMapping("/employees")
  public List<Employee> getAllEmployees() {
    return employeeRepository.findAll();
  }

  @PostMapping("/add-employee")
  public void addEmployee(@RequestBody Employee employee) {
    employeeRepository.save(employee);
  }

  @GetMapping("/employee/{id}")
  public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
    Employee employee = employeeRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Employee does not exist"));
    return ResponseEntity.ok(employee);
  }

  @PutMapping("/employee/{id}")
  public ResponseEntity<Employee> updateEmployee(@PathVariable Long id,
      @RequestBody Employee updatedEmployeeDetails) {
    Employee existingEmployee = employeeRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Employee does not exist"));
    existingEmployee.setFirstName(updatedEmployeeDetails.getFirstName());
    existingEmployee.setLastName(updatedEmployeeDetails.getLastName());
    existingEmployee.setEmailId(updatedEmployeeDetails.getEmailId());
    Employee updatedEmployee = employeeRepository.save(existingEmployee);
    return ResponseEntity.ok(updatedEmployee);
  }

  @DeleteMapping("/employee/{id}")
  public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id) {
    Employee employeeToBeDeleted = employeeRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Employee does not exist"));
    employeeRepository.delete(employeeToBeDeleted);
    Map<String, Boolean> response = new HashMap<>();
    response.put("deleted", Boolean.TRUE);
    return ResponseEntity.ok(response);
  }


}
*/
