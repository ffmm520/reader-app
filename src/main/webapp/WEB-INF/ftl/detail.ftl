<#setting number_format="#">
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${book.bookName}</title>
    <meta name="viewport" content="width=device-width,initial-scale=1.0, maximum-scale=1.0,user-scalable=no">
    <link rel="stylesheet" href="/resources/bootstrap/bootstrap.css">
    <link rel="stylesheet" href="/resources/raty/lib/jquery.raty.css">
    <script src="/resources/jquery.3.3.1.min.js"></script>
    <script src="/resources/bootstrap/bootstrap.min.js"></script>
    <script src="/resources/art-template.js"></script>
    <script src="/resources/raty/lib/jquery.raty.js"></script>
    <style>
        .container {
            padding: 0;
            margin: 0;
        }

        .row {
            padding: 0;
            margin: 0;
        }

        .alert {
            padding-left: 0.5rem;
            padding-right: 0.5rem;
        }

        .col- * {
            padding: 0;
        }

        .description p {
            text-indent: 2em;
        }

        .description img {
            width: 100%;
        }

        .highlight {
            background-color: #ff6700 !important;
        }

    </style>
    <script>
        $.fn.raty.defaults.path = '/resources/raty/lib/images';
        $(function () {
            $(".stars").raty({readOnly: true});
        });

        $(function () {
            // 如果有阅读状态，说明已登录
            <#if readState??>
            $("*[data-read-state='${readState.readState}']").addClass("highlight");
            </#if>

            <#-- 没有登录状态, -->
            <#if !loginMember??>
            <#-- 阅读状态、写评论、点赞按钮-->
            $("*[data-read-state], #btnEvaluation, *[data-evaluation-id]").click(function () {
                //未登录情况下提示"需要登录"
                $("#exampleModalCenter").modal("show");
            });
            </#if>

            <#-- 想看、看过 -->
            <#if loginMember??>
            <#-- 阅读状态、写评论、点赞按钮-->
            $("*[data-read-state]").click(function () {
                // 获取阅读状态
                let state = $(this).data("read-state");
                $.post("/update_read_state", {
                    memberId:${loginMember.memberId},
                    bookId:${book.bookId},
                    state: state
                }, function (json) {
                    if (json.code === "0") {
                        // 1.先移除全部data-read-state的高亮
                        $("*[data-read-state]").removeClass("highlight");
                        // 2.再给点击的按钮添加高亮
                        $("*[data-read-state='" + state + "']").addClass("highlight");
                    }
                }, "json");
            });

            <#-- 短评-->
            <#--打开对话框-->
            $("#btnEvaluation").click(function () {
                $("#dlgEvaluation").modal("show");
                $("#score").raty({});//转换为星型组件
            });
            // 提交数据
            $("#btnSubmit").click(function () {
                let score = $("#score").raty("score");
                let content = $("#content").val();
                if (score == 0 || $.trim(content) == "") {
                    return;
                }
                $.post("/evaluate", {
                    bookId: ${book.bookId},
                    memberId: ${loginMember.memberId},
                    score: score,
                    content: content
                }, function (json) {
                    if (json.code == "0") {
                        window.location.reload();
                    }
                }, "json");
            });
            //评论点赞
                $("*[data-evaluation-id]").click(function () {
                    let evaluationId = $(this).data("evaluation-id");
                    $.post("/enjoy", {
                        evaluationId: evaluationId
                    }, function(json){
                        if (json.code == "0"){
                            $("*[data-evaluation-id='" + evaluationId + "'] span").text(json.evaluation);
                        }
                    }, "json");
                });
            </#if>

        });
    </script>
</head>
<body>
<!--<div style="width: 375px;margin-left: auto;margin-right: auto;">-->
<div class="container ">
    <nav class="navbar navbar-light bg-white shadow mr-auto">
        <ul class="nav">
            <li class="nav-item">
                <a href="/">
                    <img src="https://m.imooc.com/static/wap/static/common/img/logo2.png" class="mt-1"
                         style="width: 100px">
                </a>
            </li>

        </ul>
    </nav>
    <div class="container mt-2 p-2 m-0" style="background-color:rgb(127, 125, 121)">
        <div class="row">
            <div class="col-4 mb-2 pl-0 pr-0">
                <img style="width: 110px;height: 160px"
                     src="${book.cover}">
            </div>
            <div class="col-8 pt-2 mb-2 pl-0">
                <h6 class="text-white">${book.bookName}</h6>
                <div class="p-1 alert alert-warning small" role="alert">
                    ${book.subTitle}
                </div>
                <p class="mb-1">
                    <span class="text-white-50 small">${book.author}</span>
                </p>
                <div class="row pl-1 pr-2">
                    <div class="col-6 p-1">
                        <button type="button" data-read-state="1" class="btn btn-light btn-sm w-100">
                            <img style="width: 1rem;" class="mr-1"
                                 src="https://img3.doubanio.com/f/talion/cf2ab22e9cbc28a2c43de53e39fce7fbc93131d1/pics/card/ic_mark_todo_s.png"/>想看
                        </button>
                    </div>
                    <div class="col-6 p-1">
                        <button type="button" data-read-state="2" class="btn btn-light btn-sm  w-100">
                            <img style="width: 1rem;" class="mr-1"
                                 src="https://img3.doubanio.com/f/talion/78fc5f5f93ba22451fd7ab36836006cb9cc476ea/pics/card/ic_mark_done_s.png"/>看过
                        </button>
                    </div>
                </div>
            </div>
        </div>
        <div class="row" style="background-color: rgba(0,0,0,0.1);">
            <div class="col-2"><h2 class="text-white">${book.evaluationScore}</h2></div>
            <div class="col-5 pt-2">
                <span class="stars" data-score="${book.evaluationScore}"></span>
            </div>
            <div class="col-5  pt-2"><h5 class="text-white">${book.evaluationQuantity}</h5></div>
        </div>
    </div>
    <div class="row p-2 description">
        ${book.description}
    </div>

    <div class="alert alert-primary w-100 mt-2" role="alert">短评
        <button type="button" id="btnEvaluation" class="btn btn-success btn-sm text-white float-right"
                style="margin-top: -3px;">
            写短评
        </button>
    </div>
    <div class="reply pl-2 pr-2">
        <#list evaluationList as evaluation>
            <div>
                <div>
                    <span class="pt-1 small text-black-50 mr-2">${evaluation.createTime?string("yy-MM")}</span>
                    <span class="mr-2 small pt-1">${evaluation.member.nickName}</span>
                    <span class="stars mr-2" data-score="${evaluation.score}"></span>
                    <button type="button" data-evaluation-id="${evaluation.evaluationId}"
                            class="btn btn-success btn-sm text-white float-right" style="margin-top: -3px;">
                        <img style="width: 24px;margin-top: -5px;" class="mr-1"
                             src="https://img3.doubanio.com/f/talion/7a0756b3b6e67b59ea88653bc0cfa14f61ff219d/pics/card/ic_like_gray.svg"/>
                        <span>${evaluation.enjoy}</span>
                    </button>
                </div>
                <div class="row mt-2 small mb-3">
                    ${evaluation.content}
                </div>
                <hr/>
            </div>
        </#list>
    </div>
</div>


<!-- 提示登录Modal -->
<div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-body">
                您需要登录才可以操作哦~
            </div>
            <div class="modal-footer">
                <a href="/login.html?url=/book/${book.bookId}" type="button" class="btn btn-primary">去登录</a>
            </div>
        </div>
    </div>
</div>

<!-- 短评 Modal -->
<div class="modal fade" id="dlgEvaluation" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-body">
                <h6>${book.bookName}</h6>
                <form id="frmEvaluation">
                    <div class="input-group  mt-2 ">
                        <span id="score"></span>
                    </div>
                    <div class="input-group  mt-2 ">
                        <input type="text" id="content" name="content" class="form-control p-4" placeholder="这里输入短评">
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" id="btnSubmit" class="btn btn-primary">提交</button>
            </div>
        </div>
    </div>
</div>

</body>
</html>