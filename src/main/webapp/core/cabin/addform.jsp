<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal"
		aria-hidden="true"></button>
	<h4 class="modal-title">新增船舱</h4>
</div>
<!-- BEGIN FORM-->
<form action="#" id="form_cl_cabin" name="form_cl_add"
	class="form-horizontal">
	<div class="modal-body">
		<div class="row">
			<div class="col-md-12">
				<div class="form-body">
					<div class="alert alert-danger display-hide">
						<button class="close" data-close="alert"></button>
						数据格式异常. 请重新输入.
					</div>
					<div class="alert alert-success display-hide">
						<button class="close" data-close="alert"></button>
						数据校验通过!
					</div>
					<div class="form-group  margin-top-20">
						<label class="control-label col-md-3">船舱号 <span
							class="required">  </span>
						</label>
						<div class="col-md-8">
							<div class="input-icon right">
								<i class="fa"></i> <input type="hidden" id="id" name="id" /> <input
									type="text" id="cabinNo" name="cabinNo" data-required="1"
									class="form-control" readonly value="${cabinNo}"/>
							</div>
						</div>
					</div>
					<div class="form-group  margin-top-20">
						<label class="control-label col-md-3">货物名称 <span
							class="required"> * </span>
						</label>
						<!-- 
						<div class="col-md-8">
							<div class="input-icon right">
								<i class="fa"></i><input type="text" id="cargoId"
									name="cargoId" data-required="1" class="form-control" />
							</div>
						</div>
						 -->
						<div class="col-md-8">
							<select id="cargoId" name="cargoId" class="form-control select2me">
								<option value="">请选择...</option>
					   			<c:if test="${!(cargos == null || fn:length(cargos) == 0)}">
					   				<c:forEach var="cargo" items="${cargos}">
					   					<option value="${cargo.id}">${cargo.cargoType}</option>
					   				</c:forEach>
					   			</c:if>
							</select>
						</div>
					</div>
					<div class="form-group  margin-top-20">
						<label class="control-label col-md-3">装载量（T） <span
							class="required"> * </span>
						</label>
						<div class="col-md-8">
							<div class="input-icon right">
								<i class="fa"></i> <input type="text" id="preunloading"
									name="preunloading" data-required="1" class="form-control" />
							</div>
						</div>
					</div>
					<div class="form-group  margin-top-20">
						<label class="control-label col-md-3">开始位置 <span
							class="required"></span>
						</label>
						<div class="col-md-8">
							<div class="input-icon right">
								<i class="fa"></i> <input type="text" id="startPosition"
									name="startPosition" data-required="1" class="form-control" value="0" />
							</div>
						</div>
					</div>
					<div class="form-group  margin-top-20">
						<label class="control-label col-md-3">结束位置 <span
							class="required"></span>
						</label>
						<div class="col-md-8">
							<div class="input-icon right">
								<i class="fa"></i> <input type="text" id="endPosition"
									name="endPosition" data-required="1" class="form-control" value="0"/>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="modal-footer">
		<button type="button" class="btn default" data-dismiss="modal">关闭</button>
		<button type="submit" class="btn blue" onclick="javascript:;">保存</button>
	</div>
</form>
<script>
	jQuery(document).ready(function() {
		CabinFormCl.init();
		FormValidation.init();
	});
</script>
<!-- END FORM-->