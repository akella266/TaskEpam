<%--
  Created by IntelliJ IDEA.
  User: akella266
  Date: 14.04.2018
  Time: 0:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Регистрация</title>
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.8.3.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#send").click(function (event) {
                event.preventDefault();
                $.ajax({
                    type:'POST',
                    url: '/reg',
                    data: {
                        firstName: $("#firstName").val(),
                        secondName: $("#secondName").val(),
                        age: $("#age").val(),
                        sex: $("input[name=sex]:checked").val(),
                        avatar: $("#avatar").val(),
                        login: $("#login").val(),
                        password: $("#password").val()
                    },
                    dataType : 'json',
                    success: function(resp){
                        if (!resp.valid) {
                            $("#error").text(resp.error)
                        }
                        else{
                            alert("рег аватар")
                            var files = ($('#avatar'))[0].files;
                            var data = new FormData();

                            $.each( files, function( key, value ){
                                data.append( key, value );
                            });

                            $.ajax({
                                url         : '/avatarUpload',
                                type        : 'POST',
                                data        : data,
                                cache       : false,
                                dataType    : 'json',
                                processData : false,
                                contentType : false,
                                success: function (resp) {
                                    location.href = "login.jsp";
                                }
                            });
                        }
                    }
                });


            })
        })
    </script>
</head>
<body>
    <header>

    </header>
    <main>
        <div>
            <input id="firstName" type="text" name="firstName" placeholder="Имя"/><br/>
            <input id="secondName" type="text" name="secondName" placeholder="Фамилия"/><br/>
            <label for="age">Возраст</label><br/>
            <input id="age" type="number" name="age"/><br/>
            <label for="sexM">Пол</label><br/>
            <input id="sexM" type="radio" name="sex" value="true" checked/>Мужской<br/>
            <input id="sexF" type="radio" name="sex" value="false"/>Женский<br/>
            <input id="avatar" type="file" name="avatar" /><br/>
            <input id="login" type="text" name="login" placeholder="Логин (минимум 4 символа)"/><br/>
            <input id="password" type="password" name="password" placeholder="Пароль (минимум 6 символов)"/><br/>
            <button id="send">Зарегистрироваться</button><br/>
            <button id="reg">Отмена</button>
        </div>
        <p id="error" style="color:red"></p>
    </main>
</body>
</html>
