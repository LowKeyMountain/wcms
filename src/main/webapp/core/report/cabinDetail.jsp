<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt"%>  

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
						</c:choose>						<ul class="page-breadcrumb breadcrumb">
							<li><i class="fa fa-home"></i> <a
								href="${BasePath}/web/main"> 主页 </a> <i
								class="fa fa-angle-right"></i></li>
							<li>管理中心 <i class="fa fa-angle-right"></i>
							</li>
							<li><a href="${IncPath}/report/reportview">报表统计 </a><i class="fa fa-angle-right"></i></li>
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

				<div class="row">
					<div class="col-md-12">

						<div class="portlet box yellow">
							<div class="portlet-title">
								<div class="tools">
									<a onclick="javascript:history.back(-1);" class="fa fa-reply"
										data-original-title="返回" title="返回"> </a> <a
										onclick="javascript:CabinView.list();" class="reload" data-original-title="刷新"
										title="刷新"> </a>
								</div>
							</div>
							<div class="portlet-body" style="display: block;">
								<div class="table-responsive">

									<table class="table table-striped">
										<tr>
											<td colspan="3"><b>${cabin.cabinNo}#舱</b></td>
											<td>货名</td>
											<td>${cabin.cargoName}</td>
											<td>总量</td>
											<td>${cabin.total}</td>
										</tr>
										<tr>
											<td colspan="3"></td>
											<td>状态</td>
											<td>
											<!-- 状态 （卸货|0、清舱|1、完成|2） -->
											<c:choose>
												<c:when test="${cabin.status == 0}">卸货</c:when>
												<c:when test="${cabin.status == 1}">清舱</c:when>
												<c:when test="${cabin.status == 2}">完成</c:when>
											</c:choose>
											</td>
											<td>清舱前</td>
											<td>${cabin.finishedBeforeClearance}</td>
										</tr>
										<tr>
											<td colspan="3"></td>
											<td>清舱量</td>
											<td>${cabin.clearance}</td>
											<td>已完成</td>
											<td>${cabin.finished}</td>
										</tr>
										<tr>
											<td colspan="3"></td>
											<td>剩余量</td>
											<td>${cabin.remainder}</td>
											<td></td>
											<td></td>
										</tr>
									</table>
									<div id="toolbar" align="left" class="btn-group">
									    <!-- <button type="button" class="btn btn-default">
									        <i class="glyphicon glyphicon-plus"></i>
									    </button>-->
										<button type="button" id="download" style="margin-left:5px" class="btn btn-primary" onClick ="$('#cabin-detail').tableExport({ type: 'excel', escape: 'false' })">数据导出</button> 
									</div>
									<table id="cabin-detail" class="table table-striped table-bordered table-hover editable" >
										<thead>
											<tr>
												<th>卸船机</th>
												<th>开始时间</th>
												<th>结束时间</th>
												<th>用时|h</th>
												<th>卸货量|t</th>
												<th>效率</th>
											</tr>
										</thead>
										<tbody id="cabin_tbody">
										</tbody>
									</table>

								</div>
							</div>
						</div>
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
<script src="${IncPath}/assets/extensions/export/tableExport.js"></script>
<!-- <script src="${IncPath}/assets/extensions/export/table-export.js"></script>
<script src="${IncPath}/assets/extensions/export/main.js"></script>-->
<script src="${IncPath}/assets/extensions/export/jquery.base64.js"></script>
<script src="${IncPath}/assets/extensions/export/bootstrap-table-export.js"></script>

	<script type="text/javascript">
		var IncPath = '${IncPath}';
		var BasePath = '${BasePath}';
		var taskId = '${taskId}';
		var cabinNo = '${cabinNo}';
		var reportType = '${reportType}';
	</script>
	<script src="${IncPath}/cl.js?v=${jsVersion}" type="text/javascript"></script>
	<script src="${IncPath}/core/report/cabinDetail.js?v=${jsVersion}" type="text/javascript"></script>
	<!-- BEGIN THEME LAYOUT SCRIPTS -->
	<!-- END THEME LAYOUT SCRIPTS -->
	<script>
		jQuery(document).ready(function() {
			App.init();
			Cl.initModal();
			CabinView.list();
		});
	</script>
</body>

</html>