package org.example.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

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
    private List<String> className;

    @Override
    public void init(ServletConfig config) throws ServletException {
        //1. 加载配置文件
        String contextConfigLocation = "contextConfigLocation";
        loadConfig(config.getInitParameter(contextConfigLocation));
        //2. 初始化扫描类
        initScanPackageClass(properties.getProperty("scan-package"));
        //3. IOC
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
        System.out.println("packagePath:" + packagePath);
    }
}
