<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="tab-pane fade" id="cabin">
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
				<a href="javascript:Cabin.add_click();" data-toggle="modal"
					class="config" data-original-title="" title=""> </a> <a
					href="javascript:Cabin.list();;" class="reload" data-original-title="" title="">
				</a>
				<!-- <a href="javascript:Cargo.list();" class="remove" data-original-title=""
					title=""> </a>
					 -->
			</div>
		</div>
		<div class="portlet-body">
			<div class="table-responsive">
				<table class="table">
					<thead>
						<tr>
							<th>船舱编号</th>
							<th>货物类型</th>
							<th>开始位置</th>
							<th>结束位置</th>
							<th>装载量</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody id="cabin_tbody">
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>