var demandList = {
    tableId: "#grid-table",
    pagerId: "#grid-pager",
    table: null,
    domain: "demandList"
};

/**
 * jqGrid初始化参数
 */
demandList.initOptions = function () {
    var options = {
        url : "/demandList/grid",
        autowidth:true,
        colNames: ['编号','要求人', '提出部门', '电话','紧急程度','状态','受理时间','操作'],
        colModel: [
            {name: 'id', index: 'id', width: 20},
            {name: 'name', index: 'name', width: 60},
            {name: 'dept_name', index: 'dept_name', width: 60},
            {name: 'phone', index: 'phone', width: 200, sortable: false},
            {name: 'emergency_degree', index: 'emergency_degree', width: 100, sortable: false,formatter:function(cellValue,options,rowObject){
                if (0==cellValue){
                    return "一般";
                }else if(1==cellValue){
                    return "次要";
                }else if(2==cellValue){
                    return "紧急";
                }
                }},
            {name: 'status', index: 'status', width: 150, sortable: false,formatter:function(cellValue,options,rowObject){
                var str = "";
                if(0==cellValue){
                    str = "未处理";
                }else if(1==cellValue){
                    str = "处理中";
                }else if(2==cellValue){
                    str = "处理完成";
                }
                return str;
                }},
            {name: 'admissibility_time', index: 'admissibility_time', width: 100,align: "center", editable: false,formatter: function (cellvar, options, rowObject) {
                    if (cellvar == "" || cellvar == undefined) {
                        return "";
                    }
                    var da = new Date(cellvar);
                    return dateFtt("yyyy-MM-dd hh:mm:ss", da);
                }},
            {name: 'operations', index: 'operations', width: 100, sortable: false, formatter: function (cellValue, options, rowObject) {
                var id = "'"+rowObject["id"]+"'";
                var str = "";
                str += '<input type="button" class=" btn btn-sm btn-info"  value="处  理" onclick="demandList.deal(' + id + ')"/>&nbsp;';
                // str += '<input type="button" class=" btn btn-sm btn-info"  value="编辑" onclick="demandList.edit(' + id + ')"/>&nbsp;';
                // str += '<input type="button" class=" btn btn-sm btn-danger"  value="删除" onclick="demandList.delete(' + id + ')"/>';
                return str;
            }}
        ]
    };
    return options;
};

/**
 * 根据关键词搜索
 */
demandList.search = function () {
    var searchParam = {};
    searchParam.name = $("#name").val();
    searchParam.emergencyDegree = $("#emergencyDegree").val();
    searchParam.status = $("#status").val();
    console.log(searchParam);
    demandList.table.reload(searchParam);
};

/**
 * 重置搜索
 */
demandList.resetSearch = function () {
    // $("#task").val("");
    // $("#deptmentId").html("请选择");
    // $("#userId").html("请选择");
    // demandList.search();
    window.location.href = "/demandList/list";
};

/**
 *新增
 */
demandList.create = function () {
    window.location.href = "/createDemand/create";
}
/**
 * 导出
 */
demandList.export = function () {
    window.location.href = "/demandList/export";

    // $("#exportModal").modal();
    // $.ajax({
    //     type : 'POST',
    //     url: '/demandList/export',
    //     contentType : "application/json" ,
    //     // data : JSON.stringify({
    //     //     "keys" : keys
    //     // }),
    //     success : function(data) {
    //          window.open("/leads/download?key="+data.obj);
    //     }
    // });
}



/**
 * 删除
 *
 * @param id    userId
 */
demandList.delete = function del(id) {
    warning("确定删除吗", "", function () {
        $.get("/demandList/delete?id=" + id, function () {
            success("成功删除");
            demandList.search();
        });
    })
};

demandList.deal = function (id) {
    window.location.href = "/demandList/deal?id="+id;
};

    function dateFtt(fmt,date)
    { //author: meizz
        var o = {
            "M+" : date.getMonth()+1,                 //月份
            "d+" : date.getDate(),                    //日
            "h+" : date.getHours(),                   //小时
            "m+" : date.getMinutes(),                 //分
            "s+" : date.getSeconds(),                 //秒
            "q+" : Math.floor((date.getMonth()+3)/3), //季度
            "S"  : date.getMilliseconds()             //毫秒
        };
        if(/(y+)/.test(fmt))
            fmt=fmt.replace(RegExp.$1, (date.getFullYear()+"").substr(4 - RegExp.$1.length));
        for(var k in o)
            if(new RegExp("("+ k +")").test(fmt))
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
        return fmt;
    }

$(function() {
    $('.chosen-select').chosen({width: "100%"});
    var jqGrid = new JqGrid("#grid-table", "#grid-pager", demandList.initOptions());
    demandList.table = jqGrid.init();

});