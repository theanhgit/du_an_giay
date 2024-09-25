var user = {
    getAllUser: function () {
        $.ajax({
            "url": "/api/v1/users/getUsers"
            , "method": "GET"
            , "success": function (response, textStatus, xhr) {
                displayUsers(response.data);
            }
            , "error": function (error) {
                alert("Fail");
            }
        })
    }
}

function displayUsers(data) {
    const tableBody = $("#userTable tbody");
    tableBody.empty();
    data.map((user) => {
        const row = $("<tr>");
        const username = $("<td>").text(user.username);
        const email = $("<td>").text(user.email);
        const phone = $("<td>").text(user.phone);
        const fullName = $("<td>").text(user.fullName);
        const role = $("<td>").text(user.role);
        const editPage = $("<a>").attr("href", "/view/user/edit/" + user.username).addClass("btn btn-warning").css("margin-right", "5px").html("<i class=\"fa fa-edit\" style=\"font-size:14px\"></i>");
        const deleteUser = $("<a>").addClass("btn btn-danger")
            .html("<i class=\"fa fa-trash\" style=\"font-size:14px\"></i>")
            .on("click", function () {
                $("#confirmDeleteModal").modal("show");
                $("#confirmDelButton").on("click", function() {
                    $("#confirmDeleteModal").modal("hide");
                    deleteUserByUsername(user.username);
                });
            });
        const action = $("<td>").append(editPage, deleteUser);
        row.append(username, email, phone, fullName, role, action);
        tableBody.append(row);
    });
}

user.getAllUser();

function deleteUserByUsername(curUsername) {
    console.log(typeof curUsername)
    $.ajax({
        url: `/api/v1/users/${curUsername}`
        , method: "DELETE"
        , success: function () {
            alert("Delete successfully!");
        }
        , error: function (error) {
            alert("Delete fail!");
        }
    })
}
$.ajax({
    url: '/api/v1/roles', // Điều chỉnh đường dẫn API của bạn
    method: 'GET',
    success: function (data) {
        // Đổ dữ liệu vào dropdown
        var roleSelect = $('#role');
        $.each(data, function (index, role) {
            roleSelect.append('<option value="' + role.role + '">' + role.role + '</option>');
        });
    },
    error: function () {
        console.error('Failed to load roles from API.');
    }
});
