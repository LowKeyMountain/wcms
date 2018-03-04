var FormCl = function () {
    // validation using icons
    var handleValidation = function() {
        // for more info visit the official plugin documentation: 
        // http://docs.jquery.com/Plugins/Validation

        var form2 = $('#form_cl');
        var error2 = $('.alert-danger', form2);
        var success2 = $('.alert-success', form2);
        
        form2.validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block help-block-error', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "",  // validate all fields including form hidden input
            rules : {
				'ship.shipName' : {
					required : true
				},
				'ship.shipEnName' : {
					required : true
				},
				'enterPortTime' : {
					required : true,
					date:true
				},
				'berth' : {
					required : true,
					range : [ 1, 2 ],
					remote : {
						type : "post",
						url : BasePath + "/task/berthCheckout",
						data : {
							id : function() {
								return $("#id").val();
							},
							berth:function() {
								return $("#berth").val();
							}
						},
						dataType : "json",
						dataFilter : function(data, type) {
							var data = jQuery.parseJSON(data);
							 if(Cl.successInt == data.code){
								return true;
							} else {
								alert(data.msg);
								return false;
							}
						}
					}
				},
				'cargoLoad' : {
					required : true,
					number : true
				},
				'ship.imoNo' : {
				},
				'ship.buildDate' : {
					// date:true
				},
				'ship.length' : {
//					required : true,
					number : true
				},
				'ship.breadth' : {
//					required : true,
					number : true
				},
				'ship.mouldedDepth' : {
//					required : true,
					number : true
				},
				'ship.cabinNum' : {
					required : true,
					range:[1,20]
				},
				'ship.hatch' : {
					required : true
				}
			}, 
            invalidHandler: function (event, validator) { // display error
															// alert on form
															// submit
                success2.hide();
                error2.show();
                App.scrollTo(error2, -200);
            },

            errorPlacement: function (error, element) { // render error placement for each input type
                var icon = $(element).parent('.input-icon').children('i');
                icon.removeClass('fa-check').addClass("fa-warning");  
//                icon.attr("data-original-title", error.text()).tooltip({'container': 'body'}); // Bux: 解决tooltip提示信息，能显示在模式modal框中，加入参数{'container': 'body'}，提示信息显示在父页面上。
                icon.attr("data-original-title", error.text()).tooltip();
            },

            highlight: function (element) { // hightlight error inputs
                $(element)
                    .closest('.form-group').removeClass("has-success").addClass('has-error'); // set error class to the control group   
            },

            unhighlight: function (element) { // revert the change done by hightlight
                
            },

            success: function (label, element) {
                var icon = $(element).parent('.input-icon').children('i');
                $(element).closest('.form-group').removeClass('has-error').addClass('has-success'); // set success class to the control group
                icon.removeClass("fa-warning").addClass("fa-check");
            },

            submitHandler: function (form) {
                success2.show();
                error2.hide();
                var name = $(form).attr("name")
                if (name == 'form_cl_add') {
                	Task.add();
                } else if (name == 'form_cl_update') {
                	Task.update();
                }
            }
        });
    }
    
    var handleWysihtml5 = function() {
        if (!jQuery().wysihtml5) {            
            return;
        }
        if ($('.wysihtml5').size() > 0) {
            $('.wysihtml5').wysihtml5({
                "stylesheets": [ IncPath+ "/assets/global/plugins/bootstrap-wysihtml5/wysiwyg-color.css"]
            });
        }
    }
    return {
        //main function to initiate the module
        init: function () {
            handleWysihtml5();
            handleValidation();
        },
        formName: "form_cl"
    };
}();

var Task = function() {
	return {
		/**
		 * 设置船舶列表参数
		 */
		options : function(status) {

			var options = {};
			if (status == null || status == undefined) {
				return options;
			}
			options = {
				method : 'post',
				contentType : "application/x-www-form-urlencoded",
				url : BasePath + "/task/getTaskList?status=" + status,
				dataType : 'json',
				 // dataField: 'res',//bootstrap table 可以前端分页也可以后端分页
				 striped : true, // 是否显示行间隔色
				 pageNumber : 1, // 初始化加载第一页，默认第一页
//				 pagination : false,// 是否分页
		         sortable: true,    //是否启用排序
		         sortOrder: "asc",  //排序方式
				 queryParamsType : '',
				 queryParams : 'queryParams',
				 sidePagination : 'server',
				 cache: false,  //是否使用缓存，默认为true
				 pageSize : 10,// 单页记录数
				 pageList : [ 10, 20, 30, 50],// 分页步进值
//				 showPaginationSwitch : true,// 是否显示选择分页数按钮
//				 showHeader : true,
//				 showRefresh : true,// 刷新按钮
				 // showToggle : true,// 是否显示 切换试图（table/card）按钮
				 // showColumns : true,// 是否显示 内容列下拉框
				 // queryParams: getPageMessage,
//				 search : true, // 显示搜索框
				 paginationPreText : '上一页',// 指定分页条中上一页按钮的图标或文字,这里是<
				 paginationNextText : '下一页',// 指定分页条中下一页按钮的图标或文字,这里是>
				 // singleSelect: false,
				 // clickToSelect : true,// 是否启用点击选中行
//				 toolbarAlign : 'right',
//				 buttonsAlign : 'right',// 按钮对齐方式
				 showExport : true, // 是否显示导出
				 exportDataType : "basic", // basic', 'all', 'selected'.
				 
/*				 queryParams: function queryParams(params){//自定义参数，这里的参数是传给后台的，分页使用
						var params = {//这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
								 limit: params.pageSize,
								 offset: params.pageNumber,
					             sortName: params.sortName,
					             sortOrder: params.sortOrder,			             

						};
						return params; 
				 },*/
				
				idField : "id",// 指定主键列
//				columns : [ {
//					title : '全选',
//					field : 'select',
//					// 复选框
//					checkbox : true,
//					width : 25,
//					align : 'center'
//				}, {
//					field : 'id',
//					title : 'ID',
//					align : 'center'
//				}, {
//					field : 'berth',
//					title : '泊位',
//					align : 'center'
//				}, {
//					field : 'shipName',
//					title : '船名',
//					align : 'center'
//				}, {
//					field : 'berthingTime',
//					title : '靠泊时间',
//					align : 'center'
//				}, {
//					field : 'beginTime',
//					title : '开工时间',
//					align : 'center',
//					sortable : true
//				}, {
//					field : 'endTime',
//					title : '完工时间',
//					align : 'center'
//				}, {
//					field : 'departureTime',
//					title : '离泊时间',
//					align : 'center'
//				}, {
//					field : 'operation',
//					title : '操作'
//				} ],
				locale : 'zh-CN',// 中文支持,
				responseHandler : function(res) {
					// 在ajax获取到数据，渲染表格之前，修改数据源
					return res;
				}
			}
			var pagination;
			if (status == '0') {
				pagination = false;
			} else if (status == '1') {
				pagination = false;
			} else if (status == '2') {
				pagination = true;
			}
			
			var queryParams;
			if (status == '0') {
				queryParams = function queryParams(params){//自定义参数，这里的参数是传给后台的，分页使用
					var params = {//这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
//							 limit: params.pageSize,
//							 offset: params.pageNumber,
//				             sortName: params.sortName,
//				             sortOrder: params.sortOrder,			             

					};
					return params; 
			 };
			} else if (status == '1') {
				queryParams = function queryParams(params){//自定义参数，这里的参数是传给后台的，分页使用
					var params = {//这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
//							 limit: params.pageSize,
//							 offset: params.pageNumber,
//				             sortName: params.sortName,
//				             sortOrder: params.sortOrder,			             

					};
					return params; 
			 };
			} else if (status == '2') {
				queryParams = function queryParams(params){//自定义参数，这里的参数是传给后台的，分页使用
					var params = {//这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
							 limit: params.pageSize,
							 offset: params.pageNumber,
				             sortName: params.sortName,
				             sortOrder: params.sortOrder,			             

					};
					return params; 
			 };
			}				
			
			var columns = [];

			if (status == '0') {
				columns = [ 
//				            {
//					title : '全选',
//					field : 'select',
//					// 复选框
//					checkbox : true,
//					width : 25,
//					align : 'center'
//				}, 
//				{
//					field : 'id',
//					title : 'ID',
//					align : 'center'
//				}, 
				{
					field : 'berth',
					title : '泊位',
					align : 'center'
				}, {
					field : 'shipName',
					title : '船名',
					align : 'center'
				}, {
					field : 'enterPortTime',
					title : '预靠时间',
					align : 'center'
				}, {
					field : 'operation',
					title : '操作'
				} ];

			} else if (status == '1') {
				columns = [ 
//				            {
//					title : '全选',
//					field : 'select',
//					// 复选框
//					checkbox : true,
//					width : 25,
//					align : 'center'
//				},
//				             {
//					field : 'id',
//					title : 'ID',
//					align : 'center'
//				},
				{
					field : 'berth',
					title : '泊位',
					align : 'center'
				}, {
					field : 'shipName',
					title : '船名',
					align : 'center'
				}, {
					field : 'enterPortTime',
					title : '预靠时间',
					align : 'center'
				}, {
					field : 'berthingTime',
					title : '靠泊时间',
					align : 'center'
				}, {
					field : 'beginTime',
					title : '开工时间',
					align : 'center',
					sortable : true
				}, 
//				{
//					field : 'endTime',
//					title : '完工时间',
//					align : 'center'
//				},
				{
					field : 'operation',
					title : '操作'
				} ];

			} else if (status == '2') {
				columns = [
//				           {
//					title : '全选',
//					field : 'select',
//					// 复选框
//					checkbox : true,
//					width : 25,
//					align : 'center'
//				}, 
//				{
//					field : 'id',
//					title : 'ID',
//					align : 'center'
//				}, 
				{
					field : 'berth',
					title : '泊位',
					align : 'center'
				}, {
					field : 'shipName',
					title : '船名',
					align : 'center'
				}, {
					field : 'enterPortTime',
					title : '预靠时间',
					align : 'center'
				}, {
					field : 'berthingTime',
					title : '靠泊时间',
					align : 'center'
				}, {
					field : 'beginTime',
					title : '开工时间',
					align : 'center',
					sortable : true
				}, {
					field : 'endTime',
					title : '完工时间',
					align : 'center'
				}, {
					field : 'departureTime',
					title : '离泊时间',
					align : 'center'
				}, {
					field : 'operation',
					title : '操作'
				}  ];

			}
			options.columns = columns;
			options.pagination = pagination;
			options.queryParams = queryParams;
//			if (status == '0') {
//				options.toolbar = '#toolbar';// 指定工作栏
//			}

			return options;
		},
		/**
		 * 点击增加按钮
		 */
		add_click : function() {
			var url = BasePath + "/task/newCalibration";
			$.post(url, null, function(result) {
				if (result && result.success == 'success') {
					Cl.action = 'create';
					window.location.href = BasePath + "/task/addform";
				} else {
					alert(result.msg);
				}
			});
		},
		/**
		 * 点击修改按钮
		 */
		update_click : function(id) {
			Cl.action = 'update';
			window.location.href = BasePath + "/task/updateform?id=" + id;
		},
		/**
		 * 增加
		 */
		add : function() {
			/*
			 * option的参数 var options = { target: '#output1', // target
			 * element(s) to be updated with server response beforeSubmit:
			 * showRequest, // pre-submit callback success: showResponse //
			 * post-submit callback // other available options: //url: url //
			 * override for form's 'action' attribute //type: type // 'get' or
			 * 'post', override for form's 'method' attribute //dataType: null //
			 * 'xml', 'script', or 'json' (expected server response type)
			 * //clearForm: true // clear all form fields after successful
			 * submit //resetForm: true // reset the form after successful
			 * submit // $.ajax options can be used here too, for example:
			 * //timeout: 3000 };
			 */
			
			if (taskId != null && taskId != '' ) {
				Task.update();
				return;
			}
			
			var options = {
				target : '#form_cl',
				type : 'post',
				dataType : 'json',
				url : BasePath + "/task/add",
				success : function(result) {
					if (!result)
						return;
					 var code = result.code;
					 if(Cl.successInt == code){
						taskId = result.taskId;
						$("#id").attr('value', result.taskId);
						alert("增加成功");
					} else {
						alert("增加失败");
						return;
					}
				},
				error : function(result) {
					alert("系统异常，增加失败！");
				}
//				,
//				clearForm : true,
//				resetForm : true,
//				timeout : 3000
			};
			$("#form_cl").ajaxSubmit(options);
		},
		/**
		 * 修改
		 */
		update : function() {
			var options = {
				target : '#form_cl',
				type : 'post',
				dataType : 'json',
				url : BasePath + "/task/update",
				success : function(result) {
					if (!result)
						return;
					 var code = result.code;
					 if(Cl.successInt == code){
						alert("修改成功");
						taskId = result.taskId;
						$("#id").attr('value', result.taskId);
					} else {
						alert("修改失败");
						return;
					}
				},
				error : function(result) {
					alert("系统异常，修改失败！");
				}
//				,
//				clearForm : true,
//				resetForm : true,
//				timeout : 3000
			};
			$("#form_cl").ajaxSubmit(options);
		},
		/**
		 * 卸船情况
		 */
		unshipInfo_click : function(id) {
			window.location.href = BasePath + "/task/unshipInfo?id=" + id;
		},
		
		/**
		 * 卸船进度
		 */
		unloadProgress_click : function(id) {
			window.location.href = BasePath + "/task/unloadProgress?id=" + id;
		},
		
		/**
		 * 卸船机总览
		 */
		unloaderOverview_click : function(id) {
			window.location.href = BasePath + "/task/unloaderOverview?id=" + id;
		},
		
		/**
		 * 删除
		 */
		remove : function(id) {
			if (!confirm("确定要删除该船舶?")) {
				return;
			}
			var url = BasePath + "/task/delete";
			var data = {
				"id" : id
			};
			Cl.ajaxRequest(url, data, function(result) {
				if (!result)
					return;
				 var code = result.code;
				 if(Cl.successInt == code){
					alert("删除成功");
					loadList('0');
				} else {
					alert(result.msg);
					return;
				}
			});
		},
		returnList:function(){
			window.location.href = BasePath + "/task/tasklist";
		},
		/**
		 * 点击查看信息
		 */
		modifyCabinPosition_click : function(taskId) {
			window.location.href = BasePath + "/cabin/modifyCabinPosition?taskId="
					+ taskId;
		},
		/**
		 * 点击设置船舶状态
		 */
		setShipStatus_click : function(taskId, status) {
			var con = confirm(status == '0' ? '请确认是否船舶靠泊！' : (status == '1' ? '请确认是否完成卸船！' : ""));
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
			}
		},
		/**
		 * 点击查看船舶信息
		 */
		view_ship_click : function(taskId) {
			window.location.href = BasePath + "/task/view?taskId="
					+ taskId;
		}

	};
}();

function loadList(status){
	if (status == '0') {
		$('#ykcbship').bootstrapTable("destroy").bootstrapTable(Task.options('0'));
	} else if (status == '1') {
		$('#zycbship').bootstrapTable("destroy").bootstrapTable(Task.options('1'));
	} else if (status == '2') {
		$('#lgcbship').bootstrapTable("destroy").bootstrapTable(Task.options('2'));
	}
}


