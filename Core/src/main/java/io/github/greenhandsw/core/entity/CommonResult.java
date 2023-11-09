package io.github.greenhandsw.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

//返回给前端的通用json数据串
@Data   //set/get方法
@AllArgsConstructor //有参构造器
@NoArgsConstructor  //无参构造器
public class CommonResult<T> {
    private Integer code;
    private String message;
    private T data; //泛型，对应类型的json数据

    //自定义两个参数的构造方法
    public CommonResult(Integer code, String message){
        this(code, message, null);
    }
}
