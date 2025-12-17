package entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "loan_applications")
public class LoanApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String clientId;
    private double amount;
    private int duration;

    @Enumerated(EnumType.STRING)
    private LoanStatus status;

    private LocalDateTime submittedAt;

    public LoanApplication() {}

    public LoanApplication(String clientId, double amount, int duration) {
        this.clientId = clientId;
        this.amount = amount;
        this.duration = duration;
        this.status = LoanStatus.SUBMITTED;
        this.submittedAt = LocalDateTime.now();
    }

    // Getters & Setters
    public String getId() { return id; }
    public String getClientId() { return clientId; }
    public double getAmount() { return amount; }
    public int getDuration() { return duration; }
    public LoanStatus getStatus() { return status; }
    public void setStatus(LoanStatus status) { this.status = status; }
}

enum LoanStatus {
    SUBMITTED, SCORING, APPROVED, REJECTED
}
