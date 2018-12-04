package cn.datawin.lightheadline.controller;

import cn.datawin.lightheadline.core.JSONResult;
import cn.datawin.lightheadline.core.Result;
import cn.datawin.lightheadline.core.jqGrid.JqGridResult;
import cn.datawin.lightheadline.dao.DemandDao;
import cn.datawin.lightheadline.dao.DeptmentDao;
import cn.datawin.lightheadline.dao.FaultCategoryDao;
import cn.datawin.lightheadline.dao.FaultDetailDao;
import cn.datawin.lightheadline.dto.DemandDto;
import cn.datawin.lightheadline.dto.DemandJqGridParam;
import cn.datawin.lightheadline.entity.Demand;
import cn.datawin.lightheadline.entity.FaultDetail;
import cn.datawin.lightheadline.service.AnalysisService;
import cn.datawin.lightheadline.service.DemandListService;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
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
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping(value = "/analysis")
public class AnalysisController extends BaseController {

    @Autowired
    private AnalysisService analysisService;

    @RequestMapping(value = "/list")
    public String list(Model model,@RequestParam String startDate,@RequestParam String endDate) {
        model.addAttribute("menus", getMenus("analysis"));
        model.addAttribute("faultDetails",analysisService.makeFaultDetailArr());
        model.addAttribute("colors",analysisService.makeColorArr());
        try {
            model.addAttribute("datas",analysisService.makeDataArr(startDate,endDate));
            if (StringUtils.isEmpty(startDate)) {
                //如果为空显示今天时间
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                Date date = new Date();
                Calendar c = Calendar.getInstance();
                c.setTime(date);
                c.add(Calendar.MONTH,-1);
                startDate = sdf.format(c.getTime());
                endDate = sdf.format(date);
            }
            model.addAttribute("startDate",startDate);
            model.addAttribute("endDate",endDate);
        }catch (ParseException e){ }
        return "/analysis/list";
    }




}
