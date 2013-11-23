<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<script>
    $(function(){
        $("#c4").datepicker();
    });

</script>
    <table class="w100">
        <tr>
            <th class="w20 r"><s:label for="c1" name="customer.firstName"/></th>
            <td class="w80"><s:text id="c1" name="customer.firstName"/><s:errors field="customer.firstName"/></td>
        </tr>
        <tr>
            <th class="w20 r"><s:label for="c2" name="customer.lastName"/></th>
            <td class="w80"><s:text id="c2" name="customer.lastName"/><s:errors field="customer.lastName"/></td>
        </tr>
        <tr>
            <th class="w20 r"><s:label for="c3" name="customer.address"/></th>
            <td class="w80"><s:text id="c3" name="customer.address"/><s:errors field="customer.address"/></td>
        </tr>
        <tr>
            <th class="w20 r"><s:label for="c4" name="customer.dateOfBirth"/></th>
            <td class="w80"><s:text id="c4" name="customer.dateOfBirth"/><s:errors field="customer.dateOfBirth"/></td>
        </tr>
        <tr>
            <th class="w20 r"><s:label for="c5" name="customer.pid"/></th>
            <td class="w80"><s:text id="c5" name="customer.pid"/><s:errors field="customer.pid"/></td>
        </tr>
    </table>
