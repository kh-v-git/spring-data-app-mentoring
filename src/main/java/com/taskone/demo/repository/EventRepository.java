package com.taskone.demo.repository;

import com.taskone.demo.domain.Event;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByTitle(String title);

    List<Event> findAllByTitle(String title, Pageable pageable);

    List<Event> findAllByDate(LocalDate date);

    List<Event> findAllByDate(LocalDate date, Pageable pageable);
}
