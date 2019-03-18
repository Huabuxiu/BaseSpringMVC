package pers.hdh.contorller;

import pers.hdh.annotation.DhController;
import pers.hdh.annotation.DhQualifier;
import pers.hdh.annotation.DhRequestMapping;
import pers.hdh.annotation.DhRequestParam;
import pers.hdh.service.TestService;
import pers.hdh.service.TestService2;
import pers.hdh.service.TestService2Impl;
import pers.hdh.service.TestServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: BaseSpringMVC
 * @description:
 * @author: Huabuxiu
 * @create: 2019-03-15 15:20
 **/

@DhController
@DhRequestMapping("/test")
public class TestController {

//    @DhQualifier("testService")
//    private TestService testService;
//
    @DhQualifier("testService2Impl")
    private TestService2 testService2;

    private TestService testService = new TestServiceImpl();
//    private TestService2 testService2 = new TestService2Impl();



    @DhRequestMapping("/1")
    public void test1(HttpServletRequest request, HttpServletResponse response, @DhRequestParam("str_param") String strParam,
                      @DhRequestParam("int_param") Integer intParam,
                      @DhRequestParam("float_param") Float floatParam,
                      @DhRequestParam("double_param") Double doubleParam){
        testService.doServiceTest();
        testService2.doServiceTest();
        try {
            response.getWriter().write(
                    "String parameter: " + strParam +
                            "\nInteger parameter: " + intParam +
                            "\nFloat parameter: " + floatParam +
                            "\nDouble parameter: " + doubleParam);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
