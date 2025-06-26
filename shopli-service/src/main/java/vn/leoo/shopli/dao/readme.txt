 DAO để viết SQL custom (sử dụng EntityManager, JdbcTemplate,...)
 *******************Querydsl*******************************************
 | Đặc điểm                      | `Projections.fields()`            | `Projections.bean()`         | `Projections.constructor()`       |
| ----------------------------- | --------------------------------- | ---------------------------- | --------------------------------- |
| **Ánh xạ theo**               | Tên field                         | Tên property (getter/setter) | Thứ tự & kiểu tham số constructor |
| **Yêu cầu setter**            | ✅ Có                              | ✅ Bắt buộc                   | ❌ Không cần (chỉ cần constructor) |
| **Truy cập field/private**    | Truy cập trực tiếp field          | Truy cập thông qua setter    | Gọi constructor                   |
| **Phù hợp với DTO dạng**      | POJO với public field hoặc setter | POJO chuẩn JavaBean          | Immutable DTO                     |
| **Tính an toàn khi refactor** | ❌ Dễ lỗi nếu đổi tên field        | ✅ An toàn hơn (qua method)   | ✅ An toàn hơn                     |
| **Lỗi phổ biến**              | Không set được nếu sai field name | Lỗi nếu không có setter đúng | Lỗi nếu thiếu constructor phù hợp |
 