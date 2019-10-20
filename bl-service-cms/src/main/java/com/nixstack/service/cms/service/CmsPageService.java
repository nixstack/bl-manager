package com.nixstack.service.cms.service;

import com.alibaba.fastjson.JSON;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.nixstack.base.common.exception.ExceptionCast;
import com.nixstack.base.common.code.ECommonCode;
import com.nixstack.base.common.response.PageResult;
import com.nixstack.base.common.response.PageResultRes;
import com.nixstack.base.common.response.ResultRes;
import com.nixstack.base.model.cms.entity.*;
import com.nixstack.base.model.cms.pojo.CmsPostPageResult;
import com.nixstack.base.model.cms.request.LinkReq;
import com.nixstack.service.cms.config.RabbitmqConfig;
import com.nixstack.service.cms.dao.*;
import com.nixstack.base.model.cms.request.QueryPageReq;
import com.nixstack.base.common.code.ECmsCode;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringSubstitutor;
import org.bson.types.ObjectId;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

//@Component
@Service
public class CmsPageService {
    @Autowired
    CmsPageDao cmsPageDao;

    @Autowired
    CmsConfigDao cmsConfigDao;

    @Autowired
    CmsTemplateDao cmsTemplateDao;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    GridFsTemplate gridFsTemplate;

    @Autowired
    GridFSBucket gridFSBucket;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    CmsSiteDao cmsSiteDao;

    @Autowired
    CmsServerDao cmsServerDao;


    /**
     * 页面查询
     * @param page 页码，从1开始计数
     * @param size 每页记录数
     * @param queryPageReq 查询条件
     * @return
     */
    public PageResultRes find(int page, int size, QueryPageReq queryPageReq) {
        if(queryPageReq == null){
            queryPageReq = new QueryPageReq();
        }
        //自定义条件查询
        //定义条件匹配器
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains());
        //条件值对象
        CmsPage cmsPage = new CmsPage();
        //设置条件值（站点id）
        if(StringUtils.isNotEmpty(queryPageReq.getSiteId())){
            cmsPage.setSiteId(queryPageReq.getSiteId());
        }

        //设置模板id作为查询条件
        if(StringUtils.isNotEmpty(queryPageReq.getTemplateId())){
            cmsPage.setTemplateId(queryPageReq.getTemplateId());
        }
        //设置页面别名作为查询条件
        if(StringUtils.isNotEmpty(queryPageReq.getPageAliase())){
            cmsPage.setPageAliase(queryPageReq.getPageAliase());
        }

        //定义条件对象Example
        Example<CmsPage> example = Example.of(cmsPage,exampleMatcher);

        // 分页参数
        if (page < 0) {
            page = 0;
        }
        page = page -1;

        if (size <= 0) {
            size = 10;
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<CmsPage> list = cmsPageDao.findAll(pageable);

//        Page<CmsPage> list = cmsPageDao.findAll(example,pageable);//实现自定义条件查询并且分页查询
        PageResult pageResult = new PageResult();
        pageResult.setList(list.getContent()); // 数据列表
        pageResult.setTotal(list.getTotalElements()); // 数据总记录数
        PageResultRes pageResultRes = new PageResultRes(ECommonCode.SUCCESS, pageResult);
        return pageResultRes;
    }

    //新增页面
    public ResultRes<CmsPage> add(CmsPage cmsPage) {
        if(cmsPage == null){
            //抛出异常，非法参数异常..指定异常信息的内容

        }
        //校验页面名称、站点Id、页面webpath的唯一性
        //根据页面名称、站点Id、页面webpath去cms_page集合，如果查到说明此页面已经存在，如果查询不到再继续添加
        CmsPage cmsPage1 = cmsPageDao.findByPageNameAndSiteIdAndPageWebPath(cmsPage.getPageName(), cmsPage.getSiteId(), cmsPage.getPageWebPath());
        if(cmsPage1!=null){
            //页面已经存在
            //抛出异常，异常内容就是页面已经存在
            ExceptionCast.cast(ECmsCode.CMS_ADDPAGE_EXISTSNAME);
        }

        //调用dao新增页面
        cmsPage.setPageId(null);
        cmsPageDao.save(cmsPage);
        return new ResultRes(ECommonCode.SUCCESS,cmsPage);

    }

    //根据页面id查询页面
    public CmsPage getById(String id){
        Optional<CmsPage> optional = cmsPageDao.findById(id);
        if(optional.isPresent()){
            CmsPage cmsPage = optional.get();
            return cmsPage;
        }
        return null;
    }

    // ResultRes<CmsPage>
    //根据页面id查询页面
    public ResultRes<CmsPage> getByIdRes(String id){
        return new ResultRes<>(ECommonCode.SUCCESS, this.getById(id));
    }

    //修改页面
    public ResultRes<CmsPage> update(String id,CmsPage cmsPage){
        //根据id从数据库查询页面信息
        CmsPage one = this.getById(id);
        if(one!=null){
            //准备更新数据
            //设置要修改的数据
            //更新模板id
            one.setTemplateId(cmsPage.getTemplateId());
            //更新所属站点
            one.setSiteId(cmsPage.getSiteId());
            //更新页面别名
            one.setPageAliase(cmsPage.getPageAliase());
            //更新页面名称
            one.setPageName(cmsPage.getPageName());
            //更新访问路径
            one.setPageWebPath(cmsPage.getPageWebPath());
            //更新物理路径
            one.setPagePhysicalPath(cmsPage.getPagePhysicalPath());
            //更新dataUrl
            one.setDataUrl(cmsPage.getDataUrl());
            //提交修改
            cmsPageDao.save(one);
            return new ResultRes(ECommonCode.SUCCESS,one);
        }
        //修改失败
        return new ResultRes(ECommonCode.FAIL,null);

    }

    //根据id删除页面
    public ResultRes delete(String id){
        //先查询一下
        Optional<CmsPage> optional = cmsPageDao.findById(id);
        if(optional.isPresent()){
            cmsPageDao.deleteById(id);
            return new ResultRes(ECommonCode.SUCCESS);
        }
        return new ResultRes(ECommonCode.FAIL);
    }

    public CmsConfig getConfigById(String id) {
        Optional<CmsConfig> optional = cmsConfigDao.findById(id);
        if(optional.isPresent()){
            CmsConfig cmsConfig = optional.get();
            return cmsConfig;
        }
        return null;
    }

    /**
     * 静态化程序获取页面的DataUrl
     *
     * 静态化程序远程请求DataUrl获取数据模型。
     *
     * 静态化程序获取页面的模板信息
     *
     * 执行页面静态化
     */
    public String getPageHtml(String pageId){

        //获取数据模型
        Map model = getModelByPageId(pageId);
        if(model == null){
            //数据模型获取不到
            ExceptionCast.cast(ECmsCode.CMS_GENERATEHTML_DATAISNULL);
        }

        //获取页面的模板信息
        String template = getTemplateByPageId(pageId);
        if(StringUtils.isEmpty(template)){
            ExceptionCast.cast(ECmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }

        //执行静态化
        String html = generateHtml(template, model);
        return html;

    }

    //获取数据模型
    private Map getModelByPageId(String pageId){
        //取出页面的信息
        CmsPage cmsPage = this.getById(pageId);
        if(cmsPage == null){
            //页面不存在
            ExceptionCast.cast(ECmsCode.CMS_PAGE_NOTEXISTS);
        }
        //取出页面的dataUrl
        String dataUrl = cmsPage.getDataUrl();
        if(StringUtils.isEmpty(dataUrl)){
            //页面dataUrl为空
            ExceptionCast.cast(ECmsCode.CMS_GENERATEHTML_DATAURLISNULL);
        }
        //通过restTemplate请求dataUrl获取数据
        ResponseEntity<Map> forEntity = restTemplate.getForEntity(dataUrl, Map.class);
        Map body = forEntity.getBody();
        return (Map) body.get("data");

    }

    //获取页面的模板信息
    private String getTemplateByPageId(String pageId){
        //取出页面的信息
        CmsPage cmsPage = this.getById(pageId);
        if(cmsPage == null){
            //页面不存在
            ExceptionCast.cast(ECmsCode.CMS_PAGE_NOTEXISTS);
        }
        //获取页面的模板id
        String templateId = cmsPage.getTemplateId();
        if(StringUtils.isEmpty(templateId)){
            ExceptionCast.cast(ECmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }
        //查询模板信息
        Optional<CmsTemplate> optional = cmsTemplateDao.findById(templateId);
        if(optional.isPresent()){
            CmsTemplate cmsTemplate = optional.get();
            //获取模板文件id
            String templateFileId = cmsTemplate.getTemplateFileId();
            //从GridFS中取模板文件内容
            //根据文件id查询文件
            GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(templateFileId)));

            //打开一个下载流对象
            GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
            //创建GridFsResource对象，获取流
            GridFsResource gridFsResource = new GridFsResource(gridFSFile,gridFSDownloadStream);
            //从流中取数据
            try {
                String content = IOUtils.toString(gridFsResource.getInputStream(), "utf-8");
                return content;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;

    }

    //执行静态化
    private String generateHtml(String templateContent,Map model ){
        //创建配置对象
        Configuration configuration = new Configuration(Configuration.getVersion());
        //创建模板加载器
        StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
        stringTemplateLoader.putTemplate("template",templateContent);
        //向configuration配置模板加载器
        configuration.setTemplateLoader(stringTemplateLoader);
        //获取模板
        try {
            Template template = configuration.getTemplate("template");
            //调用api进行静态化
            String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
            return content;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // 发布页面
    public ResultRes publish(String pageId) {
        // 执行页面静态化
        String pageHtml = this.getPageHtml(pageId);

        // 将页面静态化文件存储到GridFs中
        CmsPage cmsPage = saveHtml(pageId, pageHtml);

        //向MQ发消息
        sendPostPage(pageId);

        return new ResultRes(ECommonCode.SUCCESS);
    }

    //保存html到GridFS
    private CmsPage saveHtml(String pageId,String htmlContent){
        //先得到页面信息
        CmsPage cmsPage = this.getById(pageId);
        if(cmsPage == null){
            ExceptionCast.cast(ECommonCode.INVALID_PARAM);
        }
        ObjectId objectId = null;
        try {
            //将htmlContent内容转成输入流
            InputStream inputStream = IOUtils.toInputStream(htmlContent, "utf-8");
            //将html文件内容保存到GridFS
            objectId = gridFsTemplate.store(inputStream, cmsPage.getPageName());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //将html文件id更新到cmsPage中
        cmsPage.setHtmlFileId(objectId.toHexString());
        cmsPageDao.save(cmsPage);
        return cmsPage;
    }

    //向mq 发送消息
    private void sendPostPage(String pageId){
        //得到页面信息
        CmsPage cmsPage = this.getById(pageId);
        if(cmsPage == null){
            ExceptionCast.cast(ECommonCode.INVALID_PARAM);
        }
        //创建消息对象
        Map<String,String> msg = new HashMap<>();
        msg.put("pageId",pageId);
        //转成json串
        String jsonString = JSON.toJSONString(msg);
        //发送给mq
        //站点id
        String siteId = cmsPage.getSiteId();
        rabbitTemplate.convertAndSend(RabbitmqConfig.EX_ROUTING_CMS_POSTPAGE,siteId,jsonString);
    }

    // 保存页面，有则更新，没有则新增
    public ResultRes<CmsPage> save(CmsPage cmsPage) {
        //判断页面是否存在
        CmsPage one = cmsPageDao.findByPageNameAndSiteIdAndPageWebPath(cmsPage.getPageName(), cmsPage.getSiteId(), cmsPage.getPageWebPath());
        if(one!=null){
            //进行更新
            return this.update(one.getPageId(),cmsPage);
        }
        return this.add(cmsPage);
    }

    //一键发布页面
    public ResultRes<CmsPostPageResult> postPageQuick(CmsPage cmsPage) {
        //将页面信息存储到cms_page 集合中
        ResultRes<CmsPage> save = this.save(cmsPage);
        if(save.getFlag() != 0){
            ExceptionCast.cast(ECommonCode.FAIL);
        }
        //得到页面的id
        CmsPage cmsPageSave = save.getData();
        String pageId = cmsPageSave.getPageId();

        //执行页面发布（先静态化、保存GridFS，向MQ发送消息）
        ResultRes post = this.post(pageId);
        if(post.getFlag() != 0){
            ExceptionCast.cast(ECommonCode.FAIL);
        }
        //拼接页面Url= cmsSite.siteDomain+cmsSite.siteWebPath+ cmsPage.pageWebPath + cmsPage.pageName
        //取出站点id
        String siteId = cmsPageSave.getSiteId();
        CmsSite cmsSite = this.findCmsSiteById(siteId);
        //页面url
        String pageUrl =cmsSite.getSiteDomain() + cmsSite.getSiteWebPath() + cmsPageSave.getPageWebPath() + cmsPageSave.getPageName();
        CmsPostPageResult cmsPostPageResult = new CmsPostPageResult();
        cmsPostPageResult.setPageUrl(pageUrl);
        return new ResultRes(ECommonCode.SUCCESS, cmsPostPageResult);
    }

    //页面发布
    public ResultRes post(String pageId){
        //执行页面静态化
        String pageHtml = this.getPageHtml(pageId);
        //将页面静态化文件存储到GridFs中
        CmsPage cmsPage = saveHtml(pageId, pageHtml);
        //向MQ发消息
        sendPostPage(pageId);
        return new ResultRes(ECommonCode.SUCCESS);
    }

    //根据站点id查询站点信息
    public CmsSite findCmsSiteById(String siteId){
        Optional<CmsSite> optional = cmsSiteDao.findById(siteId);
        if(optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    // 页面预览、发布前关联数据模型、服务、站点，实现配置化需求
    public ResultRes link(LinkReq linkReq) {
        if (StringUtils.isEmpty(linkReq.getPageId())) {
            return new ResultRes(ECommonCode.INVALID_PARAM);
        }

        CmsPage cmsPage = getById(linkReq.getPageId());

        // 如果服务id非空
        if (StringUtils.isNotEmpty(linkReq.getServerId())) {
            Optional<CmsServer> optionalCmsServer = cmsServerDao.findById(linkReq.getServerId());
            if (optionalCmsServer.isPresent()) {
                CmsServer cmsServer = optionalCmsServer.get();
                if (StringUtils.isNotEmpty(linkReq.getConfigId())) {
                    Optional<CmsConfig> optionalCmsConfig = cmsConfigDao.findById(linkReq.getConfigId());
                    if (optionalCmsConfig.isPresent()) {
                        CmsConfig cmsConfig = optionalCmsConfig.get();
                        HashMap hashMap = new HashMap();
                        hashMap.put("configId", cmsConfig.getId());
                        StringSubstitutor stringSubstitutor = new StringSubstitutor(hashMap);

                        cmsPage.setDataUrl("http" + cmsServer.getIp() + ":" + cmsServer.getPort() + stringSubstitutor.replace(cmsServer.getWebPath()));
                    }
                }
                cmsPageDao.save(cmsPage);
            }
        }


        return ResultRes.SUCCESS();
    }
}
