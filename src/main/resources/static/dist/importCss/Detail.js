var url = document.location.pathname;
var id = url.substring(url.lastIndexOf('/') + 1);
$(document).ready(function () {
    function fetchProductDetails(id) {
        $.ajax({
            url: `/api/SPCT/Detail/` + id, // Thay thế bằng endpoint API của bạn
            method: 'GET',
            dataType: 'json',
            success: function (data) {
                // Ghi dữ liệu vào console để gỡ lỗi
                console.log('Dữ liệu sản phẩm:', data);
                // Kiểm tra xem URL hình ảnh có chính xác không
                if (data.hinhAnh && data.hinhAnh.duongDan) {
                    let imageUrl = data.hinhAnh.duongDan;
                    console.log('URL hình ảnh:', imageUrl);
                    // Nếu đường dẫn không bắt đầu bằng http:// hoặc https://, thêm tiền tố
                    if (!imageUrl.startsWith('http://') && !imageUrl.startsWith('https://')) {
                        imageUrl = `http://localhost:8080/${imageUrl}`;
                    }
                    $('#productImage').attr('src', imageUrl);
                } else {
                    console.error('URL hình ảnh không hợp lệ:', data.hinhAnh);
                }
                $('#productName').text(data.sanPham.tenSanPham);
                $('#productOldPrice').text(`$${(data.giaSanPham * 1.2).toFixed(2)}`); // Giả sử giá cũ cao hơn 20%
                $('#productPrice').text(`$${data.giaSanPham.toFixed(2)}`);
                $('#productDescription').html(`Mô Tả : ${data.moTa}<br>Kích Cỡ : ${data.kichCo.tenKichCo}<br>NSX : ${data.nsx.ten}`);
                // Set the value of the hidden input field with id 'sanPham'
                $('#sanPham').val(data.id);
            },
            error: function (err) {
                console.error('Lỗi khi lấy chi tiết sản phẩm:', err);
            }
        });
    }

    // Gọi ví dụ để lấy chi tiết sản phẩm cho một ID sản phẩm cụ thể
    fetchProductDetails(id); // Thay thế bằng ID sản phẩm thực tế
});
$(document).ready(function () {
    // Function to fetch top 4 products
    function fetchTop4Products() {
        $.ajax({
            url: '/api/SPCT/top4', // Endpoint API của bạn
            method: 'GET',
            dataType: 'json',
            success: function (data) {
                // Ghi dữ liệu vào console để gỡ lỗi
                console.log('Dữ liệu sản phẩm:', data);

                // Lặp qua danh sách sản phẩm và tạo HTML cho mỗi sản phẩm

                var productContainer = $('#productContainer');
                productContainer.empty(); // Xóa nội dung cũ (nếu có)

                data.forEach(function (product) {
                    var productCard = `
                            <div class="col mb-5">
                                <div class="card h-100 card-custom">
                                    <!-- Product image-->
                                    <img class="card-img-top card-img-top-custom" src="http://localhost:8080/${product.hinhAnh.duongDan}" alt="..."/>
                                    <!-- Product details-->
                                    <div class="card-body p-4 card-body-custom">
                                        <div class="text-center">
                                            <!-- Product name-->
                                            <h5 class="fw-bolder">${product.sanPham.tenSanPham}</h5>
                                            <!-- Product price-->
                                            <div>${product.giaSanPham}</div>
                                        </div>
                                    </div>
                                    <div class="card-footer p-4 pt-0 border-top-0 bg-transparent card-footer-custom">
                                        <div class="text-center">
                                            <a class="btn btn-outline-dark mt-auto" href="/DetailSP/${product.id}">View options</a>
                                        </div>
                                    </div>
                                </div>
                            </div>`;
                    productContainer.append(productCard);
                });
            },
            error: function (err) {
                console.error('Lỗi khi lấy danh sách sản phẩm:', err);
            }
        });
    }

    // Gọi hàm fetchTop4Products khi trang được tải
    fetchTop4Products();
});
$(document).ready(function () {
    // Lấy userId từ phần tử input
    var userId = $('#userId').val();
    console.log('Đã lấy userId:', userId);
    console.log('Kiểu dữ liệu của userId:', typeof userId);
    // Kiểm tra giá trị của userId
    // Kiểm tra giá trị của userId
    if (userId === null || userId === "" || userId === "null" || userId === undefined) {
        console.log('UserId is null, empty, undefined, or "null" string, redirecting to login page');
        // window.location.href = "http://localhost:8080/dang-nhap";
        return;
    }
    // Tiếp tục nếu userId hợp lệ
    $.ajax({
        url: '/api/GH/user/' + userId,
        type: 'GET',
        success: function (response) {
            console.log('Phản hồi từ API:', response); // Ghi log phản hồi từ API
            if (response && response.id) {
                $('#gioHang').val(response.id); // Gán giá trị id cho trường nhập gioHang
            } else {
                console.error('Phản hồi không như mong đợi:', response);
            }
        },
        error: function (xhr) {
            console.error('Đã xảy ra lỗi khi tải giỏ hàng:', xhr);
            alert('Đã xảy ra lỗi khi tải giỏ hàng. Vui lòng thử lại.');
        }
    });
});
$(document).ready(function () {
    $('#btnAddCart').on('click', function () {
        var gioHang = $('#gioHang').val();
        var sanPham = $('#sanPham').val();
        var soLuong = $('#soLuong').val();
        if (!gioHang || !sanPham) {
            alert('GioHang và SanPhamChiTiet không được để trống');
            return;
        }
        var data = {
            gioHang: gioHang,
            sanPhamChiTiet: sanPham,
            soLuong: parseInt(soLuong),  // Đảm bảo số lượng là số nguyên
            trangThai: 1  // Thêm trạng thái nếu cần thiết
        };
        $.ajax({
            url: '/api/GHCT',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function (response) {
                console.log('Sản phẩm đã được thêm vào giỏ hàng:', response);
                alert('Sản phẩm đã được thêm vào giỏ hàng!');
            },
            error: function (xhr, status, error) {
                console.error('Lỗi khi thêm sản phẩm vào giỏ hàng:', xhr, status, error);
                alert('Đã xảy ra lỗi khi thêm sản phẩm vào giỏ hàng. Vui lòng thử lại.');
                alert('Vượt quá số lượng sản phẩm');
            }
        });
    });
});
