# 本例用于说明如果在不使用xml的情况下如何配置一个SpringMVC项目
# Servlet容器启动时会扫描，当前应用里面每一个jar包ServletContainerInitializer的实现类
# 然后容器启动时会调用这个ServletContainerInitializer实现类的onStartup方法
# 并且会通过这个实现类上的@HandlesTypes注解将需要注入的组件传递过来（具体就是这个注解指定的类型下面的实现类和子接口等）
# 在onStartup方法里面将@HhandlesTypes注解指定的类型的实现类和子接口都拿到，并开始初始化根容器和子容器，并将这些拿到的实现类和子接口注入到容器里
# 从而实现SpringMVC的功能
# Servlet规范暴露了一个SPI
# 就是实现Servlet规范的WEB项目必须在/META-INF/services里面硬编码一个名称为
# javax.servlet.ServletContainerInitializer的文件，并且在这个文件里指定ServletContainerInitializer的实现类的包路径
# 这样Servlet容器在启动时就会根据这个指定的ServletContainerInitializer的实现类去执行上述操作了
# 当然是可以自己来实现这个实现类的，从而实现一个手写的SpringMVC
# SpringMVC的javax.servlet.ServletContainerInitializer这个文件位于spring-web-versionxxx.jar这个包里