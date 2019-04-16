package swarm_wars_library.network;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

    private String path = "./"+
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

    private boolean isLogging = true;

    private static Logger logger = new Logger();

    private Logger(){}

    public static Logger getInstance() {
        return logger;
    }

    public void log(String msg) throws IOException{
        if (isLogging) {
            File Logfile = new File(path + ".txt");
            if (!Logfile.exists()) {
                Logfile.createNewFile();
            }
            FileWriter outputStream = new FileWriter(Logfile, true);
            outputStream.write(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            outputStream.write(":");
            outputStream.write(msg);
            outputStream.write("\n");
            System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + ": " + msg);
        }
    }

    public void log(String msg, String side) throws IOException{
        if (isLogging) {
            System.out.println(path);
            File Logfile = new File(path + " " + side + ".txt");
            if (!Logfile.exists()) {
                Logfile.createNewFile();
            }
            FileWriter outputStream = new FileWriter(Logfile, true);
            outputStream.write(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            outputStream.write(":");
            outputStream.write(msg);
            outputStream.write("\n");
            outputStream.flush();
            System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + ": " + msg);
        }
    }

    public void setLogging(boolean isLogging){
        this.isLogging = isLogging;
    }

}
