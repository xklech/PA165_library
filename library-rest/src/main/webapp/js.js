$(function(){
    $(".datepick").datepicker();
    zobrazVsechnyZakazniky();
});


function show(id){
    if(id === "customers"){
        $("#books").hide();
        $("#customers").show();
        zobrazVsechnyZakazniky();
    }else{
        $("#customers").hide();
        $("#books").show();
        zobrazVsechnyKnihy();
    }
}


function pridatZakaznika(form){
    console.debug(form['firstName'].value);
    var json = {};
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
        findById(null, false, id);
        showDialog("Hotovo","Zákazník byl úspěšně přidán. Id = "+id);
          form.reset();
    }).fail(function( jqXHR, textStatus,errorThrown  ) {
        showDialog("Chyba","Při přidávání zákazníka došlo k chybě");
    });
}

function findById(form,emptyTable,idFind){
    var id ;
    if(!form){
        id = idFind;
    }else{
        id = $(form).find("[name=findid]").val();
    }
    console.log(id);
    $.ajax({
        type: "GET",
        url: "/pa165/webresources/customers/json/"+id,
        dataType: "json"
    }).done(function( msg ) {
        if(!msg){
            showDialog("Nenalezen","Zákazník s požadovaným id nebyl nalezen.");
        }else{
            console.log(msg);
            $("#zakaznici_table").show();
            if(emptyTable){
                $("#zakaznici_table").find("tr:gt(0)").remove();
            }
            $("#zakaznici_table").append('<tr id="zakaznik_'+msg.id+'"><td>'+msg.id+'</td><td>'+msg.firstName+'</td><td>'+msg.lastName+'</td><td>'+msg.address+'</td><td>'+msg.dateOfBirth+'</td><td>'+msg.pid+'</td><td><a href="#" onclick="smazatZakaznika(\''+msg.id+'\');return false;" title="Smazat zakaznika">smazat</a> &nbsp; <a href="#upravit_zakaznika" onclick="upravitZakaznikaForm(\''+msg.id+'\');" title="Upravit zakaznika">upravit</a></td></tr>');
        }
    }).fail(function( jqXHR, textStatus,errorThrown  ) {
        showDialog("Chyba","Při získávání zákazníka došlo k chybě");
    });
}

function showDialog(title,body){
        $("#dialog_chyba").text(body);
        $("#dialog_chyba").attr("title",title);
        $("#dialog_chyba").dialog({
              buttons: [
                  {
                      text: "Zavři",
                      click: function() {$( this ).dialog( "close" );}
                  }
                  ]
          });
}


function smazatZakaznika(id){
    if(!confirm('Opravdu smazat?')){
        return false;
    }
    console.log("id: "+id);
    
    $.ajax({
        type: "DELETE",
        url: "/pa165/webresources/customers/delete/"+id
    }).done(function( data, textStatus, jqXHR ) {
        $("#zakaznik_"+id).remove();
        if($("#zakaznici_table tr").length < 2){
            $("#zakaznici_table").hide();
        };
        showDialog("Smazán","Uživatel byl smazán");
    }).fail(function( jqXHR, textStatus,errorThrown  ) {
        showDialog("Chyba","Při mazaní zákazníka došlo k chybě");
    });
}


function upravitZakaznikaForm(id){
    console.log("upravit zakaznika id: "+id);
    $("#pridat_zakaznika").hide();
    $("#upravit_zakaznika").show();
    $.ajax({
        type: "GET",
        url: "/pa165/webresources/customers/json/"+id,
        dataType: "json"
    }).done(function( msg ) {
        if(!msg){
            showDialog("Nenalezen","Zákazník s požadovaným id nebyl nalezen.");
        }else{
            console.log(msg);
            $("#upravit_zakaznika input[name=firstName]").val(msg.firstName);
            $("#upravit_zakaznika input[name=lastName]").val(msg.lastName);
            $("#upravit_zakaznika input[name=address]").val(msg.address);
            $("#upravit_zakaznika input[name=dateOfBirth]").val(msg.dateOfBirth);
            $("#upravit_zakaznika input[name=pid]").val(msg.pid);
            $("#upravit_zakaznika input[name=customer_id]").val(msg.id);

        }
    }).fail(function( jqXHR, textStatus,errorThrown  ) {
        showDialog("Chyba","Při získávání zákazníka došlo k chybě");
    });
}

function zrusitUpravuZakaznika(){
            $("#upravit_zakaznika").hide();
            $("#pridat_zakaznika").show();
}

function upravitZakaznika(form){
    console.debug(form['firstName'].value);
    var json = {};
    $(form).find("input[type=text]").each(function(){
        json[$(this).attr('name')] = $(this).val();
    });
    console.log(json);
    console.log(JSON.stringify(json));
    var idCustomer = $(form).find("input[name=customer_id]").val();
    console.log(idCustomer);
    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: "/pa165/webresources/customers/json/put/"+idCustomer,
        data: JSON.stringify(json)
    }).done(function( data, textStatus, jqXHR  ) {
        console.log(idCustomer);
        $("#zakaznik_"+idCustomer).replaceWith('<tr id="zakaznik_'+idCustomer+'"><td>'+idCustomer+'</td><td>'+json.firstName+'</td><td>'+json.lastName+'</td><td>'+json.address+'</td><td>'+json.dateOfBirth+'</td><td>'+json.pid+'</td><td><a href="#" onclick="smazatZakaznika(\''+idCustomer+'\');return false;" title="Smazat zakaznika">smazat</a> &nbsp; <a href="#upravit_zakaznika" onclick="upravitZakaznikaForm(\''+idCustomer+'\');" title="Upravit zakaznika">upravit</a></td></tr>');
        showDialog("Hotovo","Zákazník byl úspěšně upraven.");
        form.reset();
        zrusitUpravuZakaznika();
    }).fail(function( jqXHR, textStatus,errorThrown  ) {
        showDialog("Chyba","Při upravování zákazníka došlo k chybě"+(jqXHR.status === 404?": neexistující zákazník":""));
    });
    
}

function zobrazVsechnyZakazniky(){
    console.log("Zobrazit vsechny zakazniky");
    zrusitUpravuZakaznika();
    $.ajax({
        type: "GET",
        url: "/pa165/webresources/customers/json/all",
        dataType: "json"
    }).done(function( msges ) {
        console.log(msges);
        if(msges.length > 0){
            $("#zakaznici_table").show();
            $("#zakaznici_table").find("tr:gt(0)").remove();
            msges.forEach(function(msg) {
                $("#zakaznici_table").append('<tr id="zakaznik_'+msg.id+'"><td>'+msg.id+'</td><td>'+msg.firstName+'</td><td>'+msg.lastName+'</td><td>'+msg.address+'</td><td>'+msg.dateOfBirth+'</td><td>'+msg.pid+'</td><td><a href="#" onclick="smazatZakaznika(\''+msg.id+'\');return false;" title="Smazat zakaznika">smazat</a> &nbsp; <a href="#upravit_zakaznika" onclick="upravitZakaznikaForm(\''+msg.id+'\');" title="Upravit zakaznika">upravit</a></td></tr>');
            });
        }
    }).fail(function( jqXHR, textStatus,errorThrown  ) {
        showDialog("Chyba","Při získávání zákazníků došlo k chybě");
    });
}

function findByName(jmeno, prijmeni){
    console.log(jmeno);
    console.log(prijmeni);
    jmeno = $.trim(jmeno);
    prijmeni = $.trim(prijmeni);
    if(!jmeno && !prijmeni){
        showDialog("Chyba","Musí být zadané minimálně jedno z polí jméno příjmení");
        return;
    }
    var tag;
    if(!jmeno){
        tag = "lastName/"+prijmeni;
    }else if(!prijmeni){
        tag = "firstname/"+jmeno;
    }else{
        tag = "fullname/"+jmeno+"/"+prijmeni;
    }
    
    $.ajax({
        type: "GET",
        url: "/pa165/webresources/customers/json/"+tag,
        dataType: "json"
    }).done(function( msges ) {
        $("#zakaznici_table").find("tr:gt(0)").remove();
        if(msges.length === 0){
            showDialog("Nenalezen","Žádný zákazník s požadovaným jménem nebyl nalezen.");
            $("#zakaznici_table").hide();
        }else{
            console.log(msges);
            if(msges.length > 0){
                $("#zakaznici_table").show();
                msges.forEach(function(msg) {
                    $("#zakaznici_table").append('<tr id="zakaznik_'+msg.id+'"><td>'+msg.id+'</td><td>'+msg.firstName+'</td><td>'+msg.lastName+'</td><td>'+msg.address+'</td><td>'+msg.dateOfBirth+'</td><td>'+msg.pid+'</td><td><a href="#" onclick="smazatZakaznika(\''+msg.id+'\');return false;" title="Smazat zakaznika">smazat</a> &nbsp; <a href="#upravit_zakaznika" onclick="upravitZakaznikaForm(\''+msg.id+'\');" title="Upravit zakaznika">upravit</a></td></tr>');
                });
            }
       }
    }).fail(function( jqXHR, textStatus,errorThrown  ) {
        showDialog("Chyba","Při získávání zákazníků podle jména došlo k chybě");
    });
    
    
}


/*
 * 
 * 
 *  KNIHA
 * 
 */


function pridatKnihu(form){
    var json = {};
    $(form).find("input[type=text]").each(function(){
        json[$(this).attr('name')] = $(this).val();
    });
    console.log(json);
    console.log(JSON.stringify(json));
    
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/pa165/webresources/books/json",
        data: JSON.stringify(json)
    }).done(function( data, textStatus, jqXHR  ) {
        var location = jqXHR.getResponseHeader('Location');
        var n = location.lastIndexOf('/');
        var id = location.substring(n+1,location.length);
        console.log(id);
        findBookById(null, false, id);
        showDialog("Hotovo","Kniha byla úspěšně přidána. Id = "+id);
          form.reset();
    }).fail(function( jqXHR, textStatus,errorThrown  ) {
        showDialog("Chyba","Při přidávání knihy došlo k chybě");
    });   
}

function findBookById(form,emptyTable,idFind){
    var id ;
    if(!form){
        id = idFind;
    }else{
        id = $(form).find("[name=findid_book]").val();
    }
    console.log(id);
    $.ajax({
        type: "GET",
        url: "/pa165/webresources/books/json/"+id,
        dataType: "json"
    }).done(function( msg ) {
        if(!msg){
            showDialog("Nenalezena","Kniha s požadovaným id nebyla nalezena.");
        }else{
            console.log(msg);
            displayBooks(msg,emptyTable);
        } 
    }).fail(function( jqXHR, textStatus,errorThrown  ) {
        showDialog("Chyba","Při získávání knihy došlo k chybě");
    });
}

function smazatKnihu(id){
    if(!confirm('Opravdu smazat?')){
        return false;
    }
    console.log("id: "+id);
    
    $.ajax({
        type: "DELETE",
        url: "/pa165/webresources/books/delete/"+id
    }).done(function( data, textStatus, jqXHR ) {
        $("#kniha_"+id).remove();
        if($("#knihy_table tr").length < 2){
            $("#knihy_table").hide();
        };
        showDialog("Smazána","Kniha byla smazána");
    }).fail(function( jqXHR, textStatus,errorThrown  ) {
        showDialog("Chyba","Při mazaní knihy došlo k chybě");
    });
}


function upravitKnihuForm(id){
    console.log("upravit knihu id: "+id);
    $("#pridat_knihu").hide();
    $("#upravit_knihu").show();
    $.ajax({
        type: "GET",
        url: "/pa165/webresources/books/json/"+id,
        dataType: "json"
    }).done(function( msg ) {
        if(!msg){
            showDialog("Nenalezen","Kniha s požadovaným id nebyla nalezena.");
        }else{
            console.log(msg);
            $("#upravit_knihu input[name=name]").val(msg.name);
            $("#upravit_knihu input[name=isbn]").val(msg.isbn);
            $("#upravit_knihu input[name=department]").val(msg.department);
            $("#upravit_knihu input[name=publishDate]").val(msg.publishDate);
            $("#upravit_knihu input[name=author]").val(msg.author);
            $("#upravit_knihu input[name=book_id]").val(msg.id);

        }
    }).fail(function( jqXHR, textStatus,errorThrown  ) {
        showDialog("Chyba","Při získávání knihy došlo k chybě");
    });
}

function zrusitUpravuKnihy(){
            $("#upravit_knihu").hide();
            $("#pridat_knihu").show();
}

function upravitKnihu(form){
    var json = {};
    $(form).find("input[type=text]").each(function(){
        json[$(this).attr('name')] = $(this).val();
    });
    console.log(json);
    console.log(JSON.stringify(json));
    var idBook = $(form).find("input[name=book_id]").val();
    console.log(idBook);
    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: "/pa165/webresources/books/json/put/"+idBook,
        data: JSON.stringify(json)
    }).done(function( data, textStatus, jqXHR  ) {
        console.log(idBook);
        $("#kniha_"+idBook).replaceWith('<tr id="kniha_'+idBook+'"><td>'+idBook+'</td><td>'+json.name+'</td><td>'+json.isbn+'</td><td>'+json.department+'</td><td>'+json.publishDate+'</td><td>'+json.author+'</td><td><a href="#" onclick="smazatKnihu(\''+idBook+'\');return false;" title="Smazat knihu">smazat</a> &nbsp; <a href="#upravit_knihu" onclick="upravitKnihuForm(\''+idBook+'\');return false;" title="Upravit knihu">upravit</a></td></tr>');
        showDialog("Hotovo","Kniha byla úspěšně upravena.");
        form.reset();
        zrusitUpravuKnihy();
    }).fail(function( jqXHR, textStatus,errorThrown  ) {
        showDialog("Chyba","Při upravování knihy došlo k chybě"+(jqXHR.status === 404?": neexistující kniha":""));
    });
    
}

function zobrazVsechnyKnihy(){
    console.log("Zobrazit vsechny knihy");
    zrusitUpravuKnihy();
    $.ajax({
        type: "GET",
        url: "/pa165/webresources/books/json/all",
        dataType: "json"
    }).done(function( msges ) {
        console.log(msges);
        displayBooks(msges,true);
    }).fail(function( jqXHR, textStatus,errorThrown  ) {
        showDialog("Chyba","Při získávání knih došlo k chybě");
    });
}


function findBookByName(name){
    name = $.trim(name);
    if(!name){
        showDialog("Chyba","Název nesmí obsahovat pouze bílé znaky");
        return;
    }
    console.log("name: "+name);
    $.ajax({
        type: "GET",
        url: "/pa165/webresources/books/json/name/"+name,
        dataType: "json"
    }).done(function( msg ) {
        if(msg.length ===0){
            showDialog("Nenalezena","Nebyla nalezena žádná kniha s požadovaným názvem.");
        }else{
            console.log(msg);
            displayBooks(msg,true);
        } 
    }).fail(function( jqXHR, textStatus,errorThrown  ) {
        showDialog("Chyba","Při získávání knihy došlo k chybě");
    });
}

function findBookByAuthor(author){
    author = $.trim(author);
    if(!author){
        showDialog("Chyba","Autor nesmí obsahovat pouze bílé znaky");
        return;
    }
    console.log("autor: "+author);
    $.ajax({
        type: "GET",
        url: "/pa165/webresources/books/json/author/"+author,
        dataType: "json"
    }).done(function( msg ) {
        if(msg.length ===0){
            showDialog("Nenalezena","Nebyla nalezena žádná kniha s požadovaným autorem.");
        }else{
            console.log(msg);
            displayBooks(msg,true);
        } 
    }).fail(function( jqXHR, textStatus,errorThrown  ) {
        showDialog("Chyba","Při získávání knihy došlo k chybě");
    });
}

function findBookByDepartment(department){
    department = $.trim(department);
    if(!department){
        showDialog("Chyba","Oddělení nesmí obsahovat pouze bílé znaky");
        return;
    }
    console.log("department: "+department);
    $.ajax({
        type: "GET",
        url: "/pa165/webresources/books/json/department/"+department,
        dataType: "json"
    }).done(function( msg ) {
        if(msg.length ===0){
            showDialog("Nenalezena","Nebyla nalezena žádná kniha v požadovaném oddělení.");
        }else{
            console.log(msg);
            displayBooks(msg,true);
        } 
    }).fail(function( jqXHR, textStatus,errorThrown  ) {
        showDialog("Chyba","Při získávání knihy došlo k chybě");
    });
}

function findBookByIsbn(isbn){
    isbn = $.trim(isbn);
    if(!isbn){
        showDialog("Chyba","ISBN nesmí obsahovat pouze bílé znaky");
        return;
    }
    console.log("isbn: "+isbn);
    $.ajax({
        type: "GET",
        url: "/pa165/webresources/books/json/isbn/"+isbn,
        dataType: "json"
    }).done(function( msg ) {
        if(!msg){
            showDialog("Nenalezena","Nebyla nalezena žádná kniha se zadaným isbn.");
        }else{
            console.log(msg);
            displayBooks(msg,true);
        } 
    }).fail(function( jqXHR, textStatus,errorThrown  ) {
        showDialog("Chyba","Při získávání knihy došlo k chybě");
    });
}


function displayBooks(msges,emptyTable){
    if($.isArray(msges)){
        if(msges.length > 0){
            $("#knihy_table").show();
            $("#knihy_table").find("tr:gt(0)").remove();
            msges.forEach(function(msg) {
                $("#knihy_table").append('<tr id="kniha_'+msg.id+'"><td>'+msg.id+'</td><td>'+msg.name+'</td><td>'+msg.isbn+'</td><td>'+msg.department+'</td><td>'+msg.publishDate+'</td><td>'+msg.author+'</td><td><a href="#" onclick="smazatKnihu(\''+msg.id+'\');return false;" title="Smazat knihu">smazat</a> &nbsp; <a href="#upravit_knihu" onclick="upravitKnihuForm(\''+msg.id+'\');" title="Upravit knihu">upravit</a></td></tr>');
            });
        }
    }else{
        var msg = msges;
        $("#knihy_table").show();
        if(emptyTable){
            $("#knihy_table").find("tr:gt(0)").remove();
        }
        $("#knihy_table").append('<tr id="kniha_'+msg.id+'"><td>'+msg.id+'</td><td>'+msg.name+'</td><td>'+msg.isbn+'</td><td>'+msg.department+'</td><td>'+msg.publishDate+'</td><td>'+msg.author+'</td><td><a href="#" onclick="smazatKnihu(\''+msg.id+'\');return false;" title="Smazat knihu">smazat</a> &nbsp; <a href="#upravit_knihu" onclick="upravitKnihuForm(\''+msg.id+'\');" title="Upravit knihu">upravit</a></td></tr>');
    }
}