function show(id){
    if(id === "customers"){
        $("#books").hide();
        $("#customers").show();
    }else{
        $("#customers").hide();
        $("#books").show();
    }
}


function pridatZakaznika(form){
    console.debug(form['firstName'].value);
    var json = {}
    $(form).find("input[type=text]").each(function(){
        json[$(this).attr('name')] = $(this).val();
    });
    console.log(json);
    console.log(JSON.stringify(json));
    
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/pa165/webresources/customers/json",
        data: JSON.stringify(json)
    }).done(function( data, textStatus, jqXHR  ) {
        var location = jqXHR.getResponseHeader('Location');
        var n = location.lastIndexOf('/');
        var id = location.substring(n+1,location.length);
        console.log(id);
        
        
        $("#dialog_chyba").text("Zákazník byl úspěšně přidán. Id = "+id);
        $("#dialog_chyba").attr("title","Hotovo");
        $("#dialog_chyba").dialog({
              buttons: [
                  {
                      text: "Zavři",
                      click: function() {$( this ).dialog( "close" );}
                  }
                  ]
          });
          form.reset();
    }).fail(function( jqXHR, textStatus,errorThrown  ) {
        $("#dialog_chyba").text("Při přidávání zákazníka došlo k chybě");
        $("#dialog_chyba").attr("title","Chyba");
        $("#dialog_chyba").dialog({
              buttons: [
                  {
                      text: "Zavři",
                      click: function() {$( this ).dialog( "close" );}
                  }
                  ]
          });
    });
}

function findById(form){
    var id = $(form).find("[name=findid]").val();
    console.log(id);
    $.ajax({
        type: "GET",
        url: "/pa165/webresources/customers/json/"+id,
        dataType: "json",
    }).done(function( msg ) {
        $("#dialog_chyba").text("Zákazník byl úspěšně přidán. Id = "+msg);
        $("#dialog_chyba").attr("title","Hotovo");
        console.log(msg);
        $("#dialog_chyba").dialog({
              buttons: [
                  {
                      text: "Zavři",
                      click: function() {$( this ).dialog( "close" );}
                  }
                  ]
          });
    }).fail(function( jqXHR, textStatus,errorThrown  ) {
        $("#dialog_chyba").text("Při přidávání zákazníka došlo k chybě");
        $("#dialog_chyba").attr("title","Chyba");
        $("#dialog_chyba").dialog({
              buttons: [
                  {
                      text: "Zavři",
                      click: function() {$( this ).dialog( "close" );}
                  }
                  ]
          });
    });
}

function fnsuccesscallback(json){
    alert(json);
    
}