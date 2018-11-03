package demo

import org.activiti.engine.ProcessEngines

fun main(args: Array<String>) {
    try {
        //创建流程引擎
        val engine = ProcessEngines.getDefaultProcessEngine()

        //得到流程存储服务组件
        val repositoryService = engine.repositoryService

        //获取流程运行时组件
        val runtimeService = engine.runtimeService

        //获取流程任务组件
        val taskService = engine.taskService

        //部署流程文件
        repositoryService.createDeployment().addClasspathResource("bpmn/First.bpmn").deploy()

        //启动流程
        runtimeService.startProcessInstanceByKey("process1")

        //查询第一个任务
        val task1 = taskService.createTaskQuery().singleResult()!!
        println("task1 name:${task1.name}")

        //完成第一个任务
        taskService.complete(task1.id)

        //查询第二个任务
        val task2 = taskService.createTaskQuery().singleResult()!!
        println("task2.name ${task2.name}")

        //完成第二个任务
        taskService.complete(task2.id)

        val moreTask = taskService.createTaskQuery().singleResult()
        println("more task: $moreTask")
        //退出
        System.exit(0)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
