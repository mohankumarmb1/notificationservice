package com.hrs.notificationservice.services.implementations;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hrs.notificationservice.entities.Notification;
import com.hrs.notificationservice.exceptions.NotificationNotFoundException;
import com.hrs.notificationservice.messaging.EmailSender;
import com.hrs.notificationservice.models.CustomerDto;
import com.hrs.notificationservice.models.NotificationDto;
import com.hrs.notificationservice.models.NotificationStatus;
import com.hrs.notificationservice.models.NotificationType;
import com.hrs.notificationservice.models.PaymentDto;
import com.hrs.notificationservice.models.ReservationDto;
import com.hrs.notificationservice.repositories.NotificationRepository;
import com.hrs.notificationservice.services.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService {

	private static final Logger log = LoggerFactory.getLogger(NotificationServiceImpl.class);

	@Autowired
	private EmailSender emailSender;

	@Autowired
	private NotificationRepository notificationRepository;

	@Autowired
	private ModelMapper mapper;

	/**
	 * Sends email notification
	 */
	@Override
	public void sendEmail(@Valid CustomerDto customer, @Valid ReservationDto reservationDto,
			@Valid PaymentDto paymentDto, @Valid NotificationType type) {

		NotificationDto notificationDto = new NotificationDto();
		notificationDto.setCustomerId(customer.getId());
		notificationDto.setType(type);

		log.info("Sending email to '" + customer.getEmail() + "'");

		String subject = "";
		String content = "";

		// Call the sendEmail method to send an email
		String recipientEmail = customer.getEmail();

		switch (type) {
		case REGISTRATION: {
			subject = "Registration successful";
			content = "<p>Hello" + customer.getName()
					+ ",</p><p>You have been registered successfully to Hotel Reservation System.</p>";
			break;
		}
		case RESERVATION: {
			subject = "Room reservation confirmed";
			content = "<p>Hello " + customer.getName() + ",</p><p>Room No: " + reservationDto.getHotelId()
					+ " has been reserved successfully.</p>";
			notificationDto.setReservationId(reservationDto.getId());
			break;
		}
		case CANCELLATION: {
			subject = "Room cancellation successful";
			content = "<p>Hello " + customer.getName()
					+ ",</p><p>Room reservation has been cancelled successfully.</p>";
			notificationDto.setReservationId(reservationDto.getId());
			break;
		}
		case PAYMENT_CONFIRMATION: {
			subject = "Payment confirmation";
			content = "<p>Hello " + customer.getName() + ",</p><p>Payment for reservation: "
					+ paymentDto.getReservationId() + " has been received successfully through "
					+ paymentDto.getProvider().toString() + ".</p>";
			notificationDto.setReservationId(reservationDto.getId());
			break;
		}
		case PAYMENT_REFUND: {
			subject = "Refund processed";
			content = "<p>Hello " + customer.getName() + ",</p><p>Refund has been processed successfully.</p>";
			notificationDto.setReservationId(reservationDto.getId());
			break;
		}
		default: {
			subject = "Default Subject";
			content = "<p>Default Content.</p>";
			break;
		}
		}

		notificationDto.setEmailSubject(subject);
		notificationDto.setEmailContent(content);
		notificationDto.setStatus(NotificationStatus.DRAFT);

		NotificationDto draftNotification = createNotification(notificationDto);

		try {
			emailSender.sendEmail(recipientEmail, subject, content);

			log.info("Email sent successfully.");
			draftNotification.setStatus(NotificationStatus.EMAIL_SENT);
			updateNotification(draftNotification.getId(), draftNotification);

		} catch (MessagingException e) {

			log.info("Failed to send email. Error: " + e.getMessage());

			draftNotification.setStatus(NotificationStatus.EMAIL_NOT_SENT);
			updateNotification(draftNotification.getId(), draftNotification);

		} catch (UnsupportedEncodingException f) {

			log.info("Failed to send email. Error: " + f.getMessage());

			draftNotification.setStatus(NotificationStatus.EMAIL_ERROR);
			updateNotification(draftNotification.getId(), draftNotification);

		}
	}

	/**
	 * Retrieves a notification with unique notification id
	 */
	@Override
	public NotificationDto getNotificationById(@Valid long id) {
		Optional<Notification> notification = notificationRepository.findById(id);
		if (notification.isPresent())
			return mapper.map(notification.get(), NotificationDto.class);
		else
			throw new NotificationNotFoundException("Notification with id: " + id + " not found");
	}

	/**
	 * Retrieves all the existing notifications with unique customer id
	 */
	@Override
	public List<NotificationDto> getNotificationsByCustomerId(@Valid long customerId) {
		List<NotificationDto> notifications = new ArrayList<>();
		notificationRepository.findByCustomerId(customerId).forEach(notification -> {
			notifications.add(mapper.map(notification, NotificationDto.class));
		});
		return notifications;
	}

	/**
	 * Creates a new notification
	 */
	@Override
	public NotificationDto createNotification(@Valid NotificationDto notificationDto) {
		Notification notification = mapper.map(notificationDto, Notification.class);
		return mapper.map(notificationRepository.save(notification), NotificationDto.class);
	}

	/**
	 * Updates a notification with unique notification id
	 */
	@Override
	public NotificationDto updateNotification(@Valid long id, @Valid NotificationDto notificationDto) {
		Optional<Notification> notificationFound = notificationRepository.findById(id);

		if (notificationFound.isPresent()) {
			Optional<Notification> updatedNotification = notificationFound.map(existingNotification -> {
				Notification notification = mapper.map(notificationDto, Notification.class);
				return notificationRepository.save(existingNotification.updateWith(notification));
			});

			return mapper.map(updatedNotification.get(), NotificationDto.class);
		} else
			throw new NotificationNotFoundException("Notification with id: " + id + " not found");
	}

	/**
	 * Deletes a notification with unique notification id
	 */
	@Override
	public void deleteNotification(@Valid long id) {
		if (getNotificationById(id) != null) {
			notificationRepository.deleteById(id);
			log.info("Notification deleted Successfully");
		} else {
			throw new NotificationNotFoundException("Notification with id: " + id + " not found");
		}
	}

}
