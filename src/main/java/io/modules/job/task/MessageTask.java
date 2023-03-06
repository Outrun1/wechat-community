package io.modules.job.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 *
 * 定期清理所有消息定时任务
 *
 *
 * @author
 * 
 */
@Component("messageTask")
public class MessageTask implements ITask {

    private Logger logger = LoggerFactory.getLogger(getClass());


    @Override
    public void run(String params){
        logger.debug("messageTask定时任务正在执行，参数为：{}", params);
    }
}