<!DOCTYPE html>
<!--[if IE 9]>         <html class="ie9 no-focus"> <![endif]-->
<!--[if gt IE 9]><!-->
<html class="no-focus"> <!--<![endif]-->
<head>
    <meta charset="utf-8">
    <title>列表</title>
    <link rel="shortcut icon" href="/static/favicon.ico"/>
    <script type="text/javascript" src="/static/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="/static/jquery/bootstrap.min.js"></script>
    <script type="text/javascript" src="/static/layui-v2.5.6/layui/layui.all.js"></script>
    <link href="/static/layui-v2.5.6/layui/css/layui.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="/static/bootstrap/bootstrap.min.css">

    <script type="text/javascript" src="/static/echart4.5/echarts.min.js"></script>

    <meta name="author" content="bianmaren">
    <meta name="robots" content="noindex, nofollow">
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1.0">
    <style>
        html{
            background-color: #f5f5f5;
        }
        body{
            margin: 10px;
        }
        tr th,tr td{
            text-align: center;
        }
        .page-head{
            display: flex;
            padding: 5px 0px;
            margin-bottom: 10px;
        }
        .page-head,.page-body{
            border-radius: 5px;
            padding: 5px;
            background-color: #FFF;
            box-shadow: 0px 0px 5px #999;
        }
        .page-head-left{
            flex: 3;
        }
        .page-head-right{
            flex: 7;
        }
        .search-item{
            float: right;
        }
        .input-search{
            padding: 2px 5px;
            border: 1px solid #ccc;
            line-height: 26px;
            font-size: 14px;
        }
        .search-item label{
            font-family: serif;
            font-size: 16px;
        }
    </style>
</head>
<body>
<form id="listForm" action="date" method="get">
    <div class="page-head">
        <div class="page-head-left">
            <@shiro.hasPermission name="110200">
            <a href="javascript:addBatch();" class="btn btn-sm btn-danger hidden-xs">
                <i class="fa fa-plus"></i>更新二手房</a>
            </@shiro.hasPermission>
            <a href="javascript:refulsh();" class="btn btn-sm btn-success hidden-xs">
                <i class="fa fa-plus"></i>刷新</a>
        </div>
        <div class="page-head-right">
            <div class="search-item">
                <label>请求:</label>
                <input class="input-sm input-search" id="url" name=""/>
                <label>charSet:</label>
                <input class="input-sm input-search" id="charSet" name=""/>
                <a href="javascript:ceshi();" class="btn btn-sm btn-success hidden-xs">
                    <i class="fa fa-plus"></i>测试</a>
                <label>日期:</label>
                <input class="input-sm input-search" id="date" name="date" value="${date}"/>
                <button class="btn btn-success btn-sm" type="submit"><span class="glyphicon glyphicon-search"></span></button>
            </div>
        </div>
    </div>
    <div class="page-body">
        <div id="map" style="width: 100%; height: 4000px">


        </div>
    </div>
</form>

<script>
    function refulsh() {
        window.location.reload();
    }

    layui.use('laydate', function(){
        var laydate = layui.laydate;
        laydate.render({
            elem: '#date'
        });

    });


    function ceshi() {
        var url = $("#url").val();
        var charSet = $("#charSet").val();
        if(url == ''){
            alert('请输入地址');
            return;
        }
        var pageCode = "";
        $.ajax({
            url: "ceshi?url="+url+"&charSet="+charSet,
            type: "get",
            async:false,
            success: function (res) {
                layer.closeAll("loading");
                pageCode = res.data;
            },
            error: function (XMLHttpRequest) {
                layer.closeAll("loading");
                console.log(XMLHttpRequest);
            }
        });
        layer.open({
            type: 1,
            title: 'ceshi',
            maxmin: true,
            area: ['500px', '600px'],
            content:pageCode
        });
    }

    function addBatch() {
        layer.load();
        $.ajax({
            url: "${base}/house/housecount/addBatch",
            type: "get",
            success: function (res) {
                layer.closeAll("loading");
                layer.confirm(res.msg,{icon:res.icon}, function (index) {
                    layer.close(index);
                });
            },
            error: function (XMLHttpRequest) {
                layer.closeAll("loading");
                console.log(XMLHttpRequest);
            }
        });
    }

    var myChart = echarts.init(document.getElementById('map'));
    option = {
        title: {
            text: '二手房挂牌量',
            subtext: '数据来自链家'
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'shadow'
            }
        },
        legend: {
            data: ['${date}']
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: {
            type: 'value',
            boundaryGap: [0, 0.01]
        },
        yAxis: {
            type: 'category',
            data: [<#list data as d>'${d.NAME}',</#list>]
        },
        series: [
            {
                label: {
                    normal: {
                        show: true,
                        position: 'right'
                    }
                },
                name: '${date}',
                type: 'bar',
                data: [<#list data as d>${d.LJ_HOUSE_COUNT},</#list>]
            }
        ]
    };
    myChart.setOption(option);
</script>
</body>
</html>