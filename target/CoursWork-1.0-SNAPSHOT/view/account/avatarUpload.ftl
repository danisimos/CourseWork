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
        <a class="navbar-brand" href="/account">

            <#if user.avatarId??>
                <img alt="" width="30" height="24" class="d-inline-block align-text-top" src="/files/${user.avatarId}"/>
            <#else>
                <img alt="" width="30" height="24" class="d-inline-block align-text-top" src="/resources/images/no-avatar.png"/>
            </#if>
            ${user.firstName} ${user.lastName}
        </a>



        <ul class="nav nav-tabs">
            <li class="nav-item">
                <a class="nav-link" aria-current="page" href="/account/myVacancy">Мои вакансии</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/account/myCV">Мои резюме</a>
            </li>

        </ul>

        <form class="d-flex" action="/account/updateAvatar" method="get">
            <button class="btn btn-outline-success" type="submit">Загрузить фото</button>
        </form>

        <form class="d-flex" action="/signOut" method="post">
            <button class="btn btn-outline-success" type="submit">Выйти</button>
        </form>
    </div>
    </div>
</nav>

<br>

<div class="container">

    <div class="row justify-content-center">
        <div class="col-5">
            <div class="mb-3">
                <form action="/account/updateAvatar" method="post" enctype="multipart/form-data">
                    <input class="form-control" type="file" id="formFile" name="file">
                    <br>
                    <button class="btn btn-lg btn-primary btn-block" type="submit">Отправить</button>
                </form>
            </div>
        </div>
    </div>

</div>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
</body>
</html>