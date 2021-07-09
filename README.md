# SpringBootTemp
SpringBoot的项目创建模板
包括：基于jpa数据库封装处理库，控制器（api规范与页面，模板），过滤器，异常处理，任务调度job，表单处理，
日志处理，队列处理，自定义项目配置，常用工具，vo与表单，面对切片编程（示例分布式job锁与注入式日志操作）
拦截器，基于security的权限访问控制(常规权限管理，图片验证码，手机验证码，google验证码，随机验证码，
访问频率控制，ip限制)
 
## apihelper
统一所有api返回格式，建议所有api在返回值里都返回一个ApiResult对你，这是一个泛型类,ApiResultUtils是api的快捷使用工具类
## aspect
切面实现，这里默认有两个实例

    JobsLockAop：其handleControllerMethod方法演示了，使用切面将所有job的加上分布式锁，
                 原因是当我们的项目是分布式项目时，我们只允许我们的job每次只有一个事件在运行
    weblogs:    演示了使用切面实现注入的方式写日志，ControllerLog是一个注入接口，通过他可以在WebLogAspect
                中拦截触发了这个接口的事件，有点类似下面的拦截器，不过这个可以应该在任何方法上，只要在应用的方法上注入：
                @ControllerLog(description = "调用测试日志的方法", logstype = LogsTypeEnum.LOGS_SETTING)
                public ApiResult<String> addLog(){
                    return  ApiResultUtils.success("请求成功");
                }

## configs 
一些全局性的配置，默认这里有两个配置，一个是多语言配置，一个是ControllerAdvice配置，
基于ControllerAdvice（RestControllerAdvice）注解的全局控制器管理类、

        在Spring 3.2中，新增了@ControllerAdvice、@RestControllerAdvice 注解
        其中@RestControllerAdvice是不用在方法上添加@ResponseBody注解就能将结果输出为json格式，
        由于我的处理可能会是直接返回页面，所以我觉得使用@ControllerAdvice更方便些
        我目前主要用它来处理全局异常，还有自定义异常，所以会用到ExceptionHandler注解，
        如果页面当前项目用到了模板引擎，我还可以为所有界面添加通用性的属性，如当前登录的用户名，
        当前系统配置对象，当前系统语言包对象，就还会用到ModelAttribute注解
        可以用于定义@ExceptionHandler、@InitBinder、@ModelAttribute，
        并应用到所有@RequestMapping、@PostMapping， @GetMapping注解中。
还有一个是 enc 是对application.yml加密处理,因为往往在项目中有些机密的配置信息，我们不想暴露在
配置文件中

        在DefaultEncryptor 中可以自定义自己的加密算法与解密算法，我这里使用的是AES，如果要在
        配置中使用加密信息，只要这样即可： 
        username: ENC(ArpBR3b9riPNlhEBEmLUYw==)
        password: ENC(He2E5x0aDghH1H+xS9f8Vw==)

## controller
    这是控制器相关的源码所在位置，一般来说包括两种，纯API与带模板页面
    apis为纯API具体请参考里面的两个文件,api的返回值可以是任何实体类型，
    不过建议统一使用ApiResult类
    pages为带模板的页面类:所有的页面类建议继承PageBase，这样要绑定template下的模板只要调用 
    getTemPath("页面模板名称") 即可

## 三层架构 dao pojo service
这三个要联合起来使用，因为这里采用了一个通用的便捷的三层设计模式，你要操作某个表，只要按以下三个步骤
即可：
    
    1.创建pojo类
        对pojo类的创建，要有个约束，就是一定要继承于BaseModel,并且实现返回Id的方法
    2.创建dao类
        dao类要继承于BaseDao,这样就具备了dao默认常用的操作方法，如果要有自动定的方法可以
        创建的类上自行编写即可
    3.创建service类
        如LogsServiceImpl，service类要继承自BaseServiceImpl<实体类, 主键类型>,并且要返回
        上面的dao实例,如

        @Autowired
        private LogsDao dataDao;
        @Override
        public BaseDao<Logs, Integer> getDAO() {
            return dataDao;
        }
    这样service就具备了以下方法:
    
    public T save(T t)  //保存一个实体
    public Iterable<T> save(Iterable<T> entities) //同时保存多个实体
    public void delete(ID id)   // 从id删除一条记录
    public void delete(T t)     //实体删除一条记录
    public T findOne(ID id)     //查找某个id的记录
    public List<T> findAll()    //返回所有记录
    public Page<T> findAll(Pageable pageable)   //分布返回记录无条件
    public T findOne(Map<String, Object> params)    //根据条件查找一条记录
    public List<T> list(final Map<String, Object> params)   //根据指定条件返回列表
    public List<T> list(final Map<String, Object> params, Sort sort)    //指定条件与排序返回列表
    public Page<T> list(final Map<String, Object> params, Pageable pageable) //指定条件返回分布记录
    public boolean existsById(ID id) //是否存在某个id的记录
    public void deleteAll()          //删除所有记录
    public long count()             //统计所有记录数
    public long count(Map<String, Object> params) //统计某条件的记录数
    protected void copyModel(Object source, Object target) //复制一个数据给指定对象

## exception 
自定义异常,具体请查看代码

## filter 
过滤器，XssHttpServletRequestWrapper 是写了一个防止防范XSS，SQL注入等攻击过滤器的现实，
要在FilterIniter里调用

## form 
表单实体类
可以在实体类里实现限制规则

## Interceptors
拦截器
    
    annotation：定义了拦截器的注入接口，在需要拦截的api上注入这样的接口即可使用，默认在这里
                编写了一个开放api接口的拦截示例,具体的实现在OpenApiCheckInterceptor
    
    interceptor: 拦截器的实现，比如这里的OpenApiCheckInterceptor就是OpenApiRequired拦截
                 器的业务实现
                 OpenApiCheckInterceptor不演示了开放api基于公钥与私钥配合使用的参数签名检验，及ip限制 





## jobs
定时任务

## queuehandler
通用队列处理，一些不用实时的，并且频繁的数据库操作可以先写到队列来处理，比如关于各种样的日志，
处理起来会比较影响系统性能，所以一般对日志的处理会通过消息队列来异步处理，默认使用redis的消息
队列，如果你没有安装redis那可以直接调用service来定日志了
 
    QueueToDbEnum:  要处理的类型，即分类可以在这里定义，在业务处理中主要是靠这个来区分，并存储到相应的设备
    QueueToDbModel: 通用数据实体，操作日志时都是通过这个类封装后传进来的，并在这里要指定操作数据的类型
    QueueToDbOpt:   将数据QueueToDbModel从消息队列取出，并存入到数据库的业务处理，要在系统启动初始后调用这个类的startToDb方法
                    考虑到的小项目不想使用队列，这个类有个属性，isOpenQueue，是否打开消息队列模式,允许你暂时不使用队列，以后想使用的时候再将其改为true
                    
    使用：
    
   
```java
   @Autowired
    private QueueToDbOpt logOpt;
    QueueToDbModel objData = new QueueToDbModel();
    objData.setQueueToDbEnum(QueueToDbEnum.MainLog);//QueueToDbEnum可以自动定义，自定义后要在QueueToDbOpt的addLogToDb里添加对应的处理业务
    objData.setLog(你的数据实体);
    logOpt.addData(objData);
```
## settings
可以在application.yml里添加配置节点，并通过类的方式来操作
    
    SiteSetting：演示了如何在application.yml添加自定义配置节点及操作
    AppConstants: 这里可以定义一些系统常量

## utils
常用工具集

## vo
在视图里可以通过vo来绑定，或者在返回json的接口中使用，这样可以格式化输出格式，比如希望将siteId在json里只显示为id

    SiteInfoVO: 演示了SiteInfo的VO
    serializerjson-SiteName: 演示了如果在输出内容里进行再次处理

## SiteApplication
SprintBoot的入口程序

## StartRun 
实现了CommandLineRunner，所以在系统启动后会执行这里的run方法，可以在这里处理一些系统初始化时需要处理的业务，比如日志处理

## ebsecurity ebsecuritycustom
基于security的权限管理封装库，
具体使用教程请查看《权限模块的调用说明》 

    ebsecurity： 是基础封装库，主要对外提供了相关实现的接口，以处理常见的权限问题
    ebsecuritycustom:   是对ebsecurity自定义实现