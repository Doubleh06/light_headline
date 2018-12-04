package cn.datawin.lightheadline.service;


import cn.datawin.lightheadline.core.AbstractService;
import cn.datawin.lightheadline.core.BaseDao;
import cn.datawin.lightheadline.core.jqGrid.JqGridParam;
import cn.datawin.lightheadline.dao.DemandDao;
import cn.datawin.lightheadline.dao.DeptmentDao;
import cn.datawin.lightheadline.dao.FaultDetailDao;
import cn.datawin.lightheadline.dao.UserDao;
import cn.datawin.lightheadline.dto.DemandJqGridParam;
import cn.datawin.lightheadline.entity.Demand;
import cn.datawin.lightheadline.entity.FaultDetail;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.xml.crypto.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AnalysisService extends AbstractService<Demand> {

    @Resource
    private DemandDao demandDao;
    @Resource
    private DeptmentDao deptmentDao;
    @Resource
    private FaultDetailDao faultDetailDao;


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
//        if(StringUtils.isNotEmpty(param.getName())){
//            sql.append(" and name like '%").append(param.getName()).append("%'");
//        }
//        if(StringUtils.isNotEmpty(param.getEmergencyDegree())){
//            sql.append(" and emergency_degree = '").append(param.getEmergencyDegree()).append("'");
//        }
//        if(null!=param.getStatus()){
//            sql.append(" and status = ").append(param.getStatus());
//        }

        List<Map> demandList = demandDao.selectDemandList(sql.toString());

        for (int i=0;i<demandList.size();i++){
            String name = deptmentDao.getDeptmentNameById(Integer.parseInt(demandList.get(i).get("dept").toString()));
            demandList.get(i).put("dept_name",name);
        }
        return new PageInfo<>(demandList);
    }

    public String makeColorArr(){
        List<String> colorList = faultDetailDao.selectColor();
        return StringUtils.join(colorList.toArray(),"|");
    }

    public String makeFaultDetailArr(){
        List<String> faultDetailList = faultDetailDao.selectFaultDetail();
        return StringUtils.join(faultDetailList.toArray(),"|");
    }

    public String makeDataArr(String startDate, String endDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if(StringUtils.isEmpty(startDate)){
            Calendar c = Calendar.getInstance();
            Date date = new Date();
            c.setTime(date);
            c.add(Calendar.MONTH,-1);
            startDate = sdf.format(c.getTime())+" 00:00:00";
            endDate = sdf.format(date)+" 23:59:59";

        }else {
            Date start = new SimpleDateFormat("MM/dd/yyyy").parse(startDate);
            startDate = new SimpleDateFormat("yyyy-MM-dd").format(start)+" 00:00:00";
            Date end = new SimpleDateFormat("MM/dd/yyyy").parse(endDate);
            endDate = new SimpleDateFormat("yyyy-MM-dd").format(end)+" 23:59:59";
        }

        List<Demand> demandList = demandDao.selectDemandByTime(startDate, endDate);
        List<Integer> faultDetailList = faultDetailDao.selectId();
        List<Integer> countList = new ArrayList<>();
        for (Integer id : faultDetailList){
            Integer count = 0;
            for(Demand demand : demandList){
                if (id == demand.getFaultDetail()){
                    count ++;
                }
            }
            countList.add(count);
        }
        return StringUtils.join(countList.toArray(),"|");
    }


}
