<!DOCTYPE html>
<html class="ie9 no-focus">
<head>
    <meta charset="utf-8">
    <title>列表</title>
    <#include "/common/tmp/commom.ftl">
</head>
<style>
    table{
        background-color: #fff;
        border: 1px solid #BBB;
        box-shadow: 0px 3px 6px #BBB;
    }
</style>
<body>
    <@adminPageNav navName1='系统工具' navName2='ExcelDemo'/>
    <div class="page-body">
        <div class="panel panel-primary">
            <div class="panel-heading">
                <h3 class="panel-title">Excel导入测试</h3>
            </div>
            <div class="panel-body">
                <form id="listForm" action="index" method="get">
                    <div class="page-head">
                        <@pageHeadLeft>
                        <button type="button" class="btn btn-success btn-sm" onclick="importWin()">导入</button>
                        </@pageHeadLeft>
                        <@pageHeadRight>
                            <div class="search-item">
                                <label>姓名:</label>
                                <input class="input-sm input-search" name="search_like_name" value=""/>
                            </div>
                        </@pageHeadRight>
                    </div>
                    <div class="page-body">
                        <table class="table table-bordered" id="listTable">
                            <tr>
                                <th class="remove-padding-t remove-padding-b">
                                    <label class="css-input css-checkbox css-checkbox-primary">
                                        <input type="checkbox" id="selectAll"><span></span>
                                    </label>
                                </th>
                                <th>序号</th>
                                <th>姓名</th>
                                <th>性别</th>
                                <th>民族</th>
                                <th>学院</th>
                            </tr>
                            <#list customSearch.pagedata.content as data>
                            <tr>
                                <td>
                                    <label class="css-input css-checkbox css-checkbox-primary remove-padding remove-margin">
                                        <input type="checkbox" name="ids" value="${data.id}"/><span></span>
                                    </label>
                                </td>
                                <td>${data_index+1}</td>
                                <td>${data.name}</td>
                                <td>${data.sex}</td>
                                <td>${data.mz}</td>
                                <td>${data.org}</td>
                            </tr>
                            </#list>
                        </table>
                        <div class="row"><@pageingTemaplte customSearch.pagedata /></div>
                    </div>
                </form>
            </div>
        </div>



        <div class="panel panel-primary">
            <div class="panel-heading">
                <h3 class="panel-title">下载一个带下拉选项的Excel</h3>
            </div>
            <div class="panel-body">
                <form target="_blank" action="downLoadTemp" method="get">
                    <button type="submit" class="btn btn-sm btn-primary">下载</button>
                    <table class="table table-bordered">
                        <tr>
                            <th>选项一</th>
                            <th>选项二</th>
                            <th>选项三</th>
                            <th>选项四</th>
                        </tr>
                        <tr>
                            <td>
                                <input class="form-control" type="text" name="parm1[0]" value="国际学院">
                            </td>
                            <td>
                                <input class="form-control" type="text" name="parm2[0]" value="2017-2018">
                            </td>
                            <td>
                                <input class="form-control" type="text" name="parm3[0]" value="汉族">
                            </td>
                            <td>
                                <input class="form-control" type="text" name="parm4[0]" value="专科">
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <input class="form-control" type="text" name="parm1[1]" value="工商学院">
                            </td>
                            <td>
                                <input class="form-control" type="text" name="parm2[1]" value="2018-2019">
                            </td>
                            <td>
                                <input class="form-control" type="text" name="parm3[1]" value="回族">
                            </td>
                            <td>
                                <input class="form-control" type="text" name="parm4[1]" value="本科">
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <input class="form-control" type="text" name="parm1[2]" value="会计学院">
                            </td>
                            <td>
                                <input class="form-control" type="text" name="parm2[2]" value="2019-2020">
                            </td>
                            <td>
                                <input class="form-control" type="text" name="parm3[2]" value="藏族">
                            </td>
                            <td>
                                <input class="form-control" type="text" name="parm4[2]" value="研究生">
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>

        <form target="_blank" action="expertData" method="post">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h3 class="panel-title">将数据导出Excel</h3>
                </div>
                <div class="panel-body">

                    <button type="button" class="btn btn-sm btn-primary" data-toggle="modal" data-target="#exportModel">导出</button>
                    <table class="table table-bordered">
                        <tr>
                            <th>姓名</th>
                            <th>学号</th>
                            <th>班级</th>
                            <th>学院</th>
                        </tr>
                        <tr>
                            <td>
                                <input class="form-control" type="text" name="parm1[0]" value="鲁班7号">
                            </td>
                            <td>
                                <input class="form-control" type="text" name="parm1[1]" value="10001">
                            </td>
                            <td>
                                <input class="form-control" type="text" name="parm1[2]" value="19会计1班">
                            </td>
                            <td>
                                <input class="form-control" type="text" name="parm1[3]" value="会计学院">
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <input class="form-control" type="text" name="parm2[0]" value="安其拉">
                            </td>
                            <td>
                                <input class="form-control" type="text" name="parm2[1]" value="10002">
                            </td>
                            <td>
                                <input class="form-control" type="text" name="parm2[2]" value="19工商管理1班">
                            </td>
                            <td>
                                <input class="form-control" type="text" name="parm2[3]" value="工商管理学院">
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <input class="form-control" type="text" name="parm3[0]" value="亚瑟">
                            </td>
                            <td>
                                <input class="form-control" type="text" name="parm3[1]" value="10003">
                            </td>
                            <td>
                                <input class="form-control" type="text" name="parm3[2]" value="19软件开发1班">
                            </td>
                            <td>
                                <input class="form-control" type="text" name="parm3[3]" value="软件工程学院">
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        <#--数据导出方法-->
            <div class="modal fade" id="exportModel" tabindex="-1"  aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-content" style="border-radius: 10px;width: 600px;margin: 0 auto;">
                    <div class="modal-header" style="background-color: #286ec5;color: #fff;border-radius: 10px 10px 0 0;">
                        <button type="button" class="close" data-dismiss="modal"
                                aria-hidden="true">×
                        </button>
                        <h4 class="modal-title" id="myModalLabel">
                            请选择导出范围
                        </h4>
                    </div>
                    <div class="modal-body">
                        <h4 style="display: inline-block">请选择导出字段</h4>
                        <div>
                            <div class="exp_column">
                                <label class="css-input css-checkbox css-checkbox-primary">
                                    <input type="checkbox" checked name="fields" value="姓名"/><span></span>姓名
                                </label>
                            </div>

                            <div class="exp_column">
                                <label class="css-input css-checkbox css-checkbox-primary">
                                    <input type="checkbox" checked name="fields" value="学号"/><span></span>学号
                                </label>
                            </div>

                            <div class="exp_column">
                                <label class="css-input css-checkbox css-checkbox-primary">
                                    <input type="checkbox" checked name="fields" value="班级"/><span></span>班级
                                </label>
                            </div>

                            <div class="exp_column">
                                <label class="css-input css-checkbox css-checkbox-primary">
                                    <input type="checkbox" checked name="fields" value="学院"/><span></span>学院
                                </label>
                            </div>

                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default"
                                data-dismiss="modal">关闭
                        </button>
                        <button type="submit" class="btn btn-primary" >
                            导出excel
                        </button>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal -->
        </form>
    </div>
</body>
<script>
    function importWin() {
        layer.open({
            type: 2,
            title: '导入数据',
            maxmin: true,
            area: ['500px', '600px'],
            content: 'importWin'
        })
    }
</script>
</html>
