package com.nghlong3004.moneybot.constant;

public final class PromptConstant {

  public static final String CLASSIFY_TRANSACTION_PROMPT = "Thời gian hiện tại là: %s.\r\n" + "\r\n"
      + "Hãy phân tích đoạn văn sau:\r\n" + "\"%s\"\r\n" + "\r\n"
      + "Và trả về JSON đúng format:\r\n" + "{\r\n" + "  \"type\": \"THU\" hoặc \"CHI\",\r\n"
      + "  \"amount\": số tiền,\r\n" + "  \"category\": mô tả ngắn gọn cho loại thu/chi,\r\n"
      + "  \"period_of_day\": \"sáng\" hoặc \"trưa\" hoặc \"chiều\" hoặc \"tối\" nếu có, nếu không rõ thì để \"không xác định\",\r\n"
      + "  \"date\": định dạng yyyy-MM-dd (không cần giờ phút)\r\n" + "}\r\n" + "\r\n"
      + "Nếu đoạn văn nói \"tối qua\", \"sáng nay\" thì suy luận theo thời gian hiện tại đã cung cấp.\r\n"
      + "Chỉ trả về JSON, không thêm chú thích.";

}
