
//kich hoat model them moi
$("#btn-modal-save").click(function () {
    $('#modal-save').modal('toggle');
});
let currentPage = 1;
let pageSize = 5;
let keySearch = "";


let category = {
    getAllCategory: function (pageNumber,pageSize,keySearch) {
        let url = `/api/v1/categorys?pageNo=${pageNumber}&pageSize=${pageSize}`;
        if(keySearch){
            url += `&code=${keySearch}`;
        }
        $.ajax({

            url:url,
            "method": "GET",
            "success": function (response, textStatus, xhr) {
                displayCategory(response.content);
                paginate(response.totalPages);
            },
            "error": function (error) {
                alert("Fail");
            }
        });
    }
};
function displayCategory(data) {
    const tableBody = $("#table-category tbody");
    tableBody.empty();
    let rows = "";
    for (let category of Object.values(data)){ // user is an array
        let row = `<tr>
                                <td>${category.code}</td>
                                <td>${category.name}</td>
                           
                                 <td>
                                 <button onclick="myFunctionEdit('${category.id}')" class="btn btn-warning" style="margin-right: 5px">
                                      <i class="fa fa-edit" style="font-size:14px"></i>
                                    </button>
                                    <a class="btn btn-danger" onclick="confirmDelCategory('${category.id}')">
                                            <i class=\"fa fa-trash\" style=\"font-size:14px\"></i>
                                    </a>
                                </td>
                </tr>`
        rows += row;
    }
    $("#table-category").append(rows);
}
$(document).on("click", ".btn-warning", function(event) {
    event.preventDefault();
    $("#modal-update2").modal("show");
});
function myFunctionEdit(id) {
    // Xử lý khi nút được nhấp vào
    console.log("Nút đã được nhấp vào!");
    var url = document.location.pathname;
    // var curCategory = url.substring(url.lastIndexOf('/') + 1);
    var categoryedit2 = {
    getCategoryById: function (curCategory) {
        $.ajax({
            url: `/api/v1/categorys/${id}`
            , method: "GET"
            , success: function (response, textStatus, xhr) {
                displayCategory(response);
            }
            , error: function (error) {
                alert("fail");
            }
        })
    },
}
    categoryedit2.getCategoryById(id);


    function displayCategory(data) {
    $("#id-update").val(data.id);
    $("#code-update").val(data.code);
    $("#name-update").val(data.name);

}

}

category.getAllCategory(currentPage - 1 ,pageSize,);
function confirmDelCategory(curUsername){
    $("#confirmDeleteModal").modal("show");

    $("#confirmDelButton").on("click", function() {
        $("#confirmDeleteModal").modal("hide");
        deleteCategoryByID(curUsername);
    });
}

function deleteCategoryByID(id) {
    console.log(typeof id)
    $.ajax({
        url: `/api/v1/categorys/${id}`
        , method: "DELETE"
        , success: function () {
            alert("Delete successfully!");
            category.getAllCategory(currentPage - 1 ,pageSize,);
        }
        , error: function (error) {
            alert("Delete fail!");
        }
    })
}
function paginate(totalPage){
    let rows ="";
    $("#page-category").empty();
    // previous
    rows += `<li class="page-item ${currentPage == 1 ? "disabled" : ""}" onclick="onClickPrevious(${currentPage})"><span class="page-link">Previous</span></li>`;

    // pages
    for (let index = 1; index <= totalPage; index++){
        let row = `<li class="page-item ${currentPage == index ? "active" : ""}" onclick="onClickToChangePage(${index})"><span class="page-link">${index}</span></li>`
        rows += row;
    }
    // Next
    rows += `<li class="page-item ${currentPage == totalPage ? "disabled" : ""}" onclick="onClickNext(${totalPage})"><span class="page-link">Next</span></li>`;
    $("#page-category").append(rows);
}
function onClickToChangePage(page){
    if(page == currentPage) return;
    currentPage = page;
    category.getAllCategory(currentPage - 1, pageSize,keySearch);

}
function onClickPrevious(currentPage){
    if(currentPage == 1) return;
    onClickToChangePage(currentPage - 1);
}
function onClickNext(totalPage){
    if(currentPage == totalPage ) return;
    onClickToChangePage(currentPage + 1);
}
function searchCategory(){
    keySearch = $("#keySearch").val();
    currentPage = 1;
    category.getAllCategory(currentPage-1,pageSize,keySearch);
}

function updateFunction() {
    var submit = $("#form-update-category").valid();
    if (submit == false) {
        return;
    }
    event.preventDefault();
    const id = $("#id-update").val();
    const code = $("#code-update").val();
    const name = $("#name-update").val();
    const obj = {code,name,id}
    categoryUpdate.update(obj, id);
};
var categoryUpdate = {
    update: function (obj, id) {
        $.ajax({
            url: `/api/v1/categorys/${id}`
            , method: "PUT"
            , contentType: "application/json"
            , data: JSON.stringify(obj)
            , success: function (response, textStatus, xhr) {
                alert("Update successfully!");
                category.getAllCategory(currentPage-1,pageSize,keySearch);
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
var validateSave = $("#form-update-category").validate(categoryUpdate.validate);


