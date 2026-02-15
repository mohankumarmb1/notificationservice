package com.hrs.notificationservice.services;

import java.util.List;

import javax.validation.Valid;

import com.hrs.notificationservice.models.CustomerDto;
import com.hrs.notificationservice.models.NotificationDto;
import com.hrs.notificationservice.models.NotificationType;
import com.hrs.notificationservice.models.PaymentDto;
import com.hrs.notificationservice.models.ReservationDto;

public interface NotificationService {

	void sendEmail(@Valid CustomerDto customer, @Valid ReservationDto reservation, @Valid PaymentDto paymentDto, @Valid NotificationType type);

	NotificationDto getNotificationById(@Valid long id);

	List<NotificationDto> getNotificationsByCustomerId(@Valid long customerId);

	NotificationDto createNotification(@Valid NotificationDto notification);

	NotificationDto updateNotification(@Valid long id, @Valid NotificationDto notification);

	void deleteNotification(@Valid long id);

}
