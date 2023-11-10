package io.github.greenhandsw.core.context;

import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@NoArgsConstructor
public abstract class CodeEnvironment {
    static CodeEnvironment CE=null;
    ClassLoader classLoader=null;
    String envFileName="environment.properties";
    Properties properties=new Properties();


    void init() throws IOException {
        CE=this;
        clear();
        loadConfig();
    }

    private void loadConfig() throws IOException{
        this.classLoader=this.getClass().getClassLoader();
        InputStream is=this.classLoader.getResourceAsStream(this.envFileName);
        this.properties.load(is);
        assert is != null;
        is.close();
    }

    private final void clear(){

    }
}
