package pers.hdh.servlet;
import pers.hdh.annotation.DhController;
import pers.hdh.annotation.DhQualifier;
import pers.hdh.annotation.DhRequestMapping;
import pers.hdh.annotation.DhService;
import pers.hdh.commons.CommonUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
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
//    缓存类的注解参数/类实例对象， 存储controller 和 service实例
    private Map<String,Object> instanceMaps = new HashMap<>();
//    请求url 和 与之对应的 handler的 method
    private Map<String, Method> handlerMaps = new HashMap<>();

//    controller实例
    private Map<String,Object> controllerMaps = new HashMap<>();

    @Override
    public void init(ServletConfig config) throws ServletException {
        try{

            //通过全自动扫描基本包下的bean,加载Springr容器
            String mvcConfig = config.getInitParameter("contextConfig;ocation")
                    .replace("*","").replace("classpath:","");
            String basePackName = CommonUtils.getBasePackName(mvcConfig);
            System.out.println("扫描的基包是："+basePackName);

            //全自动扫描基本包下的bean，加载Spring容器
            scanPack(basePackName);

            //通过注解对，找到每一个Bean,发射获取实例
            reflectBeansInstance();

            //依赖注入，实现IOC机制
            doIoc();

            //handlerMapping通过基部署和基于类的url 找到相应的处理器






        }catch (Exception e)
        {
            e.printStackTrace();
            if (e instanceof ServletException)
                new ServletException(e);
        }

    }


    
    private void initHandlerMapping()throws Exception{
        /** 
        * @Description:  通过对请求url分析之后拿到响应的handler实例里面的method处理
        * @Param: [] 
        * @return: void 
        * @Author: Huabuxiu 
        * @Date: 2019/3/14 
        */ 
        if (instanceMaps.isEmpty())
        {
            throw new   Exception("没有发现Handle对象");
        }

        for (Map.Entry<String, Object> entry :
                instanceMaps.entrySet()) {
            Class<?> aClass = entry.getValue().getClass();
            //通过实例区分Controller
            if (aClass.isAnnotationPresent(DhController.class)){
                //实现注解映射路由，允许当Contronller类没有使用@RequestMapping注解时
                //可使用@Dhcontorller的注解的value作为路径

                String classURI= "";
                if (aClass.isAnnotationPresent(DhRequestMapping.class)){
                    classURI = aClass.getAnnotation(DhRequestMapping.class).value();
                }else {
                    classURI = aClass.getAnnotation(DhController.class).value();
                }
                //遍历controller 类中每一个使用@DhRquestMapping的方法，细化路由请路径
                Method[] methods = aClass.getMethods();
                for (Method method:
                methods)
                {
                    if (method.isAnnotationPresent(DhRequestMapping.class)){    //有细化路径
                        String methodURI = method.getAnnotation(DhRequestMapping.class).value();

                        //存入handlerMaps
                        String url = ("/"+classURI+"/"+methodURI).replaceAll("/+","/");
                        handlerMaps.put(url,method);
                        //再维护一个只存储controller实例的map
                        controllerMaps.put(url,entry.getValue());
                    }
                }
            }
        }
    }




    private void doIoc()throws Exception{
        if (instanceMaps.isEmpty()){
            throw new Exception("没有发现可以注入的实例");
        }
        for (Map.Entry<String,Object> entry:instanceMaps.entrySet()){
            Field[] fields = entry.getValue().getClass().getDeclaredFields(); //获取类的所有属性
                    //遍历bean对象的字段
            for (Field fied :
                    fields) {
                if (fied.isAnnotationPresent(DhQualifier.class)){
                    //  通过bean字段对象上面的注解参数来注入实例
                    String insMapKey = fied.getAnnotation(DhQualifier.class).value();
                    if ("".equals(insMapKey)){      // 如果使用@DhController，@DhService没有配置value的值，默认使用类名 首字母小写
                        insMapKey = CommonUtils.toLowerFirstWord(fied.getType().getSimpleName());
                    }
                    fied.setAccessible(true);

                    //注入实例
                    fied.set(entry.getValue(),instanceMaps.get(insMapKey));
                }
            }
        }
    }


/**
* @Description: 通过注解对象找到每一个bean,反射获取实例
* @Param:
* @return:
* @Author: Huabuxiu
* @Date: 2019/3/13
*/
    private void reflectBeansInstance() throws Exception {
        if (beanNames.isEmpty())
        {
            return;
        }
        for (String className : beanNames){
            Class<?> aClass = Class.forName(className);

            if (aClass.isAnnotationPresent(DhController.class)) //控制层操作
            {
                Object controllerInstance = aClass.newInstance();   //创建实例
                //进一步对这个控制层实例对象打标签
                DhController controllerAnnotation = aClass.getAnnotation(DhController.class);   //获取注解

                String insMapKey = controllerAnnotation.value();

                if ("".equals(insMapKey)){      // 如果使用@DhController，@DhService没有配置value的值，默认使用类名 首字母小写
                    insMapKey = CommonUtils.toLowerFirstWord(aClass.getSimpleName());
                }
                instanceMaps.put(insMapKey,controllerAnnotation);       //放入注解map
            }else if (aClass.isAnnotationPresent(DhService.class)){
                Object serviceInstance = aClass.newInstance();
                //进一步对这个业务层实例对象打标签，维护到缓存中去
                DhService serviceAnnotation = aClass.getAnnotation(DhService.class);
                String insMapKey = serviceAnnotation.value();
                if ("".equals(insMapKey)){
                    insMapKey = CommonUtils.toLowerFirstWord(insMapKey);
                }
                instanceMaps.put(insMapKey,serviceAnnotation);
            }
        }
    }


        private void scanPack(String basePackName)throws Exception{
            URL url = this.getClass().getClassLoader().getResource("/"+CommonUtils.transferQualifiedToPath(basePackName));
            //读取到扫描包
            File dir = new File(url.getFile());
            File[] files = dir.listFiles();
            for (File file:
                files)
            {
                if (file.isDirectory())     //递归读取
                {
                    scanPack(basePackName+"."+file.getName());
                }else if (file.isFile()){
                    beanNames.add(basePackName +"."+file.getName().replace(".class",""));
                    System.out.println("扫描到的类有："+basePackName+"."+file.getName().replace(".class",""));
                }
            }
        }
}
