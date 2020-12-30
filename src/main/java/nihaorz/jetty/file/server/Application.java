package nihaorz.jetty.file.server;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;

import java.io.File;

public class Application {

    public static void main(String[] args) throws Exception {
        validation(args);
        int port = Integer.parseInt(args[0]);
        long startTime = System.currentTimeMillis();
        Server server = new Server(port);
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(true);
        resourceHandler.setResourceBase(args[1]);
        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resourceHandler, new DefaultHandler()});
        server.setHandler(handlers);
        server.start();
        long endTime = System.currentTimeMillis();
        println("jetty正常启动,请访问 http://localhost:" + args[0]);
        println("jetty服务启动耗时:" + (endTime - startTime) + "ms");
        if (System.getProperty("os.name").toLowerCase().indexOf("windows") >= 0) {
            Runtime.getRuntime().exec("cmd /c start http://localhost:" + args[0]);
            println("当前操作系统是Windows，已自动为您打开浏览器");
        }
    }

    private static void validation(String[] args) {
        if (args.length < 2) {
            println("请添加端口参数及文件服务器根目录参数，命令格式如下：");
            println("java -jar jetty-file-server.jar 1024 \"D:\\SOFT\"");
            System.exit(1);
        }
        try {
            int port = Integer.parseInt(args[0]);
            if (port < 1024 || port > 65535) {
                println("端口号取值范围为：1024 - 65535 之间");
                System.exit(1);
            }
        } catch (NumberFormatException e) {
            println("端口号取值范围为：1024 - 65535 之间");
            System.exit(1);
        }
        File file = new File(args[1]);
        if (!file.exists()) {
            println(args[1] + " 目录不存在");
            System.exit(1);
        } else if (file.isFile()) {
            println(args[1] + " 不是目录");
            System.exit(1);
        }
    }

    private static void println(String text) {
        System.out.println(text);
    }

}
