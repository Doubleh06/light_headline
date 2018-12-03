package cn.datawin.lightheadline.dao;

import cn.datawin.lightheadline.core.BaseDao;
import cn.datawin.lightheadline.entity.Deptment;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Component
public interface DeptmentDao extends BaseDao<Deptment> {
    @Select("select dept_name  from deptment where id = ${id}")
    String getDeptmentNameById (@Param("id")Integer id);

}
