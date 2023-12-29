package io.github.greenhandsw.core.controller;

import io.github.greenhandsw.core.entity.CommonResult;
import io.github.greenhandsw.core.service.BaseService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.Serializable;
import java.util.List;

@Slf4j
public class BaseTmpController<T, ID extends Serializable> {
    @Resource
    public Registration registration;
    @Autowired
    BaseService<T, ID> s;

    @Resource
    protected DiscoveryClient discoveryClient;

    @PostMapping("/add")
    protected Object add(@RequestBody T entity){
        T result=s.save(entity);
        doLog("插入", entity, result);
        return new CommonResult<>(200, "插入成功", entity);
    }

    @PostMapping("/addAll")
    protected Object add(@RequestBody List<T> entities){
        List<T> result=s.save(entities);
        doLog("批量插入", entities, result);
        return new CommonResult<>(200, "批量插入成功", entities.toString());
    }

    @PostMapping("/get")
    protected Object get(@RequestBody ID id){
        T result=s.find(id);
        doLog("查询", id, result);
        if(result==null){
            return new CommonResult<T>(200, "查询失败，不存在id=%s的记录".formatted(id));
        }
        return new CommonResult<>(200, "查询成功", result);
    }

    @PostMapping()
    protected Object page(Pageable pageable){
        Page<T> result=s.page(pageable);
        doLog("分页查询", pageable, null);
        return new CommonResult<>(200, "分页查询成功", result);
    }

    @PostMapping("/delete")
    protected Object delete(@RequestBody ID id){
        s.delete(id);
        doLog("删除", id, null);
        return new CommonResult<>(200, "删除成功", id);
    }

    @PostMapping("/update")
    protected Object update(@RequestBody T entity){
        T result = s.update(entity);
        doLog("更新", entity, result);
        return new CommonResult<>(200, "更新成功", entity);
    }

    @PostMapping("/discovery")
    protected Object discovery(){
        // 获取服务列表的信息
        List<String> services=discoveryClient.getServices();
        for (String service : services) {
            log.info("******service: " + service);
        }
        // 获取所有实例
        List<ServiceInstance> instances=discoveryClient.getInstances("pay");
        for (ServiceInstance instance :
                instances) {
            log.info("{}\t{}:{}/{}", instance.getServiceId(), instance.getHost(), instance.getPort(), instance.getUri());
        }

        return discoveryClient;
    }

    protected void doLog(String operation, Object input, Object output){
        log.info("{}:{}>>>{}信息：{}>>>结果：{}", registration.getHost(), registration.getInstanceId(), operation, input, output);
    }
}
