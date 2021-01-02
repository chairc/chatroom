(function ($) {
    "use strict";
    $(window).on('load', function () {
        /*
       MixitUp
       ========================================================================== */
        $('#portfolio').mixItUp();
        /*
         One Page Navigation & wow js
         ========================================================================== */
        var OnePNav = $('.onepage-nev');
        var top_offset = OnePNav.height() - -0;
        OnePNav.onePageNav({
            currentClass: 'active',
            scrollOffset: top_offset,
        });
        /*Page Loader active
          ========================================================*/
        $('#preloader').fadeOut();
        // Sticky Nav
        $(window).on('scroll', function () {
            // if ($(window).scrollTop() > 200) {
            //     $('.scrolling-navbar').addClass('top-nav-collapse');
            // } else {
            //     $('.scrolling-navbar').removeClass('top-nav-collapse');
            // }
        });
        /* slicknav mobile menu active  */
        $('.mobile-menu').slicknav({
            prependTo: '.navbar-header',
            parentTag: 'liner',
            allowParentLinks: true,
            duplicate: true,
            label: '',
            closedSymbol: '<i class="icon-arrow-right"></i>',
            openedSymbol: '<i class="icon-arrow-down"></i>',
        });
        /* WOW Scroll Spy
      ========================================================*/
        var wow = new WOW({
            //disabled for mobile
            mobile: false
        });
        wow.init();
        /* Nivo Lightbox
        ========================================================*/
        $('.lightbox').nivoLightbox({
            effect: 'fadeScale',
            keyboardNav: true,
        });
        /* Counter
        ========================================================*/
        $('.counterUp').counterUp({
            delay: 10,
            time: 1000
        });
        /* Back Top Link active
        ========================================================*/
        var offset = 200;
        var duration = 500;
        $(window).scroll(function () {
            if ($(this).scrollTop() > offset) {
                $('.back-to-top').fadeIn(400);
            } else {
                $('.back-to-top').fadeOut(400);
            }
        });

        $('.back-to-top').on('click', function (event) {
            event.preventDefault();
            $('html, body').animate({
                scrollTop: 0
            }, 600);
            return false;
        });
    });
}(jQuery));

/*login.html*/
function login() {
    var account = $("#account").val().trim();
    var password = $("#password").val().trim();
    $.ajax({
        url: "/api/user/loginUserByAjax",
        dataType: "JSON",
        data: {
            "account": account, "password": password
        },
        contentType: "application/json; charset=utf-8",
        success: function (data) {
            console.log(data);
            if (data.code === "1") {
                $("#message-box-text").html(data.msg);
                $("#message-box").css("color", "#ffffff");
                $("#message-box").css("background", "#1a95ff");
                setTimeout(function () {
                    window.location.href = "/home";
                }, 2000)
            } else if (data.code === "0") {
                //登录失败，用户名或密码错误
                $("#message-box-text").html(data.msg);
                $("#message-box").css("color", "#a94442");
                $("#message-box").css("background", "#f2dede");
            } else if (data.code === "error") {
                //登录失败，用户名或密码错误
                $("#message-box-text").html(data.msg);
                $("#message-box").css("color", "#a94442");
                $("#message-box").css("background", "#f2dede");
            }
            setTimeout(function () {
                $('#message-box').slideUp(300);
            }, 1000);
            if ($("#message-box").is(":hidden")) {
                $('#message-box').slideDown(300);
            } else {
                $('#message-box').slideUp(300);
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert(XMLHttpRequest.status);
            alert(XMLHttpRequest.readyState);
            alert(textStatus);
            alert(errorThrown)
        }
    })
}

function submitUser() {
    var sid = $("#sid").val().trim();
    var username = $("#username").val().trim();
    var sex = $("#sex").val().trim();
    $.ajax({
        url: "/api/user/addUserByAjax",
        dataType: "JSON",
        data: {
            "sid": sid, "username": username,"sex":sex
        },
        contentType: "application/json; charset=utf-8",
        success: function (data) {
            console.log(data);
            if (data.code === "1") {
                $("#message-box-text").html(data.msg);
                $("#message-box").css("color", "#ffffff");
                $("#message-box").css("background", "#1a95ff");
            } else if (data.code === "0") {
                $("#message-box-text").html(data.msg);
                $("#message-box").css("color", "#a94442");
                $("#message-box").css("background", "#f2dede");
            } else if (data.code === "error") {
                $("#message-box-text").html(data.msg);
                $("#message-box").css("color", "#a94442");
                $("#message-box").css("background", "#f2dede");
            }
            setTimeout(function () {
                $('#message-box').slideUp(300);
            }, 1000);
            if ($("#message-box").is(":hidden")) {
                $('#message-box').slideDown(300);
            } else {
                $('#message-box').slideUp(300);
            }
        }
    })
}

function fileUpload() {
    var formData = new FormData();
    formData.append("file", $("#file")[0].files[0]);
    $.ajax({
        type: "post",
        url: "/api/file/upload",
        dataType: "JSON",
        data: formData,
        processData : false, // 使数据不做处理
        contentType : false, // 不要设置Content-Type请求头
        success: function (data) {
            console.log(data);
            if (data.code === "1") {
                $("#message-box-text").html(data.msg);
                $("#message-box").css("color", "#ffffff");
                $("#message-box").css("background", "#1a95ff");
            } else if (data.code === "0") {
                $("#message-box-text").html(data.msg);
                $("#message-box").css("color", "#a94442");
                $("#message-box").css("background", "#f2dede");
            } else if (data.code === "error") {
                $("#message-box-text").html(data.msg);
                $("#message-box").css("color", "#a94442");
                $("#message-box").css("background", "#f2dede");
            }
            setTimeout(function () {
                $('#message-box').slideUp(300);
            }, 1000);
            if ($("#message-box").is(":hidden")) {
                $('#message-box').slideDown(300);
            } else {
                $('#message-box').slideUp(300);
            }
        }
    })
}