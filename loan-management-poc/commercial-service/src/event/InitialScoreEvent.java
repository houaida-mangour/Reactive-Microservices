package event;

public record InitialScoreEvent(
    String applicationId,
    int score,
    String riskLevel
) {}
