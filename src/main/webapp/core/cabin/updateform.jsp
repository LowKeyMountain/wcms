<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal"
		aria-hidden="true"></button>
	<h4 class="modal-title">�޸Ļ���</h4>
</div>

<!-- BEGIN FORM-->
<form action="#" id="form_cl_cabin" name="form_cl_update"
	class="form-horizontal">
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
						<label class="control-label col-md-3">���պ� <span
							class="required">  </span>
						</label>
						<div class="col-md-8">
							<div class="input-icon right">
								<i class="fa"></i> <input type="hidden" id="id" name="id" value="${cabin.id}"/> <input
									type="text" id="cabinNo" name="cabinNo" value="${cabin.cabinNo}" data-required="1"
									class="form-control" readonly/>
							</div>
						</div>
					</div>
					<div class="form-group  margin-top-20">
						<label class="control-label col-md-3">�������� <span
							class="required"> * </span>
						</label>
						<!-- 
						<div class="col-md-8">
							<div class="input-icon right">
								<i class="fa"></i> <input type="text" id="cargoId"
									name="cargoId" data-required="1" value="${cabin.cargo.id}" class="form-control" />
							</div>
						</div>
						 -->
						<%
							boolean isSelected = false;
						%>
						<div class="col-md-8">
							<select id="cargo.id" name="cargo.id"
								class="form-control select2me">
								<c:if test="${!(cargos == null || fn:length(cargos) == 0)}">
									<c:forEach var="cargo" items="${cargos}">
										<c:choose>
											<c:when test="${cargo.cargoType == null}">
												<c:set var="cargoId" value="${cargo.id}" />
											</c:when>
											<c:when test="${cargo.id == cabin.cargo.id}">
												<option value="${cargo.id}" selected>${cargo.cargoType}</option>
												<%
													isSelected = true;
												%>
											</c:when>
											<c:otherwise>
												<option value="${cargo.id}">${cargo.cargoType}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</c:if>
								<%
									if (!isSelected) {
									Integer cargoId = (Integer) pageContext.getAttribute("cargoId");
								%>
								<option value="<%=cargoId%>" selected>��ѡ��...</option>
								<%
									}
								%>
							</select>
						</div>
					</div>
					<div class="form-group  margin-top-20">
						<label class="control-label col-md-3">װ������T�� <span
							class="required"> * </span>
						</label>
						<div class="col-md-8">
							<div class="input-icon right">
								<i class="fa"></i> <input type="text" id="preunloading"
									name="preunloading" data-required="1" value="${cabin.preunloading}" class="form-control" />
							</div>
						</div>
					</div>
					<!--  
					<div class="form-group  margin-top-20">
						<label class="control-label col-md-3">��ʼλ�� <span
							class="required"></span>
						</label>
						<div class="col-md-8">
							<div class="input-icon right">
								<i class="fa"></i> <input type="text" id="startPosition"
									name="startPosition" data-required="1" value="${cabin.startPosition}" class="form-control"
									<c:if test="${!(cabin.cabinNo == 1)}">
									readonly="true"
									</c:if>
									 />
							</div>
						</div>
					</div>
					<div class="form-group  margin-top-20">
						<label class="control-label col-md-3">����λ�� <span
							class="required"></span>
						</label>
						<div class="col-md-8">
							<div class="input-icon right">
								<i class="fa"></i> <input type="text" id="endPosition"
									name="endPosition" data-required="1" value="${cabin.endPosition}" class="form-control" />
							</div>
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
		CabinFormCl.init();
		FormValidation.init();
	});
</script>