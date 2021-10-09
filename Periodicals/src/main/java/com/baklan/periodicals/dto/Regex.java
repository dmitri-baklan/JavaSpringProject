package com.baklan.periodicals.dto;

public interface Regex {
    public static final String REGEX_NAME = "^[A-ZА-ЯІЄЮЇЩ&&[^ЫЪЭ]][\\w'\\-,.а-яієющї&&[^ыъэ]][^0-9_!¡?÷?¿\\\\+=@#$%ˆ&*(){}|~<>;:[\\\\]]$";
    //public static final String REGEX_EMAIL = "(([^<>()\\\\.,;:\\s@\"]+(\\.[^<>()\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))";

}
