package com.parvar.fullnode;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parvar.fullnode.logopt.LogOptToDb;
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
    private LogOptToDb logOptToDb;
    @Override
    public void run(String... args) throws Exception {
        logOptToDb.startToDb();
    }
}
