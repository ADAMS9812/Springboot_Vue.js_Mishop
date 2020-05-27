new Vue({

    el:"#app",
    data:{
        user:{
            id:"",
            username:"",
            password:"",
            age:"",
            sex:"",
            email:"",
        },
        userList:[]
    },
    methods:{
        findAll:function () {
            //在当前方法中定义一个变量，表明是vue对象
            var _this = this;
            axios.get('/vueDemo/user/findAll')
                .then(function (response) {
                    _this.userList = response.data;  //响应数据给userList赋值
                    console.log(response);
                })
                .catch(function (error) {
                    console.log(error);
                })
        },
        findById:function (userid) {
            //在当前方法中定义一个变量，表明是vue对象
            var _this = this;
            axios.get('/vueDemo/user/findById', {
                params:{
                    id : userid
                }
            })
                .then(function (response) {
                    _this.user = response.data;  //响应数据给user赋值
                    $("#myModal").modal("show");
                })
                .catch(function (error) {
                    console.log(error);
                })
        },
        update: function (user) {
            var _this = this;
            axios.put("/vueDemo/user/update",_this.user)
                .then(function (response) {
                    _this.findAll();
                })
                .catch(function (err) {
                });
        }
    },
    created:function(){ //当我们页面加载的时候触发请求查询所有
        this.findAll();
    }
});