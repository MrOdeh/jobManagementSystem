package com.payoneer.dev.jobmanagementsystem.event;


import com.payoneer.dev.jobmanagementsystem.services.JobService;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Comparator;

/*    this is a event handler, any job that postponed or scheduled to be executed later this service will take care of it*/
@Component
@RequiredArgsConstructor
@Log4j2
@PropertySource("classpath:config.properties")
public class JobBackgroundManagementEvent {

    private final JobService jobService;
    private final QueueHandler queueHandler;

    @Value("${thread.enablebackgroundjobs}")
    private boolean enableBackgroundProcessing = true;

    @Synchronized // avoid locking issue
    public void setEnableBackgroundProcessing(boolean status){
        enableBackgroundProcessing = status;
    }

     /* see how amazing strategy = InheritanceType.JOINED which i can easily call get all jobs from job table
     it will retrun all data from both tables and then i can easily deal with them with OOP polymorphism  */
    @Scheduled(fixedRate = 2000)
    public void batchlookup(){
        if(!enableBackgroundProcessing){ // to avoid batch collusion or handle data that held by another thread
            return;
        }
        // by using this mechanism the data will never be inconsistency and if there's running batch the method will return as above :)
        setEnableBackgroundProcessing(false);
        // fetch data which has :
        // 1-Queued status
        // 2-execution dateTime with now() and now() + 2 seconds
        // then --
        // 3- sort them based on Job Propriety --> this can be straightforward from the query itself but i prefer Onfly implementation
        // 4- execute them on parallel approach
        jobService.findAllUnprocessedJobs()
                .stream()
                .sorted(Comparator.comparing(val -> val.getJobPriority().getValue()))
                .forEach(job -> queueHandler.jobHandler(job));
        setEnableBackgroundProcessing(true);
    }

}
