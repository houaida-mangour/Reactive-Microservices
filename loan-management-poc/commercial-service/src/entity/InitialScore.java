package entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "initial_scores")
public class InitialScore {

    @Id
    private String applicationId;

    private int score;
    private String riskLevel;
    private LocalDateTime computedAt;

    public InitialScore() {}

    public InitialScore(String applicationId, int score, String riskLevel) {
        this.applicationId = applicationId;
        this.score = score;
        this.riskLevel = riskLevel;
        this.computedAt = LocalDateTime.now();
    }

    public String getApplicationId() { return applicationId; }
    public int getScore() { return score; }
    public String getRiskLevel() { return riskLevel; }
}
