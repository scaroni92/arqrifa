/* global Materialize */

// Inicialización
$('.button-collapse').sideNav({
    menuWidth: 300
    , closeOnClick: false
});
$('.profile-dropdown').dropdown({
    inDuration: 300
    , outDuration: 225
    , constrainWidth: false
    , hover: false
    , gutter: 0
    , belowOrigin: false
    , alignment: 'right'
    , stopPropagation: false
});
// Funciones
function toast(mensaje) {
    Materialize.toast(mensaje, 4000);
}
