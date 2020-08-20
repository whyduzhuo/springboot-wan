<!DOCTYPE html>
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
<form id="listForm" action="list.html" method="get">
    <div class="page-head">
        <div class="page-head-left">
            <a href="javascript:addBatch();" class="btn btn-sm btn-danger hidden-xs">
                <i class="fa fa-plus"></i>更新二手房</a>
            <a href="javascript:refulsh();" class="btn btn-sm btn-success hidden-xs">
                <i class="fa fa-plus"></i>刷新</a>
        </div>
        <div class="page-head-right">
            <div class="search-item">
                <label>请求:</label>
                <input class="input-sm input-search" name=""/>
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