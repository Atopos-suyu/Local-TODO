package org.vaadin.marcus.spring;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.login.AbstractLogin;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;
import org.vaadin.marcus.spring.model.User;

import javax.servlet.http.Cookie;

/**
 * @author joe
 * @date 2021/6/15
 */
@Route("login.html")
public class LoginView extends VerticalLayout {

    private LoginForm loginForm = new LoginForm();

    public LoginView() {

        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        loginForm.setForgotPasswordButtonVisible(false);

        H1 h1 = new H1("TODO");

        HorizontalLayout regLayout = new HorizontalLayout();
        regLayout.setAlignItems(Alignment.CENTER);
        Label tips = new Label("Don’t have an account?");
        Button regBtn = new Button("Sign up here.");
        regBtn.setThemeName("tertiary");
        regBtn.addClickListener(this::onSign);

        regLayout.add(tips, regBtn);

        loginForm.addLoginListener(this::onLogin);


        add(h1, regLayout, loginForm);

    }


    public void onSign(ClickEvent event){
        getUI().ifPresent(ui -> {
            ui.navigate("/reg.html");
        });
    }

    public void onLogin(AbstractLogin.LoginEvent event){
        String userName = event.getUsername();
        String password = event.getPassword();

        for (User user : Reg.users) {
            if (user.getUserName().equals(userName) && user.getPassword().equals(password)){
                Notification notification = new Notification();
                notification.setDuration(3000);
                notification.setText("login success");
                notification.open();

                Cookie cookie = new Cookie("username",userName);
                cookie.setPath("/");

                VaadinService.getCurrentResponse().addCookie(cookie);

                System.out.println(getUI().isPresent());

                getUI().ifPresent(ui -> {
                    ui.navigate("/");
                });

                return;
            }
        }

        loginForm.setError(true);
        
    }


}
