<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@include file="../layouts/header.jsp"%> 
</head>
<body class="bg-light">
    <div class="container">
        <%@include file="../layouts/top.jsp"%> 
        <div class="text-center">
            <form id="boardInsertForm" method="post" action="<c:url value='/board/${middle}/insert'/>" enctype="multipart/form-data" onsubmit="return validationForm()">
                <div class="insertArea">
                    <div class="input-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text">제목:&nbsp;</span>
                        </div>
                        <input type="text" class="form-control" name="boardTitle">
                    </div>
                    <div class="input-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text">
                                <label for="uploadFiles">
                                    <div class="btn btn-primary">파일첨부</div>
                                </label>
                            </span>
                        </div>
                        <input class="d-none form-control" type="file" id="uploadFiles" name="uploadFiles" multiple>
                        <div id="uploadResult" class="ms-2"></div>
                    </div>
                    <div class="form-control">
                        <textarea name="boardCntnt" id="ckeditor"></textarea>
                    </div>
                </div>
                <div class="buttonArea">
                    <button type="submit" id="btnSubmit" class="btn btn-success text-center">등록</button>
                    <a href='<c:url value="/board/${category}"/>' class="btn btn-danger text-center">취소</a>
                </div>
            </form>
        </div>
        <%@include file="../layouts/bottom.jsp"%>
    </div>
</body>
<script>
let ckeditor;

 var boardInsertPage = (function(){
        var init = function(){
            ClassicEditor.create(document.querySelector('#ckeditor'), {
                toolbar:[
                    'Undo', 'Redo', 'Heading' , '|'
                    , 'FontSize', 'FontFamily', 'FontColor', 'FontBackgroundColor' , '|'
                    , 'Alignment', 'Bold', 'Italic', 'UnderLine', 'Highlight', '|'
                    , 'BlockQuote', 'Outdent', 'Indent', 'BulletedList', 'NumberedList', 'TodoList', '|'
                    , 'InsertTable', 'Link', 'ImageInsert', 'MediaEmbed', '|'
                    , 'FindAndReplace', 'PageBreak', '|'
                    // , 'ImageUpload', 'SelectAll', 'SourceEditing', 'RemoveFormat', 'HtmlEmbed', '|'
                    // , 'SpecialCharacters', 'Subscript', 'Superscript', '|'
                    // , 'ShowBlocks', 'CodeBlock', 'TextPartLanguage' 
                ],
                heading: {
                    options: [
                        { model: 'paragraph', title: 'Paragraph', class: 'ck-heading_paragraph' },
                        { model: 'heading1', view: 'h1', title: 'Heading 1', class: 'ck-heading_heading1' },
                        { model: 'heading2', view: 'h2', title: 'Heading 2', class: 'ck-heading_heading2' }
                    ]
                }
                // ,removePlugins:[
                //     'Base64UploadAdapter'
                // ]
            }). then(newEditor => {
                ckeditor = newEditor;
            })
            .catch(erorr => {
                console.error(error)
            });
        };

        var registerEvent = function(){
            $("#btnSubmit").click(()=>{
                $("#boardInsertForm textarea[name='boardCntnt']").val(ckeditor.getData());
                var str = $("#boardInsertForm").serialize();
                console.log(str);
            })

            $("#uploadFiles").change((e) => {

                const files = $("#uploadFiles")[0].files
                var filesText = "";
                for(var i=0;i<files.length;i++){
                    // console.log(files[i].name);
                    filesText = filesText + "<span>" + files[i].name + "</span><br>";
                }
                $("#uploadResult").empty();
                $("#uploadResult").append(filesText);
            })
        };

        return {
            init: function(){
                init();
                registerEvent();
            }
        }
    }());

    $(document).ready(e=>{
        boardInsertPage.init();
    });

    function validate(){
        return false;
    }

    function validationForm(){
        const boardTitle = $('#boardInsertForm input[name="boardTitle"]').val(); // 제목
        if(boardTitle.trim() == "" || boardTitle == null){
            $("#modalTitle").append("제목이 없습니다.");
            $("#modalContent").append("제목은 필수 값 입니다.");
            $('#boardInsertForm input[name="boardTitle"]').focus();
            return false;
        }
        return true;
    }
</script>
</html>
