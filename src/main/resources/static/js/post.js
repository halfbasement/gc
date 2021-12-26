var post = {
    init: function (e) {
        var _this = this;

        $('#delete-post-btn').on('click', function () {
            _this.delete();
        })

        $('#update-post-btn').on('click', function () {

            _this.update();
        })

        $('#upload-input').on('change',function (){
            _this.fileUpload();
        })

        $('.uploadResult').on('click','li button',function (e){

            var targetFile = $(this).data("file");
            var targetLi = $(this).closest("li");

            $.ajax({
                url:'/removeFile',
                data:{fileName:targetFile},
                dataType: 'text',
                type:'POST',
            }).done(function (res){
                alert(res)
                targetLi.remove();
            })
        })

        $('#post-create-btn').on('click',function (e){
            e.preventDefault();

            var str="";

            $(".uploadResult li").each(function (i,obj){
                var target = $(obj);

                console.log(target.data('name'))

                //공백 뒤 문자 잘림
                str += `<input type=hidden name=fileList[${i}].fileName value=${target.data('name')}>`;
                str += `<input type=hidden name=fileList[${i}].folderPath value=${target.data('path')}>`;
                str += `<input type=hidden name=fileList[${i}].uuid value=${target.data('uuid')}>`;
            })

            $(".box").html(str);

            $('form').submit();
        })

    },



    fileUpload : function (){

        var formData = new FormData();

        var inputFile = $("#upload-input");

        var files = inputFile[0].files;

        for(let i=0; i<files.length; i++){
            console.log(files[i])
            formData.append("uploadFiles",files[i])
        }

        function show(arr){
            var area = $(".uploadResult ul");

            var str ="";

            $(arr).each(function (i,obj){

                console.log(obj)
                str+= `<li data-name=${obj.fileName} data-path=${obj.folderPath} data-uuid=${obj.uuid}>`
                str+= `<div>`
                str+= `<button type=button data-file=${obj.imageURL}>삭제</button>`
                str+= `<img src=/display?fileName=${obj.thumbnailURL}>`
                str+= `</div>`
                str+= `</li>`
            });
            area.append(str);
        }

        $.ajax({
            url:'/uploadAjax',
            processData: false,
            contentType: false,
            data:formData,
            type:'POST',
            dataType:'json',
        }).done(function (res){
            console.log(res)
            show(res)
        }).fail(function (e){
            alert(e)
        })
    }    ,


   /* create: function () {



        var data = {
            postTitle: $('#post-create-title').val(),
            postContent: $('#post-create-content').val(),
            fileList:{}
        }


        if (data.postTitle === "" || data.postContent === "") {
            alert("제목과 내용을 입력해주세요")
        } else {
            $.ajax({
                type: 'POST',
                url: '/post',
                // dataType: 'json',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(data)
            }).done(function () {
                alert('작성 완료');
                window.location.href = '/post';
            }).fail(function (e) {
                alert(e)
            });
        }
    },
*/
    update: function () {
        var data = {
            postNo: $('#update-post-no').val(),
            postTitle: $('#update-post-title').val(),
            postContent: $('#update-post-content').val()
        }

        console.log(data)
        $.ajax({
            type: 'PUT',
            url: '/post/' + data.postNo,
            // dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert("수정 되었습니다")
            window.location.href = '/post';

        }).fail(function (e) {
            alert(JSON.stringify(e))
        });
    },


    delete: function () { //넘어갈 값이 없어서 id값으로만
        var id = $('#detail-post-no').val();

        $.ajax({
            type: 'DELETE',
            url: '/post/' + id,
            // dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(id)
        }).done(function () {
            alert("삭제 되었습니디") //일로 오기전에 요청페이지를 들어가서 done이 되기 때문에 현재 오류 뜸
            window.location.href = '/post';

        }).fail(function (e) {
            alert(JSON.stringify(e))
        });
    },


};
post.init()