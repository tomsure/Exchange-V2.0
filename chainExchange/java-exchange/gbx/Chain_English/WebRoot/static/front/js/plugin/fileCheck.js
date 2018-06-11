

/***************************ajax文件上传 start*****************************/
/**
 * 文件上传
 * @param url 请求后台的url
 * @param folderCode  上传到阿里云的文件夹code号，必须与枚举FileUploadType一致
 * @param hid_relativeUrl_id 缓存在页面上的相对地址，用于form提交到后台存储进库
 * @param fileId 页面file框的id
 * @param showImgId 如果上传的是图片，回调显示预览，传递img的id
 * @param showDivId 预览图片所在div的id，原始不显示，传递后会显示
 * @param func_a 事件1                    [可选]
 * @param f_param_a 事件１参数             [可选]
 *
 * 注：事件只在上传成功后触发
 */
function fileUpload(url, folderCode, fileId, hid_relativeUrl_id, showImgId, showDivId, func_a, f_param_a) {

    $.ajaxFileUpload({
        url: url,
        type: "post",
        data: {
            "code": folderCode
        },
        secureuri: false,
        fileElementId: fileId,
        dataType: "text/html",
        success: function (text, status) {
        	var data = eval('(' + text + ')'); 
            if (data.code == 0) {
                if (hid_relativeUrl_id) {
                    $("#" + hid_relativeUrl_id).val(data.resultUrl);
                }
                if (showImgId) {
                    $("#" + showImgId).attr("src", data.resultUrl);
                }
                if (showDivId) {
                    $("#" + showDivId).css("display", "block");
                }

                //触发事件1
                if (func_a != null) {
                    var argument = formatParam(f_param_a, data);
                    func_a.apply(this, argument);
                }
            }else{
                alert("Upload Failed");
            }
        },
        error: function (data, status, e) {
            alert("Upload Failed");
        }
    });
}

//从上传返回的结果中，取对象数据
function formatParam(params, data) {
    if(params == null) {
        params =  "";
    }

    var argu = params.split(',');
    var argument = new Array();
    for (i = 0; i < argu.length; i++) {
        var param = argu[i];
        if(param == "resultUrl"){
            argument[i] = data.resultUrl;
        }else{
            argument[i] = param;
        }
    }

    return argument;
}

/***************************ajax文件上传 end*****************************/


/***************************文件上传时，判断选择的文件是否符合 start*****************************/
/**
 * 文件上传时，判断文件上传的类型是否符合要求
 * @param inputId 上传框的id
 * @param fileType  文件类型，不传，则默认为img图片
 */
function checkFileType(inputId, fileType) {
    var check_flag = true;
    if(!fileType){
        fileType = "img";
    }

    //获取文件上传的后缀，进行格式校验
    var filepath = $("#"+inputId).val();
    var extStart = filepath.lastIndexOf(".");
    var ext = filepath.substring(extStart, filepath.length).toUpperCase();
    //图片校验
    if(fileType == "img"){
        if (ext != ".JPG" && ext != ".JPEG" && ext != ".PNG" && ext !=".BMP") {
            alert("Support file type:jpg,png,jpeg,bmp.");
            $("#"+inputId).val("");
            check_flag = false;
            return check_flag;
        }
    }else if(fileType == "video") {  //音频校验
        if (ext != ".MP4" && ext != ".AVI" && ext != ".WMV" && ext != ".FLV" && ext != ".MKV" && ext != ".MOV") {
            alert("Support file type:mp4,avi,wmv,flv,mkv,mov.");
            $("#"+inputId).val("");
            check_flag = false;
            return check_flag;
        }
    }else if(fileType == "txt"){
        if (ext != ".TXT") {
            alert("Support file type:txt");
            $("#"+inputId).val("");
            check_flag = false;
            return check_flag;
        }
    }else if(fileType == "dpdf") {
        if (ext != ".DOC" && ext != ".PDF" ) {
            alert("Support file type:doc,pdf");
            $("#"+inputId).val("");
            check_flag = false;
            return check_flag;
        }
    }else{
        check_flag = false;
        alert("File in wrong format");
    }
    return check_flag;
}

/***************************文件上传时，判断选择的文件是否符合 end*****************************/