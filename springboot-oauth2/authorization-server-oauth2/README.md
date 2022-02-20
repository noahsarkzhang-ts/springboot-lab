## 初始化数据库脚本
1. 新建数据库；
2. 导入 oauth2.sql
3. 用户：admin/user,密码为：123456
4. clientid/secret: dev/dev,oauth2/oauth2

## 获取token
```bash
curl -X POST -d "username=admin&password=123456&grant_type=password&client_id=dev&client_secret=dev" http://localhost:8080/oauth/token | json_pp
```
