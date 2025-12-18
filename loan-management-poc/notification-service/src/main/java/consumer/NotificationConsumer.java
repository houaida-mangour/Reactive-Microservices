package consumer;

import event.LoanDecisionEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class NotificationConsumer {

    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @KafkaListener(
            topics = "loan-decision",
            groupId = "notification-service"
    )
    public void notifyClient(LoanDecisionEvent event) {

        String timestamp = LocalDateTime.now().format(formatter);

        System.out.println("║       LOAN APPLICATION NOTIFICATION      ║");
        System.out.println("║ Time:           " + timestamp + "        ║");
        System.out.println("║ Application ID: " + event.applicationId() + " ║");
        System.out.println("║ Status:         " +
                (event.approved() ? " APPROVED" : " REJECTED") +
                "                   ║");
        System.out.println("║ Final Score:    " + event.finalScore() + "/100" +
                "                    ║");
        System.out.println("║ Reason:         " + event.reason() +
                "           ║");

        // Mock notification sending
        sendEmail(event);
        sendSMS(event);
    }

    private void sendEmail(LoanDecisionEvent event) {
        System.out.println("Email sent to client for loan " + event.applicationId());
    }

    private void sendSMS(LoanDecisionEvent event) {
        System.out.println("SMS sent to client for loan " + event.applicationId());
    }
}
