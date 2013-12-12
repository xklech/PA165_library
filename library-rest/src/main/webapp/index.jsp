<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
    <head>
	<title>REST api ukázka</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style.css" />
	<link rel="stylesheet" type="text/css" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
	<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
	<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
	<script src="${pageContext.request.contextPath}/js.js"></script>
    </head>
    <body>
	<div id="wrapper">
	    <div id="headerWrapper">
		<div id="header"> 
		    <h1>
			<a href="${pageContext.request.contextPath}" title="Home page">REST api ukázka</a>
		    </h1>
		</div>
	    </div>
	    <div id="contentWrapper">
		<nav>
                    <a href="#" onclick="show('customers')" title="Zákazníci">Zákazníci</a>
                    <a href="#" onclick="show('books')" title="Knihy">Knihy</a>
		</nav>
		<div id="content">   
                    <div id="dialog_chyba" title="Chyba" style="display:none">Při zpracování došlo k chybě</div>
                    
                    <div id="customers">
                        <h1>
                            Zákazníci
                        </h1>
                        <form method="POST" onsubmit="findById(this);return false;">
                            <fieldset><legend>Najít podle ID</legend>                    
                                <table>
                                    <tr>
                                        <th><label for="cf1">ID</label></th>
                                        <td><input type="text" name="findid"required="required" pattern="\d+"/></td>
                                    </tr>    
                                    <tr>
                                        <td colspan="2"><input type="submit" value="Vyhledat" /></td>
                                    </tr>
                                </table>
                            </fieldset>
                        </form>
                        
                        <script>
                            $(function(){
                                $(".datepick").datepicker();
                            });

                        </script>
                        <form method="POST" onsubmit="pridatZakaznika(this);return false;">
                            <fieldset>
                                <legend onclick="$('#pridat_zakaznika_table').toggle();">Přidat zákazníka</legend>   
                                <table class="w90" id="pridat_zakaznika_table">
                                    <tr>
                                        <th class="w20 r"><label for="c1">Jméno</label></th>
                                        <td class="w80"><input type="text" id="c1" name="firstName" class="w90" required="required"/></td>
                                    </tr>
                                    <tr>
                                        <th class="w20 r"><label for="c2">Příjmení</label></th>
                                        <td class="w80"><input type="text" id="c2" name="lastName" class="w90" required="required"/></td>
                                    </tr>
                                    <tr>
                                        <th class="w20 r"><label for="c2">Adresa</label></th>
                                        <td class="w80"><input type="text" id="c3" name="address" class="w90" required="required"/></td>
                                    </tr>
                                    <tr>
                                        <th class="w20 r"><label for="c4">Datum narození</label></th>
                                        <td class="w80"><input type="text" id="c4" name="dateOfBirth" class="w90 datepick" required="required"/></td>
                                    </tr>
                                    <tr>
                                        <th class="w20 r"><label for="c5">PID</label></th>
                                        <td class="w80"><input type="text" id="c5" name="pid" class="w90" required="required"/></td>
                                    </tr>
                                    <tr>
                                        <td class="c w100" colspan="2"><input type="submit" name="pridat_zakaznika" value="Přidat"/></td>
                                    </tr>
                                </table>
                            </fieldset>
                        </form>
                    </div>
                    <div id="books" style="display:none">
                        <h1>
                            Knihy
                        </h1>
                    </div>   
		</div>
	    </div>
	    <div id="footerWrapper">
		<div id="footer">
		    Copyright &copy; 2013 fi.muni.cz
		</div>
	    </div>
	</div>
    </body>
</html>