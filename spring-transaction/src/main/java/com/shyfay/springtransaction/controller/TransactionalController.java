package com.shyfay.springtransaction.controller;


import com.shyfay.springtransaction.service.AService;
import com.shyfay.springtransaction.service.CService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Notes Spring事务传播行为测试
 * Spring的事务其实是依赖于数据库对事务的支持，如果数据库不支持事务，那么在Spring里的事务实现也就没有任何意义
 * Spring事务几个比较常用的传播特性：
 * PROPAGATION_REQUIRED 默认配置，即如果已经在事务中则加入这个事务，如果没有事务则新建一个事务
 * PROPAGATION_SUPPORTS 如果已经在事务中则加入该事务，如果没有则自己也不开启事务
 * PROPAGATION_REQUIRED_NEW 如果已经在事务中则把当前事务挂起并开启一个新事务，这时内层事务与外层事务是两个
 *                          独立的事务，如果即使内层事务回滚，外层事务如果捕获了内层事务抛出的异常那么可以继续执行
 *                          否则外层事务也将回滚。反过来如果内层事务顺利提交，外层事务回滚，那么内层事务所作的修改是不可逆的
 * PROPAGATION_NESTED 如果内层事务回滚，那么内层事务将回滚到它执行前的SavePoint，而外部事务可以有两种处理方式
 *                    A.捕获异常，执行异常逻辑分支
 *                    B.外部事务回滚/提交 代码不做任何修改，如果内部事务回滚到它被执行的SavePoint，外部事务将根据自己的具体
 *                      配置决定自己是commit还是rollback
 * 另外还有三种事务传播方式，几乎不会用到
 * @Author
 * @Since 7/5/2020
 */
@RestController
@RequestMapping("/transactional")
public class TransactionalController {
    @Autowired
    AService aService;

    @Autowired
    CService cService;

    @GetMapping(value="/fun1")
    public String fun1(){
        aService.fun1();
        return "success";
    }

    @RequestMapping(value="/fun2", method = RequestMethod.GET)
    public String fun2(){
        aService.fun2();
        return "success";
    }

    @RequestMapping(value="/fun3/{name}", method = RequestMethod.GET)
    public String fun3(@PathVariable("name")String name){
        System.out.println(name);
        return "success";
    }

    @RequestMapping(value="/fun4", method = RequestMethod.GET)
    public String fun4(){
        cService.fun1();
        return "success";
    }
}
