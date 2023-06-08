$(document).ready(function(){
    // Добавление класса "active" к ссылке при ее нажатии
    $('.nav-link').click(function(){
        $('.nav-link').removeClass('active');
        $(this).addClass('active');
    });
});
