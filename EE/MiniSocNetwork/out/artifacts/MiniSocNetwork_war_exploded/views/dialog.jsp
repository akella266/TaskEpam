<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Диалог</title>
    <style>
        .content{
            overflow:auto;
            width:400px;
            height: 500px;
            padding:5px;
            border: 1px skyblue solid;
        }
    </style>
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.8.3.min.js"></script>
    <script>

        function deleteMessages(messageId){
            $.ajax({
                type:'POST',
                url:'/managingMessage',
                data:{
                    messageId:messageId,
                    method:"del"
                },
                success:function (resp) {
                    if(resp === 'true'){
                        getMessages()
                    }
                    else{
                        alert("Ошибка при удалении сообщение. Попробуйте позже")
                    }
                }
            })
        }

        function getMessages(){
            $.ajax({
                type:"GET",
                url:'/dialog',
                success: function(resp){
                    $(".content").empty();
                    for(var i = 0 ; i < resp.length; i++) {
                        var divMain = document.createElement('div');
                        divMain.setAttribute('class', 'message');
                        var inner = document.createElement('div');
                        inner.setAttribute('id', resp[i].id);
                        inner.setAttribute('class', 'textMessage');
                        switch (resp[i].type){
                            case "file":{
                                inner.innerText = resp[i].time + ":" + resp[i].sender.firstName + ":";
                                var a = document.createElement('a');
                                a.setAttribute("href", resp[i].file.path);
                                a.setAttribute('id', resp[i].id);
                                a.setAttribute('download', resp[i].message);
                                a.innerText = resp[i].message;
                                a.onclick = function () {
                                    $.ajax({
                                        type:'GET',
                                        url:'/fileDownload',
                                        data:{
                                            fileName:$(this).attr('download')
                                        }
                                    })
                                };
                                inner.appendChild(a);
                            }
                            break;
                            default:{
                                inner.innerText = resp[i].time + ":" + resp[i].sender.firstName + ":" + resp[i].message;
                            }
                        }
                        inner.onclick = function(e){
                            if (e.target === this) {
                                if (confirm("Удалить выбранное сообщение")) {
                                    deleteMessages($(this).attr('id'));
                                }
                            }
                        };
                        divMain.append(inner);

                        $(".content").append(divMain);
                    }
                }
            })
        }

        $(document).ready(function (){
            getMessages();
            $("#mypage").click(function () {
                location.href = 'mypage.jsp'
            });
            $("#friends").click(function () {
                $.ajax({
                    type:'POST',
                    url:'/users',
                    data:{
                        findPeople: false
                    },
                    success:function () {
                        location.href = "users.jsp"
                    }
                })
            });
            $("#find").click(function (){
                $.ajax({
                    type:'POST',
                    url:'/users',
                    data:{
                        findPeople: true
                    },
                    success:function () {
                        location.href = "users.jsp"
                    }
                })
            });
            $("#mymessages").click(function (){
                location.href = 'usersMessages.jsp'
            });
            $("#logout").click(function(){
                $.ajax({
                    type:'POST',
                    url:'/logout',
                    success: function(){
                        location.href = 'login.jsp'
                    }
                })
            })
            $("#send").click(function () {
                var message = $("#sendingMessage").val()
                if ($("#sendFile").val().length !== 0){
                    var files = ($('#sendFile'))[0].files;
                    var data = new FormData();

                    $.each( files, function( key, value ){
                        alert(key + " " + value)
                        data.append( key, value );
                    });

                    $.ajax({
                        url         : '/fileupload',
                        type        : 'POST',
                        data        : data,
                        cache       : false,
                        dataType    : 'json',
                        processData : false,
                        contentType : false,
                        success: function (resp) {
                            if (resp === "false") {
                                alert('Ошибка при отправке сообщения. Попробуйте позже')
                            }
                            else {
                                getMessages()
                            }
                        }
                    })
                }
                else if (message.length !== 0 ) {
                    $.ajax({
                        type: 'POST',
                        url: '/managingMessage',
                        data: {
                            message: message,
                            method: "send"
                        },
                        success: function (resp) {
                            if (resp === "false") {
                                alert('Ошибка при отправке сообщения. Попробуйте позже')
                            }
                            else {
                                getMessages()
                            }
                        }
                    })
                }
            })
        })
    </script>
</head>
<body>
    <header>
        <button id="mypage">Моя страница</button>
        <button id="mymessages">Мои сообщения</button>
        <button id="friends">Друзья</button>
        <button id="find">Найти друзей</button>
        <button id="logout">Выйти</button>
    </header>
    <main>
        <div class="chat">
            <div class="content">

            </div>
            <div>
                <input id="sendingMessage" type="text"/>
                <input id="sendFile" type="file" name="file">
                <button type="submit" id="send">Отправить</button>
            </div>
        </div>
    </main>
</body>
</html>
