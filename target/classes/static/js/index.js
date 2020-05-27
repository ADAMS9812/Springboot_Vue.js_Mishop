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
          id:"",               //登录用户的id
          name:""              //用户的姓名
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
        //获取已经登陆的user的方法
        getLoginUser:function () {
            //在当前方法中定义一个变量，表明是vue对象
            var _this = this;
            axios.get('/Mishop/user/getUser')
                .then(function (response) {
                    //把返回的user对象的id赋值给这里的id
                    _this.user.id = response.data.id;
                    _this.user.name = response.data.name;
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
                            window.location.href = "index.html";
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
        //创建的时候先执行判断是否登录的方法
        this.getLoginUser();
    }
});