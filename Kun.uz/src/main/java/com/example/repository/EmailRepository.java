package com.example.repository;

import com.example.entity.EmailEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface EmailRepository extends CrudRepository<EmailEntity, Integer> {


    List<EmailEntity> findAllByEmail(String email);



    @Query(value = "SELECT * from email_history where (SELECT cast(created_date as date))=?1", nativeQuery = true)
    List<EmailEntity> getEmailByDate(LocalDate localDate);

    EmailEntity findAll(Pageable paging);
}
