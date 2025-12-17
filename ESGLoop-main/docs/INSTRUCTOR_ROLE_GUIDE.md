# Hướng dẫn Role Instructor - Giảng viên

## 📱 Giới thiệu các màn hình chính

### **🏠 TRANG CHỦ (Dashboard)**
**Màn hình tổng quan** của giảng viên, hiển thị tình hình lớp học và hoạt động giảng dạy.

#### **Chức năng chính:**
- **Dashboard lớp học**: Tổng quan về các lớp học đang giảng dạy
- **Thống kê học sinh**: Số lượng học sinh, tình trạng hoạt động
- **Bài tập chưa chấm**: Danh sách bài tập cần chấm điểm
- **Điểm trung bình**: Thống kê điểm số của học sinh theo lớp

#### **Khi nào sử dụng:**
- Mỗi khi đăng nhập để xem tình hình lớp học
- Khi cần kiểm tra bài tập chưa chấm
- Khi muốn xem tiến độ học tập của học sinh
- Khi cần chuẩn bị báo cáo cho nhà trường

#### **Ví dụ thực tế:**
"Giảng viên xem dashboard và thấy lớp ESG-101 có 25 học sinh, 12 bài tập chưa chấm, điểm trung bình 85%."

---

### **👥 HỌC SINH (Students Management)**
**Trung tâm quản lý học sinh** nơi giảng viên quản lý danh sách và theo dõi tiến độ học tập.

#### **Chức năng chính:**
- **Quản lý danh sách học sinh**: Xem danh sách, thêm/xóa học sinh
- **Thêm học sinh mới**: Mời học sinh tham gia lớp học
- **Xem chi tiết từng học sinh**: Tiến độ, điểm số, hoạt động
- **Theo dõi tiến độ**: Giám sát quá trình học tập của từng học sinh

#### **Khi nào sử dụng:**
- Khi bắt đầu học kỳ mới
- Khi cần thêm học sinh vào lớp
- Khi muốn xem tiến độ học tập chi tiết
- Khi cần liên hệ với học sinh cụ thể

#### **Ví dụ thực tế:**
"Giảng viên thêm học sinh mới Nguyễn Văn A vào lớp ESG-101 và xem tiến độ học tập của học sinh này."

---

### **📝 NỘI DUNG (Content Creation)**
**Công cụ tạo nội dung** giúp giảng viên tạo bài học, bài tập và câu hỏi cho học sinh.

#### **Chức năng chính:**
- **Tạo bài học mới**: Soạn nội dung bài giảng với đa phương tiện
- **Tạo bài tập**: Thiết kế bài tập với nhiều dạng câu hỏi
- **Tạo câu hỏi**: Xây dựng ngân hàng câu hỏi cho bài kiểm tra
- **Quản lý nội dung**: Tổ chức và cập nhật tài liệu học tập

#### **Khi nào sử dụng:**
- Khi chuẩn bị bài giảng mới
- Khi cần tạo bài tập cho học sinh
- Khi chuẩn bị bài kiểm tra
- Khi muốn cập nhật nội dung học tập

#### **Ví dụ thực tế:**
"Giảng viên tạo bài học 'Phân tích báo cáo ESG' với video, tài liệu PDF và bài tập thực hành."

---

### **📊 CHẤM BÀI (Grading)**
**Hệ thống chấm điểm** giúp giảng viên chấm bài và đánh giá học sinh một cách hiệu quả.

#### **Chức năng chính:**
- **Danh sách bài tập**: Xem tất cả bài tập đã giao và trạng thái nộp bài
- **Chấm bài tự động**: Hệ thống tự động chấm câu hỏi trắc nghiệm
- **Xem điểm chi tiết**: Phân tích điểm số và tiến độ học tập
- **Thống kê chấm bài**: Báo cáo về tình hình nộp bài và điểm số

#### **Khi nào sử dụng:**
- Khi học sinh nộp bài tập
- Khi cần chấm điểm bài tập
- Khi muốn xem thống kê điểm số
- Khi chuẩn bị báo cáo điểm cho học sinh

#### **Ví dụ thực tế:**
"Giảng viên chấm 25 bài tập nộp về 'Phân tích ESG của doanh nghiệp' và nhận thấy 80% học sinh đạt điểm khá giỏi."

---

### **🔄 Chu trình giảng dạy**

```
TRANG CHỦ → HỌC SINH → NỘI DUNG → CHẤM BÀI
    ↓         ↓         ↓         ↓
  Tổng quan  Quản lý   Tạo nội dung  Đánh giá
  lớp học    học sinh  học tập     kết quả
```

**Ví dụ chu trình giảng dạy:**
1. **Trang chủ**: Xem tình hình lớp ESG-101 với 25 học sinh
2. **Học sinh**: Thêm học sinh mới và xem tiến độ học tập
3. **Nội dung**: Tạo bài học mới về "Báo cáo ESG"
4. **Chấm bài**: Chấm điểm bài tập và đánh giá kết quả học tập

---

## 📊 Chi tiết màn hình và tính năng

### **📊 Màn hình chính (4 tabs):**

| Tab | Màn hình | Tính năng chính |
|-----|----------|-----------------|
| 🏠 **Trang chủ** | `InstructorDashboardScreen` | • Dashboard lớp học<br>• Thống kê học sinh<br>• Bài tập chưa chấm<br>• Điểm trung bình |
| 👥 **Học sinh** | `InstructorStudentsScreen` | • Quản lý danh sách học sinh<br>• Thêm học sinh mới<br>• Xem chi tiết từng học sinh<br>• Theo dõi tiến độ |
| 📝 **Nội dung** | `InstructorContentScreen` | • Tạo bài học mới<br>• Tạo bài tập<br>• Tạo câu hỏi<br>• Quản lý nội dung |
| 📊 **Chấm bài** | `InstructorGradingScreen` | • Danh sách bài tập<br>• Chấm bài tự động<br>• Xem điểm chi tiết<br>• Thống kê chấm bài |

### **🔧 Tính năng đặc biệt:**
- **Create Lesson**: Tạo bài học mới với nội dung phong phú
- **Create Assignment**: Tạo bài tập với nhiều dạng câu hỏi
- **Create Question**: Tạo câu hỏi cho bài kiểm tra
- **Assignment Grading**: Chấm bài chi tiết với feedback
- **Student Detail**: Xem chi tiết tiến độ từng học sinh
- **Grade Submission**: Chấm bài nộp của học sinh

---

## 🔐 Tài khoản Demo

### **👨‍🏫 INSTRUCTOR - Giảng viên**

| Email | Mật khẩu | Tên | Trường | Gói dịch vụ |
|-------|----------|-----|--------|-------------|
| `instructor@demo.com` | `123456` | TS. Nguyễn Thị Mai | Đại học Kinh tế Quốc dân | Enterprise |
| `teacher@demo.com` | `123456` | ThS. Trần Văn Hùng | Đại học Bách khoa Hà Nội | Pro |

---

## 📝 Hướng dẫn sử dụng

1. **Đăng nhập** bằng tài khoản Instructor demo
2. **Khám phá** các tab chính: Trang chủ, Học sinh, Nội dung, Chấm bài
3. **Quản lý học sinh** bằng cách thêm học sinh mới và theo dõi tiến độ
4. **Tạo nội dung** bài học, bài tập và câu hỏi cho học sinh
5. **Chấm bài** và đánh giá kết quả học tập của học sinh

---

## 🎯 Lợi ích cho Giảng viên

- **Quản lý lớp học hiệu quả**: Công cụ toàn diện để quản lý học sinh và nội dung
- **Tạo nội dung linh hoạt**: Dễ dàng tạo bài học, bài tập đa dạng
- **Chấm điểm tự động**: Tiết kiệm thời gian chấm bài trắc nghiệm
- **Theo dõi tiến độ**: Giám sát hiệu quả quá trình học tập của học sinh
- **Báo cáo chi tiết**: Thống kê và phân tích kết quả học tập
