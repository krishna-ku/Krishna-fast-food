package com.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restaurant.entity.MailHistory;

public interface MailHistoryRepo extends JpaRepository<MailHistory, Long> {

}
