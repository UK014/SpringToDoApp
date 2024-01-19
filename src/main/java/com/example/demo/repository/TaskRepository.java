package com.example.demo.repository;

import com.example.demo.list.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task,Long> {
    @Modifying
    @Query("UPDATE Task t SET t.task = :task, t.priority = :priority, t.time = :time, t.status = :status WHERE t.id = :id")
    int updateTask(@Param("id") Long id, @Param("task") String task,
                   @Param("priority") String priority, @Param("time")LocalDateTime time,
                   @Param("status") String status);

    Optional<Task> findById(Long id);

    @Modifying
    @Query("UPDATE Task t SET t.status = :status WHERE t.id = :id")
    int updateStatus(@Param("id") Long id,@Param("status") String status);

    List<Task> findAllByStatus(String status);

    @Modifying
    @Query("SELECT t FROM Task t WHERE t.status != 'completed'")
    List<Task> findUnfinished();


}
