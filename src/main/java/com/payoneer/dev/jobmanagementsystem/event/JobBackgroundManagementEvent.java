package com.payoneer.dev.jobmanagementsystem.event;


import com.payoneer.dev.jobmanagementsystem.domain.EmailJob;
import com.payoneer.dev.jobmanagementsystem.domain.Job;
import com.payoneer.dev.jobmanagementsystem.domain.ReminderJob;
import com.payoneer.dev.jobmanagementsystem.services.EmailJobService;
import com.payoneer.dev.jobmanagementsystem.services.JobService;
import com.payoneer.dev.jobmanagementsystem.services.ReminderJobService;
import com.payoneer.dev.jobmanagementsystem.utils.EmailUtil;
import com.payoneer.dev.jobmanagementsystem.utils.ReminderUtil;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Comparator;

/*    this is a event handler, any job that postponed or scheduled to be executed later this service will take care of it*/
@Component
@RequiredArgsConstructor
@Log4j2
@PropertySource("classpath:config.properties")
public class JobBackgroundManagementEvent {

    private final EmailUtil emailUtil;
    private final ReminderUtil reminderUtil;
    private final JobService jobService;

    @Value("${thread.enablebackgroundjobs}")
    private boolean enableBackgroundProcessing = true;

    @Synchronized // avoid locking issue
    public void setEnableBackgroundProcessing(boolean status){
        enableBackgroundProcessing = status;
    }

     /* see how amazing strategy = InheritanceType.JOINED which i can easily call get all jobs from job table
     it will retrun all data from both tables and then i can easily deal with them with OOP polymorphism  */
    //@Scheduled(fixedRate = 2000)
    public void batchlookup(){
        if(!enableBackgroundProcessing){ // to avoid batch collusion or handle data that held by another previous thread
            return;
        }
        // by using this mechanism the data will never be inconsistency and if there's running batch the method will return as above :)
        setEnableBackgroundProcessing(false);
        // fetch data which has :
        // 1-Queued status
        // 2- execution dateTime with now() and now() + 2 seconds
        // 3- sort them based on Job Propriety --> this can be straightforward from the query itself but i prefer Onfly implementation
        // 4- execute them on parallel approach
        jobService.findAll()
                .stream()
                .sorted(Comparator.comparing(val -> val.getJobPriority().getValue()))
                .forEach(job -> jobHandler(job));
        setEnableBackgroundProcessing(true);
    }

    @Async
    public void jobHandler(Job job){ // in case N of possible jobs increased i would like to replace switch case with design pattern approach
        switch (job.getJobType()){
            case "email":
                emailUtil.sendAndFlush((EmailJob) job);
                break;
            case "reminder":
                reminderUtil.sendAndFlush((ReminderJob) job);
                break;
            default:
                 /*inform any related parties
                 here we can skip the new type of jobs since it can be handled by another service completely isolated
                 and there will be NO CHANGE on current implementation */
                System.out.println("its related to another job type");
        }
    }
    
}
