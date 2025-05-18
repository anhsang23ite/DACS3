package com.example.app_tblxa1.data

import com.example.app_tblxa1.R
import com.example.app_tblxa1.model.TrafficSign

// Danh sách các biển báo cấm
val bienBaoCam: List<TrafficSign> = listOf(
    TrafficSign(
        id2 = 1,
        imageResId2 = R.drawable.traffic_sign1,
        title2 = "Biển số P.124a \"Cấm quay đầu xe\"",
        description2 = "Để báo cấm các loại xe quay đầu (theo kiểu chữ U). Chiều mũi tên phù hợp với chiều cấm quay đầu. Biển không có giá trị riêng để đi sang hướng đường khác."
    ),
    TrafficSign(
        id2 = 2,
        imageResId2 = R.drawable.traffic_sign_2,
        title2 = "Biển số P.105 \"Cấm xe ô tô và xe máy\"",
        description2 = "Để báo đường cấm các loại xe cơ giới trừ xe máy được ưu tiên theo quy định."
    ),
    TrafficSign(
        id2 = 3,
        imageResId2 = R.drawable.traffic_sign_3,
        title2 = "Biển số P.101 \"Đường cấm\"",
        description2 = "Để báo đường cấm các loại phương tiện đi lại ở cả hai hướng, trừ các xe được ưu tiên theo quy định. Người đi bộ được phép đi trên vỉa hè hoặc lề đường."
    ),
    TrafficSign(
        id2 = 4,
        imageResId2 = R.drawable.traffic_sign_4,
        title2 = "Biển số P.102 \"Cấm đi ngược chiều\"",
        description2 = "Để báo đường cấm các loại xe cơ giới và thô sơ đi lại theo chiều đặt biển, trừ các xe được ưu tiên theo quy định. Người đi bộ được phép đi trên vỉa hè hoặc lề đường."
    ),
    TrafficSign(
        id2 = 5,
        imageResId2 = R.drawable.traffic_sign_5,
        title2 = "Biển số P.103a \"Cấm xe ô tô\"",
        description2 = "Để báo đường cấm các loại xe cơ giới kể cả xe máy 3 bánh có thùng qua đi, trừ xe máy 2 bánh, xe gắn máy và các xe được ưu tiên theo quy định."
    ),
    TrafficSign(
        id2 = 6,
        imageResId2 = R.drawable.traffic_sign_6,
        title2 = "Biển số P.103b \"Cấm xe ô tô rẽ phải\"",
        description2 = "Để báo đường cấm các loại xe cơ giới kể cả xe máy 3 bánh có thùng rẽ phải, trừ xe máy 2 bánh, xe gắn máy và các xe được ưu tiên theo quy định."
    ),
    TrafficSign(
        id2 = 7,
        imageResId2 = R.drawable.traffic_sign_7,
        title2 = "Biển số P.103c \"Cấm xe ô tô rẽ trái\"",
        description2 = "Để báo đường cấm các loại xe cơ giới kể cả xe máy 3 bánh có thùng rẽ trái, trừ xe máy 2 bánh, xe gắn máy và các xe được ưu tiên theo quy định."
    ),
    TrafficSign(
        id2 = 8,
        imageResId2 = R.drawable.traffic_sign_8,
        title2 = "Biển số P.104 \"Cấm xe máy\"",
        description2 = "Để báo đường cấm các loại xe máy trừ xe máy được ưu tiên theo quy định, phải đặt biển số P.104 \"Cấm xe máy\". Biển không có giá trị cấm những người đạp xe máy."
    ),
    TrafficSign(
        id2 = 9,
        imageResId2 = R.drawable.traffic_sign_9,
        title2 = "Biển số P.124b \"Cấm quay đầu xe\"",
        description2 = "Để báo cấm xe ô tô và xe máy 3 bánh có hiệu lực cấm xe ô tô và xe máy 3 bánh (side car) trừ các xe được ưu tiên theo quy định. Biển không có giá trị cấm rẽ trái để đi sang hướng đường khác."
    ),
    TrafficSign(
        id2 = 10,
        imageResId2 = R.drawable.traffic_sign_10,
        title2 = "Biển số P.106a \"Cấm xe ô tô tải\"",
        description2 = "Để báo đường cấm các loại xe ô tô tải trừ các xe được ưu tiên theo quy định, phải đặt biển số P.106a \"Cấm xe ô tô tải\". Biển có hiệu lực cấm đối với cả xe kéo và các xe máy chuyên dùng đi vào đoạn đường đặt biển số P.106a."
    ),
    TrafficSign(
        id2 = 11,
        imageResId2 = R.drawable.traffic_sign_11,
        title2 = "Biển số P.106b \"Cấm xe ô tô tải\"",
        description2 = "Để báo đường cấm các loại xe ô tô tải có khối lượng chuyên chở (xác định theo Giấy chứng nhận kiểm định an toàn kỹ thuật và bảo vệ môi trường phương tiện giao thông cơ giới đường bộ) lớn hơn giá trị chỉ số ghi trên biển (chỉ số ghi bằng màu trắng trên hình vẽ). Biển có hiệu lực cấm đối với cả xe kéo và các xe máy chuyên dùng đi vào đoạn đường đặt biển."
    ),
    TrafficSign(
        id2 = 12,
        imageResId2 = R.drawable.traffic_sign_12,
        title2 = "Biển số P.106c \"Cấm xe chở hàng nguy hiểm\"",
        description2 = "Để báo đường cấm các xe chở hàng nguy hiểm."
    ),
    TrafficSign(
        id2 = 30,
        imageResId2 = R.drawable.traffic_sign_30,
        title2 = "Biển số P.117 \"Hạn chế chiều cao\"",
        description2 = "Để báo hạn chế chiều cao của xe."
    ),
    TrafficSign(
        id2 = 31,
        imageResId2 = R.drawable.traffic_sign_31,
        title2 = "Biển số P.118 \"Hạn chế chiều ngang xe\"",
        description2 = "Để báo hạn chế chiều ngang của xe."
    ),
    TrafficSign(
        id2 = 32,
        imageResId2 = R.drawable.traffic_sign_32,
        title2 = "Biển số P.119 \"Hạn chế chiều dài xe\"",
        description2 = "Để báo đường cấm các loại xe (cơ giới và thô sơ) kể cả các xe được ưu tiên theo quy định, có độ dài toàn bộ kể cả xe và hàng lớn hơn trị số ghi trên biển đi qua."
    ),
    TrafficSign(
        id2 = 33,
        imageResId2 = R.drawable.traffic_sign_33,
        title2 = "Biển số P.120 \"Hạn chế chiều dài xe ô tô\"",
        description2 = "Để báo đường cấm các loại xe cơ giới kéo theo muioc kể cả ô tô sơ-mi-rơ-móc và các loại xe được ưu tiên kéo muioc theo luật nhà nước quy định, có độ dài toàn bộ kể cả xe, muioc và hàng lớn hơn trị số ghi trên biển đi qua."
    ),
)