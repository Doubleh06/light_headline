package cn.datawin.lightheadline.controller;

import cn.datawin.lightheadline.core.JSONResult;
import cn.datawin.lightheadline.core.Result;
import cn.datawin.lightheadline.core.jqGrid.JqGridResult;
import cn.datawin.lightheadline.dao.*;
import cn.datawin.lightheadline.dto.DemandDto;
import cn.datawin.lightheadline.dto.DemandJqGridParam;
import cn.datawin.lightheadline.entity.Demand;
import cn.datawin.lightheadline.service.DemandListService;
import com.github.pagehelper.PageInfo;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping(value = "/demandList")
public class DemandListController extends BaseController {

    @Autowired
    private DemandListService demandListService;
    @Autowired
    private DeptmentDao deptmentDao;
    @Autowired
    private FaultCategoryDao faultCategoryDao;
    @Autowired
    private FaultDetailDao faultDetailDao;
    @Autowired
    private  DemandDao demandDao;

    @RequestMapping(value = "/list")
    public String list(Model model) {
        model.addAttribute("menus", getMenus("demandList"));
        return "/demandList/list";
    }

    @RequestMapping(value = "/grid")
    @ResponseBody
    public Result grid( DemandJqGridParam param) {
        PageInfo<Map> pageInfo = demandListService.selectByJqGridParam(param);
        JqGridResult<Map> result = new JqGridResult<>();
        //当前页
        result.setPage(pageInfo.getPageNum());
        //数据总数
        result.setRecords(pageInfo.getTotal());
        //总页数
        result.setTotal(pageInfo.getPages());
        //当前页数据
        result.setRows(pageInfo.getList());
        return new JSONResult(result);
    }

    @RequestMapping(value = "/deal")
    public String deal(Model model,@RequestParam Integer id) {
        model.addAttribute("menus", getMenus("demandList"));
        Demand demand = demandDao.selectDemandById(id);
        DemandDto demandDto = new DemandDto();
        BeanUtils.copyProperties(demand,demandDto);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy|hh:mm");
        String admissibilityTime = sdf.format(demand.getAdmissibilityTime());
        String realStartDate = sdf.format(demand.getRealStartTime());
        String realEndDate = sdf.format(demand.getRealEndTime());
        demandDto.setAdmissibilityDate(admissibilityTime.split("\\|")[0]);
        demandDto.setAdmissibilityTime(admissibilityTime.split("\\|")[1]);
        demandDto.setRealStartDate(realStartDate.split("\\|")[0]);
        demandDto.setRealStartTime(realStartDate.split("\\|")[1]);
        demandDto.setRealEndDate(realEndDate.split("\\|")[0]);
        demandDto.setRealEndTime(realEndDate.split("\\|")[1]);
        model.addAttribute("demandDto",demandDto);
        model.addAttribute("deptments",deptmentDao.selectAll());
        model.addAttribute("faultDetails",faultDetailDao.selectAll());
        model.addAttribute("faultCategories",faultCategoryDao.selectAll());
        return "/demandList/deal";
    }
    @RequestMapping(value = "update")
    @ResponseBody
    public Result insert(@RequestBody DemandDto demandDto) {
        Demand demand = new Demand();
        BeanUtils.copyProperties(demandDto,demand);
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm");
            Date admissibilityTime = sdf.parse(demandDto.getAdmissibilityDate()+" "+demandDto.getAdmissibilityTime());
            demand.setAdmissibilityTime(admissibilityTime);
            Date realStartTime = sdf.parse(demandDto.getRealStartDate()+" "+demandDto.getRealStartTime());
            demand.setRealStartTime(realStartTime);
            Date realEndTime = sdf.parse(demandDto.getRealEndDate()+" "+demandDto.getRealEndTime());
            demand.setRealEndTime(realEndTime);
            Long realDealTime = (realEndTime.getTime()-realStartTime.getTime())/1000 ;
            BigDecimal bg1 = new BigDecimal(realDealTime.doubleValue());
            BigDecimal bg2 = new BigDecimal(3600);
            demand.setRealDealTime(bg1.divide(bg2, 2, RoundingMode.HALF_UP)+"h");
        }catch (Exception e){
        }
        demandListService.updateSelective(demand);
        //获取用户所属的部门
//        Demand demand = new Demand();
//        BeanUtils.copyProperties(demandDto,demand);
//        try {
//            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm");
//            Date admissibilityTime = sdf.parse(demandDto.getAdmissibilityDate()+" "+demandDto.getAdmissibilityTime());
//            demand.setAdmissibilityTime(admissibilityTime);
//            Date realStartTime = sdf.parse(demandDto.getRealStartDate()+" "+demandDto.getRealStartTime());
//            demand.setRealStartTime(realStartTime);
//            Date realEndTime = sdf.parse(demandDto.getRealEndDate()+" "+demandDto.getRealEndTime());
//            demand.setRealEndTime(realEndTime);
//            Long realDealTime = (realEndTime.getTime()-realStartTime.getTime())/1000 ;
//            BigDecimal bg1 = new BigDecimal(realDealTime.doubleValue());
//            BigDecimal bg2 = new BigDecimal(3600);
//            demand.setRealDealTime(bg1.divide(bg2, 2, RoundingMode.HALF_UP)+"h");
//        }catch (Exception e){
//        }
//        demand.setStatus(0);
//        demandDao.insert(demand);
        return OK;
    }

    @RequestMapping(value = "export")
    @ResponseBody
    public void export(HttpServletResponse response) throws IOException {
        //获取数据
        List<Demand> demandList =  demandDao.selectAll();
        //excel 表头
        String[] columns = new String[]{"序号", "要求人", "部门", "电话", "处理状态", "受理时间", "故障类别", "故障明细", "对应方式",
                "紧急程度", "联络要点", "状态原因", "对应", "实际开始时间", "实际结束时间", "处理时间", "对应人", "备注"};
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("故障报修-" + sdf.format(date));

        //创建表头
        HSSFRow header = sheet.createRow(0);
        for (int i = 0; i < columns.length; i++) {
            header.createCell(i).setCellValue(columns[i]);
        }
        String status = "";
        String emergencyDegree = "";
        //i-行   j-列
        for (int i=0;i<demandList.size();i++){
            HSSFRow row = sheet.createRow(i+1);
            for (int j = 0; j < columns.length; j++) {
                row.createCell(0).setCellValue(demandList.get(i).getId());
                row.createCell(1).setCellValue(demandList.get(i).getName());
                row.createCell(2).setCellValue(deptmentDao.getDeptmentNameById(Integer.parseInt(demandList.get(i).getDept())));
                row.createCell(3).setCellValue(demandList.get(i).getPhone());
                switch (demandList.get(i).getStatus()){
                    case 0:
                        status = "未处理";
                        break;
                    case 1:
                        status = "处理中";
                        break;
                    case 2:
                        status = "处理完成";
                        break;
                }
                row.createCell(4).setCellValue(status);
                row.createCell(5).setCellValue(sdf2.format(demandList.get(i).getAdmissibilityTime()));
                row.createCell(6).setCellValue(faultCategoryDao.selectFaultCategoryById(demandList.get(i).getFaultCategory()));
                row.createCell(7).setCellValue(faultDetailDao.selectFaultDetailById(demandList.get(i).getFaultDetail()));
                row.createCell(8).setCellValue(demandList.get(i).getCorrespondingMode());
                switch (demandList.get(i).getEmergencyDegree()){
                    case "0":
                        emergencyDegree = "一般";
                        break;
                    case "1":
                        emergencyDegree = "次要";
                        break;
                    case "2":
                        emergencyDegree = "紧急";
                        break;
                }
                row.createCell(9).setCellValue(emergencyDegree);
                row.createCell(10).setCellValue(demandList.get(i).getContactPoints());
                row.createCell(11).setCellValue(demandList.get(i).getContactReason());
                row.createCell(12).setCellValue(demandList.get(i).getContactDeal());
                row.createCell(13).setCellValue(sdf2.format(demandList.get(i).getRealStartTime()));
                row.createCell(14).setCellValue(sdf2.format(demandList.get(i).getRealEndTime()));
                row.createCell(15).setCellValue(demandList.get(i).getRealDealTime());
                row.createCell(16).setCellValue(demandList.get(i).getContactMan());
                row.createCell(17).setCellValue(demandList.get(i).getMemo());
            }
        }



        OutputStream output = response.getOutputStream();
        response.reset();
        response.setHeader("Content-disposition", "attachment; filename="+sdf.format(date)+".xls");
        response.setContentType("application/msexcel");
        wb.write(output);
        output.close();

    }
}
