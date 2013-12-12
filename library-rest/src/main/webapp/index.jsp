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
                        <table class="table_display w100" id="zakaznici_table" style="display:none">
                            <tr>
                                <th class="c b">ID</th>
                                <th class="c b">Jméno</th>
                                <th class="c b">Příjmení</th>
                                <th class="c b">Adresa</th>
                                <th class="c b">Datum narození</th>
                                <th class="c b">PID</th>
                                <th class="c b">Akce</th>
                            </tr>
                        </table>
                        
                        <form method="POST" onsubmit="findById(this,true);return false;">
                            <fieldset><legend>Najít podle ID</legend>                    
                                <table>
                                    <tr>
                                        <th><label for="cf1">ID</label></th>
                                        <td><input type="text" name="findid" required="required" pattern="\d+"/></td>
                                    </tr>    
                                    <tr>
                                        <td colspan="2"><input type="submit" value="Vyhledat" /></td>
                                    </tr>
                                </table>
                            </fieldset>
                        </form>
                        
                        <form method="POST" onsubmit="findByName(this.findname.value,this.findsurname.value);return false;">
                            <fieldset><legend>Najít podle jména</legend>                    
                                <table>
                                    <tr>
                                        <th><label for="cfn1">Jméno</label></th>
                                        <td><input id="cfn1" type="text" name="findname" /></td>
                                    </tr> 
                                    <tr>
                                        <th><label for="cfn2">Příjmení</label></th>
                                        <td><input id="cfn2" type="text" name="findsurname" /></td>
                                    </tr>    
                                    <tr>
                                        <td colspan="2"><input type="submit" value="Vyhledat" /></td>
                                    </tr>
                                </table>
                            </fieldset>
                        </form>
                        

                        <form id="pridat_zakaznika" method="POST" onsubmit="pridatZakaznika(this);return false;">
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
                                        <th class="w20 r"><label for="c3">Adresa</label></th>
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
                        <form style="display:none" id="upravit_zakaznika" method="POST" onsubmit="upravitZakaznika(this);return false;">
                            <fieldset>
                                <legend onclick="$('#upravit_zakaznika_table').toggle();">Upravit zákazníka</legend>   
                                <table class="w90" id="upravit_zakaznika_table">
                                    <tr>
                                        <th class="w20 r"><label for="uc1">Jméno</label></th>
                                        <td class="w80"><input type="text" id="uc1" name="firstName" class="w90" required="required"/></td>
                                    </tr>
                                    <tr>
                                        <th class="w20 r"><label for="uc2">Příjmení</label></th>
                                        <td class="w80"><input type="text" id="uc2" name="lastName" class="w90" required="required"/></td>
                                    </tr>
                                    <tr>
                                        <th class="w20 r"><label for="uc3">Adresa</label></th>
                                        <td class="w80"><input type="text" id="uc3" name="address" class="w90" required="required"/></td>
                                    </tr>
                                    <tr>
                                        <th class="w20 r"><label for="uc4">Datum narození</label></th>
                                        <td class="w80"><input type="text" id="uc4" name="dateOfBirth" class="w90 datepick" required="required"/></td>
                                    </tr>
                                    <tr>
                                        <th class="w20 r"><label for="uc5">PID</label></th>
                                        <td class="w80"><input type="text" id="uc5" name="pid" class="w90" required="required"/></td>
                                    </tr>
                                    <tr>
                                        <td class="c w100" colspan="2"><input type="submit" name="upravit_zakaznika" value="Upravit"/> <input type="submit" name="zrusit" value="Zrušit" onclick="zrusitUpravuZakaznika();return false;"/></td>
                                    </tr>
                                </table>
                            </fieldset>
                            <input type="hidden" name="customer_id" />
                        </form>
                    </div>
                    <div id="books" style="display:none">
                        <h1>
                            Knihy
                        </h1>
                        <table class="table_display w100" id="knihy_table" style="display:none">
                            <tr>
                                <th class="c b">ID</th>
                                <th class="c b">Název</th>
                                <th class="c b">ISBN</th>
                                <th class="c b">Oddělení</th>
                                <th class="c b">Datum vydání</th>
                                <th class="c b">Autor</th>
                                <th class="c b">Akce</th>
                            </tr>
                        </table>
                        
                        <form method="POST" onsubmit="findBookById(this,true);return false;">
                            <fieldset><legend>Najít knihu podle ID</legend>                    
                                <table>
                                    <tr>
                                        <th><label for="bf1">ID</label></th>
                                        <td><input type="text" id="bf1" name="findid_book" required="required" pattern="\d+"/></td>
                                    </tr>    
                                    <tr>
                                        <td colspan="2"><input type="submit" value="Vyhledat" /></td>
                                    </tr>
                                </table>
                            </fieldset>
                        </form>
                        
                        <form method="POST" onsubmit="findBookByName(this.find_book_name.value);return false;">
                            <fieldset><legend>Najít knihu podle názvu</legend>                    
                                <table>
                                    <tr>
                                        <th><label for="bfn">Název</label></th>
                                        <td><input type="text" id="bfn" name="find_book_name" required="required"/></td>
                                    </tr>    
                                    <tr>
                                        <td colspan="2"><input type="submit" value="Vyhledat" /></td>
                                    </tr>
                                </table>
                            </fieldset>
                        </form>
                        
                        <form method="POST" onsubmit="findBookByAuthor(this.find_book_author.value);return false;">
                            <fieldset><legend>Najít knihu podle autora</legend>                    
                                <table>
                                    <tr>
                                        <th><label for="bfn">Autor</label></th>
                                        <td><input type="text" id="bfn" name="find_book_author" required="required"/></td>
                                    </tr>    
                                    <tr>
                                        <td colspan="2"><input type="submit" value="Vyhledat" /></td>
                                    </tr>
                                </table>
                            </fieldset>
                        </form>
                        
                        <form method="POST" onsubmit="findBookByDepartment(this.find_book_department.value);return false;">
                            <fieldset><legend>Najít knihu podle oddělení</legend>                    
                                <table>
                                    <tr>
                                        <th><label for="bfd">Oddělení</label></th>
                                        <td><input type="text" id="bfd" name="find_book_department" required="required"/></td>
                                    </tr>    
                                    <tr>
                                        <td colspan="2"><input type="submit" value="Vyhledat" /></td>
                                    </tr>
                                </table>
                            </fieldset>
                        </form>
                        
                        <form method="POST" onsubmit="findBookByIsbn(this.find_book_isbn.value);return false;">
                            <fieldset><legend>Najít knihu podle isbn</legend>                    
                                <table>
                                    <tr>
                                        <th><label for="bfi">Isbn</label></th>
                                        <td><input type="text" id="bfi" name="find_book_isbn" required="required"/></td>
                                    </tr>    
                                    <tr>
                                        <td colspan="2"><input type="submit" value="Vyhledat" /></td>
                                    </tr>
                                </table>
                            </fieldset>
                        </form>
                        
                        <form id="pridat_knihu" method="POST" onsubmit="pridatKnihu(this);return false;">
                            <fieldset>
                                <legend onclick="$('#pridat_knihu_table').toggle();">Přidat knihu</legend>   
                                <table class="w90" id="pridat_knihu_table">
                                    <tr>
                                        <th class="w20 r"><label for="b1">Název</label></th>
                                        <td class="w80"><input type="text" id="b1" name="name" class="w90" required="required"/></td>
                                    </tr>
                                    <tr>
                                        <th class="w20 r"><label for="b2">ISBN</label></th>
                                        <td class="w80"><input type="text" id="b2" name="isbn" class="w90" required="required" pattern=".{12,}" title="Minimálně 12 znaků"/></td>
                                    </tr>
                                    <tr>
                                        <th class="w20 r"><label for="b3">Oddělení</label></th>
                                        <td class="w80"><input type="text" id="b3" name="department" class="w90" required="required"/></td>
                                    </tr>
                                    <tr>
                                        <th class="w20 r"><label for="b4">Datum vydání</label></th>
                                        <td class="w80"><input type="text" id="b4" name="publishDate" class="w90 datepick" required="required"/></td>
                                    </tr>
                                    <tr>
                                        <th class="w20 r"><label for="b5">Autor</label></th>
                                        <td class="w80"><input type="text" id="b5" name="author" class="w90" required="required"/></td>
                                    </tr>
                                    <tr>
                                        <td class="c w100" colspan="2"><input type="submit" name="pridat_knihu" value="Přidat"/></td>
                                    </tr>
                                </table>
                            </fieldset>
                        </form>
                        <form style="display:none" id="upravit_knihu" method="POST" onsubmit="upravitKnihu(this);return false;">
                            <fieldset>
                                <legend onclick="$('#upravit_knihu_table').toggle();">Upravit knihu</legend>   
                                <table class="w90" id="upravit_knihu_table">
                                    <tr>
                                        <th class="w20 r"><label for="ub1">Název</label></th>
                                        <td class="w80"><input type="text" id="ub1" name="name" class="w90" required="required"/></td>
                                    </tr>
                                    <tr>
                                        <th class="w20 r"><label for="ub2">ISBN</label></th>
                                        <td class="w80"><input type="text" id="ub2" name="isbn" class="w90" required="required" pattern=".{12,}" title="Minimálně 12 znaků"/></td>
                                    </tr>
                                    <tr>
                                        <th class="w20 r"><label for="ub3">Oddělení</label></th>
                                        <td class="w80"><input type="text" id="ub3" name="department" class="w90" required="required"/></td>
                                    </tr>
                                    <tr>
                                        <th class="w20 r"><label for="ub4">Datum vydání</label></th>
                                        <td class="w80"><input type="text" id="ub4" name="publishDate" class="w90 datepick" required="required"/></td>
                                    </tr>
                                    <tr>
                                        <th class="w20 r"><label for="ub5">Autor</label></th>
                                        <td class="w80"><input type="text" id="ub5" name="author" class="w90" required="required"/></td>
                                    </tr>
                                    <tr>
                                        <td class="c w100" colspan="2"><input type="submit" name="upravit_knihu" value="Upravit"/> <input type="submit" name="zrusit" value="Zrušit" onclick="zrusitUpravuKnihy();return false;"/></td>
                                    </tr>
                                </table>
                            </fieldset>
                            <input type="hidden" name="book_id" />
                        </form>
                        
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