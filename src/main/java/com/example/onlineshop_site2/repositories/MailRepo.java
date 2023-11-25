package com.example.onlineshop_site2.repositories;

import com.example.onlineshop_site2.models.entities.Mail;
import lombok.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
public interface MailRepo extends CrudRepository<Mail, Long> {

    Optional<Mail> findByEmail(String email);

    @Transactional
    void delete(Mail mail);
}
