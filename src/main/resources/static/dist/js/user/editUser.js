//get data after redirect from view list
var url = document.location.pathname;
var curUsername = url.substring(url.lastIndexOf('/') + 1);

function displayUser(data) {
    $("#edit-username").val(data.username);
    $("#edit-email").val(data.email);
    $("#edit-password").val(data.password);
    $("#edit-phone").val(data.phone);
    $("#edit-fullName").val(data.fullName);
    $("#edit-role").val(data.role);
}

var user = {

    getUserbyUsername: function (curUsername) {
        $.ajax({
            url: `/api/v1/users/username/${curUsername}`
            , method: "GET"
            , success: function (response, textStatus, xhr) {
                displayUser(response);
            }
            , error: function (error) {
                alert("Fail");
            }
        })
    },


}

//ready to display Data on UI
user.getUserbyUsername(curUsername);

// call API to update
var userUpdate = {
    update: function (obj, username) {
        $.ajax({
            url: `/api/v1/users/${username}`
            , method: "PUT"
            , contentType: "application/json"
            , data: JSON.stringify(obj)
            , success: function (response, textStatus, xhr) {
                alert("Update successfully!");

            }
            , error: function (error) {
                alert("Update fail!");
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
        , rules: {
            mail: {
                maxlength: 320
                , validEmail: true
            }
            , phone: {
                required: true
                , maxlength: 12
            }
        },
        messages: {
            mail: {
                maxlength: "Email can not be over than 320 chars"
                , validEmail: "Email is not accepted"
            }
            , phone: {
                required: "Phone is not blank or empty"
                , maxlength: "Phone can not be over than 12 chars"
            }
        }
    }

}
jQuery.validator.addMethod("validEmail", function (value, element) {
    var myRe = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    var myArray = myRe.test(value);
    return myArray;
}, "Email is not accepted");

var validateSave = $("#edit-form").validate(userUpdate.validate);

//click on Submit button to confirm updating data
$("#btn-editUser").click(function () {
    var submit = $("#edit-form").valid();
    if (submit == false) {
        return;
    }
    event.preventDefault();
    const username = $("#edit-username").val();
    const phone = $("#edit-phone").val();
    const email = $("#edit-email").val();
    const obj = {username, phone, email}
    userUpdate.update(obj, username);
});