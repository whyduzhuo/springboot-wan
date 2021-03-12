<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <#include "/common/tmp/commom.ftl">
</head>
<body>
    <table>
        <tr>
            <td>密码</td>
            <td>
                <input type="text" id="password">
                <button onclick="dadwa()">发送</button>
            </td>
        </tr>
    </table>
</body>
<script src="/static/js/jsencrypt.min.js"></script>

<script>
    function dadwa() {
        var pubkey="${publicKey}";//公钥
        // 利用公钥加密
        var encrypt = new JSEncrypt();
        encrypt.setPublicKey(pubkey);
        var password = $("#password");
        var encrypted = encrypt.encrypt(JSON.stringify(password));
        ajaxPost("jimi",{"publicKey":"${publicKey}","password":encrypted},function () {

        })
    }

</script>
</html>