package com.buildtechwithJack.holmes.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompareResult {

    private boolean exactMatch;
    private boolean normalizedMatch;
    private int aLength;
    private int bLength;
    private String aHash;
    private String bHash;
    private List<LineDiff> lineDiff;

}
