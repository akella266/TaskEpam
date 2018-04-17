
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Авторизация</title>
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.8.3.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#send").click(function (event) {
                event.preventDefault();
                $.ajax({
                    type:'POST',
                    url: '/login',
                    data: {
                        login: $("#login").val(),
                        password: $("#password").val()
                    },
                    success: function(resp){
                        if (resp.valid){
                            location.href = "mypage.jsp"
                        }
                        else{
                            $("#error").text(resp.error)
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
            <input id="login" type="text" name="login" value="akella266" placeholder="Логин"/><br/>
            <input id="password" type="password" name="password" value="astar266" placeholder="Пароль"/><br/>
            <button id="send">Отправить</button><br/>
            <button id="reg" onclick='location.href="registration.jsp"'>Регистрация</button><br/>
        </div>
        <p id="error" style="color:red"></p>
    </main>
</body>
</html>
