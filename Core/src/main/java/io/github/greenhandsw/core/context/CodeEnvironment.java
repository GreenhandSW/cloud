package io.github.greenhandsw.core.context;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@NoArgsConstructor
public abstract class CodeEnvironment {
    static CodeEnvironment CE=null;
    ClassLoader classLoader=null;
//    String envFileName="application.yml";
    Properties properties=new Properties();
    @Value("${class.prefixes}")
    Set<String> class_prefixes;
    List<Class<?>> app_classes=new ArrayList<>();


    void init() throws IOException {
        CE=this;
        clear();
        setPrefixes();
//        loadConfig();
    }

//    private void loadConfig() throws IOException{
//        this.classLoader=this.getClass().getClassLoader();
//        InputStream is=this.classLoader.getResourceAsStream(this.envFileName);
//        this.properties.load(is);
//        assert is != null;
//        is.close();
//    }

    private void clear(){

    }

    /**
     * 设置包前缀（这里直接通过注解获取，然后输出一下）
     */
    private void setPrefixes(){
        System.out.printf("包前缀主要有这些：%s", String.join(", ", class_prefixes));
    }

//    List<Class<?>> findAppClasses(ClassType type, Class... assignableOrAnnotations) throws ClassNotFoundException, IOException{
//        List<Class<?>> result=this.app_classes;
//
//    }

    private List<Class<?>> findAppClasses(ClassType type, List<Class<?>> classes, Class assignableOrAnnotation){
        List<Class<?>> result=new ArrayList<>();
        boolean isAnnotation = assignableOrAnnotation.isAnnotation();
        Iterator<Class<?>> iterator=classes.iterator();
        while (true){
            Class<?> cls=null;
            while (true){
                if(!iterator.hasNext()){
                    return result;
                }
                cls=iterator.next();
                if(isAnnotation){
                    if(cls.getAnnotation(assignableOrAnnotation)!=null){
                        break;
                    }
                }else if(assignableOrAnnotation.isAssignableFrom(cls)){
                    break;
                }
            }

//            if(cls.getAnnotation(Reservat))
        }
    }
}
