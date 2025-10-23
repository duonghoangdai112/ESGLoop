# Hướng dẫn Setup Project

## Yêu cầu hệ thống

### Java Development Kit (JDK)
- **Phiên bản yêu cầu**: Java 17 hoặc cao hơn
- **Khuyến nghị**: Eclipse Adoptium JDK 17 hoặc Oracle JDK 17

### Android Studio
- **Phiên bản tối thiểu**: Android Studio Hedgehog (2023.1.1) hoặc cao hơn
- **Android SDK**: API Level 24 trở lên

## Các bước setup

### 1. Cài đặt Java 17
1. Tải và cài đặt Java 17 từ:
   - [Eclipse Adoptium](https://adoptium.net/) (khuyến nghị)
   - [Oracle JDK](https://www.oracle.com/java/technologies/downloads/)

2. Thiết lập biến môi trường `JAVA_HOME`:
   - **Windows**: Thêm vào System Environment Variables
     ```
     JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-17.0.x.x-hotspot
     ```
   - **macOS**: Thêm vào `~/.zshrc` hoặc `~/.bash_profile`
     ```bash
     export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-17.jdk/Contents/Home
     ```
   - **Linux**: Thêm vào `~/.bashrc`
     ```bash
     export JAVA_HOME=/usr/lib/jvm/java-17-openjdk
     ```

### 2. Clone và Build Project
1. Clone repository:
   ```bash
   git clone <repository-url>
   cd VNESG
   ```

2. Mở project trong Android Studio:
   - File → Open → Chọn thư mục `VNESG`
   - Android Studio sẽ tự động sync Gradle

3. Nếu gặp lỗi Java home:
   - Kiểm tra `JAVA_HOME` environment variable
   - Restart Android Studio
   - File → Invalidate Caches and Restart

### 3. Cấu hình Android SDK
1. Mở Android Studio
2. File → Settings → Appearance & Behavior → System Settings → Android SDK
3. Cài đặt các SDK Platforms:
   - Android 14.0 (API 34) - Target SDK
   - Android 7.0 (API 24) - Minimum SDK
4. Cài đặt các SDK Tools:
   - Android SDK Build-Tools
   - Android SDK Platform-Tools
   - Android SDK Tools

## Troubleshooting

### Lỗi "Java home supplied is invalid"
- **Nguyên nhân**: Biến môi trường `JAVA_HOME` không được thiết lập đúng
- **Giải pháp**: 
  1. Kiểm tra `JAVA_HOME` trong terminal: `echo $JAVA_HOME` (macOS/Linux) hoặc `echo %JAVA_HOME%` (Windows)
  2. Đảm bảo đường dẫn trỏ đến thư mục JDK 17
  3. Restart Android Studio sau khi thiết lập

### Lỗi Gradle sync
- **Giải pháp**:
  1. File → Invalidate Caches and Restart
  2. Xóa thư mục `.gradle` trong project (nếu có)
  3. Clean và Rebuild project

### Lỗi Android SDK
- **Giải pháp**:
  1. Kiểm tra Android SDK location trong File → Project Structure
  2. Cài đặt đầy đủ các SDK components cần thiết
  3. Cập nhật Android SDK Tools

## Cấu hình Project

Project này sử dụng:
- **Kotlin**: 1.9.22
- **Android Gradle Plugin**: 8.6.0
- **Gradle**: 8.10
- **Java**: 17
- **Target SDK**: 34
- **Min SDK**: 24

## Liên hệ
Nếu gặp vấn đề trong quá trình setup, vui lòng liên hệ team lead hoặc tạo issue trên repository.
