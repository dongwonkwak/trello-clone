package com.trelloclone.backend.adapter.in.web.controller;

import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LoginControllerTest {

    @Mock
    private Model model;

    @Mock
    private HttpSession session;

    @InjectMocks
    private LoginController controller;

    @Test
    @DisplayName("에러 파라미터가 없을 때 login 뷰를 반환한다")
    void login_WithoutErrorParam_ReturnsLoginView() {
        // When
        String viewName = controller.login(null, model, session);

        // Then
        assertThat(viewName).isEqualTo("login");
        verifyNoInteractions(session);
    }

    @Test
    @DisplayName("에러 파라미터가 있으면 세션에서 에러 메시지를 가져와 모델에 추가한다")
    void login_WithErrorParam_AddsErrorMessageToModel() {
        // Given
        String errorParam = "true";
        String errorMessage = "Invalid credentials";
        when(session.getAttribute("errorMessage")).thenReturn(errorMessage);

        // When
        String viewName = controller.login(errorParam, model, session);

        // Then
        assertThat(viewName).isEqualTo("login");
        verify(model).addAttribute("errorMessage", errorMessage);
        verify(session).removeAttribute("errorMessage");
    }
}
