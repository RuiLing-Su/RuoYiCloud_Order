# **ruoyi-modules-order2**

## 1. 项目概述

**ruoyi-modules-order2** 是基于 **RuoYi-Cloud** 框架的自定义微服务模块，旨在实现订单管理功能。该模块采用 Spring Cloud 微服务架构，支持服务注册与发现、负载均衡、API 网关、分布式数据库管理等功能，能够与其他微服务（如用户服务、支付服务等）进行交互。

### 1.1 项目特性

- **微服务架构**：基于 Spring Cloud、Nacos、Gateway 实现服务注册和负载均衡。
- **订单管理功能**：实现订单的创建、更新、查询、删除等基本操作。
- **与其他服务集成**：通过 OpenFeign 与其他微服务交互，如用户服务获取用户信息。
- **高可扩展性**：可轻松扩展并与其他业务微服务集成。

---

## 2. 环境要求

- **JDK**: 1.8+
- **Maven**: 3.6+
- **MySQL**: 5.7+
- **Redis**: 5.0+
- **Nacos**: 2.0+
- **Docker**: 可选（用于部署）

---

## 3. 项目结构

```bash
ruoyi-cloud/
│
├── ruoyi-gateway             # API 网关模块
├── ruoyi-auth                # 认证授权模块
├── ruoyi-modules             # 微服务模块目录
│   ├── ruoyi-system          # 系统模块
│   ├── ruoyi-order2          # 自定义订单管理微服务
│
└── ruoyi-common              # 公共模块
```

---

## 4. 安装与运行

### 4.1 数据库配置

在启动项目前，请确保已经配置好数据库

### 4.2 Nacos 配置

在 **ruoyi-modules-order2** 模块的 `bootstrap.yml` 文件中，配置 Nacos 注册中心信息：

```yaml
spring:
  application:
    name: ruoyi-order2
  cloud:
    nacos:
      discovery:
        server-addr: 127.0.0.1:8848
```

### 4.3 数据库连接配置

在 `application-dev.yml` 中配置数据库连接信息：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/ruoyi_cloud?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC
    username: root
    password: yourpassword
```

### 4.4 网关路由配置

在 **ruoyi-gateway** 模块的 `application.yml` 中，为 **ruoyi-order2** 添加路由配置：

```yaml
spring:
  cloud:
    gateway:
      routes:
        - id: ruoyi-order2
          uri: lb://ruoyi-order2
          predicates:
            - Path=/order2/**
```

### 4.5 运行项目

1. 启动 **Nacos** 注册中心。
2. 启动 **ruoyi-gateway** 和 **ruoyi-auth** 模块。
3. 运行 **ruoyi-order2** 模块
4. 确认服务在 Nacos 中已注册，访问 `/order2` 路径进行 API 测试。

---

## 5. 微服务功能

### 5.1 订单管理接口

#### 修改菜品管理

**接口地址**: `/order2/dish/`  
**请求方式**: `put`  
**功能描述**: 修改菜品管理。

**请求示例**:

```json
{
    "createBy": null,
    "createTime": "2023-02-16 09:50:06",
    "updateBy": null,
    "updateTime": "2024-09-01 21:00:29",
    "remark": null,
    "id": 75,
    "name": "波士顿龙虾",
    "categoryId": 8,
    "price": 128,
    "image": "http://127.0.0.1:9300/statics/2024/09/19/41e7fe06-8ae7-48e5-8858-575689f4ff2f_20240919195716A001.jpg",
    "description": "2斤重 波斯顿龙虾",
    "status": 1,
    "dishFlavorList": [
        {
            "createBy": null,
            "createTime": null,
            "updateBy": null,
            "updateTime": null,
            "remark": null,
            "id": 140,
            "dishId": 75,
            "name": "忌口",
            "value": "[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]",
            "index": 1
        },
        {
            "createBy": null,
            "createTime": null,
            "updateBy": null,
            "updateTime": null,
            "remark": null,
            "id": 141,
            "dishId": 75,
            "name": "辣度",
            "value": "[\"不辣\",\"微辣\",\"中辣\"]",
            "index": 2
        }
    ]
}
```

**响应示例**:

```json
{
  "msg": "操作成功",
  "code": 200
}
```

#### 查询菜品详情

**接口地址**: `/order2/dish/{id}`  
**请求方式**: `GET`  
**功能描述**: 根据订单ID查询菜品详情。

**响应示例**:

```json
{
    "msg": "操作成功",
    "code": 200,
    "data": {
        "createBy": null,
        "createTime": "2023-02-16 09:50:06",
        "updateBy": null,
        "updateTime": "2024-09-19 19:57:30",
        "remark": null,
        "id": 75,
        "name": "波士顿龙虾",
        "categoryId": 8,
        "price": 128.00,
        "image": "http://127.0.0.1:9300/statics/2024/09/19/41e7fe06-8ae7-48e5-8858-575689f4ff2f_20240919195716A001.jpg",
        "description": "2斤重 波斯顿龙虾",
        "status": 1,
        "dishFlavorList": [
            {
                "createBy": null,
                "createTime": null,
                "updateBy": null,
                "updateTime": null,
                "remark": null,
                "id": 140,
                "dishId": 75,
                "name": "忌口",
                "value": "[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]"
            },
            {
                "createBy": null,
                "createTime": null,
                "updateBy": null,
                "updateTime": null,
                "remark": null,
                "id": 141,
                "dishId": 75,
                "name": "辣度",
                "value": "[\"不辣\",\"微辣\",\"中辣\"]"
            }
        ]
    }
}
```

---

## 6. 微服务间通信

### 6.1 Feign 客户端调用

**ruoyi-order2** 使用 Feign 调用其他微服务（如用户服务），获取用户信息。在 `ruoyi-order2` 模块中创建 Feign 客户端接口：

```java
@FeignClient(name = "ruoyi-user")
public interface UserFeignClient {

    @GetMapping("/user/dish/{id}")
    UserResponse getUserById(@PathVariable("id") Long userId);
}
```

通过此接口，可以在订单模块中实现用户验证或其他跨微服务逻辑。

---

## 7. 前端接口

### 7.1 前端配置

前端部分可以通过 AJAX 或 Vue.js 前端框架与订单管理微服务交互。以下是前端请求的示例：

**新增菜品及套餐分类**:

```javascript
import request from '@/utils/request'

export function addCategory(data) {
  return request({
    url: '/order2/category',
    method: 'post',
    data: data
  })
}
```

### 7.2 Vue 页面示例

```vue
<template>
  <div>
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['category:category:add']"
        >新增</el-button>
  </div>
</template>

<script>
import { addCategory } from '@/api/category/category'

export default {
  methods: {
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加菜品及套餐分类";
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
            addCategory(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
    },
  }
}
</script>
```

---

## 8. 贡献指南

欢迎参与贡献！请提交 **Pull Request**，在提交前请确保通过以下验证：

1. 代码格式符合项目规范。
2. 所有新增功能附带必要的单元测试。
3. 修改后的代码通过 CI 验证。

---

## 9. License

该项目基于 [MIT License](https://opensource.org/licenses/MIT) 许可。
