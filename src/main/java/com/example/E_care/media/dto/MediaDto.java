package com.example.E_care.media.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MediaDto {
    private Long id;
    private byte[] fichier;
    private String type; // IMAGE, VIDEO, PDF...
}
