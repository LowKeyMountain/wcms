<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
	<h4 class="modal-title">关联角色</h4>
</div>

<div class="modal-body">
	<div class="row">
		<div class="col-md-12">
			<!-- BEGIN MULTI SELECT-->
				<div class="form-group">
					<div class="col-md-12">
						<input  type="hidden" id="id" name="id" value="${id}" />
						<select multiple="multiple" class="multi-select" id="multi_role" name="multi_role[]">
							${options}
						</select>
					</div>
				</div>
			<!-- END MULTI SELECT-->
		</div>
	</div>
</div>

<div class="modal-footer">
	<input type="hidden" id="id" name="id" value="${id}"/>
	<button type="button" class="btn default" data-dismiss="modal">关闭</button>
	<button type="button" class="btn blue" onclick="javascript:User.assign();">保存</button>
</div>

<script>
	$(document).ready(function() {       
	   $('#multi_role').multiSelect({selectableHeader: "<div class='custom-header'>所有角色</div>",selectionHeader: "<div class='custom-header'>已拥有角色</div>"});
	});
</script>