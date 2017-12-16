<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="tab-pane fade" id="cargo">
	<div class="portlet box yellow">
		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-cogs"></i>
			</div>
			<div class="tools">
				<!-- 
				<a href="javascript:;" class="collapse" data-original-title=""
					title=""> </a>
					 -->
				<a href="javascript:Cargo.add_click();" data-toggle="modal"
					class="config" data-original-title="" title=""> </a> <a
					href="javascript:Cargo.list();;" class="reload" data-original-title="" title="">
				</a>
				<!-- <a href="javascript:Cargo.list();" class="remove" data-original-title=""
					title=""> </a>
					 -->
			</div>
		</div>
		<div class="portlet-body" style="display: block;">
			<div class="table-responsive">
				<table class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th>#</th>
							<th>货物种类</th>
							<th>货物类型</th>
							<th>装货港</th>
							<th>水分</th>
							<th>品质</th>
							<th>配载吨位</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody id="cargo_tbody">
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>