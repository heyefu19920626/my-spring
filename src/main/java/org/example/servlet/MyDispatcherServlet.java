package org.example.servlet;

import org.example.annotation.MyController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.*;

/**
 * Description:
 *
 * @author heyefu
 * Create in: 2019-12-16
 * Time: 11:05
 **/
public class MyDispatcherServlet extends HttpServlet {

    private static final String ROOT_PATH = "classpath:";

    /**
     * web.xml中配置的属性文件.
     */
    private Properties properties;

    /**
     * 扫描的所有类名.
     */
    private List<String> className = new ArrayList<>();

    /**
     * 存储所有的ioc类
     */
    private Map<String, Object> ioc = new HashMap<>();

    @Override
    public void init(ServletConfig config) throws ServletException {
        //1. 加载配置文件
        String contextConfigLocation = "contextConfigLocation";
        this.loadConfig(config.getInitParameter(contextConfigLocation));
        //2. 初始化扫描类
        this.initScanPackageClass(properties.getProperty("scan-package"));
        //3. IOC
        this.initIocClass();
        ioc.forEach((key, value) -> System.out.println(key + " : " + value));
        //4. Method
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    /**
     * Description:
     * <p>
     * 根据配置文件路径加载配置文件.
     *
     * @param configPath 配置文件路径
     * @author heyefu 14:40 2019/12/16
     **/
    private void loadConfig(String configPath) {
        if (configPath.startsWith(ROOT_PATH)) {
            configPath = configPath.replace(ROOT_PATH, "");
        }
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(configPath);
        if (properties == null) {
            properties = new Properties();
        }
        assert resourceAsStream != null;
        try {
            properties.load(resourceAsStream);
        } catch (IOException e) {
            System.out.println("加载配置文件出错");
            e.printStackTrace();
        }
    }

    /**
     * Description:
     * <p>
     * 扫描配置文件中scan-package路径下所有的类名，并存入className中.
     *
     * @param packagePath scan-package路径
     * @author heyefu 14:42 2019/12/16
     **/
    private void initScanPackageClass(String packagePath) {
        URL url = this.getClass().getClassLoader().getResource("/" + packagePath.replaceAll("\\.", "/"));
        assert url != null;
        File file = new File(url.getFile());
        for (File f : Objects.requireNonNull(file.listFiles())) {
            if (f.isDirectory()) {
                this.initScanPackageClass(packagePath + "." + f.getName());
            } else {
                className.add(packagePath + "." + f.getName().replaceAll("\\.class", ""));
            }
        }
    }

    private void initIocClass() {
        for (String s : className) {
            Class clz;
            Object obj;
            try {
                clz = Class.forName(s);
                //只实例化有MyController
                if (clz.isAnnotationPresent(MyController.class)) {
                    //将实例化的Controller放入IOC的Map,key为类名首字母小写
                    String name = s.substring(s.lastIndexOf(".") + 1);
                    name = name.substring(0, 1).toLowerCase() + name.substring(1);
                    obj = clz.getDeclaredConstructor().newInstance();
                    ioc.put(name, obj);
                }
            } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                System.out.printf("实例化[%s]失败", s);
                e.printStackTrace();
            }
        }
    }
}
