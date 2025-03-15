package com.trelloclone.backend.adapter.out.email;

import com.trelloclone.backend.application.port.out.email.EmailPort;
import com.trelloclone.backend.common.error.Failure;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Locale;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailAdapter implements EmailPort {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    private final MessageSource messageSource;

    @Value("${application.base-url}")
    private String baseUrl;

    @Override
    public Either<Failure, Void> sendEmail(String to, String subject, String content) {
        log.info("Sending email to: {}", to);
        return Try.run(() -> {
                    var message = mailSender.createMimeMessage();
                    var helper = new MimeMessageHelper(message, true, "UTF-8");
                    helper.setTo(to);
                    helper.setSubject(subject);
                    helper.setText(content, true); // HTML 형식 지원
                    mailSender.send(message);
                })
                .toEither()
                .mapLeft(ex -> Failure.ofInternalServerError("Failed to send email: " + ex.getMessage()))
                .map(unused -> null);
    }

    @Override
    public Either<Failure, Void> sendAccountActivationEmail(String to, String token, Locale locale) {
        // 템플릿에 전달할 컨텍스트 생성
        Context context = new Context(locale);
        context.setVariable("activationLink", baseUrl + "/activate?token=" + token);

        // 메시지 리소스에서 다국어 메시지 가져오기
        context.setVariable("greeting", messageSource.getMessage("email.account.activation.greeting", null, locale));
        context.setVariable("message", messageSource.getMessage("email.account.activation.message", null, locale));
        context.setVariable("buttonText", messageSource.getMessage("email.account.activation.button", null, locale));
        context.setVariable("expires", messageSource.getMessage("email.account.activation.expires", null, locale));
        context.setVariable("help", messageSource.getMessage("email.account.activation.help", null, locale));

        // 템플릿 처리
        String content = templateEngine.process("account-activation", context);

        // 제목 다국어 처리
        String subject = messageSource.getMessage("email.account.activation.subject", null, locale);

        // 이메일 전송
        return sendEmail(to, subject, content);
    }
}
