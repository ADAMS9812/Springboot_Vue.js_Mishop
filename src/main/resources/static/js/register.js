new Vue({
    el:"#app",
    data:{
        register_user:{
            id:"",            //用户id
            username:"",      //账号
            password:"",      //密码
            repassword:"",    //确认密码，当onmouseout的时候就check
            telephone:"",     //手机号
            name:"",          //用户名
            regTime:"",       //注册时间
            gexing:"",        //个性签名
            hobby:"",         //爱好
            address:"",       //地址
            yanzhengma:""     //验证码，当鼠标onclick的时候就执行
        },
        register_userList:[]
    },
    methods:{
        //注册方法
        register:function () {
            //在当前方法中定义一个变量，表明是vue对象
            var _this = this;
            axios.post('/Mishop/user/register', _this.register_user)
                .then(
                    function (response) {
                        alert(response.data);
                        window.location.href = "login.html";
                    }
                )
                .catch(function (error) {
                    console.log(error);
                })
        },
        //判断账号是否已经存在
        isExist:function(){
            //在当前方法中定义一个变量，表明是vue对象
            var _this = this;
            axios.get('/Mishop/user/isExist', {
                params:{
                    username : _this.register_user.username
                }
            })
                .then(
                    function (response) {
                        //如果用户名已经存在的话就提升用户已存在，并且清空用户名
                        if(response.data == true){
                            alert("该用户名已存在");
                            _this.register_user.username = "";
                        }
                    }
                )
                .catch(function (error) {
                    console.log(error);
                })
        },
        //验证码验证
        checkValid:function () {

        },
        //校验重复的密码
        checkRepassword:function () {

        }

    }

});