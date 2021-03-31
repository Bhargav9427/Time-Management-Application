package com.toptal.timemgmt.repository;

import com.toptal.timemgmt.model.Work;
import com.toptal.timemgmt.payloads.WorkLogResponse;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface WorkRepository extends JpaRepository<Work, Long> {

  @Query("select new com.toptal.timemgmt.payloads.WorkLogResponse(w.id, w.notes, w.workingDate, w.workingHours, w.user.id) from Work w where w.user.id = :user_id")
  List<WorkLogResponse> findByUserId(@Param("user_id") Long UserID);
}
