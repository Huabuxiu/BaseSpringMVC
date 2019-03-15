package pers.hdh.service;

import pers.hdh.annotation.DhService;


/**
 * @program: BaseSpringMVC
 * @description:
 * @author: Huabuxiu
 * @create: 2019-03-15 15:13
 **/
@DhService
public class TestServiceImpl implements TestService  {
    @Override
    public void doServiceTest() {
        System.out.println("业务层方法执行");
    }
}
