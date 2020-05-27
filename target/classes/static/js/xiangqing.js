new Vue({

    el:"#app",
    data:{
        pro:{
            id:"",            //产品id
            pName:"",         //产品名
            pSn:"",           //产品描述
            pImg:"",          //产品图片
            xqImg:"",         //产品详情图片
            iPrice:"",        //网站价
            mPrice:"",        //市场价
            cid:"",           //当前类别
        },
        user:{
            id:"",               //登录用户的id
            name:""              //用户的姓名
        },
        proList:[]
    },
    methods:{
        //获取详情页面产品的信息内容
        getXqProById:function(proid){
            //在当前方法中定义一个变量，表明是vue对象
            var _this = this;
            axios.get('/Mishop/pro/getProById/' + proid)
                .then(function (response) {
                    _this.pro.id = response.data.id;
                    _this.pro.pName = response.data.pName;
                    _this.pro.pSn = response.data.pSn;
                    _this.pro.pImg = response.data.pImg;
                    _this.pro.xqImg = response.data.xqImg;
                    _this.pro.iPrice = response.data.iPrice;
                    _this.pro.mPrice = response.data.mPrice;
                })
                .catch(function (error) {
                    console.log(error);
                })
        },
        //获取地址栏的参数,要输入字符串参数
        getUrlParams:function (variable) {
            var query = window.location.search.substring(1);
            var vars = query.split("&");
            for (var i=0;i<vars.length;i++) {
                var pair = vars[i].split("=");
                if(pair[0] == variable){
                    return pair[1];
                }
            }
            return false;
        },
        //根据产品名进行模糊查询
        getProsByName:function () {
            //在当前方法中定义一个变量，表明是vue对象
            var _this = this;
            //跳转到列表展示页面
            window.location.href = "liebiao.html?pName=" + _this.pro.pName + "&currentPage=1";
        },
        //将产品通过pro.id的方式添加到购物车，由于加载页面的时候id已经获取了，所以无需重复获取
        addToCartById:function () {
            //在当前方法中定义一个变量，表明是vue对象
            var _this = this;
            axios.post('/Mishop/cart/addCart/' + _this.pro.id)
                .then(function (response) {
                    alert(response.data);
                })
                .catch(function (error) {
                    console.log(error);
                })
        },
        //获取登录用户的方法
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
                            window.location.href = "xiangqing.html";
                        } else {
                            alert("操作异常！请联系客服！");
                        }
                    })
                    .catch(function (error) {
                        console.log(error);
                    })
            }
        },
        //点击评论查看评论内容
        lookComments:function (proid) {
            //在当前方法中定义一个变量，表明是vue对象
            var _this = this;
            axios.get('/Mishop/comment/setProid/' + proid)
                .then(function (response) {
                    if (response.data = "OK") {
                        window.location.href = "showPinglun.html";
                    }
                })
                .catch(function (error) {
                    console.log(error);
                })
        }
    },
    //页面加载的时候就执行该方法
    created:function () {

        //创建的时候先执行判断是否登录的方法
        this.getLoginUser();

        //获取url地址栏的id
        var id = this.getUrlParams("id");
        //要确保路径中确实存在id才能进行操作
        if (id != false && id != null) {
            this.pro.id =id;
            //获取到地址栏的id之后直接调用根据id查询结果的方法
            this.getXqProById(id);
        }

    }

});