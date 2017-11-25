<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
	<h4 class="modal-title">�޸���Դ</h4>
</div>

<form action="#" id="form_cl" name="form_cl_update" class="form-horizontal">
<div class="modal-body">
	<div class="row">
		<div class="col-md-12">
			<!-- BEGIN FORM-->
				<div class="form-body">
					<div class="form-group">
						<label class="control-label col-md-3">����
						<span class="required">
							 *
						</span>
						</label>
						<div class="col-md-8">
							<input type="hidden" id="id" name="id" value="${resource.id}"/>
							<input type="text" id="name" name="name" data-required="1" class="form-control" value="${resource.name}"/>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-md-3">��ע
						</label>
						<div class="col-md-8">
							<input type="text" id="remark" name="remark" data-required="1" class="form-control" value="${resource.remark}"/>
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
<!-- END FORM-->

<script>
    jQuery(document).ready(function() {       
       FormCl.init();
    });
</script>