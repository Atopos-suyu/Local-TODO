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

import javax.servlet.http.Cookie;  //用于在Web应用中存储和获取HTTP请求的cookie数据

/**
 * @author joe
 * @date 2021/6/15  用户登录界面
 */
@Route("login.html")  //基于Vaadin框架实现的用户登录界面的类
public class LoginView extends VerticalLayout {

    private LoginForm loginForm = new LoginForm();  //创建LoginForm对象loginform,用于展示用户登录表单

    public LoginView() {

        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);  //设置界面布局的对齐方式

        loginForm.setForgotPasswordButtonVisible(false);  //隐藏登录表单中的“忘记密码”按钮，true为显示

        H1 h1 = new H1("TODO");  //创建一个H1对象h1，显示一个标题

        HorizontalLayout regLayout = new HorizontalLayout();  //放置注册的相关组件
        regLayout.setAlignItems(Alignment.CENTER);
        Label tips = new Label("Don’t have an account?");
        Button regBtn = new Button("Sign up here.");
        regBtn.setThemeName("tertiary");  //表示该按钮将应用与 "tertiary" 主题相关的样式
        regBtn.addClickListener(this::onSign);  //给注册按钮regBtn添加一个点击事件的监听器，即this::onSign方法

        regLayout.add(tips, regBtn);

        loginForm.addLoginListener(this::onLogin);  //给登录表单loginForm添加一个登录事件的监听器，即this::onLogin方法


        add(h1, regLayout, loginForm);  //依次添加到垂直布局VerticalLayout中

    }  //实现一个简单的用户登录界面，在界面中展示了标题、注册相关的提示和按钮，以及一个登录表单，并为注册按钮和登录表单添加了事件处理方法


    public void onSign(ClickEvent event){  //获取当前用户界面（UI）的方法
        getUI().ifPresent(ui -> {
            ui.navigate("/reg.html");  //通过navigate()方法导航到指定的URL
        });
    }   //用户点击注册按钮时，通过导航到 /reg.html 这个URL，从而跳转到注册页面

    public void onLogin(AbstractLogin.LoginEvent event){  //onLogin 方法是一个事件处理方法，用于处理登录表单提交事件
        String userName = event.getUsername();
        String password = event.getPassword();  //分别获取用户在登录表单中输入的用户名和密码
        
        for (User user : Reg.users) {  //通过循环遍历Reg.users列表中的每个用户对象
            if (user.getUserName().equals(userName) && user.getPassword().equals(password)){//判断用户输入的用户名和密码是否与列表中的用户匹配
                Notification notification = new Notification();
                notification.setDuration(3000);
                notification.setText("login success");
                notification.open();  //创建一个Notification对象，并设置其显示时间和文本内容

                Cookie cookie = new Cookie("username",userName);
                cookie.setPath("/");  //向客户端添加一个名为"username"的 Cookie,值为当前登录的用户名，并设置路径为根路径

                VaadinService.getCurrentResponse().addCookie(cookie);
                //用于向客户端添加一个名为"username"的 Cookie,值为当前登录的用户名。在客户端上可以通过该 Cookie 来存储和获取用户的登录信息
                System.out.println(getUI().isPresent());
                //用来检查当前是否存在用户界面(UI)
                getUI().ifPresent(ui -> {
                    ui.navigate("/");  //获取当前用户界面(如果存在)并将其导航到根路径 ("/")
                });
                return;  //用来结束方法的执行
            }  //用户登录成功后，设置Cookie以存储登录信息，然后检查当前是否存在用户界面，如果存在，则将用户界面导航到根路径
        }

        loginForm.setError(true);  //循环结束后未找到匹配的用户，就将登录表单的setError属性设置为true,表示登录失败
        
    }


}
