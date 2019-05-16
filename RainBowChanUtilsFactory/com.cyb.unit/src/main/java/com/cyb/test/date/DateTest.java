package com.cyb.test.date;

import com.cyb.util.date.DateUnsafeUtil;
import com.cyb.util.date.DateUtil;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTest {
    @Test
    public void testDate(){
        System.out.println(DateUnsafeUtil.calendar());
    }

    public static void main(String[] args) {
        System.out.println(DateUnsafeUtil.calendar());
    }

}
