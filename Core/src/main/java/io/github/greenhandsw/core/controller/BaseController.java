package io.github.greenhandsw.core.controller;

import io.github.greenhandsw.core.entity.CommonResult;
import io.github.greenhandsw.core.service.BaseService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.Serializable;
import java.util.UUID;

@Slf4j
public class BaseController<T, S extends BaseService<T, ID, R>, ID extends Serializable, R extends JpaRepository<T, ID>> {

    @Resource
    protected S s;

    @PostMapping("/add")
    public Object add(@RequestBody T entity){
        T result=s.save(entity);
        log.info(">>>插入结果："+result);
        return new CommonResult<T>(200, "插入成功", entity);
    }

    @PostMapping("/get")
    public Object get(@RequestBody ID id){
        T result=s.find(id);
        log.info(">>>查询结果："+result);
        if(result==null){
            return new CommonResult<T>(200, "查询失败，不存在id=%s的记录".formatted(id));
        }
        return new CommonResult<T>(200, "插入成功", result);
    }

    @PostMapping("/delete")
    public Object delete(@RequestBody ID id){
        s.delete(id);
        log.info(">>>删除ID："+id);
        return new CommonResult<ID>(200, "删除成功", id);
    }

    @PostMapping("/update")
    public Object update(@RequestBody T entity){
        T result = s.update(entity);
        log.info(">>>更新结果："+result);
        return new CommonResult<T>(200, "更新成功", entity);
    }
}
