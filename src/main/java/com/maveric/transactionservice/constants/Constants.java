package com.maveric.transactionservice.constants;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Constants {
    public static LocalDateTime getCurrentDateTime() {
        return (java.time.LocalDateTime.now());
    }
}
