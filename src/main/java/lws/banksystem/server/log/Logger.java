package lws.banksystem.server.log;

import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Logger {

    public static void log(String msg) {
        GregorianCalendar calendar = new GregorianCalendar();
        DateFormat date = DateFormat.getDateInstance(DateFormat.MEDIUM);
        DateFormat time = DateFormat.getTimeInstance(DateFormat.MEDIUM);
        String prefix = "[" + date.format(calendar.getTime()) + " " + time.format(calendar.getTime()) + "] ";
        System.out.println(prefix + msg);
    }

}
