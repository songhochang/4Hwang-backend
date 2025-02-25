package com.green.sahwang.entity;

import com.green.sahwang.entity.enumtype.MemberRole;
import com.green.sahwang.entity.enumtype.SnsType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String name;

    // kakao 로그인 할때 가져옴
    @Enumerated(EnumType.STRING)
    private MemberRole role;

    private String phoneNum;

    // kakao 로그인 할때 가져옴
    private String email;

    private String password;

    @Embedded
    private Address address;

    // kakao 로그인 할때 가져옴
    private SnsType snsType;

    // kakao 로그인 할때 가져옴
    @Column(name = "nick_name")
    private String nickName;

    private Boolean adReceived;

    private String bankName;

    private String account;

    private Boolean gender;

    private Boolean isDeleted;

    // kakao 로그인 할때 가져옴
    private LocalDateTime joinDate;

    private LocalDateTime withdrawDate;

    private LocalDateTime lastLoginDate;

    // kakao 로그인 할때 가져옴
    private String profileImage;

    // kakao 로그인 할때 가져옴
    private String thumbnailImage;

    @Column(nullable = false, columnDefinition = "int default 0")
    private Integer warnCount = 0;

}
