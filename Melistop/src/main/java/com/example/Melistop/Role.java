package com.example.Melistop;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Role {
    ADMIN(1),// Vai trò quản trị viên, có quyền cao nhất trong hệ thống.
    EMPLOYEE(2),// Vai trò nhân viên trong hệ thống, check order.
    USER(3); // Vai trò người dùng bình thường, có quyền hạn giới hạn.
    public final long value; // Biến này lưu giá trị số tương ứng với mỗi vai trò.

}
