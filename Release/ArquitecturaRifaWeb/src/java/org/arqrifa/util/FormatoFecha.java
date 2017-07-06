package org.arqrifa.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatoFecha {
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String HOUR_FORMAT = "HH:mm";
    private static final String DATE_TIME_FORMAT = DATE_FORMAT + " " + HOUR_FORMAT;
    
    public static Date convertirFecha(String fecha) throws ParseException{
        return new SimpleDateFormat(DATE_TIME_FORMAT).parse(fecha);
    }
    
    public static String formatearFecha(Date fecha) throws ParseException{
        return new SimpleDateFormat(DATE_FORMAT).format(fecha);
    }
    
    public static String formatearHora(Date fecha) throws ParseException{
        return new SimpleDateFormat(HOUR_FORMAT).format(fecha);
    }
}
