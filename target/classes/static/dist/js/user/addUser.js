var user = {
    addUser: function(obj)
    {
        $.ajax({
            "url": "/api/v1/users"
            ,"method": "POST"
            ,"contentType": "application/json"
            ,"data": JSON.stringify(obj)
            , "success": function (response, textStatus, xhr) {
                  toastr.success("Thành Công", "Thêm mới");

            }
            , "error": function (error) {
                 toastr.error("Fail!", "That bai");
            }
        })

    },
    validate: {
        errorElement: "span"
        , onkeyup: function (element) {
            $(element).css("border", "1px solid #ced4da");
            $(element).next().children().first().children().first().css("border", "1px solid #ced4da");
        }
        , highlight: function (element) {
            $(element).css("border", "1px solid red");
            $(element).next().children().first().children().first().css("border", "1px solid red");
        }
        , unhighlight: function (element) {
            $(element).css("border", "1px solid #ced4da");
            $(element).next().children().first().children().first().css("border", "1px solid #ced4da");
        }
        , errorPlacement: function (error, element) {
            error.addClass('invalid-feedback');
            error.insertAfter(element.parents("div.insertError"));
        }
        ,rules: {
            username : {
                required: true
                , maxlength: 20
            }
            , mail : {
                maxlength: 320
                ,validEmail : true
            }
            , phone : {
                required: true
                , maxlength: 12
            }
            , fullName : {
                required: true
                , maxlength: 50
            }
            , password : {
                required: true
                , maxlength: 200
            }
        },
        messages: {
            username : {
                required: "Ten không được để trống"
                , maxlength: "Username khong duoc vuot qua 20 ky tu"
            }
            , mail : {
                maxlength: "Email khong duoc vuot qua 320 ky tu"
                ,validEmail : "Email khong hop le"
            }
            , phone : {
                required: "sdt khong duoc de trong"
                , maxlength: "so dien thoai khong vuot qua 12 ky tu"
            }
            , fullName : {
                required: "Full name khong duoc de trong"
                , maxlength: "Full name toi da 50 ky tu"
            }
            , password : {
                required: "Password khong duoc de trong"
                , maxlength: "Password toi da 200 ky tu"
            }
        }
    }
}
jQuery.validator.addMethod("validEmail", function (value, element) {
    var myRe = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    var myArray = myRe.test(value);
    return myArray;
}, "Email khong hop le");

var validateSave = $("#form-save-user").validate(user.validate);

$("#btn-save").click(function () {
     var submit = $("#form-save-user").valid();
     if (submit == false) {
         return;
     }
     event.preventDefault();
     const username = $("#username").val();
     const email = $("#mail").val();
     const phone = $("#phone").val();
     const password = $("#password").val();
     const fullName = $("#fullName").val();
     const role = $("#role").val();
     const obj = {username,phone, email,password, fullName, role};
     user.addUser(obj);
 });