package cn.datawin.lightheadline.service;


import cn.datawin.lightheadline.core.AbstractService;
import cn.datawin.lightheadline.core.BaseDao;
import cn.datawin.lightheadline.core.jqGrid.JqGridParam;
import cn.datawin.lightheadline.dao.DemandDao;
import cn.datawin.lightheadline.dao.DeptmentDao;
import cn.datawin.lightheadline.dao.UserDao;
import cn.datawin.lightheadline.dto.DemandJqGridParam;
import cn.datawin.lightheadline.entity.Demand;
import cn.datawin.lightheadline.entity.Deptment;
import cn.datawin.lightheadline.entity.User;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DemandListService extends AbstractService<Demand> {

    @Resource
    private DemandDao demandDao;
    @Resource
    private DeptmentDao deptmentDao;
    @Resource
    private UserDao userDao;


    @Override
    protected BaseDao<Demand> getDao() {
        return demandDao;
    }

    @Override
    protected List<Demand> selectByJqGridParam(JqGridParam jqGridParam) {
        DemandJqGridParam param = (DemandJqGridParam) jqGridParam;
        StringBuilder sql = new StringBuilder();
        sql.append("1=1 ");
        if (StringUtils.isNotEmpty(param.getSidx())) {
            sql.append("order by ").append(param.getSidx()).append(" ").append(param.getSord()).append("");
        }
        return demandDao.selectBySql("article",sql.toString());
    }


    public PageInfo<Map> selectByJqGridParam(DemandJqGridParam param ){
        StringBuilder sql = new StringBuilder();
        sql.append(" where 1 = 1 ");
        if(StringUtils.isNotEmpty(param.getName())){
            sql.append(" and name like '%").append(param.getName()).append("%'");
        }
        if(StringUtils.isNotEmpty(param.getEmergencyDegree())){
            sql.append(" and emergency_degree = '").append(param.getEmergencyDegree()).append("'");
        }
        if(null!=param.getStatus()){
            sql.append(" and status = ").append(param.getStatus());
        }

        List<Map> demandList = demandDao.selectDemandList(sql.toString());

        for (int i=0;i<demandList.size();i++){
            String name = deptmentDao.getDeptmentNameById(Integer.parseInt(demandList.get(i).get("dept").toString()));
            demandList.get(i).put("dept_name",name);
        }

        //获取部门信息
//        List<Deptment> deptmentList = deptmentDao.selectAll();
//        //获取用户信息
//        List<User> userList = userDao.selectAll();
//        for(Map demand : demandList){
//            for(Deptment deptment : deptmentList){
//                if(Integer.parseInt(demand.get("propose_dept_id").toString())==deptment.getId()){
//                    demand.put("propose_dept_name",deptment.getDeptName());
//                }
//            }
//            for (User user : userList){
//                if(Integer.parseInt(demand.get("propose_user_id").toString())==user.getId()){
//                    demand.put("propose_user_name",user.getNickname());
//                }
//            }
//        }
        return new PageInfo<>(demandList);
    }


}
