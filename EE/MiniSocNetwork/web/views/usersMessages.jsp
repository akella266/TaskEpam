<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Диалоги</title>
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.8.3.min.js"></script>
    <script>
        $(document).ready(function (){
            $.ajax({
                type:"GET",
                url:"/usersMessages",
                success:function (resp) {
                    for(var i = 0; i < resp.length; i++) {
                        var li = document.createElement("li");
                        var div = document.createElement('div');
                        div.setAttribute('id', resp[i].id);
                        div.innerText = resp[i].firstName + " " + resp[i].secondName;
                        div.onclick = function () {
                            var friendId = $(this).attr("id")
                            $.ajax({
                                type:'POST',
                                url:"/dialog",
                                data:{
                                    friendId: friendId
                                },
                                success:function(resp){
                                    location.href = 'dialog.jsp'
                                }
                            })
                        };
                        li.appendChild(div);
                        $("#listFriends").append(li)
                    }
                }
            })
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
        <ul id="listFriends">

        </ul>
    </main>
</body>
</html>
