package com.batchdemo.quotegenerator.batch.repository;

import com.batchdemo.quotegenerator.batch.entity.Recipient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipientRepository extends JpaRepository<Recipient, Integer> {
}
