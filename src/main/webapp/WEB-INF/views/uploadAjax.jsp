<%--
  Created by IntelliJ IDEA.
  User: yhs
  Date: 04/03/2020
  Time: 4:42 오후
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<style>
    .uploadResult{
        width: 100%;
        background-color: gray;
    }

    .uploadResult ul {
        display: flex;
        flex-flow: row;
        justify-content: center;
        align-items: center;
    }

    .uploadResult ul li {
        list-style: none;
        padding: 10px;
    }

    .uploadResult ul li img {
        width: 20px;
    }

    .uploadResult ul li span {
        color: white;
    }

    .bigPictureWrapper{
        position: absolute;
        display: none;
        justify-content: center;
        align-items: center;
        top: 0%;
        width: 100%;
        height: 100%;
        background-color: gray;
        z-index: 100;
        background: rgba(255, 255, 255, 0.5);
    }

    .bigPicture{
        position: relative;
        display: flex;
        justify-content: center;
        align-items: center;
    }

    .bigPicture img {
        width: 600px;
    }

</style>

<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Upload with Ajax</h1>

<div class="uploadDiv">
    <input type="file" name="uploadFile" multiple="multiple"/>
</div>

<div class="uploadResult">
    <ul>

    </ul>
</div>

<button id="uploadBtn">Upload</button>

<div class="bigPictureWrapper">
    <div class="bigPicture">
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.4.1.js"
        integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
        crossorigin="anonymous"></script>

<script>
    var regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");
    var maxSize = 5242880;
    var cloneObj = $(".uploadDiv").clone();

    var uploadResult = $(".uploadResult ul");

    function showUploaderFile(uploadResultArr) {
        var str = "";
        //console.log("show Up file")
        $(uploadResultArr).each(function (i, obj) {

            if(!obj.image) {
                //console.log("not image")
                var fileCallPath = encodeURIComponent(obj.uploadPath + "/" + obj.uuid + "_" + obj.fileName);

                var fileLink = fileCallPath.replace(new RegExp(/\\/g), "/");

                str += "<li><div><a href = '/download?fileName="+fileCallPath+"'><img src='/resources/img/attach.png' style='width: 20px'>"
                    +obj.fileName + "</a>"+"<span data-file=\'"+fileCallPath+"\' data-type='file'> x </span></div></li>";
            } else {
                //str += "<li>" + obj.fileName + "</li>";

                var fileCallPath = encodeURIComponent(obj.uploadPath + "/s_" + obj.uuid + "_" + obj.fileName);
                //console.log(fileCallPath);
                var originPath = obj.uploadPath + "/" + obj.uuid + "_" + obj.fileName;

                originPath = originPath.replace(new RegExp(/\\/g), "/");

                str += "<li><a href=\"javascript:showImage(\'"+originPath+
                    "\')\"><img src='/display?fileName=/upload/"+fileCallPath+"'></a><span data-file=\'"+fileCallPath+"\' data-type='image'> x </span></li>";
            }
        });

        uploadResult.append(str);
    }


    function checkExtension(fileName, fileSize) {

        if (fileSize >= maxSize) {
            alert("파일 사이즈 초과");
            return false;
        }

        if (regex.test(fileName)) {
            alert("해당 종류의 파일은 업로드 할 수 없습니다.");
            return false;
        }
        return true;
    }

    function showImage(fileCallPath) {
        //alert(fileCallPath);
        $(".bigPictureWrapper").css("display", "flex").show();

        $(".bigPicture")
        .html("<img src='/display?fileName=upload/" + encodeURI(fileCallPath)+"'>")
        .animate({width: '100%', height: '100%'}, 1000);
    }

    $(".bigPictureWrapper").on("click", function (e) {
        $(".bigPicture").animate({width: '100%', height: '0%'}, 1000);
        setTimeout(()=>{
            $(this).hide();
        }, 1000);
    });

    $(".bigPictureWrapper").on("click", function (e) {
        $(".bigPicture").animate({width: '0%', height: '0%'}, 1000);
        setTimeout(()=> {
            $(this).hide();
        }, 1000);
    });

    $(".uploadResult").on("click", "span", function (e) {
        var targetFile = $(this).data("file");
        var type = $(this).data("type");
        console.log(targetFile);

        $.ajax({
            url: '/deleteFile',
            data: {fileName: targetFile, type: type},
            dataType: 'text',
            type: 'POST',
                success: function (result) {
                    alert(result);
                }
        });//ajax end
    })

    $(document).ready(function () {
        $("#uploadBtn").on("click", function (e) {
            var formData = new FormData();

            var inputFile = $("input[name='uploadFile']");

            var files = inputFile[0].files;
            console.log(files);

            //add filedata to formdata
            for (var i = 0; i < files.length; i++) {
                // if(!checkExtension(files[i].name, files[i].size)){
                //     return false;
                // }
                formData.append("uploadFile", files[i]);
            }

            $.ajax({
                url: '/uploadAjaxAction',
                processData: false,
                contentType: false,
                data: formData,
                type: 'POST',
                dataType: 'json',
                success: function (result) {
                    console.log(result);

                    showUploaderFile(result);

                    $(".uploadDiv").html(cloneObj.html());
                }, error(e) {
                    alert("fail");
                    console.log(e);
                }
            }); //$.ajax

        });
    })

</script>
</body>
</html>

