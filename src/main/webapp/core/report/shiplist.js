	function initTable(){
		
		$('#shiplist').bootstrapTable("destroy").bootstrapTable({  // init via javascript
			
			method : 'post',
			contentType : "application/x-www-form-urlencoded",
			url:BasePath + "/task/getTaskWhList?rnd=" + Math.random(),
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
			 pageSize : 10,// 单页记录数
			 pageList : [20, 30, 50],// 分页步进值
//			 showPaginationSwitch : true,// 是否显示选择分页数按钮
			 showHeader : true,
//			 showRefresh : true,// 刷新按钮
			 // showToggle : true,// 是否显示 切换试图（table/card）按钮
//			 showColumns : true,// 是否显示 内容列下拉框
//			 queryParams: getPageMessage,
//			 search : true, // 显示搜索框
			 paginationPreText : '上一页',// 指定分页条中上一页按钮的图标或文字,这里是<
			 paginationNextText : '下一页',// 指定分页条中下一页按钮的图标或文字,这里是>
//			 singleSelect: true,
//			 clickToSelect : true,// 是否启用点击选中行
			 toolbar : '#toolbar',
			 toolbarAlign : 'right',
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
//						 cmsId: $("#cmsid").val(),
						 status: $("#status").val(),
						 dateRange: $("#dateRange").val()

				};
				return params; 
			},
			
			idField : "id",// 指定主键列
		    columns: [
/*		    {
		    	title:'',
		        field:'select',
		        //复选框
		        checkbox:true,
		        width:'3%',
		        align:'center'
		    },*/{
		        field: 'id',
		        title: 'id',
		        align: 'center',
		        visible: false
		    }, {
		        field: 'shipName',
		        title: '船舶名称',
		        align: 'center',
		        width: '10%',
		        formatter: function (value, row, index) {
                    var html = '<a href="javascript:view_ship(' + row.id + ')" class="font-weight-normal">' + row.shipName + '</a>';
                    return html;
                }
		    }, {
		        field: 'berth',
		        title: '泊位',
		        align: 'center',
		        width: '6%'
		    }, {
		        field: 'berthingTime',
		        title: '靠泊时间',
		        align: 'center',
		        width: '12%'
		    }, {
		        field: 'departureTime',
		        title: '离泊时间',
		        align: 'center',
		        width: '12%'
		    }, {
		        field: 'beginTime',
		        title: '开工时间',
		        align: 'center',
		        width: '12%',
		        sortable:true
		    }, {
		        field: 'endTime',
		        title: '结束时间',
		        align: 'center',
		        width: '12%',
		        sortable:true
		    },/*{
		        field: 'cargoLoad',
		        title: '货物总重',
		        width: '6%',		        
		        align: 'center'
		    }, */{
		        field: 'status',
		        title: '状态',
		        align: 'center',
		        width: '8%',
                formatter: function (value, row, index) {//自定义显示，这三个参数分别是：value该行的属性，row该行记录，index该行下标  
                    return row.status == 0 ? "<font color=lightgreen>预靠</font>" : row.status == 1 ? "<font color=red>作业中</font>" : "<font color=grey>已离港</font>";  
                }
		    },/* {
		        field: 'updateUser',
		        title: '修改人',
		        align: 'center',
		        width: '10%'
		    }, {
		        field: 'Date',
		        title: '更新时间',
		        align: 'center',
		        width: '10%'
		    }, */{
		        title: '操作',
		        align: 'center',
		        width: '16%',
		        formatter: function (value, row, index) {
		        	if(reportType==1){
			            return '<a class="btn cargoInfoStats default" ><i class="fa fa-bar-chart"></i>统计结果</a>'
		        	}else if(reportType==2){
			            return '<a class="btn cabinUnloadStats default" ><i class="fa fa-bar-chart"></i>统计结果</a>'
		        	}else if(reportType==3){
			            return '<a class="btn unloaderStats default" ><i class="fa fa-bar-chart"></i>统计结果</a>'
		        	}else if(reportType==4){
			            return '<a class="btn workShiftStats default" ><i class="fa fa-bar-chart"></i>统计结果</a>'
		        	}else if(reportType==5){
			            return '<a class="btn cabinEffStats default" ><i class="fa fa-bar-chart"></i>统计结果</a>'
		        	}else if(reportType==6){
			            return '<a class="btn cargoEffStats default" ><i class="fa fa-bar-chart"></i>统计结果</a>'
		        	}else if(reportType==7){
			            return '<a class="btn outboardStats default" ><i class="fa fa-bar-chart"></i>统计结果</a>'
		        	}
		        },
		    	events: {
	                'click .cargoInfoStats' : function(e, value, row, index) {
	        			window.location.href = BasePath + "/report/cargoInfoStats?taskId=" + row.id;
	                 },
	                'click .cabinUnloadStats' : function(e, value, row, index) {
	        			window.location.href = BasePath + "/report/cabinUnloadStats?taskId=" + row.id;
	                 },
	                'click .unloaderStats' : function(e, value, row, index) {
	        			window.location.href = BasePath + "/report/unloaderStats?taskId=" + row.id;
	                 },
	                'click .workShiftStats' : function(e, value, row, index) {
	        			window.location.href = BasePath + "/report/workShiftStats?taskId=" + row.id;
	                 },
	                'click .cabinEffStats' : function(e, value, row, index) {
	        			window.location.href = BasePath + "/report/cabinEffStats?cargoId=&taskId=" + row.id;
	                 },
	                'click .cargoEffStats' : function(e, value, row, index) {
	        			window.location.href = BasePath + "/report/cargoEffStats?taskId=" + row.id;
	                 },
	                'click .outboardStats' : function(e, value, row, index) {
	        			window.location.href = BasePath + "/report/outboardStats?taskId=" + row.id;
	                 }
	        	}
		    }],
			locale : 'zh-CN',// 中文支持,
			responseHandler : function(res) {
				// 在ajax获取到数据，渲染表格之前，修改数据源
				return res;
			}		
		});
		
	}
	/**
	 * 查看船舶信息
	 */
	function view_ship(taskId) {
		window.location.href = BasePath + "/task/view?taskId="+ taskId;
	}
	
	/**
	 * 点击查看报表信息
	 */
	function view_report(reportType) {
        var a= $('#maintenance').bootstrapTable('getSelections');
        if(a.length==1){
//             console.log(a[0].id);
             window.location.href = BasePath + "/report/reportview?taskId="+ a[0].id + "&reportType=" + reportType;
        }else{
        	alert("请先选中一行记录!");
        }    		
	}
	
	$(function(){
		initTable();
		
/*		if(reportType == 4){
       		$('#shift-div').show();
	    }else{
       		$('#shift-div').hide();
	    }*/
		//查询按钮
        $("#btn_query").off().on("click",function(){
			//$('#unloader').bootstrapTable('refresh');     
			initTable();
        })
        //重置按钮事件  
        $("#btn_reset").off().on("click",function(){
            $("#dateRange").val("0");
            $("#status").val("");
        });      
	});