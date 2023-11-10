package io.github.greenhandsw.core.controller;

import io.github.greenhandsw.core.entity.CommonResult;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;

@Slf4j
public class BaseRestController<T, ID extends Serializable> {
    //    private Map<String, String> methodMappings;
    @Value("${request.service.url}")
    public String url;
    @Resource
    public RestTemplate restTemplate;

    @PostMapping("/add")
    public Object add(@RequestBody T entity) {
        return post(url + "/add", entity);
    }

    @PostMapping("/get")
    public Object get(@RequestBody ID id) {
        return post(url + "/get", id);
    }

    @PostMapping("/delete")
    public Object delete(@RequestBody ID id) {
        return post(url + "/delete", id);
    }

    @PostMapping("/update")
    public Object update(@RequestBody T entity) {
        return post(url + "/update", entity);
    }

    private CommonResult<T> post(String url, @Nullable Object request){
        return restTemplate.postForObject(url, request, CommonResult.class);
    }


//    private void getMappingValue(){
//        if(methodMappings==null){
//            methodMappings=new HashMap<>();
//            Method[] methods= getClass().getMethods();
//            for (Method method :
//                    methods) {
//                PostMapping mapping=method.getAnnotation(PostMapping.class);
//                if(mapping!=null){
//                    methodMappings.put(method.getName(), mapping.value()[0]);
//                }
//            }
//        }
//    }
}
