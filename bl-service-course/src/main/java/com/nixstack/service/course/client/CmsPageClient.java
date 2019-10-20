package com.nixstack.service.course.client;

import com.nixstack.base.common.response.ResultRes;
import com.nixstack.base.model.cms.entity.CmsPage;
import com.nixstack.base.model.cms.pojo.CmsPostPageResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "BL-SERVICE-CMS") // 指定cms注册到eureka服务中心的实例名称
public interface CmsPageClient {
    // 根据页面id查询查询页面信息，远程调用CMS请求数据
    @GetMapping("/api/cms/page/{id}")
    public ResultRes<CmsPage> findCmsPageById(@PathVariable("id") String id);

    //添加页面，用于课程预览
    @PostMapping("/api/cms/page")
    public ResultRes<CmsPage> saveCmsPage(@RequestBody CmsPage cmsPage);

    //一键发布页面
    @PostMapping("/api/cms/page/publish/quick")
    public ResultRes<CmsPostPageResult> postPageQuick(@RequestBody CmsPage cmsPage);
}
