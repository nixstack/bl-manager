package com.nixstack.service.course.service;

import com.nixstack.base.common.code.ECommonCode;
import com.nixstack.base.common.response.ResultRes;
import com.nixstack.base.model.course.entity.Category;
import com.nixstack.base.model.course.ext.CategoryNode;
import com.nixstack.service.course.dao.CategoryDao;
import com.nixstack.service.course.dao.CategoryMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    CategoryMapper categoryMapper;

    /**
     * 查询分类列表
     *
     * @return CategoryNode
     */
    public ResultRes<List<Category>> findAll() {
        CategoryNode sourceCategoryNode = buildCategoryNode(categoryDao.findByParentid("0").get(0));

        // 查询第二级分类列表,条一级只作标识
        List<Category> secondaryCategoryList = categoryDao.findByParentid(sourceCategoryNode.getId());
        List<CategoryNode> secondaryCategoryNodeList = buildCategoryNodeList(secondaryCategoryList);

        sourceCategoryNode.setChildren(secondaryCategoryNodeList);

        ArrayList<Category> categories = new ArrayList<>();
        categories.add(sourceCategoryNode);

        return new ResultRes(ECommonCode.SUCCESS, categories);
    }

    /**
     * 新增分类
     *
     * @return
     */
    @Transactional
    public ResultRes<Category> add(Category category) {
        String id = category.getId();
        if (StringUtils.isEmpty(id) || StringUtils.isEmpty(category.getName())) {
            return new ResultRes<>(ECommonCode.INVALID_PARAM);
        }

        Optional<Category> byId = categoryDao.findById(id);
        Category byName = categoryDao.findByName(category.getName());
        if (!byId.isPresent()) {
            return new ResultRes<>(ECommonCode.INVALID_PARAM);
        }
        if (byName != null) {
            return new ResultRes<>(ECommonCode.RECORD_EXISTED);
        }

        List<Category> byParentid = categoryDao.findByParentid(id);

        String idPath;
        int order;

        if (byParentid.size() > 0) {
            // 倒序
            Collections.sort(byParentid, new Comparator<Category>() {
                @Override
                public int compare(Category o1, Category o2) {
                    String[] split1 = o1.getId().split("-");
                    String[] split2 = o2.getId().split("-");
                    Integer integer1 = Integer.valueOf(split1[split1.length - 1]);
                    Integer integer2 = Integer.valueOf(split2[split2.length - 1]);
                    return integer2 - integer1;
                }
            });


            // 第一条记录id为最大值,所以取第一条记录后未位id+1
            Category category1 = byParentid.get(0);

            String[] idPathSplit = category1.getId().split("-");
            order = Integer.valueOf(idPathSplit[idPathSplit.length - 1]) + 1;
            idPathSplit[idPathSplit.length - 1] = String.valueOf(order);
            idPath = String.join("-", idPathSplit);
        } else {
            // 新增分类，还没有子节点
            order = 1;
            idPath = id + "-" + order;
        }


        Category categoryNew = new Category();
        categoryNew.setId(idPath);
        categoryNew.setParentid(id);
        categoryNew.setName(category.getName());
        categoryNew.setLabel(category.getLabel());
        categoryNew.setOrderby(order);
        categoryNew.setIsshow("1");
        categoryNew.setIsleaf("1");

        Category save = categoryDao.save(categoryNew);

        // 更新父节点isleaf
        Optional<Category> parentNodeOptional = categoryDao.findById(id);

        Category parentNode = parentNodeOptional.get();
        parentNode.setIsleaf("0");
        categoryDao.save(parentNode);

        return new ResultRes<>(ECommonCode.SUCCESS, save);
    }

    /**
     * 构造分类节点列表
     *
     * @param categoryList
     * @return List<CategoryNode>
     */
    private List<CategoryNode> buildCategoryNodeList(List<Category> categoryList) {
        return categoryList.stream().map(secondaryCategory -> {
            CategoryNode categoryNode = buildCategoryNode(secondaryCategory);
            // 查询子节点
            List<Category> children = categoryDao.findByParentid(categoryNode.getId());
            List<CategoryNode> categoryNodes = buildCategoryNodeList(children);
            if (!categoryNodes.isEmpty()) {
                categoryNode.setChildren(categoryNodes);
            }

            return categoryNode;
        }).collect(Collectors.toList());
    }


    /**
     * 构造分类节点数据
     *
     * @param category 分类
     * @return CategoryNode
     */
    private CategoryNode buildCategoryNode(Category category) {
        CategoryNode categoryNode = new CategoryNode();
        categoryNode.setId(category.getId());
        categoryNode.setIsleaf(category.getIsleaf());
        categoryNode.setLabel(category.getLabel());
        categoryNode.setName(category.getName());

        return categoryNode;
    }


    public int[] getLastIndex(List list) {

        int size = list.size();
        int[] stIds = new int[size];
        for (int i = 0; i < size; i++) {
            String s = (String) list.get(i);
            stIds[i] = Integer.valueOf(s.split("-")[1]);
        }

        Arrays.sort(stIds);


        return new int[]{stIds[size - 1]};
    }

    public ResultRes<CategoryNode> findAllByMapper() {
        return new ResultRes<>(ECommonCode.SUCCESS, categoryMapper.selectList());
    }
}
