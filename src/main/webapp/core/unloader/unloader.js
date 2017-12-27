$(function(){
	
		$('#unloader').bootstrapTable("destroy").bootstrapTable({  // init via javascript
			
			method : 'post',
			contentType : "application/x-www-form-urlencoded",
			url:BasePath + "/unloader/getUnloaderList",
			dataType : 'json',
//			 dataField: 'res',//bootstrap table 可以前端分页也可以后端分页
			 striped : true, // 是否显示行间隔色
			 pageNumber : 1, // 初始化加载第一页，默认第一页
			 pagination : true,// 是否分页
//			 queryParamsType : 'limit',
			 queryParams : 'queryParams',
			 sidePagination : 'server',
			 pageSize : 50,// 单页记录数
			 pageList : [ 50, 100, 200, 300 ],// 分页步进值
			 showPaginationSwitch : false,// 是否显示选择分页数按钮
			 // showHeader : true,
			 showRefresh : false,// 刷新按钮
			 // showToggle : true,// 是否显示 切换试图（table/card）按钮
			 // showColumns : true,// 是否显示 内容列下拉框
			 // queryParams: getPageMessage,
//			 search : true, // 显示搜索框
			 paginationPreText : '上一页',// 指定分页条中上一页按钮的图标或文字,这里是<
			 paginationNextText : '下一页',// 指定分页条中下一页按钮的图标或文字,这里是>
			 singleSelect: false,
			 clickToSelect : true,// 是否启用点击选中行
			 toolbar : '#toolbar',
			 toolbarAlign : 'left',
			 buttonsAlign : 'left',// 按钮对齐方式
//			 showExport : true, // 是否显示导出
//			 exportDataType : "basic", // basic', 'all', 'selected'.
			
			queryParams: function queryParams(params){//自定义参数，这里的参数是传给后台的，分页使用
				var params = {//这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
						 pageNumber:params.limit,//页面大小
						 pageSize: params.offset,//页码
						 order: params.order,
						 orderName: params.sort,
						 unloaderName: $("#unloadername").val(),
				};
				return params; 
			},
			 
			
			idField : "id",// 指定主键列
		    columns: [
		    {
		    	title:'全选',
		        field:'select',
		        //复选框
		        checkbox:true,
		        width:25,
		        align:'center'
		    }, {
		        field: 'id',
		        title: 'id',
		        align: 'center'
		    }, {
		        field: 'Cmsid',
		        title: 'Cms卸船机编号',
		        align: 'center'
		    }, {
		        field: 'operationType',
		        title: '操作类型',
		        align: 'center',
                formatter: function (value, row, index) {//自定义显示，这三个参数分别是：value该行的属性，row该行记录，index该行下标  
                    return row.operationType == 0 ? "大车位置" : row.operationType == 1 ? "作业量信息" : "数据异常";  
                } 
		    }, {
		        field: 'time',
		        title: '操作时间',
		        align: 'center',
		        sortable:true
		    }, {
		        field: 'direction',
		        title: '方向',
		        align: 'center'
		    }, {
		        field: 'unloaderMove',
		        title: '卸船机移动位置',
		        align: 'center'
		    }, {
		        field: 'OneTask',
		        title: '一次抓钩作业量',
		        align: 'center'
		    }],
			locale : 'zh-CN',// 中文支持,
			responseHandler : function(res) {
				// 在ajax获取到数据，渲染表格之前，修改数据源
				return res;
			}
	})
});