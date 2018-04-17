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
            width: 200px;
            height: 300px;
            background-color: aqua;
            float: left;
        }

        .modal {
            display: none; /* Hidden by default */
            position: fixed; /* Stay in place */
            z-index: 1; /* Sit on top */
            left: 0;
            top: 0;
            width: 100%; /* Full width */
            height: 100%; /* Full height */
            overflow: auto; /* Enable scroll if needed */
            background-color: rgb(0,0,0); /* Fallback color */
            background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
        }
        .modal-content {
            background-color: #fefefe;
            margin: 15% auto; /* 15% from the top and centered */
            padding: 20px;
            border: 1px solid #888;
            width: 80%; /* Could be more or less, depending on screen size */
        }
    </style>
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.8.3.min.js"></script>
    <script>
        $(document).ready(function () {
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
            $(".modal").hide();
            $("#sendMessage").click(sendMessage);
            $("#cancelMessage").click(closeDialog);
            function getInfo() {
                $.ajax({
                    type:'GET',
                    url:'/userpage',
                    success:function (resp) {
                        document.title = resp.firstName + " " + resp.secondName;
                        $("#firstName").text(resp.firstName);
                        $("#secondName").text(resp.secondName);
                        $("#age").text("Возраст: " + resp.age);
                        $("#sex").text("Пол: " + (resp.sex ? "Мужской" : "Женский"));
                        $("#avatar").attr('src', resp.avatar);
                        if (resp.isFriend === true) {
                            $("#isFriend").text("Удалить из друзей");
                            $("#isFriend").attr("value", 'true')
                            var button = document.createElement("button");
                            button.innerText = "Написать сообщение";
                            button.onclick = function(){
                                openDialog()
                            };
                            $(".mainContainer").append(button);
                        }
                        else {
                            $("#isFriend").attr("value", 'false')
                        }
                        $("#isFriend").attr('class', resp.id)
                    }
                })
            }
            function openDialog(){
                $("#message").fadeIn();
            }
            function closeDialog(){
                $("#message").fadeOut();
            }
            function sendMessage(){
                var message = $("#sendingMessage").val()
                $.ajax({
                    type:'POST',
                    url:'/managingMessage',
                    data:{
                        message:message,
                        method:"send"
                    },
                    success:function(resp){
                        if (resp === "false"){
                            alert('Ошибка при отправке сообщения. Попробуйте позже')
                        }
                        else{
                            closeDialog()
                        }
                    }
                })
            }
            getInfo()
            $("#isFriend").click(function () {
                var userId = $(this).attr('class');
                var typeOper = $(this).attr('value');
                $.ajax({
                    type: 'POST',
                    url: '/managingFriends',
                    data: {
                        userId: userId,
                        isFriend: typeOper
                    },
                    success: function (resp) {
                        location.reload()
                    }
                })
            })
        });
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
            <img id="avatar"/>
            <div class="info">
                <p id="firstName" class="fio"></p>
                <p id="secondName" class="fio"></p>
                <p id="age" class="otherInfo"></p>
                <p id="sex" class="otherInfo"></p>
            </div>
            <button id="managePhoto"></button>
            <button id="isFriend">Добавить в друзья</button>
            <button id="sendingGift">Подарить подарок</button>
        <div class="gifts">

        </div>
        </div>
        <div class="modal" id="message">
            <div class="modal-content">
                <div class="content">
                    <input id="sendingMessage" type="text">
                </div>
                <div>
                    <button id="sendMessage">Отправить</button>
                    <button id="cancelMessage">Отмена</button>
                </div>
            </div>
        </div>
    </main>
    <footer>
    </footer>
</body>
</html>
