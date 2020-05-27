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
        findByCid:function(current_cid,currentPage){
            //在当前方法中定义一个变量，表明是vue对象
            var _this = this;
            axios.get('/Mishop/pro/findByCid/' + current_cid + "?currentPage=" + currentPage)
                .then(function (response) {
                    _this.proList = response.data;
                })
                .catch(function (error) {
                    console.log(error);
                })
        },
        //根据产品名进行模糊查询
        getProsByName:function (current_pName,currentPage) {
            //在当前方法中定义一个变量，表明是vue对象
            var _this = this;
            axios.get('/Mishop/pro/getProByName/' + current_pName + "?currentPage=" + currentPage)
                .then(function (response) {
                    _this.proList = response.data;
                })
                .catch(function (error) {
                    console.log(error);
                })
        },
        //根据产品名进行模糊查询
        getProsByNameLiebiao:function () {
            //在当前方法中定义一个变量，表明是vue对象
            var _this = this;
            //跳转到列表展示页面
            window.location.href = "liebiao.html?pName=" + _this.pro.pName + "&currentPage=1";
        },
        //获取地址栏的参数,要输入字符串参数
        getUrlParams:function (variable) {
            var query = window.location.search.substring(1);
            var vars = query.split("&");
            for (var i=0;i<vars.length;i++) {
                var pair = vars[i].split("=");
                if(pair[0] == variable){
                    //这里是将中文的格式进行解码
                    return decodeURI(pair[1]);
                }
            }
            return false;
        },
        //获取href中的proid进行页面的跳转
        getProById:function(proid){
            //根据pro.id进行页面的跳转
            window.location.href = "xiangqing.html?id=" + proid;

        },
        //根据cid获取总页数
        getTotalPageByCid:function(current_cid){
            //在当前方法中定义一个变量，表明是vue对象
            var _this = this;
            axios.get('/Mishop/pro/getTotalPageByCid/' + current_cid)
                .then(function (response) {
                    _this.pro.totalPage = response.data;
                })
                .catch(function (error) {
                    console.log(error);
                })
        },
        //根据pName查询总页数
        getTotalPageByName:function(current_pName){
            //在当前方法中定义一个变量，表明是vue对象
            var _this = this;
            axios.get('/Mishop/pro/getTotalPageByName/' + current_pName)
                .then(function (response) {
                    _this.pro.totalPage = response.data;
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
                // window.location.href = "liebiao.html?cid=" + _this.pro.cid + "&currentPage=" + _this.pro.currentPage;
                alert("已经是最后一页了噢！");
            } else {
                //页数 + 1
                var next_currentPage = Number(_this.pro.currentPage) + 1;
                //页面跳转,如果cid不为空的时候就进行查询
               if (_this.pro.cid != false && _this.pro.cid != null){
                   window.location.href = "liebiao.html?cid=" + _this.pro.cid + "&currentPage=" + next_currentPage;
               }
               //如果pName不为空的时候就进行查询
               if (_this.pro.pName != false && _this.pro.pName != null){
                   window.location.href = "liebiao.html?pName=" + _this.pro.pName + "&currentPage=" + next_currentPage;
               }
            }
        },
        //上一页
        prePage:function () {
            //在当前方法中定义一个变量，表明是vue对象
            var _this = this;
            //判断当前是否为第一页
            if (_this.pro.currentPage == 1){
                //如果是第一页的话就不用动,返回第一页
                // window.location.href = "liebiao.html?cid=" + _this.pro.cid + "&currentPage=" + _this.pro.currentPage;
                alert("已经是第一页了噢！");
            } else {
                //如果不是第一页的话就直接currentPage - 1
                var pre_currentPage = Number(_this.pro.currentPage) - 1;
                //如果cid不为空的时候就进行页面的跳转
                if (_this.pro.cid != false && _this.pro.cid != null) {
                    window.location.href = "liebiao.html?cid=" + _this.pro.cid + "&currentPage=" + pre_currentPage;
                }
                //如果pName不为空的时候就进行页面的跳转
                if (_this.pro.pName != false && _this.pro.pName != null) {
                    window.location.href = "liebiao.html?pName=" + _this.pro.pName + "&currentPage=" + pre_currentPage;
                }

            }

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
                            window.location.href = "liebiao.html";
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
    //页面加载的时候就执行该方法
    created:function () {

        //创建的时候先执行判断是否登录的方法
        this.getLoginUser();

        //获取url地址栏的cid
        var cid = this.getUrlParams("cid");
        this.pro.cid = cid;

        //获取url地址栏的currentPage
        var currentPage = this.getUrlParams("currentPage");
        if (currentPage != false) {
            this.pro.currentPage = currentPage;
        }

        //获取产品名
        var pName = this.getUrlParams("pName");
        if (pName != false) {
            this.pro.pName = pName;
        }

        //根据cid查询总记录数，返回总页数
        if (cid != null && cid != false){
            this.getTotalPageByCid(cid);
        }

        // 根据cid和currentPage加载所有的产品
        if (cid != null && cid != false && currentPage != null && currentPage != false){
            this.findByCid(cid,currentPage);
        }

        //根据pName模糊查询所有产品
        if (pName != null && pName != false && currentPage != false && currentPage != null){
            this.getTotalPageByName(pName);
            this.getProsByName(pName,currentPage);
        }

    }

});