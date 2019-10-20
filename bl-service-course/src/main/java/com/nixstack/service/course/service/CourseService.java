package com.nixstack.service.course.service;

import com.alibaba.fastjson.JSON;
import com.nixstack.base.common.code.ECommonCode;
import com.nixstack.base.common.code.ECourseCode;
import com.nixstack.base.common.exception.CustomException;
import com.nixstack.base.common.exception.ExceptionCast;
import com.nixstack.base.common.response.PageResult;
import com.nixstack.base.common.response.PageResultRes;
import com.nixstack.base.common.response.ResultRes;
import com.nixstack.base.model.cms.entity.CmsPage;
import com.nixstack.base.model.cms.pojo.CmsPostPageResult;
import com.nixstack.base.model.course.entity.*;
import com.nixstack.base.model.course.ext.CoursePublishResult;
import com.nixstack.base.model.course.ext.CourseView;
import com.nixstack.base.model.course.ext.TeachplanNode;
import com.nixstack.service.course.client.CmsPageClient;
import com.nixstack.service.course.dao.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CourseService {
    @Autowired
    TeachplanMapper teachplanMapper;

    @Autowired
    CourseBaseDao courseBaseDao;

    @Autowired
    TeachPlanDao teachPlanDao;

    @Autowired
    CoursePicDao coursePicDao;

    @Autowired
    CourseMarketDao courseMarketDao;

    @Autowired
    CmsPageClient cmsPageClient;

    @Value("${course-publish.dataUrlPre}")
    private String publish_dataUrlPre;
    @Value("${course-publish.pagePhysicalPath}")
    private String publish_page_physicalpath;
    @Value("${course-publish.pageWebPath}")
    private String publish_page_webpath;
    @Value("${course-publish.siteId}")
    private String publish_siteId;
    @Value("${course-publish.templateId}")
    private String publish_templateId;
    @Value("${course-publish.previewUrl}")
    private String previewUrl;

    @Autowired
    CoursePubDao coursePubDao;

    @Autowired
    CategoryService categoryService;

    // 查询所以课程
    public PageResultRes<CourseBase> findAll() {
        List<CourseBase> all = courseBaseDao.findAll();
        PageResult pageResult = new PageResult();
        pageResult.setList(all);
        pageResult.setTotal(all.size());
        return new PageResultRes(ECommonCode.SUCCESS, pageResult);
    }

    // 新增课程基本信息
    public ResultRes<CourseBase> addBasic(CourseBase courseBase) {
        if (courseBase == null) {
            ExceptionCast.cast(ECommonCode.INVALID_PARAM);
        }

        CourseBase courseBase1 = courseBaseDao.findByName(courseBase.getName());
        if (courseBase1 != null) {
            ExceptionCast.cast(ECommonCode.RECORD_EXISTED);
        }

        CourseBase courseBaseNew = new CourseBase();

//        int[] maxMtandSt = categoryService.getMaxMtandSt(courseBase.getMt());
//        // 选择的是根结点
//        if (courseBase.getMt().equals("1")) {
//            courseBaseNew.setMt( "1-" + maxMtandSt[0]++);
//            courseBaseNew.setSt(courseBase.getMt() + "-" + maxMtandSt[1]++);
//        } else {
//            courseBaseNew.setMt(courseBase.getMt());
//        }

        String stId = courseBase.getSt();
        if (StringUtils.isNotEmpty(stId)) {
            String[] split = stId.split("-");
            List<String> stIdSplit = new ArrayList(Arrays.asList(split));

            if (stIdSplit.size() != 3) {
                ExceptionCast.cast(ECommonCode.INVALID_PARAM);
            }

            stIdSplit.remove(stIdSplit.size() - 1);
            courseBaseNew.setMt(StringUtils.join(stIdSplit, '-'));
        } else {
            ExceptionCast.cast(ECommonCode.INVALID_PARAM);
        }


        courseBaseNew.setSt(stId);
        courseBaseNew.setName(courseBase.getName());
        courseBaseNew.setGrade(courseBase.getGrade());
        courseBaseNew.setStudymodel(courseBase.getStudymodel());
        courseBaseNew.setDescription(courseBase.getDescription());

        CourseBase save = courseBaseDao.save(courseBaseNew);
        return new ResultRes<>(ECommonCode.SUCCESS, save);
    }



    // 课程计划但询
    public ResultRes<TeachplanNode> findTeachplanList(String courseId) {
        return new ResultRes(ECommonCode.SUCCESS, teachplanMapper.selectList(courseId));
    }

    //添加课程计划
    @Transactional
    public ResultRes addTeachPlan(TeachPlan teachplan) {
        if(teachplan == null ||
                StringUtils.isEmpty(teachplan.getCourseid()) ||
                StringUtils.isEmpty(teachplan.getPname())){
            ExceptionCast.cast(ECommonCode.INVALID_PARAM);
        }

        //课程id
        String courseid = teachplan.getCourseid();
        //页面传入的parentId
        String parentid = teachplan.getParentid();

        if(StringUtils.isEmpty(parentid)){
            //取出该课程的根结点
            parentid = this.getTeachplanRoot(courseid);
        }

        Optional<TeachPlan> optional = teachPlanDao.findById(parentid);
        TeachPlan parentNode = optional.get();
        //父结点的级别
        String grade = parentNode.getGrade();

        //新结点
        TeachPlan teachplanNew = new TeachPlan();

        //将页面提交的teachplan信息拷贝到teachplanNew对象中
        BeanUtils.copyProperties(teachplan,teachplanNew);
        teachplanNew.setParentid(parentid);
        teachplanNew.setCourseid(courseid);
        if(grade.equals("1")){
            teachplanNew.setGrade("2");//级别，根据父结点的级别来设置
        }else{
            teachplanNew.setGrade("3");
        }

        teachPlanDao.save(teachplanNew);

        return new ResultRes(ECommonCode.SUCCESS);
    }

    //查询课程的根结点，如果查询不到要自动添加根结点
    private String getTeachplanRoot(String courseId){
        Optional<CourseBase> optional = courseBaseDao.findById(courseId);
        if(!optional.isPresent()){
            return null;
        }
        //课程信息
        CourseBase courseBase = optional.get();
        //查询课程的根结点
        List<TeachPlan> teachplanList = teachPlanDao.findByCourseidAndParentid(courseId, "0");
        if(teachplanList == null || teachplanList.size()<=0){
            //查询不到，要自动添加根结点
            TeachPlan teachplan = new TeachPlan();
            teachplan.setParentid("0");
            teachplan.setGrade("1");
            teachplan.setPname(courseBase.getName());
            teachplan.setCourseid(courseId);
            teachplan.setStatus("0");
            teachPlanDao.save(teachplan);
            return teachplan.getId();
        }
        //返回根结点id
        return teachplanList.get(0).getId();

    }

    // 往课程管理数据库添加课程与图片的关联
    @Transactional
    public ResultRes addCoursePic(CoursePic coursePicBody) {
        //课程图片信息
        CoursePic coursePic = null;
        //查询课程图片
        Optional<CoursePic> picOptional = coursePicDao.findById(coursePicBody.getCourseid());
        if(picOptional.isPresent()){
            coursePic = picOptional.get();
        }
        if(coursePic == null){
            coursePic  = new CoursePic();
        }
        coursePic.setPic(coursePicBody.getPic());
        coursePic.setCourseid(coursePicBody.getCourseid());
        coursePicDao.save(coursePic);

        return new ResultRes(ECommonCode.SUCCESS);
    }

    public ResultRes<CoursePic> findCoursPic(String courseId) {
        //查询课程图片
        Optional<CoursePic> picOptional = coursePicDao.findById(courseId);
        if(picOptional.isPresent()){
            CoursePic coursePic = picOptional.get();
            return new ResultRes<>(ECommonCode.SUCCESS, coursePic);
        }
        return new ResultRes<>(ECommonCode.SUCCESS);
    }

    @Transactional
    public ResultRes delCoursePic(String courseId) {
        //执行删除
//        long result = coursePicDao.deleteById(courseId); // deleteById没有返回值
        long result = coursePicDao.deleteByCourseid(courseId);
        if(result>0){
            return new ResultRes(ECommonCode.SUCCESS);
        }
        return new ResultRes(ECommonCode.FAIL);
    }

    // 查询课程视图，供前端调用，包括基本息，图片，课程计划
    public ResultRes<CourseView> queryCourseView(String id) {
        CourseView courseView= new CourseView();

        //查询课程基本信息
        Optional<CourseBase> courseBaseOptional = courseBaseDao.findById(id);
        if(courseBaseOptional.isPresent()){
            CourseBase courseBase = courseBaseOptional.get();
            courseView.setCourseBase(courseBase);
        }
        //查询课程图片
        Optional<CoursePic> picOptional = coursePicDao.findById(id);
        if(picOptional.isPresent()){
            CoursePic coursePic = picOptional.get();
            courseView.setCoursePic(coursePic);
        }

        //课程计划信息
        TeachplanNode teachplanNode = teachplanMapper.selectList(id);
        courseView.setTeachplanNode(teachplanNode);

        return new ResultRes<>(ECommonCode.SUCCESS, courseView);
    }

    //课程预览
    public ResultRes<CoursePublishResult> preview(String id) {
        //查询课程
        CourseBase courseBaseById = this.findCourseBaseById(id);
        //请求cms添加页面
        //准备cmsPage信息
        CmsPage cmsPage = new CmsPage();
        cmsPage.setSiteId(publish_siteId);//站点id
        cmsPage.setDataUrl(publish_dataUrlPre+id);//数据模型url
        cmsPage.setPageName(id+".html");//页面名称
        cmsPage.setPageAliase(courseBaseById.getName());//页面别名，就是课程名称
        cmsPage.setPagePhysicalPath(publish_page_physicalpath);//页面物理路径
        cmsPage.setPageWebPath(publish_page_webpath);//页面webpath
        cmsPage.setTemplateId(publish_templateId);//页面模板id

        //远程调用cms
        ResultRes<CmsPage> cmsPageResult = cmsPageClient.saveCmsPage(cmsPage);
        if(cmsPageResult.getFlag() != 0){
            return new ResultRes(ECommonCode.FAIL,null);
        }

        CmsPage cmsPage1 = cmsPageResult.getData();
        String pageId = cmsPage1.getPageId();
        //拼装页面预览的url
        String url = previewUrl+pageId;
        CoursePublishResult coursePublishResult = new CoursePublishResult();
        coursePublishResult.setPreviewUrl(url);
        //返回CoursePublishResult对象（当中包含了页面预览的url）
        return new ResultRes(ECommonCode.SUCCESS,coursePublishResult);
    }

    //根据id查询课程基本信息
    public CourseBase findCourseBaseById(String courseId){
        Optional<CourseBase> baseOptional = courseBaseDao.findById(courseId);
        if(baseOptional.isPresent()){
            CourseBase courseBase = baseOptional.get();
            return courseBase;
        }
        return null;
    }

    //课程发布
    @Transactional
    public ResultRes<CoursePublishResult> publish(String id) {
        //查询课程
        CourseBase courseBaseById = this.findCourseBaseById(id);

        //准备页面信息
        CmsPage cmsPage = new CmsPage();
        cmsPage.setSiteId(publish_siteId);//站点id
        cmsPage.setDataUrl(publish_dataUrlPre+id);//数据模型url
        cmsPage.setPageName(id+".html");//页面名称
        cmsPage.setPageAliase(courseBaseById.getName());//页面别名，就是课程名称
        cmsPage.setPagePhysicalPath(publish_page_physicalpath);//页面物理路径
        cmsPage.setPageWebPath(publish_page_webpath);//页面webpath
        cmsPage.setTemplateId(publish_templateId);//页面模板id
        //调用cms一键发布接口将课程详情页面发布到服务器
        ResultRes<CmsPostPageResult> cmsPostPageResultRes = cmsPageClient.postPageQuick(cmsPage);
        if(cmsPostPageResultRes.getFlag() != 0){
            return new ResultRes<>(ECommonCode.FAIL,null);
        }

        //保存课程的发布状态为“已发布”
        CourseBase courseBase = this.saveCoursePubState(id);
        if(courseBase == null){
            return new ResultRes<>(ECommonCode.FAIL,null);
        }

        //保存课程索引信息
        //先创建一个coursePub对象
        CoursePub coursePub = createCoursePub(id);
        //将coursePub对象保存到数据库
        saveCoursePub(id,coursePub);
        //缓存课程的信息
        //...
        //得到页面的url
        CmsPostPageResult cmsPostPageResult;
        cmsPostPageResult = cmsPostPageResultRes.getData();
        String pageUrl = cmsPostPageResult.getPageUrl();
        cmsPostPageResult.setPageUrl(pageUrl);

        return new ResultRes(ECommonCode.SUCCESS, cmsPostPageResult);
    }

    //更新课程状态为已发布 202002
    private CourseBase  saveCoursePubState(String courseId){
        CourseBase courseBaseById = this.findCourseBaseById(courseId);
        courseBaseById.setStatus("202002");
        courseBaseDao.save(courseBaseById);
        return courseBaseById;
    }

    //创建coursePub对象
    private CoursePub createCoursePub(String id){
        CoursePub coursePub = new CoursePub();
        //根据课程id查询course_base
        Optional<CourseBase> baseOptional = courseBaseDao.findById(id);
        if(baseOptional.isPresent()){
            CourseBase courseBase = baseOptional.get();
            //将courseBase属性拷贝到CoursePub中
            BeanUtils.copyProperties(courseBase,coursePub);
        }

        //查询课程图片
        Optional<CoursePic> picOptional = coursePicDao.findById(id);
        if(picOptional.isPresent()){
            CoursePic coursePic = picOptional.get();
            BeanUtils.copyProperties(coursePic, coursePub);
        }

        //课程营销信息
        Optional<CourseMarket> marketOptional = courseMarketDao.findById(id);
        if(marketOptional.isPresent()){
            CourseMarket courseMarket = marketOptional.get();
            BeanUtils.copyProperties(courseMarket, coursePub);
        }

        //课程计划信息
        TeachplanNode teachplanNode = teachplanMapper.selectList(id);
        String jsonString = JSON.toJSONString(teachplanNode);
        //将课程计划信息json串保存到 course_pub中
        coursePub.setTeachplan(jsonString);
        return coursePub;

    }

    //将coursePub对象保存到数据库
    private CoursePub saveCoursePub(String id,CoursePub coursePub){

        CoursePub coursePubNew = null;
        //根据课程id查询coursePub
        Optional<CoursePub> coursePubOptional = coursePubDao.findById(id);
        if(coursePubOptional.isPresent()){
            coursePubNew = coursePubOptional.get();
        }else{
            coursePubNew = new CoursePub();
        }

        //将coursePub对象中的信息保存到coursePubNew中
        BeanUtils.copyProperties(coursePub,coursePubNew);
        coursePubNew.setId(id);
        //时间戳,给logstach使用
        coursePubNew.setTimestamp(new Date());
        //发布时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        String date = simpleDateFormat.format(new Date());
        coursePubNew.setPubTime(date);
        coursePubDao.save(coursePubNew);
        return coursePubNew;
    }


}
