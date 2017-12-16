<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal"
		aria-hidden="true"></button>
	<h4 class="modal-title">修改货物</h4>
</div>

<!-- BEGIN FORM-->
<form action="#" id="form_cl_cargo" name="form_cl_update" class="form-horizontal">
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
						<label class="control-label col-md-3">货物种类 <span
							class="required"> * </span>
						</label>
						<div class="col-md-8">
	                      	<div class="input-icon right">
                            <i class="fa"></i>
							<input type="hidden" id="id" name="id" value="${cargo.id}" /> <input type="text"
								id="cargoCategory" name="cargoCategory" value="${cargo.cargoCategory}" data-required="1"
								class="form-control" readonly="readonly" />
							</div>
						</div>
					</div>
					<div class="form-group  margin-top-20">
						<label class="control-label col-md-3">货物类型 <span
							class="required"> * </span>
						</label>
						<div class="col-md-8">
						    <div class="input-icon right">
                            <i class="fa"></i>
							<input type="text" id="cargoType" name="cargoType"
								data-required="1" value="${cargo.cargoType}" class="form-control" />
							</div>
						</div>
					</div>
					<div class="form-group  margin-top-20">
						<label class="control-label col-md-3">装货港 <span
							class="required">  </span>
						</label>
						<div class="col-md-8">
	                      	<div class="input-icon right">
                            <i class="fa"></i>
							<input type="text" id="loadingPort" name="loadingPort"
								data-required="1" class="form-control" value="${cargo.loadingPort}" />
							</div>
						</div>
					</div>
					<div class="form-group  margin-top-20">
						<label class="control-label col-md-3">水分 <span
							class="required">  </span>
						</label>
						<div class="col-md-8">
                            <div class="input-icon right">
                            <i class="fa"></i>
							<input type="text" id="moisture" name="moisture"
								data-required="1" class="form-control" value="${cargo.moisture}" />
							</div>
						</div>
					</div>
					<div class="form-group  margin-top-20">
						<label class="control-label col-md-3">品质 <span
							class="required">  </span>
						</label>
						<div class="col-md-8">
                            <div class="input-icon right">
                            <i class="fa"></i>
							<input type="text" id="quality" name="quality"
								data-required="1" class="form-control" value="${cargo.quality}" />
							</div>
						</div>
					</div>
					
					<div class="form-group  margin-top-20">
						<label class="control-label col-md-3">配载吨位(T) <span
							class="required"> * </span>
						</label>
						<div class="col-md-8">
                            <div class="input-icon right">
                            <i class="fa"></i>
							<input type="text" id="stowage" name="stowage"
								data-required="1" class="form-control" value="${cargo.stowage}" />
							</div>
						</div>
					</div>
					
					<div class="form-group  margin-top-20">
						<label class="control-label col-md-3">货主 <span
							class="required">  </span>
						</label>
						<div class="col-md-8">
                            <div class="input-icon right">
                            <i class="fa"></i>
							<input type="text" id="cargoOwner" name="cargoOwner"
								data-required="1" class="form-control"  value="${cargo.cargoOwner}" />
							</div>
						</div>
					</div>
				</div>
		</div>
	</div>
</div>

<div class="modal-footer">
	<button type="button" class="btn default" data-dismiss="modal">关闭</button>
	<button type="submit" class="btn blue"
		onclick="javascript:;">保存</button>
</div>
</form>
<!-- END FORM-->
<script>
	jQuery(document).ready(function() {
		CargoFormCl.init();
		FormValidation.init();
	});
</script>