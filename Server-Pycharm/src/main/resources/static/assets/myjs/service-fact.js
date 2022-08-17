$(function () {
    //开始图片检测
    $("#real-detect").click(function () {
        if (confirm("After the detection starts, if it stops, please press 'Q' on the keyboard.")){
            //显示模态框
            $("#togglemodal0101").trigger("click");
            $.ajax({
                "url": "/files/realDetect",
                "type": "post",
                "dataType": "json",
                "success": function (json) {
                    if (json.state === 2000) {
                        // 消除模态框
                        $("#dialog-close").trigger("click");
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