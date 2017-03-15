
var gameModel;

$( document ).ready(function() {
  // Handler for .ready() called.
  $.getJSON("model", function( json ) {
  gameModel = json;
    console.log( "JSON Data: " + json );
   });
});

function placeShip() {
   console.log($( "#shipSelec" ).val());
   console.log($( "#rowSelec" ).val());
   console.log($( "#colSelec" ).val());
   console.log($( "#orientationSelec" ).val());
   console.log($( "#diffSelec").val());

   //var menuId = $( "ul.nav" ).first().attr( "id" );
   var request = $.ajax({
     url: "/placeShip/"+$( "#shipSelec" ).val()+"/"+$( "#rowSelec" ).val()+"/"+$( "#colSelec" ).val()+"/"+$( "#orientationSelec" ).val()+"/"+$( "#diffSelec" ).val(),
     method: "post",
     data: JSON.stringify(gameModel),
     contentType: "application/json; charset=utf-8",
     dataType: "json"
   });

   request.done(function( currModel ) {
     displayGameState(currModel);
     gameModel = currModel;

   });

   request.fail(function( jqXHR, textStatus ) {
     alert( "Request failed: " + textStatus );
   });
}


function shipchange(ship){
    var obj = document.getElementById("shipSelec");
    var selected = obj.options;
    for(var opt, j = 0; opt = selected[j]; j++) {
            if(opt.value == ship) {
                obj.selectedIndex = j;
                break;
            }
        }
}

function fire(){
 console.log($( "#colFire" ).val());
   console.log($( "#rowFire" ).val());
//var menuId = $( "ul.nav" ).first().attr( "id" );
   var request = $.ajax({
     url: "/fire/"+$( "#colFire" ).val()+"/"+$( "#rowFire" ).val(),
     method: "post",
     data: JSON.stringify(gameModel),
     contentType: "application/json; charset=utf-8",
     dataType: "json"
   });

   request.done(function( currModel ) {
     displayGameState(currModel);
     gameModel = currModel;

   });

   request.fail(function( jqXHR, textStatus ) {
     alert( "Request failed: " + textStatus );
   });

}
//Fires at the location clicked on the board by the user.
function fireClick(c, r){
   var request = $.ajax({
     url: "/fire/"+c+"/"+r,
     method: "post",
     data: JSON.stringify(gameModel),
     contentType: "application/json; charset=utf-8",
     dataType: "json"
   });

   request.done(function( currModel ) {
     displayGameState(currModel);
     gameModel = currModel;

   });

   request.fail(function( jqXHR, textStatus ) {
     alert( "Request failed: " + textStatus );
   });

}

function scan(){
 console.log($( "#colScan" ).val());
   console.log($( "#rowScan" ).val());
   //var menuId = $( "ul.nav" ).first().attr( "id" );
   var request = $.ajax({
     url: "/scan/"+$( "#colScan" ).val()+"/"+$( "#rowScan" ).val(),
     method: "post",
     data: JSON.stringify(gameModel),
     contentType: "application/json; charset=utf-8",
     dataType: "json"
   });

   request.done(function( currModel ) {
     displayGameState(currModel);
     gameModel = currModel;

   });

   request.fail(function( jqXHR, textStatus ) {
     alert( "Request failed: " + textStatus );
   });

}

function mClick(event, r, c){
    if(event.button == 0){
        fireClick(r, c);
    } else if(event.button == 2){
        scanAt(r, c);
    }
}

function scanAt(r, c){
   document.getElementById("ScanLocation").innerHTML = "Scan at: " + r + ", " + c;
   var request = $.ajax({
     url: "/scan/"+c+"/"+r,
     method: "post",
     data: JSON.stringify(gameModel),
     contentType: "application/json; charset=utf-8",
     dataType: "json"
   });

   request.done(function( currModel ) {
     displayGameState(currModel);
     gameModel = currModel;

   });

   request.fail(function( jqXHR, textStatus ) {
     alert( "Request failed: " + textStatus );
   });

}


function log(logContents){
    console.log(logContents);
}

function displayGameState(gameModel){
$( '#MyBoard td'  ).css("background-color", "blue");
$( '#TheirBoard td'  ).css("background-color", "blue");

if(gameModel.scanResult){
document.getElementById("ScanResults").innerHTML = "Scan found at least one ship"}
else{
document.getElementById("ScanResults").innerHTML = "Scan found no ships"}

displayShip(gameModel.aircraftCarrier);
displayShip(gameModel.battleship);
displayShip(gameModel.clipper);
displayShip(gameModel.dinghy);
displayShip(gameModel.submarine);

for (var i = 0; i < gameModel.computerMisses.length; i++) {
   $( '#TheirBoard #' + 'C' + gameModel.computerMisses[i].Across + '_' + gameModel.computerMisses[i].Down ).css("background-color", "white");
       var hit = document.createElement("span");
       hit.innerHTML = "O";
       var place = document.getElementById('C'+gameModel.computerMisses[i].Across + '_' + gameModel.computerMisses[i].Down);
       if(place.childNodes.length == 0)
        place.appendChild(hit);
}
for (var i = 0; i < gameModel.computerHits.length; i++) {
   $( '#TheirBoard #' + 'C' +gameModel.computerHits[i].Across + '_' + gameModel.computerHits[i].Down ).css("background-color", "red");
      var hit = document.createElement("span");
             hit.innerHTML = "X";
             var place = document.getElementById('C'+gameModel.computerHits[i].Across + '_' + gameModel.computerHits[i].Down);
   $( '#Row_Health #' + 'h_' + (i/2)+'c').css("background-color", "red");
      //var health = document.getElementById("h_"+(i/2)+'c');
      //health.style.backgroundColor = 'red';
            if(place.childNodes.length == 0)
             place.appendChild(hit);
}

for (var i = 0; i < gameModel.playerMisses.length; i++) {
   $( '#MyBoard #' + gameModel.playerMisses[i].Across + '_' + gameModel.playerMisses[i].Down ).css("background-color", "white");
      var hit = document.createElement("span");
             hit.innerHTML = "O";
             var place = document.getElementById(gameModel.playerMisses[i].Across + '_' + gameModel.playerMisses[i].Down );
             if(place.childNodes.length == 0)
             place.appendChild(hit);
}
for (var i = 0; i < gameModel.playerHits.length; i++) {
   $( '#MyBoard #' + gameModel.playerHits[i].Across + '_' + gameModel.playerHits[i].Down ).css("background-color", "red");
    var hit = document.createElement("span");
           hit.innerHTML = "X";
           var place = document.getElementById(gameModel.playerHits[i].Across + '_' + gameModel.playerHits[i].Down);
    $( '#Row_Health #' + 'h_' + (i/2)+'').css("background-color", "red");
           if(place.childNodes.length == 0)
           place.appendChild(hit);
}

}



function displayShip(ship){
 startCoordAcross = ship.start.Across;
 startCoordDown = ship.start.Down;
 endCoordAcross = ship.end.Across;
 endCoordDown = ship.end.Down;
// console.log(startCoordAcross);
 if(startCoordAcross > 0){
    if(startCoordAcross == endCoordAcross){
        for (i = startCoordDown; i <= endCoordDown; i++) {
            $( '#MyBoard #'+startCoordAcross+'_'+i  ).css("background-color", "yellow");
        }
    } else {
        for (i = startCoordAcross; i <= endCoordAcross; i++) {
            $( '#MyBoard #'+i+'_'+startCoordDown  ).css("background-color", "yellow");
        }
    }
 }



}