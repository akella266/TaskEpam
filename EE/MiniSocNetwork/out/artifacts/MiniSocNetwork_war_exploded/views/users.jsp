<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Друзья</title>
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.8.3.min.js"></script>
    <script>
        function getFriends(){
            $.ajax({
                type:'GET',
                url:'/users',
                success:function (resp) {
                    $("#listFriends").empty();
                    for(var i = 0; i < resp.length; i++){
                        var li = document.createElement('li');
                        var div = document.createElement('div');
                        div.setAttribute('id', resp[i].id);
                        div.setAttribute('class', 'users');
                        div.setAttribute('title', resp[i].isFriend);
                        div.onclick = function (e) {
                            var idUser = $(this).attr('id');
                            var isFriend = $(this).attr('title');
                            $.ajax({
                                type:'POST',
                                url:'/userpage',
                                data:{
                                    userpageId:idUser,
                                    isFriend:isFriend
                                },
                                success:function (resp) {
                                    location.href = "userpage.jsp"
                                }
                            })
                        };
                        div.innerText = resp[i].firstName + " " + resp[i].secondName;
                        li.appendChild(div);
                        $("#listFriends").append(li);
                    }
                }
            })
        }

        $(document).ready(function () {
            getFriends();
            $("#mypage").click(function () {
                location.href = 'mypage.jsp'
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
            $("#friends").click(function () {
               getFriends()
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
