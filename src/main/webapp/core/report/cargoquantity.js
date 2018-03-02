	function initTable(taskId){
		
		$('#cargoquantity').bootstrapTable("destroy").bootstrapTable({  // init via javascript
			
			method : 'post',
			contentType : "application/x-www-form-urlencoded",
			url:BasePath + "/task/getUnloadProgressList?taskId=" + taskId,
			dataType : 'json',
//			 dataField: 'res',//bootstrap table 可以前端分页也可以后端分页
			 striped : true, // 是否显示行间隔色
			 pagination : false,// 是否分页
	         sortable: true,    //是否启用排序
	         sortOrder: "asc",  //排序方式			 
			 cache: false,  //是否使用缓存，默认为true
			 queryParamsType : '',
			 queryParams : 'queryParams',
			 sidePagination : 'server',
			 pageNumber : 1, // 初始化加载第一页，默认第一页			 
//			 pageSize : 10,// 单页记录数
//			 pageList : [10,20,30,50],// 分页步进值
//			 showPaginationSwitch : true,// 是否显示选择分页数按钮
			 showHeader : true,
//			 showRefresh : true,// 刷新按钮
//			 showToggle : true,// 是否显示 切换试图（table/card）按钮
//			 showColumns : true,// 是否显示 内容列下拉框
//			 queryParams: getPageMessage,
//			 search : true, // 显示搜索框
//			 paginationPreText : '上一页',// 指定分页条中上一页按钮的图标或文字,这里是<
//			 paginationNextText : '下一页',// 指定分页条中下一页按钮的图标或文字,这里是>
//			 singleSelect: true,
//			 clickToSelect : true,// 是否启用点击选中行
//			 toolbar : '#toolbar',
//			 toolbarAlign : 'right',
//			 buttonsAlign : 'left',// 按钮对齐方式
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
				};
				return params; 
			},
			 			
//			idField : "id",// 指定主键列
		    columns: [/*{
		        field: 'cargoId',
		        title: '货物编号',
		        align: 'center',
		        width: '10%'
		    },*/ {
		        field: 'cargoName',
		        title: '货物名称',
		        align: 'center',
		        width: '10%',
		        formatter: function (value, row, index) {
                    //var html = '<a href="#" data-toggle="popover" data-original-title="货物详情" class="btn btn-success pop addon">'+row.cargoName+'</a>';
                    var html = '<a href="javascript:view_click(' + row.cargoId + ')" class="btn btn-success pop addon">' + row.cargoName + '</a>';
                    return html;
                }/*,
                events: {
                    'mouseenter .addon': function (e, value, row, index) {
//                        alert(row.cargoName);
                    	$(".pop").popover({
            			    trigger: "hover focus",
            			    html: true,
                            content: function() {
                                return $('#popover_content_wrapper').html();
                            },    
            			    animation: false
            			  })
            			  .on("mouseenter", function() {
            			    var _this = this;
            			    $(this).popover("show");
            			    $(".popover").on("mouseleave", function() {
            			      $(_this).popover('hide');
            			    });
            			  }).on("mouseleave", function() {
            			    var _this = this;
            			    setTimeout(function() {
            			      if (!$(".popover:hover").length) {
            			        $(_this).popover("hide");
            			      }
            			    }, 300);
            			  });                  	
                     }
                }*/
		    }, {
		        field: 'total',
		        title: '总量',
		        align: 'center',
		        width: '10%'
/*		        formatter:function(value,row,index) {
		            return [
		                '<a class="pop" data-toggle="popover" href="#">'+row.total+"("+row.total+')</a>'
		            ]
		        }*/		        
		    }, {
		        field: 'finished',
		        title: '作业量',
		        align: 'center',
		        width: '10%',
		    },{
		        field: 'remainder',
		        title: '台时',
		        align: 'center',
		        width: '10%'
		    }, {
		        field: 'clearance',
		        title: '速率',
		        align: 'center',
		        width: '10%'
		    }],
			locale : 'zh-CN',// 中文支持,
			responseHandler : function(res) {
				// 在ajax获取到数据，渲染表格之前，修改数据源
				return res;
			}
	});
	
}
	/**
	 * 点击查看信息
	 */
	function view_click(id) {
		Cl.showModalWindow(Cl.modalName, BasePath + "/cargo/cargoview?id=" + id);
	}
	
	/**
	 * 查看船舶信息
	 */
	function view_ship(taskId) {
		window.location.href = BasePath + "/task/view?taskId="+ taskId;
	}	
