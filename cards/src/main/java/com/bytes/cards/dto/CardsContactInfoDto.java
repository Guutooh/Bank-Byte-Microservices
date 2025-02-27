package com.bytes.cards.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

//@ConfigurationProperties(prefix = "cards")
@Data
public class CardsContactInfoDto {

    private String message;
    private Map<String, String> contactDetails;
    private List<String> onCallSupport;

}