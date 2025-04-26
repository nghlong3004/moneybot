package com.nghlong3004.moneybot.constant;

public final class PromptConstant {

  public static final String CLASSIFY_TRANSACTION_PROMPT = "Thời gian hiện tại là: %s.\r\n" + "\r\n"
      + "Hãy phân tích đoạn văn sau:\r\n" + "\"%s\"\r\n" + "\r\n"
      + "Và trả về JSON đúng format:\r\n" + "{\r\n" + "  \"type\": \"income\" hoặc \"expense\",\r\n"
      + "  \"amount\": số tiền,\r\n" + "  \"category\": mô tả ngắn gọn cho loại income/expense,\r\n"
      + "  \"period_of_day\": \"sáng\" hoặc \"trưa\" hoặc \"chiều\" hoặc \"tối\" nếu có, nếu không rõ thì để \"không xác định\",\r\n"
      + "  \"date\": định dạng yyyy-MM-dd (không cần giờ phút)\r\n" + "}\r\n" + "\r\n"
      + "Nếu đoạn văn nói \"tối qua\", \"sáng nay\" thì suy luận theo thời gian hiện tại đã cung cấp.\r\n"
      + "Chỉ trả về mảng JSON, không thêm chú thích.";
  
  public static final String CONFIRM_TRANSACTION_REPLY_PROMPT = "Bạn hãy đọc JSON sau và viết một câu trả lời tự nhiên, thân thiện để xác nhận giao dịch cho người dùng:\r\n"
      + "\r\n"
      + "%s\r\n"
      + "\r\n"
      + "Quy định:\r\n"
      + "- type = \"income\" là thu nhập, \"expense\" là chi tiêu.\r\n"
      + "- periodOfDay có thể là \"sáng\", \"trưa\", \"chiều\", \"tối\" hoặc \"không xác định\".\r\n"
      + "- Viết 1 câu thân thiện, ngắn gọn, xác nhận lại thông tin giao dịch.\r\n"
      + "\r\n"
      + "Chỉ trả lời câu xác nhận, không phân tích thêm.";
  
  public static final String CHECK_IS_TRANSACTION_PROMPT = "Đọc đoạn tin nhắn sau:\r\n"
      + "\r\n"
      + "\"%s\"\r\n"
      + "\r\n"
      + "Cho biết đây có phải là một tin nhắn báo cho người nhận rằng đây là khoản tiền tiêu đi hoặc thu vào hay không?\r\n"
      + "\r\n"
      + "Chỉ trả lời đúng 1 từ: \"YES\" hoặc \"NO\".\r\n"
      + "và giải thích.";
  
}
