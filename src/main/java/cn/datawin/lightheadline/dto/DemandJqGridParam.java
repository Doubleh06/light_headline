package cn.datawin.lightheadline.dto;


import cn.datawin.lightheadline.core.jqGrid.JqGridParam;
import lombok.Data;

/**
 * @author fonlin
 * @date 2018/4/24
 */
@Data
public class DemandJqGridParam extends JqGridParam {

    private String name;
    private String emergencyDegree;
    private Integer status;

}
