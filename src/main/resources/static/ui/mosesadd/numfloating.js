/**
 * Created by Moses on 2017/4/20.
 */

var _click_count=0;
$("body").bind("click",function(e){
    //var n=Math.round(Math.random()*100);//随机数
    var $i=$("<b>").text("+"+(++_click_count));
    var x=e.pageX,y=e.pageY;
    $i.css({
        "z-index":99999,
        "top":y-15,
        "left":x,
        "position":"absolute",
        "color":"red"
    });
    $("body").append($i);
    $i.animate(
        {"top":y-180,"opacity":0},
        1500,
        function(){$i.remove();}
    );
    e.stopPropagation();
});
