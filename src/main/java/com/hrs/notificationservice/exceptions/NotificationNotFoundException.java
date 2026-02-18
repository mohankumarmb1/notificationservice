package com.hrs.notificationservice.exceptions;

public class NotificationNotFoundException extends  RuntimeException
{
    public NotificationNotFoundException()
    {

    }
    public NotificationNotFoundException(String message)
    {
        super(message);
    }
}
