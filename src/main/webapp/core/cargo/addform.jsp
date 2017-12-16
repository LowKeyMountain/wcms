<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal"
		aria-hidden="true"></button>
	<h4 class="modal-title">��������</h4>
</div>
<!-- BEGIN FORM-->
<form action="#" id="form_cl_cargo" name="form_cl_add" class="form-horizontal">
<div class="modal-body">
	<div class="row">
		<div class="col-md-12">
				<div class="form-body">
					<div class="alert alert-danger display-hide">
						<button class="close" data-close="alert"></button>
						���ݸ�ʽ�쳣. ����������.
					</div>
					<div class="alert alert-success display-hide">
						<button class="close" data-close="alert"></button>
						����У��ͨ��!
					</div>
					<div class="form-group  margin-top-20">
						<label class="control-label col-md-3">�������� <span
							class="required"> * </span>
						</label>
						<div class="col-md-8">
	                      	<div class="input-icon right">
                            <i class="fa"></i>
							<input type="hidden" id="id" name="id" /> <input type="text"
								id="cargoCategory" name="cargoCategory" data-required="1"
								class="form-control" />
							</div>
						</div>
					</div>
					<div class="form-group  margin-top-20">
						<label class="control-label col-md-3">�������� <span
							class="required"> * </span>
						</label>
						<div class="col-md-8">
						    <div class="input-icon right">
                            <i class="fa"></i>
							<input type="text" id="cargoType" name="cargoType"
								data-required="1" class="form-control" />
							</div>
						</div>
					</div>
					<div class="form-group  margin-top-20">
						<label class="control-label col-md-3">װ���� <span
							class="required">  </span>
						</label>
						<div class="col-md-8">
	                      	<div class="input-icon right">
                            <i class="fa"></i>
							<input type="text" id="loadingPort" name="loadingPort"
								data-required="1" class="form-control" />
							</div>
						</div>
					</div>
					<div class="form-group  margin-top-20">
						<label class="control-label col-md-3">ˮ�� <span
							class="required">  </span>
						</label>
						<div class="col-md-8">
                            <div class="input-icon right">
                            <i class="fa"></i>
							<input type="text" id="moisture" name="moisture"
								data-required="1" class="form-control" />
							</div>
						</div>
					</div>
					<div class="form-group  margin-top-20">
						<label class="control-label col-md-3">Ʒ�� <span
							class="required">  </span>
						</label>
						<div class="col-md-8">
                            <div class="input-icon right">
                            <i class="fa"></i>
							<input type="text" id="quality" name="quality"
								data-required="1" class="form-control" />
							</div>
						</div>
					</div>
					
					<div class="form-group  margin-top-20">
						<label class="control-label col-md-3">���ض�λ(T)<span
							class="required"> * </span>
						</label>
						<div class="col-md-8">
                            <div class="input-icon right">
                            <i class="fa"></i>
							<input type="text" id="stowage" name="stowage"
								data-required="1" class="form-control" />
							</div>
						</div>
					</div>
					
					<div class="form-group  margin-top-20">
						<label class="control-label col-md-3">���� <span
							class="required">  </span>
						</label>
						<div class="col-md-8">
                            <div class="input-icon right">
                            <i class="fa"></i>
							<input type="text" id="cargoOwner" name="cargoOwner"
								data-required="1" class="form-control" />
							</div>
						</div>
					</div>
					
					
				</div>
		</div>
	</div>
</div>

<div class="modal-footer">
	<button type="button" class="btn default" data-dismiss="modal">�ر�</button>
	<button type="submit" class="btn blue" onclick="javascript:;">����</button>
</div>
</form>
<script>
	jQuery(document).ready(function() {
		CargoFormCl.init();
		FormValidation.init();
	});
</script>
<!-- END FORM-->