<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal"
		aria-hidden="true"></button>
	<h4 class="modal-title">�����ʻ�</h4>
</div>
<!-- BEGIN FORM-->
<form action="#" id="form_cl" name="form_cl_add" class="form-horizontal">
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
						<label class="control-label col-md-3">�û��� <span
							class="required"> * </span>
						</label>
						<div class="col-md-8">
	                      	<div class="input-icon right">
                            <i class="fa"></i>
							<input type="hidden" id="id" name="id" /> <input type="text"
								id="userName" name="userName" data-required="1"
								class="form-control" />
							</div>
						</div>
					</div>
					<div class="form-group  margin-top-20">
						<label class="control-label col-md-3">���� <span
							class="required"> * </span>
						</label>
						<div class="col-md-8">
						    <div class="input-icon right">
                            <i class="fa"></i>
							<input type="password" id="password" name="password"
								data-required="1" class="form-control" />
							</div>
						</div>
					</div>
					<div class="form-group  margin-top-20">
						<label class="control-label col-md-3">ȷ������ <span
							class="required"> * </span>
						</label>
						<div class="col-md-8">
	                      	<div class="input-icon right">
                            <i class="fa"></i>
							<input type="password" id="confirm_password" name="confirm_password"
								data-required="1" class="form-control" />
							</div>
						</div>
					</div>
					<div class="form-group  margin-top-20">
						<label class="control-label col-md-3">���� <span
							class="required"> * </span>
						</label>
						<div class="col-md-8">
                            <div class="input-icon right">
                            <i class="fa"></i>
							<input type="text" id="realName" name="realName"
								data-required="1" class="form-control" />
							</div>
						</div>
					</div>
					<div class="form-group  margin-top-20">
						<label class="control-label col-md-3">�Ա� <span
							class="required"> * </span>
						</label>
						<div class="col-md-8">
							<div class="radio-list"
								data-error-container="#form_user_gender_error">
								<label> <input type="radio" name="gender" value="1" checked/> ��
								</label> <label> <input type="radio" name="gender" value="0" />
									Ů
								</label>
							</div>
							<div id="form_user_gender_error"></div>
						</div>
					</div>
					<div class="form-group  margin-top-20">
						<label class="control-label col-md-3">���� <span
							class="required"> * </span>
						</label>
						<div class="col-md-8">
							<div class="radio-list"
								data-error-container="#form_user_isadmin_error">
								<label> <input type="radio" name="isAdmin" value="1" />
									��������Ա
								</label> <label> <input type="radio" name="isAdmin" value="0"
									checked /> ��ͨ
								</label>
							</div>
							<div id="form_user_isadmin_error"></div>
						</div>
					</div>
					<!-- 
					<div class="form-group  margin-top-20">
						<label class="control-label col-md-3">��������
						<span class="required">
							 *
						</span>
						</label>
						<div class="col-md-8">
							<select id="departmentId" name="departmentId" class="form-control select2me">
								<option value="">��ѡ��...</option>
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
	<button type="button" class="btn default" data-dismiss="modal">�ر�</button>
	<button type="submit" class="btn blue" onclick="javascript:;">����</button>
</div>
</form>
<!-- END FORM-->
<script>
	jQuery(document).ready(function() {
		FormCl.init();
		FormValidation.init();
	});
</script>