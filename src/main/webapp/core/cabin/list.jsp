<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="tab-pane fade" id="cabin">
	<div class="portlet box yellow">
		<div class="portlet-title">
			<div class="caption">
				<!-- <i class="fa fa-cogs"></i> -->
			</div>
			<div class="tools">
				<a href="javascript:Cabin.add_click();" data-toggle="modal"
					class="config" data-original-title="����" title="����"> </a> <a
					href="javascript:Cabin.list();;" class="reload" data-original-title="ˢ��" title="ˢ��">
				</a>
			</div>
		</div>
		<div class="portlet-body" style="display: block;">
			<div class="table-responsive">
				<table class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th>���ձ��</th>
							<th>��������</th>
							<th>��ʼλ��</th>
							<th>����λ��</th>
							<th>װ����</th>
							<th>����</th>
						</tr>
					</thead>
					<tbody id="cabin_tbody">
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>