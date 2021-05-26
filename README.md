# tcp-ip 

- đây là 1 demo về sử dụng TCP/IP cho viết chat trức tuyến.
- ở đây tôi sử dụng một file wav để giả lập dữ liệu âm thanh cuộc gọi.
- các config liên qua tới user tôi để tại file Common.java

  - project demo này chia làm 2 phần: 1 client và 1 là server.
  - môi trường phát triển client: java swing( java-8).
  - môt trường phát triển server: java-8
  - IDE sử dụng cho phát triển là netbean
  - mã khóa ở đây tôi dùng là RSA- 1024bit.

- dữ liệu được gửi đi là object (Data - class: Data).
- Trong object này chứa data sử dụng chao đổi là 117 byte. Do RSA 1024 bít, nên số byte tối đa 1 lần mã hóa là 117 byte.
- cấu trúc dữ liệu gửi đi.

  + byte số 1: chứa action tức là loại hành động muốn server thực hiện.
  + byte số 2: chứa trạng thái của server trả về cho client.
  + số byte còn lại chứa dữ liệu thông tin gửi cho server.
