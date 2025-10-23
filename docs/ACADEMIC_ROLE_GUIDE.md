# Hướng dẫn Role Academic - Sinh viên

## 📱 Giới thiệu các màn hình chính

### **🏠 TRANG CHỦ (Home Dashboard)**
**Màn hình học tập** của sinh viên, cung cấp tổng quan về tiến độ học tập và thông báo từ giảng viên.

#### **Chức năng chính:**
- **Dashboard học tập**: Tổng quan về khóa học ESG đang tham gia
- **Tiến độ học tập**: Hiển thị % hoàn thành các bài học và bài tập
- **Điểm số hiện tại**: Điểm trung bình của các bài tập đã nộp
- **Thông báo từ giảng viên**: Tin nhắn, nhắc nhở, feedback từ giảng viên

#### **Khi nào sử dụng:**
- Mỗi khi đăng nhập để xem tiến độ học tập
- Khi muốn kiểm tra điểm số hiện tại
- Khi cần xem thông báo mới từ giảng viên
- Khi muốn theo dõi tiến độ hoàn thành khóa học

#### **Ví dụ thực tế:**
"Sinh viên xem dashboard và thấy đã hoàn thành 60% khóa học ESG, điểm trung bình 8.5/10, có 2 thông báo mới từ giảng viên."

---

### **🎓 HỌC LIỆU (Learning Materials)**
**Thư viện học tập** nơi sinh viên truy cập bài học, tài liệu và video giảng dạy.

#### **Chức năng chính:**
- **Xem bài học**: Truy cập nội dung bài giảng với đa phương tiện
- **Tải tài liệu**: Download PDF, slide, tài liệu tham khảo
- **Video giảng dạy**: Xem video bài giảng trực tuyến
- **Ghi chú cá nhân**: Tạo ghi chú và highlight nội dung quan trọng

#### **Khi nào sử dụng:**
- Khi cần học bài mới
- Khi muốn ôn tập bài cũ
- Khi cần tải tài liệu để học offline
- Khi muốn xem lại video giảng dạy

#### **Ví dụ thực tế:**
"Sinh viên xem video bài học 'Phân tích báo cáo ESG' và tải về tài liệu hướng dẫn để tham khảo thêm."

---

### **📝 BÀI TẬP (Assignments)**
**Trung tâm bài tập** nơi sinh viên xem danh sách bài tập, nộp bài và xem điểm.

#### **Chức năng chính:**
- **Danh sách bài tập**: Xem tất cả bài tập được giao và hạn nộp
- **Nộp bài online**: Upload file bài làm và gửi cho giảng viên
- **Xem điểm**: Kiểm tra điểm số và feedback từ giảng viên
- **Lịch sử nộp bài**: Xem lại các bài tập đã nộp và điểm số

#### **Khi nào sử dụng:**
- Khi có bài tập mới được giao
- Khi cần nộp bài trước hạn
- Khi muốn xem điểm bài tập đã nộp
- Khi cần kiểm tra lịch sử nộp bài

#### **Ví dụ thực tế:**
"Sinh viên nộp bài tập 'Phân tích ESG của FPT Corporation' và nhận được điểm 9/10 với feedback tích cực từ giảng viên."

---

### **📋 ĐÁNH GIÁ (Assessment)**
**Hệ thống kiểm tra** nơi sinh viên làm bài kiểm tra và xem kết quả đánh giá.

#### **Chức năng chính:**
- **Làm bài kiểm tra**: Tham gia các bài test, quiz về ESG
- **Xem kết quả**: Kiểm tra điểm số và đáp án đúng
- **Phân tích điểm mạnh/yếu**: Xem phân tích chi tiết về kiến thức
- **Khuyến nghị cải thiện**: Nhận gợi ý để nâng cao kiến thức

#### **Khi nào sử dụng:**
- Khi có bài kiểm tra được giao
- Khi muốn tự đánh giá kiến thức
- Khi cần xem kết quả bài test
- Khi muốn biết điểm mạnh/yếu của mình

#### **Ví dụ thực tế:**
"Sinh viên làm bài kiểm tra ESG và nhận được 85%, hệ thống gợi ý cần cải thiện kiến thức về quản trị doanh nghiệp."

---

### **🔄 Chu trình học tập**

```
TRANG CHỦ → HỌC LIỆU → BÀI TẬP → ĐÁNH GIÁ
    ↓         ↓         ↓         ↓
  Theo dõi   Học kiến thức  Thực hành  Đánh giá
  tiến độ    từ tài liệu    bài tập    kết quả
```

**Ví dụ chu trình học tập:**
1. **Trang chủ**: Xem tiến độ học tập và thông báo mới
2. **Học liệu**: Học bài "Phân tích báo cáo ESG" qua video và tài liệu
3. **Bài tập**: Làm bài tập phân tích ESG của một doanh nghiệp
4. **Đánh giá**: Làm bài kiểm tra và nhận feedback để cải thiện

---

## 📊 Chi tiết màn hình và tính năng

### **📊 Màn hình chính (4 tabs):**

| Tab | Màn hình | Tính năng chính |
|-----|----------|-----------------|
| 🏠 **Trang chủ** | `StudentHomeScreen` | • Dashboard học tập<br>• Tiến độ học tập<br>• Điểm số hiện tại<br>• Thông báo từ giảng viên |
| 🎓 **Học liệu** | `StudentLearningScreen` | • Xem bài học<br>• Tải tài liệu<br>• Video giảng dạy<br>• Ghi chú cá nhân |
| 📝 **Bài tập** | `StudentAssignmentsScreen` | • Danh sách bài tập<br>• Nộp bài online<br>• Xem điểm<br>• Lịch sử nộp bài |
| 📋 **Đánh giá** | `StudentAssessmentScreen` | • Làm bài kiểm tra<br>• Xem kết quả<br>• Phân tích điểm mạnh/yếu<br>• Khuyến nghị cải thiện |

### **🔧 Tính năng đặc biệt:**
- **Assignment Submission**: Nộp bài tập với file đính kèm
- **Learning Progress**: Theo dõi tiến độ học tập
- **Grade History**: Xem lịch sử điểm số
- **Study Materials**: Truy cập tài liệu học tập

---

## 🔐 Tài khoản Demo

### **🎓 ACADEMIC - Sinh viên**

| Email | Mật khẩu | Tên | Trường | Gói dịch vụ |
|-------|----------|-----|--------|-------------|
| `student@demo.com` | `123456` | Nguyễn Thị Linh | Đại học Kinh tế Quốc dân | Free |
| `student2@demo.com` | `123456` | Trần Đức Minh | Đại học Bách khoa Hà Nội | Free |

---

## 📝 Hướng dẫn sử dụng

1. **Đăng nhập** bằng tài khoản Academic demo
2. **Khám phá** các tab chính: Trang chủ, Học liệu, Bài tập, Đánh giá
3. **Học tập** qua video và tài liệu trong mục Học liệu
4. **Làm bài tập** và nộp bài trong mục Bài tập
5. **Đánh giá** kiến thức qua các bài kiểm tra

---

## 🎯 Lợi ích cho Sinh viên

- **Học tập linh hoạt**: Truy cập tài liệu học tập mọi lúc mọi nơi
- **Theo dõi tiến độ**: Biết rõ tiến độ học tập và điểm số
- **Tương tác với giảng viên**: Nhận feedback và hướng dẫn kịp thời
- **Phát triển kỹ năng**: Học về ESG thực tế qua case study
- **Chuẩn bị nghề nghiệp**: Nắm vững kiến thức ESG cho công việc tương lai
