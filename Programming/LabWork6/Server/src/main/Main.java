package main;

import managers.Invoker;
import managers.connection.ConnectionReceiver;
import org.apache.logging.log4j.Logger;
import receivers.MusicReceiver;
import receivers.UserReceiver;

import org.apache.logging.log4j.LogManager;

public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);
    public static void main(String[] args) {
        try {
            UserReceiver.GetInstance();
            MusicReceiver.GetInstance();
            logger.info("Files loaded");
            (new ConnectionReceiver()).run(new Invoker());
        } catch (Exception e){
            logger.error(e.getMessage());
        }
    }
}
