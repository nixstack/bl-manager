package com.nixstack.service.cms.controller;

import com.nixstack.base.api.cms.ICmsPagePreviewController;
import com.nixstack.base.common.controller.BaseController;
import com.nixstack.service.cms.service.CmsPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletOutputStream;
import java.io.IOException;

@Controller
@RequestMapping("/cms/page")
public class CmsPagePreviewController extends BaseController implements ICmsPagePreviewController {
    @Autowired
    CmsPageService cmsPageService;

    //页面预览
    @RequestMapping(value="/preview/{pageId}",method = RequestMethod.GET)
    public void preview(@PathVariable("pageId") String pageId) throws IOException {
        //执行静态化
        String pageHtml = cmsPageService.getPageHtml(pageId);
        //通过response对象将内容输出
        ServletOutputStream outputStream = response.getOutputStream();
        response.setHeader("Content-type","text/html;charset=utf-8");
        outputStream.write(pageHtml.getBytes("utf-8"));

    }
}
