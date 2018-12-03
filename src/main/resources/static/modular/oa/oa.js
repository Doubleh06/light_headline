var Oa = {
    tableId: "#grid-table",
    pagerId: "#grid-pager",
    table: null,
    domain: "qadDemand"
};

/**
 * jqGrid初始化参数
 */
Oa.initOptions = function () {
    var options = {
        url : "/oa/grid",
        autowidth:true,
        colNames: ['任务', '提出部门', '提出人','需求类型','审核状态','操作'],
        colModel: [
            {name: 'task', index: 'task', width: 30, sorttype: "int"},
            {name: 'propose_dept_name', index: 'propose_dept_id', width: 90},
            {name: 'propose_user_name', index: 'propost_user_id', width: 200, sortable: false},
            {name: 'demand_type', index: 'demand_type', width: 100, sortable: false,formatter:function(cellValue,options,rowObject){
                if (0==cellValue){
                    return "QAD需求";
                }else if(1==cellValue){
                    return "OA需求";
                }
                }},
            {name: 'status', index: 'status', width: 150, sortable: false,formatter:function(cellValue,options,rowObject){
                var str = "";
                if(0==cellValue){
                    str = "已提交";
                }else if(1==cellValue){
                    str = "流程中";
                }else if(2==cellValue){
                    str = "需求已完成";
                }else{
                    str = "需求拒绝";
                }
                return str;
                }},
            {name: 'operations', index: 'operations', width: 100, sortable: false, formatter: function (cellValue, options, rowObject) {
                var id = "'"+rowObject["id"]+"'";
                var str = "";
                str += '<input type="button" class=" btn btn-sm btn-info"  value="明细" onclick="Oa.detail(' + id + ')"/>&nbsp;';
                // str += '<input type="button" class=" btn btn-sm btn-info"  value="编辑" onclick="Oa.edit(' + id + ')"/>&nbsp;';
                // str += '<input type="button" class=" btn btn-sm btn-danger"  value="删除" onclick="Oa.delete(' + id + ')"/>';
                return str;
            }}
        ]
    };
    return options;
};

/**
 * 根据关键词搜索
 */
Oa.search = function () {
    var searchParam = {};
    searchParam.task = $("#task").val();
    // searchParam.demandType = $("#demandType").val();
    searchParam.proposeDeptId = $("#deptmentId").val();
    searchParam.propostUserId = $("#userId").val();
    console.log(searchParam);
    Oa.table.reload(searchParam);
};

/**
 * 重置搜索
 */
Oa.resetSearch = function () {
    // $("#task").val("");
    // $("#deptmentId").html("请选择");
    // $("#userId").html("请选择");
    // Oa.search();
    window.location.href = "/oa/list";
};


Oa.update = function () {
    var formData = getFormJson($("#create-form"));

    if(!Array.isArray(formData.city)){
        var city = new Array(1);
        city[0] = formData.city
        formData.city = city;
    }
    if(!Array.isArray(formData.subLabel)){
        var subLabel = new Array(1);
        subLabel[0] = formData.subLabel
        formData.subLabel = subLabel;
    }
    console.log(formData);
    $.ajax({
        url:"/articleContent/update",
        type:"post",
        data:JSON.stringify(formData),
        contentType:"application/json",
        success:function (r) {
            if (r.code === 0) {
                success("保存成功");
                //todo  能否按完确定之后跳转链接
                window.location.href = "/articleContent/list";
            }
        }
    });
};

/**
 * 保存
 */
Oa.insert = function () {
    if($('#create-form').find("input[type='file']").val() == ""){
        info('文件为空！');
    }
    var url = $('#create-form').find("input[name='url']").val();
    var urlType = $('#create-form').find("select[name='urlType']").val();
    var sort = $('#create-form').find("input[name='sort']").val();
    var formData = new FormData();
    formData.append("file", $("#insertImg")[0].files[0]);
    formData.append("url",url);
    formData.append("urlType",urlType);
    formData.append("sort",sort);
    console.log(formData)
    $.ajax({
        url: "/articleContent/insert",
        type: 'POST',
        data: formData,
        async: true,
        cache: false,
        contentType: false,
        processData: false,
        success: function (r) {
            if (r.code === 0) {
                $("#createModal").modal("hide");
                success("保存成功");
                Oa.search();
                $("#create-form")[0].reset();
            }
        },
        error: function () {
            info('服务错误请刷新后重新提交！');
        }
    })
};



/**
 * 删除
 *
 * @param id    userId
 */
Oa.delete = function del(id) {
    warning("确定删除吗", "", function () {
        $.get("/articleContent/delete?id=" + id, function () {
            success("成功删除");
            Oa.search();
        });
    })
};

Oa.detail = function (id) {
    window.location.href = "/oa/edit?id="+id;
};


$(function() {
    $('.chosen-select').chosen({width: "100%"});
    var jqGrid = new JqGrid("#grid-table", "#grid-pager", Oa.initOptions());
    Oa.table = jqGrid.init();

});