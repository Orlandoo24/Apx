# APX 推特插件游戏

## APX 简介

ApeXpal 是一个基於Blast並结合SocialFi、NFT、铭文的宠物养成游戏，ApeXpal侧重的理念是將傳統遊戲与社区玩家通过大量交互共同发展并探索新玩法，尤其注重玩家的游戏体验、社区氛围、与是否能够长久经营的游戏机制，我们将虚拟宠物拥有的养成乐趣与变革能力结合在一起。为用户提供了一个充满活力、引人入胜的环境来提升、照顾、交易他们的虚拟宠物。在虚拟世界拥有虚拟宠物的体验更加愉快，同时在您饲养宠物时为您创造更多利润！

## 项目简介

APX 是一个基于 Spring Cloud 微服务架构的区块链游戏平台，集成了宠物养成、挖矿、交易等游戏功能，并支持区块链资产管理和第三方认证登录。
白皮书： https://apexpal.gitbook.io/apexpal-whitepaper


<img width="1280" height="624" alt="image" src="https://github.com/user-attachments/assets/49eaf54b-ad1e-4533-b71c-abf317541871" />


## 技术栈

- **后端框架**: Spring Boot 2.7.18
- **微服务**: Spring Cloud 2021.0.8
- **服务发现**: Netflix Eureka
- **API网关**: Spring Cloud Gateway
- **数据库**: MySQL 8.0.29
- **ORM框架**: MyBatis Plus 3.4.2
- **连接池**: Druid 1.2.20
- **缓存**: Redis
- **认证**: JWT + OAuth 1.0a (Twitter)
- **工具库**: Hutool 4.6.2
- **构建工具**: Maven 3.x
- **Java版本**: JDK 1.8

## 项目架构

```
apx/
├── apx-common/          # 公共模块
├── apx-mbg/            # 数据模型模块
├── apx-eureka/         # 服务注册中心
├── apx-gateway/        # API网关
├── apx-auth/           # 认证授权模块
├── apx-blockchain/     # 区块链服务模块
├── apx-game/           # 游戏业务模块
└── sql/                # 数据库脚本
```

## 模块说明

### apx-common (公共模块)
- **功能**: 提供公共配置、工具类、异常处理、常量定义等
- **端口**: 无（被其他模块依赖）
- **主要组件**:
  - Redis配置
  - OpenFeign配置
  - 安全上下文管理
  - 统一返回结果封装
  - 拦截器和过滤器

### apx-mbg (数据模型模块)
- **功能**: 数据库实体类和Mapper接口
- **端口**: 无（被其他模块依赖）
- **主要功能**:
  - 用户管理
  - 宠物信息
  - 道具系统
  - 挖矿记录
  - 交易记录

### apx-eureka (服务注册中心)
- **功能**: 微服务注册与发现
- **端口**: 11111
- **主要功能**:
  - 服务注册
  - 服务发现
  - 健康检查
  - 负载均衡支持

### apx-gateway (API网关)
- **功能**: 统一入口、路由转发、跨域处理
- **端口**: 43333 (HTTPS)
- **主要功能**:
  - 请求路由转发
  - 跨域配置
  - SSL证书支持
  - 服务负载均衡

### apx-auth (认证授权模块)
- **功能**: 用户认证、第三方登录
- **端口**: 5050
- **主要功能**:
  - Twitter OAuth 1.0a 认证
  - JWT Token 生成和验证
  - 用户注册和登录
  - 第三方平台集成

### apx-blockchain (区块链服务模块)
- **功能**: 区块链资产管理、挖矿、交易
- **端口**: 8082
- **主要功能**:
  - 账户管理（创建、导出、查询余额）
  - 余额提取和转账
  - 挖矿记录管理
  - Merkle树生成和验证
  - 双因子认证（2FA）
  - 定时任务（市场数据生成）

### apx-game (游戏业务模块)
- **功能**: 游戏核心业务逻辑
- **端口**: 8081
- **主要功能**:
  - 宠物养成系统
  - 道具商店和背包管理
  - 宠物升级和属性计算
  - 挖矿系统
  - 用户家园管理
  - 游戏数据统计

## 核心功能

### 1. 用户认证系统
- 支持 Twitter OAuth 1.0a 登录
- JWT Token 认证机制
- 用户信息管理和存储

### 2. 区块链资产管理
- 用户钱包地址生成和管理
- 余额查询和提取
- 链上交易处理
- Merkle树证明验证

### 3. 宠物养成游戏
- 宠物属性系统（智力、幸运值、挖矿能力等）
- 道具系统（装备、消耗品、宝箱）
- 宠物升级和成长
- 喂食和属性提升

### 4. 挖矿系统
- 定时挖矿任务
- 挖矿收益计算
- 挖矿记录管理
- 市场数据生成

### 5. 商店系统
- 道具购买和销售
- 价格管理
- 库存管理
- 交易记录

## 数据库设计

主要数据表包括：
- `apx_mining_record`: 挖矿记录表
- `apx_pet_*`: 宠物相关表
- `apx_user`: 用户信息表
- `apx_user_prop`: 用户道具表
- `apx_prop`: 道具信息表

## 环境配置

### 开发环境
```bash
# 激活开发环境配置
mvn clean install -Pdev
```

### 测试环境
```bash
# 激活测试环境配置
mvn clean install -Ptest
```

### 生产环境
```bash
# 激活生产环境配置
mvn clean install -Pprod
```

## 快速开始

### 1. 环境准备
- JDK 1.8+
- Maven 3.x
- MySQL 8.0+
- Redis 6.0+

### 2. 数据库初始化
```sql
-- 执行数据库脚本
source sql/apx_base.sql
```

### 3. 配置文件修改
修改各模块的 `application-dev.yml` 配置文件：
- 数据库连接信息
- Redis连接信息
- 第三方服务配置

### 4. 启动服务
按以下顺序启动服务：

```bash
# 1. 启动注册中心
cd apx-eureka
mvn spring-boot:run

# 2. 启动网关
cd apx-gateway
mvn spring-boot:run

# 3. 启动认证服务
cd apx-auth
mvn spring-boot:run

# 4. 启动区块链服务
cd apx-blockchain
mvn spring-boot:run

# 5. 启动游戏服务
cd apx-game
mvn spring-boot:run
```

### 5. 访问服务
- 网关地址: https://localhost:43333
- 游戏服务: https://localhost:43333/apx-game
- 认证服务: https://localhost:43333/apx-auth
- 区块链服务: https://localhost:43333/apx-blockchain

## API 接口

### 认证相关
- `GET /apx-auth/thirdPartyAuth/twitter/requestToken` - 获取Twitter认证Token
- `GET /apx-auth/thirdPartyAuth/twitter/getApxToken` - 获取APX Token

### 游戏相关
- `GET /apx-game/home/balance/query` - 查询用户余额
- `GET /apx-game/home/petInfo/query` - 查询宠物信息
- `GET /apx-game/home/prop/query` - 查询用户道具
- `GET /apx-game/shop/prop/query` - 查询商店道具

### 区块链相关
- `GET /apx-blockchain/account/export` - 导出账户信息
- `GET /apx-blockchain/account/balance/query` - 查询余额
- `POST /apx-blockchain/account/withdraw` - 提取余额
- `POST /apx-blockchain/account/claim` - 领取余额

## 部署说明

### Docker 部署
项目支持 Docker 容器化部署，各模块都有对应的 Dockerfile。

### 生产环境配置
1. 修改 SSL 证书配置
2. 配置生产环境数据库
3. 设置 Redis 集群
4. 配置负载均衡

## 开发规范

### 代码规范
- 使用 Lombok 减少样板代码
- 统一异常处理
- 使用 Hutool 工具库
- 遵循 RESTful API 设计

### 数据库规范
- 使用 MyBatis Plus 进行数据操作
- 统一使用 Lambda 查询
- 事务注解规范使用

## 注意事项

1. **SSL证书**: 网关使用HTTPS，需要配置SSL证书
2. **跨域配置**: 已配置跨域支持，支持前端调用
3. **服务依赖**: 启动顺序很重要，建议按文档顺序启动
4. **数据库**: 确保MySQL版本兼容性
5. **Redis**: 确保Redis服务正常运行

## 贡献指南

1. Fork 项目
2. 创建功能分支
3. 提交代码
4. 创建 Pull Request

## 许可证

本项目采用 MIT 许可证。

## 联系方式

如有问题，请联系开发团队。
