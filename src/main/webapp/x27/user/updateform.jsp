<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal"
		aria-hidden="true"></button>
	<h4 class="modal-title">修改帐户</h4>
</div>

<!-- BEGIN FORM-->
<form action="#" id="form_cl" name="form_cl_update" class="form-horizontal">
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
					<div class="form-group">
						<label class="control-label col-md-3">用户名 <span
							class="required"> * </span>
						</label>
						<div class="col-md-8">
							<input type="hidden" id="id" name="id" value="${user.id}" /> <input
								type="text" id="userName" name="userName" data-required="1"
								class="form-control" value="${user.userName}"
								readonly="readonly" />
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-md-3">密码 <span
							class="required"> * </span>
						</label>
						<div class="col-md-8">
							<button type="button" class="btn btn-sm yellow"
								onclick="javascript:User.resetpass('${user.id}');">重置密码</button>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-md-3">姓名 <span
							class="required"> * </span>
						</label>
						<div class="col-md-8">
                            <div class="input-icon right">
	                            <i class="fa"></i>
								<input type="text" id="realName" name="realName"
									data-required="1" class="form-control" value="${user.realName}" />
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-md-3">性别 <span
							class="required"> * </span>
						</label>
						<div class="col-md-8">
							<div class="radio-list"
								data-error-container="#form_user_gender_error">
								<label> <input type="radio" name="gender" value="1"
								<c:if test="${user.gender==true}">checked="checked"</c:if>
								/> 男
								</label> <label> <input type="radio" name="gender" value="0"
								<c:if test="${user.gender==false}">checked="checked"</c:if>
								/> 女
								</label>
								
							</div>
							<div id="form_user_gender_error"></div>
						</div>
					</div>
					<!-- 
					<div class="form-group">
						<label class="control-label col-md-3">类型 <span
							class="required"> * </span>
						</label>
						<div class="col-md-8">
							<div class="radio-list"
								data-error-container="#form_user_isadmin_error">
								<label> <input type="radio" name="isAdmin" value="1"
								<c:if test="${user.isAdmin==true}">checked="checked"</c:if>
								/>超级管理员
								</label> <label> <input type="radio" name="isAdmin" value="0"
								<c:if test="${user.isAdmin==false}">checked="checked"</c:if>
								/>普通
								</label>
							</div>
							<div id="form_user_isadmin_error"></div>
						</div>
					</div>
				 	-->
					<!-- 
					<div class="form-group">
						<label class="control-label col-md-3">所属部门 <span
							class="required"> * </span>
						</label>
						<div class="col-md-8">
							<select id="departmentId" name="departmentId" class="form-control select2me">
								<option value="">请选择...</option>
					   			<c:if test="${!(departments == null || fn:length(departments) == 0)}">
					   				<c:forEach var="department" items="${departments}">
					   					<option value="${department.id}">${department.name}</option>
					   				</c:forEach>
					   			</c:if>
							</select>
						</div>
					</div>
					 -->
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
		FormCl.init();
	});
</script>