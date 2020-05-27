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
            currentPage:"1",   //当前页码
            totalPage:""      //总页数
        },
        user:{
            id:"",               //登录用户的id
            name:""              //用户的姓名
        },
        msoid:"",             //获取产品的msoid
        msoxq:{
            msoid:"",       //订单号
            count:"",       //单个产品的数量
            subtotal:"",    //产品的总价
            pImg:"",        //产品图片,
            pSn:""          //产品描述
        },
        msoxqList:[]        //产品详情数组
    },
    methods:{
        //根据产品名进行模糊查询
        getProsByName:function () {
            //在当前方法中定义一个变量，表明是vue对象
            var _this = this;
            //跳转到列表展示页面
            window.location.href = "liebiao.html?pName=" + _this.pro.pName + "&currentPage=1";
        },
        //根据订单id获取订单详情
        getMsoxq:function () {
            //在当前方法中定义一个变量，表明是vue对象
            var _this = this;
            axios.get('/Mishop/order/getMsoxq')
                .then(function (response) {
                    //把获取到的list集合赋值给这里的数组，返回给前端进行渲染
                    _this.msoxqList = response.data;
                })
                .catch(function (error) {
                    console.log(error);
                })
        },
        //根据msoid来获取分页的详情信息
        getPageMsoxq:function(){
            //在当前方法中定义一个变量，表明是vue对象
            var _this = this;
            axios.get('/Mishop/order/pageMsoxq',{
                params:{
                    //当前页数
                    currentPage:_this.pro.currentPage
                }
            })
                .then(function (response) {
                    //把获取到的list集合赋值给这里的数组，返回给前端进行渲染
                    _this.msoxqList = response.data;
                })
                .catch(function (error) {
                    console.log(error);
                })
        },
        //根据userid查询总的mso产品数
        getMsoxqCountByMsoid:function () {
            //在当前方法中定义一个变量，表明是vue对象
            var _this = this;
            axios.get('/Mishop/order/getMsoxqCountByMsoid')
                .then(function (response) {
                    //给我们的产品总数进行赋值
                    if(response.data != null && response.data != 0){
                        _this.pro.totalPage = response.data;
                    }
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
            if (_this.pro.currentPage == _this.pro.totalPage) {
                //页面跳转
                alert("已经是最后一页了噢！");
            } else {
                //页数 + 1
                _this.pro.currentPage = Number(_this.pro.currentPage) + 1
                //重新执行查询分页方法
                _this.getPageMsoxq();
            }

        },
        //上一页
        prePage:function () {
            //在当前方法中定义一个变量，表明是vue对象
            var _this = this;
            //判断当前是否为第一页
            if (_this.pro.currentPage == 1){
                //如果是第一页的话就不用动,返回第一页
                alert("已经是第一页了噢！");
            } else {
                //如果不是第一页的话就直接currentPage - 1
                _this.pro.currentPage = Number(_this.pro.currentPage) - 1
                //重新执行查询分页方法
                _this.getPageMsoxq();
            }

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
                            window.location.href = "dingdanxiangqing.html";
                        } else {
                            alert("操作异常！请联系客服！");
                        }
                    })
                    .catch(function (error) {
                        console.log(error);
                    })
            }
        },
        //评论方法
        comment:function (proid) {
            //在当前方法中定义一个变量，表明是vue对象
            var _this = this;
            axios.get('/Mishop/comment/setProid/' + proid)
                .then(function (response) {
                    if (response.data == "OK") {
                        //跳转到评论页面
                        window.location.href = "editArticle.html";
                    }
                })
                .catch(function (error) {
                    console.log(error);
                })
        }
    },
    created:function () {

        //当页面刷新出来的时候先执行查询该页面的信息内容的方法
        this.getMsoxq();

        //根据msoid获取msoxq总数
        this.getMsoxqCountByMsoid();

        //获取登录的方法
        this.getLoginUser();

    }
});