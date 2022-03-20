## 说明
Sentinel 控制台没有显示 api 菜单的解决办法：
1. 在 IDEA JVM 参数中加入下面的参数：
```bash
-Dcsp.sentinel.app.type=11
```

2. 在启动代码中设置环境变量：
```java
@SpringBootApplication
@EnableDiscoveryClient
public class KoalaGatewayApplication {

    public static void main(String[] args) {

        System.setProperty("csp.sentinel.app.type", "1");
        SpringApplication.run(KoalaGatewayApplication.class, args);
    }
}
```

