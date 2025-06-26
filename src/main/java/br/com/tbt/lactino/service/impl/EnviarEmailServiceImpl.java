package br.com.tbt.lactino.service.impl;

import br.com.tbt.lactino.service.EnviarEmailService;
import br.com.tbt.lactino.service.dto.EnviarEmailDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnviarEmailServiceImpl implements EnviarEmailService {
  private final JavaMailSender mailSender;

  @Value("${emailFrom}")
  private String emailFrom;

  @Override
  public void enviarEmail(EnviarEmailDTO dto) {
    try {
      SimpleMailMessage message = new SimpleMailMessage();

      message.setFrom(emailFrom);
      message.setTo(dto.to());
      message.setSubject(dto.subject());
      message.setText(dto.body());

      this.mailSender.send(message);
    } catch (Exception ex) {
      throw new RuntimeException("Erro ao enviar email: " + ex.getMessage());
    }
  }
}
