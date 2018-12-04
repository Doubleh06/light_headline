package cn.datawin.lightheadline.dto;

import cn.datawin.lightheadline.entity.BaseEntity;
import lombok.Data;

@Data
public class DemandDto {
    private Integer id;
    private String name;
    private String dept;
    private String phone;
    private String memo;
    private String admissibilityDate;
    private String admissibilityTime;
    private Integer faultCategory;
    private Integer faultDetail;
    private String correspondingMode;
    private String emergencyDegree;
    private String contactPoints;
    private String contactReason;
    private String contactDeal;
    private String realStartDate;
    private String realStartTime;
    private String realEndDate;
    private String realEndTime;
    private String contactMan;
    private Integer Status;

}
