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
//基于Vaadin框架的简单用户界面类
/**
 * @author joe
 * @date 2021/6/7
 */
@Route("/reg.html")  //标记注解，用于将这个类映射到一个URL路由上，使得当用户访问这个URL时，可以呈现这个类定义的界面
public class Reg extends VerticalLayout {  //Reg类继承自Vaadin的VerticalLayout类

    public static List<User> users = new ArrayList<>();  //定义静态变量 users，类型为List<User>，并初始化为空的ArrayList

    private static ObjectMapper mapper = new ObjectMapper();  //定义静态变量 mapper，类型为ObjectMapper，用于将 JSON 数据转换为 Java 对象
    private static File usersFile = new File("./data/users.json");  //定义静态变量usersFile，类型为File，表示用于存储用户数据的JSON文件

    static {
        if (usersFile.exists()) {
            try {
               String content = FileUtils.readFileToString(usersFile, "utf-8");

                List<User> userList = mapper.readValue(content, new TypeReference<List<User>>() {
                });  //如果文件存在，就读取其中的内容，并将其转换为User对象的列表，最后将列表添加到 users 变量中

                users.addAll(userList);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }  
    }  //通过读取文件中已有的数据，并将其转换为User对象的列表，来初始化users变量，以便后续的操作可以使用到这些用户数据

    public Reg() {  //构造函数声明

        TextField userNameField = new TextField();  //创建文本字段，输入用户名
        initUserNameField(userNameField);  //调用方法进行初始化

        TextField passwordField = new TextField();  //输入密码
        initPasswordField(passwordField);

        TextField confirmPasswordField = new TextField();  //确认密码
        initConfirmPasswordField(confirmPasswordField);

        Button regButton = new Button("Reg");  //添加一个名为“Reg”的按钮

        regButton.addClickListener(click -> {  //添加按钮点击监听器
            onReg(userNameField, passwordField, confirmPasswordField);
        });  //注册按钮点击事件处理

        add(userNameField);
        add(passwordField);
        add(confirmPasswordField);
        add(regButton);  //向布局中添加组件
        
    }  //当用户点击注册按钮时，会调用相应的处理方法,将这些组件添加到垂直布局中以显示在用户界面上

    public void initUserNameField(TextField userNameField) {  //TextField作为参数，命名为userNameField
        String userName = "UserName";
        userNameField.setLabel(userName);
        userNameField.setPlaceholder("please input username");
    }  //设置用户名文本字段的标签和占位符，以便在用户界面上显示正确的提示信息

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
    //onReg方法用于处理注册按钮时的逻辑处理
    public void onReg(TextField userNameField, TextField passwordField, TextField confirmPasswordField) {
        String userNameText = userNameField.getValue();
        String pwdText = passwordField.getValue();
        String confirmPwdText = confirmPasswordField.getValue();
        //调用相应的文本字段.getValue方法获得用户输入的内容储存在字符串变量中
        Notification notification = new Notification();  //创建notification对象显示通知消息
        notification.setDuration(3000);  // 消息持续显示时间为3000ms

        if (userNameText == null || "".equals(userNameText)) {  //如果 userNameText 为空或者空字符串，则认为用户名未输入
            notification.setText("need input username");  //将通知消息设置为 "need input username"
            notification.open();  //调用open()显示通知消息"need input username"
            // 程序执行到这就结束啦，后面的代码不再执行咯
            return;
        }

        if (pwdText != null && !"".equals(pwdText) && pwdText.equals(confirmPwdText)) {
         //如果密码不为空且与确认密码相等
            for (User user : users) {
             //循环遍历用户列表中的每个用户对象
                if (userNameText.equals(user.getUserName())){
                    notification.setText("username already reg");
                    notification.open();  
                    return;
                }

            }
            //用于注册新用户并将用户信息保存到文件中
            User user = new User();  //当用户名和密码的验证都通过后，首先创建一个 User 对象
            user.setUserName(userNameText);
            user.setPassword(pwdText);  //将输入的用户名和密码分别设置为User对象的用户名和密码

            LocalDateTime gmtCreated = LocalDateTime.now();
            user.setGmtCreated(gmtCreated);  //获取当前时间作为用户对象的创建时间，并将其设置为 User 对象的 gmtCreated 属性

            users.add(user);  //将创建好的 User 对象添加到用户列表中

            String userName = user.getUserName();

            notification.setText("reg success " + userName);

            try {
                String content = mapper.writeValueAsString(users);  //将用户列表转换成 JSON 格式的字符串，并将其写入到名为 usersFile 的文件中
                FileUtils.writeStringToFile(usersFile, content, "utf-8");
            } catch (Exception e) {
                e.printStackTrace();
            }  //如果写入过程中遇到异常，会将异常对象的堆栈信息打印出来。

        } else {
            notification.setText("confirm pwd error");
        }
        notification.open();
    }  //将用户输入的用户名和密码保存到文件中，并在界面上显示注册结果的通知消息

}
