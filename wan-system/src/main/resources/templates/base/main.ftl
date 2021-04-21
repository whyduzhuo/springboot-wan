<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/html">
<script type="text/javascript" src="/static/layui-v2.5.6/layui/layui.all.js"></script>
<link href="/static/layui-v2.5.6/layui/css/layui.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="/static/css/main.css" media="all">
<link rel="stylesheet" href="/static/css/font-awesome.min.css" media="all">
<script type="text/javascript" src="/static/echart4.5/echarts.min.js"></script>
<#include "/common/tmp/commom.ftl">
<head>
    <style>
        a{
            color: #005980;
        }
        .widget-small{
            background-color: #FFFFFF;
            overflow: hidden;
            text-align: center;
            border-radius: 2px;
            box-shadow: 0 1px 2px 0 rgba(0,0,0,.05);
        }
        .widget-small .fa{
            float: left;
            width: 40%;
            line-height: 80px;
            color: #FFFFFF;
        }
        .widget-user .fa{
            background-color: #029789;
        }
        .widget-visit .fa{
            background-color: #17a2b8;
        }
        .widget-message .fa{
            background-color: #fbad4c;
        }
        .widget-like .fa{
            background-color: #ff646d;
        }
        .widget-small-info{
            float: left;
            text-align: left;
            width: 40%;
            margin-left: 20px;
            margin-top: 18px;
            line-height: 24px;
        }
        .widget-small-info h4{
            font-size: 18px;
        }
        .widget-small-info span{
            font-size: 16px;
        }
        .project-introduce{
            min-height: 466px;
        }
        .project-introduce h4{
            font-weight: bold;
            margin-top: 12px;
            margin-bottom: 8px;
        }
        .project-introduce li{
            list-style: decimal;
            margin-left: 28px;
        }
        .alert {
            padding: 15px;
            margin-bottom: 10px;
            border: 1px solid transparent;
            border-radius: 4px;
        }
        .alert-info {
            color: #31708f;
            background-color: #d9edf7;
            border-color: #bce8f1;
        }
        .layui-col-space15{
            margin: 0px
        }
    </style>
</head>
<@adminPageNav navName1='首页' navName2=''/>
<body class="timo-layout-page">
<div class="layui-row layui-col-space15" >
    <div class="layui-col-md3 layui-col-sm6 layui-col-xs12">
        <div class="widget-small widget-user">
            <i class="icon fa fa-users fa-3x"></i>
            <div class="widget-small-info">
                <h4>用户</h4>
                <span>12</span>
            </div>
        </div>
    </div>
    <div class="layui-col-md3 layui-col-sm6 layui-col-xs12">
        <div class="widget-small widget-visit">
            <i class="icon fa fa-line-chart fa-3x"></i>
            <div class="widget-small-info">
                <h4>访问</h4>
                <span>1,200</span>
            </div>
        </div>
    </div>
    <div class="layui-col-md3 layui-col-sm6 layui-col-xs12">
        <div class="widget-small widget-message">
            <i class="icon fa fa-comments-o fa-3x"></i>
            <div class="widget-small-info">
                <h4>信息</h4>
                <span>41</span>
            </div>
        </div>
    </div>
    <div class="layui-col-md3 layui-col-sm6 layui-col-xs12">
        <div class="widget-small widget-like">
            <i class="icon fa fa-star fa-3x"></i>
            <div class="widget-small-info">
                <h4>收藏</h4>
                <span>306</span>
            </div>
        </div>
    </div>
</div>
<div class="layui-row layui-col-space15">
    <div class="layui-col-md8">
        <div class="layui-card">
            <div class="layui-card-header">项目介绍</div>
            <div class="layui-card-body project-introduce">
                <blockquote class="layui-elem-quote layui-quote-nm">
                    springboot-wan后台管理系统，基于SpringBoot2.0 + Spring Data Jpa + freemarker + Shiro + redis 开发的后台管理系统，采用分模块的方式便于开发和维护，目前支持的功能有：权限管理、部门管理、字典管理、日志记录、文件上传、代码生成等，为快速开发后台系统而生的脚手架！
                    <div>开源协议：Apache License 2.0</div>
                    <div>开源地址：<a target="_blank" href="https://gitee.com/little_one/springboot-wan.git">https://gitee.com/little_one/springboot-wan.git</a></div>
                </blockquote>
                <div>
                    <h4>技术选型</h4>
                    <ol>
                        <li>后端技术：SpringBoot + Spring Data Jpa + Freemarker + Shiro + redis + poi +quartz + swagger +Activiti </li>
                        <li>前端技术：Layui + Jquery + bootstrap + zTree + Font-awesome + Echarts</li>
                    </ol>
                </div>
                <div>
                    <h4>功能列表</h4>
                    <ol>
                        <li>用户管理：用于管理后台系统的用户，可进行增删改查等操作。</li>
                        <li>角色管理：分配权限的最小单元，通过角色给用户分配权限。</li>
                        <li>菜单管理：用于配置系统菜单，同时也作为权限资源。</li>
                        <li>部门管理：通过不同的部门来管理和区分用户。</li>
                        <li>字典管理：对一些需要转换的数据进行统一管理，如：男、女等。</li>
                        <li>日志管理：用于记录用户对系统的操作，同时监视系统运行时发生的错误。</li>
                        <li>文件上传：内置了文件上传接口，方便开发者使用文件上传功能。</li>
                        <li>代码生成：可以帮助开发者快速开发项目，减少不必要的重复操作，花更多精力注重业务实现。</li>
                        <li>表单构建：通过拖拽的方式快速构建一个表单模块。</li>
                        <li>数据接口：根据业务代码自动生成相关的api接口文档</li>
                    </ol>
                </div>
            </div>
        </div>
        <div class="layui-row layui-col-space15">
            <div class="layui-col-md6">
                <div class="layui-card">
                    <div class="layui-card-header">项目信息</div>
                    <div class="layui-card-body">
                        <ul>
                            <li><i class="fa fa-paper-plane"></i> 版本：<a href="">1.0.0</a></li>

                        </ul>
                    </div>
                </div>
            </div>
            <div class="layui-col-md6">
                <div class="layui-card">
                    <div class="layui-card-header">捐赠</div>
                    <div class="layui-card-body">
                        <div class="alert alert-info">开源不易，您的支持就是我的动力！</div>
                        <img style="width: 100%" src="/static/img/login-background.jpg"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="layui-col-md4">
        <div class="layui-card">
            <div class="layui-card-header">更新日志</div>
            <div class="layui-card-body">
                <ul class="layui-timeline">

                    <li class="layui-timeline-item">
                        <i class="layui-icon layui-timeline-axis">&#xe63f;</i>
                        <div class="layui-timeline-content layui-text">
                            <h3 class="layui-timeline-title">2021-04-03 </h3>
                            <p>
                                1. 新增：滑动验证码<br>
                                2. 新增：整合Redisson分布式锁<br>
                                3. 新增：角色切换功能
                            </p>
                        </div>
                    </li>


                    <li class="layui-timeline-item">
                        <i class="layui-icon layui-timeline-axis">&#xe63f;</i>
                        <div class="layui-timeline-content layui-text">
                            <h3 class="layui-timeline-title">2021-03-03 </h3>
                            <p>
                                1. 新增：登录验证码<br>
                                2. 修复：邮件发送慢导致程序卡顿的bug<br>
                                3.新增：rememberMe记住我功能
                            </p>
                        </div>
                    </li>

                    <li class="layui-timeline-item">
                        <i class="layui-icon layui-timeline-axis">&#xe63f;</i>
                        <div class="layui-timeline-content layui-text">
                            <h3 class="layui-timeline-title">2021-01-23</h3>
                            <p>
                                1. 新增：定时任务<br>
                            </p>
                        </div>
                    </li>
                    <li class="layui-timeline-item">
                        <i class="layui-icon layui-timeline-axis">&#xe63f;</i>
                        <div class="layui-timeline-content layui-text">
                            <h3 class="layui-timeline-title">2020-12-17</h3>
                            <p>
                                1. 新增：Excel导入导出功能<br>
                            </p>
                        </div>
                    </li>

                    <li class="layui-timeline-item">
                        <i class="layui-icon layui-timeline-axis">&#xe63f;</i>
                        <div class="layui-timeline-content layui-text">
                            <h3 class="layui-timeline-title">2020-12-01</h3>
                            <p>
                                1. 修复：@Unique死循环导致内存溢出的bug<br>
                                2. 新增：系统监控<br>
                                3. 新增：OrderEntity 、OrderService、DeleteEntity、DeleteService<br>
                            </p>
                        </div>
                    </li>

                    <li class="layui-timeline-item">
                        <i class="layui-icon layui-timeline-axis">&#xe63f;</i>
                        <div class="layui-timeline-content layui-text">
                            <h3 class="layui-timeline-title">2020-11-27 </h3>
                            <p>
                                1. 新增：系统错误自动发送邮件<br>
                                2. 优化：角色授权，修改shiro-redis缓存<br>
                                3. 优化：首页菜单，清除当前登录人的权限缓存<br>
                                4. 优化：数据字典<br>
                            </p>
                        </div>
                    </li>

                    <li class="layui-timeline-item">
                        <i class="layui-icon layui-timeline-axis">&#xe63f;</i>
                        <div class="layui-timeline-content layui-text">
                            <h3 class="layui-timeline-title">2020-11-13 </h3>
                            <p>
                                1. 新增：自定义validator唯一约束注解<br>
                            </p>
                        </div>
                    </li>
                    <li class="layui-timeline-item">
                        <i class="layui-icon layui-timeline-axis">&#xe63f;</i>
                        <div class="layui-timeline-content layui-text">
                            <h3 class="layui-timeline-title">2020-11-09 </h3>
                            <p>
                                1. 新增：整合参数校验注解<br>
                                2. 优化：优化数据校验和全局异常处理<br>
                            </p>
                        </div>
                    </li>
                    <li class="layui-timeline-item">
                        <i class="layui-icon layui-timeline-axis">&#xe63f;</i>
                        <div class="layui-timeline-content layui-text">
                            <h3 class="layui-timeline-title">2020-10-05</h3>
                            <p>
                                3. 新增：整合lombok<br>
                                4. 修复：用户菜单显示优化<br>
                            </p>
                        </div>
                    </li>
                    <li class="layui-timeline-item">
                        <i class="layui-icon layui-timeline-axis">&#xe63f;</i>
                        <div class="layui-timeline-content layui-text">
                            <h3 class="layui-timeline-title">2020-08-26</h3>
                            <p>
                                1.优化：房屋挂牌走势bug<br>
                                2.新增：shiro整合redis,实现session共享<br>
                            </p>
                        </div>
                    </li>
                    <li class="layui-timeline-item">
                        <i class="layui-icon layui-timeline-axis">&#xe63f;</i>
                        <div class="layui-timeline-content layui-text">
                            <h3 class="layui-timeline-title">2020-08-20</h3>
                            <p>
                                1.新增：二手房数据爬取<br>
                                2.新增：二手房挂牌量走势<br>
                                3.新增：菜单授权功能
                                4.新增：整合Echarts
                            </p>
                        </div>
                    </li>
                    <li class="layui-timeline-item">
                        <i class="layui-icon layui-timeline-axis">&#xe63f;</i>
                        <div class="layui-timeline-content layui-text">
                            <h3 class="layui-timeline-title">2020-08-11 </h3>
                            <p>
                                1.字典和字典模块功能
                            </p>
                        </div>
                    </li>
                    <li class="layui-timeline-item">
                        <i class="layui-icon layui-timeline-axis">&#xe63f;</i>
                        <div class="layui-timeline-content layui-text">
                            <h3 class="layui-timeline-title">2020-07-30 </h3>
                            <p>
                                1.新增菜单功能<br>
                                2.加入Ztree树形工具
                            </p>
                        </div>
                    </li>
                    <li class="layui-timeline-item">
                        <i class="layui-icon layui-timeline-axis">&#xe63f;</i>
                        <div class="layui-timeline-content layui-text">
                            <h3 class="layui-timeline-title">2020-07-24</h3>
                            <p>
                                1.修复：若干bug</br>
                                2.新增：代码生成模板功能
                            </p>
                        </div>
                    </li>
                    <li class="layui-timeline-item">
                        <i class="layui-icon layui-timeline-axis">&#xe63f;</i>
                        <div class="layui-timeline-content layui-text">
                            <h3 class="layui-timeline-title">2020-07-17 </h3>
                            <p>
                                1.新增：加入swagger数据接口文档
                            </p>
                        </div>
                    </li>
                    <li class="layui-timeline-item">
                        <i class="layui-icon layui-timeline-axis">&#xe63f;</i>
                        <div class="layui-timeline-content layui-text">
                            <h3 class="layui-timeline-title">2020-01-19</h3>
                            <p>
                                1.新增：文件上传功能
                                2.新增：线程池
                                3.整合Shiro
                                4.整合Freemarker
                            </p>
                        </div>
                    </li>
                    <li class="layui-timeline-item">
                        <i class="layui-icon layui-timeline-axis">&#xe63f;</i>
                        <div class="layui-timeline-content layui-text">
                            <h3 class="layui-timeline-title">2020-01-17</h3>
                            <p>
                                1.搭建代码生成工具
                            </p>
                        </div>
                    </li>
                    <li class="layui-timeline-item">
                        <i class="layui-icon layui-timeline-axis">&#xe63f;</i>
                        <div class="layui-timeline-content layui-text">
                            <div class="layui-timeline-title">wan</div>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>
</body>
</html>

