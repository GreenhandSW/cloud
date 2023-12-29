package io.github.greenhandsw.core.context;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class EnvironmentListener extends CodeEnvironment implements ServletContextListener {
    private ServletContext servletContext;

    @Override
    public void contextInitialized(ServletContextEvent event){
        this.servletContext=event.getServletContext();
        try {
            this.init();
        }catch (Throwable throwable){
            throwable.printStackTrace();
            System.exit(-1);
        }
        this.initEnvLocation();
    }

    private void initEnvLocation(){
//        try {
//            List<Class<?>> classes=ClassResource
//        }
    }

}
