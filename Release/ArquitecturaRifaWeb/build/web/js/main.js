$('select').material_select();
$('.modal').modal();
$('.datepicker').pickadate({
    selectMonths: true
    , min: true
    , format: 'yyyy-mm-dd'
});
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

// TODO solucionar funcionamiento de tooltips
/*$('.toolbar ul a').addClass('tooltipped');
$('.toolbar .tooltipped').tooltip({delay: 50, position: 'right'});*/