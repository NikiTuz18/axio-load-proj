package ru.nikituz.rest.utils.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorPersonResponseTransfer {
    private String text;
}
