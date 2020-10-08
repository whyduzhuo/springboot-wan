<!DOCTYPE html>
<html class="ie9 no-focus">
<head>
    <meta charset="utf-8">
    <title>列表</title>
    <#include "/common/tmp/commom.ftl">
    <meta name="robots" content="noindex, nofollow">
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1.0">
    <style type="text/css">
        .map{
            display: inline-block;
            background-color: #6aa2d8;
            width: 33%;
            padding: 1%;
            height: 300px
        }
    </style>
</head>
<body>
<form id="listForm" action="city" method="get">
    <div class="page-head">
        <@pageHeadLeft>
            <a href="javascript:refulsh();" class="btn btn-sm btn-success hidden-xs">刷新</a>
        </@pageHeadLeft>
        <@pageHeadRight>
            <div class="search-item">
                <label>城市:</label>
                <input class="input-sm input-search" name="" value="">
            </div>
        </@pageHeadRight>
    </div>
    <div class="page-body">
        <div id="contry" class="map" style="width: 99.45%;margin-bottom: 5px;">
            <script>
                var myChart = echarts.init(document.getElementById('contry'));
                option = {
                    title: {
                        text: '全国挂牌量走势'
                    },
                    xAxis: {
                        type: 'category',
                        data: [<#list contry as con>'${con.D}',</#list>]
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
                        data: [<#list contry as con>${con.C},</#list>],
                        type: 'line'
                    }]
                };
                myChart.setOption(option);
            </script>
        </div>
    <#list dataList as city>
        <div id="map_${city.url.id}" class="map">
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
                        type: 'line',
                        smooth: true
                    }]
                };
                myChart.setOption(option);
            </script>
        </div>
        </#list>
    </div>
</form>

<script>
    function refulsh() {
        window.location.reload();
    }


</script>
</body>
</html>
