# Dự án chưa có tên
# Liệt kê chức năng tạm thời:
Chức năng liên quan đến tài khoản người dùng
1. Đăng nhập bằng tài khoản GG, FB
2. Đăng kí và đăng nhập bằng phone => vì email có thể trùng với đăng nhập GG
3. Lấy lại mật khẩu gửi mail => chưa xác định
4. Sửa thông tin cá nhân
   
Chức năng liên quan đến nghe nhạc
1. Nghe nhạc *
2. Tìm kiếm nhạc (theo bài hát, nghệ sĩ,lời nhạc)=> tìm kiếm một danh sách nhạc có bài đầu tiên là bài mình cần và các bài sau đó là random theo nhiều gì đấy ...
   - Tìm kiếm theo playlist,  nghệ sĩ hoặc thể loại... thì sẽ có danh sách nhạc theo playlist, nghệ sĩ...
4. Bảng xếp hạng theo số lượng tim và luợt nghe *
5. Tạo danh sách nhạc yêu thích của bạn và thêm nhạc vào danh sách *
6. Điều chỉnh âm lượng - sử dụng điều chỉnh của máy
7. Thay đổi giao diện => 2 trạng thái (để sau)
8. Tua nhạc có lời chạy theo thời gian *
9. Tự động phát nhạc tiếp theo => khi nghe hết nhạc tự phát bài tiếp theo *
10. Chức năng vòng lặp, phát 1 lần => lặp lại danh sách phát nhạc hoặc lặp lại chỉ 1 lần *
11. Hẹn giờ phát nhạc 10p,30p,45p,60p,... (để sau)

Chức năng liên quan đến admin
1. Thêm người dùng
2. Xóa tài khoản ngừoi dùng
3. Tìm kiếm người dùng
4. Có full chức năng của ngừoi dùng
5. Thêm nhạc mới, nghệ sĩ mới (kiểm duyệt nhạc)
6. Xóa nhạc, xóa nghệ sĩ
7. Thống kê thông tin:
   - Lượt nghe trong tháng
   - Lượt người đăng kí
   - Bảng xếp hạng
   - Album
   - Bảng kiểm duyệt thêm nhạc của artist

Chức năng của artist:
   - Có các chức năng như 1 user
   - Trang riêng của artist có thống kê thông tin người nghe, thả tim
   - Thêm nhạc mới
      
Tính năng thêm chủ đề sáng tạo thêm
Lời chào khi vào app và thông báo thời tiết + danh sách nhạc gợi ý theo cảm xúc và thời tiết

Frontend
Admin sẽ là giao diện web 
Còn lại là giao diện mobile

Database => mysql
Backend java spring boot
Các công nghệ khác: firebase, redis, docker, git,...

Note lại thông tin họp:
1. Artist nhập form đăng kí và gửi mail để lấy tài khoản
