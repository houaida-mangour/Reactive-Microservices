package consumer;

import entity.RiskAssessment;
import event.InitialScoreEvent;
import event.LoanDecisionEvent;
import repository.RiskAssessmentRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class RiskConsumer {

    private final KafkaTemplate<String, LoanDecisionEvent> kafkaTemplate;
    private final RiskAssessmentRepository repository;

    public RiskConsumer(KafkaTemplate<String, LoanDecisionEvent> kafkaTemplate,
                        RiskAssessmentRepository repository) {
        this.kafkaTemplate = kafkaTemplate;
        this.repository = repository;
    }

    @KafkaListener(topics = "initial-score-computed", groupId = "risk-service")
    public void assessRisk(InitialScoreEvent event) {
        boolean centralBankOk = true; // Mock
        boolean approved = event.score() >= 60 && centralBankOk;
        String reason = approved ? "Approved" : "Rejected";

        RiskAssessment assessment = new RiskAssessment(
            event.applicationId(), event.score(), event.score(), centralBankOk, approved, reason
        );
        repository.save(assessment);

        LoanDecisionEvent decisionEvent = new LoanDecisionEvent(
            event.applicationId(), approved, event.score(), reason
        );
        kafkaTemplate.send("loan-decision", decisionEvent);
    }
}
