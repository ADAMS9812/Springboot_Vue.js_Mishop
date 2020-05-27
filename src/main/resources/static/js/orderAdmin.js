new Vue({
    el:"#app",
    data:{
        admin:{
            adminId:"",             //管理员id
            adminName:"",
            adminPassword:""
        },
        msoxq:{
            msoid:""                //订单id
        },
        msoxqList:[],               //订单详情集合
        orderList:[],               //产品订单集合
        currentPage:"1",            //当前页数
        totalPage:""                //总页数
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
                            //刷新当前页面即可
                            window.location.href = "orderAdmin.html";
                        } else {
                            alert("退出账号时发生异常！请刷新页面重新操作！");
                        }
                    })
                    .catch(function (error) {
                        console.log(error);
                    })
            }
        },
        //查询所有订单的方法
        findAll:function () {
            var _this = this;
            axios.get('/Mishop/order/findAll',{
                params:{
                    currentPage : _this.currentPage
                }
            })
                .then(function (response) {
                    _this.orderList = response.data;
                })
                .catch(function (error) {
                    console.log(error);
                })
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
                _this.findAll();
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
                _this.findAll();
            }
        },
        //获取订单总数，最后计算总页数
        getTotalPage:function(){
            //在当前方法中定义一个变量，表明是vue对象
            var _this = this;
            axios.get('/Mishop/order/getTotalPage')
                .then(function (response) {
                    //赋值总页数
                    _this.totalPage = response.data;
                })
                .catch(function (error) {
                    console.log(error);
                })
        },
        //根据msoid删除mso和msoxq表中的订单
        deleteByMsoid:function (msoid) {
            //在当前方法中定义一个变量，表明是vue对象
            var _this = this;
            if (confirm("是否确定删除该订单？")) {
                axios.get('/Mishop/order/deleteByMsoid/' + msoid)
                    .then(function (response) {
                        //赋值总页数
                        if (response.data == "OK") {
                            alert("删除成功！");
                            //重新刷新页面
                            _this.findAll();
                        }
                    })
                    .catch(function (error) {
                        console.log(error);
                    })
            }
        },
        //根据msoid展示msoxq中的所有订单
        getMsoxq:function (msoid) {
            //在当前方法中定义一个变量，表明是vue对象
            var _this = this;
            //这里把传进来的msoid传给我们的msoxq对象中去
            _this.msoxq.msoid = msoid;
            axios.get('/Mishop/order/getMsoxqByMsoid_admin/' + msoid)
                .then(function (response) {
                    //把查询到返回的集合赋值给这里的msoxqList进行数据渲染
                    _this.msoxqList = response.data;
               })
               .catch(function (error) {
                   console.log(error);
               })
        },
        //确认发货
        sendPro:function () {
            //在当前方法中定义一个变量，表明是vue对象
            var _this = this;
            axios.get('/Mishop/order/sendPro/' + _this.msoxq.msoid)
                .then(function (response) {
                    if (response.data == "OK") {
                        alert("发货成功！")
                        //重新刷新页面
                        _this.findAll();
                    } else {
                        alert("操作异常！请联系客服！");
                    }
                })
                .catch(function (error) {
                    console.log(error);
                })
        }
    },
    created:function () {
        //一刷新页面就判断该管理员是否已经登陆
        this.isLogin();

        //获取总页数
        this.getTotalPage();

        //查询所有的订单进行分页展示
        this.findAll();
    }
});