<<<<<<< HEAD
<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal"
		aria-hidden="true"></button>
	<h4 class="modal-title">�޸Ľ�ɫ</h4>
</div>

<!-- BEGIN FORM-->
<form action="#" id="form_cl" name="form_cl_update" class="form-horizontal">
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
					<div class="form-group">
						<label class="control-label col-md-3">���� <span
							class="required"> * </span>
						</label>
						<div class="col-md-8">
							<input type="hidden" id="id" name="id" value="${role.id}" /> <input
								type="text" id="roleName" name="roleName" data-required="1"
								class="form-control" value="${role.roleName}" />
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-md-3">��ע</label>
						<div class="col-md-8">
                            <div class="input-icon right">
	                            <i class="fa"></i>
								<input type="text" id="remark" name="remark"
									data-required="1" class="form-control" value="${role.remark}" />
							</div>
						</div>
					</div>
				</div>
		</div>
	</div>
</div>

<div class="modal-footer">
	<button type="button" class="btn default" data-dismiss="modal">�ر�</button>
	<button type="submit" class="btn blue"
		onclick="javascript:;">����</button>
</div>
</form>
<!-- END FORM-->

<script>
	jQuery(document).ready(function() {
		FormCl.init();
	});
=======
<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal"
		aria-hidden="true"></button>
	<h4 class="modal-title">�޸Ľ�ɫ</h4>
</div>

<!-- BEGIN FORM-->
<form action="#" id="form_cl" name="form_cl_update" class="form-horizontal">
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
					<div class="form-group">
						<label class="control-label col-md-3">���� <span
							class="required"> * </span>
						</label>
						<div class="col-md-8">
							<input type="hidden" id="id" name="id" value="${role.id}" /> <input
								type="text" id="roleName" name="roleName" data-required="1"
								class="form-control" value="${role.roleName}" />
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-md-3">��ע</label>
						<div class="col-md-8">
                            <div class="input-icon right">
	                            <i class="fa"></i>
								<input type="text" id="remark" name="remark"
									data-required="1" class="form-control" value="${role.remark}" />
							</div>
						</div>
					</div>
				</div>
		</div>
	</div>
</div>

<div class="modal-footer">
	<button type="button" class="btn default" data-dismiss="modal">�ر�</button>
	<button type="submit" class="btn blue"
		onclick="javascript:;">����</button>
</div>
</form>
<!-- END FORM-->

<script>
	jQuery(document).ready(function() {
		FormCl.init();
	});
>>>>>>> branch 'wcms_dev' of https://github.com/LowKeyMountain/wcms.git
</script>