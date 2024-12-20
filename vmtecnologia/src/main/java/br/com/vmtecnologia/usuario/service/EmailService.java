package br.com.vmtecnologia.usuario.service;

import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
@Service
@Setter(onMethod_ = @Autowired)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmailService {

    private JavaMailSender mailSender;

    public void sendEmail(String destinatario, String titulo, String mensagem) {
        System.out.println("Email enviado");
    }
}
