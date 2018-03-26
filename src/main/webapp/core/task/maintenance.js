	function initTable(){
		
		$('#maintenance').bootstrapTable("destroy").bootstrapTable({  // init via javascript
			
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
			 pageList : [10,20,30,50],// 分页步进值
//			 showPaginationSwitch : true,// 是否显示选择分页数按钮
			 showHeader : true,
			 showRefresh : true,// 刷新按钮
			 // showToggle : true,// 是否显示 切换试图（table/card）按钮
			 showColumns : true,// 是否显示 内容列下拉框
//			 queryParams: getPageMessage,
//			 search : true, // 显示搜索框
			 paginationPreText : '上一页',// 指定分页条中上一页按钮的图标或文字,这里是<
			 paginationNextText : '下一页',// 指定分页条中下一页按钮的图标或文字,这里是>
			 singleSelect: true,
			 clickToSelect : true,// 是否启用点击选中行
			 toolbar : '#toolbar',
			 toolbarAlign : 'right',
			 buttonsAlign : 'left',// 按钮对齐方式
			 showExport : true, // 是否显示导出
			 exportDataType : "basic", // basic', 'all', 'selected'.
			
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
			             startDepartureDate:$("#startDepartureDate").val(),
			             endDepartureDate:$("#endDepartureDate").val(),
			             startBerthDate:$("#startBerthDate").val(),
			             endBerthDate:$("#endBerthDate").val(),			             
//						 cmsId: $("#cmsid").val(),
						 status: $("#status").val()
				};
				return params; 
			},
			 
			
			idField : "id",// 指定主键列
		    columns: [
/*		    {
		    	title:'全选',
		        field:'select',
		        //复选框
		        checkbox:true,
		        width:25,
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
		        width: '12%',
		        sortable:true
		    }, {
		        field: 'departureTime',
		        title: '离泊时间',
		        align: 'center',
		        width: '12%',
		        sortable:true
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
		    }, /*{
		        field: 'updateUser',
		        title: '修改人',
		        align: 'center',
		        width: '10%'
		    }, {
		        field: 'Date',
		        title: '更新时间',
		        align: 'center',
		        width: '10%'
		    },*/ {
		        title: '修改',
		        align: 'center',
		        width: '16%',
		        formatter: function (value, row, index) {
		            return ['<a class="btn cabindetail default" ><i class="fa fa-search"></i>船舱</a>',
		            	'<a class="btn mod default" ><i class="fa fa-pencil-square-o"></i>船舶</a>'].join('');
		        },
		    	events: {
					'click .mod' : function(e, value, row, index) {
	                	if(row.status==0 || row.status==2){
	                		$("#modiStatus").empty();
	                		$("#modiStatus").append("<option value='1'>作业中</option>");
							$('#begintime-div').show();
							$('#endtime-div').hide();
	                	}else if(row.status==1){
	                		$("#modiStatus").empty();
	                		$("#modiStatus").append("<option value='0'>预靠</option>").append("<option value='2'>已离港</option>");
							if($("#modiStatus").val() == 0){
								$('#begintime-div').hide();
								$('#endtime-div').hide();
							}else{
								$('#endtime-div').show();
							}
	                	}else{
	                	}
						$('#taskId').val(row.id);
						$('#shipStatus').modal('show');
					},
	                'click .cabindetail' : function(e, value, row, index) {
	        			window.location.href = BasePath + "/task/cabinlist?id=" + row.id;
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
$(function(){
		initTable();
		
	
		//查询按钮
        $("#btn_query").off().on(
        		"click",function(){
        			//$('#unloader').bootstrapTable('refresh');     
        			initTable();
        })
        //重置按钮事件  
        $("#btn_reset").off().on("click",function(){
            $("#startDepartureDate").val("");
            $("#endDepartureDate").val("");
            $("#startBerthDate").val("");
            $("#endBerthDate").val("");
            $("#status").val("");
    		$("#startDepartureDate").datetimepicker('setStartDate', null);
    		$("#startDepartureDate").datetimepicker('setEndDate', new Date());
    		$("#endDepartureDate").datetimepicker('setStartDate', null);
    		$("#endDepartureDate").datetimepicker('setEndDate', new Date());
    		
    		$("#startBerthDate").datetimepicker('setStartDate', null);
    		$("#startBerthDate").datetimepicker('setEndDate', new Date());
    		$("#endBerthDate").datetimepicker('setStartDate', null);
    		$("#endBerthDate").datetimepicker('setEndDate', new Date());
        });
        
        $("#mod").off().on(
        		"click",function(){
         	    $('#addForm')[0].reset();
        })
        
        $('#btn_submit').modal({backdrop: 'static', show:false,  keyboard: false});

        $('#btn_submit').off().on("click", function () {
        	
        	if($("#modiStatus").val()==1 && $("#beginTime").val()==""){
				alert("请选择开工时间！");
				return;        		
        	}
        	if($("#modiStatus").val()==2 && $("#endTime").val()==""){
				alert("请选择完工时间！");
				return;        		
        	}
        	var data={
        			status:$("#modiStatus").val(),
        			taskId:$("#taskId").val(),
        			endTime:$("#endTime").val(),
        			beginTime:$("#beginTime").val(),        			
        	}
            $.ajax({
                url: BasePath + "/task/doModifyShipStatus",
                type: "post",
                dataType: "json",
                cache: false,
                async:false,
                data: data,
                success: function (data) {
                	if (data.success == true){
                		alert(data.msg);
                	} else {
                		alert(data.msg);                		
                	}
            		initTable();              	
                },
                failure: function(data){
                    alert("修改失败!");
                }                
            });
        });        
});