//datatables - list instructos
$(document).ready(function() {
	moment.locale('pt-BR');
	var table = $('#table-users').DataTable({
		searching : true,
		lengthMenu : [ 5, 10 ],
		processing : true,
		serverSide : true,
		responsive : true,
		ajax : {
			url : '/u/datatables/server/users',
			data : 'data'
		},
		columns : [
				{data : 'id'},
				{data : 'email'},
				{	data : 'active', 
					render : function(active) {
						return active == true ? 'Yes' : 'No';
					}
				},
				{	data : 'profiles',									 
					render : function(profiles) {
						var aux = new Array();
						$.each(profiles, function( index, value ) {
							  aux.push(value.desc);
						});
						return aux;
					},
					orderable : false,
				},
				{	data : 'id',	
					render : function(id) {
						return ''.concat('<a class="btn btn-success btn-sm btn-block"', ' ')
								 .concat('href="').concat('/u/edit/credentials/user/').concat(id, '"', ' ') 
								 .concat('role="button" title="Edit" data-toggle="tooltip" data-placement="right">', ' ')
								 .concat('<i class="fas fa-edit"></i></a>');
					},
					orderable : false
				}
		]
	});
	
    $('#table-users tbody').on('click', '[id*="dp_"]', function () {
    	var data = table.row($(this).parents('tr')).data();
    	var aux = new Array();
		$.each(data.profiles, function( index, value ) {
			  aux.push(value.id);
		});
		document.location.href = '/u/edit/datas/user/' + data.id + '/profiles/' + aux;
    } );	
	
});	

$('.pass').keyup(function(){
	$('#password1').val() === $('#password2').val()
	    ? $('#password3').removeAttr('readonly')
	    : $('#password3').attr('readonly', 'readonly');
});

