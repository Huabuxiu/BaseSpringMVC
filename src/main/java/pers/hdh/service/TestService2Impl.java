package pers.hdh.service;

import pers.hdh.annotation.DhService;

/**
 * @program: BaseSpringMVC
 * @description:
 * @author: Huabuxiu
 * @create: 2019-03-15 17:20
 **/

@DhService
public class TestService2Impl implements TestService2{
    @Override
    public void doServiceTest() {
        System.out.println("Service2 方法运行");
    }
}
