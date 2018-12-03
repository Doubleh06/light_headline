var QadEdit = {
    tableId: "#grid-table",
    pagerId: "#grid-pager",
    table: null,
    domain: "createDemand"
};

/**
 * 保存
 */
QadEdit.insert = function () {
    var form = $("#create-form");
    var formData = getFormJson(form);
    console.log(formData);
    $.ajax({
        url: "/createDemand/insert",
        type: 'POST',
        data: JSON.stringify(formData),
        async: true,
        cache: false,
        contentType: "application/json",
        success: function (r) {
            if (r.code === 0) {
                successthen("保存成功",null,"/createDemand/create");
            }
        },
        error: function () {
            info('服务错误请刷新后重新提交！');
        }
    })
};
/**
 * 下载
 */
QadEdit.download = function () {
    var url = "/qad/download?id="+$("#id").val();
    window.location.href=url;
};




$(function() {

});