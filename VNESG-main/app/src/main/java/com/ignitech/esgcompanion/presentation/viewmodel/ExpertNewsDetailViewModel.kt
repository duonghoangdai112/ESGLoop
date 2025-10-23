package com.ignitech.esgcompanion.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.ignitech.esgcompanion.presentation.screen.expert.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpertNewsDetailViewModel @Inject constructor() : ViewModel() {
    
    private val _uiState = MutableStateFlow(NewsDetailUiState())
    val uiState: StateFlow<NewsDetailUiState> = _uiState.asStateFlow()
    
    private var navController: NavController? = null
    
    fun setNavController(navController: NavController) {
        this.navController = navController
    }
    
    fun loadNewsDetail(newsId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            // Simulate loading delay
            delay(500)
            
            // Find news by ID from mock data
            val news = getMockNewsById(newsId)
            if (news != null) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    news = news,
                    isBookmarked = news.isBookmarked
                )
            } else {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Không tìm thấy tin tức"
                )
            }
        }
    }
    
    fun toggleBookmark() {
        viewModelScope.launch {
            val currentNews = _uiState.value.news
            if (currentNews != null) {
                val updatedNews = currentNews.copy(isBookmarked = !currentNews.isBookmarked)
                _uiState.value = _uiState.value.copy(
                    news = updatedNews,
                    isBookmarked = updatedNews.isBookmarked
                )
                
                // TODO: Save bookmark state to database
            }
        }
    }
    
    fun shareNews() {
        viewModelScope.launch {
            val news = _uiState.value.news
            if (news != null) {
                // TODO: Implement sharing functionality
                // For now, just show a toast or snackbar
            }
        }
    }
    
    private fun getMockNewsById(newsId: String): ESGNews? {
        // Return mock news data based on ID
        return when (newsId) {
            "breaking_001" -> ESGNews(
                id = "breaking_001",
                title = "EU công bố quy định ESG mới",
                summary = "Liên minh châu Âu vừa công bố bộ quy định ESG mới sẽ có hiệu lực từ 2025",
                content = """
                    <h2>EU công bố bộ quy định ESG mới - Bước ngoặt quan trọng</h2>
                    
                    <p>Liên minh châu Âu (EU) vừa chính thức công bố bộ quy định ESG mới mang tên "Corporate Sustainability Reporting Directive" (CSRD) sẽ có hiệu lực từ ngày 1/1/2025. Đây được coi là bước ngoặt quan trọng trong việc chuẩn hóa báo cáo bền vững trên toàn châu Âu.</p>
                    
                    <h3>Những điểm nổi bật của quy định mới:</h3>
                    <ul>
                        <li><strong>Mở rộng phạm vi áp dụng:</strong> Từ 11.000 công ty hiện tại lên 50.000 công ty vào năm 2025</li>
                        <li><strong>Chuẩn hóa báo cáo:</strong> Sử dụng bộ tiêu chuẩn báo cáo bền vững chung của EU (ESRS)</li>
                        <li><strong>Bắt buộc kiểm toán:</strong> Tất cả báo cáo ESG phải được kiểm toán độc lập</li>
                        <li><strong>Báo cáo kỹ thuật số:</strong> Áp dụng định dạng XBRL để dễ dàng so sánh và phân tích</li>
                    </ul>
                    
                    <h3>Tác động đến doanh nghiệp Việt Nam:</h3>
                    <p>Các công ty Việt Nam có hoạt động kinh doanh tại EU hoặc trong chuỗi cung ứng của các công ty EU sẽ phải tuân thủ quy định mới này. Điều này đòi hỏi các doanh nghiệp cần chuẩn bị hệ thống thu thập và báo cáo dữ liệu ESG ngay từ bây giờ.</p>
                    
                    <h3>Khuyến nghị cho doanh nghiệp:</h3>
                    <ol>
                        <li>Đánh giá mức độ sẵn sàng của hệ thống báo cáo hiện tại</li>
                        <li>Đầu tư vào công nghệ và nhân lực cho báo cáo ESG</li>
                        <li>Tham gia các chương trình đào tạo về CSRD</li>
                        <li>Thiết lập quan hệ đối tác với các chuyên gia tư vấn ESG</li>
                    </ol>
                    
                    <p><em>Nguồn: Ủy ban châu Âu, ESG Today</em></p>
                """.trimIndent(),
                source = "ESG Today",
                pillar = com.ignitech.esgcompanion.domain.entity.ESGPillar.GOVERNANCE,
                publishedAt = System.currentTimeMillis() - 3600000,
                isBreaking = true
            )
            "breaking_002" -> ESGNews(
                id = "breaking_002",
                title = "Việt Nam cam kết Net Zero 2050",
                summary = "Chính phủ Việt Nam chính thức cam kết đạt mục tiêu Net Zero vào năm 2050",
                content = """
                    <h2>Việt Nam cam kết Net Zero 2050 - Tầm nhìn xanh cho tương lai</h2>
                    
                    <p>Tại Hội nghị COP26, Thủ tướng Chính phủ Phạm Minh Chính đã chính thức cam kết Việt Nam sẽ đạt mục tiêu Net Zero (phát thải ròng bằng 0) vào năm 2050. Đây là cam kết mạnh mẽ thể hiện quyết tâm của Việt Nam trong cuộc chiến chống biến đổi khí hậu.</p>
                    
                    <h3>Lộ trình thực hiện Net Zero:</h3>
                    <ul>
                        <li><strong>Giai đoạn 2021-2030:</strong> Giảm 15.8% phát thải so với kịch bản thông thường</li>
                        <li><strong>Giai đoạn 2031-2040:</strong> Giảm 43.5% phát thải so với kịch bản thông thường</li>
                        <li><strong>Giai đoạn 2041-2050:</strong> Đạt Net Zero hoàn toàn</li>
                    </ul>
                    
                    <h3>Các biện pháp chính:</h3>
                    <ol>
                        <li><strong>Chuyển đổi năng lượng:</strong> Tăng tỷ trọng năng lượng tái tạo lên 70% vào 2050</li>
                        <li><strong>Tiết kiệm năng lượng:</strong> Giảm 7-8% cường độ năng lượng mỗi năm</li>
                        <li><strong>Bảo vệ rừng:</strong> Tăng độ che phủ rừng lên 45%</li>
                        <li><strong>Phát triển giao thông xanh:</strong> Chuyển đổi sang xe điện và nhiên liệu sạch</li>
                    </ol>
                    
                    <h3>Thách thức và cơ hội:</h3>
                    <p><strong>Thách thức:</strong> Cần đầu tư ít nhất 368 tỷ USD trong 30 năm tới, tương đương 6.8% GDP hàng năm.</p>
                    <p><strong>Cơ hội:</strong> Tạo ra 1.8 triệu việc làm mới trong lĩnh vực năng lượng tái tạo và công nghệ xanh.</p>
                    
                    <h3>Hỗ trợ quốc tế:</h3>
                    <p>Việt Nam sẽ nhận được hỗ trợ từ các quỹ khí hậu quốc tế như Green Climate Fund, Global Environment Facility và các đối tác phát triển.</p>
                    
                    <p><em>Nguồn: VnExpress, Bộ Tài nguyên và Môi trường</em></p>
                """.trimIndent(),
                source = "VnExpress",
                pillar = com.ignitech.esgcompanion.domain.entity.ESGPillar.ENVIRONMENTAL,
                publishedAt = System.currentTimeMillis() - 7200000,
                isBreaking = true
            )
            "featured_001" -> ESGNews(
                id = "featured_001",
                title = "Xu hướng ESG trong ngành tài chính 2024",
                summary = "Phân tích chi tiết về xu hướng ESG đang định hình ngành tài chính toàn cầu",
                content = """
                    <h2>Xu hướng ESG trong ngành tài chính 2024: Cuộc cách mạng bền vững</h2>
                    
                    <p>Năm 2024 đánh dấu một bước ngoặt quan trọng trong việc tích hợp ESG vào ngành tài chính toàn cầu. Các ngân hàng, quỹ đầu tư và tổ chức tài chính đang chuyển mình mạnh mẽ để đáp ứng yêu cầu bền vững từ nhà đầu tư và cơ quan quản lý.</p>
                    
                    <h3>Những xu hướng nổi bật:</h3>
                    
                    <h4>1. Tăng trưởng mạnh mẽ của tài chính xanh</h4>
                    <ul>
                        <li>Thị trường trái phiếu xanh toàn cầu đạt 500 tỷ USD trong năm 2024</li>
                        <li>Việt Nam phát hành 1 tỷ USD trái phiếu xanh đầu tiên</li>
                        <li>Ngân hàng Nhà nước ban hành hướng dẫn phân loại tài chính xanh</li>
                    </ul>
                    
                    <h4>2. Chuyển đổi số trong báo cáo ESG</h4>
                    <ul>
                        <li>Ứng dụng AI và Big Data trong đo lường tác động ESG</li>
                        <li>Blockchain để minh bạch hóa chuỗi cung ứng</li>
                        <li>Báo cáo thời gian thực thông qua IoT</li>
                    </ul>
                    
                    <h4>3. Áp lực từ nhà đầu tư</h4>
                    <p>BlackRock, Vanguard và các quỹ lớn khác đang yêu cầu các công ty trong danh mục đầu tư phải có kế hoạch ESG rõ ràng và có thể đo lường được.</p>
                    
                    <h3>Tác động đến Việt Nam:</h3>
                    <p>Ngành ngân hàng Việt Nam đang tích cực chuẩn bị cho cuộc cách mạng ESG:</p>
                    <ol>
                        <li><strong>VPBank:</strong> Phát hành 200 triệu USD trái phiếu xanh</li>
                        <li><strong>BIDV:</strong> Thành lập bộ phận ESG chuyên biệt</li>
                        <li><strong>Techcombank:</strong> Triển khai hệ thống đánh giá rủi ro ESG</li>
                    </ol>
                    
                    <h3>Dự báo 2025:</h3>
                    <p>Năm 2025 sẽ chứng kiến sự bùng nổ của các sản phẩm tài chính ESG, với ước tính 70% các ngân hàng lớn sẽ có sản phẩm tài chính xanh riêng biệt.</p>
                    
                    <p><em>Nguồn: Financial Times, Ngân hàng Nhà nước Việt Nam</em></p>
                """.trimIndent(),
                source = "Financial Times",
                pillar = com.ignitech.esgcompanion.domain.entity.ESGPillar.GOVERNANCE,
                publishedAt = System.currentTimeMillis() - 86400000,
                isFeatured = true,
                isBookmarked = true
            )
            "featured_002" -> ESGNews(
                id = "featured_002",
                title = "Công nghệ xanh: Giải pháp cho tương lai",
                summary = "Các công nghệ xanh đang mở ra cơ hội mới cho doanh nghiệp bền vững",
                content = """
                    <h2>Công nghệ xanh: Cuộc cách mạng công nghệ vì môi trường</h2>
                    
                    <p>Trong bối cảnh biến đổi khí hậu ngày càng nghiêm trọng, công nghệ xanh đang trở thành chìa khóa để giải quyết các thách thức môi trường và tạo ra cơ hội kinh doanh mới. Từ năng lượng tái tạo đến trí tuệ nhân tạo, các công nghệ xanh đang định hình lại tương lai của nền kinh tế toàn cầu.</p>
                    
                    <h3>Những công nghệ xanh đột phá:</h3>
                    
                    <h4>1. Năng lượng tái tạo thông minh</h4>
                    <ul>
                        <li><strong>Pin mặt trời perovskite:</strong> Hiệu suất cao hơn 30% so với silicon truyền thống</li>
                        <li><strong>Tuabin gió nổi:</strong> Khai thác năng lượng gió ở vùng biển sâu</li>
                        <li><strong>Lưu trữ năng lượng hydro:</strong> Giải pháp lưu trữ năng lượng quy mô lớn</li>
                    </ul>
                    
                    <h4>2. Công nghệ carbon capture</h4>
                    <ul>
                        <li><strong>Direct Air Capture (DAC):</strong> Thu giữ CO2 trực tiếp từ không khí</li>
                        <li><strong>Carbon utilization:</strong> Chuyển đổi CO2 thành nhiên liệu và vật liệu</li>
                        <li><strong>Nature-based solutions:</strong> Sử dụng rừng và đại dương để hấp thụ carbon</li>
                    </ul>
                    
                    <h4>3. Trí tuệ nhân tạo xanh</h4>
                    <ul>
                        <li><strong>AI tối ưu hóa năng lượng:</strong> Giảm 20-30% tiêu thụ năng lượng</li>
                        <li><strong>Dự báo năng lượng tái tạo:</strong> Tăng hiệu quả sử dụng năng lượng mặt trời và gió</li>
                        <li><strong>Quản lý chất thải thông minh:</strong> Tối ưu hóa quy trình tái chế</li>
                    </ul>
                    
                    <h3>Ứng dụng tại Việt Nam:</h3>
                    <p>Việt Nam đang tích cực áp dụng các công nghệ xanh:</p>
                    <ol>
                        <li><strong>Nông nghiệp thông minh:</strong> Sử dụng IoT và AI để tối ưu hóa tưới tiêu</li>
                        <li><strong>Giao thông điện:</strong> Phát triển mạng lưới sạc xe điện</li>
                        <li><strong>Thành phố thông minh:</strong> Tích hợp năng lượng tái tạo vào đô thị</li>
                    </ol>
                    
                    <h3>Cơ hội đầu tư:</h3>
                    <p>Thị trường công nghệ xanh toàn cầu dự kiến đạt 2.5 nghìn tỷ USD vào 2030, tạo ra hàng triệu việc làm mới và cơ hội đầu tư hấp dẫn.</p>
                    
                    <h3>Thách thức và giải pháp:</h3>
                    <p><strong>Thách thức:</strong> Chi phí đầu tư ban đầu cao, thiếu nhân lực chuyên môn</p>
                    <p><strong>Giải pháp:</strong> Hợp tác quốc tế, đào tạo nhân lực, chính sách hỗ trợ từ chính phủ</p>
                    
                    <p><em>Nguồn: Green Tech, Bộ Khoa học và Công nghệ</em></p>
                """.trimIndent(),
                source = "Green Tech",
                pillar = com.ignitech.esgcompanion.domain.entity.ESGPillar.ENVIRONMENTAL,
                publishedAt = System.currentTimeMillis() - 172800000,
                isFeatured = true
            )
            "latest_001" -> ESGNews(
                id = "latest_001",
                title = "Báo cáo ESG của các ngân hàng lớn",
                summary = "Tổng hợp báo cáo ESG của 10 ngân hàng lớn nhất thế giới",
                content = """
                    <h2>Báo cáo ESG của các ngân hàng lớn: Xu hướng và thách thức</h2>
                    
                    <p>Nghiên cứu mới nhất về báo cáo ESG của 10 ngân hàng lớn nhất thế giới cho thấy sự tiến bộ đáng kể trong việc minh bạch hóa thông tin bền vững, nhưng vẫn còn nhiều thách thức cần giải quyết.</p>
                    
                    <h3>Kết quả nghiên cứu chính:</h3>
                    
                    <h4>1. Mức độ minh bạch</h4>
                    <ul>
                        <li><strong>JPMorgan Chase:</strong> 85% - Dẫn đầu về báo cáo tác động môi trường</li>
                        <li><strong>Bank of America:</strong> 82% - Xuất sắc trong báo cáo xã hội</li>
                        <li><strong>Wells Fargo:</strong> 78% - Cải thiện đáng kể về quản trị</li>
                        <li><strong>Citigroup:</strong> 80% - Cân bằng tốt giữa 3 trụ cột ESG</li>
                    </ul>
                    
                    <h4>2. Những điểm nổi bật</h4>
                    <ul>
                        <li>Tất cả ngân hàng đều có mục tiêu Net Zero rõ ràng</li>
                        <li>90% đã thiết lập quỹ tài chính xanh</li>
                        <li>85% có chính sách đa dạng hóa nguồn nhân lực</li>
                        <li>100% tuân thủ các tiêu chuẩn báo cáo quốc tế</li>
                    </ul>
                    
                    <h4>3. Thách thức còn tồn tại</h4>
                    <ul>
                        <li>Thiếu chuẩn hóa trong đo lường tác động</li>
                        <li>Khó khăn trong việc định lượng rủi ro ESG</li>
                        <li>Chi phí cao cho việc thu thập và báo cáo dữ liệu</li>
                        <li>Áp lực từ các bên liên quan khác nhau</li>
                    </ul>
                    
                    <h3>Khuyến nghị cho ngành ngân hàng Việt Nam:</h3>
                    <ol>
                        <li>Học hỏi kinh nghiệm từ các ngân hàng quốc tế</li>
                        <li>Đầu tư vào công nghệ để tự động hóa báo cáo ESG</li>
                        <li>Đào tạo nhân viên về kiến thức ESG chuyên sâu</li>
                        <li>Thiết lập quan hệ đối tác với các chuyên gia quốc tế</li>
                    </ol>
                    
                    <p><em>Nguồn: Banking News, Global Banking Association</em></p>
                """.trimIndent(),
                source = "Banking News",
                pillar = com.ignitech.esgcompanion.domain.entity.ESGPillar.GOVERNANCE,
                publishedAt = System.currentTimeMillis() - 21600000
            )
            "latest_002" -> ESGNews(
                id = "latest_002",
                title = "Đa dạng hóa nguồn nhân lực trong ESG",
                summary = "Tầm quan trọng của đa dạng hóa nguồn nhân lực trong lĩnh vực ESG",
                content = """
                    <h2>Đa dạng hóa nguồn nhân lực trong ESG: Chìa khóa thành công</h2>
                    
                    <p>Trong lĩnh vực ESG, đa dạng hóa nguồn nhân lực không chỉ là vấn đề đạo đức mà còn là yếu tố quyết định thành công. Nghiên cứu cho thấy các đội ngũ đa dạng có khả năng đưa ra giải pháp sáng tạo và hiệu quả hơn 40% so với các đội ngũ đồng nhất.</p>
                    
                    <h3>Tại sao đa dạng hóa quan trọng trong ESG?</h3>
                    
                    <h4>1. Góc nhìn đa chiều</h4>
                    <p>ESG đòi hỏi hiểu biết sâu sắc về các vấn đề xã hội, môi trường và quản trị. Đội ngũ đa dạng về giới tính, tuổi tác, văn hóa và kinh nghiệm sẽ mang lại những góc nhìn phong phú và toàn diện hơn.</p>
                    
                    <h4>2. Tăng cường sáng tạo</h4>
                    <ul>
                        <li>Phụ nữ thường có xu hướng quan tâm nhiều hơn đến các vấn đề xã hội</li>
                        <li>Thế hệ trẻ có hiểu biết sâu về công nghệ và xu hướng mới</li>
                        <li>Người có kinh nghiệm quốc tế hiểu rõ các tiêu chuẩn toàn cầu</li>
                    </ul>
                    
                    <h4>3. Phản ánh đúng đối tượng phục vụ</h4>
                    <p>Khách hàng và cộng đồng mà doanh nghiệp phục vụ rất đa dạng. Đội ngũ đa dạng sẽ hiểu rõ hơn nhu cầu và mong đợi của họ.</p>
                    
                    <h3>Thực trạng tại Việt Nam:</h3>
                    <ul>
                        <li><strong>Tỷ lệ nữ giới trong lĩnh vực ESG:</strong> 35% (thấp hơn mức trung bình 45% của khu vực)</li>
                        <li><strong>Độ tuổi trung bình:</strong> 32 tuổi (trẻ hơn so với các lĩnh vực khác)</li>
                        <li><strong>Trình độ học vấn:</strong> 80% có bằng đại học trở lên</li>
                        <li><strong>Kinh nghiệm quốc tế:</strong> 25% có kinh nghiệm làm việc ở nước ngoài</li>
                    </ul>
                    
                    <h3>Chiến lược đa dạng hóa hiệu quả:</h3>
                    <ol>
                        <li><strong>Thu hút tài năng:</strong> Tạo môi trường làm việc hòa nhập và bình đẳng</li>
                        <li><strong>Đào tạo và phát triển:</strong> Đầu tư vào chương trình đào tạo ESG chuyên sâu</li>
                        <li><strong>Mentoring:</strong> Thiết lập chương trình cố vấn cho nhân viên mới</li>
                        <li><strong>Đánh giá và đo lường:</strong> Thiết lập KPIs về đa dạng hóa</li>
                    </ol>
                    
                    <h3>Lợi ích kinh doanh:</h3>
                    <p>Các công ty có đội ngũ ESG đa dạng thường đạt được:</p>
                    <ul>
                        <li>Hiệu suất tài chính cao hơn 15%</li>
                        <li>Mức độ hài lòng của nhân viên cao hơn 25%</li>
                        <li>Khả năng thu hút và giữ chân nhân tài tốt hơn 30%</li>
                        <li>Uy tín thương hiệu mạnh hơn trong cộng đồng</li>
                    </ul>
                    
                    <p><em>Nguồn: HR Today, McKinsey Global Institute</em></p>
                """.trimIndent(),
                source = "HR Today",
                pillar = com.ignitech.esgcompanion.domain.entity.ESGPillar.SOCIAL,
                publishedAt = System.currentTimeMillis() - 43200000
            )
            "latest_003" -> ESGNews(
                id = "latest_003",
                title = "Năng lượng tái tạo tại Việt Nam",
                summary = "Tiềm năng và thách thức của năng lượng tái tạo tại Việt Nam",
                content = """
                    <h2>Năng lượng tái tạo tại Việt Nam: Tiềm năng to lớn, thách thức không nhỏ</h2>
                    
                    <p>Việt Nam được đánh giá là một trong những quốc gia có tiềm năng năng lượng tái tạo lớn nhất Đông Nam Á. Với bờ biển dài 3.260km, ánh nắng mặt trời dồi dào và địa hình đa dạng, Việt Nam có cơ hội trở thành trung tâm năng lượng sạch của khu vực.</p>
                    
                    <h3>Tiềm năng năng lượng tái tạo:</h3>
                    
                    <h4>1. Năng lượng mặt trời</h4>
                    <ul>
                        <li><strong>Tiềm năng kỹ thuật:</strong> 434 GW (gấp 8 lần nhu cầu hiện tại)</li>
                        <li><strong>Giờ nắng trung bình:</strong> 2.000-2.500 giờ/năm</li>
                        <li><strong>Công suất đã lắp đặt:</strong> 16.5 GW (2023)</li>
                        <li><strong>Mục tiêu 2030:</strong> 20 GW</li>
                    </ul>
                    
                    <h4>2. Năng lượng gió</h4>
                    <ul>
                        <li><strong>Tiềm năng kỹ thuật:</strong> 311 GW (trên bờ và ngoài khơi)</li>
                        <li><strong>Tốc độ gió trung bình:</strong> 7-9 m/s ở vùng ven biển</li>
                        <li><strong>Công suất đã lắp đặt:</strong> 4.1 GW (2023)</li>
                        <li><strong>Mục tiêu 2030:</strong> 12 GW</li>
                    </ul>
                    
                    <h4>3. Thủy điện nhỏ</h4>
                    <ul>
                        <li><strong>Tiềm năng kỹ thuật:</strong> 25 GW</li>
                        <li><strong>Công suất đã lắp đặt:</strong> 4.2 GW (2023)</li>
                        <li><strong>Ưu điểm:</strong> Ổn định, có thể điều chỉnh</li>
                    </ul>
                    
                    <h3>Thành tựu đạt được:</h3>
                    <ol>
                        <li><strong>Năng lượng mặt trời:</strong> Tăng trưởng 200% trong 3 năm qua</li>
                        <li><strong>Năng lượng gió:</strong> Dự án gió ngoài khơi đầu tiên 3.4 GW</li>
                        <li><strong>Đầu tư nước ngoài:</strong> Thu hút 15 tỷ USD từ 2018-2023</li>
                        <li><strong>Việc làm:</strong> Tạo ra 50.000 việc làm mới</li>
                    </ol>
                    
                    <h3>Thách thức cần giải quyết:</h3>
                    
                    <h4>1. Hạ tầng lưới điện</h4>
                    <ul>
                        <li>Lưới điện chưa đủ mạnh để tiếp nhận năng lượng tái tạo</li>
                        <li>Thiếu hệ thống lưu trữ năng lượng</li>
                        <li>Cần đầu tư 15 tỷ USD để nâng cấp lưới điện</li>
                    </ul>
                    
                    <h4>2. Chính sách và quy định</h4>
                    <ul>
                        <li>Giá mua điện (FIT) chưa hấp dẫn</li>
                        <li>Thủ tục phê duyệt dự án phức tạp</li>
                        <li>Thiếu cơ chế thị trường điện cạnh tranh</li>
                    </ul>
                    
                    <h4>3. Tài chính</h4>
                    <ul>
                        <li>Chi phí vốn cao (8-12%)</li>
                        <li>Thiếu sản phẩm tài chính xanh</li>
                        <li>Rủi ro tỷ giá và chính sách</li>
                    </ul>
                    
                    <h3>Cơ hội phát triển:</h3>
                    <ul>
                        <li><strong>Hydro xanh:</strong> Tiềm năng xuất khẩu 10 GW</li>
                        <li><strong>Lưu trữ năng lượng:</strong> Thị trường 2 tỷ USD</li>
                        <li><strong>Hydrogen:</strong> Sản xuất từ năng lượng tái tạo</li>
                        <li><strong>Xuất khẩu điện:</strong> Kết nối với Lào, Campuchia</li>
                    </ul>
                    
                    <h3>Khuyến nghị:</h3>
                    <ol>
                        <li>Đầu tư nâng cấp hạ tầng lưới điện</li>
                        <li>Cải thiện cơ chế giá điện</li>
                        <li>Phát triển thị trường điện cạnh tranh</li>
                        <li>Đào tạo nhân lực chuyên môn</li>
                        <li>Hợp tác quốc tế về công nghệ</li>
                    </ol>
                    
                    <p><em>Nguồn: Energy Vietnam, Bộ Công Thương</em></p>
                """.trimIndent(),
                source = "Energy Vietnam",
                pillar = com.ignitech.esgcompanion.domain.entity.ESGPillar.ENVIRONMENTAL,
                publishedAt = System.currentTimeMillis() - 64800000
            )
            "insight_001" -> ESGNews(
                id = "insight_001",
                title = "Góc nhìn chuyên gia: Tương lai của ESG",
                summary = "Chia sẻ góc nhìn chuyên sâu về xu hướng phát triển của ESG trong 5 năm tới",
                content = """
                    <h2>Tương lai của ESG: 5 xu hướng định hình thập kỷ tới</h2>
                    
                    <p>Là một chuyên gia với hơn 15 năm kinh nghiệm trong lĩnh vực ESG, tôi muốn chia sẻ góc nhìn về những xu hướng sẽ định hình tương lai của ESG trong 5 năm tới. Đây không chỉ là dự báo mà còn là cơ hội cho các doanh nghiệp và cá nhân chuẩn bị sẵn sàng cho cuộc cách mạng bền vững sắp tới.</p>
                    
                    <h3>1. Tích hợp AI và Machine Learning</h3>
                    <p>Trí tuệ nhân tạo sẽ trở thành công cụ không thể thiếu trong việc đo lường, dự báo và tối ưu hóa tác động ESG. Từ việc phân tích dữ liệu vệ tinh để theo dõi phá rừng đến dự báo rủi ro khí hậu, AI sẽ giúp các doanh nghiệp đưa ra quyết định chính xác và kịp thời hơn.</p>
                    
                    <h3>2. Chuẩn hóa toàn cầu</h3>
                    <p>Chúng ta sẽ chứng kiến sự hội tụ của các tiêu chuẩn báo cáo ESG trên toàn cầu. ISSB (International Sustainability Standards Board) sẽ trở thành chuẩn mực chung, giúp các nhà đầu tư so sánh và đánh giá doanh nghiệp một cách công bằng.</p>
                    
                    <h3>3. Tài chính xanh bùng nổ</h3>
                    <p>Thị trường tài chính xanh sẽ tăng trưởng mạnh mẽ, với ước tính đạt 5 nghìn tỷ USD vào 2030. Các sản phẩm tài chính mới như trái phiếu chuyển đổi xanh và tín dụng carbon sẽ trở nên phổ biến.</p>
                    
                    <h3>4. Đo lường tác động thời gian thực</h3>
                    <p>IoT và blockchain sẽ cho phép đo lường tác động ESG theo thời gian thực, thay vì báo cáo hàng năm như hiện tại. Điều này sẽ tạo ra sự minh bạch hoàn toàn và trách nhiệm giải trình cao hơn.</p>
                    
                    <h3>5. ESG trở thành yếu tố cạnh tranh cốt lõi</h3>
                    <p>Trong 5 năm tới, ESG sẽ không còn là "nice to have" mà trở thành yếu tố cạnh tranh cốt lõi. Các doanh nghiệp không có chiến lược ESG mạnh mẽ sẽ khó tồn tại trong thị trường.</p>
                    
                    <h3>Khuyến nghị cho doanh nghiệp Việt Nam:</h3>
                    <ol>
                        <li>Đầu tư ngay vào công nghệ và nhân lực ESG</li>
                        <li>Thiết lập hệ thống đo lường và báo cáo minh bạch</li>
                        <li>Tham gia các sáng kiến ESG quốc tế</li>
                        <li>Xây dựng văn hóa bền vững từ trong ra ngoài</li>
                    </ol>
                    
                    <p><em>Dr. Nguyễn Minh - Chuyên gia ESG hàng đầu Việt Nam, Cựu Giám đốc Bộ phận Bền vững tại PwC</em></p>
                """.trimIndent(),
                source = "Expert Insights",
                pillar = com.ignitech.esgcompanion.domain.entity.ESGPillar.GOVERNANCE,
                publishedAt = System.currentTimeMillis() - 259200000
            )
            "insight_002" -> ESGNews(
                id = "insight_002",
                title = "Thách thức trong đo lường tác động ESG",
                summary = "Phân tích các thách thức và giải pháp trong việc đo lường tác động ESG",
                content = """
                    <h2>Thách thức trong đo lường tác động ESG: Từ lý thuyết đến thực tiễn</h2>
                    
                    <p>Trong hơn 10 năm làm việc với các doanh nghiệp về ESG, tôi nhận thấy rằng đo lường tác động ESG vẫn là thách thức lớn nhất mà hầu hết các tổ chức đang phải đối mặt. Hôm nay, tôi muốn chia sẻ những kinh nghiệm thực tế và giải pháp cụ thể để vượt qua những thách thức này.</p>
                    
                    <h3>Những thách thức chính:</h3>
                    
                    <h4>1. Thiếu dữ liệu chất lượng</h4>
                    <p>Nhiều doanh nghiệp gặp khó khăn trong việc thu thập dữ liệu ESG đáng tin cậy. Dữ liệu thường bị phân tán, không đồng nhất và thiếu tính so sánh.</p>
                    
                    <h4>2. Khó khăn trong định lượng tác động xã hội</h4>
                    <p>Khác với tác động môi trường có thể đo lường bằng số liệu cụ thể, tác động xã hội thường mang tính định tính và khó định lượng.</p>
                    
                    <h4>3. Chi phí cao</h4>
                    <p>Việc thiết lập hệ thống đo lường ESG chuyên nghiệp đòi hỏi đầu tư lớn về công nghệ và nhân lực.</p>
                    
                    <h4>4. Áp lực từ các bên liên quan</h4>
                    <p>Mỗi bên liên quan có yêu cầu đo lường khác nhau, tạo ra sự phức tạp trong báo cáo.</p>
                    
                    <h3>Giải pháp thực tiễn:</h3>
                    
                    <h4>1. Xây dựng framework đo lường rõ ràng</h4>
                    <ul>
                        <li>Sử dụng các tiêu chuẩn quốc tế như GRI, SASB</li>
                        <li>Thiết lập KPIs phù hợp với ngành nghề</li>
                        <li>Định nghĩa rõ ràng các chỉ số đo lường</li>
                    </ul>
                    
                    <h4>2. Đầu tư vào công nghệ</h4>
                    <ul>
                        <li>Sử dụng phần mềm chuyên dụng cho ESG</li>
                        <li>Tích hợp IoT để thu thập dữ liệu tự động</li>
                        <li>Ứng dụng AI để phân tích và dự báo</li>
                    </ul>
                    
                    <h4>3. Đào tạo nhân viên</h4>
                    <ul>
                        <li>Chương trình đào tạo ESG chuyên sâu</li>
                        <li>Chứng chỉ quốc tế về đo lường ESG</li>
                        <li>Chia sẻ kinh nghiệm từ các chuyên gia</li>
                    </ul>
                    
                    <h4>4. Hợp tác với bên thứ ba</h4>
                    <ul>
                        <li>Thuê chuyên gia tư vấn ESG</li>
                        <li>Tham gia các sáng kiến ngành</li>
                        <li>Hợp tác với các tổ chức phi lợi nhuận</li>
                    </ul>
                    
                    <h3>Case study: Công ty ABC</h3>
                    <p>Công ty ABC đã thành công trong việc đo lường tác động ESG bằng cách:</p>
                    <ol>
                        <li>Thiết lập hệ thống thu thập dữ liệu tự động</li>
                        <li>Đào tạo 20 nhân viên chuyên về ESG</li>
                        <li>Hợp tác với đại học để nghiên cứu phương pháp đo lường</li>
                        <li>Báo cáo minh bạch và thường xuyên</li>
                    </ol>
                    
                    <p>Kết quả: Giảm 30% chi phí đo lường, tăng 50% độ chính xác của dữ liệu.</p>
                    
                    <h3>Dự báo tương lai:</h3>
                    <p>Trong 3-5 năm tới, chúng ta sẽ thấy sự phát triển mạnh mẽ của các công nghệ đo lường ESG, giúp giảm chi phí và tăng độ chính xác. Các tiêu chuẩn quốc tế cũng sẽ được chuẩn hóa hơn, tạo điều kiện thuận lợi cho việc so sánh và đánh giá.</p>
                    
                    <p><em>Ms. Trần Lan - Chuyên gia tư vấn ESG, Cựu Giám đốc Bền vững tại Deloitte</em></p>
                """.trimIndent(),
                source = "Expert Insights",
                pillar = com.ignitech.esgcompanion.domain.entity.ESGPillar.SOCIAL,
                publishedAt = System.currentTimeMillis() - 345600000
            )
            else -> null
        }
    }
}
