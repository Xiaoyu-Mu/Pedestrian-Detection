$(function () {
    //展示图片
    getImgUrl();
    //下载检测报告
    $("#downloadPDF").click(function () {
        //获得图片名称
        let originImg=$("#show-img-origin").attr("src");
        originImg=originImg.substr(originImg.lastIndexOf("/"));//包含一个反斜杠"/blog-single-1.png"
        let detectImg=$("#show-img-detect").attr("src");
        detectImg=detectImg.substr(detectImg.lastIndexOf("/"));
        console.log(originImg+"||"+detectImg);
        window.location.href="http://localhost:8080/pdf/export?originImg="+originImg+"&detectImg="+detectImg;
    })

})
function getImgUrl() {
    $.ajax({
        "url": "/files/show",
        "type": "get",
        "dataType": "json",
        "success": function (json) {
            if (json.state === 2000) {
                let srcOrigin="http://localhost:8080"+json.data[0];
                let srcDetect="http://localhost:8080"+json.data[1];
                $("#show-img-origin").attr("src",srcOrigin);
                $("#show-img-detect").attr("src",srcDetect);
            }else {
                alert("出现未知错误！")
            }
        },
        "error":function () {
            alert("出现未知错误")
        }
    })
}