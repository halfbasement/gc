var member = {
    init : function (e) {
        var _this = this;

        $('#header-title').on('click',function (){
            window.location.href=('/')
        })

        $('#update-btn').on('click',function (){
            _this.update();
        })

        $('#delete-btn').on('click',function (){
            _this.delete();
        })
        $('#create-btn').on('click',function (){
            _this.create();
        })

        $('#login-btn').on('click',function (){
            _this.login();
        })
    },


    create : function () {
        var data={
            memberId:$('#create-id').val(),
            memberPwd:$('#create-password').val(),
            memberName:$('#create-name').val(),
            memberEmail:$('#create-email').val(),

        }


        if(data.memberId === "" ||data.memberPwd === "" || data.memberName=== "" ||data.memberEmail === "" ){
            alert("회원 정보를 모두 입력해주세요")
        }else {
            $.ajax({
                type: 'POST',
                url: '/member',
                // dataType: 'json',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(data)
            }).done(function () {
                alert('저장 완료');
                window.location.href = '/';
            }).fail(function (e) {
                alert("아이디가 중복됐습니다.")
            });
        }
    },

    delete : function (){
        var data={
            memberId: $('#update-id').val(),
        }

        $.ajax({
            type: 'DELETE',
            url: '/member',
            // dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('삭제 완료');
            window.location.href = '/member';
        }).fail(function (e) {
            alert(JSON.stringify(e))
        });
    },


    update : function () {
    var data = {
        memberId: $('#update-id').val(),
        memberPwd: $('#update-password').val(),
        memberName: $('#update-name').val(),
        memberEmail: $('#update-email').val(),
    }
    console.log(data)

        if(data.memberId === "" ||data.memberPwd === "" || data.memberName=== "" ||data.memberEmail === "" ){
            alert("회원 정보를 모두 입력해주세요")
        }else {
            $.ajax({
                type: 'PUT',
                url: '/member',
                // dataType: 'json',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(data)
            }).done(function () {
                alert('수정 완료');
                window.location.href = '/member';
            }).fail(function (e) {
                alert(JSON.stringify(e))
            });
        }
},
    login : function (){
        var data={
            memberId:$('#login-id').val(),
            memberPwd:$('#login-password').val(),
        }


        if(data.memberId === "" ||data.memberPwd === ""  ){
            alert("회원 정보를 모두 입력해주세요")
        }else {
            $.ajax({
                type: 'POST',
                url: '/member/login',
                // dataType: 'json',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(data)
            }).done(function () {
                window.location.href = '/';
            }).fail(function (e) {
                alert("아이디 혹은 비밀번호가 다릅니다.")
                console.log(data)
            });
        }
    }

};
member.init()