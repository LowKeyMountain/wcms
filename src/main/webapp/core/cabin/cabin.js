var CabinFormCl = function () {
    // validation using icons
    var handleValidation = function() {
        // for more info visit the official plugin documentation: 
        // http://docs.jquery.com/Plugins/Validation

        var form2 = $('#form_cl_cabin');
        var error2 = $('.alert-danger', form2);
        var success2 = $('.alert-success', form2);
        
        jQuery.validator.addMethod("compareCabinPosition", function(value, element) {   
        	var startPosition = $("#startPosition").val();
        	var endPosition = $("#endPosition").val();
        	if (startPosition == '0.0' && endPosition == '0.0') {
        		return true;
        	}
        	var number = parseInt(endPosition) - parseInt(startPosition);
            return number >= 0;
        }, "开始位置不能大于结束位置！");
        
        form2.validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block help-block-error', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "",  // validate all fields including form hidden input
            rules : {
				cargoId : {
					required : true
				},
				preunloading : {
					required : true,
					number : true
				}
//				,
//				startPosition : {
//					number : true,
//					range:[0,855]
//				},
//				endPosition : {
//					number : true,
//					range:[0,855],
//					compareCabinPosition:true
//				}
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
                	Cabin.add();
                } else if (name == 'form_cl_update') {
                	Cabin.update();
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
        formName: "form_cl_cabin"
    };
}();

var Cabin = function() {
	return {
		/**
		 * 加载列表
		 */
		list : function() {
//			alert('作业船舶任务ID' + taskId);
			if (taskId == null || taskId == undefined) {
				return;
			}
			var mapping = {};
			var pageParam = {};
			pageParam.taskId = taskId;
			var url = BasePath + "/cabin/getCabinList";
			$.post(url, pageParam, function(result) {
				if (result && Cl.successInt == result.code) {
					
					// <tr>
					// <td>船舱编号</td>
					// <td>货物类型</td>
					// <td>开始位置</td>
					// <td>结束位置</td>
					// <td>装载量</td>
					// <td>操作</td>
					// </tr>
					
					// 清空列表数据
					$("#cabin_tbody").children().empty();
					// 加载列表数据
					var obj = result.rows;
					mapping = result.mapping;
					var authorize_2 = authorize('2');
					for (var i = 0; i < obj.length; i++) {
						var res = obj[i];
						var id = i + 1;
						var tr = ""
								+ "<tr>"
								+ "<td>"+res.cabinNo+"</td>"
								+ "<td type='select'>" + res.cargoName + "</td>"
//								+ "<td>"+res.startPosition+"</td>"
//								+ "<td>"+res.endPosition+"</td>"
								+ "<td>"+res.preunloading+"</td>"
								+ "<td>";
								if (authorize_2) {
									tr+= "<a href='javascript:Cabin.update_click(" + res.id + ");' class='btn btn-xs default btn-editable'><i class='fa fa-edit'></i> 修改</a>"
									+ "&nbsp;&nbsp;"
	//								+ "<a href='javascript:Cabin.remove(" + res.id + ");' class='btn btn-xs default btn-editable'><i class='fa fa-edit'></i> 删除</a>";
								}
								tr+= "</td>"
								+ "</tr>";

						$("#cabin_tbody").append(tr);
					}
					
//				    $('.editable').handleTable({
//				        "handleFirst" : true,
//				        "cancel" : "&nbsp;<span class='glyphicon glyphicon-remove'></span>&nbsp;",
//				        "edit" : "&nbsp;<span class='glyphicon glyphicon-edit'></span>&nbsp;",
//				        "add" : "&nbsp;<span class='glyphicon glyphicon-plus'></span>&nbsp;",
//				        "save" : "&nbsp;<span class='glyphicon glyphicon-saved'></span>&nbsp;",
//				        "confirm" : "&nbsp;<span class='glyphicon glyphicon-ok'></span>&nbsp;",
//				        "operatePos" : -1,
//				        "editableCols" : [1,2,3,4],
//						"order": ["edit"],
//				        "saveCallback" : function(data, isSuccess) { //这里可以写ajax内容，用于保存编辑后的内容
//				        	//data: 返回的数据
//				        	//isSucess: 方法，用于保存数据成功后，将可编辑状态变为不可编辑状态
//							var url = BasePath + "/cabin/update";
//							
//							var params = mapping[data[0]];
//							params['cargo.id'] = data[1];
//							params['startPosition'] = data[2];
//							params['endPosition'] = data[3];
//							params['preunloading'] = data[4];
//							Cl.ajaxRequest(url, params, function(result) {
//								if (!result)
//									return;
//					            if(result.success == "success") { //ajax请求成功（保存数据成功），才回调isSuccess函数（修改保存状态为编辑状态）
//					                isSuccess();
//					                alert(data + " 保存成功");
//					            } else {
//					                alert("保存失败");
//					            }
//							});
//				            return true;
//				        },
//				        "addCallback" : function(data,isSuccess) {
//				            var flag = true;
//				            if(flag) {
//				                isSuccess();
//				                alert(data + " 增加成功");
//				            } else {
//				                alert(data + " 增加失败");
//				            }
//				        },
//				        "delCallback" : function(isSuccess) {
//				            var flag = true;
//				            if(flag) {
//				                isSuccess();
//				                alert("删除成功");
//				            } else {
//				                alert("删除失败");
//				            }
//				        }
//				    });
					
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
			Cl.showModalWindow(Cl.modalName, BasePath + "/cabin/addform?taskId=" + taskId);
		},
		
		/**
		 * 点击修改按钮
		 */
		update_click : function(id) {
			Cl.action = 'update';
			Cl.showModalWindow(Cl.modalName, BasePath + "/cabin/updateform?id="
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
				target : '#form_cl_cabin',
				type : 'post',
				dataType : 'json',
				url : BasePath + "/cabin/add?taskId=" + taskId,
				success : function(result) {
					if (!result)
						return;
					 var code = result.code;
					 if(Cl.successInt == code){
						Cl.hideModalWindow(Cl.modalName);
						Cabin.list();
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
			$("#form_cl_cabin").ajaxSubmit(options);
		},
		/**
		 * 修改
		 */
		update : function() {
			var options = {
				target : '#form_cl_cabin',
				type : 'post',
				dataType : 'json',
				url : BasePath + "/cabin/update?taskId=" + taskId,
				success : function(result) {
					if (!result)
						return;
					 var code = result.code;
					 if(Cl.successInt == code){
						alert("修改成功");
						Cl.hideModalWindow(Cl.modalName);
						Cabin.list();
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
			$("#form_cl_cabin").ajaxSubmit(options);
		},
		/**
		 * 删除
		 */
		remove : function(id) {
			if (!confirm("确定要删除?")) {
				return;
			}
			var url = BasePath + "/cabin/delete";
			var data = {
				"id" : id
			};
			Cl.ajaxRequest(url, data, function(result) {
				if (!result)
					return;
				 var code = result.code;
				 if(Cl.successInt == code){
					Cl.deleteDataRow(DataTableCl.tableName, data.id, 1);
					alert("删除成功");
				} else {
					alert("删除失败");
					return;
				}
			});
		}

	};
}();