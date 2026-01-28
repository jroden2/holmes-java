package com.buildtechwithJack.holmes.services;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.buildtechwithJack.holmes.models.CompareResult;
import com.buildtechwithJack.holmes.models.LineDiff;
import com.buildtechwithJack.holmes.utilities.RenderUtils;
import com.buildtechwithJack.holmes.utilities.SecurityUtils;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.SerializationFeature;

import java.util.List;

@Service("diffService")
public class DiffService {

    private static final Logger logger = LoggerFactory.getLogger(DiffService.class);

    public DiffService() {
    }

    private final JsonMapper objectMapper = JsonMapper.builder()
            .enable(SerializationFeature.INDENT_OUTPUT)
            .configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true)
            .build();


    public CompareResult compare(String textA, String textB, String mode,
                                 boolean ignoreWhitespace, boolean ignoreCase) {

        String sourceA = StringUtils.defaultString(textA);
        String sourceB = StringUtils.defaultString(textB);

        if ("json".equalsIgnoreCase(mode)) {
            sourceA = formatJson(sourceA);
            sourceB = formatJson(sourceB);
        }

        CompareResult result = new CompareResult();
        result.setALength(sourceA.length());
        result.setBLength(sourceB.length());
        result.setAHash(SecurityUtils.Sha3_256Hex(sourceA));
        result.setBHash(SecurityUtils.Sha3_256Hex(sourceB));

        result.setExactMatch(sourceA.equals(sourceB));

        String normA = normalize(sourceA, ignoreWhitespace, ignoreCase);
        String normB = normalize(sourceB, ignoreWhitespace, ignoreCase);
        result.setNormalizedMatch(normA.equals(normB));

        result.setLineDiff(RenderUtils.basicLineDiffWithHighlight(sourceA, sourceB));

        return result;
    }

    private String formatJson(String json) {
        try {
            Object obj = objectMapper.readValue(json, Object.class);
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            return json; // Fallback to raw if invalid
        }
    }

    private String normalize(String str, boolean ignoreWS, boolean ignoreCase) {
        if (ignoreWS) str = str.replaceAll("\\s+", "");
        if (ignoreCase) str = str.toLowerCase();
        return str;
    }

}
