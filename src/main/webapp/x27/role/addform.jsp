<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
	<h4 class="modal-title">������ɫ</h4>
</div>

<form action="#" id="form_cl_add" class="form-horizontal">
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
							<input type="hidden" id="id" name="id"/>
							<input type="text" id="roleName" name="roleName" data-required="1" class="form-control"/>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-md-3">��ע
						</label>
						<div class="col-md-8">
							<input type="text" id="remark" name="remark" data-required="1" class="form-control"/>
						</div>
					</div>
				</div>
			<!-- END FORM-->
		</div>
	</div>
</div>

<div class="modal-footer">
	<button type="button" class="btn default" data-dismiss="modal">�ر�</button>
	<button type="button" class="btn blue" onclick="javascript:;">����</button>
</div>
</form>

<script>
    jQuery(document).ready(function() {       
       FormCl.init();
    });
</script>