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
        mso:{
            msoid:"",         //订单id
            paystate:"",      //付款状态
            deliveryState:"", //发货状态
            subtotal:"",      //总价
            msoTime:""        //付款时间
        },
        proList:[],           //产品集合
        msoList:[],           //订单集合
        msoid:"",             //订单号
        totalMoney:""         //总价,无需设置或者获取,第一时间已经传过来了
    },
    methods:{
        //根据产品名进行模糊查询
        getProsByName:function () {
            //在当前方法中定义一个变量，表明是vue对象
            var _this = this;
            //跳转到列表展示页面
            window.location.href = "liebiao.html?pName=" + _this.pro.pName + "&currentPage=1";
        },
        //根据登录的用户查询个人订单内容
        getOrderByUser:function () {
            //在当前方法中定义一个变量，表明是vue对象
            var _this = this;
            axios.get('/Mishop/order/getMsoByUserid',{
                params:{
                    //当前页数
                    currentPage:_this.pro.currentPage
                }
            })
                .then(function (response) {
                    //把后台拿到的list集合返回给这里的msoList进行遍历渲染的操作
                    _this.msoList = response.data;
                })
                .catch(function (error) {
                    console.log(error);
                })
        },
        //根据msoid查询msoxq的集合
        getMsoxq:function (msoid) {
            //在当前方法中定义一个变量，表明是vue对象
            var _this = this;
            axios.get('/Mishop/order/getMsoxqByMsoid/' + msoid, {
                params:{
                    //初始化的时候当前页数先赋值为1
                    currentPage:1
                }
            })
                .then(function (response) {
                    //如果返回为OK并且不为空的话，就直接跳转到订单详情页面
                    if(response.data == "OK" && response.data != null){
                        //跳转到dingdanxiangqing.html页面查看订单内容
                        window.location.href = "dingdanxiangqing.html";
                    }
                })
                .catch(function (error) {
                    console.log(error);
                })
            //alert(msoid);     //输出测试一下产品的订单号msoid
        },
        //根据userid查询总的mso产品数
        getMsoCountByUserid:function () {
            //在当前方法中定义一个变量，表明是vue对象
            var _this = this;
            axios.get('/Mishop/order/getMsoCountByUserid')
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
                _this.getOrderByUser();
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
                _this.pro.currentPage = Number(_this.pro.currentPage) - 1;
                //重新执行查询分页方法
                _this.getOrderByUser();
            }

        },
        //判断当前订单是否已经支付
        isPayOrNot:function (msoid,totalMoney) {

            //alert(msoid);        //把订单号赋值给这里的data对象msoid
            //alert(totalMoney); //赋值总价给data对象的属性totalMoney

            //根据msoid查询当前订单是否已经付款，如果是的话就无需付款，否则进入支付页面
            //在当前方法中定义一个变量，表明是vue对象
            var _this = this;
            axios.get('/Mishop/pay/getPayState/' + msoid)
                .then(function (response) {
                    //给我们的产品总数进行赋值
                    if(response.data != null && response.data == true){
                        alert("该订单已支付，无需重复支付！");
                    } else {
                        if (confirm("是否确认要支付？")) {
                            _this.payMoney(msoid,totalMoney);
                        }
                    }
                })
                .catch(function (error) {
                    console.log(error);
                })

        },
        //支付方法，将未付款的订单进行付款
        payMoney:function (msoid,totalMoney) {
            //在当前方法中定义一个变量，表明是vue对象
            var _this = this;
            //根据订单号查询msoxq中的产品，并支付总价totalMoney
            axios.get('/Mishop/pay/payMoney',{
                params:{
                    msoid:msoid,            //订单号
                    totalMoney:totalMoney   //总金额
                }
            })
                .then(function (response) {
                    if (response.data == "OK") {
                        //跳转到支付页面
                        window.location.href = "order_late.html";
                    }
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
                            window.location.href = "dingdanzhongxin.html";
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

        //根据用户id查询总的产品数
        this.getMsoCountByUserid();

        //打开HTML页面的时候就要把所有的订单详情展示出来
        this.getOrderByUser();

        //创建的时候先执行判断是否登录的方法
        this.getLoginUser();

    }
});