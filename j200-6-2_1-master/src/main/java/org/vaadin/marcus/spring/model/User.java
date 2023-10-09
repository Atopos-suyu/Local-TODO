package org.vaadin.marcus.spring.model;

import com.fasterxml.jackson.annotation.JsonFormat;  //用于指定在序列化和反序列化过程中如何格式化日期时间属性
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;  //用于指定在反序列化过程中使用的自定义反序列化类
import com.fasterxml.jackson.databind.annotation.JsonSerialize;  //用于指定在序列化过程中使用的自定义序列化类
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;  //用于将JSON字符串转换为java.time.LocalDateTime对象
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;  //用于将java.time.LocalDateTime对象转换为JSON字符串

import java.time.LocalDateTime;  ///用于表示年、月、日、时、分、秒等信息的不可变日期时间对象
  //类定义了一个包含日期时间属性的类，并使用Jackson库提供的注解和类来控制日期时间属性的序列化和反序列化行为
/**
 * @author joe
 * @date 2021/6/7
 */
public class User {

    private String userName;
    private String password;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)//反序列化过程中使用LocalDateTimeDeserializer类，将JSON字符串转换为LocalDateTime对象
    @JsonSerialize(using = LocalDateTimeSerializer.class)//序列化过程中将LocalDateTime对象转换为JSON字符串
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")//指定了日期时间的格式化模式
    private LocalDateTime gmtCreated;

    public String getUserName() {
        return userName;
    }//获取当前对象的用户名，并将其作为字符串类型的结果返回
    //封装
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(LocalDateTime gmtCreated) {
        this.gmtCreated = gmtCreated;
    }
}
