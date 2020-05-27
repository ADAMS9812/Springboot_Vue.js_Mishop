new Vue({
    el:"#app",
    data:{
        login_user:{
            username:"",    //用户名
            password:"",    //密码
            id:""           //id号
        }
    },
    methods:{
        //登录方法
        login:function (username,password) {
            //alert(username + " - " + password);       //输出测试
            //在当前方法中定义一个变量，表明是vue对象
            var _this = this;
            axios.get('/Mishop/admin/login',{
                params:{
                    username:username,      //账号
                    password:password       //密码
                }
            })
                .then(function (response) {
                    if (response.data.adminId == null || response.data.adminId == 0) {
                        alert("账号或密码有误！请重新输入！");
                        //清空username和password重新登录
                        _this.login_user.username = "";
                        _this.login_user.password = "";
                    } else {
                        alert("登录成功(●'◡'●)！欢迎您，尊敬的：" + _this.login_user.username);
                        //登录成功后跳转到管理端页面
                        window.location.href = "userAdmin.html";
                    }
                })
                .catch(function (error) {
                    console.log(error);
                })
        }
    }


});