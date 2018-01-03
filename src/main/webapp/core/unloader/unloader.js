$(function(){
	
		$('#unloader').bootstrapTable("destroy").bootstrapTable({  // init via javascript
			
			method : 'post',
			contentType : "application/x-www-form-urlencoded",
			url:BasePath + "/unloader/getUnloaderList",
			dataType : 'json',
//			 dataField: 'res',//bootstrap table 可以前端分页也可以后端分页
			 striped : true, // 是否显示行间隔色
			 pagination : true,// 是否分页
	         sortable: true,    //是否启用排序
	         sortOrder: "asc",  //排序方式			 
			 cache: false,  //是否使用缓存，默认为true
			 queryParamsType : '',
			 queryParams : 'queryParams',
			 sidePagination : 'server',
			 pageNumber : 1, // 初始化加载第一页，默认第一页			 
			 pageSize : 50,// 单页记录数
			 pageList : [50,100,150,300],// 分页步进值
			 showPaginationSwitch : true,// 是否显示选择分页数按钮
			 showHeader : true,
			 showRefresh : true,// 刷新按钮
			 // showToggle : true,// 是否显示 切换试图（table/card）按钮
			 showColumns : true,// 是否显示 内容列下拉框
//			  queryParams: getPageMessage,
//			 search : true, // 显示搜索框
			 paginationPreText : '<',// 指定分页条中上一页按钮的图标或文字,这里是<
			 paginationNextText : '>',// 指定分页条中下一页按钮的图标或文字,这里是>
			 singleSelect: true,
			 clickToSelect : true,// 是否启用点击选中行
			 toolbar : '#toolbar',
			 toolbarAlign : 'left',
			 buttonsAlign : 'left',// 按钮对齐方式
//			 showExport : true, // 是否显示导出
//			 exportDataType : "basic", // basic', 'all', 'selected'.
			
			queryParams: function queryParams(params){//自定义参数，这里的参数是传给后台的，分页使用
				var params = {//这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
//						 limit: params.limit,   //页面大小
//			             offset: params.offset-1,  //页码
						 limit: params.pageSize,
						 offset: params.pageNumber,
			             sortName: params.sortName,
			             sortOrder: params.sortOrder,			             
//			             maxrows: params.limit,
//			             pageindex:params.pageNumber,
//						 order: params.order,
//						 sort: params.sort,
			             startDate:$("#startDate").val(),
			             endDate:$("#endDate").val(),
			             startPosition:$("#startPosition").val(),
			             endPosition:$("#endPosition").val(),			             
						 cmsId: $("#cmsid").val(),
				};
				return params; 
			},
			 
			
			idField : "id",// 指定主键列
		    columns: [
		    /*{
		    	title:'全选',
		        field:'select',
		        //复选框
		        checkbox:true,
		        width:25,
		        align:'center'
		    }, */{
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
                    return row.operationType == 0 ? "<font color=grey>位移</font>" : row.operationType == 1 ? "<font color=red>作业</font>" : "<font color=lightgreen>在线</font>";  
                } 
		    }, {
		        field: 'time',
		        title: '操作时间',
		        align: 'center',
		        sortable:true
		    }, {
		        field: 'pushTime',
		        title: '推送时间',
		        align: 'center',
		        sortable:true
		    },{
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
	});
		//查询按钮
        $("#btn_query").off().on(
        		"click",function(){
        			$('#unloader').bootstrapTable('refresh');                    
        })
        //重置按钮事件  
        $("#btn_reset").off().on("click",function(){  
            $("#startDate").val("");
            $("#endDate").val("");
            $("#cmsid").val("");
            $("#startPosition").val("");
            $("#endPosition").val("");
        });          
});