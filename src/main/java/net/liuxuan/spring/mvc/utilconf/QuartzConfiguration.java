package net.liuxuan.spring.mvc.utilconf;

import net.liuxuan.spring.mvc.utilconf.QuartzJobs.DataBackupJob;
import net.liuxuan.spring.mvc.utilconf.QuartzJobs.MyJobTwo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved.
 * ***************************************************************************
 * 源文件名:  net.liuxuan.spring.mvc.utilconf.QuartzConfiguration
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/3/7 14:14
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2016/3/7  |    Moses       |     Created
 */
@Configuration
public class QuartzConfiguration {

    @Value("${spring.datasource.username}")
    private String mysqluser;
    @Value("${spring.datasource.password}")
    private String mysqlpassword;
    @Value("${spring.datasource.usedatabase}")
    private String mysqldb;
    @Value("${spring.datasource.backupdir}")
    private String backupPath;
    @Value("${spring.datasource.backupcron}")
    private String backupCron;


    @Value("${SprKi.upload.savepathroot}")
    private String picSavePathRoot;
    @Value("${SprKi.upload.savepathchild}")
    private String picSavePathChild;
    @Value("${SprKi.upload.backupattachpath}")
    private String attachBackupPath;

    @Bean
    public MethodInvokingJobDetailFactoryBean methodInvokingJobDetailFactoryBean() {
        MethodInvokingJobDetailFactoryBean obj = new MethodInvokingJobDetailFactoryBean();
        obj.setTargetBeanName("jobone");
        obj.setTargetMethod("myTask");
        return obj;
    }

    //Job  is scheduled for 3+1 times with the interval of 30 seconds
    @Bean
    public SimpleTriggerFactoryBean simpleTriggerFactoryBean() {
        SimpleTriggerFactoryBean stFactory = new SimpleTriggerFactoryBean();
        stFactory.setJobDetail(methodInvokingJobDetailFactoryBean().getObject());
        stFactory.setStartDelay(3000);
        stFactory.setRepeatInterval(30000);
        stFactory.setRepeatCount(3);
        return stFactory;
    }

    @Bean
    public JobDetailFactoryBean jobDetailFactoryBean() {
        JobDetailFactoryBean factory = new JobDetailFactoryBean();
        factory.setJobClass(MyJobTwo.class);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "ON_Time");
        map.put(MyJobTwo.COUNT, 1);
        factory.setJobDataAsMap(map);
        factory.setGroup("Quartz");
        factory.setName("timer");
        return factory;
    }

    //Job is scheduled after every 1 minute
    @Bean
    public CronTriggerFactoryBean cronTriggerFactoryBean() {
        CronTriggerFactoryBean stFactory = new CronTriggerFactoryBean();
        stFactory.setJobDetail(jobDetailFactoryBean().getObject());
        stFactory.setStartDelay(3000);
        stFactory.setName("cronTriggerFactoryBean");
        stFactory.setGroup("mygroup");
        stFactory.setCronExpression("0 0/1 * 1/1 * ? *");
        return stFactory;
    }
    @Bean(name="jobDataBackupFactoryBean")
    public JobDetailFactoryBean jobDataBackupFactoryBean() {
        JobDetailFactoryBean factory = new JobDetailFactoryBean();
        factory.setJobClass(DataBackupJob.class);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("username", mysqluser);
        map.put("password", mysqlpassword);
        map.put("db", mysqldb);
        map.put("backupPath", backupPath);
        map.put("attachPath",picSavePathRoot + picSavePathChild +"/");
        map.put("attachBackupPath",attachBackupPath);
        factory.setJobDataAsMap(map);
        factory.setGroup("Quartz");
        factory.setName("backup");
        return factory;
    }
    @Bean(name = "CronTriggerBackupFactoryBean")
    public CronTriggerFactoryBean CronTriggerBackup(){
        CronTriggerFactoryBean stFactory = new CronTriggerFactoryBean();
        stFactory.setJobDetail(jobDataBackupFactoryBean().getObject());
        stFactory.setStartDelay(3000);
        stFactory.setName("CronTriggerBackupFactoryBean");
        stFactory.setGroup("mygroup");
        /*
        按顺序依次为
        秒（0~59）
        分钟（0~59）
        小时（0~23）
        天（月）（0~31，但是你需要考虑你月的天数）
        月（0~11）
        天（星期）（1~7 1=SUN 或 SUN，MON，TUE，WED，THU，FRI，SAT）
        年份（1970－2099）

        其中每个元素可以是一个值(如6),一个连续区间(9-12),
        一个间隔时间(8-18/4)(/表示每隔4小时),一个列表(1,3,5),通配符。
        由于"月份中的日期"和"星期中的日期"这两个元素互斥的,必须要对其中一个设置？
         */
//        stFactory.setCronExpression("0 0 1 1/1 * ? *");
        stFactory.setCronExpression(backupCron);
//        stFactory.setCronExpression("0 0/1 * 1/1 * ? *");
        return stFactory;
    }

    /*
    Quartz的SchedulerFactory是标准的工厂类，不太适合在Spring环境下使用。
    此外，为了保证Scheduler能够感知Spring容器的生命周期，完成自动启动和关闭的操作，
    必须让Scheduler和Spring容器的生命周期相关联。以便在Spring容器启动后，
    Scheduler自动开始工作，而在Spring容器关闭前，自动关闭Scheduler。
    为此，Spring提供SchedulerFactoryBean，这个FactoryBean大致拥有以下的功能：
    1)以更具Bean风格的方式为Scheduler提供配置信息；
    2)让Scheduler和Spring容器的生命周期建立关联，相生相息；
    3)通过属性配置部分或全部代替Quartz自身的配置文件。
    spring容器中的bean只能放到SchedulerContext里面传入job中。
    */

    /**
     * 调用规划类
     *
     * @return
     */
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {

        SchedulerFactoryBean scheduler = new SchedulerFactoryBean();
        scheduler.setTriggers(
                simpleTriggerFactoryBean().getObject(),
                CronTriggerBackup().getObject(),
                cronTriggerFactoryBean().getObject()
        );
        return scheduler;
    }
}
