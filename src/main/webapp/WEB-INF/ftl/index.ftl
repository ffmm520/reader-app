<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>慕课书评网</title>
    <meta name="viewport" content="width=device-width,initial-scale=1.0, maximum-scale=1.0,user-scalable=no">
    <link rel="icon" href="https://www.imooc.com/static/img/common/touch-icon-iphone-retina.png"/>
    <link rel="stylesheet" href="/resources/bootstrap/bootstrap.css">
    <link rel="stylesheet" href="/resources/raty/lib/jquery.raty.css">
    <script src="/resources/jquery.3.3.1.min.js"></script>
    <script src="/resources/bootstrap/bootstrap.min.js"></script>
    <script src="/resources/art-template.js"></script>
    <script src="/resources/raty/lib/jquery.raty.js"></script>

    <style>
        .highlight {
            color: red !important;
        }

        a:active {
            text-decoration: none !important;
        }

        .container {
            padding: 0;
            margin: 0;
        }

        .row {
            padding: 0;
            margin: 0;
        }

        .col- * {
            padding: 0;
        }
    </style>

    <script type="text/html" id="tpl">
        <a href="/book/{{bookId}}" style="color: inherit">
            <div class="row mt-2 book">
                <div class="col-4 mb-2 pr-2">
                    <img class="img-fluid" src="{{cover}}"/>
                </div>
                <div class="col-8  mb-2 pl-0">
                    <h5 class="text-truncate">{{bookName}}</h5>
                    <div class="mb-2 bg-light small  p-2 w-100 text-truncate">{{author}}</div>
                    <div class="mb-2 w-100">{{subTitle}}</div>
                    <p>
                        <span class="stars" data-score="{{evaluationScore}}" title="gorgeous"></span>
                        <span class="mt-2 ml-2">{{evaluationScore}}</span>
                        <span class="mt-2 ml-2">{{evaluationQuantity}}</span>
                    </p>
                </div>
            </div>
            <hr/>
        </a>
    </script>
    <script>
        $.fn.raty.defaults.path = "/resources/raty/lib/images";

        //加载更多
        function loadMore(isReset) {
            // 如果是初次加载页面，将nextPage设置为1
            if (isReset) {
                // 初次加载页面清空页面，重新渲染
                $("#bookList").html("");
                $("#nextPage").val(1);
            }
            let nextPage = $("#nextPage").val();
            let categoryId = $("#categoryId").val();
            let order = $("#order").val();
            $.ajax({
                url: "/books",
                data: {p: nextPage, categoryId: categoryId, order: order},
                type: "get",
                dataType: "json",
                success: (json) => {
                    // console.log(json);
                    const list = json.records;
                    for (let i = 0; i < list.length; i++) {
                        const book = json.records[i];
                        const html = template("tpl", book);
                        $("#bookList").append(html);
                    }
                    // 星形评分组件
                    $(".stars").raty({readonly: true});
                    if (json.current < json.pages) {
                        //当前页码小于总页码
                        $("#nextPage").val(parseInt(json.current) + 1);
                        $("#btnMore").show();
                        $("#divNoMore").hide();
                    } else {
                        $("#btnMore").hide();
                        $("#divNoMore").show();
                    }
                }
            });
        }

        $(function () {
            loadMore(true);
            $("#btnMore").click(function () {
                loadMore(false);
            });

            // 类别高亮
            $(".category").click(function(){
                $(".category").removeClass("highlight").addClass("text-black-50");
                // $(".category").addClass("text-black-50");
                $(this).addClass("highlight");
                let categoryId = $(this).data("category");
                $("#categoryId").val(categoryId);
                loadMore(true);
            });
            // 排序高亮
            $(".order").click(function(){
                $(".order").removeClass("highlight").addClass("text-black-50");
                $(this).addClass("highlight");
                let order = $(this).data("order");
                $("#order").val(order);
                loadMore(true);
            });
        })

    </script>
</head>
<body>
<div class="container">
    <nav class="navbar navbar-light bg-white shadow mr-auto">
        <ul class="nav">
            <li class="nav-item">
                <a href="/">
                    <img src="https://m.imooc.com/static/wap/static/common/img/logo2.png" class="mt-1"
                         style="width: 100px">
                </a>
            </li>

        </ul>
        <#if loginMember??>
            <h6 class="mt-1">
                <img style="width: 2rem;margin-top: -5px" class="mr-1" src="/images/user_icon.png" alt=""/>${loginMember.nickName}
            </h6>
        <#else>
            <a href="/login.html" class="btn btn-light btn-sm">
                <img style="width: 2rem;margin-top: -5px" class="mr-1" src="/images/user_icon.png" alt="">登录
            </a>
        </#if>
    </nav>
    <div class="row mt-2">
        <div class="col-8 mt-2">
            <h4>热评好书推荐</h4>
        </div>
        <div class="col-8 mt-2">
            <span data-category="-1" style="cursor: pointer" class="highlight  font-weight-bold category">全部</span>
            <#list categoryList as category>
                |
                <a style="cursor: pointer" data-category="${category.categoryId}"
                   class="text-black-50 font-weight-bold category">${category.categoryName}
                </a>
            </#list>
        </div>
        <div class="col-8 mt-2">
            <span data-order="quantity" style="cursor: pointer"
                  class="order highlight  font-weight-bold mr-3">按热度</span>
            <span data-order="score" style="cursor: pointer"
                  class="order text-black-50 mr-3 font-weight-bold">按评分</span>
        </div>
    </div>
    <div class="d-none">
        <input type="hidden" id="nextPage" value="2">
        <input type="hidden" id="categoryId" value="-1">
        <input type="hidden" id="order" value="quantity">
    </div>

    <div id="bookList">

        <#--<a href="/book/5" style="color: inherit">-->
        <#--    <div class="row mt-2 book">-->
        <#--        <div class="col-4 mb-2 pr-2">-->
        <#--            <img class="img-fluid" src="https://img4.mukewang.com/5ce256ea00014bc903600480.jpg">-->
        <#--        </div>-->
        <#--        <div class="col-8  mb-2 pl-0">-->
        <#--            <h5 class="text-truncate">从 0 开始学爬虫</h5>-->

        <#--            <div class="mb-2 bg-light small  p-2 w-100 text-truncate">梁睿坤 · 19年资深架构师</div>-->


        <#--            <div class="mb-2 w-100">零基础开始到大规模爬虫实战</div>-->

        <#--            <p>-->
        <#--                <span class="stars" data-score="4.9" title="gorgeous"><img alt="1"-->
        <#--                                                                           src="/resources/raty/lib/images/star-on.png"-->
        <#--                                                                           title="gorgeous">&nbsp;<img alt="2"-->
        <#--                                                                                                       src="/resources/raty/lib/images/star-on.png"-->
        <#--                                                                                                       title="gorgeous">&nbsp;<img-->
        <#--                            alt="3" src="/resources/raty/lib/images/star-on.png" title="gorgeous">&nbsp;<img-->
        <#--                            alt="4" src="/resources/raty/lib/images/star-on.png" title="gorgeous">&nbsp;<img-->
        <#--                            alt="5" src="/resources/raty/lib/images/star-on.png" title="gorgeous"><input-->
        <#--                            name="score" type="hidden" value="4.9" readonly=""></span>-->
        <#--                <span class="mt-2 ml-2">4.9</span>-->
        <#--                <span class="mt-2 ml-2">15人已评</span>-->
        <#--            </p>-->
        <#--        </div>-->
        <#--    </div>-->
        <#--</a>-->

        <#--<hr>-->

    </div>
    <button type="button" id="btnMore" data-next-page="1" class="mb-5 btn btn-outline-primary btn-lg btn-block">
        点击加载更多...
    </button>
    <div id="divNoMore" class="text-center text-black-50 mb-5" style="display: none;">没有其他数据了</div>
</div>

</body>
</html>