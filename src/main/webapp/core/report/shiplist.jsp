<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <title>京唐港  | 卸船机 - 报表统计</title>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta content="width=device-width, initial-scale=1" name="viewport" />
        <meta content="" name="description" />
        <meta content="" name="author" />
        <!-- BEGIN GLOBAL MANDATORY STYLES -->
        <link href="http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700&subset=all" rel="stylesheet" type="text/css" />
	    <link href="${IncPath}/assets/global/plugins/bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css" />
	    <link href="${IncPath}/assets/global/plugins/bootstrap-table/css/bootstrap-table.css" rel="stylesheet" type="text/css" />
	    <link href="${IncPath}/assets/examples.css" rel="stylesheet" type="text/css" />
	    
        <link href="${IncPath}/assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
        <link href="${IncPath}/assets/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css" />
        <link href="${IncPath}/assets/global/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css" />
        <link href="${IncPath}/assets/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css" rel="stylesheet" type="text/css" />
        <!-- END GLOBAL MANDATORY STYLES -->
        <!-- BEGIN PAGE LEVEL PLUGINS -->
<!--         <link href="${IncPath}/assets/global/plugins/datatables/datatables.min.css" rel="stylesheet" type="text/css" />
        <link href="${IncPath}/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css" rel="stylesheet" type="text/css" /> -->
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
        <link href="${IncPath}/assets/global/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.css" rel="stylesheet" type="text/css" />
                
        <!-- END THEME LAYOUT STYLES -->
        <link rel="shortcut icon" href="favicon.ico" /> </head>
    <!-- END HEAD -->

<body
	class="page-header-fixed page-sidebar-closed-hide-logo page-content-white">

	<jsp:include page="../../header.jsp" />

	<!-- BEGIN HEADER & CONTENT DIVIDER -->
	<div class="clearfix"></div>
	<!-- END HEADER & CONTENT DIVIDER -->
	<!-- BEGIN CONTAINER -->
	<div class="page-container">
		<jsp:include page="../../sidebar.jsp" />

		<!-- BEGIN CONTENT -->

		<div class="page-content-wrapper">
			<!-- BEGIN CONTENT BODY -->
			<div class="page-content">
				<!-- BEGIN PAGE HEADER-->
				<div class="row">
					<div class="col-md-12">
						<!-- BEGIN PAGE TITLE & BREADCRUMB-->
						<c:choose>
							<c:when test="${reportType == 1}">
								<h3 class="page-title icon-settings">&nbsp;&nbsp;船舶货物进度统计</h3>
							</c:when>
							<c:when test="${reportType == 2}">
								<h3 class="page-title icon-settings">&nbsp;&nbsp;船舶舱口卸货统计</h3>
							</c:when>
							<c:when test="${reportType == 3}">
								<h3 class="page-title icon-settings">&nbsp;&nbsp;船舶卸船机作业量统计</h3>
							</c:when>
							<c:when test="${reportType == 4}">
								<h3 class="page-title icon-settings">&nbsp;&nbsp;船舶班次作业量统计</h3>
							</c:when>
							<c:when test="${reportType == 5}">
								<h3 class="page-title icon-settings">&nbsp;&nbsp;船舶舱口效率统计</h3>
							</c:when>
							<c:when test="${reportType == 6}">
								<h3 class="page-title icon-settings">&nbsp;&nbsp;船舶货物效率统计</h3>
							</c:when>
							<c:otherwise>
								<h3 class="page-title icon-settings">&nbsp;&nbsp;舱外作业量统计</h3>
							</c:otherwise>
						</c:choose>
						<ul class="page-breadcrumb breadcrumb">
							<li><i class="fa fa-home"></i> <a
								href="${BasePath}/web/main"> 主页 </a> <i
								class="fa fa-angle-right"></i></li>
							<li>管理中心 <i class="fa fa-angle-right"></i>
							<li><a href="${IncPath}/report/reportview">报表统计 </a><i class="fa fa-angle-right"></i>
							</li>
							<c:choose>
								<c:when test="${reportType == 1}">
									<li>船舶货物进度统计</li>
								</c:when>
								<c:when test="${reportType == 2}">
									<li>船舶舱口卸货统计</li>
								</c:when>
								<c:when test="${reportType == 3}">
									<li>船舶卸船机作业量统计</li>
								</c:when>
								<c:when test="${reportType == 4}">
									<li>船舶班次作业量统计</li>
								</c:when>
								<c:when test="${reportType == 5}">
									<li>船舶舱口效率统计</li>
								</c:when>
								<c:when test="${reportType == 6}">
									<li>船舶货物效率统计</li>
								</c:when>
								<c:otherwise>
									<li>舱外作业量统计</li>
								</c:otherwise>
							</c:choose>
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
<!-- 						<div class="portlet light bordered">
							<div class="portlet-body"> -->
							
						<div class="portlet box yellow">
								<div class="portlet-title">
									<div class="caption">
										<i>查询条件</i>
									</div>
									<div class="tools">
										<a onclick="javascript:history.back(-1);" class="fa fa-reply"
										data-original-title="返回" title="返回"> </a> <a
										onclick="javascript:initTable(${taskId});" class="reload" data-original-title="刷新"
										title="刷新"> </a>
									</div>
								</div>	
								<div class="portlet-body" style="display: block;">							
								<div class="table-toolbar">
						                <div class="col-md-12">
									        <div class="panel panel-primary">
<!-- 									            <div class="panel-heading">
									            		<h3 class="panel-title">查询条件</h3>
									            </div> -->
									            <div class="panel-body">
									                <form id="formSearch" class="form-horizontal">
							                
									                    <div class="form-group" style="margin-top:5px;margin-bottom:5px">
										                        <!--<label class="control-label col-md-1" for="shipName">船名: </label>
										                        <div class="col-md-3">
																	<select id="shipName" name="shipName" class="form-control select2me">
																		<option value="">请选择...</option>
																	</select>
										                        </div>-->									                    
										                        <label class="control-label col-md-1" for="status">状态: </label>
										                        <div class="col-md-2">
																	<select id="status" name="status" class="form-control select2me">
																		<option value="">请选择...</option>
													   					<option value="1" >作业中</option>
													   					<option value="2" >已离港</option>
																	</select>
										                        </div>
										                        <label class="control-label col-md-1" for="dateRange">查询范围: </label>
										                        <div class="col-md-3">
																	<select id="dateRange" name="dateRange" class="form-control select2me">
																		<option value="0">最近一周</option>
																		<option value="1">最近一个月</option>
																		<option value="2">最近三个月</option>
																		<option value="3">最近半年</option>
																		<option value="4">最近一年</option>
																		<option value="">全部</option>																		
																	</select>
										                        </div>
									                        </div>
<!-- 									                        <div class="form-group" style="margin-top:5px;margin-bottom:5px" id="shift-div">
										                        <label class="control-label col-md-1" for="status">班次: </label>
										                        <div class="col-md-2">
																	<select id="shift" name="shift" class="form-control select2me">
																		<option value="">请选择...</option>
													   					<option value="0" >白班</option>
													   					<option value="1" >夜班</option>
																	</select>
										                        </div>
									                    	</div>
									                        										                        
													    	<label class="control-label  col-md-1 cy-pad-hor-s">靠泊时间：</label>
													        <div class="col-md-4 input-daterange input-group">
													            <input class="form-control" name="startBerthDate" placeholder="请选择靠泊起始时间" id="startBerthDate" readonly/>
													            <span class="input-group-addon">--</span>
																<input class="form-control" name="endBerthDate" placeholder="请选择靠泊截止时间" id="endBerthDate" readonly/>
															</div>
													    <div class="form-group"  style="margin-top:10px;margin-bottom:5px">
															<label class="control-label  col-md-1 cy-pad-hor-s">离泊时间：</label>
													        <div class="col-md-4 input-daterange input-group">
													            <input class="form-control" name="startDepartureDate" placeholder="请选择离泊起始时间" id="startDepartureDate" readonly/>
													            <span class="input-group-addon">--</span>
																<input class="form-control" name="endDepartureDate" placeholder="请选择离泊截止时间" id="endDepartureDate" readonly/>
															</div>
														</div> -->
									                    <div class="form-group" style="margin-top:5px;margin-left:500px;margin-bottom:5px">
									                        <div class="col-md-2" style="text-align:left;">
									                            <button type="button" style="margin-left:20px" id="btn_query" class="btn btn-primary btn-sm">查询</button>
									                        </div>
									                        <div class="col-md-2" style="text-align:left;">
									                            <button type="button" style="margin-left:20px" id="btn_reset" class="btn btn-sm">重置</button>
									                        </div>
									                    </div>
<!-- 												        <div id="toolbar" class="btn-group">
												            <button id="btn_add" type="button" class="btn btn-primary btn-default" data-toggle="modal" data-target="#addModal">
												                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
												            </button>
												        </div> -->
<!-- 														<div class="dropdown" id="toolbar" >
														  <button class="btn btn-success dropdown-toggle" type="button" id="menu1" data-toggle="dropdown">报表统计
														  <span class="caret"></span></button>
														  <ul class="dropdown-menu" role="menu" aria-labelledby="menu1">
														    <li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:view_report(1)">船舶货物进度统计</a></li>
														    <li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:view_report(2)">船舶舱口卸货统计</a></li>
														    <li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:view_report(3)">船舶卸船机作业量统计</a></li>
														    <li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:view_report(4)">船舶班次作业量统计</a></li>
														    <li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:view_report(5)">船舶分舱作业量统计</a></li>
														    <li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:view_report(6)">船舶分货作业量统计</a></li>
														  </ul>
														</div> -->													        
									                    <table id="shiplist" class="table table-striped table-bordered table-hover table-checkable order-column"></table>												            
									                </form>
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
</div>
	<!-- END CONTAINER -->
	<jsp:include page="../../footer.jsp" />
	<!--[if lt IE 9]>
<script src="${IncPath}/assets/global/plugins/respond.min.js"></script>
<script src="${IncPath}/assets/global/plugins/excanvas.min.js"></script> 
<![endif]-->
        <!-- BEGIN CORE PLUGINS -->
	    <script src="${IncPath}/assets/global/plugins/jquery.min.js" type="text/javascript"></script>
	    <script src="${IncPath}/assets/global/plugins/bootstrap-table/js/bootstrap-table.js" type="text/javascript"></script>
	    <script src="${IncPath}/assets/global/plugins/bootstrap/js/bootstrap.js" type="text/javascript"></script>
    	<script src="${IncPath}/assets/ga.js" type="text/javascript"></script>
    	
        <script src="${IncPath}/assets/global/plugins/js.cookie.min.js" type="text/javascript"></script>
        <script src="${IncPath}/assets/global/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js" type="text/javascript"></script>
        <script src="${IncPath}/assets/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
        <script src="${IncPath}/assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
        <script src="${IncPath}/assets/global/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
        <script src="${IncPath}/assets/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js" type="text/javascript"></script>
        <!-- END CORE PLUGINS -->
        <!-- BEGIN PAGE LEVEL PLUGINS -->
<!--         <script src="${IncPath}/assets/global/scripts/datatable.js" type="text/javascript"></script>
        <script src="${IncPath}/assets/global/plugins/datatables/datatables.min.js" type="text/javascript"></script>
        <script src="${IncPath}/assets/global/plugins/datatables/datatables.js?v=201709281620" type="text/javascript"></script>
        <script src="${IncPath}/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" type="text/javascript"></script>     -->
           
        <script src="${IncPath}/assets/global/plugins/bootstrap-table/js/bootstrap-table-zh-CN.js" type="text/javascript"></script>
        <!-- END PAGE LEVEL PLUGINS -->
        <!-- BEGIN THEME GLOBAL SCRIPTS -->
        <script src="${IncPath}/assets/global/scripts/app.min.js" type="text/javascript"></script>
        <script src="${IncPath}/assets/global/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript"></script>
		<script src="${IncPath}/assets/global/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript"></script>
		<script src="${IncPath}/assets/global/plugins/jquery-validation/js/jquery.validate.min.js"></script>
		<script src="${IncPath}/assets/global/plugins/jquery-validation/js/additional-methods.min.js"></script>
        <!-- END THEME GLOBAL SCRIPTS -->
        <!-- BEGIN PAGE LEVEL SCRIPTS -->
        <script src="${IncPath}/assets/pages/scripts/table-datatables-managed.min.js" type="text/javascript"></script>
        <!-- END PAGE LEVEL SCRIPTS -->
        <!-- BEGIN THEME LAYOUT SCRIPTS -->
        <script src="${IncPath}/assets/layouts/layout/scripts/layout.min.js" type="text/javascript"></script>
        <script src="${IncPath}/assets/layouts/layout/scripts/demo.min.js" type="text/javascript"></script>
        <script src="${IncPath}/assets/layouts/global/scripts/quick-sidebar.min.js" type="text/javascript"></script>
		<script src="${IncPath}/assets/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js" type="text/javascript"></script>
		<script src="${IncPath}/assets/global/plugins/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js" type="text/javascript"></script>
		
		<script src="${IncPath}/assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.js" type="text/javascript"></script>
		<script src="${IncPath}/assets/global/plugins/bootstrap-datepicker/js/locales/bootstrap-datepicker.zh-CN.min.js" type="text/javascript"></script>
		
		<script src="${IncPath}/assets/global/plugins/knockout/knockout-3.4.2.js" type="text/javascript"></script>
        
		<script type="text/javascript">
			var IncPath = '${IncPath}';
			var BasePath = '${BasePath}';
			var reportType = '${reportType}';
		</script>
		<script src="${IncPath}/cl.js?v=${jsVersion}"
			type="text/javascript"></script>
		<script src="${IncPath}/core/report/shiplist.js?v=${jsVersion}"
			type="text/javascript"></script>
			
        <script>  
            $(document).ready(function(){
      			$(".form_datetime").datetimepicker({
	            	startView: 'month',
	            	minView: 'month',
    			    language:  'zh-CN',
    			    format: 'yyyy-mm-dd hh:ii:ss',
    			    todayBtn:  true,
    			    autoclose: true,
	            	clearBtn: true,
	            	todayHighlight: true,
	            	showMeridian: true,
	            	endDate: new Date()
    			});

     			function DatePicker(beginSelector,endSelector){
    	            // 仅选择日期
    	            $(beginSelector).datetimepicker(
    	            {
    	            	language:  "zh-CN",
    	            	autoclose: true,
    	            	startView: 'month',
    	            	minView: 'month',
    	            	format: "yyyy-mm-dd hh:ii:ss",
    	            	clearBtn: true,
    	            	//todayBtn: true,
    	            	todayHighlight: true,
    	            	showMeridian: true,
    	            	endDate: new Date()
    	            }).on('changeDate', function(s){
    	            	var startTime = s.date;     	            	
    	            	if(s.date){
    	            		$(endSelector).datetimepicker('setStartDate', startTime)
    	            	}else{
    	            		$(endSelector).datetimepicker('setStartDate',null);
    	            	}
    	            })

    	            $(endSelector).datetimepicker(
    	            {
    	            	language:  "zh-CN",
    	            	autoclose: true,
    	            	startView: 'month',
    	            	minView: 'month',
    	            	format: "yyyy-mm-dd hh:ii:ss",
    	            	clearBtn: true,
    	            	//todayBtn: true,
    	            	todayHighlight: true,
    	            	showMeridian: true,
    	            	endDate: new Date()
    	            }).on('changeDate', function(e){
    	            	var endTime = e.date; 
    	            	if(e.date){
    	            		$(beginSelector).datetimepicker('setEndDate', endTime)
    	            	}else{
    	            		$(beginSelector).datetimepicker('setEndDate', new Date());
    	            	} 

    	            })
    	        }
    	        DatePicker("#startDepartureDate","#endDepartureDate");
    	        DatePicker("#startBerthDate","#endBerthDate");
/*     	        if($("#modiStatus").val()==0){
            		$('#begintime').attr('disabled', 'false');
            		$('#departuretime').attr('disabled', 'false'); 
    	        }else if($("#modiStatus").val()==1){
            		$('#begintime').attr('disabled', 'true');
            		$('#departuretime').attr('disabled', 'false');     	        	
    	        } */
            });  
        </script>			
		<!-- BEGIN THEME LAYOUT SCRIPTS -->
        <!-- END THEME LAYOUT SCRIPTS -->

</body>

</html>