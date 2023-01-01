package ru.vityaman.itmo.web.lab.taparia.app;

import jakarta.annotation.ManagedBean;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import ru.vityaman.itmo.web.lab.taparia.backend.Backend;
import ru.vityaman.itmo.web.lab.taparia.logic.abstraction.AuthService;
import ru.vityaman.itmo.web.lab.taparia.logic.exception.LogicException;
import ru.vityaman.itmo.web.lab.taparia.user.UserAccessToken;
import ru.vityaman.itmo.web.lab.taparia.user.UserCredentials;

@ManagedBean
@RequestScoped
@Named("loginForm")
public final class LoginForm {
    private final AuthService authService =
        Backend.instance().authService();

    private String username = "";
    private String password = "";
    private String error = "";

    public void doLogin() {
        saveTokenAndRedirect(authService::login);
    }

    public void doRegister() {
        saveTokenAndRedirect(authService::register);
    }

    private void saveTokenAndRedirect(TokenExchanger exchanger) {
        try {
            final var token = exchanger.exchange(extractCredentials());
            ExternalContext ctx = FacesContext
                .getCurrentInstance()
                .getExternalContext();
            addCookie(ctx, "user_id", String.valueOf(token.userId().value()));
            addCookie(ctx, "access_token", token.secret().value());
            ctx.redirect("html/index.html");
        } catch (Exception e) {
            setError(e.getMessage());
        }
    }

    private void addCookie(ExternalContext ctx, String key, String obj) {
        ctx.addResponseCookie(key, obj, null);
    }

    private UserCredentials extractCredentials() {
        return UserCredentials.from(username, password);
    }

    @FunctionalInterface
    private interface TokenExchanger {
        UserAccessToken exchange(UserCredentials credentials)
            throws LogicException;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
