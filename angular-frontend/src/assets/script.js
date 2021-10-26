function myFunc(dateDebut, dateFin){
    var dateD = document.getElementById("dateDebut").value;
    var dateF = document.getElementById("dateFin").value;

    dateD.value = dateDebut;
    dateF.value = dateFin;


    var boolVal = false;

    if(dateD.length === 0 || dateF.length === 0){
      alert("Veuillez saisir la période avant.")
      boolVal = false;
    }else{
      if(dateD > dateF){
        alert("La date de début ne peut pas être plus grande que la date de fin.");
        boolVal = false;
      }else{
        boolVal = true;
      }
    }
    return boolVal;
}

//au chargement de la page, affiche directement les stats mails de la semaine en cours
//$( document ).ready(function() {
function onInitPage(){
  var dateD = document.getElementById("dateDebut");
  var dateF = document.getElementById("dateFin");

  var currentDate = new Date();
  console.log(currentDate.getDate());
  var firstday = new Date(currentDate.setDate(currentDate.getDate()+1 - currentDate.getDay()))
  firstday = new Date(currentDate.setDate(currentDate.getDate() - currentDate.getDay()-6))
  var lastday = new Date(currentDate.setDate(currentDate.getDate()+1 - currentDate.getDay() + 6))

  var temp = [firstday.getFullYear(), firstday.getMonth()+1, firstday.getDate()]
    .map(n => n < 10 ? `0${n}` : `${n}`).join('-');

  var temp1 = [lastday.getFullYear(), lastday.getMonth()+1, lastday.getDate()]
    .map(n => n < 10 ? `0${n}` : `${n}`).join('-');

    dateD.value = temp;
    dateF.value = temp1;

}



$(document).ready(function(){
  $("#myInput").on("keyup", function() {
    var value = $(this).val().toLowerCase();
    $("#myTable tr").filter(function() {
      $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
    });
  });
});
