$(function(){
		$('#ykcbship').bootstrapTable("refresh").bootstrapTable({
		    	method: 'post',
		    	contentType: "application/x-www-form-urlencoded",
		    	url:BasePath + "/task/getTaskList?status=02",
		        dataType: 'json',
//		        dataField: 'res',//bootstrap table 可以前端分页也可以后端分页
		    	striped: true, //是否显示行间隔色
		    	pageNumber: 1, //初始化加载第一页，默认第一页
		    	pagination:true,//是否分页
		    	queryParamsType:'limit',
		    	sidePagination:'server',
		    	pageSize:10,//单页记录数
		    	pageList:[5,10,20,30],//分页步进值
	            showPaginationSwitch: true,//是否显示选择分页数按钮
	            showHeader:true,
		    	showRefresh:true,//刷新按钮
		        showToggle: true,//是否显示 切换试图（table/card）按钮  
		    	showColumns:true,//是否显示 内容列下拉框
//		        queryParams: getPageMessage,
		        search: true, //显示搜索框  
		        paginationPreText: '‹',//指定分页条中上一页按钮的图标或文字,这里是<
		        paginationNextText: '›',//指定分页条中下一页按钮的图标或文字,这里是>
//		        singleSelect: false,
		    	clickToSelect: true,//是否启用点击选中行
		    	toolbarAlign:'right',
		    	buttonsAlign:'right',//按钮对齐方式
	            showExport: true,                     //是否显示导出		    	
	            exportDataType: "basic",              //basic', 'all', 'selected'.
		    	toolbar:'#toolbar',//指定工作栏
/*		        queryParams: function queryParams(params) {//自定义参数，这里的参数是传给后台的，分页使用  
		        	var param = {    
	                  pageNumber: params.pageNumber,    
	                  pageSize: params.pageSize,  
	                  orderNum : $("#orderNum").val()  
              		};    
              		return param; 
		        }, */
		        idField: "id",//指定主键列  
			    columns: [{
			    	title:'全选',
	                field:'select',
	                //复选框
	                checkbox:true,
	                width:25,
	                align:'center'
	            },{
			        field: 'id',
			        title: 'id',
                    align: 'center'
			    }, {
			        field: 'shipName',
			        title: '船名',
                    align: 'center'
			    }, {
			        field: 'berthingTime',
			        title: '停靠时间',
                    align: 'center'
			    }, {
			        field: 'beginTime',
			        title: '开工时间',
                    align: 'center',
	        		sortable:true
			    },{
			        field: 'updateUser',
			        title: '修改人',
                    align: 'center'
			    },{
			        field: 'updateTime',
			        title: '修改时间',
                    align: 'center'
			    },{
			        field: 'operation',
			        title: '操作'
			    }],
		        locale:'zh-CN',//中文支持,
			    responseHandler:function(res){
		            //在ajax获取到数据，渲染表格之前，修改数据源
		            return res;
		        }
		});
		
		$('#zycbship').bootstrapTable("refresh").bootstrapTable({
	    	method: 'post',
	    	contentType: "application/x-www-form-urlencoded",
	    	url:BasePath + "/task/getTaskList?status=01",
	        dataType: 'json',
//	        dataField: 'res',//bootstrap table 可以前端分页也可以后端分页
	    	striped: true, //是否显示行间隔色
	    	pageNumber: 1, //初始化加载第一页，默认第一页
	    	pagination:true,//是否分页
	    	queryParamsType:'limit',
	    	sidePagination:'server',
	    	pageSize:10,//单页记录数
	    	pageList:[5,10,20,30],//分页步进值
            showPaginationSwitch: true,//是否显示选择分页数按钮
            showHeader:true,
	    	showRefresh:true,//刷新按钮
	        showToggle: true,//是否显示 切换试图（table/card）按钮  
	    	showColumns:true,//是否显示 内容列下拉框 
	        search: true, //显示搜索框  
	        paginationPreText: '‹',//指定分页条中上一页按钮的图标或文字,这里是<
	        paginationNextText: '›',//指定分页条中下一页按钮的图标或文字,这里是>
//	        singleSelect: false,
	    	clickToSelect: true,//是否启用点击选中行
	    	toolbarAlign:'right',
	    	buttonsAlign:'right',//按钮对齐方式
            showExport: true,                     //是否显示导出		    	
            exportDataType: "basic",              //basic', 'all', 'selected'.	    	
	    	toolbar:'#toolbar',//指定工作栏
/*		        queryParams: function (params) {//自定义参数，这里的参数是传给后台的，分页使用  
	            return {//这里的params是table提供的  
	                cp: params.offset,//从数据库第几条记录开始  
	                ps: params.limit//找多少条  
	            };  
	        }, */
	        idField: "id",//指定主键列  
		    columns: [{
		    	title:'全选',
                field:'select',
                //复选框
                checkbox:true,
                width:25,
                align:'center'
            },{
		        field: 'id',
		        title: 'id',
                align: 'center'
		    }, {
		        field: 'shipName',
		        title: '船名',
                align: 'center'
		    }, {
		        field: 'berthingTime',
		        title: '停靠时间',
                align: 'center'
		    }, {
		        field: 'beginTime',
		        title: '开工时间',
                align: 'center',
        		sortable:true
		    },{
		        field: 'updateUser',
		        title: '修改人',
                align: 'center'
		    },{
		        field: 'updateTime',
		        title: '修改时间',
                align: 'center'
		    },{
		        field: 'operation',
		        title: '操作'
		    },],
	        locale:'zh-CN',//中文支持,
		    responseHandler:function(res){
	            //在ajax获取到数据，渲染表格之前，修改数据源
	            return res;
	        }
		});
		
		$('#lgcbship').bootstrapTable("destroy").bootstrapTable({
	    	method: 'post',
	    	contentType: "application/x-www-form-urlencoded",
	    	url:BasePath + "/task/getTaskList?status=03",
	        dataType: 'json',
//	        dataField: 'res',//bootstrap table 可以前端分页也可以后端分页
	    	striped: true, //是否显示行间隔色
	    	pageNumber: 1, //初始化加载第一页，默认第一页
	    	pagination:true,//是否分页
	    	queryParamsType:'limit',
	    	sidePagination:'server',
	    	pageSize:10,//单页记录数
	    	pageList:[5,10,20,30],//分页步进值
            showPaginationSwitch: true,//是否显示选择分页数按钮
            showHeader:true,
	    	showRefresh:true,//刷新按钮
	        showToggle: true,//是否显示 切换试图（table/card）按钮  
	    	showColumns:true,//是否显示 内容列下拉框 
	        search: true, //显示搜索框  
	        paginationPreText: '‹',//指定分页条中上一页按钮的图标或文字,这里是<
	        paginationNextText: '›',//指定分页条中下一页按钮的图标或文字,这里是>
//	        singleSelect: false,
	    	clickToSelect: true,//是否启用点击选中行
	    	toolbarAlign:'right',
	    	buttonsAlign:'right',//按钮对齐方式
	    	toolbar:'#toolbar',//指定工作栏
/*		        queryParams: function (params) {//自定义参数，这里的参数是传给后台的，分页使用  
	            return {//这里的params是table提供的  
	                cp: params.offset,//从数据库第几条记录开始  
	                ps: params.limit//找多少条  
	            };  
	        }, */
	        idField: "id",//指定主键列  
		    columns: [{
		    	title:'全选',
                field:'select',
                //复选框
                checkbox:true,
                width:25,
                align:'center'
            },{
		        field: 'id',
		        title: 'id',
                align: 'center'
		    }, {
		        field: 'shipName',
		        title: '船名',
                align: 'center'
		    }, {
		        field: 'berthingTime',
		        title: '停靠时间',
                align: 'center'
		    }, {
		        field: 'beginTime',
		        title: '开工时间',
                align: 'center',
        		sortable:true
		    },{
		        field: 'updateUser',
		        title: '修改人',
                align: 'center'
		    },{
		        field: 'updateTime',
		        title: '修改时间',
                align: 'center'
		    },{
		        field: 'operation',
		        title: '操作'
		    },],
	        locale:'zh-CN',//中文支持,
		    responseHandler:function(res){
	            //在ajax获取到数据，渲染表格之前，修改数据源
	            return res;
	        }
		});
		
		function tableHeight() {
		    return $(window).height() - 140;
		}
		
		function add_click(){
			window.location.href = BasePath + "/task/addform"
		}
})