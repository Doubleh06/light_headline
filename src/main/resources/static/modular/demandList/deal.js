var DealDemand = {
    tableId: "#grid-table",
    pagerId: "#grid-pager",
    table: null,
    domain: "dealDemand"
};

/**
 * 更新
 */
DealDemand.update = function () {
    var form = $("#create-form");
    var formData = getFormJson(form);
    console.log(formData);
    $.ajax({
        url: "/demandList/update",
        type: 'POST',
        data: JSON.stringify(formData),
        async: true,
        cache: false,
        contentType: "application/json",
        success: function (r) {
            if (r.code === 0) {
                successthen("保存成功",null,"/demandList/list")//"/createDemand/create");
            }
        },
        error: function () {
            info('服务错误请刷新后重新提交！');
        }
    })
};


$(function() {

});