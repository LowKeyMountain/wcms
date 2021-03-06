<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springsecurity.org/jsp" prefix="security"%>  
<div class="tab-pane fade" id="cargo">
	<div class="portlet box yellow">
		<div class="portlet-title">
			<div class="caption">
				<!-- <i class="fa fa-cogs"></i> -->
			</div>
			<div class="tools">
				<security:authorize buttonUrl="2">
				<a href="javascript:Cargo.add_click();" data-toggle="modal"
					class="config" title="新增" title=""> </a> <a
					href="javascript:Cargo.list();;" class="reload" data-original-title="刷新" title="">
				</a>
				</security:authorize>
			</div>
		</div>
		<div class="portlet-body" style="display: block;">
			<div class="table-responsive">
				<table class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<!-- <th>#</th> -->
							<th>货物种类</th>
							<th>货物名称</th>
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