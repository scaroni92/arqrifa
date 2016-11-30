<%@attribute name="name"%>
<%@attribute name="valores"%>

<input type="text" id="valor"/>
<input type="button" value="Agregar" onclick="agregar()"><br>
<textarea name="${name}" id="valores" readonly>${valores}</textarea>

<script>
    function agregar() {
        var txtValor = document.getElementById("valor");
        var valor = txtValor.value;

        if (valor !== "") {
            document.getElementById("valores").innerHTML += valor + "\n";
            txtValor.value = "";
        }
    }
</script>