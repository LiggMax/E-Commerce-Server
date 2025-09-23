# E-commerce-Api

<p align="center">
  <img src="https://img.shields.io/badge/Java-17-orange?logo=java" alt="Java 17" />
  <img src="https://img.shields.io/badge/Spring%20Boot-3.5.5-brightgreen?logo=spring-boot" alt="Spring Boot 3.5.5" />
  <img src="https://img.shields.io/badge/MySQL-8.0-blue?logo=mysql" alt="MySQL 8.0" />
  <img src="https://img.shields.io/badge/Redis-6.0-red?logo=redis" alt="Redis 6.0" />
  <img src="https://img.shields.io/badge/Maven-3.6+-orange?logo=apache-maven" alt="Maven" />
</p>

### 🏗️ 技术栈

* **核心框架**: Spring Boot 3.5.5
* **数据库**: MySQL 8.0
* **缓存**: Redis
* **ORM框架**: MyBatis-Plus
* **安全框架**: JWT + Redis Token管理
* **构建工具**: Maven

### 📂 项目结构

```
E-commerce/                     # E-commerce-api系统根目录
├── api/                        # API模块
│   ├── api-admin/              # 管理端API
│   │   ├── controller/         # 控制器层
│   │   ├── service/            # 服务层
│   │   └── mapper/             # 数据访问层
│   ├── api-client/             # 客户端API
│   │   └── controller/         # 控制器层
│   └── api-common/             # API公共模块
│
├── common/                     # 公共模块
│   ├── dto/                    # 数据传输对象
│   ├── entity/                 # 实体类
│   ├── utils/                  # 工具类
│   ├── service/                # 公共服务接口
│   ├── statuEnum/              # 状态枚举
│   └── Imagenum/               # 图片类型枚举
│
├── entrance/                   # 入口模块
│   ├── config/                 # 配置类
│   ├── interceptors/           # 拦截器
│   └── resources/              # 配置文件和静态资源
│
└── sql/                        # 数据库脚本
```
