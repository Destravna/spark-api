package org.dhruv.sparkapi.repository;

import java.util.List;

import org.dhruv.sparkapi.entity.Extract;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface ExtractJobRepo extends JpaRepository<Extract, Long> {
    List<Extract> findAllByOrderByJobIdDesc();
}
