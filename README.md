# SpringBootWiki

## 技术框架
1. 底层采用Spring Boot
2. 数据访问使用Spring Data Jpa，Hibernate
3. 连接池采用 Druid
4. 搜索采用Hibernate Search，基于Lucene.中文分词采用org.apdplat.word 
5. 采用Quartz作业调度
6. 安全认证，访问权限控制采用Spring Security4.
7. 页面渲染引擎采用Thymeleaf
8. 采用Freemarker自动生成Entity、Repository、service

## 实现功能
### Front End
#### 前台页面：
* 登录
* 验证码
* RemeberMe
* 支持类信息发布（类似FAQ）
* 支持类信息列表，已发布信息修改
* TODO 消息系统
* TODO 评论系统
* TODO 数据计算


#### 后台页面：
* 用户管理
* TODO 停用用户启用
* 权限配置
* TODO 修改密码
* TODO 配置部门
* TODO 配置设备
* TODO 配置分类
* TODO FAQ数量统计
* TODO 日志系统

### Back End