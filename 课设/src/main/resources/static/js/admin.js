//展示个人主页
function showHome() {
    $("#home").css("display", "block");
    $("#showData").css("display", "none");
}

//查找单一用户
function selectUser() {
    $("#home").css("display", "none");
    $("#showData").css("display", "block");
    var userId = $("#userId").val().trim();
    $.ajax({
        url: "/api/user/showUser",
        dataType: "JSON",
        data: {
            "sid": userId
        },
        success: function (data) {
            console.log(data);
            if (data.code === "1") {
                $("#showSelectData").html(
                    '<div class="education wow fadeInRight animated" data-wow-delay="0.3s"' +
                    'style="visibility: visible;-webkit-animation-delay: 0.3s; -moz-animation-delay: 0.3s; animation-delay: 0.3s;">' +
                    '<table cellpadding="0" cellspacing="0" style="width: 100%;table-layout: fixed;border: #000000 solid 1px;' +
                    'text-align: center;word-break:break-all;" >' +
                    '<tr class="border" style="text-align: center;">' +
                    '<td>流水号</td>' +
                    '<td>学号号</td>' +
                    '<td>姓名</td>' +
                    '<td>密码</td>' +
                    '<td>性别</td>' +
                    '<td>IP</td>' +
                    '<td>浏览器</td>' +
                    '<td>权重</td>' +
                    '<td>更多</td>' +
                    '</tr>' +
                    '<tr class="border admin-table">' +
                    '<td title="' + data.data["uid"] + '">' + data.data["uid"] + '</td>' +
                    '<td title="' + data.data["sid"] + '">' + data.data["sid"] + '</td>' +
                    '<td title="' + data.data["username"] + '">' + data.data["username"] + '</td>' +
                    '<td title="' + data.data["password"] + '">' + data.data["password"] + '</td>' +
                    '<td title="' + data.data["sex"] + '">' + data.data["sex"] + '</td>' +
                    '<td title="' + data.data["ip"] + '">' + data.data["ip"] + '</td>' +
                    '<td title="' + data.data["browser"] + '">' + data.data["browser"] + '</td>' +
                    '<td title="' + data.data["weight"] + '">' + data.data["weight"] + '</td>' +
                    '<td><button class="btn btn-danger" style="height: 100%;width: 50%;text-align: center;padding: 10%;"' +
                    'onclick="deleteUser(this.value)" value="' + data.data["uid"] + '" ">' +
                    '删除</button><button class="btn btn-info" style="height: 100%;width: 50%;text-align: center;padding: 10%;"' +
                    ' id="more-info-click" onclick="moreUserInfoClick(this.value)" value="' + data.data["uid"] + '" ">' +
                    '详情</button></td>' +
                    '</tr>'
                );
            } else {
                $("#message-box-text").html(data.msg);
                $("#message-box").css("color", "#a94442");
                $("#message-box").css("background", "#f2dede");
                setTimeout(function () {
                    $('#message-box').slideUp(300);
                }, 1000);
                if ($("#message-box").is(":hidden")) {
                    $('#message-box').slideDown(300);
                } else {
                    $('#message-box').slideUp(300);
                }
            }
            ;
        },
    })
}

//遍历输出用户表
function selectUserList(value) {
    $("#home").css("display", "none");
    $("#showData").css("display", "block");
    $.ajax({
        url: "/api/user/showUserList",
        dataType: "JSON",
        data: {
            "pageNum": value
        },
        success: function (data) {
            console.log(data);
            if (value > data.data.pageNum["total"]) {
                warningLastPage();
            } else if (value === 0) {
                warningFirstPage();
            } else {
                var html =
                    '<div class="education wow fadeInRight animated" data-wow-delay="0.3s"' +
                    'style="visibility: visible;-webkit-animation-delay: 0.3s; -moz-animation-delay: 0.3s; animation-delay: 0.3s;">' +
                    '<table cellpadding="0" cellspacing="0" style="width: 100%;table-layout: fixed;border: #000000 solid 1px;' +
                    'text-align: center;word-break:break-all;" >' +
                    '<tr class="border" style="text-align: center;">' +
                    '<td>流水号</td>' +
                    '<td>学号号</td>' +
                    '<td>姓名</td>' +
                    '<td>密码</td>' +
                    '<td>性别</td>' +
                    '<td>IP</td>' +
                    '<td>浏览器</td>' +
                    '<td>权重</td>' +
                    '<td>更多</td>' +
                    '</tr>';
                for (var i = 0; i < 10; i++) {
                    try {
                        html += '<tr class="border admin-table">' +
                            '<td title="' + data.data.data[i]["uid"] + '">' + data.data.data[i]["uid"] + '</td>' +
                            '<td title="' + data.data.data[i]["sid"] + '">' + data.data.data[i]["sid"] + '</td>' +
                            '<td title="' + data.data.data[i]["username"] + '">' + data.data.data[i]["username"] + '</td>' +
                            '<td title="' + data.data.data[i]["password"] + '">' + data.data.data[i]["password"] + '</td>' +
                            '<td title="' + data.data.data[i]["sex"] + '">' + data.data.data[i]["sex"] + '</td>' +
                            '<td title="' + data.data.data[i]["ip"] + '">' + data.data.data[i]["ip"] + '</td>' +
                            '<td title="' + data.data.data[i]["browser"] + '">' + data.data.data[i]["browser"] + '</td>' +
                            '<td title="' + data.data.data[i]["weight"] + '">' + data.data.data[i]["weight"] + '</td>' +
                            '<td><button class="btn btn-danger" style="height: 100%;width: 50%;text-align: center;padding: 10%;"' +
                            'onclick="deleteUser(this.value)" value="' + data.data["uid"] + '" ">' +
                            '删除</button><button class="btn btn-info" style="height: 100%;width: 50%;text-align: center;padding: 10%;"' +
                            ' id="more-info-click" onclick="moreUserInfoClick(this.value)" value="' + data.data["uid"] + '" ">' +
                            '详情</button></td>' +
                            '</tr>';
                    } catch (e) {
                        //异常处理，如果遍历不够十次则中断遍历直接输出
                        break;
                    }
                }
                html += '</table>' +
                    '</div>' +
                    '<div class="btn-container">' +
                    '<div class="btn-prev-or-next">' +
                    '<button class="btn btn-primary" id="userPrev"' +
                    'onclick="selectUserList(' + (data.data.pageNum["page"] - 1) + ')">上一页</button>' +
                    '</div>' +
                    '<div class="btn-prev-or-next">' +
                    '<button class="btn btn-primary" id="userNext"' +
                    'onclick="selectUserList(' + (data.data.pageNum["page"] + 1) + ')">下一页</button>' +
                    '</div>' +
                    '<div class="btn-prev-or-next">' +
                    '<p>当前页：</p>' +
                    '<p id="userPageNow">' + data.data.pageNum["page"] + '</p>' +
                    '<p>/总页数：</p>' +
                    '<p id="userPageTotal">' + data.data.pageNum["total"] + '</p>' +
                    '</div>' +
                    '</div>';
                $("#showData").html(html);
            }
        },
    })
}

//展示查询数据页面
function showSelectUser() {
    $("#home").css("display", "none");
    $("#showData").css("display", "block");
    var html =
        '<div class="education wow fadeInRight animated" data-wow-delay="0.3s"' +
        'style="visibility: visible;-webkit-animation-delay: 0.3s; -moz-animation-delay: 0.3s; animation-delay: 0.3s;">' +
        '<div style="width: 100%">' +
        '<input type="text" id="userId"/>' +
        '<button class="btn btn-info" id="btn_change" onclick="selectUser()">查询数据</button>' +
        '<div id="p_test" style="word-break:break-all;">点击可以显示json数据！</div>' +
        '<div style="width: 100%">' +
        '<div class="education wow fadeInRight animated" data-wow-delay="0.3s"\n' +
        'style="visibility: visible;-webkit-animation-delay: 0.3s; -moz-animation-delay: 0.3s; animation-delay: 0.3s;">' +
        '<ul class="timeline">' +
        '<li>' +
        '<i class="icon-graduation"></i>' +
        '<h2 class="timelin-title">用户信息</h2>' +
        '</li>' +
        '<div id="showSelectData">' +
        '</div>\n' +
        '</ul>' +
        '</div>' +
        '</div>' +
        '</div>\n' +
        '</div>'
    $("#showData").html(html);

}

//小于第一页警告
function warningFirstPage() {
    $("#message-box-text").html("已经是第一页了！")
    $("#message-box").css("color", "#a94442");
    $("#message-box").css("background", "#f2dede");
    setTimeout(function () {
        $('#message-box').slideUp(300);
    }, 1000);
    if ($("#message-box").is(":hidden")) {
        $('#message-box').slideDown(300);
    } else {
        $('#message-box').slideUp(300);
    }
}

//大于最后一页警告
function warningLastPage() {
    $("#message-box-text").html("已经是最后一页了！")
    $("#message-box").css("color", "#a94442");
    $("#message-box").css("background", "#f2dede");
    setTimeout(function () {
        $('#message-box').slideUp(300);
    }, 1000);
    if ($("#message-box").is(":hidden")) {
        $('#message-box').slideDown(300);
    } else {
        $('#message-box').slideUp(300);
    }
}

function closeMoreInfoDialog() {
    $("#more-info-dialog").css("display", "none");
    $("#more-info-dialog-background").css("display", "none");
    $("#target").css("overflow", "auto");
}

function moreUserInfoClick(value) {
    $("#more-info-dialog").css("display", "block");
    $("#more-info-dialog-background").css("display", "block");
    $("#target").css("overflow", "hidden");
    var userId = value;
    $.ajax({
        url: "/api/user/showUser",
        dataType: "JSON",
        data: {
            "userId": userId
        },
        success: function (data) {
            console.log(data);
            if (data.code === "0") {
                $("#message-box-text").html(data.msg);
                $("#message-box").css("color", "#a94442");
                $("#message-box").css("background", "#f2dede");
                setTimeout(function () {
                    $('#message-box').slideUp(300);
                }, 1000);
                if ($("#message-box").is(":hidden")) {
                    $('#message-box').slideDown(300);
                } else {
                    $('#message-box').slideUp(300);
                }
            } else if (data.code === "1") {
                $("#moreInfoData").html(
                    '<div class="content-text" style="display: inline-block;width: 100%;align-content: center">' +
                    '<div style="float: left;">' +
                    '<p>公有ID:' +
                    '<label>' +
                    '<input style="background: transparent;border: none"' +
                    'th:id="id' + data.data['user_common_id'] + '" type="text"' +
                    'value="' + data.data['user_common_id'] + '">' +
                    '</label>' +
                    '</p>' +
                    '<p>私有ID:' +
                    '<label>' +
                    '<input style="background: transparent;border: none"' +
                    'id="privateId" type="text"' +
                    'value="' + data.data['user_common_private_id'] + '">' +
                    '</label>' +
                    '</p>' +
                    '<p>OpenID:' +
                    '<label>' +
                    '<input style="background: transparent;border: none" id="openId" type="text"' +
                    'value="' + data.data['user_common_open_id'] + '">' +
                    '</label>' +
                    '</p>' +
                    '<p>用户名:' +
                    '<label>' +
                    '<input style="background: transparent;border: none"' +
                    'id="username" type="text"' +
                    'value="' + data.data['user_common_username'] + '">' +
                    '</label>' +
                    '</p>' +
                    '<p>密码:' +
                    '<label>' +
                    '<input style="background: transparent;border: none"' +
                    'id="password" type="text"' +
                    'value="' + data.data['user_common_password'] + '">' +
                    '</label>' +
                    '</p>' +
                    '<p>昵称:' +
                    '<label>' +
                    '<input style="background: transparent;border: none"' +
                    'id="nickname" type="text"' +
                    'value="' + data.data['user_common_nickname'] + '">' +
                    '</label>' +
                    '</p>' +
                    '<p>头像编号:' +
                    '<label>' +
                    '<input style="background: transparent;border: none"' +
                    'id="headImage" type="text"' +
                    'value="' + data.data['user_common_head_image_id'] + '">' +
                    '</label>' +
                    '</p>' +
                    '</div>' +
                    '<div style="float: left;">' +
                    '<p>友人帐编号:' +
                    '<label>' +
                    '<input style="background: transparent;border: none"' +
                    'id="friendLink" type="text"' +
                    'value="' + data.data['user_common_friend_link_id'] + '">' +
                    '</label>' +
                    '</p>' +
                    '<p>真实姓名:' +
                    '<label>' +
                    '<input style="background: transparent;border: none"' +
                    'id="realname" type="text"' +
                    'value="' + data.data['user_secret_real_name'] + '">' +
                    '</label>' +
                    '</p>' +
                    '<p>电话:' +
                    '<label>' +
                    '<input style="background: transparent;border: none"' +
                    'id="phone" type="text"' +
                    'value="' + data.data['user_secret_phone'] + '">' +
                    '</label>' +
                    '</p>' +
                    '<p>电子邮箱:' +
                    '<label>' +
                    '<input style="background: transparent;border: none"' +
                    'id="email" type="text"' +
                    'value="' + data.data['user_secret_email'] + '">' +
                    '</label>' +
                    '</p>' +
                    '<p>生日:' +
                    '<label>' +
                    '<input style="background: transparent;border: none"' +
                    'id="birthday" type="text"' +
                    'value="' + data.data['user_secret_birthday'] + '">' +
                    '</label>' +
                    '</p>' +
                    '<p>性别:' +
                    '<label>' +
                    '<input style="background: transparent;border: none"' +
                    'id="sex" type="text"' +
                    'value="' + data.data['user_secret_sex'] + '">' +
                    '</label>' +
                    '</p>' +
                    '<p>年龄:' +
                    '<label>' +
                    '<input style="background: transparent;border: none"' +
                    'id="age" type="text"' +
                    'value="' + data.data['user_secret_age'] + '">' +
                    '</label>' +
                    '</p>' +
                    '</div>' +
                    '<div style="float: left">' +
                    '<p>微信:' +
                    '<label>' +
                    '<input style="background: transparent;border: none"' +
                    'id="wechat" type="text"' +
                    'value="' + data.data['user_secret_wechat'] + '">' +
                    '</label>' +
                    '</p>' +
                    '<p>QQ:' +
                    '<label>' +
                    '<input style="background: transparent;border: none"' +
                    'id="qq" type="text"' +
                    'value="' + data.data['user_secret_qq'] + '">' +
                    '</label>' +
                    '</p>' +
                    '<p>微博:' +
                    '<label>' +
                    '<input style="background: transparent;border: none"' +
                    'id="weibo" type="text"' +
                    'value="' + data.data['user_secret_weibo'] + '">' +
                    '</label>' +
                    '</p>' +
                    '<p>地址:' +
                    '<label>' +
                    '<input style="background: transparent;border: none"' +
                    'id="address" type="text"' +
                    'value="' + data.data['user_secret_address'] + '">' +
                    '</label>' +
                    '</p>' +
                    '<p>登录时间:' +
                    '<label>' +
                    '<input style="background: transparent;border: none"' +
                    'id="logtime" type="text"' +
                    'value="' + data.data['user_safe_logtime'] + '">' +
                    '</label>' +
                    '</p>' +
                    '<p>IP地址:' +
                    '<label>' +
                    '<input style="background: transparent;border: none"' +
                    'id="ip" type="text"' +
                    'value="' + data.data['user_safe_ip'] + '">' +
                    '</label>' +
                    '</p>' +
                    '<p>权重:' +
                    '<label>' +
                    '<input style="background: transparent;border: none"' +
                    'id="weight" type="text"' +
                    'value="' + data.data['user_safe_weight'] + '">' +
                    '</label>' +
                    '</p>' +
                    '</div>' +
                    '<div style="float: left">' +
                    '<p>安全问题:' +
                    '<label>' +
                    '<input style="background: transparent;border: none"' +
                    'id="question" type="text"' +
                    'value="' + data.data['user_safe_question'] + '">' +
                    '</label>' +
                    '</p>' +
                    '<p>安全答案:' +
                    '<label>' +
                    '<input style="background: transparent;border: none"' +
                    'id="answer" type="text"' +
                    'value="' + data.data['user_safe_answer'] + '">' +
                    '</label>' +
                    '</p>' +
                    '<p>操作系统:' +
                    '<label>' +
                    '<input style="background: transparent;border: none"' +
                    'id="system" type="text"' +
                    'value="' + data.data['user_safe_system'] + '">' +
                    '</label>' +
                    '</p>' +
                    '<p>浏览器:' +
                    '<label>' +
                    '<input style="background: transparent;border: none"' +
                    'id="browser" type="text"' +
                    'value="' + data.data['user_safe_browser'] + '">' +
                    '</label>' +
                    '</p>' +
                    '<p>权限:' +
                    '<label>' +
                    '<input style="background: transparent;border: none"' +
                    'id="permission" type="text"' +
                    'value="' + data.data['user_safe_permission'] + '">' +
                    '</label>' +
                    '</p>' +
                    '<p>角色:' +
                    '<label>' +
                    '<input style="background: transparent;border: none"' +
                    'id="role' + data.data['user_safe_role'] + '" type="text"' +
                    'value="' + data.data['user_safe_role'] + '">' +
                    '</label>' +
                    '</p>' +
                    '</div>' +
                    '</div>' +
                    '<div class="btn-container">' +
                    '<button class="btn btn-info" onclick="updateUser()">更新</button>' +
                    '<button class="btn btn-danger" onclick="deleteUser(this.value)" ' +
                    'value="' + data.data['user_common_id'] + '">删除</button>' +
                    '</div>');
            }
        },
    })
}

//删除用户
function deleteUser(value) {
    $.ajax({
        url: "/api/user/deleteUserByAjax",
        dataType: "JSON",
        data: {
            "deleteId": value
        },
        contentType: "application/json; charset=utf-8",
        success: function (data) {
            if (data.result === "1") {
                $("#message-box-text").html(data.msg);
                $("#message-box").css("color", "#d7f7ff");
                $("#message-box").css("background", "#1a95ff");
                selectUserList();
            } else {
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
    })
}

//更新用户
function updateUser() {
    alert(1);
    var uid = $("#uid").val();
    var sid = $("#sid").val();
    var username = $("#username").val().trim();
    var password = $("#password").val().trim();
    var sex = $("#sex").val();
    var ip = $("#ip").val();
    var browser = $("#browser").val();
    var weight = $("#weight").val();
    $.ajax({
        url: "/api/user/addUserByAjax",
        dataType: "JSON",
        type: "POST",
        data: {
            "uid": uid, "sid": sid, "username": username, "password": password,
            "sex": sex, "ip": ip, "browser": browser, "weight": weight
        },
        contentType: "application/json; charset=utf-8",
        success: function (data) {
            if (data.result === "1") {
                $("#message-box-text").html("更新成功");
                $("#message-box").css("color", "#d7f7ff");
                $("#message-box").css("background", "#1a95ff");
                selectUserList();
            } else {
                $("#message-box-text").html("更新失败");
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
    })
}
