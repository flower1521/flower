package com.trans.actional;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * create by lcl on 2020/1/19 9:48
 */
public class ActivitiTest {
    @Test
    public void createTable() {
        //1.创建Activiti配置对象的实例
        ProcessEngineConfiguration configuration = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
        //2.设置数据库连接信息
        //设置数据库地址
        configuration.setJdbcUrl("jdbc:mysql://localhost:3306/btms?createDatabaseIfNotExist=true&useUnicode=true&characterEncoding=utf8");
        //数据库驱动
        configuration.setJdbcDriver("com.mysql.jdbc.Driver");
        //用户名
        configuration.setJdbcUsername("root");
        //密码
        configuration.setJdbcPassword("root");
        //设置数据库建表策略
        /**
         * DB_SCHEMA_UPDATE_TRUE: 如果不存在表就创建表，存在就直接使用
         * DB_SCHEMA_UPDATE_FALSE: 如果不存在表就抛出异常
         * DB_SCHEMA_UPDATE_CREATE_DROP: 每次都先删除表，再创建新的表
         */
        configuration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        //3.使用配置对象创建流程引擎实例（检查数据库连接等环境信息是否正确）
        ProcessEngine processEngine = configuration.buildProcessEngine();
        System.out.println(processEngine);
    }

    //1.发布流程
    @Test
    public void deploy() {
        //获取流程引擎
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //获取仓库服务的实例
        Deployment deployment = processEngine.getRepositoryService()
                .createDeployment()
                .addClasspathResource("processes/appliction.bpmn")
                .addClasspathResource("processes/appliction.applictionProcess.png")
                .key("applictionProcess")
                .deploy();
        System.out.println(deployment.getId());
    }

    //2.启动流程
    @Test
    public void startProcess() {
        //获取流程引擎对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //启动流程
        //使用流程定义的key启动流程实例，默认会按照最新版本启动流程实例
        ProcessInstance pi = processEngine.getRuntimeService().startProcessInstanceByKey("myProcess");
        System.out.println("pid:" +  pi.getId() + ",activitiId:" + pi.getActivityId());

        //查询当前任务
        String userName = "李四";
        TaskService taskService = processEngine.getTaskService();
        Task currentTask = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
        taskService.setAssignee(currentTask.getId(), userName);

        //申明任务
        taskService.claim(currentTask.getId(), userName);
        Map<String, Object> vars = new HashMap<String, Object>();
        vars.put("imputUser", userName);
        vars.put("volume", 1000);
        vars.put("groupid", 12);
        taskService.complete(currentTask.getId(), vars);
    }

    //3.查看任务
    @Test
    public void queryMyTask() {
        //指定任务办理者
        String assignee = "张三";//张三 李四
        //获取流程引擎对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //查询任务的列表
        List<Task> tasks = processEngine.getTaskService()
                .createTaskQuery() //创建任务查询对象
                .taskAssignee(assignee) //指定个人任务办理人
                .list();
        for (Task task : tasks) {
            System.out.println("taskId:" + task.getId() + ",taskName:" + task.getName());
            System.out.println("***************************");
        }
    }

    //4.办理任务
    @Test
    public void completeTask() {
        String taskId = "17502";
        //完成任务
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getTaskService().complete(taskId);
        System.out.println("完成任务");
    }

    //4.扩展办理任务
    @Test
    public void dealTask() {
        Map<String, Object> vars = new HashMap<String, Object>();
        vars.put("result", "result");
        vars.put("auditor", "10");
        vars.put("auditTime", new Date());
        vars.put("volume", 1000);
        vars.put("groupid", 1);
        vars.put("operate", "审核");

        //获取流程引擎对象
        String processInstanceId = "7501";
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        Task currentTask = taskService.createTaskQuery().processInstanceId(processInstanceId).active().singleResult();
        //指定个人任务的办理人
        taskService.setAssignee(currentTask.getId(), "10");;
        taskService.claim(currentTask.getId(), "10");
        taskService.complete(currentTask.getId(), vars);
    }

    //查询历史流程实例
    @Test
    public void queryHistoryProcessInstance() {
        String processInstanceId = "2501";
        //获取流程引擎对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        HistoricProcessInstance hpi = processEngine.getHistoryService()
                .createHistoricProcessInstanceQuery() //创建历史流程实例查询
                .processInstanceId(processInstanceId) //使用流程实例ID查询
                .singleResult();
        System.out.println("流程定义ID:" + hpi.getProcessDefinitionId());
        System.out.println("流程实例ID: " + hpi.getId());
        System.out.println(hpi.getStartTime() + "      "  + hpi.getEndTime() + "      " + hpi.getDurationInMillis());
    }

    @Test
    public void testProcessidQuery() {
        String processid = "2501";
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        Task task = processEngine.getTaskService().createTaskQuery().processInstanceId(processid).active().singleResult();
        System.out.println(task.getId());
    }

    //5.查看流程状态（判断流程是正在执行，还是已经结束）
    @Test
    public void queryProcessState() {
        String processInstanceId = "7501";//2502
        //通过流程实例ID查询流程实例
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        ProcessInstance pi = processEngine.getRuntimeService()
                .createProcessInstanceQuery() //创建流程实例查询，查询正在执行的流程实例
                .processInstanceId(processInstanceId) //按照流程实例ID查询
                .singleResult(); //返回唯一的结果集
        if (pi != null) {
            System.out.println("当前流程在：" + pi.getActivityId());
        } else {
            System.out.println("流程已结束！");
        }
    }
}
