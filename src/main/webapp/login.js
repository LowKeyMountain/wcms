var Login = function() {

    var handleLogin = function() {

        $('.login-form').validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            rules: {
                username: {
                    required: true
                },
                password: {
                    required: true
                },
                remember: {
                    required: false
                }
            },

            messages: {
                username: {
                    required: "Username is required."
                },
                password: {
                    required: "Password is required."
                }
            },

            invalidHandler: function(event, validator) { //display error alert on form submit   
            	$('.alert-danger-span').text("Enter any username and password. ");
                $('.alert-danger', $('.login-form')).show();
            },

            highlight: function(element) { // hightlight error inputs
                $(element)
                    .closest('.form-group').addClass('has-error'); // set error class to the control group
            },

            success: function(label) {
                label.closest('.form-group').removeClass('has-error');
                label.remove();
            },

            errorPlacement: function(error, element) {
                error.insertAfter(element.closest('.input-icon'));
            },

            submitHandler: function(form) {
            	// form.submit(); // form validation success, call ajax form submit
            	formSubmit();
            }
        });
        
        var formSubmit = function(){
			var url = IncPath + "/web/login";
			var data = {
				"username" : $('#username').val(),
				"password" : hex_md5($('#password').val())
			};
			Cl.ajaxRequest(url, data, function(result) {
				if (result == undefined && result == null) {
					return;
				}
				var code = result.code;
				if (Cl.successInt == code) {
					location = IncPath + result.url;
				} else if (Cl.failInt == code) {
					$('.alert-danger-span').text(result.msg);
					$('.alert-danger', $('.login-form')).show();
				}
			});
        }
        
        $('.login-form input').keypress(function(e) {
            if (e.which == 13) {
                if ($('.login-form').validate().form()) {
                	// $('.login-form').submit(); //form validation success, call ajax form submit
                	formSubmit(); 
                }
                return false;
            }
        });

        $('.forget-form input').keypress(function(e) {
            if (e.which == 13) {
                if ($('.forget-form').validate().form()) {
                    $('.forget-form').submit();
                }
                return false;
            }
        });

        $('#forget-password').click(function(){
            $('.login-form').hide();
            $('.forget-form').show();
        });

        $('#back-btn').click(function(){
            $('.login-form').show();
            $('.forget-form').hide();
        });
    }
    

    return {
        //main function to initiate the module
        init: function() {

            handleLogin();

            // init background slide images
            $('.login-bg').backstretch([
                "../img/login/bg1.jpg"
//                ,
//                "../assets/pages/img/login/bg2.jpg",
//                "../assets/pages/img/login/bg3.jpg"
                ], {
                  fade: 1000,
                  duration: 8000
                }
            );

            $('.forget-form').hide();

        }
    };

}();

jQuery(document).ready(function() {
    Login.init();
});