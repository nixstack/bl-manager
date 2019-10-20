package com.nixstack.base.model.course.ext;

import com.nixstack.base.model.course.entity.TeachPlan;
import lombok.Data;

import java.util.List;

/**
 * 课程计划树型结构，采用表的自连接方式进行查询
 * SELECT
 *   a.id one_id,
 *   a.pname one_pname,
 *   b.id two_id,
 *   b.pname two_pname,
 *   c.id three_id,
 *   c.pname three_pname
 * FROM
 *   teachplan a
 *   LEFT JOIN teachplan b
 *     ON b.parentid = a.id
 *   LEFT JOIN teachplan c
 *     ON c.parentid = b.id
 * WHERE a.parentid = '0'
 *   AND a.courseid = '4028e581617f945f01617f9dabc40000'
 * ORDER BY a.orderby,
 *   b.orderby,
 *   c.orderby
 */
@Data
public class TeachplanNode extends TeachPlan {
    List<TeachplanNode> children;
}
