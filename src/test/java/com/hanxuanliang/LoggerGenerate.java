package com.hanxuanliang;

import org.apache.log4j.Logger;

public class LoggerGenerate {

    private static Logger logger = Logger.getLogger(LoggerGenerate.class.getName());

    public static void main(String[] args) throws InterruptedException {
        int index = 0;
        while (true) {
            Thread.sleep(1000);
            logger.info("value: " + index++);
        }
    }
}
