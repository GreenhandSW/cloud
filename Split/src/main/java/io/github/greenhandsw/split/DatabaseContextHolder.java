package io.github.greenhandsw.split;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class DatabaseContextHolder {
    public enum DbType {
        MASTER,
        SLAVE1,
        SLAVE2;
    }
    private static final ThreadLocal<DbType> contextHolder=new ThreadLocal<>();
    private static final AtomicInteger counter=new AtomicInteger(-1);

    public static void set(DbType dbType){
        contextHolder.set(dbType);
    }

    public static DbType get(){
        return contextHolder.get();
    }
    public static void remove(){
        contextHolder.remove();
    }

    public static void master(){
        DbType db=DbType.MASTER;
        set(db);
        log.info("切换到数据源"+db.name());
    }

    public static void slave1(){
        DbType db=DbType.SLAVE1;
        set(db);
        log.info("切换到数据源"+db.name());
    }

    public static void slave2(){
        DbType db=DbType.SLAVE2;
        set(db);
        log.info("切换到数据源"+db.name());
    }

    public static void slave(){
        final int slaves=2;
        int index=counter.getAndIncrement()%slaves;
        if(counter.get()==Integer.MAX_VALUE){
            counter.set(0);
        }
        switch (index){
            case 0: slave1();break;
            case 1:slave2();break;
            default:
        }
    }
}
