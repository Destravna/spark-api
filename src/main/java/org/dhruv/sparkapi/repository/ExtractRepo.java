package org.dhruv.sparkapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.dhruv.sparkapi.entity.Extracts;
import java.util.List;

@Repository
public interface ExtractRepo extends JpaRepository<Extracts, Long>  {
    

}
