package org.arqrifa.logica.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utilidades {

    public static Date formatearFecha(Date fecha) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(sdf.format(fecha));
    }

    public static int compararFechas(Date fecha1, Date fecha2) throws ParseException {
        return formatearFecha(fecha1).compareTo(formatearFecha(fecha2));
    }
}
