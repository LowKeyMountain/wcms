<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="tab-pane fade active in" id="ship">
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
							<label class="control-label col-md-3">船名 <span
								class="required"> * </span>
							</label>
							<div class="col-md-8">
								<div class="input-icon right">
									<i class="fa"></i> <input type="hidden" id="id" name="id" /> <input
										type="text" id="ship.shipName" name="ship.shipName" data-required="1"
										class="form-control" />
								</div>
							</div>
						</div>
						<div class="form-group  margin-top-20">
							<label class="control-label col-md-3">船英文名 <span
								class="required"> *</span>
							</label>
							<div class="col-md-8">
								<div class="input-icon right">
									<i class="fa"></i> <input type="text" id="ship.shipEnName"
										name="ship.shipEnName" data-required="1" class="form-control" />
								</div>
							</div>
						</div>
						<div class="form-group  margin-top-20">
							<label class="control-label col-md-3">预靠时间<span
								class="required"> </span>
							</label>
							<div class="col-md-8">
								<div class="input-icon right">
									<i class="fa"></i> 
									<input size="16" type="text" id="enterPortTime" name="enterPortTime" readonly class="form_datetime form-control">
								</div>
							</div>
						</div>
						
						<!-- 
						<div class="form-group  margin-top-20">
							<label class="control-label col-md-3">靠泊时间 <span
								class="required"> </span>
							</label>
							<div class="col-md-8">
								<div class="input-icon right">
									<i class="fa"></i> 
									<input size="16" type="text" id="berthingTime" name="berthingTime" readonly class="form_datetime form-control">
								</div>
							</div>
						</div>
						 -->
						<!-- 
						<div class="form-group  margin-top-20">
							<label class="control-label col-md-3">离泊时间 <span
								class="required"> </span>
							</label>
							<div class="col-md-8">
								<div class="input-icon right">
									<i class="fa"></i> 
									<input size="16" type="text" id="departureTime" name="departureTime" readonly class="form_datetime form-control">
								</div>
							</div>
						</div>
						 -->
						<div class="form-group  margin-top-20">
							<label class="control-label col-md-3">停靠泊位 <span
								class="required"> *</span>
							</label>
							<div class="col-md-8">
								<select id="berth" name="berth" class="form-control select2me">
									<option value="">请选择...</option>
				   					<option value="1" >矿一</option>
				   					<option value="2" >矿二</option>
								</select>
							</div>
						</div>

						<div class="form-group  margin-top-20">
							<label class="control-label col-md-3">货物总重（T）<span
								class="required"> *</span>
							</label>
							<div class="col-md-8">
								<div class="input-icon right">
									<i class="fa"></i> <input type="text" id="cargoLoad"
										name="cargoLoad" data-required="1" class="form-control" />
								</div>
							</div>
						</div>

						<div class="form-group  margin-top-20">
							<label class="control-label col-md-3">IMO 编号<span
								class="required"> </span>
							</label>
							<div class="col-md-8">
								<div class="input-icon right">
									<i class="fa"></i> <input type="text" id="ship.imoNo" name="ship.imoNo"
										data-required="1" class="form-control" />
								</div>
							</div>
						</div>
						
						<div class="form-group  margin-top-20">
							<label class="control-label col-md-3">建造时间 <span
								class="required"> </span>
							</label>
							<div class="col-md-8">
								<div class="input-icon right">
									<i class="fa"></i> 
									<!-- <input type="text" id="ship.buildDate" name="ship.buildDate" data-required="1" class="form-control" /> -->
									<input size="16" type="text" id="ship.buildDate" name="ship.buildDate" readonly class="form_datetime_yyyy_mm_dd form-control">
								</div>
							</div>
						</div>
 						
						<div class="form-group  margin-top-20">
							<label class="control-label col-md-3">船长（m）
							</label>
							<div class="col-md-8">
								<div class="input-icon right">
									<i class="fa"></i> <input type="text" id="ship.length"
									 name="ship.length" data-required="1" class="form-control" />
								</div>
							</div>
						</div>

						<div class="form-group  margin-top-20">
							<label class="control-label col-md-3">船宽 （m）
							</label>
							<div class="col-md-8">
								<div class="input-icon right">
									<i class="fa"></i> <input type="text" id="ship.breadth"
										name="ship.breadth" data-required="1" class="form-control" />
								</div>
							</div>
						</div>

						<div class="form-group  margin-top-20">
							<label class="control-label col-md-3">型深 （m）
							</label>
							<div class="col-md-8">
								<div class="input-icon right">
									<i class="fa"></i> <input type="text" id="ship.mouldedDepth"
										name="ship.mouldedDepth" data-required="1" class="form-control" />
								</div>
							</div>
						</div>

						<div class="form-group  margin-top-20">
							<label class="control-label col-md-3">舱口数（个） <span
								class="required"> * </span>
							</label>
							<div class="col-md-8">
								<div class="input-icon right">
									<i class="fa"></i> <input type="text" id="ship.cabinNum"
										name="ship.cabinNum" data-required="1" class="form-control" />
								</div>
							</div>
						</div>

						<div class="form-group  margin-top-20">
							<label class="control-label col-md-3">船舱数(个) <span
								class="required"> * </span>
							</label>
							<div class="col-md-8">
								<div class="input-icon right">
									<i class="fa"></i> <input type="text" id="ship.hatch" name="ship.hatch"
										data-required="1" class="form-control" />
								</div>
							</div>
						</div>

						<div class="form-group  margin-top-20">
							<label class="control-label col-md-3">缆绳 <span
								class="required"> </span>
							</label>
							<div class="col-md-8">
								<div class="input-icon right">
									<i class="fa"></i> <input type="text" id="ship.wire"
										name="ship.wire" data-required="1" class="form-control" />
								</div>
							</div>
						</div>
						
						<div class="form-group  margin-top-20">
							<label class="control-label col-md-3">吃水<span
								class="required"> </span>
							</label>
							<div class="col-md-8">
								<div class="input-icon right">
									<i class="fa"></i> <input type="text" id="depth"
										name="depth" data-required="1" class="form-control" />
								</div>
							</div>
						</div>

						<div class="form-group  margin-top-20">
							<label class="control-label col-md-3">干舷高度<span
								class="required"> </span>
							</label>
							<div class="col-md-8">
								<div class="input-icon right">
									<i class="fa"></i> <input type="text"
										id="freeboardDepth" name="freeboardDepth"
										data-required="1" class="form-control" />
								</div>
							</div>
						</div>
						
						<div class="form-group  margin-top-20">
							<label class="control-label col-md-3">特殊舱型 <span
								class="required"> </span>
							</label>
							<div class="col-md-8">
								<div class="input-icon right">
									<i class="fa"></i> <input type="text" id="ship.specialCabinType"
										name="ship.specialCabinType" data-required="1" class="form-control" />
								</div>
							</div>
						</div>
						
					</div>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<button type="button" class="btn default" data-dismiss="modal" onclick="javascript:Task.returnList();">返回</button>
			<button type="submit" class="btn blue" onclick="javascript:;">保存</button>
		</div>

	</form>
	<!-- END FORM-->
</div>