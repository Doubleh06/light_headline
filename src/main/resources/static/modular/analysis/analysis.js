var analysis = {
    tableId: "#grid-table",
    pagerId: "#grid-pager",
    table: null,
    domain: "analysis"
};



/**
 * 根据关键词搜索
 */
analysis.search = function () {
        var startDate = $("#startDate").val();
        if(null==startDate||""==startDate){
            warning("请选择开始日期");
            return ;
        }
        var endDate = $("#endDate").val();
        if(null==endDate||""==endDate){
            warning("请选择结束日期");
            return ;
        }
        if(endDate<startDate){
            warning("开始日期大于结束日期");
            return ;
        }
        window.location.href = "/analysis/list?startDate="+startDate+"&endDate="+endDate;
};

/**
 * 重置搜索
 */
analysis.resetSearch = function () {
    window.location.href = "/analysis/list?startDate=&endDate=";
};


$(function() {
    $('.chosen-select').chosen({width: "100%"});
    var jqGrid = new JqGrid("#grid-table", "#grid-pager", analysis.initOptions());
    analysis.table = jqGrid.init();

});