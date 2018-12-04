package cn.datawin.lightheadline.dao;

import cn.datawin.lightheadline.core.BaseDao;
import cn.datawin.lightheadline.entity.Deptment;
import cn.datawin.lightheadline.entity.FaultDetail;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface FaultDetailDao extends BaseDao<FaultDetail> {
    @Select("select fault_detail from fault_detail where id = ${id}")
    String selectFaultDetailById (@Param("id")Integer id);

    @Select("select fault_detail from fault_detail ")
    List<String> selectFaultDetail();

    @Select("select color from fault_detail ")
    List<String> selectColor();

    @Select("select id from fault_detail ")
    List<Integer> selectId();

}
