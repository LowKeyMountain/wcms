<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal"
		aria-hidden="true"></button>
	<h4 class="modal-title">����������Ϣ</h4>
</div>

<!-- BEGIN FORM-->
<form action="#" id="form_cl_cargo" name="form_cl_update"
	class="form-horizontal">
	<div class="modal-body">
		<div class="row">
			<div class="col-md-12">
				<div class="form-body">
					<div class="form-group  margin-top-20">
						<label class="control-label col-md-3">�������� <span
							class="required"> </span>
						</label>
						<div class="col-md-8">
							<div class="input-icon right">
								<i class="fa"></i> <input type="hidden" id="id" name="id"
									value="${cargo.id}" /> <input type="text" id="cargoCategory"
									name="cargoCategory" value="${cargo.cargoCategory}"
									data-required="1" class="form-control" readonly="readonly" />
							</div>
						</div>
					</div>
					<div class="form-group  margin-top-20">
						<label class="control-label col-md-3">��������<span
							class="required"> </span>
						</label>
						<div class="col-md-8">
							<div class="input-icon right">
								<i class="fa"></i> <input type="text" id="cargoType"
									name="cargoType" data-required="1" value="${cargo.cargoType}"
									class="form-control" readonly="readonly" />
							</div>
						</div>
					</div>
					<div class="form-group  margin-top-20">
						<label class="control-label col-md-3">װ���� <span
							class="required"> </span>
						</label>
						<div class="col-md-8">
							<div class="input-icon right">
								<i class="fa"></i> <input type="text" id="loadingPort"
									name="loadingPort" data-required="1" class="form-control"
									value="${cargo.loadingPort}" readonly="readonly"/>
							</div>
						</div>
					</div>
					<div class="form-group  margin-top-20">
						<label class="control-label col-md-3">ˮ�� <span
							class="required"> </span>
						</label>
						<div class="col-md-8">
							<div class="input-icon right">
								<i class="fa"></i> <input type="text" id="moisture"
									name="moisture" data-required="1" class="form-control"
									value="${cargo.moisture}" readonly="readonly"/>
							</div>
						</div>
					</div>
					<div class="form-group  margin-top-20">
						<label class="control-label col-md-3">Ʒ�� <span
							class="required"> </span>
						</label>
						<div class="col-md-8">
							<div class="input-icon right">
								<i class="fa"></i> <input type="text" id="quality"
									name="quality" data-required="1" class="form-control"
									value="${cargo.quality}" readonly="readonly"/>
							</div>
						</div>
					</div>

					<div class="form-group  margin-top-20">
						<label class="control-label col-md-3">���ض�λ(T) <span
							class="required"> </span>
						</label>
						<div class="col-md-8">
							<div class="input-icon right">
								<i class="fa"></i> <input type="text" id="stowage"
									name="stowage" data-required="1" class="form-control"
									value="${cargo.stowage}" readonly="readonly"/>
							</div>
						</div>
					</div>

					<div class="form-group  margin-top-20">
						<label class="control-label col-md-3">���� <span
							class="required"> </span>
						</label>
						<div class="col-md-8">
							<div class="input-icon right">
								<i class="fa"></i> <input type="text" id="cargoOwner"
									name="cargoOwner" data-required="1" class="form-control"
									value="${cargo.owner}" readonly="readonly"/>
							</div>
						</div>
					</div>
				
					<div class="form-group  margin-top-20">
						<label class="control-label col-md-3">��Ų�λ <span
							class="required"> </span>
						</label>
						<div class="col-md-8">
							<div class="input-icon right">
								<i class="fa"></i> <input type="text" id="stowage"
									name="stowage" data-required="1" class="form-control"
									value="${cargo.warehouse}" readonly="readonly"/>
							</div>
						</div>
					</div>
					
				</div>
			</div>
		</div>
	</div>

	<div class="modal-footer">
		<button type="button" class="btn default" data-dismiss="modal">�ر�</button>
	</div>
</form>
<!-- END FORM-->
<script>
	
</script>