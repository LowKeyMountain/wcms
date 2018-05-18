<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springsecurity.org/jsp" prefix="security"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<link href="http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700&subset=all" rel="stylesheet" type="text/css" />
<link href="${IncPath}/assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
<link href="${IncPath}/assets/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css" />
<link href="${IncPath}/assets/global/plugins/bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css" />
<link href="${IncPath}/assets/global/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css" />
<link href="${IncPath}/assets/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css" rel="stylesheet" type="text/css" />
<!-- END GLOBAL MANDATORY STYLES -->
<!-- BEGIN PAGE LEVEL PLUGINS -->
<link href="${IncPath}/assets/global/plugins/datatables/datatables.min.css" rel="stylesheet" type="text/css" />
<link href="${IncPath}/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css" rel="stylesheet" type="text/css" />
 <link href="${IncPath}/assets/global/plugins/select2/css/select2.min.css" rel="stylesheet" type="text/css" />
<link href="${IncPath}/assets/global/plugins/select2/css/select2-bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="${IncPath}/assets/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" rel="stylesheet" type="text/css" />
<link href="${IncPath}/assets/global/plugins/bootstrap-wysihtml5/bootstrap-wysihtml5.css" rel="stylesheet" type="text/css" />
<link href="${IncPath}/assets/global/plugins/bootstrap-markdown/css/bootstrap-markdown.min.css" rel="stylesheet" type="text/css" />
<link href="${IncPath}/assets/global/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.css" rel="stylesheet" type="text/css" />

<!-- END PAGE LEVEL PLUGINS -->
<!-- BEGIN THEME GLOBAL STYLES -->
<link href="${IncPath}/assets/global/css/components.min.css" rel="stylesheet" id="style_components" type="text/css" />
<link href="${IncPath}/assets/global/css/plugins.min.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${IncPath}/assets/global/plugins/bootstrap-modal/css/bootstrap-modal-bs3patch.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="${IncPath}/assets/global/plugins/bootstrap-modal/css/bootstrap-modal.css" rel="stylesheet" type="text/css"/>
<!-- END THEME GLOBAL STYLES -->
<!-- BEGIN THEME LAYOUT STYLES -->
<link href="${IncPath}/assets/layouts/layout/css/layout.min.css" rel="stylesheet" type="text/css" />
<link href="${IncPath}/assets/layouts/layout/css/themes/darkblue.min.css" rel="stylesheet" type="text/css" id="style_color" />
<link href="${IncPath}/assets/layouts/layout/css/custom.min.css" rel="stylesheet" type="text/css" />
<!-- END THEME LAYOUT STYLES -->
<link href="${IncPath}/assets/global/plugins/jquery-multi-select/css/multi-select.css" rel="stylesheet" type="text/css" />

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
						<h3 class="page-title">卸船情况</h3>
						<ul class="page-breadcrumb breadcrumb">
							<li><i class="fa fa-home"></i> <a
								href="${BasePath}/web/main"> 主页 </a> <i
								class="fa fa-angle-right"></i></li>
							<li>管理中心 <i class="fa fa-angle-right"></i>
							</li>
							<li><a href="${BasePath}/task/tasklist"> 工作管理 </a><i
								class="fa fa-angle-right"></i></li>
							<li>卸船情况</li>
						</ul>
						<!-- END PAGE TITLE & BREADCRUMB-->
					</div>
				</div>
				<!-- END PAGE HEADER-->
				<div class="row">
					<div class="col-md-12">
						<div id="cabinStatus" class="modal fade" role="dialog" tabindex="-1" aria-hidden="true" aria-labelledby="myModalLabel" style="display: block;">
						    <div class="modal-dialog">
						        <div class="modal-content">
						            <div class="modal-header bg-primary">
						                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
						                <h4 class="modal-title">
						                    <i class="icon-pencil"></i>
						                    <a class="close" data-dismiss="modal">×</a>
						                    
						                    <span id="lblAddTitle" style="font-weight:bold">清舱</span>
						                </h4>
						            </div>
					                <div class="modal-body" style="text-align:left;">
					                    <form  id ='qcForm' class="bs-example bs-example-form" role = "form">
					                    <div class="modal-body" >
											<div class = "input-group" id="cleartime-div">
													<span class="input-group-addon text-center"><b class="icon-td">清舱时间</b></span>
											        <input class="form-control form_datetime" name="clearTime" placeholder="请选择清舱时间" id="clearTime" style="width:240px;" readonly />
											</div>
<!-- 											<div class = "input-group" id="berthingTime-div">
													<span class="input-group-addon text-center"><b class="icon-td">靠泊时间</b></span>
											        <input class="form-control form_datetime" name="berthingTime" placeholder="请选择靠泊时间" id="berthingTime" style="width:240px;" readonly />
											</div> -->										
											<input type="hidden" class="form-control" name="taskId" placeholder="taskId" id="taskId" />
											<input type="hidden" class="form-control" name="cabinNo" placeholder="cabinNo" id="cabinNo" />											
											<input type="hidden" class="form-control" name="status" placeholder="status" id="status" />
											
					                    </div>                            
					                    </form>
					                </div>
									<div class="modal-footer bg-info"  style="width:500px;">
										<button type="button" class="btn btn-default" data-dismiss="modal"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>取消</button>
										<button type="button" id="btn_submit_qc" class="btn btn-primary" data-dismiss="modal"><span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>提交</button>
									</div>
						        </div>
						    </div>
						</div>

						<div id="taskStatus" class="modal fade" role="dialog" tabindex="-1" aria-hidden="true" aria-labelledby="myModalLabel" style="display: block;">
						    <div class="modal-dialog">
						        <div class="modal-content">
						            <div class="modal-header bg-primary">
						                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
						                <h4 class="modal-title">
						                    <i class="icon-pencil"></i>
						                    <a class="close" data-dismiss="modal">×</a>
						                    
						                    <span id="lblAddTitle" style="font-weight:bold">完成卸船</span>
						                </h4>
						            </div>
					                <div class="modal-body" style="text-align:left;">
					                    <form  id ='wgForm' class="bs-example bs-example-form" role = "form">
					                    <div class="modal-body" >
											<div class = "input-group" id="endtime-div">
													<span class="input-group-addon text-center"><b class="icon-td">完工时间</b></span>
											        <input class="form-control form_datetime" name="endTime" placeholder="请选择完工时间" id="endTime" style="width:240px;" readonly />
											</div>
<!-- 											<div class = "input-group" id="berthingTime-div">
													<span class="input-group-addon text-center"><b class="icon-td">靠泊时间</b></span>
											        <input class="form-control form_datetime" name="berthingTime" placeholder="请选择靠泊时间" id="berthingTime" style="width:240px;" readonly />
											</div> -->										
											<input type="hidden" class="form-control" name="taskId" placeholder="taskId" id="taskId" />
											<input type="hidden" class="form-control" name="status" placeholder="status" id="status" />
											
					                    </div>                            
					                    </form>
					                </div>
									<div class="modal-footer bg-info"  style="width:500px;">
										<button type="button" class="btn btn-default" data-dismiss="modal"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>取消</button>
										<button type="button" id="btn_submit_wg" class="btn btn-primary" data-dismiss="modal"><span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>提交</button>
									</div>
						        </div>
						    </div>
						</div>			            
						<!-- BEGIN EXAMPLE TABLE PORTLET-->
						<div class="tab-pane" id="cabin">
							<div class="portlet box yellow">
								<div class="portlet-title">
									<div class="caption">
										<i><a href="javascript:UnshipInfo.view_ship_click(${taskId})">${shipName}</a></i>
									</div>
									<div class="tools">
										<a onclick="javascript:history.back(-1);" class="fa fa-reply"
										data-original-title="返回" title="返回"> </a> <a
										onclick="javascript:UnshipInfo.list();" class="reload" data-original-title="刷新"
										title="刷新"> </a>
									</div>

								</div>
	
								<div class="portlet-body" style="display: block;">
									<div class="table-responsive">
<!-- 										<div class="dropdown" id="toolbar" >
										  <button class="btn btn-default dropdown-toggle" type="button" id="menu1" data-toggle="dropdown">卸货统计
										  <span class="caret"></span></button>
										  <ul class="dropdown-menu" role="menu" aria-labelledby="menu1">
										    <li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:UnshipInfo.unloadProgress_click(${taskId})">货物进度</a></li>
										    <li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:UnshipInfo.unloaderOverview_click(${taskId})">卸船机总览</a></li>
										  </ul>
										</div> -->
								        <div id="toolbar" align="right">
								        	
								            <button type="button" onclick="javascript:UnshipInfo.unloadProgress_click(${taskId});" class="btn default" >货物进度
								            </button>
								            <button type="button" onclick="javascript:UnshipInfo.unloaderOverview_click(${taskId});" class="btn default" >卸船机总览
								            </button>
								            								            
								        </div>
										<table class="table table-striped table-bordered table-hover editable">
											<thead>
												<tr>
													<th>船舱号</th>
													<th>货名</th>
													<th>总量</th>
													<th>清舱前</th>
													<th>清舱量</th>			
													<th>完成量</th>
													<th>剩余量</th>
													<th>状态</th>
													<th>清舱时间</th>
													<th>操作</th>
												</tr>
											</thead>
											<tbody id="unshipInfo_tbody">
											</tbody>
										</table>
										<div style="text-align: center">
											<c:choose>
												<c:when test="${task.status == 0 || task.status == 1}">
													<security:authorize buttonUrl="6">
													<button id="sample_editable_1_new" class="btn default"
														onclick="javascript:UnshipInfo.modifyCabinPosition_click(${taskId});">
														舱口标定 <i class="fa"></i>
													</button>
													</security:authorize>
												</c:when>
											</c:choose>
											<c:choose>
												<c:when test="${task.status == 0}">
													<button id="sample_editable_1_new" class="btn default"
														onclick="javascript:UnshipInfo.setShipStatus_click('${taskId}','0');">
														开始卸船 <i class="fa"></i>
													</button>
												</c:when>
												<c:when test="${task.status == 1}">
												<security:authorize buttonUrl="9">
													<button id="sample_editable_1_new" class="btn default"
														onclick="javascript:UnshipInfo.setShipStatus_click('${taskId}','1');">
														完成卸船 <i class="fa"></i>
													</button>
												</security:authorize>
												</c:when>
											</c:choose>
										</div>
									</div>
								</div>
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
<script src="${IncPath}/assets/global/plugins/jquery.min.js" type="text/javascript"></script>
<script src="${IncPath}/assets/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="${IncPath}/assets/global/plugins/js.cookie.min.js" type="text/javascript"></script>
<script src="${IncPath}/assets/global/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js" type="text/javascript"></script>
<script src="${IncPath}/assets/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
<script src="${IncPath}/assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
<script src="${IncPath}/assets/global/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
<script src="${IncPath}/assets/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js" type="text/javascript"></script>
<!-- END CORE PLUGINS -->
<!-- BEGIN PAGE LEVEL PLUGINS -->
<script src="${IncPath}/assets/global/scripts/datatable.js" type="text/javascript"></script>
<!-- <script src="${IncPath}/assets/global/plugins/datatables/datatables.min.js" type="text/javascript"></script> -->
<script src="${IncPath}/assets/global/plugins/datatables/datatables.js?v=201709281620" type="text/javascript"></script>
<script src="${IncPath}/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" type="text/javascript"></script>
<!-- END PAGE LEVEL PLUGINS -->
<!-- BEGIN THEME GLOBAL SCRIPTS -->
<script src="${IncPath}/assets/global/scripts/app.min.js" type="text/javascript"></script>
<script src="${IncPath}/assets/global/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript"></script>
<script src="${IncPath}/assets/global/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript"></script>
<script src="${IncPath}/assets/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js" type="text/javascript"></script>

<!-- END THEME GLOBAL SCRIPTS -->
<!-- BEGIN PAGE LEVEL SCRIPTS -->
<script src="${IncPath}/assets/pages/scripts/table-datatables-managed.min.js" type="text/javascript"></script>
<script src="${IncPath}/assets/global/plugins/select2/js/select2.full.min.js" type="text/javascript"></script>
<script src="${IncPath}/assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="${IncPath}/assets/global/plugins/jquery-validation/js/additional-methods.min.js" type="text/javascript"></script>
<script src="${IncPath}/assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" type="text/javascript"></script>
<script src="${IncPath}/assets/global/plugins/bootstrap-wysihtml5/wysihtml5-0.3.0.js" type="text/javascript"></script>
<script src="${IncPath}/assets/global/plugins/bootstrap-wysihtml5/bootstrap-wysihtml5.js" type="text/javascript"></script>
<script src="${IncPath}/assets/global/plugins/ckeditor/ckeditor.js" type="text/javascript"></script>
<script src="${IncPath}/assets/global/plugins/bootstrap-markdown/lib/markdown.js" type="text/javascript"></script>
<script src="${IncPath}/assets/global/plugins/bootstrap-markdown/js/bootstrap-markdown.js" type="text/javascript"></script>
<script src="${IncPath}/scripts/jquery-validation/jquery.form.js" type="text/javascript"></script>
<script src="${IncPath}/assets/global/plugins/jquery-validation/js/localization/messages_zh.js?" type="text/javascript"></script>
<!-- END PAGE LEVEL SCRIPTS -->
<script src="../assets/pages/scripts/form-validation.js" type="text/javascript"></script>
<!-- BEGIN THEME LAYOUT SCRIPTS -->

<script src="${IncPath}/assets/global/plugins/jquery-multi-select/js/jquery.multi-select.js" type="text/javascript"></script>
<script src="${IncPath}/assets/layouts/layout/scripts/layout.min.js" type="text/javascript"></script>
<script src="${IncPath}/assets/layouts/layout/scripts/demo.min.js" type="text/javascript"></script>
<script src="${IncPath}/assets/layouts/global/scripts/quick-sidebar.min.js" type="text/javascript"></script>
<script src="${IncPath}/assets/global/plugins/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js" type="text/javascript"></script>
		
	<script type="text/javascript">
		var IncPath = '${IncPath}';
		var BasePath = '${BasePath}';
		var taskId = '${taskId}';
	</script>
	
	<script src="${IncPath}/cl.js?v=${jsVersion}" type="text/javascript"></script>
	<script src="${IncPath}/scripts/jquery-editTable/editTable.js?v=${jsVersion}" type="text/javascript"></script>
	<script src="${IncPath}/core/unshipInfo/unshipInfo.js?v=${jsVersion}" type="text/javascript"></script>
	<!-- BEGIN THEME LAYOUT SCRIPTS -->
	<!-- END THEME LAYOUT SCRIPTS -->
	<script>
		jQuery(document).ready(function() {
			App.init();
			Cl.initModal();
			UnshipInfo.list();
			
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
			
            $('#btn_submit_wg').modal({backdrop: 'static', show:false,  keyboard: false});

            $('#btn_submit_wg').off().on("click", function () {
            	
	        	if($("#endTime").val()==""){
    				alert("请选择完工时间！");
    				return;        		
            	}
            	var data={
            			status:$("#status").val(),
            			taskId:$("#taskId").val(),
            			time:$("#endTime").val(),
//            			beginTime:$("#beginTime").val(),
//            			time:$("#berthingTime").val(),
            	}
                $.ajax({
                    url: BasePath + "/task/doSetShipStatus",
                    type: "post",
                    dataType: "json",
                    cache: false,
                    async:false,
                    data: data,
                    success: function (data) {
                    	if (data.code == Cl.successInt){
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
            $('#btn_submit_qc').modal({backdrop: 'static', show:false,  keyboard: false});

            $('#btn_submit_qc').off().on("click", function () {
            	
	        	if($("#clearTime").val()==""){
    				alert("请选择清舱时间！");
    				return;        		
            	}
            	var data={
            			status:'1',
            			taskId:taskId,
            			cabinNo:$("#cabinNo").val(),
            			clearTime:$("#clearTime").val()
            	}
                $.ajax({
                    url: BasePath + "/cabin/updateCabinStatus",
                    type: "post",
                    dataType: "json",
                    cache: false,
                    async:false,
                    data: data,
                    success: function (data) {
                    	if (data.code == Cl.successInt){
                    		alert(data.msg);                		
    						UnshipInfo.list();
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