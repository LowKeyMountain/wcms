	function initTable(){
		
		$('#unloader').bootstrapTable("destroy").bootstrapTable({  // init via javascript
			
			method : 'post',
			contentType : "application/x-www-form-urlencoded",
			url:BasePath + "/unloader/getUnloaderList?rnd=" + Math.random(),
			dataType : 'json',
//			 dataField: 'res',//bootstrap table 可以前端分页也可以后端分页
			 striped : true, // 是否显示行间隔色
			 pagination : true,// 是否分页
	         sortable: true,    //是否启用排序
	         sortOrder: 'asc',  //排序方式
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
//			 showToggle : true,// 是否显示 切换试图（table/card）按钮
			 showColumns : true,// 是否显示 内容列下拉框
//			 queryParams: getPageMessage,
//			 search : true, // 显示搜索框
			 paginationPreText : '上一页',// 指定分页条中上一页按钮的图标或文字,这里是<
			 paginationNextText : '下一页',// 指定分页条中下一页按钮的图标或文字,这里是>
//			 singleSelect: true,
			 clickToSelect : true,// 是否启用点击选中行
			 toolbar : '#toolbar',
			 toolbarAlign : 'right',
			 buttonsAlign : 'left',// 按钮对齐方式
			 showExport : true, // 是否显示导出
			 exportDataType : 'basic', // 'basic', 'all', 'selected'.
//			 Icons:'glyphicon glyphicon-export', //导出图标
			 exportTypes:['excel'],  //导出文件类型 'csv', 'txt', 'sql', 'doc', 'excel', 'xlsx', 'pdf'
/*			 exportOptions:{
				    // ignoreColumn: [0,1],  //忽略某一列的索引
				 fileName: questionNaireName,  //文件名称设置
				 worksheetName: 'sheet1',  //表格工作区名称
				 tableName: questionNaireName,
				 // excelstyles: ['background-color', 'color', 'font-size', 'font-weight'], 设置格式
			 },*/
			
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
						 operationType: $("#operationType").val()
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
		        align: 'center',
		        width: '10%'
		    }, {
		        field: 'operationType',
		        title: '操作类型',
		        align: 'center',
		        width: '8%',
                formatter: function (value, row, index) {//自定义显示，这三个参数分别是：value该行的属性，row该行记录，index该行下标  
                    return row.operationType == 0 ? "<font color=grey>位移</font>" : row.operationType == 1 ? "<font color=red>作业</font>" : "<font color=lightgreen>在线</font>";  
                } 
		    }, {
		        field: 'time',
		        title: '操作时间',
		        align: 'center',
		        width: '16%',
		        sortable:true
		    }, {
		        field: 'pushTime',
		        title: '推送时间',
		        align: 'center',
		        width: '16%',
		        sortable:true
		    },/*{
		        field: 'direction',
		        title: '方向',
		        align: 'center'
		    }, */{
		        field: 'unloaderMove',
		        title: '卸船机移动位置',
		        align: 'center',
		        width: '12%'
		    }, {
		        field: 'OneTask',
		        title: '一次抓钩作业量',
		        align: 'center',
		        width: '12%'
		    }],
			locale : 'zh-CN',// 中文支持,
			responseHandler : function(res) {
				// 在ajax获取到数据，渲染表格之前，修改数据源
				return res;
			}		
	});
		
	}

$(function(){
		initTable();
		$("#operationtype").change(function(){ 
			if($('#operationtype').val() == "0"){
	       		$('#onetask_div').hide();
			}else{
	       		$('#onetask_div').show();
			}
		}) 		
		//查询按钮
        $("#btn_query").off().on(
        		"click",function(){
        			//$('#unloader').bootstrapTable('refresh');     
        			initTable();
        })

        $("#btn_add").off().on(
        		"click",function(){
         	    $('#addForm')[0].reset();
	       		$('#onetask_div').show();
        })
              
        //重置按钮事件  
        $("#btn_reset").off().on("click",function(){  
            $("#startDate").val("");
            $("#endDate").val("");
            $("#cmsid").val("");
            $("#startPosition").val("");
            $("#endPosition").val("");
            $("#operationType").val("");
    		$("#startDate").datetimepicker('setStartDate', null);
    		$("#startDate").datetimepicker('setEndDate', new Date());
    		$("#endDate").datetimepicker('setStartDate', null);
    		$("#endDate").datetimepicker('setEndDate', new Date());

        });
        $('#btn_submit').modal({backdrop: 'static', show:false,  keyboard: false});
        
        $('#btn_submit').off().on("click", function () {
        	if ($("#operationtime").val() == ""){
        		alert('请选择操作时间！');
        		return;
        	}
        	var data={
        			fcmsid:$("#fcmsid").val(),
        			operationtype:$("#operationtype").val(),
        			direction:$("#direction").val(),
        			operationtime:$("#operationtime").val(),
        			move:$("#move").val(),			             
        			onetask: $("#onetask").val()
        	}
            $.ajax({
                url: BasePath + "/unloader/addUnloader",
                type: "post",
                dataType: "json",
                cache: false,
                async:false,
                data: data,
                success: function (data) {
                	if (data.success == true){
                		alert(data.msg);
                    	$('#unloader').bootstrapTable("refresh");
                	} else {
                		alert(data.msg);                		
                	}
                },
                failure: function(data){
                    alert("保存失败!");
                }                
            });
        });        
});

/*var TableDatatablesButtons = function () {

    var initTable1 = function () {
        var table = $('#unloader');

        var oTable = table.dataTable({

            // Internationalisation. For more info refer to http://datatables.net/manual/i18n
            "language": {
                "aria": {
                    "sortAscending": ": activate to sort column ascending",
                    "sortDescending": ": activate to sort column descending"
                },
                "emptyTable": "No data available in table",
                "info": "Showing _START_ to _END_ of _TOTAL_ entries",
                "infoEmpty": "No entries found",
                "infoFiltered": "(filtered1 from _MAX_ total entries)",
                "lengthMenu": "_MENU_ entries",
                "search": "Search:",
                "zeroRecords": "No matching records found"
            },

            // Or you can use remote translation file
            //"language": {
            //   url: '//cdn.datatables.net/plug-ins/3cfcc339e89/i18n/Portuguese.json'
            //},


            buttons: [
                //{ extend: 'print', className: 'btn dark btn-outline' },
                { extend: 'copy', className: 'btn red btn-outline' },
                { extend: 'pdf', className: 'btn green btn-outline' },
                { extend: 'excel', className: 'btn yellow btn-outline ' },
                { extend: 'csv', className: 'btn purple btn-outline ' },
                { extend: 'colvis', className: 'btn dark btn-outline', text: 'Columns'}
            ],

            // setup responsive extension: http://datatables.net/extensions/responsive/
            responsive: true,

            //"ordering": false, disable column ordering 
            //"paging": false, disable pagination

            "order": [
                [0, 'asc']
            ],
            
            "lengthMenu": [
                [5, 10, 15, 20, -1],
                [5, 10, 15, 20, "All"] // change per page values here
            ],
            // set the initial value
            "pageLength": 10,

            "dom": "<'row' <'col-md-12'B>><'row'<'col-md-6 col-sm-12'l><'col-md-6 col-sm-12'f>r><'table-scrollable't><'row'<'col-md-5 col-sm-12'i><'col-md-7 col-sm-12'p>>", // horizobtal scrollable datatable

            // Uncomment below line("dom" parameter) to fix the dropdown overflow issue in the datatable cells. The default datatable layout
            // setup uses scrollable div(table-scrollable) with overflow:auto to enable vertical scroll(see: assets/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.js). 
            // So when dropdowns used the scrollable div should be removed. 
            //"dom": "<'row' <'col-md-12'T>><'row'<'col-md-6 col-sm-12'l><'col-md-6 col-sm-12'f>r>t<'row'<'col-md-5 col-sm-12'i><'col-md-7 col-sm-12'p>>",
        });
    }
    var initAjaxDatatables = function () {

        //init date pickers
        $('.date-picker').datepicker({
            rtl: App.isRTL(),
            autoclose: true
        });

        var grid = new Datatable();

        grid.init({
            src: $("#datatable_ajax"),
            onSuccess: function (grid, response) {
                // grid:        grid object
                // response:    json object of server side ajax response
                // execute some code after table records loaded
            },
            onError: function (grid) {
                // execute some code on network or other general error  
            },
            onDataLoad: function(grid) {
                // execute some code on ajax data load
            },
            loadingMessage: 'Loading...',
            dataTable: { // here you can define a typical datatable settings from http://datatables.net/usage/options 

                // Uncomment below line("dom" parameter) to fix the dropdown overflow issue in the datatable cells. The default datatable layout
                // setup uses scrollable div(table-scrollable) with overflow:auto to enable vertical scroll(see: assets/global/scripts/datatable.js). 
                // So when dropdowns used the scrollable div should be removed. 
                
                //"dom": "<'row'<'col-md-8 col-sm-12'pli><'col-md-4 col-sm-12'<'table-group-actions pull-right'>>r>t<'row'<'col-md-8 col-sm-12'pli><'col-md-4 col-sm-12'>>",
                
                "bStateSave": true, // save datatable state(pagination, sort, etc) in cookie.

                "lengthMenu": [
                    [10, 20, 50, 100, 150, -1],
                    [10, 20, 50, 100, 150, "All"] // change per page values here
                ],
                "pageLength": 10, // default record count per page
                "ajax": {
                    "url": "../demo/table_ajax.php", // ajax source
                },
                "order": [
                    [1, "asc"]
                ],// set first column as a default sort by asc
            
                // Or you can use remote translation file
                //"language": {
                //   url: '//cdn.datatables.net/plug-ins/3cfcc339e89/i18n/Portuguese.json'
                //},

                buttons: [
                    { extend: 'print', className: 'btn default' },
                    { extend: 'copy', className: 'btn default' },
                    { extend: 'pdf', className: 'btn default' },
                    { extend: 'excel', className: 'btn default' },
                    { extend: 'csv', className: 'btn default' },
                    {
                        text: 'Reload',
                        className: 'btn default',
                        action: function ( e, dt, node, config ) {
                            dt.ajax.reload();
                            alert('Datatable reloaded!');
                        }
                    }
                ],

            }
        });

        // handle group actionsubmit button click
        grid.getTableWrapper().on('click', '.table-group-action-submit', function (e) {
            e.preventDefault();
            var action = $(".table-group-action-input", grid.getTableWrapper());
            if (action.val() != "" && grid.getSelectedRowsCount() > 0) {
                grid.setAjaxParam("customActionType", "group_action");
                grid.setAjaxParam("customActionName", action.val());
                grid.setAjaxParam("id", grid.getSelectedRows());
                grid.getDataTable().ajax.reload();
                grid.clearAjaxParams();
            } else if (action.val() == "") {
                App.alert({
                    type: 'danger',
                    icon: 'warning',
                    message: 'Please select an action',
                    container: grid.getTableWrapper(),
                    place: 'prepend'
                });
            } else if (grid.getSelectedRowsCount() === 0) {
                App.alert({
                    type: 'danger',
                    icon: 'warning',
                    message: 'No record selected',
                    container: grid.getTableWrapper(),
                    place: 'prepend'
                });
            }
        });

        //grid.setAjaxParam("customActionType", "group_action");
        //grid.getDataTable().ajax.reload();
        //grid.clearAjaxParams();

        // handle datatable custom tools
        $('#datatable_ajax_tools > li > a.tool-action').on('click', function() {
            var action = $(this).attr('data-action');
            grid.getDataTable().button(action).trigger();
        });
    }

    return {

        //main function to initiate the module
        init: function () {

            if (!jQuery().dataTable) {
                return;
            }

            initTable1();
            initAjaxDatatables();
        }

    };

}(); */   