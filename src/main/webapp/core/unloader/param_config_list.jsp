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
        <title>京唐港  | 工作中心 - 卸船机参数面板</title>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta content="width=device-width, initial-scale=1" name="viewport" />
        <meta content="" name="description" />
        <meta content="" name="author" />
        <!-- BEGIN GLOBAL MANDATORY STYLES -->
        <style type="text/css">
        .dataTable{
			text-align:center
	        }
		</style>
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
        
        <link rel="shortcut icon" href="favicon.ico" /> </head>
    <!-- END HEAD -->

<body
	class="page-header-fixed page-sidebar-closed-hide-logo page-content-white wysihtml5-supported">

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
						<!-- <h3 class="page-title">卸船机参数面板</h3> -->
						<ul class="page-breadcrumb breadcrumb">
							<li><i class="fa fa-home"></i> <a
								href="${BasePath}/web/main"> 主页 </a> <i
								class="fa fa-angle-right"></i></li>
							<li>工作中心 <i class="fa fa-angle-right"></i>
							</li>
							<li>卸船机参数面板</li>
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
										class="caption-subject bold uppercase"> 参数列表</span>
								</div>
							</div>
							<div class="portlet-body" id = "table">
										<!-- 
								<div class="table-toolbar">
									<div class="row">
										<div class="col-md-6">
											<div class="btn-group">
												<button id="sample_editable_1_new" class="btn sbold green"
													onclick="javascript:User.add_click();">
													新增 <i class="fa fa-plus"></i>
												</button>
											</div>
										</div>
										<div class="col-md-6">
											<div class="btn-group pull-right">
												<button class="btn green  btn-outline dropdown-toggle"
													data-toggle="dropdown">
													Tools <i class="fa fa-angle-down"></i>
												</button>
												<ul class="dropdown-menu pull-right">
													<li><a href="javascript:;"> <i class="fa fa-print"></i>
															Print
													</a></li>
													<li><a href="javascript:;"> <i
															class="fa fa-file-pdf-o"></i> Save as PDF
													</a></li>
													<li><a href="javascript:;"> <i
															class="fa fa-file-excel-o"></i> Export to Excel
													</a></li>
												</ul>
											</div>
										</div>
									</div>
								</div>
										 -->
								<!-- table table-striped table-bordered table-hover table-checkable order-column -->
								<table
									class="dataTable table table-striped table-bordered table-hover table-checkable order-column"
									id="datatable_cl">
									<thead>
										<tr role="row" class="heading">
											<th style="text-align:center" width="7.5%">卸船机编号</th>
											<th style="text-align:center" width="7.5%">给料速度(%)</th>
											<th style="text-align:center" width="7.5%">料斗门开度(%)</th>
											<th style="text-align:center" width="7.5%">料斗载荷(吨)</th>
											<th style="text-align:center" width="7.5%">最后更新时间</th>
											<th style="text-align:center" width="7.5%">卸船机状态</th>
										</tr>
									</thead>
									<tbody>
									</tbody>
								</table>
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
	<jsp:include page="../../footer.jsp" />
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
		<script type="text/javascript">
			var IncPath = '${IncPath}';
			var BasePath = '${BasePath}';
		</script>
		<script src="${IncPath}/cl.js?v=${jsVersion}"
			type="text/javascript"></script>
		<script src="${IncPath}/core/unloader/param_config_list.js?v=${jsVersion}"
			type="text/javascript"></script>
		<!-- BEGIN THEME LAYOUT SCRIPTS -->
        <!-- END THEME LAYOUT SCRIPTS -->
	<script>
		jQuery(document).ready(function() {
			// initiate layout and plugins
			App.init();
			Cl.initModal();
			DataTableCl.init();
			setInterval("refreshTable()", 10000);
		});
	</script>
</body>

</html>