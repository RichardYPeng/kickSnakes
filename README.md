#### 一个伟大的史诗级巨著

#### 后端逻辑

![业务逻辑](/Users/yongpeng/IdeaProjects/kickSnakes/业务逻辑.png)

#### 使用说明

```
导入后端，运行三个服务；
导入前端，安装vue ui，启动前端；
```



##### 数据库命令和密码

```
mysql:
如果找不到mysql指令: export PATH=${PATH}:/usr/local/mysql/bin/
输入指令: mysql -uroot -pyp995101
密码: yp995101
```

### 4.1 知识点

##### pom依赖和分层

```
pom依赖:
Spring Boot Starter JDBC: 连接数据库需要的依赖
Project Lombok: 帮助简化代码
MySQL Connector/J:
mybatis-plus-boot-starter:
mybatis-plus-generator: 自动生成一些函数, 比如mapper;
```

```
<dependency>
     <groupId>org.springframework.boot</groupId>
     <artifactId>spring-boot-starter-jdbc</artifactId>
     <version>2.7.0</version>
</dependency>
<dependency>
      <groupId>org.projectlombok</groupId>
       <artifactId>lombok</artifactId>
       <version>1.18.22</version>
       <scope>provided</scope>
</dependency>
<dependency>
       <groupId>mysql</groupId>
       <artifactId>mysql-connector-java</artifactId>
       <version>8.0.28</version>
</dependency>
<dependency>
       <groupId>com.baomidou</groupId>
       <artifactId>mybatis-plus-boot-starter</artifactId>
       <version>3.5.1</version>
</dependency>
<dependency>
       <groupId>com.baomidou</groupId>
       <artifactId>mybatis-plus-generator</artifactId>
       <version>3.5.1</version>
</dependency>
```



```
加入依赖后修改配置文件:
spring.datasource.username=root
spring.datasource.password=yp995101
spring.datasource.url=jdbc:mysql://localhost:3306/kob?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

##### 注解

```
使用注解可以帮助我们不在需要配置繁杂的xml文件，以前最基本的web项目是需要写xml配置的，需要标注你的哪个页面和哪个 servle 是对应的，注解可以帮助我们减少这方面的麻烦。

@Controller：用于定义控制器类，在spring项目中由控制器负责将用户发来的URL请求转发到对应的服务接口（service层），一般这个注解在类中，通常方法需要配合注解@RequestMapping。

@RequestMapping：提供路由信息，负责URL到Controller中的具体函数的映射。

@Autowired：自动导入依赖的bean

@Service：一般用于修饰service层的组件

@Bean：用@Bean标注方法等价于XML中配置的bean。

@AutoWired：自动导入依赖的bean。byType方式。把配置好的Bean拿来用，完成属性、方法的组装，它可以对类成员变量、方法及构造函数进行标注，完成自动装配的工作。当加上（required=false）时，就算找不到bean也不报错。

可以不用花时间去了解类似注解的实现原理，用的时候背下来就行，这里只是简单记录一下，如果想深入了解，可以百度。

```

```
12、SpringBoot中关于层的概念
数据库里的表user类似于Java中的class，

pojo层其实就是直接将这个表转化成class，表的名称直接定义成class名称就可以了。

mapper层 对于class里面的增删改查，最终所有数据都需要存到数据库上，
对于class的对象的增删改查都需要将数据存到数据库里
都需要用到sql语句，所以mapper层就是将class里面的增删改查操作crud，
转化成SQL语句。

service层，实现具体业务可以用多个不同的mapper操作
mapper类似于一些最基本的操作， service可以将最基本的操作组合实现一些具体的业务

Controller，将前端的请求以及请求中的参数接收然后选择将这些参数传给哪个service
将service的返回结果再返回给前端，调度service
```

```
使用Lombok实现Pojo层
MyBatis Plus SQL注入一般都会帮大家屏蔽掉了
实现一些基本操作，函数，Lombok依赖
Data会把我们常用的函数加进去
NoArgs无参构造函数，AllArgs全参构造函数

加lombok或者不加lombok在编译后从target文件目录下面查看会很不一样; 加lombok后会多很多机械化的方法; 
```

```
使用myBatis可以帮助我们进行CURD的模块化操作; 有些方法是可以直接调用的; y总调试的时候在controller层, 正常的情况下应该是写在service层

使用MyBatis Plus实现Mapper层
继承过来MyBatis Plus，写真正的业务逻辑的时候再分开
现在调试的时候都放到Contorller里，一个表对应一个pojo对应一共mapper
Request会把所有类型映射过来，如果要用到数据库里的mapper加autowired注解
mapper接口是mybatis plus帮我们实现的、写null表示查询所有用户
重启之后可以查看所有用户，跟数据库中完全一样
```

##### 配置Spring Security

```
配置Spring Security

是用户认证操作 – 一种授权机制，目的是安全。

4.1 添加依赖：
1. 添加依赖，添加之后刷新。
spring-boot-starter-security

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
    <version>2.7.0</version>
</dependency>
// 加入依赖后重启显示登录; 
刷新之后显示登陆
默认username: user; 密码在启动后会在控制台给到; 
```

```
加入service层后可以输入用户名和密码进行登录, 但是如果是明文密码需要在密码前面加上前缀{noop}
```

```
密码的加密存储: 
在config插入类, 对密码进行加密; test里面可以直接用这个然后进行密码储存到数据库; 
```

```markdown
实现service.impl.UserDetailsServiceImpl类，继承自UserDetailsService接口，用来接入**数据库信息;**
如果没有这步是不能正常用用户进行登录操作的; UserDetailsServiceImpl类输入用户id, 返回用户的细节; 
返回的细节实现UserDetails接口; 
```

### 4.2 知识点

```
每个url会有一个controller接口进行对应; 
页面查看权限; 有些页面是登录后才能查看(授权页面), 有些是不需要登录就能查看(公开页面, 登录一次就可以持续访问); 
4.1的知识点里面是: 完成UserDetails接口和UserDetailsService接口, UserDetailsServiceImpl传入用户参数后会和数据库的密码做比对, 会给用户发送sessionID, 会存到cookie里面, sessionID也是存在服务器里面的, 下次访问的时候用户会带着sessionID然后服务器进行比对, 存在并且会映射到服务; 

4.2不用session进行判断了; 很多情况下都会跨域, 多个服务器共享session的情况;
直接用JWT验证方式去解决跨域问题,不需要在服务器端存储; 
JWT原理: userID+密钥=hash后的结果; 当服务器接收到userID和hash后的结果的时候, 会根据userID加上自己本地的密钥然后和hash后的结果进行比对, 如果一样就进行授权; 得到hash后的结果的时候是不能反推userID和密钥的. 
```

##### 配置JWT

```
1. 在 backend 目录下创建软件包 utils 并创建 JwtUtil 类。
JwtUtil 类为jwt 工具类，用来创建、解析 jwt token。

2. 实现 JwtAuthenticationTokenFilter 类
在 backend 的 config 目录下创建 cinfig 软件包，并创建 JwtAuthenticationTokenFilter 类。
实现 JwtAuthenticationTokenFilter 类，用来验证 jwt token ，如果验证成功，则将 User 信息注入上下文中。

3. 配置config.SecurityConfig类
放行登录、注册等接口。
```



```
将数据库中的id域变为自增
在数据库中将id列变为自增
在pojo.User类中添加注解：@TableId(type = IdType.AUTO)
```

##### 编写API

```
这步开始前就删除了Controller调试前端的数据接口; 
实现/user/account/token/：验证用户名密码，验证成功后返回jwt token（令牌）(公开)
实现/user/account/info/：根据令牌返回用户信息(权限)
实现/user/account/register/：注册账号(公开)
```

```
用前端debug的时候注意端口和url接口是否是正确的; 
```

### 4.3 知识点

```
前端知识点: 
授权页面
注册功能
页面持久化
```

### 5.1

```
在数据库中创建表bot
表中包含的列：

id: int：非空、自动增加、唯一、主键
user_id: int：非空
注意：在pojo中需要定义成userId，在queryWrapper中的名称仍然为user_id
title: varchar(100)
description: varchar(300)
content：varchar(10000)
rating: int：默认值为1500
createtime: datetime
pojo中定义日期格式的注解：@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
modifytime: datetime
pojo中定义日期格式的注解：@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")

实现后端API(增删改查)
/user/bot/add/：创建一个Bot
/user/bot/remove/：删除一个Bot
/user/bot/update/：修改一个Bot
/user/bot/getlist/：查询Bot列表

```

