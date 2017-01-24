package net.liuxuan.spring.mvc.utilconf.QuartzJobs;

import net.liuxuan.spring.Helper.SystemHelper;
import net.liuxuan.spring.Helper.ZipHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.*;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Copyright (c) 2010-2017.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.spring.mvc.utilconf.QuartzJobs.DataBackupJob
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2017/1/6 9:50
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2017/1/6  |    Moses       |     Created
 */

//此标记说明在执行完Job的execution方法后保存JobDataMap当中固定数据,
// 在默认情况下 也就是没有设置 @PersistJobDataAfterExecution的时候 每个job都拥有独立JobDataMap
// 否则改任务在重复执行的时候具有相同的JobDataMap
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class DataBackupJob extends QuartzJobBean {

    public static final String LAST_BACKUP = "LastBackup";
    private static Log logger = LogFactory.getLog(DataBackupJob.class);
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
//    private String path = SystemHelper.getRootPath() + "/backup/";
//    private String sqlFileName = null;
    //    private String lastStr = null;
    private String name;

    public void excuteCmd(String lastStr) throws IOException {
//        String temp = ("cmd /c " + lastStr).replaceAll("//", "\"\\\\\"").replaceAll("\\\\", "\"\\\\\"");//把\换成windows的地址分隔符/
        String temp = ("cmd /c " + lastStr);//把\换成windows的地址分隔符/
//        temp = temp.replaceFirst("\"", "");

        Runtime.getRuntime().exec(temp);
//        System.out.println("已经完成计算 数据库文件备份----------");

    }

    protected void executeInternal(JobExecutionContext ctx) throws JobExecutionException {

        JobDataMap dataMap = ctx.getJobDetail().getJobDataMap();

        String username = dataMap.getString("username");
        String password = dataMap.getString("password");
        String db = dataMap.getString("db");
        String backupPath = dataMap.getString("backupPath");
        String attachPath = dataMap.getString("attachPath");
        String attachBackupPath = dataMap.getString("attachBackupPath");

        //db 备份地址
        String mysqlBackupPath;
        mysqlBackupPath = judgePathRelativity(backupPath);
        mysqlBackupPath = getUsableExistsPath(mysqlBackupPath);

        //利用mysql的mysqldump 工具，前提是你要吧mysql 的bin目录加入你的系统path
        StringBuffer mysqlDumpString = new StringBuffer("mysqldump ");
        mysqlDumpString.append(" -u").append(username).append(" -p").append(password).append(" ").append(db).append(" > ");
        String sqlFileName = mysqlBackupPath + "/" + db + "_" + sdf.format(ctx.getFireTime()) + ".sql";
        File tempfile = new File(sqlFileName);
        mysqlDumpString.append(tempfile.getAbsolutePath());
        try {
            logger.info("数据库备份指令：  " + mysqlDumpString.toString());
            excuteCmd(mysqlDumpString.toString());
            logger.info("Success Backup to  " + sqlFileName + "  ,Finished at:" + sdf.format(new Date()));
        } catch (IOException e) {
            logger.error(e);
//            e.printStackTrace();
        }

        File attatchPath_f = new File(attachPath);
        if(attatchPath_f.exists()&&attatchPath_f.isDirectory()){
            //只有是目录才备份
            attachBackupPath = judgePathRelativity(attachBackupPath);
            attachBackupPath = getUsableExistsPath(attachBackupPath);
            /*robocopy /E /XO d:/zhichi/SpringBootTest-1.2/static/uploaded d:\backupfiles >> d:/backupfiles/backup.log*/
            StringBuffer attatchBackupString = new StringBuffer("robocopy  /XO /E  ");
            attatchBackupString.append(attatchPath_f.getAbsolutePath()).append(" ");
            attatchBackupString.append(attachBackupPath);
            attatchBackupString.append(" >> ").append(attachBackupPath).append("\\backup.log");
            try {
                logger.info("文件备份指令：  " + attatchBackupString.toString());
                excuteCmd(attatchBackupString.toString());
                logger.info("Success Backup to  " + attachBackupPath+"\\backup.log" + "  ,Finished at:" + sdf.format(new Date()));
            } catch (IOException e) {
                logger.error(e);
//            e.printStackTrace();
            }
        }






        String[] sl = {sqlFileName};
        boolean zipped = ZipHelper.zipCompress(sl, mysqlBackupPath + "\\" + db + ".zip");
        if (zipped) {
            logger.info("压缩了sql文件");
        }


//        JobKey jobKey = ctx.getJobDetail().getKey();
//        System.out.println(jobKey + ": " + name + ": " + cnt);
//        cnt++;
//        dataMap.put(LAST_BACKUP, cnt);

    }

    /**
     * 检查目的是否是可用的目录，如无则创建
     * @param path
     * @return
     */
    private static String getUsableExistsPath(String path) {
        File toPath = new File(path);
        if (toPath.exists()) {
            if (toPath.isFile()) {
                toPath = new File(path + "_Backup");
                toPath.mkdirs();
            }
        } else {
            toPath.mkdirs();
        }
        path = toPath.getAbsolutePath();
        return path;
    }

    /**
     * 分析目录是绝对目录还是以/开头的相对目录，如果是绝对目录则进行处理成绝对地址。
     * @param backupPath
     * @return
     */
    private static String judgePathRelativity(String backupPath) {
        String path;
        if (backupPath.startsWith("/")) {
            path = SystemHelper.getRootPath() + backupPath;
        } else {
            path = backupPath;
        }
        return path;
    }

    public void setName(String name) {
        this.name = name;
    }

}
