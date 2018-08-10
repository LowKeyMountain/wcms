	function initTable(taskId){
		
		$('#unshipInfo').bootstrapTable("destroy").bootstrapTable({  // init via javascript
			
			method : 'post',
			contentType : "application/x-www-form-urlencoded",
			url:BasePath + "/task/getUnshipInfoList?taskId=" + taskId,
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
			 pageSize : 10,// 单页记录数
//			 pageList : [10,20,30,50],// 分页步进值
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
			 showFooter: true,
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
		    columns: [{
		        field: 'cabinNo',
		        title: '船舱号',
		        align: 'center',
		        width: '6%',
		        formatter: function (value, row, index) {
                    var html = '<a href="javascript:view_unship_click(' + taskId + ',' + row.cabinNo + ')" class="font-weight-normal">' + row.cabinNo + '</a>';
                    return html;
                }
		    }, {
		        field: 'cargoName',
		        title: '货名',
		        align: 'center',
		        width: '12%',
		        formatter: function (value, row, index) {
                    //var html = '<a href="#" data-toggle="popover" data-original-title="货物详情" class="btn btn-success pop addon">'+row.cargoName+'</a>';
                    var html = '<a href="javascript:view_click(' + taskId + ',' + row.cabinNo + ')" class="font-weight-normal">' + row.cargoName + '</a>';
                    return html;
                },
                footerFormatter: '合计'
		    }, {
		        field: 'total',
		        title: '总量',
		        align: 'center',
		        width: '10%',
	            footerFormatter: function (value) {
	                var count = 0;
	                for (var i in value) {
	                	count += value[i].total;
	                }
	                return count.toFixed(1);
	            }	        
		    }, {
		        field: 'finishedBeforeClearance',
		        title: '清舱前',
		        align: 'center',
		        width: '10%',
	            footerFormatter: function (value) {
	                var count = 0;
	                for (var i in value) {
	                	count += value[i].finishedBeforeClearance;
	                }
	                return count.toFixed(1);
	            }		        
		    }, {
		        field: 'clearance',
		        title: '清舱量',
		        align: 'center',
		        width: '10%',
	            footerFormatter: function (value) {
	                var count = 0;
	                for (var i in value) {
	                	count += value[i].clearance;
	                }
	                return count.toFixed(1);
	            } 
		    }, {
		        field: 'finished',
		        title: '完成量',
		        align: 'center',
		        width: '10%',
	            footerFormatter: function (value) {
	                var count = 0;
	                for (var i in value) {
	                	count += value[i].finished;
	                }
	                return count.toFixed(1);
	            }		        
		    },{
		        field: 'remainder',
		        title: '剩余量',
		        align: 'center',
		        width: '10%',
	            footerFormatter: function (value) {
	                var count = 0;
	                for (var i in value) {
	                	count += value[i].remainder;
	                }
	                return count.toFixed(1);
	            }        
		    },{
		        field: 'status',
		        title: '状态',
		        align: 'center',
		        width: '8%',
                formatter: function (value, row, index) {//自定义显示，这三个参数分别是：value该行的属性，row该行记录，index该行下标  
                    return row.status == 0 ? "<font color=red>卸货</font>" : row.status == 1 ? "<font color=lightgreen>清舱</font>" : "<font color=grey>未知</font>";  
                }
		    },{
		        field: 'clearTime',
		        title: '清舱时间',
		        align: 'center',
		        width: '12%' 
		    },{
		        title: '操作',
		        align: 'center',
		        width: '16%',
		        formatter: function (value, row, index) {
					var authorize_11 = authorize('11');
		        	if(row.status == 0 && authorize_11){
			            return '<a class="btn mod default" ><i class="fa fa-pencil-square-o"></i>清舱</a>';
		        	} else {
		        		return '';
		        	}
		        },
		    	events: {
					'click .mod' : function(e, value, row, index) {
			     	    $('#qcForm')[0].reset();
						$('#taskId').val(taskId);
						$('#cabinNo').val(row.cabinNo);
						$('#status').val(row.status);
						$('#cabinStatus').modal('show');
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
	 * 点击增加按钮
	 */
	function add_click() {
		if (taskId == null || taskId == undefined) {
			alert("请先录入船舶信息！");
			return;
		}
		Cl.action = 'create';
		Cl.showModalWindow(Cl.modalName, BasePath + "/cabin/addform?taskId=" + taskId);
	}
	
	/**
	 * 点击修改按钮
	 */
	function update_click(id) {
		Cl.action = 'update';
		Cl.showModalWindow(Cl.modalName, BasePath + "/cabin/updateform?id="
				+ id);
	}
	/**
	 * 点击查看信息
	 */
	function view_click(taskId, cabinNo) {
		Cl.showModalWindow(Cl.modalName, BasePath + "/cargo/view?taskId="
				+ taskId + '&cabinNo=' + cabinNo);
	}
	/**
	 * 点击查看信息
	 */
	function modifyCabinPosition_click(taskId) {
		window.location.href = BasePath + "/cabin/modifyCabinPosition?taskId="
				+ taskId;
	}
	/**
	 * 点击查看船舶信息
	 */
	function view_ship_click(taskId) {
		window.location.href = BasePath + "/task/view?taskId="
				+ taskId;
	}
	/**
	 * 点击查看卸船信息
	 */
	function view_unship_click(taskId, cabinNo) {
		window.location.href = BasePath + "/cabin/view?taskId="
				+ taskId + '&cabinNo=' + cabinNo;
	}
	
	/**
	 * 卸船进度
	 */
	function unloadProgress_click(id) {
		window.location.href = BasePath + "/task/unloadProgress?id=" + id;
	}
	
	/**
	 * 卸船机总览
	 */
	function unloaderOverview_click(id) {
		window.location.href = BasePath + "/task/unloaderOverview?id=" + id;
	}	
	/**
	 * 点击设置船舶状态
	 */
	function setShipStatus_click(taskId, status) {
 	    $('#wgForm')[0].reset();			
		$('#taskId').val(taskId);
		$('#status').val(status);
		$('#taskStatus').modal('show');
/*			var con = confirm(status == '0' ? '请确认是否开始卸船！' : (status == '1' ? '请确认是否完成卸船！' : ""));
			if (con == true) {
				var pageParam = {};
				pageParam.taskId = taskId;
				pageParam.status = status;
				var url = BasePath + "/task/doSetShipStatus";
				$.post(url, pageParam, function(result) {
					if (result && Cl.successInt == result.code) {
						alert(result.msg);
						if (status == '1') {
							window.location.href = BasePath + "/task/tasklist?type=2";
						} else {
							window.location.href = BasePath + "/task/tasklist";
						}
					} else {
						alert(result.msg);
					}
				});
			}*/
	}
	/**
	 * 点击设置船舱状态
	 */
	function setCabinStatus_click(taskId, cabinNo, status) {
 	    $('#qcForm')[0].reset();
		$('#taskId').val(taskId);
		$('#cabinNo').val(cabinNo);
		$('#status').val(status);
		$('#cabinStatus').modal('show');
/*			var con = confirm(status == '1' ? '请确认是否清舱！！' : (status == '2' ? '请确认是否完成！！' : ""));
			if (con == true) {
				//（0|卸货;1|清舱;2|完成）
				var pageParam = {};
				pageParam.taskId = taskId;
				pageParam.status = status;
				pageParam.cabinNo = cabinNo;
				var url = BasePath + "/cabin/updateCabinStatus";
				$.post(url, pageParam, function(result) {
					if (result && Cl.successInt == result.code) {
						alert(result.msg);
						UnshipInfo.list();
					} else {
						alert(result.msg);
					}
				});
			}*/
	}