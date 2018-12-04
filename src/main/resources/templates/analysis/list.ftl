<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/html">

<head>
<#include "/templates/layout/meta.ftl">
    <link href="/static/css/plugins/chosen/bootstrap-chosen.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/bootstrap-datetimepicker.min.css" />
    <link href="/static/css/plugins/select2/select2.min.css" rel="stylesheet">
    <link href="/static/css/plugins/awesome-bootstrap-checkbox/awesome-bootstrap-checkbox.css" rel="stylesheet">
    <link href="/static/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
    <link href="/static/css/style.css" rel="stylesheet">
</head>

<body>
<div id="wrapper">
<#include "/templates/layout/left.ftl">
    <div id="page-wrapper" class="gray-bg">
    <#include "/templates/layout/header.ftl">

        <div class="row wrapper border-bottom white-bg page-heading">
            <div class="col-lg-10">
                <h2>故障维修分析</h2>
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
                    <div class="ibox ">
                        <div class="ibox-content">
                            <div class="bar search-bar">
                                <div class="form-inline">
                                    <div class="form-group">
                                        <input id="faultDetails" type="hidden" value="${faultDetails}">
                                        <input id="colors" type="hidden" value="${colors}">
                                        <input id="datas" type="hidden" value="${datas}">
                                        <label>开始时间</label>
                                        <div class="input-group date">
                                            <span class="input-group-addon"><i class="fa fa-calendar"></i></span><input id="startDate" name="startDate" type="text" class="form-control" placeholder="MM/dd/yyyy" value="${startDate}">
                                        </div>
                                        &nbsp;&nbsp;&nbsp;&nbsp;
                                        <label>结束时间</label>
                                        <div class="input-group date">
                                            <span class="input-group-addon"><i class="fa fa-calendar"></i></span><input id="endDate" name="endDate" type="text" class="form-control" placeholder="MM/dd/yyyy" value="${endDate}">
                                        </div>
                                        &nbsp;&nbsp;&nbsp;&nbsp;
                                    <button class="btn btn-success"  id="search" type="button" onclick="analysis.search()">搜索</button>&nbsp
                                    <button class="btn btn-success" type="button" onclick="analysis.resetSearch()">重置</button>&nbsp
                                    <#--<button class="btn btn-primary" type="button" onclick="demandList.create()">新增</button>-->
                                    <#--<button class="btn btn-danger" type="button" onclick="demandList.export()">导出</button>-->
                                    <#--<button class="btn btn-primary" onclick="Qad.create()">新增</button>-->

                                        <br>
                                        <br>
                                        <br>
                                        <br>
                                        <br>
                                        <#--<div class="ibox-content">-->
                                            <div class="flot-chart">
                                                <div class="flot-chart-pie-content" id="flot-pie-chart"></div>
                                            </div>
                                        <#--</div>-->
                                </div>
                            </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    <#include "/templates/layout/footer.ftl">
    </div>
</div>

<#--分配角色弹框-->
<#include "/templates/layout/commonjs.ftl">
<!-- Mainly scripts -->
<script src="/static/js/jquery-3.1.1.min.js"></script>
<script src="/static/js/bootstrap.min.js"></script>
<script src="/static/js/plugins/metisMenu/jquery.metisMenu.js"></script>
<script src="/static/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>

<!-- Flot -->
<script src="/static/js/plugins/flot/jquery.flot.js"></script>
<script src="/static/js/plugins/flot/jquery.flot.tooltip.min.js"></script>
<script src="/static/js/plugins/flot/jquery.flot.resize.js"></script>
<script src="/static/js/plugins/flot/jquery.flot.pie.js"></script>
<script src="/static/js/plugins/flot/jquery.flot.time.js"></script>

<!-- Custom and plugin javascript -->
<script src="/static/js/inspinia.js"></script>
<script src="/static/js/plugins/pace/pace.min.js"></script>

<!-- Flot demo data -->
<script src="/static/modular/analysis/flot-demo.js"></script>

<script src="/static/js/bootstrap-datetimepicker.min.js"></script>
<script src="/static/js/plugins/datapicker/bootstrap-datepicker.js"></script>
<script src="/static/modular/analysis/analysis.js"></script>

<script type="text/javascript">
    $(document).ready(function(){
        $('#startDate').datepicker({
            todayBtn: "linked",
            keyboardNavigation: false,
            forceParse: false,
            calendarWeeks: true,
            autoclose: true
        });
        $('#endDate').datepicker({
            todayBtn: "linked",
            keyboardNavigation: false,
            forceParse: false,
            calendarWeeks: true,
            autoclose: true
        });
    });
</script>
</body>
</html>
