package entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "risk_assessments")
public class RiskAssessment {

    @Id
    private String applicationId;
    private int initialScore;
    private int finalScore;
    private boolean centralBankCheck;
    private boolean approved;
    private String reason;
    private LocalDateTime assessedAt;

    public RiskAssessment() {}

    public RiskAssessment(String applicationId, int initialScore, int finalScore,
                          boolean centralBankCheck, boolean approved, String reason) {
        this.applicationId = applicationId;
        this.initialScore = initialScore;
        this.finalScore = finalScore;
        this.centralBankCheck = centralBankCheck;
        this.approved = approved;
        this.reason = reason;
        this.assessedAt = LocalDateTime.now();
    }

    // Getters
    public String getApplicationId() { return applicationId; }
    public int getInitialScore() { return initialScore; }
    public int getFinalScore() { return finalScore; }
    public boolean isCentralBankCheck() { return centralBankCheck; }
    public boolean isApproved() { return approved; }
    public String getReason() { return reason; }
}
