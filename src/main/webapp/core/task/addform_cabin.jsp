<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="tab-pane fade" id="cabin">
	<!-- BEGIN FORM-->
	<form action="#" id="form_cl" name="form_cl_add"
		class="form-horizontal">
		<div class="alert alert-danger display-hide">
			<button class="close" data-close="alert"></button>
			数据格式异常. 请重新输入.
		</div>
		<div class="alert alert-success display-hide">
			<button class="close" data-close="alert"></button>
			数据校验通过!
		</div>
		<div class="modal-body">
			<div class="row">
				<div class="col-md-12">
					<div class="form-body">

						<div class="form-group  margin-top-20">
							<label class="control-label col-md-3"> 货类 <span
								class="required"> </span>
							</label>
							<div class="col-md-8">
								<div class="input-icon right">
									<i class="fa"></i> <input type="text" id="specialCabinType"
										name="specialCabinType" data-required="1" class="form-control" />
								</div>
							</div>
						</div>


					</div>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<button type="button" class="btn default" data-dismiss="modal">重置</button>
			<button type="submit" class="btn blue" onclick="javascript:;">保存</button>
		</div>

	</form>
	<!-- END FORM-->
</div>