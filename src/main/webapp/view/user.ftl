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

        <ul class="nav nav-tabs">
            <li class="nav-item">
                <a class="nav-link active" aria-current="page" href="/vacancy">Вакансии</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/curriculumVitae">Резюме</a>
            </li>

        </ul>

        <form class="d-flex" action="/account" method="get">
            <button class="btn btn-outline-success" type="submit">Личный кабинет</button>
        </form>
    </div>
    </div>
</nav>

<br>

<div class="row justify-content-center">
        <div class="col-2">
            <#if userOnPage.avatarId??>
                <img alt="" width="30" height="24" class="d-inline-block align-text-top" src="/files/${userOnPage.avatarId}"/>
            <#else>
                <img alt="" width="30" height="24" class="d-inline-block align-text-top" src="/resources/images/no-avatar.png"/>
            </#if>
        </div>
        <div class="col-3">
            <h4 class="my-0 font-weight-normal">${userOnPage.firstName} ${userOnPage.lastName}</h4>
        </div>
</div>

<div class="container">
    <div class="row justify-content-center" id="post-list">
        <#list curriculumVitaeList as cv>

            <div class="col-8">
                <div class="card-deck mb-3 text-center">
                    <div class="card mb-4 box-shadow">

                        <div class="card-body">
                            <h3 class="card-title pricing-card-title">${cv.content}</h3>
                        </div>
                    </div>
                </div>
            </div>

            <br>
        </#list>
    </div>
</div>




<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
</body>
</html>