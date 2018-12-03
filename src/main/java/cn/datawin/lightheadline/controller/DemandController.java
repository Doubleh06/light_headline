package cn.datawin.lightheadline.controller;

import cn.datawin.lightheadline.core.ErrorCode;
import cn.datawin.lightheadline.core.ErrorResult;
import cn.datawin.lightheadline.core.JSONResult;
import cn.datawin.lightheadline.core.Result;
import cn.datawin.lightheadline.dao.DemandDao;
import cn.datawin.lightheadline.dao.DeptmentDao;
import cn.datawin.lightheadline.dao.FaultCategoryDao;
import cn.datawin.lightheadline.dao.FaultDetailDao;
import cn.datawin.lightheadline.dto.DemandDto;
import cn.datawin.lightheadline.entity.Demand;
import cn.datawin.lightheadline.entity.Deptment;
import cn.datawin.lightheadline.security.UserDetail;
import cn.datawin.lightheadline.util.SpringSecurityUtil;
import cn.datawin.lightheadline.util.UploadUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/createDemand")
public class DemandController extends BaseController {

    @Autowired
    private DeptmentDao deptmentDao;
    @Autowired
    private FaultCategoryDao faultCategoryDao;
    @Autowired
    private FaultDetailDao faultDetailDao;
    @Autowired
    private DemandDao demandDao;

    @RequestMapping(value = "/create")
    public String list(Model model){
        model.addAttribute("menus", getMenus("createDemand"));
        model.addAttribute("deptments",deptmentDao.selectAll());
        model.addAttribute("faultDetails",faultDetailDao.selectAll());
        model.addAttribute("faultCategories",faultCategoryDao.selectAll());
        return "/demand/create";
    }

    @RequestMapping(value = "insert")
    @ResponseBody
    public Result insert(@RequestBody DemandDto demandDto) {
        UserDetail userDetail = SpringSecurityUtil.getUser();
        Integer id = userDetail.getId();
        //获取用户所属的部门
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
        demand.setStatus(0);
        demandDao.insert(demand);
        return OK;
    }
//    @RequestMapping(value = "/uploadFile")
//    @ResponseBody
//    public Result uploadFile(MultipartFile[] files) {
//        List<String> subFiles = new ArrayList<>();
//        for (MultipartFile file : files) {
//            String imgPath  = UploadUtil.saveFile(file);
//            subFiles.add(imgPath);
//        }
//        String subFilesString= StringUtils.join(subFiles.toArray(), ",");
//        return new JSONResult(subFilesString);
//    }
//    @RequestMapping(value = "/deleteFile")
//    @ResponseBody
//    public Result deleteFile(@RequestBody JSONObject dto) {
//        String file = dto.getString("files");
//        String[] files =  file.split(",");
//        String flag = "";
//        for (int i=0;i<files.length;i++){
//            flag  += UploadUtil.deleteFile(files[i]);
//        }
//
//        if(!flag.contains("false")){
//            return OK;
//        }else{
//            return new ErrorResult(ErrorCode.FAIL_DELETE.code(),ErrorCode.FAIL_DELETE.message());
//        }
//    }
}
