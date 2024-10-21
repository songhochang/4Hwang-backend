package com.green.sahwang.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ImageResponse {

    private String filename;
    private String path;
    private String fileDesc;
}
