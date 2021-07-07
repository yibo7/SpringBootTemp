package com.ebsite.tempsite.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

/**
 * @author ：蔡齐盛
 * @date ：Created in 2019/10/10 18:53
 * @description：演示表单对象类
 * @modified By：
 */
@Data
public class UserFormDemo {
    @NotBlank(message = "名字不能为空")
    @Size(min = 2, max = 6, message = "名字长度在2-6位") //字符串，集合，map限制大小
    @Length(min = 2, max = 6, message = "名字长度在2-6位")
    private String name;

    @Length(min = 3, max = 3, message = "pass 长度不为3")
    private String pass;
    @DecimalMin(value = "10", inclusive = true, message = "salary 低于10") // 被注释的元素必须是一个数字，其值必须大于等于指定的最小值
    private int salary;
    @Range(min = 5, max = 10, message = "range 不在范围内")
    private int range;
    @NotNull(message = "年龄不能为空")
    @Min(value = 18, message = "年龄不能小于18")
    @Max(value = 70, message = "年龄不能大于70")
    private int age;
    @Email
    private String email;
    @AssertTrue
    private boolean flag;
    @Past
    private Date birthday;
    @Future
    private Date expire;
    @URL(message = "url 格式不对")
    private String url;
    //    @AnnoValidator(value = "1,2,3")
//    private String anno;
    //@Pattern(regex=,flag=)  被注释的元素必须符合指定的正则表达式
    @Size(min = 2, max = 6, message = "长度在2-6位") //字符串，集合，map限制大小
    private List<Integer> list;
}
