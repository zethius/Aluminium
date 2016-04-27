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
function showAttemptsModal(index,name) {
    var editUrl = "/solutionTest/loadResultEntity/" + index;
    $('#nazwaTestu').text(name);
    $('#tabelaaa').dataTable().fnClearTable();
    $.getJSON(editUrl, {}, function (data) {
        for (var i in data) {
            $('#tabelaaa').dataTable()
                .fnAddData([
                    data[i].user.name + " " + data[i].user.lastName,
                    data[i].points,
                    data[i].test.maxPoints]);
        }
        $('#wynikiA').modal('show');
    });


}

