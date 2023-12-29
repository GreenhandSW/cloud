package io.github.greenhandsw.core.controller;

import io.github.greenhandsw.core.entity.CommonResult;
import io.github.greenhandsw.core.service.BaseService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.Serializable;
import java.util.List;

@Slf4j
public class BaseCrudController<T, S extends BaseService<T, ID>, ID extends Serializable, R extends JpaRepository<T, ID>> {
    @Resource
    public Registration registration;
    @Resource
    protected S s;

    @Resource
    private DiscoveryClient discoveryClient;

    @PostMapping("/add")
    public Object add(@RequestBody T entity){
        T result=s.save(entity);
        doLog("插入", entity, result);
        return new CommonResult<T>(200, "插入成功", entity);
    }

    @PostMapping("/get")
    public Object get(@RequestBody ID id){
        T result=s.find(id);
        doLog("查询", id, result);
        if(result==null){
            return new CommonResult<T>(200, "查询失败，不存在id=%s的记录".formatted(id));
        }
        return new CommonResult<T>(200, "查询成功", result);
    }

    @PostMapping("/delete")
    public Object delete(@RequestBody ID id){
        s.delete(id);
        doLog("删除", id, null);
        return new CommonResult<ID>(200, "删除成功", id);
    }

    @PostMapping("/update")
    public Object update(@RequestBody T entity){
        T result = s.update(entity);
        doLog("更新", entity, result);
        return new CommonResult<T>(200, "更新成功", entity);
    }

    @PostMapping("/discovery")
    public Object discovery(){
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

    public void doLog(String operation, Object input, Object output){
        log.info("{}:{}>>>{}信息：{}>>>结果：{}", registration.getHost(), registration.getInstanceId(), operation, input, output);
    }
}
