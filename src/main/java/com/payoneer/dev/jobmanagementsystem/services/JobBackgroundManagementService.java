package com.payoneer.dev.jobmanagementsystem.services;


import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
@PropertySource("classpath:config.properties")
public class JobBackgroundManagementService {

    @Value("${thread.enablebackgroundjobs}")
    private boolean enableBackgroundProcessing = true;

    @Synchronized // avoid locking issue
    public void setEnableBackgroundProcessing(boolean status){
        enableBackgroundProcessing = status;
    }

    //@Scheduled(fixedRate = 2000) // every two seconds
    public void batchlookup(){
        if(enableBackgroundProcessing) {
            setEnableBackgroundProcessing(false);
            System.out.println("running from# " + this.getClass().getName());
        }else {
            System.out.println("the activiation is# " + enableBackgroundProcessing);
        }
        if(enableBackgroundProcessing) {
            System.out.println("running from# " + this.getClass().getName());
        }else {
            System.out.println("the activiation is# " + enableBackgroundProcessing);
        }
        setEnableBackgroundProcessing(true);
    }
    
}
