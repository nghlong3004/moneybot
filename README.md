# 📊 MoneyBot

📝 **MoneyBot** là một bot Telegram giúp bạn dễ dàng quản lý các khoản thu chi, từ đó kiểm soát dòng tiền hiệu quả hơn.

---

## ✨ Tính năng

- **Liên kết và quản lý dữ liệu** qua Google Sheets.
- **Theo dõi và quản lý giao dịch** nhanh chóng, tiện lợi.
- **Xử lý tự động** các tin nhắn và lệnh từ người dùng.
- **Trải nghiệm ngay:** [Telegram Bot - nghlong3004_moneybot](https://t.me/nghlong3004_moneybot)

### 📸 Demo

- ![image](https://github.com/user-attachments/assets/47c840e8-4963-4349-906e-4bca6fd25ce7)
- ![image](https://github.com/user-attachments/assets/406f61c5-965d-4634-8bca-8983a8f81684)
- ![image](https://github.com/user-attachments/assets/bc71c158-d3e6-4209-b71d-803bce0880d5)
- ![image](https://github.com/user-attachments/assets/0e565001-f819-46d7-b6ba-9d460e764c26)

---

## 🔧 Yêu cầu cài đặt

- Java 17
- PostgreSQL
- Google Sheets API
- Telegram Bot API

### ⚙️ Thiết lập cấu hình

- Điều chỉnh các thông số trong file `config.properties`.
- Tải file `credentials.json` từ [Google Cloud Console](https://console.cloud.google.com/) để liên kết với Google Sheets API.

---

## 🛠️ Cài đặt & Chạy Bot

1. **Clone repository về máy:**

```bash
git clone https://github.com/nghlong3004/moneybot.git
cd moneybot
```

2. **Cấu hình và chạy ứng dụng:**

Sau khi chỉnh sửa các thông số trong `config.properties` và đặt `credentials.json` vào đúng thư mục, bạn chạy lệnh:

```bash
./mvnw clean compile exec:java
```

---
