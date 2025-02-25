package com.green.sahwang.pendingsale.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSaleReqImageDto {

    private Long pendingSaleId;
    private String name;
    private String path;
    private String desc;
    private boolean isUsed;
}
