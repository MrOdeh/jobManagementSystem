package com.payoneer.dev.jobmanagementsystem.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class Validation {

    private static final String EMAIL_PATTERN = "^(.+)@payoneer.com$"; //immutable safety

    // let us suppose this is a germen phone number rgex but i will make a fake lookup
    private static final String MOBILE_PATTERN = "^\\+(?:[0-9] ?){6,14}[0-9]$";

    private static final String FAKE_MOBILE_PATTERN = "^[0-9]{7,9}$";

    public boolean isEmailValid(String email){
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean isNumberValid(String mobileNumber){
        Pattern pattern = Pattern.compile(FAKE_MOBILE_PATTERN);
        Matcher matcher = pattern.matcher(mobileNumber);
        return matcher.matches();
    }

    public boolean isDateValid(LocalDateTime localDateTime){
        // minus seconds to avoid delay issue
        return localDateTime.isAfter(LocalDateTime.now().minusSeconds(1));
    }
}
