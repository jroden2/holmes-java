package com.buildtechwithJack.holmes.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompareRequest {

    private String mode = "auto";
    private boolean ignoreWhitespace = false;
    private boolean ignoreCase = false;
    private String textA = "";
    private String textB = "";
    private MultipartFile fileA;
    private MultipartFile fileB;

}