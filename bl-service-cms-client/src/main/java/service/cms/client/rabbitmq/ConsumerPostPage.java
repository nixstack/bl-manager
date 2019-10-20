package service.cms.client.rabbitmq;

import com.alibaba.fastjson.JSON;
import com.nixstack.base.model.cms.entity.CmsPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.cms.client.service.CmsClientService;

import java.util.Map;

/**
 * 监听MQ，接收页面发布消息
 */
@Component
public class ConsumerPostPage {
    private static  final Logger LOGGER = LoggerFactory.getLogger(ConsumerPostPage.class);
    @Autowired
    CmsClientService cmsClientService;

    @RabbitListener(queues = {"${boundless.mq.queue}"})
    public void postPage(String msg){
        //解析消息
        Map map = JSON.parseObject(msg, Map.class);
        //得到消息中的页面id
        String pageId = (String) map.get("pageId");
        //校验页面是否合法
        CmsPage cmsPage = cmsClientService.findCmsPageById(pageId);
        if(cmsPage == null){
            LOGGER.error("receive postpage msg,cmsPage is null,pageId:{}",pageId);
            return ;
        }
        //调用service方法将页面从GridFs中下载到服务器
        cmsClientService.savePageToServerPath(pageId);

    }
}
