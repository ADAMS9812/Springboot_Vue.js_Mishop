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
            totalPage:"",     //总页数
            currentCount:"",  //当前产品的个数
            totalCount:"",    //总产品件数
            singlePrice:"",   //单个产品的总价
            totalPrice:""     //产品总价
        },
        user:{
            id:"",               //登录用户的id
            name:""              //用户的姓名
        },
        proList:[],           //用于存储数据的集合
        ids:[],               //用于存放数组元素的数组
        orders:[]             //用于存放订单的数组
    },
    methods:{
        //获取session中的cart购物车的所有内容
        getCart:function () {
            //在当前方法中定义一个变量，表明是vue对象
            var _this = this;
            axios.get('/Mishop/cart/getCart')
                .then(function (response) {
                    //把返回的list数据返回给proList集合，前端进行v-for渲染
                    _this.proList = response.data;
                    //alert(Object.keys(_this.proList).length);
                    //获取产品的总数
                    if (Object.keys(_this.proList).length == 0 || Object.keys(_this.proList).length == null) {
                        //如果返回的集合为空的话，就默认赋值为0
                        _this.pro.totalCount = 0;
                    } else {
                        //如果有产品的话，就赋值产品的总个数
                        _this.pro.totalCount = Object.keys(_this.proList).length;
                    }
                })
                .catch(function (error) {
                    console.log(error);
                })
        },
        //根据id清空购物车里面的内容
        deleteProById:function (proid) {
            //在当前方法中定义一个变量，表明是vue对象
            var _this = this;
            axios.delete('/Mishop/cart/deleteProById/' + proid)
                .then(function (response) {
                    alert(response.data);
                    window.location.href = "gouwuche.html";
                })
                .catch(function (error) {
                    console.log(error);
                })
        },
        //获取当前产品的数量并进行更新操作,就是更新数量用的
        getCount:function (proid,count) {
            //在当前方法中定义一个变量，表明是vue对象
            var _this = this;
            //var count = document.getElementById("shuliang").value;  //产品的数量
            //产品的数量必须大于0才能进行更新操作
            if (count > 0){
                //alert(count + "-" + proid);   //输出测试
                // console.log(_this.ids);
                axios.get('/Mishop/cart/updateCartPro', {
                    params:{
                        proid_update:proid,
                        count_update:count
                    }
                })
                    .then(function () {
                        //alert("产品数量改为：" + count);
                        window.location.href = "gouwuche.html";
                    })
                    .catch(function (error) {
                        console.log(error);
                    })
            } else {
                alert("购买产品的数量异常，请输入大于1的产品数量！");
            }
        },
        //清空购物车的操作，这里主要是根据proid删除当前的session和redis
        clearAll:function () {
            //在当前方法中定义一个变量，表明是vue对象
            var _this = this;
            var pros = document.getElementsByName("pro_ids");
            //只有购物车里面有内容的时候才能进行清除工作
            if (pros != null && pros.length > 0){
                for (var i = 0 ; i < pros.length ; i++) {
                    _this.ids.push(pros[i].innerText);
                    // alert(pros[i].innerText);
                }
                // console.log(_this.ids);
                axios.delete('/Mishop/cart/clearCart', {
                    params:{
                        ids:_this.ids.toString()
                    }
                })
                    .then(function (response) {
                        alert(response.data);
                        window.location.href = "gouwuche.html";
                    })
                    .catch(function (error) {
                        console.log(error);
                    })
            } else {
                alert("购物车暂时为空！");
            }
        },
        //将购物车的产品添加到订单页面
        addToOrder:function () {
            //在当前方法中定义一个变量，表明是vue对象
            var _this = this;
            //获取总价,实现思路是获取总价和所有的id号,传一个数组给后台
            var current_totalMoney = _this.totalMoney;
            //获取所有的产品id
            var pros_id = document.getElementsByName("pro_ids");
            for (var i = 0; i < pros_id.length; i++) {
                _this.orders.push(pros_id[i].innerText);
            }
            //alert(_this.orders);      //检查数组的内容是否有误
            //把当前购物车的产品添加到订单中心
            axios.get('/Mishop/order/addToOrder',{
                params:{
                    order_ids:_this.orders.toString(),      //产品的id数组
                    totalMoney:current_totalMoney           //产品的总价
                }
            })
                .then(function (response) {

                    //如果删除成功的话就跳转到订单中心
                    var msg = response.data;

                    if (msg != null && "OK" == msg) {

                        alert("立即结算！进入订单支付页面！");

                        //清除购物车的内容
                        _this.clearAll_order();

                        //订单支付中心进行支付
                        window.location.href = "order.html";

                    } else {
                        alert("账号暂未登录！请登录后再进行操作！");
                        window.location.href = "login.html";
                    }

                })
                .catch(function (error) {
                    console.log(error);
                })
        },
        //清空购物车的操作，这里主要是根据proid删除当前的session和redis
        clearAll_order:function () {
            //在当前方法中定义一个变量，表明是vue对象
            var _this = this;
            var pros = document.getElementsByName("pro_ids");
            //只有购物车里面有内容的时候才能进行清除工作
            if (pros != null && pros.length > 0){
                for (var i = 0 ; i < pros.length ; i++) {
                    _this.ids.push(pros[i].innerText);
                    // alert(pros[i].innerText);
                }
                // console.log(_this.ids);
                axios.delete('/Mishop/cart/clearCart', {
                    params:{
                        ids:_this.ids.toString()
                    }
                })
            } else {
                alert("购物车暂时为空！");
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
                            window.location.href = "gouwuche.html";
                        } else {
                            alert("操作异常！请联系客服！");
                        }
                    })
                    .catch(function (error) {
                        console.log(error);
                    })
            }
        },
        getTotalMoney:function () {
            //在当前方法中定义一个变量，表明是vue对象
            var _this = this;
            axios.get('/Mishop/cart/getCartTotalMoney')
                .then(function (response) {
                    //赋值总价
                    _this.totalMoney = response.data;
                })
                .catch(function (error) {
                    console.log(error);
                })
        }
    },
    created:function () {
        //页面创建的时候就直接执行getCart操作获取购物车的内容
        this.getCart();

        //先计算总价格
        this.getTotalMoney();

        //创建的时候先执行判断是否登录的方法
        this.getLoginUser();
    }
});