function showStuff(btn) {
    document.querySelectorAll('.btn1').forEach(e => e.style.display = "none");
    document.querySelectorAll('.btn2').forEach(e => e.style.display = "none");
    btn.querySelector('.btn1').style.display = "flex";
    btn.querySelector('.btn2').style.display = "flex";
    let command = btn.querySelector('.command-code');
    let bgColor = command.style.background;
/*    command.style.background = "#e7e7e7";
        setTimeout(function(){
            command.style.background = bgColor;
        }, 1000);*/
}