package com.chuangxin.musicroom.client.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

/**
 * 工程启动完成后执行的程序
 */
@Component
@Slf4j
public class ApplicationRunnerImpl implements ApplicationRunner {

    @Value("${enviroment}")
    private String enviroment;
    
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.debug("Package mode " + enviroment);

    }
}
