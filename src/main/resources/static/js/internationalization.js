<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js">
</script>
<script type="text/javascript">
$(document).ready(function() {
    $("#locales").change(function () {
        var selectedOption = $('#locales').val();
        if (selectedOption != ''){
            window.location.replace('international?lang=' + selectedOption);
            
            var table = $('#table-idioma').DataTable({
            	searching: true,
            	order: [[ 1, "asc" ]],
            	lengthMenu: [5, 10],
                processing: true,
                serverSide: true,
                responsive: true,
                columns: [
                    {data: 'id'},
                    {data: 'titulo'},
                    {orderable: false, 
                     data: 'id',
                        "render": function(selectedOption) {
                            return '<a class="btn btn-success btn-sm btn-block" href="/idioma/lang/'+ 
                            selectedOption +'" role="button"><i class="fas fa-edit"></i></a>';
                        }
                    }
                ]
            });
        }    
        
    });
});
</script>
   
