package org.vaadin.marcus.spring;
//com.vaadin.flow.component：这是一个Vaadin Flow框架的组件包，里面包含各种UI组件
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;  //com.vaadin.flow.router：这是Vaadin Flow框架的路由器包，用于管理页面路由和导航
import com.vaadin.flow.server.VaadinService;  //com.vaadin.flow.server：这是Vaadin Flow框架的服务器包，用于处理与服务器的交互

import javax.servlet.http.Cookie;  //javax.servlet.http：这是Java Servlet API的一部分，提供了处理HTTP请求和响应的类和接口
import java.awt.*;  //java.awt：这是Java Abstract Window Toolkit (AWT)的一部分，它提供了用于创建图形用户界面的类和方法
//通过导入这些类和包，可以使用它们提供的功能和特性来开发基于Vaadin Flow框架的Web应用程序
@Route("/")  //注解，组件将会被映射到根路径"/"
public class Todo extends VerticalLayout implements AfterNavigationObserver{
//定义名为"Todo"的公共类，它继承自VerticalLayout类，并实现AfterNavigationObserver接口
     private VerticalLayout totoList = new VerticalLayout();  //创建了一个私有的VerticalLayout实例对象"totolist",用于存储待办事项文本
     private TextField todoField = new TextField();  //创建私有的TextFiled实例对象"todoField",用于输入待办事项的文本

     public Todo() {  //Todo类的构造函数

        Button addButton = new Button("Add");  //创建了一个"Add"按钮的实例对像"addButton"
        addButton.addClickListener(this::onAdd);  //添加点击事件监听器

        add(new H1("TODO"), new H4("Hello "+getUserName()),totoList,new HorizontalLayout(todoField,addButton));
        //使用add方法将各个组件添加到Todo类的实例中
    }//创建了一个名为 "Todo" 的页面组件，用于显示待办事项列表。在界面上还有一个文本输入框和一个 "Add" 按钮，用于向列表中添加新的待办事项
     //同时，页面顶部显示了一个标题和当前登录用户的欢迎信息
    public String getUserName(){  //返回当前登录用户的用户名
        for (Cookie cookie : VaadinService.getCurrentRequest().getCookies()) {  //遍历当前请求中的所有cookie
            if (cookie.getName().equals("username")){  //判断当前cookie是否包含名为"username"的键
                return cookie.getValue();  //返回当前登录用户的用户名
            }
        }
        return "";  //如果没有找到名为"username"的cookie或对应值为空，则返回一个空字符串
    }  //该方法通过从Vaadin服务的当前请求中获取cookie来获取当前登录用户的用户名

    public void onAdd(ClickEvent event){  //处理"Add"按钮的点击事件
        String todoVal = todoField.getValue();  //获取输入框 "todoField" 中用户输入的文本值，并将其赋值给字符串类型的变量 "todoVal"
        Checkbox checkbox = new Checkbox(todoVal);//将用户输入的待办事项文本值作为参数传递给构造函数，创建一个带有默认标签文本的复选框
        totoList.add(checkbox);  //将新创建的复选框添加到待办事项列表中 "totoList"
    }//它在点击 "Add" 按钮时，获取文本输入框中用户输入的文本值，创建一个复选框对象，并将其添加到待办事项列表中

    @Override  //表示重写了父类或接口的同名方法
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {  //实现用户导航到相应页面后自动执行一些逻辑
    //实现AfterNavigationObserver接口所必需的方法，用于在用户导航到相应的页面后执行逻辑
        for (Cookie cookie : VaadinService.getCurrentRequest().getCookies()) {
            if (cookie.getName().equals("username")){
                return;
            }
        }
        getUI().ifPresent(ui -> {
            ui.navigate("/login.html");
        });  //如果当前请求中不包含名为 "username" 的cookie，则跳转到登录页面
    }  //实现用户导航到相应的页面后自动检查是否存在名为 "username" 的cookie
}
