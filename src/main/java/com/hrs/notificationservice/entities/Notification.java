package com.hrs.notificationservice.entities;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.hrs.notificationservice.models.NotificationStatus;
import com.hrs.notificationservice.models.NotificationType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "notification")
public class Notification {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "Invalid customerId: CustomerId may not be null.")
	private Long customerId;

	private Long reservationId;

	@Enumerated(EnumType.STRING)
	@NotNull(message = "Invalid type: Notification type may not be null.")
	private NotificationType type;

	@NotNull(message = "Invalid subject: Email subject may not be null.")
	private String emailSubject;

	@NotNull(message = "Invalid content: Email content may not be null.")
	private String emailContent;

	@Enumerated(EnumType.STRING)
	@NotNull(message = "Invalid status: Notification status may not be null.")
	private NotificationStatus status;

	public Notification updateWith(Notification notification) {
		return new Notification(this.id, notification.customerId, notification.reservationId, notification.type,
				notification.emailSubject, notification.emailContent, notification.status);
	}

}
