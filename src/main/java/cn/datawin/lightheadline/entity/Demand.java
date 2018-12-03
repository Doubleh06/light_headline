package cn.datawin.lightheadline.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Demand extends BaseEntity {
    private String name;
    private String dept;
    private String phone;
    //0：未处理  1：处理中  2：处理完成  3：处理失败
    private Integer status;
    private String memo;
    private Date admissibilityTime;
    private Integer faultCategory;
    private Integer faultDetail;
    private String correspondingMode;
    private String emergencyDegree;
    private String contactPoints;
    private String contactReason;
    private String contactDeal;
    private Date realStartTime;
    private Date realEndTime;
    private String realDealTime;
    private String contactMan;

}
