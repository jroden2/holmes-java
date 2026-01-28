package com.buildtechwithJack.holmes.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LineDiff {
    private int lineNum;
    private String aHtml;
    private String bHtml;
    private String status;
}
