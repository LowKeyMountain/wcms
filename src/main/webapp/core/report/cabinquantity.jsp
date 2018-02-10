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
        <title>京唐港  | 报表统计</title>
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
						<h3 class="page-title">船舶分舱作业量统计</h3>
						<ul class="page-breadcrumb breadcrumb">
							<li><i class="fa fa-home"></i> <a
								href="${BasePath}/web/main"> 主页 </a> <i
								class="fa fa-angle-right"></i></li>
							<li>管理中心 <i class="fa fa-angle-right"></i>
							</li>
							<li><a href="${IncPath}/task/report">报表统计 </a><i class="fa fa-angle-right"></i>
							<li>船舶分舱作业量统计</li>
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
				<div id="popover_content_wrapper" style="display: none">
					<div><span style="color:red">Hello</span>
					</div>
				</div>
            	<div class="row">
					<div class="col-md-12">
						<!-- BEGIN EXAMPLE TABLE PORTLET-->
						<div class="portlet light bordered">
							<div class="portlet-title">
								<div class="caption font-dark">
									<i class="icon-settings font-dark"></i> <span
										class="caption-subject bold uppercase">船舶舱口卸货统计</span>
								</div>

							</div>
							<div class="portlet-body">

								<div class="table-toolbar">
						                <div class="col-md-12 col-sm-2">
									        <div class="panel panel-primary">
									            <div class="panel-heading">
									            		<h3 class="panel-title"><a href="javascript:view_ship(${taskId})">${shipName}</a></h3></div>
									            <div class="panel-body">
									                <form id="formSearch" class="form-horizontal">
							                
									                    <div class="form-group" style="margin-top:5px;margin-bottom:5px">
									                        <label class="control-label col-md-1" for="status">船名: </label>
									                        <div class="col-md-3">
																<select id="status" name="status" class="form-control select2me">
																	<option value="">请选择...</option>
																</select>
									                        </div>							                        

									                        <label class="control-label col-md-1" for="status">查询范围: </label>
									                        <div class="col-md-3">
																<select id="daterange" name="daterange" class="form-control select2me">
																	<option value="0">最近一周</option>
																	<option value="1">最近一个月</option>
																	<option value="1">最近三个月</option>
																	<option value="1">最近半年</option>
																	<option value="1">最近一年</option>
																</select>
									                        </div>									                        
									                    </div>
									                    <div class="form-group" style="margin-top:5px;margin-left:500px;margin-bottom:5px">
									                        <div class="col-md-2" style="text-align:left;">
									                            <button type="button" style="margin-left:20px" id="btn_query" class="btn btn-primary btn-sm">查询</button>
									                        </div>
									                        <div class="col-md-2" style="text-align:left;">
									                            <button type="button" style="margin-left:20px" id="btn_reset" class="btn btn-sm">重置</button>
									                        </div>
									                    </div>
									                	<table id="cabinquantity" class="table table-striped table-bordered table-hover table-checkable order-column"></table>												            
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
		<script src="${IncPath}/assets/global/plugins/knockout/knockout-3.4.2.js" type="text/javascript"></script>
        
		<script type="text/javascript">
			var IncPath = '${IncPath}';
			var BasePath = '${BasePath}';
			var taskId = '${taskId}';
		</script>
		<script src="${IncPath}/cl.js?v=${jsVersion}"
			type="text/javascript"></script>
		<script src="${IncPath}/core/report/cabinquantity.js?v=${jsVersion}"
			type="text/javascript"></script>
	<script>
		jQuery(document).ready(function() {
			// initiate layout and plugins
			
			initTable(taskId);
			
			Cl.initModal();

		
		});
	</script>						
		<!-- BEGIN THEME LAYOUT SCRIPTS -->
        <!-- END THEME LAYOUT SCRIPTS -->

</body>

</html>