function copyToClipboard(parentElement) {
    let element = parentElement.querySelector('.command-code');
    let $temp = $("<input>");
    $("body").append($temp);
    $temp.val($(element).text().trim()).select();
    document.execCommand("copy");
    $temp.remove();
}