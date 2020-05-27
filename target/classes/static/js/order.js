new Vue({
    el:"#app",
    data:{
        user:{
            id:"",            //用户id
            username:"",      //账号
            telephone:"",     //手机号
            name:"",          //用户名
            regTime:"",       //注册时间
            gexing:"",        //个性签名
            hobby:"",         //爱好
            address:"",       //地址
        },
        totalMoney:"",      //所有订单产品的总价
        orderList:[]        //获取订单详情里面的所有信息内容
    },
    methods:{
        //进入个人修改页面
        updateInfo:function () {
            window.location.href = "self_info.html";
        },
        //获取订单信息内容进行展示
        getOrder:function () {
            //在当前方法中定义一个变量，表明是vue对象
            var _this = this;
            axios.get('/Mishop/order/getOrder')
                .then(function (response) {
                    if (response.data != null) {
                        //把总价赋值给这里的totalMoney
                        _this.totalMoney = response.data.subtotal;
                        //这里把mso对象里面的订单详情信息赋值给oderList集合
                        _this.orderList = response.data.msoxqList;
                    } else {
                        alert("用户暂未登录！请登录后再进行此操作！");
                        window.location.href = "login.html";
                    }

                })
                .catch(function (error) {
                    console.log(error);
                })
        },
        //获取登录的用户的信息内容
        getUser:function () {
            //在当前方法中定义一个变量，表明是vue对象
            var _this = this;
            axios.get('/Mishop/order/getLoginUser')
                .then(function (response) {
                    //判断用户是否已经登录
                    if (response.data == null) {
                        alert("用户暂未登录！请登录后再进行此操作！");
                        window.location.href = "login.html";
                    } else {
                        //把返回的user对象赋值给这里的user进行显示
                        _this.user = response.data;
                    }
                })
                .catch(function (error) {
                    console.log(error);
                })
        },
        //付款功能
        payMoney:function () {
            //在当前方法中定义一个变量，表明是vue对象
            var _this = this;

            var current_totalMoney = document.getElementById("totalPrice").innerText;

            //当购物车里面有内容的时候才可以进行支付
            if (current_totalMoney != 0 && current_totalMoney != null) {
                var current_msoid = document.getElementById("msoid").innerText;
                var current_userid = document.getElementById("userid").innerText;

                //询问客户是否要进行支付
                if (confirm("是否确定要支付？共计：" + current_totalMoney + "元")){
                    //进行支付操作
                    axios.get('/Mishop/order/payMoney',{
                        params:{
                            //根据用户id和订单号进行支付
                            totalMoney : current_totalMoney,        //总价格
                            msoid : current_msoid,                  //订单号
                            userid : current_userid                 //用户id
                        }
                    })
                        .then(function () {
                            alert("支付成功！详情可以到个人中心查看订单详情！");
                            window.location.href = "dingdanzhongxin.html";
                        })
                        .catch(function (error) {
                            console.log(error);
                        })
                }
            } else {
                alert("订单中心暂无购买内容");
            }

            //alert(current_totalMoney + " - "  + current_msoid + " - " + current_userid);  //测试
        }
    },
    created:function () {
        //获取账号信息内容
        this.getUser();
        //当我们的订单页面刷新的时候就直接获取order里面的内容
        this.getOrder();
    }
});