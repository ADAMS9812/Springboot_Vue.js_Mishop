new Vue({
    el:"#app",
    data:{
        admin:{
            adminId:"",             //管理员id
            adminName:"",           //管理员账号
            adminPassword:""        //管理员密码
        },
        user:{
            id:"",                  //用户id
            name:"",                //用户名
            telephone:"",           //手机号
            password:"",            //密码
            address:""              //收货地址
        },
        userList:[],                //用户集合
        totalPage:"",               //总页数
        currentPage:"1"             //当前页数,默认为1
    },
    methods:{
        //判断我们的管理员是否已经登陆的方法
        isLogin:function () {
            var _this = this;
            axios.get('/Mishop/admin/isLogin')
                .then(function (response) {
                    if (response.data.adminId == null || response.data.adminId == 0) {
                        alert("暂未登录管理员账号！请登录后在进行操作");
                        //清空username和password重新登录
                        window.location.href = "admin.html";
                    } else {
                        //如果已经登陆的话就把登录的对象返回给前端进行渲染
                        _this.admin.adminName = response.data.adminName;
                        _this.admin.adminId = response.data.adminId;
                        //alert(_this.admin.adminName);
                    }
                })
                .catch(function (error) {
                    console.log(error);
                })
        },
        //注销管理员登录的方法
        loginOut:function () {
            var _this = this;
            if (confirm("是否确定要退出当前管理员账号？")){
                axios.get('/Mishop/admin/loginOut')
                    .then(function (response) {
                        if (response.data == "OK") {
                            //刷新当前页面
                            window.location.href = "userAdmin.html";
                        } else {
                            alert("退出账号时发生异常！请刷新页面重新操作！");
                        }
                    })
                    .catch(function (error) {
                        console.log(error);
                    })
            }
        },
        //获取所有用户的方法
        getAllUser:function () {
            var _this = this;
            axios.get('/Mishop/user/findAll',{
                params:{
                    currentPage:_this.currentPage
                }
            })
                 .then(function (response) {
                     //把返回的集合赋值给userList
                     _this.userList = response.data;
                     //console.log(_this.userList);
                 })
                 .catch(function (error) {
                     console.log(error);
                 })
        },
        //根据用户id获取用户信息
        getUserById:function(userid){

            //这里首先根据用户id获取user对象，然后把那些对象的信息都展示到modal框里面
            //alert(userid);

            var _this = this;
            axios.get('/Mishop/user/getUserById/' + userid)
                .then(function (response) {
                    //把返回的集合赋值给userList
                    _this.user.id = response.data.id;
                    _this.user.name = response.data.name;
                    _this.user.telephone = response.data.telephone;
                    _this.user.password = response.data.password;
                    _this.user.address = response.data.address;
                })
                .catch(function (error) {
                    console.log(error);
                })
        },
        //根据id更新用户信息
        updateUser:function () {
            var _this = this;
            //根据用户id进行信息更新的操作
            axios.get('/Mishop/user/updateUserById/' + _this.user.id,{
                params:{
                    name : _this.user.name,
                    telephone : _this.user.telephone,
                    password : _this.user.password,
                    address : _this.user.address,
                }
            })
                .then(function (response) {
                    //如果更新成功就返回OK
                    if (response.data == "OK") {
                        alert("用户信息更新成功！");
                        //重新刷新当前页面的数据
                        _this.getAllUser();
                    }
                })
                .catch(function (error) {
                    console.log(error);
                })

        },
        //删除用户信息
        deleteUser:function (userid) {
            var _this = this;
            if (confirm("是否确定要删除该用户？")) {
                axios.delete('/Mishop/user/deleteByUserid/' + userid)
                    .then(function (response) {
                        if (response.data != null && response.data == "OK") {
                            alert("删除成功！");
                            //重新刷新页面
                            _this.getAllUser();
                        }
                    })
                    .catch(function (error) {
                        console.log(error);
                    })
            }
        },
        //下一页
        nextPage:function () {
            //在当前方法中定义一个变量，表明是vue对象
            var _this = this;
            //判断当前页面是否为最后一页
            if (_this.currentPage == _this.totalPage) {
                //页面跳转
                alert("已经是最后一页了噢！");
            } else {
                //页数 + 1
                _this.currentPage = Number(_this.currentPage) + 1
                //重新执行查询分页方法
                _this.getAllUser();
            }
        },
        //上一页
        prePage:function () {
            //在当前方法中定义一个变量，表明是vue对象
            var _this = this;
            //判断当前是否为第一页
            if (_this.currentPage == 1){
                //如果是第一页的话就不用动,返回第一页
                alert("已经是第一页了噢！");
            } else {
                //如果不是第一页的话就直接currentPage - 1
                _this.currentPage = Number(_this.currentPage) - 1;
                //重新执行查询分页方法
                _this.getAllUser();
            }
        },
        //获取用户的总页数
        getAllUserCount:function () {
            //在当前方法中定义一个变量，表明是vue对象
            var _this = this;
            axios.get('/Mishop/user/getAllUserCount')
                .then(function (response) {
                    //把返回的总页数赋值给这里的总页数
                    _this.totalPage = response.data;
                })
                .catch(function (error) {
                    console.log(error);
                })
        }
    },
    created:function () {
        //一刷新页面就判断该管理员是否已经登陆
        this.isLogin();
        //弹出所有的用户信息
        this.getAllUser();
        //获取所有的用户总数,返回总页数
        this.getAllUserCount();
    }
});