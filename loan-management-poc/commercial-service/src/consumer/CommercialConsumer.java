package consumer;

import entity.InitialScore;
import event.InitialScoreEvent;
import event.LoanApplicationEvent;
import repository.InitialScoreRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CommercialConsumer {

    private final KafkaTemplate<String, InitialScoreEvent> kafkaTemplate;
    private final InitialScoreRepository scoreRepository;

    public CommercialConsumer(KafkaTemplate<String, InitialScoreEvent> kafkaTemplate,
                              InitialScoreRepository scoreRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.scoreRepository = scoreRepository;
    }

    @KafkaListener(topics = "loan-application-submitted", groupId = "commercial-service")
    public void processLoanApplication(LoanApplicationEvent event) {
        int score = calculateScore(event.amount(), event.duration());
        String riskLevel = determineRiskLevel(score);

        InitialScore initialScore = new InitialScore(event.applicationId(), score, riskLevel);
        scoreRepository.save(initialScore);

        InitialScoreEvent scoreEvent = new InitialScoreEvent(event.applicationId(), score, riskLevel);
        kafkaTemplate.send("initial-score-computed", scoreEvent);
    }

    private int calculateScore(double amount, int duration) {
        if (amount < 10000) return 85;
        if (amount < 50000 && duration <= 24) return 75;
        if (amount < 100000 && duration <= 36) return 60;
        return 40;
    }

    private String determineRiskLevel(int score) {
        if (score >= 80) return "LOW";
        if (score >= 60) return "MEDIUM";
        return "HIGH";
    }
}
