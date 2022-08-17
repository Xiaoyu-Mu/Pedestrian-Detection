$(function () {
    //选择图片后，在前台展示 & 动态添加“上传按钮”
    $("#file").change(function(){
        $("#imgs").attr("src",URL.createObjectURL($(this)[0].files[0]));
        $("#imgs").after("<a onclick='upload()' id=\"upload\" style=\"margin-top: 20px;background: #0dcaf0\" class=\"btn btn-sm btn-outline-primary text-uppercase\">\n" +
            "                                        <span><i class=\"icofont-double-right icon-space-left\"></i> upload <i class=\"icofont-double-left icon-space-left\"></i></span> </a>");
        $("#upload").next().remove();
    });
    //开始图片检测
    $("#detect").click(function () {
        let file = $("#file")[0].files[0];
        if (file===""||file==null){
            alert("Please select the file！");
        }else {
            //显示模态框
            $("#togglemodal0101").trigger("click");
            $.ajax({
                "url": "/files/detect",
                "type": "post",
                "dataType": "json",
                "success": function (json) {
                    if (json.state === 2000) {
                        // 消除模态框
                        $("#dialog-close").trigger("click");
                        alert("Image detected successfully！");
                        // 跳转到某个页面
                    } else {
                        // 消除模态框
                        $("#dialog-close").trigger("click");
                        alert(json.message);
                    }
                }
            });
        }
    });
})
//上传图片
function upload() {
    let file = $("#file")[0].files[0];
    let fd = new FormData();
    fd.append("originImg",file);
    if (file===""||file==null){
        alert("Please select the file！");
    }
    $.ajax({
        "url": "/files/upload",
        "data": fd,
        "type": "post",
        "dataType": "json",
        "processData": false,//用于对data参数进行序列化处理 这里必须false
        "contentType": false,//必须
        "success": function (json) {
            if (json.state === 2000) {
                alert("Upload complete！");
                // 跳转到某个页面
            } else {
                alert(json.message);
            }
        }
    });
}
let mask01 = $(".mask01")[0];
let modal01 = $(".modal01")[0];
function model01() {
    mask01.style.display = "block";
    modal01.style.display = "block";
}
function model02() {
    mask01.style.display = "none";
    modal01.style.display = "none";
}