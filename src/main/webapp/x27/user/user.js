var DataTableCl = function() {
	var handleUser = function() {
        var table = $('#datatable_cl');
        // begin first table
        table.dataTable({
            // Internationalisation. For more info refer to http://datatables.net/manual/i18n
            "language": {
                "aria": {
                    "sortAscending": ": activate to sort column ascending",
                    "sortDescending": ": activate to sort column descending"
                },
                "emptyTable": "No data available in table",
                "info": "Showing _START_ to _END_ of _TOTAL_ records",
                "infoEmpty": "No records found",
                "infoFiltered": "(filtered1 from _MAX_ total records)",
                "lengthMenu": "Show _MENU_",
                "search": "Search:",
                "zeroRecords": "No matching records found",
                "paginate": {
                    "previous":"Prev",
                    "next": "Next",
                    "last": "Last",
                    "first": "First"
                }
            },
			"bServerSide" : true, // server side processing
			"sAjaxSource" : BasePath + "/user/getUserDataTables", // ajax source
            
            // Or you can use remote translation file
            //"language": {
            //   url: '//cdn.datatables.net/plug-ins/3cfcc339e89/i18n/Portuguese.json'
            //},

            // Uncomment below line("dom" parameter) to fix the dropdown overflow issue in the datatable cells. The default datatable layout
            // setup uses scrollable div(table-scrollable) with overflow:auto to enable vertical scroll(see: assets/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.js). 
            // So when dropdowns used the scrollable div should be removed. 
            //"dom": "<'row'<'col-md-6 col-sm-12'l><'col-md-6 col-sm-12'f>r>t<'row'<'col-md-5 col-sm-12'i><'col-md-7 col-sm-12'p>>",

            "bStateSave": true, // save datatable state(pagination, sort, etc) in cookie.

            "columnDefs": [ {
                "targets": 0,
                "orderable": false,
                "searchable": false
            }],

            "lengthMenu": [
                [5, 15, 20, -1],
                [5, 15, 20, "All"] // change per page values here
            ],
            // set the initial value
            "pageLength": 5,            
            "pagingType": "bootstrap_full_number",
            "columnDefs": [{  // set default column settings
                'orderable': false,
                'targets': [0]
            }, {
                "searchable": false,
                "targets": [0]
            }],
            "order": [
                [1, "asc"]
            ] // set first column as a default sort by asc
        });

        var tableWrapper = jQuery('#sample_1_wrapper');

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
    var handleValidation = function() {
		// for more info visit the official plugin documentation:
	    // http://docs.jquery.com/Plugins/Validation
	    var form1 = $('#form_cl');
	    var error1 = $('.alert-danger', form1);
	    var success1 = $('.alert-success', form1);	
	    form1.validate( );
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
			var url = BasePath + "/user/add.do";
			var data={
				"userName":$("#userName").val(),
				"password":$("#password").val(),
				"realName":$("#realName").val(),
				"gender":$('input[name="gender"]:checked').val(),
				"isAdmin":$('input[name="isAdmin"]:checked').val()
//				,
//				"departmentId":$("#departmentId").val()
			};
			Cl.ajaxRequest(url,data,function(result){
				if(!result) return ;		
				result = result.replace(/(^\s*)|(\s*$)/g,'');
				if(result == "success"){								
					Cl.hideModalWindow(Cl.modalName);
					Cl.refreshDataTable(DataTableCl.tableName);
					alert("增加成功");
				} else {
					alert("增加失败");
					return ;			
				}
			});
		},
		/**
		 * 修改用户
		 */
		update: function(){
			var url = BasePath + "/user/update.do";
			var data={
				"id":$("#id").val(),
				"userName":$("#userName").val(),
				"realName":$("#realName").val(),
				"gender":$('input[name="gender"]:checked').val(),
				"isAdmin":$('input[name="isAdmin"]:checked').val()
//				,
//				"departmentId":$("#departmentId").val()
			};
			Cl.ajaxRequest(url,data,function(result){
				if(!result) return ;		
				result = result.replace(/(^\s*)|(\s*$)/g,'');
				if(result == "success"){			
					Cl.hideModalWindow(Cl.modalName);
					Cl.updateDataRow(DataTableCl.tableName,data.id,1,BasePath + '/user/getUserDataRow.do');
					alert("修改成功");
				} else {
					alert("修改失败");
					return ;			
				}
			});
		},
		/**
		 * 删除角色
		 */
		remove: function(id){
			if(!confirm("确定要删除该角色")){
				return;
			}	
			var url=BasePath + "/user/delete.do";
			var data={
				"id":id
			};
			Cl.ajaxRequest(url,data,function(result){
				if(!result) return ;		
				result = result.replace(/(^\s*)|(\s*$)/g,'');
				if(result == "success"){
					Cl.deleteDataRow(DataTableCl.tableName,data.id,1);
					alert("删除成功");
				} else {
					alert("被用户使用的角色不允许删除");
					return ;			
				}
			});
		},
		/**
		 * 用户分配角色
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
			if(selectedStr == ""){
				alert("没有选择任何角色");
				return;
			}	
			var url="assign.do";
			var data={
				"id":$("#id").val(),
				"selectedStr":selectedStr
			};	
			Cl.ajaxRequest(url,data,function(result){
				if(!result) return ;		
				result = result.replace(/(^\s*)|(\s*$)/g,'');
				if(result == "success"){
					Cl.hideModalWindow(Cl.modalName);
					alert("分配角色成功");			
				} else {
					alert("分配角色失败");
					return ;			
				}
			});
		},
		/**
		 * 复位密码
		 */
		resetpass: function(id){
			if(!confirm("确定要重置密码为root吗")){
				return;
			}	
			var url=BasePath + "/user/resetpass.do";
			var data={
				"id":id
			};
			Cl.ajaxRequest(url,data,function(result){
				if(!result) return ;		
				result = result.replace(/(^\s*)|(\s*$)/g,'');
				if(result == "success"){
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
				result = result.replace(/(^\s*)|(\s*$)/g,'');
				if(result == "success"){
					Cl.updateDataRow(DataTableCl.tableName,data.id,1,BasePath + '/user/getUserDataRow.do');
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
				result = result.replace(/(^\s*)|(\s*$)/g,'');
				if(result == "success"){
					Cl.updateDataRow(DataTableCl.tableName,data.id,1,BasePath + '/user/getUserDataRow.do');
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