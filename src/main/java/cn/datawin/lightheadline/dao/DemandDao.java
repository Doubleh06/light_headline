package cn.datawin.lightheadline.dao;

import cn.datawin.lightheadline.core.BaseDao;
import cn.datawin.lightheadline.entity.Demand;
import cn.datawin.lightheadline.entity.Deptment;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface DemandDao extends BaseDao<Demand> {

    @Select("select * from demand ${sql}")
    List<Map> selectMap(@Param("sql") String sql);

    @Select("select id,name,dept,phone,emergency_degree,status,admissibility_time from demand ${sql}")
    List<Map> selectDemandList(@Param("sql") String sql);

    @Select("select * from demand where id = ${id}")
    Demand selectDemandById(@Param("id")Integer id);

}
