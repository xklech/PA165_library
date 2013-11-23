<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<script>
    $(function(){
        $("#b5").datepicker();
    });

</script>
    <table class="w100">
        <tr>
            <th class="w20 r"><s:label for="b1" name="book.author"/></th>
            <td class="w80"><s:text id="b1" name="book.author"/><s:errors field="book.author"/></td>
        </tr>
        <tr>
            <th class="w20 r"><s:label for="b2" name="book.name"/></th>
            <td class="w80"><s:text id="b2" name="book.name"/><s:errors field="book.name"/></td>
        </tr>
        <tr>
            <th class="w20 r"><s:label for="b3" name="book.isbn"/></th>
            <td class="w80"><s:text id="b3" name="book.isbn"/><s:errors field="book.isbn"/></td>
        </tr>
        <tr>
            <th class="w20 r"><s:label for="b4" name="book.department"/></th>
            <td class="w80"><s:text id="b4" name="book.department"/><s:errors field="book.department"/></td>
        </tr>
        <tr>
            <th class="w20 r"><s:label for="b5" name="book.publishDate"/></th>
            <td class="w80"><s:text id="b5" name="book.publishDate"/><s:errors field="book.publishDate"/></td>
        </tr>
    </table>
