
# order2 项目前端 (Vue3 版本)

## 项目简介

`ruoyi-modules-order2` 是基于 RuoYi-Cloud 框架的自定义微服务模块，旨在实现菜单管理功能，包括菜品的增删改查、分类管理、套餐管理和订单管理。前端代码由 Vue2 升级为 Vue3，并采用了新的组件和状态管理方式。

## 技术栈

- **前端**: Vue3, Vue Router, Vuex (Pinia), Axios
- **后端**: Spring Cloud, Nacos, MySQL, RuoYi-Cloud
- **UI 框架**: Element Plus (Vue3 版本的 Element UI)
- **构建工具**: Vite
- **包管理工具**: npm

## 项目结构

```
|-- src
    |-- api              # 与后端交互的 API 请求封装
    |-- assets           # 静态资源（图片、字体等）
    |-- components       # 通用组件
    |-- router           # Vue Router 路由配置
    |-- store            # Vuex (Pinia) 状态管理
    |-- views            # 各页面的 Vue 文件
    |-- App.vue          # 根组件
    |-- main.js          # 入口文件
|-- public               # 公共静态资源
|-- package.json         # 项目信息和依赖管理
|-- vite.config.js       # Vite 构建配置文件
```

## 环境要求

- Node.js >= 16.x
- npm >= 8.x
- RuoYi-Cloud 后端服务已经启动

## 项目启动步骤

1. **克隆仓库**

   ```bash
   git clone https://github.com/Traveler-a11/RuoYiCloud_Order.git
   ```

2. **安装依赖**

   ```bash
   npm install --registry=https://registry.npmmirror.com
   ```

3. **运行开发环境**

   ```bash
   npm run dev
   ```

   本地开发服务器启动后，访问 [http://localhost:80](http://localhost:80)。

4. **打包部署**

   打包项目以用于生产环境。

   ```bash
   npm run build:prod
   ```

   打包完成后，生成的文件位于 `dist/` 文件夹中。可以将这些文件部署到 Nginx 等服务器。

## 主要改动说明

- **Vue2 → Vue3**: 项目从 Vue2 升级为 Vue3，采用了 Vue3 的组合式 API 和新的生命周期钩子。
- **状态管理**: 将 Vuex 替换为 Pinia，用于管理全局状态，提升了模块化和代码简洁性。
- **路由管理**: 使用 Vue Router 4.x 进行路由管理，并优化了路由懒加载。
- **UI 组件库**: 由 Element UI 升级为适配 Vue3 的 Element Plus，组件风格保持一致，但 API 使用有所不同。
- **构建工具**: 由 Webpack 升级为 Vite，提升了开发速度和打包效率。

## 常见问题

1. **依赖冲突**: 请确保 `node_modules` 和 `package-lock.json` 中的包版本一致。如果遇到依赖问题，建议删除 `node_modules` 文件夹并重新安装依赖。

   ```bash
   rm -rf node_modules
   npm install --registry=https://registry.npmmirror.com
   ```

2. **后端接口跨域**: 如果在开发环境中遇到跨域问题，请确认后端已配置了正确的 CORS 设置，或者在开发环境下配置 `vite.config.js` 中的代理。

## 贡献指南

如果你有任何建议或发现问题，欢迎提交 issue 或 pull request！

---