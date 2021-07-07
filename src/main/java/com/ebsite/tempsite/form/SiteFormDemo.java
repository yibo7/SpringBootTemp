package com.ebsite.tempsite.form;

import lombok.Data;

import javax.validation.constraints.*;

/**
 * @author ：蔡齐盛
 * @date ：Created in 2019/10/10 18:53
 * @description：演示表单对象类
 * @modified By：
 */
@Data
public class SiteFormDemo {
    @NotBlank(message = "名字不能为空")
    private String name;
    @NotNull(message = "年龄不能为空")
    @Min(value = 18, message = "年龄不能小于18")
    @Max(value = 70, message = "年龄不能大于70")
    private int age;

}
