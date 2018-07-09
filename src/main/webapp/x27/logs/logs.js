function initTable() {

	$('#systemLog')
			.bootstrapTable("destroy")
			.bootstrapTable(
					{ // init via javascript

						method : 'post',
						contentType : "application/x-www-form-urlencoded",
						url : BasePath + "/logs/getUnloaderList?rnd="
								+ Math.random(),
						dataType : 'json',
						// dataField: 'res',//bootstrap table 可以前端分页也可以后端分页
						striped : true, // 是否显示行间隔色
						pagination : true,// 是否分页
						sortable : true, // 是否启用排序
						sortOrder : 'asc', // 排序方式
						cache : false, // 是否使用缓存，默认为true
						queryParamsType : '',
						queryParams : 'queryParams',
						sidePagination : 'server',
						pageNumber : 1, // 初始化加载第一页，默认第一页
						pageSize : 10,// 单页记录数
						pageList : [ 10, 20, 30, 50 ],// 分页步进值
						// showPaginationSwitch : true,// 是否显示选择分页数按钮
						showHeader : true,
						showRefresh : true,// 刷新按钮
						// showToggle : true,// 是否显示 切换试图（table/card）按钮
						showColumns : true,// 是否显示 内容列下拉框
						// queryParams: getPageMessage,
						// search : true, // 显示搜索框
						paginationPreText : '上一页',// 指定分页条中上一页按钮的图标或文字,这里是<
						paginationNextText : '下一页',// 指定分页条中下一页按钮的图标或文字,这里是>
						// singleSelect: true,
						clickToSelect : true,// 是否启用点击选中行
						toolbar : '#toolbar',
						toolbarAlign : 'right',
						buttonsAlign : 'left',// 按钮对齐方式

						queryParams : function queryParams(params) {// 自定义参数，这里的参数是传给后台的，分页使用
							var params = {// 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
								// limit: params.limit, //页面大小
								// offset: params.offset-1, //页码
								limit : params.pageSize,
								offset : params.pageNumber,
								sortName : params.sortName,
								sortOrder : params.sortOrder,
								// maxrows: params.limit,
								// pageindex:params.pageNumber,
								// order: params.order,
								// sort: params.sort,
								startDate : $("#startDate").val(),
								endDate : $("#endDate").val(),
								inputUserId : $("#inputUserId").val(),
								bussTypeDesc : $("#bussTypeDesc").val()
							};
							return params;
						},

						idField : "id",// 指定主键列
						columns : [
								{
									field : 'bussTypeDesc',
									title : '业务类型',
									align : 'center',
									width : '10%'
								},
								{
									field : 'moudleName',
									title : '模块名称',
									align : 'center',
									width : '10%'
								},
								{
									field : 'operateTypeDesc',
									title : '操作类型',
									align : 'center',
									width : '10%'
								},
								{
									field : 'inputUserId',
									title : '操作用户',
									align : 'center',
									width : '10%',
									sortable : true
								},
								{
									field : 'requestIp',
									title : '请求IP',
									align : 'center',
									width : '10%',
									sortable : true
								},
								{
									field : 'operateResult',
									title : '操作结果',
									align : 'center',
									width : '10%',
									sortable : true,
									formatter : function(value, row, index) {// 自定义显示，这三个参数分别是：value该行的属性，row该行记录，index该行下标
										return row.operateResult == 1 ? "<font color=grey>成功</font>"
												: row.operateResult == 0 ? "<font color=red>失败</font>"
														: "<font color=lightgreen>异常</font>";
									}
								},
								{
									field : 'operationTime',
									title : '操作时间',
									align : 'center',
									width : '10%',
									sortable : true
								},
								{
									field : 'logDetails',
									title : '日志详情',
									align : 'center',
									width : '10%',
									formatter : function(value, row, index) {// 自定义显示，这三个参数分别是：value该行的属性，row该行记录，index该行下标
										var v = row.logDetails;
										if (v == null || v == undefined) {
											v = "";
										}
										v = v.replace(/\'/g, "’").replace(
												/\"/g, "”");
										return "<a href='#' onclick='javascript:show(\""
												+ v
												+ "\");' class='nav-link '>查看</a>";
									}
								} ],
						locale : 'zh-CN',// 中文支持,
						responseHandler : function(res) {
							// 在ajax获取到数据，渲染表格之前，修改数据源
							return res;
						}
					});

}

function show(val) {
	var oNewWin = window
			.open(
					'',
					'newwindow',
					'height=400, width=600, toolbar =no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');
	oNewWin.document.close();
	oNewWin.document.write(val);
}

$(function() {
	initTable();
	$("#operationtype").change(function() {
		if ($('#operationtype').val() == "0") {
			$('#onetask_div').hide();
		} else {
			$('#onetask_div').show();
		}
	})
	// 查询按钮
	$("#btn_query").off().on("click", function() {
		initTable();
	})

	// 重置按钮事件
	$("#btn_reset").off().on("click", function() {
		$("#startDate").val("");
		$("#endDate").val("");
		$("#bussTypeDesc").val("");
		$("#inputUserId").val("");
	});

});
