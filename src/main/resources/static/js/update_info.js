new Vue({
    el:"#app",
    data:{
        pro:{
            id:"",            //产品id
            pName:"",         //产品名
            pSn:"",           //产品描述
            pImg:"",          //产品图片
            iPrice:"",        //网站价
            cid:"",           //当前类别
            currentPage:"",   //当前页码
            totalPage:""      //总页数
        },
        user:{
            id:"",            //登录用户的id
            username:"",      //账户
            password:"",      //密码
            name:"",          //用户名
            telephone:"",     //联系电话
            gexing:"",        //个性签名
            hobby:"",         //爱好
            address:"",       //收货地址
        },
        proList:[]
    },
    methods:{
        //根据产品名进行模糊查询
        getProsByName:function () {
            //在当前方法中定义一个变量，表明是vue对象
            var _this = this;
            //跳转到列表展示页面
            window.location.href = "liebiao.html?pName=" + _this.pro.pName + "&currentPage=1";
        },
        //修改个人资料
        getUserDetails:function () {
            //在当前方法中定义一个变量，表明是vue对象
            var _this = this;
            axios.get('/Mishop/user/getUser')
                .then(function (response) {
                    _this.user.id = response.data.id;
                    _this.user.username = response.data.username;
                    _this.user.name = response.data.name;
                    _this.user.telephone = response.data.telephone;
                    _this.user.password =  response.data.password;
                    if (response.data.gexing != null) {
                        _this.user.gexing = response.data.gexing;
                    } else {
                        _this.user.gexing = "暂无个性签名";
                    }

                    if (response.data.hobby != null) {
                        _this.user.hobby = response.data.hobby;
                    } else {
                        _this.user.hobby = "暂无爱好";
                    }

                    if (response.data.address != null) {
                        _this.user.address = response.data.address;
                    } else {
                        _this.user.address = "暂无收货地址";
                    }
                })
                .catch(function (error) {
                    console.log(error);
                })
        },
        //更新个人资料
        update_detail:function () {
            //在当前方法中定义一个变量，表明是vue对象
            var _this = this;
            if (_this.user.password == "" || _this.user.name == "" ||
                _this.user.telephone == "" || _this.user.address == ""){
                alert("重要信息（姓名、密码、手机号、地址）请填完整！")
            } else {
                axios.get('/Mishop/user/updateUserByUsername',{
                    params:{
                        username:_this.user.username,
                        password:_this.user.password,
                        name:_this.user.name,
                        telephone:_this.user.telephone,
                        gexing:_this.user.gexing,
                        hobby:_this.user.hobby,
                        address:_this.user.address
                    }
                })
                    .then(function (response) {
                        if (response.data == "OK"){
                            alert("修改成功！请重新登陆账号！");
                            window.location.href = "login.html";
                        } else {
                            alert("修改失败，操作异常！")
                        }
                    })
                    .catch(function (error) {
                        console.log(error);
                    })
            }
        },
        //退出当前账号
        loginOut:function () {
            if (confirm("是否确定要退出账号？")) {
                axios.get('/Mishop/user/loginOut')
                    .then(function (response) {
                        //把返回的user对象的id赋值给这里的id
                        if (response.data == "OK") {
                            alert("退出成功！");
                            //刷新页面
                            window.location.href = "update_info.html";
                        } else {
                            alert("操作异常！请联系客服！");
                        }
                    })
                    .catch(function (error) {
                        console.log(error);
                    })
            }
        }
    },
    created:function () {
        //打开页面的时候先执行用户资料展示的方法
        this.getUserDetails();
    }
});