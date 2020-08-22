<!<!DOCTYPE html>
<!--[if IE 9]>         <html class="ie9 no-focus"> <![endif]-->
<!--[if gt IE 9]><!-->
<html class="no-focus"> <!--<![endif]-->
<head>
    <meta charset="utf-8">
    <title>列表</title>
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
            <a href="javascript:addBatch();" class="btn btn-sm btn-danger hidden-xs">
                <i class="fa fa-plus"></i>更新二手房</a>
            <a href="javascript:refulsh();" class="btn btn-sm btn-success hidden-xs">
                <i class="fa fa-plus"></i>刷新</a>
        </div>
        <div class="page-head-right">
            <div class="search-item">
                <button class="btn btn-success btn-sm" type="submit"><span class="glyphicon glyphicon-search"></span></button>
            </div>
        </div>
    </div>
    <div class="page-body">
    <#list dataList as city>
        <div id="map_${city.url.id}" style="width: 100%; height: 300px">
            <script>
                var myChart = echarts.init(document.getElementById('map_${city.url.id}'));
                option = {
                    title: {
                        text: '${city.url.name}'
                    },
                    xAxis: {
                        type: 'category',
                        data: [<#list city.data as data>'${data.date}',</#list>]
                    },
                    yAxis: {
                        scale:true,
                        type: 'value'
                    },
                    series: [{
                        label: {
                            normal: {
                                show: true,
                                position: 'top'
                            }
                        },
                        data: [<#list city.data as data>${data.num},</#list>],
                        type: 'line'
                    }]
                };
                myChart.setOption(option);
            </script>
        </div>
        </#list>



        <#--<#list dataList as city>-->
            <#--<div class="map_${city.url.id}" style="width: 100%; height: 300px">-->
            <#--</div>-->
            <#--<script>-->
                <#--var myChart = echarts.init(document.getElementById('map_${city.url.id}'));-->
                <#--option = {-->
                    <#--title: {-->
                        <#--text: '${city.url.name}'-->
                    <#--},-->
                    <#--xAxis: {-->
                        <#--type: 'category',-->
                        <#--data: [<#list city.data as data>'${data.date}',</#list>]-->
                    <#--},-->
                    <#--yAxis: {-->
                        <#--type: 'value'-->
                    <#--},-->
                    <#--series: [{-->
                        <#--data: [<#list city.data as data>${data.num},</#list>],-->
                        <#--type: 'line'-->
                    <#--}]-->
                <#--};-->
                <#--myChart.setOption(option);-->
            <#--</script>-->
        <#--</#list>-->
    </div>
</form>

<script>
    function refulsh() {
        window.location.reload();
    }


</script>
</body>
</html>
