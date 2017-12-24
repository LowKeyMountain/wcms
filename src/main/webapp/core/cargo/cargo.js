var CargoFormCl = function () {
    // validation using icons
    var handleValidation = function() {
        // for more info visit the official plugin documentation: 
        // http://docs.jquery.com/Plugins/Validation

        var form2 = $('#form_cl_cargo');
        var error2 = $('.alert-danger', form2);
        var success2 = $('.alert-success', form2);

        form2.validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block help-block-error', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "",  // validate all fields including form hidden input
            rules: {
            	cargoCategory: {
                    required: true
                },
                cargoType: {
                    required: true
                },
                stowage: {
                    required: true,
                    number : true
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
                	Cargo.add();
                } else if (name == 'form_cl_update') {
                	Cargo.update();
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
        formName: "form_cl_cargo"
    };
}();

var Cargo = function() {
	return {
		/**
		 * 加载列表
		 */
		list : function() {
//			alert('作业船舶任务ID' + taskId);
			if (taskId == null || taskId == undefined) {
				return;
			}
			var pageParam = {};
			pageParam.taskId = taskId;
			var url = BasePath + "/cargo/getCargoList";
			$.post(url, pageParam, function(result) {
				if (result && Cl.successInt == result.code) {
					
					// <tr>
					// <td>#</td>
					// <td>货物种类</td>
					// <td>货物类型</td>
					// <td>装货港</td>
					// <td>水分</td>
					// <td>品质</td>
					// <td>配载吨位</td>
					// <td>操作</td>
					// </tr>
					
					// 清空列表数据
					$("#cargo_tbody").children().empty();
					// 加载列表数据
//					result.data = ['0','2','3','5','6']
					var obj = result.rows;
					for (var i = 0; i < obj.length; i++) {
						var res = obj[i];
						var id = i + 1;
						var tr = ""
								+ "<tr>"
//								+ "<td>"+res.id+"</td>"
								+ "<td>"+res.cargoCategory+"</td>"
								+ "<td>"+res.cargoType+"</td>"
								+ "<td>"+res.loadingPort+"</td>"
								+ "<td>"+res.moisture+"</td>"
								+ "<td>"+res.quality+"</td>"
								+ "<td>"+res.stowage+"</td>"
								+ "<td>"
								+ "<a href='javascript:Cargo.update_click(" + res.id + ");' class='btn btn-xs default btn-editable'><i class='fa fa-edit'></i> 修改</a>"
								+ "&nbsp;&nbsp;"
								+ "<a href='javascript:Cargo.remove(" + res.id + ");' class='btn btn-xs default btn-editable'><i class='fa fa-times'></i> 删除</a>"
								+ "</td>" + "</tr>";

						$("#cargo_tbody").append(tr);
					}
				} else {
					alert(result.msg);
				}
			});
		},
		/**
		 * 点击增加按钮
		 */
		add_click : function() {
			if (taskId == null || taskId == undefined) {
				alert("请先录入船舶信息！");
				return;
			}
			Cl.action = 'create';
			Cl.showModalWindow(Cl.modalName, BasePath + "/cargo/addform");
		},
		
		/**
		 * 点击修改按钮
		 */
		update_click : function(id) {
			Cl.action = 'update';
			Cl.showModalWindow(Cl.modalName, BasePath + "/cargo/updateform?taskId="
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
				target : '#form_cl_cargo',
				type : 'post',
				dataType : 'json',
				url : BasePath + "/cargo/add?taskId=" + taskId,
				success : function(result) {
					if (!result)
						return;
					 var code = result.code;
					 if(Cl.successInt == code){
						Cl.hideModalWindow(Cl.modalName);
						Cargo.list();
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
			$("#form_cl_cargo").ajaxSubmit(options);
		},
		/**
		 * 修改
		 */
		update : function() {
			var options = {
				target : '#form_cl_cargo',
				type : 'post',
				dataType : 'json',
				url : BasePath + "/cargo/update?taskId=" + taskId,
				success : function(result) {
					if (!result)
						return;
					 var code = result.code;
					 if(Cl.successInt == code){
						alert("修改成功");
						Cl.hideModalWindow(Cl.modalName);
						Cargo.list();
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
			$("#form_cl_cargo").ajaxSubmit(options);
		},
		/**
		 * 删除
		 */
		remove : function(id) {
			if (!confirm("确定要删除?")) {
				return;
			}
			var url = BasePath + "/cargo/delete";
			var data = {
				"id" : id
			};
			Cl.ajaxRequest(url, data, function(result) {
				if (!result)
					return;
				 var code = result.code;
				 if(Cl.successInt == code){
					alert("删除成功");
					Cargo.list();
				} else {
					alert(result.msg);
					return;
				}
			});
		}

	};
}();