$(document).ready(function() {
    moment.locale('pt-BR');
    var table = $('#table-student-historic').DataTable({
        searching : false,
        lengthMenu : [ 5, 10 ],
        processing : true,
        serverSide : true,
        responsive : true,
        order: [2, 'desc'],
        ajax : {
            url : '/schedulings/datatables/server/historic',
            data : 'data'
        },
        columns : [
            {data : 'id'},
            {data : 'student.name'},
            {data: 'dateConsult', render:
                function( dateConsult ) {
                    return moment(dateConsult).format('LLL');
                }
            },
            {data : 'instructor.name'},
            {orderable : false,	data : 'id', "render" : function(id) {
                    return '<a class="btn btn-success btn-sm btn-block" href="/schedulings/edit/query/'
                            + id + '" role="button"><i class="fas fa-edit"></i></a>';
                }
            },
            {orderable : false,	data : 'id', "render" : function(id) {
                    return '<a class="btn btn-danger btn-sm btn-block" href="/schedulings/delete/query/'
                    + id +'" role="button" data-toggle="modal" data-target="#confirm-modal"><i class="fas fa-times-circle"></i></a>';
                }
            }
        ]
    });
});
