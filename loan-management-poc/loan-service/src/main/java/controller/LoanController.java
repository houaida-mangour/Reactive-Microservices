package controller;

import entity.LoanApplication;
import event.LoanApplicationEvent;
import repository.LoanRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final KafkaTemplate<String, LoanApplicationEvent> kafkaTemplate;
    private final LoanRepository loanRepository;

    public LoanController(KafkaTemplate<String, LoanApplicationEvent> kafkaTemplate,
                          LoanRepository loanRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.loanRepository = loanRepository;
    }

    @PostMapping
    public Mono<String> submitLoan(@RequestBody LoanRequest request) {
        LoanApplication loan = new LoanApplication(request.clientId(), request.amount(), request.duration());
        LoanApplication saved = loanRepository.save(loan);

        LoanApplicationEvent event = new LoanApplicationEvent(
            saved.getId(), saved.getClientId(), saved.getAmount(), saved.getDuration()
        );

        kafkaTemplate.send("loan-application-submitted", event);
        return Mono.just("Loan application submitted with ID: " + saved.getId());
    }
}

record LoanRequest(String clientId, double amount, int duration) {}
