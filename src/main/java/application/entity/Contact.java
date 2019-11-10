package application.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* Created by Mybatis Generator on 2019/11/10
*/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Contact {
    /**
     * id
     */
    private Integer id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 座机号
     */
    private String phone;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 单位地址
     */
    private String companyAddress;

    /**
     * 家庭地址
     */
    private String homeAddress;

    /**
     * 头像地址
     */
    private String photo;

    /**
     * 备注
     */
    private String mark;

    /**
     * 姓名首字母
     */
    private String letterGroup;
}