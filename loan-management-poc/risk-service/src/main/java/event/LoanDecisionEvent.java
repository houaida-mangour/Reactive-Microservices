package event;


public record LoanDecisionEvent(
    String applicationId,
    boolean approved,
    int finalScore,
    String reason
) {}
