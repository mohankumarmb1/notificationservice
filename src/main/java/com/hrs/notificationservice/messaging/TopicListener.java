package com.hrs.notificationservice.messaging;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.hrs.notificationservice.models.CustomerDto;
import com.hrs.notificationservice.models.NotificationType;
import com.hrs.notificationservice.models.PaymentDto;
import com.hrs.notificationservice.models.ReservationDto;
import com.hrs.notificationservice.services.NotificationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class TopicListener {

	@Value("${consumer.config.customer.topic.name}")
	private String customerTopicName;

	@Value("${consumer.config.reservation.topic.name}")
	private String reservationTopicName;

	@Value("${consumer.config.cancellation.topic.name}")
	private String cancellationTopicName;

	@Value("${consumer.config.payment.topic.name}")
	private String paymentTopicName;

	@Value("${consumer.config.refund.topic.name}")
	private String refundTopicName;

	@Autowired
	NotificationService notificationService;

	@KafkaListener(id = "${consumer.config.customer.topic.name}", topics = "${consumer.config.customer.topic.name}", groupId = "${consumer.config.group-id}")
	public void consumeCustomer(ConsumerRecord<String, CustomerDto> payload) {
		log.info("Topic : {}", customerTopicName);
		log.info("Key : {}", payload.key());
		log.info("Headers : {}", payload.headers());
		log.info("Partion : {}", payload.partition());
		log.info("Customer : {}", payload.value());

		CustomerDto customer = payload.value();
		notificationService.sendEmail(customer, null, null, NotificationType.REGISTRATION);
	}

	@KafkaListener(id = "${consumer.config.reservation.topic.name}", topics = "${consumer.config.reservation.topic.name}", groupId = "${consumer.config.group-id}")
	public void consumeReservation(ConsumerRecord<String, ReservationDto> payload) {
		log.info("Topic : {}", reservationTopicName);
		log.info("Key : {}", payload.key());
		log.info("Headers : {}", payload.headers());
		log.info("Partion : {}", payload.partition());
		log.info("Reservation : {}", payload.value());

		ReservationDto reservation = payload.value();
		notificationService.sendEmail(null, reservation, null, NotificationType.RESERVATION);
	}

	@KafkaListener(id = "${consumer.config.cancellation.topic.name}", topics = "${consumer.config.cancellation.topic.name}", groupId = "${consumer.config.group-id}")
	public void consumeCancellation(ConsumerRecord<String, ReservationDto> payload) {
		log.info("Topic : {}", cancellationTopicName);
		log.info("Key : {}", payload.key());
		log.info("Headers : {}", payload.headers());
		log.info("Partion : {}", payload.partition());
		log.info("Reservation : {}", payload.value());

		ReservationDto reservation = payload.value();
		notificationService.sendEmail(null, reservation, null, NotificationType.CANCELLATION);
	}

	@KafkaListener(id = "${consumer.config.payment.topic.name}", topics = "${consumer.config.payment.topic.name}", groupId = "${consumer.config.group-id}")
	public void consumePayment(ConsumerRecord<String, PaymentDto> payload) {
		log.info("Topic : {}", paymentTopicName);
		log.info("Key : {}", payload.key());
		log.info("Headers : {}", payload.headers());
		log.info("Partion : {}", payload.partition());
		log.info("Payment : {}", payload.value());

		PaymentDto payment = payload.value();
		notificationService.sendEmail(null, null, payment, NotificationType.PAYMENT_CONFIRMATION);
	}

	@KafkaListener(id = "${consumer.config.refund.topic.name}", topics = "${consumer.config.refund.topic.name}", groupId = "${consumer.config.group-id}")
	public void consumeRefund(ConsumerRecord<String, PaymentDto> payload) {
		log.info("Topic : {}", refundTopicName);
		log.info("Key : {}", payload.key());
		log.info("Headers : {}", payload.headers());
		log.info("Partion : {}", payload.partition());
		log.info("Payment : {}", payload.value());

		PaymentDto payment = payload.value();
		notificationService.sendEmail(null, null, payment, NotificationType.PAYMENT_REFUND);
	}

}