package com.nixstack.service.course.client;

import com.nixstack.base.common.response.ResultRes;
import com.nixstack.base.model.cms.entity.CmsPage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CmsPageClientTest {
    @Autowired
    CmsPageClient cmsPageClient; // 接口代理对象，由Feign生成

    @Test
    public void findCmsPageById() {
        ResultRes<CmsPage> cmsPageRes = cmsPageClient.findCmsPageById("5a795ac7dd573c04508f3a56");
        System.out.println(cmsPageRes.getData());
    }
}
