package com.nghlong3004.moneybot.constant;

public final class PromptConstant {

  public static final String CLASSIFY_TRANSACTION_PROMPT = "Thời gian hiện tại là: %s.\r\n" + "\r\n"
      + "Hãy phân tích đoạn văn sau:\r\n" + "\"%s\"\r\n" + "\r\n"
      + "Và trả về JSON đúng format sau:\r\n" + "{\r\n"
      + "  \"type\": \"income\" hoặc \"expense\",\r\n"
      + "  \"amount\": số tiền (ví dụ: 30k = 30000, 30 cành = 30000),\r\n"
      + "  \"category\": mô tả ngắn gọn cho loại income/expense,\r\n"
      + "  \"period_of_day\": \"sáng\", \"trưa\", \"chiều\" hoặc \"tối\" nếu có, nếu không rõ thì lấy theo thời gian hiện tại,\r\n"
      + "  \"date\": định dạng yyyy-MM-dd (không cần giờ phút),\r\n"
      + "  \"spending_type\": phân loại khoản chi theo các nhóm sau nếu là chi tiêu:\r\n"
      + "    - \"ăn ngoài\"\r\n"
      + "    - \"đi chợ\"\r\n"
      + "    - \"chi tiêu bắt buộc\"\r\n"
      + "    - \"chi tiêu khác\"\r\n"
      + "    - \"phương tiện đi lại\"\r\n"
      + "    - \"đầu tư\"\r\n"
      + "Nếu là thu nhập (income) thì trường \"spending_type\" bỏ trống hoặc để null.\r\n"
      + "}\r\n"
      + "\r\n"
      + "Nếu đoạn văn nói \"tối qua\", \"sáng nay\" thì suy luận theo thời gian hiện tại đã cung cấp.\r\n"
      + "Chỉ trả về JSON, không thêm chú thích hay giải thích gì khác.";
  
  public static final String CONFIRM_TRANSACTION_REPLY_PROMPT = "Bạn là một trợ lý tài chính thân thiện và biết quan tâm đến cảm xúc người dùng.\r\n"
      + "\r\n"
      + "Dưới đây là một JSON mô tả một giao dịch của người dùng:\r\n"
      + "\r\n"
      + "%s\r\n"
      + "\r\n"
      + "Hãy viết một câu trả lời xác nhận tự nhiên, giống như bạn đang trò chuyện với người dùng.\r\n"
      + "\r\n"
      + "Luôn xác nhận rằng bạn đã ghi nhận giao dịch.  \r\n"
      + "Nếu nội dung giao dịch liên quan đến sức khỏe, ăn uống, hay những chi tiêu mệt mỏi (ví dụ: mua thuốc, viện phí, ăn khuya, nạp năng lượng...), hãy thêm một lời nhắn nhẹ nhàng để động viên hoặc chúc sức khỏe.\r\n"
      + "\r\n"
      + "Hãy nói tự nhiên, ấm áp. Không dùng dấu **\r\n"
      + "";
  
  public static final String CHECK_IS_TRANSACTION_PROMPT = "Đọc đoạn tin nhắn sau:\r\n"
      + "\r\n"
      + "\"%s\"\r\n"
      + "\r\n"
      + "Cho biết đây có phải là một tin nhắn thông báo rằng đây là khoản tiền tiêu đi hoặc thu vào hay không?\r\n"
      + "\r\n"
      + "Chỉ trả lời đúng 1 từ: \"YES\" hoặc \"NO\".\r\n"
      + "và giải thích.";
}
