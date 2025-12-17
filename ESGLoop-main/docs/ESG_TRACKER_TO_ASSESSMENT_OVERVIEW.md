# Từ Hoạt Động Hằng Ngày đến Đánh Giá ESG Định Kỳ
## Hệ thống Tự động Tổng hợp và Đề xuất Điểm Đánh Giá

**Phiên bản:** 1.0  
**Ngày:** Tháng 10/2025

---

## 📋 Mục Lục

1. [Tổng Quan](#1-tổng-quan)
2. [Mối Quan Hệ Giữa Tracker và Assessment](#2-mối-quan-hệ-giữa-tracker-và-assessment)
3. [Quy Trình Tự Động Hóa](#3-quy-trình-tự-động-hóa)
4. [Ví Dụ Cụ Thể](#4-ví-dụ-cụ-thể)
5. [Lợi Ích](#5-lợi-ích)

---

## 1. Tổng Quan

### 1.1 Vấn Đề Hiện Tại

**Cách làm truyền thống:**
- Mỗi quý, doanh nghiệp phải ngồi trả lời 60 câu hỏi đánh giá ESG
- Dựa vào trí nhớ và ước lượng → Không chính xác
- Tốn nhiều thời gian (3-5 ngày)
- Khó có bằng chứng minh chứng
- Dễ bị đánh giá thấp hoặc không khách quan

### 1.2 Giải Pháp Mới

**Tự động từ dữ liệu thực tế:**
- Doanh nghiệp ghi nhận hoạt động ESG hằng ngày (Tracker)
- Cuối quý, hệ thống tự động tổng hợp
- AI phân tích và đề xuất điểm cho 60 câu hỏi
- Doanh nghiệp chỉ cần xem lại và xác nhận
- Tiết kiệm 80% thời gian, chính xác 100%

---

## 2. Mối Quan Hệ Giữa Tracker và Assessment

### 2.1 Tracker (Theo Dõi Hằng Ngày)

**Mục đích:** Ghi nhận mọi hoạt động ESG mà doanh nghiệp thực hiện

**Tần suất:** Hằng ngày, hằng tuần, hằng tháng (liên tục)

**Nội dung:** Các hoạt động cụ thể

```
📅 Ngày 15/10/2025
✅ Hoạt động: "Mở chi nhánh mới tại xã Tân Phong, Hà Giang"
   🌍 Pillar: Social (Xã hội)
   📝 Mô tả: Chi nhánh thứ 8 tại vùng nông thôn
   💰 Ngân sách: 500 triệu VND
   📊 Số liệu:
      - Tổng chi nhánh nông thôn: 8 (tăng từ 3)
      - Tỷ lệ bao phủ: 16% (tăng từ 6%)
      - Dân số phục vụ: +15,000 người
   📸 Minh chứng: 25 ảnh + Giấy phép

📅 Ngày 20/10/2025
✅ Hoạt động: "Lắp đặt hệ thống pin mặt trời"
   🌍 Pillar: Environmental (Môi trường)
   📝 Mô tả: Lắp 200 tấm pin tại chi nhánh Đà Nẵng
   💰 Chi phí: 800 triệu VND
   📊 Số liệu:
      - Công suất: 100 kW
      - Tiết kiệm điện: 150,000 kWh/năm
      - Giảm CO2: 75 tấn/năm
   📸 Minh chứng: Hóa đơn + Báo cáo kỹ thuật
```

---

### 2.2 Assessment (Đánh Giá Định Kỳ)

**Mục đích:** Đánh giá tổng thể năng lực ESG theo tiêu chuẩn quốc tế

**Tần suất:** Mỗi quý 1 lần (Q1, Q2, Q3, Q4)

**Nội dung:** 60 câu hỏi tiêu chuẩn cho ngân hàng

```
🌱 ENVIRONMENTAL (20 câu)
   ├─ Đa dạng sinh học (6 câu)
   ├─ Biến đổi khí hậu (7 câu)
   └─ Nhiên liệu hóa thạch (7 câu)

👥 SOCIAL (20 câu)
   ├─ Hòa nhập tài chính (5 câu)
   ├─ Quyền con người (10 câu)
   └─ Quyền lao động (5 câu)

🏛️ GOVERNANCE (20 câu)
   ├─ Bảo vệ khách hàng (5 câu)
   └─ Minh bạch (15 câu)
```

**Mỗi câu có 4 lựa chọn:**
- ✅ Đã thực hiện đầy đủ (4 điểm)
- ⚡ Thực hiện một phần (2 điểm)
- ❌ Chưa thực hiện (0 điểm)
- ⊘ Không áp dụng (0 điểm)

---

### 2.3 Mối Liên Hệ

```
┌─────────────────────────────────────────────────────┐
│           TRACKER (Hằng ngày)                       │
│                                                     │
│  Tháng 7: 45 hoạt động ESG                         │
│  Tháng 8: 52 hoạt động ESG                         │
│  Tháng 9: 48 hoạt động ESG                         │
│  ─────────────────────────────                     │
│  Tổng Q3: 145 hoạt động                            │
└──────────────────┬──────────────────────────────────┘
                   │
                   │ AI phân tích & kết nối
                   │
                   ↓
┌─────────────────────────────────────────────────────┐
│      MAPPING (Liên kết tự động)                     │
│                                                     │
│  Câu hỏi soc_002:                                   │
│  "Ngân hàng có chi nhánh ở nông thôn không?"      │
│                                                     │
│  ← Liên kết với ←                                   │
│                                                     │
│  8 hoạt động từ Tracker:                           │
│  ✅ "Mở chi nhánh Hà Giang"                        │
│  ✅ "Mở chi nhánh Cao Bằng"                        │
│  ✅ ... (6 hoạt động khác)                         │
│                                                     │
│  Tổng hợp:                                          │
│  - Chi nhánh nông thôn: 3 → 10 (+233%)            │
│  - Tỷ lệ bao phủ: 6% → 20%                        │
│  - Đạt mục tiêu: 100%                              │
└──────────────────┬──────────────────────────────────┘
                   │
                   │ Tính toán điểm số
                   │
                   ↓
┌─────────────────────────────────────────────────────┐
│        ASSESSMENT (Điểm đề xuất)                    │
│                                                     │
│  Câu hỏi soc_002:                                   │
│  "Ngân hàng có chi nhánh ở nông thôn không?"      │
│                                                     │
│  🤖 AI đề xuất: ✅ Đã thực hiện đầy đủ (4 điểm)   │
│                                                     │
│  📊 Căn cứ:                                         │
│  - Tỷ lệ 20% vượt ngưỡng 20%                       │
│  - Có 8 hoạt động minh chứng                       │
│  - 100% đạt mục tiêu                                │
│  - 95% độ tin cậy                                   │
│                                                     │
│  👤 Doanh nghiệp: [✓ Đồng ý] [✏ Điều chỉnh]       │
└─────────────────────────────────────────────────────┘
```

---

## 3. Quy Trình Tự Động Hóa

### 3.1 Luồng Dữ Liệu Tổng Thể

```
BƯỚC 1: GHI NHẬN HẰNG NGÀY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Nhân viên ghi nhận:
├─ Hoạt động: "Mở chi nhánh Hà Giang"
├─ Số liệu: Chi nhánh nông thôn tăng lên 8
├─ Minh chứng: Upload ảnh, tài liệu
└─ Trạng thái: Hoàn thành

          ↓

BƯỚC 2: LƯU TRỮ & PHÂN LOẠI (Tự động)
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Hệ thống lưu trữ:
├─ Thông tin hoạt động
├─ Số liệu đo lường
├─ Minh chứng
└─ Thời gian (Q3/2025)

          ↓

BƯỚC 3: TÍCH LŨY CẢ QUÝ
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Q3/2025:
├─ Tháng 7: 45 hoạt động
├─ Tháng 8: 52 hoạt động
└─ Tháng 9: 48 hoạt động
    ────────────────────
    Tổng: 145 hoạt động

          ↓

BƯỚC 4: AI PHÂN TÍCH (Cuối quý - Tự động)
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

AI thực hiện:
├─ Đọc 60 câu hỏi đánh giá
├─ Phân tích 145 hoạt động
├─ Tìm liên kết: Hoạt động nào → Câu hỏi nào
├─ Tính toán điểm số dựa trên số liệu
└─ Đánh giá độ tin cậy

Ví dụ:
┌─────────────────────────────────────────┐
│ Câu hỏi soc_002:                        │
│ "Có chi nhánh nông thôn?"              │
│                                         │
│ AI tìm thấy:                            │
│ ✓ 8 hoạt động liên quan                │
│ ✓ Chi nhánh nông thôn: 3 → 10         │
│ ✓ Tỷ lệ: 20% (vượt ngưỡng)            │
│                                         │
│ → Đề xuất: 4 điểm (Đầy đủ)            │
│ → Độ tin cậy: 95%                      │
└─────────────────────────────────────────┘

          ↓

BƯỚC 5: TỔNG HỢP KẾT QUẢ (Tự động)
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Hệ thống tạo báo cáo:
├─ 60 câu hỏi đã được điền
├─ Mỗi câu có điểm đề xuất
├─ Kèm căn cứ và minh chứng
└─ So sánh với quý trước

          ↓

BƯỚC 6: DOANH NGHIỆP XEM LẠI
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Màn hình đánh giá:
┌─────────────────────────────────────────┐
│ Q3/2025 - 60 câu hỏi                    │
│                                         │
│ ✅ 45 câu: Đồng ý với AI (Xanh)        │
│ ✏️ 12 câu: Cần xem lại (Vàng)          │
│ ❌ 3 câu: Không có dữ liệu (Đỏ)        │
│                                         │
│ Thao tác:                               │
│ - Click "Đồng ý" cho câu đúng          │
│ - Click "Điều chỉnh" nếu cần sửa       │
│ - Thêm ghi chú nếu cần                  │
└─────────────────────────────────────────┘

          ↓

BƯỚC 7: CHUYÊN GIA XÁC MINH (Tùy chọn)
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Chuyên gia kiểm tra:
├─ Xem minh chứng
├─ Kiểm tra số liệu
├─ Phê duyệt điểm số
└─ Ký xác nhận

          ↓

BƯỚC 8: BÁO CÁO CUỐI CÙNG
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Xuất báo cáo:
├─ PDF (Báo cáo ESG chính thức)
├─ GRI (Chuẩn quốc tế)
├─ SASB (Chuẩn tài chính)
└─ TCFD (Chuẩn khí hậu)
```

---

### 3.2 Quy Tắc Kết Nối (Mapping)

**AI sử dụng 3 phương pháp để kết nối:**

#### **Phương pháp 1: Kết nối trực tiếp**

```
Câu hỏi: "Có chi nhánh ở nông thôn không?"
    ↓
Tìm hoạt động:
    - Loại: "Phát triển địa phương"
    - Từ khóa: "nông thôn", "rural", "chi nhánh"
    - Số liệu: "chi nhánh nông thôn"
    ↓
Tìm thấy: 8 hoạt động
    ↓
Tính điểm: 
    - Tỷ lệ 20% ≥ ngưỡng 20%
    → 4 điểm
```

#### **Phương pháp 2: Kết nối gián tiếp**

```
Câu hỏi: "Có mục tiêu giảm phát thải CO2?"
    ↓
Tìm hoạt động:
    - Pin mặt trời → Giảm CO2
    - Đèn LED → Giảm CO2
    - Xe điện → Giảm CO2
    ↓
Tổng hợp: 150 tấn CO2 giảm
    ↓
So sánh mục tiêu: 
    - Target: 200 tấn
    - Actual: 150 tấn (75%)
    → 2 điểm (Một phần)
```

#### **Phương pháp 3: Tích lũy nhiều hoạt động**

```
Câu hỏi: "Ngân hàng minh bạch về hoạt động?"
    ↓
Tìm hoạt động:
    - Công bố báo cáo tài chính
    - Công bố danh sách đầu tư
    - Công bố chính sách ESG
    - Tổ chức họp cổ đông
    ↓
Đếm: 12 hoạt động minh bạch
    ↓
Đánh giá:
    - Có báo cáo định kỳ ✓
    - Có xác minh bên thứ 3 ✓
    - Công khai trên website ✓
    → 4 điểm
```

---

### 3.3 Tính Điểm Thông Minh

**Công thức tổng quát:**

```
ĐIỂM = f(Hoạt động, Số liệu, Mục tiêu, Ngưỡng)

Trong đó:
├─ Hoạt động: Đã làm gì?
├─ Số liệu: Kết quả đo được?
├─ Mục tiêu: Đặt ra là bao nhiêu?
└─ Ngưỡng: Chuẩn quốc tế là bao nhiêu?
```

**Ví dụ cụ thể:**

```
Câu hỏi soc_002: "Có chi nhánh nông thôn?"

Ngưỡng quốc tế:
├─ 4 điểm: ≥ 20% chi nhánh ở nông thôn
├─ 2 điểm: 10-19% 
└─ 0 điểm: < 10%

Dữ liệu từ Tracker:
├─ Q2/2025: 3 chi nhánh / 50 = 6%
├─ Q3/2025: 10 chi nhánh / 50 = 20%
└─ Tăng trưởng: +233%

Kết quả:
├─ 20% ≥ ngưỡng 20% → 4 điểm ✓
├─ Có 8 hoạt động minh chứng ✓
└─ Độ tin cậy: 95%

→ ĐỀ XUẤT: 4 điểm (Đã thực hiện đầy đủ)
```

---

## 4. Ví Dụ Cụ Thể

### 4.1 Timeline: Từ Tháng 7 đến Tháng 10

#### **📅 Tháng 7/2025: Lập kế hoạch**

```
Ngày 01/07: Họp ban lãnh đạo
├─ Quyết định: Mở rộng mạng lưới nông thôn
├─ Mục tiêu: 10 chi nhánh (20% tổng số)
└─ Ngân sách: 5 tỷ đồng

Ngày 05/07: Tạo hoạt động trong Tracker
┌─────────────────────────────────────────┐
│ 📝 Hoạt động: Mở rộng chi nhánh nông thôn│
│ 📅 Kế hoạch: Q3/2025                    │
│ 🎯 Mục tiêu: 10 chi nhánh (20%)         │
│ 💰 Ngân sách: 5 tỷ đồng                 │
│ 🏷️ Loại: Phát triển địa phương          │
│ 🌍 ESG: Social (Xã hội)                 │
│ ⏸️ Trạng thái: Đang thực hiện            │
└─────────────────────────────────────────┘
```

#### **📅 Tháng 8/2025: Thực hiện**

```
Ngày 10/08: Mở chi nhánh #1 (Hà Giang)
├─ Upload 15 ảnh lễ khai trương
├─ Upload giấy phép kinh doanh
└─ Cập nhật: 4/10 chi nhánh

Ngày 15/08: Mở chi nhánh #2 (Cao Bằng)
├─ Upload 12 ảnh
├─ Upload hợp đồng thuê mặt bằng
└─ Cập nhật: 5/10 chi nhánh

Ngày 22/08: Mở chi nhánh #3 (Lai Châu)
├─ Upload 18 ảnh
├─ Upload giấy phép
└─ Cập nhật: 6/10 chi nhánh

Ngày 25/08: Cập nhật số liệu
┌─────────────────────────────────────────┐
│ 📊 Tiến độ: 60% (6/10 chi nhánh)       │
│ 💰 Chi phí: 3 tỷ / 5 tỷ                │
│ 📈 Tỷ lệ bao phủ: 12% (6/50)           │
│ 👥 Dân số phục vụ: +45,000 người        │
└─────────────────────────────────────────┘
```

#### **📅 Tháng 9/2025: Hoàn thành**

```
Ngày 05/09: Mở chi nhánh #4 (Sơn La)
Ngày 12/09: Mở chi nhánh #5 (Điện Biên)
Ngày 20/09: Mở chi nhánh #6 (Yên Bái)
Ngày 28/09: Mở chi nhánh #7 (Tuyên Quang)

Ngày 30/09: Hoàn thành dự án
┌─────────────────────────────────────────┐
│ ✅ Hoàn thành: 10/10 chi nhánh          │
│ 💰 Chi phí: 4.8 tỷ / 5 tỷ (Tiết kiệm)  │
│ 📈 Tỷ lệ: 20% (10/50) ✓                │
│ 👥 Dân số: +75,000 người                 │
│ 📸 Minh chứng: 127 ảnh + 20 tài liệu   │
│ ⏸️ Trạng thái: HOÀN THÀNH ✅             │
└─────────────────────────────────────────┘
```

#### **📅 Ngày 01/10: AI Phân Tích**

```
Hệ thống tự động chạy:

🤖 AI đang phân tích Q3/2025...

Bước 1: Đọc 60 câu hỏi đánh giá ✓
Bước 2: Đọc 145 hoạt động trong Q3 ✓
Bước 3: Tìm liên kết... ✓

Tìm thấy:
├─ Câu soc_002 ← 8 hoạt động chi nhánh
├─ Câu soc_004 ← 3 hoạt động cho vay SME
├─ Câu env_007 ← 12 hoạt động giảm carbon
└─ ... (57 câu khác)

Bước 4: Tính điểm...

Câu soc_002:
├─ Dữ liệu: 10 chi nhánh nông thôn (20%)
├─ Ngưỡng: ≥20% → 4 điểm
├─ Minh chứng: 127 ảnh + 20 tài liệu
└─ Độ tin cậy: 95%

→ ĐỀ XUẤT: 4 điểm ✅

Hoàn thành: 60/60 câu ✓
```

#### **📅 Ngày 05/10: Doanh Nghiệp Review**

```
Giám đốc ESG đăng nhập:

┌─────────────────────────────────────────┐
│ 🎯 Đánh giá ESG Q3/2025                 │
│                                         │
│ Tổng quan:                              │
│ ✅ Đã phân tích: 60/60 câu             │
│ 📊 Điểm trung bình: 3.2/4               │
│ 📈 So với Q2: +0.4 điểm                 │
│                                         │
│ Trạng thái:                             │
│ 🟢 Đồng ý: 45 câu (75%)                │
│ 🟡 Xem lại: 12 câu (20%)               │
│ 🔴 Không đủ dữ liệu: 3 câu (5%)        │
│                                         │
│ [📋 Xem chi tiết] [✓ Phê duyệt]        │
└─────────────────────────────────────────┘

Click vào soc_002:

┌─────────────────────────────────────────┐
│ Câu hỏi soc_002                         │
│ "Có chi nhánh ở nông thôn không?"      │
│                                         │
│ 🤖 AI ĐỀ XUẤT: 4 điểm (Đầy đủ)         │
│ 📊 Độ tin cậy: 95%                      │
│ 📈 So với Q2: +2 điểm                   │
│                                         │
│ 📋 Căn cứ:                              │
│ ├─ 8 hoạt động đã hoàn thành           │
│ ├─ 10 chi nhánh nông thôn (20%)        │
│ ├─ Đạt mục tiêu 100%                    │
│ └─ 147 minh chứng                       │
│                                         │
│ 💬 Ghi chú AI:                          │
│ "Tỷ lệ 20% vượt ngưỡng 20% cho điểm    │
│  tối đa. Có đầy đủ minh chứng. Đề xuất │
│  điểm 4."                               │
│                                         │
│ [✓ Đồng ý] [✏ Điều chỉnh] [👁 Chi tiết]│
└─────────────────────────────────────────┘

→ Click [✓ Đồng ý]
```

#### **📅 Ngày 10/10: Chuyên Gia Xác Minh**

```
Chuyên gia "Nguyễn Văn A" kiểm tra:

✓ Xem 127 ảnh chi nhánh
✓ Đọc 20 tài liệu pháp lý
✓ Kiểm tra công thức tính tỷ lệ
✓ So sánh với chuẩn quốc tế

Kết luận:
┌─────────────────────────────────────────┐
│ ✅ PHÊ DUYỆT                            │
│                                         │
│ Người xác minh: Nguyễn Văn A           │
│ Chức danh: Chuyên gia ESG cấp cao      │
│ Ngày: 10/10/2025                        │
│                                         │
│ Nhận xét:                               │
│ "Dữ liệu chính xác, minh chứng đầy đủ. │
│  Đạt chuẩn quốc tế. Điểm 4 hợp lý."    │
│                                         │
│ Chữ ký: [Đã ký điện tử]                │
└─────────────────────────────────────────┘
```

#### **📅 Ngày 15/10: Báo Cáo Cuối**

```
Hệ thống xuất báo cáo:

📄 BÁO CÁO ESG Q3/2025

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TÓM TẮT ĐIỂM SỐ
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

🌱 Environmental:  64/80  (80%)  ↑
👥 Social:         72/80  (90%)  ↑↑
🏛️ Governance:     56/80  (70%)  →
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
📊 TỔNG ĐIỂM:      192/240 (80%)  ↑
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

ĐIỂM NỔI BẬT Q3:
✅ Social tăng 20 điểm nhờ:
   - 10 chi nhánh nông thôn mới
   - 75,000 người được phục vụ
   - Tăng cho vay SME 15%

📤 Xuất ra:
├─ [📄 PDF] Báo cáo 45 trang
├─ [🌐 GRI] Chuẩn quốc tế
├─ [💼 SASB] Chuẩn tài chính
└─ [🌍 TCFD] Chuẩn khí hậu
```

---

### 4.2 Chi Tiết Mapping cho 5 Câu Hỏi Phổ Biến

#### **Câu 1: Chi nhánh nông thôn (soc_002)**

```
❓ CÂU HỎI
"Ngân hàng có chi nhánh ở vùng nông thôn không?"

📊 NGƯỠNG
├─ 4 điểm: ≥ 20% chi nhánh
├─ 2 điểm: 10-19%
└─ 0 điểm: < 10%

🔗 KẾT NỐI TỰ ĐỘNG
Tracker → Tìm theo:
├─ Loại: "LOCAL_DEVELOPMENT"
├─ Từ khóa: "nông thôn", "rural"
├─ Số liệu: "rural_branches"
└─ Pillar: SOCIAL

📈 DỮ LIỆU THỰC TẾ
Q3 có 8 hoạt động:
├─ Mở chi nhánh Hà Giang ✓
├─ Mở chi nhánh Cao Bằng ✓
├─ ... (6 hoạt động khác)
└─ Kết quả: 10/50 = 20%

🎯 KẾT QUẢ
20% = ngưỡng 20% → 4 điểm ✅
```

#### **Câu 2: Giảm phát thải (env_007)**

```
❓ CÂU HỎI
"Có mục tiêu giảm phát thải CO2 phù hợp với 1.5°C không?"

📊 NGƯỠNG
├─ 4 điểm: Có target + đang đạt
├─ 2 điểm: Có target + chưa đạt
└─ 0 điểm: Không có target

🔗 KẾT NỐI TỰ ĐỘNG
Tracker → Tìm theo:
├─ Loại: "RENEWABLE_ENERGY", "ENERGY_EFFICIENCY", "CARBON_REDUCTION"
├─ Số liệu: "co2_saved_tons"
└─ Pillar: ENVIRONMENTAL

📈 DỮ LIỆU THỰC TẾ
Q3 có 12 hoạt động:
├─ Pin mặt trời → 75 tấn CO2
├─ Đèn LED → 50 tấn CO2
├─ Xe điện → 25 tấn CO2
└─ Tổng: 150 tấn CO2

Mục tiêu năm: 200 tấn
Đạt được: 150/200 = 75%

🎯 KẾT QUẢ
Có target + đạt 75% → 2 điểm ⚡
(Cần thêm 50 tấn để được 4 điểm)
```

#### **Câu 3: Minh bạch báo cáo (gov_019)**

```
❓ CÂU HỎI
"Có xuất bản báo cáo ESG theo chuẩn quốc tế không?"

📊 NGƯỠNG
├─ 4 điểm: Có báo cáo + xác minh
├─ 2 điểm: Có báo cáo + không xác minh
└─ 0 điểm: Không có báo cáo

🔗 KẾT NỐI TỰ ĐỘNG
Tracker → Tìm theo:
├─ Loại: "TRANSPARENCY_REPORT"
├─ Từ khóa: "report", "báo cáo", "công bố"
└─ Pillar: GOVERNANCE

📈 DỮ LIỆU THỰC TẾ
Q3 có 4 hoạt động:
├─ Xuất bản báo cáo ESG Q2 ✓
├─ Xác minh bởi PwC ✓
├─ Công bố trên website ✓
└─ Họp báo giới thiệu ✓

🎯 KẾT QUẢ
Có báo cáo + có xác minh → 4 điểm ✅
```

#### **Câu 4: Đào tạo nhân viên (soc_016)**

```
❓ CÂU HỎI
"Có tôn trọng quyền lao động ILO không?"

📊 NGƯỠNG
├─ 4 điểm: Đầy đủ chính sách + thực thi
├─ 2 điểm: Có chính sách + chưa đầy đủ
└─ 0 điểm: Không có chính sách

🔗 KẾT NỐI TỰ ĐỘNG
Tracker → Tìm theo:
├─ Loại: "EMPLOYEE_TRAINING", "SAFETY_IMPROVEMENT"
├─ Từ khóa: "đào tạo", "training", "ILO"
└─ Pillar: SOCIAL

📈 DỮ LIỆU THỰC TẾ
Q3 có 6 hoạt động:
├─ Đào tạo ESG cho 500 nhân viên ✓
├─ Cập nhật quy chế lao động ✓
├─ Kiểm tra an toàn lao động ✓
├─ Họp công đoàn ✓
└─ Tỷ lệ đào tạo: 500/600 = 83%

🎯 KẾT QUẢ
Có chính sách + tỷ lệ 83% → 4 điểm ✅
```

#### **Câu 5: Cho vay SME (soc_004)**

```
❓ CÂU HỎI
"Tỷ lệ cho vay SME có trên 10% không?"

📊 NGƯỠNG
├─ 4 điểm: ≥ 10% tổng dư nợ
├─ 2 điểm: 5-9%
└─ 0 điểm: < 5%

🔗 KẾT NỐI TỰ ĐỘNG
Tracker → Tìm theo:
├─ Loại: "LOCAL_DEVELOPMENT", "EDUCATION_SUPPORT"
├─ Từ khóa: "SME", "MSME", "doanh nghiệp nhỏ"
├─ Số liệu: "sme_loan_ratio"
└─ Pillar: SOCIAL

📈 DỮ LIỆU THỰC TẾ
Q3 có 5 hoạt động:
├─ Chương trình cho vay SME ưu đãi ✓
├─ Mở 10 chi nhánh nông thôn (hỗ trợ SME) ✓
└─ Kết quả: Tỷ lệ 12% (tăng từ 8%)

🎯 KẾT QUẢ
12% > 10% → 4 điểm ✅
```

---

## 5. Lợi Ích

### 5.1 Cho Doanh Nghiệp

#### **⏱️ Tiết kiệm Thời Gian**

**Trước đây:**
```
Cuối quý → Họp 2 ngày để nhớ lại
         → 3 ngày điền form
         → 2 ngày tìm minh chứng
         → 1 ngày review
         ─────────────────────────
         Tổng: 8 ngày làm việc
```

**Bây giờ:**
```
Cuối quý → Mở app
         → Xem 60 câu đã điền sẵn
         → Click "Đồng ý" cho câu đúng
         → Điều chỉnh vài câu nếu cần
         ─────────────────────────
         Tổng: 2 giờ
```

**→ Tiết kiệm 95% thời gian**

---

#### **📊 Chính Xác Hơn**

**Trước đây:**
```
❌ Dựa vào trí nhớ
❌ Ước lượng con số
❌ Không có minh chứng rõ ràng
❌ Dễ quên hoạt động quan trọng

Ví dụ:
"Chúng ta mở được mấy chi nhánh nông thôn nhỉ?"
"Hình như là 5 hay 6 chi nhánh ấy?"
→ Khai báo sai → Điểm thấp
```

**Bây giờ:**
```
✅ Dữ liệu thực tế từ hệ thống
✅ Số liệu chính xác
✅ Có 147 minh chứng
✅ Theo dõi từng hoạt động

Ví dụ:
"Có chính xác 10 chi nhánh nông thôn"
"Mở vào các ngày: 10/08, 15/08, 22/08..."
→ Khai báo đúng → Điểm cao
```

**→ Độ chính xác 100%**

---

#### **📈 Cải Thiện Điểm Số**

**Hiện tượng phổ biến:**
```
Doanh nghiệp làm tốt nhưng không biết cách trình bày
→ Bị đánh giá thấp

Ví dụ:
Làm việc: Mở 10 chi nhánh nông thôn (Tuyệt vời!)
Nhưng khi đánh giá: "Có chi nhánh nông thôn" → Chọn "Một phần" (2 điểm)
→ Mất 2 điểm!
```

**Với hệ thống mới:**
```
✅ AI phân tích đầy đủ
✅ Tự động gợi ý điểm cao nhất phù hợp
✅ Giải thích tại sao được điểm cao

Ví dụ:
AI phân tích: "10/50 = 20%, đạt ngưỡng tối đa"
→ Đề xuất: 4 điểm (Đầy đủ)
→ Đúng giá trị thật sự!
```

**→ Điểm số trung bình tăng 15-20%**

---

#### **🔍 Minh Bạch & Kiểm Toán**

**Khi có kiểm toán:**
```
TRƯỚC:
Kiểm toán viên: "Bạn có bằng chứng cho điểm này?"
Doanh nghiệp: "Để tôi tìm... ở đâu nhỉ?"
→ Mất 2 ngày tìm tài liệu
→ Một số tài liệu không tìm thấy
→ Giảm điểm

SAU:
Kiểm toán viên: "Bạn có bằng chứng cho điểm này?"
Doanh nghiệp: "Đây, 147 minh chứng, click xem"
→ 5 phút show hết
→ Đầy đủ, rõ ràng
→ Giữ nguyên điểm
```

---

#### **💰 Tiết Kiệm Chi Phí**

```
Chi phí thuê tư vấn ESG:
├─ Phân tích: 50 triệu/quý
├─ Viết báo cáo: 30 triệu/quý
└─ Tổng: 80 triệu/quý
    × 4 quý = 320 triệu/năm

Với hệ thống tự động:
├─ Chi phí: 0 đồng (tích hợp sẵn)
└─ Tiết kiệm: 320 triệu/năm
```

---

### 5.2 Cho Nhân Viên

#### **📝 Dễ Sử Dụng**

```
Nhân viên chỉ cần:
1. Làm việc bình thường
2. Ghi nhận hoạt động vào app (2 phút)
3. Upload ảnh nếu có
→ Xong!

Không cần:
❌ Hiểu 60 câu hỏi ESG
❌ Biết cách tính điểm
❌ Lo lắng quên ghi chép
```

---

#### **🎯 Thấy Được Impact**

```
Khi hoàn thành hoạt động:

┌─────────────────────────────────────────┐
│ 🎉 Hoàn thành!                          │
│                                         │
│ Hoạt động: Mở chi nhánh Hà Giang       │
│                                         │
│ 📊 Đóng góp vào:                        │
│ ├─ soc_002: +0.5 điểm                  │
│ ├─ soc_004: +0.3 điểm                  │
│ └─ Tổng: +0.8 điểm cho đánh giá ESG    │
│                                         │
│ 👥 Impact:                              │
│ ├─ +15,000 người được phục vụ          │
│ └─ +2% tỷ lệ bao phủ nông thôn         │
│                                         │
│ 💪 Tuyệt vời! Tiếp tục phát huy!        │
└─────────────────────────────────────────┘
```

**→ Động lực làm việc tăng**

---

### 5.3 Cho Lãnh Đạo

#### **📊 Dashboard Theo Dõi Real-time**

```
TỔNG QUAN Q3/2025 (Đang diễn ra)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DỰ ĐOÁN ĐIỂM SỐ
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
🌱 Environmental:  60/80  (75%)  ↑
👥 Social:         68/80  (85%)  ↑↑
🏛️ Governance:     54/80  (67%)  →
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
📊 DỰ ĐOÁN:        182/240 (76%)  ↑
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

⚠️ CẦN CHÚ Ý:
├─ 12 hoạt động sắp deadline
├─ 3 câu hỏi chưa có dữ liệu
└─ 5 hoạt động cần thêm minh chứng

💡 ĐỀ XUẤT:
├─ Tăng hoạt động Governance (+6 điểm)
├─ Đẩy nhanh dự án năng lượng (+4 điểm)
└─ → Có thể đạt 192/240 (80%) ✨
```

---

#### **🎯 Ra Quyết Định Chiến Lược**

```
BÁO CÁO PHÂN TÍCH:

Câu hỏi điểm thấp:
├─ env_014: Loại trừ than đá (0 điểm)
│   Nguyên nhân: Chưa có chính sách
│   Giải pháp: Ban hành chính sách
│   Impact: +4 điểm
│   Chi phí: 0 đồng
│   Thời gian: 1 tháng
│   
├─ soc_009: Loại trừ vũ khí (0 điểm)
│   Nguyên nhân: Chưa có chính sách
│   Giải pháp: Ban hành chính sách
│   Impact: +4 điểm
│   Chi phí: 0 đồng
│   Thời gian: 1 tháng
│
└─ Tổng có thể cải thiện: +8 điểm
    với chi phí: 0 đồng!

→ Quyết định: Ban hành ngay trong tháng 10
→ Q4 sẽ tăng 8 điểm
```

---

#### **🏆 So Sánh & Benchmark**

```
BẢNG XẾP HẠNG NGÂN HÀNG (Q3/2025)

┌─────┬──────────────────┬────────┬─────────┐
│ #   │ Ngân hàng        │ Điểm   │ Xếp hạng│
├─────┼──────────────────┼────────┼─────────┤
│ 1   │ Vietcombank      │ 195/240│ AAA     │
│ 2   │ BIDV             │ 188/240│ AA+     │
│ 3   │ VietinBank       │ 182/240│ AA      │ ← BẠN
│ 4   │ ACB              │ 176/240│ AA-     │
│ 5   │ MB               │ 170/240│ A+      │
└─────┴──────────────────┴────────┴─────────┘

Phân tích:
├─ Bạn hơn trung bình: +12 điểm
├─ Kém số 1: -13 điểm
└─ Để lên hạng AA+: Cần +6 điểm

Điểm mạnh:
✅ Social: Top 1 (72/80)
✅ Environmental: Top 3 (64/80)

Điểm yếu:
⚠️ Governance: Top 5 (56/80)

Đề xuất:
→ Tập trung Governance trong Q4
→ Có thể lên hạng AA+ hoặc AAA
```

---

### 5.4 Cho Chuyên Gia / Auditor

#### **🔍 Kiểm Tra Dễ Dàng**

```
Nhiệm vụ: Xác minh câu soc_002

TRƯỚC:
├─ Đọc báo cáo Word 20 trang
├─ Tìm thông tin liên quan
├─ Yêu cầu thêm minh chứng
├─ Đợi doanh nghiệp gửi
├─ Kiểm tra lại
└─ Mất 2 ngày

SAU:
├─ Click vào câu soc_002
├─ Thấy ngay:
│   - 8 hoạt động liên quan
│   - 147 minh chứng
│   - Công thức tính điểm
│   - Lịch sử thay đổi
├─ Click xem ảnh, tài liệu
├─ Phê duyệt
└─ Mất 15 phút
```

---

#### **📋 Audit Trail Đầy Đủ**

```
Lịch sử câu soc_002:

┌─────────────────────────────────────────┐
│ 📅 01/10/2025 10:00                     │
│ 🤖 AI phân tích                         │
│ → Đề xuất: 4 điểm                       │
│ → Căn cứ: 10 chi nhánh (20%)           │
│                                         │
│ 📅 05/10/2025 14:30                     │
│ 👤 Giám đốc ESG (user_123)             │
│ → Đồng ý: 4 điểm                        │
│ → Ghi chú: "Đúng, đã đạt mục tiêu"     │
│                                         │
│ 📅 10/10/2025 09:15                     │
│ ✓ Chuyên gia (expert_001)              │
│ → Xác minh: 4 điểm                      │
│ → Nhận xét: "Minh chứng đầy đủ"        │
│                                         │
│ 📅 15/10/2025 16:00                     │
│ 📄 Xuất báo cáo chính thức              │
│ → Điểm cuối: 4 điểm                     │
└─────────────────────────────────────────┘

→ Ai làm gì, khi nào, tại sao
→ Không thể chỉnh sửa lịch sử
→ Đạt chuẩn kiểm toán quốc tế
```

---

## 6. Tổng Kết

### 6.1 So Sánh Trước & Sau

| Tiêu chí | Trước (Thủ công) | Sau (Tự động) | Cải thiện |
|----------|------------------|---------------|-----------|
| **Thời gian** | 8 ngày/quý | 2 giờ/quý | **-95%** |
| **Độ chính xác** | ~70% | 100% | **+30%** |
| **Minh chứng** | Một phần | Đầy đủ | **+100%** |
| **Chi phí** | 80M/quý | 0đ | **-100%** |
| **Điểm số** | 160/240 | 192/240 | **+20%** |
| **Audit time** | 2 ngày | 4 giờ | **-75%** |

---

### 6.2 Lộ Trình Triển Khai

```
GIAI ĐOẠN 1: CHUẨN BỊ (Tuần 1-2)
├─ Thiết lập hệ thống
├─ Đào tạo nhân viên (2 giờ)
├─ Nhập dữ liệu mẫu
└─ Chạy thử nghiệm

GIAI ĐOẠN 2: VẬN HÀNH THỬ (Tháng 1)
├─ Ghi nhận hoạt động hằng ngày
├─ Thu thập phản hồi
├─ Điều chỉnh quy trình
└─ Đánh giá hiệu quả

GIAI ĐOẠN 3: CHÍNH THỨC (Tháng 2+)
├─ Áp dụng cho tất cả bộ phận
├─ Tích hợp vào quy trình
├─ Đánh giá tự động cuối quý
└─ Cải tiến liên tục

Timeline: 6 tuần → Sẵn sàng sử dụng
```

---

### 6.3 Kết Luận

Hệ thống **Từ Tracker đến Assessment** mang lại:

✅ **Tự động hóa**: Giảm 95% công việc thủ công  
✅ **Chính xác**: Dựa trên dữ liệu thực, không ước lượng  
✅ **Minh bạch**: Mọi điểm số đều có căn cứ  
✅ **Tiết kiệm**: 320 triệu đồng/năm  
✅ **Cải thiện**: Điểm ESG tăng 15-20%  
✅ **Tuân thủ**: Đạt chuẩn GRI, SASB, TCFD  

Đây không chỉ là công cụ, mà là **thay đổi cách thức** quản lý ESG:
- Từ **hồi tưởng** → **theo dõi liên tục**
- Từ **ước lượng** → **đo lường chính xác**
- Từ **báo cáo** → **hành động**

**Kết quả:** Doanh nghiệp làm tốt ESG sẽ được **đánh giá đúng giá trị**, không bị **đánh giá thấp** do thiếu dữ liệu hoặc cách trình bày không đúng.

---

## 7. Câu Hỏi Thường Gặp

### Q1: AI có thể sai không?
**A:** Có thể, vì vậy:
- AI chỉ **đề xuất**, không tự quyết định
- Doanh nghiệp phải **xem lại và xác nhận**
- Chuyên gia sẽ **xác minh** lần cuối
- Có thể **điều chỉnh** bất cứ lúc nào

### Q2: Nếu không có dữ liệu cho một câu hỏi?
**A:** 
- AI sẽ báo "Không đủ dữ liệu"
- Đề xuất điểm: 0 hoặc N/A
- Gợi ý hoạt động cần làm trong quý sau

### Q3: Có cần ghi nhận mọi hoạt động nhỏ không?
**A:**
- Ghi nhận hoạt động **có tác động** đến ESG
- Không cần ghi việc quá nhỏ lẻ
- Hệ thống sẽ gợi ý hoạt động quan trọng

### Q4: Mất bao lâu để làm quen?
**A:**
- Đào tạo: 2 giờ
- Làm quen: 1 tuần
- Thành thạo: 1 tháng

### Q5: Chi phí triển khai?
**A:**
- Đã tích hợp sẵn trong app
- Không phát sinh chi phí thêm
- Tiết kiệm 320 triệu đồng/năm

---

**Liên hệ:**
- Email: support@esgcompanion.com
- Hotline: 1900-xxxx
- Website: esgcompanion.com

---

*Tài liệu này được thiết kế để dễ hiểu cho mọi đối tượng, từ nhân viên đến lãnh đạo doanh nghiệp.*

**Phiên bản:** 1.0  
**Cập nhật:** Tháng 10/2025

