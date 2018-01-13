<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
							<li><a href="${BasePath}/task/unshipInfo?id=56">卸船情况</a><i
								class="fa fa-angle-right"></i></li>
							<li>修改舱位</li>
						</ul>
						<!-- END PAGE TITLE & BREADCRUMB-->
					</div>
				</div>
				<!-- END PAGE HEADER-->
				<div class="row">
					<div class="col-md-12">
						<!-- BEGIN EXAMPLE TABLE PORTLET-->
						<div class="tab-pane" id="cabin">
							<div class="portlet box yellow">
								<div class="portlet-title">
									<div class="caption">
										<i><a href="#">货船A</a></i>
									</div>
									<div class="tools">
										<a onclick="javascript:history.back(-1);" class="fa fa-reply"
											data-original-title="返回" title="返回"> </a> <a
											onclick="javascript:;" class="reload" data-original-title="刷新"
											title="刷新"> </a>
									</div>
								</div>
								<div class="portlet-body" style="display: block;">
									<div class="table-responsive">
										<table
											class="table table-striped table-bordered table-hover editable">
											<thead>
												<tr>
													<th>船舱编号</th>
													<th>开始位置</th>
													<th>结束位置</th>
													<th>操作</th>
												</tr>
											</thead>
											<tbody id="cabin_tbody">

												<tr>
													<th scope="row">1</th>
													<td>0</td>
													<td>20</td>
													<td>
														<!-- <a href="javascript:void(0)" class="edit"></a> -->
													</td>
												</tr>
												<tr>
													<th scope="row">2</th>
													<td>21</td>
													<td>40</td>
													<td>
														<!-- <a href="javascript:void(0)" class="edit"></a> -->
													</td>
												</tr>
												<tr>
													<th scope="row">3</th>
													<td>41</td>
													<td>60</td>
													<td>
														<!-- <a href="javascript:void(0)" class="edit"></a> -->
													</td>
												</tr>

											</tbody>
										</table>
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
		var taskId;
	</script>
	
	<script src="${IncPath}/cl.js?v=${jsVersion}" type="text/javascript"></script>
	<script src="${IncPath}/scripts/jquery-editTable/editTable.js?v=${jsVersion}" type="text/javascript"></script>
	<!-- BEGIN THEME LAYOUT SCRIPTS -->
	<!-- END THEME LAYOUT SCRIPTS -->
    <script>
        $(function() {
            //$('.edit').handleTable({"cancel" : "<span class='glyphicon glyphicon-remove'></span>"});
            $('.editable').handleTable({
                "handleFirst" : true,
                "cancel" : "&nbsp;<span class='glyphicon glyphicon-remove'></span>&nbsp;",
                "edit" : "&nbsp;<span class='glyphicon glyphicon-edit'></span>&nbsp;",
                "add" : "&nbsp;<span class='glyphicon glyphicon-plus'></span>&nbsp;",
                "save" : "&nbsp;<span class='glyphicon glyphicon-saved'></span>&nbsp;",
                "confirm" : "&nbsp;<span class='glyphicon glyphicon-ok'></span>&nbsp;",
                "operatePos" : -1,
                "editableCols" : [1,2],
//                "order": ["add","edit"],
				"order": ["edit"],
                "saveCallback" : function(data, isSuccess) { //这里可以写ajax内容，用于保存编辑后的内容
                    //data: 返回的数据
                    //isSucess: 方法，用于保存数据成功后，将可编辑状态变为不可编辑状态
                    var flag = true; //ajax请求成功（保存数据成功），才回调isSuccess函数（修改保存状态为编辑状态）
                    if(flag) {
                        isSuccess();
                        alert(data + " 保存成功");
                    } else {
                        alert(data + " 保存失败");
                    }

                    return true;
                },
                "addCallback" : function(data,isSuccess) {
                    var flag = true;
                    if(flag) {
                        isSuccess();
                        alert(data + " 增加成功");
                    } else {
                        alert(data + " 增加失败");
                    }
                },
                "delCallback" : function(isSuccess) {
                    var flag = true;
                    if(flag) {
                        isSuccess();
                        alert("删除成功");
                    } else {
                        alert("删除失败");
                    }
                }
            });
        });
    </script>
</body>

</html>