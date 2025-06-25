package br.com.tbt.lactino.service.impl;

import br.com.tbt.lactino.service.EnviarEmailService;
import br.com.tbt.lactino.service.dto.EnviarEmailDTO;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnviarEmailServiceImpl implements EnviarEmailService {
  private final JavaMailSender mailSender;

  @Value("${spring.mail.username}")
  private String emailFrom;

  @Override
  public void enviarEmail(EnviarEmailDTO dto) {
    try {
      MimeMessage message = this.mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, true);

      helper.setFrom(emailFrom);
      helper.setTo(dto.to());
      helper.setSubject(dto.subject());
      helper.setText(dto.body());

      this.mailSender.send(message);
    } catch (Exception ex) {
      throw new RuntimeException("Erro ao enviar email: " + ex.getMessage());
    }
  }
}
