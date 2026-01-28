package com.buildtechwithJack.holmes.utilities;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class InputUtil {

    public static String processInput(String text, MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            return new String(file.getBytes(), StandardCharsets.UTF_8);
        }
        return text != null ? text : "";
    }

}
