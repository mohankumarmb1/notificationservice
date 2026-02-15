package com.hrs.notificationservice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hrs.notificationservice.entities.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

	List<Notification> findByCustomerId(Long customerId);

}
