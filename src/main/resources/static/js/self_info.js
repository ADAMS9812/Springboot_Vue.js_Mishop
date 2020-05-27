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
        //获取个人资料
        getUserDetails:function () {
            //在当前方法中定义一个变量，表明是vue对象
            var _this = this;
            axios.get('/Mishop/user/getUser')
                .then(function (response) {
                    _this.user.id = response.data.id;
                    _this.user.username = response.data.username;
                    _this.user.name = response.data.name;
                    _this.user.telephone = response.data.telephone;

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
        //退出当前账号
        loginOut:function () {
            if (confirm("是否确定要退出账号？")) {
                axios.get('/Mishop/user/loginOut')
                    .then(function (response) {
                        //把返回的user对象的id赋值给这里的id
                        if (response.data == "OK") {
                            alert("退出成功！");
                            //刷新页面
                            window.location.href = "self_info.html";
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