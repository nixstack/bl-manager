package com.nixstack.service.cms.dao;

import com.nixstack.base.model.cms.entity.CmsPage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CmsPageDaoTest {
    @Autowired
    CmsPageDao cmsPageDao;

    @Test
    public void findAllTest() {
        List<CmsPage> list = cmsPageDao.findAll();
        System.out.println(list);
    }

    // 分页查询
    @Test
    public void findAllPageable() {
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        Page<CmsPage> list = cmsPageDao.findAll(pageable);

        System.out.println(list);
    }

    //根据页面名称查询
    @Test
    public void findByPageNameTest(){
        CmsPage cmsPage = cmsPageDao.findByPageName("测试页面");
        System.out.println(cmsPage);
    }

    // 自定义条件查询
    @Test
    public void findAllByExampleTest() {
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);

        // 构造条件对象
        CmsPage cmsPage = new CmsPage();
//        cmsPage.setPageName("测试页面");
//        cmsPage.setSiteId("5a751fab6abb5044e0d19ea1");
        cmsPage.setPageAliase("轮播图");

        // 条件匹配器
        ExampleMatcher exampleMatcher = ExampleMatcher.matching();

        // 定义Example
        Example<CmsPage> example = Example.of(cmsPage, exampleMatcher);

        Page<CmsPage> pages = cmsPageDao.findAll(example, pageable);

        List<CmsPage> list = pages.getContent();

        System.out.println(list);
    }

}
