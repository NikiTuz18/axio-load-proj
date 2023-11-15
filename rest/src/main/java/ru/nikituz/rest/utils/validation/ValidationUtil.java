package ru.nikituz.rest.utils.validation;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ValidationUtil {
    public static final String NSP_REGEXP = "^([А-ЯЁ][а-яё]+[\\-\\s]?){1}$";
    public static final String NAME_ERROR_MESSAGE = "Неверно указано Имя";
    public static final String SURNAME_ERROR_MESSAGE = "Неверно указана Фамилия";
    public static final String PATRONYMIC_ERROR_MESSAGE = "Неверно указано Отчество";
    public static final String BIRTH_DATE_NULL_ERROR_MESSAGE = "Не указана дата рождения";
    public static final String GENDER_NULL_ERROR_MESSAGE = "Не указана дата рождения";
    public static final String DOCUMENT_NULL_ERROR_MESSAGE = "Не указаны данные документа";

    public static final String DOCUMENT_SERIES_REGEXP = "^\\w{2, 4}$";
    public static final String DOCUMENT_SERIES_ERROR_MESSAGE = "Неверно указана СЕРИЯ документа";
    public static final String DOCUMENT_NUMBER_REGEXP = "^\\d{6, 8}$";
    public static final String DOCUMENT_NUMBER_ERROR_MESSAGE = "Неверно указан НОМЕР документа";
    public static final String DOCUMENT_TYPE_NULL_ERROR_MESSAGE = "Не указан тип документа";
    public static final String DOCUMENT_ISSUE_DATE_NULL_ERROR_MESSAGE = "Не указана дата выдачи документа";

}
