<!DOCTYPE html>
<html>
    <head>
        <title>支付</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <#include "/common/tmp/commom.ftl">
        <script src="http://res.wx.qq.com/open/js/jweixin-1.6.0.js"></script>
    </head>
    <body>
        <div class="main">
            <form>
                <input type="number">
                <button type="button" onclick="onBridgeReady()">支付</button>
            </form>

        </div>
    </body>
    <script>
        function onBridgeReady(){
            WeixinJSBridge.invoke(
                    'getBrandWCPayRequest', {
                        "appId":"wx527204cf937aad4a",     //公众号ID，由商户传入
                        "timeStamp":"1395712654",         //时间戳，自1970年以来的秒数
                        "nonceStr":"e61463f8efa94090b1f366cccfbbb444", //随机串
                        "package":"prepay_id=u802345jgfjsdfgsdg88811",
                        "signType":"MD5",         //微信签名方式：
                        "paySign":"70EA570631E4BB79628FBCA90534C63FF7FADD89" //微信签名
                    },
                    function(res){
                        if(res.err_msg == "get_brand_wcpay_request:ok" ){
                            // 使用以上方式判断前端返回,微信团队郑重提示：
                            //res.err_msg将在用户支付成功后返回ok，但并不保证它绝对可靠。
                        }
                    });
        }
        if (typeof WeixinJSBridge == "undefined"){
            if( document.addEventListener ){
                document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
            }else if (document.attachEvent){
                document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
                document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
            }
        }else{
            onBridgeReady();
        }

    </script>
</html>