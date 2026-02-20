package com.hrs.notificationservice.proxy;

import com.hrs.notificationservice.exceptions.NotificationNotFoundException;
import com.hrs.notificationservice.model.Notification;

import java.util.List;

public interface INotificationService
{

    public Notification notify(Notification notification);

    public Notification getNotification(String id) throws NotificationNotFoundException;

    public List<Notification> getNotificationByUserId(String id) throws NotificationNotFoundException;
    public List<Notification> getAllNotification();


    public Notification updateNotification(Notification notification) throws NotificationNotFoundException;


    public boolean deleteNotification(String id) throws NotificationNotFoundException;


}
