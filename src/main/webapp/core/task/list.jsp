<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springsecurity.org/jsp" prefix="security"%>   
<!DOCTYPE html>
<!-- 
Template Name: Metronic - Responsive Admin Dashboard Template build with Twitter Bootstrap 3.3.5
Version: 4.5.2
Author: KeenThemes
Website: http://www.keenthemes.com/
Contact: support@keenthemes.com
Follow: www.twitter.com/keenthemes
Like: www.facebook.com/keenthemes
Purchase: http://themeforest.net/item/metronic-responsive-admin-dashboard-template/4021469?ref=keenthemes
License: You must have a valid license purchased only from themeforest(the above link) in order to legally use the theme for your project.
-->
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<!-- BEGIN HEAD -->

<head>
<meta charset="utf-8" />
<title>京唐港 | 工作中心 - 工作管理</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1" name="viewport" />
<meta content="" name="description" />
<meta content="" name="author" />
<!-- BEGIN GLOBAL MANDATORY STYLES -->
<link
	href="http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700&subset=all"
	rel="stylesheet" type="text/css" />
<link
	href="${IncPath}/assets/global/plugins/bootstrap/css/bootstrap.css"
	rel="stylesheet" type="text/css" />
<link
	href="${IncPath}/assets/global/plugins/bootstrap-table/css/bootstrap-table.css"
	rel="stylesheet" type="text/css" />

<link
	href="${IncPath}/assets/global/plugins/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css" />
<link
	href="${IncPath}/assets/global/plugins/simple-line-icons/simple-line-icons.min.css"
	rel="stylesheet" type="text/css" />
<link
	href="${IncPath}/assets/global/plugins/uniform/css/uniform.default.css"
	rel="stylesheet" type="text/css" />
<link
	href="${IncPath}/assets/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css"
	rel="stylesheet" type="text/css" />
<!-- END GLOBAL MANDATORY STYLES -->
<!-- BEGIN PAGE LEVEL PLUGINS -->
<!--         <link href="${IncPath}/assets/global/plugins/datatables/datatables.min.css" rel="stylesheet" type="text/css" />
        <link href="${IncPath}/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css" rel="stylesheet" type="text/css" /> -->
<!-- END PAGE LEVEL PLUGINS -->
<!-- BEGIN THEME GLOBAL STYLES -->
<link href="${IncPath}/assets/global/css/components.min.css"
	rel="stylesheet" id="style_components" type="text/css" />
<link href="${IncPath}/assets/global/css/plugins.min.css"
	rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css"
	href="${IncPath}/assets/global/plugins/bootstrap-modal/css/bootstrap-modal-bs3patch.css"
	rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css"
	href="${IncPath}/assets/global/plugins/bootstrap-modal/css/bootstrap-modal.css"
	rel="stylesheet" type="text/css" />
<!-- END THEME GLOBAL STYLES -->
<!-- BEGIN THEME LAYOUT STYLES -->
<link href="${IncPath}/assets/layouts/layout/css/layout.min.css"
	rel="stylesheet" type="text/css" />
<link
	href="${IncPath}/assets/layouts/layout/css/themes/darkblue.min.css"
	rel="stylesheet" type="text/css" id="style_color" />
<link href="${IncPath}/assets/layouts/layout/css/custom.min.css"
	rel="stylesheet" type="text/css" />
<link href="${IncPath}/assets/global/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.css" rel="stylesheet" type="text/css" />

<!-- END THEME LAYOUT STYLES -->
<link rel="shortcut icon" href="favicon.ico" />
</head>
<!-- END HEAD -->

<body
	class="page-header-fixed page-sidebar-closed-hide-logo page-content-white">

	<jsp:include page="./../../header.jsp" />

	<!-- BEGIN HEADER & CONTENT DIVIDER -->
	<div class="clearfix"></div>
	<!-- END HEADER & CONTENT DIVIDER -->
	<!-- BEGIN CONTAINER -->
	<div class="page-container">

		<jsp:include page="./../../sidebar.jsp" />

		<!-- BEGIN CONTENT -->
		<div class="page-content-wrapper">
			<!-- BEGIN CONTENT BODY -->
			<div class="page-content">
				<!-- BEGIN PAGE HEADER-->
				<div class="row">
					<div class="col-md-12">
						<!-- BEGIN PAGE TITLE & BREADCRUMB-->
						<h3 class="page-title">工作管理</h3>
						<ul class="page-breadcrumb breadcrumb">
							<li><i class="fa fa-home"></i> <a
								href="${BasePath}/web/main"> 主页 </a> <i
								class="fa fa-angle-right"></i></li>
							<li>管理中心 <i class="fa fa-angle-right"></i>
							</li>
							<li>工作管理</li>
						</ul>
						<!-- END PAGE TITLE & BREADCRUMB-->
					</div>
				</div>
				<!-- END PAGE HEADER-->
				<!-- 
	                    <div class="m-heading-1 border-green m-bordered">
	                        <h3>注意事项</h3>
	                        <p> DataTables is a plug-in for the jQuery Javascript library. It is a highly flexible tool, based upon the foundations of progressive enhancement, and will add advanced interaction controls to any HTML table. </p>
	                        <p> For more info please check out
	                            <a class="btn red btn-outline" href="#" target="_blank">the official documentation</a>
	                        </p>
	                    </div>
                    	-->
				<div class="row">
					<div class="col-md-12">
						<!-- BEGIN EXAMPLE TABLE PORTLET-->
						<div class="portlet light bordered">
							<div class="portlet-title">
								<div class="caption font-dark">
									<i class="icon-settings font-dark"></i> <span
										class="caption-subject bold uppercase">工作列表</span>
								</div>
								<security:authorize buttonUrl="1">
								<div class="col-md-6">
									<div class="btn-group">
										<button id="sample_editable_1_new" class="btn sbold green"
											onclick="javascript:Task.add_click();">
											新增 <i class="fa fa-plus"></i>
										</button>
									</div>
								</div>
								</security:authorize>
							</div>
							<div id="taskStatus" class="modal fade" role="dialog" tabindex="-1" aria-hidden="true" aria-labelledby="myModalLabel" style="display: block;">
							    <div class="modal-dialog">
							        <div class="modal-content">
							            <div class="modal-header bg-primary">
							                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
							                <h4 class="modal-title">
							                    <i class="icon-pencil"></i>
							                    <a class="close" data-dismiss="modal">×</a>
							                    
							                    <span id="lblAddTitle" style="font-weight:bold">船舶靠泊</span>
							                </h4>
							            </div>
						                <div class="modal-body" style="text-align:left;">
						                    <form  id ='updateForm' class="bs-example bs-example-form" role = "form">
						                    <div class="modal-body" >
<!-- 												<div class = "input-group" >
						                                <span class="input-group-addon text-center"><b class="icon-td">作业状态</b></span>
														<select id="modiStatus" name="modiStatus" class="form-control select2me"  style="width:240px;">
										   					<option value="0" >预靠</option>
										   					<option value="1" >作业中</option>
										   					<option value="2" >已离港</option>
														</select>
												</div> -->
		<!-- 										<div class = "input-group" id="begintime-div">
														<span class="input-group-addon text-center"><b class="icon-td">开工时间</b></span>
												        <input class="form-control form_datetime" name="beginTime"  placeholder="请选择开工时间" id="beginTime" style="width:240px;" readonly />
												</div> 
												<div class = "input-group" id="endtime-div">
														<span class="input-group-addon text-center"><b class="icon-td">完工时间</b></span>
												        <input class="form-control form_datetime" name="endTime" placeholder="请选择完工时间" id="endTime" style="width:240px;" readonly />
												</div>-->
												<div class = "input-group" id="berthingTime-div">
														<span class="input-group-addon text-center"><b class="icon-td">靠泊时间</b></span>
												        <input class="form-control form_datetime" name="berthingTime" placeholder="请选择靠泊时间" id="berthingTime" style="width:240px;" readonly />
												</div>										
												<input type="hidden" class="form-control" name="taskId" placeholder="taskId" id="taskId" />
												<input type="hidden" class="form-control" name="status" placeholder="status" id="status" />
												
						                    </div>                            
						                    </form>
						                </div>
										<div class="modal-footer bg-info"  style="width:500px;">
											<button type="button" class="btn btn-default" data-dismiss="modal"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>取消</button>
											<button type="button" id="btn_submit" class="btn btn-primary" data-dismiss="modal"><span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>提交</button>
										</div>
							        </div>
							    </div>
							</div>	

							<div class="portlet-body">
								<div class="table-toolbar">
									<div class="col-md-12 col-sm-6">
										<div class="panel panel-default">
											<div class="panel-body">
												<ul class="nav nav-pills">

													<li class="" id="li_ykcb"
														style="border-style: solid; border-width: 1.4px; border-color: #2f353b;"><a
														href="#ykcb" onclick="javascript:loadList('0');" data-toggle="tab">预靠船舶</a></li>
													<li class=""  id="li_zycb"
														style="border-style: solid; border-width: 1.4px; border-color: #2f353b;"><a
														href="#zycb" onclick="javascript:loadList('1');" data-toggle="tab">作业船舶</a></li>
													<li class="" id="li_lgcb"
														style="border-style: solid; border-width: 1.4px; border-color: #2f353b;"><a
														href="#lgcb" onclick="javascript:loadList('2');" data-toggle="tab">离港船舶</a>
												</ul>
												<div class="tab-content">
													<div id="toolbar" class="btn-group">
														<!-- 
														<button id="btn_add" type="button" class="btn btn-default">
															<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
														</button>
														
														<button id="btn_edit" type="button"
															class="btn btn-default">
															<span class="glyphicon glyphicon-pencil"
																aria-hidden="true"></span>修改
														</button>
														<button id="btn_delete" type="button"
															class="btn btn-default">
															<span class="glyphicon glyphicon-remove"
																aria-hidden="true"></span>删除
														</button>
														 -->
													</div>
													<div class="tab-pane fade" id="ykcb" >
														<table id="ykcbship"
															class="table table-striped table-bordered table-hover table-checkable order-column"></table>
													</div>
													<div class="tab-pane fade" id="zycb" > <!-- active in -->
														<table id="zycbship"
															class="table table-striped table-bordered table-hover table-checkable order-column"></table>
													</div>
													<div class="tab-pane fade" id="lgcb" >
														<table id="lgcbship"
															class="table table-striped table-bordered table-hover table-checkable order-column"></table>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
								<!-- table table-striped table-bordered table-hover table-checkable order-column -->

							</div>
						</div>
						<!-- END EXAMPLE TABLE PORTLET-->
					</div>
				</div>
				<!-- END CONTENT BODY -->
			</div>
			<!-- END CONTENT -->
		</div>
	</div>
	<!-- END CONTAINER -->
	<jsp:include page="./../../footer.jsp" />
	<!--[if lt IE 9]>
<script src="${IncPath}/assets/global/plugins/respond.min.js"></script>
<script src="${IncPath}/assets/global/plugins/excanvas.min.js"></script> 
<![endif]-->
	<!-- BEGIN CORE PLUGINS -->
	<script src="${IncPath}/assets/global/plugins/jquery.min.js"
		type="text/javascript"></script>
	<script
		src="${IncPath}/assets/global/plugins/bootstrap-table/js/bootstrap-table.js"
		type="text/javascript"></script>
	<script
		src="${IncPath}/assets/global/plugins/bootstrap/js/bootstrap.js"
		type="text/javascript"></script>
	<script src="${IncPath}/assets/global/plugins/js.cookie.min.js"
		type="text/javascript"></script>
	<script
		src="${IncPath}/assets/global/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js"
		type="text/javascript"></script>
	<script
		src="${IncPath}/assets/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js"
		type="text/javascript"></script>
	<script src="${IncPath}/assets/global/plugins/jquery.blockui.min.js"
		type="text/javascript"></script>
	<script
		src="${IncPath}/assets/global/plugins/uniform/jquery.uniform.min.js"
		type="text/javascript"></script>
	<script
		src="${IncPath}/assets/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js"
		type="text/javascript"></script>
	<!-- END CORE PLUGINS -->
	<!-- BEGIN PAGE LEVEL PLUGINS -->
	<!--         <script src="${IncPath}/assets/global/scripts/datatable.js" type="text/javascript"></script>
        <script src="${IncPath}/assets/global/plugins/datatables/datatables.min.js" type="text/javascript"></script>
        <script src="${IncPath}/assets/global/plugins/datatables/datatables.js?v=201709281620" type="text/javascript"></script>
        <script src="${IncPath}/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" type="text/javascript"></script>     -->

	<script
		src="${IncPath}/assets/global/plugins/bootstrap-table/js/bootstrap-table-zh-CN.js"
		type="text/javascript"></script>
	<!-- END PAGE LEVEL PLUGINS -->
	<!-- BEGIN THEME GLOBAL SCRIPTS -->
	<script src="${IncPath}/assets/global/scripts/app.min.js"
		type="text/javascript"></script>
	<script
		src="${IncPath}/assets/global/plugins/bootstrap-modal/js/bootstrap-modalmanager.js"
		type="text/javascript"></script>
	<script
		src="${IncPath}/assets/global/plugins/bootstrap-modal/js/bootstrap-modal.js"
		type="text/javascript"></script>
	<script
		src="${IncPath}/assets/global/plugins/jquery-validation/js/jquery.validate.min.js"></script>
	<script
		src="${IncPath}/assets/global/plugins/jquery-validation/js/additional-methods.min.js"></script>
	<!-- END THEME GLOBAL SCRIPTS -->
	<!-- BEGIN PAGE LEVEL SCRIPTS -->
	<script
		src="${IncPath}/assets/pages/scripts/table-datatables-managed.min.js"
		type="text/javascript"></script>
	<!-- END PAGE LEVEL SCRIPTS -->
	<!-- BEGIN THEME LAYOUT SCRIPTS -->
	<script src="${IncPath}/assets/layouts/layout/scripts/layout.min.js"
		type="text/javascript"></script>
	<script src="${IncPath}/assets/layouts/layout/scripts/demo.min.js"
		type="text/javascript"></script>
	<script
		src="${IncPath}/assets/layouts/global/scripts/quick-sidebar.min.js"
		type="text/javascript"></script>
	<script src="${IncPath}/assets/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js" type="text/javascript"></script>
	<script src="${IncPath}/assets/global/plugins/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js" type="text/javascript"></script>		
	<script type="text/javascript">
		var IncPath = '${IncPath}';
		var BasePath = '${BasePath}';
		var type = '${type}';
	</script>
	<script src="${IncPath}/cl.js?v=${jsVersion}" type="text/javascript"></script>
	<script src="${IncPath}/core/task/task.js?v=${jsVersion}" type="text/javascript"></script>
	<!-- BEGIN THEME LAYOUT SCRIPTS -->
	<!-- END THEME LAYOUT SCRIPTS -->
	<script>
		jQuery(document).ready(function() {
			// initiate layout and plugins		
			App.init();
			Cl.initModal();
			$('#ykcbship').bootstrapTable("destroy").bootstrapTable(Task.options('0'));
			$('#zycbship').bootstrapTable("destroy").bootstrapTable(Task.options('1'));
			$('#lgcbship').bootstrapTable("destroy").bootstrapTable(Task.options('2'));
			if ('0' == type) {
				$('#li_ykcb').addClass("active");
				$('#ykcb').addClass("active in");
			} else if ('2' == type) {
				$('#li_lgcb').addClass("active");
				$('#lgcb').addClass("active in");
			} else {
				$('#li_zycb').addClass("active");
				$('#zycb').addClass("active in");
			}
     		$(".form_datetime").datetimepicker({
            	startView: 'month',
            	minView: 'hour',
   			    language:  'zh-CN',
   			    format: 'yyyy-mm-dd hh:ii:ss',
   			    todayBtn:  true,
   			    autoclose: true,
            	clearBtn: true,
            	todayHighlight: true,
            	showMeridian: true,
            	endDate: new Date()
   			});
    		
            $('#btn_submit').modal({backdrop: 'static', show:false,  keyboard: false});

            $('#btn_submit').off().on("click", function () {
            	
            	if($("#berthingTime").is(':visible')){
    	        	if($("#berthingTime").val()==""){
    					alert("请选择靠泊时间！");
    					return;        		
    	        	}
            	}
    /*        	if($("#modiStatus").val()==2 && $("#endTime").val()==""){
    				alert("请选择完工时间！");
    				return;        		
            	}*/
            	var data={
            			status:$("#status").val(),
            			taskId:$("#taskId").val(),
//            			endTime:$("#endTime").val(),
//            			beginTime:$("#beginTime").val(),
            			time:$("#berthingTime").val(),
            	}
                $.ajax({
                    url: BasePath + "/task/doSetShipStatus",
                    type: "post",
                    dataType: "json",
                    cache: false,
                    async:false,
                    data: data,
                    success: function (data) {
                    	if (Cl.successInt == data.code){
                    		alert(data.msg);  
    						if (status == '1') {
    							window.location.href = BasePath + "/task/tasklist?type=2";
    						} else {
    							window.location.href = BasePath + "/task/tasklist";
    						}
    					} else {
                    		alert(data.msg);                		
                    	}
                    },
                    failure: function(data){
                        alert("修改失败!");
                    }                
                });
            });
		});
	</script>
</body>

</html>