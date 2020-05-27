new Vue({
    el:"#app",
    data:{
        admin:{
            adminId:"",             //管理员id
            adminName:"",
            adminPassword:""
        },
        pro:{
            id:"",                  //产品id
            pName:"",               //产品名
            pNum:"",                //产品库存
            iPrice:"",              //产品单价
            pImg:"",                //产品封面图片
            xqImg:"",               //产品详情图片
            cid:""                  //产品类别
        },
        add_pro:{
            id:"",                  //产品id
            pName:"",               //产品名
            pSn:"",                 //产品描述
            pNum:"",                //产品库存
            mPrice:"",              //产品市场价
            iPrice:"",              //产品单价
            pImg:"",                //产品封面图片
            xqImg:"",               //产品详情图片
            cid:""                  //产品类别
        },
        proimages:"",               //封面图片
        xqimg:"",                   //详情图片
        current_pImg:"",            //当前封面图片的暂时图片名
        current_xqImg:"",           //当前详情图片的暂时图片名
        add_pImg:"",                //当前封面图片的暂时图片名
        add_xqImg:"",               //当前详情图片的暂时图片名
        myVal:"",                   //下拉框
        cidList:[],                 //类别集合
        totalPage:"",               //总页数
        currentPage:"1",            //当前页数
        proList:[]                  //产品集合
    },
    methods:{
        //当封面图片上传的时候出发change方法
        getProimages:function(event){
            //获取图片
            var _this = this;
            _this.proimages = event.target.files[0];

            //上传图片
            event.preventDefault();

            var formData = new FormData();
            formData.append('upload', _this.proimages);

            var config = {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            }
            axios.post('/Mishop/pro/uploadpImg',formData,config)
                .then(function (response) {
                    alert(response.data);
                })
                .catch(function (error) {
                    console.log(error);
                })
        },
        //当详情图片上传的时候出发change方法
        getXqimg:function(event){
            //获取图片
            var _this = this;
            _this.xqimg = event.target.files[0];

            //上传图片
            event.preventDefault();

            var formData = new FormData();
            formData.append('upload', _this.xqimg);

            var config = {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            }
            axios.post('/Mishop/pro/uploadxqImg',formData,config)
                .then(function (response) {
                    alert(response.data);
                })
                .catch(function (error) {
                    console.log(error);
                })
        },
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
                            window.location.href = "proAdmin.html";
                        } else {
                            alert("退出账号时发生异常！请刷新页面重新操作！");
                        }
                    })
                    .catch(function (error) {
                        console.log(error);
                    })
            }
        },
        //获取所有产品的方法
        findAll:function () {
            //在当前方法中定义一个变量，表明是vue对象
            var _this = this;
            axios.get('/Mishop/pro/findAll',{
                params:{
                    currentPage:_this.currentPage
                }
            })
                .then(function (response) {
                    _this.proList = response.data;
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
            axios.get('/Mishop/pro/getTotalPage')
                .then(function (response) {
                    //赋值总页数
                    _this.totalPage = response.data;
                })
                .catch(function (error) {
                    console.log(error);
                })
        },
        //根据id删除产品
        deleteById:function (id) {
            //在当前方法中定义一个变量，表明是vue对象
            var _this = this;
            if (confirm("是否确定要删除该产品？")) {
                axios.get('/Mishop/pro/deleteById/' + id)
                    .then(function (response) {
                        if (response.data = "OK") {
                            alert("删除成功！");
                            //刷新页面
                            _this.findAll();
                        } else {
                            alert("删除失败！操作异常！")
                        }
                    })
                    .catch(function (error) {
                        console.log(error);
                    })
            }
        },
        //根据id更新产品信息
        updateById:function (proid) {
            //在当前方法中定义一个变量，表明是vue对象
            var _this = this;
            axios.get('/Mishop/pro/updateById/' + proid, {
                params:{
                    pName:_this.pro.pName,  //产品名
                    iPrice:_this.pro.iPrice,//产品单价
                    pNum:_this.pro.pNum,    //库存
                    cid:_this.myVal,        //更新后的cid
                    pImg:_this.current_pImg,    //更新后的封面图片名
                    xqImg:_this.current_xqImg   //更新后的详情图片名
                }
            })
                .then(function (response) {
                    if (response.data == "OK") {
                        alert("更新成功！");
                        //刷新页面
                        _this.findAll();
                        //清空图片输入框
                        _this.current_pImg = "";
                        _this.current_xqImg = "";
                    } else {
                        alert("操作异常，请联系客服！");
                    }

                })
                .catch(function (error) {
                    console.log(error);
                })
            /*
                alert("产品id：" + proid);
                alert("更新后的产品cid：" + _this.myVal);
                alert("pro.pImg：" + _this.current_pImg);
                alert("pro.xqImg：" + _this.current_xqImg);
            */
        },
        //获取所有的商品类别
        getAllCid:function(){
            //在当前方法中定义一个变量，表明是vue对象
            var _this = this;
            axios.get('/Mishop/pro/getAllCid')
                .then(function (response) {
                    //根据id获取到的pro产品赋值过来
                    _this.cidList = response.data;
                })
                .catch(function (error) {
                    console.log(error);
                })
        },
        //根据pro_id获取产品展示
        getById:function (proid) {
            //在当前方法中定义一个变量，表明是vue对象
            var _this = this;
            axios.get('/Mishop/pro/getProById/' + proid)
                .then(function (response) {
                    //根据id获取到的pro产品赋值过来
                    _this.pro = response.data;
                    //给当前产品的类别进行赋值
                    _this.myVal = _this.pro.cid;
                    //alert(_this.myVal);  查看这个cid对不对
                })
                .catch(function (error) {
                    console.log(error);
                })
        },
        //新增产品
        addPro:function () {
            //在当前方法中定义一个变量，表明是vue对象
            var _this = this;

            /*alert(_this.add_pro.pName);
            alert(_this.add_pro.pSn);
            alert(_this.add_pro.pNum);
            alert(_this.add_pro.iPrice);
            alert(_this.add_pImg);
            alert(_this.add_xqImg);
            alert(_this.myVal);*/

            axios.get('/Mishop/pro/addPro', {
                params:{
                    pName:_this.add_pro.pName,  //产品名
                    pSn:_this.add_pro.pSn,      //产品描述
                    pNum:_this.add_pro.pNum,    //库存
                    iPrice:_this.add_pro.iPrice,//产品单价
                    mPrice:_this.add_pro.mPrice,//产品市场价
                    pImg:_this.add_pImg,    //更新后的封面图片名
                    xqImg:_this.add_xqImg,  //更新后的详情图片名
                    cid:_this.myVal        //更新后的cid
                    //还差个上传时间
                }
            })
                .then(function (response) {
                    if (response.data == "OK") {
                        alert("新增成功！");
                        //刷新页面
                        _this.findAll();
                        //清空图片输入框
                        _this.current_pImg = "";
                        _this.current_xqImg = "";
                    } else {
                        alert("操作异常，请联系客服！");
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

        //获取所有产品
        this.findAll();

        //执行查询所有分类的方法
        this.getAllCid();
    }
});