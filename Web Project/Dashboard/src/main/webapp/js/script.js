function showPassword() 
{
    var password = $('#pass').attr('type');
    
    if(password != 'text')
	{
        $('.checkbox').addClass('show');
        $('#pass').attr('type', 'text');
    } else
	{
        $('.checkbox').removeClass('show');
        $('#pass').attr('type', 'password');
    } 
}
