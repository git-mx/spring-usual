# Spring的事务其实是依赖于数据库对事务的支持，如果数据库不支持事务，那么在Spring里的事务实现也就没有任何意义
# 用传统的JDBC操作MYSQL数据时如果要使用事务功能那么，伪代码如下
# 1.获取连接 Connection con = DriverManager.getConnection();
# 2.开启事务：con.setAutoCommit(false);
# 3.执行CRUD
# 4.提交/回滚事务 con.commit()/con.rollback
# 5.关闭连接
# Spring就是通过AOP扫描使用了@Transactional注解的类和方法，为这些类或者方法所在的类生成代理类
# 在生成的代理类中注入类似上面2和4的代码块，这样当这些代理类的方法被调用时，就会执行事务相关操作了
# SpringAOP在实现上如果被代理的对象实现了接口则默认使用JDK动态代理，否则采用CGLIB代理（CGLIB代理的原理是生成一个被代理类的子类）
# 无论是JDK动态代理CGLIB代理，被代理类里面的private方法和final方法是不会代理(拦截的)的
# 因为就JDK动态代理而言只能代理继承制接口的方法，而接口里是不可能存在private和final方法的
# 就CGLIB代理而言，在生成被代理类的子类的时候private方法不会被继承到子类里去，final方法也无法被子类覆盖
# 要让Spring的事务生效，那么必须遵循以下两点
# 1.@Transactional注解只能作用于pulic方法上
# 2.即使是被@Transactional的public方法如果这个方法被private方法调用，那么事务也是不会生效的
# 3.在被@Transactional方法作用的public方法里，如果抛出的异常并不是@Transactional的rollbackFor制定的异常的子类，那么方法内抛出异常事务是不会回滚的
#   比如@Transaction方法的rollbackFor = NullPointException 而方法里面抛出的是RunTimeException，那么事务时不会回滚的
# 4.一个没有被@Transactional注解的public方法调用一个本类的被@transactional作用的public方法,这时在后者里抛出异常事务不会生效
# 5.嵌套的事务方法调用，还要看事务的传播性设置
# Spring事务几个比较常用的传播特性：
# PROPAGATION_REQUIRED 默认配置，即如果已经在事务中则加入这个事务，如果没有事务则新建一个事务
# PROPAGATION_SUPPORTS 如果已经在事务中则加入该事务，如果没有则自己也不开启事务
# PROPAGATION_REQUIRED_NEW 如果已经在事务中则把当前事务挂起并开启一个新事务，这时内层事务与外层事务是两个
#                          独立的事务，如果即使内层事务回滚，外层事务如果捕获了内层事务抛出的异常那么可以继续执行
#                          否则外层事务也将回滚。反过来如果内层事务顺利提交，外层事务回滚，那么内层事务所作的修改是不可逆的
# PROPAGATION_NESTED 如果内层事务回滚，那么内层事务将回滚到它执行前的SavePoint，而外部事务可以有两种处理方式
#                    A.捕获异常，执行异常逻辑分支
#                    B.外部事务回滚/提交 代码不做任何修改，如果内部事务回滚到它被执行的SavePoint，外部事务将根据自己的具体
#                      配置决定自己是commit还是rollback
# 另外还有三种事务传播方式，几乎不会用到