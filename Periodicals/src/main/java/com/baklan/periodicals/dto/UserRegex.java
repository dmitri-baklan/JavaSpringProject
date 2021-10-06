package com.baklan.periodicals.dto;

public interface UserRegex {
    public static final String REGEX_NAME = "^[A-ZА-ЯІЄЮЇЩ&&[^ЫЪЭ]][\\w'\\-,.а-яієющї&&[^ыъэ]][^0-9_!¡?÷?¿\\\\+=@#$%ˆ&*(){}|~<>;:[\\\\]]{0,18}$";
    //public static final String REGEX_EMAIL = "(([^<>()\\\\.,;:\\s@\"]+(\\.[^<>()\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))";

}
