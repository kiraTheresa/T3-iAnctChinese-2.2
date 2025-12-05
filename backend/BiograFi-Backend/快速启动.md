# BiograFi-Backend 快速启动

## 环境要求

- JDK 17+
- Maven 3.8+

## 一键启动

### 1. 克隆项目

```bash
git clone <repository-url>
cd BiograFi-Backend
```

### 2. 启动项目

项目默认使用开发环境（H2 内存数据库），无需额外配置，直接运行以下命令即可：

```bash
mvn spring-boot:run
```

### 3. 验证项目启动

项目启动后，访问以下地址验证是否正常运行：

- 健康检查：http://localhost:5002/api/health
- Swagger UI：http://localhost:5002/swagger-ui.html
- API 文档：http://localhost:5002/v3/api-docs

## 默认测试账号

```
用户名：admin
密码：admin123
```

> 注：如需生产环境部署或更详细的配置说明，请参考 `部署运行说明.md` 文档。
