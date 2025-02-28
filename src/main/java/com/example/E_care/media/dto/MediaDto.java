package com.example.E_care.media.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MediaDto {
    private Long id;
    private String url;
    private String type; // IMAGE, VIDEO, PDF...
}
