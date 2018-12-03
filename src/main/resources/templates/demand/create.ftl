<!DOCTYPE html>
<html>

<head>
<#include "/templates/layout/meta.ftl">
    <link href="/static/css/plugins/chosen/bootstrap-chosen.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/bootstrap-datetimepicker.min.css" />
    <link href="/static/css/plugins/summernote/summernote.css" rel="stylesheet">
    <link href="/static/css/plugins/summernote/summernote-bs3.css" rel="stylesheet">
    <link href="/static/css/plugins/select2/select2.min.css" rel="stylesheet">
    <link href="/static/css/plugins/dropzone/dropzone.css" rel="stylesheet">
    <link href="/static/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
    <link href="/static/css/plugins/clockpicker/clockpicker.css" rel="stylesheet">
    <link href="/static/css/style.css" rel="stylesheet">

</head>

<body>
<div id="wrapper">
<#include "/templates/layout/left.ftl">
    <div id="page-wrapper" class="gray-bg">
    <#include "/templates/layout/header.ftl">

        <div class="row wrapper border-bottom white-bg page-heading">
            <div class="col-lg-10">
                <h2>故障受理表</h2>
                <ol class="breadcrumb">
                    <li>
                        <a href="/main">Home</a>
                    </li>
                    <li class="active">
                        <strong>列表</strong>
                    </li>
                </ol>
            </div>
            <div class="col-lg-2">

            </div>
        </div>

        <div class="wrapper wrapper-content">
            <div class="row">
                <div class="col-lg-12">
                    <form class="form-horizontal"  id="create-form" enctype="multipart/form-data" method="post" >


                        <div class="form-group">
                            <label class="col-sm-2 control-label">状态</label>
                            <div class="col-sm-4">
                                <select class="form-control" name="status">
                                    <option value="0">未处理</option>
                                    <option value="1">处理中</option>
                                    <option value="2">处理完成</option>
                                </select>
                            </div>
                            <label class="col-sm-2 control-label">受理时间</label>
                            <div class="col-sm-2">
                                <div class="input-group date">
                                    <span class="input-group-addon"><i class="fa fa-calendar"></i></span><input id="admissibilityDate" name="admissibilityDate" type="text" class="form-control" placeholder="MM/dd/yyyy">
                                </div>
                            </div>
                            <div class="col-sm-2">
                                <div class="input-group clockpicker" data-autoclose="true">
                                    <input type="text" class="form-control" placeholder="xx:xx" id="admissibilityTime" name="admissibilityTime">
                                    <span class="input-group-addon">
                                    <span class="fa fa-clock-o"></span>
                                </span>
                                </div>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label">要求人</label>
                            <div class="col-sm-2">
                                <input id="name" type="text" class="form-control" name="name" placeholder="请填入">
                            </div>
                            <label class="col-sm-2 control-label">部门</label>
                            <div class="col-sm-2">
                                <select class="form-control" name="dept">
                                <#list deptments as deptment>
                                    <option value="${deptment.id}">${deptment.deptName}</option>
                                </#list>
                                </select>
                            </div>
                            <label class="col-sm-2 control-label">电话</label>
                            <div class="col-sm-2">
                                <input id="phone" type="text" class="form-control" name="phone" placeholder="请填入">
                            </div>
                        </div>

                        <div class="form-group">

                            <#--<div class="col-sm-4">-->
                                <#--<input id="dept" type="text" class="form-control" name="dept" placeholder="请填入">-->
                            <#--</div>-->

                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label">故障类别</label>
                            <div class="col-sm-4">
                                <select class="form-control" name="faultCategory">
                                    <#list faultCategories as faultCategory>
                                        <option value="${faultCategory.id}">${faultCategory.faultCategory}</option>
                                    </#list>
                                </select>
                            </div>
                            <label class="col-sm-2 control-label">故障明细</label>
                            <div class="col-sm-4">
                                <select class="form-control" name="faultDetail">
                                    <#list faultDetails as faultDetail>
                                        <option value="${faultDetail.id}">${faultDetail.faultDetail}</option>
                                    </#list>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label">紧急程度</label>
                            <div class="col-sm-4">
                                <select class="form-control" name="emergencyDegree">
                                    <option value="0">一般</option>
                                    <option value="1">次要</option>
                                    <option value="2">紧急</option>
                                </select>
                            </div>
                            <label class="col-sm-2 control-label">对应方式</label>
                            <div class="col-sm-4">
                                <input id="correspondingMode" type="text" class="form-control" name="correspondingMode">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label">联络要点</label>
                            <div class="col-sm-10">
                                <textarea id="contactPoints" name="contactPoints" placeholder="请填入" style="width: 100%;height: 100px"></textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">状况原因</label>
                            <div class="col-sm-10">
                                <textarea id="contactReason" name="contactReason" placeholder="请填入" style="width: 100%;height: 100px"></textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">对应</label>
                            <div class="col-sm-10">
                                <textarea id="contactDeal" name="contactDeal" placeholder="请填入" style="width: 100%;height: 100px"></textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">对应人</label>
                            <div class="col-sm-10">
                                <input id="contactMan" type="text" class="form-control" name="contactMan">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label">实际开始时间</label>
                            <div class="col-sm-2">
                                <div class="input-group date">
                                    <span class="input-group-addon"><i class="fa fa-calendar"></i></span><input id="realStartDate" name="realStartDate" type="text" class="form-control" placeholder="MM/dd/yyyy">
                                </div>
                            </div>
                            <div class="col-sm-2">
                                <div class="input-group clockpicker" data-autoclose="true">
                                    <input type="text" class="form-control" placeholder="xx:xx" id="realStartTime" name="realStartTime">
                                    <span class="input-group-addon">
                                    <span class="fa fa-clock-o"></span>
                                </span>
                                </div>
                            </div>
                            <label class="col-sm-2 control-label">实际结束时间</label>
                            <div class="col-sm-2">
                                <div class="input-group date">
                                    <span class="input-group-addon"><i class="fa fa-calendar"></i></span><input id="realEndDate" name="realEndDate" type="text" class="form-control" placeholder="MM/dd/yyyy">
                                </div>
                            </div>
                            <div class="col-sm-2">
                                <div class="input-group clockpicker" data-autoclose="true">
                                    <input type="text" class="form-control" placeholder="xx:xx" id="realEndTime" name="realEndTime">
                                    <span class="input-group-addon">
                                    <span class="fa fa-clock-o"></span>
                                </span>
                                </div>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label">备注</label>
                            <div class="col-sm-10">
                                <input id="memo" type="text" class="form-control" name="memo">
                            </div>
                            <#--<div class="col-sm-10" hidden>-->
                                <#--<input id="files" type="text" class="form-control" name="files" >-->
                            <#--</div>-->
                        </div>
                        <div class="col-sm-4 col-sm-offset-2">
                            <button type="button" class="btn btn-sm btn-primary" onclick="CreateDemand.insert()">确定</button>
                        <#--<button type="button" class="btn btn-sm btn-default" data-dismiss="modal">关闭</button>-->
                        </div>
                    </form>
                    <#--<div class="col-sm-6 col-sm-offset-2">-->
                        <#--<form action="#" class="dropzone " id="dropzoneForm">-->
                            <#--<div class="fallback">-->
                                <#--<input name="subImgs" type="file"  multiple />-->
                            <#--</div>-->
                        <#--</form>-->
                    <#--</div>-->
                    <#--</br>-->
                    <#--</br>-->


                </div>
            </div>
        </div>
    <#include "/templates/layout/footer.ftl">
    </div>
</div>


<#--分配角色弹框-->
<#include "/templates/layout/commonjs.ftl">
<script src="/static/js/plugins/chosen/chosen.jquery.js"></script>
<script src="/static/modular/demand/create.js"></script>
<script src="/static/js/plugins/select2/select2.full.min.js"></script>
<script src="/static/js/plugins/dropzone/dropzone.js"></script>
<script src="/static/js/plugins/datapicker/bootstrap-datepicker.js"></script>
<script src="/static/js/plugins/clockpicker/clockpicker.js"></script>

<script type="text/javascript">
    $(document).ready(function() {
        $('#admissibilityDate').datepicker({
            todayBtn: "linked",
            keyboardNavigation: false,
            forceParse: false,
            calendarWeeks: true,
            autoclose: true
        });
        $('#realStartDate').datepicker({
            todayBtn: "linked",
            keyboardNavigation: false,
            forceParse: false,
            calendarWeeks: true,
            autoclose: true
        });
        $('#realEndDate').datepicker({
            todayBtn: "linked",
            keyboardNavigation: false,
            forceParse: false,
            calendarWeeks: true,
            autoclose: true
        });
    });
    $('.clockpicker').clockpicker();
</script>
</body>
</html>
