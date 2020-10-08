<!DOCTYPE html>
<html class="ie9 no-focus">
<head>
    <meta charset="utf-8">
    <title>列表</title>
   <#include "/common/tmp/commom.ftl">
    <meta name="robots" content="noindex, nofollow">
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1.0">
</head>
<body>
<form id="listForm" action="date" method="get">
    <div class="page-head">
         <@pageHeadLeft>
            <@shiro.hasPermission name="110200">
            <a href="javascript:addBatch();" class="btn btn-sm btn-danger hidden-xs">
                <i class="fa fa-plus"></i>更新二手房</a>
            </@shiro.hasPermission>
            <a href="javascript:refulsh();" class="btn btn-sm btn-success hidden-xs">
                <i class="fa fa-plus"></i>刷新</a>
         </@pageHeadLeft>
         <@pageHeadRight>
            <div class="search-item">
                <label>请求:</label>
                <input class="input-sm input-search" id="url" name=""/>
            </div>
            <div class="search-item">
                <label>charSet:</label>
                <input class="input-sm input-search" id="charSet" name=""/>
            </div>
            <div class="search-item">
                <a href="javascript:ceshi();" class="btn btn-sm btn-success hidden-xs">
                    <i class="fa fa-plus"></i>测试</a>
                <label>日期:</label>
                <input class="input-sm input-search" id="date" name="date" value="${date}"/>
            </div>
         </@pageHeadRight>
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
            text: '二手房挂牌量${data?size}',
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