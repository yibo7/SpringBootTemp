package com.ebsite.tempsite;

import com.ebsite.tempsite.queuehandler.QueueToDbOpt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import java.util.Map;

/**
 * 系统启动时加载
 */
public class StartRun implements CommandLineRunner {
//    public static final ObjectMapper objectMapper = new ObjectMapper();
    /**
     * 系统参数
     */
    public static Map<String,String> AppArgs ;
    @Autowired
    private QueueToDbOpt logOpt;
    @Override
    public void run(String... args) throws Exception {
        logOpt.startToDb();
    }
}
