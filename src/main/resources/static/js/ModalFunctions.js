var url = "/solutionTest/";
var testModalTarget = url + "loadEntity/";
var haslo = "";
var isOpen;
var href = "";
function showTestModal(index, value) {
    isOpen = value;
    var editUrl = testModalTarget + index;
    if (isOpen) {
        $("#spanTest").remove();
        document.getElementById('haslodostepu').style.display = "none";
        $("#labelhaslo").remove();
    }
    loadEntity(editUrl);
}
function loadEntity(url) {
    $.getJSON(url, {}, function (data) {
        populateModal(data);
    });
}
function populateModal(data) {
    href = "/getSolutionTest?id=" + data.id;
    haslo = data.password;
}
function checkPassword(pass) {
    alert(pass);
    if (!isOpen) {
        if (haslo == pass.value) {
            href = href + "&pass=" + pass.value;
            document.getElementById('form_test').setAttribute("action", href)
        } else alert("Złe hasło!");
    }
    else {
        alert(href);
        document.getElementById('form_test').setAttribute("action", href);
    }
}
