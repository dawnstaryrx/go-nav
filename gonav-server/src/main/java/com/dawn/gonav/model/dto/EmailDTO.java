package com.dawn.gonav.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName: EmailDTO
 * Package: com.dawn.gonav.model.dto
 * Description:
 *
 * @Author yrx
 * @Create 2024/2/1 9:59
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDTO {
    // 收件邮箱
    private String email;
    // 标题
    private String title;
    // 内容
    private String content;
}
