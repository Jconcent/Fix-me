package ru.school.fixme.router.validation;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

public class MessageHandler {

    private static final String SEPARATOR = String.valueOf((char)1);
    private static final Checksum CRC32 = new CRC32();

    private Map<Integer, String> tagsMap;
    private boolean isValid = true;

    public boolean validateAndMapMsg(String msg) {
        if (StringUtils.isNotBlank(msg)) {
            var tags = msg.split(SEPARATOR);
            if (tags.length > 1) {
                mapTags(tags);
                validateCheckSum(tagsMap, msg);
            }
        } else {
            isValid = false;
        }
        return isValid;
    }

    private void mapTags(String[] tags) {
        this.tagsMap = Arrays.stream(tags)
                .map(tag -> tag.split("="))
                .collect(Collectors.toMap(splitTag -> Integer.valueOf(splitTag[0]), splitTag -> splitTag[1]));
    }

    private void validateCheckSum(Map<Integer, String> tagsMap, String msg) {
        var msgCheckSumTag = tagsMap.get(10);
        if (StringUtils.isNotBlank(msgCheckSumTag)) {
            var msgCheckSum = Long.parseLong(msgCheckSumTag);
            var checkSumTagPosition = msg.indexOf("10=");
            var bytes = msg.getBytes();
            CRC32.update(bytes, 0, checkSumTagPosition);
            var checkSum = CRC32.getValue();
            System.out.println(checkSum + " " + msgCheckSum);
            if (msgCheckSum != checkSum) {
                isValid = false;
            }
        }
    }

    public Map<Integer, String> getTagsMap() {
        return tagsMap;
    }
}
