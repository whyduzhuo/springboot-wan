<!DOCTYPE html>
<html>
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
                    <div>开源地址：<a href="https://gitee.com/little_one/springboot-wan.git">https://gitee.com/little_one/springboot-wan.git</a></div>
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
                            <h3 class="layui-timeline-title">v2.0.3</h3>
                            <p>
                                1. 更新：重命名菜单类型为：目录、菜单、按钮<br>
                                2. 更新：重写Shiro“记住我”系列化数据，减少cookie体积<br>
                                3. 新增：获取用户角色列表方法<br>
                                4. 修复：获取部门数据时延迟加载超时问题<br>
                                5. 修复：将jq版本改为2.2.4，解决layui弹出窗口最大化问题<br>
                                6. 新增：项目配置项，可直接通过yml文件配置Shiro和XSS防护忽略规则<br>
                                7. 新增：ResultExceptionError和ResultExceptionSuccess异常类<br>
                                8. 修复：若干页面显示问题，优化加载时提示<br>
                            </p>
                        </div>
                    </li>
                    <li class="layui-timeline-item">
                        <i class="layui-icon layui-timeline-axis">&#xe63f;</i>
                        <div class="layui-timeline-content layui-text">
                            <h3 class="layui-timeline-title">v2.0.2</h3>
                            <p>
                                1.优化：根据【阿里巴巴Java开发手册】对代码进行优化处理！<br>
                                2.新增：上传文件路径输出为全路径方法<br>
                                3.更新：介绍文档及HTML页面头部信息<br>
                                4.更新：去掉“更多按钮”，直接显示隐藏内容，不再需要点击按钮滑动内容<br>
                                5.修复：编译时警告信息！<br>
                                6.修复：代码生成新模块版本号问题<br>
                                7.修复：接口无法继承多个父接口的问题<br>
                                8.修复：jwt组件中添加获取用户名的方法<br>
                                9.修复：代码生成时控制器保存地址问题<br>
                                10.修复：部门、菜单控制器更新数据时，pids无法更新<br>
                            </p>
                        </div>
                    </li>
                    <li class="layui-timeline-item">
                        <i class="layui-icon layui-timeline-axis">&#xe63f;</i>
                        <div class="layui-timeline-content layui-text">
                            <h3 class="layui-timeline-title">v2.0.1</h3>
                            <p>
                                1.新增：加入JWT TOKEN鉴权机制，实现多端的权限验证！<br>
                                2.更新：模块化全局统一异常处理机制及项目配置，降低模块间的耦合性！<br>
                                3.修复：部分环境下代码生成模板文件编译时的后缀遗留问题！<br>
                                4.修复：部门更新导致pids字段为空的问题！<br>
                            </p>
                        </div>
                    </li>
                    <li class="layui-timeline-item">
                        <i class="layui-icon layui-timeline-axis">&#xe63f;</i>
                        <div class="layui-timeline-content layui-text">
                            <h3 class="layui-timeline-title">v2.0</h3>
                            <p>
                                1.重构项目结构，引入组件及业务组概念<br>
                                2.前后台分离部署，可以针对前台进行性能优化<br>
                                3.优化逻辑删除功能，限制查询已删除的数据<br>
                                4.重构代码生成功能解析方式<br>
                                5.优化弹出式窗口，加入最大化及自动适应大小<br>
                                6.优化数据列表在小窗口下显示混乱问题
                            </p>
                        </div>
                    </li>
                    <li class="layui-timeline-item">
                        <i class="layui-icon layui-timeline-axis">&#xe63f;</i>
                        <div class="layui-timeline-content layui-text">
                            <h3 class="layui-timeline-title">v1.0.8</h3>
                            <p>
                                1.重构字典模块，简化字典操作<br>
                                2.修复个人信息修改时不通过问题<br>
                                3.修复用户名修改报错问题，防止脏数据报错<br>
                                4.修复上传文件无法访问的bug<br>
                                5.修复生成实体类Text类型的bug
                            </p>
                        </div>
                    </li>
                    <li class="layui-timeline-item">
                        <i class="layui-icon layui-timeline-axis">&#xe63f;</i>
                        <div class="layui-timeline-content layui-text">
                            <h3 class="layui-timeline-title">v1.0.6</h3>
                            <p>
                                加入导入导出功能
                            </p>
                        </div>
                    </li>
                    <li class="layui-timeline-item">
                        <i class="layui-icon layui-timeline-axis">&#xe63f;</i>
                        <div class="layui-timeline-content layui-text">
                            <h3 class="layui-timeline-title">v1.0.5</h3>
                            <p>
                                1.加入QuerySpec动态查询实例<br>
                                2.加入选择排序功能<br>
                                3.加入xss防护功能<br>
                                4.加入swagger数据接口文档
                            </p>
                        </div>
                    </li>
                    <li class="layui-timeline-item">
                        <i class="layui-icon layui-timeline-axis">&#xe63f;</i>
                        <div class="layui-timeline-content layui-text">
                            <h3 class="layui-timeline-title">v1.0.1</h3>
                            <p>
                                1.加入部门管理<br>
                                2.更新项目开源协议<br>
                                3.支持第三级子菜单
                            </p>
                        </div>
                    </li>
                    <li class="layui-timeline-item">
                        <i class="layui-icon layui-timeline-axis">&#xe63f;</i>
                        <div class="layui-timeline-content layui-text">
                            <h3 class="layui-timeline-title">v1.0</h3>
                            <p>
                                正式发布v1.0系统<br>
                                1.权限管理<br>
                                2.字典管理<br>
                                3.日志管理<br>
                                4.代码生成
                            </p>
                        </div>
                    </li>
                    <li class="layui-timeline-item">
                        <i class="layui-icon layui-timeline-axis">&#xe63f;</i>
                        <div class="layui-timeline-content layui-text">
                            <div class="layui-timeline-title">小懒虫</div>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>
</body>
</html>

