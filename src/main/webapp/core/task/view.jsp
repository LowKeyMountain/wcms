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
						<h3 class="page-title">查看船舶信息</h3>
						<ul class="page-breadcrumb breadcrumb">
							<li><i class="fa fa-home"></i> <a
								href="${BasePath}/web/main"> 主页 </a> <i
								class="fa fa-angle-right"></i></li>
							<li>管理中心 <i class="fa fa-angle-right"></i>
							</li>
							<li><a href="${BasePath}/task/tasklist"> 工作管理 </a><i
								class="fa fa-angle-right"></i></li>
							<li><a href="${BasePath}/task/unshipInfo?id=${task.id}">卸船情况</a><i
								class="fa fa-angle-right"></i></li>
							<li>查看船舶信息</li>
						</ul>
						<!-- END PAGE TITLE & BREADCRUMB-->
					</div>
				</div>
				<!-- END PAGE HEADER-->
				<div class="row">
					<div class="col-md-12">
						<!-- BEGIN EXAMPLE TABLE PORTLET-->
						<div class="portlet-body">
							<div class="table-toolbar">
								<div class="col-md-12 col-sm-6">
									<div class="panel panel-default">
										<div class="panel-body">
												<div class="tab-content">
												<div class="tab-pane fade active in" id="ship">
													<!-- BEGIN FORM-->
													<form action="#" id="form_cl" name="form_cl_update"
														class="form-horizontal">
														<div class="modal-body">
															<div class="row">
																<div class="col-md-12">
																	<div class="form-body">

																		<div class="form-group  margin-top-20">
																			<label class="control-label col-md-3">船名 <span
																				class="required">  </span>
																			</label>
																			<div class="col-md-8">
																				<div class="input-icon right">
																					<i class="fa"></i><input
																						type="text" id="shipName"
																						name="shipName" value="${task.shipName}"
																						data-required="1" class="form-control"
																						readonly="readonly" />
																				</div>
																			</div>
																		</div>
																		<div class="form-group  margin-top-20">
																			<label class="control-label col-md-3">船英文名 <span
																				class="required"> </span>
																			</label>
																			<div class="col-md-8">
																				<div class="input-icon right">
																					<i class="fa"></i> <input type="text"
																						id="shipEname" name="shipEname"
																						data-required="1" value="${task.shipEname}"
																						class="form-control" readonly="readonly"/>
																				</div>
																			</div>
																		</div>
																		
																		<div class="form-group  margin-top-20">
																			<label class="control-label col-md-3">预靠时间 <span
																				class="required"> </span>
																			</label>
																			<div class="col-md-8">
																				<div class="input-icon right">
																					<i class="fa"></i> <input type="text"
																						id="enterPortTime" name="enterPortTime"
																						data-required="1"
																						class="form_datetime form-control" readonly
																						value="${task.enterPortTime}" readonly="readonly"/>
																				</div>
																			</div>
																		</div>
																		
																		<div class="form-group  margin-top-20">
																			<label class="control-label col-md-3">靠泊时间 <span
																				class="required"> </span>
																			</label>
																			<div class="col-md-8">
																				<div class="input-icon right">
																					<i class="fa"></i> <input type="text"
																						id="berthingTime" name="berthingTime"
																						data-required="1"
																						class="form_datetime form-control" readonly
																						value="${task.berthingTime}" readonly="readonly"/>
																				</div>
																			</div>
																		</div>

																		<div class="form-group  margin-top-20">
																			<label class="control-label col-md-3">离泊时间 <span
																				class="required"> </span>
																			</label>
																			<div class="col-md-8">
																				<div class="input-icon right">
																					<i class="fa"></i> <input size="16" type="text"
																						id="departureTime"
																						value="${task.departureTime}"
																						name="departureTime" readonly
																						class="form_datetime form-control" readonly="readonly" >
																				</div>
																			</div>
																		</div>

																		<div class="form-group  margin-top-20">
																			<label class="control-label col-md-3">泊位 <span
																				class="required"> </span>
																			</label>
																			<div class="col-md-8">
																				<div class="input-icon right">
																					<i class="fa"></i> <input size="16" type="text"
																						id="departureTime"
																						<c:choose>
																						<c:when test="${reportType == 1}">
																						</c:when>
																						</c:choose>
																						value="${task.berth}"
																						name="departureTime" readonly
																						class="form_datetime form-control" readonly="readonly" >
																				</div>
																			</div>
																		</div>
																																				
																		<div class="form-group  margin-top-20">
																			<label class="control-label col-md-3">开工时间 <span
																				class="required"> </span>
																			</label>
																			<div class="col-md-8">
																				<div class="input-icon right">
																					<i class="fa"></i> <input type="text"
																						id="beginTime" name="beginTime"
																						data-required="1"
																						class="form_datetime form-control" readonly
																						value="${task.beginTime}" />
																				</div>
																			</div>
																		</div>

																		<div class="form-group  margin-top-20">
																			<label class="control-label col-md-3">完工时间 <span
																				class="required"> </span>
																			</label>
																			<div class="col-md-8">
																				<div class="input-icon right">
																					<i class="fa"></i> <input size="16" type="text"
																						id="endTime"
																						value="${task.endTime}"
																						name="endTime" readonly
																						class="form_datetime form-control">
																				</div>
																			</div>
																		</div>
																		
																		<div class="form-group  margin-top-20">
																			<label class="control-label col-md-3">货物总重（T）<span
																				class="required"> </span>
																			</label>
																			<div class="col-md-8">
																				<div class="input-icon right">
																					<i class="fa"></i> <input type="text"
																						id="load" name="load" data-required="1"
																						class="form-control" value="${task.load}" readonly/>
																				</div>
																			</div>
																		</div>

																		<div class="form-group  margin-top-20">
																			<label class="control-label col-md-3">IMO 编号<span
																				class="required"> </span>
																			</label>
																			<div class="col-md-8">
																				<div class="input-icon right">
																					<i class="fa"></i> <input type="text"
																						id="imoNo" name="imoNo"
																						data-required="1" class="form-control"
																						value="${task.imoNo}" readonly/>
																				</div>
																			</div>
																		</div>

																		<div class="form-group  margin-top-20">
																			<label class="control-label col-md-3">建造时间 <span
																				class="required"> </span>
																			</label>
																			<div class="col-md-8">
																				<div class="input-icon right">
																					<i class="fa"></i> <input type="text"
																						id="buildDate" name="buildDate"
																						data-required="1" readonly
																						class="yyyy_mm_dd form-control"
																						value="${task.buildDate}" readonly/>
																				</div>
																			</div>
																		</div>

																		<div class="form-group  margin-top-20">
																			<label class="control-label col-md-3">船长（m）<span
																				class="required">  </span>
																			</label>
																			<div class="col-md-8">
																				<div class="input-icon right">
																					<i class="fa"></i> <input type="text"
																						id="length" name="length"
																						data-required="1" class="form-control"
																						value="${task.length}" readonly/>
																				</div>
																			</div>
																		</div>

																		<div class="form-group  margin-top-20">
																			<label class="control-label col-md-3">船宽 （m）<span
																				class="required">  </span>
																			</label>
																			<div class="col-md-8">
																				<div class="input-icon right">
																					<i class="fa"></i> <input type="text"
																						id="breadth" name="breadth"
																						data-required="1" class="form-control"
																						value="${task.breadth}" readonly/>
																				</div>
																			</div>
																		</div>

																		<div class="form-group  margin-top-20">
																			<label class="control-label col-md-3">型深
																				（单位：米） <span class="required">  </span>
																			</label>
																			<div class="col-md-8">
																				<div class="input-icon right">
																					<i class="fa"></i> <input type="text"
																						id="depth" name="depth"
																						data-required="1" class="form-control"
																						value="${task.depth}" readonly/>
																				</div>
																			</div>
																		</div>

																		<div class="form-group  margin-top-20">
																			<label class="control-label col-md-3">舱口数（个）<span
																				class="required">  </span>
																			</label>
																			<div class="col-md-8">
																				<div class="input-icon right">
																					<i class="fa"></i> <input type="text"
																						id="cabinNum" name="cabinNum"
																						data-required="1" class="form-control"
																						value="${task.cabinNum}" readonly/>
																				</div>
																			</div>
																		</div>

																		<div class="form-group  margin-top-20">
																			<label class="control-label col-md-3">船舱数（个）  <span
																				class="required"> </span>
																			</label>
																			<div class="col-md-8">
																				<div class="input-icon right">
																					<i class="fa"></i> <input type="text"
																						id="hatch" name="hatch"
																						data-required="1" class="form-control"
																						value="${task.hatch}" readonly/>
																				</div>
																			</div>
																		</div>

																		<div class="form-group  margin-top-20">
																			<label class="control-label col-md-3">缆绳 <span
																				class="required"> </span>
																			</label>
																			<div class="col-md-8">
																				<div class="input-icon right">
																					<i class="fa"></i> <input type="text"
																						id="cable" name="cable" data-required="1"
																						class="form-control" value="${task.cable}" readonly/>
																				</div>
																			</div>
																		</div>

																		<div class="form-group  margin-top-20">
																			<label class="control-label col-md-3">吃水<span
																				class="required"> </span>
																			</label>
																			<div class="col-md-8">
																				<div class="input-icon right">
																					<i class="fa"></i> <input type="text" id="draft"
																						name="draft" data-required="1"
																						class="form-control" value="${task.draft}" readonly/>
																				</div>
																			</div>
																		</div>

																		<div class="form-group  margin-top-20">
																			<label class="control-label col-md-3">干舷高度<span
																				class="required"> </span>
																			</label>
																			<div class="col-md-8">
																				<div class="input-icon right">
																					<i class="fa"></i> <input type="text"
																						id="freeboardHeight" name="freeboardHeight"
																						data-required="1" class="form-control"
																						value="${task.freeboardHeight}" readonly/>
																				</div>
																			</div>
																		</div>

																		<div class="form-group  margin-top-20">
																			<label class="control-label col-md-3">特殊舱型 <span
																				class="required"> </span>
																			</label>
																			<div class="col-md-8">
																				<div class="input-icon right">
																					<i class="fa"></i> <input type="text"
																						id="cabinType"
																						name="cabinType" data-required="1"
																						class="form-control"
																						value="${task.cabinType}" readonly/>
																				</div>
																			</div>
																		</div>

																	</div>
																</div>
															</div>
														</div>
														<div class="modal-footer">
															<button type="button" class="btn default"
																data-dismiss="modal"
																onclick="javascript:history.back(-1);">返回</button>
														</div>
													</form>
													<!-- END FORM-->
												</div>
											</div>
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
		
	<script type="text/javascript">
		var IncPath = '${IncPath}';
		var BasePath = '${BasePath}';
		var taskId = ${task.id};
	</script>
	<script src="${IncPath}/cl.js?v=${jsVersion}" type="text/javascript"></script>
	<script src="${IncPath}/core/task/task.js?v=${jsVersion}" type="text/javascript"></script>
	<script src="${IncPath}/core/cargo/cargo.js?v=${jsVersion}" type="text/javascript"></script>
	<script src="${IncPath}/scripts/jquery-editTable/editTable.js?v=${jsVersion}" type="text/javascript"></script>
	<script src="${IncPath}/core/cabin/cabin.js?v=${jsVersion}" type="text/javascript"></script>
	<!-- BEGIN THEME LAYOUT SCRIPTS -->
	<!-- END THEME LAYOUT SCRIPTS -->
	<script>
		jQuery(document).ready(function() {
			App.init();
			Cl.initModal();
		});
	</script>
</body>

</html>