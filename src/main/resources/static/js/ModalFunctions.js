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
    $('#nazwaTestu').text(data.name);
    href = "/getSolutionTest?id=" + data.id;
    haslo = data.password;
}
function checkPassword(pass) {
    if (!isOpen) {
        if (haslo == pass.value) {
            href = href + "&pass=" + pass.value;
            document.getElementById('form_test').setAttribute("action", href)
        } else alert("Złe hasło!");
    }
    else {
        document.getElementById('form_test').setAttribute("action", href);
    }
}

//======== NIZEJ DO /test/all
function showAttemptsModal(index) {
    var editUrl = "/solutionTest/loadResultEntity/" + index;
    loadResultEntity(editUrl);
}

function loadResultEntity(url) {
    $("#tabela tbody tr").remove();
    $.getJSON(url, {}, function (data) {
        for (var i in data) {
            $('#nazwaTestu').text(data[i].test.name);
            var osoba = data[i].user.name + " " + data[i].user.lastName;
            $('#tabela').append('<tr><td>' + osoba + '</td><td>' + data[i].points + '</td><td>' + data[i].test.maxPoints + '</td></tr>');
        }

    });
}