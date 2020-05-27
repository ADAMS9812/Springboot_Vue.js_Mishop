new Vue({

    el:"#app",
    data:{
        login_user:{
            id:"",            //用户id
            username:"",      //账号
            password:"",      //密码
            telephone:"",     //手机号
            name:"",          //用户名
            regTime:"",       //注册时间
            gexing:"",        //个性签名
            hobby:"",         //爱好
            address:""       //地址
        },
        login_userList:[]
    },
    methods:{
        login:function () {
            //在当前方法中定义一个变量，表明是vue对象
            var _this = this;
            axios.get('/Mishop/user/login', {
                params:{
                    username : _this.login_user.username,
                    password : _this.login_user.password
                }
            })
                .then(function (response) {
                    // _this.login_user = response.data;  //响应数据给user赋值
                    // 因为在数据库中id不为空，假如id为空则说明没有这个数据
                    if (response.data.id != null && response.data.id != 0){
                        alert("登录成功！(●'◡'●)，欢迎您！尊敬的：" + response.data.name);
                        window.location.href = "index.html";
                    } else {
                        alert("账号或密码有误！");
                        _this.login_user.username = "";
                        _this.login_user.password = "";
                    }
                })
                .catch(function (error) {
                    console.log(error);
                })
        },
        findAll:function () {
            var _this = this;
            axios.get('/Mishop/user/findAll')
                .then(function (response) {
                    _this.login_userList = response.data;
                })
                .catch(function (error) {
                    console.log(error);
                })
        }
    }

});