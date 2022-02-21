var DataTableCl = function() {
	var handleUser = function() {
        var table = $('#datatable_cl');
        // begin first table
        table.dataTable({
            // Internationalisation. For more info refer to http://datatables.net/manual/i18n
            "language": {
            	"sProcessing":   "处理中...",
            	"sLengthMenu":   "显示 _MENU_ 项结果",
            	"sZeroRecords":  "没有匹配结果",
            	
            	"sInfo":         "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
            	"sInfoEmpty":    "显示第 0 至 0 项结果，共 0 项",
            	"sInfoFiltered": "(由 _MAX_ 项结果过滤)",
            	"sInfoPostFix":  "",
            	"sSearch":       "搜索:",
            	"sUrl":          "",
            	"sEmptyTable":     "表中数据为空",
            	"sLoadingRecords": "载入中...",
            	"sInfoThousands":  ",",
            	"oPaginate": {
            		"sFirst":    "首页",
            		"sPrevious": "上页",
            		"sNext":     "下页",
            		"sLast":     "末页"
            	},
            	"oAria": {
            		"sSortAscending":  ": 以升序排列此列",
            		"sSortDescending": ": 以降序排列此列"
            	}
            },
            
			"bServerSide" : true, // server side processing
			"sAjaxSource" : BasePath + "/user/getUserDataTables", // ajax
			
            // Or you can use remote translation file
            //"language": {
            //   url: '//cdn.datatables.net/plug-ins/3cfcc339e89/i18n/Portuguese.json'
            //},
			
            // Uncomment below line("dom" parameter) to fix the dropdown overflow issue in the datatable cells. The default datatable layout
            // setup uses scrollable div(table-scrollable) with overflow:auto to enable vertical scroll(see: assets/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.js). 
            // So when dropdowns used the scrollable div should be removed. 
            //"dom": "<'row'<'col-md-6 col-sm-12'l><'col-md-6 col-sm-12'f>r>t<'row'<'col-md-5 col-sm-12'i><'col-md-7 col-sm-12'p>>",

            "bStateSave": true, // save datatable state(pagination, sort, etc) in cookie.

            "lengthMenu": [
                [5, 15, 20],
                [5, 15, 20] // change per page values here
            ],
            // set the initial value
            "pageLength": 5,            
            "pagingType": "bootstrap_full_number",
            "columnDefs" : [ { // set default column settings
				"targets" : [ 0, -1 ],
				"orderable" : false,
				"searchable" : false
			} ],
            "columns" : [ 
                          //{"name":""},
                          {"name":"id"}, 
                          {"name":"userName"}, 
                          {"name":"realName"}, 
//                          {"name":"gender"}, 
//                          {"name":"isAdmin"}, 
                          {"name":"isLock"}, 
                          {"name":"updatePerson"}, 
                          {"name":"updateDate"}, 
                          {"name":""}
                      ],
            "order": [
                [1, "asc"]
            ] // set first column as a default sort by asc
        });

        var tableWrapper = jQuery('#datatable_cl_wrapper');

        table.find('.group-checkable').change(function () {
            var set = jQuery(this).attr("data-set");
            var checked = jQuery(this).is(":checked");
            jQuery(set).each(function () {
                if (checked) {
                    $(this).prop("checked", true);
                    $(this).parents('tr').addClass("active");
                } else {
                    $(this).prop("checked", false);
                    $(this).parents('tr').removeClass("active");
                }
            });
            jQuery.uniform.update(set);
        });

        table.on('change', 'tbody tr .checkboxes', function () {
            $(this).parents('tr').toggleClass("active");
        });
	}
	return {
		// main function to initiate the module
		init : function() {
	        if (!jQuery().dataTable) {
	            return;
	        }
			handleUser();
		},
		tableName : "datatable_cl"
	};
}();

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
            	userName: {
                    minlength: 2,
                    required: true
                },
                password: {
                    required: true,
                    minlength: 5
                },
                confirm_password: {
                    required: true,
                    minlength: 5,
                    equalTo: "#password"
                },
                realName: {
                    required: true,
                    minlength: 2
                },
                gender: {
                    required: true
                },
                isAdmin: {
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
//              form[0].submit(); // submit the form
                var name = $(form).attr("name")
                if (name == 'form_cl_add') {
                	User.add();
                } else if (name == 'form_cl_update') {
                	User.update();
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

var User = function(){
	return {
		/**
		 * 点击增加按钮
		 */
		add_click: function() {
	    	Cl.action='create';
	    	Cl.showModalWindow(Cl.modalName, BasePath + "/user/addform");
		},
		/**
		 * 点击修改按钮
		 */
		update_click: function(id) {
			Cl.action='update';	
			Cl.showModalWindow(Cl.modalName, BasePath + "/user/updateform?id=" + id);
		},
		/**
		 * 点击分配权限按钮按钮
		 */
		assign_click: function(id) {
			Cl.action='assign';	
			Cl.showModalWindow(Cl.modalName,"assignform.do?id="+id);
		},
		/**
		 * 增加用户
		 */
		add: function(){
			/* option的参数 
			var options = {    
			       target:        '#output1',   // target element(s) to be updated with server response    
			       beforeSubmit:  showRequest,  // pre-submit callback    
			       success:       showResponse  // post-submit callback    
			  
			       // other available options:    
			       //url:       url         // override for form's 'action' attribute    
			       //type:      type        // 'get' or 'post', override for form's 'method' attribute    
			       //dataType:  null        // 'xml', 'script', or 'json' (expected server response type)    
			       //clearForm: true        // clear all form fields after successful submit    
			       //resetForm: true        // reset the form after successful submit    
			  
			       // $.ajax options can be used here too, for example:    
			       //timeout:   3000    
			   }; 
			 **/
			 var options = {
				target : '#form_cl',
				type : 'post',
				dataType : 'json',
				url : BasePath + "/user/add.do",
				success : function(result) {
					 if(!result) return ;
					 var code = result.code;
					 if(Cl.successInt == code){
						 Cl.hideModalWindow(Cl.modalName);
						 Cl.refreshDataTable(DataTableCl.tableName);
						 alert("增加成功");
					 } else {
						 alert(result.msg);
						 return ;
					 }
				},
				error : function(result){
					 Cl.hideModalWindow(Cl.modalName);
					 Cl.refreshDataTable(DataTableCl.tableName);
					 alert("系统异常，增加失败！");
				},
				clearForm: true,
				resetForm: true,
			 	timeout:   3000  
			};
			 	$("#form_cl").ajaxSubmit(options);
		},
		/**
		 * 修改用户
		 */
		update: function(){
			 var options = {
				target : '#form_cl',
				type : 'post',
				dataType : 'json',
				url : BasePath + "/user/update.do",
				success : function(result) {
					 if(!result) return ;
					 var code = result.code;
					 if(Cl.successInt == code){
						Cl.hideModalWindow(Cl.modalName);
						Cl.updateDataRow(DataTableCl.tableName,result.id,0,BasePath + '/user/getUserDataRow.do');
						 alert("修改成功");
					 } else {
						 alert("修改失败");
						 return ;
					 }
				},
				error : function(result){
					 Cl.hideModalWindow(Cl.modalName);
					 Cl.refreshDataTable(DataTableCl.tableName);
					 alert("系统异常，修改失败！");
				},
				clearForm: true,
				resetForm: true,
			 	timeout:   3000  
			};
		 	$("#form_cl").ajaxSubmit(options);
		},
		/**
		 * 删除用户
		 */
		remove: function(id){
			if(!confirm("确定要删除该用户?")){
				return;
			}	
			var url=BasePath + "/user/delete.do";
			var data={
				"id":id
			};
			Cl.ajaxRequest(url,data,function(result){
				if(!result) return ;		
				 var code = result.code;
				 if(Cl.successInt == code){
					Cl.deleteDataRow(DataTableCl.tableName,data.id,0);
					alert("删除成功");
				} else {
					alert("被用户使用的角色不允许删除");
					return ;			
				}
			});
		},
		/**
		 * 用户分配权限
		 */
		assign: function(){
			var selectedStr = "";
			var i = 0;
			$("#multi_role").find("option:selected").each(function(){
				if(i==0)
				{
					selectedStr = $(this).val();
				} else {
					selectedStr = selectedStr + "," + $(this).val();
				}
				i++;
			});
//			if(selectedStr == ""){
//				alert("没有选择任何角色");
//				return;
//			}	
			var url=BasePath + "/user/assign.do";
			
			var data={
				"id":$("#id").val(),
				"selectedStr":selectedStr
			};	
			Cl.ajaxRequest(url,data,function(result){
				if(!result) return ;		
				 var code = result.code;
				 if(Cl.successInt == code){
					Cl.hideModalWindow(Cl.modalName);
					alert("分配权限成功!");			
				} else {
//					alert("分配权限失败!");
					alert(result.msg);
					return ;			
				}
			});
		},
		/**
		 * 复位密码
		 */
		resetpass: function(id){
			if(!confirm("确定要重置为默认密码？")){
				return;
			}	
			var url=BasePath + "/user/resetpass.do";
			var data={
				"id":id
			};
			Cl.ajaxRequest(url,data,function(result){
				if(!result) return ;		
				 var code = result.code;
				 if(Cl.successInt == code){
					alert("重置成功");
				} else {
					alert("重置失败");
					return ;			
				}
			});
		},
		/**
		 * 锁定用户
		 */
		lock: function(id){
			if(!confirm("确定要锁定用户")){
				return;
			}	
			var url=BasePath + "/user/lock.do";
			var data={
				"id":id
			};
			Cl.ajaxRequest(url,data,function(result){
				if(!result) return ;		
				 var code = result.code;
				 if(Cl.successInt == code){
					Cl.updateDataRow(DataTableCl.tableName,data.id,0,BasePath + '/user/getUserDataRow.do');
					alert("锁定成功");
				} else {
					alert("锁定失败");
					return ;			
				}
			});
		},
		/**
		 * 解锁用户
		 */
		unlock: function(id){
			if(!confirm("确定要解锁用户")){
				return;
			}	
			var url=BasePath + "/user/unlock.do";
			var data={
				"id":id
			};
			Cl.ajaxRequest(url,data,function(result){
				if(!result) return ;		
				 var code = result.code;
				 if(Cl.successInt == code){
					Cl.updateDataRow(DataTableCl.tableName,data.id,0,BasePath + '/user/getUserDataRow.do');
					alert("解锁成功");
				} else {
					alert("解锁失败");
					return ;			
				}
			});
		}
	};
}();

/**
 * 查询参数的处理，每个功能的DataTable都要处理自己的查询条件，并向服务器提交
 * 如果使用了DataTables控件，则都要定义这个函数
 */
var aoDataHandler = function(aoData) {
	//页面的查询条件
	//aoData.push( { "name": "name", "value": "" } );
}