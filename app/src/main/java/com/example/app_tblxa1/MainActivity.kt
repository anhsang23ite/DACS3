package com.example.app_tblxa1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.rememberPagerState
import kotlinx.coroutines.launch
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.text.style.TextAlign
import com.example.app_tblxa1.ui.theme.APP_TBLXA1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            APP_TBLXA1Theme {
                val navController = rememberNavController()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFF3F4F6)
                ) {
                    NavHost(navController = navController, startDestination = "main") {
                        composable("main") { MainScreen(navController) }
                        composable("theory") { TheoryScreen(navController) }
                        composable("exam") { ExamScreen(navController) }
                        composable("traffic_screen") { TrafficScreen(navController) } // Màn hình Biển báo
                        composable("tips_screen") { TipsScreen(navController) }      // Màn hình Mẹo thi
                        composable("questions_screen") { QuestionsScreen(navController) } // Màn hình Các câu hỏi
                    }
                }
            }
        }
    }
}

@Composable
fun MainScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "GPLX Hạng A1",
                color = Color(0xFF2563EB),
                fontSize = 22.sp, // Slightly smaller for flexibility
                fontWeight = FontWeight.Bold
            )
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Settings",
                tint = Color(0xFF2563EB),
                modifier = Modifier.size(28.dp) // Slightly larger for touch target
            )
        }

        // Banner Image
        Image(
            painter = painterResource(id = R.drawable.banner_image),
            contentDescription = "Banner Image",
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.4f) // Takes ~40% of available height, adjustable
                .clip(RoundedCornerShape(8.dp))
                .shadow(elevation = 4.dp, shape = RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop // Better for scaling
        )

        Spacer(modifier = Modifier.height(12.dp)) // Smaller spacing

        // Card Layout (Học lý thuyết và Thi sát hạch)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 2.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CardItem(
                imageResId = R.drawable.book,
                text = "Học lý thuyết",
                modifier = Modifier.weight(1f) // Equal width distribution
            ) {
                navController.navigate("theory")
            }
            CardItem(
                imageResId = R.drawable.directionscar,
                text = "Thi sát hạch",
                modifier = Modifier.weight(1f)
            ) {
                navController.navigate("exam")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Additional Cards (Biển báo, Mẹo thi, Các câu sai)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 2.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CardItem(
                imageResId = R.drawable.traffic,
                text = "Biển báo",
                modifier = Modifier.weight(1f)
            ) {
                navController.navigate("traffic_screen")
            }
            CardItem(
                imageResId = R.drawable.lightbulb,
                text = "Mẹo thi",
                modifier = Modifier.weight(1f)
            ) {
                navController.navigate("tips_screen")
            }
            CardItem(
                imageResId = R.drawable.email,
                text = "Các câu sai",
                modifier = Modifier.weight(1f)
            ) {
                navController.navigate("questions_screen")
            }
        }
    }
}

@Composable
fun CardItem(
    imageResId: Int,
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxWidth() // Takes allocated width from weight
            .aspectRatio(0.8f) // Keeps cards square for consistency
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(8.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp), // Internal padding for content
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = text,
                modifier = Modifier
                    .size(60.dp) // Smaller, flexible image size
                    .padding(bottom = 5.dp)
            )
            Text(
                text = text,
                color = Color(0xFF2563EB),
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp, // Smaller for flexibility
                textAlign = TextAlign.Center
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TheoryScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Học Lý Thuyết", color = Color.Black) },
                navigationIcon = {
                    IconButton(onClick = {
                        println("Back button clicked")
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White // Set background color here
                ),
                modifier = Modifier
                    .shadow(elevation = 4.dp, shape = RoundedCornerShape(8.dp))
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues) // Respect the top bar padding
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Card 1: KHÁI NIỆM VÀ QUY TẮC
                TheoryCard(
                    imageResId = R.drawable.icon_concept,
                    title = "KHÁI NIỆM VÀ QUY TẮC",
                    subtitle = "Gồm 83 câu hỏi (18 Câu điểm liệt)",
                    progress = "0/83"
                )

                // Card 2: VĂN HÓA VÀ ĐẠO ĐỨC LÁI
                TheoryCard(
                    imageResId = R.drawable.icon_culture,
                    title = "VĂN HÓA VÀ ĐẠO ĐỨC LÁI",
                    subtitle = "Gồm 5 câu hỏi",
                    progress = "0/5"
                )

                // Card 3: KỸ THUẬT LÁI XE
                TheoryCard(
                    imageResId = R.drawable.icon_driving,
                    title = "KỸ THUẬT LÁI XE",
                    subtitle = "Gồm 12 câu hỏi (2 Câu điểm liệt)",
                    progress = "0/12"
                )

                // Card 4: BIỂN BÁO ĐƯỜNG BỘ
                TheoryCard(
                    imageResId = R.drawable.icon_traffic_sign,
                    title = "BIỂN BÁO ĐƯỜNG BỘ",
                    subtitle = "Gồm 65 câu hỏi",
                    progress = "0/65"
                )

                // Card 5: SA HÌNH
                TheoryCard(
                    imageResId = R.drawable.icon_practice,
                    title = "SA HÌNH",
                    subtitle = "Gồm 35 câu hỏi",
                    progress = "0/35"
                )

                // Card 6: TỔNG HỢP CÂU ĐIỂM LIỆT
                TheoryCard(
                    imageResId = R.drawable.icon_dangerous,
                    title = "TỔNG HỢP CÂU ĐIỂM LIỆT",
                    subtitle = "Gồm 20 câu hỏi (20 Câu điểm liệt)",
                    progress = "0/20"
                )
            }
        }
    )
}
@Composable
fun TheoryCard(
    imageResId: Int,
    title: String,
    subtitle: String,
    progress: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(8.dp)),
        color = Color.White
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Image
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = title,
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Title and Subtitle
            Column {
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = subtitle,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.weight(1f)) // Giữ khoảng trống giữa Subtitle và Progress

            // Progress
            Text(
                text = progress,
                fontSize = 16.sp,
                color = Color(0xFF00BFA5) // Màu xanh lá
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExamScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Thi Sát Hạch", color = Color.Black) },
                navigationIcon = {
                    IconButton(onClick = {
                        println("Back button clicked")
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White // Set background color here
                ),
                modifier = Modifier
                    .shadow(elevation = 4.dp, shape = RoundedCornerShape(8.dp))
            )
        },
        content = { paddingValues ->
            LazyColumn( // Replaced Column with LazyColumn for scrolling
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues) // Respect the top bar padding
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp) // Spacing between items
            ) {
                // Card Layout
                items(8) { index -> // Use items to generate 5 cards
                    ExamCard(
                        questionCount = "25 câu hỏi",
                        examNumber = "Đề số ${index + 1}", // index starts at 0, so add 1
                        duration = "Thời gian 19 phút",
                        onStartExam = { /* Xử lý khi bắt đầu thi */ }
                    )
                }
            }
        }
    )
}

@Composable
fun ExamCard(
    questionCount: String,
    examNumber: String,
    duration: String,
    modifier: Modifier = Modifier,
    onStartExam: () -> Unit // Thêm callback
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .border(width = 1.dp, color = Color.Blue, shape = RoundedCornerShape(8.dp))
            .padding(16.dp),
        color = Color.White
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = questionCount,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = examNumber,
                fontSize = 16.sp,
                color = Color.Gray
            )
            Text(
                text = duration,
                fontSize = 16.sp,
                color = Color.Gray
            )
            Button(
                onClick = onStartExam, // Sử dụng onStartExam
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Blue)
            ) {
                Text(text = "Bắt đầu", color = Color.Blue)
            }
        }
    }
}
data class TrafficSign(
    val id: Int,
    val imageResId: Int,
    val title: String,
    val description: String
)

@Composable
fun TrafficSignList(signs: List<TrafficSign>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(signs) { trafficSign ->
            TrafficCard(trafficSign = trafficSign)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun FragmentBienBaoCam() {
    TrafficSignList(
        listOf(
            
            TrafficSign(1, R.drawable.traffic_sign1, "Biển số P.124a \"Cấm quay đầu xe\"", "Để báo cấm các loại xe quay đầu (theo kiểu chữ U). Chiều mũi tên phù hợp với chiều cấm quay đầu. Biển không có giá trị riêng để đi sang hướng đường khác."
            ),


            TrafficSign(2, R.drawable.traffic_sign_2, "Biển số P.105 \"Cấm xe ô tô và xe máy\"", "Để báo đường cấm các loại xe cơ giới trừ xe máy được ưu tiên theo quy định."
            ),


            TrafficSign(3, R.drawable.traffic_sign_3, "Biển số P.101 \"Đường cấm\"", "Để báo đường cấm các loại phương tiện đi lại ở cả hai hướng, trừ các xe được ưu tiên theo quy định. Người đi bộ được phép đi trên vỉa hè hoặc lề đường."
            ),


            TrafficSign(4, R.drawable.traffic_sign_4, "Biển số P.102 \"Cấm đi ngược chiều\"", "Để báo đường cấm các loại xe cơ giới và thô sơ đi lại theo chiều đặt biển, trừ các xe được ưu tiên theo quy định. Người đi bộ được phép đi trên vỉa hè hoặc lề đường."
            ),


            TrafficSign(5, R.drawable.traffic_sign_5, "Biển số P.103a \"Cấm xe ô tô\"", "Để báo đường cấm các loại xe cơ giới kể cả xe máy 3 bánh có thùng qua đi, trừ xe máy 2 bánh, xe gắn máy và các xe được ưu tiên theo quy định."
            ),


            TrafficSign(6, R.drawable.traffic_sign_6, "Biển số P.103b \"Cấm xe ô tô rẽ phải\"", "Để báo đường cấm các loại xe cơ giới kể cả xe máy 3 bánh có thùng rẽ phải, trừ xe máy 2 bánh, xe gắn máy và các xe được ưu tiên theo quy định."
            ),


            TrafficSign(7, R.drawable.traffic_sign_7, "Biển số P.103c \"Cấm xe ô tô rẽ trái\"", "Để báo đường cấm các loại xe cơ giới kể cả xe máy 3 bánh có thùng rẽ trái, trừ xe máy 2 bánh, xe gắn máy và các xe được ưu tiên theo quy định."),


            TrafficSign(8, R.drawable.traffic_sign_8, "Biển số P.104 \"Cấm xe máy\"", "Để báo đường cấm các loại xe máy trừ xe máy được ưu tiên theo quy định, phải đặt biển số P.104 \"Cấm xe máy\". Biển không có giá trị cấm những người đạp xe máy."),


            TrafficSign(9, R.drawable.traffic_sign_9, "Biển số P.124b \"Cấm quay đầu xe\"", "Để báo cấm xe ô tô và xe máy 3 bánh có hiệu lực cấm xe ô tô và xe máy 3 bánh (side car) trừ các xe được ưu tiên theo quy định. Biển không có giá trị cấm rẽ trái để đi sang hướng đường khác."),


            TrafficSign(10, R.drawable.traffic_sign_10, "Biển số P.106a \"Cấm xe ô tô tải\"", "Để báo đường cấm các loại xe ô tô tải trừ các xe được ưu tiên theo quy định, phải đặt biển số P.106a \"Cấm xe ô tô tải\". Biển có hiệu lực cấm đối với cả xe kéo và các xe máy chuyên dùng đi vào đoạn đường đặt biển số P.106a."),


            TrafficSign(11, R.drawable.traffic_sign_11, "Biển số P.106b \"Cấm xe ô tô tải\"", "Để báo đường cấm các loại xe ô tô tải có khối lượng chuyên chở (xác định theo Giấy chứng nhận kiểm định an toàn kỹ thuật và bảo vệ môi trường phương tiện giao thông cơ giới đường bộ) lớn hơn giá trị chỉ số ghi trên biển (chỉ số ghi bằng màu trắng trên hình vẽ). Biển có hiệu lực cấm đối với cả xe kéo và các xe máy chuyên dùng đi vào đoạn đường đặt biển."),


            TrafficSign(12, R.drawable.traffic_sign_12, "Biển số P.106c \"Cấm xe chở hàng nguy hiểm\"", "Để báo đường cấm các xe chở hàng nguy hiểm."),


            TrafficSign(30, R.drawable.traffic_sign_30, "Biển số P.117 \"Hạn chế chiều cao\"", "Để báo hạn chế chiều cao của xe."),


            TrafficSign(31, R.drawable.traffic_sign_31, "Biển số P.118 \"Hạn chế chiều ngang xe\"", "Để báo hạn chế chiều ngang của xe."),


            TrafficSign(32, R.drawable.traffic_sign_32, "Biển số P.119 \"Hạn chế chiều dài xe\"", "Để báo đường cấm các loại xe (cơ giới và thô sơ) kể cả các xe được ưu tiên theo quy định, có độ dài toàn bộ kể cả xe và hàng lớn hơn trị số ghi trên biển đi qua."),


            TrafficSign(33, R.drawable.traffic_sign_33, "Biển số P.120 \"Hạn chế chiều dài xe ô tô\"", "Để báo đường cấm các loại xe cơ giới kéo theo muioc kể cả ô tô sơ-mi-rơ-móc và các loại xe được ưu tiên kéo muioc theo luật nhà nước quy định, có độ dài toàn bộ kể cả xe, muioc và hàng lớn hơn trị số ghi trên biển đi qua.")

            )
    )
}

@Composable
fun FragmentBienHieuLenh() {
    TrafficSignList(
        listOf(
            TrafficSign(1, R.drawable.ic_hieu_lenh_1, "Biển số R.122 \"Dừng lại\"", "Để báo các xe (cơ giới và thô sơ) dừng lại."),
            TrafficSign(2, R.drawable.ic_hieu_lenh_2, "Biển số R.301a \"Hướng đi phải theo\"", "Khi đặt biển số R.301a ở trước nơi đường giao nhau thì hiệu lực tác dụng của biển là ở phạm vi khu vực đường giao nhau phía sau biển tức là cấm xe rẽ phải hay rẽ trái. Nếu biển này đặt ở sau nơi đường giao nhau (bắt đầu vào đoạn đường phố) thì hiệu lực tác dụng của biển là vị trí đặt biển đến nơi đường giao nhau tiếp theo.")
            )
    )
}

@Composable
fun FragmentBienChiDan() {
    TrafficSignList(
        listOf(
            TrafficSign(1, R.drawable.ic_chi_dan_1, "Biển số I.401 \"Bắt đầu đường ưu tiên\"", "Biểu thị ưu tiên cho các phương tiện trên đường có đặt biển này được đi trước."),
            TrafficSign(2, R.drawable.ic_chi_dan_2, "Biển số I.402 \"Hết đoạn đường ưu tiên\"", "Biểu thị hết đoạn đường quy định là ưu tiên."),
            )
    )
}

@Composable
fun FragmentBienBaoNguyHiem() {
    TrafficSignList(
        listOf(
            TrafficSign(1, R.drawable.bien_nguy_hiem_1, "Biển số W.201a \"Chỗ ngoặt nguy hiểm\"", "Báo trước sắp đến một chỗ ngoặt nguy hiểm vòng bên trái."),
            TrafficSign(2, R.drawable.bien_nguy_hiem_2, "Biển số W.201b \"Chỗ ngoặt nguy hiểm\"", "Báo trước sắp đến một chỗ ngoặt nguy hiểm vòng bên phải."),

            )
    )
}

@Composable
fun FragmentBienPhu() {
    TrafficSignList(
        listOf(
            TrafficSign(1, R.drawable.ic_bien_phu_1, "Biển số S.501 \"Phạm vi tác dụng của biển\"", "Để thông báo chiều dài đoạn đường nguy hiểm hoặc cấm hoặc hạn chế bên dưới một số biển báo nguy hiểm, biển báo cấm hoặc hạn chế."),
            TrafficSign(2, R.drawable.ic_bien_phu_2, "Biển số S.502 \"Khoảng cách đến đối tượng báo hiệu\"", "Để thông báo khoảng cách thực tế từ vị trí đặt biển đến đối tượng báo hiệu ở phía trước. Con số trên biển ghi theo đơn vị mét (m) và lấy chẵn đến hàng chục mét."),

            )
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun TrafficScreen(navController: NavHostController) {
    val tabTitles = listOf(
        "Biển báo cấm",
        "Biển hiệu lệnh",
        "Biển chỉ dẫn",
        "Nguy hiểm & cảnh báo",
        "Biển phụ"
    )
    val pagerState = rememberPagerState(pageCount = { tabTitles.size })
    val coroutineScope = rememberCoroutineScope() // Thêm scope của Compose

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Biển Báo Giao Thông", color = Color.Black) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White),
                modifier = Modifier.shadow(elevation = 4.dp, shape = RoundedCornerShape(8.dp))
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            ScrollableTabRow(
                selectedTabIndex = pagerState.currentPage,
                edgePadding = 8.dp
            ) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = {
                            coroutineScope.launch { // Sử dụng coroutineScope thay vì CoroutineScope(Dispatchers.Main)
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        text = { Text(title) }
                    )
                }
            }
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                Box(modifier = Modifier.fillMaxSize()) {
                    when (page) {
                        0 -> FragmentBienBaoCam()
                        1 -> FragmentBienHieuLenh()
                        2 -> FragmentBienChiDan()
                        3 -> FragmentBienBaoNguyHiem()
                        4 -> FragmentBienPhu()
                    }
                }
            }
        }
    }
}

@Composable
fun TrafficCard(
    trafficSign: TrafficSign,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(8.dp)),
        color = Color.White
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = trafficSign.imageResId),
                contentDescription = trafficSign.title,
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = trafficSign.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = trafficSign.description,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

data class TipSection(
    val title: String,
    val content: List<String>
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TipsScreen(navController: NavHostController) { // Added navController parameter
    val tipsList = listOf(
        TipSection(
            "Cấp phép",
            listOf(
                "Đường cấm dừng, cấm đỗ, cấm đi do UBND cấp tỉnh cấp",
                "Xe quá khổ, quá tải do: cơ quan quản lý đường bộ có thẩm quyền cấp phép"
            )
        ),
        TipSection(
            "Nồng độ cồn",
            listOf(
                "Người điều khiển xe mô tô, ô tô, máy kéo trên đường mà trong máu hoặc hơi thở có nồng độ cồn: Bị nghiêm cấm"
            )
        ),
        TipSection(
            "Khoảng cách an toàn tối thiểu",
            listOf(
                "35m nếu vận tốc lưu hành(v) = 60 (km/h)",
                "55m nếu 60 < v ≤ 80",
                "70m nếu 80 < v ≤ 100",
                "100m nếu 100 < v ≤ 120",
                "Dưới 60km/h: Chủ động và đảm bảo khoảng cách"
            )
        ),
        TipSection(
            "Hỏi về tuổi (T)",
            listOf(
                "Tuổi tối đa hạng E: nam 55, nữ 50",
                "Tuổi lấy bằng lái xe (cách nhau 3 tuổi):",
                " Gắn máy: 16T (dưới 50cm3)",
                " Mô tô + B1 + B2: 18T",
                " C, FB: 21T",
                " D, FC: 24T",
                " E, FD: 27T"
            )
        ),
        TipSection(
            "Trên đường cao tốc, trong đường hầm, đường vòng, đầu dốc, nơi tầm nhìn hạn chế",
            listOf(
                "Không được quay đầu xe, không lùi, không vượt",
                "Không được vượt trên cầu hẹp có một làn xe",
                "Không được phép quay đầu xe ở phần đường dành cho người đi bộ qua đường",
                "Cấm lùi xe ở khu vực cấm dừng và nơi đường bộ giao nhau"
            )
        ),
        TipSection(
            "Tại nơi giao nhau không có tín hiệu đèn",
            listOf(
                "Có vòng xuyến: Nhường đường bên trái",
                "Không có vòng xuyến: Nhường bên phải"
            )
        ),
        TipSection(
            "Niên hạn sử dụng (tính từ năm sx)",
            listOf(
                "25 năm: ô tô tải",
                "20 năm: ô tô chở người trên 9 chỗ"
            )
        ),
        TipSection(
            "Biển báo cấm",
            listOf(
                "Cấm ô tô (Gồm: mô tô 3 bánh, Xe Lam, xe khách) > Cấm xe tải > Cấm Máy kéo > Cấm rơ moóc, sơ mi rơ moóc"
            )
        ),
        TipSection(
            "Nhất chớm, nhì ưu, tam đường, tứ hướng",
            listOf(
                "Nhất chớm: Xe nào chớm tới vạch trước thì được đi trước",
                "Nhì ưu: Xe ưu tiên được đi trước. Thứ tự xe ưu tiên: Hỏa-Sự-An-Thương (Cứu hỏa - Quân sự - Công an - Cứu thương - Hộ đê - Đoàn xe tang)",
                "Tam đường: Xe ở đường chính, đường ưu tiên",
                "Tứ hướng: Thứ tự hướng: Bên phải trống - Rẽ phải - Đi thẳng - Rẽ trái",
                "Thứ tự ưu tiên với xe ưu tiên: Hỏa-Sự-An-Thương",
                " Hỏa: Xe Cứu hỏa",
                " Sự: Xe Quân sự",
                " An: Xe Công an",
                " Thương: Xe cứu thương",
                "Xe hộ đê, xe đi làm nhiệm vụ khẩn cấp",
                "Đoàn xe tang"
            )
        ),
        TipSection(
            "Các hạng GPLX",
            listOf(
                "A1: mô tô dưới 175 cm3 và xe 3 bánh của người khuyết tật",
                "A2: mô tô 175 cm3 trở lên",
                "A3: xe mô tô 3 bánh",
                "B1: không hành nghề lái xe",
                "B1, B2: đến 9 chỗ ngồi, xe tải dưới 3.500kg",
                "C: đến 9 chỗ ngồi, xe trên 3.500kg",
                "D: chở đến 30 người",
                "E: chở trên 30 người",
                "FC: C + kéo (ô tô đầu kéo, kéo sơ mi rơ moóc)",
                "FE: E + kéo (ô tô chở khách nối toa)"
            )
        ),
        TipSection(
            "Phân nhóm biển báo hiệu",
            listOf(
                "Biển nguy hiểm (hình tam giác vàng)",
                "Biển cấm (vòng tròn đỏ)",
                "Biển hiệu lệnh (vòng tròn xanh)",
                "Biển chỉ dẫn (vuông hoặc hình chữ nhật xanh)",
                "Biển phụ (vuông, chữ nhật trắng đen): Hiệu lực nằm ở biển phụ khi có đặt biển phụ"
            )
        ),
        TipSection(
            "Tốc độ tối đa TRONG khu vực đông dân cư",
            listOf(
                "60km/h: Đối với đường đôi hoặc đường 1 chiều có từ 2 làn xe cơ giới trở lên",
                "50km/h: Đối với đường 2 chiều hoặc đường 1 chiều có 1 làn xe cơ giới"
            )
        ),
        TipSection(
            "Tốc độ tối đa NGOÀI khu vực đông dân cư (trừ đường cao tốc)",
            listOf(
                "Đối với đường đôi hoặc đường 1 chiều có từ 2 làn xe cơ giới trở lên:",
                " 90km/h: Xe ô tô con, xe ô tô chở người đến 30 chỗ (trừ xe buýt), ô tô tải có trọng tải ≤3.5 tấn",
                " 80km/h: Xe ô tô chở người trên 30 chỗ (trừ xe buýt), ô tô tải có trọng tải >3.5 tấn (trừ ô tô xi téc)",
                " 70km/h: Ô tô buýt, ô tô đầu kéo kéo sơ mi rơ mooc, xe mô tô, ô tô chuyên dùng (trừ ô tô trộn vữa, trộn bê tông)",
                " 60km/h: Ô tô kéo rơ mooc, ô tô kéo xe khác, ô tô trộn vữa, ô tô trộn bê tông, ô tô xi téc",
                "Đối với đường 2 chiều hoặc đường 1 chiều có 1 làn xe cơ giới:",
                " 80km/h: Xe ô tô con, xe ô tô chở người đến 30 chỗ (trừ xe buýt), ô tô tải có trọng tải ≤3.5 tấn",
                " 70km/h: Xe ô tô chở người trên 30 chỗ (trừ xe buýt), ô tô tải có trọng tải >3.5 tấn (trừ ô tô xi téc)",
                " 60km/h: Ô tô buýt, ô tô đầu kéo kéo sơ mi rơ mooc, xe mô tô, ô tô chuyên dùng (trừ ô tô trộn vữa, trộn bê tông)",
                " 50km/h: Ô tô kéo rơ mooc, ô tô kéo xe khác, ô tô trộn vữa, ô tô trộn bê tông, ô tô xi téc"
            )
        ),
        TipSection(
            "Tốc độ tối đa cho phép đối với",
            listOf(
                "Xe máy chuyên dùng, xe gắn máy (kể cả xe máy điện) và các loại xe tương tự trên đường bộ (trừ đường cao tốc): 40km/h",
                "Tốc độ tối đa cho phép của các loại xe cơ giới, xe máy chuyên dùng trên đường cao tốc phải tuân thủ tốc độ tối đa, tốc độ tối thiểu và không vượt quá: 120km/h"
            )
        ),
        TipSection(
            "Tăng số, giảm số",
            listOf(
                "Tăng 1 Giảm 2 (giảm số chọn ý có từ “vù ga”)"
            )
        ),
        TipSection(
            "Phương tiện giao thông đường bộ",
            listOf(
                "Bao gồm phương tiện giao thông cơ giới đường bộ và phương tiện giao thông thô sơ đường bộ"
            )
        ),
        TipSection(
            "Phương tiện tham gia giao thông đường bộ",
            listOf(
                "Gồm phương tiện giao thông đường bộ và xe máy chuyên dùng"
            )
        ),
        TipSection(
            "Xe máy chuyên dùng",
            listOf(
                "Gồm xe máy thi công, xe máy nông nghiệp, lâm nghiệp và các loại xe đặc chủng khác sử dụng vào mục đích quốc phòng, an ninh có tham gia giao thông đường bộ"
            )
        ),
        TipSection(
            "Hiệu lệnh người điều khiển giao thông",
            listOf(
                "Giơ tay thẳng đứng: Tất cả dừng, trừ xe đã ở trong ngã tư được phép đi",
                "Giang ngang tay: Trái phải đi; Trước sau dừng",
                "Tay phải giơ trước: Sau, phải dừng, trước rẽ phải, trái đi các hướng, người đi bộ qua đường đi sau người điều khiển"
            )
        ),
        TipSection(
            "Khái niệm và quy tắc",
            listOf(
                "Tất cả các câu có đáp án bị nghiêm cấm, không cho phép hoặc không được phép thì chọn đáp án đó",
                "Tốc độ chậm đi về bên phải",
                "Chỉ sử dụng còi từ 5 giờ sáng đến 22 giờ tối",
                "Trong đô thị sử dụng đèn chiếu gần",
                "Không được phép lắp đặt còi đèn không đúng thiết kế, trừ phi được chấp thuận của cơ quan có thẩm quyền",
                "Xe mô tô không được kéo xe khác",
                "05 năm không cấp lại nếu sử dụng bằng lái đã khai báo mất",
                "Chuyển làn đường phải có tín hiệu báo trước",
                "Xe thô sơ phải đi làn đường nên phải trong cùng",
                "Tránh xe ngược chiều thì nhường đường qua đường hẹp và nhường xe lên dốc",
                "Đứng cách ray đường sắt 5m",
                "Vào cao tốc phải nhường đường cho xe đang chạy trên đường",
                "Xe thiết kế nhỏ hơn 70km/h không được vào cao tốc",
                "Trên cao tốc chỉ được dừng xe, đỗ xe ở nơi quy định",
                "Trong hầm chỉ được dừng xe, đỗ xe ở nơi quy định",
                "Xe quá tải trọng phải do cơ quan quản lý đường bộ cấp phép",
                "Trọng lượng xe kéo rơ moóc phải lớn hơn rơ moóc",
                "Kéo xe không hệ thống hãm phải dùng thanh nối cứng",
                "Xe gắn máy tối đa 40km/h",
                "Xe cơ giới không bao gồm xe gắn máy",
                "Đường có giải phân cách được xem là đường đôi",
                "Giảm tốc độ, chú ý quan sát khi gặp biển báo nguy hiểm",
                "Giảm tốc độ, đi sát về bên phải khi xe sau xin vượt",
                "Điểm giao cắt đường sắt thì ưu tiên đường sắt",
                "Nhường đường cho xe ưu tiên có tín hiệu còi, cờ, đèn",
                "Không vượt xe khác trên đường vòng, khuất tầm nhìn",
                "Nơi có vạch kẻ đường dành cho người đi bộ thì nhường đường",
                "Dừng xe, đỗ xe cách lề đường, hè phố không quá 0,25 mét",
                "Dừng xe, đỗ xe trên đường hẹp cách xe khác 20 mét",
                "Giảm tốc độ trên đường ướt, đường hẹp và đèo dốc"
            )
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Mẹo Ôn Tập Lý Thuyết GPLX",
                        color = Color.Black, // Changed to black for consistency
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        println("Back button clicked")
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White // Changed to white for consistency
                ),
                modifier = Modifier
                    .shadow(elevation = 4.dp, shape = RoundedCornerShape(8.dp))
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0x1E90FF)),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(tipsList) { section ->
                TipCard(section)
            }
        }
    }
}

@Composable
fun TipCard(section: TipSection) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = section.title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            section.content.forEach { tip ->
                Row {
                    Text(
                        text = "• ",
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                    Text(
                        text = tip,
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionsScreen(navController: NavHostController) { // Added navController parameter
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Màn hình Các Câu Sai",
                        color = Color.Black, // Match the color style of other screens
                        fontSize = 20.sp, // Adjusted font size to match typical TopAppBar title
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        println("Back button clicked")
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White // White background to match other screens
                ),
                modifier = Modifier
                    .shadow(elevation = 4.dp, shape = RoundedCornerShape(8.dp)) // Shadow effect
            )
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues), // Respect the top bar padding
            color = Color(0xFFF3F4F6)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Màn hình Các Câu Sai",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2563EB)
                )
            }
        }
    }
}