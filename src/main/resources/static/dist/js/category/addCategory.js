let categoryadd = {
    addCategory: function (obj) {
        $.ajax({
            "url": "/api/v1/categorys/addCategory"
            , "method": "POST"
            , "contentType": "application/json"
            , "data": JSON.stringify(obj)
            , "success": function (response, textStatus, xhr) {
                toastr.success("Thành Công", "Thêm mới");
                setTimeout(function () {
                    reloadData(); // Gọi hàm reloadTable sau khi thêm mới thành công
                }, 1000);
                $(".clear-data").val(""); // Xóa đi giá trị của tất cả các trường có lớp CSS 'clear-data'
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
            //Dựa vào name ở ô input
            code : {
                required: true
                    , maxlength: 20
            },
            name : {
                required: true
                    , maxlength: 20
            }
        },
        messages: {
            code : {
                required: "code không được để trống"
                    , maxlength: "code khong duoc vuot qua 20 ky tu"
            },
            name : {
                required: "name không được để trống"
                    , maxlength: "name khong duoc vuot qua 20 ky tu"
            }

        }
    }


}
function validate(){

}
var validateSave = $("#form-insert-category").validate(categoryadd.validate);
$("#btn-save").click(function () {
    var submit = $("#form-insert-category").valid();
    if (submit == false) {
        return;
    }
    const code = $("#code-save").val();
    const name = $("#name-save").val();
    const obj = {code,name};
    categoryadd.addCategory(obj);

});
function reloadData() {
    category.getAllCategory(currentPage - 1 ,pageSize,); // Gọi hàm getAllCategory với trang đầu tiên và kích thước trang hiện tại
}


