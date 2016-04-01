function escape_html(str){
    str = $("<div></div>").text(str).html();

    return str;
}

function unescape_html(str){
    str = $("<div></div>").html(str).text();
    return str;
}
