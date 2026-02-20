package com.hrs.notificationservice.controller;


import com.hrs.notificationservice.exceptions.NotificationNotFoundException;
import com.hrs.notificationservice.model.Notification;
import com.hrs.notificationservice.proxy.NotificationServiceProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notification")
public class NotificationController
{

    @Autowired
    private NotificationServiceProxy notificationService;


    @PostMapping("/notify")
    ResponseEntity<Notification> notify(@RequestBody Notification notification)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(notificationService.notify(notification));
    }

    @GetMapping("/{id}")
    ResponseEntity<Notification> getNotification(@PathVariable String id) throws NotificationNotFoundException
    {
        return ResponseEntity.status(HttpStatus.OK).body(notificationService.getNotification(id));
    }

    @GetMapping("/customer/{id}")
    ResponseEntity<List<Notification>> getNotificationByCustomer(@PathVariable String id)
    {
        return ResponseEntity.status(HttpStatus.OK).body(notificationService.getNotificationByUserId(id));
    }

    @GetMapping("/")
    ResponseEntity<List<Notification>> getAllNotification()
    {
        return ResponseEntity.status(HttpStatus.OK).body(notificationService.getAllNotification());
    }

    @PutMapping("/")
    ResponseEntity<Notification> updateNotification(@RequestBody Notification notification) throws NotificationNotFoundException
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(notificationService.updateNotification(notification));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Boolean> deleteNotification(@PathVariable String id) throws NotificationNotFoundException
    {
        return ResponseEntity.status(HttpStatus.OK).body(notificationService.deleteNotification(id));
    }


}
