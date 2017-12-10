$(function() {
	$('#ykcbship').bootstrapTable("refresh").bootstrapTable(options('0'));

	$('#zycbship').bootstrapTable("refresh").bootstrapTable(options('1'));

	$('#lgcbship').bootstrapTable("destroy").bootstrapTable(options('2'));

	function options(status) {
		var options = {};
		if (status == null || status == undefined) {
			return options;
		}
		options = {
			method : 'post',
			contentType : "application/x-www-form-urlencoded",
			url : BasePath + "/task/getTaskList?status=" + status,
			dataType : 'json',
			// // dataField: 'res',//bootstrap table 可以前端分页也可以后端分页
			// striped : true, // 是否显示行间隔色
			// pageNumber : 1, // 初始化加载第一页，默认第一页
			// pagination : true,// 是否分页
			// queryParamsType : 'limit',
			// sidePagination : 'server',
			// pageSize : 10,// 单页记录数
			// pageList : [ 5, 10, 20, 30 ],// 分页步进值
			// // showPaginationSwitch : true,// 是否显示选择分页数按钮
			// // showHeader : true,
			// showRefresh : true,// 刷新按钮
			// // showToggle : true,// 是否显示 切换试图（table/card）按钮
			// // showColumns : true,// 是否显示 内容列下拉框
			// // queryParams: getPageMessage,
			// search : true, // 显示搜索框
			// paginationPreText : '‹',// 指定分页条中上一页按钮的图标或文字,这里是<
			// paginationNextText : '›',// 指定分页条中下一页按钮的图标或文字,这里是>
			// // singleSelect: false,
			// // clickToSelect : true,// 是否启用点击选中行
			// toolbarAlign : 'right',
			// buttonsAlign : 'right',// 按钮对齐方式
			// showExport : true, // 是否显示导出
			// exportDataType : "basic", // basic', 'all', 'selected'.
			/*
			 * queryParams: function queryParams(params)
			 * {//自定义参数，这里的参数是传给后台的，分页使用 var param = { pageNumber:
			 * params.pageNumber, pageSize: params.pageSize, orderNum :
			 * $("#orderNum").val() }; return param; },
			 */

			striped : true, // 是否显示行间隔色
			cache : false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
			pagination : true, // 是否显示分页（*）
			sortable : false, // 是否启用排序
			sortOrder : "asc", // 排序方式
			// queryParams : oTableInit.queryParams,// 传递参数（*）
			// sidePagination : "server", // 分页方式：client客户端分页，server服务端分页（*）
			pageNumber : 1, // 初始化加载第一页，默认第一页
			pageSize : 10, // 每页的记录行数（*）
			pageList : [ 10, 25, 50, 100 ], // 可供选择的每页的行数（*）
			search : true, // 是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
			// strictSearch : true,
			// showColumns : true, // 是否显示所有的列
			showRefresh : true, // 是否显示刷新按钮
			// minimumCountColumns : 2, // 最少允许的列数
			// clickToSelect : true, // 是否启用点击选中行
			// height : 500, // 行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
			// uniqueId : "ID", // 每一行的唯一标识，一般为主键列
			// showToggle : true, // 是否显示详细视图和列表视图的切换按钮
			// cardView : false, // 是否显示详细视图
			// detailView : false, // 是否显示父子表

			idField : "id",// 指定主键列
			columns : [ {
				title : '全选',
				field : 'select',
				// 复选框
				checkbox : true,
				width : 25,
				align : 'center'
			}, {
				field : 'id',
				title : 'ID',
				align : 'center'
			}, {
				field : 'berth',
				title : '泊位',
				align : 'center'
			}, {
				field : 'shipName',
				title : '船名',
				align : 'center'
			}, {
				field : 'berthingTime',
				title : '停靠时间',
				align : 'center'
			}, {
				field : 'beginTime',
				title : '开工时间',
				align : 'center',
				sortable : true
			}, {
				field : 'updateUser',
				title : '完工时间',
				align : 'center'
			}, {
				field : 'updateTime',
				title : '离泊时间',
				align : 'center'
			}, {
				field : 'operation',
				title : '操作'
			} ],
			locale : 'zh-CN',// 中文支持,
			responseHandler : function(res) {
				// 在ajax获取到数据，渲染表格之前，修改数据源
				return res;
			}
		}

		if (status == '0') {
			options.toolbar = '#toolbar';// 指定工作栏
		}

		return options;
	}

	function tableHeight() {
		return $(window).height() - 140;
	}

	function add_click() {
		window.location.href = BasePath + "/task/addform"
	}
})