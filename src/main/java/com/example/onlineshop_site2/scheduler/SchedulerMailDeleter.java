package com.example.onlineshop_site2.scheduler;

import com.example.onlineshop_site2.models.entities.Mail;
import com.example.onlineshop_site2.repositories.MailRepo;
import lombok.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
@Component
@RequiredArgsConstructor
public class SchedulerMailDeleter {

    private final MailRepo mailRepo;

    @Scheduled(cron = "0 * * * * *")
    public void mailDeleter() {
        List<Mail> res = mailRepo.findAllByTimeToEndBefore(LocalDateTime.now());
        mailRepo.deleteAll(res);
    }
}
