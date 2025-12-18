package event;

public record LoanApplicationEvent(
    String applicationId,
    String clientId,
    double amount,
    int duration
) {}
