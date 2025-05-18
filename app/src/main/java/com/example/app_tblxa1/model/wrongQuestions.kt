package com.example.app_tblxa1.model

data class Question(
    val id: Int,
    val question_text: String,
    val answers: List<Answers>
)

data class Answer(
    val id: Int,
    val answer_text: String,
    val is_correct: Boolean
)

val wrongQuestions: List<Questions> = listOf(
    Questions(
        id = 1,
        question_text = "Phần của đường bộ được sử dụng cho các phương tiện giao thông qua lại là gì?",
        answers = listOf(
            Answers(id = 1, answer_text = "Phần mặt đường và lề đường.", is_correct = false),
            Answers(id = 2, answer_text = "Phần đường xe chạy.", is_correct = true),
            Answers(id = 3, answer_text = "Phần đường xe cơ giới.", is_correct = false)
        )
    ),
    Questions(
        id = 2,
        question_text = "“Làn đường” là gì?",
        answers = listOf(
            Answers(id = 4, answer_text = "Là một phần của phần đường xe chạy được chia theo chiều dọc của đường, sử dụng cho xe chạy.", is_correct = false),
            Answers(id = 5, answer_text = "Là một phần của phần đường xe chạy được chia theo chiều dọc của đường, có bề rộng đủ cho xe chạy an toàn.", is_correct = true),
            Answers(id = 6, answer_text = "Là đường cho xe ô tô chạy, dừng, đỗ an toàn.", is_correct = false)
        )
    ),
    Questions(
        id = 3,
        question_text = "Trong các khái niệm dưới đây, “dải phân cách” được hiểu như thế nào là đúng?",
        answers = listOf(
            Answers(id = 7, answer_text = "Là bộ phận của đường để ngăn cách không cho các loại xe vào những nơi không được phép.", is_correct = false),
            Answers(id = 8, answer_text = "Là bộ phận của đường để phân tách phần đường xe chạy và hành lang an toàn giao thông.", is_correct = false),
            Answers(id = 9, answer_text = "Là bộ phận của đường để phân chia mặt đường thành hai chiều xe chạy riêng biệt hoặc để phân chia phần đường của xe cơ giới và xe thô sơ.", is_correct = true)
        )
    ),
    Questions(
        id = 4,
        question_text = "“Dải phân cách” trên đường bộ gồm những loại nào?",
        answers = listOf(
            Answers(id = 10, answer_text = "Dải phân cách gồm loại cố định và loại di động.", is_correct = true),
            Answers(id = 11, answer_text = "Dải phân cách gồm tường chống ồn, hộ lan cứng và hộ lan mềm.", is_correct = false),
            Answers(id = 12, answer_text = "Dải phân cách gồm giá long môn và biển báo hiệu đường bộ.", is_correct = false)
        )
    ),
    Questions(
        id = 5,
        question_text = "Người lái xe được hiểu như thế nào trong các khái niệm dưới đây?",
        answers = listOf(
            Answers(id = 13, answer_text = "Là người điều khiển xe cơ giới.", is_correct = true),
            Answers(id = 14, answer_text = "Là người điều khiển xe thô sơ.", is_correct = false),
            Answers(id = 15, answer_text = "Là người điều khiển xe có súc vật kéo.", is_correct = false)
        )
    ),
    Questions(
        id = 6,
        question_text = "Đường được cắm biển báo hiệu là đường ưu tiên là loại đường gì?",
        answers = listOf(
            Answers(id = 16, answer_text = "Đường không ưu tiên.", is_correct = false),
            Answers(id = 17, answer_text = "Đường tỉnh lộ.", is_correct = false),
            Answers(id = 18, answer_text = "Đường quốc lộ.", is_correct = false),
            Answers(id = 19, answer_text = "Đường ưu tiên.", is_correct = true)
        )
    ),
    Questions(
        id = 7,
        question_text = "Khái niệm “phương tiện giao thông cơ giới đường bộ” được hiểu thế nào là đúng?",
        answers = listOf(
            Answers(id = 20, answer_text = "Gồm xe ô tô; máy kéo; xe mô tô hai bánh; xe mô tô ba bánh; xe gắn máy; xe cơ giới dùng cho người khuyết tật và xe máy chuyên dùng.", is_correct = false),
            Answers(id = 21, answer_text = "Gồm xe ô tô; máy kéo; rơ moóc hoặc sơ mi rơ moóc được kéo bởi xe ô tô, máy kéo; xe mô tô hai bánh; xe mô tô ba bánh, xe gắn máy (kể cả xe máy điện) và các loại xe tương tự.", is_correct = true)
        )
    ),
    Questions(
        id = 8,
        question_text = "Khái niệm “phương tiện giao thông thô sơ đường bộ” được hiểu thế nào là đúng?",
        answers = listOf(
            Answers(id = 22, answer_text = "Gồm xe đạp (kể cả xe đạp máy, xe đạp điện), xe xích lô, xe lăn dùng cho người khuyết tật, xe súc vật kéo và các loại xe tương tự.", is_correct = true),
            Answers(id = 23, answer_text = "Gồm xe đạp (kể cả xe đạp máy, xe đạp điện), xe gắn máy, xe cơ giới dùng cho người khuyết tật và xe máy chuyên dùng.", is_correct = false),
            Answers(id = 24, answer_text = "Gồm xe ô tô, máy kéo, rơ moóc hoặc sơ mi rơ moóc được kéo bởi xe ô tô, máy kéo.", is_correct = false)
        )
    ),
    Questions(
        id = 9,
        question_text = "“Phương tiện tham gia giao thông đường bộ” gồm những loại nào?",
        answers = listOf(
            Answers(id = 25, answer_text = "Phương tiện giao thông cơ giới đường bộ.", is_correct = false),
            Answers(id = 26, answer_text = "Phương tiện giao thông thô sơ đường bộ và xe máy chuyên dùng.", is_correct = false),
            Answers(id = 27, answer_text = "Cả ý 1 và ý 2.", is_correct = true)
        )
    ),
    Questions(
        id = 10,
        question_text = "“Người tham gia giao thông đường bộ” gồm những đối tượng nào?",
        answers = listOf(
            Answers(id = 28, answer_text = "Người điều khiển, người sử dụng phương tiện tham gia giao thông đường bộ.", is_correct = false),
            Answers(id = 29, answer_text = "Người điều khiển, dẫn dắt súc vật; người đi bộ trên đường bộ.", is_correct = false),
            Answers(id = 30, answer_text = "Cả ý 1 và ý 2.", is_correct = true)
        )
    ),
    Questions(
        id = 11,
        question_text = "“Người điều khiển phương tiện tham gia giao thông đường bộ” gồm những đối tượng nào dưới đây?",
        answers = listOf(
            Answers(id = 31, answer_text = "Người điều khiển xe cơ giới, người điều khiển xe thô sơ.", is_correct = false),
            Answers(id = 32, answer_text = "Người điều khiển xe máy chuyên dùng tham gia giao thông đường bộ.", is_correct = false),
            Answers(id = 33, answer_text = "Cả ý 1 và ý 2.", is_correct = true)
        )
    ),
    Questions(
        id = 12,
        question_text = "Khái niệm “người điều khiển giao thông” được hiểu như thế nào là đúng?",
        answers = listOf(
            Answers(id = 34, answer_text = "Là người điều khiển phương tiện tham gia giao thông tại nơi thi công, nơi ùn tắc giao thông, ở bến phà, tại cầu đường bộ đi chung với đường sắt.", is_correct = false),
            Answers(id = 35, answer_text = "Là cảnh sát giao thông, người được giao nhiệm vụ hướng dẫn giao thông tại nơi thi công, nơi ùn tắc giao thông, ở bến phà, tại cầu đường bộ đi chung với đường sắt.", is_correct = true),
            Answers(id = 36, answer_text = "Là người tham gia giao thông tại nơi thi công, nơi ùn tắc giao thông, ở bến phà, tại cầu đường bộ đi chung với đường sắt.", is_correct = false)
        )
    ),
    Questions(
        id = 13,
        question_text = "Trong các khái niệm dưới đây khái niệm “dừng xe” được hiểu như thế nào là đúng?",
        answers = listOf(
            Answers(id = 37, answer_text = "Là trạng thái đứng yên của phương tiện giao thông không giới hạn thời gian để cho người lên, xuống phương tiện, xếp dỡ hàng hóa hoặc thực hiện công việc khác.", is_correct = false),
            Answers(id = 38, answer_text = "Là trạng thái đứng yên tạm thời của phương tiện giao thông trong một khoảng thời gian cần thiết đủ để cho người lên, xuống phương tiện, xếp dỡ hàng hóa hoặc thực hiện công việc khác.", is_correct = true),
            Answers(id = 39, answer_text = "Là trạng thái đứng yên của phương tiện giao thông không giới hạn thời gian giữa 02 lần vận chuyển hàng hoá hoặc hành khách.", is_correct = false)
        )
    ),
    Questions(
        id = 14,
        question_text = "Khái niệm “đỗ xe” được hiểu như thế nào là đúng?",
        answers = listOf(
            Answers(id = 40, answer_text = "Là trạng thái đứng yên của phương tiện giao thông có giới hạn trong một khoảng thời gian cần thiết đủ để cho người lên, xuống phương tiện đó, xếp dỡ hàng hóa hoặc thực hiện công việc khác.", is_correct = false),
            Answers(id = 41, answer_text = "Là trạng thái đứng yên của phương tiện giao thông không giới hạn thời gian.", is_correct = true)
        )
    ),
    Questions(
        id = 15,
        question_text = "Cuộc đua xe chỉ được thực hiện khi nào?",
        answers = listOf(
            Answers(id = 42, answer_text = "Diễn ra trên đường phố không có người qua lại.", is_correct = false),
            Answers(id = 43, answer_text = "Được người dân ủng hộ.", is_correct = false),
            Answers(id = 44, answer_text = "Được cơ quan có thẩm quyền cấp phép.", is_correct = true)
        )
    )
)