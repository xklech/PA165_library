<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

    <table class="w100">
        <tr>
            <th class="w20 r"><s:label for="b1" name="impression.initialDamage"/></th>
            <td class="w80">
                <s:select id="b1" name="impression.initialDamage"><s:options-enumeration enum="cz.muni.fi.pa165.library.enums.DamageType"/></s:select><s:errors field="impression.initialDamage"/>
            </td>
        </tr>
        <tr>
            <th class="w20 r"><s:label for="b2" name="impression.damage"/></th>
            <td class="w80"><s:select id="b2" name="impression.damage"><s:options-enumeration enum="cz.muni.fi.pa165.library.enums.DamageType"/></s:select><s:errors field="impression.damage"/></td>
        </tr>
        <tr>
            <th class="w20 r"><s:label for="b3" name="impression.status"/></th>
            <td class="w80"><s:select id="b2" name="impression.status"><s:options-enumeration enum="cz.muni.fi.pa165.library.enums.StatusType"/></s:select><s:errors field="impression.status"/></td>
        </tr>
    </table>
