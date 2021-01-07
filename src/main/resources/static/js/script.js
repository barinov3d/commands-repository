function showButtons(codeText) {
    document.querySelectorAll('.buttons').forEach(e => e.style.display = "none");
    let closestCommand = codeText.closest('.command');
    let buttons = closestCommand.querySelector('.buttons');
    buttons.style.display = "flex";
}