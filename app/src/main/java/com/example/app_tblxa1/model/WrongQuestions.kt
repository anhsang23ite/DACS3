package com.example.app_tblxa1.model

val wrongQuestions: List<Question> = listOf(
    Question(
        id1 = 1,
        question_texts1 = "Phần của đường bộ được sử dụng cho các phương tiện giao thông qua lại là gì?",
        answer1 = listOf(
            Answer(id1 = 1, answer_text1 = "Phần mặt đường và lề đường.", is_correct1 = false),
            Answer(id1 = 2, answer_text1 = "Phần đường xe chạy.", is_correct1 = true),
            Answer(id1 = 3, answer_text1 = "Phần đường xe cơ giới.", is_correct1 = false)
        )
    ),
    Question(
        id1 = 2,
        question_texts1 = "“Làn đường” là gì?",
        answer1 = listOf(
            Answer(id1 = 4, answer_text1 = "Là một phần của phần đường xe chạy được chia theo chiều dọc của đường, sử dụng cho xe chạy.", is_correct1 = false),
            Answer(id1 = 5, answer_text1 = "Là một phần của phần đường xe chạy được chia theo chiều dọc của đường, có bề rộng đủ cho xe chạy an toàn.", is_correct1 = true),
            Answer(id1 = 6, answer_text1 = "Là đường cho xe ô tô chạy, dừng, đỗ an toàn.", is_correct1 = false)
        )
    ),
    Question(
        id1 = 3,
        question_texts1 = "Trong các khái niệm dưới đây, “dải phân cách” được hiểu như thế nào là đúng?",
        answer1 = listOf(
            Answer(id1 = 7, answer_text1 = "Là bộ phận của đường để ngăn cách không cho các loại xe vào những nơi không được phép.", is_correct1 = false),
            Answer(id1 = 8, answer_text1 = "Là bộ phận của đường để phân tách phần đường xe chạy và hành lang an toàn giao thông.", is_correct1 = false),
            Answer(id1 = 9, answer_text1 = "Là bộ phận của đường để phân chia mặt đường thành hai chiều xe chạy riêng biệt hoặc để phân chia phần đường của xe cơ giới và xe thô sơ.", is_correct1 = true)
        )
    ),
    Question(
        id1 = 4,
        question_texts1 = "“Dải phân cách” trên đường bộ gồm những loại nào?",
        answer1 = listOf(
            Answer(id1 = 10, answer_text1 = "Dải phân cách gồm loại cố định và loại di động.", is_correct1 = true),
            Answer(id1 = 11, answer_text1 = "Dải phân cách gồm tường chống ồn, hộ lan cứng và hộ lan mềm.", is_correct1 = false),
            Answer(id1 = 12, answer_text1 = "Dải phân cách gồm giá long môn và biển báo hiệu đường bộ.", is_correct1 = false)
        )
    ),
    Question(
        id1 = 5,
        question_texts1 = "Người lái xe được hiểu như thế nào trong các khái niệm dưới đây?",
        answer1 = listOf(
            Answer(id1 = 13, answer_text1 = "Là người điều khiển xe cơ giới.", is_correct1 = true),
            Answer(id1 = 14, answer_text1 = "Là người điều khiển xe thô sơ.", is_correct1 = false),
            Answer(id1 = 15, answer_text1 = "Là người điều khiển xe có súc vật kéo.", is_correct1 = false)
        )
    ),
    Question(
        id1 = 6,
        question_texts1 = "Đường được cắm biển báo hiệu là đường ưu tiên là loại đường gì?",
        answer1 = listOf(
            Answer(id1 = 16, answer_text1 = "Đường không ưu tiên.", is_correct1 = false),
            Answer(id1 = 17, answer_text1 = "Đường tỉnh lộ.", is_correct1 = false),
            Answer(id1 = 18, answer_text1 = "Đường quốc lộ.", is_correct1 = false),
            Answer(id1 = 19, answer_text1 = "Đường ưu tiên.", is_correct1 = true)
        )
    ),
    Question(
        id1 = 7,
        question_texts1 = "Khái niệm “phương tiện giao thông cơ giới đường bộ” được hiểu thế nào là đúng?",
        answer1 = listOf(
            Answer(id1 = 20, answer_text1 = "Gồm xe ô tô; máy kéo; xe mô tô hai bánh; xe mô tô ba bánh; xe gắn máy; xe cơ giới dùng cho người khuyết tật và xe máy chuyên dùng.", is_correct1 = false),
            Answer(id1 = 21, answer_text1 = "Gồm xe ô tô; máy kéo; rơ moóc hoặc sơ mi rơ moóc được kéo bởi xe ô tô, máy kéo; xe mô tô hai bánh; xe mô tô ba bánh, xe gắn máy (kể cả xe máy điện) và các loại xe tương tự.", is_correct1 = true)
        )
    ),
    Question(
        id1 = 8,
        question_texts1 = "Khái niệm “phương tiện giao thông thô sơ đường bộ” được hiểu thế nào là đúng?",
        answer1 = listOf(
            Answer(id1 = 22, answer_text1 = "Gồm xe đạp (kể cả xe đạp máy, xe đạp điện), xe xích lô, xe lăn dùng cho người khuyết tật, xe súc vật kéo và các loại xe tương tự.", is_correct1 = true),
            Answer(id1 = 23, answer_text1 = "Gồm xe đạp (kể cả xe đạp máy, xe đạp điện), xe gắn máy, xe cơ giới dùng cho người khuyết tật và xe máy chuyên dùng.", is_correct1 = false),
            Answer(id1 = 24, answer_text1 = "Gồm xe ô tô, máy kéo, rơ moóc hoặc sơ mi rơ moóc được kéo bởi xe ô tô, máy kéo.", is_correct1 = false)
        )
    ),
    Question(
        id1 = 9,
        question_texts1 = "“Phương tiện tham gia giao thông đường bộ” gồm những loại nào?",
        answer1 = listOf(
            Answer(id1 = 25, answer_text1 = "Phương tiện giao thông cơ giới đường bộ.", is_correct1 = false),
            Answer(id1 = 26, answer_text1 = "Phương tiện giao thông thô sơ đường bộ và xe máy chuyên dùng.", is_correct1 = false),
            Answer(id1 = 27, answer_text1 = "Cả ý 1 và ý 2.", is_correct1 = true)
        )
    ),
    Question(
        id1 = 10,
        question_texts1 = "“Người tham gia giao thông đường bộ” gồm những đối tượng nào?",
        answer1 = listOf(
            Answer(id1 = 28, answer_text1 = "Người điều khiển, người sử dụng phương tiện tham gia giao thông đường bộ.", is_correct1 = false),
            Answer(id1 = 29, answer_text1 = "Người điều khiển, dẫn dắt súc vật; người đi bộ trên đường bộ.", is_correct1 = false),
            Answer(id1 = 30, answer_text1 = "Cả ý 1 và ý 2.", is_correct1 = true)
        )
    ),
    Question(
        id1 = 11,
        question_texts1 = "“Người điều khiển phương tiện tham gia giao thông đường bộ” gồm những đối tượng nào dưới đây?",
        answer1 = listOf(
            Answer(id1 = 31, answer_text1 = "Người điều khiển xe cơ giới, người điều khiển xe thô sơ.", is_correct1 = false),
            Answer(id1 = 32, answer_text1 = "Người điều khiển xe máy chuyên dùng tham gia giao thông đường bộ.", is_correct1 = false),
            Answer(id1 = 33, answer_text1 = "Cả ý 1 và ý 2.", is_correct1 = true)
        )
    ),
    Question(
        id1 = 12,
        question_texts1 = "Khái niệm “người điều khiển giao thông” được hiểu như thế nào là đúng?",
        answer1 = listOf(
            Answer(id1 = 34, answer_text1 = "Là người điều khiển phương tiện tham gia giao thông tại nơi thi công, nơi ùn tắc giao thông, ở bến phà, tại cầu đường bộ đi chung với đường sắt.", is_correct1 = false),
            Answer(id1 = 35, answer_text1 = "Là cảnh sát giao thông, người được giao nhiệm vụ hướng dẫn giao thông tại nơi thi công, nơi ùn tắc giao thông, ở bến phà, tại cầu đường bộ đi chung với đường sắt.", is_correct1 = true),
            Answer(id1 = 36, answer_text1 = "Là người tham gia giao thông tại nơi thi công, nơi ùn tắc giao thông, ở bến phà, tại cầu đường bộ đi chung với đường sắt.", is_correct1 = false)
        )
    ),
    Question(
        id1 = 13,
        question_texts1 = "Trong các khái niệm dưới đây khái niệm “dừng xe” được hiểu như thế nào là đúng?",
        answer1 = listOf(
            Answer(id1 = 37, answer_text1 = "Là trạng thái đứng yên của phương tiện giao thông không giới hạn thời gian để cho người lên, xuống phương tiện, xếp dỡ hàng hóa hoặc thực hiện công việc khác.", is_correct1 = false),
            Answer(id1 = 38, answer_text1 = "Là trạng thái đứng yên tạm thời của phương tiện giao thông trong một khoảng thời gian cần thiết đủ để cho người lên, xuống phương tiện, xếp dỡ hàng hóa hoặc thực hiện công việc khác.", is_correct1 = true),
            Answer(id1 = 39, answer_text1 = "Là trạng thái đứng yên của phương tiện giao thông không giới hạn thời gian giữa 02 lần vận chuyển hàng hoá hoặc hành khách.", is_correct1 = false)
        )
    ),
    Question(
        id1 = 14,
        question_texts1 = "Khái niệm “đỗ xe” được hiểu như thế nào là đúng?",
        answer1 = listOf(
            Answer(id1 = 40, answer_text1 = "Là trạng thái đứng yên của phương tiện giao thông có giới hạn trong một khoảng thời gian cần thiết đủ để cho người lên, xuống phương tiện đó, xếp dỡ hàng hóa hoặc thực hiện công việc khác.", is_correct1 = false),
            Answer(id1 = 41, answer_text1 = "Là trạng thái đứng yên của phương tiện giao thông không giới hạn thời gian.", is_correct1 = true)
        )
    ),
    Question(
        id1 = 15,
        question_texts1 = "Cuộc đua xe chỉ được thực hiện khi nào?",
        answer1 = listOf(
            Answer(id1 = 42, answer_text1 = "Diễn ra trên đường phố không có người qua lại.", is_correct1 = false),
            Answer(id1 = 43, answer_text1 = "Được người dân ủng hộ.", is_correct1 = false),
            Answer(id1 = 44, answer_text1 = "Được cơ quan có thẩm quyền cấp phép.", is_correct1 = true)
        )
    )
)