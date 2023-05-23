

let register_form = {
    init: function () {
        $("#register_btn").click(function () {
            register_form.send(); // this.send() 안됨! 유의
        });
    },
    // 서버로 보내기
    send: function () {
        $("#register_form").attr({
            'action': '/registerimpl',
            'method': 'post'
        });
        $("#register_form").submit();
    }
};

// web 04 - webapp - views = register.jsp

let jsp01 = {
    data:0,
    init:(num)=>{
        jsp01.data = num;
        jsp01.display();
    },
    display:()=>{
        $('#jsp01').text(this.data + '');
    }
}
$(()=>{
    jsp01.init(); //함수호출.
})

// web 04 - webapp - views = js01.jsp

