package org.vaadin.marcus.spring;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.apache.commons.io.FileUtils;
import org.vaadin.marcus.spring.model.User;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author joe
 * @date 2021/6/7
 */
@Route("/reg.html")
public class Reg extends VerticalLayout {

    public static List<User> users = new ArrayList<>();

    private static ObjectMapper mapper = new ObjectMapper();
    private static File usersFile = new File("./data/users.json");

    static {

        if (usersFile.exists()) {
            try {
               String content = FileUtils.readFileToString(usersFile, "utf-8");

                List<User> userList = mapper.readValue(content, new TypeReference<List<User>>() {
                });

                users.addAll(userList);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public Reg() {

        TextField userNameField = new TextField();
        initUserNameField(userNameField);

        TextField passwordField = new TextField();
        initPasswordField(passwordField);

        TextField confirmPasswordField = new TextField();
        initConfirmPasswordField(confirmPasswordField);

        Button regButton = new Button("Reg");

        regButton.addClickListener(click -> {
            onReg(userNameField, passwordField, confirmPasswordField);
        });

        add(userNameField);
        add(passwordField);
        add(confirmPasswordField);
        add(regButton);

    }

    public void initUserNameField(TextField userNameField) {
        String userName = "UserName";
        userNameField.setLabel(userName);
        userNameField.setPlaceholder("please input username");
    }

    public void initPasswordField(TextField passwordField) {
        String password = "Password";
        passwordField.setLabel(password);
        passwordField.setPlaceholder("please input password");
    }

    public void initConfirmPasswordField(TextField passwordField) {
        String password = "Confirm Password";
        passwordField.setLabel(password);
        passwordField.setPlaceholder("please input confirm password");
    }

    public void onReg(TextField userNameField, TextField passwordField, TextField confirmPasswordField) {
        String userNameText = userNameField.getValue();
        String pwdText = passwordField.getValue();
        String confirmPwdText = confirmPasswordField.getValue();

        Notification notification = new Notification();
        notification.setDuration(3000);

        if (userNameText == null || "".equals(userNameText)) {
            notification.setText("need input username");
            notification.open();
            // 程序执行到这就结束啦，后面的代码不再执行咯
            return;
        }

        if (pwdText != null && !"".equals(pwdText) && pwdText.equals(confirmPwdText)) {

            for (User user : users) {

                if (userNameText.equals(user.getUserName())){
                    notification.setText("username already reg");
                    notification.open();
                    return;
                }

            }

            User user = new User();
            user.setUserName(userNameText);
            user.setPassword(pwdText);

            LocalDateTime gmtCreated = LocalDateTime.now();
            user.setGmtCreated(gmtCreated);

            users.add(user);

            String userName = user.getUserName();

            notification.setText("reg success " + userName);

            try {
                String content = mapper.writeValueAsString(users);
                FileUtils.writeStringToFile(usersFile, content, "utf-8");
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            notification.setText("confirm pwd error");
        }
        notification.open();

    }

}
