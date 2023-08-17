package com.ctrlcv.ersentinel_springboot.component;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import com.ctrlcv.ersentinel_springboot.data.entity.Log;
import com.ctrlcv.ersentinel_springboot.data.repository.LogRepository;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Slf4j
@Getter
@Setter
@Component
public class MariaDBAppender extends UnsynchronizedAppenderBase<ILoggingEvent> implements ApplicationContextAware {
    private static LogRepository logRepository;

    @Override
    protected void append(ILoggingEvent eventObject){
        Log log = new Log(eventObject.getFormattedMessage());

        logRepository.save(log);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(applicationContext.getAutowireCapableBeanFactory().getBean(LogRepository.class)!=null){
            logRepository = (LogRepository) applicationContext.getAutowireCapableBeanFactory().getBean(LogRepository.class);
        }
    }
}
