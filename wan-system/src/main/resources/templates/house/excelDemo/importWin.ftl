<!DOCTYPE html>
<!--[if IE 9]>
<html class="ie9 no-focus"> <![endif]-->
<!--[if gt IE 9]><!-->
<html class="no-focus"> <!--<![endif]-->
<head>
    <meta charset="utf-8">
    <title>列表</title>
    <meta name="author" content="bianmaren">
    <meta name="robots" content="noindex, nofollow">
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1.0">
    <#include "/common/tmp/commom.ftl">
</head>
<body>
 <@adminPageNav navName1='系统工具' navName2='ExcelDemo'/>
<div class="block">
    <div class="block-content table-responsive">
        <form id="inputForm" class="form-horizontal"  method="post">
            <div class="form-group">
                <div class="col-xs-12 col-md-12">
                    <label for="filePath">选择文件（*.xls)</label>
                    <input id="file" type="file" name="file" class="form-control">
                    <input value="false" type="hidden" id="isupload" name="isupload">
                </div>
                <a style="margin-right: 80px;" href='downLoadExcelTemp' target="_blank" title="点击下载模板">
                    <i class="glyphicon glyphicon-save" data-toggle="tooltip" data-original-title="下载Excel模板"></i>
                    下载Excel模板
                </a>
                <div class="col-xs-12 col-md-12 alert alert-warning">
                    注：姓名，工号，学院，身份必填。
                    身份可填 教工、学生
                    导入之后密码与工号相同，但没有角色信息
                </div>
                <div class="col-xs-12 col-md-12 " id="message_detil" style="display: none;">
                    <div style="border: 1px solid #faccc6;color: #e4393c;background-color: #ffebeb;padding: 5px;"></div>
                </div>
            </div>
            <div class="form-group">
                <div class="col-xs-12 col-md-4">
                    <button class="btn btn-sm btn-success" type="button" onclick="importData(false)">检查</button>
                    <button class="btn btn-sm btn-primary" name="11"  type="button" onclick="importData(true)">上传</button>
                    <button type="button" class="btn btn-default" onclick="closeLayer()" id="submit-btn">关闭</button>
                </div>
            </div>
        </form>
    </div>
</div><!-- /.block -->
<script>

    function closeLayer() {
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
    }


    //是否检查过
    var isReady = false;
    //检查是否通过
    var  isWorn = false;
    //是否导入成功
    var isSuccess = false;
    $("#file").change(function () {
        isReady = false;
        isWorn = false;
        $("#message_detil div").html('');
        $("#message_detil").hide();
    });


    function importData(isupload) {
        layer.load();
        $("#message_detil div").html('');
        $("#isupload").val(isupload);
        console.log("isupload :"+isupload);
        if ($(":file[name=filePath]").val() == "") {
            alert("请选择文件");
            layer.closeAll("loading");
            return;
        }
        if(!isReady && isupload){
            layer.confirm("请先进行数据检查！再上传！",{icon:0}, function (index) {
                layer.close(index);
                return;
            });
            layer.closeAll("loading");
            return;
        }
        if(!isWorn && isupload){
            layer.confirm("检查出错！，是否继续上传？",{icon:0},function (index) {
                isWorn = true;
                layer.close(index);
            });
        }
        if(!isWorn && isupload){
            layer.closeAll("loading");
            return;
        }
        var form = new FormData(document.getElementById('inputForm'));
        $.ajax({
            url: "importData",
            type: "POST",
            data: form,
            timeout:1200*1000,
            processData: false,
            contentType: false,
            success: function (data) {
                layer.closeAll("loading");
                isReady = true;
                if(data.msg.indexOf("检查无异常")>0){
                    isWorn = true;
                }
                var a = data.msg.substring(data.msg.indexOf("<h5>成功")+7,data.msg.indexOf("条。失败:"));
                if(a>0){
                    isSuccess = true;
                }
                showImportMessage(isupload,data.msg);

            },
            error: function (XMLHttpRequest) {
                layer.closeAll("loading");
                layer.msg("请刷新页面重试！",{icon:0})
            }
        })
    }

    function showImportMessage(isupload,message) {
        $("#message_detil div").html(message);
        $("#message_detil").show();
    }
</script>
</body>
</html>


