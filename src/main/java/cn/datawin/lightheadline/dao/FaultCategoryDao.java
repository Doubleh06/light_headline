package cn.datawin.lightheadline.dao;

import cn.datawin.lightheadline.core.BaseDao;
import cn.datawin.lightheadline.entity.Deptment;
import cn.datawin.lightheadline.entity.FaultCategory;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Component
public interface FaultCategoryDao extends BaseDao<FaultCategory> {
    @Select("select fault_category from fault_category where id = ${id}")
    String selectFaultCategoryById (@Param("id")Integer id);
}
