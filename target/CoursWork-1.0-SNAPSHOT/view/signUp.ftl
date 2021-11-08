<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>HeadHunter</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
</head>
<body>
<nav class="navbar navbar-light bg-light">
    <div class="container-fluid">
        <a class="navbar-brand" href="/">HeadHunter</a>
    </div>
</nav>

<br>

<div class="container">

    <div class="row justify-content-center">
        <div class="col-5">
            <div class="text-center">
                <form class="form-signin" method="post" action="/signUp">
                    <h1 class="h3 mb-3 font-weight-normal">Регистрация</h1>
                    <input type="text" name="firstName" class="form-control" placeholder="Имя" required autofocus>
                    <br>
                    <input type="text" name="lastName" class="form-control" placeholder="Фамилия" required autofocus>
                    <br>
                    <input type="email" name="email" id="inputEmail" class="form-control" placeholder="Email address" required autofocus>
                    <br>
                    <input type="password" name="password" id="inputPassword" class="form-control" placeholder="Password" required>
                    <br>
                    <button class="btn btn-lg btn-primary btn-block" type="submit">Sign up</button>
                </form>

                <br>
                <a href="/signIn" class="text">Войти</a>
            </div>
        </div>
    </div>

</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
</body>
</html>