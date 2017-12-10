
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
            rules: {
            	'ship.shipName': {
                    minlength: 5,
                    required: true
                },
                'ship.length': {
                    required: true
                },
                'ship.breadth': {
                    required: true
                },
                'ship.mouldedDepth': {
                    required: true
                },
                cargoLoad: {
                    required: true
                },
                berth: {
                    required: true
                },
                'ship.cabinNum': {
                    required: true
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
		},

		/**
		 * 点击增加按钮
		 */
		add_click : function() {
			Cl.action = 'create';
			window.location.href = BasePath + "/task/addform";
		},
		/**
		 * 点击修改按钮
		 */
		update_click : function(id) {
			Cl.action = 'update';
			Cl.showModalWindow(Cl.modalName, BasePath + "/user/updateform?id="
					+ id);
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
			var options = {
				target : '#form_cl',
				type : 'post',
				dataType : 'json',
				url : BasePath + "/task/add",
				success : function(result) {
					if (!result)
						return;
					if (result.success == "success") {
						alert("增加成功");
					} else {
						alert("增加失败");
						return;
					}
				},
				error : function(result) {
					alert("系统异常，增加失败！");
				},
				clearForm : true,
				resetForm : true,
				timeout : 3000
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
				url : BasePath + "/task/update.do",
				success : function(result) {
					if (!result)
						return;
					if (result.success == "success") {
						alert("修改成功");
					} else {
						alert("修改失败");
						return;
					}
				},
				error : function(result) {
					alert("系统异常，修改失败！");
				},
				clearForm : true,
				resetForm : true,
				timeout : 3000
			};
			$("#form_cl").ajaxSubmit(options);
		},
		/**
		 * 删除
		 */
		remove : function(id) {
			if (!confirm("确定要删除该用户?")) {
				return;
			}
			var url = BasePath + "/user/delete.do";
			var data = {
				"id" : id
			};
			Cl.ajaxRequest(url, data, function(result) {
				if (!result)
					return;
				result = result.replace(/(^\s*)|(\s*$)/g, '');
				if (result == "success") {
					Cl.deleteDataRow(DataTableCl.tableName, data.id, 1);
					alert("删除成功");
				} else {
					alert("被用户使用的角色不允许删除");
					return;
				}
			});
		}

	};
}();
