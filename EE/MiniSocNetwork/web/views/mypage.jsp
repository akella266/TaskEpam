<%--
  Created by IntelliJ IDEA.
  User: akella266
  Date: 14.04.2018
  Time: 14:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <style type="text/css">
        .mainContainer{
            background-color: aquamarine;
            opacity: 0.5;
            border: 1px black solid;
        }
        #avatar{
            background-color: aqua;
            float: left;
        }
    </style>
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.8.3.min.js"></script>
    <script>
        function getMyPage(){
            $.ajax({
                type: "GET",
                url:"/mypage",
                success: function (resp) {
                    document.title = "Моя страница";
                    $("#firstName").text(resp.firstName);
                    $("#secondName").text(resp.secondName);
                    $("#age").text("Возраст: " + resp.age);
                    $("#sex").text("Пол: " + (resp.sex ? "Мужской" : "Женский"))
                    $("#avatar").attr('src', resp.avatar);
                    $("#avatar").attr("id", resp.id)
                }
            })
        }
        function getMyGifts(){

        }
        $(document).ready(function () {
            getMyPage();
            $("#mypage").click(function () {
                getMyPage();
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
            })
            $("#updateAvatar").click(function () {
                var files = ($('#newAvatar'))[0].files;
                var data = new FormData();

                $.each( files, function( key, value ){
                    data.append( key, value );
                });

                $.ajax({
                    url         : '/avatarChange',
                    type        : 'POST',
                    data        : data,
                    cache       : false,
                    dataType    : 'json',
                    processData : false,
                    contentType : false,
                    success: function (resp) {
                        location.reload()
                    }
                })
            })
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
        <div class="mainContainer">
            <img id="avatar" width="250px" height="350px"/>
            <div class="info">
                <p id="firstName" class="fio"></p>
                <p id="secondName" class="fio"></p>
                <p id="age" class="otherInfo"></p>
                <p id="sex" class="otherInfo"></p>
            </div>
            <div class="changeAvatar">
                <input id="newAvatar" type="file" name="avatar"/>
                <button type="submit" id="updateAvatar">Сменить аватар</button>
            </div>
            <div class="gifts">

            </div>
        </div>
    </main>
</body>
</html>
