new Vue({
    el:"#app",
    data:{
        pro:{
            id:"",           //产品id
            pName:"",         //产品名
            pSn:"",           //产品描述
            pImg:"",          //产品图片
            iPrice:"",        //网站价
            cid:"",           //当前类别
            currentPage:"",   //当前页码
            totalPage:""      //总页数
        },
        comment:{
            titles:"",        //标题
            proid:"",         //产品id
            userid:"",        //用户id
            username:"",      //用户名
            fcontent:"",      //内容
        },
        user:{
            id:"",               //登录用户的id
            name:""              //用户的姓名
        }
    },
    methods:{
        //获取已经登陆的user的方法
        getLoginUser:function () {
            //在当前方法中定义一个变量，表明是vue对象
            var _this = this;
            axios.get('/Mishop/user/getUser')
                .then(function (response) {
                    if (response.data.id == null || response.data.id == "") {
                        alert("暂未登录！无法进行评论操作！")
                    } else {
                        //把返回的user对象的id赋值给这里的id
                        _this.user.id = response.data.id;
                        _this.user.name = response.data.name;
                        //给comment对象赋值
                        _this.comment.userid = response.data.id;
                        _this.comment.username = response.data.name;
                    }
                })
                .catch(function (error) {
                    console.log(error);
                })
        },
        //获取产品id
        getProid:function () {
            //在当前方法中定义一个变量，表明是vue对象
            var _this = this;
            axios.get('/Mishop/comment/getProid')
                .then(function (response) {
                    _this.pro.id = response.data;
                    _this.comment.proid = response.data;
                })
                .catch(function (error) {
                    console.log(error);
                })
        }
    },
    created:function () {
        //创建的时候先执行判断是否登录的方法
        this.getLoginUser();
        //加载页面的时候先获取产品id
        this.getProid();
    }
});