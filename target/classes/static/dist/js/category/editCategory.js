//
// get data after redirect from view list
// var url = document.location.pathname;
// var curCategory = url.substring(url.lastIndexOf('/') + 1);
// function displayCategory(data) {
//     $("#idUpdate").val(data.id);
//     $("#codeUpdate").val(data.code);
//     $("#nameUpdate").val(data.name);
//
// }
//
// var categoryedit = {
//
//     getCategoryById: function (curCategory) {
//         $.ajax({
//             url: `/api/v1/categorys/${curCategory}`
//             , method: "GET"
//             , success: function (response, textStatus, xhr) {
//                 displayCategory(response);
//             }
//             , error: function (error) {
//                 alert("fail");
//             }
//         })
//     },
//
//
// }

// //ready to display Data on UI
// categoryedit.getCategoryById(curCategory);
//
// var categoryUpdate = {
//     update: function (obj, id) {
//         $.ajax({
//             url: `/api/v1/categorys/${id}`
//             , method: "PUT"
//             , contentType: "application/json"
//             , data: JSON.stringify(obj)
//             , success: function (response, textStatus, xhr) {
//                 alert("Update successfully!");
//
//             }
//             , error: function (error) {
//                 alert("Update fail!");
//             }
//         })
//
//     },
//     validate: {
//         errorElement: "span"
//         , onkeyup: function (element) {
//             $(element).css("border", "1px solid #ced4da");
//             $(element).next().children().first().children().first().css("border", "1px solid #ced4da");
//         }
//         , highlight: function (element) {
//             $(element).css("border", "1px solid red");
//             $(element).next().children().first().children().first().css("border", "1px solid red");
//         }
//         , unhighlight: function (element) {
//             $(element).css("border", "1px solid #ced4da");
//             $(element).next().children().first().children().first().css("border", "1px solid #ced4da");
//         }
//         , errorPlacement: function (error, element) {
//             error.addClass('invalid-feedback');
//             error.insertAfter(element.parents("div.insertError"));
//         }
//         ,rules: {
//             //Dựa vào name ở ô input
//             codeUpdate : {
//                 required: true
//                 , maxlength: 20
//             },
//             nameUpdate : {
//                 required: true
//                 , maxlength: 20
//             }
//         },
//         messages: {
//             codeUpdate : {
//                 required: "code không được để trống"
//                 , maxlength: "code khong duoc vuot qua 20 ky tu"
//             },
//             nameUpdate : {
//                 required: "name không được để trống"
//                 , maxlength: "name khong duoc vuot qua 20 ky tu"
//             }
//
//         }
//     }
//
// }
//
// var validateSave = $("#form-update").validate(categoryUpdate.validate);
// click on Submit button to confirm updating data
// $("#btn-update-Category").click(function () {
//     var submit = $("#form-update").valid();
//     if (submit == false) {
//         return;
//     }
//     event.preventDefault();
//     const id = $("#idUpdate").val();
//     const code = $("#codeUpdate").val();
//     const name = $("#nameUpdate").val();
//     const obj = {code,name,id}
//     categoryUpdate.update(obj, id);
// });
//
//
