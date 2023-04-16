$(document).ready(function () {

    $('#sortByName').click(function () {
        $('.book').sort(function (a, b) {
            let titleA = $(a).find('.title').text().toUpperCase();
            let titleB = $(b).find('.title').text().toUpperCase();
            return (titleA < titleB) ? -1 : (titleA > titleB) ? 1 : 0;
        }).appendTo('#list-th');
    });

    $('#sortByAuthor').click(function () {
        $('.book').sort(function (a, b) {
            let authorA = $(a).find('.author').text().toUpperCase();
            let authorB = $(b).find('.author').text().toUpperCase();
            return (authorA < authorB) ? -1 : (authorA > authorB) ? 1 : 0;
        }).appendTo('#list-th');
    });
});