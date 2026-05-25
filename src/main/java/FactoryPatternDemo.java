interface Logger {
    void log(String message);
}

class FileLogger implements Logger {

    @Override
    public void log(String message) {
        System.out.println("将日志写入文件: " + message);
    }

}

class DatabaseLogger implements Logger {

    @Override
    public void log(String message) {
        System.out.println("将日志存入数据库: " + message);

    }

}

interface LoggerFactory {
    Logger createLogger();
}

class FileLoggerFactory implements LoggerFactory {

    @Override
    public Logger createLogger() {
        return new FileLogger();
    }

}

class DatabaseLoggerFactory implements LoggerFactory {

    @Override
    public Logger createLogger() {
        return new DatabaseLogger();
    }

}

public class FactoryPatternDemo {
    public static void main(String[] args) {
        // 1. 需要写文件日志时
        LoggerFactory fileFactory = new FileLoggerFactory();
        Logger logger1 = fileFactory.createLogger();
        logger1.log("系统启动成功");

        // 2. 需要写数据库日志时
        LoggerFactory dbFactory = new DatabaseLoggerFactory();
        Logger logger2 = dbFactory.createLogger();
        logger2.log("用户登录成功");
    }
}
