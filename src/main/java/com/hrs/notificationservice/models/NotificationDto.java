package com.hrs.notificationservice.models;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "Class representing a Notification in hotel reservation system.")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto {

	@ApiModelProperty(notes = "Unique identifier of the Notification.", example = "1")
	private Long id;

	@ApiModelProperty(notes = "Unique identifier of the Customer.", example = "1", required = true)
	@NotNull(message = "Invalid customerId: CustomerId may not be null.")
	private Long customerId;

	@ApiModelProperty(notes = "Unique identifier of the Reservation.", example = "1")
	private Long reservationId;

	@ApiModelProperty(notes = "Type of Notification.", example = "REGISTRATION", required = true)
	@Enumerated(EnumType.STRING)
	@NotNull(message = "Invalid type: Notification type may not be null.")
	private NotificationType type;

	@ApiModelProperty(notes = "Subject of the notification.", example = "Registration successful.", required = true)
	@NotNull(message = "Invalid subject: Email subject may not be null.")
	private String emailSubject;

	@ApiModelProperty(notes = "Content of the notification.", example = "<p>Hello,</p><p>You have been registered successfully to Hotel Reservation System.</p>", required = true)
	@NotNull(message = "Invalid content: Email content may not be null.")
	private String emailContent;

	@ApiModelProperty(notes = "Status of the Notification.", example = "EMAIL_SENT", required = true)
	@Enumerated(EnumType.STRING)
	@NotNull(message = "Invalid status: Notification status may not be null.")
	private NotificationStatus status;

}
