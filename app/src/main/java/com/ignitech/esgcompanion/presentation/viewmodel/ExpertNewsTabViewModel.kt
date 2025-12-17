package com.ignitech.esgcompanion.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.ignitech.esgcompanion.presentation.screen.expert.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpertNewsTabViewModel @Inject constructor() : ViewModel() {
    
    private val _uiState = MutableStateFlow(ExpertNewsTabUiState())
    val uiState: StateFlow<ExpertNewsTabUiState> = _uiState.asStateFlow()
    
    private var navController: NavController? = null
    
    fun setNavController(navController: NavController) {
        this.navController = navController
    }
    
    fun loadNewsData() {
        viewModelScope.launch {
            // Load mock data for now
            _uiState.value = _uiState.value.copy(
                searchQuery = "",
                isFilterVisible = false,
                selectedCategory = null,
                selectedPillar = null,
                selectedTimeRange = null,
                breakingNews = listOf(
                    ESGNews(
                        id = "breaking_001",
                        title = "EU Announces New ESG Regulations",
                        summary = "European Union has just announced new ESG regulations that will take effect from 2025",
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
                    ),
                    ESGNews(
                        id = "breaking_002",
                        title = "Vietnam Commits to Net Zero 2050",
                        summary = "Vietnamese Government officially commits to achieving Net Zero goal by 2050",
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
                            
                            <p><em>Source: VnExpress, Ministry of Natural Resources and Environment</em></p>
                        """.trimIndent(),
                        source = "VnExpress",
                        pillar = com.ignitech.esgcompanion.domain.entity.ESGPillar.ENVIRONMENTAL,
                        publishedAt = System.currentTimeMillis() - 7200000,
                        isBreaking = true
                    )
                ),
                featuredNews = listOf(
                    ESGNews(
                        id = "featured_001",
                        title = "ESG Trends in Finance Industry 2024",
                        summary = "Detailed analysis of ESG trends shaping the global finance industry",
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
                    ),
                    ESGNews(
                        id = "featured_002",
                        title = "Green Technology: Solutions for the Future",
                        summary = "Green technologies are opening new opportunities for sustainable businesses",
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
                ),
                newsCategories = listOf(
                    NewsCategory("env", "Environmental", "🌱", 45),
                    NewsCategory("social", "Social", "👥", 32),
                    NewsCategory("governance", "Governance", "🏛️", 28),
                    NewsCategory("finance", "Finance", "💰", 15),
                    NewsCategory("tech", "Technology", "💻", 12)
                ),
                latestNews = listOf(
                    ESGNews(
                        id = "latest_001",
                        title = "ESG Reports of Major Banks",
                        summary = "Summary of ESG reports from the world's 10 largest banks",
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
                    ),
                    ESGNews(
                        id = "latest_002",
                        title = "Workforce Diversity in ESG",
                        summary = "The importance of workforce diversity in the ESG field",
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
                    ),
                    ESGNews(
                        id = "latest_003",
                        title = "Renewable Energy in Vietnam",
                        summary = "Potential and challenges of renewable energy in Vietnam",
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
                ),
                expertInsights = listOf(
                    ExpertInsight(
                        id = "insight_001",
                        title = "Expert View: The Future of ESG",
                        content = """
                            <h2>The Future of ESG: 5 Trends Shaping the Next Decade</h2>
                            
                            <p>As an expert with over 15 years of experience in the ESG field, I want to share insights on the trends that will shape the future of ESG in the next 5 years. This is not just a forecast but also an opportunity for businesses and individuals to prepare for the upcoming sustainability revolution.</p>
                            
                            <h3>1. Integration of AI and Machine Learning</h3>
                            <p>Artificial intelligence will become an indispensable tool in measuring, predicting and optimizing ESG impact. From analyzing satellite data to track deforestation to predicting climate risks, AI will help businesses make more accurate and timely decisions.</p>
                            
                            <h3>2. Global Standardization</h3>
                            <p>We will witness the convergence of ESG reporting standards globally. ISSB (International Sustainability Standards Board) will become the common standard, helping investors compare and evaluate businesses fairly.</p>
                            
                            <h3>3. Green Finance Explosion</h3>
                            <p>The green finance market will grow strongly, with an estimated 5 trillion USD by 2030. New financial products such as green transition bonds and carbon credits will become popular.</p>
                            
                            <h3>4. Real-time Impact Measurement</h3>
                            <p>IoT and blockchain will enable real-time ESG impact measurement, instead of annual reporting as currently. This will create complete transparency and higher accountability.</p>
                            
                            <h3>5. ESG Becomes Core Competitive Factor</h3>
                            <p>In the next 5 years, ESG will no longer be "nice to have" but become a core competitive factor. Businesses without strong ESG strategies will struggle to survive in the market.</p>
                            
                            <h3>Recommendations for Vietnamese Businesses:</h3>
                            <ol>
                                <li>Invest immediately in ESG technology and human resources</li>
                                <li>Establish transparent measurement and reporting systems</li>
                                <li>Participate in international ESG initiatives</li>
                                <li>Build sustainability culture from the inside out</li>
                            </ol>
                            
                            <p><em>Dr. Nguyễn Minh - Leading ESG Expert in Vietnam, Former Director of Sustainability Division at PwC</em></p>
                        """.trimIndent(),
                        expertName = "Dr. Nguyễn Minh",
                        expertId = "expert_001",
                        publishedAt = System.currentTimeMillis() - 259200000
                    ),
                    ExpertInsight(
                        id = "insight_002",
                        title = "Challenges in Measuring ESG Impact",
                        content = """
                            <h2>Challenges in Measuring ESG Impact: From Theory to Practice</h2>
                            
                            <p>After more than 10 years of working with businesses on ESG, I have found that measuring ESG impact remains the biggest challenge that most organizations are facing. Today, I want to share practical experiences and specific solutions to overcome these challenges.</p>
                            
                            <h3>Main Challenges:</h3>
                            
                            <h4>1. Lack of Quality Data</h4>
                            <p>Many businesses struggle to collect reliable ESG data. Data is often scattered, inconsistent and lacks comparability.</p>
                            
                            <h4>2. Difficulty in Quantifying Social Impact</h4>
                            <p>Unlike environmental impact which can be measured with specific numbers, social impact is often qualitative and difficult to quantify.</p>
                            
                            <h4>3. High Costs</h4>
                            <p>Setting up a professional ESG measurement system requires significant investment in technology and human resources.</p>
                            
                            <h4>4. Pressure from Stakeholders</h4>
                            <p>Each stakeholder has different measurement requirements, creating complexity in reporting.</p>
                            
                            <h3>Practical Solutions:</h3>
                            
                            <h4>1. Build Clear Measurement Framework</h4>
                            <ul>
                                <li>Use international standards such as GRI, SASB</li>
                                <li>Establish KPIs suitable for the industry</li>
                                <li>Clearly define measurement indicators</li>
                            </ul>
                            
                            <h4>2. Invest in Technology</h4>
                            <ul>
                                <li>Use specialized software for ESG</li>
                                <li>Integrate IoT to automatically collect data</li>
                                <li>Apply AI for analysis and forecasting</li>
                            </ul>
                            
                            <h4>3. Train Employees</h4>
                            <ul>
                                <li>In-depth ESG training programs</li>
                                <li>International certifications in ESG measurement</li>
                                <li>Share experiences from experts</li>
                            </ul>
                            
                            <h4>4. Collaborate with Third Parties</h4>
                            <ul>
                                <li>Hire ESG consulting experts</li>
                                <li>Participate in industry initiatives</li>
                                <li>Collaborate with non-profit organizations</li>
                            </ul>
                            
                            <h3>Case Study: Company ABC</h3>
                            <p>Company ABC successfully measured ESG impact by:</p>
                            <ol>
                                <li>Establishing automatic data collection system</li>
                                <li>Training 20 employees specialized in ESG</li>
                                <li>Collaborating with universities to research measurement methods</li>
                                <li>Reporting transparently and regularly</li>
                            </ol>
                            
                            <p>Result: Reduced 30% of measurement costs, increased 50% data accuracy.</p>
                            
                            <h3>Future Forecast:</h3>
                            <p>In the next 3-5 years, we will see strong development of ESG measurement technologies, helping to reduce costs and increase accuracy. International standards will also be more standardized, facilitating comparison and evaluation.</p>
                            
                            <p><em>Ms. Trần Lan - ESG Consulting Expert, Former Director of Sustainability at Deloitte</em></p>
                        """.trimIndent(),
                        expertName = "Ms. Trần Lan",
                        expertId = "expert_002",
                        publishedAt = System.currentTimeMillis() - 345600000
                    )
                )
            )
        }
    }
    
    fun updateSearchQuery(query: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(searchQuery = query)
        }
    }
    
    fun clearSearch() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(searchQuery = "")
        }
    }
    
    fun toggleFilter() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isFilterVisible = !_uiState.value.isFilterVisible)
        }
    }
    
    fun selectCategory(category: String?) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(selectedCategory = category)
        }
    }
    
    fun selectPillar(pillar: com.ignitech.esgcompanion.domain.entity.ESGPillar?) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(selectedPillar = pillar)
        }
    }
    
    fun selectTimeRange(timeRange: TimeRange?) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(selectedTimeRange = timeRange)
        }
    }
    
    fun openNews(newsId: String) {
        viewModelScope.launch {
            navController?.navigate("expert_news_detail/$newsId")
        }
    }
    
    fun toggleBookmark(newsId: String) {
        viewModelScope.launch {
            // TODO: Toggle bookmark for news
        }
    }
    
    fun shareNews(newsId: String) {
        viewModelScope.launch {
            // TODO: Share news
        }
    }
    
    fun viewAllNews() {
        viewModelScope.launch {
            // TODO: Navigate to all news screen
        }
    }
}

data class ExpertNewsTabUiState(
    val searchQuery: String = "",
    val isFilterVisible: Boolean = false,
    val selectedCategory: String? = null,
    val selectedPillar: com.ignitech.esgcompanion.domain.entity.ESGPillar? = null,
    val selectedTimeRange: TimeRange? = null,
    val breakingNews: List<ESGNews> = emptyList(),
    val featuredNews: List<ESGNews> = emptyList(),
    val newsCategories: List<NewsCategory> = emptyList(),
    val latestNews: List<ESGNews> = emptyList(),
    val expertInsights: List<ExpertInsight> = emptyList()
)

