var CabinModifyFormCl = function () {
    // validation using icons
    var handleValidation = function() {
        // for more info visit the official plugin documentation: 
        // http://docs.jquery.com/Plugins/Validation

        var form2 = $('#form_modify_cabin');
        var error2 = $('.alert-danger', form2);
        var success2 = $('.alert-success', form2);
        
        form2.validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block help-block-error', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "",  // validate all fields including form hidden input
            rules : {
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
                if (name == 'form_cl_modify') {
                	ModifyCabin.add();
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
var ModifyCabin = function() {
	return {
		list : function() {
//			alert('任务ID' + taskId);
			if (taskId == null || taskId == undefined) {
				return;
			}
			var mapping = {};
			var pageParam = {};
			pageParam.taskId = taskId;
			var url = BasePath + "/cabin/getCabinPositionList";
			$.post(url, pageParam, function(result) {
				if (result && Cl.successInt == result.code) {
					// 清空列表数据
					$("#cabin_tbody").children().empty();
					// 加载列表数据
					var obj = result.rows;
					for (var i = 0; i < obj.length; i++) {
						var res = obj[i];
						var tr = ""
						+ "<tr>"
						+ "<td>"
						+ "<input id=\"" + res.cabinNo + "_cabinNo\" type=\"hidden\" value=\""
						+ res.cabinNo
						+ "\"/> #"
						+ res.cabinNo
						+ "</td>"
						+ "<td>"
						+ "<input id=\"" + res.cabinNo + "_startPosition\" type=\"text\" class=\"form-control\" value=\""
						+ res.startPosition
						+ "\"/>"
						+ "</td>"
						+ "<td>"
						+ "<input id=\"" + res.cabinNo + "_endPosition\" type=\"text\" class=\"form-control\" value=\""
						+ res.endPosition + "\"/>"
						+ "</td>" + "</tr>";

						$("#cabin_tbody").append(tr);
					}
				} else {
					alert(result.msg);
				}
			});
		},
		/**
		 * 保存
		 */
		add : function() {
			
			var arrayObj = new Array();
			for (var i = 1; i <= cabinNum; i++) {
				var obj = {};
				obj['cabinNo'] = $("#" + i + "_cabinNo").val();
				obj['startPosition'] = $("#" + i + "_startPosition").val();
				obj['endPosition'] = $("#" + i + "_endPosition").val();
				arrayObj[i] = obj;
			}
			var jsonObj = {};
			jsonObj['taskId'] = taskId;
			jsonObj['data'] = arrayObj;
			var json = JSON.stringify(jsonObj);
			
			var options = {
				target : '#form_modify_cabin',
				type : 'post',
				dataType : 'json',
				url : BasePath + "/cabin/doSetCabinPosition?json=" + json,
				success : function(result) {
					if (!result)
						return;
					 var code = result.code;
					 if(Cl.successInt == code){
						alert("保存成功");
					} else {
						alert("保存失败");
						return;
					}
				},
				error : function(result) {
					alert("系统异常，增加失败！");
				},
//				clearForm : true,
//				resetForm : true,
				timeout : 3000
			};
			$("#form_modify_cabin").ajaxSubmit(options);
		}
	};
}();
