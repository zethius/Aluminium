/**
 * Created by Peps on 2016-04-21.
 */
var url="/solutionTest/";
var testModalTarget = url + "loadEntity/";
var haslo="";
var href="";
function showTestModal(index) {
    var editUrl = testModalTarget + index;
    loadEntity(editUrl);
}
function loadEntity(url) {
    $.getJSON(url, {}, function (data) {
       populateModal(data);
    });
}
function populateModal(data) {
    href= "/solutionTest/"+data.id;
    haslo=data.password
}
function checkPassword(pass){
    document.getElementById('form_test_noPass').setAttribute("action", href);
    if(haslo==pass.value){
        document.getElementById('form_test').setAttribute("action", href);
    }else{
        alert("Złe hasło!");
    }
}