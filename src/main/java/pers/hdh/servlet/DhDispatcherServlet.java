package pers.hdh.servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @program: BaseSpringMVC
 * @description:
 * @author: Huabuxiu
 * @create: 2019-03-12 22:29
 **/
public class DhDispatcherServlet extends HttpServlet{
//    集合全自动扫描基础包下的类下的限定名
    private List<String> beanNames = new ArrayList<>();
//    缓存 类的注解参数/类实例对象， 存储controller 和 service实例
    private Map<String,Object> instanceMaps = new HashMap<>();
//    请求url/handler的method
    private Map<String, Method> handlerMaps = new HashMap<>();

//    controller实例
    private Map<String,Object> controllerMaps = new HashMap<>();

    @Override
    public void init(ServletConfig config) throws ServletException {
        try{

        }catch (Exception e)
        {
            e.printStackTrace();
            if (e instanceof ServletException)
                new ServletException(e);
        }

    }
}
